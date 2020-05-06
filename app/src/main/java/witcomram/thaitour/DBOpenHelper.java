package witcomram.thaitour;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {
        super(context, "thaitour.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tours (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, city TEXT, image TEXT, choice NUMERIC, latitude NUMERIC, longitude NUMERIC)");
        db.execSQL("CREATE TABLE myfaves (id INTEGER PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tours");
        db.execSQL("DROP TABLE IF EXISTS myfaves");
        onCreate(db);
    }
}