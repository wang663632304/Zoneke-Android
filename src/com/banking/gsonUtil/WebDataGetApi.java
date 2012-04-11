package com.banking.gsonUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class WebDataGetApi {

    private static final String TAG = "WebDataGetAPI";
    private static final String USER_AGENT = "Mozilla/4.5";
    private DefaultHttpClient theClient = new DefaultHttpClient(new BasicHttpParams());
    
    private Handler serverHandler = new Handler(){
    		public void HandlerMessage(Message message){
    			switch(message.what)
    			{
    			case 1:
    			     System.out.println(" server will wait!");
    				 stopServerConnect();
    				 System.out.println("server wait");
    				 
	    			 break;
    			}
    		}
    };
    private class MyTask extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			message.what=1;
			serverHandler.sendMessage(message);
		}
    	
    }
    public void stopServerConnect(){
    	try {
			theClient.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    protected String getRequest(String url) throws Exception {
        return getRequest(url, new DefaultHttpClient(new BasicHttpParams()));
    }
    
    
    
    
    //不用这个client参数
    protected String getRequest(String url, DefaultHttpClient client1)
            throws Exception {
        String result = null;
        int statusCode = 0;
        
        //在这里处理url中的空格问题,+代替。可以解决
        String newUrl = url.replace(" ", "+");
        
         //怎么处理服务器出错,用handler
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTask(), 1, 3000);
        
        
        HttpGet getMethod = new HttpGet(newUrl);
        
        Log.d(TAG, "do the getRequest,url=" + newUrl + "");
        try {
            getMethod.setHeader("User-Agent", USER_AGENT);
            HttpParams httpParameters = new BasicHttpParams();
            
            HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
            HttpConnectionParams.setSoTimeout(httpParameters, 10000);
            
            
            // 添加用户密码验证信息
            // client.getCredentialsProvider().setCredentials(
            // new AuthScope(null, -1),
            // new UsernamePasswordCredentials(mUsername, mPassword));
            DefaultHttpClient client = new DefaultHttpClient(httpParameters);
            theClient = client;
           
            HttpResponse httpResponse = client.execute(getMethod);
            // statusCode == 200 正常
            statusCode = httpResponse.getStatusLine().getStatusCode();
            Log.d(TAG, "statuscode = " + statusCode);
            // 处理返回的httpResponse信息
            result = retrieveInputStream(httpResponse.getEntity());
            
        }catch(ConnectTimeoutException e) 
            
        {
        	System.out.println("服务器超时！");
        	throw new Exception(e);
        	//继续抛出这个异常
        }
        catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new Exception(e);
        } finally {
            getMethod.abort();
        }
        
        return result;
    }

    /**
     * 处理httpResponse信息,返回String
     * 
     * @param httpEntity
     * @return String
     */
    protected String retrieveInputStream(HttpEntity httpEntity) {
        int length = (int) httpEntity.getContentLength();
        if (length < 0)
            length = 10000;
        StringBuffer stringBuffer = new StringBuffer(length);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    httpEntity.getContent(), HTTP.UTF_8);
            char buffer[] = new char[length];
            int count;
            while ((count = inputStreamReader.read(buffer, 0, length - 1)) > 0) {
                stringBuffer.append(buffer, 0, count);
            }
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return stringBuffer.toString();
    }
}
