
package mod.agus.jcoderz.dx.cf.direct;

import mod.agus.jcoderz.dx.cf.attrib.AttBootstrapMethods;
import mod.agus.jcoderz.dx.cf.attrib.AttSourceFile;
import mod.agus.jcoderz.dx.cf.code.BootstrapMethodsList;
import mod.agus.jcoderz.dx.cf.cst.ConstantPoolParser;
import mod.agus.jcoderz.dx.cf.iface.Attribute;
import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.cf.iface.ClassFile;
import mod.agus.jcoderz.dx.cf.iface.FieldList;
import mod.agus.jcoderz.dx.cf.iface.MethodList;
import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.cf.iface.StdAttributeList;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.cst.StdConstantPool;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public class DirectClassFile implements ClassFile {
    private static final int CLASS_FILE_MAGIC = 0xcafebabe;

    private static final int CLASS_FILE_MIN_MAJOR_VERSION = 45;

    private static final int CLASS_FILE_MAX_MAJOR_VERSION = 53;

    private static final int CLASS_FILE_MAX_MINOR_VERSION = 0;

    private final String filePath;

    private final mod.agus.jcoderz.dx.util.ByteArray bytes;

    private final boolean strictParse;

    private mod.agus.jcoderz.dx.rop.cst.StdConstantPool pool;

    private int accessFlags;

    private mod.agus.jcoderz.dx.rop.cst.CstType thisClass;

    private mod.agus.jcoderz.dx.rop.cst.CstType superClass;

    private mod.agus.jcoderz.dx.rop.type.TypeList interfaces;

    private mod.agus.jcoderz.dx.cf.iface.FieldList fields;

    private mod.agus.jcoderz.dx.cf.iface.MethodList methods;

    private StdAttributeList attributes;

    private mod.agus.jcoderz.dx.cf.direct.AttributeFactory attributeFactory;

    private mod.agus.jcoderz.dx.cf.iface.ParseObserver observer;

    public static String stringOrNone(Object obj) {
        if (obj == null) {
            return "(none)";
        }

        return obj.toString();
    }

    public DirectClassFile(mod.agus.jcoderz.dx.util.ByteArray bytes, String filePath,
                           boolean strictParse) {
        if (bytes == null) {
            throw new NullPointerException("bytes == null");
        }

        if (filePath == null) {
            throw new NullPointerException("filePath == null");
        }

        this.filePath = filePath;
        this.bytes = bytes;
        this.strictParse = strictParse;
        this.accessFlags = -1;
    }

    public DirectClassFile(byte[] bytes, String filePath,
                           boolean strictParse) {
        this(new mod.agus.jcoderz.dx.util.ByteArray(bytes), filePath, strictParse);
    }

    public void setObserver(mod.agus.jcoderz.dx.cf.iface.ParseObserver observer) {
        this.observer = observer;
    }

    public void setAttributeFactory(mod.agus.jcoderz.dx.cf.direct.AttributeFactory attributeFactory) {
        if (attributeFactory == null) {
            throw new NullPointerException("attributeFactory == null");
        }

        this.attributeFactory = attributeFactory;
    }

    public String getFilePath() {
      return filePath;
    }

    public mod.agus.jcoderz.dx.util.ByteArray getBytes() {
        return bytes;
    }

    @Override
    public int getMagic() {
        parseToInterfacesIfNecessary();
        return getMagic0();
    }

    @Override
    public int getMinorVersion() {
        parseToInterfacesIfNecessary();
        return getMinorVersion0();
    }

    @Override
    public int getMajorVersion() {
        parseToInterfacesIfNecessary();
        return getMajorVersion0();
    }

    @Override
    public int getAccessFlags() {
        parseToInterfacesIfNecessary();
        return accessFlags;
    }

    @Override
    public mod.agus.jcoderz.dx.rop.cst.CstType getThisClass() {
        parseToInterfacesIfNecessary();
        return thisClass;
    }

    @Override
    public mod.agus.jcoderz.dx.rop.cst.CstType getSuperclass() {
        parseToInterfacesIfNecessary();
        return superClass;
    }

    @Override
    public ConstantPool getConstantPool() {
        parseToInterfacesIfNecessary();
        return pool;
    }

    @Override
    public mod.agus.jcoderz.dx.rop.type.TypeList getInterfaces() {
        parseToInterfacesIfNecessary();
        return interfaces;
    }

    @Override
    public FieldList getFields() {
        parseToEndIfNecessary();
        return fields;
    }

    @Override
    public MethodList getMethods() {
        parseToEndIfNecessary();
        return methods;
    }

    @Override
    public mod.agus.jcoderz.dx.cf.iface.AttributeList getAttributes() {
        parseToEndIfNecessary();
        return attributes;
    }

    @Override
    public mod.agus.jcoderz.dx.cf.code.BootstrapMethodsList getBootstrapMethods() {
        mod.agus.jcoderz.dx.cf.attrib.AttBootstrapMethods bootstrapMethodsAttribute =
                (mod.agus.jcoderz.dx.cf.attrib.AttBootstrapMethods) getAttributes().findFirst(AttBootstrapMethods.ATTRIBUTE_NAME);
        if (bootstrapMethodsAttribute != null) {
            return bootstrapMethodsAttribute.getBootstrapMethods();
        } else {
            return BootstrapMethodsList.EMPTY;
        }
    }

    @Override
    public CstString getSourceFile() {
        AttributeList attribs = getAttributes();
        Attribute attSf = attribs.findFirst(mod.agus.jcoderz.dx.cf.attrib.AttSourceFile.ATTRIBUTE_NAME);

        if (attSf instanceof mod.agus.jcoderz.dx.cf.attrib.AttSourceFile) {
            return ((AttSourceFile) attSf).getSourceFile();
        }

        return null;
    }

    public mod.agus.jcoderz.dx.rop.type.TypeList makeTypeList(int offset, int size) {
        if (size == 0) {
            return StdTypeList.EMPTY;
        }

        if (pool == null) {
            throw new IllegalStateException("pool not yet initialized");
        }

        return new DcfTypeList(bytes, offset, size, pool, observer);
    }

    public int getMagic0() {
        return bytes.getInt(0);
    }

    public int getMinorVersion0() {
        return bytes.getUnsignedShort(4);
    }

    public int getMajorVersion0() {
        return bytes.getUnsignedShort(6);
    }

    private void parseToInterfacesIfNecessary() {
        if (accessFlags == -1) {
            parse();
        }
    }

    private void parseToEndIfNecessary() {
        if (attributes == null) {
            parse();
        }
    }

    private void parse() {
        try {
            parse0();
        } catch (mod.agus.jcoderz.dx.cf.iface.ParseException ex) {
            ex.addContext("...while parsing " + filePath);
            throw ex;
        } catch (RuntimeException ex) {
            mod.agus.jcoderz.dx.cf.iface.ParseException pe = new mod.agus.jcoderz.dx.cf.iface.ParseException(ex);
            pe.addContext("...while parsing " + filePath);
            throw pe;
        }
    }

    private boolean isGoodMagic(int magic) {
        return magic == CLASS_FILE_MAGIC;
    }

    private boolean isGoodVersion(int minorVersion, int majorVersion) {
        if (minorVersion >= 0) {
            if (majorVersion == CLASS_FILE_MAX_MAJOR_VERSION) {
                if (minorVersion <= CLASS_FILE_MAX_MINOR_VERSION) {
                    return true;
                }
            } else if (majorVersion < CLASS_FILE_MAX_MAJOR_VERSION &&
                       majorVersion >= CLASS_FILE_MIN_MAJOR_VERSION) {
                return true;
            }
        }

        return false;
    }

    private void parse0() {
        if (bytes.size() < 10) {
            throw new mod.agus.jcoderz.dx.cf.iface.ParseException("severely truncated class file");
        }

        if (observer != null) {
            observer.parsed(bytes, 0, 0, "begin classfile");
            observer.parsed(bytes, 0, 4, "magic: " + mod.agus.jcoderz.dx.util.Hex.u4(getMagic0()));
            observer.parsed(bytes, 4, 2,
                            "minor_version: " + mod.agus.jcoderz.dx.util.Hex.u2(getMinorVersion0()));
            observer.parsed(bytes, 6, 2,
                            "major_version: " + mod.agus.jcoderz.dx.util.Hex.u2(getMajorVersion0()));
        }

        if (strictParse) {
            if (!isGoodMagic(getMagic0())) {
                throw new mod.agus.jcoderz.dx.cf.iface.ParseException("bad class file magic (" + mod.agus.jcoderz.dx.util.Hex.u4(getMagic0()) + ")");
            }

            if (!isGoodVersion(getMinorVersion0(), getMajorVersion0())) {
                throw new mod.agus.jcoderz.dx.cf.iface.ParseException("unsupported class file version " +
                                         getMajorVersion0() + "." +
                                         getMinorVersion0());
            }
        }

        mod.agus.jcoderz.dx.cf.cst.ConstantPoolParser cpParser = new ConstantPoolParser(bytes);
        cpParser.setObserver(observer);
        pool = cpParser.getPool();
        pool.setImmutable();

        int at = cpParser.getEndOffset();
        int accessFlags = bytes.getUnsignedShort(at);
        int cpi = bytes.getUnsignedShort(at + 2);
        thisClass = (mod.agus.jcoderz.dx.rop.cst.CstType) pool.get(cpi);
        cpi = bytes.getUnsignedShort(at + 4);
        superClass = (mod.agus.jcoderz.dx.rop.cst.CstType) pool.get0Ok(cpi);
        int count = bytes.getUnsignedShort(at + 6);

        if (observer != null) {
            observer.parsed(bytes, at, 2,
                            "access_flags: " +
                            AccessFlags.classString(accessFlags));
            observer.parsed(bytes, at + 2, 2, "this_class: " + thisClass);
            observer.parsed(bytes, at + 4, 2, "super_class: " +
                            stringOrNone(superClass));
            observer.parsed(bytes, at + 6, 2,
                            "interfaces_count: " + mod.agus.jcoderz.dx.util.Hex.u2(count));
            if (count != 0) {
                observer.parsed(bytes, at + 8, 0, "interfaces:");
            }
        }

        at += 8;
        interfaces = makeTypeList(at, count);
        at += count * 2;

        if (strictParse) {
            String thisClassName = thisClass.getClassType().getClassName();
            if (!(filePath.endsWith(".class") &&
                  filePath.startsWith(thisClassName) &&
                  (filePath.length() == (thisClassName.length() + 6)))) {
                throw new mod.agus.jcoderz.dx.cf.iface.ParseException("class name (" + thisClassName +
                                         ") does not match path (" +
                                         filePath + ")");
            }
        }

        this.accessFlags = accessFlags;

        mod.agus.jcoderz.dx.cf.direct.FieldListParser flParser =
            new mod.agus.jcoderz.dx.cf.direct.FieldListParser(this, thisClass, at, attributeFactory);
        flParser.setObserver(observer);
        fields = flParser.getList();
        at = flParser.getEndOffset();

        mod.agus.jcoderz.dx.cf.direct.MethodListParser mlParser =
            new mod.agus.jcoderz.dx.cf.direct.MethodListParser(this, thisClass, at, attributeFactory);
        mlParser.setObserver(observer);
        methods = mlParser.getList();
        at = mlParser.getEndOffset();

        mod.agus.jcoderz.dx.cf.direct.AttributeListParser alParser =
            new mod.agus.jcoderz.dx.cf.direct.AttributeListParser(this, AttributeFactory.CTX_CLASS, at,
                                    attributeFactory);
        alParser.setObserver(observer);
        attributes = alParser.getList();
        attributes.setImmutable();
        at = alParser.getEndOffset();

        if (at != bytes.size()) {
            throw new ParseException("extra bytes at end of class file, " +
                                     "at offset " + Hex.u4(at));
        }

        if (observer != null) {
            observer.parsed(bytes, at, 0, "end classfile");
        }
    }

    private static class DcfTypeList implements mod.agus.jcoderz.dx.rop.type.TypeList {
        private final mod.agus.jcoderz.dx.util.ByteArray bytes;

        private final int size;

        private final mod.agus.jcoderz.dx.rop.cst.StdConstantPool pool;

        public DcfTypeList(ByteArray bytes, int offset, int size,
                           StdConstantPool pool, ParseObserver observer) {
            if (size < 0) {
                throw new IllegalArgumentException("size < 0");
            }

            bytes = bytes.slice(offset, offset + size * 2);
            this.bytes = bytes;
            this.size = size;
            this.pool = pool;

            for (int i = 0; i < size; i++) {
                offset = i * 2;
                int idx = bytes.getUnsignedShort(offset);
                mod.agus.jcoderz.dx.rop.cst.CstType type;
                try {
                    type = (mod.agus.jcoderz.dx.rop.cst.CstType) pool.get(idx);
                } catch (ClassCastException ex) {

                    throw new RuntimeException("bogus class cpi", ex);
                }
                if (observer != null) {
                    observer.parsed(bytes, offset, 2, "  " + type);
                }
            }
        }

        @Override
        public boolean isMutable() {
            return false;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public int getWordCount() {

            return size;
        }

        @Override
        public mod.agus.jcoderz.dx.rop.type.Type getType(int n) {
            int idx = bytes.getUnsignedShort(n * 2);
            return ((CstType) pool.get(idx)).getClassType();
        }

        @Override
        public TypeList withAddedType(Type type) {
            throw new UnsupportedOperationException("unsupported");
        }
    }
}
