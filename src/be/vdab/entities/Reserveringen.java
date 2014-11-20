package be.vdab.entities;

import java.util.Objects;

/*
 class om per reservatie uit "ReserverenServlet" voorstelling(=Datum, Titel, Uitvoerders, Prijs), Plaatsen (aantal tickets) aan te geven, reservatieNr als unieke key
 */
public class Reserveringen implements Comparable<Reserveringen> {

	private long reservatieNr;
	private Voorstelling voorstelling;
	private long aantalPlaatsen;
	private boolean succesReservering;

	public Reserveringen(Voorstelling voorstelling,
			long aantalPlaatsen) {
		this.voorstelling = voorstelling;
		this.aantalPlaatsen = aantalPlaatsen;
	}

	public long getReservatieNr() {
		return reservatieNr;
	}

	public void setReservatieNr(long reservatieNr) {
		this.reservatieNr = reservatieNr;
	}

	public Voorstelling getVoorstelling() {
		return voorstelling;
	}

	public void setVoorstelling(Voorstelling voorstelling) {
		this.voorstelling = voorstelling;
	}

	public long getAantalPlaatsen() {
		return aantalPlaatsen;
	}

	public void setAantalPlaatsen(long aantalPlaatsen) {
		this.aantalPlaatsen = aantalPlaatsen;
	}	

	public boolean isSuccesReservering() {
		return succesReservering;
	}

	public void setSuccesReservering(boolean succesReservering) {
		this.succesReservering = succesReservering;
	}

	@Override
	public int compareTo(Reserveringen o) {
		return (this.voorstelling).compareTo(o.voorstelling);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 41 * hash
				+ (int) (this.reservatieNr ^ (this.reservatieNr >>> 32));
		hash = 41 * hash + Objects.hashCode(this.voorstelling);
		hash = 41 * hash
				+ (int) (this.aantalPlaatsen ^ (this.aantalPlaatsen >>> 32));
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
		final Reserveringen other = (Reserveringen) obj;
		if (this.reservatieNr != other.reservatieNr) {
			return false;
		}
		if (!Objects.equals(this.voorstelling, other.voorstelling)) {
			return false;
		}
		return this.aantalPlaatsen == other.aantalPlaatsen;
	}

	@Override
	public String toString() {
		return "Reserveringen{" + "reservatieNr=" + reservatieNr
				+ ", voorstelling=" + voorstelling + ", aantalPlaatsen="
				+ aantalPlaatsen + '}';
	}
}
