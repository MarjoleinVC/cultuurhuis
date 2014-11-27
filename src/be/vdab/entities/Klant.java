package be.vdab.entities;

import java.io.Serializable;
import java.util.Objects;

/*
 klanten toevoegen aan database:
 per nieuwe klant een regel in tabel klanten (long id (=primary), String voornaam, String familienaam, String straat, String huisnr, String postcode, String gemeente, String gebruikersnaam, String wachtwoord  

 Adres = String straat, String huisnr, String postcode, String gemeente

 alle velden zijn verplicht! op OK klikken op toe te voegen aan database
 mogelijke fouten: lege velden, paswoord != herhaal paswoord, gebruikersnaam bestaat al
 -> ieder fout oplijsten onder formulier
 ->geslaagde aanmaak nieuwe klant = terugkeren naar reserverenBevestigen
 */
public class Klant implements Serializable {
	private static final long serialVersionUID = 1L;
	private long KlantId;
	private String vnaam;
	private String fnaam;
	private Adres adres;
	private String gebruikersnaam;
	private String paswoord;


	public Klant(String vnaam, String fnaam, Adres adres,
			String gebruikersnaam, String paswoord) {
		this.vnaam = vnaam;
		this.fnaam = fnaam;
		this.adres = adres;
		this.gebruikersnaam = gebruikersnaam;
		this.paswoord = paswoord;
	}

	public Klant(long KlantId, String vnaam, String fnaam, Adres adres,
			String gebruikersnaam, String paswoord) {
		this.KlantId = KlantId;
		this.vnaam = vnaam;
		this.fnaam = fnaam;
		this.adres = adres;
		this.gebruikersnaam = gebruikersnaam;
		this.paswoord = paswoord;
	}
	
	public Klant(long KlantId, String vnaam, String fnaam, Adres adres) {
		this.KlantId = KlantId;
		this.vnaam = vnaam;
		this.fnaam = fnaam;
		this.adres = adres;
	}

	public long getKlantId() {
		return KlantId;
	}

	public void setKlantId(long KlantId) {
		this.KlantId = KlantId;
	}

	public String getVnaam() {
		return vnaam;
	}

	public void setVnaam(String vnaam) {
		this.vnaam = vnaam;
	}

	public String getFnaam() {
		return fnaam;
	}

	public void setFnaam(String fnaam) {
		this.fnaam = fnaam;
	}

	public Adres getAdres() {
		return adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public void setGebruikersnaam(String gebruikersnaam) {
		this.gebruikersnaam = gebruikersnaam;
	}

	public String getPaswoord() {
		return paswoord;
	}

	public void setPaswoord(String paswoord) {
		this.paswoord = paswoord;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + (int) (this.KlantId ^ (this.KlantId >>> 32));
		hash = 67 * hash + Objects.hashCode(this.vnaam);
		hash = 67 * hash + Objects.hashCode(this.fnaam);
		hash = 67 * hash + Objects.hashCode(this.adres);
		hash = 67 * hash + Objects.hashCode(this.gebruikersnaam);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Klant other = (Klant) obj;
		if (this.KlantId != other.KlantId) {
			return false;
		}
		return Objects.equals(this.gebruikersnaam, other.gebruikersnaam);
	}

	@Override
	public String toString() {
		return "Klant{" + "KlantId=" + KlantId + ", vnaam=" + vnaam
				+ ", fnaam=" + fnaam + ", adres=" + adres + ", gebruikersnaam="
				+ gebruikersnaam + '}';
	}
}
