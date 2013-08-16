package com.crunchbang.takshak;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.TabPageIndicator;

public class EventPagerActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.event_pager_layout);

		boolean firstdb = getSharedPreferences("DB_PREF", MODE_PRIVATE)
				.getBoolean("firstdb", true);
		if (firstdb) {
			Toast.makeText(this, "Initializing Database",
					Toast.LENGTH_LONG).show();
			getSharedPreferences("DB_PREF", MODE_PRIVATE).edit()
					.putBoolean("firstdb", false).commit();
		}

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new DetailsAdapter(this, getSupportFragmentManager()));

		TabPageIndicator tabIndicator = (TabPageIndicator) findViewById(R.id.tabIndicator);
		tabIndicator.setViewPager(pager);
	}

	private class DetailsAdapter extends FragmentPagerAdapter {

		public DetailsAdapter(Context context, FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return EventListFragment
					.newInstance(Constants.eventListShort[position]);
		}

		@Override
		public int getCount() {
			return Constants.eventListShort.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return Constants.eventListShort[position];
		}
	}
}
