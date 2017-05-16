package com.zll.xunyiwenyao.util;
import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewUtils{
	/**
	 * ������item������listview�ĸ߶�
	 * @param listView
	 */
	public static List<View> setListViewHeightBasedOnChildren(ListView listView) {
		List<View> view_lt = new ArrayList<View>();
		
	    ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter == null)
	        return null;

	    int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
	    int totalHeight = 0;
	    View view = null;
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        view = listAdapter.getView(i, view, listView);
	        if (i == 0)
	            view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));

	        view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
	        totalHeight += view.getMeasuredHeight();
	        
	        ////rxz
	        view_lt.add(view);
	    }
	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	    listView.setLayoutParams(params);
	    
	    return view_lt;
	}
}