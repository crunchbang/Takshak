package com.crunchbang.takshak;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

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

		// Efficient bitmap loading from dev.android.com
		ImageView mImageView = (ImageView) findViewById(R.id.imageView1);
		mImageView.setImageBitmap(decodeSampledBitmapFromResource(
				getResources(), R.drawable.college, 800, 800));

		boolean firstboot = getSharedPreferences("BOOT_PREF", MODE_PRIVATE)
				.getBoolean("firstboot", true);
		if (firstboot) {
			Toast.makeText(this, "Swipe left to right for details",
					Toast.LENGTH_LONG).show();
			getSharedPreferences("BOOT_PREF", MODE_PRIVATE).edit()
					.putBoolean("firstboot", false).commit();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long itemId) {
		String item = (String) parent.getItemAtPosition(position);
		Class<?> cls = null;

		if (item.equals(Constants.menuItems[0])) {
			cls = AboutActivity.class;
		} else if (item.equals(Constants.menuItems[1])) {
			cls = EventPagerActivity.class;
		} else if (item.equals(Constants.menuItems[2])) {
			cls = ShowCaseActivity.class;
		} else if (item.equals(Constants.menuItems[3])) {
			cls = LocationActivity.class;
		} else if (item.equals(Constants.menuItems[4])) {
			cls = NewsFeedActivity.class;
		} else {
			return;
		}

		Intent intent = new Intent(this, cls);
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

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}
}