package com.salehd1979.arabic;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		

		TextView txtViewLetters = (TextView) findViewById(R.id.txtViewLetters);

		txtViewLetters.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view)
				{
					Log.v("Main Activity", "Letters Clicked");
					Intent lettersIntent = new Intent(MainActivity.this, LettersActivity.class);
					startActivity(lettersIntent);

				}

			});

		TextView txtViewNumbers = (TextView) findViewById(R.id.txtViewNumbers);

		txtViewNumbers.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View view)
				{
					Log.v("Main Activity", "Numbers Clicked");
					Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
					startActivity(numbersIntent);

				}
			});

		TextView txtViewAbout = (TextView) findViewById(R.id.txtViewAbout);

		txtViewAbout.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View view)
				{
					Log.v("Main Activity", "About Clicked");
					Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
					startActivity(aboutIntent);

				}
			});
	}
}
