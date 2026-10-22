package com.nexusteam.blacklogics.generator.source;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BlCommandProcessor {
	private static final String TAG = "BlCommandProcessor";
	private static final Pattern MULTI_LINE = Pattern.compile(
			"/\\*@BLCMD\\s*\\r?\\n(.*?)^---\\s*\\r?\\n(.*?)^@BLCMD\\*/",
			Pattern.DOTALL | Pattern.MULTILINE);
	private static final Pattern COMPACT = Pattern.compile(
			"/\\*@BLCMD\\s+(.*?)\\s+---\\s+(.*?)\\s*@BLCMD\\*/", Pattern.DOTALL);
	private BlCommandProcessor() {}

	public static String apply(String source) {
		if (source == null || source.length() == 0) return source;
		List<Cmd> commands = new ArrayList<Cmd>();
		Matcher m1 = MULTI_LINE.matcher(source);
		while (m1.find()) {
			try {
				Cmd c = parse(m1.group(1), m1.group(2));
				if (c != null) { c.start = m1.start(); c.end = m1.end(); commands.add(c); }
			} catch (Throwable t) { Log.w(TAG, "multi fail", t); }
		}
		Matcher m2 = COMPACT.matcher(source);
		while (m2.find()) {
			boolean ov = false;
			for (int i = 0; i < commands.size(); i++) {
				Cmd x = commands.get(i);
				if (m2.start() < x.end && m2.end() > x.start) { ov = true; break; }
			}
			if (ov) continue;
			try {
				Cmd c = parse(m2.group(1), m2.group(2));
				if (c != null) { c.start = m2.start(); c.end = m2.end(); commands.add(c); }
			} catch (Throwable t) { Log.w(TAG, "compact fail", t); }
		}
		if (commands.isEmpty()) return source;
		Collections.sort(commands, new Comparator<Cmd>() {
			public int compare(Cmd a, Cmd b) { return Integer.compare(a.start, b.start); }
		});
		StringBuilder sb = new StringBuilder(source);
		for (int i = commands.size() - 1; i >= 0; i--) {
			Cmd c = commands.get(i);
			sb.replace(c.start, c.end, "");
			sb = new StringBuilder(exec(sb.toString(), c));
		}
		return sb.toString();
	}

	private static Cmd parse(String header, String body) {
		if (header == null) return null;
		Cmd c = new Cmd();
		c.content = body != null ? body.trim() : "";
		String[] tokens = header.contains("\n") ? header.split("\\r?\\n") : header.trim().split("\\s+");
		for (int i = 0; i < tokens.length; i++) {
			String line = tokens[i].trim();
			int eq = line.indexOf('=');
			if (eq <= 0) continue;
			String key = line.substring(0, eq).trim().toLowerCase(Locale.US);
			String val = line.substring(eq + 1).trim();
			if ("cmd".equals(key) || "command".equals(key)) c.cmd = val.toLowerCase(Locale.US);
			else if ("ref".equals(key) || "reference".equals(key)) c.ref = unquote(val);
			else if ("dist".equals(key) || "distance".equals(key)) try { c.dist = Integer.parseInt(val.trim()); } catch (Exception e) {}
		}
		if (c.cmd == null || c.cmd.length() == 0) return null;
		if (c.ref == null) c.ref = "";
		return c;
	}

	private static String unquote(String s) {
		if (s == null) return "";
		s = s.trim();
		if (s.length() >= 2) {
			char a = s.charAt(0), b = s.charAt(s.length()-1);
			if ((a=='"'&&b=='"')||(a=='\''&&b=='\'')) return s.substring(1,s.length()-1);
		}
		return s;
	}

	private static String exec(String source, Cmd c) {
		if (c.ref.length()==0) return source;
		String ref=c.ref, content=c.content!=null?c.content:"";
		int dist=Math.max(0,c.dist);
		if ("insert".equals(c.cmd)) return nth(source,ref,content+ref,dist);
		if ("add".equals(c.cmd)) return nth(source,ref,ref+content,dist);
		if ("replace".equals(c.cmd)||"find-replace".equals(c.cmd)) return nth(source,ref,content,dist);
		if ("find-replace-first".equals(c.cmd)) return nth(source,ref,content,0);
		if ("find-replace-all".equals(c.cmd)) return source.replace(ref,content);
		return source;
	}

	private static String nth(String source, String target, String replacement, int n) {
		if (target.length()==0) return source;
		int from=0, found=-1;
		for (int i=0;i<=n;i++) {
			found=source.indexOf(target,from);
			if (found<0) return source;
			from=found+target.length();
		}
		return source.substring(0,found)+replacement+source.substring(found+target.length());
	}

	private static final class Cmd {
		String cmd, ref="", content="";
		int dist, start, end;
	}
}
