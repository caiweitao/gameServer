package game.core.db.mybatis;


import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import game.core.db.mybatis.provider.DeleteSqlProvider;
import game.core.db.mybatis.provider.InsertSqlProvider;
import game.core.db.mybatis.provider.SelectSqlProvider;
import game.core.db.mybatis.provider.UpdateSqlProvider;

/**
* @author caiweitao
* @Date 2019年10月11日
* @Description 公共 Mapper
*/
public interface CommonMapper<K,T> {
	
	/**
	 * 根据主键查询
	 * @param id 主键
	 * @param tc 实体类类型
	 * @return
	 */
	@SelectProvider(type=SelectSqlProvider.class,method="selectById")
	T get (@Param(value="id")K id,@Param(value="tc")Class<?> tc);
	
	/**
	 * 查询总数量
	 * @param tc
	 * @return
	 */
	@SelectProvider(type=SelectSqlProvider.class,method="count")
	long count (@Param(value="tc")Class<?> tc);
	
//	@SelectProvider(type=SelectSqlProvider.class,method="selectListByKey")
//	List<T> getListByKey (@Param(value="key")Pram key,@Param(value="tc")Class<?> tc);

	/**
	 * 添加
	 * @param t
	 * @return 添加数量
	 */
	@Options(useGeneratedKeys = true,keyProperty="id")
	@InsertProvider(type=InsertSqlProvider.class,method="insert")
	int insert (T t);
	
	/**
	 * 批量插入
	 * @param list
	 * @param tc
	 * @return
	 */
	@Options(useGeneratedKeys = true,keyProperty="list.id")
	@InsertProvider(type=InsertSqlProvider.class,method="batchInsert")
	int batchInsert (@Param(value="list")List<T> list,@Param(value="tc")Class<?> tc);
	
	/**
	 * 更新
	 * @param t
	 * @return 更新数量
	 */
	@UpdateProvider(type=UpdateSqlProvider.class,method="update")
	int update (T t);
	
	/**
	 * 删除
	 * @param id 主键
	 * @param tc 实体类类型
	 * @return
	 */
	@DeleteProvider(type=DeleteSqlProvider.class,method="delete")
	int delete(@Param(value="id")K id,@Param(value="tc")Class<?> tc);
	
}
