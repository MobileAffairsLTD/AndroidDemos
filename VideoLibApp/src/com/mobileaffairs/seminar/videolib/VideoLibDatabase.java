package com.mobileaffairs.seminar.videolib;

/*
 * This code is part of the Android Development Course developed by Mobile Affairs LTD for demo purposes
 * http://www.mobile-affairs.com
 * 
 * Copyright (c) 2012 Mobile Affairs LTD.
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php 
 *
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class VideoLibDatabase extends SQLiteOpenHelper  {

	public VideoLibDatabase(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);		
	}
	
	private static int getVersion(Context context){
		int ver=1;
        try
        {
        	ver= context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }
        catch(Exception ex){        	
        }
        return ver;
	}
	
	public VideoLibDatabase(Context context,CursorFactory factory){		       
        super(context, "videodb",factory, VideoLibDatabase.getVersion(context));	
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table videos(_id INTEGER PRIMARY KEY, int,title VARCHAR(100),duration INTEGER,dateCreated datetime,videoUrl VARCHAR(500),thumbnail BLOB)");		
		db.execSQL("create table contacts(_id INTEGER PRIMARY KEY,firstname VARCHAR(100),lastname VARCHAR(100),email VARCHAR(100),dateCreated datetime)");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
