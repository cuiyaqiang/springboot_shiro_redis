package com.guye.utils;

import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.File;
import java.io.FileReader;  
import java.io.FileWriter;  
import java.io.FilenameFilter;
import java.io.IOException;  
import java.lang.reflect.Field;
import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.HashMap;  
import java.util.HashSet;
import java.util.LinkedHashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.Set;
import java.util.Map.Entry;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  

  
public class MapperUtil {  
      
	//-----------------------------需要自定义----------------------------
	
	/** sql导出文件所在文件夹的路径，文件夹中只能有一个文件，结尾斜杠不可省略*/ 
	private static String sqlFileFolder = "C:/Users/suneee/Desktop/sql2Bean/";  

    /** 类注释中的作者*/
    private static String author = "@author cuiyaqiang";

    /** 生成实体类所在包路径 */
    private static String modelPackagePath = "com.guye.model";
    
    /** 生成dao类所在包名*/  
    private static String daoPackageName = "com.guye.dao.master";
    
	//-----------------------------不需要修改----------------------------
    /** sql导出文件全路径*/  
    private static String sqlFile = getSqlFile(sqlFileFolder);  
    
    //生成的实体类的简单类名
    private static String className;
    
    //数据库表名
    private static String tableName;
    
    //数据库主键
    private static String pk;
    
    /** 表前缀*/  
    public static String table_prefix = getPrefix();  
    
    /** 包路径之前的根路径*/  
    private static String localPath = getPath();
      
    /** 实体类的包名 */  
    private static String packageName = "package @packPath@;\r\n\r\n@date@\r\n\r\n/** \r\n * @sign\r\n * " + author + "\r\n */\r\n";  
      
    /** 导入Date*/    
    private static String importDate = "import java.util.Date;";  
      
    /** 存文件名称 */   
    private static Map<String, String> fileNameMap = new LinkedHashMap<String, String>();  
      
    /** 存类注释 */ 
    private static Map<String, String> tipMap = new LinkedHashMap<String, String>();  
      
    /** 存get/set方法 */ 
    private static Map<String, String> methodMap = new LinkedHashMap<String, String>();  
      
    /** 存字段 */   
    private static Map<String, Map<String, String>> dataMap = new HashMap<String, Map<String, String>>();  
      
    /** pg字段类型的集合 */  
    private static List<String> isFieldList = new ArrayList<String>();  
    private static List<String> longList = Arrays.asList("BIGINT", "INT4","INT8", "BIGSERIAL", "SERIAL8");  
    private static List<String> intList = Arrays.asList("SMALLINT", "INT2", "NUMERIC", "SERIAL", "SERIAL4"  );  
    private static List<String> doubleList = Arrays.asList("DECIMAL", "REAL", "FLOAT4", "DOUBLE", "FLOAT8");  
    private static List<String> stringList = Arrays.asList("CHARACTER", "VARCHAR", "CHAR", "TEXT");  
    private static List<String> timeList = Arrays.asList("INTERVAL", "TIMESTAMP", "DATE", "TIME");  
    static {  
        isFieldList.addAll(longList);  
        isFieldList.addAll(intList);  
        isFieldList.addAll(doubleList);  
        isFieldList.addAll(stringList);  
        isFieldList.addAll(timeList);  
    }  
      
    /** 
     * 数据库类型转代码类型 
     * @param fieldType 字段类型 
     * @return String 
     */ 
    public static String getClassType(String fieldType) {
    	fieldType =fieldType.toUpperCase();
        String type = fieldType.replaceAll(",", "");  
        int idx = type.indexOf("(");  
        if (idx >= 0) {  
            type = type.substring(0, idx);  
        }  
        if (longList.contains(type)) {  
            return "Long";  
        } else if (intList.contains(type)) {  
            if ("NUMERIC".equals(type)) {  
                String[] arr = fieldType.replaceAll("NUMERIC", "").replace("(", "").replace(")", "").split(",");  
                if (arr.length >1 && Integer.parseInt(arr[1]) > 1) {  
                    return "Double";  
                } else if (Integer.parseInt(arr[0]) > 5) {  
                    return "Long";  
                }  
            }  
            return "Integer";  
        } else if (doubleList.contains(type)) {  
            return "Double";  
        } else if (stringList.contains(type)) {  
            return "String";  
        } else if (timeList.contains(type)) {  
            return "Date";  
        }   
        return "";  
    }  
      
