package com.sncrc.ordersys;
import java.io.InputStream;
import java.sql.Connection;  
import java.text.SimpleDateFormat;
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List; 

import org.json.*;
import org.ksoap2.*;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.HttpTransportSE;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.R.string;
import android.util.Log;

public class DBUtil {
	private ArrayList<String> arrayList = new ArrayList<String>();  
    private ArrayList<String> brrayList = new ArrayList<String>();  
    private ArrayList<String> crrayList = new ArrayList<String>();  
    private HttpConnSoap Soap = new HttpConnSoap();  
    private ChangeXMLtoListByMode ChangeXMLtoListByMode =new ChangeXMLtoListByMode();
    //private String ServerUrl = "http://111.20.213.92:8091/WorksheetProcessingSysWeb.asmx";
    //private String ServerUrl = "http://222.41.236.19:8091/WorksheetProcessingSysWeb.asmx";
    //private String NameSpace="http://tempuri.org/";
    public static Connection getConnection() {  
        Connection con = null;  
        try {  
            //Class.forName("org.gjt.mm.mysql.Driver");  
            //con=DriverManager.getConnection("jdbc:mysql://192.168.0.106:3306/test?useUnicode=true&characterEncoding=UTF-8","root","initial");               
        } catch (Exception e) {  
            //e.printStackTrace();  
        }  
        return con;  
    }  
  
    /** 
//     * 获取县区维护人员的信息 
     *  
     * @return 
     */  
    public List<HashMap<String, String>> SelectPersonByCountry(String country ) {  
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();  
        Document doc=null;
        arrayList.clear();  
        brrayList.clear();  
        crrayList.clear(); 
        arrayList.add("country");
        brrayList.add(country);
        doc = Soap.GetWebServre("SelectPersonByCountry", arrayList, brrayList);  
       
         list=ChangeXMLtoListByMode.T_PersonXMLRead(doc);
  
        return list;  
    }  
    
    /** 
//   * 获取片区数据
   *  
   * @return 
   */  
    public List<HashMap<String, String>> SelectArea(String country ) {  
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();  
        Document doc=null;
        arrayList.clear();  
        brrayList.clear();  
        crrayList.clear(); 
        arrayList.add("country");
        brrayList.add(country);
        doc = Soap.GetWebServre("SelectArea", arrayList, brrayList);  
        list=ChangeXMLtoListByMode.T_AreaXMLRead(doc);
        return list;  
    }
    
    public List<HashMap<String, String>> GetBlqd(String phone ) {  
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();  
        HashMap<String, String> map=new HashMap<String, String>();
        String doc=null;
        map.put("type", "getblqd");
        map.put("pphone", phone);
        doc=HttpConn.GetWebServre("zdgdgl", map);
        Log.d("GetBlqd:",doc);
        list=JsonUtil.json2list(doc);
        return list;  
        }  
    
    public List<HashMap<String, String>> GetAreaZd(String area ) {  
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();     
        HashMap<String, String> map=new HashMap<String, String>();
        String doc=null;
        map.put("type", "getxz");
        map.put("xq", area);
        doc=HttpConn.GetWebServre("zdgdgl", map);
        Log.d("GetAreaZd:",doc);
        list=JsonUtil.json2list(doc);
        return list;  
    }
    
//    public String AddAddress(LinkedHashMap<String, String> params) {  
//        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();     
//        String doc=null;
//        doc=HttpConn.SendHttp("http://222.41.236.19:8091/WorksheetProcessingSysWeb.asmx/markcell","POST", params);
//        Log.d("AddAddress:",doc);
//        //list=JsonUtil.json2list(doc);
//        return doc;  
//    }
    public String AddAddress(LinkedHashMap<String, String> params) {  
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();     
        String doc=null;
        doc=HttpConn.GetWebServre("markcell", params);
        Log.d("AddAddress:",doc);
        //list=JsonUtil.json2list(doc);
        return doc;  
    }
    
