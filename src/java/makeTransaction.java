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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kareem_Eltemsah
 */
@WebServlet(urlPatterns = {"/makeTransaction"})
public class makeTransaction extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/banksystem";
            String user = "root";
            String password = "root";
            Connection Con = null;
            Statement Stmt = null;
            Con = DriverManager.getConnection(url, user, password);
            Stmt = Con.createStatement();
            HttpSession session = request.getSession(true);
            String BTFromAccount = session.getAttribute("BankAccountID").toString();
            float balance = Float.parseFloat(session.getAttribute("BACurrentBalance").toString());
            String BTToAccount = request.getParameter("BTToAccount");
            float amount = Float.parseFloat(request.getParameter("amount"));
            String output = "";
            if (BTFromAccount.equals(BTToAccount)) {
                output = "you can't make transactions to yourself";
            } else if (amount > balance) {
                output = "you don't have enough money to make this transaction";
            } else {
                String line = "select * from bankaccount where BankAccountID = " + BTToAccount + ";";
                ResultSet rs = Stmt.executeQuery(line);
                if (rs.next()) {
                    long millis = System.currentTimeMillis();
                    Timestamp dateTimeObj = new Timestamp(millis);
                    String date = dateTimeObj.toString().substring(0, dateTimeObj.toString().indexOf("."));//eliminating milli seconds
                    //insert transaction
                    line = "insert into banktransaction(BTCreationDate,BTAmount,BTFromAccount,BTToAccount) "
                            + "values ('" + date + "'," + amount + "," + BTFromAccount + "," + BTToAccount + ");";
                    Stmt.executeUpdate(line);
                    //update sender account
                    line = "update bankaccount set BACurrentBalance = BACurrentBalance - " + amount
                            + " where BankAccountID = " + BTFromAccount + ";";
                    Stmt.executeUpdate(line);
                    //update receiver account
                    line = "update bankaccount set BACurrentBalance = BACurrentBalance + " + amount
                            + " where BankAccountID = " + BTToAccount + ";";
                    Stmt.executeUpdate(line);
                    
                    output = "transaction has been successfully made";
                } else {
                    output = "there's no bank account with ID " + BTToAccount;
                }
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
        } catch (SQLException ex) {
            Logger.getLogger(makeTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(makeTransaction.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(makeTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(makeTransaction.class.getName()).log(Level.SEVERE, null, ex);
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
