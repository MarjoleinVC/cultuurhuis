package be.vdab.entities;

import java.io.Serializable;

/*
 klanten toevoegen aan database:
 per nieuwe klant een regel in tabel klanten (long id (=primary), String voornaam, String familienaam, String straat, String huisnr, String postcode, String gemeente, String gebruikersnaam, String wachtwoord  

 Adres = String straat, String huisnr, String postcode, String gemeente

 alle velden zijn verplicht! op OK klikken op toe te voegen aan database
 mogelijke fouten: lege velden, paswoord != herhaal paswoord, gebruikersnaam bestaat al
 -> ieder fout oplijsten onder formulier
 ->geslaagde aanmaak nieuwe klant = terugkeren naar reserverenBevestigen
 */
public class Adres implements Serializable {
	private static final long serialVersionUID = 1L;
	private String straat;
	private String huisNr;
	private String postcode;
	private String gemeente;

	public Adres(String straat, String huisNr, String postcode, String gemeente) {
		this.straat = straat;
		this.huisNr = huisNr;
		this.postcode = postcode;
		this.gemeente = gemeente;
	}

	public String getStraat() {
		return straat;
	}

	public void setStraat(String straat) {
		if (!Validatie.isStringValid(straat)) {
			throw new IllegalArgumentException();
		}
		this.straat = straat;
	}

	public String getHuisNr() {
		return huisNr;
	}

	public void setHuisNr(String huisNr) {
		if (!Validatie.isCijferValid(huisNr)) {
			throw new IllegalArgumentException();
		}
		this.huisNr = huisNr;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		if (!Validatie.isCijferValid(postcode)) {
			throw new IllegalArgumentException();
		}
		this.postcode = postcode;
	}

	public String getGemeente() {
		return gemeente;
	}

	public void setGemeente(String gemeente) {
		if (!Validatie.isStringValid(huisNr)) {
			throw new IllegalArgumentException();
		}
		this.gemeente = gemeente;
	}

	@Override
	public String toString() {
		return "Adres{" + "straat=" + straat + ", huisNr=" + huisNr
				+ ", postcode=" + postcode + ", gemeente=" + gemeente + '}';
	}
}
