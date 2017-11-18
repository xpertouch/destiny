package corp.burenz.expertouch.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by xperTouch on 8/12/2016.
 */

public class Favourites  {


    public static final String COMPANY_BANNER = "companyBanner";
    public static final String COMPANY_TITLE = "companyTitles";
    public static final String POST_DATE = "postDate";
    public static final String JOB_INFO = "jobInfo";
    public static final String COMPANY_CALL = "companyCall";
    public static final String MAIL_COMPANY = "mailCompany";
    public static final String VISIT_COMPANY = "visitCompany";



    public static final String EXPERT_NAME = "expertName";
    public static final String EXPERT_EXPERTISE = "expertExpertise";
    public static final String EXPERT_EXPERIENCE = "yearsOfEx";
    public static final String EXPERT_STATUS = "status";
    public static final String EXPERT_SKILLS = "skills";
    public static final String EXPERT_PIC = "expertPic";
    public static final String EXPERT_ID = "expertId";





    private static final String DATABASE_NAME = "favourites";
    private static final String DATABASE_TABLE = "companyAdds";
    private static final String EXPERT_PROFILES = "expertProfiles";

    private static final int DATABASE_VERSION = 1;


    private DbHelper dbHelper;
    private final  Context ourContext;
    private SQLiteDatabase sqLiteDatabase;

    Cursor cursor;


    public Favourites(Context ourContext) {
        this.ourContext = ourContext;

    }
    public Favourites writer(){

        dbHelper = new DbHelper(ourContext);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;

    }

    public void  close(){
        dbHelper.close();

    }

    public long insertCompany(String companyTitles,String postDate, String jobInfo, String companyCall,String companyVisit, String companyMail, String companyBanner){

        ContentValues contentValues = new ContentValues();
        
        contentValues.put(COMPANY_TITLE,companyTitles);
        contentValues.put(POST_DATE,postDate);
        contentValues.put(JOB_INFO,jobInfo);
        contentValues.put(COMPANY_CALL,companyCall);
        contentValues.put(MAIL_COMPANY,companyMail);
        contentValues.put(COMPANY_BANNER,companyBanner);
        contentValues.put(VISIT_COMPANY,companyVisit);

        return sqLiteDatabase.insert(DATABASE_TABLE,null,contentValues);



    }
    public long insertExpert(String expertId,String expertName, String expertExpertise, String yearsOfEx, String status, String skills,String expertPic){

        ContentValues contentValues = new ContentValues();

        Log.e("EXPERT ID - ",expertId);
        Log.e("EXPERT NAME - ",expertName);


        contentValues.put(EXPERT_NAME,expertName);
        contentValues.put(EXPERT_EXPERTISE,expertExpertise);
        contentValues.put(EXPERT_EXPERIENCE,yearsOfEx);
        contentValues.put(EXPERT_ID,expertId);
        contentValues.put(EXPERT_STATUS,status);
        contentValues.put(EXPERT_PIC,expertPic);
        contentValues.put(EXPERT_SKILLS,skills);

        return sqLiteDatabase.insert(EXPERT_PROFILES,null,contentValues);

    }
  