    /** 
     * 校验是否为数据库字段 
     * @param str 字段 
     * @return boolean 
     */  
    public static boolean checkField(String str) {  
        if (str.indexOf("NUMERIC") >= 0 || str.indexOf("VARCHAR") >= 0) {  
            return true;  
        }  
        
        String st = strToArr(str.toUpperCase())[1];
        if(!(null == st || st.length() <0)){
        	st = st.replaceAll(",", "");
        	
        	int idx = st.indexOf("(");  
            if (idx >= 0) {  
            	st = st.substring(0, idx);  
            }  
        }
        return isFieldList.contains(st);  
    }  
      
    /** 
     * 多个空格转为一个空格并将字符串转为数组 
     * @param str 
     * @return String[] 
     */   
    public static String[] strToArr(String str) {  
        str = dealSpace(str);  
        String[] arr = str.split(" "); 
        
        if (arr.length < 2) {  
            return Arrays.copyOf(arr, 2);  
        }  
        return arr;  
    }  
      
    /** 
     * 处理空格（去掉首尾空格且将多个空格转为一个空格） 
     * @param str 字符串 
     * @return String 
     */   
    public static String dealSpace(String str) {  
        Pattern p = Pattern.compile("\\s+");  
        Matcher m = p.matcher(str.trim());  
        return m.replaceAll(" ");  
    } 
    
      
    /** 
     * 创建javabean文件 
     * @param inFile sql脚本路径 
     * @param outPath 目标目录 
     * @throws Exception 
     */  
    public static void createBean(String inFile, String outPath) throws Exception {  
        BufferedReader reader = getReader(inFile);  
        String sign = null;         //每一次读一行的数据
        packageName = packageName.replace("@packPath@", modelPackagePath);  
        while ((sign = reader.readLine()) != null) {  
            int index = 0;  
            sign = dealSpace(sign);  
            String upperSign = sign.toUpperCase();   //数据转大写    
            if ((index = upperSign.indexOf("CREATE TABLE")) >= 0) {  //保存类型
                className = getClassName(sign.substring(index + "CREATE TABLE".length(), sign.length()).replaceAll(" ", "").replace("(","").replace(table_prefix, ""));  
                className = dealClassName(className);
                tableName = dealClassName(sign.substring(index + "CREATE TABLE".length(), sign.length()).replaceAll(" ", "").replace("(","")).replace("\"", "");
                pk = tableName.replace(table_prefix, "pk");
                fileNameMap.put(className, outPath + className);  
                Map<String, String> map = new LinkedHashMap<String, String>();  
                map.put("classHead", packageName + "public class " + className + " {\r\n\r\n");  
                dataMap.put(className, map);  
            } else if (checkField(upperSign)) { //保存属性与get/set方法    
                String[] arr = strToArr(sign);  
                String fieldName = arr[0].replaceAll("\"", "");     //属性名  
                String classType = getClassType(arr[1]);    //属性类型
                Map<String, String> fieldMap = dataMap.get(className);  
                String classHead = fieldMap.get("classHead");  
                if ("Date".equals(classType) && classHead.indexOf("@date@") >= 0) {  
                    classHead = classHead.replace("@date@", importDate);  
                    fieldMap.put("classHead", classHead);  
                }  
                String fieldVal = "\tprivate " + classType + " " + fieldName + ";\r\n\r\n";  
                fieldMap.put(fieldName, fieldVal);  
                String oldMethod = methodMap.get(className);  
                String method = "\tpublic " + classType + " get"+ getMethodName(fieldName) + "() {\r\n\t\treturn " + fieldName   
                        + ";\r\n\t}\r\n\r\n\tpublic void set" + getMethodName(fieldName) + "(" + classType + " " + fieldName+") {\r\n\t\tthis."  
                        + fieldName + " = " + fieldName+";\r\n\t}\r\n\r\n";  
                if (oldMethod == null) {  
                    methodMap.put(className, method);  
                } else {  
                    methodMap.put(className, oldMethod + method);  
                }  
            } else if ((index = upperSign.indexOf("COMMENT ON TABLE")) >= 0) {   //������ע��  
                int idx = sign.indexOf(" IS ");  
                sign = sign.substring(idx + 5, sign.length() - 2);  
                sign = sign.replace("��", "");  
                tipMap.put(className, sign);  
                sign = sign + "ʵ����";  
                Map<String, String> fieldMap = dataMap.get(className);  
                String classHead = fieldMap.get("classHead");  
                classHead = classHead.replace("@sign@", sign);  
                fieldMap.put("classHead", classHead);  
            } else if ((index = upperSign.indexOf("COMMENT ON COLUMN")) >= 0) {  //��������ע��  
                int idx = sign.indexOf(" IS ");  
                String field = sign.substring(sign.lastIndexOf(".") + 1, idx).replaceAll("\"", "");  
                String tip = sign.substring(idx + 5, sign.length() - 2);  
                Map<String, String> fieldMap = dataMap.get(className);  
                String val = fieldMap.get(field);  
                if (val != null) {  
                    fieldMap.put(field, "\t/** " + tip + " */\r\n" + val);  
                }  
            } else if ((index = upperSign.indexOf("CONSTRAINT")) >= 0 || (index = upperSign.indexOf(");")) >= 0) {  
                continue;  
            }  
        }  
        reader.close();  
        for (Entry<String, Map<String, String>> entryMap : dataMap.entrySet()) {  
            String key = entryMap.getKey();  
            String outFilePath = fileNameMap.get(key);  
            BufferedWriter writer = getWriter(outFilePath + ".java");  
            BufferedWriter writerVO = getWriter(outFilePath + "VO.java");  
            BufferedWriter writerDO = getWriter(outFilePath + "DO.java");  
            Map<String, String> fieldMap = entryMap.getValue();  
            String classHead = fieldMap.get("classHead");  
            if (classHead.indexOf("@date@") >= 0) {  
                fieldMap.put("classHead", classHead.replace("@date@", ""));  
            }  
            for (Entry<String, String> entry : fieldMap.entrySet()) {  
                writer.write(entry.getValue());  
                writerDO.write("classHead".equals(entry.getKey()) ? entry.getValue().replace(" {", "DO {") : entry.getValue());  
                writerVO.write("classHead".equals(entry.getKey()) ? entry.getValue().replace(" {", "VO {") : entry.getValue());  
            }  
            writer.write(methodMap.get(key));  
            writerDO.write(methodMap.get(key));  
            writerVO.write(methodMap.get(key));  
            writer.write("}");  
            writerDO.write("}");  
            writerVO.write("}");  
            writer.close();  
            writerDO.close();  
            writerVO.close();  
        }  
        System.out.println("beanOver");  
    }  
    
      
    /** 
     * 处理表头可能有的前缀 CREATE TABLE "public"."smf_capital_application"
     * @param str 
     * @return 
     */  
    public static String dealClassName(String str) {  
       return str.substring(str.indexOf(".") +1);
    }  
    
