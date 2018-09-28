package com.guye.utils;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;


/**
 * 
 * @author admin
 *
 * @modify list: 增加 多级目录的配置，如：  vr/iesp/base , base 的配置，可以赢 继承 iesp 下的  common ， 或者 继承 vr/common 
 */
public class PropertiesFactory {
	
	private PropertiesFactory(){};	
	
	/**
	 * @param projectName 项目名称
	 * @param propertiesName 配置文件名称
	 * @return Properties
	 * */
	public static Properties createProperties(String projectName,String propertiesName){
		Properties properties = new Properties();
		String path = projectName+"/"+propertiesName;
		ResourceBundle rb = ResourceBundle.getBundle(path.trim());
		Enumeration<String> allKey = rb.getKeys(); 
		//取父配置，并判断是否存在
		//Properties parentProp = getParentProperties(propertiesName);
		List<Properties> ps = getParent(projectName,propertiesName);
				
	    while (allKey.hasMoreElements()) {  
	        String key = allKey.nextElement().trim();  
	        String value = rb.getString(key).trim(); 
	        if (value==null || value.length()==0 || value.contains("$parent$")) {
	        	String pValue=getParentValue(ps,key,value);
	        	if (pValue!=null) {
					value = pValue;
				}				
	        }
	        if("jdbc.maxActive".equals(key)&&SystemTools.isWindows()){
				properties.put(key, SystemTools.getDbInitSize());
			}else if("jdbc.initialSize".equals(key)&&SystemTools.isWindows()){
				properties.put(key, SystemTools.getDbInitSize());
			}else if("jdbc.minIdle".equals(key)&&SystemTools.isWindows()){
				properties.put(key, SystemTools.getDbInitSize());
			}else{
				properties.put(key, value);
			}

	    }
		return properties;
	}
	
	private static String getParentValue(List<Properties> ps,String key,String value){
		int i=0;
		String pValue=null;
		Properties p;
		while(i<ps.size()){
			p=ps.get(i);
			pValue = p.getProperty(key);  
			if(pValue!=null && pValue.length()>0){
				pValue=pValue.trim();
				if(value.length()==0){
					value=pValue; 
				}else{
					if(value.contains("$parent$")){
						value=value.replace("$parent$", pValue);
					}
				}
				if(value.contains("$parent$")==false){
					break;  //无变量替换,跳出
				}
			}
			i++;
		}
		return value;
	}
	
	/**
	 * 修改dubbo配置文件
	 * @param projectName 项目名称
	 * @param propertiesName 配置文件名称
	 * *//*
	public stat void modifyDubboProperties(String projectName,String propertiesName){
		PropertiesFactory propertiesFactory = new PropertiesFactory();
		propertiesFactory.modifyProperties(projectName, propertiesName, "/", "dubbo");
	}*/
	
	
	/**
	 * 修改src目录下的配置文件
	 * @param projectName 项目名称
	 * @param propertiesName 配置文件名称
	 * @param path 保存配置文件路径
	 * @param targetName 保存配置文件名称
	 * */
	public static void modifyProperties(String projectName,String propertiesName,String path,String targetName){
		Properties sourceProp = createProperties(projectName, propertiesName);
		Properties dubboProp = new Properties();  
		targetName = path+targetName+".properties";
		
		FileInputStream is;
		try {
			is = new FileInputStream(targetName);
			dubboProp.load(is);
		} catch (Exception e) {
			File file = new File(targetName);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
		
		try {			
			Iterator<Entry<Object, Object>> it = sourceProp.entrySet().iterator();  
	        while (it.hasNext()) {  
	            Entry<Object, Object> entry = it.next();  
	            String key = entry.getKey().toString();  
	            String value = entry.getValue().toString();  
	            dubboProp.setProperty(key, value);
	        }  
	        OutputStream fos=new FileOutputStream(targetName);
	        dubboProp.store(fos, "");
	        fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 取父配置目录
	 * @param propertiesName 配置名
	 * @return Properties
	 * */
	private static List<Properties> getParent(String path,String propertiesName){
		ArrayList<Properties> ps=new ArrayList<Properties>();
		if(path==null || path.length()==0){
			Properties p=getParentProperties(propertiesName);
			if(p!=null){
				ps.add(p);
			}
			return ps;
		}
		Properties p;
		int i=path.lastIndexOf("/");
		while(i>0){
			String dir= path.substring(0,i)+"/common";
			p=getParentProperties(dir,propertiesName);
			if(p!=null){
				ps.add(p);
			}
			i=(path.substring(0,i)).lastIndexOf('/');
		}
		p=getParentProperties(propertiesName);
		if(p!=null){
			ps.add(p);
		}
		return ps;
	}
	
	
	/**
	 * 取父配置信息
	 * @param propertiesName 配置名
	 * @return Properties
	 * */
	private static Properties getParentProperties(String propertiesName){
		return getParentProperties("common", propertiesName);
	}
	
	/**
	 * 取父配置信息
	 * @param pathName 路径
	 * @param propertiesName 配置名
	 * @return Properties
	 * */
	private static Properties getParentProperties(String pathName,String propertiesName){
		String parentPath = pathName+"/"+propertiesName;
		try {
			ResourceBundle prb = ResourceBundle.getBundle(parentPath.trim());
			Properties properties = new Properties();
			Enumeration<String> pkeys = prb.getKeys(); 
			while (pkeys.hasMoreElements()) {  
		        String key = pkeys.nextElement();  
		        String value = prb.getString(key).trim(); 
		        properties.put(key, value); 
		    }
			return properties;
		} catch (Exception e) {
//			System.out.println(propertiesName+".properties配置无父配置");
		}
		return null;
	}
	
	public static void main(String[] args) {
			Properties properties = createProperties("common", "ceshi");
			Enumeration<Object> keys = properties.keys(); 
			while(keys.hasMoreElements()){
				String key=(String)keys.nextElement();
				System.out.println(key+"="+properties.getProperty(key));
			}
			 
	}
}
