package com.android.ex.th2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "th2.db";
    private final static int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE spending(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT," +
                "price real," +
                "date TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int category, int i1) {
        // noop
    }

    public void insertSpending(Spending spending) {
        String sql = "insert into spending(name, description, price, date) values(?,?,?,?)";
        String[] args = {spending.getName(), spending.getDescription(), String.valueOf(spending.getPrice()), spending.getDate()};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, args);
    }

    public List<Spending> getAll() {
        List<Spending> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query(
                "spending",
                null,
                null,
                null,
                null,
                null,
                null
        );
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String description = rs.getString(2);
            double price = rs.getDouble(3);
            String date = rs.getString(4);
            list.add(new Spending(id, name, description, price, date));
        }
        return list;
    }

    public List<Spending> getAllToDay() {
        List<Spending> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String where = "date like ?";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String[] args = {sdf.format(new Date())};
        Cursor rs = sqLiteDatabase.query(
                "spending",
                null,
                where,
                args,
                null,
                null,
                null
        );
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String description = rs.getString(2);
            double price = rs.getDouble(3);
            String date = rs.getString(4);
            list.add(new Spending(id, name, description, price, date));
        }
        return list;
    }

    public void deleteSpending(int id) {
        String sql = "DELETE FROM spending WHERE id = ?";
        String[] args = {Integer.toString(id)};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, args);
    }

    public void updateSpending(Spending spending) {
        String sql = "UPDATE spending SET name = ?, description=? ,price=? ,date=? WHERE id = ?";
        String[] args = {spending.getName(), spending.getDescription(), String.valueOf(spending.getPrice()), spending.getDate(), String.valueOf(spending.getId())};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, args);
    }

    public Spending getItemById(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("spending",
                null, whereClause, whereArgs,
                null, null, null);
        if (rs != null && rs.moveToFirst()) {
            String name = rs.getString(1);
            String description = rs.getString(2);
            double price = rs.getDouble(3);
            String date = rs.getString(4);
            rs.close();
            return new Spending(id, name, description, price, date);
        }
        return null;
    }

    public List<Spending> searchByName(String key) {
        List<Spending> list= new ArrayList<>();
        String whereClause = "name like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("spending",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String name = rs.getString(1);
            String description = rs.getString(2);
            double price = rs.getDouble(3);
            String date = rs.getString(4);
            list.add(new Spending(id,name,description,price,date));
        }
        return list;
    }

    public List<Spending> searchByDescription(String key) {
        List<Spending> list= new ArrayList<>();
        String whereClause = "description like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("spending",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String name = rs.getString(1);
            String description = rs.getString(2);
            double price = rs.getDouble(3);
            String date = rs.getString(4);
            list.add(new Spending(id,name,description,price,date));
        }
        return list;
    }

    public List<Spending> getByDateFromTo(String from,String to) {
        List<Spending> list = new ArrayList<>();
        String whereClause = "date BETWEEN ? AND ?";
        String[] whereArgs = { from.trim(),to.trim()};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("spending",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String name = rs.getString(1);
            String description = rs.getString(2);
            double price = rs.getDouble(3);
            String date = rs.getString(4);
            list.add(new Spending(id,name,description,price,date));
        }
        return list;
    }
}
