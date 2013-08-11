package com.crunchbang.takshak;

import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

import com.actionbarsherlock.app.SherlockActivity;

public class CreditsActivity extends SherlockActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		WebView mWebview = new WebView(this);
		mWebview.getSettings().setJavaScriptEnabled(true); 
		setContentView(mWebview);
		mWebview.loadUrl("file:///android_asset/about.html");
	}

}
