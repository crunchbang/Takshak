package com.crunchbang.takshak;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.crunchbang.takshak.cardview.MyCard;
import com.fima.cardsui.views.CardUI;

public class SocialAboutActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_details_layout);
		String type = getIntent().getExtras().getString(Constants.KEY);

		CardUI mCardUI = (CardUI) findViewById(R.id.cardsView);
		mCardUI.setSwipeable(false);

		if (type.equalsIgnoreCase("about")) {
			MyCard cardTop, cardBottom;
			cardTop = new MyCard(getString(R.string.mace),
					getString(R.string.mace_des));
			cardBottom = new MyCard(getString(R.string.t_title),
					getString(R.string.takshak_des));
			mCardUI.addCard(cardBottom);
			mCardUI.addCardToLastStack(cardTop);
		} else {
			MyCard cardTop;
			MyCard cardBottom;
			cardTop = new MyCard("Education for all",
					getString(R.string.education));
			cardBottom = new MyCard("Rebuilding Uttarakhand",
					getString(R.string.flood));
			mCardUI.addCard(cardBottom);
			mCardUI.addCard(cardTop);
		}

		mCardUI.refresh();
	}
}
