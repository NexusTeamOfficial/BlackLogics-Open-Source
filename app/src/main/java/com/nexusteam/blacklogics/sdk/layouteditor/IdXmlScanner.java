package com.nexusteam.blacklogics.sdk.layouteditor;

import android.util.Log;

import com.nexusteam.blacklogics.sdk.utils.AttributeUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

class IdXmlScanner {

	private static final String STORE_FILE = "layoutstore";
	private static final byte[] MASK_KEY = "NEXUS_LAYOUT_KEY_7734".getBytes();

	private static byte[] applyMask(byte[] data) {
		byte[] result = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			result[i] = (byte) (data[i] ^ MASK_KEY[i % MASK_KEY.length]);
		}
		return result;
	}

	private static String readXmlForActivity(String savePath, String activityName) {
		File storeFile = new File(savePath, STORE_FILE);
		if (!storeFile.exists()) return null;

		FileInputStream fis = null;
		DataInputStream in = null;
		try {
			byte[] raw = new byte[(int) storeFile.length()];
			fis = new FileInputStream(storeFile);
			fis.read(raw);

			byte[] plain = applyMask(raw);
			in = new DataInputStream(new ByteArrayInputStream(plain));

			in.readInt();
			int entryCount = in.readInt();
			for (int i = 0; i < entryCount; i++) {
				String name = in.readUTF();
				String xml = in.readUTF();
				int rootCount = in.readInt();
				for (int r = 0; r < rootCount; r++) {
					skipViewNode(in);
				}
				if (activityName.equals(name)) return xml;
			}
		} catch (Exception e) {
			Log.e("IdXmlScanner", "readXmlForActivity failed: " + e.getMessage(), e);
		} finally {
			try { if (in != null) in.close(); } catch (Exception ignored) {}
			try { if (fis != null) fis.close(); } catch (Exception ignored) {}
		}
		return null;
	}

	private static void skipViewNode(DataInputStream in) throws Exception {
		in.readUTF();
		in.readBoolean();
		in.readBoolean();
		in.readUTF();
		in.readUTF();

		int attrCount = in.readInt();
		for (int i = 0; i < attrCount; i++) {
			in.readUTF();
			in.readUTF();
		}

		int childCount = in.readInt();
		for (int i = 0; i < childCount; i++) {
			skipViewNode(in);
		}
	}

	static Map<String, String> getIdsWithClass(String type, String activityName, String savePath) {
		Map<String, String> ids = new LinkedHashMap<String, String>();

		String xmlContent = readXmlForActivity(savePath, activityName);
		if (xmlContent == null || xmlContent.trim().isEmpty()) return ids;

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new StringReader(xmlContent));

			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String tagName = parser.getName();
					String viewId = null;
					for (int i = 0; i < parser.getAttributeCount(); i++) {
						if ("android:id".equals(parser.getAttributeName(i))) {
							viewId = AttributeUtils.getName(
							parser.getAttributeValue(i));
							break;
						}
					}
					if (viewId != null && !viewId.isEmpty()) {
						if ("all".equalsIgnoreCase(type)
						|| tagName.equalsIgnoreCase(type)) {
							ids.put(viewId, tagName);
						}
					}
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			Log.e("IdXmlScanner", "getIdsWithClass failed: " + e.getMessage(), e);
		}
		return ids;
	}
}
