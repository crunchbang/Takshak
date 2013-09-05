package com.crunchbang.takshak.dbhelper;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DbHelper extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "techfest";
	private static final int DATABASE_VERSION = 7;

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		setForcedUpgradeVersion(7);
	}
}
