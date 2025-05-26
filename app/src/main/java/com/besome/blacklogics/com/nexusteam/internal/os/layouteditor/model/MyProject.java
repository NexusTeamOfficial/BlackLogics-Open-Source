package com.nexusteam.internal.os.layouteditor.model;

import java.io.*;
import com.besome.blacklogics.*;
import com.besome.blacklogics.R;
//import com.nexusteam.internal.os.layouteditor.R;
//import com.nexusteam.internal.os.layouteditor.*;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

public class MyProject
{
	String path;
    File mPath;
	private String mFilePath;
	private String mProjectName;
	private String scId;
	private String pkgName;
	

    public MyProject(String path)
	{
        this.path = path;
	    mPath = new File(path);
		this.scId = TheBlockLogicsUtil.readFile(mPath.getAbsolutePath() + "/project_id.txt").trim();
		this.pkgName = TheBlockLogicsUtil.readFile(mPath.getAbsolutePath() + "/pkgName.txt").trim();
    }
	public void setProjectName(String str) {
        this.mProjectName = str;
    }
    public String getProjectName() {
        return this.mProjectName;
    }
	public String getPath()
	{
		return mPath.getAbsolutePath();
	}

    public String getName()
	{
        return mPath.getName();
    }
	
	public String getScId() {
        return this.scId; // Ye method ab sc_id return karega
    }
	
	public String getPkgName() { return this.pkgName;}
}
