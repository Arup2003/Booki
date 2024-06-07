package com.booki.ai.model;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.OverScroller;

import androidx.annotation.Nullable;

import com.booki.ai.activity.BookReadActivity;

public class CustomWebView extends WebView {

    private OverScroller scroller;
    private OnScrollChangedCallback mOnScrollChangedCallback;
    private int lastScrollY;

    public CustomWebView(Context context) {
        super(context);
        init(context);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // Set a WebViewClient to handle URL clicks within the WebView
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        scroller = new OverScroller(context);
        GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                scroller.fling(getScrollX(), getScrollY(), 0, (int) -velocityY, 0, 0, 0, computeVerticalScrollRange());
                invalidate();
                return true;
            }
        });

        // Enable JavaScript if needed
        getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Prevent horizontal scrolling
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (event.getHistorySize() > 0) {
                float historicalX = event.getHistoricalX(0);
                float historicalY = event.getHistoricalY(0);
                if (Math.abs(event.getX() - historicalX) > Math.abs(event.getY() - historicalY)) {
                    return false; // Ignore horizontal scroll events
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        // Prevent horizontal over-scrolling
        if (clampedX) {
            scrollTo(0, scrollY);
        } else {
            super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        }
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        return null;  // Disable text selection action mode
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return null;  // Disable text selection action mode
    }

//    @Override
//    public boolean dispatchActionModeStarted(ActionMode mode) {
//        return true;  // Prevent action mode from starting
//    }

    @Override
    public boolean performAccessibilityAction(int action, @Nullable Bundle arguments) {
        return false;
    }


//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        if (!scroller.isFinished()) {
//            // The user is flinging
//            int scrolledDistance = t - lastScrollY;
//            lastScrollY = t;
//            // Use the scrolled distance
//            System.out.println("Scrolled distance: " + scrolledDistance);
//            BookReadActivity.scrollY+=scrolledDistance;
//        }
//
//    }
//
//    public void fling(int velocityY) {
//        scroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, computeVerticalScrollRange());
//        invalidate();
//    }
//
//    @Override
//    public void computeScroll() {
//        if (scroller.computeScrollOffset()) {
//            scrollTo(scroller.getCurrX(), scroller.getCurrY());
//            postInvalidate();
//        }
//    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l, t);
        }
    }

    public void setOnScrollChangedCallback(OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public interface OnScrollChangedCallback {
        void onScroll(int l, int t);
    }


}

