package be.vdab.servlets;

import java.io.IOException;
import java.util.ArrayList;

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
import be.vdab.entities.Genre;
import be.vdab.entities.Voorstelling;

@WebServlet(name = "VoorstellingenServlet", urlPatterns = { "/voorstellingen.htm" })
/*
 * Servlet waarin voorstellingen per genre worden opgehaald
 */
public class VoorstellingenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/voorstellingen.jsp";
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
		HttpSession session = request.getSession();
		Iterable<Genre> genres = genresDAO.findAll();
		request.setAttribute("genres", genres);
		String selectedGenre = request.getParameter("genre");
		if (selectedGenre != null && !selectedGenre.isEmpty()
		) {
			ArrayList<Voorstelling> voorstelling = voorstellingenDAO
					.findByGenre(selectedGenre);
			if (voorstelling == null) {
				request.setAttribute("fout", "Voorstelling niet gevonden");
			}
			session.setAttribute("genre", selectedGenre);
			request.setAttribute("voorstellingen", voorstelling);
		}
		request.getRequestDispatcher(VIEW).forward(request, response);

	}
}
