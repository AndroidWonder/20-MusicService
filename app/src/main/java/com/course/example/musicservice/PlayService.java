/*
 * Create a background thread to play music.
 * Notice Manifest entry for service.
 * Be sure music file is loaded into Android memory.
 */

package com.course.example.musicservice;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import java.io.IOException;

public class PlayService extends Service {
	
	private final static String tag = "PlayService";
	private Thread music = null;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i (tag, "Service created");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);	  
		//create and start thread
		music = new Thread(background);
		music.start();
		Log.i (tag, "Service started");
		return Service.START_NOT_STICKY;
	}
	@Override 
	public void onDestroy() {
		super.onDestroy();
		music = null; //terminate thread
		Log.i (tag, "Service destroyed");
	}
	
	Runnable background = new Runnable() {

		public void run(){
			MediaPlayer mp = new MediaPlayer();
			mp.setAudioAttributes(
					new AudioAttributes
							.Builder()
							.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
							.build());

				try {
					//select BBC stream
					mp.setDataSource("http://vprbbc.streamguys.net:80/vprbbc24.mp3");
				mp.prepare();
				mp.start();
				} catch (IOException e) {};
				
				//check every second if it's time to stop music
				while (music != null) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {e.printStackTrace();}
			    }
				mp.stop();
				mp.release();
		}
	}; 
	   
}