    public List<HashMap<String, String>> GetZdhd() {  
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();   
        HashMap<String, String> map=new HashMap<String, String>();
        String doc=null;
        arrayList.clear();  
        brrayList.clear();  
        crrayList.clear(); 
        arrayList.add("type");
        brrayList.add("getzdlx");
        map.put("type", "getzdlx");
        doc=HttpConn.GetWebServre("zdgdgl", map);
        Log.d("GetZdhd:",doc);
        list=JsonUtil.json2list(doc);
        return list;  
    }
    
    public List<HashMap<String, String>> SelectAreaZd(String county ,String area) {  
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();      
        HashMap<String, String> map=new HashMap<String, String>();
        String doc=null;       
        map.put("type", "getjdrinfo");
        map.put("code", area);
        map.put("codetype", "pq");
        map.put("xq", county);
        doc=HttpConn.GetWebServre("zdgdgl", map);
        Log.d("SelectAreaZd:",doc);
        list=JsonUtil.json2list(doc);
        return list;  
    }
    
    public List<HashMap<String, String>> SelectZdhd(String id) {  
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();   
        HashMap<String, String> map=new HashMap<String, String>();
        String doc=null;
        map.put("type", "getzdlxi");
        map.put("id", id);
        doc=HttpConn.GetWebServre("zdgdgl", map);
        Log.d("SelectZdhd:",doc);
        list=JsonUtil.json2list(doc);
        return list;
    }
  
    public List<HashMap<String, String>> GetJdrInfo(String code ,String codetype) {  
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();      
        HashMap<String, String> map=new HashMap<String, String>();
        String doc=null;       
        map.put("type", "getjdrinfo");
        map.put("code", code);
        map.put("codetype", codetype);
        doc=HttpConn.GetWebServre("zdgdgl", map);
        Log.d("SelectAreaZd:",doc);
        list=JsonUtil.json2list(doc);
        return list;  
    }
  /** 
// * 获取片区数据
 *  
 * @return 
 */  
	public List<HashMap<String, String>> SelectCell(String country,String area) {  
		System.out.println("SelectCell");
	    List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();  
	    Document doc=null;
	    arrayList.clear();
	    brrayList.clear();
	    crrayList.clear();
	    arrayList.add("country");
	    arrayList.add("area");
	    brrayList.add(country);
	    brrayList.add(area);
	    doc = Soap.GetWebServre("SelectCell", arrayList, brrayList);
	    list=ChangeXMLtoListByMode.T_CellXMLRead(doc);
	    return list;  
	}
	
	 /** 
	// * 获取接入方式数据
	 *  
	 * @return 
	 */  
		public List<HashMap<String, String>> SelectInMode(String country,String area,String cell) {  
			System.out.println("SelectInMode");
		    List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();  
		    Document doc=null;
		    arrayList.clear();  
		    brrayList.clear();  
		    crrayList.clear(); 
		    arrayList.add("country");
		    arrayList.add("area");
		    arrayList.add("cell");
		    brrayList.add(country);
		    brrayList.add(area);
		    brrayList.add(cell);
		    doc = Soap.GetWebServre("SelectInMode", arrayList, brrayList);  
		   
		     list=ChangeXMLtoListByMode.T_InModeXMLRead(doc);
		
		    return list;  
		}
    
    /** 
//   * 根据类型获取装机订单信息
   *  
   * @return 
   */  
  public List<HashMap<String, String>> GetAllJob(String country ,String typeID,String Date ,String EndDate,String Power,String User,String Name) {  
      List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();            
      String doc=null;
      arrayList.clear();  
      brrayList.clear();  
      crrayList.clear(); 
      arrayList.add("country");
      arrayList.add("typeID");
      arrayList.add("Date");
      arrayList.add("EndDate");
      arrayList.add("Power");
      arrayList.add("User");
      arrayList.add("Name");
      brrayList.add(country);
      brrayList.add(typeID);
      brrayList.add(Date);
      brrayList.add(EndDate);
      brrayList.add(Power);
      brrayList.add(User);
      brrayList.add(Name);
      doc = Soap.GetWebServre_str("GetAllJob", arrayList, brrayList);  
      list=JsonUtil.json2list(doc);
      return list;  
  } 
  
