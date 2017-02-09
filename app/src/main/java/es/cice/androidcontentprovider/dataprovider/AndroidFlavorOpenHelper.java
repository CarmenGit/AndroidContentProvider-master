package es.cice.androidcontentprovider.dataprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cice on 8/2/17.
 */

public class AndroidFlavorOpenHelper extends SQLiteOpenHelper {

    public static final String ANDROID_FLAVORS_DB="AndroidFlavorsDB";
    public static final String ANDROID_FLAVORS_TABLE="ANDROID_FLAVORS";

    public AndroidFlavorOpenHelper(Context context, int version) {
        super(context, ANDROID_FLAVORS_DB, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table " + ANDROID_FLAVORS_TABLE +"(" +
                AndroidFlavorContract.FLAVORS_CONTENT._ID + " integer primary key autoincrement," +
                AndroidFlavorContract.FLAVORS_CONTENT.NAME_COLUMN + " text not null," +
                AndroidFlavorContract.FLAVORS_CONTENT.DESCRIPTION_COLUMN + " text," +
                AndroidFlavorContract.FLAVORS_CONTENT.IMAGE_ID_COLUMN + " integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion!=oldVersion){
            onCreate(db);
        }

    }
}
