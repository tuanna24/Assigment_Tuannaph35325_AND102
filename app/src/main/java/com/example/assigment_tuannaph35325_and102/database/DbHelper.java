package com.example.assigment_tuannaph35325_and102.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "AND102", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String qNguoiDung = "CREATE TABLE NGUOIDUNG(tendangnhap TEXT PRIMARY KEY, matkhau TEXT, hoten TEXT)";
        db.execSQL(qNguoiDung);
        String qSanPham = "CREATE TABLE SANPHAM(masp INTEGER PRIMARY KEY AUTOINCREMENT, tensp TEXT, giaban INTEGER, soluong INTEGER )";
        db.execSQL(qSanPham);

        String dNguoiDung = "INSERT INTO NGUOIDUNG VALUES('tuanna','ph35325','NguyễnAnhTuấn')";
        db.execSQL(dNguoiDung);
        String dSanPham = "INSERT INTO SANPHAM VALUES(1, 'Bánh quy bơ LU Pháp', 45000, 10),(2, 'Snack mực lăn muối ớt', 8000, 52), (3, 'Snack khoai tây Lays', 12000, 38), (4, 'Bánh gạo One One', 30000, 11), (5, 'Kẹo sữa Socola', 25000, 30)";
        db.execSQL(dSanPham);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS SANPHAM");
            onCreate(db);
        }
    }
}
