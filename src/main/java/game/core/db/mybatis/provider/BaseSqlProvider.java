package game.core.db.mybatis.provider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import game.core.db.mybatis.annotation.ID;
import game.core.db.mybatis.annotation.TempField;
import game.core.db.mybatis.bean.Pram;

/**
 * @author caiweitao
 * @Date 2019年10月11日
 * @Description 
 */
public class BaseSqlProvider {

	private String table;

	public String getTable (Class<?> beanClass) {
		if (beanClass == null) {
			return null;
		}
		if (this.table == null) {
			this.table = classToTable(beanClass.getSimpleName());
		}
		return this.table;
	}

	public String getTable (Object o) {
		if (o == null) {
			return null;
		}
		
		if (this.table == null) {
			this.table = classToTable(o.getClass().getSimpleName());
		}
		return this.table;
	}

	/**
	 * 类名转表名
	 * @param className
	 * @return
	 */
	public String classToTable (String className) {
		String table = toTableString(className);
		return table;
	}

	/**
	 * 获得对象的域（name/value）
	 * @param po
	 * @return
	 */
	public List<Pram> getPramList(Object po){
		List<Pram> list = new ArrayList<>();
		Class<?> thisClass = po.getClass();
		Field[] fields = thisClass.getDeclaredFields();
		try {
			for(Field f : fields){
				ID id = f.getAnnotation(ID.class);
				//(不是主键 || 主键不是自增) && 不是临时字段
				if ((id == null || !id.autoIncrement()) && !f.isAnnotationPresent(TempField.class)) {
					Pram pram = fieldToPram(f, po);
					list.add(pram);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 得到主键（用@ID主键的域-优先，没有@ID默认把名为id的当主键，都没有返回null）
	 * @param po
	 * @return
	 */
	public Pram getPrimaryKey (Object po) {
		if (po == null) {
			return null;
		}
		Class<?> thisClass = po.getClass();
		Field[] fields = thisClass.getDeclaredFields();
		try {
			Field idField = null;//名为 id 的域
			for(Field f : fields){
				ID id = f.getAnnotation(ID.class);
				if (id != null) {
					return fieldToPram(f, po);
				}
				if (f.getName().equalsIgnoreCase("ID")) {
					idField = f;
				}
			}
			if (idField != null) {//没有给主键加注解，默认把名为 id 的字段当主键
				return fieldToPram(idField, po);
			}
		}catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 得到主键名
	 * @param thisClass
	 * @return
	 */
	public String getPrimaryKeyName (Class<?> thisClass) {
		Field[] fields = thisClass.getDeclaredFields();
		try {
			Field idField = null;//名为 id 的域
			for(Field f : fields){
				ID id = f.getAnnotation(ID.class);
				if (id != null) {
					return f.getName();
				}
				if (f.getName().equalsIgnoreCase("ID")) {
					idField = f;
				}
			}
			if (idField != null) {//没有给主键加注解，默认把名为 id 的字段当主键
				return idField.getName();
			}
		}catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析得到属性名和属性值
	 * @param field
	 * @param po
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Pram fieldToPram (Field field,Object po) throws IllegalArgumentException, IllegalAccessException {
		if (field == null) {
			return null;
		}
		field.setAccessible(true);
		String fName = field.getName();
		Object value = field.get(po);
		return new Pram(fName,value);
	}

	/**
	 * 将驼峰标识转换为下划线
	 * @param text
	 * @return
	 */
	public static String toTableString(String text){
		if (text==null||"".equals(text.trim())){  
			return "";  
		} 

		StringBuilder sb=new StringBuilder(text);
		int temp=0;//定位
		for(int i=0;i<text.length();i++){
			if(i != 0 && Character.isUpperCase(text.charAt(i)) && !Character.isDigit(text.charAt(i))){
				sb.insert(i+temp, "_");
				temp+=1;
			}
		}
		return sb.toString().toLowerCase(); 
	}

	/**
	 * 下划线转驼峰
	 * @param para
	 * @return
	 */
	public static String UnderlineToHump(String para){
		if (para==null||"".equals(para.trim())){  
			return "";  
		} 
		if (!para.contains("_")) {
			return para;
		}
		StringBuilder result=new StringBuilder();
		String a[]=para.split("_");
		for(String s:a){
			if(result.length()==0){
				result.append(s.toLowerCase());
			}else{
				result.append(s.substring(0, 1).toUpperCase());
				result.append(s.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}
}
