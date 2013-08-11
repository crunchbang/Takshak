package com.crunchbang.takshak.cardview;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.crunchbang.takshak.R;
import com.crunchbang.takshak.dbhelper.DataBaseHelper;
import com.fima.cardsui.views.CardUI;

public class CardsDetailsActivity extends SherlockActivity {

	public static String ITEM = "item";

	private DataBaseHelper dbHelper;
	private String programTitle;
	private String contactNumber;

	private CardUI mCardUI;
	private MyCard contactCard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_details_layout);

		dbHelper = new DataBaseHelper(this.getApplicationContext());

		// Initialize card view
		mCardUI = (CardUI) findViewById(R.id.cardsView);
		mCardUI.setSwipeable(false);

		Bundle bundle = getIntent().getExtras();
		programTitle = bundle.getString(ITEM);

		// mCardUI.addCard(new MyCard("test"));
		new LoadCursorTask().execute();

		boolean firstDetails = getSharedPreferences("DETAILS_PREF",
				MODE_PRIVATE).getBoolean("firstdetails", true);
		if (firstDetails) {
			Toast.makeText(this, "Click on the Contact to dial their number",
					Toast.LENGTH_LONG).show();
			getSharedPreferences("DETAILS_PREF", MODE_PRIVATE).edit()
					.putBoolean("firstdetails", false).commit();
		}

	}

	private class LoadCursorTask extends AsyncTask<Void, Void, Void> {
		Cursor c;
		String titleText, descText, contactText;
		int TITLE_INDEX, DESCRIPTION_INDEX, CONTACT_INDEX, NUMBER_INDEX;

		@Override
		protected Void doInBackground(Void... arg0) {
			c = dbHelper.getProgram(programTitle);
			// c.getCount();
			c.moveToFirst();
			TITLE_INDEX = c.getColumnIndex(DataBaseHelper.KEY_TITLE);
			DESCRIPTION_INDEX = c
					.getColumnIndex(DataBaseHelper.KEY_DESCRIPTION);
			CONTACT_INDEX = c.getColumnIndex(DataBaseHelper.KEY_CONTACT);
			NUMBER_INDEX = c.getColumnIndex(DataBaseHelper.KEY_NUMBER);

			titleText = c.getString(TITLE_INDEX);
			descText = c.getString(DESCRIPTION_INDEX);
			contactText = c.getString(CONTACT_INDEX);
			contactNumber = c.getString(NUMBER_INDEX);
			c.close();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// title card
			mCardUI.addCard(new MyCard(titleText, descText));
			// contact card
			contactCard = new MyCard("Contact", contactText);
			contactCard.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_DIAL);
					intent.setData(Uri.parse("tel:" + contactNumber));
					startActivity(intent);
				}
			});
			mCardUI.addCard(contactCard);
			mCardUI.refresh();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		dbHelper.close();
	}
}
