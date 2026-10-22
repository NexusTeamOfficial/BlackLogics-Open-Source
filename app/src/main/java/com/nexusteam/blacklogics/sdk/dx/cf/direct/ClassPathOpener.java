
package mod.agus.jcoderz.dx.cf.direct;

import mod.agus.jcoderz.dex.util.FileUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassPathOpener {

    private final String pathname;
    private final Consumer consumer;
    private final boolean sort;
    private FileNameFilter filter;

    public interface Consumer {

        boolean processFileBytes(String name, long lastModified, byte[] bytes);

        void onException(Exception ex);

        void onProcessArchiveStart(File file);
    }

    public interface FileNameFilter {

        boolean accept(String path);
    }

    public static final FileNameFilter acceptAll = new FileNameFilter() {

        @Override
        public boolean accept(String path) {
            return true;
        }
    };

    public ClassPathOpener(String pathname, boolean sort, Consumer consumer) {
        this(pathname, sort, acceptAll, consumer);
    }

    public ClassPathOpener(String pathname, boolean sort, FileNameFilter filter,
            Consumer consumer) {
        this.pathname = pathname;
        this.sort = sort;
        this.consumer = consumer;
        this.filter = filter;
    }

    public boolean process() {
        File file = new File(pathname);

        return processOne(file, true);
    }

    private boolean processOne(File file, boolean topLevel) {
        try {
            if (file.isDirectory()) {
                return processDirectory(file, topLevel);
            }

            String path = file.getPath();

            if (path.endsWith(".zip") ||
                    path.endsWith(".jar") ||
                    path.endsWith(".apk")) {
                return processArchive(file);
            }
            if (filter.accept(path)) {
                byte[] bytes = FileUtils.readFile(file);
                return consumer.processFileBytes(path, file.lastModified(), bytes);
            } else {
                return false;
            }
        } catch (Exception ex) {
            consumer.onException(ex);
            return false;
        }
    }

    private static int compareClassNames(String a, String b) {

        a = a.replace('$','0');
        b = b.replace('$','0');

        a = a.replace("package-info", "");
        b = b.replace("package-info", "");

        return a.compareTo(b);
    }

    private boolean processDirectory(File dir, boolean topLevel) {
        if (topLevel) {
            dir = new File(dir, ".");
        }

        File[] files = dir.listFiles();
        int len = files.length;
        boolean any = false;

        if (sort) {
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File a, File b) {
                    return compareClassNames(a.getName(), b.getName());
                }
            });
        }

        for (int i = 0; i < len; i++) {
            any |= processOne(files[i], false);
        }

        return any;
    }

    private boolean processArchive(File file) throws IOException {
        ZipFile zip = new ZipFile(file);

        ArrayList<? extends java.util.zip.ZipEntry> entriesList
                = Collections.list(zip.entries());

        if (sort) {
            Collections.sort(entriesList, new Comparator<ZipEntry>() {
               @Override
               public int compare (ZipEntry a, ZipEntry b) {
                   return compareClassNames(a.getName(), b.getName());
               }
            });
        }

        consumer.onProcessArchiveStart(file);

        ByteArrayOutputStream baos = new ByteArrayOutputStream(40000);
        byte[] buf = new byte[20000];
        boolean any = false;

        for (ZipEntry one : entriesList) {
            final boolean isDirectory = one.isDirectory();

            String path = one.getName();
            if (filter.accept(path)) {
                final byte[] bytes;
                if (!isDirectory) {
                    InputStream in = zip.getInputStream(one);

                    baos.reset();
                    int read;
                    while ((read = in.read(buf)) != -1) {
                        baos.write(buf, 0, read);
                    }

                    in.close();
                    bytes = baos.toByteArray();
                } else {
                    bytes = new byte[0];
                }

                any |= consumer.processFileBytes(path, one.getTime(), bytes);
            }
        }

        zip.close();
        return any;
    }
}
