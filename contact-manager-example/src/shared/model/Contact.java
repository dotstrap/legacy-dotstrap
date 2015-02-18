package shared.model;

public class Contact {

	private int id;
	private String name;
	private String phone;
	private String address;
	private String email;
	private String url;
	
	public Contact() {
		setId(-1);
		setName("New Contact");
		setPhone(null);
		setAddress(null);
		setEmail(null);
		setUrl(null);
	}
	
	public Contact(int id, String name, String phone, String address,
					String email, String url) {
		setId(id);
		setName(name);
		setPhone(phone);
		setAddress(address);
		setEmail(email);
		setUrl(url);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
