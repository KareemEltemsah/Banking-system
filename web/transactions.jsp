<%-- 
    Document   : transactions
    Created on : Dec 27, 2020, 10:00:44 PM
    Author     : Kareem_Eltemsah
--%>

<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transactions</title>
    </head>
    <body>
        <% String BankAccountID = session.getAttribute("BankAccountID").toString();%>
        <div align="center">
            <h2>Account ID : <%= BankAccountID%></h2>
            <br><br>
            <form action="makeTransaction.jsp">
                <input type="submit" value='make new transaction'>
            </form>
            <br><br>
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
                String line = "select * from banktransaction join bankaccount "
                        + "on bankaccount.BankAccountID = banktransaction.BTFromAccount "
                        + "or bankaccount.BankAccountID = banktransaction.BTToAccount "
                        + "where bankaccount.BankAccountID = " + Integer.parseInt(BankAccountID) + ";";
                result = Stmt.executeQuery(line);
                int transactionsN = 0;
            %>
            <table cellpadding="5px" border="2px">
                <%while (result.next()) {
                        if (transactionsN == 0) {%>
                <tr>
                    <th>Transaction ID</th>
                    <th>Creation Date</th>
                    <th>Amount</th>
                    <th>From Account</th>
                    <th>To Account</th>
                    <th>Status</th>
                </tr>
                <%}%>
                <tr>
                    <td align="center"><%= result.getInt("BankTransactionID")%></td>
                    <td align="center"><%= result.getTimestamp("BTCreationDate")%></td>
                    <td align="center"><%= result.getFloat("BTAmount") + "$"%></td>
                    <td align="center"><%= result.getInt("BTFromAccount")%></td>
                    <td align="center"><%= result.getInt("BTToAccount")%></td>
                    <td align="center">
                        <%if (Integer.parseInt(BankAccountID) == result.getInt("BTFromAccount")) {%>Sender <%}%>
                        <%if (Integer.parseInt(BankAccountID) == result.getInt("BTToAccount")) {%>Receiver <%}%>
                    </td>
                    <td>
                        <form action="cancelTransaction">
                            <input name="BTID" type="hidden" value=<%=result.getInt("BankTransactionID")%>>
                            <input type="submit" value="cancel">
                        </form>
                    </td>
                </tr>
                <%transactionsN++;
                    }%>
                <tr> <td colspan="6" align="center"> <br> Transactions Count : <%= transactionsN%></td> </tr>
            </table>
            <br>
            <form action="customerhome.jsp">
                <input type="submit" value='Back to home page'>
            </form>
        </div> 
    </body>
</html>
