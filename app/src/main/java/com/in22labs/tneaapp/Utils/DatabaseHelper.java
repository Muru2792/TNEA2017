package com.in22labs.tneaapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {


    Context myContext;
    private static final int DATABASE_VERSION = 1;
    String DB_PATH =null;
    // Database Name
    private static final String DATABASE_NAME = "counsel";
    private SQLiteDatabase dbase;
    public String rank;
    public String cutoff;
    public String city;
    public String Location;

    public String college;
    public String Dist_city;
    public String Dist_course;
    public String Dist_cutoff;
    public String Cate_course;
    public String College_cate;

    public String clg_code;

    public String course;
    public String tempLoc;
    public String RatingFiled;

    //Notification Center
    public static final String TABLE_Notification = "Notification";
    public static final String KEY_ID = "id";
    public static final String KEY_Title = "title";
    public static final String KEY_Notification = "message";
    public static final String KEY_Date = "date";
    public static final String KEY_Time = "time";



    public String ratingid;
    public String quality;






    public DatabaseHelper(Context context) throws IOException {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext=context;
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        boolean dbexist = checkDataBase();
        if (dbexist) {
            //System.out.println("Database exists");
            openDataBase();
        } else {
            System.out.println("Database doesn't exist");
            createDataBase();
        }

    }

    public void createDataBase() throws SQLiteException, IOException {
        boolean dbExist = checkDataBase();

        if(dbExist){

        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

                            copyDataBase();

        }
    }

    public void copyDataBase() throws IOException{
        //Open your local db as the input stream

//        CopyAssets();


        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        String outFileName = DB_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }



    public void openDataBase() throws SQLiteException{
        String myPath = DB_PATH + DATABASE_NAME;
        dbase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public boolean checkDataBase() throws IOException {
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){


        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL(CREATE_TABLE_CARRIER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> getClgNameNew(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgnamelist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT CollegeName FROM CollegeList ORDER BY Grades ASC"; //" +buttype+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clgnamelist.add(cursor.getString(0)); //array 0  res id
                //Toast.makeText(myContext, cursor.getString(0),Toast.LENGTH_SHORT).show();


            } while (cursor.moveToNext());

        }
        return clgnamelist;

    }

    public ArrayList<String> getCityNew(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgnamelist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT District FROM cutoff"; //" +buttype+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clgnamelist.add(cursor.getString(0)); //array 0  res id
                //Toast.makeText(myContext, cursor.getString(0),Toast.LENGTH_SHORT).show();


            } while (cursor.moveToNext());

        }
        return clgnamelist;

    }


    public ArrayList<String> getCourseNew(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgnamelist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT CourseName FROM CourseList"; //" +buttype+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clgnamelist.add(cursor.getString(0)); //array 0  res id
                //Toast.makeText(myContext, cursor.getString(0),Toast.LENGTH_SHORT).show();


            } while (cursor.moveToNext());

        }
        return clgnamelist;

    }


    public ArrayList<String> getNewClgCourseListOC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clglist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT Course FROM Cutoff WHERE Location ='"+ Location +"'AND OC <='"+cutoff+"'AND College_Code ='"+clg_code+"'"; //" +buttype+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clglist.add(cursor.getString(0)); //array 0  res id
