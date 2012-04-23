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


import videolib.seminar.com.R;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VideosAdapter extends CursorAdapter {

	public VideosAdapter(Context context, Cursor c) {
		super(context, c,0);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
		if(tvTitle!=null){
			tvTitle.setText(cursor.getString(cursor.getColumnIndex("title")));
		}		
		TextView tvDuration = (TextView)view.findViewById(R.id.tvDuration);
		if(tvDuration!=null){
			int secs = cursor.getInt(cursor.getColumnIndex("duration"));
			float mins = secs/60;
			String s = String.format("%.2f min", mins);		
			tvDuration.setText(s);
		}		
		ImageView tvImage = (ImageView)view.findViewById(R.id.tvImage);
		if(tvImage!=null){
			byte[] bb = cursor.getBlob(cursor.getColumnIndex("thumbnail"));		
			tvImage.setImageBitmap(BitmapFactory.decodeByteArray(bb,0,bb.length));
		}
		if(cursor!=null){
			view.setTag(cursor.getString(cursor.getColumnIndex("_id")));		
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.videoslistitem, parent, false);
		bindView(v, context, cursor);
		return v;
	}
	

}
