package nl.s5630213023.healthcareproject.HeartRate.exercise;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class Exercise extends ContentProvider {
    MyDatabase mydb;
    class MyDatabase extends SQLiteOpenHelper {

        public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE exercise (exercise_id INTEGER PRIMARY KEY AUTOINCREMENT,Date TEXT, Time TEXT, Timer INTEGER,Type TEXT )";
            db.execSQL(sql);
            Log.d("CREATE TABLE", "Create Table Successfully.");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXITS exercise");
            onCreate(db);
            Log.d("ExerCiseDB", "table upgraded");
        }
    }
    public Exercise() {
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mydb.getWritableDatabase();
        int row = db.delete("exercise", selection, selectionArgs);
        Log.d("ExerCiseDB", "delete completed");
        return  row;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mydb.getWritableDatabase();
        long rowid = db.insert("exercise", null,values);
        Uri nuri = ContentUris.withAppendedId(uri, rowid);
        Log.d("ExerCiseDB", "insert completed");
        return nuri;
    }

    @Override
    public boolean onCreate() {
        mydb = new MyDatabase(getContext(), "exercise", null, 1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mydb.getReadableDatabase();
        Cursor c = db.query("exercise", projection, selection, selectionArgs, null, null, sortOrder);
        Log.d("ExerCiseDB", "query completed");
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mydb.getWritableDatabase();
        int row = db.update("pressure", values, selection, selectionArgs);
        Log.d("ExerCiseDB", "update completed");
        return row;
    }
}
