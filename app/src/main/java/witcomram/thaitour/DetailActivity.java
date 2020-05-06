package witcomram.thaitour;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {

	Item tour;
	DataSource datasource;
	boolean isMyFaves;
	boolean showMap;
	GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle b = getIntent().getExtras();

		showMap = initMap();

		tour = b.getParcelable(".model.Tour");
		isMyFaves = b.getBoolean("isMyFaves");

		refreshDisplay();

		datasource = new DataSource(this);

	}

	private boolean initMap() {
		if (map == null) {
			MapFragment mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
			map = mapFrag.getMap();
		}
		return (map != null);
	}

	private void refreshDisplay() {

		TextView tv = (TextView) findViewById(R.id.titleText);
		tv.setText(tour.getTitle());
		this.setTitle(tour.getTitle());

		tv = (TextView) findViewById(R.id.cityText);
		tv.setText("จังหวัด"+tour.getCity());

		tv = (TextView) findViewById(R.id.descText);
		tv.setText(tour.getDescription());

		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		int imageResource = getResources().getIdentifier(tour.getImage(), "drawable", getPackageName());
		if (imageResource != 0) {
			iv.setImageResource(imageResource);
		}

		if (showMap){
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(tour.getLatLng(), 10);
			map.moveCamera(update);
			map.addMarker(new MarkerOptions().position(tour.getLatLng()).title(tour.getTitle()).anchor(.5f, .5f).icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_detail, menu);

		menu.findItem(R.id.menu_delete).setVisible(isMyFaves);

		menu.findItem(R.id.menu_add).setVisible(!isMyFaves);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.menu_add:
			if (datasource.addToMyFaves(tour)) {
			}
			break;
		case R.id.menu_delete:
			if (datasource.removeFromMyFaves(tour)) {
				setResult(-1);
				finish();
			}
			break;
		}
			
		return super.onOptionsItemSelected(item);
	}

	protected void onResume() {
		super.onResume();
		datasource.open();
	}

	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}
}
