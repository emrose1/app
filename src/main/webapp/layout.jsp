<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>

   <title>Tables Basic - Canvas Admin</title>
	<meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0" />  
   
   <jsp:include page="css_includes.jsp" />
</head>
<body>
	<div id="wrapper">
		
		<header id="header">
	
			<h1 id="site-logo">
				<a href="./index.html">
					<img src="./img/logos/logo.png" alt="Site Logo" />
				</a>
			</h1>	
	
			<a href="" data-toggle="collapse" data-target=".top-bar-collapse" id="top-bar-toggle" class="navbar-toggle collapsed">
				<i class="fa fa-cog"></i>
			</a>
	
			<a href="" data-toggle="collapse" data-target=".sidebar-collapse" id="sidebar-toggle" class="navbar-toggle collapsed">
				<i class="fa fa-reorder"></i>
			</a>
	
		</header> <!-- header -->
	
	
		<nav id="top-bar" class="collapse top-bar-collapse">
	
			<ul class="nav navbar-nav pull-left">
				<li class="">
					<a href="./index.html">
						<i class="fa fa-home"></i> 
						Home
					</a>
				</li>
	
				<li class="dropdown">
			    	<a class="dropdown-toggle" data-toggle="dropdown" href="">
			        	Dropdown <span class="caret"></span>
			    	</a>
	
			    	<ul class="dropdown-menu" role="menu">
				        <li><a href=""><i class="fa fa-user"></i>&nbsp;&nbsp;Example #1</a></li>
				        <li><a href=""><i class="fa fa-calendar"></i>&nbsp;&nbsp;Example #2</a></li>
				        <li class="divider"></li>
				        <li><a href=""><i class="fa fa-tasks"></i>&nbsp;&nbsp;Example #3</a></li>
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
	
		</nav> <!-- /#top-bar -->
	
	
		<div id="sidebar-wrapper" class="collapse sidebar-collapse">
		
			<div id="search">
				<form>
					<input class="form-control input-sm" type="text" name="search" placeholder="Search..." />
	
					<button type="submit" id="search-btn" class="btn"><i class="fa fa-search"></i></button>
				</form>		
			</div> <!-- #search -->
		
			<nav id="sidebar">		
				
				<ul id="main-nav" class="open-active">			
	
					<li class="">				
						<a href="./index.html">
							<i class="fa fa-dashboard"></i>
							Dashboard
						</a>				
					</li>
								
					<li class="dropdown">
						<a href=""> <i class="fa fa-file-text"></i> Example
							Pages <span class="caret"></span>
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
							<li>
								<a href="./page-pricing.html">
									<i class="fa fa-dollar"></i> 
									Pricing Plans
								</a>
							</li>
							<li>
								<a href="./page-support.html">
									<i class="fa fa-question"></i> 
									Support Page
								</a>
							</li>
							<li>
								<a href="./page-gallery.html">
									<i class="fa fa-picture-o"></i> 
									Gallery
								</a>
							</li>
							<li>
								<a href="./page-settings.html">
									<i class="fa fa-cogs"></i> 
									Settings
								</a>
							</li>
							<li>
								<a href="./page-calendar.html">
									<i class="fa fa-calendar"></i> 
									Calendar
								</a>
							</li>
						</ul>						
						
					</li>	
					
					
	
				</ul>
						
			</nav> <!-- #sidebar -->
	
		</div> <!-- /#sidebar-wrapper -->
	
	
		
			<div id="content"></div>
	  </div>
</body>
</html>