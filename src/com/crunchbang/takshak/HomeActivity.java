package com.crunchbang.takshak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class HomeActivity extends SlidingActivity implements
		OnItemClickListener {

	public final static String KEY = "com.crunchbang.takshak.key";
	private static String[] menuItems = { "Nozione", "Social Initiative",
			"Events", "Showcase", "Treasure Hunt", "News Feed" };

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
		ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(
				HomeActivity.this, R.layout.slidingmenu_list_row,
				R.id.listItem, menuItems);
		mListView.setAdapter(arrAdapter);
		mListView.setOnItemClickListener(this);

		Crouton.makeText(this, "Swipe left to right for details", Style.INFO)
				.show();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long itemId) {
		String item = (String) parent.getItemAtPosition(position);
		Class<?> cls = null;
		Bundle bundle = new Bundle();

		if (item.equals(menuItems[0])) {
			cls = SocialQuizActivity.class;
			bundle.putString(KEY, "nozione");
		} else if (item.equals(menuItems[1])) {
			cls = SocialQuizActivity.class;
			bundle.putString(KEY, "social");
		} else if (item.equals(menuItems[2])) {
			cls = EventPagerActivity.class;
		} else if (item.equals(menuItems[3])) {
			cls = ShowCaseActivity.class;
		} else if (item.equals(menuItems[4])) {
			cls = WebViewActivity.class;
			bundle.putString(KEY, "treasure");
		} else if (item.equals(menuItems[5])) {
			cls = WebViewActivity.class;
			bundle.putString(KEY, "news");
		} else {
			return;
		}

		Intent intent = new Intent(this, cls);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.home_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Bundle bundle = new Bundle();
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.devs:
			intent = new Intent(this, WebViewActivity.class);
			bundle.putString(KEY, "devs");
			intent.putExtras(bundle);
			startActivity(intent);
			return true;
		case R.id.organizers:
			intent = new Intent(this, WebViewActivity.class);
			bundle.putString(KEY, "organizers");
			intent.putExtras(bundle);
			startActivity(intent);
			return true;
		case R.id.location:
			intent = new Intent(this, LocationActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}