

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet("/ChangeDate")
public class ChangeDateServlet extends SessionManagement {

	/**直列化用バージョン番号
	 * @see
	 * */
	private static final long serialVersionUID = 1L;

	/**直接アクセスは、ログインページにリダイレクトする*/
	/**
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 直接アクセスされた場合の画面遷移
		String nextpage = request.getContextPath() + "/login.jsp";
		response.sendRedirect(nextpage);
		return;
	}

	/**
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 利用日の形式を変更する
		request.setCharacterEncoding("UTF-8");

		//セッションの有無の確認
		//		boolean sessionAvailability = false;
		HttpSession session = request.getSession();

		//インスタンス生成
		MeetingRoom meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
		// 親クラスにてIDチェックしログイン情報を管理
		if (meetingRoom == null || !(loginManagement(request))) {
			String nextpage = request.getContextPath() + "/login.jsp";
			response.sendRedirect(nextpage);
			return;
		}

		String date = request.getParameter("date");
		String page = request.getParameter("page");
		meetingRoom.setDate(date);
		session.setAttribute("meetingRoom", meetingRoom);
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

}
