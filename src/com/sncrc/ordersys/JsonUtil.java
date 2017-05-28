package com.sncrc.ordersys;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.*;;  

/**
 * common xml conver utility
 * 
 * @author viruscodecn@gmail.com
 * @version Framework 2010.10.26
 * @url http://blog.csdn.net/arjick/article/details/6251777
 */
public class JsonUtil {
	public static List json2list(String json)
	{
		List<Map<String, Object>> list = null; 
		try 
		{ 
			JSONArray jsonArray = new JSONArray(json); 
			JSONObject jsonObject; 
			list = new ArrayList<Map<String, Object>>(); 
			for (int i = 0; i < jsonArray.length(); i++) 
			{ 
				jsonObject = jsonArray.getJSONObject(i); 
				list.add(getMap(jsonObject.toString())); 
			} 
		} 
		catch (Exception e) 
		{ 
			e.printStackTrace(); 
		} 
		return list; 

	}
	/**
	* 将json 数组转换为Map 对象
	* @param jsonString
	* @return
	*/ 
	public static Map<String, Object> getMap(String jsonString) 
	{ 
		JSONObject jsonObject; 
		try 
		{ 
			jsonObject = new JSONObject(jsonString); @SuppressWarnings("unchecked") 
			Iterator<String> keyIter = jsonObject.keys(); 
			String key; 
			Object value; 
			Map<String, Object> valueMap = new HashMap<String, Object>(); 
			while (keyIter.hasNext()) 
			{ 
				key = (String) keyIter.next(); 
				value = jsonObject.get(key); 
				valueMap.put(key, value);
			} 
			return valueMap; 
		} 
		catch (JSONException e) 
		{ 
			e.printStackTrace(); 
		} 
		return null; 
	}
}