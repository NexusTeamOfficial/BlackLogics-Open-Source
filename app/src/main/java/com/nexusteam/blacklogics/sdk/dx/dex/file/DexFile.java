
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.DexFormat;
import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.dex.DexOptions;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef;
import mod.agus.jcoderz.dx.rop.cst.CstEnumRef;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstMethodHandle;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Adler32;

public final class DexFile {
    private final mod.agus.jcoderz.dx.dex.DexOptions dexOptions;

    private final MixedItemSection wordData;

    private final MixedItemSection typeLists;

    private final MixedItemSection map;

    private final MixedItemSection stringData;

    private final mod.agus.jcoderz.dx.dex.file.StringIdsSection stringIds;

    private final mod.agus.jcoderz.dx.dex.file.TypeIdsSection typeIds;

    private final mod.agus.jcoderz.dx.dex.file.ProtoIdsSection protoIds;

    private final mod.agus.jcoderz.dx.dex.file.FieldIdsSection fieldIds;

    private final MethodIdsSection methodIds;

    private final mod.agus.jcoderz.dx.dex.file.ClassDefsSection classDefs;

    private final MixedItemSection classData;

    private final mod.agus.jcoderz.dx.dex.file.CallSiteIdsSection callSiteIds;

    private final mod.agus.jcoderz.dx.dex.file.MethodHandlesSection methodHandles;

    private final MixedItemSection byteData;

    private final HeaderSection header;

    private final Section[] sections;

    private int fileSize;

    private int dumpWidth;

    public DexFile(final mod.agus.jcoderz.dx.dex.DexOptions dexOptions) {
        this.dexOptions = dexOptions;

        header = new HeaderSection(this);
        typeLists = new MixedItemSection(null, this, 4, MixedItemSection.SortType.NONE);
        wordData = new MixedItemSection("word_data", this, 4, MixedItemSection.SortType.TYPE);
        stringData =
            new MixedItemSection("string_data", this, 1, MixedItemSection.SortType.INSTANCE);
        classData = new MixedItemSection(null, this, 1, MixedItemSection.SortType.NONE);
        byteData = new MixedItemSection("byte_data", this, 1, MixedItemSection.SortType.TYPE);
        stringIds = new mod.agus.jcoderz.dx.dex.file.StringIdsSection(this);
        typeIds = new mod.agus.jcoderz.dx.dex.file.TypeIdsSection(this);
        protoIds = new mod.agus.jcoderz.dx.dex.file.ProtoIdsSection(this);
        fieldIds = new mod.agus.jcoderz.dx.dex.file.FieldIdsSection(this);
        methodIds = new MethodIdsSection(this);
        classDefs = new mod.agus.jcoderz.dx.dex.file.ClassDefsSection(this);
        map = new MixedItemSection("map", this, 4, MixedItemSection.SortType.NONE);

        if (dexOptions.apiIsSupported(DexFormat.API_METHOD_HANDLES)) {
            callSiteIds = new mod.agus.jcoderz.dx.dex.file.CallSiteIdsSection(this);
            methodHandles = new mod.agus.jcoderz.dx.dex.file.MethodHandlesSection(this);

            sections = new Section[] {
                header, stringIds, typeIds, protoIds, fieldIds, methodIds, classDefs,
                callSiteIds, methodHandles,
                wordData, typeLists, stringData, byteData, classData, map };
        } else {
            callSiteIds = null;
            methodHandles = null;

            sections = new Section[] {
                header, stringIds, typeIds, protoIds, fieldIds, methodIds, classDefs,
                wordData, typeLists, stringData, byteData, classData, map };
        }

        fileSize = -1;
        dumpWidth = 79;
    }

    public boolean isEmpty() {
        return classDefs.items().isEmpty();
    }

    public DexOptions getDexOptions() {
        return dexOptions;
    }

    public void add(ClassDefItem clazz) {
        classDefs.add(clazz);
    }

    public ClassDefItem getClassOrNull(String name) {
        try {
            mod.agus.jcoderz.dx.rop.type.Type type = Type.internClassName(name);
            return (ClassDefItem) classDefs.get(new mod.agus.jcoderz.dx.rop.cst.CstType(type));
        } catch (IllegalArgumentException ex) {

            return null;
        }
    }

