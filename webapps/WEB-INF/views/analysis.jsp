<!DOCTYPE HTML>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head>
<script type='text/javascript' src='<c:url value="/resources/js/golfcompanion-search.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/golfcompanion-analysis.js"/>'></script>	
<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
<style type="text/css">
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
<form:form id="profilepage" name="profilepage"  >
<table>
<tr>
<td>
<table>
	<tr height=35%>
		<td>
		<fieldset><legend>Search Scores</legend>
			<table>
			<%@ include file="/WEB-INF/views/search.jsp" %>					
				<tr>
					<td>Golf Course Played:</td>
					<td><select id="golfcourse" name="golfcourse"  class="chosen-select">
						<option value="-9" label="--Please Select" />
					</select>
					<span id="golfcourse_err"></span>					
				</td>
				</tr>				
			</table>			
		<input id="search" type="button" value="Search" />
		<input id="reset" type="Reset" value="Reset" />
		</fieldset>
		</td>		
	</tr>
</table>
</td>
<td>
	<div id="statschart" style="height:200px;width:500px; "></div>
</td>
<td> 
	<div id="scoreschart" style="height:200px;width:500px; "></div>
</td>
</tr>
</table>
<br>
Available Scorecards: <select id="scores" name="scores" class="chosen-select">
	<option value="-9" label="--Please Select" />
</select>	
	<input id="view" type="button" value="View Scorecard" disabled="disabled"/>	
	<input id="plyResultId" value=${playerResult} type=hidden />		
	<div id="scorecard">				
				<table id="scorecardgrid" ></table>
				<div id="pager3"></div>				
			</div>
<div>
<div id="roundstats" style="height:400px;width:500px; "></div>
<!-- <table>
	<tr>
		<td>
			<div id="statschart" style="height:400px;width:500px; "></div>
		</td>
		<td> 
			<div id="scoreschart" style="height:400px;width:500px; "></div>
		</td>
	</tr>
</table>
 --></div>			
</form:form>
</body>
</html>