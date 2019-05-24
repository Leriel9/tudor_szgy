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

import hu.elte.szgy.tudor.data.Kategoria;

@Entity
@Table(name="erdekeltkategoria")
public class ErdekeltKategoria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int erdekeltKategoriaID;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
	private Ugyfel ugyfel;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
	private Kategoria kategoria;

	public int getSzakteruletID() { return erdekeltKategoriaID; }
	public Ugyfel getUgyfel() { return ugyfel; }
	public void setUgyfel(Ugyfel ugyfel) { this.ugyfel = ugyfel; } 
	public Kategoria getKategoria() { return kategoria; }
	public void setKategoria(Kategoria kategoria) { this.kategoria = kategoria; } }
