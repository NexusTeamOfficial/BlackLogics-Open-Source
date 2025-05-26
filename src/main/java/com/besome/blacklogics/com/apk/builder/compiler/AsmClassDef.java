package com.tyron.compiler;

import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.reference.TypeReference;
import org.jf.dexlib2.immutable.ImmutableField;
import org.jf.dexlib2.immutable.ImmutableMethod;
import org.jf.dexlib2.immutable.ImmutableMethodParameter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AsmClassDef implements ClassDef, CharSequence, TypeReference {

    private final String type;
    private final String superclass;
    private final List<String> interfaces;
    private final String sourceFile;
    private final int accessFlags;
    private final List<? extends Field> fields;
    private final List<? extends Method> methods;

    public static ClassDef fromClassReader(ClassReader reader) {
        AsmClassDefBuilder builder = new AsmClassDefBuilder();
        reader.accept(builder, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        return builder.build();
    }

    private AsmClassDef(String type, String superclass, List<String> interfaces,
                       String sourceFile, int accessFlags, List<? extends Field> fields,
                       List<? extends Method> methods) {
        this.type = type;
        this.superclass = superclass;
        this.interfaces = interfaces;
        this.sourceFile = sourceFile;
        this.accessFlags = accessFlags;
        this.fields = fields;
        this.methods = methods;
    }

    @Override public String getType() { return type; }
    @Override public String getSuperclass() { return superclass; }
    @Override public List<String> getInterfaces() { return interfaces; }
    @Override public String getSourceFile() { return sourceFile; }
    @Override public int getAccessFlags() { return accessFlags; }
    @Override public Set<? extends Annotation> getAnnotations() { return Collections.emptySet(); }
    @Override public Iterable<? extends Field> getStaticFields() { return Collections.emptyList(); }
    @Override public Iterable<? extends Field> getInstanceFields() { return fields; }
    @Override public Iterable<? extends Method> getDirectMethods() { return Collections.emptyList(); }
    @Override public Iterable<? extends Method> getVirtualMethods() { return methods; }
    @Override public Iterable<? extends Field> getFields() { return fields; }
    @Override public Iterable<? extends Method> getMethods() { return methods; }
    
    @Override public int length() { return type.length(); }
    @Override public char charAt(int index) { return type.charAt(index); }
    @Override public CharSequence subSequence(int start, int end) { return type.subSequence(start, end); }
    @Override public int compareTo(CharSequence o) { return type.compareTo(o.toString()); }
    
    public void validateReference() {
        if (type == null || type.isEmpty()) {
            throw new RuntimeException("Invalid class type");
        }
    }

    private static class AsmClassDefBuilder extends ClassVisitor {
        private String className;
        private String superName;
        private final List<String> interfaces = new ArrayList<>();
        private String sourceFile;
        private int accessFlags;
        private final List<Field> fields = new ArrayList<>();
        private final List<Method> methods = new ArrayList<>();

        private static final int ASM_API_VERSION = 458752; // Equivalent to Opcodes.ASM7

        public AsmClassDefBuilder() {
            super(ASM_API_VERSION);
        }

        @Override
        public void visit(int version, int access, String name, 
                         String signature, String superName, String[] interfaces) {
            this.className = "L" + name.replace('.', '/') + ";";
            this.superName = superName != null ? "L" + superName.replace('.', '/') + ";" : null;
            if (interfaces != null) {
                for (String iface : interfaces) {
                    this.interfaces.add("L" + iface.replace('.', '/') + ";");
                }
            }
            this.accessFlags = access;
        }

        @Override
        public void visitSource(String source, String debug) {
            this.sourceFile = source;
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc, 
                                     String signature, Object value) {
            String fieldType = Type.getType(desc).getDescriptor();
            ImmutableField field = new ImmutableField(
                className, name, fieldType, access, null, Collections.emptySet()
            );
            fields.add(field);
            return null;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, 
                                        String signature, String[] exceptions) {
            List<ImmutableMethodParameter> parameters = new ArrayList<>();
            Type[] argTypes = Type.getArgumentTypes(desc);
            for (Type argType : argTypes) {
                parameters.add(new ImmutableMethodParameter(argType.getDescriptor(), Collections.emptySet(), null));
            }
            
            String returnType = Type.getReturnType(desc).getDescriptor();
            ImmutableMethod method = new ImmutableMethod(
                className, name, parameters, returnType, access, 
                Collections.emptySet(), null
            );
            methods.add(method);
            return null;
        }

        public ClassDef build() {
            return new AsmClassDef(
                className, 
                superName, 
                interfaces, 
                sourceFile, 
                accessFlags, 
                fields, 
                methods
            );
        }
    }
}