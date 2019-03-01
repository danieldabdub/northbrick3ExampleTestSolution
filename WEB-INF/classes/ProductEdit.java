import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class ProductEdit extends HttpServlet { // distinguish another product
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {  // printing out the table in html
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println(Utils.header("Product Form"));  // header 
        toClient.println("<form action='ProductUpdate' method='GET'>");
        toClient.println("<table border='1'>");
        String idStr = req.getParameter("id");
        ProductData product = ProductData.getProduct(connection, idStr);  //varaible created here -> select sentence -> go to product data
        toClient.println("<tr><td>Id</td>");
        toClient.println("<td><input name='productId' value='" + product.productId + "'></td></tr>");
        toClient.println("<tr><td>Name</td>");
        String name = product.productName;
			
		
        System.out.println("Name: " + name);
        name = name.replace("'","&#39;");
        System.out.println("Name: " + name);
        toClient.println("<td><input name='productName' value='" + name + "'></td></tr>");
        toClient.println("<tr><td>Supplier</td>");
		
        toClient.println("<td><input name='supplierId' value='" + product.supplierId + "'></td>");
        toClient.println("<tr><td>Price</td>");    
		toClient.println("<td><input name='unitPrice' value='" + product.unitPrice + "'></td>");
		
		// add the new row with the UnitsOrder field
		toClient.println("<tr><td>UnitsOnOrder</td>");
		toClient.println("<td><input name='UnitsOnOrder' value='" + product.UnitsOnOrder + "'></td>");
        
		
        toClient.println("</tr>");
		
        toClient.println("<tr><td>Image</td>");
        toClient.println("<td><img src='http://northbrick1.appspot.com/images/" + product.productId + ".png'></td>");
		
        toClient.println("</tr>");
        toClient.println("</table>");
        toClient.println("<input type='submit'>");
        toClient.println("</form>");
        toClient.println(Utils.footer("Product Form"));
        toClient.close();
    }
}