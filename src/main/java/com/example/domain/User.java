package com.example.domain;

public class User {
	private String name;
	private String email;
	private String password;
	private Integer storeId;
	private Integer authority;
	private String zipcode;
	private String address;
	private String telephone;
	private boolean enabled = true;

	public User() {
	}

	public User(String name, String email, String password, Integer storeId, Integer authority, String zipcode,
			String address, String telephone, boolean enabled) {
		super();
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

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", password=" + password + ", storeId=" + storeId
				+ ", authority=" + authority + ", zipcode=" + zipcode + ", address=" + address + ", telephone="
				+ telephone + ", enabled=" + enabled + "]";
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

	public void setEnablate(boolean enabled) {
		this.enabled = enabled;
	}
}
