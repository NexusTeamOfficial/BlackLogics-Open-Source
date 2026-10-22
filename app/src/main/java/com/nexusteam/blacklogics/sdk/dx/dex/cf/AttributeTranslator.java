
package mod.agus.jcoderz.dx.dex.cf;

import mod.agus.jcoderz.dx.dex.file.AnnotationUtils;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.cf.attrib.AttAnnotationDefault;
import mod.agus.jcoderz.dx.cf.attrib.AttEnclosingMethod;
import mod.agus.jcoderz.dx.cf.attrib.AttExceptions;
import mod.agus.jcoderz.dx.cf.attrib.AttInnerClasses;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeInvisibleAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeInvisibleParameterAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeVisibleAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeVisibleParameterAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttSignature;
import mod.agus.jcoderz.dx.cf.attrib.AttSourceDebugExtension;
import mod.agus.jcoderz.dx.cf.attrib.InnerClassList;
import mod.agus.jcoderz.dx.cf.direct.DirectClassFile;
import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.cf.iface.Method;
import mod.agus.jcoderz.dx.cf.iface.MethodList;
import mod.agus.jcoderz.dx.rop.annotation.Annotation;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationVisibility;
import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationsList;
import mod.agus.jcoderz.dx.rop.annotation.NameValuePair;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.Warning;

 class AttributeTranslator {
    private AttributeTranslator() {

    }

    public static mod.agus.jcoderz.dx.rop.type.TypeList getExceptions(mod.agus.jcoderz.dx.cf.iface.Method method) {
        mod.agus.jcoderz.dx.cf.iface.AttributeList attribs = method.getAttributes();
        mod.agus.jcoderz.dx.cf.attrib.AttExceptions exceptions = (mod.agus.jcoderz.dx.cf.attrib.AttExceptions)
            attribs.findFirst(AttExceptions.ATTRIBUTE_NAME);

        if (exceptions == null) {
            return mod.agus.jcoderz.dx.rop.type.StdTypeList.EMPTY;
        }

        return exceptions.getExceptions();
    }

    public static mod.agus.jcoderz.dx.rop.annotation.Annotations getAnnotations(mod.agus.jcoderz.dx.cf.iface.AttributeList attribs) {
        mod.agus.jcoderz.dx.rop.annotation.Annotations result = getAnnotations0(attribs);
        mod.agus.jcoderz.dx.rop.annotation.Annotation signature = getSignature(attribs);
        mod.agus.jcoderz.dx.rop.annotation.Annotation sourceDebugExtension = getSourceDebugExtension(attribs);

        if (signature != null) {
            result = mod.agus.jcoderz.dx.rop.annotation.Annotations.combine(result, signature);
        }

        if (sourceDebugExtension != null) {
            result = mod.agus.jcoderz.dx.rop.annotation.Annotations.combine(result, sourceDebugExtension);
        }

        return result;
    }

    public static mod.agus.jcoderz.dx.rop.annotation.Annotations getClassAnnotations(mod.agus.jcoderz.dx.cf.direct.DirectClassFile cf,
                                                                                     CfOptions args) {
        mod.agus.jcoderz.dx.rop.cst.CstType thisClass = cf.getThisClass();
        mod.agus.jcoderz.dx.cf.iface.AttributeList attribs = cf.getAttributes();
        mod.agus.jcoderz.dx.rop.annotation.Annotations result = getAnnotations(attribs);
        mod.agus.jcoderz.dx.rop.annotation.Annotation enclosingMethod = translateEnclosingMethod(attribs);

        try {
            mod.agus.jcoderz.dx.rop.annotation.Annotations innerClassAnnotations =
                translateInnerClasses(thisClass, attribs,
                        enclosingMethod == null);
            if (innerClassAnnotations != null) {
                result = mod.agus.jcoderz.dx.rop.annotation.Annotations.combine(result, innerClassAnnotations);
            }
        } catch (mod.agus.jcoderz.dx.util.Warning warn) {
            args.warn.println("warning: " + warn.getMessage());
        }

        if (enclosingMethod != null) {
            result = mod.agus.jcoderz.dx.rop.annotation.Annotations.combine(result, enclosingMethod);
        }

        if (AccessFlags.isAnnotation(cf.getAccessFlags())) {
            mod.agus.jcoderz.dx.rop.annotation.Annotation annotationDefault =
                translateAnnotationDefaults(cf);
            if (annotationDefault != null) {
                result = mod.agus.jcoderz.dx.rop.annotation.Annotations.combine(result, annotationDefault);
            }
        }

        return result;
    }

    public static mod.agus.jcoderz.dx.rop.annotation.Annotations getMethodAnnotations(mod.agus.jcoderz.dx.cf.iface.Method method) {
        mod.agus.jcoderz.dx.rop.annotation.Annotations result = getAnnotations(method.getAttributes());
        TypeList exceptions = getExceptions(method);

        if (exceptions.size() != 0) {
            mod.agus.jcoderz.dx.rop.annotation.Annotation throwsAnnotation =
                AnnotationUtils.makeThrows(exceptions);
            result = mod.agus.jcoderz.dx.rop.annotation.Annotations.combine(result, throwsAnnotation);
        }

        return result;
    }

    private static mod.agus.jcoderz.dx.rop.annotation.Annotations getAnnotations0(mod.agus.jcoderz.dx.cf.iface.AttributeList attribs) {
        mod.agus.jcoderz.dx.cf.attrib.AttRuntimeVisibleAnnotations visible =
            (mod.agus.jcoderz.dx.cf.attrib.AttRuntimeVisibleAnnotations)
            attribs.findFirst(AttRuntimeVisibleAnnotations.ATTRIBUTE_NAME);
        mod.agus.jcoderz.dx.cf.attrib.AttRuntimeInvisibleAnnotations invisible =
            (mod.agus.jcoderz.dx.cf.attrib.AttRuntimeInvisibleAnnotations)
            attribs.findFirst(AttRuntimeInvisibleAnnotations.ATTRIBUTE_NAME);

        if (visible == null) {
            if (invisible == null) {
                return mod.agus.jcoderz.dx.rop.annotation.Annotations.EMPTY;
            }
            return invisible.getAnnotations();
        }

        if (invisible == null) {
            return visible.getAnnotations();
        }



        return mod.agus.jcoderz.dx.rop.annotation.Annotations.combine(visible.getAnnotations(),
                invisible.getAnnotations());
    }

    private static mod.agus.jcoderz.dx.rop.annotation.Annotation getSignature(mod.agus.jcoderz.dx.cf.iface.AttributeList attribs) {
        mod.agus.jcoderz.dx.cf.attrib.AttSignature signature = (mod.agus.jcoderz.dx.cf.attrib.AttSignature)
            attribs.findFirst(AttSignature.ATTRIBUTE_NAME);

        if (signature == null) {
            return null;
        }

        return AnnotationUtils.makeSignature(signature.getSignature());
    }


    private static mod.agus.jcoderz.dx.rop.annotation.Annotation getSourceDebugExtension(mod.agus.jcoderz.dx.cf.iface.AttributeList attribs) {
        mod.agus.jcoderz.dx.cf.attrib.AttSourceDebugExtension extension = (mod.agus.jcoderz.dx.cf.attrib.AttSourceDebugExtension)
            attribs.findFirst(AttSourceDebugExtension.ATTRIBUTE_NAME);

        if (extension == null) {
            return null;
        }

        return AnnotationUtils.makeSourceDebugExtension(extension.getSmapString());
    }

    private static mod.agus.jcoderz.dx.rop.annotation.Annotation translateEnclosingMethod(mod.agus.jcoderz.dx.cf.iface.AttributeList attribs) {
        mod.agus.jcoderz.dx.cf.attrib.AttEnclosingMethod enclosingMethod = (mod.agus.jcoderz.dx.cf.attrib.AttEnclosingMethod)
            attribs.findFirst(AttEnclosingMethod.ATTRIBUTE_NAME);

        if (enclosingMethod == null) {
            return null;
        }

        mod.agus.jcoderz.dx.rop.cst.CstType enclosingClass = enclosingMethod.getEnclosingClass();
        CstNat nat = enclosingMethod.getMethod();

        if (nat == null) {
            return AnnotationUtils.makeEnclosingClass(enclosingClass);
        }

        return AnnotationUtils.makeEnclosingMethod(
                new CstMethodRef(enclosingClass, nat));
    }

    private static mod.agus.jcoderz.dx.rop.annotation.Annotations translateInnerClasses(mod.agus.jcoderz.dx.rop.cst.CstType thisClass,
                                                                                        mod.agus.jcoderz.dx.cf.iface.AttributeList attribs, boolean needEnclosingClass) {
        mod.agus.jcoderz.dx.cf.attrib.AttInnerClasses innerClasses = (mod.agus.jcoderz.dx.cf.attrib.AttInnerClasses)
            attribs.findFirst(AttInnerClasses.ATTRIBUTE_NAME);

        if (innerClasses == null) {
            return null;
        }


        mod.agus.jcoderz.dx.cf.attrib.InnerClassList list = innerClasses.getInnerClasses();
        int size = list.size();
        mod.agus.jcoderz.dx.cf.attrib.InnerClassList.Item foundThisClass = null;
        ArrayList<mod.agus.jcoderz.dx.rop.type.Type> membersList = new ArrayList<Type>();

        for (int i = 0; i < size; i++) {
            InnerClassList.Item item = list.get(i);
            mod.agus.jcoderz.dx.rop.cst.CstType innerClass = item.getInnerClass();
            if (innerClass.equals(thisClass)) {
                foundThisClass = item;
            } else if (thisClass.equals(item.getOuterClass())) {
                membersList.add(innerClass.getClassType());
            }
        }

        int membersSize = membersList.size();

        if ((foundThisClass == null) && (membersSize == 0)) {
            return null;
        }

        mod.agus.jcoderz.dx.rop.annotation.Annotations result = new Annotations();

        if (foundThisClass != null) {
            result.add(AnnotationUtils.makeInnerClass(
                               foundThisClass.getInnerName(),
                               foundThisClass.getAccessFlags()));
            if (needEnclosingClass) {
                mod.agus.jcoderz.dx.rop.cst.CstType outer = foundThisClass.getOuterClass();
                if (outer == null) {
                    throw new Warning(
                            "Ignoring InnerClasses attribute for an " +
                            "anonymous inner class\n" +
                            "(" + thisClass.toHuman() +
                            ") that doesn't come with an\n" +
                            "associated EnclosingMethod attribute. " +
                            "This class was probably produced by a\n" +
                            "compiler that did not target the modern " +
                            ".class file format. The recommended\n" +
                            "solution is to recompile the class from " +
                            "source, using an up-to-date compiler\n" +
                            "and without specifying any \"-target\" type " +
                            "options. The consequence of ignoring\n" +
                            "this warning is that reflective operations " +
                            "on this class will incorrectly\n" +
                            "indicate that it is *not* an inner class.");
                }
                result.add(AnnotationUtils.makeEnclosingClass(
                                   foundThisClass.getOuterClass()));
            }
        }

        if (membersSize != 0) {
            mod.agus.jcoderz.dx.rop.type.StdTypeList typeList = new StdTypeList(membersSize);
            for (int i = 0; i < membersSize; i++) {
                typeList.set(i, membersList.get(i));
            }
            typeList.setImmutable();
            result.add(AnnotationUtils.makeMemberClasses(typeList));
        }

        result.setImmutable();
        return result;
    }

    public static mod.agus.jcoderz.dx.rop.annotation.AnnotationsList getParameterAnnotations(mod.agus.jcoderz.dx.cf.iface.Method method) {
        mod.agus.jcoderz.dx.cf.iface.AttributeList attribs = method.getAttributes();
        mod.agus.jcoderz.dx.cf.attrib.AttRuntimeVisibleParameterAnnotations visible =
            (mod.agus.jcoderz.dx.cf.attrib.AttRuntimeVisibleParameterAnnotations)
            attribs.findFirst(
                    AttRuntimeVisibleParameterAnnotations.ATTRIBUTE_NAME);
        mod.agus.jcoderz.dx.cf.attrib.AttRuntimeInvisibleParameterAnnotations invisible =
            (mod.agus.jcoderz.dx.cf.attrib.AttRuntimeInvisibleParameterAnnotations)
            attribs.findFirst(
                    AttRuntimeInvisibleParameterAnnotations.ATTRIBUTE_NAME);

        if (visible == null) {
            if (invisible == null) {
                return mod.agus.jcoderz.dx.rop.annotation.AnnotationsList.EMPTY;
            }
            return invisible.getParameterAnnotations();
        }

        if (invisible == null) {
            return visible.getParameterAnnotations();
        }



        return AnnotationsList.combine(visible.getParameterAnnotations(),
                invisible.getParameterAnnotations());
    }

    private static mod.agus.jcoderz.dx.rop.annotation.Annotation translateAnnotationDefaults(DirectClassFile cf) {
        CstType thisClass = cf.getThisClass();
        MethodList methods = cf.getMethods();
        int sz = methods.size();
        mod.agus.jcoderz.dx.rop.annotation.Annotation result =
            new Annotation(thisClass, AnnotationVisibility.EMBEDDED);
        boolean any = false;

        for (int i = 0; i < sz; i++) {
            Method one = methods.get(i);
            AttributeList attribs = one.getAttributes();
            mod.agus.jcoderz.dx.cf.attrib.AttAnnotationDefault oneDefault = (mod.agus.jcoderz.dx.cf.attrib.AttAnnotationDefault)
                attribs.findFirst(AttAnnotationDefault.ATTRIBUTE_NAME);

            if (oneDefault != null) {
                mod.agus.jcoderz.dx.rop.annotation.NameValuePair pair = new NameValuePair(
                        one.getNat().getName(),
                        oneDefault.getValue());
                result.add(pair);
                any = true;
            }
        }

        if (! any) {
            return null;
        }

        result.setImmutable();
        return AnnotationUtils.makeAnnotationDefault(result);
    }
}
