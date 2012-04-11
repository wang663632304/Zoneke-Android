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
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;
public class GetSimpleTitlesService extends Service{

static final String TAG = "MyService"; 
	
	private MyBinder mBinder = new MyBinder();
	private String scopeValue = "1000";
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.e(TAG, "start simpleTitleService binder");
		
		Bundle bundle = arg0.getExtras();
		//scopeValue = bundle.getString("scope");
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

	
	//onStart�������Բ���д
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		Log.e(TAG, "start onStart");
		super.onStart(intent, startId);
	}
	
	//����дһ�������ɸ�����

	//����ĳɷ���list������
    public  Messages getJsonData() {
        JsonDataGetApi api = new JsonDataGetApi();
        ArrayList<JSONObject> jsonmessageAL = new ArrayList<JSONObject>();
        JSONArray jArr =null;
        JSONObject jobj1;
        JSONObject jobj2;
       
        Messages messages = new Messages();
       
        try {
            //����GetData����
        	String userid;
        	String lng=WholeVariable.getLNG();
        	String lat=WholeVariable.getLAT();
        	String scope;
        	//һ���Ի�ȡ����message
        	String extentUrl="message/get/?id=1&lat="+lat+"&lng="+lng+"&scope=1";
        	String url="";
        	url+=WholeVariable.getBaseUrl();
        	url+=extentUrl;
        	jobj1 = api.getObject(url);
        	
            //�ӷ��ص�Account Array��ȡ����һ������
            String jname = jobj1.getString("ret");
            jArr=jobj1.getJSONObject("data").getJSONArray("messages");
            int count  = jArr.length();
            System.out.println("��Ϣ������");
            System.out.println(count);
            
            
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            for(int y=0;y<count;y++)
            {
            	jobj2=jArr.getJSONObject(y).getJSONObject("message");
            	message m1 = gson.fromJson(jobj2.toString(), message.class);
            	//System.out.println(m1.getTags());
            	if(y==0)
            	{
            		System.out.println("��һ����Ϣ");
            		System.out.println(m1);
            	}
            	if(m1.getTags()[0].contains("Dota"))
            	{
            		messages.getDotamessageAL().add(m1);
            	}
            	if(m1.getTags()[0].contains("�Է�"))
            	{
            		messages.getEatmessageAL().add(m1);
            	}
            	if(m1.getTags()[0].contains("����"))
            	{
            		messages.getOutmessageAL().add(m1);
            	}
            	if(m1.getTags()[0].contains("Ѱ��"))
            	{
            		messages.getFindmessageAL().add(m1);
            	}
            	if(m1.getTags()[0].contains("�˶�"))
            	{
            		messages.getSportmessageAL().add(m1);
            	}
            	//messageAL.add(m1);
            	//System.out.println(m1.getNum());
            }
            
            
            
            return messages;
            

        } 
        catch (Exception e) {
        	System.out.println("parse error");
        	/*
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
                    */
        	System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
            //TextView movie_Address = (TextView) findViewById(R.id.Address);
            //movie_Address.setText(e.getMessage());
        }
        
    }
    
    public void fillTitles(View v,Messages messages1){
    	
		Context context = getBaseContext();
		LinearLayout mainLayout = (LinearLayout)v.findViewById(R.id.mainContentLayout);
        
		 mainLayout.removeAllViewsInLayout();
		
		//����ֻ���õ�һ���~�����ģ���ҳʱ����
		for(int x = 0;x<messages1.getDotamessageAL().size();x++)
		{
			SimpleTitleLayout st = new SimpleTitleLayout(context,GetSimpleTitlesService.this,messages1.getDotamessageAL().get(x));
	        st.setPeopleName(messages1.getDotamessageAL().get(x).getUsername());
	        st.setTitleTime(messages1.getDotamessageAL().get(x).getTime());
	        st.setTitleContent(messages1.getDotamessageAL().get(x).getContent());
	        //st.setMinimumWidth(480);
	        
	        System.out.println("mainLayout");
	        System.out.println(mainLayout.getWidth());
	        mainLayout.addView(st, new LinearLayout.LayoutParams(
	        		LinearLayout.LayoutParams.MATCH_PARENT,
	        		LinearLayout.LayoutParams.MATCH_PARENT));
		}
		//�������ؿհ�
		TextView emptyTv = new TextView(context);
		emptyTv.setText("ssd");
        //emptyTv.setHeight(200);
        emptyTv.setMinHeight(70);
        mainLayout.addView(emptyTv, new LinearLayout.LayoutParams(
        		LinearLayout.LayoutParams.WRAP_CONTENT,
        		LinearLayout.LayoutParams.WRAP_CONTENT));
    }
    //���mybinder������Ϊ��ȡ��service����
    public class MyBinder extends Binder{  
        public GetSimpleTitlesService getService()  
        {  
            return GetSimpleTitlesService.this;  
        }  
    }  

}
