package corp.burenz.expertouch.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by buren on 2/2/18.
 */



public class SearchHistory {


    private static  final String DATABASE_NAME   = "history";
    private static  final String DATABASE_TABLE  = "channel_history";
    private static  final String CHANNEL_HISTORY = "channel_name";
    private static  final String CHANNEL_ID      = "channel_id";




    private SearchHistoryMode dbHelper;
    private final  Context ourContext;
    private SQLiteDatabase sqLiteDatabase;


    public SearchHistory(Context ourContext) {
        this.ourContext = ourContext;
    }

    public SearchHistory writer(){

        dbHelper = new SearchHistoryMode(ourContext);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void  close(){
        dbHelper.close();

    }

    private Cursor cursor;

    public String getHistoryStringArray(){

        String []list = {
                CHANNEL_HISTORY,
        };

        cursor = sqLiteDatabase.query(DATABASE_TABLE,list,null,null,null,null,CHANNEL_ID + " DESC","5");
        int channelHistoryValue = cursor.getColumnIndex(CHANNEL_HISTORY);

        String result = "";
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            result = result + cursor.getString(channelHistoryValue) + " \n";
        }

        return result;

    }

    public long createHistory(String s){

        ContentValues contentValues = new ContentValues();

        contentValues.put(CHANNEL_HISTORY,s);

        return sqLiteDatabase.insert(DATABASE_TABLE,null,contentValues);


    }


    private class SearchHistoryMode extends SQLiteOpenHelper {


        SearchHistoryMode(Context context) {
            super(context, DATABASE_NAME, null ,1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            String channelHistoryTable = "CREATE TABLE " + DATABASE_TABLE +  " ( " +"channel_name TEXT NOT NULL " + ");" ;

            String exec = "CREATE TABLE channel_history ( " +
                    "channel_name TEXT NOT NULL, " +
                    "channel_id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ");" ;

            sqLiteDatabase.execSQL(exec);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE + ";" );
            onCreate(sqLiteDatabase);

        }




    }


}




