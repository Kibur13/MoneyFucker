

/**
 * Created by j2md7_000 on 8/27/2017.
 */
package Database;

import com.example.android.firstapp.Transactions;

import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;



public class TransactionDB extends SQLiteOpenHelper{


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "transactionData.db";

    public static final String TABLE_PRODUCTS = "transactions";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "transactionName";



    public TransactionDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE" + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGAR PRIMARY KEY AUTOINCREMENT " + COLUMN_PRODUCTNAME + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);

    }

    public void addProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.get_productname());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS,null,values);
        db.close();
    }

    public void deleteTransaction (String TransactionName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME +"=\"" + productName + "\";");
    }

    public String databaseToString(){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = " SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();

        while  (!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("productname"))!=null){
                dbString += c.getString(c.getColumnIndex("productname"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }
}

