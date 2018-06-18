package hr.fer.zemris.java.tecaj_13.model;

public class Record implements Comparable<Record> {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String nickName;
	private String email;
	private String password;
	
	public Record(Long id, String firstName, String lastName, String nickName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickName = nickName;
		this.email = email;
		this.password = password;
	}

	public Record() {
	}
	
	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getNickName() {
		return nickName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
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

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int compareTo(Record o) {
		if(this.id==null) {
			if(o.id==null) return 0;
			return -1;
		} else if(o.id==null) {
			return 1;
		}
		return this.id.compareTo(o.id);
	}
	
	@Override
	public String toString() {
		return "{" + (id==null ? "?" : id.toString()) +
				" " + lastName + ", " + firstName + " (" +
				email + ")}";
	}
}
