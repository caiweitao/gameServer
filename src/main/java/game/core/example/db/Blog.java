package game.core.example.db;

import game.core.db.mybatis.annotation.ID;
import game.core.db.mybatis.annotation.TempField;

/**
* @author caiweitao
* @Date 2019年10月10日
* @Description 
*/
public class Blog {

	@ID(autoIncrement=true)
	private int id;
	private String articleTitle;
	private String articleContent;
	@TempField
	private String temp;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	@Override
	public String toString() {
		return String.format("[id=%s,title=%s,content=%s]", id,articleTitle,articleContent);
	}
	
}
