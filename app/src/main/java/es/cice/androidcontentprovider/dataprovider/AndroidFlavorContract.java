package es.cice.androidcontentprovider.dataprovider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by cice on 8/2/17.
 */

public class AndroidFlavorContract {
    public static final String AUTHORITY="es.cice.androidcontentprovider.app";
    public static final String AUTHORITY_URI_STRING="content://es.cice.androidcontentprovider.app";
    public static final String CONTENT_URI_SEGMENT="flavors";
    public static final String ITEM_URI_SEGMENT="item";

    public static final Uri AUTHORITY_URI=Uri.parse("content://es.cice.androidcontentprovider.app");


    //definimos un mime que defina un registro único y otro que defina un grupo
    public static final String ANDROID_FLAVOR_ITEM_MIME= ContentResolver.CURSOR_ITEM_BASE_TYPE +
            "/es.cice.androidcontentprovider.flavors";
    public static final String ANDROID_FLAVOR_DIR_MIME=ContentResolver.CURSOR_DIR_BASE_TYPE +
            "/es.cice.androidcontentprovider.flavors";

    //uri para acceder a todos los registros
    public static final Uri ANDROID_FLAVOR_CONTENT=AUTHORITY_URI.buildUpon().appendPath("flavors").build();
    //uri para acceder a un item
    public static final Uri ANDROID_FLAVOR_ITEM=AUTHORITY_URI.buildUpon().appendPath("item").build();

    //una subclase para registrar las columnas de la tabla
    public static class FLAVORS_CONTENT implements BaseColumns {
        //SE DEFINEN LOS CAMPOS PARA QUE UNA APLICaCION EXTERNA PUEDA ACCEDER AL CONTENT PROVIDER

        //todas las tablas deben tener esta columna
        public static final String PK_COLUMN="_id";
        public static  final String NAME_COLUMN="NAME";
        public static final String DESCRIPTION_COLUMN="DESCRIPTION";
        public static final String IMAGE_ID_COLUMN="IMAGE_ID";
    }

}
