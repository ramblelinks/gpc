<%@ include file="/WEB-INF/views/include.jsp" %>
<%@ page import="java.io.*,java.util.*" %>
<%
String username;
username = (String) session.getAttribute("uName");
String invitation;
invitation = (String) session.getAttribute("hdr");
%>
<html>
  <head>
    <title>Welcome to Golf-Pro Companion: <sitemesh:write property='title'/></title>
    <sitemesh:write property='script' />
    <style type='text/css'>
      /* Some CSS */
     body { font-family: arial, sans-serif; background-color: transparent; }
     a { color:#999; text-decoration:none; }
     h1, h2, h3, h4 { text-align: center; background-color: transparent;
                      /* border-top: 1px solid #66ff66; */ 
                      }     
     .mainBody { padding: 10px; border: 0px solid #555555; }
     .disclaimer { text-align: center; border-top: 1px solid #cccccc;
                   margin-top: 40px; color: #666666; font-size: smaller; }
     .ui-menu { width: auto; clear:none; font-size:70%;}  
     .ui-menu .ui-menu-item {width: auto; clear:none;     }               
    </style>   
    <script> 
    $(function() {    	
     $('.popbox').popbox();
     
   	 $( "#menu" ).menu({
         position: {at: "left bottom"},
         selected: function(event, ui) {
        	 var sPath=window.location.pathname; 
        	 var sPage = sPath.substring(sPath.lastIndexOf('/') + 1); 
        	 
        	 if (sPage != "login" && sPage != "register" )
        	{
             	
             if (ui.item.text() == "Home"){            	 
            	 window.location = "/golfcompanion/gohome/s";            	 
             }
             if (ui.item.text() == "Scorecard"){            	 
            	 window.location = "/golfcompanion/scorecard/main";            	 
             }
             if (ui.item.text() == "Companions"){
            	 window.location = "/golfcompanion/companion/main";
             }
             if (ui.item.text() == "Analysis"){
            	 window.location = "/golfcompanion/analysis/-9";
             }
             if (ui.item.text() == "FAQ"){
            	 window.location = "/golfcompanion/faq";
             }
        	}
         }
     });    	 
		});  
    </script>      
    <sitemesh:write property='head'/>
  </head>
  <body>
  
  <div>
	  <table width="100%">
	  <tr>
		  <td width=85%>
		    <h1>Golf Pro Companion</h1>
		  </td>
		  <td width=15%>  
		  <% if (invitation != null) { %>
		    <span id="numInv" style="text-align:right;font-size:8pt"><% out.print(invitation); %></span>
		    <% } %>
		 <div id="userMenu" class='popbox' style="display: none;">
          <a class='open' href='#'><% out.print(username); %></a>
           <div class='collapse'>
            <div class='box' style="width:100px;">
              <div class='arrow'></div>
              <div class='arrow-border'></div>
              <a href="/golfcompanion/profile/fetch">Profile</a>
              <br>
              <a href="/golfcompanion/logout">logout</a>                           
            </div>
          </div>          
		</div>
		   </td>
	  </tr>
	  </table>
  </div>    
	<div>
	<ul id="menu">
		<li><a href="#">Home</a></li>
		<li><a href="#">Companions</a></li>
		<li><a href="#">Scorecard</a></li>
		<li><a href="#">Analysis</a></li>
		<li><a href="#">FAQ</a></li>
	</ul>	
	</div>
	<br><br><br>	
    <div class='mainBody'>
      <sitemesh:write property='body'/>
    </div>
    <div>
  <!-- Feedzilla Widget BEGIN -->

<div class="feedzilla-news-widget feedzilla-5270830981403787" style="width:100%; padding: 0; text-align: center; font-size: 11px; border: 0;">
<script type="text/javascript" src="http://widgets.feedzilla.com/news/iframe/js/widget.js"></script>
<script type="text/javascript">
new FEEDZILLA.Widget({
	style: 'ticker',
	culture_code: 'en_us',
	c: '27',
	sc: '1462',
	headerBackgroundColor: '#4829f5',
	title: 'Sports',
	caption: 'Golf',
	order: 'relevance',
	count: '20',
	w: '998',
	h: '30',
	timestamp: 'true',
	scrollbar: 'false',
	theme: 'ui-lightness',
	className: 'feedzilla-5270830981403787'
});
</script></div>

<!-- Feedzilla Widget END -->
  </div>
    <div class='disclaimer'>Copyright Ramble Links LLC</div>
  </body>
</html>