    public void writeTo(OutputStream out, Writer humanOut, boolean verbose)
        throws IOException {
        writeTo(out, null , humanOut, verbose);
    }


    public void writeTo(OutputStream out, Storage storage, Writer humanOut, boolean verbose)
            throws IOException {
        boolean annotate = (humanOut != null);
        mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput result = toDex0(annotate, verbose, storage);

        if (out != null) {
            out.write(result.getArray());
        }

        if (annotate) {
            result.writeAnnotationsTo(humanOut);
        }
    }

    public mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput writeTo(Storage storage) {
        return toDex0(false, false, storage);
    }


    public byte[] toDex(Writer humanOut, boolean verbose)
        throws IOException {
        boolean annotate = (humanOut != null);
        mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput result = toDex0(annotate, verbose, null);

        if (annotate) {
            result.writeAnnotationsTo(humanOut);
        }

        return result.getArray();
    }

    public void setDumpWidth(int dumpWidth) {
        if (dumpWidth < 40) {
            throw new IllegalArgumentException("dumpWidth < 40");
        }

        this.dumpWidth = dumpWidth;
    }

    public int getFileSize() {
        if (fileSize < 0) {
            throw new RuntimeException("file size not yet known");
        }

        return fileSize;
    }

     MixedItemSection getStringData() {
        return stringData;
    }

     MixedItemSection getWordData() {
        return wordData;
    }

     MixedItemSection getTypeLists() {
        return typeLists;
    }

     MixedItemSection getMap() {
        return map;
    }

     StringIdsSection getStringIds() {
        return stringIds;
    }

    public ClassDefsSection getClassDefs() {
        return classDefs;
    }

     MixedItemSection getClassData() {
        return classData;
    }

    public TypeIdsSection getTypeIds() {
        return typeIds;
    }

     ProtoIdsSection getProtoIds() {
        return protoIds;
    }

    public FieldIdsSection getFieldIds() {
        return fieldIds;
    }

    public MethodIdsSection getMethodIds() {
        return methodIds;
    }

    public MethodHandlesSection getMethodHandles() {
        return methodHandles;
    }

    public CallSiteIdsSection getCallSiteIds() {
        return callSiteIds;
    }

     MixedItemSection getByteData() {
        return byteData;
    }

     Section getFirstDataSection() {
        return wordData;
    }

     Section getLastDataSection() {
        return map;
    }

