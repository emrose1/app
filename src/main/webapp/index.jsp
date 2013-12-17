<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html ng-app="bookingsApp">
<head>
    <title>Bookings</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<!-- Optional theme -->
	<link rel="stylesheet" href="/css/font-awesome.min.css" type="text/css" />			
	<link rel="stylesheet" href="/css/App.css" type="text/css" />
	<link rel="stylesheet" href="/css/custom.css" type="text/css" />
	<link rel="stylesheet" href="css/bootstrap-timepicker.css">
	<link rel="stylesheet" href="css/bootstrap-datepicker.css">
	<base href="/" />
</head>
<body>


<div id="wrapper">

	<header id="header">

		<h1 id="site-logo">
			<a href="/">
				<img src="/images/logo.png" alt="Site Logo" />
			</a>
		</h1>	

		<a href="javascript:;" data-toggle="collapse" data-target=".top-bar-collapse" id="top-bar-toggle" class="navbar-toggle collapsed">
			<i class="fa fa-cog"></i>
		</a>

		<a href="javascript:;" data-toggle="collapse" data-target=".sidebar-collapse" id="sidebar-toggle" class="navbar-toggle collapsed">
			<i class="fa fa-reorder"></i>
		</a>

	</header> <!-- header -->
	
	<nav id="top-bar" class="collapse top-bar-collapse">

		<ul class="nav navbar-nav pull-left">
			<li class="">
				<a href="/">
					<i class="fa fa-home"></i> 
					Home
				</a>
			</li>

			<li class="dropdown">
		    	<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
		        	Dropdown <span class="caret"></span>
		    	</a>

		    	<ul class="dropdown-menu" role="menu">
			        <li><a href="javascript:;"><i class="fa fa-user"></i>&nbsp;&nbsp;Example #1</a></li>
			        <li><a href="javascript:;"><i class="fa fa-calendar"></i>&nbsp;&nbsp;Example #2</a></li>
			        <li class="divider"></li>
			        <li><a href="javascript:;"><i class="fa fa-tasks"></i>&nbsp;&nbsp;Example #3</a></li>
		    	</ul>
		    </li>
		    
		</ul>

		<ul class="nav navbar-nav pull-right">
			<li class="dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown" href="">
					<i class="fa fa-user"></i>
		        	Rod Howard 
		        	<span class="caret"></span>
		    	</a>

		    	<ul class="dropdown-menu" role="menu">
			        <li>
			        	<a href="./page-profile.html">
			        		<i class="fa fa-user"></i> 
			        		&nbsp;&nbsp;My Profile
			        	</a>
			        </li>
			        <li>
			        	<a href="./page-calendar.html">
			        		<i class="fa fa-calendar"></i> 
			        		&nbsp;&nbsp;My Calendar
			        	</a>
			        </li>
			        <li>
			        	<a href="./page-settings.html">
			        		<i class="fa fa-cogs"></i> 
			        		&nbsp;&nbsp;Settings
			        	</a>
			        </li>
			        <li class="divider"></li>
			        <li>
			        	<a href="./page-login.html">
			        		<i class="fa fa-sign-out"></i> 
			        		&nbsp;&nbsp;Logout
			        	</a>
			        </li>
		    	</ul>
		    </li>
		</ul>

	</nav> 
	
	<div id="sidebar-wrapper">
		<nav id="sidebar">		
			<ul id="main-nav" class="open-active">			
				<li class="">				
					<a href="./index.html">
						<i class="fa fa-dashboard"></i>
						Dashboard
					</a>				
				</li>
				<li class="dropdown">
					<a href="">
						<i class="fa fa-file-text"></i>
						Example Pages
						<span class="caret"></span>
					</a>				
					<ul class="sub-nav">
						<li>
							<a href="./page-profile.html">
								<i class="fa fa-user"></i> 
								Profile
							</a>
						</li>
						<li>
							<a href="./page-invoice.html">
								<i class="fa fa-money"></i> 
								Invoice
							</a>
						</li>
					</ul>						
				</li>	
				
				<li class="dropdown">
					<a href="">
						<i class="fa fa-tasks"></i>
						Form Elements
						<span class="caret"></span>
					</a>
					
					<ul class="sub-nav">
						<li>
							<a href="./form-regular.html">
								<i class="fa fa-location-arrow"></i>
								Regular Elements
							</a>
						</li>
						<li>
							<a href="./form-extended.html">
								<i class="fa fa-magic"></i>
								Extended Elements
							</a>
						</li>				
					</ul>				
				</li>
				
				<li class="dropdown">
					<a href="">
						<i class="fa fa-desktop"></i>
						UI Features
						<span class="caret"></span>
					</a>	
					<ul class="sub-nav">
						<li>
							<a href="./ui-buttons.html">
								<i class="fa fa-hand-o-up"></i>
								Buttons
							</a>
						</li>
						<li>
							<a href="./ui-tabs.html">
								<i class="fa fa-reorder"></i>
								Tabs & Accordions
							</a>
						</li>	
					</ul>
				</li>
			</ul>
		</nav>
	</div>

		<div id="content" ng-controller="MainCntl">
		    
		      Choose:
		      <a href="Book/Moby">Moby</a> |
		      <a href="Book/Moby/ch/1">Moby: Ch1</a> |
		      <a href="Book/Gatsby">Gatsby</a> |
		      <a href="Book/Gatsby/ch/4?key=value">Gatsby: Ch4</a> |
		      <a href="Book/Scarlet">Scarlet Letter</a><br/>
		    
		      <div ng-view></div>
		      <hr />
		    
		      <pre>$location.path() = {{$location.path()}}</pre>
		      <pre>$route.current.templateUrl = {{$route.current.templateUrl}}</pre>
		      <pre>$route.current.params = {{$route.current.params}}</pre>
		      <pre>$route.current.scope.name = {{$route.current.scope.name}}</pre>
		      <pre>$routeParams = {{$routeParams}}</pre>
		       
	    </div>
    </div> 
    
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
	<script src="//code.angularjs.org/1.2.4/angular.js"></script>
	<script src="//code.angularjs.org/1.2.4/angular-route.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
    <script src="js/angular-strap.min.js"></script>
    <script src="js/bootstrap-datepicker.js"></script>
    <script src="js/bootstrap-timepicker.js"></script>
    <script src="js/script_routes.js"></script>
    <script src="js/main.js"></script>
    <script src="https://apis.google.com/js/client.js?onload=init"></script>
</body>
</html>
