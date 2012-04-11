package com.banking.zoneke;

import java.util.ArrayList;

import org.json.JSONObject;

import com.banking.SelfDefineLayout.SimpleTitleLayout;
import com.banking.entity.message;
import com.banking.gsonUtil.JsonDataGetApi;
import com.banking.wholevariable.WholeVariable;
import com.banking.zoneke.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewTitleActivity extends Activity {
    /** Called when the activity is first created. */
	
	private String titleTag="Dota";
	private EditText contentET;
	
	private SendMessageService sms;
	private Button submitButton;
	private Button atButton;
	private Button titleKindButton1;
	private Button titleKindButton2;
	private Button titleKindButton3;
	private Button titleKindButton4;
	private Button titleKindButton5;
	
	private TextView showTitleTextView;
	private TextView ntAuthorName;
	
	private ServiceConnection newTitleServiceConnection = new ServiceConnection() {
		//当我bindService时，让TextView显示MyService里getSystemTime()方法的返回值	
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			sms = ((SendMessageService.MyBinder)service).getService();
			
			sms.getJsonData();
			System.out.println("发送了一条新消息");
		
			getBaseContext().unbindService(this);
			NewTitleActivity.this.finish();
			
		}
		
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtitle);
        WholeVariable.getList().add(this);
        
        contentET=(EditText)findViewById(R.id.content);
        //contentET.addTextChangedListener(watcher);
        contentET.setOnKeyListener(contentETOnKeyListener);
        
        submitButton=(Button)findViewById(R.id.SubmitMessageButton);
        submitButton.setOnClickListener(submitButtonListener);
        atButton=(Button)findViewById(R.id.atButton);
        atButton.setOnClickListener(atButtonListener);
        
        
        titleKindButton1=(Button)findViewById(R.id.titleKindButton1);
        titleKindButton2=(Button)findViewById(R.id.titleKindButton2);
        titleKindButton3=(Button)findViewById(R.id.titleKindButton3);
        titleKindButton4=(Button)findViewById(R.id.titleKindButton4);
        titleKindButton5=(Button)findViewById(R.id.titleKindButton5);
        
        titleKindButton1.setOnClickListener(titleKindButtonListener);
        titleKindButton2.setOnClickListener(titleKindButtonListener);
        titleKindButton3.setOnClickListener(titleKindButtonListener);
        titleKindButton4.setOnClickListener(titleKindButtonListener);
        titleKindButton5.setOnClickListener(titleKindButtonListener);
        
        showTitleTextView=(TextView)findViewById(R.id.showTitleTextView);
        
        ntAuthorName =(TextView)findViewById(R.id.ntAuthorName);
        ntAuthorName.setText(WholeVariable.getNowUserName());
        ntAuthorName.append(":");
        
    }
    /*
    private TextWatcher watcher = new TextWatcher(){
		
		private char charValue ;
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			//charValue=s.charAt(start);
			System.out.println(s+""+start+""+before+""+count);
			System.out.println(s);
			System.out.println("new Char below");
			int len = s.length();
			String s1 = "";
			if(len != 0)
			{
			System.out.println(s.subSequence(len-1, len));
			
			s1=s.subSequence(len-1, len).toString();
			}
			if(s1.equals("@"))
			{
				System.out.println("@ altered!");
				
			}
		}	
		
	};
	*/
    private OnKeyListener contentETOnKeyListener = new OnKeyListener(){

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			//System.out.println("onkey");
			return false;
		}
    	
    };
    private OnClickListener submitButtonListener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			/*
			
			 //这是service写法，不用了
			Intent submitIntent= new Intent();;
			Bundle bundle=new Bundle();
			bundle.putString("tag", titleTag);
			bundle.putString("content",contentET.getText().toString());
			submitIntent.putExtras(bundle);
			submitIntent.setClass(NewTitleActivity.this, SendMessageService.class);
			startService(submitIntent);
			bindService(submitIntent, newTitleServiceConnection, BIND_AUTO_CREATE);
			*/
			JsonDataGetApi api = new JsonDataGetApi();
	        //ArrayList<JSONObject> jsonmessageAL = new ArrayList<JSONObject>();
	        //JSONArray jArr =null;
	        JSONObject jobj1;
	        JSONObject jobj2;
	        ArrayList<message> messageAL = new ArrayList<message>();
	        
	        try {
	            //调用GetData方法
	        	String userid;
	        	String lng=WholeVariable.getLNG();
	        	String lat=WholeVariable.getLAT();
	        	String expile;
	        	//String content = (EditText)
	        	String extentUrl="message/send/?lat="+lat+"&lng="+lng+"&id=1&tags="+titleTag+"&content="+contentET.getText().toString()+"&expile=24";
	        	String url="";
	        	url+=WholeVariable.getBaseUrl();
	        	url+=extentUrl;
	        	System.out.println(url);
	        	jobj1 = api.getObject(url);
	        	System.out.println("next");
	            //从返回的Account Array中取出第一个数据
	            String retNumber = jobj1.getString("ret");
	            
	            if(retNumber.equals("200"))
	            {
	            	System.out.println("成功发送消息");
	            	System.out.println("消息内容"+contentET.getText().toString());
	            	System.out.println("类别"+titleTag);
	            }
	            else{
	            	System.out.println("新消息发送失败，返回码"+retNumber);
	            }
	            /*
	             //这儿是检测消息的
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
	        	System.out.println("发送消息异常");
	        	System.out.println("消息内容"+contentET.getText().toString());
            	System.out.println("类别"+titleTag);
	        	System.out.println(e.getMessage());
	            e.printStackTrace();
	          
	        }
	        finally{
	        	NewTitleActivity.this.finish();
	        }
		}
		
	};
	private OnClickListener atButtonListener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			titleKindButton1.setVisibility(0);
			titleKindButton2.setVisibility(0);
			titleKindButton3.setVisibility(0);
			titleKindButton4.setVisibility(0);
			titleKindButton5.setVisibility(0);
			
		}
		
	};
	public void hindButton(){
		titleKindButton1.setVisibility(4);
		titleKindButton2.setVisibility(4);
		titleKindButton3.setVisibility(4);
		titleKindButton4.setVisibility(4);
		titleKindButton5.setVisibility(4);
	}
	private OnClickListener titleKindButtonListener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			if(v.getId()==R.id.titleKindButton1)
			{
				showTitleTextView.setText("Dota");
				titleTag="Dota";
				hindButton();
			}
			if(v.getId()==R.id.titleKindButton2)
			{
				showTitleTextView.setText("吃饭");
				titleTag="吃饭";
				hindButton();
			}
			if(v.getId()==R.id.titleKindButton3)
			{
				showTitleTextView.setText("出行");
				titleTag="出行";
				hindButton();
			}
			if(v.getId()==R.id.titleKindButton4)
			{
				showTitleTextView.setText("寻物");
				titleTag="寻物";
				hindButton();
			}
			if(v.getId()==R.id.titleKindButton5)
			{
				showTitleTextView.setText("运动");
				titleTag="运动";
				hindButton();
			}
			
			
		}
		
	};
}