package com.nexusteam.blacklogics.editor.layout;

import android.util.Log;
import com.nexusteam.blacklogics.editor.layout.model.LayoutData;
import com.shapun.layouteditor.utils.AttributeUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.*;
import java.util.*;

public final class LayoutLoader {
	private static final String TAG = "LayoutLoader";
	private static final String FILE_NAME = "layout.bin";
	
	public static Map<String, String> extractWidgetsFromLayout(String activityName, String layoutDirPath) {
		Map<String, String> widgets = new LinkedHashMap<>();
		if (activityName == null || activityName.isEmpty()) return widgets;
		
		File binFile = new File(layoutDirPath, FILE_NAME);
		if (!binFile.exists()) return widgets;
		
		try {
			ArrayList<LayoutData> layouts = readBinaryLayouts(binFile);
			for (LayoutData data : layouts) {
				if (data != null && activityName.equals(data.name) && data.xml != null) {
					parseWidgetsFromXML(data.xml, widgets);
					break;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Extraction failed", e);
		}
		
		return widgets;
	}
	
	private static void parseWidgetsFromXML(String xml, Map<String, String> widgets) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(false);
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xml));
			
			int eventType;
			while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String tag = parser.getName();
					String widgetType = mapTagToWidgetType(tag);
					
					for (int i = 0; i < parser.getAttributeCount(); i++) {
						String attrName = parser.getAttributeName(i);
						if ("android:id".equals(attrName) || "id".equals(attrName)) {
							String rawId = parser.getAttributeValue(i);
							String id = AttributeUtils.getName(rawId);
							if (id != null && !id.isEmpty()) {
								widgets.put(id, widgetType);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "XML Parse Error", e);
		}
	}
	
	public static String getLayoutXml(String activityName, String layoutDirPath) {
		if (activityName == null || activityName.isEmpty()) return null;
		
		File binFile = new File(layoutDirPath, FILE_NAME);
		if (!binFile.exists()) return null;
		
		try {
			ArrayList<LayoutData> layouts = readBinaryLayouts(binFile);
			for (LayoutData data : layouts) {
				if (data != null && activityName.equals(data.name) && data.xml != null) {
					return data.xml;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Failed to get layout XML", e);
		}
		
		return null;
	}
	
	
	private static ArrayList<LayoutData> readBinaryLayouts(File file) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			Object obj = ois.readObject();
			@SuppressWarnings("unchecked")
			ArrayList<LayoutData> result = (obj instanceof ArrayList) ? (ArrayList<LayoutData>) obj : new ArrayList<LayoutData>();
			return result;
		}
	}
	
	private static String mapTagToWidgetType(String tag) {
		if (tag == null) return "View";
		if (tag.contains(".")) return tag.substring(tag.lastIndexOf('.') + 1);
		return tag;
	}
}
