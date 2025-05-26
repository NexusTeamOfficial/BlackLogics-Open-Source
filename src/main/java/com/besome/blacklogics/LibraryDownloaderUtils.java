package com.besome.blacklogics;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * ENGLISH: This class is not used in this project. You may find it anywhere, but adding it to your work is completely wasteful. Thank you
 * HINDI: इस प्रोजेक्ट में यह क्लास उपयोग नहीं की गई है। आप इसे कहीं भी पा सकते हैं, लेकिन इसे अपने काम में जोड़ना पूरी तरह से व्यर्थ है। धन्यवाद
 * SPANISH: Esta clase no se usa en este proyecto. Puedes encontrarla en cualquier lugar, pero añadirla a tu trabajo es completamente inútil. Gracias
 * FRENCH: Cette classe n'est pas utilisée dans ce projet. Vous pouvez la trouver n'importe où, mais l'ajouter à votre travail est totalement inutile. Merci
 * GERMAN: Diese Klasse wird in diesem Projekt nicht verwendet. Sie kann überall gefunden werden, aber sie in Ihre Arbeit einzufügen ist völlig verschwendet. Danke
 * CHINESE (SIMPLIFIED): 这个类在这个项目中未被使用。你可以在任何地方找到它，但将其添加到你的工作中完全是浪费。谢谢
 * ARABIC: هذا الصنف غير مستخدم في هذا المشروع. يمكنك العثور عليه في أي مكان، لكن إضافته إلى عملك مضيعة تامة. شكرًا
 * RUSSIAN: Этот класс не используется в данном проекте. Вы можете найти его где угодно, но добавление его в вашу работу совершенно бесполезно. Спасибо
 * JAPANESE: このクラスはこのプロジェクトでは使用されていません。どこでも見つけることができますが、あなたの作業に追加するのは完全に無駄です。ありがとう
 * PORTUGUESE: Esta classe não é usada neste projeto. Você pode encontrá-la em qualquer lugar, mas adicioná-la ao seu trabalho é completamente inútil. Obrigado
 */

public class LibraryDownloaderUtils {
    

    private static void createFolder(String path) {
        if (!isExistFile(path)) {
    	    makeDir(path);
        }
    }

    public static void createLibraryDownloaderFolder() {
        createFolder("/storage/emulated/0/Library Downloader/");
        createFolder("/storage/emulated/0/Library Downloader/.d8_libs/");
    }

    public static String getLibraryDownloaderJarFolder() {
        return "/storage/emulated/0/Library Downloader/.d8_libs/";
    }

    public static String getLibraryDownloaderFolder() {
        return "/storage/emulated/0/Library Downloader/";
    }

    public static boolean isExistFile(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static void makeDir(String path) {
        if (!isExistFile(path)) {
            File file = new File(path);
            file.mkdirs();
        }
    }
}