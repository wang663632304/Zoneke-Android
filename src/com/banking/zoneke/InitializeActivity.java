
package com.banking.zoneke;

import com.banking.SelfDefineLayout.OneCommentLayout;
import com.banking.SelfDefineLayout.SimpleTitleLayout;
import com.banking.db.ZonekeDatabaseHelper;
import com.banking.entity.Messages;

import com.banking.wholevariable.WholeVariable;
import com.banking.zoneke.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InitializeActivity extends Activity {
	
	
	
	private EditText userName;
	private EditText passWord;
	private Button registerButton;
	private Button loginButton;
	private CheckBox isRemberPassword;
	private String[] userProfile = new String[4];
	private InitializeService is;
	private static Context context ;
	private boolean hasInternet;
    /** Called when the activity is first created. */
	
	private ServiceConnection mServiceConnection = new ServiceConnection() {
		//当我bindService时，让TextView显示MyService里的方法的返回值	
		
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			is = ((InitializeService.MyBinder)service).getService();
			userProfile = is.getUserProfile();
			if(userProfile != null)
			{
				userName.setText(userProfile[0]);
				passWord.setText(userProfile[1]);
				if(userProfile[3]==null||userProfile[3].equals("1"))
				{
					isRemberPassword.setChecked(true);
				}
				else{
					isRemberPassword.setChecked(false);
				}
				WholeVariable.setNowUserId(userProfile[2]);
			}
			context.unbindService(this);
		}
		
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initiallogin);
        context=this.getBaseContext();
        
        hasInternet=true;
        if(!hasInternet(this))
		{
        	hasInternet =false;
        	dialog();
		}
		        
        
        WholeVariable.getList().add(this);
        //初始化地理位置
        String[] d=getLocationInfo();
        if(d!=null)
        {
        	WholeVariable.setLAT(d[0]);
        	WholeVariable.setLNG(d[1]);
        	System.out.println("新的经纬度");
        	System.out.println(d[0]+","+d[1]);
        }
        else{
        	System.out.println("未获得现在的经纬度");
        }
        context=getBaseContext();
        userName = (EditText)findViewById(R.id.userNameEditText);
        passWord = (EditText)findViewById(R.id.passWordEditText);
        registerButton=(Button)findViewById(R.id.registerButton);
        loginButton=(Button)findViewById(R.id.loginButton);
        isRemberPassword=(CheckBox)findViewById(R.id.isRemberPassword);
        
        registerButton.setOnClickListener(registerButtonListener);
        loginButton.setOnClickListener(loginButtonListener);
        
        //设置password隐藏和记住密码
        passWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
        //这个要从数据库中获取
        isRemberPassword.setChecked(true);
        
        Intent i  = new Intent();
		i.setClass(InitializeActivity.this, InitializeService.class);
		context.startService(i);
		context.bindService(i, mServiceConnection, BIND_AUTO_CREATE);
    }
    protected void dialog() { 
    	
        AlertDialog.Builder builder = new Builder(this); 
        builder.setMessage("未检测到网络！"); 
        builder.setTitle("提示"); 
        builder.setPositiveButton("设置", 
                new android.content.DialogInterface.OnClickListener() { 
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                    	//exitClient();
                    	//设置onrestart
                    	startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);//设置完毕会返回当前应用
                        dialog.dismiss(); 
                        
                    }

					
                }); 
        builder.setNegativeButton("忽略", 
                new android.content.DialogInterface.OnClickListener() { 
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                    	//提示会出现离线数据
                        dialog.dismiss(); 
                        Toast.makeText(getApplicationContext(), "将展示离线缓存数据！",
    		                    Toast.LENGTH_LONG).show();
                        
                    } 
                }); 
        builder.create().show(); 
    } 
    public static void exitClient(){
    	System.out.println("目前activity数量："+WholeVariable.getList().size());
    	for(int i=0;i<WholeVariable.getList().size();i++)
    	{
    		if(null!=WholeVariable.getList().get(i))
    		{
    			WholeVariable.getList().get(i).finish();
    		}
    	}
    	System.out.println("the programe finished!");
    	
    	ActivityManager am= (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
    	am.restartPackage("com.banking.aft.ActivitiesFunctionTestActivity");
    	System.exit(0);
    }
    private String[] getLocationInfo() { 
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
        Location location = locationManager 
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER); 
        String[] s=new String[2];
        if(location!=null){
        	java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.####");
        	//return 
        	/*
        	System.out.println(location.getLatitude());
        	System.out.println(location.getLongitude());
        	*/
        	s[0]=df.format(location.getLatitude());
        	s[1]=df.format(location.getLongitude());
        	return s;
        }
        else{
        	 WholeVariable.setGetRightLocation(false);
        	 return null;
        }
        
    }
    private OnClickListener registerButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//跳转到注册activity
			Intent toRegisterIntent = new Intent(InitializeActivity.this,RegisterActivity.class);
			startActivity(toRegisterIntent);
		}
    };
    public  boolean hasInternet(Activity activity) {

		ConnectivityManager manager = (ConnectivityManager) activity

				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = manager.getActiveNetworkInfo();

		if (info == null || !info.isConnected()) {

			return false;

		}

		if (info.isRoaming()) {

			// here is the roaming option you can change it if you want to

			// disable internet while roaming, just return false

			return true;

		}

		return true;

 

	}


    private OnClickListener loginButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//返回码为200的情况
			//处理数据库
			ZonekeDatabaseHelper dh = new ZonekeDatabaseHelper(InitializeActivity.this,"zoneke_db");
	    	//这儿，没有数据库会自动建立
	    	SQLiteDatabase sd = dh.getReadableDatabase();
			String userNameStr = userName.getText().toString();
			String passwordStr = passWord.getText().toString();
			//get from ret
			String userId = "2"; 
			
			Cursor cursor = sd.query("user", new String[]{"id","username"}, null,null, null, null, null,null);
			int count = 0;
			
			String remembered = "0";
			if(isRemberPassword.isChecked())
			{
				remembered="1";
			}
			
			while(cursor.moveToNext()){
				ContentValues values = new ContentValues();
				//这个id
	    		values.put("id",Integer.parseInt( userId));
	    		System.out.println(userProfile);
				values.put("username", userNameStr);
				
				values.put("password",passwordStr);
				values.put("rememberpw",remembered);
				System.out.println("update before");
				//这儿是凭借用户名识别的
				
				sd.update("user", values, "username=?", new String[]{userProfile[0]});
				System.out.println("update after");
				count++;
				
				
			}
			System.out.println(count);
			if(count==0)
	    	{
				System.out.println("insert new user values!");
	    		ContentValues values = new ContentValues();
				//下面是插入操作
				values.put("id", Integer.parseInt(userId));
				values.put("username", userNameStr);
				values.put("password",passwordStr);
				values.put("dota",1);
				values.put("eat",1);
				values.put("out",1);
				values.put("find",1);
				values.put("sport",1);
				values.put("scope",200);
				values.put("rememberpw","1");
				sd.insert("user", null, values);
				
	    	}
	    
			WholeVariable.setNowUserId(userId);
			WholeVariable.setNowUserName(userNameStr);
			
			Intent goHomepageIntent;
			goHomepageIntent = new Intent(InitializeActivity.this,ZoneKeActivity.class);
			
			//这儿判断是否有网络~
			if(!hasInternet)
			{
				Bundle bundle = new Bundle();
				bundle.putString("ifShowOfflineData", "yes");
				goHomepageIntent.putExtras(bundle);
			}
			startActivity(goHomepageIntent);
			InitializeActivity.this.finish();
		}
    };
    
}