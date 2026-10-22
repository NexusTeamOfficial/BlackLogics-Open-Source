package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


public class lb {
    public byte[] a(byte[] bArr) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = "sketchwarelangsc".getBytes();
            instance.init(1, new SecretKeySpec(bytes, "AES"), new IvParameterSpec(bytes));
            return instance.doFinal(bArr);
        } catch(Exception e) { return null;}
    }
    
    public byte[] b(byte[] bArr) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = "sketchwarelangsc".getBytes();
            instance.init(2, new SecretKeySpec(bytes, "AES"), new IvParameterSpec(bytes));
            return instance.doFinal(bArr);
        } catch(Exception e) { return null;}
    }
    
    public static HashMap<String, Object> a(String str) {
        String str2 = "<" + str + "/>";
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            NodeList elementsByTagName = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(str2.getBytes())).getElementsByTagName("sketchware");
            if (elementsByTagName.getLength() <= 0) {
                return hashMap;
            }
            Node namedItem = elementsByTagName.item(0).getAttributes().getNamedItem("version");
            Node namedItem2 = elementsByTagName.item(0).getAttributes().getNamedItem("locale");
            if (namedItem != null) {
                hashMap.put("version", namedItem.getNodeValue());
            }
            if (namedItem2 != null) {
                hashMap.put("locale", namedItem2.getNodeValue());
            }
            return hashMap;
        } catch (Exception unused) {
            return null;
        }
    }
    
    public static HashMap<String, Object> b(String path) {
        HashMap<String, Object> result = new HashMap<>();
        FileInputStream fis = null;
        InputStreamReader reader = null;
        
        try {
            File file = new File(path);
            if (!file.exists()) {
                return result;
            }
            
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            
            fis = new FileInputStream(file);
            reader = new InputStreamReader(fis, "UTF-8");
            parser.setInput(reader);
            
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.TEXT) {
                    int line = parser.getLineNumber();
                    if ((line == 1 || line == 2) && result.isEmpty()) {
                        String text = parser.getText();
                        if (text != null) {
                            HashMap<String, Object> parsedMap = a(text);
                            if (parsedMap != null) {
                                result = parsedMap;
                            }
                        }
                    }
                }
                eventType = parser.nextToken();
            }
            
            result.put("valid", Boolean.TRUE);
        } catch (Exception e) {
            result.put("valid", Boolean.FALSE);
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (Exception ignored) {}
            
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {}
        }
        
        return result;
    }
    
    
    public static void a(String str, String str2, String str3) {
        try {
            Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(str));
            Element documentElement = parse.getDocumentElement();
            NodeList childNodes = documentElement.getParentNode().getChildNodes();
            ProcessingInstruction createProcessingInstruction = parse.createProcessingInstruction("sketchware", "version=\"" + str2 + "\" locale=\"" + str3 + "\"");
            if (childNodes.item(0).getNodeName().equals("sketchware")) {
                documentElement.getParentNode().removeChild(childNodes.item(0));
            }
            documentElement.getParentNode().insertBefore(createProcessingInstruction, documentElement);
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(parse), new StreamResult(new File(str)));
        } catch(Exception e) {}
    }
}
