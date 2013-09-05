package com.crunchbang.takshak;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class WebViewActivity extends Activity {

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

		String type = getIntent().getExtras().getString(HomeActivity.KEY);
		if (type.equalsIgnoreCase("news")) {
			mWebview.loadUrl("http://takshak13.com/news");
		} else if (type.equalsIgnoreCase("treasure")) {
			mWebview.loadUrl("http://breadcrumbs.takshak13.com/");
		} else if (type.equalsIgnoreCase("devs")) {
			mWebview.loadUrl("file:///android_asset/devs.html");
		} else if (type.equalsIgnoreCase("organizers")) {
			mWebview.loadUrl("file:///android_asset/organizers.html");
		}
	}

	void croutonAlert() {
		Crouton.makeText(this, "Please check your internet connection",
				Style.ALERT).show();
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
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			setContentView(R.layout.news_error);
			croutonAlert();

		}

		@Override
		public void onPageFinished(WebView view, String url) {
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
		}
	}
}
