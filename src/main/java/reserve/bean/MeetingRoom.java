package bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.ReservationDao;
import dao.RoomDao;
import dao.UserDao;

/**
 * 
 * 会議室管理システムのモデル
 * @version 1.0.0
 * @author 板津　征隆
 */
public class MeetingRoom implements Serializable {
	/**利用日*/
	private String date;
	/**利用時間(分)60分とする
	 * @see
	 * */
	private final int INTERVAL = 3600;
	/**利用時間帯(開始時刻) ("09:00", "10:00", "11:00", "12:00","13:00", "14:00", "15:00", "16:00")*/
	private String[] PERIOD = { "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00" };
	/**会議室*/
	private RoomBean[] rooms;
	/**直列化用バージョン番号
	 * @see
	 * */
	private long serialVersionUID = 1L;
	/**利用者*/
	private UserBean user;

	/**生成 会議室予約システムを初期化します。*/
	public MeetingRoom() {
		try {
			this.rooms = RoomDao.findAll​();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**roomIdの会議室が配列に格納されている添字を返します。*/
	private int roomIndex(String roomId) throws IndexOutOfBoundsException {
		/**
		 * @param roomId - String 会議室ID
		 * @return int 配列の添字
		 * @throws IndexOutOfBoundsException - 会議室が存在しない場合
		 */
		for (int i = 0; i < this.rooms.length; i++) {
			if (this.rooms[i].getId().equals(roomId)) {
				return i;
			}
		}
		throw new IndexOutOfBoundsException();
	}

	/**利用開始時刻に対応する利用時間帯の添え字を計算します。*/
	private int startPeriod(String start) throws IndexOutOfBoundsException {
		/**
		 * @param start - String 利用開始時刻
		 * @return int 時間帯番号
		 * @throws IndexOutOfBoundsException - 利用時間帯の範囲外
		 */
		for (int i = 0; i < this.PERIOD.length; i++) {
			if (this.PERIOD[i].equals(start)) {
				return i;
			}
		}
		throw new IndexOutOfBoundsException();
	}

	/**利用時間帯の配列を返す。*/
	public String[] getPeriod() {
		/**
		 * @return String[] 開始時刻の配列
		 */
		return PERIOD;
	}

	/**会議室予約システムで利用できるすべての会議室を返します。*/
	public RoomBean[] getRooms() {
		/**
		 * @return RoomBean[] 会議室の配列
		 */
		return this.rooms;
	}

	/**利用会議室取得<br>
	会議室IDがroomIdの会議室を返します。*/
	public RoomBean getRoom(String roomId) throws Exception {
		/**
		 * @param roomId - String 会議室ID
		 * @return RoomBean 会議室(見つからない場合は、nullを返却)
		 * @throws Exception
		 */
		RoomBean gr = null;
		if(this.rooms[roomIndex(roomId)] != null) {
			gr = this.rooms[roomIndex(roomId)];
		}
		return gr;
	}

	/**会議室予約システムにログインしている利用者を返します。*/
	public UserBean getUser() {
		/**
		 * @return UserBean 利用者
		 */
		return this.user;
	}

	/**会議室予約システムの利用日を返します。*/
	public String getDate() {
		/**
		 * @return String 利用日
		 */
		//		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		//		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy/MM/dd");
		//		Date date = null;
		//		try {
		//			date = sdFormat.parse(this.date);
		//		} catch (ParseException e) {
		//			// TODO 自動生成された catch ブロック
		//			e.printStackTrace();
		//		}
		//		return dateformatter.format(date);
		return this.date;
	}

	/**利用日更新<br>
	会議室予約システムの利用日を設定します。*/
	public void setDate(String date) {
		/**
		 * @param date - String 利用日
		 */
		this.date = date;
	}

	/**認証<br>
	会議室予約システムにログインします。*/
	public boolean login(String id, String password) {
		/**
		 * @param id - String 利用者ID
		 * @param password - String パスワード
		 * @return ログインできた場合はtrue，それ以外の場合false
		 */
		UserBean user = null;
		try {
			// Daoを用いてユーザーの有無の確認
			user = UserDao.certificate​(id, password);
			if (user == null) {
				return false;
			} else {
				// ユーザーが存在する場合ユーザー情報の登録
				this.user = user;
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	/**会議室予約システムの利用日における予約状況を返します。*/
	public ReservationBean[][] getReservations() {
		/**
		 * @return ReservationBean[][] 会議室，時間帯ごとの予約状況
		 */
		ReservationBean[][] rbArray = new ReservationBean[rooms.length][PERIOD.length];
		try {
			// Daoを使いデータベースから予約情報を取得
			List<ReservationBean> rbList = ReservationDao.findByDate(date);
			// 日付時間のフォーマットを設定
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat hmformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (int i = 0; i < rbArray.length; i++) {
				for (int j = 0; j < rbArray[i].length; j++) {
					for (ReservationBean rb : rbList) {
						// フォーマットを元にデータを整理
						Date fdate = sdFormat.parse(date + " " + rb.getStart());
						Date masdate = hmformatter.parse(date + " " + PERIOD[j]);
						//						if (rb.getUserId().equals(user.getId())) {
						// ルームIDと日付を比較し、該当箇所に格納
						if (rooms[i].getId().equals(rb.getRoomId()) && (masdate.equals(fdate))) {
							rbArray[i][j] = rb;
							break;
						} else {
							rbArray[i][j] = null;
							continue;
						}
						//						} else {
						//							rbArray[i][j] = null;
						//							continue;
						//						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return rbArray;
	}

	/**予約生成<br>
	予約日で会議室と時間帯を指定した会議室予約情報を生成します。
	 * @throws ParseException */
	public ReservationBean createResevation(String roomId, String start) throws ParseException {
		/**
		 * @param roomId - String 会議室ID
		 * @paramt start - String 利用開始時刻(HH:mm形式で受け取る事を想定)
		 * @return ReservationBean 会議室予約情報
		 * @throws ParseException
		 */
		// 日付時間のフォーマットを設定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat hmformatter = new SimpleDateFormat("HH:mm");
		// 初期値設定
		ReservationBean rb = null;
		java.util.Date date = null;
		try {
			// インスタンス生成
			Calendar calendar = Calendar.getInstance();
			date = sdf.parse("1970-01-01 " + PERIOD[startPeriod(start)]);
			calendar.setTime(date);
			// 日付を1時間ずらす
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			date = calendar.getTime();
			// ReservationBean生成
			rb = new ReservationBean(roomId, this.date, PERIOD[startPeriod(start)], hmformatter.format(date),
					this.user.getId());
		} catch (IndexOutOfBoundsException | ParseException e) {
			// TODO 自動生成された catch ブロック
			throw e;
		}
		return rb;
	}

	/**予約登録<br>
	会議室予約情報で会議室Daoを利用し、予約します。*/
	public void reserve(ReservationBean reservation) throws Exception {
		/**
		 * @param reservation - ReservationBean 会議室予約情報
		 * @throws - 予約ができない場合に次のメッセージの例外を投げます。<br>
		 * 予約済みの場合："既に予約されています"<br>
		 * 現在時刻が予約時間を過ぎている場合："時刻が過ぎているため予約できません"
		 */
		// インスタンス生成、現在日付取得
		Calendar cal = Calendar.getInstance();
		// 日付時間のフォーマットを設定
		//		SimpleDateFormat sdfhm = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		java.util.Date date = sdf.parse(reservation.getDate() + " " + reservation.getStart());
		// 日付を1時間ずらす
		cal.add(Calendar.HOUR_OF_DAY, -1);
		// 現在日付と比較
		if (date.before(cal.getTime())) {
			throw new DateTimeException("時刻が過ぎているため予約できません");
		} else {
			try {
				// 予約登録
				if (ReservationDao.insert(reservation) <= 0) {
					throw new NoSuchFieldException();
				}
			} catch (Exception e) {
				throw e;
				//				throw new Exception("予約が重複しているため登録に失敗しました");
			}
		}
	}

	/**予約キャンセル<br>
	会議室予約情報で会議室をキャンセルします。*/
	public void cancel(ReservationBean reservation) throws Exception {
		/**
		 * @param reservation - ReservationBean 会議室予約情報
		 * @throws - キャンセルができない場合に次のメッセージの例外を投げます。 キャンセル済みの場合："既にキャンセルされています" 現在時刻が予約時間を過ぎている場合："時刻が過ぎているためキャンセルできません"
		 */
		// インスタンス生成
		Calendar cal = Calendar.getInstance();
		// 日付時間のフォーマットを設定
		//		SimpleDateFormat sdfhm = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		java.util.Date date = sdf.parse(reservation.getDate() + " " + reservation.getStart());
		// 現在日付と比較
		if (date.before(cal.getTime())) {
			throw new DateTimeException("時間が過ぎているためキャンセルできません");
		} else {
			try {
				// キャンセル実行
				if (!(ReservationDao.delete(reservation))) {
					throw new NoSuchFieldException();
				}
			} catch (Exception e) {
				throw e;
			}
		}
	}

	/**このオブジェクトの文字列表現を返します。
	 * デバッグ用*/
	@Override
	public String toString() {
		/**
		 * @return String 会議室予約システムの文字列表現
		 */
		return "MeetingRoom [date=" + date + ", INTERVAL=" + INTERVAL + ", PERIOD=" + Arrays.toString(PERIOD)
				+ ", rooms=" + Arrays.toString(rooms) + ", serialVersionUID=" + serialVersionUID + ", user=" + user
				+ "]";
	}

}
