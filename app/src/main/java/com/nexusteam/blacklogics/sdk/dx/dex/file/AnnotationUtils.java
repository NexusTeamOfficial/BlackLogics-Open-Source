
package mod.agus.jcoderz.dx.dex.file;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.annotation.Annotation;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationVisibility;
import mod.agus.jcoderz.dx.rop.annotation.NameValuePair;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstAnnotation;
import mod.agus.jcoderz.dx.rop.cst.CstArray;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstKnownNull;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;

public final class AnnotationUtils {

    private static final mod.agus.jcoderz.dx.rop.cst.CstType ANNOTATION_DEFAULT_TYPE =
        mod.agus.jcoderz.dx.rop.cst.CstType.intern(mod.agus.jcoderz.dx.rop.type.Type.intern("Ldalvik/annotation/AnnotationDefault;"));

    private static final mod.agus.jcoderz.dx.rop.cst.CstType ENCLOSING_CLASS_TYPE =
        mod.agus.jcoderz.dx.rop.cst.CstType.intern(mod.agus.jcoderz.dx.rop.type.Type.intern("Ldalvik/annotation/EnclosingClass;"));

    private static final mod.agus.jcoderz.dx.rop.cst.CstType ENCLOSING_METHOD_TYPE =
        mod.agus.jcoderz.dx.rop.cst.CstType.intern(mod.agus.jcoderz.dx.rop.type.Type.intern("Ldalvik/annotation/EnclosingMethod;"));

    private static final mod.agus.jcoderz.dx.rop.cst.CstType INNER_CLASS_TYPE =
        mod.agus.jcoderz.dx.rop.cst.CstType.intern(mod.agus.jcoderz.dx.rop.type.Type.intern("Ldalvik/annotation/InnerClass;"));

    private static final mod.agus.jcoderz.dx.rop.cst.CstType MEMBER_CLASSES_TYPE =
        mod.agus.jcoderz.dx.rop.cst.CstType.intern(mod.agus.jcoderz.dx.rop.type.Type.intern("Ldalvik/annotation/MemberClasses;"));

    private static final mod.agus.jcoderz.dx.rop.cst.CstType SIGNATURE_TYPE =
        mod.agus.jcoderz.dx.rop.cst.CstType.intern(mod.agus.jcoderz.dx.rop.type.Type.intern("Ldalvik/annotation/Signature;"));

    private static final mod.agus.jcoderz.dx.rop.cst.CstType SOURCE_DEBUG_EXTENSION_TYPE =
        mod.agus.jcoderz.dx.rop.cst.CstType.intern(mod.agus.jcoderz.dx.rop.type.Type.intern("Ldalvik/annotation/SourceDebugExtension;"));

    private static final mod.agus.jcoderz.dx.rop.cst.CstType THROWS_TYPE =
        mod.agus.jcoderz.dx.rop.cst.CstType.intern(Type.intern("Ldalvik/annotation/Throws;"));

    private static final mod.agus.jcoderz.dx.rop.cst.CstString ACCESS_FLAGS_STRING = new mod.agus.jcoderz.dx.rop.cst.CstString("accessFlags");

    private static final mod.agus.jcoderz.dx.rop.cst.CstString NAME_STRING = new mod.agus.jcoderz.dx.rop.cst.CstString("name");

    private static final mod.agus.jcoderz.dx.rop.cst.CstString VALUE_STRING = new mod.agus.jcoderz.dx.rop.cst.CstString("value");

    private AnnotationUtils() {

    }

    public static mod.agus.jcoderz.dx.rop.annotation.Annotation makeAnnotationDefault(mod.agus.jcoderz.dx.rop.annotation.Annotation defaults) {
        mod.agus.jcoderz.dx.rop.annotation.Annotation result = new mod.agus.jcoderz.dx.rop.annotation.Annotation(ANNOTATION_DEFAULT_TYPE, AnnotationVisibility.SYSTEM);

        result.put(new mod.agus.jcoderz.dx.rop.annotation.NameValuePair(VALUE_STRING, new CstAnnotation(defaults)));
        result.setImmutable();
        return result;
    }

    public static mod.agus.jcoderz.dx.rop.annotation.Annotation makeEnclosingClass(mod.agus.jcoderz.dx.rop.cst.CstType clazz) {
        mod.agus.jcoderz.dx.rop.annotation.Annotation result = new mod.agus.jcoderz.dx.rop.annotation.Annotation(ENCLOSING_CLASS_TYPE, AnnotationVisibility.SYSTEM);

        result.put(new mod.agus.jcoderz.dx.rop.annotation.NameValuePair(VALUE_STRING, clazz));
        result.setImmutable();
        return result;
    }

    public static mod.agus.jcoderz.dx.rop.annotation.Annotation makeEnclosingMethod(CstMethodRef method) {
        mod.agus.jcoderz.dx.rop.annotation.Annotation result = new mod.agus.jcoderz.dx.rop.annotation.Annotation(ENCLOSING_METHOD_TYPE, AnnotationVisibility.SYSTEM);

        result.put(new mod.agus.jcoderz.dx.rop.annotation.NameValuePair(VALUE_STRING, method));
        result.setImmutable();
        return result;
    }

