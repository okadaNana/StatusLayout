package com.owen.statuslayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class StatusLayout extends FrameLayout {

	private Context mContext;

	private FrameLayout mContentViewContainer;
	private FrameLayout mProgressLayoutContainer;
	private FrameLayout mEmptyLayoutContainer;
	private FrameLayout mErrorLayoutContainer;

	public enum MODE {
		PROGRESS,
		EMPTY,
		CONTENT,
		ERROR
	}

	public StatusLayout(Context context) {
		super(context);
		init(context, null, 0);
	}

	public StatusLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		mContext = context;

		TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.StatusLayout);
		try {
			int progressLayout = typedArray.getResourceId(R.styleable.StatusLayout_progressLayout,
					R.layout.status_layout_progress);
			int emptyLayout = typedArray.getResourceId(R.styleable.StatusLayout_emptyLayout,
					R.layout.status_layout_empty);
			int errorLayout = typedArray.getResourceId(R.styleable.StatusLayout_errorLayout,
					R.layout.status_layout_error);

			initLayoutContainer();
			addViewToContainer(progressLayout, emptyLayout, errorLayout);
		} finally {
			typedArray.recycle();
		}
	}

	private void initLayoutContainer() {
		LayoutInflater.from(mContext).inflate(R.layout.status_layout, this);
		mContentViewContainer = (FrameLayout) findViewById(R.id.status_content);
		mProgressLayoutContainer = (FrameLayout) findViewById(R.id.status_progress);
		mEmptyLayoutContainer = (FrameLayout) findViewById(R.id.status_empty);
		mErrorLayoutContainer = (FrameLayout) findViewById(R.id.status_error);
	}

	private void addViewToContainer(int progressLayout, int emptyLayout, int errorLayout) {
		LayoutInflater.from(mContext).inflate(progressLayout, mProgressLayoutContainer);
		LayoutInflater.from(mContext).inflate(emptyLayout, mEmptyLayoutContainer);
		LayoutInflater.from(mContext).inflate(errorLayout, mErrorLayoutContainer);
	}

	/**
	 * StatusLayout have four sub layout: <br/>
	 * 1) progress layout <br/> 
	 * 2) content layout <br/>
	 * 3) empty layout <br/>
	 * 4) error layout <br/>
	 * 
	 * override this method to add all the views into content layout.
	 */
	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		if (mContentViewContainer == null) {
			super.addView(child, index, params);
		} else {
			mContentViewContainer.addView(child, index, params);
		}
	}

	public void setMode(MODE mode) {
		switch (mode) {
		case PROGRESS:
			mProgressLayoutContainer.setVisibility(View.VISIBLE);
			mContentViewContainer.setVisibility(View.GONE);
			mEmptyLayoutContainer.setVisibility(View.GONE);
			mErrorLayoutContainer.setVisibility(View.GONE);
			break;
		case EMPTY:
			mProgressLayoutContainer.setVisibility(View.GONE);
			mEmptyLayoutContainer.setVisibility(View.VISIBLE);
			mContentViewContainer.setVisibility(View.GONE);
			mErrorLayoutContainer.setVisibility(View.GONE);
			break;
		case CONTENT:
			mProgressLayoutContainer.setVisibility(View.GONE);
			mEmptyLayoutContainer.setVisibility(View.GONE);
			mContentViewContainer.setVisibility(View.VISIBLE);
			mErrorLayoutContainer.setVisibility(View.GONE);
			break;
		case ERROR:
			mProgressLayoutContainer.setVisibility(View.GONE);
			mEmptyLayoutContainer.setVisibility(View.GONE);
			mContentViewContainer.setVisibility(View.GONE);
			mErrorLayoutContainer.setVisibility(View.VISIBLE);
			break;
		}
	}

	public void setProgressLayout(int layoutResId) {
		mProgressLayoutContainer.removeAllViewsInLayout();
		LayoutInflater.from(mContext).inflate(layoutResId, mProgressLayoutContainer);
	}

	public void setProgressLayout(View layoutView) {
		mProgressLayoutContainer.removeAllViewsInLayout();
		mProgressLayoutContainer.addView(layoutView);
	}

	public void setEmptyLayout(int layoutResId) {
		mEmptyLayoutContainer.removeAllViewsInLayout();
		LayoutInflater.from(mContext).inflate(layoutResId, mEmptyLayoutContainer);
	}

	public void setEmptyLayout(View layoutView) {
		mEmptyLayoutContainer.removeAllViewsInLayout();
		mEmptyLayoutContainer.addView(layoutView);
	}
	
	public void setErrorLayout(int layoutResId) {
		mErrorLayoutContainer.removeAllViewsInLayout();
		LayoutInflater.from(mContext).inflate(layoutResId, mErrorLayoutContainer);
	}
	
	public void setErrorLayout(View layoutView) {
		mErrorLayoutContainer.removeAllViewsInLayout();
		mErrorLayoutContainer.addView(layoutView);
	}
}