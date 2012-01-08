package fi.opendemocracy.OpenDemocracyWebService.data.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user", catalog = "od", uniqueConstraints = {
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "password") })
public class User implements java.io.Serializable {

	private Integer userId;
	private String userName;
	private String password;

	public User() {
	}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_ID", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", unique = true, nullable = false, length = 10)
	public String getUserName() {
		return this.userName;
	}

	public void setStockCode(String userName) {
		this.userName = userName;
	}

	@Column(name = "PASSWORD", unique = true, nullable = false, length = 20)
	public String getStockName() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", userId=" + userId
				+ ", password=" + password + "]";
	}

	private static final long serialVersionUID = -1873172514377404995L;
}
