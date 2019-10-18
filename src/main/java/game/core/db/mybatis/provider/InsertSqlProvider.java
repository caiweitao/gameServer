package game.core.db.mybatis.provider;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import game.core.db.mybatis.bean.Pram;

/**
* @author caiweitao
* @Date 2019年10月11日
* @Description 
*/
public class InsertSqlProvider extends BaseSqlProvider{
	
	public String insert (Object t) {//,@Param(value="tc")Class<?> tc
		return new SQL() {
	            {
	                INSERT_INTO(getTable(t));
	                List<Pram> pramList = getPramList(t);
	                for (Pram pram:pramList) {
	                	if (pram.getValue() != null) {
	                		VALUES(classToTable(pram.getFile()), "#{"+pram.getFile()+"}");//"'"+pram.getValue().toString()+"'"
	                	}
	                }
	            }
	        }.toString();
	}
	
	public String batchInsert (@Param(value="list")List<Object> list,@Param(value="tc")Class<?> tc) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ")
		.append(getTable(tc))
		.append("(");
		for (int i=0,len=list.size();i<len;i++) {
        	Object o = list.get(i);
        	List<Pram> pramList = getPramList(o);
        	
        	if (i == 0) {//组装列名
        		for (Pram pram:pramList) {
        			if (pram.getFile() != null) {
        				sb.append(classToTable(pram.getFile())).append(",");
        			}
        		}
        		sb.replace(sb.length()-1, sb.length(), ")");
        		sb.append(" VALUES ");
			}
        	
        	sb.append("(");
        	
        	for (Pram pram:pramList) {
        		sb.append("#{list["+i+"]."+pram.getFile()+"}").append(",");
    		}
    		sb.replace(sb.length()-1, sb.length(), "),");
        	
        }
		return sb.substring(0, sb.length() - 1);
	}
	
//	private static Class<?> getClass (Object o) {
//		ParameterizedType pt = (ParameterizedType) o.getClass().getGenericSuperclass();
//		// 获取第一个类型参数的真实类型
//		if (pt.getActualTypeArguments().length > 0) {
//			return (Class<?>) pt.getActualTypeArguments()[0];
//		} else {
//			return null;
//		}
//	}
	
}
