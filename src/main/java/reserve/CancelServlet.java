

import java.io.IOException;
import java.sql.SQLException;
import java.time.DateTimeException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MeetingRoom;
import bean.ReservationBean;

/**
 * 
 * 会議室管理システムのモデル
 * @version 1.0.0
 * @author 板津　征隆
 */
@WebServlet("/Cancel")
public class CancelServlet extends SessionManagement {

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
		
		String nextPage = null;
		String errorReason = null;
		try {
			ReservationBean rb = (ReservationBean) session.getAttribute("reservation");
			if (rb == null) {
				throw new NullPointerException();
			}
			meetingRoom.cancel(rb);
			nextPage = "/canceled.jsp";
		} catch (NullPointerException e) {
			errorReason = "予約されていません";
			session.setAttribute("errorReason", errorReason);
			nextPage = "/cancelError.jsp";
		} catch (SQLException | NamingException e) {
			errorReason = "データベースでエラーが発生しました";
			session.setAttribute("errorReason", errorReason);
			nextPage = "/cancelError.jsp";
		} catch (NoSuchFieldException e) {
			errorReason = "キャンセルに失敗しました";
			session.setAttribute("errorReason", errorReason);
			nextPage = "/cancelError.jsp";
		} catch (DateTimeException e) {
			errorReason = "時刻が過ぎているためキャンセルできません";
			session.setAttribute("errorReason", errorReason);
			nextPage = "/cancelError.jsp";
		} catch (Exception e) {
			errorReason = "システムエラー、管理者に確認してください";
			session.setAttribute("errorReason", errorReason);
			nextPage = "/cancelError.jsp";
		}
		request.getRequestDispatcher(nextPage).forward(request, response);
	}

	/**
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextpage = request.getContextPath() + "/login.jsp";
		response.sendRedirect(nextpage);
	}
}