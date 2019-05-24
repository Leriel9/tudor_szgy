package hu.elte.szgy.tudor.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ugyfel")
public class Ugyfel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ugyfelID;
	
	private String nev;
	
	private String telefonszam;
	
	private String email;
		
	public int getUgyfelId() { return ugyfelID; }
	public String getNev() { return nev; }
	public void setNev(String nev) { this.nev = nev; } 
	public String getTelefonszam() { return telefonszam; }
	public void setTelefonszam(String telefonszam) { this.telefonszam = telefonszam; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; } }
