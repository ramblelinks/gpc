<!DOCTYPE HTML>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head>
<script type='text/javascript' src='<c:url value="/resources/js/golfcompanion-search.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/scorecard.js"/>'></script>	
<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
<style type="text/css">
label, input { display:block; }    
input.text { margin-bottom:12px; width:95%; padding: .4em; }
 .ui-datepicker {
		font-size: 60%;
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
.ui-dialog {padding: .3em; font-size:62.5%;}
.ui-state-error { padding: .3em; }		
.validateTips { border: 1px solid transparent; padding: 0.3em; }
</style>
</head>
<body>
<form id="profilepage" name="profilepage">

<table>
	<tr height=35%>
		<td>
		<fieldset><legend>Retrieve Course Scorecard</legend>
			<table>
			<%@ include file="/WEB-INF/views/search.jsp" %>					
				<tr>
					<td>*Golf Course Played:</td>
					<td><select id="golfcourse" name="golfcourse" class="chosen-select">
						<option value="-9" label="--Please Select--" />
					</select>
					<span id="golfcourse_err"></span>
					</td>
				</tr>
				<tr>
					<td>*Date Played:</td>
					<td><input id="playdate" type="text" name="playdate">
					<span id="playdate_err"></span>
					</td>
				</tr>
				<tr>
					<td>*Tee Type:</td>			
					<td><select id="teetype" name="teetype">
					    <option value="-9" label="--Please Select--" />
					</select>
					<span id="teetype_err"></span>
					</td>
				</tr>
			</table>			
		<input id="search" type="button" value="Search" />
		</fieldset>
		</td>		
	</tr>
</table>
	<div>
				<fieldset><legend>Scorecard</legend>
				<table id="scorecardgrid" ></table>
				<div id="pager3"></div>
				</fieldset>
			</div>
</form>
<div id="datetimedialog" title="Date Time" >  
<p class="validateTips">All form fields are required.</p>
<p>
*Play Date: <input id="playd" type="text" class="text ui-widget-content ui-corner-all"><br>
*Play Time: <input id="playt" type="text">
</p>
</div>			
</body>
</html>