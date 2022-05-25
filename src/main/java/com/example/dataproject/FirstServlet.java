package com.example.dataproject;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*DataSource datasource;

        try {
            InitialContext ctx = new InitialContext();
            datasource = (DataSource) ctx.lookup( "java:/comp/env/jdbc/postgres" );
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection con = datasource.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Country");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                request.setAttribute("id", rs.getInt("country_id"));
                request.setAttribute("name", rs.getString("name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
*/

        /*response.setContentType("text/html");


        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + "first servlet" + "</h1>");
        out.println("</body></html>");*/
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
