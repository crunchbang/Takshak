package com.crunchbang.takshak;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.crunchbang.takshak.R;
import com.crunchbang.takshak.dbhelper.DataBaseHelper;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

@SuppressLint("DefaultLocale")
public class DetailsActivity extends SherlockActivity implements
		OnClickListener {

	public static String ITEM = "item";

	private DataBaseHelper dbHelper;
	private String programTitle;
	private String contactNumber;

	private TextView title, details, contact, contactNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_layout);

		title = (TextView) findViewById(R.id.title);
		details = (TextView) findViewById(R.id.details);
		contact = (TextView) findViewById(R.id.contact);
		contactNum = (TextView) findViewById(R.id.contact_number);

		dbHelper = new DataBaseHelper(this.getApplicationContext());

		Bundle bundle = getIntent().getExtras();
		programTitle = bundle.getString(ITEM);

		new LoadCursorTask().execute();

		boolean firstDetails = getSharedPreferences("DETAILS_PREF",
				MODE_PRIVATE).getBoolean("firstdetails", true);
		if (firstDetails) {
			Crouton.makeText(this, "Click on the Contact to dial their number",
					Style.INFO).show();
			getSharedPreferences("DETAILS_PREF", MODE_PRIVATE).edit()
					.putBoolean("firstdetails", false).commit();
		}

	}

	private class LoadCursorTask extends AsyncTask<Void, Void, Void> {
		Cursor c;
		int TITLE_INDEX, DESCRIPTION_INDEX, CONTACT_INDEX, NUMBER_INDEX;
		String titleText, descText, contactText;

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
			title.setText(titleText.toUpperCase());
			details.setText(descText);
			contact.setText(contactText);
			contactNum.setText(contactNumber);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		dbHelper.close();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + contactNumber));
		startActivity(intent);

	}

}