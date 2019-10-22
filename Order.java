package javaProjectV2;

public class Order 
{
	public static class OrderBuilder 
	{

		private static long builderOrderID;
		private static String builderDetails;
		private static double builderSubtotal;
		private static double builderSalesTax;
		private static double builderTotalPrice;

		public static Order build() 
		{
			Order onlineOrder = new Order(builderOrderID, builderDetails, builderSubtotal, builderSalesTax,
					builderTotalPrice);
			return onlineOrder;
		}// end build

		public static void setOrderID(long incOrderID) 
		{
			builderOrderID = incOrderID;
		}// end setOrderID

		public static void setDetails(String incDetails) 
		{
			builderDetails = incDetails;
		}// end setDetails

		public static void setSubtotal(double incSubtotal) 
		{
			builderSubtotal = incSubtotal;
		}// end setSubtotal

		public static void setSalesTax(double incSalesTax) 
		{
			builderSalesTax = incSalesTax;
		}// end setSalesTax

		public static void setTotalPrice(double incTotalPrice) 
		{
			builderTotalPrice = incTotalPrice;
		}// end setTotalPrice

	}// end Orderbuilder

	private final long orderID;
	private final String details;
	private final double subtotal;
	private final double salesTax;
	private final double totalPrice;

	Order(long orderID, String details, double subtotal, double salesTax, double totalPrice) {
		this.orderID = orderID;
		this.details = details;
		this.subtotal = subtotal;
		this.salesTax = salesTax;
		this.totalPrice = totalPrice;

	}// end ctor

	public String toJSON() {
		return ("{\"orderID\":\"" + this.orderID + "\", " + "\"details\":\"" + this.details + "\", " + "\"subtotal\":\""
				+ this.subtotal + "\", " + "\"salesTax\":\"" + this.salesTax + "\", " + "\"totalPrice\":\""
				+ this.totalPrice + "\"}");

	}// end toJSON

	public String toHTML() {
		return "<tr><td>orderID: </td><td>" + this.orderID + "</td></tr>" + "<tr><td>details: </td><td>" + this.details
				+ "</td></tr>" + "<tr><td>subtotal: </td><td>" + this.subtotal + "</td></tr>"
				+ "<tr><td>saleTax: </td><td>" + this.salesTax + "</td></tr>" + "<tr><td>totalPrice: </td><td>"
				+ this.totalPrice + "</td></tr>";

	}// end toHTML
}// end Order