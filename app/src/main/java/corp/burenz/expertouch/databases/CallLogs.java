package corp.burenz.expertouch.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by xperTouch on 8/16/2016.
 */
public class CallLogs {


        public static final String CALL_TITLE = "callTitle";
        public static final String CALL_DATE = "callDate";
        public static final String CALL_PHONE = "callPhone";


        private static final String DATABASE_NAME = "logDatabase";
        private static final String COMPANY_TABLE = "myCalls";
        private static final String EXPERT_TABLE = "expertCalls";
        private static final int DATABASE_VERSION = 1;


        Context context;
        LogDatabase logs;
        SQLiteDatabase sqLiteDatabase;

        Cursor cursor;



        public long updateCompanyCall(String callTitle,String callDate,String callPhone){

            ContentValues contentValues = new ContentValues();
            contentValues.put(CALL_TITLE,callTitle);
            contentValues.put(CALL_DATE,callDate);
            contentValues.put(CALL_PHONE,callPhone);
            Log.e("TAG","Inside Update Copany Call "+callTitle+" "+callDate+" "+callPhone+" ");
          return   sqLiteDatabase.insert(COMPANY_TABLE,null,contentValues);

        }

        public void updateExpertCalls(String callTitle,String callDate,String callPhone){

        ContentValues contentValues = new ContentValues();
        contentValues.put(CALL_TITLE,callTitle);
        contentValues.put(CALL_DATE,callDate);
        contentValues.put(CALL_PHONE,callPhone);

        sqLiteDatabase.insert(EXPERT_TABLE,null,contentValues);

    }





        public String getCallTitles(){



            String []list = {

                    CALL_TITLE,

            };



            cursor = sqLiteDatabase.query(EXPERT_TABLE,list,null,null,null,null,null);
            int callTitle = cursor.getColumnIndex(CALL_TITLE);

            String result = "";


            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

                result = result + cursor.getString(callTitle) + " \n";

            }

            return result;

        }
        public String getCallDates(){



        String []list = {

                CALL_DATE,

        };



        cursor = sqLiteDatabase.query(EXPERT_TABLE,list,null,null,null,null,null);
        int callDates = cursor.getColumnIndex(CALL_DATE);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(callDates) + " \n";

        }

        return result;

    }
        public String getCallPhone(){



        String []list = {

                CALL_PHONE,

        };



        cursor = sqLiteDatabase.query(EXPERT_TABLE,list,null,null,null,null,null);
        int callTitle = cursor.getColumnIndex(CALL_PHONE);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(callTitle) + " \n";

        }

        return result;

    }

        public String getCompanyCallTitles(){



        String []list = {

                CALL_TITLE,

        };



        cursor = sqLiteDatabase.query(COMPANY_TABLE,list,null,null,null,null,null);
        int callTitle = cursor.getColumnIndex(CALL_TITLE);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(callTitle) + " \n";

        }
            Log.e("TAG",result);

        return result;

    }




        public void clearCompanyLogs(){
            sqLiteDatabase.execSQL("DELETE FROM "+COMPANY_TABLE+" WHERE 1");
        }


        public void clearExpertLogs(){
        sqLiteDatabase.execSQL("DELETE FROM "+EXPERT_TABLE+" WHERE 1");
        }





    public String getCompanyCallDates(){



        String []list = {

                CALL_DATE,

        };



        cursor = sqLiteDatabase.query(COMPANY_TABLE,list,null,null,null,null,null);
        int callDates = cursor.getColumnIndex(CALL_DATE);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(callDates) + " \n";

        }

        return result;

    }
        public String getCompanyCallPhones(){



        String []list = {

                CALL_PHONE,

        };



        cursor = sqLiteDatabase.query(COMPANY_TABLE,list,null,null,null,null,null);
        int callTitle = cursor.getColumnIndex(CALL_PHONE);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(callTitle) + " \n";

        }

        return result;

    }






    public CallLogs writer(){
            logs  = new LogDatabase(context);
            sqLiteDatabase = logs.getWritableDatabase();
            return this;
        }











        public void close(){
            logs.close();
        }






        public CallLogs(Context myContext){
             this.context = myContext;
        }


        public static class LogDatabase extends SQLiteOpenHelper{

        public LogDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        String exec = "CREATE TABLE "+COMPANY_TABLE+" ( " +
                ""+CALL_TITLE+" TEXT NOT NULL," +
                ""+CALL_DATE+" TEXT NOT NULL," +
                ""+CALL_PHONE+" TEXT NOT NULL" +
                ");";
                db.execSQL(exec);

            String exec1 = "CREATE TABLE "+EXPERT_TABLE+" ( " +
                    ""+CALL_TITLE+" TEXT NOT NULL," +
                    ""+CALL_DATE+" TEXT NOT NULL," +
                    ""+CALL_PHONE+" TEXT NOT NULL" +
                    ");";
            db.execSQL(exec1);



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


            String exec = "DROP TABLE IF EXISTS "+COMPANY_TABLE+"; DROP TABLE IF EXISTS "+EXPERT_TABLE+";";
            db.execSQL(exec);
            onCreate(db);

        }


    }




}
