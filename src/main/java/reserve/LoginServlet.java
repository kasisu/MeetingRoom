

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MeetingRoom;

/**
 * 
 * 会議室管理システムのモデル
 * @version 1.0.0
 * @author 板津　征隆
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	/**直列化用バージョン番号
	 * @see
	 * */
	private static final long serialVersionUID = 1L;

	//メソッド一覧
	/**
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//文字コード受信
		request.setCharacterEncoding("UTF-8");

		//入力パラメーター
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String errorReason = null;
		if (userId.length() > 7 && (userPw.length() > 10 || userPw.length() < 6)) {
			errorReason = "IDは7文字以下、パスワードは6文字以上10文字以下で入力してください";
		} else if (userId.length() > 7) {
			errorReason = "IDは7文字以下で入力してください";
		} else if ((userPw.length() > 10 || userPw.length() < 6)) {
			errorReason = "パスワードは6文字以上10文字以下で入力してください";
		} else {
			errorReason = null;
		}
		//セッションの取得
		//インスタンス生成
		HttpSession session = request.getSession();
		MeetingRoom mr = (MeetingRoom) session.getAttribute("meetingRoom");
		if (mr == null) {
			mr = new MeetingRoom();
			session.setMaxInactiveInterval(3600); // 3600秒(1時間)
		}
		//セッション属性の設定
		session.setAttribute("userId", userId
				.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&#39;"));
		session.setAttribute("userPw", userPw
				.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&#39;"));

		String nextpage = null;

		if (mr.login(userId, userPw)) {//一致するとき
			session.setAttribute("meetingRoom", mr);
			session.setAttribute("userId", mr.getUser().getId());
			nextpage = "/menu.jsp";
		} else {//一致しないとき
			if(errorReason ==null) {
				errorReason = "IDとパスワードを確認してください";
			}
			session.setAttribute("errorReason", errorReason);
			//			nextpage = request.getContextPath() + "/login.jsp";
			//			response.sendRedirect(nextpage);
			//			return;
			nextpage = "/login.jsp";
		}
		request.getRequestDispatcher(nextpage).forward(request, response);
	}

	//Getで送ってきたらログイン画面にリダイレクト
	/**直接アクセスは、ログインページにリダイレクトする*/
	/**
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextpage = request.getContextPath() + "/login.jsp";
		response.sendRedirect(nextpage);
		return; //リダイレクト
	}
}