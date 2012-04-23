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


import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CustomOverlay extends ItemizedOverlay<OverlayItem> {
	
	private Context ctx;
	private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
	
	public void addOverlay(OverlayItem overlay) {
		overlays.add(overlay);
		populate();
	}

	public CustomOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		ctx = context;
	}

	
	@Override
	protected boolean onTap(int index) {
		//when clicked, a dialog is displayed containing content from the item clicked
		OverlayItem oItem = overlays.get(index);
		new AlertDialog.Builder(ctx). setTitle(oItem.getTitle()).setMessage(oItem.getSnippet()).show();
		return true;
	}
	

	@Override
	protected OverlayItem createItem(int i) {
		return overlays.get(i);
	}
	
	@Override
	public int size() {
		return overlays.size();
	}


}