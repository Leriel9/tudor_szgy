package hu.elte.szgy.tudor.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tudor")
public class Tudor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tudorId;
	
	private String nev;
	
	private String email;
	
	public int gettudorId() { return tudorId; }
	public String getNev() { return nev; }
	public void setNev(String nev) { this.nev = nev; } 
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; } }
