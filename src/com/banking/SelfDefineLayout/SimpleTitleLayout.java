package com.banking.SelfDefineLayout;  
import com.banking.entity.message;
import com.banking.zoneke.*;

import android.app.Activity;
import android.content.Context;   
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;   
import android.view.LayoutInflater;   
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;   
import android.widget.LinearLayout;   
import android.widget.TextView;   
  
public class SimpleTitleLayout extends LinearLayout {   
	
	private Context fatherActivity;
    private TextView peopleName;   
    private TextView titleTime;
    private TextView titleContent;
    private Button titleDetailButton;
    
    private message theMessage;
    
    public SimpleTitleLayout(Context context) {   
    	//AttributeSet attrs = null;
        this(context, null);   
    }   
    
    public SimpleTitleLayout(Context context,Context fatherActivity,message theMessage) {   
        
    	this(context);
        this.fatherActivity = fatherActivity;
        this.theMessage=theMessage;
    }   
    
    
   public SimpleTitleLayout(Context context, AttributeSet attrs) {   
        super(context, attrs);   
        // 导入布局   
        LayoutInflater.from(context).inflate(R.layout.simpletitle, this, true);   
        //this.setMinimumWidth(480);
        peopleName = (TextView) findViewById(R.id.stPeopleName);   
        titleTime = (TextView) findViewById(R.id.stTime);   
        titleContent = (TextView) findViewById(R.id.stContent);  
        titleDetailButton = (Button)findViewById(R.id.detailButton);
        //需要得到一个类
        titleDetailButton.setOnClickListener(titleDetailButtonListener);
    }   
    
    
   private OnClickListener titleDetailButtonListener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent titleDetailIntent;
			Bundle bundle=new Bundle();
			bundle.putString("id", theMessage.get_id());
			bundle.putString("content", theMessage.getContent());
			bundle.putString("authorName", theMessage.getUsername());
			bundle.putString("time", theMessage.getTime());
			
			titleDetailIntent=new Intent(fatherActivity ,TitleDetailActivity.class);
			
			titleDetailIntent.putExtras(bundle);
			
			fatherActivity.startActivity(titleDetailIntent);
			
		}
		
	};
    /**  
     * 设置显示的文字  
     */  
    public void setPeopleName(String text) {   
        peopleName.setText(text);   
    }   
    public void setTitleTime(String text) {   
        titleTime.setText(text);   
    }   
  
    public void setTitleContent(String text) {   
        titleContent.setText(text);   
    }   
  
  
} 