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


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.text.TextUtils;

public class VideoLibContentProvider extends ContentProvider {
	
	public static final String PROVIDER_NAME ="videolib.seminar.com.VideoLibContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER_NAME + "/videos");
    public static final String _ID = "_id";
	public static final String TITLE = "title";
   	private static final int VIDEOS = 1;
	private static final int VIDEO_ID = 2;
	private static final UriMatcher uriMatcher;
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "videos", VIDEOS);
		uriMatcher.addURI(PROVIDER_NAME, "videos/#",VIDEO_ID);
		}
			
	
	private VideosDAO dao;
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count=0;
		switch (uriMatcher.match(uri)){
			case VIDEOS:
				count = dao.delete(selection,selectionArgs);
		break;
		case VIDEO_ID:
		String id = uri.getPathSegments().get(1);
		count = dao.delete(_ID + " = " + id +(!TextUtils.isEmpty(selection) ? " AND (" +
		selection + ')' : ""),
		selectionArgs);
		break;
			default: 
				throw new IllegalArgumentException("Unknown URI " + uri);}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)){
		case VIDEOS:
			return "vnd.android.cursor.dir/vnd.videolib.videos";
		case VIDEO_ID:
			return "vnd.android.cursor.item/vnd.videolib.videos ";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowID = dao.insert(values);
		//---if added successfully---
		if (rowID>0)
		{
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
		return _uri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		Context context = getContext();
		this.dao = new VideosDAO(context);		
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
			String _selection  = selection;
			if(_selection==null)
				_selection="";
			if (uriMatcher.match(uri) == VIDEO_ID)
				_selection+=	_ID + " = " + uri.getPathSegments().get(1);
			if (sortOrder==null || sortOrder=="")
				sortOrder = "title";
			Cursor c = dao.query(
			projection,
			_selection,
			selectionArgs,			
			sortOrder);
			//---register to watch a content URI for changes---
			c.setNotificationUri(getContext().getContentResolver(), uri);
			return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)){
		case VIDEOS:
			count = dao.update(		   
		    values,
		    selection,
		    selectionArgs);
			break;
		case VIDEO_ID:
			count = dao.update(values,
		_ID + " = " + uri.getPathSegments().get(1) +
		(!TextUtils.isEmpty(selection) ? " AND (" +
		selection + ')' : ""),
		selectionArgs);
		break;
		default: 
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