  public String getExpertId(){
      Cursor cursor;
      String []list = {
            EXPERT_ID,
        };
        cursor = sqLiteDatabase.query(EXPERT_PROFILES,list,null,null,null,null,null);
        int iexpertId = cursor.getColumnIndex(EXPERT_ID);
        String result = "";
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            result = result + cursor.getString(iexpertId) + " \n";
        }
        return result;
    }
    public String getExpertName(){
        String []list = {
            EXPERT_NAME,
        };
        cursor = sqLiteDatabase.query(EXPERT_PROFILES,list,null,null,null,null,null);
        int iexpertName = cursor.getColumnIndex(EXPERT_NAME);
        String result = "";
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            result = result + cursor.getString(iexpertName) + " \n";
        }
        return result;
    }



    public String getExpertExpertise(){
        String []list = {
                EXPERT_EXPERTISE
        };
        cursor = sqLiteDatabase.query(EXPERT_PROFILES,list,null,null,null,null,null);
        int iexpertName = cursor.getColumnIndex(EXPERT_EXPERTISE);
        String result = "";
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            result = result + cursor.getString(iexpertName) + " \n";
        }
        return result;
    }



    public String getExpertExperience(){

        String []list = {

                EXPERT_EXPERIENCE

        };



        cursor = sqLiteDatabase.query(EXPERT_PROFILES,list,null,null,null,null,null);
        int iexpertName = cursor.getColumnIndex(EXPERT_EXPERIENCE);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(iexpertName) + " \n";

        }


        return result;


    }






    public String getExpertPic(){
        String []list = {
                EXPERT_PIC
        };
        cursor = sqLiteDatabase.query(EXPERT_PROFILES,list,null,null,null,null,null);
        int iexpertPic = cursor.getColumnIndex(EXPERT_PIC);
        String result = "";
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            result = result + cursor.getString(iexpertPic) + " \n";
        }
        return result;
    }



    public String getExpertStatus(){

        String []list = {

                EXPERT_STATUS

        };



        cursor = sqLiteDatabase.query(EXPERT_PROFILES,list,null,null,null,null,null);
        int iexpertName = cursor.getColumnIndex(EXPERT_STATUS);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(iexpertName) + " \n";

        }
        return result;
    }

    public String getExpertSkills(){

        String []list = {

                EXPERT_SKILLS

        };



        cursor = sqLiteDatabase.query(EXPERT_PROFILES,list,null,null,null,null,null);
        int iexpertName = cursor.getColumnIndex(EXPERT_SKILLS);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(iexpertName) + " \n";

        }


        return result;


    }
    public void deleteExpert(String expertiD){

        String exec = "DELETE FROM "+EXPERT_PROFILES+" WHERE "+EXPERT_ID+"='"+expertiD+"'";
        Log.e("sasa",exec);
        sqLiteDatabase.delete(EXPERT_PROFILES,EXPERT_ID +"='"+expertiD+"'",null);
        sqLiteDatabase.execSQL(exec);
    }
    public int getExpertCount(){

        String[] list  = {
                EXPERT_NAME,
        };
        cursor = sqLiteDatabase.query(EXPERT_PROFILES,list,null,null,null,null,null);

        int i  = cursor.getColumnCount();

//        Toast.makeText(ourContext, "" + i , Toast.LENGTH_SHORT).show();
        return 1;
    }

    public String getCompanyTitle(){

        String[] list  = {
                COMPANY_TITLE,
        };
        cursor = sqLiteDatabase.query(DATABASE_TABLE,list,null,null,null,null,null);
        int icompany = cursor.getColumnIndex(COMPANY_TITLE);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(icompany) + " \n";

        }


        return result;



    }
    public String getCompanyBanner(){

        String[] list  = {
                COMPANY_BANNER,
        };
        cursor = sqLiteDatabase.query(DATABASE_TABLE,list,null,null,null,null,null);
        int icompany = cursor.getColumnIndex(COMPANY_BANNER);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(icompany) + " \n";

        }


        return result;



    }

    public String getPostDate(){
        String[] list  = {
                POST_DATE,
        };
        cursor = sqLiteDatabase.query(DATABASE_TABLE,list,null,null,null,null,null);
        int ipostDate = cursor.getColumnIndex(POST_DATE);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(ipostDate)  + " \n";

        }


        return result;



    }
    public String getJobInfo(){
        String[] list  = {
                JOB_INFO,
        };
        cursor = sqLiteDatabase.query(DATABASE_TABLE,list,null,null,null,null,null);

        int ijobInfo = cursor.getColumnIndex(JOB_INFO);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(ijobInfo)  + "\n";

        }


        return result;



    }
    public String getmailCompany(){


        String[] list  = {
                MAIL_COMPANY,
        };

        cursor = sqLiteDatabase.query(DATABASE_TABLE,list,null,null,null,null,null);

        int imailCompany = cursor.getColumnIndex(MAIL_COMPANY);

        String result = "";

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(imailCompany)  + " \n";

        }


        return result;




    }



    public String getvisitCompany(){
        String[] list  = {
                VISIT_COMPANY,
        };
        cursor = sqLiteDatabase.query(DATABASE_TABLE,list,null,null,null,null,null);
        int iaddCata= cursor.getColumnIndex(VISIT_COMPANY);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(iaddCata)  + " \n";

        }


        return result;



    }


