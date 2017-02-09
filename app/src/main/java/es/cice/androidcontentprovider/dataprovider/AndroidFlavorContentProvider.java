package es.cice.androidcontentprovider.dataprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class AndroidFlavorContentProvider extends ContentProvider {
    public SQLiteDatabase db;
    //uri matcher permite asociar números enteros a las uris, así es más fácil manejarlas
    public static UriMatcher matcher;

    private static final int FLAVOR_CONTENT_URI_VALUE=100;
    private static final int FLAVOR_ITEM_URI_VALUE=200;

    public AndroidFlavorContentProvider() {
    }

    //construir n grupo de contentvalues y que se inserten, devuelve cuantos resgistros se han innsertado
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        int numInsertedRegs=0;
        switch (matcher.match(uri)) {
            case FLAVOR_CONTENT_URI_VALUE:
                db.beginTransaction();
                for (ContentValues cv : values) {
                    //devuelve el identificador de la fila del registro insertado
                    long rowId = db.insert(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE, null, cv);
                    if (rowId != -1)
                        numInsertedRegs++;
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                return numInsertedRegs;
            default:
                throw new UnsupportedOperationException(uri + " No válida");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        switch (matcher.match(uri)){
            //delete toda la tabla
            case FLAVOR_CONTENT_URI_VALUE:
                return db.delete(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE,
                        selection, selectionArgs );
            //delete un sólo registro
            case FLAVOR_ITEM_URI_VALUE:
                //obtenemos el número que viene al final de la uri
                int id=Integer.parseInt(uri.getLastPathSegment());

                selection=AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN + "=" + id;
                return db.delete(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE,
                        selection, null);

            default:
                throw new UnsupportedOperationException(uri + " No reconocida");
        }

    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)){
            case FLAVOR_CONTENT_URI_VALUE:

                return AndroidFlavorContract.ANDROID_FLAVOR_DIR_MIME;
            case FLAVOR_ITEM_URI_VALUE:
                return AndroidFlavorContract.ANDROID_FLAVOR_ITEM_MIME;

            default:
                throw new UnsupportedOperationException(uri + " No válida");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //en el contentvalues están envueltos cada columna con un valor
        // TODO: Implement this to handle requests to insert a new row.

        switch (matcher.match(uri)){
            case FLAVOR_CONTENT_URI_VALUE:
                if(values.containsKey(AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN))
                    values.remove(AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN);
                //devuelve la clave del registro insertado
                long id=db.insert(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE, null, values);
                //devolvemos la uri que resulta de añadir el número
                return AndroidFlavorContract.ANDROID_FLAVOR_ITEM.buildUpon().appendPath("" +id).build();

            default:
                throw new UnsupportedOperationException(uri + " No válida");
        }

    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        AndroidFlavorOpenHelper helper=new AndroidFlavorOpenHelper(getContext(), 1);
        db=helper.getWritableDatabase();
        matcher=new UriMatcher(UriMatcher.NO_MATCH);
        //le asociamos el número 100 a la uri
        matcher.addURI(AndroidFlavorContract.AUTHORITY,
                AndroidFlavorContract.CONTENT_URI_SEGMENT,FLAVOR_CONTENT_URI_VALUE);

        matcher.addURI(AndroidFlavorContract.AUTHORITY,
                AndroidFlavorContract.ITEM_URI_SEGMENT + "/#",FLAVOR_ITEM_URI_VALUE);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        switch (matcher.match(uri)){

            case FLAVOR_CONTENT_URI_VALUE:
                return db.query(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE, projection, selection, selectionArgs, null, null, sortOrder);

            case FLAVOR_ITEM_URI_VALUE:
                //obtenemos el número que viene al final de la uri
                int id=Integer.parseInt(uri.getLastPathSegment());
                return db.query(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE, projection,
                        AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN + "=?",
                        new String[]{"" + id}, null, null, null);

            default:
                throw new UnsupportedOperationException(uri + " No reconocida");
         }

        }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.

        switch (matcher.match(uri)){
            //actualizar toda la tabla
            case FLAVOR_CONTENT_URI_VALUE:
                return db.update(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE, values,
                       selection, selectionArgs );
            //actualizar un sólo registro
            case FLAVOR_ITEM_URI_VALUE:
                //obtenemos el número que viene al final de la uri
                int id=Integer.parseInt(uri.getLastPathSegment());

                selection=AndroidFlavorContract.FLAVORS_CONTENT.PK_COLUMN + "=" + id;
                return db.update(AndroidFlavorOpenHelper.ANDROID_FLAVORS_TABLE, values,
                    selection,null);

            default:
                throw new UnsupportedOperationException(uri + " No reconocida");
        }


    }
}
