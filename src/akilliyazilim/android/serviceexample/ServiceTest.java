package akilliyazilim.android.serviceexample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceTest extends Activity {

	private TextView textView;
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle bundle = intent.getExtras();
			handleResult(bundle);
		}
		
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_test);
		textView = (TextView) findViewById(R.id.status);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, new IntentFilter(
				DownloadService.NOTIFICATION));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	public void onClick(View view) {

		Intent intent = new Intent(this, DownloadService.class);
		intent.putExtra(DownloadService.FILENAME, "akillirobot.html");
		intent.putExtra(DownloadService.URL,
				"http://www.akilliyazilim.org/androiddersleri/merhaba-akilli-robot.html");
		startService(intent);
		textView.setText("Service baþlatýldý.");
	}
	
	private void handleResult(Bundle bundle) {
		if (bundle != null) {
			String string = bundle.getString(DownloadService.FILEPATH);
			int resultCode = bundle.getInt(DownloadService.RESULT);
			if (resultCode == RESULT_OK) {
				Toast.makeText(ServiceTest.this,
						"Ýndirme iþlemi tamamlandý. Klasör: " + string,
						Toast.LENGTH_LONG).show();
				textView.setText("Ýndirme iþlemi tamamlandý.");
			} else {
				Toast.makeText(ServiceTest.this, "Ýndirme Baþarýsýz.",
						Toast.LENGTH_LONG).show();
				textView.setText("Ýndirme Baþarýsýz.");
			}
		}
	}


}
