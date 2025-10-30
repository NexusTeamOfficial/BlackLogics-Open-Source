package com.besome.blacklogics.project.encryption;

import com.iyfeng.arsceditor.AndrolibResources;
import com.iyfeng.arsceditor.ResDecoder.ARSCCallBack;
import com.iyfeng.arsceditor.ResDecoder.data.ResTable;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArscObfuser {
    AndrolibResources mAndRes;
    List<String> configs = new ArrayList<>();
    List<String> types = new ArrayList<>();
    List<String> keys = new ArrayList<>();
    List<String> values = new ArrayList<>();

    List<String> changedValues = new ArrayList<>();
    List<String> resTypes = new ArrayList<>();
    List<String> resValues = new ArrayList<>();
    HashMap<String, String> obfusedMap = new HashMap<>();

    byte[] input;
    int obfuscationIndex = 0;

    public ArscObfuser(InputStream in) throws Exception {
        resTypes.add("layout");
        resTypes.add("mipmap");
        resTypes.add("menu");
        resTypes.add("drawable");
        resTypes.add("anim");
        resTypes.add("animator");
        resTypes.add("xml");
        resTypes.add("interpolator");
        resTypes.add("color");

        this.input = toByteArray(in);

        byte[] newBytes = input.clone();
        mAndRes = new AndrolibResources();
        ResTable resTable = mAndRes.getResTable(new ByteArrayInputStream(newBytes));

        ARSCCallBack callback = new ARSCCallBack() {
            @Override
            public void back(String config, String type, String key, String value) {
                if (type != null) {
                    configs.add(config);
                    types.add(type);
                    keys.add(key);
                    values.add(value);
                    changedValues.add("");

                    if (resTypes.contains(type) && value.startsWith("res/")) {
                        resValues.add(value);
                    }
                }
            }
        };

        mAndRes.decodeARSC(resTable, callback);

        for (String value : resValues) {
            String obfusedValue;
            if (value.endsWith(".xml")) {
                obfusedValue = getSequentialName() + ".xml";
            } else {
                obfusedValue = getSequentialName();
            }
            obfusedMap.put(value, obfusedValue);
            mAndRes.mARSCDecoder.replace(value, obfusedValue);
            System.out.println(value + " -> " + obfusedValue);
        }
    }

    private String getSequentialName() {
        int index = obfuscationIndex++;
        StringBuilder sb = new StringBuilder();
        while (index >= 0) {
            sb.append((char) ('a' + index % 26));
            index = index / 26 - 1;
        }
        return sb.reverse().toString();
    }

    public HashMap<String, String> getMap() {
        return obfusedMap;
    }

    private String getKey(String value) {
        int position = values.indexOf(value);
        return keys.get(position);
    }

    private void changeValue(String key, String value) {
        int position = keys.indexOf(key);
        if (position == -1) {
            System.err.println("not found: " + key);
            return;
        }
        changedValues.remove(position);
        changedValues.add(position, value);
    }

    public byte[] getData() throws Exception {
        return mAndRes.mARSCDecoder.write(new ByteArrayInputStream(input), values, changedValues);
    }

    public static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        in.close();
        return out.toByteArray();
    }
}
