package com.example.domain;

/**
 * ユーザードメイン
 * @author yoshida_yuuta
 *
 */
public class User {
	/**id*/
	private Integer id;
	/**名前*/
	private String name;
	/**メールアドレス*/
	private String email;
	/**パスワード*/
	private String password;
	/**店舗Id*/
	private Integer storeId;
	/**権限*/
	private Integer authority;
	/**郵便番号*/
	private String zipcode;
	/**住所*/
	private String address;
	/**電話番号*/
	private String telephone;
	/**認証情報*/
	private boolean enabled = true;

	public User() {
	}

	public User(Integer id, String name, String email, String password, Integer storeId, Integer authority,
			String zipcode, String address, String telephone, boolean enabled) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.storeId = storeId;
		this.authority = authority;
		this.zipcode = zipcode;
		this.address = address;
		this.telephone = telephone;
		this.enabled = enabled;
	}

	public User(User user) {
	    this.id = user.getId();
	    this.name = user.getName();
	    this.email = user.getEmail();
	    this.password = user.getPassword();
	    this.storeId = user.getStoreId();
	    this.authority = user.getAuthority();
	    this.zipcode = user.getZipcode();
	    this.address = user.getAddress();
	    this.telephone = user.getTelephone();
	    this.enabled = user.isEnabled();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", storeId="
				+ storeId + ", authority=" + authority + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + ", enabled=" + enabled + "]";
	}

}
