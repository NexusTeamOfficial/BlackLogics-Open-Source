package com.nexusteam.blacklogics.generator.utils;

import com.nexusteam.blacklogics.security.crypto.FileEncryptionUtil;
import com.nexusteam.blacklogics.editor.layout.model.LayoutData;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public final class LayoutBinaryReader {

    private static final char[] ENC_PASSWORD =
            "blacklogic_layout_secure".toCharArray();

    private LayoutBinaryReader() {}

    public static ArrayList<LayoutData> readAll(File file) throws Exception {

        if (file == null || !file.exists()) {
            return new ArrayList<>();
        }

        byte[] decrypted =
                FileEncryptionUtil.decryptFromFile(file, ENC_PASSWORD);

        ObjectInputStream ois =
                new ObjectInputStream(
                        new ByteArrayInputStream(decrypted)
                );

        Object obj = ois.readObject();
        ois.close();

        if (!(obj instanceof ArrayList)) {
            return new ArrayList<>();
        }

        @SuppressWarnings("unchecked")
        ArrayList<LayoutData> list =
                (ArrayList<LayoutData>) obj;

        return list;
    }
}
