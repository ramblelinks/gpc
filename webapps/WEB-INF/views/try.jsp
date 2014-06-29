<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/cupertino/jquery-ui-1.8.18.custom.css"/>'/>	
<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/ui.jqgrid.css"/>'/>	
<link type="text/css" rel="stylesheet" media="screen" href='<c:url value="/resources/css/jquery.jqplot.min.css"/>'    />
<link type="text/css" rel="stylesheet" media="screen" href='<c:url value="/resources/css/ui.multiselect.css"/>'    />	
<link type="text/css" rel="stylesheet" media="screen" href='<c:url value="/resources/css/jquery.multiselect.css"/>'    />

<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.7.1.min.js"/>'></script>	
<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui-1.8.18.custom.min.js"/>'></script>	
<script type='text/javascript' src='<c:url value="/resources/js/grid.locale-en.js"/>'></script>	
<script type='text/javascript' src='<c:url value="/resources/js/ui.multiselect.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery.jqGrid.min.js"/>'></script>	
<script type='text/javascript' src='<c:url value="/resources/js/jquery.multiselect.min.js"/>'></script>	

<title>Try Page for R and D</title>
<script type='text/javascript'>
$(function() {	
	
$("#grid").jqGrid({		   	
	url:'g',			
	datatype: 'json',			
	mtype: 'GET',		   	
	colNames:['Id', 'Username', 'First Name', 'Last Name'],		   	
	colModel:[		   		
	          {name:'id',index:'id', width:55},		   		
	          {name:'username',index:'username', width:100 },		   			          	   	
	          {name:'firstname',index:'firstname', width:100,editable:true },		   		
	          {name:'lastname',index:'lastname', width:100}	          		   
	          ],		   	
	          postData: {},			
	          rowNum:10,		   	
	          rowList:[10,20,40,60],		   	
	          height: 240,		   	
	          autowidth: true,			
	          rownumbers: true,		   	
	          pager: '#pager',		   	
	          sortname: 'id',		    
	          viewrecords: true,		    
	          sortorder: "asc",		    
	          caption:"Records",		    
	          emptyrecords: "Empty records",		    
	          loadonce: false,	
	          sortable: true,
	          loadComplete: function() {},
	          onSelectRow: function(id){
	        	  alert(id);
	        	  if (id == 2){
	        	  $('#grid').jqGrid('editRow',id,true);
	        	  }
	          },
	          jsonReader : {		        
	        	  root: "rows",		        
	        	  page: "page",		        
	        	  total: "total",		        
	        	  records: "records",		        
	        	  repeatitems: false,		        
	        	  cell: "cell",		        
	        	  id: "id"		    
	        	  }		
	          });
jQuery("#grid").jqGrid('navGrid','#pager',{edit:false,add:false,del:false});
	          
$("#grid2").jqGrid({		   	
	url:'gc',			
	datatype: 'json',			
	mtype: 'GET',		   	
	colNames:['Id', 'CourseName'],		   	
	colModel:[		   		
	          {name:'golfcourse_id',index:'golfcourse_id', width:55},		   		
	          {name:'course_name',index:'course_name', width:100 }	          	          		  
	          ],		   	
	          postData: {},			
	          rowNum:10,		   	
	          rowList:[10,20,40,60],		   	
	          height: 240,		   	
	          autowidth: true,			
	          rownumbers: true,		   	
	          pager: '#pager2',		   	
	          sortname: 'id',		    
	          viewrecords: true,		    
	          sortorder: "asc",		    
	          caption:"Records",		    
	          emptyrecords: "Empty records",		    
	          loadonce: false,	
	          deselectAfterSort: false,
	          loadComplete: function() {},		    
	          jsonReader : {		        
	        	  root: "rows",		        
	        	  page: "page",		        
	        	  total: "total",		        
	        	  records: "records",		        
	        	  repeatitems: false,		        
	        	  cell: "cell",		        
	        	  id: "id"		    
	        	  }		
	          });

$("#grid3").jqGrid({		   	
	url:'pl',			
	datatype: 'json',			
	mtype: 'GET',		   	
	colNames:['Id', 'CourseName','Date Played','Total Score', 'Eagles','Birdies','Pars','Bogeys','Double Bogeys','Double Bogey +','Net Score', 'Tee Type'],		   	
	colModel:[		   		
	          {name:'playerResult_id',index:'playerResult_id', width:55},		   		
	          {name:'golfCourse.course_name',index:'golfCourse.course_name', width:100 },	          	          		  
	          {name:'date_played',index:'date_played', width:100 },
	          {name: 'total_Score',index: 'total_Score', width:10},
              {name: 'eagles',index: 'eagles', width:10},
              {name: 'birdies',index: 'birdies', width:10},
              {name: 'pars',index: 'pars', width:10},
              {name: 'bogeys',index: 'bogeys', width:10},
              {name: 'doublebogeys',index: 'doublebogeys', width:10},
              {name: 'doublebogeyplus', index: 'doublebogeyplus', width:10},
              {name: 'net_score',index: 'net_score', width:10},
              {name: 'tee_type',index: 'tee_type', width:10}              
	          ],		   	
	          postData: {},			
	          rowNum:10,		   	
	          rowList:[10,20,40,60],		   	
	          height: 240,		   	
	          autowidth: true,			
	          rownumbers: true,		   	
	          pager: '#pager3',		   	
	          sortname: 'id',		    
	          viewrecords: true,		    
	          sortorder: "asc",		    
	          caption:"Records",		    
	          emptyrecords: "Empty records",		    
	          loadonce: false,		    
	          loadComplete: function() {},		    
	          jsonReader : {		        
	        	  root: "rows",		        
	        	  page: "page",		        
	        	  total: "total",		        
	        	  records: "records",		        
	        	  repeatitems: false,		        
	        	  cell: "cell",		        
	        	  id: "id"		    
	        	  }		
	          });	

$.ajax({
	url:'dd',
	mtype:'GET',
	success: function(data)
	{
		populateAuto(data);	
	}	
});

var cityJson;

$.ajax({
	url:'city',
	mtype:'GET',
	datatype:'json',
	success: function(data)
	{
		cityJson = data;
		alert(JSON.stringify(cityJson));
		$( "#city" ).autocomplete({      
			minLength: 0,      
			source: cityJson,      				      
			select: function( event, ui ) {        
				$( "#city" ).val( ui.item.label );        
				$( "#city_id" ).val( ui.item.value );        
				return false;      
				}    
			});
	}	
});



function populateAuto(data)
{
$("input#autocomplete").autocomplete({
    source: data//["c++", "java", "php", "coldfusion", "javascript", "asp", "ruby"]
});
}	          

$('select[name="color"]').multiselect();

$('select[name="golfclubs"]').multiselect();

});
</script>

</head>
<body>
redirected
<br> 
<input id="autocomplete" />
<br>
City: <input id="city" name="city" type="text"/>
City Id: <input id="city_id" name="city_id" />
<br>
<select name="color" multiple="multiple" class="chzn-select" style="width: 40em;" >
		<option value="white">White</option>
		<option value="black" selected="selected">Black</option>
		<option value="red">Red</option>
</select>
<br>
<br>
<select multiple="multiple" id="golfclubs" name="golfclubs" style="width: 20em;">
   	<option value="-9" label="--Please Select"/>
   	<c:forEach var="golfclub" items="${golfclub }" >
   	<option Value="${golfclub.golfclub_id}" selected="selected" ><c:out value="${golfclub.club_name}" /></option>
   	</c:forEach>  
</select>


<table id="grid"></table>
<div id="pager"></div>
<table id="grid2"></table>
<table id="grid3"></table>
</body>
</html>