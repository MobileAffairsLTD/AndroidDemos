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


import com.mobileaffairs.seminar.videolib.VideoLibDatabase;
import com.mobileaffairs.seminar.videolib.VideosCursor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

public class VideosDAO extends VideoLibDatabase {

	private final static String VIDEO_TABLE = "videos";
	
	public VideosDAO(Context context) {
		super(context, new VideosCursor.Factory()); 
	}

	public VideosCursor getVideoList(CharSequence title,Integer maxRows){
		SQLiteDatabase db = this.getWritableDatabase();
		VideosCursor cursor = null;
		if(title!=null && title.length()>0)
			cursor = (VideosCursor) db.rawQuery("select * from "+VIDEO_TABLE+" where title like ? limit ?",new String[]{"%"+title.toString()+"%",maxRows.toString()});
		else
			cursor =(VideosCursor) db.rawQuery("select * from "+VIDEO_TABLE+" limit ?",new String[]{maxRows.toString()}); 
		cursor.moveToFirst();
		return cursor;
	}		
	
	public long insert(ContentValues values){
		SQLiteDatabase db = this.getWritableDatabase();
		long pk = db.insert(VIDEO_TABLE, "", values);
		db.close();
		return pk;
	}
	
	public int update(ContentValues values,String selection,String[] selectionsArgs){
		SQLiteDatabase db = this.getWritableDatabase();		
		int recsAffected = db.update(VIDEO_TABLE,values,selection,selectionsArgs);
		db.close();
		return recsAffected;
		
	}
	
	public Cursor query(String[] projection,String selection ,String[] selectionArgs,String sortOrder){
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
		sqlBuilder.setTables(VIDEO_TABLE);
		Cursor c = sqlBuilder.query(
		db,
		projection,
		selection,
		selectionArgs,
		null,
		null,
		sortOrder);
		return c;
	}
	
	public int delete(String selection,String[] selectionsArgs){
		SQLiteDatabase db = this.getWritableDatabase();	
		int recsAffected = db.delete(VIDEO_TABLE,selection,selectionsArgs);
		return recsAffected;
		
	}	
}




    
    



