package com.mobileaffairs.seminars.contentprovider;
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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class PeopleDAO extends CustomOpenSQLiteHelper {

	private String TABLE_NAME = "PEOPLE";
	
	public PeopleDAO(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);		
	}
	
	//add person to the database
	public void Add(String title){
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("insert into "+TABLE_NAME+"(title)value(?)",new String[]{title});
	}
	
	//Retrieve all persons
	public Cursor GetAll(){
		SQLiteDatabase db = this.getReadableDatabase();
		return db.query(TABLE_NAME, null, null, null, null, null, "TITLE descending");
	}
}
