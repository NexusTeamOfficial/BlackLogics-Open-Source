
package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationsList;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public final class AnnotationsDirectoryItem extends mod.agus.jcoderz.dx.dex.file.OffsettedItem {
    private static final int ALIGNMENT = 4;

    private static final int HEADER_SIZE = 16;

    private static final int ELEMENT_SIZE = 8;

    private mod.agus.jcoderz.dx.dex.file.AnnotationSetItem classAnnotations;

    private ArrayList<mod.agus.jcoderz.dx.dex.file.FieldAnnotationStruct> fieldAnnotations;

    private ArrayList<mod.agus.jcoderz.dx.dex.file.MethodAnnotationStruct> methodAnnotations;

    private ArrayList<mod.agus.jcoderz.dx.dex.file.ParameterAnnotationStruct> parameterAnnotations;

    public AnnotationsDirectoryItem() {
        super(ALIGNMENT, -1);

        classAnnotations = null;
        fieldAnnotations = null;
        methodAnnotations = null;
        parameterAnnotations = null;
    }

    @Override
    public ItemType itemType() {
        return ItemType.TYPE_ANNOTATIONS_DIRECTORY_ITEM;
    }

    public boolean isEmpty() {
        return (classAnnotations == null) &&
            (fieldAnnotations == null) &&
            (methodAnnotations == null) &&
            (parameterAnnotations == null);
    }

    public boolean isInternable() {
        return (classAnnotations != null) &&
            (fieldAnnotations == null) &&
            (methodAnnotations == null) &&
            (parameterAnnotations == null);
    }

    @Override
    public int hashCode() {
        if (classAnnotations == null) {
            return 0;
        }

        return classAnnotations.hashCode();
    }

    @Override
    public int compareTo0(mod.agus.jcoderz.dx.dex.file.OffsettedItem other) {
        if (! isInternable()) {
            throw new UnsupportedOperationException("uninternable instance");
        }

        AnnotationsDirectoryItem otherDirectory =
            (AnnotationsDirectoryItem) other;
        return classAnnotations.compareTo(otherDirectory.classAnnotations);
    }

    public void setClassAnnotations(Annotations annotations, mod.agus.jcoderz.dx.dex.file.DexFile dexFile) {
        if (annotations == null) {
            throw new NullPointerException("annotations == null");
        }

        if (classAnnotations != null) {
            throw new UnsupportedOperationException(
                    "class annotations already set");
        }

        classAnnotations = new mod.agus.jcoderz.dx.dex.file.AnnotationSetItem(annotations, dexFile);
    }

    public void addFieldAnnotations(CstFieldRef field,
            Annotations annotations, mod.agus.jcoderz.dx.dex.file.DexFile dexFile) {
        if (fieldAnnotations == null) {
            fieldAnnotations = new ArrayList<mod.agus.jcoderz.dx.dex.file.FieldAnnotationStruct>();
        }

        fieldAnnotations.add(new mod.agus.jcoderz.dx.dex.file.FieldAnnotationStruct(field,
                        new mod.agus.jcoderz.dx.dex.file.AnnotationSetItem(annotations, dexFile)));
    }

    public void addMethodAnnotations(CstMethodRef method,
            Annotations annotations, mod.agus.jcoderz.dx.dex.file.DexFile dexFile) {
        if (methodAnnotations == null) {
            methodAnnotations = new ArrayList<mod.agus.jcoderz.dx.dex.file.MethodAnnotationStruct>();
        }

        methodAnnotations.add(new mod.agus.jcoderz.dx.dex.file.MethodAnnotationStruct(method,
                        new AnnotationSetItem(annotations, dexFile)));
    }

    public void addParameterAnnotations(CstMethodRef method,
            AnnotationsList list, mod.agus.jcoderz.dx.dex.file.DexFile dexFile) {
        if (parameterAnnotations == null) {
            parameterAnnotations = new ArrayList<mod.agus.jcoderz.dx.dex.file.ParameterAnnotationStruct>();
        }

        parameterAnnotations.add(new mod.agus.jcoderz.dx.dex.file.ParameterAnnotationStruct(method, list, dexFile));
    }

    public Annotations getMethodAnnotations(CstMethodRef method) {
        if (methodAnnotations == null) {
            return null;
        }

        for (mod.agus.jcoderz.dx.dex.file.MethodAnnotationStruct item : methodAnnotations) {
            if (item.getMethod().equals(method)) {
                return item.getAnnotations();
            }
        }

        return null;
    }

    public AnnotationsList getParameterAnnotations(CstMethodRef method) {
        if (parameterAnnotations == null) {
            return null;
        }

        for (mod.agus.jcoderz.dx.dex.file.ParameterAnnotationStruct item : parameterAnnotations) {
            if (item.getMethod().equals(method)) {
                return item.getAnnotationsList();
            }
        }

        return null;
    }

    @Override
    public void addContents(mod.agus.jcoderz.dx.dex.file.DexFile file) {
        MixedItemSection wordData = file.getWordData();

        if (classAnnotations != null) {
            classAnnotations = wordData.intern(classAnnotations);
        }

        if (fieldAnnotations != null) {
            for (mod.agus.jcoderz.dx.dex.file.FieldAnnotationStruct item : fieldAnnotations) {
                item.addContents(file);
            }
        }

        if (methodAnnotations != null) {
            for (mod.agus.jcoderz.dx.dex.file.MethodAnnotationStruct item : methodAnnotations) {
                item.addContents(file);
            }
        }

        if (parameterAnnotations != null) {
            for (mod.agus.jcoderz.dx.dex.file.ParameterAnnotationStruct item : parameterAnnotations) {
                item.addContents(file);
            }
        }
    }

    @Override
    public String toHuman() {
        throw new RuntimeException("unsupported");
    }

    @Override
    protected void place0(Section addedTo, int offset) {


        int elementCount = listSize(fieldAnnotations)
            + listSize(methodAnnotations) + listSize(parameterAnnotations);
        setWriteSize(HEADER_SIZE + (elementCount * ELEMENT_SIZE));
    }

    @Override
    protected void writeTo0(DexFile file, AnnotatedOutput out) {
        boolean annotates = out.annotates();
        int classOff = OffsettedItem.getAbsoluteOffsetOr0(classAnnotations);
        int fieldsSize = listSize(fieldAnnotations);
        int methodsSize = listSize(methodAnnotations);
        int parametersSize = listSize(parameterAnnotations);

        if (annotates) {
            out.annotate(0, offsetString() + " annotations directory");
            out.annotate(4, "  class_annotations_off: " + Hex.u4(classOff));
            out.annotate(4, "  fields_size:           " +
                    Hex.u4(fieldsSize));
            out.annotate(4, "  methods_size:          " +
                    Hex.u4(methodsSize));
            out.annotate(4, "  parameters_size:       " +
                    Hex.u4(parametersSize));
        }

        out.writeInt(classOff);
        out.writeInt(fieldsSize);
        out.writeInt(methodsSize);
        out.writeInt(parametersSize);

        if (fieldsSize != 0) {
            Collections.sort(fieldAnnotations);
            if (annotates) {
                out.annotate(0, "  fields:");
            }
            for (mod.agus.jcoderz.dx.dex.file.FieldAnnotationStruct item : fieldAnnotations) {
                item.writeTo(file, out);
            }
        }

        if (methodsSize != 0) {
            Collections.sort(methodAnnotations);
            if (annotates) {
                out.annotate(0, "  methods:");
            }
            for (mod.agus.jcoderz.dx.dex.file.MethodAnnotationStruct item : methodAnnotations) {
                item.writeTo(file, out);
            }
        }

        if (parametersSize != 0) {
            Collections.sort(parameterAnnotations);
            if (annotates) {
                out.annotate(0, "  parameters:");
            }
            for (mod.agus.jcoderz.dx.dex.file.ParameterAnnotationStruct item : parameterAnnotations) {
                item.writeTo(file, out);
            }
        }
    }

    private static int listSize(ArrayList<?> list) {
        if (list == null) {
            return 0;
        }

        return list.size();
    }

     void debugPrint(PrintWriter out) {
        if (classAnnotations != null) {
            out.println("  class annotations: " + classAnnotations);
        }

        if (fieldAnnotations != null) {
            out.println("  field annotations:");
            for (FieldAnnotationStruct item : fieldAnnotations) {
                out.println("    " + item.toHuman());
            }
        }

        if (methodAnnotations != null) {
            out.println("  method annotations:");
            for (MethodAnnotationStruct item : methodAnnotations) {
                out.println("    " + item.toHuman());
            }
        }

        if (parameterAnnotations != null) {
            out.println("  parameter annotations:");
            for (ParameterAnnotationStruct item : parameterAnnotations) {
                out.println("    " + item.toHuman());
            }
        }
    }
}
