
package mod.agus.jcoderz.dx.dex.file;

public interface DebugInfoConstants {


    static final int DBG_END_SEQUENCE = 0x00;

    static final int DBG_ADVANCE_PC = 0x01;

    static final int DBG_ADVANCE_LINE = 0x02;

    static final int DBG_START_LOCAL = 0x03;

    static final int DBG_START_LOCAL_EXTENDED = 0x04;

    static final int DBG_END_LOCAL = 0x05;

    static final int DBG_RESTART_LOCAL = 0x06;


    static final int DBG_SET_PROLOGUE_END = 0x07;

    static final int DBG_SET_EPILOGUE_BEGIN = 0x08;

    static final int DBG_SET_FILE = 0x09;



    static final int DBG_FIRST_SPECIAL = 0x0a;
    static final int DBG_LINE_BASE = -4;
    static final int DBG_LINE_RANGE = 15;

}
