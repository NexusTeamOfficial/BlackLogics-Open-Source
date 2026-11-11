package com.nexusteam.blacklogics.logic.editor;

import android.content.Context;
import java.util.HashMap;

public class ScDefine {
    public static final String[] CIDS = new String[]{"101", "109", "111", "110", "112", "105", "113", "102", "104", "114", "103", "106", "107", "108", "600"};
    public static final String CID_BASEBALL = "103";
    public static final String CID_CALC = "104";
    public static final String CID_CUSTOM_EDIT = "600";
    public static final String CID_DICE = "113";
    public static final String CID_FAMILY_CALL = "110";
    public static final String CID_GET_TIME = "109";
    public static final String CID_HYBRIDWEB = "106";
    public static final String CID_LOTTO = "114";
    public static final String CID_MEMO = "105";
    public static final String CID_PAIRPIC = "108";
    public static final String CID_RPS = "102";
    public static final String CID_SPEEDCLICK = "107";
    public static final String CID_TOAST = "101";
    public static final String CID_WEB_BROWSER = "111";
    public static final String CID_WHOAMI = "112";
    public static final boolean FLAG_NOT_STUDYING = false;
    public static final boolean FLAG_STUDYING = true;
    public static final int PROJECT_TYPE_DOWNLOAD = 2;
    public static final int PROJECT_TYPE_GENERAL = 0;
    public static final int PROJECT_TYPE_HINT = 1;
    public static boolean isCustomEditMode(String var0) {
        return Integer.valueOf(var0).intValue() >= Integer.valueOf("600").intValue();
    }

}

