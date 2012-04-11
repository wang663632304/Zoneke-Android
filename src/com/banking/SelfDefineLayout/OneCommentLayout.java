package com.banking.SelfDefineLayout;  
 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.banking.entity.Comment;
import com.banking.zoneke.*;

import android.content.Context;   
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.text.Html.ImageGetter;
import android.util.AttributeSet;   
import android.view.LayoutInflater;   
import android.widget.Button;
import android.widget.ImageView;   
import android.widget.LinearLayout;   
import android.widget.TextView;   
  
public class OneCommentLayout extends LinearLayout {   
  
    private TextView peopleName;   
    private TextView commentTime;
    private TextView commentContent;
    //private Button titleDetailButton;
    private Comment targetComment;
    private Context context=getContext();
    
    public OneCommentLayout(Context context) {   
        this(context, null);   
    }   
  
   public OneCommentLayout(Context context, AttributeSet attrs) {   
        super(context, attrs);   
        // 导入布局   
        LayoutInflater.from(context).inflate(R.layout.onecomment, this, true);   
        peopleName = (TextView) findViewById(R.id.ocPeopleName);   
        commentTime = (TextView) findViewById(R.id.ocTime);   
        commentContent = (TextView) findViewById(R.id.ocContent);  
        //titleDetailButton = (Button)findViewById(R.id.detailButton);
        //需要得到一个类
        
        
        
    }   
   public OneCommentLayout(Context context, AttributeSet attrs,Comment comment) {  
	   super(context, attrs); 
	   //targetComment = comment;
	   LayoutInflater.from(context).inflate(R.layout.onecomment, this, true);
	   peopleName = (TextView) findViewById(R.id.ocPeopleName);   
       commentTime = (TextView) findViewById(R.id.ocTime);   
       commentContent = (TextView) findViewById(R.id.ocContent);  
       String commentBodyStr = comment.getCommentContent();
       //commentBodyStr.
       System.out.println("this is  a piece of comment");
       System.out.println(comment.getCommentContent());
       
       
       
	   peopleName.setText(comment.getCommenterName());
       commentTime.setText(comment.getCommentTime());
       //这儿需要识别图片,用这个方法，需要改良
       //commentContent.setText(commentBodyStr);
       // Html.fromHtml(commentBodyStr,ig, null);
       //String faceContent =FilterHtml(Html.fromHtml(commentBodyStr));
       filterContent(commentBodyStr,commentContent);
       
       
   }
    /**  
     * 设置显示的文字  
     */  
    public void setPeopleName(String text) {   
        peopleName.setText(text);   
    }   
    public void setCommentTime(String text) {   
        commentTime.setText(text);   
    }   
    
    public void setCommentContent(String text) {   
        commentContent.setText(text);   
    }  
    public  void  filterContent(String str,TextView commentContent){
    	
    	System.out.println(str);
    	ImageGetter ig = new ImageGetter(){

			@Override
			public Drawable getDrawable(String source) {
				// TODO Auto-generated method stub
				int imageId= Integer.parseInt(source);
				Drawable d = getResources().getDrawable(imageId);
				 d.setBounds(0, 0, d.getIntrinsicWidth(),d.getIntrinsicHeight());
			     return d;    
			}
		};
		//String c = "abc@234@df@3423@";
		// 正则初始化
		Pattern p = Pattern.compile("@[0-9]+@");
		// 匹配器初始化
		Matcher m = p.matcher(str);
		// 匹配查询
		
		String[] strs = new String[10];   //这儿设置了10个最大值
		String target = str;
		int i=0;
		while (m.find()) {
			System.out.println(m.group(0));
			System.out.println("c");
			//System.out.println(m.group(1));
			strs[i]=m.group(0);
			//String strid=m.group(0).substring(1, m.group(0).length()-1);
			//strs[i] =((String) target).replace(m.group(0), Html.fromHtml("<img src='"+strid+"'/>", ig, null));
			i++;
			
		}
		System.out.println(str);
		commentContent.setText("");
		System.out.println("i is "+i);
		//commentContent.append(target);
		for(int j=0;j<i;j++){
			int before = target.indexOf(strs[j]);
			int after = before+strs[j].length();
			commentContent.append(target.substring(0, before));
			String strid=strs[j].substring(1, strs[j].length()-1);
			CharSequence cs= Html.fromHtml("<img src='"+strid+"'/>", ig, null);
			commentContent.append(cs);
			if(j==i-1)
			{
				System.out.println(after);
				System.out.println("test!");
				System.out.println(target.length());
				commentContent.append(target.substring(after, target.length()));
				
			}
		}
		if(i==0) commentContent.append(str);
    }
    
} 