  public List<HashMap<String, String>> GetAllFaultJob(String country ,String typeID,String Date ,String EndDate,String Power,String User,String Name) {  
      List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();            
      String doc=null;
      arrayList.clear();  
      brrayList.clear();  
      arrayList.add("country");
      arrayList.add("typeID");
      arrayList.add("Date");
      arrayList.add("EndDate");
      arrayList.add("Power");
      arrayList.add("User");
      arrayList.add("Name");
      brrayList.add(country);
      brrayList.add(typeID);
      brrayList.add(Date);
      brrayList.add(EndDate);
      brrayList.add(Power);
      brrayList.add(User);
      brrayList.add(Name);
      doc = Soap.GetWebServre_str("GetAllFaultJob", arrayList, brrayList);  
      list=JsonUtil.json2list(doc);
      return list;  
  } 
  
  public List<HashMap<String, String>> GetAllZdJob(String country ,String typeID,String Date ,String EndDate,String Power,String User,String Name) {  
      List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();            
      String doc=null;
      arrayList.clear();  
      brrayList.clear();  
      arrayList.add("country");
      arrayList.add("typeID");
      arrayList.add("Date");
      arrayList.add("EndDate");
      arrayList.add("Power");
      arrayList.add("User");
      arrayList.add("Name");
      brrayList.add(country);
      brrayList.add(typeID);
      brrayList.add(Date);
      brrayList.add(EndDate);
      brrayList.add(Power);
      brrayList.add(User);
      brrayList.add(Name);
      doc = Soap.GetWebServre_str("GetAllZdJob", arrayList, brrayList);  
      list=JsonUtil.json2list(doc);
      return list;  
  } 
  
  public String GetReport() {
      //List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();            
      Document doc=null;
      arrayList.clear();  
      brrayList.clear();  
      crrayList.clear(); 
      String d=Soap.GetWebServre_str("GetReport", arrayList, brrayList);
      return d;  
  }
  
  public String getReport(String type,String date) {
      //List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();            
      Document doc=null;
      arrayList.clear();  
      brrayList.clear();  
      
      arrayList.add("type");
      arrayList.add("pdate");
      brrayList.add(type);
      brrayList.add(date);
      String d=Soap.GetWebServre_str("getReport", arrayList, brrayList);
      Log.d("report", d);
      return d;  
  }
  
  public List<HashMap<String, String>> GetCounty() {
      arrayList.clear();
      brrayList.clear();
      crrayList.clear();
      String d=Soap.GetWebServre_str("GetFgq_County", arrayList, brrayList);
      return JsonUtil.json2list(d);  
  }
  
  public List<HashMap<String, String>> GetCell(String County) {
      arrayList.clear();
      brrayList.clear();
      crrayList.clear();
      arrayList.add("County");
      brrayList.add(County);
      String d=Soap.GetWebServre_str("GetFgq_Cell", arrayList, brrayList);
      return JsonUtil.json2list(d);  
  }
  
  public List<HashMap<String, String>> GetAddr(String County,String Cell) {
      arrayList.clear();
      brrayList.clear();
      crrayList.clear();
      arrayList.add("County");
      arrayList.add("Cell");
      brrayList.add(County);
      brrayList.add(Cell);
      String d=Soap.GetWebServre_str("GetFgq_Addr", arrayList, brrayList);
      return JsonUtil.json2list(d);  
  }
  
