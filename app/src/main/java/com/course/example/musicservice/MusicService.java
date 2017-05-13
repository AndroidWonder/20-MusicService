/*
 * Start a service to play music on a background thread.
 * Button click ends service and thread.
 * Manifest entry for service.
 */

package com.course.example.musicservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MusicService extends Activity {
	
	 	TextView txtMsg;
	    Button btnStopService;
	    ComponentName service;
	    Intent intentMyService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        txtMsg = (TextView) findViewById(R.id.txtMsg);        
        
        //start service
        intentMyService = new Intent(this, PlayService.class);
        service = startService(intentMyService);  
        
        txtMsg.setText("Service started");
        
        //stop service when button clicked
        btnStopService = (Button) findViewById(R.id.btnStopService);
        btnStopService.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					stopService(intentMyService);
					txtMsg.setText("Service stopped: \n" + 
							        service.getClassName());
				} catch (Exception e) {
					Toast.makeText(MusicService.this, e.getMessage(), Toast.LENGTH_LONG).show();
				}
				
			}
        	
        });
    }
}