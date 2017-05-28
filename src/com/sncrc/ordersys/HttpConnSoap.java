package com.sncrc.ordersys;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import android.util.Log;

public class HttpConnSoap {
	//服务器地址
	String ServerUrl = "http://222.41.236.19:8091/WorksheetProcessingSysWeb.asmx";
	
	public Document GetWebServre(String methodName,
			ArrayList<String> Parameters, ArrayList<String> ParValues) {
		
		Document doc = null;
		
		//String ServerUrl = "http://111.20.213.92:8091/WorksheetProcessingSysWeb.asmx";

		// String soapAction="http://tempuri.org/LongUserId1";
		String soapAction = "http://tempuri.org/" + methodName;
		// String data = "";
		String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Body>";

		String tps, vps, ts;
		String mreakString = "";

		mreakString = "<" + methodName + " xmlns=\"http://tempuri.org/\">";
		for (int i = 0; i < Parameters.size(); i++) {
			tps = Parameters.get(i).toString();
			// 设置该方法的参数为.net webService中的参数名称
			vps = ParValues.get(i).toString();
			ts = "<" + tps + ">" + vps + "</" + tps + ">";
			mreakString = mreakString + ts;
		}
		mreakString = mreakString + "</" + methodName + ">";
		/*
		 * +"<HelloWorld xmlns=\"http://tempuri.org/\">" +"<x>string11661</x>"
		 * +"<SF1>string111</SF1>" + "</HelloWorld>"
		 */
		String soap2 = "</soap:Body></soap:Envelope>";
		String requestData = soap + mreakString + soap2;
		// 其上所有的数据都是在拼凑requestData，即向服务器发送的数据
		// System.out.println(requestData);
		HttpURLConnection con=null;
		try {
			// 新建一个URL对象，指定到请求的url.
			// ServerUrl="http://www.baidu.com/";
			URL url = new URL(ServerUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type","text/xml; charset=UTF-8");
		    con.setRequestProperty("SOAPAction",soapAction);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setInstanceFollowRedirects(true);
			byte[] bytes = requestData.getBytes("utf-8");
			OutputStream outStream = con.getOutputStream();
			outStream.write(bytes);
			outStream.flush();
			outStream.close();
			if(con.getResponseCode()==200)
			{
				InputStream inStream = con.getInputStream();
				doc = newDocumentFromInputStream(inStream);
				con.disconnect();
				return doc;
			}else
			{
				System.out.println("ResponseCode:"+con.getResponseCode());
				con.disconnect();
				return null;
			}

		} catch (Exception e) {
			con.disconnect();
			System.out.println("2221");
			return null;
		}
	}
	
	public String GetWebServre_str(String methodName,
			ArrayList<String> Parameters, ArrayList<String> ParValues) {
		
		Document doc = null;
		
		//String ServerUrl = "http://111.20.213.92:8091/WorksheetProcessingSysWeb.asmx";

		// String soapAction="http://tempuri.org/LongUserId1";
		String soapAction = "http://tempuri.org/" + methodName;
		// String data = "";
		String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<soap:Body>";

		String tps, vps, ts;
		String mreakString = "";

		mreakString = "<" + methodName + " xmlns=\"http://tempuri.org/\">";
		for (int i = 0; i < Parameters.size(); i++) {
			tps = Parameters.get(i).toString();
			// 设置该方法的参数为.net webService中的参数名称
			vps = ParValues.get(i).toString();
			ts = "<" + tps + ">" + vps + "</" + tps + ">";
			mreakString = mreakString + ts;
		}
		mreakString = mreakString + "</" + methodName + ">";
		/*
		 * +"<HelloWorld xmlns=\"http://tempuri.org/\">" +"<x>string11661</x>"
		 * +"<SF1>string111</SF1>" + "</HelloWorld>"
		 */
		String soap2 = "</soap:Body></soap:Envelope>";
		String requestData = soap + mreakString + soap2;
		// 其上所有的数据都是在拼凑requestData，即向服务器发送的数据
		// System.out.println(requestData);
		HttpURLConnection con=null;
		try {
			// 新建一个URL对象，指定到请求的url.
			// ServerUrl="http://www.baidu.com/";
			URL url = new URL(ServerUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type","text/xml; charset=UTF-8");
		    con.setRequestProperty("SOAPAction",soapAction);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setInstanceFollowRedirects(true);
			byte[] bytes = requestData.getBytes("utf-8");
			OutputStream outStream = con.getOutputStream();
			outStream.write(bytes);
			outStream.flush();
			outStream.close();
			if(con.getResponseCode()==200)
			{
				InputStream inStream = con.getInputStream();
				//doc = newDocumentFromInputStream(inStream);
				String s=inputStream2String(inStream);
				//Log.d("InputStream",s);
				con.disconnect();
				return s;
			}else
			{
				System.out.println("ResponseCode:"+con.getResponseCode());
				con.disconnect();
				return null;
			}

		} catch (Exception e) {
			con.disconnect();
			System.out.println("请求服务器失败！"+e);
			return null;
		}
	}
	
	public static String inputStream2String(InputStream is) throws IOException{ 
        ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream(); 
        int   i=-1; 
        while((i=is.read())!=-1){ 
        baos.write(i); 
        } 
       return   baos.toString(); 
}
	
	public static Document newDocumentFromInputStream(InputStream in) {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document ret = null;

		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		try {
			ret = builder.parse(new InputSource(in));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
