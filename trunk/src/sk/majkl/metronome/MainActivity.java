package sk.majkl.metronome;

import sk.majkl.metronome.ui.views.MainView;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		MainView mainView = new MainView(this);
		setContentView(mainView);
	}
}
	