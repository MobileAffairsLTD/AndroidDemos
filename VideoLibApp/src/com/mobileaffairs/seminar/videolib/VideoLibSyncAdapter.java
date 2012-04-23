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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class VideoLibSyncAdapter extends AbstractThreadedSyncAdapter {

	public VideoLibSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
	
	}	
	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		
		String videosJson = readVideosFromRemoteService();	
		try {
			JSONArray jsonArray = new JSONArray(videosJson);
			Log.i("SyncAdapter",
					"Number of entries " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Cursor cs = provider.getLocalContentProvider().query(Uri.parse(VideoLibContentProvider.CONTENT_URI+"/" +jsonObject.getString("id")), null, null, null, null);
				long count = cs.getCount();
				cs.close();
				if(count==0)
				{
					ContentValues values = new  ContentValues();
					values.put("_id", jsonObject.getString("id"));
					values.put("title", jsonObject.getString("title"));
					values.put("duration", jsonObject.getString("duration"));
					values.put("videoUrl", jsonObject.getString("url"));
					byte[] imageBytes =  readImage(jsonObject.getString("thumbnail_small"));
					if(imageBytes!=null && imageBytes.length>0){
						values.put("thumbnail", imageBytes);	
					}					
					provider.getLocalContentProvider().insert(VideoLibContentProvider.CONTENT_URI, values);
				}				
				syncResult.fullSyncRequested  = true;				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}		
	}
	
	private String readVideosFromRemoteService() {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
			"http://vimeo.com/api/v2/group/shortfilms/videos.json");
			
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e("readVideos", "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	private byte[]  readImage(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();			
			if (statusCode == 200) {				
				HttpEntity entity = response.getEntity();
				byte[] imageBytes =  EntityUtils.toByteArray(entity);
				return imageBytes;				
			} else {
				Log.e("readImage", "Failed to download file");
			}
		
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}