  public List<HashMap<String, String>> addZdJob(String id,String xq,String xz,String hdlx,String yxbbm,String jx,String yhjk,String yfhf,String fhys,String zdxf,String kbys,String cpyq,String xjcj,String pdr,String pdbz,String jdr,String qdbm,String qdmc,String khxm,String khdh,String xj,String ywq,String gsjt) {
	  HashMap<String, String> map=new HashMap<String, String>();
	  map.put("type", "update");
	  map.put("op", "new");
	  map.put("id", id);
	  map.put("xq", xq);
	  map.put("xz", xz);
	  map.put("hdlx", hdlx);
	  map.put("yxbbm", yxbbm);
	  map.put("jx", jx);
	  map.put("yhjk", yhjk);
	  map.put("yfhf", yfhf);
	  map.put("fhys", fhys);
	  map.put("zdxf", zdxf);
	  map.put("kbys", kbys);
	  map.put("cpyq", cpyq);
	  map.put("xjcj", xjcj);
	  map.put("pdr", pdr);
	  map.put("pdbz", pdbz);
	  map.put("jdr", jdr);
	  map.put("qdbm", qdbm);
	  map.put("qdmc", qdmc);
	  map.put("khxm", khxm);
	  map.put("khdh", khdh);
	  map.put("xj", xj);
	  map.put("ywq", ywq);
	  map.put("gsjt", gsjt);
	  
	  String d=HttpConn.GetWebServre("zdgdgl", map);
	  Log.d("addZdJob", d);
      return JsonUtil.json2list(d);  
  }
  
  public List<HashMap<String, String>> addKdJob(String id,String xq,String xz,String kdlx,String tclx,String yf,String dk,String pdr,String pdbz,String jdr,String qdbm,String qdmc,String khxm,String khdh,String xj,String ywq,String gsjt) {
	  HashMap<String, String> map=new HashMap<String, String>();
	  map.put("type", "update");
	  map.put("op", "new");
	  map.put("id", id);
	  map.put("xq", xq);
	  map.put("xz", xz);
	  map.put("kdlx", kdlx);
	  map.put("tclx", tclx);
	  map.put("yf", yf);
	  map.put("dk", dk);
	  map.put("pdr", pdr);
	  map.put("pdbz", pdbz);
	  map.put("jdr", jdr);
	  map.put("qdbm", qdbm);
	  map.put("qdmc", qdmc);
	  map.put("khxm", khxm);
	  map.put("khdh", khdh);
	  map.put("xj", xj);
	  map.put("ywq", ywq);
	  map.put("gsjt", gsjt);
	  
	  String d=HttpConn.GetWebServre("kdgdgl", map);
	  Log.d("addKdJob", d);
      return JsonUtil.json2list(d);  
  }
  /** 
// * 根据类型获取装机订单信息
 *  
 * @return 
 */  
     public List<HashMap<String, String>> GetJobById(String Id ) {  
    List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();            
    String doc=null;
    arrayList.clear();  
    brrayList.clear();  
    crrayList.clear(); 
    arrayList.add("Id");
 
    brrayList.add(Id);
   
    doc = Soap.GetWebServre_str("GetJobById", arrayList, brrayList);  
    //Log.d("GetJobById", doc);
    list=JsonUtil.json2list(doc);
    return list;  
    }    
     
     /** 
   // * 根据类型获取故障装机订单信息
    *  
    * @return 
    */  
        public List<HashMap<String, String>> GetFaultJobById(String Id ) {  
       List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();            
       String doc=null;
       arrayList.clear();  
       brrayList.clear();  
       crrayList.clear(); 
       arrayList.add("Id");
    
       brrayList.add(Id);
      
       doc = Soap.GetWebServre_str("GetFaultJobById", arrayList, brrayList);  
       list=JsonUtil.json2list(doc);
       return list;  
       } 
        
        public List<HashMap<String, String>> GetZdJobById(String Id ) {  
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();            
            String doc=null;
            arrayList.clear();  
            brrayList.clear();  
            crrayList.clear(); 
            arrayList.add("Id");
         
            brrayList.add(Id);
           
            doc = Soap.GetWebServre_str("GetZdJobById", arrayList, brrayList);  
            list=JsonUtil.json2list(doc);
            return list;  
            } 
        

