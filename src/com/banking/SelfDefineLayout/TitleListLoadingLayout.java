package com.banking.SelfDefineLayout;  
 
import com.banking.entity.Comment;
import com.banking.zoneke.*;

import android.content.Context;   
import android.util.AttributeSet;   
import android.view.LayoutInflater;   
import android.widget.Button;
import android.widget.ImageView;   
import android.widget.LinearLayout;   
import android.widget.TextView;   
  
public class TitleListLoadingLayout extends LinearLayout {   
  
    
    
    public TitleListLoadingLayout(Context context) {   
        this(context, null);   
    }   
  
   public TitleListLoadingLayout(Context context, AttributeSet attrs) {   
        super(context, attrs);   
        // 导入布局   
        LayoutInflater.from(context).inflate(R.layout.titlesloading, this, true);   
    }   
   public TitleListLoadingLayout(Context context, AttributeSet attrs,Comment comment) {  
	   super(context, attrs); 
	   //targetComment = comment;
	   LayoutInflater.from(context).inflate(R.layout.onecomment, this, true);
	  
       
   }
    
  
  
} 