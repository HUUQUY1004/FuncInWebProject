import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@WebServlet(name = "RegisterController", value = "/register")
public class RegisterController extends HttpServlet {
    private static String DB_URL = "jdbc:mysql://localhost:3306/user";
    private static String USER_NAME = "root";
    private static String PASS_NAME = "";
    static String email, pass, username;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        email = req.getParameter("email");
        pass = req.getParameter("pass");
        username = req.getParameter("username");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER_NAME, PASS_NAME);
            List<Integer> listId = new ArrayList<Integer>();
            String sql = "select  id_user from user";
            PreparedStatement pr = connection.prepareStatement(sql);
            ResultSet r1 = pr.executeQuery();
            while (r1.next()){
                listId.add(r1.getInt("id_user"));
            }
            int maxID = listId.get(listId.size() -1);
            System.out.println(maxID);
            String sqlInsert= "insert into user(id_user, email, name, pass) values (? , ? ,?,? )";
            PreparedStatement pr1 = connection.prepareStatement(sqlInsert);
            pr1.setInt(1, maxID + 1);
            pr1.setString(2,email);
            pr1.setString(3, username);
            pr1.setString(4, pass);
            int r2 = pr1.executeUpdate();
            if(r2 > 0 ){
                System.out.println("Success");
            }
            else{
                System.out.println("fail");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
