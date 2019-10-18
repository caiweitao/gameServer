package game.core.db.mybatis.provider;

import java.util.List;

import org.apache.ibatis.jdbc.SQL;

import game.core.db.mybatis.bean.Pram;

/**
* @author caiweitao
* @Date 2019年10月11日
* @Description 
*/
public class UpdateSqlProvider extends BaseSqlProvider{
	
	public String update (Object t) {
		return new SQL() {{
			try {
				UPDATE(getTable(t));
				List<Pram> pramList = getPramList(t);
				Pram primaryKey = getPrimaryKey(t);
				if (primaryKey == null) {
					throw new Exception("没有主键，请用注解@ID标记主键，或者将主键名设为id");
				}
				for (Pram pram:pramList) {
					if (pram.getValue() != null) {
						SET(classToTable(pram.getFile())+" = #{"+pram.getFile()+"}");
						WHERE(classToTable(primaryKey.getFile())+" = #{"+primaryKey.getFile()+"}");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}}.toString();
	}
	
}
