import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class OrderDetailInsert extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        String idStr = req.getParameter("categoryId");
        int orderId = Integer.parseInt(req.getParameter("id"));
        OrderDetailData orderDetailData = new OrderDetailData(
                    orderId,
                    req.getParameter("productId"),
                    req.getParameter("productName"),
                    Float.parseFloat(req.getParameter("unitPrice")),
                    Float.parseFloat(req.getParameter("quantity")),
                    Float.parseFloat(req.getParameter("discount"))
        );
        int n = OrderData.insertOrderDetail(connection, orderDetailData);

        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        toClient.println(Utils.header("Order updated"));
        OrderEdit.printOrder(toClient, connection, orderId);
        toClient.println(Utils.footer("Order updated"));
        toClient.close();
    }
}