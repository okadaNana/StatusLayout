package com.owen.statuslayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

public class StatusLayout extends FrameLayout {

    private Context mContext;

    private FrameLayout mContentViewContainer;
    private FrameLayout mProgressLayoutContainer;
    private FrameLayout mEmptyLayoutContainer;
    private FrameLayout mErrorLayoutContainer;

    private int mProgressLayout;
    private int mEmptyLayout;
    private int mErrorLayout;

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
            mProgressLayout = typedArray.getResourceId(R.styleable.StatusLayout_progressLayout,
                    R.layout.status_layout_progress);
            mEmptyLayout = typedArray.getResourceId(R.styleable.StatusLayout_emptyLayout,
                    R.layout.status_layout_empty);
            mErrorLayout = typedArray.getResourceId(R.styleable.StatusLayout_errorLayout,
                    R.layout.status_layout_error);
        } finally {
            typedArray.recycle();
        }

        LayoutInflater.from(mContext).inflate(R.layout.status_layout, this);
        mContentViewContainer = (FrameLayout) findViewById(R.id.status_content);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (mContentViewContainer == null) {
            super.addView(child, index, params);
            return;
        }

        if (mContentViewContainer.getChildCount() != 0) {
            super.addView(child, index, params);
        } else {
            if (mContentViewContainer == null) {
                super.addView(child, index, params);
            } else {
                mContentViewContainer.addView(child, index, params);
            }
        }
    }

    public void setMode(MODE mode) {
        switch (mode) {
            case PROGRESS:
                setProgressLayoutVisibility(true);
                setEmptyLayoutVisibility(false);
                setContentLayoutVisibility(false);
                setErrorLayoutVisibility(false);
                break;
            case EMPTY:
                setProgressLayoutVisibility(false);
                setEmptyLayoutVisibility(true);
                setContentLayoutVisibility(false);
                setErrorLayoutVisibility(false);
                break;
            case CONTENT:
                setProgressLayoutVisibility(false);
                setEmptyLayoutVisibility(false);
                setContentLayoutVisibility(true);
                setErrorLayoutVisibility(false);
                break;
            case ERROR:
                setProgressLayoutVisibility(false);
                setEmptyLayoutVisibility(false);
                setContentLayoutVisibility(false);
                setErrorLayoutVisibility(true);
                break;
        }
    }

    // visibility

    private void setProgressLayoutVisibility(boolean visible) {
        if (mProgressLayoutContainer == null) {
            if (visible) {
                inflateProgressLayoutContainer();
            }
        } else {
            mProgressLayoutContainer.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    private void setEmptyLayoutVisibility(boolean visible) {
        if (mEmptyLayoutContainer == null) {
            if (visible) {
                inflateEmptyLayoutContainer();
            }
        } else {
            mEmptyLayoutContainer.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    private void setContentLayoutVisibility(boolean visible) {
        mContentViewContainer.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setErrorLayoutVisibility(boolean visible) {
        if (mErrorLayoutContainer == null) {
            if (visible) {
                inflateErrorLayoutContainer();
            }
        } else {
            mErrorLayoutContainer.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    // inflate ViewStub

    private void inflateProgressLayoutContainer() {
        ViewStub viewStub = (ViewStub) findViewById(R.id.viewStub_progress);
        viewStub.inflate();
        mProgressLayoutContainer = (FrameLayout) findViewById(R.id.status_progress);
        LayoutInflater.from(mContext).inflate(mProgressLayout, mProgressLayoutContainer);
        mProgressLayoutContainer.setVisibility(View.VISIBLE);
    }

    private void inflateEmptyLayoutContainer() {
        ViewStub viewStub = (ViewStub) findViewById(R.id.viewStub_empty);
        viewStub.inflate();
        mEmptyLayoutContainer = (FrameLayout) findViewById(R.id.status_empty);
        LayoutInflater.from(mContext).inflate(mEmptyLayout, mEmptyLayoutContainer);
        mEmptyLayoutContainer.setVisibility(View.VISIBLE);
    }

    private void inflateErrorLayoutContainer() {
        ViewStub viewStub = (ViewStub) findViewById(R.id.viewStub_error);
        viewStub.inflate();
        mErrorLayoutContainer = (FrameLayout) findViewById(R.id.status_error);
        LayoutInflater.from(mContext).inflate(mErrorLayout, mErrorLayoutContainer);
        mErrorLayoutContainer.setVisibility(View.VISIBLE);
    }

    // set layout

    public void setProgressLayout(int layoutResId) {
        if (mProgressLayoutContainer == null) {
            inflateProgressLayoutContainer();
        }
        mProgressLayoutContainer.removeAllViewsInLayout();
        LayoutInflater.from(mContext).inflate(layoutResId, mProgressLayoutContainer);
    }

    public void setProgressLayout(View layoutView) {
        if (mProgressLayoutContainer == null) {
            inflateProgressLayoutContainer();
        }
        mProgressLayoutContainer.removeAllViewsInLayout();
        mProgressLayoutContainer.addView(layoutView);
    }

    public void setEmptyLayout(int layoutResId) {
        if (mEmptyLayoutContainer == null) {
           inflateEmptyLayoutContainer();
        }
        mEmptyLayoutContainer.removeAllViewsInLayout();
        LayoutInflater.from(mContext).inflate(layoutResId, mEmptyLayoutContainer);
    }

    public void setEmptyLayout(View layoutView) {
        if (mEmptyLayoutContainer == null) {
            inflateEmptyLayoutContainer();
        }
        mEmptyLayoutContainer.removeAllViewsInLayout();
        mEmptyLayoutContainer.addView(layoutView);
    }

    public void setErrorLayout(int layoutResId) {
        if (mErrorLayoutContainer == null) {
            inflateErrorLayoutContainer();
        }
        mErrorLayoutContainer.removeAllViewsInLayout();
        LayoutInflater.from(mContext).inflate(layoutResId, mErrorLayoutContainer);
    }

    public void setErrorLayout(View layoutView) {
        if (mErrorLayoutContainer == null) {
            inflateErrorLayoutContainer();
        }
        mErrorLayoutContainer.removeAllViewsInLayout();
        mErrorLayoutContainer.addView(layoutView);
    }
}