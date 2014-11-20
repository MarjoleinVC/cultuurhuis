package be.vdab.entities;

import java.util.Date;
import java.util.Objects;

public class Voorstelling implements Comparable<Voorstelling> {

	private String titel;
	private String uitvoerders;
	private Date datum;
	private double prijs;
	private long vrijeplaatsen;
	private long voorstellingId;
	private long genreid;

	public Voorstelling(long voorstellingId, String titel, String uitvoerders,
			Date datum, long genreid, double prijs, long vrijeplaatsen) {
		this.voorstellingId = voorstellingId;
		this.titel = titel;
		this.uitvoerders = uitvoerders;
		this.datum = datum;
		this.genreid = genreid;
		this.prijs = prijs;
		this.vrijeplaatsen = vrijeplaatsen;
	}

	public Voorstelling(long voorstellingId) {
		this.voorstellingId = voorstellingId;
	}

	public static boolean isTekstValid(String tekst) {
		return tekst != null && tekst.isEmpty();
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		if (!isTekstValid(titel)) {
			throw new IllegalArgumentException();
		}
		this.titel = titel;
	}

	public String getUitvoerders() {
		return uitvoerders;
	}

	public void setUitvoerders(String uitvoerders) {
		if (!isTekstValid(uitvoerders)) {
			throw new IllegalArgumentException();
		}
		this.uitvoerders = uitvoerders;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public static boolean isPrijsValid(double prijs) {
		return (prijs > 0);
	}

	public double getPrijs() {
		return prijs;
	}

	public void setPrijs(double prijs) {
		if (!isPrijsValid(prijs)) {
			throw new IllegalArgumentException();
		}
		this.prijs = prijs;
	}

	public static boolean isVrijeplaatsenValid(long vrijeplaatsen) {
		return vrijeplaatsen >= 0;
	}

	public long getVrijeplaatsen() {
		return vrijeplaatsen;
	}

	public void setVrijeplaatsen(long vrijeplaatsen) {
		if (!isVrijeplaatsenValid(vrijeplaatsen)) {
			throw new IllegalArgumentException();
		}
		this.vrijeplaatsen = vrijeplaatsen;
	}

	public long getVoorstellingId() {
		return voorstellingId;
	}

	public void setVoorstellingId(long voorstellingId) {
		this.voorstellingId = voorstellingId;
	}

	public long getGenreid() {
		return genreid;
	}

	// private void setGenreid(long genreid) {
	// this.genreid = genreid;
	// }

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 71 * hash + Objects.hashCode(this.titel);
		hash = 71 * hash + Objects.hashCode(this.uitvoerders);
		hash = 71 * hash + Objects.hashCode(this.datum);
		hash = 71
				* hash
				+ (int) (Double.doubleToLongBits(this.prijs) ^ (Double
						.doubleToLongBits(this.prijs) >>> 32));
		hash = 71 * hash
				+ (int) (this.vrijeplaatsen ^ (this.vrijeplaatsen >>> 32));
		hash = 71 * hash
				+ (int) (this.voorstellingId ^ (this.voorstellingId >>> 32));
		hash = 71 * hash + Objects.hashCode(this.genreid);
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
		final Voorstelling other = (Voorstelling) obj;
		if (!Objects.equals(this.titel, other.titel)) {
			return false;
		}
		return this.voorstellingId == other.voorstellingId;
	}

	@Override
	public String toString() {
		return "Voorstelling{" + ", voorstellingId=" + voorstellingId
				+ "titel=" + titel + ", uitvoerders=" + uitvoerders
				+ ", datum=" + datum + ", genreid=" + genreid + ", prijs=" + prijs
				+ ", vrijeplaatsen=" + vrijeplaatsen + '}';
	}

	@Override
	public int compareTo(Voorstelling o) {
		return (this.titel).compareTo(o.titel);
	}
}
