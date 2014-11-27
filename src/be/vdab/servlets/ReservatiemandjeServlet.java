package be.vdab.servlets;

import java.io.IOException;
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
import be.vdab.entities.Reserveringen;

@WebServlet(name = "ReservatiemandjeServlet", urlPatterns = { "/reservatiemandje.htm" })
/*
 * Servlet waarin overzicht gegeven wordt van de reserveringen die de klant
 * heeft gedaan, maar nog moet bevestigen. Aangevinkte reservatielijnen
 * verwijderen en reservatiemandje opnieuw tonen.
 */
public class ReservatiemandjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/reservatiemandje.jsp";
	private static final String REDIRECT_URL = "/reservatiemandje.htm";
	private final transient GenresDAO genresDAO = new GenresDAO();
	private final transient KlantDAO klantDAO = new KlantDAO();
	private final transient ReserverenBevestigenDAO reserverenBevestigenDAO = new ReserverenBevestigenDAO();
	private final transient VoorstellingenDAO voorstellingenDAO = new VoorstellingenDAO();

	/*
	 * TODO geen reserveringen zichtbaar in reservatiemandje. Volgens mij wordt
	 * een reservatie niet onthouden?
	 */

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
		HttpSession session = request.getSession(false);
		@SuppressWarnings("unchecked")
		HashMap<Long, Long> reservatiemandje = (HashMap<Long, Long>) session
				.getAttribute("reservatiemandje");
		List<Reserveringen> reserveringInReservatiemandje = new ArrayList<>();
		double totaalTeBetalen = 0;
		if (reservatiemandje != null) {
			for (Entry<Long, Long> entry : reservatiemandje.entrySet()) {
				long voorstellingId = entry.getKey();
				long aantalPlaatsen = entry.getValue();
				Reserveringen reservering = new Reserveringen(
						voorstellingenDAO.findByPK(voorstellingId),
						aantalPlaatsen);
				reserveringInReservatiemandje.add(reservering);
				totaalTeBetalen += (reservering.getVoorstelling().getPrijs())
						* (reservering.getAantalPlaatsen());
			}
			request.setAttribute("totaalTeBetalen", totaalTeBetalen);
			request.setAttribute("reserveringenInReservatiemandje",
					reserveringInReservatiemandje);
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String[] removeReserveringen = request
				.getParameterValues("verwijderenAangevinkt");
		if (session != null) {
			@SuppressWarnings("unchecked")
			HashMap<Long, Long> reservatiemandje = (HashMap<Long, Long>) session
					.getAttribute("reservatiemandje");
			List<Reserveringen> reserveringInReservatiemandje = new ArrayList<>();
			double totaalTeBetalen = 0;
			if (reservatiemandje != null) {
				if (request.getParameter("verwijderenAangevinkt") != null) {
					List<Long> toRemove = new ArrayList<>();
					for (String verwijderenAangevinkt : removeReserveringen) {
						toRemove.add(Long.parseLong(verwijderenAangevinkt));
					}
					for (long remove : toRemove) {
						if (reservatiemandje.containsKey(remove)) {
							reservatiemandje.remove(remove);
						}
					}
				}
				for (Entry<Long, Long> entry : reservatiemandje.entrySet()) {
					long voorstellingId = entry.getKey();
					long aantalPlaatsen = entry.getValue();
					Reserveringen reservering = new Reserveringen(
							voorstellingenDAO.findByPK(voorstellingId),
							aantalPlaatsen);
					reserveringInReservatiemandje.add(reservering);
					totaalTeBetalen += (reservering.getVoorstelling()
							.getPrijs()) * (reservering.getAantalPlaatsen());
				}
				request.setAttribute("totaalTeBetalen", totaalTeBetalen);
				request.setAttribute("reserveringenInReservatiemandje",
						reserveringInReservatiemandje);
			}
			session.setAttribute("reservatiemandje", reservatiemandje);
			response.sendRedirect(response.encodeURL(request.getContextPath()
					+ REDIRECT_URL));
		}
	}
}
