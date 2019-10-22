package javaProjectV2;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uca.mis3339.OracleWrapper;

public class StoreJSON  extends HttpServlet 
{
	private static final long serialVersionUID = 7L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		try {
			List<Order> allOrders = new ArrayList();
			OracleWrapper.prepareStatement("SELECT * FROM TB_Order_T");
			ResultSet rs = OracleWrapper.queryDB();
			while (rs.next()) {
				Order.OrderBuilder.setOrderID(Long.valueOf(String.valueOf(rs.getObject(1))));
                Order.OrderBuilder.setDetails(String.valueOf(rs.getObject(2)));
                Order.OrderBuilder.setSubtotal(Double.valueOf(String.valueOf(rs.getObject(3))));
                Order.OrderBuilder.setSalesTax(Double.valueOf(String.valueOf(rs.getObject(4))));
                Order.OrderBuilder.setTotalPrice(Double.valueOf(String.valueOf(rs.getObject(5))));
				allOrders.add(Order.OrderBuilder.build());
			} // end while

			out.println(("{\"Orders\":["));
			for (Order eachOne: allOrders) 
			{
				out.print(eachOne.toJSON());
				if (allOrders.indexOf(eachOne) != allOrders.size()-1) 
				{ // test if this is the last record
					out.print(","); // add a comma if not the last record
					out.println(" ");
				} // end if
			} // end for
			out.println("]}");
		} catch (SQLException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		} // end catch

	} // end doGet

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// this is used for form processing, we'll deal with this later
	} // end doPost
} // end StoreJSON