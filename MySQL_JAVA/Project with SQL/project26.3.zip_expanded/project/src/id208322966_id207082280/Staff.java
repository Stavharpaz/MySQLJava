package id208322966_id207082280;
import java.util.Scanner;

public class Staff {
	static Scanner s = new Scanner(System.in);
	
	private int id;
	private String name;
	private String role;
	
	public Staff(int id, String name, String role) {
		this.id = id;
		this.name = name;
		this.role = role;		
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
