
package com.nexusteam.blacklogics.utils;

import android.os.Environment;

import java.io.File;

public class FilePathUtil {

    private static final File BLACKLOGICS_DATA = new File("/storage/emulated/0/", ".blacklogics/data/");
    private static final File BLACKLOGICS_LIBS = new File("/storage/emulated/0/", ".blacklogics/libs/local_libs");

    public static String getLastCompileLogPath(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/compile_log").getAbsolutePath();
    }

    public String getPathPermission(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/permission").getAbsolutePath();
    }

    public String getPathImport(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/import").getAbsolutePath();
    }

    public String getPathBroadcast(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/files/broadcast").getAbsolutePath();
    }

    public String getPathService(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/files/service").getAbsolutePath();
    }

    public String getPathAssets(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/files/assets").getAbsolutePath();
    }

    public String getPathJava(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/files/java").getAbsolutePath();
    }

    public String getPathResource(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/files/resource").getAbsolutePath();
    }

    public String getPathLocalLibrary(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/local_library").getAbsolutePath();
    }

    public String getJarPathLocalLibrary(String libraryName) {
        return new File(BLACKLOGICS_LIBS, libraryName + "/classes.jar").getAbsolutePath();
    }

    public String getDexPathLocalLibrary(String libraryName) {
        return new File(BLACKLOGICS_LIBS, libraryName + "/classes.dex").getAbsolutePath();
    }

    public String getResPathLocalLibrary(String libraryName) {
        return new File(BLACKLOGICS_LIBS, libraryName + "/res").getAbsolutePath();
    }

    public String getJarPathLocalLibraryUser(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/files/library/jar").getAbsolutePath();
    }

    public String getDexPathLocalLibraryUser(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/files/library/dex").getAbsolutePath();
    }

    public String getResPathLocalLibraryUser(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/files/library/res").getAbsolutePath();
    }

    public String getManifestJava(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/java").getAbsolutePath();
    }

    public String getManifestBroadcast(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/broadcast").getAbsolutePath();
    }

    public String getPathNativelibs(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/files/native_libs").getAbsolutePath();
    }

    public String getManifestService(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id + "/service").getAbsolutePath();
    }
    
    public String getProjectDataPath(String sc_id) {
        return new File(BLACKLOGICS_DATA, sc_id).getAbsolutePath();
    }
}