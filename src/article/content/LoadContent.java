package article.content;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.ConMysql;

@WebServlet("/LoadContent")
public class LoadContent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		HttpSession session=request.getSession();
		ConMysql con = new ConMysql();
		con.conDb();
		int arti_id = Integer.parseInt(request.getParameter("arti_id"));
		String path = "links/post_content.jsp";
		
		String title = con.getArticleData("arti_title", arti_id);
		String content = con.getArticleData("arti_txt", arti_id);
		String user = con.getUserDataWithAricle("User_Name", arti_id);
		path += "?arti_title="+title+"&arti_txt="+content+"&User_Name="+user;
		response.sendRedirect(path);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}