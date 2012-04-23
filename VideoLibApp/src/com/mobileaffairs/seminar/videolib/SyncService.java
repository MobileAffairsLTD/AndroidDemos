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


import android.app.Service;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.content.SyncResult;
import android.os.IBinder;


//NOTE: you do not need normally a service , when using SyncAdapter
public class SyncService extends Service {	
	 private static final Object sSyncAdapterLock = new Object();
	    public static String CUSTOM_INTENT="data.videolib.adnroid.seminar.com.syncservice.progress";
	    private static VideoLibSyncAdapter sSyncAdapter = null;
	    @Override
	    public void onCreate() {
	        synchronized (sSyncAdapterLock) {
	            if (sSyncAdapter == null) {
	                sSyncAdapter = new VideoLibSyncAdapter(getApplicationContext(), true);
	            }
	        }
	    }
	    
	    @Override
	    public void onStart(Intent intent, int startId) {
	    	// TODO Auto-generated method stub
	    	
	    	 new Thread(new Runnable() {
				
				public void run() {
					ContentProviderClient provider=  getContentResolver().acquireContentProviderClient(VideoLibContentProvider.PROVIDER_NAME);
			    	SyncResult sc = new  SyncResult();
			    	progressUpdate(1);
			    	try{
			    		sSyncAdapter.onPerformSync(null, null, null, provider, sc);		
			    	}
			    	finally{
			    		progressUpdate(100);
			    	}
				}
			}).start();	    	
	    }

	    @Override
	    public IBinder onBind(Intent intent) {
	        return sSyncAdapter.getSyncAdapterBinder();
	    }
	    
	    
	    protected void progressUpdate(Integer progress) {
	        Intent i = new Intent();
	        i.setAction(CUSTOM_INTENT);
	        i.putExtra("progress",progress);
	        this.getApplicationContext().sendBroadcast(i);
	    }
	    
}

