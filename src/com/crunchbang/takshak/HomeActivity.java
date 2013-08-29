package com.crunchbang.takshak;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class HomeActivity extends SlidingActivity implements
		OnItemClickListener {

	String[] mMenuList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set the above view
		setContentView(R.layout.home_layout);

		// sliding menu
		setBehindContentView(R.layout.slidingmenu_layout);
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		// populate the menu
		ListView mListView = (ListView) findViewById(R.id.menuListView);
		mMenuList = Constants.menuItems;
		mListView.setAdapter(new ColorAdapter());
		mListView.setOnItemClickListener(this);

		boolean firstboot = getSharedPreferences("BOOT_PREF", MODE_PRIVATE)
				.getBoolean("firstboot", true);
		if (firstboot) {
			Crouton.makeText(this, "Swipe left to right for details",
					Style.INFO).show();
			getSharedPreferences("BOOT_PREF", MODE_PRIVATE).edit()
					.putBoolean("firstboot", false).commit();
		}
		
		//Parse initialization
		Parse.initialize(this, "GR1SGBch2t7Bc9tI0ELWrH9dOD5w8HkgE93ivm1H", "l9lPDWUQWBKrQJxWErogh0BaDd0jfZrOqS4xUdGY"); 
		PushService.setDefaultPushCallback(this, HomeActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long itemId) {
		String item = (String) parent.getItemAtPosition(position);
		Class<?> cls = null;
		Bundle bundle = new Bundle();

		if (item.equals(Constants.menuItems[0])) {
			cls = SocialAboutActivity.class;
			bundle.putString(Constants.KEY, "about");
		} else if (item.equals(Constants.menuItems[1])) {
			cls = SocialAboutActivity.class;
			bundle.putString(Constants.KEY, "social");
		} else if (item.equals(Constants.menuItems[2])) {
			cls = EventPagerActivity.class;
		} else if (item.equals(Constants.menuItems[3])) {
			cls = ShowCaseActivity.class;
		} else if (item.equals(Constants.menuItems[4])) {
			cls = LocationActivity.class;
		} else if (item.equals(Constants.menuItems[5])) {
			cls = NewsFeedActivity.class;
		} else {
			return;
		}

		Intent intent = new Intent(this, cls);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private class ColorAdapter extends ArrayAdapter<String> {

		public ColorAdapter() {
			super(HomeActivity.this, R.layout.slidingmenu_list_row,
					R.id.listItem, mMenuList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = super.getView(position, convertView, parent);
			ImageView iv = (ImageView) row.findViewById(R.id.imageView1);
			iv.setBackgroundColor(Color
					.parseColor(Constants.menuColors[position]));
			return row;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.home_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			Intent intent = new Intent(this, CreditsActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}