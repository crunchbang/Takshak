package com.crunchbang.takshak;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crunchbang.takshak.dbhelper.DataBaseHelper;

public class ImageCursorAdapter extends SimpleCursorAdapter {

	private Cursor c;
	private Context context;

	public ImageCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to, 0);
		this.c = c;
		this.context = context;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.card_picture, null);
		}
		this.c.moveToPosition(pos);
		String title = this.c.getString(this.c
				.getColumnIndex(DataBaseHelper.KEY_TITLE));
		String description = this.c.getString(this.c
				.getColumnIndex(DataBaseHelper.KEY_DESCRIPTION));
		byte[] image = this.c.getBlob(this.c
				.getColumnIndex(DataBaseHelper.KEY_PIC));
		ImageView iv = (ImageView) view.findViewById(R.id.des_pic);
		if (image != null) {
			if (image.length > 3) {
				iv.setImageBitmap(BitmapFactory.decodeByteArray(image, 0,
						image.length));
			} else {
				iv.setImageResource(R.drawable.ic_launcher);
			}

		}
		TextView titleView = (TextView) view.findViewById(R.id.title);
		titleView.setText(title);
		TextView descView = (TextView) view.findViewById(R.id.description);
		descView.setText(description);

		return view;
	}
}