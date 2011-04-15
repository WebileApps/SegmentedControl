package com.webileapps.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.webileapps.segmentedControl.R;

public class SegmentedControl extends RadioGroup {
	
//	private static final String TAG = "SegmentedControl";

	public SegmentedControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SegmentedControl(Context context) {
		super(context);
		init();
	}
    private PassThroughHierarchyChangeListener mPassThroughListener;
	private void init() {
		this.mChildOnCheckedChangeListener = new CheckedStateTracker();
        mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(mPassThroughListener);
	}
	
    private class PassThroughHierarchyChangeListener implements
    ViewGroup.OnHierarchyChangeListener {

		/**
		 * {@inheritDoc}
		 */
		public void onChildViewAdded(View parent, View child) {
			if (parent == SegmentedControl.this && child instanceof RadioButton) {
				((RadioButton) child).setOnCheckedChangeListener(mChildOnCheckedChangeListener);
				redrawChildrenBackgrounds();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void onChildViewRemoved(View parent, View child) {
			if (parent == SegmentedControl.this && child instanceof RadioButton) {
				((RadioButton) child).setOnCheckedChangeListener(null);
				redrawChildrenBackgrounds();
			}
		}
	}
    
    private void redrawChildrenBackgrounds() {
    	int count = SegmentedControl.this.getChildCount();
    	
		if(count == 0) return;
		if(count == 1) {
			View childView = SegmentedControl.this.getChildAt(0);
			boolean isChecked = ((RadioButton)childView).isChecked();
			if(isChecked)
				childView.setBackgroundResource(R.drawable.single_selected);
			else
				childView.setBackgroundResource(R.drawable.single_selected);
			return;
		}
		for(int position = 0; position< count; position++) {
			View childView = SegmentedControl.this.getChildAt(position);
			boolean isChecked = ((RadioButton)childView).isChecked();
			if(position == 0) {
				if(isChecked)
					childView.setBackgroundResource(R.drawable.left_selected);
				else
					childView.setBackgroundResource(R.drawable.left_unselected);
			} else if (position == count-1) {
				if(isChecked)
					childView.setBackgroundResource(R.drawable.right_selected);
				else
					childView.setBackgroundResource(R.drawable.right_unselected);
			} else {
				if(isChecked)
					childView.setBackgroundResource(R.drawable.middle_selected);
				else
					childView.setBackgroundResource(R.drawable.middle_unselected);
			}
		}
    }

    private CheckedStateTracker mChildOnCheckedChangeListener;
    private class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener {
    	@Override
    	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			redrawChildrenBackgrounds();
    	}
    }
}