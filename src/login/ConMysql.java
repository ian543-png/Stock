package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConMysql{
	private Connection conn = null;
	private Statement stat = null;
	private ResultSet res = null;
	private String sDriver="com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/TestDB?serverTimezone=CST";
	private String user = "root"; //資料庫使用者
	private String password = "12345678"; //資料庫密碼
	private String sql = "";

	//連接資料庫
	public void conDb() {
		/*載入驅動*/
		try {
			Class.forName(sDriver);
		} catch (ClassNotFoundException e) {
			System.out.print("驅動器載入失敗");
		}
		
		/*進行連線*/
		try {
			conn = DriverManager.getConnection(url, user, password);
			stat = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print("資料庫連接失敗");
		}
	}
	//根據username取出欄位對應的data
	public String getUserData(String column,String username)
	{
		/*執行指令*/
		sql = "SELECT * FROM user WHERE User_Account ='"+ username +"';";
		try {
			res = stat.executeQuery(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
		/*取出資料*/
		String data = "";
		try {
			while(res.next())
			{
				data = res.getString(column);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	//根據id取出欄位對應的data
	public String getStockData(String column,String id)
	{
		/*執行指令*/
		sql = "SELECT * FROM stock_lastest WHERE stock_id ='"+ id +"';";
		try {
			res = stat.executeQuery(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
		/*取出資料*/
		String data = "";
		try {
			while(res.next())
			{
				data = res.getString(column);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	/*取得最新的文章編號*/
	public int getLastestArticleID()
	{
		/*執行指令*/
		sql = "select arti_id from article order by arti_id desc LIMIT 1;";
		try {
			res = stat.executeQuery(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
		/*取出資料*/
		int data = 0;
		try {
			while(res.next())
			{
				data = res.getInt("arti_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	/*取得最舊的文章編號*/
	public int getOldestArticleID()
	{
		/*執行指令*/
		sql = "select arti_id from article order by arti_id asc LIMIT 1;";
		try {
			res = stat.executeQuery(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
		/*取出資料*/
		int data = 0;
		try {
			while(res.next())
			{
				data = res.getInt("arti_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	/*取得文章總數*/
	public int getArticleCount()
	{
		/*執行指令*/
		sql = "select count(*) from article;";
		try {
			res = stat.executeQuery(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
		/*取出資料*/
		int data = 0;
		try {
			while(res.next())
			{
				data = res.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	/*根據文章編號取得資料*/
	public String getArticleData(String column,int id) {
		/*執行指令*/
		sql = "select * from article where arti_id="+id+";";
		try {
			res = stat.executeQuery(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
		/*取出資料*/
		String data = "";
		try {
			while(res.next())
			{
				data = res.getString(column);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	/*根據文章編號取得使用者資料*/
	public String getUserDataWithAricle(String column,int id) {
		/*執行指令*/
		sql =   "SELECT * from article " + 
				"left join user " + 
				"on article.user_id=user.User_id " + 
				"where article.arti_id='"+id+"';";
		try {
			res = stat.executeQuery(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
		/*取出資料*/
		String data = "";
		try {
			while(res.next())
			{
				data = res.getString(column);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	/*新增文章近資料庫*/
	public void addArticle(String title,String content,int id) {
		/*執行指令*/
		sql =  "insert into article (arti_title,arti_txt,user_id) " + 
				"values('"+title+"','"+content+"','"+id+"');";
		try {
			stat.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
		
	}
}