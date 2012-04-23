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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class VideoLibAppActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
        //add listview's footer item
        //footer view must be added before setadapter
        VideoLibApplication app = (VideoLibApplication)this.getApplication();
        ListView moviesList = (ListView)this.findViewById(R.id.videosList);
    	LayoutInflater li  = (LayoutInflater)app.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	View footer = li.inflate(R.layout.morerecordsitem,null,false);				
    	moviesList.addFooterView(footer);
    	
        Button findButton = (Button)findViewById(R.id.videosSearchBtn);
        findButton.setOnClickListener(new View.OnClickListener() {		
    		public void onClick(final View v) {   
    				  renderMovies(VideoLibAppActivity.this,true);
    		}
    	});       
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater infl = getMenuInflater();
    	infl.inflate(R.menu.actionbar, menu);
    	return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch (item.getItemId()){
		case R.id.itemVideos:
			//do nothing, cause we're already showing videos
			break;
		case R.id.itemFeeds:		
			break;
		case R.id.itemSync:	
			Intent intent  = new Intent(this,SyncActivity.class);
			startActivity(intent);
			break;
		case R.id.itemSettings:			
			break;
		default:
			break;
		}
    	return true;
    }
    
    /**used to store activity information while the app is alive
    if the app is killed, this state will be loosed**/
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//       if(outState!=null){
//    	 TextView  tbSearchBox = (TextView)findViewById(R.id.videosSearch);
//    	 outState.putString("searchpattern", tbSearchBox.getText().toString());
//       }
//    }


    
//    @Override
//   public boolean onOptionsItemSelected(MenuItem item) {
//   	switch (item.getItemId()) {
//   	case videolib.adnroid.seminar.com.R.id.SyncMenuItem:
//   		Intent intent1 = new Intent(getApplicationContext(),SyncActivity.class);
//   		startActivity(intent1);
//   		break;
//   	case videolib.adnroid.seminar.com.R.id.OptionsMenuItem:
//   		Intent intent2 = new Intent(getApplicationContext(),VideoLibPreferencesActivity.class);
//   		startActivity(intent2);
//   		break;
//   	default:
//   		break;
//   	}
//   	return true;
//   }
    
   /** Renders movie on the screen in asynchronous manner **/
   private void renderMovies(final Activity a,final boolean showProgress) {
   	TextView tv = 	 (TextView)findViewById(R.id.videosSearch);			   	
   	ProgressDialog pd = null;
   	if(showProgress){
   		pd  = ProgressDialog.show(a,getString(R.string.app_name), "Working",true,false);
   	}
   	MoviesRenderer renderer = new  MoviesRenderer(this,pd, tv.getText().toString()) ;
   	VideoLibApplication app  = (VideoLibApplication)a.getApplicationContext();
   	renderer.execute(app.configManager.maxRecords,0);
   }
}