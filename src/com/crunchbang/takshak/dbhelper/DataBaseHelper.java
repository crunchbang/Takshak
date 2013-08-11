package com.crunchbang.takshak.dbhelper;

import android.content.Context;
import android.database.Cursor;

public class DataBaseHelper extends DbHelper {

	public static final String TABLE_PROGRAMS = "programs";

	public static final String KEY_ID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_DEPARTMENT = "department";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_CONTACT = "contact";
	public static final String KEY_NUMBER = "number";
	public static final String KEY_PIC = "pic";

	private static DbHelper mDataBase;

	public DataBaseHelper(Context context) {
		super(context);
		mDataBase = new DbHelper(context);
	}

	public Cursor getProgramList(String category, String department) {
		String[] projections = new String[] { KEY_ID, KEY_TITLE, KEY_DESCRIPTION, KEY_CATEGORY,
				KEY_DEPARTMENT, KEY_PIC };
		String where = null;
		if (category == "event") {
			where = KEY_CATEGORY + "=" + "'" + category + "'" + " AND "
					+ KEY_DEPARTMENT + "=" + "'" + department + "'";
		} else {
			where = KEY_CATEGORY + "=" + "'" + category + "'";
		}
		return mDataBase.getReadableDatabase().query(TABLE_PROGRAMS,
				projections, where, null, null, null, null);
	}

	public Cursor getProgram(String title) {
		String[] projections = new String[] { KEY_ID, KEY_TITLE,
				KEY_DESCRIPTION, KEY_CONTACT, KEY_NUMBER };
		String where = KEY_TITLE + "=" + "'" + title + "'";
		return mDataBase.getReadableDatabase().query(TABLE_PROGRAMS,
				projections, where, null, null, null, null);
	}

	public void close() {
		mDataBase.close();
	}
}