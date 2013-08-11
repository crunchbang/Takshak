package com.crunchbang.takshak;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.crunchbang.takshak.cardview.CardsDetailsActivity;
import com.crunchbang.takshak.dbhelper.DataBaseHelper;

public class ShowCaseActivity extends SherlockListActivity {
	private DataBaseHelper dbHelper;
	private static String CATEGORY = "workshop";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showcase_list_activity);

		dbHelper = new DataBaseHelper(this.getApplicationContext());
		new LoadCursorTask().execute();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Cursor itemCursor = (Cursor) getListAdapter().getItem(position);
		String title = itemCursor.getString(itemCursor
				.getColumnIndex(DataBaseHelper.KEY_TITLE));
		Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
		Bundle args = new Bundle();
		args.putString(CardsDetailsActivity.ITEM, title);

		Intent intent = new Intent(this, CardsDetailsActivity.class);
		intent.putExtras(args);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		((CursorAdapter) getListAdapter()).getCursor().close();
		dbHelper.close();
	}

	private class LoadCursorTask extends AsyncTask<Void, Void, Void> {
		Cursor c;

		@Override
		protected Void doInBackground(Void... arg0) {
			c = dbHelper.getProgramList(CATEGORY, null);
			c.getCount();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			String[] from = { DataBaseHelper.KEY_TITLE,
					DataBaseHelper.KEY_DEPARTMENT };
			int[] to = new int[] { R.id.title, R.id.description };
			/*
			 * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			 * adapter = new XSimpleCursorAdapter(getActivity(),
			 * R.layout.card_picture, c, from, to); } else { adapter = new
			 * XSimpleCursorAdapter(getActivity(), R.layout.card_picture, c,
			 * from, to); }
			 */
			ImageCursorAdapter adapter = new ImageCursorAdapter(getApplicationContext(),
					R.layout.card_picture, c, from, to);
			setListAdapter(adapter);
		}
	}
}
