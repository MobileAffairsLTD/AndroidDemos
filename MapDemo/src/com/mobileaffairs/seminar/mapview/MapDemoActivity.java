package com.mobileaffairs.seminar.mapview;

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
import java.util.List;

import mapdemo.com.R;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapDemoActivity extends MapActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//prepare mapview
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		//add your overlay and custom points(overlay items )
		List<Overlay> overlays = mapView.getOverlays();
		Drawable dr = this.getResources().getDrawable(R.drawable.ic_launcher);
		CustomOverlay myOverlay = new CustomOverlay(dr, this);
		GeoPoint p1 = new GeoPoint(30443769, -91158458);
		OverlayItem   overlayItem1 = new OverlayItem(p1,"Hello", "I am here");
		GeoPoint p2 = new GeoPoint(17385812, 78480667);
		OverlayItem overlayItem2 = new OverlayItem(p2, "Ops!","Hi!");
		myOverlay.addOverlay(overlayItem1);
		myOverlay.addOverlay(overlayItem2);
		overlays.add(myOverlay);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}