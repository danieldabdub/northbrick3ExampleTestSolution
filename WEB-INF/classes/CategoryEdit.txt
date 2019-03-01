import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class CategoryEdit extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println(Utils.header("Category Form"));
        String idStr = req.getParameter("id");
        CategoryData category = CategoryData.getCategory(connection, idStr);
        toClient.println("<form action='CategoryUpdate' method='GET'>");
        toClient.println("<input name='categoryId' type='hidden' value='" + category.categoryId + "'></input>");
        toClient.println("<table border='1'>");
        toClient.println("<tr><td>Id</td>");
        toClient.println("<td>" + category.categoryId + "</td></tr>");
        toClient.println("<tr><td>Name</td>");
        String name = category.categoryName;
        toClient.println("<td><input name='categoryName' value='" + name + "'></td></tr>");
        toClient.println("<tr><td>Description</td>");
        toClient.println("<td><input name='description' value='" + category.description + "'></td></tr>");
        toClient.println("</table>");
        toClient.println("<input type='submit'>");
        toClient.println("</form>");
        toClient.println(Utils.footer("Category Form"));
        toClient.close();
    }
}