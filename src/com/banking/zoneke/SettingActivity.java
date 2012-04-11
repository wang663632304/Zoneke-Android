package com.banking.zoneke;

import com.banking.db.ZonekeDatabaseHelper;
import com.banking.wholevariable.WholeVariable;
import com.banking.zoneke.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class SettingActivity extends Activity {
    /** Called when the activity is first created. */
	//private EditText contentET;
	private CheckBox cb1;
	private CheckBox cb2;
	private CheckBox cb3;
	private CheckBox cb4;
	private CheckBox cb5;
	private TextView scopeTextView;
	private TextView locationTv;
	private RatingBar scopeRatingBar;
	private Button settingResetButton;
	private Button settingOKButton;
	private Button changeLocButton;
	private String forUpdateUserName="";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        WholeVariable.getList().add(this);
        
        cb1=(CheckBox)findViewById(R.id.checkbox1);
        cb2=(CheckBox)findViewById(R.id.checkbox2);
        cb3=(CheckBox)findViewById(R.id.checkbox3);
        cb4=(CheckBox)findViewById(R.id.checkbox4);
        cb5=(CheckBox)findViewById(R.id.checkbox5);
        scopeTextView=(TextView)findViewById(R.id.scopeTextView);
        locationTv=(TextView)findViewById(R.id.locationTv);
        scopeRatingBar=(RatingBar)findViewById(R.id.ratingBar1);
        settingResetButton=(Button)findViewById(R.id.settingResetButton);
        settingOKButton=(Button)findViewById(R.id.settingOKButton);
        changeLocButton=(Button)findViewById(R.id.changeLocationButton);
        
        settingResetButton.setOnClickListener(settingResetOnClickListener);
        settingOKButton.setOnClickListener(settingOKOnClickListener);
        scopeRatingBar.setOnRatingBarChangeListener(rbchangeListener);
        changeLocButton.setOnClickListener(changeLoactionListener);
        InitializeSetting();
    	
    	
    }
    
    public void InitializeSetting(){
    	
    	
    	//初始化经纬度
    	if(WholeVariable.isGetRightLocation())
    	{
    		locationTv.setText("经度："+WholeVariable.getLAT()+"纬度"+WholeVariable.getLNG());
    	}
    	else{
    		locationTv.setText("获取位置失败，取默认值："+"经度："+WholeVariable.getLAT()+"纬度"+WholeVariable.getLNG());
    	}
    	
    	ZonekeDatabaseHelper dh = new ZonekeDatabaseHelper(SettingActivity.this,"zoneke_db");
    	//这儿，没有数据库会自动建立
    	SQLiteDatabase sd = dh.getReadableDatabase();
    	
    	int userInterests[] = new int[5];
    	int scopeInt=0;
    	//下面查询表里面的数据
    	boolean hasUserData;
    	Cursor cursor = sd.query("user", new String[]{"username","dota","eat","out","find","sport","scope"}, null,null, null, null, null);
    	
    	
    	while(cursor.moveToNext())
    	{
    		userInterests[0] = cursor.getInt(cursor.getColumnIndex("dota"));
    		if(userInterests[0]==1)cb1.setChecked(true);
    		userInterests[1] = cursor.getInt(cursor.getColumnIndex("eat"));
    		if(userInterests[1]==1)cb2.setChecked(true);
    		userInterests[2] = cursor.getInt(cursor.getColumnIndex("out"));
    		if(userInterests[2]==1)cb3.setChecked(true);
    		userInterests[3] = cursor.getInt(cursor.getColumnIndex("find"));
    		if(userInterests[3]==1)cb4.setChecked(true);
    		userInterests[4] = cursor.getInt(cursor.getColumnIndex("sport"));
    		if(userInterests[4]==1)cb5.setChecked(true);
    		
    		scopeInt=cursor.getInt(cursor.getColumnIndex("scope"));
    		//为什么不可以用？
    		//scopeTextView.setText(scopeInt);
    		scopeTextView.setText(String.valueOf(scopeInt));
    		switch(scopeInt){
	    		case 100: scopeRatingBar.setRating((float) 1.0);break;
	    		case 200: scopeRatingBar.setRating((float) 2.0);break;
	    		case 500: scopeRatingBar.setRating((float) 3.0);break;
	    		case 1000: scopeRatingBar.setRating((float) 4.0);break;
    		}
    		
    		System.out.println(scopeInt);
    		System.out.println(userInterests[2]);
    		
    		forUpdateUserName=cursor.getString(cursor.getColumnIndex("username"));
    	}
    	
    }
    private OnClickListener settingResetOnClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//目前是回复默认
			InitializeSetting();
			
		}
    	
    };
    private OnClickListener settingOKOnClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//提交新数据入库，跳转页面
			int datas[] =new int[6];
			if(cb1.isChecked()) datas[0]=1;
			else datas[0]=0;
			if(cb2.isChecked()) datas[1]=1;
			else datas[1]=0;
			if(cb3.isChecked()) datas[2]=1;
			else datas[2]=0;
			if(cb4.isChecked()) datas[3]=1;
			else datas[3]=0;
			if(cb5.isChecked()) datas[4]=1;
			else datas[4]=0;
			
			float scopeFloat =  scopeRatingBar.getRating();
			if(scopeFloat ==1.0) datas[5]=100;
			if(scopeFloat ==2.0) datas[5]=200;
			if(scopeFloat ==3.0) datas[5]=500;
			if(scopeFloat ==4.0) datas[5]=1000;
		
			
			ZonekeDatabaseHelper dh = new ZonekeDatabaseHelper(SettingActivity.this,"zoneke_db");
	    	//这儿，没有数据库会自动建立
	    	SQLiteDatabase sd = dh.getReadableDatabase();
			ContentValues values = new ContentValues();
			
    		values.put("dota",datas[0]);
    		
			values.put("eat", datas[1]);
			
			values.put("out",datas[2]);
			values.put("find",datas[3]);
			values.put("sport",datas[4]);
			values.put("scope",datas[5]);
			System.out.println("update interests before");
			//这儿是凭借用户名识别的
			sd.update("user", values, "username=?", new String[]{forUpdateUserName});
			System.out.println("update interests after");
			
			
			//这儿把数据存到全局变量中
			WholeVariable.setScope(String.valueOf(datas[5]));
			WholeVariable.setInterests(datas);
			
			Intent goHomepageIntent;
			//要有一个防止重复刷网络数据的功能，通过extras
			Bundle bundle = new Bundle();
			bundle.putString("NeedGetData", "no");
			goHomepageIntent = new Intent(SettingActivity.this,ZoneKeActivity.class);
			goHomepageIntent.putExtras(bundle);
			
			startActivity(goHomepageIntent);
			SettingActivity.this.finish();
			
			
		}
    	
    };
    private OnRatingBarChangeListener rbchangeListener = new OnRatingBarChangeListener(){

		@Override
		public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
			// TODO Auto-generated method stub
			 String scopeValue="0";
			 if(arg1==1) scopeValue="100";
			 if(arg1==2) scopeValue="200";
			 if(arg1==3) scopeValue="500";
			 if(arg1==4) scopeValue="1000";
			 scopeTextView.setText(scopeValue);
		}
    
    };
    private String[] getLocationInfo() { 
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
        Location location = locationManager 
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER); 
        String[] s=new String[2];
        if(location!=null){
        	java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.####");
        	//return 
        	s[0]=df.format(location.getLatitude());
        	s[1]=df.format(location.getLongitude());
        	return s;
        }
        else return null;
    }
    private OnClickListener changeLoactionListener  = new OnClickListener(){

    	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 String[] d=getLocationInfo();
		        if(d!=null)
		        {
		        	WholeVariable.setLAT(d[0]);
		        	WholeVariable.setLNG(d[1]);
		        	System.out.println("新的经纬度");
		        	System.out.println(d[0]+","+d[1]);
		        }
		     locationTv.setText("经度："+WholeVariable.getLAT()+"纬度"+WholeVariable.getLNG());
		}
    
    };
    
}