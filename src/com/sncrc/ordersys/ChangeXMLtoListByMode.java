package com.sncrc.ordersys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.util.Log;

public class ChangeXMLtoListByMode {
	// 将T_Job类型的xml结构转换成list
	public List<HashMap<String, String>> T_JobXMLRead(Document doc) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> tempHash = new HashMap<String, String>();
		try{
			NodeList nList = doc.getElementsByTagName("T_job");
			for (int i = 0; i < nList.getLength(); i++) {
				Element node = (Element) nList.item(i);
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("Id", node.getElementsByTagName("Id").item(0).getFirstChild().getNodeValue());
				hashMap.put("sender", node.getElementsByTagName("sender").item(0).getFirstChild().getNodeValue());
				hashMap.put("StrDatetime", node.getElementsByTagName("StrDatetime").item(0).getFirstChild().getNodeValue());
				hashMap.put("country", node.getElementsByTagName("country").item(0).getFirstChild().getNodeValue());
				hashMap.put("type", node.getElementsByTagName("type").item(0).getFirstChild().getNodeValue());
				hashMap.put("area", node.getElementsByTagName("area").item(0).getFirstChild().getNodeValue());
				hashMap.put("cell", node.getElementsByTagName("cell").item(0).getFirstChild().getNodeValue());
				hashMap.put("inmode", node.getElementsByTagName("inmode").item(0).getFirstChild().getNodeValue());
				hashMap.put("money", node.getElementsByTagName("money").item(0).getFirstChild().getNodeValue());
				hashMap.put("isline", node.getElementsByTagName("isline").item(0).getFirstChild().getNodeValue());
				hashMap.put("address", node.getElementsByTagName("address").item(0).getFirstChild().getNodeValue());
				hashMap.put("userInfo", node.getElementsByTagName("userInfo").item(0).getFirstChild().getNodeValue());
				hashMap.put("installperson",node.getElementsByTagName("installperson").item(0).getFirstChild().getNodeValue());
				hashMap.put("issuccess", node.getElementsByTagName("issuccess").item(0).getFirstChild().getNodeValue());
				hashMap.put("mainreason", node.getElementsByTagName("mainreason").item(0).getFirstChild().getNodeValue());
				hashMap.put("reason", node.getElementsByTagName("reason").item(0).getFirstChild().getNodeValue());
				hashMap.put("okDatetime", node.getElementsByTagName("okDatetime").item(0).getFirstChild().getNodeValue());
				hashMap.put("state", node.getElementsByTagName("state").item(0).getFirstChild().getNodeValue());
				hashMap.put("jdtime", node.getElementsByTagName("jdtime").item(0).getFirstChild().getNodeValue());
				hashMap.put("usedtime", node.getElementsByTagName("usedtime").item(0).getFirstChild().getNodeValue());
				hashMap.put("isover", node.getElementsByTagName("isover").item(0).getFirstChild().getNodeValue());
				hashMap.put("remark", node.getElementsByTagName("remark").item(0).getFirstChild().getNodeValue());
				hashMap.put("account", node.getElementsByTagName("account").item(0).getFirstChild().getNodeValue());
				hashMap.put("yyremark", node.getElementsByTagName("yyremark").item(0).getFirstChild().getNodeValue());
				hashMap.put("celladdress", node.getElementsByTagName("celladdress").item(0).getFirstChild().getNodeValue());
				hashMap.put("spremark", node.getElementsByTagName("spremark").item(0).getFirstChild().getNodeValue());
				hashMap.put("zpspjg", node.getElementsByTagName("zpspjg").item(0).getFirstChild().getNodeValue());
				hashMap.put("zpspbz", node.getElementsByTagName("zpspbz").item(0).getFirstChild().getNodeValue());
				hashMap.put("zppath", node.getElementsByTagName("zppath").item(0).getFirstChild().getNodeValue());
				hashMap.put("zp2path", node.getElementsByTagName("zp2path").item(0).getFirstChild().getNodeValue());
				list.add(hashMap);
			}
		}catch(Exception e)
		{
			System.out.println("Error in [T_JobXMLRead]");
		}
		return list;
	}
	
	public List<HashMap<String, String>> T_FaultJobXMLRead(Document doc) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> tempHash = new HashMap<String, String>();
		try{
			NodeList nList = doc.getElementsByTagName("T_FaultJob");
			for (int i = 0; i < nList.getLength(); i++) {
				Element node = (Element) nList.item(i);
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("Id", node.getElementsByTagName("Id").item(0).getFirstChild().getNodeValue());
				hashMap.put("county", node.getElementsByTagName("county").item(0).getFirstChild().getNodeValue());
				hashMap.put("area", node.getElementsByTagName("area").item(0).getFirstChild().getNodeValue());
				hashMap.put("cell", node.getElementsByTagName("cell").item(0).getFirstChild().getNodeValue());
				hashMap.put("inmode", node.getElementsByTagName("inmode").item(0).getFirstChild().getNodeValue());
				hashMap.put("sender", node.getElementsByTagName("sender").item(0).getFirstChild().getNodeValue());
				hashMap.put("StrDatetime", node.getElementsByTagName("StrDatetime").item(0).getFirstChild().getNodeValue());
				hashMap.put("faulttype", node.getElementsByTagName("faulttype").item(0).getFirstChild().getNodeValue());
				hashMap.put("address", node.getElementsByTagName("address").item(0).getFirstChild().getNodeValue());
				hashMap.put("userphone", node.getElementsByTagName("userphone").item(0).getFirstChild().getNodeValue());
				hashMap.put("receiveperson", node.getElementsByTagName("receiveperson").item(0).getFirstChild().getNodeValue());
				hashMap.put("faultdescribe", node.getElementsByTagName("faultdescribe").item(0).getFirstChild().getNodeValue());
				hashMap.put("okDatetime",node.getElementsByTagName("okDatetime").item(0).getFirstChild().getNodeValue());
				hashMap.put("issuccess", node.getElementsByTagName("issuccess").item(0).getFirstChild().getNodeValue());
				hashMap.put("faultreason", node.getElementsByTagName("faultreason").item(0).getFirstChild().getNodeValue());
				hashMap.put("state", node.getElementsByTagName("state").item(0).getFirstChild().getNodeValue());
				hashMap.put("jdtime", node.getElementsByTagName("jdtime").item(0).getFirstChild().getNodeValue());
				hashMap.put("usedtime", node.getElementsByTagName("usedtime").item(0).getFirstChild().getNodeValue());
				hashMap.put("isover", node.getElementsByTagName("isover").item(0).getFirstChild().getNodeValue());
				hashMap.put("celladdress", node.getElementsByTagName("celladdress").item(0).getFirstChild().getNodeValue());
				list.add(hashMap);
			}
		}catch(Exception e)
		{
			System.out.println("Error in [T_FaultJobXMLRead]");
		}
		return list;
	}
	
	public List<HashMap<String, String>> T_ReportXMLRead(Document doc) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> tempHash = new HashMap<String, String>();
		tempHash.put("县区", "县区");
		tempHash.put("当日派单", "当日派单");
		tempHash.put("当日成功装机", "当日成功装机");
		tempHash.put("当日驳回", "当日驳回");
		tempHash.put("当月派单", "当月派单");
		tempHash.put("当月成功装机", "当月成功装机");
		tempHash.put("当月驳回", "当月驳回");
		tempHash.put("未完成工单", "未完成工单");
		tempHash.put("超时未完成工单", "超时未完成工单");
		tempHash.put("当日APP活跃数", "当日APP活跃数");
		list.add(tempHash);
		try{
			NodeList nList = doc.getElementsByTagName("T_Report");
//			org.dom4j.io.DOMReader   xmlReader= new org.dom4j.io.DOMReader();
//			org.dom4j.Document docm=xmlReader.read(doc);
//			Log.d("测试node",docm.getRootElement().getName());
			for (int i = 0; i < nList.getLength(); i++) {
				Element node = (Element) nList.item(i);
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("县区", node.getElementsByTagName("county").item(0).getFirstChild().getNodeValue());
				hashMap.put("当日派单", node.getElementsByTagName("drpd").item(0).getFirstChild().getNodeValue());
				hashMap.put("当日成功装机", node.getElementsByTagName("drzj").item(0).getFirstChild().getNodeValue());
				hashMap.put("当日驳回", node.getElementsByTagName("drbh").item(0).getFirstChild().getNodeValue());
				hashMap.put("当月派单", node.getElementsByTagName("dypd").item(0).getFirstChild().getNodeValue());
				hashMap.put("当月成功装机", node.getElementsByTagName("dyzj").item(0).getFirstChild().getNodeValue());
				hashMap.put("当月驳回", node.getElementsByTagName("dybh").item(0).getFirstChild().getNodeValue());
				hashMap.put("未完成工单", node.getElementsByTagName("wwcgd").item(0).getFirstChild().getNodeValue());
				hashMap.put("超时未完成工单", node.getElementsByTagName("csgd").item(0).getFirstChild().getNodeValue());
				hashMap.put("当日APP活跃数", node.getElementsByTagName("appalive").item(0).getFirstChild().getNodeValue());
				list.add(hashMap);
			}
		}catch(Exception e)
		{
			System.out.println("Error in [T_ReportXMLRead]");
		}
		return list;
	}

	// 将T_Person类型的xml结构转换成list
	public List<HashMap<String, String>> T_PersonXMLRead(Document doc) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> tempHash = new HashMap<String, String>();
		tempHash.put("country", "country");
		tempHash.put("area", "area");
		tempHash.put("name", "name");
		tempHash.put("logname", "logname");
		tempHash.put("tel", "tel");
		tempHash.put("Email", "Email");
		tempHash.put("IdentityCard", "IdentityCard");
		tempHash.put("type", "type");
		tempHash.put("role", "role");
		list.add(tempHash);
		try{
		NodeList nList = doc.getElementsByTagName("T_Person");
		for (int i = 0; i < nList.getLength(); i++) {
			Element node = (Element) nList.item(i);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("country", node.getElementsByTagName("country").item(0)
					.getFirstChild().getNodeValue());
			hashMap.put("area", node.getElementsByTagName("area").item(0)
					.getFirstChild().getNodeValue());
			hashMap.put("name", node.getElementsByTagName("name").item(0)
					.getFirstChild().getNodeValue());
			hashMap.put("logname", node.getElementsByTagName("logname").item(0)
					.getFirstChild().getNodeValue());
			hashMap.put("tel", node.getElementsByTagName("tel").item(0)
					.getFirstChild().getNodeValue());
			hashMap.put("Email", node.getElementsByTagName("Email").item(0)
					.getFirstChild().getNodeValue());
			hashMap.put("IdentityCard",
					node.getElementsByTagName("IdentityCard").item(0)
							.getFirstChild().getNodeValue());
			hashMap.put("type", node.getElementsByTagName("type").item(0)
					.getFirstChild().getNodeValue());
			hashMap.put("role", node.getElementsByTagName("role").item(0)
					.getFirstChild().getNodeValue());
			list.add(hashMap);
		}
		}catch(Exception e)
		{
			System.out.println("Error in [T_PersonXMLRead]");
		}

		return list;
	}
	
	public List<HashMap<String, String>> T_AreaXMLRead(Document doc) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> tempHash = new HashMap<String, String>();
		tempHash.put("country", "country");
		list.add(tempHash);
		try{
			NodeList nList = doc.getElementsByTagName("T_Area");
			for (int i = 0; i < nList.getLength(); i++) {
				Element node = (Element) nList.item(i);
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("片区", node.getElementsByTagName("Area").item(0)
						.getFirstChild().getNodeValue());
				list.add(hashMap);
			}
		}
		catch(Exception e){
			System.out.println("Error in [T_AreaXMLRead]");
		}
		return list;
	}
	public List<HashMap<String, String>> T_CellXMLRead(Document doc) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> tempHash = new HashMap<String, String>();
		tempHash.put("country", "country");
		list.add(tempHash);
		try{
			NodeList nList = doc.getElementsByTagName("T_Cell");
			for (int i = 0; i < nList.getLength(); i++) {
				Element node = (Element) nList.item(i);
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("小区", node.getElementsByTagName("Cell").item(0)
						.getFirstChild().getNodeValue());
				hashMap.put("小区详细地址", node.getElementsByTagName("CellAddress").item(0)
						.getFirstChild().getNodeValue());
				list.add(hashMap);
			}
		}catch(Exception e)
		{
			System.out.println("Error in [T_CellXMLRead]");
		}
		return list;
	}
	
	public List<HashMap<String, String>> T_InModeXMLRead(Document doc) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> tempHash = new HashMap<String, String>();
		tempHash.put("country", "country");
		//list.add(tempHash);
		try{
			NodeList nList = doc.getElementsByTagName("T_InMode");
			for (int i = 0; i < nList.getLength(); i++) {
				Element node = (Element) nList.item(i);
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("接入方式", node.getElementsByTagName("InMode").item(0).getFirstChild().getNodeValue());
				hashMap.put("装机人员姓名", node.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue());
				hashMap.put("装机人员电话", node.getElementsByTagName("Phone").item(0).getFirstChild().getNodeValue());
				hashMap.put("维护人员姓名", node.getElementsByTagName("whName").item(0).getFirstChild().getNodeValue());
				hashMap.put("维护人员电话", node.getElementsByTagName("whPhone").item(0).getFirstChild().getNodeValue());
				hashMap.put("小区详细地址", node.getElementsByTagName("address").item(0).getFirstChild().getNodeValue());
				list.add(hashMap);
			}
		}catch(Exception e)
		{
			System.out.println("Error in [T_InModeXMLRead]");
		}
		return list;
	}
}
