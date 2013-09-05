package com.crunchbang.takshak;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

public class SocialQuizActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String type = getIntent().getExtras().getString(HomeActivity.KEY);

		if (type.equalsIgnoreCase("social")) {
			setContentView(R.layout.social_layout);
		} else if (type.equalsIgnoreCase("nozione")) {
			setContentView(R.layout.nozione_layout);
		}

	}
}
