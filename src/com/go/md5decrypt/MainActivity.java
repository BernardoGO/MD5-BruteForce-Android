package com.go.md5decrypt;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.*;
import android.view.*;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
	Button btn1;
	TextView txt1;
	//TextView txt2;
	EditText box1;
	
	public String Password = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        box1 = (EditText)findViewById(R.id.editText1);
        btn1 = (Button)findViewById(R.id.button1);
        txt1 = (TextView)findViewById(R.id.textView1);
        //txt2 = (TextView)findViewById(R.id.textView2);
        
        //

        btn1.setOnClickListener(new View.OnClickListener() {
			
			 

			@Override
			public void onClick(View arg0) {
				
				 
				Password = box1.getText().toString().toLowerCase();
			
					   
	
				new DownloadImageTask().execute();
				
				// TODO Auto-generated method stub
				
			}
		});
        
         
    }
    
    
    
    public static String md5(String senha){  
        String sen = "";  
        MessageDigest md = null;  
        try {  
            md = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
        sen = hash.toString(16);              
        return sen;  
    }  
    
    
    public class DownloadImageTask extends AsyncTask<Void, String, String> {
        protected String doInBackground(Void... Url) {
        	
        	String STRCharset = "";
        	String password = Password; //"bac82a9993c15f257413a6c3def4bc4a";
        	
        	STRCharset += "!@#$%&*";
        	STRCharset += "0123456789";
        	STRCharset += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        	STRCharset += "abcdefghijklmnopqrstuvwxyz";
        	
			char[] charset = STRCharset.toCharArray();
			java.util.Arrays.sort(charset);
			Bruteforce bf = new Bruteforce(charset, 1);

			String attempt = bf.toString();
			while (true) {
				if (md5(attempt).equals(password.toLowerCase())) { 
					return ("Found: " + attempt);
					//break;
				}
				attempt = bf.toString();
				//txt1.setText("Tried: " + attempt);
				publishProgress(attempt);
				bf.increment();
			}
        	
            //return "Err";
        }

       
        @Override
        protected void onProgressUpdate(String... values) {
            txt1.setText(values[0]);
            super.onProgressUpdate(values);
        }
        
        protected void onPostExecute(String result) {
            txt1.setText(result);
        }
    }
   
    

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	private void onMarketLaunch(String url) {
		
		Intent donate = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(
				"market://details?id=%s", url)));
		startActivity(donate);
	}
	
	private void onDevLaunch(String url) {
		
		Intent donate = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(
				"market://search?q=pub:%s", url)));
		startActivity(donate);
	}
	
	public void share()
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.go.docgenval");
		startActivity(Intent.createChooser(intent, "Share with"));
	}
	
	private void showHelp()
	{
/*
        final String message = this.getString(R.string.help);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    	builder.setTitle("SMS Combo  -  Help");
    	builder.setMessage(message);
    	builder.setPositiveButton("Back", null);
    	AlertDialog dialog = builder.show();
	*/
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_rate:    onMarketLaunch("com.go.docgenval");
	                            break;
	                            //https://play.google.com/store/apps/developer?id=Godinho
	        //case R.id.menu_help:    showHelp();
            //break;
            
	        case R.id.menu_moreapps:	onDevLaunch("Ranebord");
	        break;              
	                            
	        case R.id.menu_share:	share();
	        break;

	    }
	    return true;
	}
}