        public List<HashMap<String, String>> InsertKr(String country,String sender,String installperson, String userInfo,String area,String cell,String celladdress,String inmode,String User,String remark) {  
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();   
            HashMap<String, String> map=new HashMap<String, String>();
            String doc=null;
            map.put("country", country);
            map.put("sender", sender);
            map.put("installperson", installperson);
            map.put("userInfo", userInfo);
            map.put("area", area);
            map.put("cell", cell);
            map.put("celladdress", celladdress);
            map.put("inmode", inmode);
            map.put("User", User);
            map.put("remark", remark);
            doc=HttpConn.GetWebServre("zdgdgl", map);
            Log.d("SelectZdhd:",doc);
            list=JsonUtil.json2list(doc);
            return list;
        }
    /** 
     * 增加一条装机信息 
     *  
     * @return 
     */  
    public String InsertJob(String country, String type, String sender,String installperson, String userInfo, String useraddress,String money,String area,String cell,String celladdress,String inmode,String User,String account,String remark) {  
  
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("Country");  
        arrayList.add("type");
        arrayList.add("sender");  
        arrayList.add("InstallPerson");  
        arrayList.add("userInfo");  
        arrayList.add("address");  
        arrayList.add("Money");  
        arrayList.add("area");
        arrayList.add("cell");
        arrayList.add("celladdress");
        arrayList.add("inmode");
        arrayList.add("User");
        arrayList.add("Account");
        arrayList.add("remark");

        brrayList.add(country);  
        brrayList.add(type);
        brrayList.add(sender); 
        brrayList.add(installperson);  
        brrayList.add(userInfo);  
        brrayList.add(useraddress); 
        brrayList.add(money); 
        brrayList.add(area);
        brrayList.add(cell);
        brrayList.add(celladdress);
        brrayList.add(inmode);
        brrayList.add(User);
        brrayList.add(account);
        brrayList.add(remark);
          
        Document doc = Soap.GetWebServre("InsertJob", arrayList, brrayList);
        if(doc==null)
        	return "网络错误";
        NodeList  nList= doc.getElementsByTagName("T_result");
        if(nList.getLength()>0)
        {
       	  Element node = (Element) nList.item(0);
          return  node.getElementsByTagName("message").item(0).getFirstChild().getNodeValue();
        }
       else
        {
			return "False";
        }
    }  
    
    /** 
     * 增加一条故障信息 
     *  
     * @return 
     */  
    public String InsertFault(final String county,final String FaultType,final String FaultDescribe,final String Sender,final String ReceivePerson,final String UserPhone,final String UserAddress,final String area,final String cell,final String celladdress,final String inmode ,final String User) {  
  
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("County");  
        arrayList.add("FaultType");
        arrayList.add("FaultDescribe");
        arrayList.add("Sender");  
        arrayList.add("ReceivePerson");  
        arrayList.add("UserPhone");  
        arrayList.add("UserAddress");   
        arrayList.add("area");
        arrayList.add("cell");
        arrayList.add("celladdress");
        arrayList.add("inmode");
        arrayList.add("User");

        brrayList.add(county);  
        brrayList.add(FaultType);
        brrayList.add(FaultDescribe);
        brrayList.add(Sender); 
        brrayList.add(ReceivePerson);  
        brrayList.add(UserPhone);  
        brrayList.add(UserAddress); 
        brrayList.add(area);
        brrayList.add(cell);
        brrayList.add(celladdress);
        brrayList.add(inmode);
        brrayList.add(User);
          
        Document doc = Soap.GetWebServre("InsertFault", arrayList, brrayList);
        if(doc==null)
        	return "网络错误";
        NodeList  nList= doc.getElementsByTagName("T_result");
        if(nList.getLength()>0)
        {
       	  Element node = (Element) nList.item(0);
          return  node.getElementsByTagName("message").item(0).getFirstChild().getNodeValue();
        }
       else
        {
			return "False";
        }
    }  
    
