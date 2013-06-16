package com.mais.leantasks;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mais.leantasks.asyncTask.RegisterTask;
import com.mais.leantasks.http.WebAPI;

public class CreateAccount extends Activity{
	
	private TextView textInfo;
	private ProgressBar progressBar;
	private boolean hasClickedOnETLogin = false;
	private boolean hasClickedOnETPassword1 = false;
	private boolean hasClickedOnETPassword2 = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_create_account);
		
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
		
		final EditText editTextPassword1 = (EditText)findViewById(R.id.editTextPassword1);
		editTextPassword1.setOnFocusChangeListener(new OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if(hasFocus && !hasClickedOnETPassword1){
		        	hasClickedOnETPassword1 = true;
		        	editTextPassword1.setText("");
		        }
		    }
		});
		
		final EditText editTextPassword2 = (EditText)findViewById(R.id.editTextPassword2);
		editTextPassword2.setOnFocusChangeListener(new OnFocusChangeListener() {
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if(hasFocus && !hasClickedOnETPassword2){
		        	hasClickedOnETPassword2 = true;
		        	editTextPassword2.setText("");
		        }
		    }
		});
		
		progressBar = (ProgressBar)findViewById(R.id.progressBarConnect);
		
		textInfo = (TextView)findViewById(R.id.editTextInfo);
		textInfo.setText("");
		textInfo.setGravity(Gravity.CENTER);
		textInfo.setTextColor(Color.RED);
		
		TextView textViewNewAccount = (TextView)findViewById(R.id.textViewNewAccount); 
		
		Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto_thin.ttf"); 
		
		textViewNewAccount.setTypeface(robotoThin);
	}

	public void onClick(View view) {
		switch (view.getId()) {				
			case R.id.buttonCreateAccount:
				textInfo.setText("");
				
				if(hasClickedOnETPassword1 && hasClickedOnETPassword2 && hasClickedOnETLogin)
				// the user has to put passwords & login first
				{
					// TODO : link with the web service for user creation into the DB
					if(!WebAPI.isNetworkAvailable(this))
						textInfo.setText("Turn on your network connection");
					else
					{
						String login = ((EditText) findViewById(R.id.editTextLogin)).getText().toString();
						String password1 = ((EditText) findViewById(R.id.editTextPassword1)).getText().toString();
						String password2 = ((EditText) findViewById(R.id.editTextPassword2)).getText().toString();
						
						if(password1.compareTo(password2) == 0)
						{
							RegisterTask register = new RegisterTask(progressBar, textInfo, this);
							register.execute(login, password1);
						}
						else
						{
							textInfo.setText("Passwords don't match");
						}
					}
				}
				else
				{
					textInfo.setText("You have to put your login & passwords first ...");
				}
				break;
		}
	}
	
}
