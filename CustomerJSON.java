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

public class CustomerJSON  extends HttpServlet 
{
	private static final long serialVersionUID = 7L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		try {
			List<Customer> allCustomers = new ArrayList();
			OracleWrapper.prepareStatement("SELECT * FROM TB_Customer_T");
			ResultSet rs = OracleWrapper.queryDB();
			while (rs.next()) {
				Customer.CustomerBuilder.setCustomerID(Long.valueOf(String.valueOf(rs.getObject(1))));
                Customer.CustomerBuilder.setCustomerName(String.valueOf(rs.getObject(2)));
                Customer.CustomerBuilder.setCustomerAddress(String.valueOf(rs.getObject(3)));
                Customer.CustomerBuilder.setCustomerPhone(String.valueOf(rs.getObject(4)));
                
                allCustomers.add(Customer.CustomerBuilder.build());
			} // end while

			out.println(("{\"Customer\":["));
			for (Customer eachOne: allCustomers) 
			{
				out.print(eachOne.toJSON());
				if (allCustomers.indexOf(eachOne) != allCustomers.size()-1) 
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// this is used for form processing, we'll deal with this later
	} // end doPost
} // end StoreJSON