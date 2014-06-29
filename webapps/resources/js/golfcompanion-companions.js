/**
 * 
 */
 $(document).ready(function() { 

	// var subcity = document.getElementById('subcity');
	  // 	subcity.style.visibility = 'hidden';

	// this is to enable disable the user logout menu
   	 d = document.getElementById("userMenu");
   	 if(d.style.display == "none") {
   			 d.style.display ='block';
   	 }

   	var inviteButton = document.getElementById('invitation');
   	inviteButton.style.visibility = 'hidden';

   	$( "#accordion" ).accordion({
   			collapsible: true,
   			width: 20,
   			autoheight: false
   			//heightStyle: "fill"       			
   	});
  	
   	$("input[name='rating']").change(function(){
   	    alert($('input[name="rating"]:checked').val()); 
   	    alert(document.getElementById("guestPlayerId").value);
   	    var playerRating = $('input[name="rating"]:checked').val();
   	    var guestPlayer_id = document.getElementById("guestPlayerId").value;
   	    var playerComments = document.getElementById("comments").value;
   	    alert(playerComments);
   	    var accordion = $( "#accordion" ).accordion();
   	 var active = accordion.accordion( "option", "active" );
   	 alert(active);
     if ( active === 1 ) {
       accordion.accordion( "option", "active", 0 );
     }
     accordion.find( ".ui-accordion-header:eq("+active+")" ).toggle();   	 
   	    $.ajax({
   	    	type:'POST',
   	    	url:'rate',
   	    	data: {reviewee_id:guestPlayer_id, rating: playerRating, comments: playerComments},
   	    	success: function (data)
   	    	{
   	    		alert(data);
   	    	}
   	    });
   	});
	 
	 // this is to enable disable the user logout menu
	 m = document.getElementById("userMenu");
	 if(m.style.display == "none") {
			 m.style.display ='block';
	 }
	 
	 function sleep(milliseconds) {
		 var start = new Date().getTime();
		 while ((new Date().getTime() - start) < milliseconds){
		 // Do nothing
		 }
		 }

     $("#search").click(function(){ 
     // validation 
    	 var zipcode = $('#zipcode').val();
    	 var city = $('#city').val();
    	 var validFlag;
    	 validFlag = validateAjaxCall('validatesearch',$("#profilepage").serialize());
    	 if (validFlag == 0) 
    	 {
    		 reloadCompanions();    		     
    	 }
    	 /*if (!zipcode && !city )
    		 {
    		 $("#ziperr").html('<br><span style="color:red;font-size:8pt">Please provide either Zipcode or City to search Companions</span>');
    		 //alert('Please provide either Zipcode or City to search Companions');
    		 $('#city').focus();
    		 return false;
    		 }
    	
    	 if (zipcode && !city)
    		 {
    		 if(!$('#latitude').val()){
    				$("#ziperr").html('<span style="color:red;font-size:8pt">Invalid Zip code. Please correct input</span>');    					
    			            	$('#zipcode').focus();
    			            	return false;
    					}
    		 }
    	 
    	 // make sure if the user types in city and tabs out then no city id populated as autocomplete not used
    	 // in this case we need to get city Id and validate
    	 
    	 var city_id = $('#city_id').val();
    	     	    	 
    	// alert('City id:' + city_id);
    	 if ( city_id == -9 && city && !zipcode)
    		 {
    		 	// get city id 
    		 $("#cityerr").html('<span style="color:red;font-size:8pt">Please select an appropriate city using the options provided</span>');
    		 	//alert('Please use Autocomplete functionality and select city');
    		 	$('#city').focus();
    		 	return;
    		 }
    	 
    */
    	 //alert (validFlag);
    	 
  	});
          
     function reloadCompanions(){
    	 $("#searchresults").setGridParam(
					{
					 url:'srch',
					 datatype:'json', 								
					 page:1 ,
					 postData: $("#profilepage").serialize()
					 }).trigger('reloadGrid');
	
	$('#searchresults').jqGrid({
		url:'srch',
	datatype:'json',
	mtype:'POST',
	colNames:['Id', 'Player Name','First Name','Last Name', 'Gender','Age','Handicap'//,'Rating'
	          ,'Golf Course','Play Date', 'Tee Time','Message'],		   	
	colModel:[		   		
         {name:'players.player_id',index:'players.player_id', width:55, sorttype:"int", hidden:true },		   		      	          	
        {name:'player_name',index:'player_name', width:100,sorttype:"text" },
         {name:'players.first_name',index:'players.first_name', width:10,sorttype:"text", hidden:true },
         {name: 'players.last_name',index: 'players.last_name', width:10, sorttype:"text", hidden:true},                                         
         {name: 'players.gender',index: 'players.gender', width:50, sorttype:"text"},
         {name: 'player_age',index: 'player_age', width:50, sorttype:"int"},
         {name: 'players.handicap_index', index: 'players.handicap_index', width:100, sorttype:"number"},
//         {name: 'players.review_rating', index: 'players.review_rating', width:50, sorttype:"float"},
         {name:'golf_course',index:'golf_course', width:230,resizable:true,   
         	   align:"left",sorttype:"text",editable:true,edittype:"select",formatter:"select", editoptions:   
         	   { /*dataInit: function (elem) {
         		$(elem).autocomplete({
         			source:golfCourses
         		});
         		   value:golfCourses
         	   }*/
         		  value:golfCourses
         		   },editrules:{required:true}
         	  },
         {name: 'play_date', index: 'play_date', width:70, sorttype:"date", editable:true},
       /*  {name:'tee_time',index:'tee_time', width:120,resizable:true,   
      	   align:"left",sorttype:"text",editable:true,edittype:"select",formatter:"select", editoptions:   
      	   { value:"M:Morning;A:Afternoon;T:Twilight;E:Evening;"},editrules:{required:true}
      	  },*/
      	 {name: 'tee_time', index: 'tee_time', width:80, sorttype:"text", editable:true},
           {name: 'err_msg', index: 'err_msg', width:300, sorttype:"text", hidden:true},
         ],		   	
         postData: $("#profilepage").serialize(),			
         rowNum:10,		         
         rowList:[10,20,40,60],		   	
         height: 200,		   	
        // autowidth: true,			
         rownumbers: true,		   	
         pager: '#pager3',		   	
         sortname: 'players.player_id',		    
         viewrecords: true,		    
         sortorder: "desc",
         multiselect: true,
         caption:"Companions Found",		    
         emptyrecords: "No companions found for your search",		    
         loadonce: true,
         sortable: true,
         deselectAfterSort: false,
         beforeSelectRow: function(rowid)
         {             
             rowdata = $('#searchresults').getRowData(rowid);
             $('#searchresults').setColProp('golf_course', {editoptions:{value:golfCourses}});
             return true;
         },
       onSelectRow: function(id, status, e){
    	   if (status) {
    		   jQuery('#searchresults').jqGrid('editRow',id,true,pickdates);      		   
    	   }
    	   else{
	  			jQuery('#searchresults').restoreRow(id);  	  				  			
	  		}
	  		
	  		},
         loadComplete: function() {
       	inviteButton.style.visibility = 'visible';
       	 $.each($("#searchresults").jqGrid("getDataIDs"),function(i,val){      	        		 
 	  			var rowData = $("#searchresults").jqGrid("getRowData",val);            	  			
 	  			$("#searchresults").jqGrid("setCell",val,"player_name",rowData["players.first_name"]+ " " + rowData["players.last_name"],"","");      	  			            	  			 	  			
       	 });
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
	jQuery("#searchresults").jqGrid('navGrid','#pager3',{edit:false,add:false,del:false});
     }
     
     function pickdates(id){
    		jQuery("#"+id+"_play_date","#searchresults").datepicker(
    				{dateFormat:"mm/dd/yy",
    					minDate: "+1D", 
    					maxDate: "+1M +10D"});
    		jQuery('#'+id+"_tee_time","#searchresults").timePicker({
    			startTime: "07:00", // Using string. Can take string or Date object.
			  endTime: new Date(0, 0, 0, 19, 30, 0), // Using Date object here.
			  show24Hours: false,
			  separator: ':',
			  step: 15
    		});    		
    	}
	 
	 var selectedItems = function(g) {
		    return $(g).jqGrid("getGridParam","selarrrow");
		};
	 
	
	 $("#invitation").click(function(){
		 //alert("Invitation clicked");		 
		 $('#infoMessage').html("");
		 var selectedPlayers = selectedItems("#searchresults");
		 $.each(selectedPlayers, function(i,id){
			var ret = jQuery("#searchresults").jqGrid('getRowData',id);
			 var player = ret["players.player_id"];
			 var gc = $('#' + id + '_golf_course option:selected').val();
			 var teeTime = $('#' + id + '_tee_time').val();
			 var playDate = $('#'+ id + '_play_date').val();
			 if (gc && teeTime && playDate )
				 {
				 $('#searchresults').jqGrid('hideCol', "err_msg");	
				 var urlVal = 'playerinvite';
				 $.ajax({
						type: 'POST',
						url: urlVal,
						data:{player_id: player, golfcourse: gc, playdate: playDate, teetime: teeTime},			
						success: function(data) {
						//alert(data);
						//$('#infoMessage').append(data + "<br>");
						//$('#infoMessage').css("color","red");
							$.notify(data, "success");
						}
					});
				 }
			 else
			 {				 
				 $('#searchresults').jqGrid("setCell", id, 'err_msg', "Ensure golf course, play date and tee time are selected for invitation.",{background:'red'});
				 $('#searchresults').jqGrid('showCol', "err_msg");
			 }
		 });
	 });
	 
 });