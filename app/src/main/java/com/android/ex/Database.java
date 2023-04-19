package com.android.ex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "nhom8.db";
    private final static int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE categories(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "cid TEXT," +
                "price real," +
                "date TEXT," +
                "FOREIGN KEY(cid) REFERENCES categories(id))";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int category, int i1) {

    }

    public void insertCategory(Category category) {
        String sql = "insert into categories(name) values(?)";
        String[] args = {category.getName()};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, args);
    }

    public long insertItem(Item item) {
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("cid", item.getCategory().getId());
        values.put("price", item.getPrice());
        values.put("date", item.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("items", null, values);
    }

    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String order = "name DESC";
        Cursor rs = sqLiteDatabase.query(
                "categories",
                null,
                null,
                null,
                null,
                null,
                order
        );
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            list.add(new Category(id, name));
        }
        return list;
    }

    public void deleteCategory(int id){
        String sql = "DELETE FROM categories WHERE id = ?";
        String[] args = {Integer.toString(id)};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, args);
    }

    public void updateCategory(Category i) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        String[] args = {i.getName(), String.valueOf(i.getId())};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, args);
    }

    public Item getItemById(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        if (rs != null && rs.moveToFirst()) {
            String name = rs.getString(1);
            double price = rs.getDouble(3);
            String date = rs.getString(4);
            rs.close();
            return new Item(id, name, price, date, new Category(rs.getInt(2), ""));
        }
        return null;
    }


    public List<Item> getAllItem() {
        List<Item> items = new ArrayList<>();
        String sql = "select i.id, i.name, i.price, i.date, c.id, c.name " +
                "from categories c inner join items i " +
                "on (c.id == i.cid)";

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor != null && cursor.moveToNext()) {
            Category category = new Category(
                    cursor.getInt(4),
                    cursor.getString(5)
            );

            Item item = new Item(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getString(3),
                    category
            );
            items.add(item);
        }
        return items;
    }
}
