package com.example.healthcare;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static final String dbNAme="signup.db";

    public Database(@Nullable Context context) {
        super(context, dbNAme, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String q="create table Users1(username text, email text, password text)";
        sqLiteDatabase.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        onCreate(sqLiteDatabase);
    }

    public void register(String username, String email, String password){
        ContentValues cv= new ContentValues();
        cv.put("username",username);
        cv.put("email",email);
        cv.put("password",password);

        SQLiteDatabase db= this.getWritableDatabase();

        db.insert("Users1",null,cv);
        db.close();


    }

    public String login(String username,String password){
        String result="0";
        SQLiteDatabase db1=this.getReadableDatabase();
        Cursor cursor=db1.rawQuery("select * from Users1 where username=? and password=?",new String[]{username,password});
        if (cursor.getCount()>0){
            result="1";
        }

        return result;

    }
}
