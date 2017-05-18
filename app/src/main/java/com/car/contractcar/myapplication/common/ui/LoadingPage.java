package com.car.contractcar.myapplication.common.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.car.contractcar.myapplication.R;
import com.car.contractcar.myapplication.common.http.HttpUtil;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * Created by Administrator on 2015/12/14.
 */
public abstract class LoadingPage extends FrameLayout {


    //AsyncHttpClient client = new AsyncHttpClient();

    private static final int PAGE_LOADING_STATE = 1;

    private static final int PAGE_ERROR_STATE = 2;

    private static final int PAGE_EMPTY_STATE = 3;

    private static final int PAGE_SUCCESS_STATE = 4;

    private int PAGE_CURRENT_STATE = 1;

    private View loadingView;

    private View errorView;

    private View emptyView;

    private View successView;

    private LayoutParams lp;

    private ResultState resultState = null;

    private Context mConext;

    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mConext = context;
        init();
    }

    private void init() {
        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (loadingView == null) {
            loadingView = UIUtils.getXmlView(R.layout.page_loading);
            addView(loadingView, lp);
        }
        if (errorView == null) {
            errorView = UIUtils.getXmlView(R.layout.page_error);
            addView(errorView, lp);
        }
        if (emptyView == null) {
            emptyView = UIUtils.getXmlView(R.layout.page_empty);
            addView(emptyView, lp);
        }
        showSafePage();
    }

    private void showSafePage() {
        UIUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }

    private void showPage() {
        loadingView.setVisibility(PAGE_CURRENT_STATE == PAGE_LOADING_STATE ? View.VISIBLE : View.GONE);
        errorView.setVisibility(PAGE_CURRENT_STATE == PAGE_ERROR_STATE ? View.VISIBLE : View.GONE);

        LinearLayout linearLayout = (LinearLayout) errorView.findViewById(R.id.ly_err);
        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSafePage();
                show();
            }
        });

        emptyView.setVisibility(PAGE_CURRENT_STATE == PAGE_EMPTY_STATE ? View.VISIBLE : View.GONE);
        if (successView == null) {
            successView = View.inflate(mConext, LayoutId(), null);
            addView(successView, lp);
        }
        successView.setVisibility(PAGE_CURRENT_STATE == PAGE_SUCCESS_STATE ? View.VISIBLE : View.GONE);
    }

    public abstract int LayoutId();


    public void show() {
        //状态归位
        if (PAGE_CURRENT_STATE != PAGE_LOADING_STATE) {
            PAGE_CURRENT_STATE = PAGE_LOADING_STATE;
        }
        //处理不需要发送请求来决定界面的情况
        String url = url();
        if (TextUtils.isEmpty(url)) {
            resultState = ResultState.SUCCESS;
            resultState.setContent("");
            loadPage();
        } else {
//            HttpUtil.get(url, params(), new AsyncHttpResponseHandler() {
//                @Override
//                public void onSuccess(String content) {
//                    if (TextUtils.isEmpty(content)) {
//                        resultState = ResultState.EMPTY;
//                        resultState.setContent("");
//                    } else {
//                        resultState = ResultState.SUCCESS;
//                        resultState.setContent(content);
//                    }
//                    loadPage();
//                }
//
//                @Override
//                public void onFailure(Throwable error, String content) {
//                    resultState = ResultState.ERROR;
//                    resultState.setContent("");
//                    loadPage();
//                }
//            });
            HttpUtil.get(url, new HttpUtil.callBlack() {
                @Override
                public void succcess(String code) {
                    if (TextUtils.isEmpty(code)) {
                        resultState = ResultState.EMPTY;
                        resultState.setContent("");
                    } else {
                        resultState = ResultState.SUCCESS;
                        resultState.setContent(code);
                    }
                    loadPage();
                }

                @Override
                public void fail(String code) {
                    resultState = ResultState.ERROR;
                    resultState.setContent("");
                    loadPage();
                }

                @Override
                public void err() {
                    resultState = ResultState.ERROR;
                    resultState.setContent("");
                    loadPage();
                }
            }, 0);
        }
    }

    private void loadPage() {
        switch (resultState) {
            case ERROR:
                //当前状态设置为2，显示错误界面
                PAGE_CURRENT_STATE = 2;
                break;
            case EMPTY:
                //当前状态设置为3，显示空界面
                PAGE_CURRENT_STATE = 3;
                break;
            case SUCCESS:
                //当前状态设置为4，显示成功界面
                PAGE_CURRENT_STATE = 4;
                break;
        }
        showSafePage();
        if (PAGE_CURRENT_STATE == 4) {
            OnSuccess(resultState, successView);
        }
    }

    protected abstract void OnSuccess(ResultState resultState, View successView);

    protected abstract RequestParams params();

    protected abstract String url();

    public enum ResultState {

        ERROR(2), EMPTY(3), SUCCESS(4);

        private int state;

        private String content;

        ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "ResultState{" +
                    "state=" + state +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
