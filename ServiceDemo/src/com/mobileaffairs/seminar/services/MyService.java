package com.mobileaffairs.seminar.services;

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
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {

public static final  String MYCUSTOMINTENT="my.custom.intent.com";
	
	//do not do this as it may cause memory leaks  - according to Geoff Bruckner,Dec 2009
	//	public class LocalBinder extends Binder{
	//		MyService getService(){
	//			return MyService.this;
	//		}
	//	}
	//private final IBinder binder = new LocalBinder();	
	
	 protected void progressUpdate(Integer progress) {
	        Intent i = new Intent();
	        i.setAction(MYCUSTOMINTENT);
	        i.putExtra("progress",progress);
	        this.getApplicationContext().sendBroadcast(i);
	    }
	 
	
	@Override
	public IBinder onBind(Intent arg0) {
		//return new LocalBinder
		return new LocalBinder<MyService>(this);
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		
		new Thread(new Runnable(){

			public void run() {
				
				for(int i=0;i<=100;i++){
					try {
						Thread.sleep(100);
						progressUpdate(i);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		).start();
	}
	

		
}
