package bean;

import java.io.Serializable;

/**
 * 
 * 会議室予約情報
 * @version 1.0.0
 * @author 板津　征隆
 */
public class UserBean implements Serializable {
	/**利用者ID*/
	private String id;
	/**パスワード*/
	private String password;
	/**氏名*/
	private String name;
	/**住所*/
	private String address;
	/**直列化用バージョン番号
	 * @see
	 * */
	private static final long serialVersionUID = 1L;

	
	/**直列化復元時に使用します。*/
	public UserBean() {
	}

	/**
	 * 利用者情報を基に初期化します。
	 * @param id - String 利用者ID
	 * @param password - String パスワード
	 * @param name - String 氏名
	 * @param address - String 住所
	 */
	public UserBean(String id, String password, String name, String address) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.address = address;
	}

	/**
	 * 利用者IDを返します。
	 * @return String 利用者ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * パスワードを返します。
	 * @return String パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 氏名を返します。
	 * @return String 氏名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 住所を返します。
	 * @return String 住所
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * このオブジェクトの文字列表現を返します。
	 * @return String 利用者の文字列表現
	 */
	@Override
	public String toString() {
		return "UserBean [id=" + id + ", password=" + password + ", name=" + name + ", address=" + address + "]";
	}

}
