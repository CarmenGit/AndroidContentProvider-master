package es.cice.androidcontentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;

import es.cice.androidcontentprovider.dataprovider.AndroidFlavorContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Flavor[] flavors = {
                new Flavor("Cupcake", "The first release of Android", R.drawable.cupcake),
                new Flavor("Donut", "The world's information is at your fingertips – " +
                        "search the web, get driving directions... or just watch cat videos.",
                        R.drawable.donut),
                new Flavor("Eclair", "Make your home screen just how you want it. Arrange apps " +
                        "and widgets across multiple screens and in folders. Stunning live wallpapers " +
                        "respond to your touch.", R.drawable.eclair),
                new Flavor("Froyo", "Voice Typing lets you input text, and Voice Actions let " +
                        "you control your phone, just by speaking.", R.drawable.froyo),
                new Flavor("GingerBread", "New sensors make Android great for gaming - so " +
                        "you can touch, tap, tilt, and play away.", R.drawable.gingerbread),
                new Flavor("Honeycomb", "Optimized for tablets, this release opens up new " +
                        "horizons wherever you are.", R.drawable.honeycomb),
                new Flavor("Ice Cream Sandwich", "Android comes of age with a new, refined design. " +
                        "Simple, beautiful and beyond smart.", R.drawable.icecream),
                new Flavor("Jelly Bean", "Android is fast and smooth with buttery graphics. " +
                        "With Google Now, you get just the right information at the right time.",
                        R.drawable.jellybean),
                new Flavor("KitKat", "Smart, simple, and truly yours. A more polished design, " +
                        "improved performance, and new features.", R.drawable.kitkat),
                new Flavor("Lollipop", "A sweet new take on Android. Get the smarts of Android on" +
                        " screens big and small – with the right information at the right moment.",
                        R.drawable.lollipop)};

        insertData(flavors);


        //si queremos los nombres y descripciones del content provider
        Cursor cursor=getContentResolver().query(AndroidFlavorContract.ANDROID_FLAVOR_CONTENT,
                new String[]{
                        AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN,
                        AndroidFlavorContract.FLAVORS_CONTENT.NAME_COLUMN,
                        AndroidFlavorContract.FLAVORS_CONTENT.IMAGE_ID_COLUMN},
                null,null,null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.grid_cell, cursor,
                new String[]{AndroidFlavorContract.FLAVORS_CONTENT.NAME_COLUMN,AndroidFlavorContract.FLAVORS_CONTENT.IMAGE_ID_COLUMN},
                new int[]{R.id.androidFlavorNameTV, R.id.androidFlavorImageIV},0);
        GridView flavorGV=(GridView)findViewById(R.id.flavors_grid);
        flavorGV.setAdapter(adapter);

        flavorGV.setAdapter(adapter);

    }

    private void insertData(Flavor[] flavors) {

        ContentValues[] values = new ContentValues[flavors.length];
        for (int i = 0; i < flavors.length; i++) {
            ContentValues cv = new ContentValues();
            cv.put(AndroidFlavorContract.FLAVORS_CONTENT.NAME_COLUMN, flavors[i].name);
            cv.put(AndroidFlavorContract.FLAVORS_CONTENT.DESCRIPTION_COLUMN, flavors[i].description);
            cv.put(AndroidFlavorContract.FLAVORS_CONTENT.IMAGE_ID_COLUMN, flavors[i].image);
            
        }
        getContentResolver().bulkInsert(AndroidFlavorContract.ANDROID_FLAVOR_CONTENT,values);
    }
}
