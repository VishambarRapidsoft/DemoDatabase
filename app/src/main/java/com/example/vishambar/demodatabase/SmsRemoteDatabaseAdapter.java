package com.example.vishambar.demodatabase;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class SmsRemoteDatabaseAdapter {
	private ArrayList<String> arrayList = new ArrayList<String>();
	private String nameAlreadyTaken = "Este nombre ya est� en uso";
	private String nameIsNew = "This name is new";
	MySQLiteDatabase database;
	private Context context;

	public SmsRemoteDatabaseAdapter(Context context) {
		database = new MySQLiteDatabase(context);
		this.context = context;
	}

	public long insert(String name, String phone, String codeActive,
			String codeDeactive) {
		SQLiteDatabase db = database.getWritableDatabase();


			ContentValues contentValues = new ContentValues();
			contentValues.put(database.NAME, name);
			contentValues.put(database.PHONE, phone);
			contentValues.put(database.CODE_ACTIVE, codeActive);
			contentValues.put(database.CODE_DEACTIVE, codeDeactive);
			long id = db.insert(database.TABLE_NAME, null, contentValues);
			return id;

	}

	public String findSpecificData(String oldName) {
		SQLiteDatabase db = database.getWritableDatabase();
		String columns[] = { database.NAME };
		String whereArgs[] = { oldName };
		Cursor cursor = db.query(database.TABLE_NAME, columns, database.NAME
				+ "=?", whereArgs, null, null, null);

		while (cursor.moveToNext()) {
			int index0 = cursor.getColumnIndex(database.NAME);
			String name = cursor.getString(index0);

			if (name.equals(oldName)) {
				return nameAlreadyTaken;

			}
		}

		return nameIsNew;
	}

	public String findSpecificDataOfSpecificName(String oldName) {
		SQLiteDatabase db = database.getWritableDatabase();
		String columns[] = { database.PHONE };
		String whereArgs[] = { oldName };
		Cursor cursor = db.query(database.TABLE_NAME, columns, database.NAME
				+ "=?", whereArgs, null, null, null);

		while (cursor.moveToNext()) {
			int index0 = cursor.getColumnIndex(database.PHONE);
			String phone = cursor.getString(index0);
			return phone;
		}

		return "";
	}

	public String findActivateCodeOfSpecificName(String oldName) {
		SQLiteDatabase db = database.getWritableDatabase();
		String columns[] = { database.CODE_ACTIVE };
		String whereArgs[] = { oldName };
		Cursor cursor = db.query(database.TABLE_NAME, columns, database.NAME
				+ "=?", whereArgs, null, null, null);

		while (cursor.moveToNext()) {
			int index0 = cursor.getColumnIndex(database.CODE_ACTIVE);
			String codeActive = cursor.getString(index0);
			return codeActive;
		}

		return "no active code";
	}

	public String findDeactivateCodeOfSpecificName(String oldName) {
		SQLiteDatabase db = database.getWritableDatabase();
		String columns[] = { database.CODE_DEACTIVE };
		String whereArgs[] = { oldName };
		Cursor cursor = db.query(database.TABLE_NAME, columns, database.NAME
				+ "=?", whereArgs, null, null, null);

		while (cursor.moveToNext()) {
			int index0 = cursor.getColumnIndex(database.CODE_DEACTIVE);
			String codeDeactive = cursor.getString(index0);
			return codeDeactive;
		}

		return "no deactive code";
	}

	public ArrayList<String> getAllData(String oldName) {
		SQLiteDatabase db = database.getWritableDatabase();
		String columns[] = { database.NAME, database.PHONE,
				database.CODE_ACTIVE, database.CODE_DEACTIVE };
		String whereArgs[] = { oldName };
		Cursor cursor = db.query(database.TABLE_NAME, columns, database.NAME
				+ "=?", whereArgs, null, null, null);
		while (cursor.moveToNext()) {
			int index0 = cursor.getColumnIndex(database.NAME);
			int index1 = cursor.getColumnIndex(database.PHONE);
			int index2 = cursor.getColumnIndex(database.CODE_ACTIVE);
			int index3 = cursor.getColumnIndex(database.CODE_DEACTIVE);
			String name = cursor.getString(index0);
			String phone = cursor.getString(index1);
			String codeActive = cursor.getString(index2);
			String codeDeactive = cursor.getString(index3);
			String s = name + " " + phone + " " + codeActive + " "
					+ codeDeactive;
			arrayList.add(s);
		}
		// Log.d("MyMessage", "arrayList values" + arrayList);
		return arrayList;
	}

	public ArrayList<String> getAlarmNames() {
		SQLiteDatabase db = database.getWritableDatabase();
		String columns[] = { database.NAME };

		Cursor cursor = db.query(database.TABLE_NAME, columns, null, null,
				null, null, null);
		while (cursor.moveToNext()) {
			int index0 = cursor.getColumnIndex(database.NAME);
			String name = cursor.getString(index0);
			String s = name;
			arrayList.add(s);
		}
		return arrayList;
	}

	public void updateTheAddedItem(String name, String phone,
			String codeActive, String codeDeactive) {
		SQLiteDatabase db = database.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(database.PHONE, phone);
		contentValues.put(database.CODE_ACTIVE, codeActive);
		contentValues.put(database.CODE_DEACTIVE, codeDeactive);
		String whereArgs[] = { name };
		db.update(database.TABLE_NAME, contentValues, database.NAME + "=?",
				whereArgs);

	}

	public void deleteRow(String oldName) {
		SQLiteDatabase db = database.getWritableDatabase();
		String whereArgs[] = { oldName };
		db.delete(database.TABLE_NAME, database.NAME + "=?", whereArgs);
	}

	static class MySQLiteDatabase extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "SMS_REMOTE_DATABASE";
		private static final String TABLE_NAME = "SMS_REMOTE";
		private static final String _ID = "_id";
		private static final String NAME = "name";
		private static final String PHONE = "phone";
		private static final String CODE_ACTIVE = "code_active";
		private static final String CODE_DEACTIVE = "code_deactive";
		private static final int DATABASE_VERSION = 1;
		private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
				+ "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME
				+ " VARCHAR(255)," + PHONE + " VARCHAR(255)," + CODE_ACTIVE
				+ " VARCHAR(255)," + CODE_DEACTIVE + " VARCHAR(255));";
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;

		public MySQLiteDatabase(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_TABLE);
				Message m = new Message();
				Log.d("database created");
				// m.message("Database Created with a Table");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				Log.d("database updated");
				db.execSQL(DROP_TABLE);
				Log.d("database created", "table created");
				onCreate(db);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

}
