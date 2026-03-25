package com.nexusteam.internal;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.beans.ProjectResourceBean;
import com.bumptech.glide.signature.ObjectKey;
import com.bumptech.glide.load.Key;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class mb {
	private static Key i;
	
	/* renamed from: a  reason: collision with root package name */
	public ArrayList<ProjectResourceBean> f373a;
	public ArrayList<ProjectResourceBean> b;
	public ArrayList<ProjectResourceBean> c;
	protected String d;
	protected String e;
	protected String f;
	public String g;
	private kk h;
	private Gson j;
	
	public mb(String str) {
		this(str, fe.r() + File.separator + str, fe.s() + File.separator + str, fe.t() + File.separator + str);
	}
	
	public mb(String str, String str2, String str3, String str4) {
		this.d = "";
		this.e = "";
		this.f = "";
		this.d = str2;
		this.e = str3;
		this.f = str4;
		this.g = str;
		c();
		this.h = new kk(false);
		this.f373a = new ArrayList<>();
		this.b = new ArrayList<>();
		this.c = new ArrayList<>();
		this.j = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}
	
	public void a() {
		this.g = "";
		this.d = "";
		this.e = "";
		this.f = "";
		this.f373a = new ArrayList<>();
		this.b = new ArrayList<>();
		this.c = new ArrayList<>();
	}
	
	public static Key b() {
		if (i == null) {
			c();
		}
		return i;
	}
	
	public static void c() {
		i = new ObjectKey(String.valueOf(System.currentTimeMillis()));
	}
	
	public void a(ArrayList<ProjectResourceBean> arrayList) {
		this.f373a = arrayList;
	}
	
	public void b(ArrayList<ProjectResourceBean> arrayList) {
		this.b = arrayList;
	}
	
	public void c(ArrayList<ProjectResourceBean> arrayList) {
		this.c = arrayList;
	}
	
	public String d() {
		return this.d;
	}
	
	public String e() {
		return this.e;
	}
	
	public String f() {
		return this.f;
	}
	
	public void g() {
		
	}
	
	public void a(BufferedReader bufferedReader) {
		try {
			String str = "";
			StringBuffer stringBuffer = new StringBuffer();
			while (true) {
				String readLine = bufferedReader.readLine();
				if (readLine == null) {
					break;
				} else if (readLine.length() > 0) {
					if (readLine.charAt(0) == '@') {
						if (str.length() > 0) {
							a(str, stringBuffer.toString());
							stringBuffer = new StringBuffer();
						}
						str = readLine.substring(1);
					} else {
						stringBuffer.append(readLine);
						stringBuffer.append("\n");
					}
				}
			}
			if (str.length() > 0) {
				a(str, stringBuffer.toString());
			}
		} catch(Exception je) {
			
		}
	}
	
	public void a(String type, String data) {
		if (data.trim().length() <= 0) {
			return;
		}
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new StringReader(data));
			String line;
			
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				
				if (line.charAt(0) != '{') continue;
				
				
				ProjectResourceBean bean = j.fromJson(line, ProjectResourceBean.class);
				
				if ("images".equals(type)) {
					f373a.add(bean);
				} else if ("sounds".equals(type)) {
					b.add(bean);
				} else if ("fonts".equals(type)) {
					c.add(bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void h() {
		String str = fe.d(this.g) + File.separator + "resource";
		StringBuffer stringBuffer = new StringBuffer();
		a(stringBuffer);
		try {
			this.h.a(str, this.h.g(stringBuffer.toString()));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		i();
	}
	
	private void a(StringBuffer stringBuffer) {
		stringBuffer.append("@");
		stringBuffer.append("images");
		stringBuffer.append("\n");
		Iterator<ProjectResourceBean> it = this.f373a.iterator();
		while (it.hasNext()) {
			stringBuffer.append(this.j.toJson(it.next(), ProjectResourceBean.class));
			stringBuffer.append("\n");
		}
		stringBuffer.append("@");
		stringBuffer.append("sounds");
		stringBuffer.append("\n");
		Iterator<ProjectResourceBean> it2 = this.b.iterator();
		while (it2.hasNext()) {
			stringBuffer.append(this.j.toJson(it2.next(), ProjectResourceBean.class));
			stringBuffer.append("\n");
		}
		stringBuffer.append("@");
		stringBuffer.append("fonts");
		stringBuffer.append("\n");
		Iterator<ProjectResourceBean> it3 = this.c.iterator();
		while (it3.hasNext()) {
			stringBuffer.append(this.j.toJson(it3.next(), ProjectResourceBean.class));
			stringBuffer.append("\n");
		}
	}
	
	public void i() {
		String e2 = fe.e(this.g);
		this.h.e(e2 + File.separator + "resource");
	}
	
	public void j() {
		String str = fe.e(this.g) + File.separator + "resource";
		StringBuffer stringBuffer = new StringBuffer();
		a(stringBuffer);
		try {
			this.h.a(str, this.h.g(stringBuffer.toString()));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public boolean k() {
		String e2 = fe.e(this.g);
		return this.h.a(e2 + File.separator + "resource");
	}
	
	public void l() {
		
		this.f373a = new ArrayList<>();
		this.b = new ArrayList<>();
		this.c = new ArrayList<>();
		
		
		String path = fe.e(this.g);
		String resourcePath = path + File.separator + "resource";
		
		BufferedReader reader = null;
		try {
			
			byte[] bytes = this.h.f(resourcePath);
			String data = this.h.b(bytes);
			
			
			reader = new BufferedReader(new StringReader(data));
			
			
			a(reader);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		}
	}
	
	
	public ArrayList<String> m() {
		ArrayList<String> arrayList = new ArrayList<>();
		Iterator<ProjectResourceBean> it = this.f373a.iterator();
		while (it.hasNext()) {
			arrayList.add(it.next().resName);
		}
		return arrayList;
	}
	
	public int a(String str) {
		if (this.f373a == null || this.f373a.size() <= 0) {
			return -1;
		}
		Iterator<ProjectResourceBean> it = this.f373a.iterator();
		while (it.hasNext()) {
			ProjectResourceBean next = it.next();
			if (next.resName.equals(str)) {
				return next.resType;
			}
		}
		return -1;
	}
	
	public String b(String str) {
		if (this.f373a == null || this.f373a.size() <= 0) {
			return "";
		}
		Iterator<ProjectResourceBean> it = this.f373a.iterator();
		while (it.hasNext()) {
			ProjectResourceBean next = it.next();
			if (next.resName.equals(str)) {
				return this.d + File.separator + next.resFullName;
			}
		}
		return "";
	}
	
	public ArrayList<String> n() {
		ArrayList<String> arrayList = new ArrayList<>();
		Iterator<ProjectResourceBean> it = this.b.iterator();
		while (it.hasNext()) {
			arrayList.add(it.next().resName);
		}
		return arrayList;
	}
	
	public ArrayList<String> o() {
		ArrayList<String> arrayList = new ArrayList<>();
		Iterator<ProjectResourceBean> it = this.c.iterator();
		while (it.hasNext()) {
			arrayList.add(it.next().resName);
		}
		return arrayList;
	}
	
	public String c(String str) {
		if (this.c == null || this.c.size() <= 0) {
			return "";
		}
		Iterator<ProjectResourceBean> it = this.c.iterator();
		while (it.hasNext()) {
			ProjectResourceBean next = it.next();
			if (next.resName.equals(str)) {
				return this.f + File.separator + next.resFullName;
			}
		}
		return "";
	}
	
	public String d(String str) {
		if (this.b == null || this.b.size() <= 0) {
			return "";
		}
		Iterator<ProjectResourceBean> it = this.b.iterator();
		while (it.hasNext()) {
			ProjectResourceBean next = it.next();
			if (next.resName.equals(str)) {
				return this.e + File.separator + next.resFullName;
			}
		}
		return "";
	}
	
	public void p() {
		boolean z;
		File[] listFiles = new File(this.d).listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				file.isDirectory();
				if (file.isFile()) {
					Iterator<ProjectResourceBean> it = this.f373a.iterator();
					while (true) {
						if (it.hasNext()) {
							if (it.next().resFullName.equals(file.getName())) {
								z = true;
								break;
							}
						} else {
							z = false;
							break;
						}
					}
					if (!z) {
						file.delete();
					}
				}
			}
		}
	}
	
	public void q() {
		boolean z;
		File[] listFiles = new File(this.e).listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				file.isDirectory();
				if (file.isFile()) {
					Iterator<ProjectResourceBean> it = this.b.iterator();
					while (true) {
						if (it.hasNext()) {
							if (it.next().resFullName.equals(file.getName())) {
								z = true;
								break;
							}
						} else {
							z = false;
							break;
						}
					}
					if (!z) {
						file.delete();
					}
				}
			}
		}
	}
	
	public void r() {
		boolean z;
		File[] listFiles = new File(this.f).listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				file.isDirectory();
				if (file.isFile()) {
					Iterator<ProjectResourceBean> it = this.c.iterator();
					while (true) {
						if (it.hasNext()) {
							if (it.next().resFullName.equals(file.getName())) {
								z = true;
								break;
							}
						} else {
							z = false;
							break;
						}
					}
					if (!z) {
						file.delete();
					}
				}
			}
		}
	}
	
	public void s() {
		p();
		q();
		r();
	}
	
	public void e(String str) {
		if (this.f373a != null && this.f373a.size() > 0) {
			File file = new File(str);
			if (!file.exists()) {
				file.mkdirs();
			}
			Iterator<ProjectResourceBean> it = this.f373a.iterator();
			while (it.hasNext()) {
				ProjectResourceBean next = it.next();
				try {
					this.h.a(this.d + File.separator + next.resFullName.toLowerCase(), str + File.separator + next.resFullName.toLowerCase());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	public void f(String str) {
		if (this.b != null && this.b.size() > 0) {
			File file = new File(str);
			if (!file.exists()) {
				file.mkdirs();
			}
			Iterator<ProjectResourceBean> it = this.b.iterator();
			while (it.hasNext()) {
				ProjectResourceBean next = it.next();
				try {
					this.h.a(this.e + File.separator + next.resFullName.toLowerCase(), str + File.separator + next.resFullName.toLowerCase());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	public void g(String str) {
		if (this.c != null && this.c.size() > 0) {
			File file = new File(str);
			if (!file.exists()) {
				file.mkdirs();
			}
			Iterator<ProjectResourceBean> it = this.c.iterator();
			while (it.hasNext()) {
				ProjectResourceBean next = it.next();
				try {
					this.h.a(this.f + File.separator + next.resFullName.toLowerCase(), str + File.separator + next.resFullName.toLowerCase());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	public void t() {
		String m = fe.m();
		String n = fe.n();
		String o = fe.o();
		try {
			this.h.d(m);
			this.h.d(n);
			this.h.d(o);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public void u() {
		String m = fe.m();
		try {
			this.h.d(m);
			this.h.a(new File(this.d), new File(m));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public void v() {
		String n = fe.n();
		try {
			this.h.d(n);
			this.h.a(new File(this.e), new File(n));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public void w() {
		String o = fe.o();
		try {
			this.h.d(o);
			this.h.a(new File(this.f), new File(o));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public void x() {
		String m = fe.m();
		try {
			this.h.a(new File(this.d));
			this.h.a(new File(m), new File(this.d));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public void y() {
		String n = fe.n();
		try {
			this.h.a(new File(this.e));
			this.h.a(new File(n), new File(this.e));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public void z() {
		String o = fe.o();
		try {
			this.h.a(new File(this.f));
			this.h.a(new File(o), new File(this.f));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public boolean h(String str) {
		Iterator<ProjectResourceBean> it = this.f373a.iterator();
		while (it.hasNext()) {
			if (it.next().resName.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean i(String str) {
		Iterator<ProjectResourceBean> it = this.b.iterator();
		while (it.hasNext()) {
			if (it.next().resName.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean j(String str) {
		Iterator<ProjectResourceBean> it = this.c.iterator();
		while (it.hasNext()) {
			if (it.next().resName.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	public ProjectResourceBean k(String str) {
		Iterator<ProjectResourceBean> it = this.f373a.iterator();
		while (it.hasNext()) {
			ProjectResourceBean next = it.next();
			if (next.resName.equals(str)) {
				return next;
			}
		}
		return null;
	}
	
	public ProjectResourceBean l(String str) {
		Iterator<ProjectResourceBean> it = this.b.iterator();
		while (it.hasNext()) {
			ProjectResourceBean next = it.next();
			if (next.resName.equals(str)) {
				return next;
			}
		}
		return null;
	}
	
	public ProjectResourceBean m(String str) {
		Iterator<ProjectResourceBean> it = this.c.iterator();
		while (it.hasNext()) {
			ProjectResourceBean next = it.next();
			if (next.resName.equals(str)) {
				return next;
			}
		}
		return null;
	}
}
