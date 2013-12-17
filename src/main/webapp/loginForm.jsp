<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>

    <title>Login - Studio Zone Admin</title>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="">
	<meta name="author" content="" />

	<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,800italic,400,600,800" type="text/css">
	<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css" />		
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" type="text/css" />	
	<link rel="stylesheet" href="js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.css" type="text/css" />	
	<link rel="stylesheet" href="css/App.css" type="text/css" />
	<link rel="stylesheet" href="css/Login.css" type="text/css" />
	<link rel="stylesheet" href="css/custom.css" type="text/css" />
	
</head>

<body>

<div id="login-container">

	<div id="login">

		<h3>Welcome to Studio Zone Admin.</h3>

		<h5>Please sign in to get access.</h5>

		<form id="login-form" method="post" action="login" class="form">
			<div>${message}</div>
			<div class="form-group">
				<label for="login-username">Username</label>
				<input type="text" class="form-control" name="username" placeholder="Username">
			</div>

			<div class="form-group">
				<label for="login-password">Password</label>
				<input type="password" class="form-control" name="password" placeholder="Password">
			</div>

			<div class="form-group">

				<button type="submit" id="login-btn" class="btn btn-primary btn-block">Signin &nbsp; 
					<i class="fa fa-play-circle"></i>
				</button>

			</div>
		</form>


		<a href="javascript:;" class="btn btn-default">Forgot Password?</a>

	</div> <!-- /#login -->

	<a href="" id="signup-btn" class="btn btn-lg btn-block">
		Create an Account
	</a>


</div> <!-- /#login-container -->

<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
<script src="js/Login.js"></script>

</body>
</html>
