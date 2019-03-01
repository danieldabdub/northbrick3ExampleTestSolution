import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class CategoryUpdate extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        String idStr = req.getParameter("categoryId");
        CategoryData category = new CategoryData(
                    Integer.parseInt(idStr),
                    req.getParameter("categoryName"),
                    req.getParameter("description")
                );
        int n = CategoryData.updateCategory(connection, category);
		
		res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        String categoryId = req.getParameter("id");

        toClient.println(Utils.header("Categories updated"));
		CategoryList.printCategoryTable(toClient, connection);
        toClient.println(Utils.footer("Categories"));
        toClient.close();

        //res.sendRedirect("CategoryList");
        //res.sendRedirect("CategoryEdit?id=" + idStr + "&a=" + Math.random());
    }
}