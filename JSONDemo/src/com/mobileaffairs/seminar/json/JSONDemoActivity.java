package com.mobileaffairs.seminar.json;

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
import json.demo.com.R;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class JSONDemoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
       //JSON parse demo
        String jsonContent = getResources().getString(R.string.jsonString);
        try {
			JSONArray jsonArray = new JSONArray(jsonContent);
			for(int i=0;i<100;i++){
				Log.i("json",jsonArray.getJSONObject(0).getString("field1"));
				Log.i("json",jsonArray.getJSONObject(0).getJSONArray("field3").getJSONObject(0).getString("f1"));
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
        
        
//        //Xml parse demo via Xml Pull API
//        try {
//        	XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();        
//			parser.setInput(new StringReader(""));
//			int eventType = parser.getEventType();
//			while(eventType!=XmlPullParser.END_DOCUMENT){
//				parser.next();
//				if(parser.getEventType()==XmlPullParser.START_TAG){
//					if(parser.getName()=="tag1"){
//						String value = parser.getAttributeValue(null,"attr1");
//					}
//				}
//			}
//			
//		} catch (XmlPullParserException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
}