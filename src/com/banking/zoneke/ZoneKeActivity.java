package com.banking.zoneke;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


import com.banking.SelfDefineLayout.*;



import com.banking.db.ZonekeDatabaseHelper;
import com.banking.db.ZonekeTitleDatabaseHelper;
import com.banking.entity.Messages;
import com.banking.entity.message;

import com.banking.wholevariable.WholeVariable;
import com.banking.zoneke.R;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class ZoneKeActivity extends Activity {
    /** Called when the activity is first created. */
	
	private boolean needGetData=true;
	private ViewPager myViewPager;
	private MyPagerAdapter myAdapter;
	private LayoutInflater mInflater;
	private List<View> mListViews;
	private View layout1 = null;
	private View layout2 = null;
	private View layout3 = null;
	private View layout4 = null;
	private View layout5 = null;
	
	static Context context;
	private LinearLayout titlesLayout;
	private View nowView;
	private int nowViewNumber;
	private String nowTag; 
	private static Messages nowAllMessages;
	private ArrayList<message> nowMessages;
	private boolean ifShowOfflineData;
	
	private Button newTitleButton;
	private RatingBar mainRatingBar;
	
	
	private GetSimpleTitlesService gsts; 
	//private ArrayList<message> nowMessages;
	private Messages messages = new Messages();
	private ServiceConnection mServiceConnection = new ServiceConnection() {
		//����bindServiceʱ����TextView��ʾMyService��ķ����ķ���ֵ	
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			
			if(!ifShowOfflineData)
			{
				gsts = ((GetSimpleTitlesService.MyBinder)service).getService();
				
				
				Messages messages1=gsts.getJsonData();
				System.out.println("������ڵ�message");
				System.out.println(messages1);
				nowAllMessages = messages1;
			}
			
			//System.out.println(nowView);
			System.out.println("nowViewNumber"+nowViewNumber);
			
			
			fillTitles(nowView,nowAllMessages ,nowViewNumber);
			context.unbindService(this);
			
			//service��Ҫunbind�������´β��ܼ���ִ�д��롣
			
		}
		
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getBaseContext();
        setContentView(R.layout.main);
        setTitle("Zoneke��ҳ");
        WholeVariable.getList().add(this);
        
        ifShowOfflineData =false;
        
        Bundle bundle1=getIntent().getExtras();
        if(bundle1!=null)
        {
        	String value1=bundle1.getString("NeedGetData");
        	if(value1!=null&&value1.equals("no"))
        		needGetData=false;
        	String value2=bundle1.getString("ifShowOfflineData");
        	if(value2!=null&&value2.equals("yes"))
        		ifShowOfflineData=true;
        }
        //���⿴ʱ�����������
        if(ifShowOfflineData)
        {
        	System.out.println("�����������");
        	fillOfflineData();
        	
        	System.out.println("�����������Ok");
        	System.out.println("��ʱ��alldata");
        	System.out.println(nowAllMessages);//����tostring����
        	
        }
        
        
        for(int t=0;t<5;t++)
        {
        	WholeVariable.getViewLoading()[t]=false;
        }
        
        //�ӵĴ���
        myAdapter = new MyPagerAdapter();
		myViewPager = (ViewPager) findViewById(R.id.viewpagerMainLayout);
		myViewPager.setAdapter(myAdapter);
        
        mListViews = new ArrayList<View>();
        mInflater = getLayoutInflater();
        
        //Ŀǰ������5��layout���ֱ���dota,�Է�,����,Ѱ��,�˶�
        layout1 = mInflater.inflate(R.layout.mainpage1, null);
        layout2 = mInflater.inflate(R.layout.mainpage2, null);
        layout3 = mInflater.inflate(R.layout.mainpage3, null);
        layout4 = mInflater.inflate(R.layout.mainpage4, null);
        layout5 = mInflater.inflate(R.layout.mainpage5, null);
       
        mListViews.add(layout1);
        mListViews.add(layout2);
        mListViews.add(layout3);
        mListViews.add(layout4);
        mListViews.add(layout5);
        
        
        //��ʼ����ǰ��ʾ��view
        myViewPager.setCurrentItem(0);
        titlesLayout = (LinearLayout) layout1.findViewById(R.id.mainContentLayout);
    	nowView = layout1;
        nowViewNumber = 0;
        nowTag="dota";
        
        //��ʼ���ڶ���view����Ϣ
        //EditText v2EditText = (EditText)layout2.findViewById(R.id.TesteditText1);
        //v2EditText.setText("��̬���õڶ���view��ֵ");
        
        myViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				System.out.println("onPageSelected"+arg0 );
				Log.d("k", "onPageSelected - " + arg0);
				//activity��1��2������2�����غ���ô˷���
				View v = mListViews.get(arg0);
				nowView = v;
				//EditText editText = (EditText)v.findViewById(R.id.TesteditText1);
				//editText.setText("��̬����#"+arg0+"edittext�ؼ���ֵ");
				
				nowViewNumber = arg0;
				if(arg0==0)nowTag="dota";
				if(arg0==1)nowTag="eat";
				if(arg0==2)nowTag="out";
				if(arg0==3)nowTag="find";
				if(arg0==4)nowTag="sport";
				System.out.println(nowTag);
				
				if(!WholeVariable.getViewLoading()[arg0])
				{
				
					fillBlanks(nowView);
					//bindTheService("",nowTag);
					fillTitles(nowView,nowAllMessages,nowViewNumber);
					WholeVariable.getViewLoading()[arg0]=true;
				}
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				Log.d("k", "onPageScrolled - " + arg0);
				//��1��2��������1����ǰ����
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				Log.d("k", "onPageScrollStateChanged - " + arg0);
				//״̬������0���У�1�����ڻ����У�2Ŀ��������
				/**
			     * Indicates that the pager is in an idle, settled state. The current page
			     * is fully in view and no animation is in progress.
			     */
			    //public static final int SCROLL_STATE_IDLE = 0;
			    /**
			     * Indicates that the pager is currently being dragged by the user.
			     */
			    //public static final int SCROLL_STATE_DRAGGING = 1;
			    /**
			     * Indicates that the pager is in the process of settling to a final position.
			     */
			    //public static final int SCROLL_STATE_SETTLING = 2;

			}
		});
        
        
        if(needGetData){
       
	        System.out.println("ˢ��������");
	        
	        Intent i  = new Intent();
			i.setClass(ZoneKeActivity.this, GetSimpleTitlesService.class);
			Bundle bundle = new Bundle();
			bundle.putString("scope",WholeVariable.getScope() );
			
			i.putExtras(bundle);
			context.startService(i);
			context.bindService(i, mServiceConnection, BIND_AUTO_CREATE);
			WholeVariable.getViewLoading()[0]=true;
			System.out.println("service ��ʼ��");
        //��̨����Ҫ�ŵ�service����
            
        }
        
        System.out.println("oncreate���鿴alldata");
        System.out.println(nowAllMessages);
        //��ÿ��button�����¼�
		//�����������
        for(int i0 =0;i0<mListViews.size();i0++)
        {
        	(mListViews.get(i0).findViewById(R.id.newTitleButton)).
        	setOnClickListener(newTitleButtonListener);
        	
        	
        	//��������ת����������
        	(mListViews.get(i0).findViewById(R.id.bIButton3)).
        	setOnClickListener(bIButton3Listener);
        	
        	//���ĸ�������
        	(mListViews.get(i0).findViewById(R.id.bIButton4)).
        	setOnClickListener(bIButton4Listener);
        	
        	//Ϊratingbar���¼�
        	((RatingBar)(mListViews.get(i0).findViewById(R.id.mainRatingBar))).
        	setOnRatingBarChangeListener(rbchangeListener);
        	
        }
        
        
       
    }
    public void fillOfflineData(){
    	ZonekeTitleDatabaseHelper dh = new ZonekeTitleDatabaseHelper(ZoneKeActivity.this,"zoneke_data_db");
    	//�����û�����ݿ���Զ�����
    	SQLiteDatabase sd = dh.getReadableDatabase();
		
		
		Cursor cursor = sd.query("dotadata", new String[]{"username","content","time"}, null,null, null, null, null,null);
		int count = 0;
		
		
		ArrayList<message> dotames = new ArrayList<message>();
		while(cursor.moveToNext())
    	{
    		String s1 = cursor.getString(cursor.getColumnIndex("username"));
    		String s2 = cursor.getString(cursor.getColumnIndex("content"));
    		String s3 = cursor.getString(cursor.getColumnIndex("time"));
    		
    		//userProfile[3]="0";
    		//�����Ҫ�������ݿ⡣�����һ����ǡ�
    		//����ָ����dota
    		message m = new message();
    		m.setUsername(s1);
    		m.setContent(s2);
    		m.setTime(s3);
    		dotames.add(m);
    		
    		System.out.println("��¼һ����������");
    	}
		
		Messages ms= new Messages();
		ms.setDotamessageAL(dotames);
		nowAllMessages = ms;
    }
    
    public void onRestart(){
    	System.out.println("Activity On Restart");
    	//���濽������
    	Intent i  = new Intent();
		 i.setClass(ZoneKeActivity.this, GetSimpleTitlesService.class);
		 Bundle bundle = new Bundle();
		 bundle.putString("scope",WholeVariable.getScope());
			
		 i.putExtras(bundle);
		 context.startService(i);
		 context.bindService(i, mServiceConnection, BIND_AUTO_CREATE);
		 
		 //�ٰ�����loading�ĳ�δ���
		 for(int t=0;t<5;t++)
	        {
	        	WholeVariable.getViewLoading()[t]=false;
	        }
		 
    	super.onRestart();
    }
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
    	System.out.println("Activity On Resume");
		super.onResume();
	}

	public void onStart(){
    	System.out.println("Activity On Start");
    	super.onStart();
    }
    /*
    public void onResume(){
    	System.out.println("Activity On Resume");
    }
    */
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
    	//������ʾ�������˳���
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
            dialog(); 
            return false; 
        } 
        /*
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
        	Toast.makeText(getApplicationContext(), "�ٰ�һ�η��ؼ��˳�",
                    Toast.LENGTH_LONG).show(); 
            return false; 
        } 
        */
        return false; 
    }

    protected void dialog() { 
    	
        AlertDialog.Builder builder = new Builder(ZoneKeActivity.this); 
        builder.setMessage("ȷ��Ҫ�˳���?"); 
        builder.setTitle("��ʾ"); 
        builder.setPositiveButton("ȷ��", 
                new android.content.DialogInterface.OnClickListener() { 
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                    	exitClient();
                        dialog.dismiss(); 
                        
                    }

					
                }); 
        builder.setNegativeButton("ȡ��", 
                new android.content.DialogInterface.OnClickListener() { 
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        dialog.dismiss(); 
                        
                    } 
                }); 
        builder.create().show(); 
    } 

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "����");
		menu.add(0, 2, 2, "�˳�");
		
		return super.onCreateOptionsMenu(menu);
		
	}
    
  
   @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==1)
		{
			System.out.println("About");
		}
		if(item.getItemId()==2)
		{
			dialog();
		}
		
		return super.onOptionsItemSelected(item);
	}
   public void addDataToDatabase(String databaseName,ArrayList<message> targetMessages){
	   ZonekeTitleDatabaseHelper dh = new ZonekeTitleDatabaseHelper(ZoneKeActivity.this,"zoneke_data_db");
	   	//�����û�����ݿ���Զ�����
	    
	   	SQLiteDatabase sd = dh.getReadableDatabase();
	   	
	   	
	   	//��������.����������֮ǰ��Ҫ���ԭ����
	   	//����û�и��õķ�ʽ��
	   	//sd.execSQL("drop table dotadata");
	   	//sd.execSQL("create table dotadata( id int,username char(20),content text,time date)");
	   	sd.execSQL("delete from dotadata");
	   	
	   	System.out.println("insert new dotadata values!");
		ContentValues values = new ContentValues();
		
		//�����ǲ������
		for(int i =0;i<targetMessages.size();i++)
		{
			values.put("username",targetMessages.get(i).getUsername());
			values.put("content", targetMessages.get(i).getContent());
			values.put("time",targetMessages.get(i).getTime());
			
			sd.insert(databaseName, null, values);
		}
   }
    public  void exitClient(){  //ȥ����static
    	//�����ﱣ������
    	
    	if(nowAllMessages != null)
    	{
    		
	    	ArrayList<message> targetMessages1=nowAllMessages.getDotamessageAL();
	    	//�ȴ����һ��,dota����
	    	System.out.println("�˳�ǰ��������1");
	    	addDataToDatabase("dotadata",targetMessages1);
	    	System.out.println("�˳�ǰ��������2");
	    	
	    	ArrayList<message> targetMessages2=nowAllMessages.getEatmessageAL();
	    	ArrayList<message> targetMessages3=nowAllMessages.getOutmessageAL();
	    	ArrayList<message> targetMessages4=nowAllMessages.getFindmessageAL();
	    	ArrayList<message> targetMessages5=nowAllMessages.getSportmessageAL();
    	}
    	else{
    		System.out.println("��ǰ��Ϣ��Ϊ�㡣û�б�������");
    	}
    	
    	
    	
    	
    	System.out.println("Ŀǰactivity������"+WholeVariable.getList().size());
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
    private class MyPagerAdapter extends PagerAdapter{

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			Log.d("k", "destroyItem");
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
			Log.d("k", "finishUpdate");
		}

		@Override
		public int getCount() {
			Log.d("k", "getCount");
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			Log.d("k", "instantiateItem");
			((ViewPager) arg0).addView(mListViews.get(arg1),0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			Log.d("k", "isViewFromObject");
			return arg0==(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			Log.d("k", "restoreState");
		}

		@Override
		public Parcelable saveState() {
			Log.d("k", "saveState");
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			Log.d("k", "startUpdate");
		}
    	
    }
    
    //�������������ŵ�service��
    public void fillTitles(View v,Messages messgaes1,int number){
    	if(messgaes1 != null)
			messages = messgaes1;
		
    	
    	
		LinearLayout mainLayout = (LinearLayout)v.findViewById(R.id.mainContentLayout);
		mainLayout.removeAllViewsInLayout();
		ArrayList<message> targetMessages=null;
		//����ֻ���õ�һ���~�����ģ���ҳʱ����
		switch(number){
		case 0: targetMessages=messages.getDotamessageAL();break;
		case 1: targetMessages=messages.getEatmessageAL();break;
		case 2: targetMessages=messages.getOutmessageAL();break;
		case 3: targetMessages=messages.getFindmessageAL();break;
		case 4: targetMessages=messages.getSportmessageAL();break;
		}
		nowMessages = targetMessages;
		
		System.out.println("now fill titles");
    	System.out.println("number="+number);
    	System.out.println("message count="+targetMessages.size());
		//���nowMessagesΪ�գ���ô���롰������Ϣ��ʾ��
    	
    	
		if(nowMessages.size()==0)
		{
			TextView noInfoTv = new TextView(context);
	        noInfoTv.setHeight(50);
	        noInfoTv.setText("������Ϣ");
	        mainLayout.addView(noInfoTv, new LinearLayout.LayoutParams(
	        		LinearLayout.LayoutParams.WRAP_CONTENT,
	        		LinearLayout.LayoutParams.WRAP_CONTENT));
		}
		System.out.println(mainLayout);
		for(int x = 0;x<targetMessages.size();x++) //��targetΪnow
		{
			SimpleTitleLayout st = new SimpleTitleLayout(context,ZoneKeActivity.this,targetMessages.get(x));
	        st.setPeopleName(targetMessages.get(x).getUsername());
	        st.setTitleTime(targetMessages.get(x).getTime());
	        st.setTitleContent(targetMessages.get(x).getContent());
	        st.setPadding(5, 15, 5, 5);
	        mainLayout.addView(st, new LinearLayout.LayoutParams(
	        		LinearLayout.LayoutParams.MATCH_PARENT,
	        		LinearLayout.LayoutParams.MATCH_PARENT));
	        //System.out.println(st.getWidth());
	        //System.out.println(st.getHeight());
		}
		//�������ؿհ�
		TextView emptyTv = new TextView(context);
        emptyTv.setHeight(80);
        mainLayout.addView(emptyTv, new LinearLayout.LayoutParams(
        		LinearLayout.LayoutParams.WRAP_CONTENT,
        		LinearLayout.LayoutParams.MATCH_PARENT));
    }
    //��Ҫ����view����
    public void fillBlanks(View v){
    	titlesLayout = (LinearLayout)v.findViewById(R.id.mainContentLayout);
    	titlesLayout.removeAllViewsInLayout();//����ǲ���ȥ��������Ԫ���أ�
    	TitleListLoadingLayout tl = new TitleListLoadingLayout(context);
    	LinearLayout mainLayout = (LinearLayout)v.findViewById(R.id.mainContentLayout);
    	 mainLayout.addView(tl, new LinearLayout.LayoutParams(
         		LinearLayout.LayoutParams.WRAP_CONTENT,
         		LinearLayout.LayoutParams.WRAP_CONTENT));
    	
    }
 
    private OnClickListener newTitleButtonListener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent newTitleIntent;
			//Bundle bundle=new Bundle();
			newTitleIntent=new Intent(ZoneKeActivity.this,NewTitleActivity.class);
			
			//newTitleIntent.putExtras(bundle);
			
			startActivity(newTitleIntent);
			
		}
		
	};
	
	private OnClickListener bIButton3Listener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent newTitleIntent;
			//Bundle bundle=new Bundle();
			newTitleIntent=new Intent(ZoneKeActivity.this,NewTitleActivity.class);
			
			//newTitleIntent.putExtras(bundle);
			startActivity(newTitleIntent);
			
		}
		
	};
	private OnClickListener bIButton4Listener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent newTitleIntent;
			
			newTitleIntent=new Intent(ZoneKeActivity.this,SettingActivity.class);
			startActivity(newTitleIntent);
			
		}
		
	};
	private OnRatingBarChangeListener rbchangeListener = new OnRatingBarChangeListener(){

		@Override
		public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
			// TODO Auto-generated method stub
			//���ȣ���ratingbar��Ϊ���ɵ��������loading
			 //Toast.makeText(ZoneKeActivity.this, "new rating:"+arg1, Toast.LENGTH_SHORT).show();
			System.out.println("new rating:"+arg1) ;
			fillBlanks(nowView);
			 
			 String scopeValue = "1000";
			 if(arg1==1) scopeValue="100";
			 if(arg1==2) scopeValue="200";
			 if(arg1==3) scopeValue="500";
			 if(arg1==4) scopeValue="1000";
			 WholeVariable.setScope(scopeValue);
			 
			 
			 //���ʹ�ù�ʽɸѡ��,���ٷ��ʷ�����
			 //�����Ҫmessage�м���һ�����ԡ��������Ļ�ֻ��ͨ��������ɸѡ
			 //bindTheService(scopeValue,"");
			 Intent i  = new Intent();
			 i.setClass(ZoneKeActivity.this, GetSimpleTitlesService.class);
			 Bundle bundle = new Bundle();
			 bundle.putString("scope",WholeVariable.getScope());
				
			 i.putExtras(bundle);
			 context.startService(i);
			 context.bindService(i, mServiceConnection, BIND_AUTO_CREATE);
			
		}
		
	};
}