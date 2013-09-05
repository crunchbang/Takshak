package com.crunchbang.takshak;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.crunchbang.takshak.dbhelper.DataBaseHelper;

public class ShowCaseActivity extends SherlockListActivity {
	private DataBaseHelper dbHelper;
	private static String CATEGORY = "workshop";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);

		dbHelper = new DataBaseHelper(this.getApplicationContext());
		new LoadCursorTask().execute();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Cursor itemCursor = (Cursor) getListAdapter().getItem(position);
		String title = itemCursor.getString(itemCursor
				.getColumnIndex(DataBaseHelper.KEY_TITLE));
		Bundle args = new Bundle();
		args.putString(DetailsActivity.ITEM, title);

		Intent intent = new Intent(this, DetailsActivity.class);
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
					DataBaseHelper.KEY_DESCRIPTION};
			int[] to = new int[] { R.id.title, R.id.description };

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
				ImageCursorAdapter adapter = new ImageCursorAdapter(
						getApplicationContext(), R.layout.card_picture, c,
						from, to);
				setListAdapter(adapter);
			} else {
				SimpleCursorAdapter adapter = new SimpleCursorAdapter(
						getApplicationContext(), R.layout.card_ex, c, from, to, 0);
				setListAdapter(adapter);
			}
		}
	}
}
