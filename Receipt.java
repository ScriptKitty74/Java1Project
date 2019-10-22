package javaProjectV2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uca.mis3339.OracleWrapper;

public class Receipt extends HttpServlet {
	private static final long serialVersionUID = 5L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			OracleWrapper.prepareStatement("SELECT * FROM TB_Order_T WHERE OrderID = ?", request.getParameter("id"));

			ResultSet rs = OracleWrapper.queryDB();
			List<Order> allOrders = new ArrayList<Order>();

			long orderID = 0;
			String details = null;
			double subtotal = 0.0;
			double salesTax = 0.0;
			double totalPrice = 0.0;

			while (rs.next()) {
				orderID = rs.getLong(1);
				details = rs.getString(2);
				subtotal = rs.getDouble(3);
				salesTax = rs.getDouble(4);
				totalPrice = rs.getDouble(5);

				allOrders.add(
						new Order(orderID, details, subtotal, salesTax, totalPrice));
			} // end while
			out.println("<!DOCTYPE html><html>");
			out.println("<body style = 'background-color: lightblue;'>");
			out.println("<center><img src = 'javaProjectV2/top.PNG' style = 'width: 600px;'>");
			out.println("<div>");
			out.println("<form action=\"/Receipt\"><table  \"display:inline-block\" border=0>");
			out.println("<body>");
			out.println("<font size=\"3\"face= \"arial\">");
			out.println("</body>");
			out.println("<div id=\"invoice-POS\">");
			out.println("<center id=\"top\">");
			out.println("<div class=\"logo\"></div>");
			out.println("<div class=\"info\"> ");
			out.println("<h1>Amazon Purchase Receipt</h1>");
			out.println("</div>  ");
			out.println("<div id=\"mid\">");
			out.println("<div class=\"info\">");
			out.println("<h2> Customer Information </h2>");
			out.println("</center>");
			out.println("<center><p> ");
			out.println("Name   : "+ request.getParameter("name") +"</br>");
			out.println("Address: "+ request.getParameter("address") +"</br>");
			out.println("Phone  : "+ request.getParameter("phone") + "</br>");
			out.println("</p></center>");
			out.println("</div>");
			out.println("</div>");
			out.println("<div id=\"table\">");
			out.println("<center><table cellspacing =\"15\">");
			out.println("<tr class=\"service\">");
			out.println("<td class=\"tableitem\"><p class=\"itemtext\"><b>#Transaction:  <b></p></td>");
			out.println("<td class=\"tableitem\"><p class=\"itemtext\"> " + orderID + " </p></td>");
			out.println("</tr>");
			out.println("<tr class=\"service\">");
			out.println("<td class=\"tableitem\"><p class=\"itemtext\"><b>SubTotal:</b></p></td>");
			out.println("<td class=\"tableitem\"><p class=\"itemtext\">" + NumberFormat.getCurrencyInstance().format(subtotal) + "</p></td>");
			out.println("</tr>");
			out.println("<tr class=\"service\">");
			out.println("<td class=\"tableitem\"><p class=\"itemtext\"><b>TaxTotal:</b></p></td>");
			out.println("<td class=\"tableitem\"><p class=\"itemtext\"> " + NumberFormat.getCurrencyInstance().format(salesTax) + "</p></td>");
			out.println("<tr class=\"service\">");
			out.println("<td class=\"tableitem\"><p class=\"itemtext\"><b>Total Price:</b></p></td>");
			out.println("<td class=\"tableitem\"><p class=\"itemtext\">" + NumberFormat.getCurrencyInstance().format(totalPrice) + " </p></td>");
			out.println("</tr>");
			out.println("</table></center>");
			out.println("</div>");
			out.println("<hr width=\"40\">");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} // end catch
	}// end doGet

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}// end doPost
}// end Receipt