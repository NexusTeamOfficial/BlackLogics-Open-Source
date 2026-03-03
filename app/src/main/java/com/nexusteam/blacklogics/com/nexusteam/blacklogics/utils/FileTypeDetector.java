package com.nexusteam.blacklogics.utils;

public class FileTypeDetector {

    public static String getLanguageFromFileName(String fileName) {
        if (fileName.endsWith(".java")) {
            return "java";
        } else if (fileName.endsWith(".kt")) {
            return "kotlin";
        } else if (fileName.endsWith(".xml")) {
            return "xml";
        } else if (fileName.endsWith(".js")) {
            return "javascript";
        } else if (fileName.endsWith(".c")) {
            return "c";
        } else if (fileName.endsWith(".cpp") || fileName.endsWith(".c++")) {
            return "cpp";
        } else {
            return "text";
        }
    }

    public static boolean isJavaFile(String fileName) {
        return fileName.endsWith(".java");
    }

    public static boolean isKotlinFile(String fileName) {
        return fileName.endsWith(".kt");
    }

    public static boolean isXmlFile(String fileName) {
        return fileName.endsWith(".xml");
    }

    public static boolean isCodeFile(String fileName) {
        return isJavaFile(fileName) || isKotlinFile(fileName) || 
               fileName.endsWith(".js") || fileName.endsWith(".c") || 
               fileName.endsWith(".cpp") || fileName.endsWith(".py");
    }
}