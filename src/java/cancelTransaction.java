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
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kareem_Eltemsah
 */
@WebServlet(urlPatterns = {"/cancelTransaction"})
public class cancelTransaction extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String BTID = request.getParameter("BTID");
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/banksystem";
            String user = "root";
            String password = "root";
            Connection Con = null;
            Statement Stmt = null;
            Con = DriverManager.getConnection(url, user, password);
            Stmt = Con.createStatement();
            String line = "select * from banktransaction where BankTransactionID = " + BTID + ";";
            ResultSet rs = Stmt.executeQuery(line);
            if (rs.next()) {
                long millis = System.currentTimeMillis();
                Timestamp currentDate = new Timestamp(millis);
                Timestamp creationDate = rs.getTimestamp("BTCreationDate");
                long diff = currentDate.getTime() - creationDate.getTime();
                String output = "";
                if (diff < 1 * 24 * 60 * 60 * 1000) {
                    float amount = rs.getFloat("BTAmount");
                    int sender = rs.getInt("BTFromAccount");
                    int receiver = rs.getInt("BTToAccount");
                    //return amount to sender
                    line = "update bankaccount set BACurrentBalance = BACurrentBalance + " + amount
                            + " where BankAccountID = " + sender + ";";
                    Stmt.executeUpdate(line);
                    //take amount from receiver
                    line = "update bankaccount set BACurrentBalance = BACurrentBalance - " + amount
                            + " where BankAccountID = " + receiver + ";";
                    Stmt.executeUpdate(line);
                    //delete transaction
                    line = "delete from banktransaction where BankTransactionID = " + BTID;
                    Stmt.executeUpdate(line);
                    
                    output = "transaction has been successfully canceled";
                }
                else {
                    output = "you can't cancel a transaction after 1 day since creation date";
                }
                Con.close();
                Stmt.close();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Make Transaction</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div align='center'>"
                        + "<br>"
                        + "<h3>" + output + "</h3>"
                        + "<br>"
                        + "<a href='transactions.jsp'>Back to transactions</a>"
                        + "<div>");
                out.println("</body>");
                out.println("</html>");
            }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(cancelTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(cancelTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(cancelTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(cancelTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
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
