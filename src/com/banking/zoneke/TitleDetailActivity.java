package com.banking.zoneke;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


import com.banking.SelfDefineLayout.OneCommentLayout;
import com.banking.SelfDefineLayout.SimpleTitleLayout;
import com.banking.entity.Comment;
import com.banking.entity.Comments;
import com.banking.entity.message;
import com.banking.gsonUtil.JsonDataGetApi;
import com.banking.wholevariable.WholeVariable;
import com.banking.zoneke.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class TitleDetailActivity extends Activity {
	
	private Context context;
	private String messageId = null;
	private Comments comments;
	private Button submitCommentButton;
	private TextView tdTitleContent;
	private TextView tdAuthorName;
	private TextView tdCommenterName;
	private EditText tdResponseCommentContent;
	
	private Button expressionButton;
	private PopupWindow mpopupWindow;
	
	private static boolean expressionsShow=false;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.titledetail);
        WholeVariable.getList().add(this);
        
        submitCommentButton=(Button)findViewById(R.id.submitCommentButton);
        tdTitleContent=(TextView)findViewById(R.id.tdTitleContent);
        tdAuthorName=(TextView)findViewById(R.id.tdAuthorName);
        tdCommenterName=(TextView)findViewById(R.id.tdCommenterName);
        tdResponseCommentContent=(EditText)findViewById(R.id.tdResponseCommentContent);
        
        submitCommentButton.setOnClickListener(submitCommentButtonListener);
        
        expressionButton = (Button)findViewById(R.id.tdBottomButton2);
        expressionButton.setOnClickListener(expressionButtonListener);
        
        if(getIntent().getExtras()!=null)
        {
         
         messageId=getIntent().getExtras().getString("id");
         tdTitleContent.setText(getIntent().getExtras().getString("content"));
         tdAuthorName.setText(getIntent().getExtras().getString("authorName"));
         
         System.out.println(getIntent().getExtras().getString("content"));
         System.out.println(getIntent().getExtras().getString("authorName"));
        }
        else{
        	System.out.println("null extras in titledetail");
        }
        
