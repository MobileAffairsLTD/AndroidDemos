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
import android.app.Activity;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoDetailsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.videodetails);		
		
		String videoId  = getIntent().getStringExtra("videoid");									
		VideosDAO dao = new VideosDAO(this);
		Cursor c = dao.query(null, "_ID="+videoId, null, "");
		c.moveToFirst();
		
		TextView tbTitle = (TextView) findViewById(R.id.tbTitle);
		tbTitle.setText(c.getString(c.getColumnIndex("title")));
		
		
		TextView tbUrl = (TextView) findViewById(R.id.tbUrl);
		tbUrl.setText(c.getString(c.getColumnIndex("videoUrl")));
		
		ImageView iv   = (ImageView)findViewById(R.id.imageView1);
		byte[] bb = c.getBlob(c.getColumnIndex("thumbnail")); 
		iv.setImageBitmap(BitmapFactory.decodeByteArray(bb, 0, bb.length));
		
		TextView tvDuration = (TextView) findViewById(R.id.tbDuration);
		int secs = c.getInt(c.getColumnIndex("duration"));
		float mins = secs/60;
		String s = String.format("%.2f min", mins);		
		tvDuration.setText(s);
		
		c.close();
	}

}
