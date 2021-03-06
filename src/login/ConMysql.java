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
	/*根據User_id取得自選股總數*/
	public int getSelfStockCount(int User_id)
	{
		/*執行指令*/
		sql =   "SELECT count(*) from stock_self " + 
				"left join stock_lastest " + 
				"on stock_self.stock_id=stock_lastest.stock_id " + 
				"where stock_self.user_id='"+User_id+"';";
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
	/*根據會員編號取得自選股票資料*/
	public String[][] getSelfStockWtihUser(int SCount,int User_id) {
		/*執行指令*/
		sql =   "SELECT * from stock_self " + 
				"left join stock_lastest " + 
				"on stock_self.stock_id=stock_lastest.stock_id " + 
				"where stock_self.user_id='"+User_id+"';";
		try {
			res = stat.executeQuery(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
		/*取出資料*/
		String[][] result = new String[SCount][10];
		int count=0;
		try {
			while(res.next())
			{
				result[count][0] = res.getString("stock_id");
				result[count][1] = res.getString("stock_name");
				result[count][2] = res.getString("stock_trade");
				result[count][3] = res.getString("stock_trunover");
				result[count][4] = res.getString("stock_open");
				result[count][5] = res.getString("stock_max");
				result[count][6] = res.getString("stock_min");
				result[count][7] = res.getString("stock_close");
				result[count][8] = res.getString("stock_diff");
				result[count][9] = res.getString("stock_transaction");
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/*新增自選股進資料庫*/
	public void addSelfStock(String stock_id,String User_id) {
		/*執行指令*/
		sql =  "insert into stock_self values('"+stock_id+"','"+User_id+"');";
		try {
			stat.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
	}
	/*刪除自選股*/
	public void deleteSelfStock(String stock_id,String User_id) {
		/*執行指令*/
		sql = "DELETE from stock_self Where stock_id='"+stock_id+"' AND user_id='"+User_id+"';";
		try {
			stat.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
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
	/*更新文章近資料庫*/
	public void updateArticle(String title,String content,int id) {
		/*執行指令*/
		sql =   "update article set arti_title='"+title
				+ "',arti_txt='"+content+"'"
				+ " where arti_id='"+id+"';";
		try {
			stat.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
	}
	
	/*更新使用者資料*/
	public void updateUser(String name,String psd,String mail,int id) {
		/*執行指令*/
		sql =   "update user " + 
				"set User_Name='"+name+"',User_Password='"+psd+"',User_Email='"+mail+"'" + 
				"where User_id="+id+";";
		try {
			stat.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.print("sql執行失敗");
		}
	}
	/*新增使用者資料進資料庫*/
	public void insert_signup(String account, String upassword, String name, String uGender, String email,
			String birthday) {
		try {
			stat.execute(
					"insert into user(User_Account,User_password,User_name,User_Gender,User_Email,User_Birthday) values('"
							+ account + "','" + upassword + "','" + name + "','" + uGender + "','" + email + "','"
							+ birthday + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("sucess");
		}
	}
	/*新增文章觀看人數進資料庫*/
	public void updateViewNum(int viewNum, int arti_id) {
		try {
			stat.execute("update article set arti_viewNum = '"+viewNum+"' where arti_id="+arti_id+";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.print("sucess");
		}
	}
}