    /*
     * 派单回单
     *  
     * @return 
     */  
    public String ReplayJob(String ID,String result,String MainReason,String Reason,String SN,String WiringMode ,String remark,String useraccount,String userid,String needphoto) { 

    	 
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("ID");  
        arrayList.add("result");
        arrayList.add("MainReason");  
        arrayList.add("Reason");
        arrayList.add("SN");  
        arrayList.add("WiringMode");
        arrayList.add("remark");
        arrayList.add("useraccount");
        arrayList.add("userid");
        arrayList.add("needphoto");
        brrayList.add(ID);  
        brrayList.add(result);
        brrayList.add(MainReason);  
        brrayList.add(Reason);
        brrayList.add(SN);  
        brrayList.add(WiringMode);
        brrayList.add(remark);
        brrayList.add(useraccount);
        brrayList.add(userid);
        brrayList.add(needphoto);
        Document doc =  Soap.GetWebServre("ReplyJob", arrayList, brrayList);  
        if(doc==null)
        	return "网络错误";
        NodeList  nList= doc.getElementsByTagName("T_result");
        if(nList.getLength()>0)
        {
       	  Element node = (Element) nList.item(0);
          return  node.getElementsByTagName("message").item(0).getFirstChild().getNodeValue();
        }
       else
        {
			return "False";
        }
	}
    
    /*
     * 故障回单
     *  
     * @return 
     */  
    public String ReplayFaultJob(String ID,String result,String faultreason,String remark,String userid,String calltime,String duration) { 

    	 
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("ID");  
        arrayList.add("result");
        arrayList.add("faultreason");  
        arrayList.add("remark");
        arrayList.add("userid");
        arrayList.add("calltime");
        arrayList.add("duration");
        brrayList.add(ID);  
        brrayList.add(result);
        brrayList.add(faultreason);  
        brrayList.add(remark);
        brrayList.add(userid);
        brrayList.add(calltime);
        brrayList.add(duration);
        Document doc =  Soap.GetWebServre("ReplyFaultJob", arrayList, brrayList);  
        if(doc==null)
        	return "网络错误";
        NodeList  nList= doc.getElementsByTagName("T_result");
        if(nList.getLength()>0)
        {
       	  Element node = (Element) nList.item(0);
          return  node.getElementsByTagName("message").item(0).getFirstChild().getNodeValue();
        }
       else
        {
			return "False";
        }
	}
    
    public String ReplayZdJob(String ID,String result,String Reason,String remark,String userid) { 

   	 
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("ID");  
        arrayList.add("result");
        arrayList.add("Reason");  
        arrayList.add("remark");
        arrayList.add("userid");
        brrayList.add(ID);  
        brrayList.add(result);
        brrayList.add(Reason);  
        brrayList.add(remark);
        brrayList.add(userid);
        String doc =  Soap.GetWebServre_str("ReplyZdJob", arrayList, brrayList);  
        return doc;
	}
        
    /*
     * 判断当前用户身份是否合法
     *  
     * @return 
     */  
    public String IsExistAccount(String account,String psw,String ver) {  
    	 
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("account");  
        arrayList.add("password");
        arrayList.add("ver");
        brrayList.add(account);
//        String encodepwd="";
//        try{
//        	encodepwd=MD5Util.md5Encode(psw);
//        }catch(Exception e)
//        {
//        	System.out.println("密码加密出错");
//        }
        brrayList.add(psw);
        brrayList.add(ver);
        try{
	        Document doc =  Soap.GetWebServre("IsExistAccount", arrayList, brrayList);  
	        if(doc==null)
	        	return "网络错误";
	        NodeList  nList= doc.getElementsByTagName("T_result");
	        if(nList.getLength()>0)
	        {
	       	  Element node = (Element) nList.item(0);
	       	  String info=doc.getElementsByTagName("message").item(0).getFirstChild().getNodeValue();
	          return  info;
	        }
	       else
	        {
				return "服务器错误";
	        }
        }catch(Exception e)
        {
        	 	return "登录出错";
        }
    } 
    
    public String GetVersion() {  
   	 
        arrayList.clear();  
        brrayList.clear();  
        try{
        	//String doc=Soap.GetWebServre_str("GetVersion", arrayList, brrayList);
	        Document doc=Soap.GetWebServre("GetVersion", arrayList, brrayList);
        	if(doc==null)
	        	return "网络错误";
        	NodeList  nList= doc.getElementsByTagName("T_Version");
	        if(nList.getLength()>0)
	        {
	        	Element node = (Element) nList.item(0);
	        	String s=node.getElementsByTagName("Version").item(0).getFirstChild().getNodeValue()+"|"+node.getElementsByTagName("Url").item(0).getFirstChild().getNodeValue()+"|"+node.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue()+"|"+node.getElementsByTagName("Detail").item(0).getFirstChild().getNodeValue();
	        	return  s;
	        }
	       else
	        {
				return "服务器错误";
	        }
        }catch(Exception e)
        {
        	return "服务器错误";
        }
        
    }
    
