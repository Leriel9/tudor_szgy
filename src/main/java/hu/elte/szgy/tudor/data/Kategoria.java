package hu.elte.szgy.tudor.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="kategoria")
public class Kategoria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int kategoriaId;
	
	private String nev;
	
	private String leiras;

	public int getKategoriaId() { return kategoriaId; }
	public String getNev() { return nev; }
	public void setNev(String nev) { this.nev = nev; } 
	public String getLeiras() { return leiras; }
	public void setLeiras(String leiras) { this.leiras = leiras; } }
