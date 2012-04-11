package com.banking.zoneke;



import java.util.ArrayList;

import com.banking.SelfDefineLayout.SimpleTitleLayout;
import com.banking.entity.message;
import com.banking.gsonUtil.JsonDataGetApi;
import com.banking.wholevariable.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
public class SendMessageService extends Service{

static final String TAG = "MyService"; 
	
	private MyBinder mBinder = new MyBinder();
	
	private String contentStr;
	private String tagStr;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.e(TAG, "start simpleTitleService binder");
		
		Bundle extras=arg0.getExtras();
		if(extras==null)
		{
			System.out.println("Null extras");
		}
		else{
			contentStr=extras.getString("content");
			tagStr=extras.getString("tag");
			System.out.println("in service get data");
			System.out.println(tagStr);
		}
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
		//Bundle extras=getIntent().getExtras();
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

	
	//onStart�������Բ���д
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		Log.e(TAG, "start onStart");
		super.onStart(intent, startId);
	}
	
	//����дһ�������ɸ�����

    public  void getJsonData() {
        JsonDataGetApi api = new JsonDataGetApi();
        //ArrayList<JSONObject> jsonmessageAL = new ArrayList<JSONObject>();
        //JSONArray jArr =null;
        JSONObject jobj1;
        JSONObject jobj2;
        ArrayList<message> messageAL = new ArrayList<message>();
        
        try {
            //����GetData����
        	String userid;
        	String lng=WholeVariable.getLNG();
        	String lat=WholeVariable.getLAT();
        	String expile;
        	//String content = (EditText)
        	String extentUrl="message/send/?lat="+lat+"&lng="+lng+"&id=1&tags="+tagStr+"&content="+contentStr+"&expile=24";
        	String url="";
        	url+=WholeVariable.getBaseUrl();
        	url+=extentUrl;
        	jobj1 = api.getObject(url);
        	
            //�ӷ��ص�Account Array��ȡ����һ������
            String retNumber = jobj1.getString("ret");
            
            if(retNumber.equals("200"))
            {
            	System.out.println("�ɹ�������Ϣ");
            	System.out.println("��Ϣ����"+contentStr);
            	System.out.println("���"+tagStr);
            }
            
            /*
             //����Ǽ����Ϣ��
            jobj2=jobj1.getJSONObject("data").getJSONObject("mid");
            String messageId = jobj2.toString();
            System.out.println(messageId);
          
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            */

        } catch (Exception e) {
        	/*
        	Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
                    */
        	System.out.println("������Ϣ�쳣");
        	System.out.println("��Ϣ����"+contentStr);
        	System.out.println("���"+tagStr);
        	System.out.println(e.getMessage());
            e.printStackTrace();
          
        }
        
    }
    
    //���mybinder������Ϊ��ȡ��service����
    public class MyBinder extends Binder{  
        public SendMessageService getService()  
        {  
            return SendMessageService.this;  
        }  
    }  

}
