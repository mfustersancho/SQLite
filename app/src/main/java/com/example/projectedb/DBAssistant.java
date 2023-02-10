package com.example.projectedb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBAssistant extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "commentariesDB";

    private static final String COMMENTARY_TABLE = "commentary";

    public DBAssistant(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + COMMENTARY_TABLE + "("
            + "id integer primary key autoincrement"
            + ", title text not null"
            + ", body text not null"
            + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + COMMENTARY_TABLE);
        onCreate(db);
    }

    @SuppressLint("Range")
    public ArrayList<CommentaryModel> getCommentaries() {
        ArrayList<CommentaryModel> commentaryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + COMMENTARY_TABLE, null);
        if(cursor.getCount() != 0) {
            if(cursor.moveToFirst()) {
                do {
                    CommentaryModel commentary = new CommentaryModel();
                    commentary.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    commentary.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    commentary.setBody(cursor.getString(cursor.getColumnIndex("body")));
                    commentaryList.add(commentary);
                } while(cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return commentaryList;
    }

    public void addCommentary(CommentaryModel commentary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", commentary.getTitle());
        contentValues.put("body", commentary.getBody());
        db.insert(COMMENTARY_TABLE, null, contentValues);
        db.close();
    }

    public void deleteCommentary(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + COMMENTARY_TABLE + " where id=" + id);
        db.close();
    }
}
