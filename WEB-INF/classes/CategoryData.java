import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryData {
    int    categoryId;
    String categoryName;
    String    description;
    
    CategoryData (int categoryId, String categoryName, String description) {
        this.categoryId    = categoryId;
        this.categoryName  = categoryName;
        this.description = description;
    }
    public static Vector<CategoryData> getCategoryList(Connection connection) {
        Vector<CategoryData> vec = new Vector<CategoryData>();
        String sql = "Select CategoryId, CategoryName, Description FROM Categories";
        System.out.println("getCategoryList: " + sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                CategoryData category = new CategoryData(
                    Integer.parseInt(result.getString("CategoryId")),
                    result.getString("CategoryName"),
                    result.getString("Description")
                );
                vec.addElement(category);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getCategoryList: " + sql + " Exception: " + e);
        }
        return vec;
    }
    
    public static CategoryData getCategory(Connection connection, String id) {
        String sql = "Select CategoryId, CategoryName, Description FROM Categories";
        sql += " WHERE CategoryId=?";
        System.out.println("getCategory: " + sql);
        CategoryData category = null;;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet result = pstmt.executeQuery();
            if(result.next()) {
                category = new CategoryData(
                    Integer.parseInt(result.getString("CategoryId")),
                    result.getString("CategoryName"),
                    result.getString("Description")
                );
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getCategory: " + sql + " Exception: " + e);
        }
        return category;
    }
    
    public static int updateCategory(Connection connection, CategoryData category) {
        String sql ="UPDATE Categories "
            + "SET CategoryName = ?, Description = ?"
            + " WHERE CategoryId = ?";
        System.out.println("updateCategory: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setString(1,category.categoryName);
            stmtUpdate.setString(2,category.description);
            stmtUpdate.setInt(3,category.categoryId);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateCategory: " + sql + " Exception: " + e);
        }
        return n;
    }
}
