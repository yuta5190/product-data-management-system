package com.example.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class InsertUserForm {
/**名前*/
	@NotBlank(message="Please enter your name")
	private String name;
	/** メールアドレス */
	@NotBlank(message="Please enter your Email")
	@Email(message="Invalid email format")
	private String email;
	/** 郵便番号 */
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}$",message="Please enter your zipcode in the format of XXX-XXXX.")
	private String zipcode;
	/** 住所 */
	@NotBlank(message="Please enter your address")
	private String address;
	/**  電話番号 */
	@Pattern(regexp="\\d{3,4}-\\d{4}-\\d{4}$",message="Please enter your telephone number in the format of XXX-XXXX-XXXX or XXXX-XXXX-XXXX")
	private String telephone;
	/** パスワード */
	@Size(min=8,max=16,message="Please set your password between 8 and 16 characters.")
	private String password;
	/** 確認パスワード */
	@NotBlank(message="Please enter the confirmation password.")
	private String confirmationPassword;
	@NotNull(message="Please specify administrative privileges.")
	private Integer authority;
	public InsertUserForm() {}
	
	public InsertUserForm(@NotBlank(message = "Please enter your name") String name,
			@NotBlank(message = "Please enter your Email") @Email(message = "Invalid email format") String email,
			@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "Please enter your zipcode in the format of XXX-XXXX.") String zipcode,
			@NotBlank(message = "Please enter your address") String address,
			@Pattern(regexp = "\\d{3,4}-\\d{4}-\\d{4}$", message = "Please enter your telephone number in the format of XXX-XXXX-XXXX") String telephone,
			@Size(min = 8, max = 16, message = "Please set your password between 8 and 16 characters.") String password,
			@NotBlank(message = "Please enter the confirmation password.") String confirmationPassword,
			@NotBlank(message = "Please specify administrative privileges.") Integer authority) {
		super();
		this.name = name;
		this.email = email;
		this.zipcode = zipcode;
		this.address = address;
		this.telephone = telephone;
		this.password = password;
		this.confirmationPassword = confirmationPassword;
		this.authority = authority;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmationPassword() {
		return confirmationPassword;
	}
	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}
	public Integer getAuthority() {
		return authority;
	}
	public void setAuthority(Integer authority) {
		this.authority = authority;
	}
	@Override
	public String toString() {
		return "InsertUserForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + ", password=" + password + ", confirmationPassword="
				+ confirmationPassword + ", authority=" + authority + "]";
	}
	
}