//    public String getAddType(){
//
//
//
//        String[] list  = {
//                ADD_TYPE,
//        };
//        cursor = sqLiteDatabase.query(DATABASE_TABLE,list,null,null,null,null,null);
//        int iaddType = cursor.getColumnIndex(ADD_TYPE);
//
//        String result = "";
//
//
//        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
//
//            result = result + cursor.getString(iaddType)  + " \n";
//
//        }
//
//
//        return result;
//
//
//
//    }


    public String getcompanyCall(){

        String[] list  = {
                COMPANY_CALL,
        };

        cursor = sqLiteDatabase.query(DATABASE_TABLE,list,null,null,null,null,null);

        int icompanyCall = cursor.getColumnIndex(COMPANY_CALL);

        String result = "";


        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            result = result + cursor.getString(icompanyCall) + " \n";

        }


        return result;



    }


    public void deletePost(String info){

    //    sqLiteDatabase.delete(DATABASE_TABLE,JOB_INFO + "=" + info+"",null );
        String exec = "DELETE FROM companyAdds WHERE jobInfo='"+info+"'";
        Log.e("exec",exec);
        sqLiteDatabase.execSQL(exec);
    }
    public int getFavouriteCount(){


      //  String exec = "SELECT * FROM "+DATABASE_TABLE+" WHERE 1";
     //   sqLiteDatabase.execSQL(exec);

        return 1;

    }


//    public String getData(){
//
//        String result = "";
//
//        String[] list  = {
//                COMPANY_TITLE,
//                POST_DATE,
//                JOB_INFO,
//                COMPANY_CALL,
//                VISIT_COMPANY,
//                MAIL_COMPANY,
////                ADD_TYPE
//        };
//
//
//        int icompany = cursor.getColumnIndex(COMPANY_TITLE);
//        int ipostDate = cursor.getColumnIndex(POST_DATE);
//        int ijobInfo = cursor.getColumnIndex(JOB_INFO);
//        int ibanner = cursor.getColumnIndex(COMPANY_CALL);
//        int ivisitCompany = cursor.getColumnIndex(VISIT_COMPANY);
//        int istate  = cursor.getColumnIndex(MAIL_COMPANY);
//        //int iaddType = cursor.getColumnIndex(ADD_TYPE);
//
//
//        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
//
//            result = result + cursor.getString(icompany) + " " + cursor.getString(ipostDate) + " " + cursor.getString(ijobInfo) + " " + cursor.getString(ibanner) + " " +cursor.getString(ivisitCompany) + " " + cursor.getString(istate) + " " + cursor.getString(iaddType) +"\n";
//
//        }
//
//
//
//        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
//
//        result = result + cursor.getString(icompany) + " " + cursor.getString(ipostDate) + " " + cursor.getString(ijobInfo) + " " + cursor.getString(ibanner) + " " +cursor.getString(ivisitCompany) + " " + cursor.getString(istate) + " " + cursor.getString(iaddType) +"\n";
//
//        }
//
//
//        return  result;
//
//
//
//
//
//
//
//    }

    public boolean checkAvailibility(String s) {



        String[] test = getJobInfo().split("\n");

        for(int i = 0; i < test.length; i++){

            if (test[i].equals(s)){
                    return true;

            }
        }

        return false;

    }




    public boolean checkExpertAvailibility(String s) {



        String[] test = getExpertId().split(" \n");

        for(int i = 0; i < test.length; i++){

            if (test[i].equals(s)){
                return true;


            }
        }

        return false;

    }




    private static class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null ,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {


            String exec = "CREATE TABLE companyAdds ( " +
                    "companyTitles TEXT NOT NULL, " +
                    "postDate TEXT NOT NULL, " +
                    "jobInfo TEXT NOT NULL PRIMARY KEY, " +
                    "companyCall TEXT NOT NULL, " +
                    "mailCompany TEXT NOT NULL, " +
                    "companyBanner TEXT NOT NULL, " +
                    "visitCompany TEXT NOT NULL " +
                    ");" ;

            String execProfiles =
                    "CREATE TABLE expertProfiles ( " +
                    "expertName TEXT NOT NULL, " +
                    "expertExpertise TEXT NOT NULL, " +
                    "yearsOfEx TEXT NOT NULL, " +
                    "expertId TEXT NOT NULL PRIMARY KEY, " +
                    "status TEXT NOT NULL, " +
                    "expertPic TEXT NOT NULL, " +
                    "skills TEXT NOT NULL " +
                    ");";


            db.execSQL(exec);
            db.execSQL(execProfiles);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE + "; DROP TABLE IF EXISTS " + EXPERT_PROFILES +";" );
            onCreate(db);

        }




    }

}