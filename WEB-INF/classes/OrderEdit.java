import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class OrderEdit extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println(Utils.header("Order Form"));
        String idStr = req.getParameter("id");
        int id = Integer.parseInt(idStr);
        printOrder(toClient, connection, id);
        toClient.println(Utils.footer("Order Form"));
        toClient.close();
    }
    
    public static void printOrder(PrintWriter toClient, Connection connection, int id) {
        OrderData order = OrderData.getOrderHeader(connection, id);
        
		toClient.println(OrderView.orderHeader(order));
        
		Vector<OrderDetailData> orderDetail = OrderData.getOrderDetail(connection, id);
       

		toClient.println("<form action='OrderDetailInsert' method='GET'>");
	   
        toClient.println("<input name='id' type='hidden' value='" + id + "'></input>");
        toClient.println(OrderView.orderDetail(orderDetail));
       
	   toClient.println("</form>");
        
		toClient.println("<script src=orders.js></script>");
    }
    
}