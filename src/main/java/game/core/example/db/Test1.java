package game.core.example.db;


import java.util.ArrayList;
import java.util.List;

import game.core.db.mybatis.MybatisConfig;
import game.core.example.db.dao.BlogDao;

/**
* @author caiweitao
* @Date 2019年10月10日
* @Description 
*/
public class Test1 {

	public static void main(String[] args) {
		MybatisConfig.init("config/mybatis-config.xml");
//		SqlSession sqlSession = StarterDB.getSqlSession();
//		BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
//		BlogMapper mapper = StarterDB.getMapper(BlogMapper.class);
//		Blog blog = mapper.get(2,Blog.class);
//		System.out.println(blog.toString());
//		sqlSession.close();
		
		BlogDao dao = new BlogDao();
//		Blog blog = dao.get(28);
//		blog.setContent("bbb");
//		dao.update(blog);
//		System.out.println(blog.toString());
//		System.out.println(dao.count());
//		dao.delete(28);
//		blog.setTitle("oooooooo");
//		blog.setContent("fffffffffff");
//		mapper.update(blog);
//		System.out.println(blog.toString());
//		List<Blog> list	 = mapper.selectListById(2,Blog.class);
//		System.out.println(blog.toString());
		
//		System.out.println(mapper.delete(1, Blog.class));
		List<Blog> list = new ArrayList<>();
		Blog b = new Blog();
		b.setArticleTitle("aa");
		b.setArticleContent("aaa");
		b.setTemp("oo");
//		dao.add(b);
//		Blog b2 = new Blog();
//		b2.setTitle("jjj");
//		b2.setContent("yyyy");
		
		list.add(b);
//		list.add(b2);
		dao.batchAdd(list);
		
//		int insert = mapper.insert(b);
//		System.out.println(b.toString()+insert);
//		Blog selectByPrimaryKey = mapper.selectByPrimaryKey(2);
//		System.out.println(selectByPrimaryKey.toString());
	}
}
