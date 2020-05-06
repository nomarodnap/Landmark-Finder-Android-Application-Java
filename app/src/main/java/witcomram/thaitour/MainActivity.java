package witcomram.thaitour;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private SharedPreferences settings;
	private OnSharedPreferenceChangeListener listener;
	private List<Item> tours;
	boolean isMyFaves;

	DataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.setTitle(getResources().getString(R.string.app_name));

		settings = PreferenceManager.getDefaultSharedPreferences(this);

		listener = new OnSharedPreferenceChangeListener() {

			@Override
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
					String key) {
				MainActivity.this.refreshDisplay();
			}
		};
		settings.registerOnSharedPreferenceChangeListener(listener);

		datasource = new DataSource(this);
		datasource.open();


		tours = datasource.findAll();
		if (tours.size() == 0) {
			createData();
			tours = datasource.findAll();
		}
		isMyFaves = false;

		refreshDisplay();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_filter:
				ChoiceList myList = new ChoiceList();
				myList.show(getFragmentManager(), "myList");
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void refreshDisplay() {
		ArrayAdapter<Item> adapter = new ListAdapter(this, tours);
		ListView listView = (ListView) findViewById(android.R.id.list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Item tour = tours.get(position);
				displayDetail(tour);
			}
		});
	}

	public void displayDetail(Item tour) {

		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(".model.Tour", tour);
		intent.putExtra("isMyFaves", isMyFaves);

		startActivityForResult(intent, 9999);
	}

	private void createData() {
		RawPullParser parser = new RawPullParser();
		List<Item> tours = parser.parseXML(this);

		for (Item tour : tours) {
			datasource.create(tour);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 9999 && resultCode == -1) {
			datasource.open();
			tours = datasource.findMyFaves();
			refreshDisplay();
			isMyFaves = true;
		}
	}

	public void setChoice(String selection){
		TextView textView = (TextView)findViewById(R.id.txtOutput);

		switch(selection) {
			case "แสดงทั้งหมด":
				tours = datasource.findAll();
				refreshDisplay();
				isMyFaves = false;
				textView.setText(selection);
				break;
			case "วัด โบราณสถาน และประวัติศาสตร์":
				tours = datasource.findFiltered("choice = 1");
				refreshDisplay();
				isMyFaves = false;
				textView.setText(selection);
				break;
			case "ป่าเขา น้ำตก":
				tours = datasource.findFiltered("choice = 2");
				refreshDisplay();
				isMyFaves = false;
				textView.setText(selection);
				break;
			case "ทะเล เกาะ และหมู่เกาะ":
				tours = datasource.findFiltered("choice = 3");
				refreshDisplay();
				isMyFaves = false;
				textView.setText(selection);
				break;
			case "กิจกรรม งานวัฒนธรรม ประเพณี":
				tours = datasource.findFiltered("choice = 4");
				refreshDisplay();
				isMyFaves = false;
				textView.setText(selection);
				break;
			case "ชุมชนวิถีชีวิต":
				tours = datasource.findFiltered("choice = 5");
				refreshDisplay();
				isMyFaves = false;
				textView.setText(selection);
				break;
			case "ที่คุณชื่นชอบ":
				tours = datasource.findMyFaves();
				refreshDisplay();
				isMyFaves = true;
				textView.setText(selection);
				break;
			case "null":
				break;
			default:
				break;
		}

	}
	
}


