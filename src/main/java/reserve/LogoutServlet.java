

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 
 * 会議室管理システムのモデル
 * @version 1.0.0
 * @author 板津　征隆
 */
@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
	/**直列化用バージョン番号
	 * @see
	 * */
	private static final long serialVersionUID = 1L;

	/**
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
        if (session != null) {
			session.removeAttribute("meetingRoom");
			session.removeAttribute("reservation");
			session.removeAttribute("room");
            session.invalidate();
        }
        String nextpage = request.getContextPath()+"/login.jsp";
		response.sendRedirect(nextpage);
		return;
	}

	/**直接アクセスは、ログインページにリダイレクトする*/
	/**
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String nextpage = request.getContextPath()+"/login.jsp";
		response.sendRedirect(nextpage);
		return;
	}
}

