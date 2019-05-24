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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="valasz")
public class Valasz implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int valaszId;
	
	private Date datum;
	
	private String szoveg;
	
    public enum ValaszErtekeles { KRITIKAN_ALULI, MEH, OK, JO, SZUPER }

    @Enumerated(EnumType.STRING)
    private ValaszErtekeles ertekeles;

	@OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
	private Kerdes kerdes;

	@OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
	private Tudor tudor;

	public int getValaszId() { return valaszId; }
	public Date getDatum() { return datum; }
	public void setDatum(Date datum) { this.datum = datum; } 
	public String getSzoveg() { return szoveg; }
	public void setSzoveg(String szoveg) { this.szoveg = szoveg; }
	public ValaszErtekeles getErtekeles() { return ertekeles; }
	public void setErtekeles(ValaszErtekeles ertekeles) { this.ertekeles = ertekeles; }
	public Kerdes getKerdes() { return kerdes; }
	public void setKerdes(Kerdes kerdes) { this.kerdes = kerdes; }
	public Tudor getTudor() { return tudor; }
	public void setTudor(Tudor tudor) { this.tudor = tudor; }  }
