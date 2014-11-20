package be.vdab.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
import be.vdab.entities.Reserveringen;
import be.vdab.entities.Voorstelling;

@WebServlet(name = "OverzichtReserveringen", urlPatterns = { "/overzichtReserveringen.htm" })
/*
 * Servlet waarin alle bewerkingen op de database worden uitgevoerd. Servlet
 * heeft geen view, maar verwijst onmiddelijk door naar overzicht met
 * gelukte/mislukte reserveringen. --> opsplitsing tussen gelukte en mislukte
 * boekingen!
 */
public class OverzichtReserveringenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "WEB-INF/JSP/overzichtReserveringen.jsp";
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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		@SuppressWarnings("unchecked")
		HashMap<Long, Long> reservatiemandje = (HashMap<Long, Long>) session
				.getAttribute("reservatiemandje");
		List<Reserveringen> afgehandeldeReserveringen = new ArrayList<>();
		List<Reserveringen> mislukteReserveringen = new ArrayList<>();
		List<Reserveringen> gelukteReserveringen = new ArrayList<>();
		Klant klant = (Klant) session.getAttribute("klant");
		for (Entry<Long, Long> entry : reservatiemandje.entrySet()) {
			Voorstelling voorstelling = new Voorstelling(entry.getKey()
					.longValue());
			Reserveringen reservering = new Reserveringen(voorstelling,
					entry.getValue());
			/*
			 * TODO melding van unhandled SQLException, maar throwen van
			 * SQLException in Try-Catch lost niets op, want dan opnieuw melding
			 * van unhandled SQLException.
			 */
			Reserveringen afgehandeldeReservering = null;
			try {
				afgehandeldeReservering = reserverenBevestigenDAO
						.confirmReserveringen(reservering, klant.getKlantId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			afgehandeldeReserveringen.add(afgehandeldeReservering);
		}
		for (Reserveringen reservering : afgehandeldeReserveringen) {
			if (reservering.isSuccesReservering()) {
				gelukteReserveringen.add(reservering);
				request.setAttribute("gelukteReserveringen",
						gelukteReserveringen);
			} else {
				mislukteReserveringen.add(reservering);
				request.setAttribute("mislukteReserveringen",
						mislukteReserveringen);
			}
		}
		session.removeAttribute("reservatiemandje");
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
