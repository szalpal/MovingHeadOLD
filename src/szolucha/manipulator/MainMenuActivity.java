package szolucha.manipulator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main_menu);

		
	}
	
	public void startRecognizeNetworkActivity(View view) {
		startActivity(new Intent(getApplicationContext(), RecognizeNetworkActivity.class));
	}
	
	public void startControlerActivity(View view) {
		startActivity(new Intent(getApplicationContext(), ControlActivity.class));
	}
	
	public void startScreenControlerActivity(View view) {
		startActivity(new Intent(getApplicationContext(), ScreenControlActivity.class));
	}
}
