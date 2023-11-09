import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {
    private static  String DB_URL = "jdbc:mysql://localhost:3306/user";
    private  static String USER_NAME = "root";
    private  static  String PASS_NAME = "";
    static String email, pass;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        email = req.getParameter("email");
        pass = req.getParameter("pass");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL,USER_NAME,PASS_NAME);
            String sql = "select email,name, pass from user WHERE email=? and pass=? ";
            PreparedStatement pr = connection.prepareStatement(sql);
            pr.setString(1,email);
            pr.setString(2, pass);
            ResultSet r1 = pr.executeQuery();
            User user = new User();
            if(r1.next()){
                HttpSession session = req.getSession();
                user.setEmail(r1.getString("email"));
                user.setName(r1.getString("name"));
                user.setPassword("pass");

                System.out.println(user.getEmail());
//                System.out.println(r1.getString("name"));
                session.setAttribute("user", user);
                resp.sendRedirect("index.jsp");
            }
            else{
                req.getRequestDispatcher("login.jsp").forward(req,resp);
            }
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
