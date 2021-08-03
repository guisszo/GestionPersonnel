package com.iam.gestionpersonnel.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.iam.gestionpersonnel.dao.PersonnelDAO;
import com.iam.gestionpersonnel.model.Personnel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/")
public class PersonnelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PersonnelDAO personnelDAO;

	public void init() {
		personnelDAO = new PersonnelDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			case "/search":
				searchPerson(request,response);
				break;
			default:
				listUser(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {

		System.out.println("execution listIser");
		List<Personnel> listUser = personnelDAO.selectAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Personnel existingUser = personnelDAO.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);

	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String nom = request.getParameter("nom");
		String email = request.getParameter("email");
		String pays = request.getParameter("pays");
		Personnel newUser = new Personnel(nom, email, pays);
		personnelDAO.insertUser(newUser);
		response.sendRedirect("list");
	}

	private void searchPerson(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("je suis ici");

		String nom = request.getParameter("nom");
		String email = request.getParameter("email");
		String pays = request.getParameter("pays");
		System.out.println("null");
		List<Personnel> listUser = personnelDAO.searchPerson(nom, email, pays);
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String nom = request.getParameter("nom");
		String email = request.getParameter("email");
		String pays = request.getParameter("pays");

		Personnel editUser = new Personnel(id, nom, email, pays);
		personnelDAO.updateUser(editUser);
		response.sendRedirect("list");
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		personnelDAO.deleteUser(id);
		response.sendRedirect("list");

	}

}
