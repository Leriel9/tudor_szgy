package hu.elte.szgy.tudor.data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hu.elte.szgy.tudor.data.Kategoria;

@Entity
@Table(name="kerdes")
@NamedQuery(name="Kerdes.bongeszes", query="SELECT kerdesId from Kerdes")
//@NamedQuery(name="Kerdes.bongeszes", query="SELECT * from Kerdes k WHERE k.szoveg like :szoveg AND k.kategoria=:temakor")
public class Kerdes implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int kerdesId;
	
	private Date datum;
	
	private String szoveg;
	
    public enum KerdesStatusz {
	NYITOTT, LEZART, TOROLT
    }

    @Enumerated(EnumType.STRING)
    private KerdesStatusz statusz;
	
	private Boolean masolat;

	@OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
	private Ugyfel ugyfel;

	@OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
	private Kategoria kategoria;
	
	public int getKerdesId() { return kerdesId; }
	public Date getDatum() { return datum; }
	public void setDatum(Date datum) { this.datum = datum; } 
	public String getSzoveg() { return szoveg; }
	public void setSzoveg(String szoveg) { this.szoveg = szoveg; }
	public KerdesStatusz getKerdesStatusz() { return statusz; }
	public void setKerdesStatusz(KerdesStatusz statusz) { this.statusz = statusz; }
	public Boolean getMasolat() { return masolat; }
	public void setMasolat(Boolean masolat) { this.masolat = masolat; }
	public Ugyfel getUgyfel() { return ugyfel; }
	public void setUgyfel(Ugyfel ugyfel) { this.ugyfel = ugyfel; }
	public Kategoria getKategoria() { return kategoria; }
	public void setKategoria(Kategoria kategoria) { this.kategoria = kategoria; }  }
