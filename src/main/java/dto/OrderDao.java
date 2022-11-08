package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Order;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDao {
    private Connection con;
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public OrderDao(Connection connection) {
        this.con = connection;
    }

    public boolean insertOrder(Order model) {
        boolean result = false;

        try {

            query = "insert into orders(p_id, u_id, o_quantity, o_date) values(?,?,?,?)";
            preparedStatement = this.con.prepareStatement(query);
            preparedStatement.setInt(1,model.getId());
            preparedStatement.setInt(2,model.getUid());
            preparedStatement.setInt(3,model.getQuantity());
            preparedStatement.setString(4,model.getDate());
            preparedStatement.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
