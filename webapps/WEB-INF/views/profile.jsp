<!DOCTYPE HTML>
<%@ include file="/WEB-INF/views/include.jsp" %>
<html>
    <head>
        <title>Player Profile</title>
        <script type='text/javascript' src='<c:url value="/resources/js/golfcompanion-validator.js"/>'></script>
        <script type='text/javascript' src='<c:url value="/resources/js/golfcompanion-search.js"/>'></script>
        <script type='text/javascript' src='<c:url value="/resources/js/golfcompanion-companions.js"/>'></script>        
		<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
        <style>
        .ui-accordion-content {	
        font-size: 8pt;
        }
        .ui-accordion-header {   
    		background-color: pink;
    		font-size:10pt;   
        }
        .ui-datepicker {
		font-size: 60%;
		}	
		.ui-jqgrid tr.ui-row-ltr td {
		    border-right-style: none;
		    border-left-style: none;
		}
		.ui-autocomplete {
		    max-height: 200px;
		    width: 50px;
		    overflow-y: auto;
		    overflow-x: hidden;  /* no horizontal scrollbar */
		    padding-right: 20px; /* for the vertical scrollbar */
		    background: lightgreen;
		    font-size: .8em;
		}
        </style>                                                   
    </head>
<body>
<form id="profilepage" name="profilepage">    
 <table>  
        <tr>
	        <td width=30%> 	            
		        <div>		        
		        <fieldset>
		        <legend>Search Companions</legend>	
		        <Table>	        
		        	 <tr>
      <td>
      	Handicap:
      </td>
      <td>
       <select id="handicap" name="handicap" class="chosen-select">        
       <option value=0-50>Ignore</option> 
       <option value=0-5>0-5</option>
       <option value=5-10>5-10</option>
       <option value=10-15>10-15</option>
       <option value=15-20>15-20</option>
       <option value=20-50>20+</option>
       </select>
     	</td>
     </tr>
     <%@ include file="/WEB-INF/views/search.jsp" %>
	 <tr>
      <td>
      	Gender:
      </td>
      <td>
       <select id="gender" name="gender" class="chosen-select">  
       <option value=A>Ignore</option>       
       <option value=M>Male</option>
       <option value=F>Female</option>
       </select>
      </td>
     </tr>
     <tr>
      <td>
      	Age:
      </td>
      <td>
       <select id="age" name="age" class="chosen-select">         
       <option value=0-110>Ignore</option>
       <option value=10-20>10-20</option>
       <option value=20-30>20-30</option>
       <option value=30-40>30-40</option>
       <option value=40-50>40-50</option>
       <option value=50-60>50-60</option>
       <option value=60-110>60+</option>
       </select>
      </td>
     </tr>			       
			    </Table>
			    <input id="search" type="button" value="Search" />   			       
			    </fieldset>	        	        	        	      
		     </div>		    
	        </td>
	        <td width=70%>
		        <div id="results" >
		        <fieldset>
		        <legend>Companions Found</legend>
		        <table id="searchresults"></table>
		        <div id="pager3"></div>	
		        <input id="invitation" type="button" value="Invite" />
		        <br>
		        <span id="infoMessage"></span>        
		        </fieldset>
		        </div>
	        </td> 	         
        </tr>
        </table> 
	<%--
	Commented out as will wont apply reviews/rating as of now. 
	<p>this is where we will have awards, previous invites for review</p>
	<div id="accordion-wrapper" style="width:300px;">
		<div id="accordion">
		<c:forEach var="invitations" items="${invites }" >
		<h3>
			<input id="guestPlayerId" type="hidden" value="${invitations.guestPlayer.player_id}" />
				<c:out value="${invitations.guestPlayer.first_name}" />
				<c:out value="${invitations.guestPlayer.last_name}" />
			
		</h3>
		<div>
		<p>
			Golf Course Played: <c:out value="${invitations.hostInv.golfCourse.course_name}" /> On <c:out value="${invitations.hostInv.playDate}" />		
		</p>
		<select id="comments" name="comments">
					<option value="Great Player">Great Player</option>
					<option value="No Show">No show</option>
					<option value="Other">Other</option>
		</select>	
		<input type="radio" name="rating" value="1" />1
		<input type="radio" name="rating" value="2" />2
		<input type="radio" name="rating" value="3" />3
		<input type="radio" name="rating" value="4" />4
		<input type="radio" name="rating" value="5" />5 		
 		<!-- div id="ratePopBox" class='popbox'>
          <a class='open' href='#' style="padding:2px;">Rate</a>
           <div class='collapse'>
            <div class='box' style="width:200px;">
              <div class='arrow'></div>
              <div class='arrow-border'></div>
                <input type="radio" name="rating" value="1" />1
				<input type="radio" name="rating" value="2" />2
				<input type="radio" name="rating" value="3" />3
				<input type="radio" name="rating" value="4" />4
				<input type="radio" name="rating" value="5" />5 											                         
            </div>
          </div>          
		</div-->	 
		</div>
		</c:forEach>
		</div>
	</div>
	--%>
	<div>
	<!-- table>
	<c:forEach var="invitations" items="${invites }" >
	<tr>	
		<td>
		<c:out value="${invitations.guestPlayer.first_name}" />
		<c:out value="${invitations.guestPlayer.last_name}" /><br>
		Golf Course Played: <c:out value="${invitations.hostInv.golfCourse.course_name}" /> On <c:out value="${invitations.hostInv.playDate}" /> 
		</td>
		<td width=50%>
		<div id="ratePopBox" class='popbox'>
          <a class='open' href='#' style="padding:2px;">Rate</a>
           <div class='collapse'>
            <div class='box' style="width:200px;">
              <div class='arrow'></div>
              <div class='arrow-border'></div>
                <input type="radio" name="rating" value="one" />1
				<input type="radio" name="rating" value="two" />2
				<input type="radio" name="rating" value="three" />3
				<input type="radio" name="rating" value="four" />4
				<input type="radio" name="rating" value="five" />5 				                         
            </div>
          </div>          
		</div>		
		</td>		
	</tr>
	</c:forEach> 
	</table-->		
	</div>
	</form>
</body>        
</html>    

    