//                Toast.makeText(myContext, cursor.getString(0), Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());

        }
        return clglist;
    }

    public ArrayList<String> getNewClgCourseListBCM(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clglist = new ArrayList<String>();
        Toast.makeText(myContext, college, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT Course FROM Cutoff WHERE Location ='"+ Location +"'AND BCM <='"+cutoff+"'AND College_Code ='"+clg_code+"'"; //" +buttype+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clglist.add(cursor.getString(0)); //array 0  res id

            } while (cursor.moveToNext());

        }
        return clglist;
    }

    public ArrayList<String> getNewClgCourseListBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clglist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT Course FROM Cutoff WHERE Location ='"+ Location +"'AND BC <='"+cutoff+"'AND College_Code ='"+clg_code+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clglist.add(cursor.getString(0)); //array 0  res id

            } while (cursor.moveToNext());

        }
        return clglist;
    }
    public ArrayList<String> getNewClgCourseListMBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clglist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT Course FROM Cutoff WHERE Location ='"+ Location +"'AND MBC <='"+cutoff+"'AND College_Code ='"+clg_code+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clglist.add(cursor.getString(0)); //array 0  res id

            } while (cursor.moveToNext());

        }
        return clglist;
    }

    public ArrayList<String> getNewClgCourseListSC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clglist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT Course FROM Cutoff WHERE Location ='"+ Location +"'AND SC <='"+cutoff+"'College_Code ='"+clg_code+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clglist.add(cursor.getString(0)); //array 0  res id

            } while (cursor.moveToNext());

        }
        return clglist;
    }
    public ArrayList<String> getNewClgCourseListST(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clglist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT Course FROM Cutoff WHERE Location ='"+ Location +"'AND ST <='"+cutoff+"'AND College_Code ='"+clg_code+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clglist.add(cursor.getString(0)); //array 0  res id

            } while (cursor.moveToNext());

        }
        return clglist;
    }

    public ArrayList<String> getNewClgCourseListSCA(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clglist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT Course FROM Cutoff WHERE Location ='"+ Location +"'AND SCA <='"+cutoff+"'AND College_Code ='"+clg_code+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clglist.add(cursor.getString(0)); //array 0  res id

            } while (cursor.moveToNext());

        }
        return clglist;
    }


    public ArrayList<String> getCourse(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> year1 = new ArrayList<String>();
        //Toast.makeText(myContext, clg_name, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT course FROM course"; //" +buttype+"'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                year1.add(cursor.getString(0)); //array 0  res id

            } while (cursor.moveToNext());

        }
        return year1;

    }

    public ArrayList<String> getCourseNewSub(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> year1 = new ArrayList<String>();
        //Toast.makeText(myContext, clg_name, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT CourseName FROM CourseList WHERE Category='"+ Cate_course +"'"; //" +buttype+"'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                year1.add(cursor.getString(0)); //array 0  res id

            } while (cursor.moveToNext());

        }
        return year1;

    }

    public ArrayList<String> getCourseCollegeNewSub(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> year1 = new ArrayList<String>();
//        Toast.makeText(myContext, College_cate, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Course='"+ College_cate +"'ORDER BY Grades";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                year1.add(cursor.getString(0)); //array 0  res id
               // Toast.makeText(myContext, cursor.getString(0), Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());

        }
        return year1;

    }

    public ArrayList<String> getLocationCollegeNewSub(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> year1 = new ArrayList<String>();
//        Toast.makeText(myContext, College_cate, Toast.LENGTH_SHORT).show();
        String query = "SELECT Location FROM Cutoff INNER JOIN CollegeList ON Cutoff.College_Code=CollegeList.CollegeCode WHERE Course='"+ College_cate +"'ORDER BY Grades";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                year1.add(cursor.getString(0)); //array 0  res id
                // Toast.makeText(myContext, cursor.getString(0), Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());

        }
        return year1;

    }


    public ArrayList<String> getRankCollegeNewSub(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> year1 = new ArrayList<String>();
//        Toast.makeText(myContext, College_cate, Toast.LENGTH_SHORT).show();
        String query = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Course='"+ College_cate +"'ORDER BY Grades";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                year1.add(cursor.getString(0)); //array 0  res id
                // Toast.makeText(myContext, cursor.getString(0), Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());

        }
        return year1;

    }


    public ArrayList<String> getCourseCollegeAutoNewSub(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> year1 = new ArrayList<String>();
//        Toast.makeText(myContext, College_cate, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Course='"+ College_cate +"'AND Location ='"+tempLoc+"'ORDER BY Grades";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                year1.add(cursor.getString(0)); //array 0  res id
                // Toast.makeText(myContext, cursor.getString(0), Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());

        }
        return year1;

    }

    public ArrayList<String> getLocationCollegeAutoNewSub(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> year1 = new ArrayList<String>();
//        Toast.makeText(myContext, College_cate, Toast.LENGTH_SHORT).show();
        String query = "SELECT Location FROM Cutoff INNER JOIN CollegeList ON Cutoff.College_Code=CollegeList.CollegeCode WHERE Course='"+ College_cate +"'AND Location ='"+tempLoc+"'ORDER BY Grades";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                year1.add(cursor.getString(0)); //array 0  res id
                // Toast.makeText(myContext, cursor.getString(0), Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());

        }
        return year1;

    }


    public ArrayList<String> getRankCollegeAutoNewSub(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> year1 = new ArrayList<String>();
//        Toast.makeText(myContext, College_cate, Toast.LENGTH_SHORT).show();
        String query = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Course='"+ College_cate +"'AND Location ='"+tempLoc+"'ORDER BY Grades";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                year1.add(cursor.getString(0)); //array 0  res id
                // Toast.makeText(myContext, cursor.getString(0), Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());

        }
        return year1;

    }





    public ArrayList<String> getCollegeCodeNew(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> year2 = new ArrayList<String>();
        //Toast.makeText(myContext, clg_name, Toast.LENGTH_SHORT).show();
        String query = "SELECT CollegeCode FROM CollegeList"; //" +buttype+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                year2.add(cursor.getString(0)); //array 0  res id
               // Toast.makeText(myContext,cursor.getString(0),Toast.LENGTH_SHORT).show();


            } while (cursor.moveToNext());

        }
        return year2;

    }



    public ArrayList<String> getCollegeDistrictNew(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgDist = new ArrayList<String>();
        //Toast.makeText(myContext, clg_name, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE District ='"+ Dist_city +"'AND Cutoff.Course ='"+Dist_course+"'ORDER BY Grades";//" +buttype+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clgDist.add(cursor.getString(0)); //array 0  res id
               // clgDist.add(cursor.getString(6));
                //Toast.makeText(myContext,cursor.getString(0),Toast.LENGTH_SHORT).show();

            } while (cursor.moveToNext());

        }
        return clgDist;
    }

    public ArrayList<String> getLocationDistrictNew(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgDist = new ArrayList<String>();
        //Toast.makeText(myContext, clg_name, Toast.LENGTH_SHORT).show();
        String query = "SELECT Location FROM Cutoff INNER JOIN CollegeList ON Cutoff.College_Code=CollegeList.CollegeCode WHERE District ='"+ Dist_city +"'AND Cutoff.Course ='"+Dist_course+"'ORDER BY CollegeList.Grades";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clgDist.add(cursor.getString(0)); //array 0  res id
                //clgDist.add(cursor.getString(6));
                //Toast.makeText(myContext,cursor.getString(0),Toast.LENGTH_SHORT).show();

            } while (cursor.moveToNext());

        }
        return clgDist;
    }

    public ArrayList<String> getRankDistrictNew(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgDist = new ArrayList<String>();
        //Toast.makeText(myContext, clg_name, Toast.LENGTH_SHORT).show();
        String query = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE District ='"+ Dist_city +"'AND Cutoff.Course ='"+Dist_course+"'ORDER BY CollegeList.Grades";
        //String query1 = "SELECT DISTINCT College FROM cutoff WHERE OC ='"+Dist_cutoff+"'OR BCM ='"+Dist_cutoff+"'OR BC ='"+Dist_cutoff+"'OR MBC ='"+Dist_cutoff+"'OR SC ='"+Dist_cutoff+"'OR SCA ='"+Dist_cutoff+"'OR ST ='"+Dist_cutoff+"'";
        Cursor cursor = db.rawQuery(query, null);
        //Cursor c1=db.rawQuery(query1,null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clgDist.add(cursor.getString(0)); //array 0  res id

                //Toast.makeText(myContext,cursor.getString(0),Toast.LENGTH_SHORT).show();

            } while (cursor.moveToNext());

        }
        return clgDist;
    }


    public ArrayList<String> getCollegeDistrictCutoffNew(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgDist = new ArrayList<String>();
        //Toast.makeText(myContext, clg_name, Toast.LENGTH_SHORT).show();
        String query = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE District ='"+ Dist_city +"'AND OC <='"+Dist_cutoff+"'ORDER BY Grades";
        //String query1 = "SELECT DISTINCT College FROM cutoff WHERE OC ='"+Dist_cutoff+"'OR BCM ='"+Dist_cutoff+"'OR BC ='"+Dist_cutoff+"'OR MBC ='"+Dist_cutoff+"'OR SC ='"+Dist_cutoff+"'OR SCA ='"+Dist_cutoff+"'OR ST ='"+Dist_cutoff+"'";
        Cursor cursor = db.rawQuery(query, null);
        //Cursor c1=db.rawQuery(query1,null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clgDist.add(cursor.getString(0)); //array 0  res id

                //Toast.makeText(myContext,cursor.getString(0),Toast.LENGTH_SHORT).show();

            } while (cursor.moveToNext());

        }
        return clgDist;
    }


    public ArrayList<String> getLocationDistrictCutoffNew(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgDist = new ArrayList<String>();
        //Toast.makeText(myContext, clg_name, Toast.LENGTH_SHORT).show();
        String query = "SELECT Location FROM Cutoff INNER JOIN CollegeList ON Cutoff.College_Code=CollegeList.CollegeCode WHERE District ='"+ Dist_city +"'AND OC <='"+Dist_cutoff+"'ORDER BY CollegeList.Grades";
        //String query1 = "SELECT DISTINCT College FROM cutoff WHERE OC ='"+Dist_cutoff+"'OR BCM ='"+Dist_cutoff+"'OR BC ='"+Dist_cutoff+"'OR MBC ='"+Dist_cutoff+"'OR SC ='"+Dist_cutoff+"'OR SCA ='"+Dist_cutoff+"'OR ST ='"+Dist_cutoff+"'";
        Cursor cursor = db.rawQuery(query, null);
        //Cursor c1=db.rawQuery(query1,null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clgDist.add(cursor.getString(0)); //array 0  res id

                //Toast.makeText(myContext,cursor.getString(0),Toast.LENGTH_SHORT).show();

            } while (cursor.moveToNext());

        }
        return clgDist;
    }


    public ArrayList<String> getRankDistrictCutoffNew(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgDist = new ArrayList<String>();
        //Toast.makeText(myContext, clg_name, Toast.LENGTH_SHORT).show();
        String query = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE District ='"+ Dist_city +"'AND OC <='"+Dist_cutoff+"'ORDER BY CollegeList.Grades";
        //String query1 = "SELECT DISTINCT College FROM cutoff WHERE OC ='"+Dist_cutoff+"'OR BCM ='"+Dist_cutoff+"'OR BC ='"+Dist_cutoff+"'OR MBC ='"+Dist_cutoff+"'OR SC ='"+Dist_cutoff+"'OR SCA ='"+Dist_cutoff+"'OR ST ='"+Dist_cutoff+"'";
        Cursor cursor = db.rawQuery(query, null);
        //Cursor c1=db.rawQuery(query1,null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clgDist.add(cursor.getString(0)); //array 0  res id

                //Toast.makeText(myContext,cursor.getString(0),Toast.LENGTH_SHORT).show();

            } while (cursor.moveToNext());

        }
        return clgDist;
    }
    public String getRankCode(String temp1) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgDist = new ArrayList<String>();
        //Toast.makeText(myContext, clg_name, Toast.LENGTH_SHORT).show();
        String query = "SELECT Rank FROM CollegeList WHERE CollegeName ='"+ temp1 +"'ORDER BY Grades";
        String tamp2="c";
        //String query1 = "SELECT DISTINCT College FROM cutoff WHERE OC ='"+Dist_cutoff+"'OR BCM ='"+Dist_cutoff+"'OR BC ='"+Dist_cutoff+"'OR MBC ='"+Dist_cutoff+"'OR SC ='"+Dist_cutoff+"'OR SCA ='"+Dist_cutoff+"'OR ST ='"+Dist_cutoff+"'";
        Cursor cursor = db.rawQuery(query, null);
        //Cursor c1=db.rawQuery(query1,null);
        if (cursor.moveToFirst()) {
            do {
                //basicArr.add(cursor.getString(0));

                clgDist.add(cursor.getString(0)); //array 0  res id
                tamp2=cursor.getString(0);
                //Toast.makeText(myContext,cursor.getString(0),Toast.LENGTH_SHORT).show();

            } while (cursor.moveToNext());

        }
        return tamp2;
    }

    public ArrayList<String> getCollegeDetails() {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > cutoff = new ArrayList<String>();
        String query = "SELECT * FROM college WHERE CollegeId ='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                cutoff.add(cursor.getString(1));
                cutoff.add(cursor.getString(2));
                cutoff.add(cursor.getString(3));
                cutoff.add(cursor.getString(4));
                cutoff.add(cursor.getString(5));
                cutoff.add(cursor.getString(6));

                cutoff.add(cursor.getString(12));
                cutoff.add(cursor.getString(13));
                cutoff.add(cursor.getString(14));
                cutoff.add(cursor.getString(15));
                cutoff.add(cursor.getString(16));
            }while (cursor.moveToNext());
        }
        return cutoff;
    }


    public ArrayList<String> getCourseDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > course = new ArrayList<String>();
        String q = "SELECT Course FROM cutoff WHERE College_code ='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                course.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return course;

    }

    public ArrayList<String> getOCDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > course = new ArrayList<String>();
        String q = "SELECT OC FROM cutoff WHERE College_code ='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                course.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return course;

    }

    public ArrayList<String> getBCMDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > course = new ArrayList<String>();
        String q = "SELECT BCM FROM cutoff WHERE College_code ='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                course.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return course;

    }

    public ArrayList<String> getBCDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > course = new ArrayList<String>();
        String q = "SELECT BC FROM cutoff WHERE College_code ='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                course.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return course;

    }

    public ArrayList<String> getMBCDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > course = new ArrayList<String>();
        String q = "SELECT MBC FROM cutoff WHERE College_code ='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                course.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return course;

    }

    public ArrayList<String> getSCDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > course = new ArrayList<String>();
        String q = "SELECT SC FROM cutoff WHERE College_code ='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                course.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return course;

    }

    public ArrayList<String> getSTDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > course = new ArrayList<String>();
        String q = "SELECT ST FROM cutoff WHERE College_code ='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                course.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return course;

    }

    public ArrayList<String> getSCADetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > course = new ArrayList<String>();
        String q = "SELECT SCA FROM cutoff WHERE College_code ='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                course.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return course;

    }

    public String getBrachCode(String temp) {
        SQLiteDatabase db = this.getReadableDatabase();
        String BrCode = "AI";
        String q = "SELECT BranchCode FROM CourseList WHERE CourseName ='"+ temp +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {

                BrCode=cursor.getString(0).toString();
                //Toast.makeText(myContext, BrCode, Toast.LENGTH_SHORT).show();
//
            }while (cursor.moveToNext());
        }


        return BrCode;
    }

    public String getCollegeCode(String temp){

        SQLiteDatabase db = this.getReadableDatabase();
        String CollCode = "AI";
        String q = "SELECT CollegeCode FROM CollegeList WHERE CollegeName ='"+ temp +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {

                CollCode=cursor.getString(0).toString();
                //Toast.makeText(myContext, BrCode, Toast.LENGTH_SHORT).show();
//
            }while (cursor.moveToNext());
        }
        return CollCode;
    }


    public String getCollegeCodeV1(String temp){

        SQLiteDatabase db = this.getReadableDatabase();
        String CollCode = "AI";
        String q = "SELECT CollegeCode FROM CollegeList WHERE CollegeName ='"+ temp +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {

                CollCode=cursor.getString(0).toString();
                //Toast.makeText(myContext, BrCode, Toast.LENGTH_SHORT).show();
//
            }while (cursor.moveToNext());
        }
        return CollCode;
    }


    //////College List View Start From here
    public ArrayList<String> getCollegeOrderListOC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.OC <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }
    public ArrayList<String> getCollegeOrderListBCM(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.BCM <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getCollegeOrderListBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.BC <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getCollegeOrderListMBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.MBC <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getCollegeOrderListSC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.SC <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getCollegeOrderListSCA(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.SCA <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
               // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getCollegeOrderListST(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.ST <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
               // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }
    public ArrayList<String> getLocationOrderListOC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM Cutoff INNER JOIN CollegeList ON Cutoff.College_Code=CollegeList.CollegeCode WHERE Cutoff.Course ='"+ course +"'AND Cutoff.OC <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }
    public ArrayList<String> getLocationOrderListBCM(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM Cutoff INNER JOIN CollegeList ON Cutoff.College_Code=CollegeList.CollegeCode WHERE Cutoff.Course ='"+ course +"'AND Cutoff.BCM <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getLocationOrderListBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM Cutoff INNER JOIN CollegeList ON Cutoff.College_Code=CollegeList.CollegeCode WHERE Cutoff.Course ='"+ course +"'AND Cutoff.BC <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getLocationOrderListMBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM Cutoff INNER JOIN CollegeList ON Cutoff.College_Code=CollegeList.CollegeCode WHERE Cutoff.Course ='"+ course +"'AND Cutoff.MBC <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getLocationOrderListSC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM Cutoff INNER JOIN CollegeList ON Cutoff.College_Code=CollegeList.CollegeCode WHERE Cutoff.Course ='"+ course +"'AND Cutoff.SC <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getLocationOrderListSCA(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM Cutoff INNER JOIN CollegeList ON Cutoff.College_Code=CollegeList.CollegeCode WHERE Cutoff.Course ='"+ course +"'AND Cutoff.SCA <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }
    public ArrayList<String> getLocationOrderListST(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM Cutoff INNER JOIN CollegeList ON Cutoff.College_Code=CollegeList.CollegeCode WHERE Cutoff.Course ='"+ course +"'AND Cutoff.ST <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }
    public ArrayList<String> getRankOrderListOC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.OC <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }
    public ArrayList<String> getRankOrderListBCM(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.BCM <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getRankOrderListBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.BC <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getRankOrderListMBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.MBC <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getRankOrderListSC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.SC <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getRankOrderListSCA(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.SCA <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }
    public ArrayList<String> getRankOrderListST(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.ST <='"+cutoff+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }



    //ends here
    //auto complete view database

    public ArrayList<String> getSpecCollegeOrderListOC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.OC <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }
    public ArrayList<String> getSpecCollegeOrderListBCM(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.BCM <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecCollegeOrderListBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.BC <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecCollegeOrderListMBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.MBC <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecCollegeOrderListSC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.SC <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecCollegeOrderListSCA(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.SCA <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecCollegeOrderListST(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT CollegeName FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.ST <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }



    public ArrayList<String> getSpecLocationOrderListOC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.OC <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }
    public ArrayList<String> getSpecLocationOrderListBCM(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.BCM <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecLocationOrderListBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.BC <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecLocationOrderListMBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.MBC <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecLocationOrderListSC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.SC <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecLocationOrderListSCA(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.SCA <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecLocationOrderListST(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Location FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.ST <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }


    public ArrayList<String> getSpecRankOrderListOC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.OC <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }
    public ArrayList<String> getSpecRankOrderListBCM(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.BCM <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecRankOrderListBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.BC <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecRankOrderListMBC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.MBC <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecRankOrderListSC(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.SC <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                //Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }public ArrayList<String> getSpecRankOrderListSCA(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.SCA <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }
    public ArrayList<String> getSpecRankOrderListST(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Rank FROM CollegeList INNER JOIN Cutoff ON CollegeList.CollegeCode=Cutoff.College_Code WHERE Cutoff.Course ='"+ course +"'AND Cutoff.ST <='"+cutoff+"'AND Cutoff.Location ='"+tempLoc+"'ORDER By Grades ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }

    /// ends here





    public ArrayList<String> getCourseInTake(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT BranchCode FROM college WHERE CollegeId='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }
    public ArrayList<String> getInTake(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Intake FROM college WHERE CollegeId='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
               // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;

    }

    public String getCollegeTopRank() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String College="null";
        String q = "SELECT CollegeName FROM CollegeList WHERE CollegeCode='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                College = cursor.getString(0).toString();
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return College;
    }

    public String getLocationTopRank() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String College="null";
        String q = "SELECT Location FROM Cutoff WHERE College_code='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                College = cursor.getString(0).toString();
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return College;
    }

    public String getRankTopRank() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String College="null";
        String q = "SELECT Rank FROM CollegeList WHERE CollegeCode='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                College = cursor.getString(0).toString();
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return College;
    }

    public String getPlaceRank() {
        SQLiteDatabase db = this.getReadableDatabase();
        String College="null";
        String q = "SELECT Placement FROM CollegeRank WHERE Code='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {

                College = cursor.getString(0).toString();
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return College;
    }

    public String getIndusRank() {
        SQLiteDatabase db = this.getReadableDatabase();
        String College="null";
        String q = "SELECT Industry FROM CollegeRank WHERE Code='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {

                College = cursor.getString(0).toString();
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return College;
    }

    public String getQualRank() {
        SQLiteDatabase db = this.getReadableDatabase();
        String College="null";
        String q = "SELECT Quality FROM CollegeRank WHERE Code='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {

                College = cursor.getString(0).toString();
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return College;
    }

    public String getFaceRank() {
        SQLiteDatabase db = this.getReadableDatabase();
        String College="null";
        String q = "SELECT Facilities FROM CollegeRank WHERE Code='"+ clg_code +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {

                College = cursor.getString(0).toString();
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return College;
    }

    public ArrayList<String> getCollegeRank() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT College FROM CollegeRank ORDER By Rank ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }

    public ArrayList<String> getLocationRank() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT District FROM CollegeRank ORDER By Rank ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }

    public ArrayList<String> getRankListRank() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Ranking FROM CollegeRank ORDER By Rank ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }

    public String getRankCourse(String s) {
        SQLiteDatabase db = this.getReadableDatabase();
        String College="null";
        String q = "SELECT CourseName FROM CourseList WHERE BranchCode='"+ s +"'";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {

                College = cursor.getString(0).toString();
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return College;
    }

    public ArrayList<String> getTopCollegeAutoNewSub() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT College FROM CollegeRank WHERE District='"+ tempLoc+"' ORDER By Rank ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }

    public ArrayList<String> getTopLocationCollegeAutoNewSub() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT District FROM CollegeRank WHERE District='"+ tempLoc+"'ORDER By Rank ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }

    public ArrayList<String> getTopRankAutoNewSub() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Ranking FROM CollegeRank WHERE District='"+ tempLoc+"'ORDER By Rank ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }

    public ArrayList<String> getLocationRankTemp() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT DISTINCT District FROM CollegeRank";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }


    public ArrayList<String> getRatingCollegeList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT College FROM CollegeRank ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }

    public ArrayList<String> getRatingLocationList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT District FROM CollegeRank ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }


    public ArrayList<String> getRatingRankList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Ranking FROM CollegeRank ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }

    public ArrayList<String> getRatingPlaceFilterCollegeList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT College FROM CollegeRank WHERE Placement_Filter='" + quality + "' ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        //Toast.makeText(myContext, q, Toast.LENGTH_LONG).show();
        return collegeorder;
    }

    public ArrayList<String> getRatingPlaceFilterLocationList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT District FROM CollegeRank WHERE Placement_Filter='" +quality+ "' ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }


    public ArrayList<String> getRatingPlaceFilterRankList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Ranking FROM CollegeRank WHERE Placement_Filter='" +quality+ "' ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }



    public ArrayList<String> getRatingQualityFilterCollegeList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT College FROM CollegeRank WHERE Quality_Filter='" + quality + "' ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        //Toast.makeText(myContext, q, Toast.LENGTH_LONG).show();
        return collegeorder;
    }

    public ArrayList<String> getRatingQualityFilterLocationList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT District FROM CollegeRank WHERE Quality_Filter='" +quality+ "' ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }


    public ArrayList<String> getRatingQualityFilterRankList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Ranking FROM CollegeRank WHERE Quality_Filter='" +quality+ "' ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }


    public ArrayList<String> getRatingIndustryFilterCollegeList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT College FROM CollegeRank WHERE Industry_Filter='" + quality + "' ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        //Toast.makeText(myContext, q, Toast.LENGTH_LONG).show();
        return collegeorder;
    }

    public ArrayList<String> getRatingIndustryFilterLocationList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT District FROM CollegeRank WHERE Industry_Filter='" +quality+ "' ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }


    public ArrayList<String> getRatingIndustryFilterRankList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Ranking FROM CollegeRank WHERE Industry_Filter='" +quality+ "' ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }


    public ArrayList<String> getRatingFacilityFilterCollegeList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT College FROM CollegeRank WHERE Facilities_Filter='" + quality + "' ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        //Toast.makeText(myContext, q, Toast.LENGTH_LONG).show();
        return collegeorder;
    }

    public ArrayList<String> getRatingFacilityFilterLocationList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT District FROM CollegeRank WHERE Facilities_Filter='" +quality+ "' ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }


    public ArrayList<String> getRatingFacilityFilterRankList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Ranking FROM CollegeRank WHERE Facilities_Filter='" +quality+ "' ORDER By '"+ratingid+"' ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }


    public ArrayList<String> getCollegeRanking() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();

        String q = "SELECT College FROM CollegeRank WHERE Ranking='" +rank+ "' ORDER By Rank ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }

    public ArrayList<String> getLocationRanking() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT District FROM CollegeRank WHERE Ranking='" +rank+ "' ORDER By Rank ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }

    public ArrayList<String> getRankRanking() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Ranking FROM CollegeRank WHERE Ranking='" +rank+ "' ORDER By Rank ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }


    public ArrayList<String> getCollegeRankingLocation() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();

        String q = "SELECT College FROM CollegeRank WHERE Ranking='" +rank+"'AND District ='"+tempLoc+ "' ORDER By Rank ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }

    public ArrayList<String> getLocationRankingLocation() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT District FROM CollegeRank WHERE Ranking='" +rank+"'AND District ='"+tempLoc+ "' ORDER By Rank ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }

    public ArrayList<String> getRankRankingLocation() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > collegeorder= new ArrayList<String>();
        String q = "SELECT Ranking FROM CollegeRank WHERE Ranking='" +rank+"'AND District ='"+tempLoc+ "' ORDER By Rank ASC";
        Cursor cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst()){
            do {
                collegeorder.add(cursor.getString(0));
                // Toast.makeText(myContext, cursor.getString(0)+"  h h h hb h", Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        return collegeorder;
    }
    public void setNotification(String title, String message, String date, String time){
        SQLiteDatabase dbase=this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_Notification, message);
        values.put(KEY_Title, title);
        values.put(KEY_Date, date);
        values.put(KEY_Time, time);

        dbase.insert(TABLE_Notification, null, values);
    }
    public void updateNotiDateTime(String id, String Date, String Time){
        SQLiteDatabase dbase=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Date, Date);
        values.put(KEY_Time, Time);
        dbase.update(TABLE_Notification, values,KEY_ID + " = ?", new String[] {id});
    }

    public ArrayList<String> getNotification(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgnamelist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT title FROM Notification ORDER BY id DESC"; //" +buttype+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                clgnamelist.add(cursor.getString(0)); //array 0  res id



            } while (cursor.moveToNext());

        }
        return clgnamelist;
    }

    public ArrayList<String> getDateNotification() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgnamelist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT date FROM Notification ORDER BY id DESC"; //" +buttype+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                clgnamelist.add(cursor.getString(0)); //array 0  res id



            } while (cursor.moveToNext());

        }
        return clgnamelist;
    }
    public ArrayList<String> getMessageNotification() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgnamelist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT message FROM Notification ORDER BY id DESC"; //" +buttype+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                clgnamelist.add(cursor.getString(0)); //array 0  res id



            } while (cursor.moveToNext());

        }
        return clgnamelist;
    }

    public ArrayList<String> getTimeNotification() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgnamelist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT time FROM Notification ORDER BY id DESC"; //" +buttype+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                clgnamelist.add(cursor.getString(0)); //array 0  res id



            } while (cursor.moveToNext());

        }
        return clgnamelist;
    }
    public void deleteNotification(String message){
        SQLiteDatabase dbase=this.getWritableDatabase();

        dbase.delete(TABLE_Notification, KEY_Title + " = ? ", new String[] {message});
    }


    public ArrayList<String> getNotificationMessage(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> clgnamelist = new ArrayList<String>();
        //Toast.makeText(myContext, city, Toast.LENGTH_SHORT).show();
        String query = "SELECT * FROM Notification WHERE title='"+title+"'"; //" +buttype+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                clgnamelist.add(cursor.getString(1)); //array 0  res id
                clgnamelist.add(cursor.getString(2)); //array 0  res id
                clgnamelist.add(cursor.getString(3)); //array 0  res id
                clgnamelist.add(cursor.getString(4)); //array 0  res id



            } while (cursor.moveToNext());

        }
        return clgnamelist;
    }
}
