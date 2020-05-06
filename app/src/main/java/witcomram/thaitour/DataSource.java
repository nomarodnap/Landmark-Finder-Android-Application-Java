package witcomram.thaitour;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataSource {

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;


	private static final String[] allColumns = { "id","title","description","city","image","choice","latitude","longitude"};

	public DataSource(Context context) {
		dbhelper = new DBOpenHelper(context);
	}

	public void open() {
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		dbhelper.close();
	}

	public Item create(Item tour) {
		ContentValues values = new ContentValues();
		values.put("id", tour.getId());
		values.put("title", tour.getTitle());
		values.put("description", tour.getDescription());
		values.put("city", tour.getCity());
		values.put("image", tour.getImage());
		values.put("choice", tour.getChoice());
		values.put("latitude", tour.getLatitude());
		values.put("longitude", tour.getLongitude());
		long insertid = database.insert("tours", null, values);
		tour.setId(insertid);
		return tour;
	}

	public List<Item> findAll() {

		Cursor cursor = database.query("tours", allColumns, null, null, null, null, null);

		List<Item> tours = cursorToList(cursor);
		return tours;
	}

	public List<Item> findFiltered(String selection) {

		Cursor cursor = database.query("tours", allColumns, selection, null, null, null, null);

		List<Item> tours = cursorToList(cursor);
		return tours;
	}

	private List<Item> cursorToList(Cursor cursor) {
		List<Item> tours = new ArrayList<>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Item tour = new Item();
				tour.setId(cursor.getLong(cursor.getColumnIndex("id")));
				tour.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				tour.setDescription(cursor.getString(cursor.getColumnIndex("description")));
				tour.setImage(cursor.getString(cursor.getColumnIndex("image")));
				tour.setCity(cursor.getString(cursor.getColumnIndex("city")));
				tour.setChoice(cursor.getInt(cursor.getColumnIndex("choice")));
				tour.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
				tour.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
				tours.add(tour);
			}
		}
		return tours;
	}

	public boolean addToMyFaves(Item tour) {
		ContentValues values = new ContentValues();
		values.put("id", tour.getId());
		long result = database.insert("myfaves", null, values);
		return (result != -1);
	}

	public boolean removeFromMyFaves(Item tour) {
		String where = "id = " + tour.getId();
		int result = database.delete("myfaves", where, null);
		return (result == 1);
	}

	public List<Item> findMyFaves() {

		String query = "SELECT tours.* FROM " + "tours JOIN myfaves ON " + "tours.id = myfaves.id";
		Cursor cursor = database.rawQuery(query, null);

		List<Item> tours = cursorToList(cursor);
		return tours;
	}





}
