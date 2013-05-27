package com.mais.leantasks;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AccountManagement extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_connect);
		
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

	Intent intent = null;
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.button_createAccount:
				intent = new Intent(view.getContext(), CreateAccount.class);
				startActivityForResult(intent, 0);
				break;
		
			case R.id.buttonLogin:
				EditText editLogin = (EditText)findViewById(R.id.editTextLogin);
				String login = editLogin.getText().toString();
				
				EditText editPasswd = (EditText)findViewById(R.id.editTextPassword);
				String passwd = editPasswd.getText().toString();
				
				// TODO : link the application with DB using web service
				
				intent = new Intent(view.getContext(), MainActivity.class);
				startActivityForResult(intent, 0);
				
				break;
		}
	}
}
