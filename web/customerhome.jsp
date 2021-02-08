<%-- 
    Document   : customerhome
    Created on : Dec 25, 2020, 4:29:19 AM
    Author     : Kareem_Eltemsah
--%>
<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Home</title>
    </head>
    <body>
        <div align='center'>
            <% String CustomerName = session.getAttribute("CustomerName").toString();%>
            <h2>Welcome <%= CustomerName%></h2>
            <br><br><br>
            <%
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/banksystem";
                String user = "root";
                String password = "root";
                Connection Con = null;
                Statement Stmt = null;
                ResultSet result = null;

                Con = DriverManager.getConnection(url, user, password);
                Stmt = Con.createStatement();
                String CustomerID = session.getAttribute("CustomerID").toString();
                String line = "select * from bankaccount join customer "
                        + "where bankaccount.CustomerID = customer.CustomerID "
                        + "and customer.CustomerID = " + Integer.parseInt(CustomerID) + ";";
                result = Stmt.executeQuery(line);

                if (result.next()) {
                    session.setAttribute("BankAccountID", result.getInt("BankAccountID"));
                    session.setAttribute("BACurrentBalance", result.getFloat("BACurrentBalance"));
                    String BankAccountID = session.getAttribute("BankAccountID").toString();
                    String BACurrentBalance = session.getAttribute("BACurrentBalance").toString();%>
            <table>
                <tr>
                    <td>Account ID</td>
                    <td><%= BankAccountID%></td>
                </tr>
                <tr>
                    <td>Current Balance</td>
                    <td><%= BACurrentBalance + "$"%></td>
                </tr>
                <tr>
                    <td colspan="2" align='center'>
                        <form action="transactions.jsp">
                            <br><input type="submit" value="View Transactions">
                        </form>
                    </td>
                </tr>
            </table>
            <%} else {%>
            <p>you don't have bank account yet, get one now!</p>
            <form action="addaccount">
                <input type="submit" value='Add Account'>
            </form>
            <%}%>
            <br>
            <form action="Login.html">
                <input type="submit" value='Logout'>
            </form>
        </div>
    </body>
</html>
