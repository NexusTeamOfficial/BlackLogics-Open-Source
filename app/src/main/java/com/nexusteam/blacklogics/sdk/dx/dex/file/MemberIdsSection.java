
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.DexFormat;
import mod.agus.jcoderz.dex.DexIndexOverflowException;
import java.util.Formatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MemberIdsSection extends UniformItemSection {

    public MemberIdsSection(String name, DexFile file) {
        super(name, file, 4);
    }

    @Override
    protected void orderItems() {
        int idx = 0;

        if (items().size() > DexFormat.MAX_MEMBER_IDX + 1) {
            throw new DexIndexOverflowException(getTooManyMembersMessage());
        }

        for (Object i : items()) {
            ((MemberIdItem) i).setIndex(idx);
            idx++;
        }
    }

    private String getTooManyMembersMessage() {
        Map<String, AtomicInteger> membersByPackage = new TreeMap<String, AtomicInteger>();
        for (Object member : items()) {
            String packageName = ((MemberIdItem) member).getDefiningClass().getPackageName();
            AtomicInteger count = membersByPackage.get(packageName);
            if (count == null) {
                count = new AtomicInteger();
                membersByPackage.put(packageName, count);
            }
            count.incrementAndGet();
        }

        Formatter formatter = new Formatter();
        try {
            String memberType = this instanceof MethodIdsSection ? "method" : "field";
            formatter.format("Too many %1$s references to fit in one dex file: %2$d; max is %3$d.%n" +
                            "You may try using multi-dex. If multi-dex is enabled then the list of " +
                            "classes for the main dex list is too large.%n" +
                    "References by package:",
                    memberType, items().size(), DexFormat.MAX_MEMBER_IDX + 1);
            for (Map.Entry<String, AtomicInteger> entry : membersByPackage.entrySet()) {
                formatter.format("%n%6d %s", entry.getValue().get(), entry.getKey());
            }
            return formatter.toString();
        } finally {
            formatter.close();
        }
    }

}
