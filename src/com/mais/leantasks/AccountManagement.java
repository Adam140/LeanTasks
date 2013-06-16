package com.mais.leantasks;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AccountManagement extends Activity {
	private boolean hasClickedOnETLogin = false;
	private boolean hasClickedOnETPassword = false;
	private Button button;
	private ProgressBar progressBar;
	private TextView textInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_connect);
		
		button = ((Button) findViewById(R.id.buttonLogin));
		progressBar = ((ProgressBar)findViewById(R.id.progressBarConnect));
				
		final EditText editTextLogin = (EditText)findViewById(R.id.editTextLogin);
		editTextLogin.setOnFocusChangeListener(new OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if(hasFocus && !hasClickedOnETLogin){
		        	hasClickedOnETLogin = true;
		        	editTextLogin.setText("");
		        }
		    }
		});
		
		final EditText editTextPassword = (EditText)findViewById(R.id.editTextPassword);
		editTextPassword.setOnFocusChangeListener(new OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if(hasFocus && !hasClickedOnETPassword){
		        	hasClickedOnETPassword = true;
		        	editTextPassword.setText("");
		        }
		    }
		});
		
		textInfo = (TextView)findViewById(R.id.textViewInfo);
		textInfo.setText("");
		textInfo.setGravity(Gravity.CENTER);
		textInfo.setTextColor(Color.RED);
		
		TextView textViewHello = (TextView)findViewById(R.id.textViewHello); 
		TextView textViewIntroduce = (TextView)findViewById(R.id.textViewIntroduce); 
		TextView textViewNewHere = (TextView)findViewById(R.id.textViewNewHere); 
		
		Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/roboto_light.ttf"); 
		Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto_thin.ttf"); 
		
		textViewHello.setTypeface(robotoThin); 
		textViewIntroduce.setTypeface(robotoLight); 
		textViewNewHere.setTypeface(robotoLight);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		if(hasFocus)
		{
			button.setVisibility(Button.VISIBLE);
			progressBar.setVisibility(ProgressBar.INVISIBLE);
		}
		else
		{
			button.setVisibility(Button.INVISIBLE);
			progressBar.setVisibility(ProgressBar.VISIBLE);
		}
	}

	Intent intent = null;
	public void onClick(View view) {
		switch (view.getId()) {
			//DOESN'T WORK, don't know why, we have to put it in default ...
			/*
			case R.id.button_createAccount:
				intent = new Intent(view.getContext(), CreateAccount.class);
				startActivityForResult(intent, 0);
				break;
			*/
		
			case R.id.buttonLogin:
				EditText editLogin = (EditText)findViewById(R.id.editTextLogin);
				String login = editLogin.getText().toString();
				
				EditText editPasswd = (EditText)findViewById(R.id.editTextPassword);
				String passwd = editPasswd.getText().toString();
				
				// TODO : link the application with DB using web service
				
				intent = new Intent(view.getContext(), MainActivity.class);
				startActivityForResult(intent, 0);
				
				break;
				
			default:
				intent = new Intent(view.getContext(), CreateAccount.class);
				startActivityForResult(intent, 0);
				break;
		}
	}
}
