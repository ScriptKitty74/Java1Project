package javaProjectV2;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uca.mis3339.OracleWrapper;

public class PlacingOrder extends HttpServlet {
	private static final long serialVersionUID = 15L;
	private static double SALESTAXRATE = 0.0925;

	private String details = new String();
	private double subTotal = 0;
	private double salesTax = 0;
	private double totalPrice = 0;
	private String userName = "";
	private String userAddress = "";
	private String userPhone = "";

	long uniqueID = java.util.Calendar.getInstance().getTimeInMillis();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html><html>");
		out.println("<body style = 'background-color: lightblue;'>");
		out.println(
				"<center><img src='images/top.PNG' style=\" width: 90px;\" \"height= 80px;\"></center><hr width=\"80px\">");

		out.println("<div>");
		out.println("<form action=\"/Order\" method=\"post\"><table  \"display:inline-block\" border=0>");
		out.println("<tr><td colspan=3><center><b>Invoice </b></center> </td></tr>");

		if (request.getParameter("add") == null) 
		{
			out.println(
					"<tr><td>Last Name:</td><td><input type=\"textbox\" name=\"customerName\"></td> <td rowspan=\"5\"><textarea name=\"details\" "
							+ "rows=\"10\" cols=\"50\" style=\"resize: none; value=\"" + details + "\" readonly>" + details
							+ "</textarea></td></tr>");
			out.println("<tr><td>Address:</td><td><input type=\"textbox\" name=\"address\" ></td></tr>");
			out.println("<tr><td>Phone Number:</td><td><input type=\"tel\" name=\"phone\"></td></tr>");		
		}
		userName = request.getParameter("customerName");
		userAddress = request.getParameter("address");
		userPhone = request.getParameter("phone");

		if(request.getParameter("add") != null) 
		{
			out.println("<tr><td>Name:</td><td><input type=\"textbox\" name=\"customerName\" value=\""+ userName +"\" readonly></td> <td rowspan=\"5\"><textarea name=\"details\" "
					+ "rows=\"10\" cols=\"50\" style=\"resize: none; value=\"" + details + "\" readonly>" + details
					+ "</textarea></td></tr>");
			out.println("<tr><td>Address:</td><td><input type=\"textbox\" name=\"address\" value=\""+ userAddress +"\"readonly></td></tr>");
			out.println("<tr><td>Phone Number:</td><td><input type=\"tel\" name=\"phone\" value=\""+ userPhone +"\"readonly></td></tr>");

		}

		out.println("<tr><td>Product SKU:</td><td><input type=\"textbox\" name=\"sku\"></td></tr>");
		out.println("<tr><td>Quantity:</td><td><input type=\"textbox\" name=\"quantity\"></td></tr>");
		out.println("<tr><td><input type=\"submit\" value=\"Add Item to Sale\" name=\"add\"></td>");
		out.println("</table> ");
		out.println("</div>");

		out.println("<div><br>");
		out.println("<br><tr><td colspan=2><b> Total Pricing </b></td></tr><br>");
		out.println("<br><tr><td>Sales Subtotal:</td><td><input type =\"textbox\" name =\"subtotal\" value=\""
				+ subTotal + "\" readonly></td></tr><br>");
		out.println("<br><tr><td>Sales Tax Total:</td><td><input type = \"textbox\" name = \"salestax\" value=\""
				+ salesTax + "\" readonly></td></tr><br>");
		out.println("<br><tr><td> Sales Order Price:</td><td> <input type = \"textbox\" name = \"totalPrice\" value=\""
				+ totalPrice + "\" readonly></td></tr> <br>");
		out.println("<br><td><input type=\"submit\" value=\"Cancel Order\" name=\"cancel\"></td></tr>");
		out.println("<input type=\"submit\" value=\"Complete Sale\" name=\"complete\"></td></tr>");
		out.println("</table></form>");
		out.println("</div>");

