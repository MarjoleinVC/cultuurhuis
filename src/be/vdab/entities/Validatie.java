package be.vdab.entities;

public class Validatie {

	public static boolean isStringValid(String tekst) {
		if (tekst.matches("[a-zA-Z]") && tekst.length() <= 20)
			return true;
		return false;
	}

	public static boolean isCijferValid(String tekst) {
		if (tekst.matches("[0-9]") && tekst.length() <= 20)
			return true;
		return false;
	}
}