//后台代码要放到service里面
        
        context=getBaseContext();
        tdCommenterName.setText(WholeVariable.getNowUserName());
        tdCommenterName.append(":");
       
        
        //先不使用service看效果
        comments=getCommentData();
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.tdCommentLayout);
        
        System.out.println("comment 个数");
        System.out.println(comments.getCommentsList().size());
        for(int x = 0;x<comments.getCommentsList().size();x++)
		{
			OneCommentLayout oclt = new OneCommentLayout(context,null,comments.getCommentsList().get(x));
	        
	        //st.setTitleContent(nowMessages.get(x).getContent());
	        mainLayout.addView(oclt, new LinearLayout.LayoutParams(
	        		LinearLayout.LayoutParams.WRAP_CONTENT,
	        		LinearLayout.LayoutParams.WRAP_CONTENT));
		}   
        
      //设置最后地空白
		TextView emptyTv = new TextView(context);
        emptyTv.setHeight(50);
        mainLayout.addView(emptyTv, new LinearLayout.LayoutParams(
        		LinearLayout.LayoutParams.WRAP_CONTENT,
        		LinearLayout.LayoutParams.WRAP_CONTENT));   
       
    }
    private OnClickListener expressionButtonListener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			Context mContext = TitleDetailActivity.this;
			if(!expressionsShow)
			{
				LayoutInflater mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
			View expression_popunwindwow = mLayoutInflater.inflate(
					R.layout.expressions, null);
			
			ImageView im1=(ImageView)(expression_popunwindwow.findViewById(R.id.expression1IV));
			ImageView im2=(ImageView)(expression_popunwindwow.findViewById(R.id.expression2IV));
			ImageView im3=(ImageView)(expression_popunwindwow.findViewById(R.id.expression3IV));
			ImageView im4=(ImageView)(expression_popunwindwow.findViewById(R.id.expression4IV));
			ImageView im5=(ImageView)(expression_popunwindwow.findViewById(R.id.expression5IV));
			ImageView im6=(ImageView)(expression_popunwindwow.findViewById(R.id.expression6IV));
			ImageView im7=(ImageView)(expression_popunwindwow.findViewById(R.id.expression7IV));
			ImageView im8=(ImageView)(expression_popunwindwow.findViewById(R.id.expression8IV));
			ImageView im9=(ImageView)(expression_popunwindwow.findViewById(R.id.expression9IV));
			
			im1.setOnClickListener(expressionImageViewClickLintener);
			im2.setOnClickListener(expressionImageViewClickLintener);
			im3.setOnClickListener(expressionImageViewClickLintener);
			im4.setOnClickListener(expressionImageViewClickLintener);
			im5.setOnClickListener(expressionImageViewClickLintener);
			im6.setOnClickListener(expressionImageViewClickLintener);
			im7.setOnClickListener(expressionImageViewClickLintener);
			im8.setOnClickListener(expressionImageViewClickLintener);
			im9.setOnClickListener(expressionImageViewClickLintener);
			
			
			
			mpopupWindow = new PopupWindow(expression_popunwindwow,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
			mpopupWindow.showAtLocation(findViewById(R.id.titleDetailMain), Gravity.BOTTOM|Gravity.RIGHT, 5, 70);
			expressionsShow=true;
			}
			else{
				mpopupWindow.dismiss();
				expressionsShow=false;
			}
		}
    };
    private OnClickListener expressionImageViewClickLintener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mpopupWindow.dismiss();
			expressionsShow=false;
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
			//这个需要判断
			int drawableID = 0;
			switch(v.getId()){
				case R.id.expression1IV: drawableID=R.drawable.expression1;break;
				case R.id.expression2IV: drawableID=R.drawable.expression2;break;
				case R.id.expression3IV: drawableID=R.drawable.expression3;break;
				case R.id.expression4IV: drawableID=R.drawable.expression4;break;
				case R.id.expression5IV: drawableID=R.drawable.expression5;break;
				case R.id.expression6IV: drawableID=R.drawable.expression6;break;
				case R.id.expression7IV: drawableID=R.drawable.expression7;break;
				case R.id.expression8IV: drawableID=R.drawable.expression8;break;
				case R.id.expression9IV: drawableID=R.drawable.expression9;break;
			}
			CharSequence cs =Html.fromHtml("<img src='"+drawableID+"'/>", ig, null);
			System.out.println(cs);
			tdResponseCommentContent.append(cs);
			System.out.println("before");
			System.out.println(tdResponseCommentContent.getText());
			String faceContent =FilterHtml(Html.toHtml(tdResponseCommentContent.getText()));
			//tdResponseCommentContent.append(faceContent);
			//有疑问
			//tdResponseCommentContent.append(faceContent);
			System.out.println("after");
			System.out.println(faceContent);
			
			//someContent = faceContent;
			//System.out.println(tdResponseCommentContent.getText());
		}
    	
    };
    public static String FilterHtml(String str){
        str = str .replaceAll("<(?!br|img)[^>]+>", "").trim();
        //return UnicodeToGBK2(str);
        String str2 = str.replace("<img src=\"", "@");
        String str3 = str2.replace("\">","@");
        return str3;
    }
    
 
    
    private OnClickListener submitCommentButtonListener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			//发评论请求
				String commentContent =FilterHtml(Html.toHtml(tdResponseCommentContent.getText()));
		        JsonDataGetApi api = new JsonDataGetApi();
		        JSONObject jobj1;
		        JSONObject jobj2;
		        ArrayList<message> messageAL = new ArrayList<message>();
		        
		        try {
		            //调用GetData方法
		        	String userid;
		        	String lng=WholeVariable.getLNG();
		        	String lat=WholeVariable.getLAT();
		        	
		        	String extentUrl="message/reply/?id=1&mid="+messageId+"&lat="+lat+"&lng="+lng+"&content="+commentContent;
		        	String url="";
		        	url+=WholeVariable.getBaseUrl();
		        	url+=extentUrl;
		        	jobj1 = api.getObject(url);
		        	
		            //从返回的Account Array中取出第一个数据
		            String retNumber = jobj1.getString("ret");
		            
		            if(retNumber.equals("200"))
		            {
		            	System.out.println("成功发送");
		            }
		           
		            jobj2=jobj1.getJSONObject("data").getJSONObject("mid");
		            String messageId = jobj2.toString();
		            System.out.println(messageId);

		            GsonBuilder gsonb = new GsonBuilder();
		            Gson gson = gsonb.create();
		  
		         } catch (Exception e) {
		        	 /*
		            Toast.makeText(getApplicationContext(), e.getMessage(),
		                    Toast.LENGTH_LONG).show();
		                    */
		        	System.out.println(e.getMessage());
		            e.printStackTrace();
		          
		        }	
		         Intent goHomeIntent = new Intent();
		         goHomeIntent.setClass(TitleDetailActivity.this, ZoneKeActivity.class);
		         TitleDetailActivity.this.finish();
		         startActivity(goHomeIntent);
		         
		}
		
	};
    private Comments getCommentData(){
    	
    	Comments comments = new Comments();
    	JSONArray jArr =null;
        JSONObject jobj1;
        JSONObject jobj2;
    	String extentUrl="message/getReplys/?mid="+messageId;
    	String url="";
    	url+=WholeVariable.getBaseUrl();
    	url+=extentUrl;
    	 try {
             //调用GetData方法
    		JsonDataGetApi api = new JsonDataGetApi();
         	jobj1 = api.getObject(url);
         	
             //从返回的Account Array中取出第一个数据
             String jname = jobj1.getString("ret");
             
             jArr=jobj1.getJSONObject("data").getJSONArray("replys");
             int count  = jArr.length();
             System.out.println(count);
             
             GsonBuilder gsonb = new GsonBuilder();
             Gson gson = gsonb.create();
             
             for(int y=0;y<count;y++)
             {
             	jobj2=jArr.getJSONObject(y);
             	//message m1 = gson.fromJson(jobj2.toString(), message.class);
             	Comment comment = new Comment();
             	comment.setCommentContent(jobj2.getString("content"));
             	comment.setCommenterId(jobj2.getString("userID"));
             	comment.setCommenterName(jobj2.getString("username"));
             	comment.setCommentTime(jobj2.getString("time"));
             	System.out.println(comment.getCommentTime()+comment.getCommentContent());
             	comments.getCommentsList().add(comment);	
             }
             return comments;
    	 } catch (Exception e) {
    		 /*
             Toast.makeText(getApplicationContext(), e.getMessage(),
                     Toast.LENGTH_LONG).show();
                     */
             e.printStackTrace();
             
             //TextView movie_Address = (TextView) findViewById(R.id.Address);
             //movie_Address.setText(e.getMessage());
         }
		return comments;
    }
}