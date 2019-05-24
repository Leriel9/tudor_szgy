package hu.elte.szgy.tudor.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="szakterulet")
public class Szakterulet implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int szakteruletID;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
	private Tudor tudor;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
	private Kategoria kategoria;

	public int getSzakteruletID() { return szakteruletID; }
	public Tudor getTudor() { return tudor; }
	public void setTudor(Tudor tudor) { this.tudor = tudor; } 
	public Kategoria getKategoria() { return kategoria; }
	public void setKategoria(Kategoria kategoria) { this.kategoria = kategoria; } }