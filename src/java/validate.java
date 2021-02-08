/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kareem_Eltemsah
 */
@WebServlet(urlPatterns = {"/validate"})
public class validate extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/banksystem";
            String user = "root";
            String password = "root";
            Connection Con = null;
            Statement Stmt = null;
            ResultSet result = null;
            
            Con = DriverManager.getConnection(url, user, password);
            Stmt = Con.createStatement();
            String customerID = request.getParameter("customerID");
            String customerPass = request.getParameter("password");
            if (customerID == "" && customerPass == "") {
                Stmt.close();
                Con.close();
                result.close();
                response.sendRedirect("index.html");
            }
            
            String line = "select * from customer where CustomerID = " + customerID + " and CustomerPassword = '" + customerPass + "';";
            result = Stmt.executeQuery(line);
            HttpSession session = request.getSession(true);
            
            String redirect = "";
            if (result.next()) {
                session.setAttribute("CustomerID", result.getInt("CustomerID"));
                session.setAttribute("CustomerName", result.getString("CustomerName"));
                redirect = "customerhome.jsp";
            }
            else {
                redirect = "Login.html";
            }
            Stmt.close();
            Con.close();
            result.close();
            
            response.sendRedirect(redirect);
        } catch (Exception ex) {
                ex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
