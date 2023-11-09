import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Search", value = "/search")
public class Search extends HttpServlet {
    private static String DB_URL = "jdbc:mysql://localhost:3306/user";
    private static String USER_NAME = "root";
    private static String PASS_NAME = "";
    static String  name;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        name = req.getParameter("name");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER_NAME, PASS_NAME);
            List<User> list = new ArrayList<User>();
            String sql = "select * from user where name LIKE ?";
            PreparedStatement pr = connection.prepareStatement(sql);
            pr.setString(1, "%" + name + "%");
            ResultSet r1 = pr.executeQuery();
            User user = new User();
            while(r1.next()){
                user.setEmail(r1.getString("email"));
                user.setName(r1.getString("name"));
                user.setPassword(r1.getString("pass"));
                list.add(user);
            }
            System.out.println(list.toString());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
