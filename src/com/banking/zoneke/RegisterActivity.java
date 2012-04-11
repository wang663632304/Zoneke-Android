
package com.banking.zoneke;

import com.banking.SelfDefineLayout.OneCommentLayout;
import com.banking.SelfDefineLayout.SimpleTitleLayout;
import com.banking.db.ZonekeDatabaseHelper;
import com.banking.entity.Messages;

import com.banking.wholevariable.WholeVariable;
import com.banking.zoneke.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	private Context context;
	
	private EditText userName;
	private EditText passWord;
	private EditText passWordConfirm;
	private Button registerFinishButton;
	
	private String userNameStr = "";
	private String passWordStr = "";
	private String passWordConfirmStr = "";
	
	
	
    /** Called when the activity is first created. */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        WholeVariable.getList().add(this);

        
        context=getBaseContext();
        userName = (EditText)findViewById(R.id.registerUserNameEditText);
        passWord = (EditText)findViewById(R.id.registerPassWordEditText);
        passWordConfirm = (EditText)findViewById(R.id.registerPassWordConfirmEditText);
        registerFinishButton=(Button)findViewById(R.id.registerFinishButton);
        
        
        registerFinishButton.setOnClickListener(registerFinishButtonListener);
        
        
        
    }
    
    private OnClickListener registerFinishButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			userNameStr = userName.getText().toString();
			passWordStr = passWord.getText().toString();
			passWordConfirmStr = passWordConfirm.getText().toString();
			//�ж��Ƿ���ȷ�����ע��
			//δ����д������Ϣ
			 if(userNameStr.trim().equals("")||passWordStr.trim().equals("")||passWordConfirmStr.trim().equals(""))
			 {
				System.out.println("null values");
				Toast.makeText(RegisterActivity.this, "������ע����Ϣ�ı�����",Toast.LENGTH_LONG ).show();
				return;
			 }
			//���벻ƥ��
			if(!passWordStr.equals(passWordConfirmStr))
			{
				System.out.println("not equal passwords");
				Toast.makeText(RegisterActivity.this, "������д�����벻һ�£�",Toast.LENGTH_LONG ).show();
				return;
			}
			
			//��ȷ���ע�ᣬ����Ҫ�жϷ�����
			ZonekeDatabaseHelper dh = new ZonekeDatabaseHelper(RegisterActivity.this,"zoneke_db");
	    	//�����û�����ݿ���Զ�����
	    	SQLiteDatabase sd = dh.getReadableDatabase();
	    	String userId = "2"; //get from ret
			Cursor cursor = sd.query("user", new String[]{"id","username"}, null,null, null, null, null);
			int count = 0;
			String userProfile[] = new String[3];
			while(cursor.moveToNext()){
				/*
				ContentValues values = new ContentValues();
				//���id
	    		values.put("id",Integer.parseInt( userId));
	    		
				values.put("username", userNameStr);
				
				values.put("password",passWordStr);
				System.out.println("update before");
				//�õ��û��������е�
				userProfile[0] = cursor.getString(cursor.getColumnIndex("username"));
				System.out.println(userProfile[0]);
				//�����ƾ���û���ʶ���
				sd.update("user", values, "username=?", new String[]{userProfile[0]});
				System.out.println("update after");
				*/
				System.out.println("�ظ��û���");
				count++;
			}
			System.out.println(count);
			if(count==0)
	    	{
				System.out.println("insert new user values!");
	    		ContentValues values = new ContentValues();
				//�����ǲ������
				values.put("id", Integer.parseInt(userId));
				values.put("username", userNameStr);
				values.put("password",passWordStr);
				values.put("dota",1);
				values.put("eat",1);
				values.put("out",1);
				values.put("find",1);
				values.put("sport",1);
				values.put("scope",200);
				values.put("rememberpw", "1");
				sd.insert("user", null, values);
				
	    	}
			Intent finishRegisterIntent = new Intent(RegisterActivity.this,ZoneKeActivity.class);
			startActivity(finishRegisterIntent);
			//������״����ע��,������ʾ����
		}
    };
    
}