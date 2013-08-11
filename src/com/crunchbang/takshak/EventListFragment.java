package com.crunchbang.takshak;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.crunchbang.takshak.cardview.CardsDetailsActivity;
import com.crunchbang.takshak.dbhelper.DataBaseHelper;

public class EventListFragment extends SherlockListFragment {

	private static final String EVENT_RESOURCES = "eventresources";
	private String positionItem = null;
	private DataBaseHelper dbHelper;
	private static String CATEGORY = "event";

	public static Fragment newInstance(String item) {
		EventListFragment frag = new EventListFragment();

		Bundle args = new Bundle();
		args.putString(EVENT_RESOURCES, item);
		frag.setArguments(args);

		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dbHelper = new DataBaseHelper(getActivity().getApplicationContext());
		if (getArguments() != null) {
			positionItem = getArguments().getString(EVENT_RESOURCES);
		}
		new LoadCursorTask().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_details, container,
				false);
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Cursor itemCursor = (Cursor) getListAdapter().getItem(position);
		String title = itemCursor.getString(itemCursor
				.getColumnIndex(DataBaseHelper.KEY_TITLE));
		Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
		Bundle args = new Bundle();
		args.putString(CardsDetailsActivity.ITEM, title);

		Intent intent = new Intent(getActivity(), CardsDetailsActivity.class);
		intent.putExtras(args);
		startActivity(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		((CursorAdapter) getListAdapter()).getCursor().close();
		dbHelper.close();
	}

	private class LoadCursorTask extends AsyncTask<Void, Void, Void> {
		Cursor c;

		@Override
		protected Void doInBackground(Void... arg0) {
			c = dbHelper.getProgramList(CATEGORY, positionItem);
			// c.getCount();
			c.moveToFirst();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			String[] from = new String[] { DataBaseHelper.KEY_TITLE,
					DataBaseHelper.KEY_DESCRIPTION };
			int[] to = new int[] { R.id.title, R.id.description };
			/*
			 * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			 * adapter = new XSimpleCursorAdapter(getActivity(),
			 * R.layout.card_picture, c, from, to); } else { adapter = new
			 * XSimpleCursorAdapter(getActivity(), R.layout.card_picture, c,
			 * from, to); }
			 */
			ImageCursorAdapter adapter = new ImageCursorAdapter(getActivity(),
					R.layout.card_picture, c, from, to);
			setListAdapter(adapter);
		}
	}
}