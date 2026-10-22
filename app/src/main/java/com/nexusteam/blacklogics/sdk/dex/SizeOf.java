
package mod.agus.jcoderz.dex;

public final class SizeOf {
    private SizeOf() {}

    public static final int UBYTE = 1;
    public static final int USHORT = 2;
    public static final int UINT = 4;

    public static final int SIGNATURE = UBYTE * 20;

    public static final int HEADER_ITEM = (8 * UBYTE) + UINT + SIGNATURE + (20 * UINT);

    public static final int STRING_ID_ITEM = UINT;

    public static final int TYPE_ID_ITEM = UINT;

    public static final int TYPE_ITEM = USHORT;

    public static final int PROTO_ID_ITEM = UINT + UINT + UINT;

    public static final int MEMBER_ID_ITEM = USHORT + USHORT + UINT;

    public static final int CLASS_DEF_ITEM = 8 * UINT;

    public static final int MAP_ITEM = USHORT + USHORT + UINT + UINT;

    public static final int TRY_ITEM = UINT + USHORT + USHORT;

    public static final int CALL_SITE_ID_ITEM = UINT;

    public static final int METHOD_HANDLE_ITEM = USHORT + USHORT + USHORT + USHORT;
}
