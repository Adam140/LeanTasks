package com.mais.leantasks;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CreateAccount extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_create_account);
		
		EditText editInfo = (EditText)findViewById(R.id.editTextInfo);
		editInfo.setFocusable(false);
		editInfo.setText("");
		editInfo.setGravity(Gravity.CENTER);
		editInfo.setTextColor(Color.RED);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view) {
		switch (view.getId()) {				
			case R.id.buttonCreateAccount:
				// TODO : link with the web service for user creation into the DB
				
				this.finish(); // finish the activity
				break;
		}
	}
}
