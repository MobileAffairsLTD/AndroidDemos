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
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class SyncActivity extends Activity {
	// Flag if receiver is registered 
    private boolean mReceiversRegistered = false;
    // Define a handler and a broadcast receiver
    private final Handler mHandler = new Handler();
    //progress dialog to show during sync
	private ProgressDialog pd=null;
    
	 //broadcast receiver to listen for progress updates from the sync service
	 private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            if(intent.getAction().equals(SyncService.CUSTOM_INTENT)) {
	                int progressValue = intent.getIntExtra("progress", 100);
	            	//syncing is not done, yet, show progress
	                if(progressValue<100){
	            		startProgress();
	            		Button btnSync  = (Button)findViewById(R.id.btnSync);
	            		btnSync.setEnabled(false);
	            	}
	            	else
	            	//syncing is done, close progress bar
	            	if(progressValue==100)
	            	{
	            		Button btnSync  = (Button)findViewById(R.id.btnSync);
	            		stopProgress();
	            		btnSync.setEnabled(true);
	            	}
	            }
	        }
	    };
	    
	 @Override
	 protected void onResume() {
	      super.onResume();

	      // Register Recievers
	      IntentFilter intentToReceiveFilter = new IntentFilter();
	      intentToReceiveFilter.addAction(SyncService.CUSTOM_INTENT);
	      this.registerReceiver(mIntentReceiver, intentToReceiveFilter, null, mHandler);
	      mReceiversRegistered = true;
	    }

     @Override
     public void onPause() {
      super.onPause();
      // Make sure you unregister your receivers when you pause your activity
      if(mReceiversRegistered) {
        unregisterReceiver(mIntentReceiver);
        mReceiversRegistered = false;
      }
    }
	 //displays the progress dialog
	 private void startProgress(){
		if(this.pd==null){
			this.pd = ProgressDialog.show(this, "VideoLib", "Syncing...",true,false);
		}
     }	
	//hides the progress dialog
 	private void stopProgress(){
	 if(this.pd!=null)
		  this.pd.cancel();
	      pd=null;
	}	    
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.sync);		
		Button btnSync = (Button)findViewById(R.id.btnSync);		
		btnSync.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {				
				Intent service = new Intent(v.getContext(),SyncService.class);				
				startService(service);				
				
			}
		});
	}	 
}