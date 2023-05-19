package com.aldo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public  class Administrador extends SQLiteOpenHelper{

    public Administrador(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PERSONAS (ID INTEGER PRIMARY KEY, NOMBRE TEXT," +
                "DIRECCION TEXT, GENERO TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS PERSONAS");
        db.execSQL("CREATE TABLE PERSONAS (ID INTEGER PRIMARY KEY, NOMBRE TEXT, " +
                "DIRECCION TEXT, GENERO TEXT)");
    }
}