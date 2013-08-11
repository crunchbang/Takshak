package com.crunchbang.takshak;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsFeedActivity extends Activity {

	private WebView mWebview;
	private ProgressDialog mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mWebview = new WebView(this);
		mWebview.getSettings().setRenderPriority(RenderPriority.HIGH);
		mWebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		setContentView(mWebview);
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Loading...");
		mProgressDialog.show();
		mWebview.setWebViewClient(new MyWebViewClient());
		mWebview.loadUrl("http://takshak13.com/news");
	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			if (!mProgressDialog.isShowing()) {
				mProgressDialog.show();
			}
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
		}
	}
}
