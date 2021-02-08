<%-- 
    Document   : makeTransaction
    Created on : Dec 27, 2020, 11:29:11 PM
    Author     : Kareem_Eltemsah
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Make Transaction</title>
    </head>
    <body>
        <% String BankAccountID = session.getAttribute("BankAccountID").toString(); %>
        <div align="center">
            <br><br>
            <p>enter the account ID you want to transfer to
                <br> and the amount of money</p>
            <br>
            <form action="makeTransaction">
                <table>
                    <tr>
                        <td>Account ID</td>
                        <td><input type="number" name="BTToAccount"></td>
                    </tr>
                    <tr>
                        <td>Amount</td>
                        <td><input type="number" name="amount"></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <br><input type="submit" value="Transfer">
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>
