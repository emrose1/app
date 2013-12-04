<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
      <title>Login</title>
      <link href="css/format.css" rel=stylesheet type="text/css">
  </head>
  <body>

    <form method="post" action="login">
        <table width="219">
              <tr>
                <td width="191" height="20" colspan="2" align="center" class="garislrtb"><b>Restricted Access</b></td>
              </tr>
              <tr>
                <td width="63" height="20" class="kuningMudaTabel">Username</td>
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

  </body>
</html>