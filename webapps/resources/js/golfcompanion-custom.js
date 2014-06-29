 $(document).ready(function() { 
	 	 

	 // this is to enable disable the user logout menu
	 m = document.getElementById("userMenu");
	 if(m.style.display == "none") {
			 m.style.display ='block';
	 }
	 
	
	 $("#tabs").tabs();
	 
	 
	 
//	 $('select[name="golfcourse"]').multiselect().multiselectfilter();
	 	 
	/*
	var d = new Date();
	$("#playdatefrom").val((d.getMonth()+1)+"/"+d.getDate()+"/"+d.getFullYear());
    $("#playdateto").val((d.getMonth()+1)+"/"+d.getDate()+"/"+d.getFullYear());
	
	 
	$('#playdatefrom').datepicker({
		dateFormat:"mm/dd/yy",        		
		minDate: 0, 
		maxDate: "+1M +10D",				
		changeMonth: true,
		changeYear: true,
		showButtonPanel: true,
		showAnim: "blind"
	});

	$('#playdateto').datepicker({
		dateFormat:"mm/dd/yy",        		
		minDate: 0, 
		maxDate: "+1M +10D",				
		changeMonth: true,
		changeYear: true,
		showButtonPanel: true,
		showAnim: "blind"
	});
*/	
	 
	 $("#playerresult").jqGrid({		   	
     	url:'playerresults',			
     	datatype: 'json',			
     	mtype: 'GET',		   	
     	colNames:['Id', 'CourseName','Date Played','Total Score', 'Eagles','Birdies','Pars','Bogeys','Double Bogeys','Double Bogey +','Net Score', 'Tee Type','Details'],		   	
     	colModel:[		   		
     	          {name:'playerResult_id',index:'playerResult_id', width:55, sorttype:"int", hidden:true },		   		
     	          {name:'golfCourse.course_name',index:'golfCourse.course_name', width:100, sorttype:"text" },	          	          		  
     	          {name:'date_played',index:'date_played', width:20, align:'center' },
     	          {name: 'total_score',index: 'total_score', width:20, align:'right', sorttype:"number" },
                   {name: 'eagles',index: 'eagles', width:10, align:'right', sorttype:"number" },
                   {name: 'birdies',index: 'birdies', width:10, align:'right', sorttype:"number" },
                   {name: 'pars',index: 'pars', width:10, align:'right', sorttype:"number" },
                   {name: 'bogeys',index: 'bogeys', width:10, align:'right', sorttype:"number" },
                   {name: 'doublebogeys',index: 'doublebogeys', width:10, align:'right', sorttype:"number" },
                   {name: 'doublebogeyplus', index: 'doublebogeyplus', width:10, align:'right', sorttype:"number" },
                   {name: 'net_score',index: 'net_score', width:10, align:'right', sorttype:"number" },
                   {name: 'tee_type',index: 'tee_type', width:10, align:'center' },  
                   {name:'act',index:'act', width:15,sortable:false}
                   //{name: 'details', width:10, formatter:formatOperations}
     	          ],		   	
     	          postData: {},			
     	          rowNum:5,		   	
     	          rowList:[],		   	
     	          height: "100%",		   	
     	          autowidth: true,			
     	          rownumbers: false,
     	          pgbuttons:false,
     	          pgtext:false,
     	          pager: '#pager',		   	
     	          sortname: 'playerResult_id',		    
     	          viewrecords: true,		    
     	          sortorder: "desc",		    
     	          caption:"Last Five Rounds",		    
     	          emptyrecords: "No rounds recorded yet!!",		    
     	          loadonce: true,
     	          sortable: true,      	          
     	          loadComplete: function() {
     	        	 $.each($("#playerresult").jqGrid("getDataIDs"),function(i,val){
     	        		var rowData = $("#playerresult").jqGrid("getRowData",val);
     	        	 ce = "<input style='height:22px;width:70px;' type='button' value='Scorecard' onclick=\"javascript:retrieveScorecard('" + rowData["playerResult_id"] + "')\" />"; 
	      	  			jQuery("#playerresult").jqGrid('setRowData',i+1,{act:ce});
     	        	 });
     	          }, 
     	          /*gridComplete: function () {
	      	          var recs = parseInt($("#playerresult").getGridParam("records"), 10);
	      	          if (isNaN(recs) || recs == 0) {
	      	              $("#results").hide();	      	              
	      	          }
	      	          else {
	      	              $('#results').show();	      	              
	      	          }
	      	      } ,*/
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
     jQuery("#playerresult").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false});
     
     
     
	 
     
     
     function formatOperations(cellvalue, options, rowObject) {
         return "<a href='/golfcompanion/analysis.jsp?result_id=" + rowObject.playerResult_id +"'><u>ScoreCard</u></a>";
     }    

	 var selectedItems = function(g) {
		    return $(g).jqGrid("getGridParam","selarrrow");
		};
	 
	
	 
	 function processInvitations(pBottonVal){
		 //alert("invitation accepted" + $('#invitation_id').val());
		 var invitationRecs = selectedItems("#invites");
		 $.each(invitationRecs, function(i,id){
			 //alert(id);
			 var inviteId = jQuery("#invites").jqGrid('getRowData',id);
			 //alert(inviteId);
			 //alert(inviteId["hostInv.invitation_id"]);
		 $.ajax({
			type: 'POST',
			url: 'inviteresponse/'+pBottonVal + '/' +inviteId["hostInv.invitation_id"],			
			success: function (data) {
				// disable the accept button as the invitation has been accepted.
				//document.getElementById("accept").disabled = true;
				//document.getElementById("reject").disabled = false;
				//jQuery("#invites").jqGrid("delRowData", id);
				$("#invites").setGridParam({datatype:'json'}).trigger('reloadGrid');
				$.notify(data, "success");
				}		 
		 }); 
		 });
	 }

	 $('#cancel').click(function() {
		 alert($('#invite_id').val());
	 });
	 
	 
	 // sent invites grid population
	 $("#sentinvites").jqGrid({		   	
	      	url:'sentinvitations',			
	      	datatype: 'json',			
	      	mtype: 'GET',		   	
	      	colNames:['Id', 'Invitation','First Name','Last Name','Course Name','Play Date','Tee Time','Handicap','Status','Actions'],		   	
	      	colModel:[		   		
	      	          {name:'hostInv.invitation_id',index:'hostInv.invitation_id', width:5, sorttype:"int", hidden:true },
	      	          {name:'playerName',index:'playerName', width:100, sorttype:"text", align:"center"},
	      	          {name:'guestPlayer.first_name',index:'guestPlayer.first_name', width:100, sorttype:"text", hidden:true },
	      	          {name:'guestPlayer.last_name',index:'guestPlayer.last_name', width:100, sorttype:"text", hidden:true },
	      	          {name:'hostInv.golfCourse.course_name',index:'hostInv.golfCourse.course_name', width:50, sorttype:"text", hidden:true },	          	          		  
	      	          {name:'hostInv.playDate',index:'hostInv.playDate', width:30, align:'center', hidden:true },    
	      	          {name:'hostInv.playTime',index:'hostInv.playTime', width:30, align:'center', hidden:true },    
	      	          {name:'guestPlayer.handicap_index',index: 'guestPlayer.player.handicap_index', width:20, align:'right', hidden:true },
	      	          {name:'status',index:'status', width:50, sorttype:"text",align:"center"},
	      	          {name:'act',index:'act', width:10,sortable:false}
	      	          ],      	        
	      	          postData: {},			
	      	          rowNum:10,		   	
	      	          rowList:[10,20,40,60],		   	
	      	          height: "100%",	
	      	          width:1000,
	      	         // autowidth: true,			
	      	          rownumbers: true,	
	      	          //multiselect:true,
	      	          sortname: 'guestPlayer.first_name',		    
	      	          viewrecords: true,
	      	          pager:"#sentPager",
	      	          sortorder: "desc",		    
	      	          caption:"Invitations Sent",		    
	      	          emptyrecords: "No invitations were Sent.",		    
	      	          loadonce: true,
	      	          sortable: true,      	          
	      	          loadComplete: function() {
	      	        	$.each($("#sentinvites").jqGrid("getDataIDs"),function(i,val){
	      	  			var rowData = $("#sentinvites").jqGrid("getRowData",val);
			      	  	var invStatus = rowData["status"].toLowerCase();		      	  	
	      	  			if(invStatus  == "pending") {
	      	  					$("#sentinvites").jqGrid("setCell",val,"status","",{background:'yellow'});
			  	            }
			  	            else if(invStatus == "accepted") {
			  	            	$("#sentinvites").jqGrid("setCell",val,"status","",{background:'green'});
			  	            }
			  	            else if(invStatus == "rejected") {
			  	            	$("#sentinvites").jqGrid("setCell",val,"status","",{background:'red'});
			  	            }
			  	          else if(invStatus == "obsolete") {
			  	            	$("#sentinvites").jqGrid("setCell",val,"status","",{background:'grey'});			  	            	
			  	            }
	      	  			$("#sentinvites").jqGrid("setCell",val,
	      	  					"playerName",rowData["guestPlayer.first_name"]+ " " + rowData["guestPlayer.last_name"] 
	      	  					+ "\nHandicap: " + rowData["guestPlayer.handicap_index"]
	      	  					+ "\nGolf Course: " + rowData["hostInv.golfCourse.course_name"]
	      	  					+ " Play Date: " + rowData["hostInv.playDate"]
	      	  					+ " , Time: " + rowData["hostInv.playTime"]
	      	  				,"","");  
	      	  			if (invStatus != "obsolete") {
		      	  			ce = "<input style='height:22px;width:60px;' type='button' value='Cancel' onclick=\"javascript:CancelInvites('" + rowData["hostInv.invitation_id"] + "')\" />"; 
		      	  			jQuery("#sentinvites").jqGrid('setRowData',i+1,{act:ce});
	      	  			}
	      	        	});
	      	          }, 
	      	        gridComplete: function () {
	      	          var recs = parseInt($("#sentinvites").getGridParam("records"), 10);
	      	          if (isNaN(recs) || recs == 0) {
	      	              $("#sent").hide();	      	              
	      	          }
	      	          else {
	      	              $('#sent').show();
	      	            $("#sent-count").text("("+recs+")");
	      	          }
	      	      } ,
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
	 jQuery("#sentinvites").jqGrid('navGrid','#sentPager',{edit:false,add:false,del:false,search:false});
	 
	 
	 
	// invitations grid to be populated
     $("#invites").jqGrid({		   	
	      	url:'invitations',			
	      	datatype: 'json',			
	      	mtype: 'GET',		   	
	      	colNames:['Id', 'Invitation','First Name','Last Name','Course Name','Play Date','Tee Time','Handicap','Status'],		   	
	      	colModel:[		   		
	      	          {name:'hostInv.invitation_id',index:'hostInv.invitation_id', width:5, sorttype:"int", hidden:true },
	      	          {name:'playerName',index:'playerName', width:300, sorttype:"text", align:"center"},
	      	          {name:'hostInv.player.first_name',index:'hostInv.player.first_name', width:100, sorttype:"text", hidden:true },
	      	          {name:'hostInv.player.last_name',index:'hostInv.player.last_name', width:100, sorttype:"text", hidden:true },
	      	          {name:'hostInv.golfCourse.course_name',index:'hostInv.golfCourse.course_name', width:50, sorttype:"text", hidden:true },	          	          		  
	      	          {name:'hostInv.playDate',index:'hostInv.playDate', width:30, align:'center', hidden:true },    
	      	          {name:'hostInv.playTime',index:'hostInv.playTime', width:30, align:'center', hidden:true },    
	      	          {name:'hostInv.player.handicap_index',index: 'hostInv.player.player.handicap_index', width:20, align:'right', hidden:true },
	      	          {name:'status',index:'status', width:40, sorttype:"text",align:"center"}
	      	          ],      	        
	      	          postData: {},			
	      	          rowNum:10,		   	
	      	          rowList:[10,20,40,60],		   	
	      	          height: "100%",	
	      	          width:1000,
	      	          //autowidth: true,			
	      	          rownumbers: true,	
	      	          multiselect:true,
	      	          sortname: 'hostInv.playDate',		    
	      	          viewrecords: true,
	      	          pager:"#invitePager",
	      	          sortorder: "desc",		    
	      	          caption:"Invitations Received",		    
	      	          emptyrecords: "No invitations were Recieved.",		    
	      	          loadonce: true,
	      	          sortable: true,      	          
	      	          loadComplete: function() {
	      	        	$.each($("#invites").jqGrid("getDataIDs"),function(i,val){
	      	  			var rowData = $("#invites").jqGrid("getRowData",val);
			      	  	var invStatus = rowData["status"].toLowerCase();		      	  	
	      	  			if(invStatus  == "pending") {
	      	  					$("#invites").jqGrid("setCell",val,"status","",{background:'yellow'});
			  	            }
			  	            else if(invStatus == "accepted") {
			  	            	$("#invites").jqGrid("setCell",val,"status","",{background:'green'});
			  	            }
			  	            else if(invStatus == "rejected") {
			  	            	$("#invites").jqGrid("setCell",val,"status","",{background:'red'});
			  	            }
	      	  		$("#invites").jqGrid("setCell",val,
      	  					"playerName",rowData["hostInv.player.first_name"]+ " " + rowData["hostInv.player.last_name"] 
      	  					+ "\nHandicap: " + rowData["hostInv.player.handicap_index"]
      	  					+ "\nGolf Course: " + rowData["hostInv.golfCourse.course_name"]
      	  					+ "\nPlay Date: " + rowData["hostInv.playDate"]
      	  					+ "\nPlay Time: " + rowData["hostInv.playTime"]
      	  				,"","");       	  			
	      	        	});
	      	          }, 
	      	        gridComplete: function () {
		      	          var recs = parseInt($("#invites").getGridParam("records"), 10);
		      	          if (isNaN(recs) || recs == 0) {
		      	              $("#received").hide();	      	              
		      	          }
		      	          else {
		      	              $('#received').show();	
		      	              $("#recv-count").text("("+recs+")");
		      	          }
		      	      } ,
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
     jQuery("#invites").jqGrid('navGrid','#invitePager',{edit:false,add:false,del:false,search:false});
     jQuery("#invites").jqGrid('navButtonAdd',"#invitePager",{caption:"Accept", onClickButton:function(){ 
     					     processInvitations("ACCEPTED");
     				   }, position: "last", title:"", cursor: "pointer"});
     jQuery("#invites").jqGrid('navButtonAdd',"#invitePager",{caption:"Reject", onClickButton:function(){ 
    					     processInvitations("REJECTED");
    				   }, position: "last", title:"", cursor: "pointer"});
	 
});   
        
 function CancelInvites(val){
	 $.ajax({
			type: 'POST',
			url: 'inviteresponse/CANCELLED/' +val,			
			success: function (data) {
				$("#sentinvites").setGridParam({datatype:'json'}).trigger('reloadGrid');
				//alert(data);
				$.notify(data, "success");
				}		 
		 }); 
 }
 
	 function retrieveScorecard(val){		 
		 window.location = '/golfcompanion/analysis/' +val;		 
	 }		 
