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
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MoviesRenderer extends  AsyncTask<Integer,Integer,VideosCursor> {	
	  String patternToSearch;
	  Activity actv;
	  int lastPosition;
	  ProgressDialog pd;
	  int maxRecords=10;
	  int initialPosition=0;	  
	  public MoviesRenderer(Activity actv,ProgressDialog pd, String patternToSearch) {
		this.patternToSearch = patternToSearch;
		this.actv = actv;
		this.pd = pd;
	}	
	protected VideosCursor doInBackground(Integer... params) {	
		    initialPosition = params[1];
			VideosDAO dao = new VideosDAO(actv);
			VideosCursor cursor=  dao.getVideoList(patternToSearch,params[0]);
			this.maxRecords = params[0];
			return cursor;
	 }
	  @Override
	  protected void onProgressUpdate(Integer... progress) {
	        
	  }  
	  @Override
	  protected void onPreExecute() {
	    	
		  VideoLibApplication app = (VideoLibApplication)actv.getApplication();
		  //release existing cursor
		  if(app.activeMoviesCursor!=null){
			  app.activeMoviesCursor.close();
			  app.activeMoviesCursor = null;
		  }
	  }		    
	  @Override  
	  protected void onPostExecute(VideosCursor result) {
		  	VideoLibApplication app = (VideoLibApplication)actv.getApplication();
		  	app.activeMoviesCursor = result;
		    VideosAdapter adapter = new VideosAdapter(actv,result);
			final ListView moviesList = (ListView)actv.findViewById(R.id.videosList);
			
			moviesList.setAdapter(adapter);
			moviesList.setSelectionFromTop(initialPosition, 0);
			moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0,
						View view, int index, long l) {
					if(view.getTag()!=null){
						String videoId  = (String)view.getTag();						
						Intent activityIntent = new Intent(actv.getApplicationContext(),VideoDetailsActivity.class);
						activityIntent.putExtra("videoid",videoId );
						moviesList.getContext().startActivity(activityIntent);
					}
					else{
						//may be the footer item was clicked	
						ProgressDialog newPd = ProgressDialog.show(actv, "VideoLib", "Loading all...");
						VideoLibApplication app = (VideoLibApplication)actv.getApplication();
						new MoviesRenderer(actv, newPd, patternToSearch).execute(app.configManager.evenMoreRecords,moviesList.getLastVisiblePosition());
					}
				}				
			}); 
			View footerItem = actv.findViewById(R.id.footer_layout);
			footerItem.setVisibility((this.maxRecords==app.configManager.evenMoreRecords)?View.INVISIBLE:View.VISIBLE);		
			
			
			if(pd!=null)
				pd.cancel();
    }	
}

