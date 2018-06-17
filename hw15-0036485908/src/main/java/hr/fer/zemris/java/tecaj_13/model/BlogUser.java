package hr.fer.zemris.java.tecaj_13.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blog_user")
public class BlogUser {

	private Long id;
	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private String passwordHash;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	@Column(length = 100, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	@Column(length = 100, nullable = false)
	public String getLastName() {
		return lastName;
	}

	@Column(length = 100, nullable = false)
	public String getNick() {
		return nick;
	}
	
	@Column(length = 100, nullable = false)
	public String getEmail() {
		return email;
	}
	
	@Column(length = 1000, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
