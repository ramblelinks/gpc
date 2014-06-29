<!DOCTYPE HTML>
<%@ include file="/WEB-INF/views/include.jsp" %>
<html>
    <head>
        <title>Home Page</title>         
		<script type='text/javascript' src='<c:url value="/resources/js/golfcompanion-custom.js"/>'></script>
		<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
		<style>
		.ui-jqgrid tr.ui-row-ltr td {
		    border-right-style: none;
		    border-left-style: none;
		}	
		.ui-tabs {font-size: 8pt;}				
		</style>                                
    </head>
    <body>
	<div>
	<div id="results">              
        <table id="playerresult"></table>
        <div id="pager"></div>                                            
     </div>
	
	<div id="tabs">
	     	<ul>
	     	<li><a href="#received">Recieved <span id="recv-count">(0)</span></a></li>
	     	<li><a href="#sent">Sent <span id="sent-count">(0)</span></a></li>	     	
	     	</ul>			     	     
	    <div id="sent">	        		        	
	        	<table id="sentinvites"></table>	
	        	<div id="sentPager"></div>        		        	
	     </div> 	               
        <div id="received">	        		        	
	        	<table id="invites"></table>	
	        	<div id="invitePager"></div>        		        	
	     </div>
	</div>
	</div>                     
    </body>
</html>
