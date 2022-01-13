package com.nine.childcare.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.nine.childcare.Constants;
import com.nine.childcare.model.Question;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DatabaseManager extends SQLiteOpenHelper {
    private final Context mContext;
    private SQLiteDatabase database;

    public static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context){
        super(context, Constants.DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
        try {
            createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDataBase();
        if(!dbExist) {
            this.getReadableDatabase();
            openDatabase();
            copyDataBase();
            closeDataBase();
            this.close();
        }

    }

    private void copyDataBase() {
        try {
            String outFileName =  Constants.DATABASE_PATH +  Constants.DATABASE_NAME;
            DataInputStream mInput = new DataInputStream(mContext.getAssets().open( Constants.DATABASE_NAME));
            DataOutputStream mOutput = new DataOutputStream(new FileOutputStream(outFileName));
            byte[] mBuffer = new byte[2024];
            int mLength;
            while ((mLength = mInput.read(mBuffer)) > 0) {
                mOutput.write(mBuffer, 0, mLength);
            }
            mOutput.flush();
            mOutput.close();
            mInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean checkDataBase()
    {
        boolean checkDB = false;
        try {
            String myPath =  Constants.DATABASE_PATH +  Constants.DATABASE_NAME;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        }
        catch(SQLiteException e) {
            e.printStackTrace();
        }
        return checkDB;
    }

    //Open database
    public void openDatabase() throws SQLException {
        String myPath =  Constants.DATABASE_PATH +  Constants.DATABASE_NAME;
        database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDataBase()throws SQLException
    {
        if(database != null)
            database.close();
        super.close();
    }

    public Question getQuestion(int id) {
        openDatabase();
        String sql_query = "SELECT * FROM Question WHERE id = '" + id + "'";
        Cursor cursor = database.rawQuery(sql_query, null);
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        String ques = cursor.getString(cursor.getColumnIndexOrThrow("CauHoi"));
        String caseA = cursor.getString(cursor.getColumnIndexOrThrow("A"));
        String caseB = cursor.getString(cursor.getColumnIndexOrThrow("B"));
        String caseC = cursor.getString(cursor.getColumnIndexOrThrow("C"));
        String caseD = cursor.getString(cursor.getColumnIndexOrThrow("D"));
        String trueCase = cursor.getString(cursor.getColumnIndexOrThrow("DapAnDung"));
        Question question = new Question(id, ques, caseA, caseB, caseC, caseD, trueCase);
        closeDataBase();
        return question;
    }

    public String getWordMean(String word) {
        openDatabase();
        String sql_query = "SELECT * FROM anhviet WHERE tu = '" + word + " '";
        Cursor cursor = database.rawQuery(sql_query, null);
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        String mean = cursor.getString(cursor.getColumnIndexOrThrow("nghia"));
        closeDataBase();
        return mean;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
