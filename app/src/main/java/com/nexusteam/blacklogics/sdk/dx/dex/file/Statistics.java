
package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public final class Statistics {
    private final HashMap<String, Data> dataMap;

    public Statistics() {
        dataMap = new HashMap<String, Data>(50);
    }

    public void add(Item item) {
        String typeName = item.typeName();
        Data data = dataMap.get(typeName);

        if (data == null) {
            dataMap.put(typeName, new Data(item, typeName));
        } else {
            data.add(item);
        }
    }

    public void addAll(Section list) {
        Collection<? extends Item> items = list.items();
        for (Item item : items) {
            add(item);
        }
    }

    public final void writeAnnotation(mod.agus.jcoderz.dx.util.AnnotatedOutput out) {
        if (dataMap.size() == 0) {
            return;
        }

        out.annotate(0, "\nstatistics:\n");

        TreeMap<String, Data> sortedData = new TreeMap<String, Data>();

        for (Data data : dataMap.values()) {
            sortedData.put(data.name, data);
        }

        for (Data data : sortedData.values()) {
            data.writeAnnotation(out);
        }
    }

    public String toHuman() {
        StringBuilder sb = new StringBuilder();

        sb.append("Statistics:\n");

        TreeMap<String, Data> sortedData = new TreeMap<String, Data>();

        for (Data data : dataMap.values()) {
            sortedData.put(data.name, data);
        }

        for (Data data : sortedData.values()) {
            sb.append(data.toHuman());
        }

        return sb.toString();
    }

    private static class Data {
        private final String name;

        private int count;

        private int totalSize;

        private int largestSize;

        private int smallestSize;

        public Data(Item item, String name) {
            int size = item.writeSize();

            this.name = name;
            this.count = 1;
            this.totalSize = size;
            this.largestSize = size;
            this.smallestSize = size;
        }

        public void add(Item item) {
            int size = item.writeSize();

            count++;
            totalSize += size;

            if (size > largestSize) {
                largestSize = size;
            }

            if (size < smallestSize) {
                smallestSize = size;
            }
        }

        public void writeAnnotation(AnnotatedOutput out) {
            out.annotate(toHuman());
        }

        public String toHuman() {
            StringBuilder sb = new StringBuilder();

            sb.append("  " + name + ": " +
                         count + " item" + (count == 1 ? "" : "s") + "; " +
                         totalSize + " bytes total\n");

            if (smallestSize == largestSize) {
                sb.append("    " + smallestSize + " bytes/item\n");
            } else {
                int average = totalSize / count;
                sb.append("    " + smallestSize + ".." + largestSize +
                             " bytes/item; average " + average + "\n");
            }

            return sb.toString();
        }
    }
}
