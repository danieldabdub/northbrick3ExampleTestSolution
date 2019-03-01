import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductData { // not a servlet but a class, defining some fields and methods
    String    productId;
    String productName;
    int    supplierId;
    String companyName;
    float    unitPrice;
	int UnitsOnOrder;  //change this and add additional atrribute
    
    ProductData (String productId, String productName, int supplierId, String companyName, float unitPrice) {
        this.productId    = productId;
        this.productName  = productName;
        this.supplierId   = supplierId;
        this.companyName = companyName;
        this.unitPrice = unitPrice;
		
    }
	
	   ProductData (String productId, String productName, int supplierId, String companyName, float unitPrice, int UnitsOnOrder) {
        this.productId    = productId;
        this.productName  = productName;
        this.supplierId   = supplierId;
        this.companyName = companyName;
        this.unitPrice = unitPrice;
		this.UnitsOnOrder = UnitsOnOrder;
    }
	
    public static Vector<ProductData> getProductList(Connection connection) {
        Vector<ProductData> vec = new Vector<ProductData>();
        String sql = "Select ProductId, ProductName, Products.SupplierId as SupplierId, CompanyName, UnitPrice, UnitsOnOrder FROM Products, Suppliers";
        sql += " WHERE Products.SupplierId = Suppliers.SupplierId";
        System.out.println("getProductList: " + sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                ProductData product = new ProductData(
                    result.getString("ProductId"),
                    result.getString("ProductName"),
                    Integer.parseInt(result.getString("SupplierId")),
                    result.getString("CompanyName"),
                    Float.parseFloat(result.getString("UnitPrice"))
					//Integer.parseInt(result.getString("UnitsOnOrder"))
                );
                vec.addElement(product);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getProductList: " + sql + " Exception: " + e);
        }
        return vec;
    }
    public static Vector<ProductData> getCategoryProductList(Connection connection, String id) {
        Vector<ProductData> vec = new Vector<ProductData>();
        String sql = "Select ProductId, ProductName, Products.SupplierId as SupplierId, CompanyName, UnitPrice, UnitsOnOrder FROM Products, Suppliers";
        sql += " WHERE Products.SupplierId = Suppliers.SupplierId AND CategoryID=?";
        System.out.println("getProductList: " + sql);
        try {
            PreparedStatement pstmt=connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(id)); //first parameter is an integer
            ResultSet result = pstmt.executeQuery();  // execute query
            while(result.next()) {    //get information
                ProductData product = new ProductData(
                    result.getString("ProductId"),
                    result.getString("ProductName"),
                    Integer.parseInt(result.getString("SupplierId")),
                    result.getString("CompanyName"),
                    Float.parseFloat(result.getString("UnitPrice"))
					//Integer.parseInt(result.getString("UnitsOnOrder"))
                );
                vec.addElement(product);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getProductList: " + sql + " Exception: " + e);
        }
        return vec;
    }
    public static ProductData getProduct(Connection connection, String id) {
        String sql = "Select ProductId, ProductName, SupplierId, UnitPrice, UnitsOnOrder FROM Products"; //controller - select statement  change this sentence
        sql += " WHERE ProductId=?";
        System.out.println("getProduct: " + sql);
		System.out.println("hello hi: ");
        ProductData product = null;;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql); //execute
            pstmt.setString(1, id);
            ResultSet result = pstmt.executeQuery(); //execute
            if(result.next()) {    //use next to go to the next row of this table
                product = new ProductData(      //constructor is ProductData
                    result.getString("ProductId"),
                    result.getString("ProductName"),
                    Integer.parseInt(result.getString("SupplierId")),
                    null,
                    Float.parseFloat(result.getString("UnitPrice")),
					Integer.parseInt(result.getString("UnitsOnOrder"))
                 
                );
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getProduct: " + sql + " Exception: " + e);
        }
        return product;
    }
    public static int updateProduct(Connection connection, ProductData product) {
        String sql ="UPDATE Products "
            + "SET ProductName = ?, SupplierId = ?, UnitPrice = ?, UnitsOnOrder = ?"
            + " WHERE ProductId = ?";
        System.out.println("updateProduct: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setString(1,product.productName);
            stmtUpdate.setInt(2,product.supplierId);
            stmtUpdate.setFloat(3,product.unitPrice);
          
			stmtUpdate.setInt(4,product.UnitsOnOrder);
			stmtUpdate.setString(5,product.productId);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateProduct: " + sql + " Exception: " + e);
        }
        return n;
    }
}
