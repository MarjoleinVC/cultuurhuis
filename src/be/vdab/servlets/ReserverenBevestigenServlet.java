package be.vdab.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.dao.GenresDAO;
import be.vdab.dao.KlantDAO;
import be.vdab.dao.ReserverenBevestigenDAO;
import be.vdab.dao.VoorstellingenDAO;
import be.vdab.entities.Klant;

@WebServlet(name = "ReserverenBevestigen", urlPatterns = { "/reserverenBevestigen.htm" })
/*
 * Servlet waarin klant de reservaties moet bevestigen: reservaties wegschrijven
 * in database: vrije plaatsen aanpassen in tabel voorstellingen (verminderen
 * met aantal tickets) + record toevoegen aan tabel reservaties (long id
 * (primary), long klantid, long voorstellingsid, long plaatsen) + aangeven
 * gelukte en mislukte reserveringen (als vrije plaatsen < gevraagde tickets +
 * reservatiemandje leegmaken Klant is aangemeld met gebruikersnaam en
 * wachtwoord
 */
public class ReserverenBevestigenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "WEB-INF/JSP/reserverenBevestigen.jsp";
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
		HttpSession session = request.getSession();
		String gebruikersnaam = request.getParameter("gebruikersnaam");
		String paswoord = request.getParameter("paswoord");
		Klant klant = klantDAO.findKlant(gebruikersnaam, paswoord);
		if (klant == null) {
			request.setAttribute("fout", "Verkeerde gebruikersnaam of paswoord");
		} else {
			/*
			 * TODO als session.setAttribute("klant", klant) wordt in jsp
			 * "Bevestigen" niet beschikbaar als request.setAttribute("klant",
			 * klant) wordt klant niet onthouden om te gebruiken in
			 * OverzichtReserveringenServlet 
			 * session.setAttribute("klant", klant);
			 */
			session.setAttribute("klantId", klant.getKlantId());
			request.setAttribute("klant", klant);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
