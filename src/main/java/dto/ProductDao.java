package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Cart;
import model.Product;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDao {

    private Connection con;
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ProductDao(Connection connection) {
        this.con = connection;
    }

    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();
        try {
            query = "select * from products";
            preparedStatement = this.con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setCategory(resultSet.getString("category"));
                product.setPrice(resultSet.getDouble("price"));
                product.setImage(resultSet.getString("image"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Cart> getCartProducts(ArrayList<Cart> cartList) {
        List<Cart> products = new ArrayList<>();

        try {
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    query = "select * from products where id=?";
                    preparedStatement = this.con.prepareStatement(query);
                    preparedStatement.setInt(1, item.getId());
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Cart row = new Cart();
                        row.setId(resultSet.getInt("id"));
                        row.setName(resultSet.getString("name"));
                        row.setCategory(resultSet.getString("category"));
                        row.setPrice(resultSet.getDouble("price") * item.getQuantity());
                        row.setQuantity(item.getQuantity());
                        products.add(row);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    public Double getTotalCartPrice(ArrayList<Cart> cartList) {
        double sum = 0;
        try {
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    query = "select price from products where id=?";
                    preparedStatement = this.con.prepareStatement(query);
                    preparedStatement.setInt(1, item.getId());
                    resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        sum += resultSet.getDouble("price") * item.getQuantity();
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }
}