    /** 
     * 将数据库字段格式转为类名格式 
     * @param str 
     * @return 
     */  
    public static String getClassName(String str) {  
    	str = str.replaceAll("\"", "");
        int firIdx = 0;  
        while ((firIdx = str.indexOf("_")) >= 0) {  
            String first = str.substring(firIdx + 1, firIdx + 2);  
            str = str.replace("_"+first, first.toUpperCase());  
        }  
        return str;  
    }  
      
    /** 
     * 字符串转为首字母大写 
     * @param str 字符串 
     * @return String 
     */  
    public static String getMethodName(String str) {  
        str = str.replaceAll("\"", ""); 
        String firstName = str.substring(0, 1);  
        if (firstName.equals(firstName.toLowerCase())) {  
            str = firstName.toUpperCase() + str.substring(1);  
        }  
        return str;  
    }  
      
    /** 
     * 字符串转为变量格式 
     * @param str 字符串 
     * @return String 
     */  
    public static String getFieldName(String str) {  
        str = str.replaceAll("\"", "");
        String firstName = str.substring(0, 1);  
        if (!firstName.equals(firstName.toLowerCase())) {  
            str = firstName.toLowerCase() + str.substring(1);  
        }  
        return str;  
    }  
      
    /** 
     * 获取缓冲输入流 
     * @param fileUrl 路径
     * @return BufferedWriter 
     * @throws IOException 
     */   
    public static BufferedReader getReader(String fileUrl) throws IOException {  
        String filePath = fileUrl.substring(0, fileUrl.lastIndexOf("/") + 1);  
		File file = new File(filePath);  
		if (!file.exists() && !file.mkdirs()) {  
			throw new IOException("创建目录失败");  
		}  
		return new BufferedReader(new FileReader(fileUrl));  
    }  
      
