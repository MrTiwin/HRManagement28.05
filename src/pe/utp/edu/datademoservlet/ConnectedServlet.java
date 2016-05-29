package pe.utp.edu.datademoservlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by GrupoUTP on 28/05/2016.
 */
@WebServlet(name = "ConnectedServlet",urlPatterns = "/connected")

public class ConnectedServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request,response);

    }

    private void processRequest (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        PrintWriter out = null;
        out = response.getWriter();
        out.println("<p>Cantidad de Empleados</p>");

        // JDBC DATASOURCE CONECCTION TEST

        try{
            InitialContext ctx= new InitialContext();
            //out.println("BEFORE LOOKUP");
            DataSource ds= (DataSource) ctx.lookup("jdbc/MySQLDataSource");
            //out.println("BEFORE CONNECTION");
            Connection con= ds.getConnection();
            //out.println("BEFORE STATEMENT");
            Statement stmt= con.createStatement();
            //out.println("BEFORE QUERY");
            ResultSet rs = stmt.executeQuery("Select count(*) from employees");
            //out.println("AFTER QUERY");

            if (rs != null) {
                out.println("Total: ");
                while (rs.next()) {
                    out.println(rs.getString("Count(*)"));
                }
            }
            else{
                out.println("NO HAY EMPLEADOS");
            }

        }
        catch(NamingException e){
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }


    }
}

