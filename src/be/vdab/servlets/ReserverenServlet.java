package be.vdab.servlets;

import java.io.IOException;
import java.util.HashMap;

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
import be.vdab.entities.Voorstelling;

@WebServlet(name = "ReserverenServlet", urlPatterns = { "/reserveren.htm" })
/*
 * Servlet waarin klant per voorstelling aantal tickets reserveert
 */
public class ReserverenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/reserveren.jsp";
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
		long selectedVoorstellingId = Long.parseLong(request
				.getParameter("voorstelling"));
		Voorstelling voorstelling = voorstellingenDAO
				.findByPK(selectedVoorstellingId);
		if (voorstelling == null) {
			request.setAttribute("fout", "Voorstelling niet gevonden");
		}
		request.setAttribute("voorstelling", voorstelling);
		if (reservatiemandje != null
				&& reservatiemandje
						.containsKey(new Long(selectedVoorstellingId))) {
			request.setAttribute("aantalTeReserveren",
					reservatiemandje.get(new Long(selectedVoorstellingId)));

		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Voorstelling voorstelling = voorstellingenDAO.findByPK(Long
				.parseLong(request.getParameter("voorstelling")));
		request.setAttribute("voorstelling", voorstelling);
		try {
			long aantalTeReserveren = Long.parseLong(request
					.getParameter("aantalTeReserveren"));
			if (!(aantalTeReserveren <= 0)
					&& aantalTeReserveren <= voorstelling.getVrijeplaatsen()) {
				Reserveringen reservering = new Reserveringen(voorstelling,
						aantalTeReserveren);
				HashMap<Long, Long> reservatiemandje = (HashMap<Long, Long>) session
						.getAttribute("reservatiemandje");
				reservatiemandje.put(reservering.getVoorstelling()
						.getVoorstellingId(), reservering.getAantalPlaatsen());
				session.setAttribute("reservatiemandje", reservatiemandje);
				response.sendRedirect(response.encodeURL(request
						.getContextPath() + REDIRECT_URL));
			} else {
				request.setAttribute("fout", "Tik een getal tussen 1 en "
						+ voorstelling.getVrijeplaatsen());
				request.getRequestDispatcher(VIEW).forward(request, response);
			}
		} catch (NumberFormatException ex) {
			request.setAttribute("fout", "Tik een getal tussen 1 en "
					+ voorstelling.getVrijeplaatsen());
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}
}
