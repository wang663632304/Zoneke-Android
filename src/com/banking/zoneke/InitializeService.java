package com.banking.zoneke;



import java.util.ArrayList;

import com.banking.SelfDefineLayout.SimpleTitleLayout;
import com.banking.entity.Messages;
import com.banking.entity.message;
import com.banking.gsonUtil.JsonDataGetApi;

import com.banking.wholevariable.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.banking.db.*;

public class InitializeService extends Service{

static final String TAG = "MyService"; 
	
	private MyBinder mBinder = new MyBinder();
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.e(TAG, "start simpleTitleService binder");
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.e(TAG, "start simpleTitleService onUnbind");
		return super.onUnbind(intent);
	}

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.e(TAG, "start onCreate");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//return super.onStartCommand(intent, flags, startId);
		Log.e(TAG, "start onStartCommand");
		return  START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.e(TAG, "start onDestory");
		super.onDestroy();
	}

	
	//onStart方法可以不重写
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		Log.e(TAG, "start onStart");
		super.onStart(intent, startId);
	}
	
	//这里写一个或若干个函数

    public void getJingWei(){
    	
    }
    public String[] getUserProfile(){
    	ZonekeDatabaseHelper dh = new ZonekeDatabaseHelper(InitializeService.this,"zoneke_db");
    	//这儿，没有数据库会自动建立
    	SQLiteDatabase sd = dh.getReadableDatabase();
    	
    	String userProfile[] = new String[4];
    	//下面查询表里面的数据
    	boolean hasUserData;
    	Cursor cursor = sd.query("user", new String[]{"id","username","password","rememberpw"}, null,null, null, null, null,null);
    	
    	
    	while(cursor.moveToNext())
    	{
    		userProfile[0] = cursor.getString(cursor.getColumnIndex("username"));
    		userProfile[1] = cursor.getString(cursor.getColumnIndex("password"));
    		userProfile[2] = cursor.getString(cursor.getColumnIndex("id"));
    		userProfile[3] = cursor.getString(cursor.getColumnIndex("rememberpw"));
    		//userProfile[3]="0";
    		
    		System.out.println(userProfile[0]);
    	}
    	/*
    	if(cursor.isNull(0))
    	{
    		System.out.println("第一次使用该软件");
    		return null;
    		
    	}
    	*/
    	
    	return userProfile;
    	
    }
    
    //这个mybinder对象是为了取到service对象
    public class MyBinder extends Binder{  
        public InitializeService getService()  
        {  
            return InitializeService.this;  
        }  
    }  

}
