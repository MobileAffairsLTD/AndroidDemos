package com.mobileaffairs.seminars.asynctask;

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

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Async_UsingAsyncTaskActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void runLongOperation(View v){      	
    	new AsyncTask<String,Integer,Double>(){
			@Override
			protected void onPreExecute() {
				//executed on the main UI thread
				//safe to manipulate UI resources(Views, ViewGroups, etc)
				ProgressBar bar = (ProgressBar)Async_UsingAsyncTaskActivity.this.findViewById(R.id.progressBar);
				bar.setProgress(0);
				bar.setMax(100);
			}			
			
			@Override
			protected void onProgressUpdate(Integer... values) {
				//executed on the main UI thread
				//safe to manipulate UI resources(Views, ViewGroups, etc)
				ProgressBar bar = (ProgressBar)Async_UsingAsyncTaskActivity.this.findViewById(R.id.progressBar);
				bar.setProgress(values[0]);
			}			
			@Override
			protected Double doInBackground(String... params) {		
				//executed in separate thread - DO NOT TOUCH UI RESOURCES HERE
				for(int i=0;i<100;i++){
					try {
						Thread.sleep(100);//do a hard and long work here...
					} catch (InterruptedException e) {
						e.printStackTrace();
					}					
					//force the AsyncTask to post progress update message to the UI thread
					//we are passing the current progress as parameter
					this.publishProgress(new Integer(i));
				}
				return 123.00;//this is the final result from the operation
			}			
			@Override
			protected void onPostExecute(Double result) {
				//executed on the main UI thread
				//safe to manipulate UI resources(Views, ViewGroups, etc)
				Toast.makeText(Async_UsingAsyncTaskActivity.this, "Job done", Toast.LENGTH_LONG).show();
			}			
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{"here comes the params passed to the task","param 2"});		
    }
}