    public String GetPersonalNum(String user) 
    {    		
    	arrayList.clear();  
        brrayList.clear();  
        arrayList.add("user");
        brrayList.add(user);  

	    String doc = Soap.GetWebServre_str("GetPersonalNum", arrayList, brrayList);
	    return doc;
    }
   
    public String AccManagerOpt(String ID,String user, String type, String remark) {  
    	  
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("ID");  
        arrayList.add("user");
        arrayList.add("type");  
        arrayList.add("remark");  

        brrayList.add(ID);  
        brrayList.add(user);
        brrayList.add(type); 
        brrayList.add(remark);   

        try{  
	        Document doc = Soap.GetWebServre("AccManagerOpt", arrayList, brrayList);  
	        if(doc==null)
	        	return "网络错误";
	        NodeList  nList= doc.getElementsByTagName("T_result");
	        if(nList.getLength()>0)
	        {
	       	  Element node = (Element) nList.item(0);
	          return  node.getElementsByTagName("message").item(0).getFirstChild().getNodeValue();
	        }
	       else
	        {
				return "False";
	        }
        }catch(Exception e)
        {
        	return "False";
        }
    } 
    
    public String UploadImg(String ID,String fileName, String imageBuffer,String fileName1,String imageBuffer1,String fgqaddr,String longitude,String latitude,String location,String fgqnum,String fgqport) 
    {    		
    	arrayList.clear();  
        brrayList.clear();  
        arrayList.add("ID");  
        arrayList.add("fileName");
        arrayList.add("imageBuffer");
        arrayList.add("fileName1");
        arrayList.add("imageBuffer1");
        arrayList.add("fgqaddr");
        arrayList.add("longitude");
        arrayList.add("latitude");
        arrayList.add("location");
        arrayList.add("fgqnum");
        arrayList.add("fgqport");
        
        brrayList.add(ID);  
        brrayList.add(fileName);  
        brrayList.add(imageBuffer);
        brrayList.add(fileName1);  
        brrayList.add(imageBuffer1);
        brrayList.add(fgqaddr);
        brrayList.add(longitude);
        brrayList.add(latitude);
        brrayList.add(location);
        brrayList.add(fgqnum);
        brrayList.add(fgqport);
	    String doc = Soap.GetWebServre_str("UploadImg", arrayList, brrayList);
	    return doc;
    }
    
    public String GetAddrName(String AddrID) 
    {    		
    	arrayList.clear();  
        brrayList.clear();  
        arrayList.add("AddrID");
        brrayList.add(AddrID);  

	    String doc = Soap.GetWebServre_str("GetAddrName", arrayList, brrayList);
	    return doc;
    }
    
    public String DownloadImg(String fileName,String fileName1) 
    {    		
    	arrayList.clear();  
        brrayList.clear();  
        arrayList.add("fileName");
        arrayList.add("fileName1");
        brrayList.add(fileName);  
        brrayList.add(fileName1);  

	    String doc = Soap.GetWebServre_str("DownloadImg", arrayList, brrayList);
	    return doc;
    }
    //弃单审核
    public String Qdsh(String op, String id, String remark,String name) {  
  	  
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("op");  
        arrayList.add("id");
        arrayList.add("remark");  
        arrayList.add("name");  

        brrayList.add(op);  
        brrayList.add(id);
        brrayList.add(remark); 
        brrayList.add(name);   

	    String doc = Soap.GetWebServre_str("Qdsh", arrayList, brrayList);
	    return doc;
    } 
    
  //弃单审核
    public String Zpsh(String id,String op,String account,String remark,String name) {  
  	  
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("id");  
        arrayList.add("op");
        arrayList.add("account");
        arrayList.add("remark");
        arrayList.add("name");  

        brrayList.add(id); 
        brrayList.add(op);  
        brrayList.add(account);
        brrayList.add(remark); 
        brrayList.add(name);   

	    String doc = Soap.GetWebServre_str("Zpsh", arrayList, brrayList);
	    return doc;
    } 
    