    /** 
     * 获取缓冲输出流 
     * @param filePath 路径 
     * @return BufferedWriter 
     * @throws IOException 
     */   
    public static BufferedWriter getWriter(String filePath) throws IOException {  
    	File file = new File(filePath);    
    	File fileParent = file.getParentFile();  
    	if(!fileParent.exists()){  
    	    fileParent.mkdirs();  
    	}  
    	file.createNewFile();  
        return new BufferedWriter(new FileWriter(file));
    }  
    
    //根据项目所在是路径获取生成bean所在根路径（包路径之前的uri）
    private static String getPath(){
    	
    	String clazzPath = MapperUtil.class.getResource("").getFile();
    	System.out.println("项目路径:" + clazzPath);
    	
    	String middle = "src/main/java/";
    	int index = clazzPath.indexOf("target");
    	if (index == -1) {
			index = clazzPath.indexOf("bin");
			middle = "src/";
			if (index == -1) {
				throw new RuntimeException("请使用maven工程或者java工程作为该文件的所属工程");
			}
		}
    	String root = clazzPath.substring(0, index);
    	return root + middle;
    }
      
    //扫描文件夹下sql文件，获取需要生成bean的sql文件名
    private static String getSqlFile(String folder){
    	File sqlFolder = new File(folder);
    	if (sqlFolder.isDirectory()) {
    		String[] names = sqlFolder.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".sql");
				}
			});
    		if(names.length == 1) {
    			return folder + names[0];
    		}else {
    			throw new RuntimeException("读取的sql的文件夹不存在sql文件");
    		}
		}else {
			throw new RuntimeException("读取的sql的文件夹路径不正确");
		}
    }
    
    //获取数据库表前缀
    private static String getPrefix(){
    	String sqlFileName = sqlFile.substring(sqlFileFolder.length());
    	return sqlFileName.substring(0, sqlFileName.indexOf("_"));
    }

    
    
    static class Bean2Sql {
    	
    	//新增时排除字段以及实体类中的不需要的属性        自定义
    	private Set<String> excludeFieldsInInsert = new HashSet<>(Arrays.asList(pk));
    	//编辑时排除字段以及实体类中的不需要的属性        自定义
    	private Set<String> excludeFieldsInEdit = new HashSet<>(Arrays.asList(pk, "enterpriseid"));
    	
    	//删除时变更的字段        自定义
    	private Set<String> fieldsInDelete = new HashSet<>();
    	
    	//------------------------  以下无需修改            ------------------------------//
    	
    	//目标实体类
        @SuppressWarnings("all")
    	private Class bean;
    	//目标表名称
    	//private static String tableName = "stp_investment_error";
    	//目标实体所在的包路
    	private String packDO;
    	//目标实体所在的包路
    	private String packVO;
    	//目标dao所在的包路
    	private String packDao;
    	//获取主键
    	//预定义删除标志字段
    	private final Set<String> deleteMarkFields = new HashSet<>(Arrays.asList("cancelid","cancelname","canceldate"));
    	//预定义编辑标志字段
    	private final Set<String> editMarkFields = new HashSet<>(Arrays.asList("modiferid","modifername","modefydate"));
    	//预定义插入标志字段
    	private final Set<String> bookinMarkFields = new HashSet<>(Arrays.asList("inputmanid","inputmanname","bookindate"));
    	//xml首尾
    	private final String head = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
    			+ "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n";
    	private final String tail = "</mapper>";
    	
    	@SuppressWarnings("all")
    	public Bean2Sql(Class bean){
    		this.bean = bean;
    		this.packDO = bean.getName() + "DO";
    		this.packVO = bean.getName() + "VO";
    		this.packDao = daoPackageName + "." + className + "Dao";
    	}
    	
    	//程序入口
    	private void getMapperAndDao() {
    		long start = System.currentTimeMillis();
    		System.out.println();
    		
    		String mapperXML = head + "<mapper namespace=\"" + packDao + "\" >\n" + getPageList() + getInsert() + getDelete() + getUpdate() + getGetById() + getCountByCode() + tail;
    		
    		String daoFolder = localPath + daoPackageName.replace(".", "/") + "/";
    		
    		File file = new File(daoFolder);
    		if (file.exists() && !file.isDirectory()) {
				throw new RuntimeException("dao生成路径已存在且不是文件夹");
			}
    		if (!file.exists()) {
    			file.mkdirs();
    		}
    		
    		try {
    			writerFile(mapperXML,daoFolder,bean.getSimpleName().toString()+"Mapper","xml");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
    		System.out.println("=====================MapperXML耗时" + (System.currentTimeMillis() - start) + "毫秒=======================");
    		String daoJava = getDao();
    		try {
    			writerFile(daoJava,daoFolder,bean.getSimpleName().toString()+"Dao","java");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
    		System.out.println("=====================DAO耗时" + (System.currentTimeMillis() - start) + "毫秒=======================");
    		System.out.println("转换成功！");
    	}
    	
    	//获取insert的sql
    	private String getInsert(){
    		
    		//新增sql中需要排除的字段
    		excludeFieldsInInsert.addAll(deleteMarkFields);
    		excludeFieldsInInsert.addAll(editMarkFields);
    		
    		StringBuilder sb1 = new StringBuilder("\n\t<insert id=\"insert\" parameterType=\""+ packDO +"\" useGeneratedKeys=\"true\" keyProperty=\"" + pk +"\">\n\t\tINSERT INTO ");
    		sb1.append(tableName);
    		sb1.append(" (\n");
    		StringBuilder sb2 = new StringBuilder("VALUES (\n");
    		Field[] fields = bean.getDeclaredFields();
    		for (Field field : fields) {
    			String fieldName = field.getName();
    			if (excludeFieldsInInsert.contains(fieldName)) {
    				continue;
    			}
    			sb1.append("\t\t\t\t");
    			sb1.append(fieldName);
    			sb1.append(",\n");
    			
    			if ("time_stamp".equals(fieldName)) {
    				sb2.append("\t\t\t\tcurrent_timestamp(3),\n");
    			}else {
    				sb2.append("\t\t\t\t#{");
    				sb2.append(fieldName);
    				sb2.append("},\n");
    			}
    		}
    		
    		String sql = sb1.substring(0, sb1.length()-2) + "\n\t\t)" + sb2.substring(0, sb2.length()-2) + " \n\t\t)\n\t</insert>\n";
    		
    		return sql;
    	}
    	
    	private String getDelete(){
    		
    		//删除时需要修改的字段
    		fieldsInDelete.addAll(deleteMarkFields);
    		
    		StringBuilder sb = new StringBuilder("\n\t<update id=\"delete\" parameterType=\"" + packDO + "\">\n\t\tUPDATE ");
    		sb.append(tableName);
    		sb.append("\n\t\tSET state=#{state},time_stamp=current_timestamp(3)");
    		
    		fieldsInDelete.removeAll(Arrays.asList("state", "time_stamp"));
    		Field[] fields = bean.getDeclaredFields();
    		for (Field field : fields) {
    			String fieldName = field.getName();
    			if (fieldsInDelete.contains(fieldName)) {
    				sb.append(",");
    				sb.append(fieldName);
    				sb.append("=#{");
    				sb.append(fieldName);
    				sb.append("}");
    			}
    		}
    		sb.append("\n");
    		sb.append("\t\tWHERE ");
    		sb.append(pk);
    		sb.append(" = #{");
    		sb.append(pk);
    		sb.append("}");
    		sb.append(" AND enterpriseid = #{enterpriseid} AND time_stamp = #{time_stamp}\n");
    		
    		sb.append("\t</update>\n");
    		return sb.toString();
    	}
    	
    	//编辑方法
    	private String getUpdate() {
    		
    		//编辑时需要排除的字段
    		excludeFieldsInEdit.addAll(deleteMarkFields);
    		excludeFieldsInEdit.addAll(bookinMarkFields);
    		StringBuilder sb = new StringBuilder("\n\t<update id=\"update\" parameterType=\""+ packDO +"\">\n\t\tUPDATE ");
    		sb.append(tableName);
    		sb.append("\n\t\tSET\n");
    		Field[] fields = bean.getDeclaredFields();
    		
    		for (Field field : fields) {
    			String fieldName = field.getName();
    			if (excludeFieldsInEdit.contains(fieldName)) {
    				continue;
    			}
    			
    			sb.append("\t\t\t");
    			sb.append(fieldName);
    			if ("time_stamp".equals(fieldName)) {
    				sb.append(" = current_timestamp(3),\n");
    			}else {
    				sb.append(" = #{");
    				sb.append(fieldName);
    				sb.append("},\n");
    			}
    		}
    		String whereStatement = "\n\t\tWHERE " + pk + "= #{" + pk + "} AND enterpriseid = #{enterpriseid} AND time_stamp = #{time_stamp}";
    		
    		return sb.substring(0, sb.length() - 2) + whereStatement + "\n\t</update>\n";
    	}
    	//分页查询方法
    	private String getPageList(){
    		StringBuilder sb = new StringBuilder("\n\t<select id=\"selectByExample\" parameterType=\"java.util.Map\" resultType=\"" + packVO + "\">\n");
    		sb.append("\t\tSELECT\n\t\t*\n\t\tFROM ");
    		sb.append(tableName);
    		sb.append("\n\n\t\tORDER BY bookindate desc\n\t\tLIMIT #{pageSize} OFFSET #{startNum}\n\t</select>\n");
    		
    		sb.append("\n\t<select id=\"countByExample\" parameterType=\"java.util.Map\" resultType=\"java.lang.Long\">\n");
    		sb.append("\t\tSELECT count(*) FROM ");
    		sb.append(tableName);
    		sb.append("\n\n\t</select>\n");
    		return sb.toString();
    	}
    	//根据主键查询
    	private String getGetById(){
    		StringBuilder sb = new StringBuilder("\n\t<select id=\"getById\" parameterType=\"" + packDO + "\" resultType=\"" + packVO + "\">\n");
    		sb.append("\t\tSELECT *\n\t\tFROM ");
    		sb.append(tableName);
    		sb.append("\n\t\tWHERE ");
    		sb.append(pk);
    		sb.append(" = #{");
    		sb.append(pk);
    		sb.append("}\n");
    		sb.append("\t\t<if test=\"enterpriseid != null\">\n\t\t\tAND enterpriseid = #{enterpriseid}\n\t\t</if>\n\t</select>\n");
    		return sb.toString();
    	}
    	//根据code计数
    	private String getCountByCode(){
    		StringBuilder sb = new StringBuilder("\n\t<select id=\"countByCode\" parameterType=\"java.util.Map\" resultType=\"java.lang.Long\">\n");
    		sb.append("\t\tSELECT count(*)\n\t\tFROM ");
    		sb.append(tableName);
    		sb.append("\n\t\tWHERE code = #{code} AND enterpriseid = #{enterpriseid}\n");
    		sb.append("\t\t<if test=\"" + pk + " != null\">\n");
    		sb.append("\t\t\tAND ");
    		sb.append(pk);
    		sb.append(" != #{");
    		sb.append(pk);
    		sb.append("}\n");
    		sb.append("\t\t</if>\n\t</select>\n");
    		return sb.toString();
    	}
    	
    	//生成dao接口
    	private String getDao(){
    		
    		String simpleNameForDO = packDO.substring(packDO.lastIndexOf(".") + 1);
    		String simpleNameForVO = packVO.substring(packVO.lastIndexOf(".") + 1);
    		String simpleNameForDao = packDao.substring(packDao.lastIndexOf(".") + 1);
    		
    		StringBuilder sb = new StringBuilder("package ");
    		sb.append(packDao.substring(0, packDao.lastIndexOf(".")));
    		sb.append(";\n\n");
    		
    		sb.append("import ");
    		sb.append(packDO + ";\n");
    		sb.append("import ");
    		sb.append(packVO + ";\n\n");
    		
    		sb.append("import java.util.List;\nimport java.util.Map;\n\n");
    		sb.append("/**\n\t* @Description:\n\t* @author:\n\t* @date:\n*/\n");
    		
    		sb.append("public interface " + simpleNameForDao + " {\n");
    		
    		sb.append("\t//根据数据条件查询列表\n");
    		sb.append("\tList<" + simpleNameForVO + "> selectByExample(Map<String, Object> map);\n");
    		sb.append("\t//根据条件查询记录条数\n");
    		sb.append("\tlong countByExample(Map<String, Object> map);\n");
    		sb.append("\t//新增\n");
    		sb.append("\tint insert(" + simpleNameForDO + " bean);\n");
    		sb.append("\t//删除\n");
    		sb.append("\tint delete(" + simpleNameForDO + " bean);\n");
    		sb.append("\t//编辑\n");
    		sb.append("\tint update(" + simpleNameForDO + " bean);\n");
    		sb.append("\t//根据id查询任务信息\n\t");
    		sb.append(simpleNameForVO);
    		sb.append(" getById(" + simpleNameForDO + " bean);\n");
    		sb.append("\t//判断编号是否重复\n");
    		sb.append("\tlong countByCode(Map<String, Object> map);\n");
    		sb.append("}");
    		return sb.toString();
    	}
    	
    	private void writerFile(String writeContent, String outFilePath,String fileName,String fileType) throws IOException{
    		 BufferedWriter writer = getWriter(outFilePath +fileName+ "."+fileType);  
    		 writer.write(writeContent);   
             writer.close();  
    	}
    	
    	/** 
         * 获取缓冲输出流 
         * @param filePath 路径 
         * @return BufferedWriter 
         * @throws IOException 
         */  
    	private BufferedWriter getWriter(String filePath) throws IOException {  
        	File file = new File(filePath);    
        	File fileParent = file.getParentFile();  
        	if(!fileParent.exists()){  
        	    fileParent.mkdirs();  
        	}  
        	file.createNewFile();  
            return new BufferedWriter(new FileWriter(file));
        }  
    }
    
    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {  
    	System.out.println(localPath);
        createBean(sqlFile, localPath + modelPackagePath.replace(".", "/") + "/");
        Thread.sleep(5000);
        Class clazz = Class.forName(modelPackagePath + "." + className);
        Bean2Sql bean2Sql = new Bean2Sql(clazz);
        bean2Sql.getMapperAndDao();
    }
    
}  