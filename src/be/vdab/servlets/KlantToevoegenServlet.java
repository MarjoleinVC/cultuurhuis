package be.vdab.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.dao.GenresDAO;
import be.vdab.dao.KlantDAO;
import be.vdab.dao.ReserverenBevestigenDAO;
import be.vdab.dao.VoorstellingenDAO;
import be.vdab.entities.Adres;
import be.vdab.entities.Klant;

@WebServlet(name = "KlantToevoegenServlet", urlPatterns = { "/nieuweKlant.htm" })
/*
 * Servlet waarin nieuwe klant zijn gegevens met een gebruikersnaam en
 * wachtwoord aanmaakt en toevoegt aan database Volgt op aanklikken
 * "ik ben nieuw"
 */
public class KlantToevoegenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "WEB-INF/JSP/nieuweKlant.jsp";
	private static final String REDIRECT_URL = "%s/reserverenBevestigen.jsp";
	private final transient GenresDAO genresDAO = new GenresDAO();
	private final transient KlantDAO klantDAO = new KlantDAO();
	private final transient ReserverenBevestigenDAO reserverenBevestigenDAO = new ReserverenBevestigenDAO();
	private final transient VoorstellingenDAO voorstellingenDAO = new VoorstellingenDAO();

	@Resource(name = VoorstellingenDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		voorstellingenDAO.setDataSource(dataSource);
		genresDAO.setDataSource(dataSource);
		klantDAO.setDataSource(dataSource);
		reserverenBevestigenDAO.setDataSource(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> fouten = new LinkedHashMap<>();
		String vnaam = null;
		vnaam = request.getParameter("vnaam");
		if (vnaam == null || vnaam.isEmpty()) {
			fouten.put("vnaam", "Voornaam niet ingevuld.");
		}
		String fnaam = null;
		fnaam = request.getParameter("fnaam");
		if (fnaam == null || fnaam.isEmpty()) {
			fouten.put("fnaam", "Familienaam niet ingevuld.");
		}
		String straat = null;
		straat = request.getParameter("straat");
		if (straat == null || straat.isEmpty()) {
			fouten.put("straat", "Straat niet ingevuld.");
		}
		String huisnr = null;
		huisnr = request.getParameter("huisnr");
		if (huisnr == null || huisnr.isEmpty()) {
			fouten.put("huisnr", "Huisnr. niet ingevuld.");
		}
		String postcode = null;
		postcode = request.getParameter("postcode");
		if (postcode == null || postcode.isEmpty()) {
			fouten.put("postcode", "Postcode niet ingevuld.");
		}
		String gemeente = null;
		gemeente = request.getParameter("gemeente");
		if (gemeente == null || gemeente.isEmpty()) {
			fouten.put("gemeente", "Gemeente niet ingevuld.");
		}
		String gebruikersnaam = null;
		gebruikersnaam = request.getParameter("gebruikersnaam");
		if (gebruikersnaam == null || gebruikersnaam.isEmpty()) {
			fouten.put("gebruikersnaam", "Gebruikersnaam niet ingevuld.");
		}
		String paswoord = null;
		paswoord = request.getParameter("paswoord");
		if (paswoord == null || paswoord.isEmpty()) {
			fouten.put("paswoord", "Paswoord niet ingevuld.");
		}
		String paswoord2 = null;
		paswoord2 = request.getParameter("paswoord2");
		if (paswoord2 == null || paswoord2.isEmpty()) {
			fouten.put("paswoord2", "Herhaal paswoord niet ingevuld.");
		}
		if (paswoord2 != null) {
			if (!paswoord.equals(paswoord2)) {
				fouten.put("paswoord",
						"Het paswoord en het herhaalde paswoord zijn verschillend.");
			}
		}
		if (klantDAO.gebruikersnaamBestaat(gebruikersnaam)) {
			fouten.put("gebruikersnaam",
					"De gebruikersnaam komt al voor in de database.");
		}
		if (!fouten.isEmpty()) {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		} else {
			klantDAO.createKlant(new Klant(vnaam, fnaam, new Adres(straat,
					huisnr, postcode, gemeente), gebruikersnaam, paswoord2));
			request.setAttribute("klant",
					klantDAO.findKlant(gebruikersnaam, paswoord2));
			response.sendRedirect(String.format(REDIRECT_URL,
					request.getContextPath()));
		}
	}
}