     void internIfAppropriate(mod.agus.jcoderz.dx.rop.cst.Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstString) {
            stringIds.intern((mod.agus.jcoderz.dx.rop.cst.CstString) cst);
        } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstType) {
            typeIds.intern((mod.agus.jcoderz.dx.rop.cst.CstType) cst);
        } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef) {
            methodIds.intern((mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef) cst);
        } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstFieldRef) {
            fieldIds.intern((mod.agus.jcoderz.dx.rop.cst.CstFieldRef) cst);
        } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstEnumRef) {
            fieldIds.intern(((mod.agus.jcoderz.dx.rop.cst.CstEnumRef) cst).getFieldRef());
        } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstProtoRef) {
            protoIds.intern(((mod.agus.jcoderz.dx.rop.cst.CstProtoRef) cst).getPrototype());
        } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstMethodHandle) {
            methodHandles.intern((mod.agus.jcoderz.dx.rop.cst.CstMethodHandle) cst);
        }
    }

     IndexedItem findItemOrNull(Constant cst) {
        if (cst instanceof CstString) {
            return stringIds.get(cst);
        } else if (cst instanceof CstType) {
            return typeIds.get(cst);
        } else if (cst instanceof CstBaseMethodRef) {
            return methodIds.get(cst);
        } else if (cst instanceof CstFieldRef) {
            return fieldIds.get(cst);
        } else if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstEnumRef) {
            return fieldIds.intern(((CstEnumRef) cst).getFieldRef());
        } else if (cst instanceof CstProtoRef) {
            return protoIds.get(cst);
        } else if (cst instanceof CstMethodHandle) {
            return methodHandles.get(cst);
        } else if (cst instanceof CstCallSiteRef) {
            return callSiteIds.get(cst);
        } else {
            return null;
        }
    }

    public static final class Storage {
        byte[] storage;
        public Storage(byte[] storage) {
            this.storage = storage;
        }

        public byte[] getStorage(int requestedLength) {
            if (storage.length < requestedLength) {
                Logger.getAnonymousLogger().log(
                        Level.FINER,
                        "DexFile storage too small  "
                                + storage.length
                                + " vs "
                                + requestedLength);
                storage = new byte[requestedLength];
            }
            return storage;
        }
    }

    private mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput toDex0(boolean annotate,
                                                                     boolean verbose,
                                                                     Storage storage) {

        classDefs.prepare();
        classData.prepare();
        wordData.prepare();
        if (dexOptions.apiIsSupported(DexFormat.API_METHOD_HANDLES)) {

            callSiteIds.prepare();
        }
        byteData.prepare();
        if (dexOptions.apiIsSupported(DexFormat.API_METHOD_HANDLES)) {

            methodHandles.prepare();
        }
        methodIds.prepare();
        fieldIds.prepare();
        protoIds.prepare();
        typeLists.prepare();
        typeIds.prepare();
        stringIds.prepare();
        stringData.prepare();
        header.prepare();



        int count = sections.length;
        int offset = 0;

        for (int i = 0; i < count; i++) {
            Section one = sections[i];
            if ((one == callSiteIds || one == methodHandles) && one.items().isEmpty()) {
                continue;
            }

            int placedAt = one.setFileOffset(offset);
            if (placedAt < offset) {
                throw new RuntimeException("bogus placement for section " + i);
            }

            try {
                if (one == map) {
                    MapItem.addMap(sections, map);
                    map.prepare();
                }

                if (one instanceof MixedItemSection) {
                    ((MixedItemSection) one).placeItems();
                }

                offset = placedAt + one.writeSize();
            } catch (RuntimeException ex) {
                throw ExceptionWithContext.withContext(ex,
                        "...while writing section " + i);
            }
        }



        fileSize = offset;
        byte[] barr = storage == null ? new byte[fileSize] : storage.getStorage(fileSize);

        mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput out = new ByteArrayAnnotatedOutput(barr);

        if (annotate) {
            out.enableAnnotations(dumpWidth, verbose);
        }

        for (int i = 0; i < count; i++) {
            try {
                final Section one = sections[i];
                if ((one == callSiteIds || one == methodHandles) && one.items().isEmpty()) {
                   continue;
                }
                final int zeroCount = one.getFileOffset() - out.getCursor();
                if (zeroCount < 0) {
                    throw new ExceptionWithContext("excess write of " +
                            (-zeroCount));
                }
                out.writeZeroes(zeroCount);
                one.writeTo(out);
            } catch (RuntimeException ex) {
                ExceptionWithContext ec;
                if (ex instanceof ExceptionWithContext) {
                    ec = (ExceptionWithContext) ex;
                } else {
                    ec = new ExceptionWithContext(ex);
                }
                ec.addContext("...while writing section " + i);
                throw ec;
            }
        }

        if (out.getCursor() != fileSize) {
            throw new RuntimeException("foreshortened write");
        }



        calcSignature(barr, out.getCursor());
        calcChecksum(barr, out.getCursor());

        if (annotate) {
            wordData.writeIndexAnnotation(out, ItemType.TYPE_CODE_ITEM,
                    "\nmethod code index:\n\n");
            getStatistics().writeAnnotation(out);
            out.finishAnnotating();
        }

        return out;
    }

    public mod.agus.jcoderz.dx.dex.file.Statistics getStatistics() {
        mod.agus.jcoderz.dx.dex.file.Statistics stats = new Statistics();

        for (Section s : sections) {
            stats.addAll(s);
        }

        return stats;
    }

    private static void calcSignature(byte[] bytes, int len) {
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }

        md.update(bytes, 32, len - 32);

        try {
            int amt = md.digest(bytes, 12, 20);
            if (amt != 20) {
                throw new RuntimeException("unexpected digest write: " + amt +
                        " bytes");
            }
        } catch (DigestException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void calcChecksum(byte[] bytes, int len) {
        Adler32 a32 = new Adler32();

        a32.update(bytes, 12, len - 12);

        int sum = (int) a32.getValue();

        bytes[8]  = (byte) sum;
        bytes[9]  = (byte) (sum >> 8);
        bytes[10] = (byte) (sum >> 16);
        bytes[11] = (byte) (sum >> 24);
    }
}
