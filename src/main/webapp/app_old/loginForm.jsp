<%--
  Created by IntelliJ IDEA.
  User: gardiary
  Date: Nov 25, 2010
  Time: 10:55:47 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <title>ACL Demo - Login</title>
      <link href="css/format.css" rel=stylesheet type="text/css">
  </head>
  <body>
    <center>
    <form method="post" action = "login">
        <table border="0" cellpadding="6" cellspacing="0" style="border-collapse: collapse" class="garislrtb" width="219">
              <tr>
                <td width="191" height="20" colspan="2" align="center" class="garislrtb"><b>Restricted Access</b></td>
              </tr>
              <tr>
                <td width="63" height="20" class="kuningMudaTabel" >Username</td>
                <td width="128" height="20"><input type="text" name="username" size="20" class="kotakteks"></td>
              </tr>
              <tr>
                <td width="63" height="30" class="kuningMudaTabel" >Password</td>
                <td width="128" height="30"><input type="password" name="password" size="20" class="kotakteks"></td>
              </tr>
              <tr>
                <td colspan="2" class="garislrtb" width="203">
                    <p align="center"><input type="submit" value="o Login o" class="kotaktombol"></p>
                </td>
              </tr>
        </table>
        <div align="center"><b>${message}</b></div>
    </form>
    </center>
  </body>
</html>