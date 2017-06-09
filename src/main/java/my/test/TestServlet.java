package my.test;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import my.test.entity.Person;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Context ctx = new InitialContext();
            UserTransaction transaction = (UserTransaction) ctx.lookup("java:comp/UserTransaction");
            EntityManager entityManager = (EntityManager) ctx.lookup("java:comp/env/myEntityMgr");
            transaction.begin();
            Person person = new Person(1, "firstName", "lastName");
            entityManager.persist(person);
            transaction.commit();
            response.getWriter().println("Success!");
            response.getWriter().flush();
		} catch (Exception e) {
			response.getWriter().println("Fail!");
			e.printStackTrace(response.getWriter());
            response.getWriter().flush();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
