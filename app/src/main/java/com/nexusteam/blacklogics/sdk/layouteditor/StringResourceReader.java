package com.nexusteam.blacklogics.sdk.layouteditor;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;

class StringResourceReader {

	static String read(String scId, String resourceName) {
		String filePath = "/storage/emulated/0/.blacklogics/data/"
		+ scId + "/files/resource/values/strings.xml";
		File file = new File(filePath);
		if (!file.exists()) return null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new FileInputStream(file), "UTF-8");
			String currentName = null;
			StringBuilder currentValue = new StringBuilder();
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG
				&& "string".equals(parser.getName())) {
					currentName = parser.getAttributeValue(null, "name");
				} else if (eventType == XmlPullParser.TEXT && currentName != null) {
					currentValue.append(parser.getText());
				} else if (eventType == XmlPullParser.END_TAG
				&& "string".equals(parser.getName())) {
					if (currentName != null && currentName.equals(resourceName))
					return currentValue.toString();
					currentName = null;
					currentValue.setLength(0);
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			Log.e("StringResourceReader", "Error parsing strings.xml: " + e.getMessage(), e);
		}
		return null;
	}
}
