package javaProjectV2;

public class Customer 
{
	public static class CustomerBuilder
	{

		private static long builderCustomerID;
		private static String builderCustomerName;
		private static String builderCustomerAddress;
		private static String builderCustomerPhone;

		public static Customer build()
		{
			Customer OnlineCustomer = new Customer(builderCustomerID, builderCustomerName, builderCustomerAddress, builderCustomerPhone);
			  return OnlineCustomer;
		}//end build
		public static void setCustomerID(long incCustomerID) 
		{
			builderCustomerID = incCustomerID;
		}// end setCustomerID
		public static void setCustomerName(String incCustomerName) 
		{
			builderCustomerName = incCustomerName;
		}// end setCustomerName
		public static void setCustomerAddress(String incCustomerAddress) 
		{
			builderCustomerAddress = incCustomerAddress;
		}// end setCustomerAddress
		public static void setCustomerPhone(String incCustomerPhone) 
		{
			builderCustomerPhone = incCustomerPhone;
		}// end setCustomerPhone

	}// end CustomerBuilder

	private final long customerID;
	private final String customerName;
	private final String customerAddress;
	private final String customerPhone;

	 private Customer(long customerID, String customerName, String customerAddress, String customerPhone) 
	 {
		this.customerID = customerID;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerPhone = customerPhone;

	}//end ctor
	public String toJSON() 
	{
		return ("{\"customerID\":\"" + this.customerID + "\", " +
				"\"customerName\":\"" + this.customerName + "\", " +
				"\"customerAddress\":\"" + this.customerAddress + "\", " +
				"\"customerPhone\":\"" + this.customerPhone + "\"}");

	}// end toJSON

	public String toHTML() 
	{
		return "<tr><td>customerID: </td><td>" + this.customerID + "</td></tr>"
				+ "<tr><td>customerName: </td><td>" + this.customerName + "</td></tr>"
				+ "<tr><td>customerAddress: </td><td>" + this.customerAddress + "</td></tr>"
				+ "<tr><td>customerPhone: </td><td>" + this.customerPhone + "</td></tr>";

	}// end toHTML
}//end Customer
