package com.banking.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class ZonekeTitleDatabaseHelper extends SQLiteOpenHelper {
    
	public  ZonekeTitleDatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
    public  ZonekeTitleDatabaseHelper(Context context,String name)
    {
    	this(context, name, null, 1);
    }
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("create a new database");
		//��һ�ν����ݿ��ͬʱ��user��
		db.execSQL("create table dotadata( id int,username char(20),content text,time date)");
		
		//�ĸ����ݿ�
		
	}
    //�������������ݿ�ʱ�����Լ�����
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("the sqlite dotadatabase update!Will never use now!");
		
	}
	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
		//�����ݿ����ȱ�ִ�е�
	}
	

}

