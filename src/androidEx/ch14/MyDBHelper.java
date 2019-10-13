package androidEx.ch14;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "Bookkeeping";
	private static final int DATABASE_VERSION = 1;
	public MyDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE cost "  + "( "  + 
				"Date text no null, " + 
				"Name text no null, Money real no null, IO text no null)");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Cost");
        onCreate(db);
	}
}