    public String FaultManagerOpt(String ID,String user, String type) {  
  	  
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("ID");  
        arrayList.add("user");
        arrayList.add("type");  

        brrayList.add(ID);  
        brrayList.add(user);
        brrayList.add(type);    

        try{  
	        Document doc = Soap.GetWebServre("FaultManagerOpt", arrayList, brrayList);  
	        if(doc==null)
	        	return "网络错误";
	        NodeList  nList= doc.getElementsByTagName("T_result");
	        if(nList.getLength()>0)
	        {
	       	  Element node = (Element) nList.item(0);
	          return  node.getElementsByTagName("message").item(0).getFirstChild().getNodeValue();
	        }
	       else
	        {
				return "False";
	        }
        }catch(Exception e)
        {
        	return "False";
        }
    } 
    
    public String ZdListOpt(String ID,String user,String name, String type) {  
    	  
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("ID");  
        arrayList.add("user");
        arrayList.add("name");
        arrayList.add("type");  

        brrayList.add(ID);  
        brrayList.add(user);
        brrayList.add(name);
        brrayList.add(type);    

	    String doc = Soap.GetWebServre_str("ZdListOpt", arrayList, brrayList);  
	    return doc;
    } 
    
    public String NewAccount(String county,String name,String pwd,String power,String category,String phone,String user) {  
  	  
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("county");  
        arrayList.add("name");
        arrayList.add("pwd");  
        arrayList.add("power"); 
        arrayList.add("category");  
        arrayList.add("phone");
        arrayList.add("user");  

        brrayList.add(county);  
        brrayList.add(name);
        brrayList.add(pwd); 
        brrayList.add(power); 
        brrayList.add(category);  
        brrayList.add(phone);  
        brrayList.add(user); 
        try{
	        Document doc = Soap.GetWebServre("NewAccount", arrayList, brrayList);
	        if(doc==null)
	        	return "网络错误";
	        NodeList  nList= doc.getElementsByTagName("T_result");
	        if(nList.getLength()>0)
	        {
	       	  Element node = (Element) nList.item(0);
	          return  node.getElementsByTagName("message").item(0).getFirstChild().getNodeValue();
	        }
	       else
	        {
				return "False";
	        }
        }catch(Exception e)
        {
        	return "False";
        }
    } 
    
    public String ChangePwd(String user,String oldpwd,String pwd) {  
    	  
        arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("user");  
        arrayList.add("oldpwd");
        arrayList.add("pwd");   

        brrayList.add(user);  
        brrayList.add(oldpwd);
        brrayList.add(pwd); 

        try{
	        Document doc = Soap.GetWebServre("ChangePwd", arrayList, brrayList);
	        if(doc==null)
	        	return "网络错误";
	        NodeList  nList= doc.getElementsByTagName("T_result");
	        if(nList.getLength()>0)
	        {
	       	  Element node = (Element) nList.item(0);
	          return  node.getElementsByTagName("message").item(0).getFirstChild().getNodeValue();
	        }
	       else
	        {
				return "False";
	        }
        }catch(Exception e)
        {
        	return "False";
        }
    }

	public String UploadCID(String User,String CID) {
		arrayList.clear();  
        brrayList.clear();  
          
        arrayList.add("User");  
        arrayList.add("CID");   

        brrayList.add(User);  
        brrayList.add(CID);
        try{
	        Document doc = Soap.GetWebServre("UploadCID", arrayList, brrayList);
	        if(doc==null)
	        	return "网络错误";
	        NodeList  nList= doc.getElementsByTagName("T_result");
	        if(nList.getLength()>0)
	        {
	       	  Element node = (Element) nList.item(0);
	          return  node.getElementsByTagName("message").item(0).getFirstChild().getNodeValue();
	        }
	       else
	        {
				return "False";
	        }
        }catch(Exception e)
        {
        	return "False";
        }
	}
}