		//		out.println("<script type =\"text/javascript\">");
		//		out.println("alert('Please enter a valid SKU(1000,1001 or 1002) and an interger positive Quantity');");
		//		out.println("</script>");


	} // end doGet

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>receipt</title></head>");
		// check if the button's name parameter field is not null - not null means it
		// was the one that was clicked
		if (request.getParameter("cancel") != null) 
		{
			response.sendRedirect("/Order"); // if the user cancels, redirect to the base url and start over
			details = "";
			subTotal = 0.0;
			salesTax = 0.0;
			totalPrice = 0.0;
		} // end if

		if (request.getParameter("add") != null) 
		{

			//Let check if the user inter a valid SKU (does work as I wanted it to)
			if(Integer.valueOf(String.valueOf(request.getParameter("sku")))!= 1000 || Integer.valueOf(String.valueOf(request.getParameter("sku"))) != 1001
					|| Integer.valueOf(String.valueOf(request.getParameter("sku"))) != 1002)
			{
				out.println("<script type =\"text/javascript\">");
				out.println("alert('Please enter a valid SKU(1000,1001 or 1002)');");
				out.println("</script>"); 
			}




			try {

				Integer SKU = (Integer.valueOf(String.valueOf(request.getParameter("sku"))));
				System.out.println(SKU);
				OracleWrapper.prepareStatement(
						"SELECT SKU, PRODUCTPRICE, PRODUCTDESCRIPTION FROM TB_Product_T WHERE SKU =?", SKU);

				ResultSet rs = OracleWrapper.queryDB();
				String description = new String();
				double price = 0;
				while (rs.next()) 
				{
					price = rs.getDouble(2);
					description = rs.getString(3);
				}
				details = "";
				details += request.getParameter("details"); // reads the existing textarea
				details += request.getParameter("sku") + "\t(" + request.getParameter("quantity") + ")\t" + price + "\t" + description
						+ "\n"; // appends the new info
				subTotal = Double.valueOf(String.valueOf(request.getParameter("subtotal")));
				subTotal += (Double.valueOf(String.valueOf(request.getParameter("quantity")))) * price;

				salesTax = subTotal * SALESTAXRATE;
				totalPrice = subTotal + salesTax;

			} catch (SQLException e) 
			{
				System.out.println(e.getMessage());
			} // end catch

			doGet(request, response); // if the user adds, then we're still in data entry so pass it to the doGet()
			// method
		}

		else if (request.getParameter("complete") != null) 
		{ // if the user didn't click add or cancel then the only

			out.println("complete");
			try {

				OracleWrapper.prepareStatement("SELECT * FROM TB_Order_T");
				ResultSet rs = OracleWrapper.queryDB();
				String localDetails = request.getParameter("details");
				Double subtotal = (Double.valueOf(String.valueOf(request.getParameter("subtotal"))));
				Double salesTax = (Double.valueOf(String.valueOf(request.getParameter("salestax"))));
				Double totalPrice = (Double.valueOf(String.valueOf(request.getParameter("totalPrice"))));
				String customerName = request.getParameter("customerName");
				String customerAddress = request.getParameter("address");
				String customerPhone = request.getParameter("Phone");
				OracleWrapper.prepareStatement("INSERT INTO TB_Customer_T (customerID, customerName, customerAddress, customerPhone) VALUES (?,?,?,?)",
						uniqueID,customerName, customerAddress, customerPhone);
				//OracleWrapper.updateDB();

				OracleWrapper.prepareStatement(
						"INSERT INTO TB_Order_T (orderID, details, subtotal, saleTax, totalPrice) VALUES (?,?,?,?,?)",
						uniqueID,localDetails, subtotal, salesTax, totalPrice);
				OracleWrapper.updateDB();

			} catch (SQLException e) 
			{
				System.out.println(e.getMessage());
			} // end catch

			response.sendRedirect("/Receipt?id=" + uniqueID + "&address=" + request.getParameter("address")+"&name=" + 
					request.getParameter("customerName")+"&customerPhone=" + request.getParameter("phone"));

			// this should write the transaction to the database and then redirect to the
			// Receipt 

		} // end else
		out.println("</body></html>");
	} // end doPost
} // end PlacingOrder