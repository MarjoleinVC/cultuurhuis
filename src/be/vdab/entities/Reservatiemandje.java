package be.vdab.entities;

import java.util.List;

/*
 overzicht van de reserveringen die de klant heeft gedaan (voor inloggen)
 */
public class Reservatiemandje {

	private final List<Reserveringen> reservering;

	public Reservatiemandje(List<Reserveringen> reservering) {
		this.reservering = reservering;
	}

	// totaal van de (prijs*aantal tickets) per voorstelling
	public double totaalTeBetalen() {
		double totaal = 0;
		for (Reserveringen reserveringen : reservering) {
			totaal += (reserveringen.getVoorstelling().getPrijs())
					* (reserveringen.getAantalPlaatsen());
		}
		return totaal;
	}

	public List<Reserveringen> getReservering() {
		return reservering;
	}
}