    public static mod.agus.jcoderz.dx.rop.annotation.Annotation makeInnerClass(mod.agus.jcoderz.dx.rop.cst.CstString name, int accessFlags) {
        mod.agus.jcoderz.dx.rop.annotation.Annotation result = new mod.agus.jcoderz.dx.rop.annotation.Annotation(INNER_CLASS_TYPE, AnnotationVisibility.SYSTEM);
        Constant nameCst = (name != null) ? name : CstKnownNull.THE_ONE;

        result.put(new mod.agus.jcoderz.dx.rop.annotation.NameValuePair(NAME_STRING, nameCst));
        result.put(new mod.agus.jcoderz.dx.rop.annotation.NameValuePair(ACCESS_FLAGS_STRING,
                        CstInteger.make(accessFlags)));
        result.setImmutable();
        return result;
    }

    public static mod.agus.jcoderz.dx.rop.annotation.Annotation makeMemberClasses(mod.agus.jcoderz.dx.rop.type.TypeList types) {
        mod.agus.jcoderz.dx.rop.cst.CstArray array = makeCstArray(types);
        mod.agus.jcoderz.dx.rop.annotation.Annotation result = new mod.agus.jcoderz.dx.rop.annotation.Annotation(MEMBER_CLASSES_TYPE, AnnotationVisibility.SYSTEM);
        result.put(new mod.agus.jcoderz.dx.rop.annotation.NameValuePair(VALUE_STRING, array));
        result.setImmutable();
        return result;
    }

    public static mod.agus.jcoderz.dx.rop.annotation.Annotation makeSignature(mod.agus.jcoderz.dx.rop.cst.CstString signature) {
        mod.agus.jcoderz.dx.rop.annotation.Annotation result = new mod.agus.jcoderz.dx.rop.annotation.Annotation(SIGNATURE_TYPE, AnnotationVisibility.SYSTEM);


        String raw = signature.getString();
        int rawLength = raw.length();
        ArrayList<String> pieces = new ArrayList<String>(20);

        for (int at = 0; at < rawLength; ) {
            char c = raw.charAt(at);
            int endAt = at + 1;
            if (c == 'L') {

                while (endAt < rawLength) {
                    c = raw.charAt(endAt);
                    if (c == ';') {
                        endAt++;
                        break;
                    } else if (c == '<') {
                        break;
                    }
                    endAt++;
                }
            } else {

                while (endAt < rawLength) {
                    c = raw.charAt(endAt);
                    if (c == 'L') {
                        break;
                    }
                    endAt++;
                }
            }

            pieces.add(raw.substring(at, endAt));
            at = endAt;
        }

        int size = pieces.size();
        mod.agus.jcoderz.dx.rop.cst.CstArray.List list = new mod.agus.jcoderz.dx.rop.cst.CstArray.List(size);

        for (int i = 0; i < size; i++) {
            list.set(i, new mod.agus.jcoderz.dx.rop.cst.CstString(pieces.get(i)));
        }

        list.setImmutable();

        result.put(new mod.agus.jcoderz.dx.rop.annotation.NameValuePair(VALUE_STRING, new mod.agus.jcoderz.dx.rop.cst.CstArray(list)));
        result.setImmutable();
        return result;
    }

    public static mod.agus.jcoderz.dx.rop.annotation.Annotation makeSourceDebugExtension(CstString smapString) {
        mod.agus.jcoderz.dx.rop.annotation.Annotation result = new mod.agus.jcoderz.dx.rop.annotation.Annotation(SOURCE_DEBUG_EXTENSION_TYPE, AnnotationVisibility.SYSTEM);

        result.put(new mod.agus.jcoderz.dx.rop.annotation.NameValuePair(VALUE_STRING, smapString));
        result.setImmutable();
        return result;
    }

    public static mod.agus.jcoderz.dx.rop.annotation.Annotation makeThrows(mod.agus.jcoderz.dx.rop.type.TypeList types) {
        mod.agus.jcoderz.dx.rop.cst.CstArray array = makeCstArray(types);
        mod.agus.jcoderz.dx.rop.annotation.Annotation result = new Annotation(THROWS_TYPE, AnnotationVisibility.SYSTEM);
        result.put(new NameValuePair(VALUE_STRING, array));
        result.setImmutable();
        return result;
    }

    private static mod.agus.jcoderz.dx.rop.cst.CstArray makeCstArray(TypeList types) {
        int size = types.size();
        mod.agus.jcoderz.dx.rop.cst.CstArray.List list = new mod.agus.jcoderz.dx.rop.cst.CstArray.List(size);

        for (int i = 0; i < size; i++) {
            list.set(i, CstType.intern(types.getType(i)));
        }

        list.setImmutable();
        return new CstArray(list);
    }
}
