 $(document).ready(function() {

	 $("#teetype").chosen();
	 
	 var playd = $( "#playd" ),      
	 	playt = $( "#playt" ),      	 	     
	 	allFields = $( [] ).add( playd ).add( playt ),      
	 	tips = $( ".validateTips" );
	 
	 // this is to enable disable the user logout menu
	 m = document.getElementById("userMenu");
	 if(m.style.display == "none") {
			 m.style.display ='block';
	 }
	 
	 function updateTips( t ) {      
		 tips        
		 .text( t )        
		 .addClass( "ui-state-highlight" );      
		 setTimeout(function() { tips.removeClass( "ui-state-highlight"); }, 500 );    
		 }
	 
	 function checkRegexp( o, regexp, n ) { 
		 
		 if ( !( regexp.test( o.val() ) ) ) 
		 {        
			 o.addClass( "ui-state-error" );        
			 updateTips( n );        
			 return false;      
			 } 
		 else {        
			 return true;      
			 }    
		 }
	 
	 function checkLength( o, n, min, max ) {      
		 if ( o.val().length > max || o.val().length < min ) {        
			 o.addClass( "ui-state-error" );        
			 updateTips( "Input provided for " + n + " is incorrect." );        
			 return false;      
			 } 
		 else {        
			 return true;      
			 }    
		 }
	 
	 var reGoodDate = /^(?:(0[1-9]|1[012])[\/.](0[1-9]|[12][0-9]|3[01])[\/.](19|20)[0-9]{2})$/;
	 var reGoodTime = /^(0?[1-9]|1[012])(:[0-5]\d) [APap][mM]$/;
	 
	 $( "#datetimedialog" ).dialog({      
		 autoOpen: false,      
		 show: {        
			 effect: "blind",        
			 duration: 1000      
			 }, 
			 buttons: {  
				 	"Ok": function() {
				 		var bValid = true;
				 		allFields.removeClass( "ui-state-error" );				 		
				 		bValid = bValid && checkLength( playd, "Play Date", 1, 10 );          
				 		bValid = bValid && checkLength( playt, "Play Time", 1, 8 );
				 		
				 		bValid = bValid && checkRegexp(playd,reGoodDate,"Correct the Date Format (mm/dd/yyyy)");
				 		bValid = bValid && checkRegexp(playt,reGoodTime,"Correct the Time Format (##:## AM/PM)");
				 		
				 		 if ( bValid ) {				 			  				 		 
				 			 var score = jQuery("#scorecardgrid").jqGrid('getRowData',4);
				 			 jQuery("#scorecardgrid").jqGrid('saveRow',
				 					 						4, 
				 					 						{ url : 'save', 
				 				 							 "extraparam" : {"obj":$("#profilepage").serialize(),
				 				 								 			"playerResult_id":score["playerResult_id"],
				 				 								 			"gc_id":$('#golfcourse').val(),
				 				 								 			"gcd_id":$('#teetype').val(),
				 				 								 			"date_played":$('#playd').val(),
				 				 								 			"time_played":$('#playt').val()
				 				 								 			} });
				 			$( this ).dialog( "close" );}
				 		 },
				 	"Cancel": function() {$( this ).dialog( "close" );}
			 	} , 
			 hide: {        
				 effect: "explode",        
				 duration: 10
				 },
				 close: function() {        
					 allFields.val( "" ).removeClass( "ui-state-error" );      
					 }
			 });
	 
		$('#playd').datepicker( {					
			dateFormat : "mm/dd/yy",
			minDate : "-1M -10D",
			maxDate : 0,
			changeMonth : true,
			changeYear : true,
			showButtonPanel : true,
			showAnim : "blind"
		});
		
		$("#playt").timePicker({
		  startTime: "07:00", // Using string. Can take string or Date object.
		  endTime: new Date(0, 0, 0, 19, 30, 0), // Using Date object here.
		  show24Hours: false,
		  separator: ':',
		  step: 15
		});   
	 
	 
var firstClick;
firstClick =0;
function reloadScorecard(p_postdata)
{
	firstClick =firstClick+1;
	$("#scorecardgrid").setGridParam(
			{
			  url:'srch',
			  datatype:'json',
			  mtype:'GET',
			  page:1, 
			  postData: p_postdata
			 }).trigger('reloadGrid');
	
	 $('#scorecardgrid').jqGrid({
		 	url:'srch',
			datatype:'json',
			mtype:'GET',
			colNames : [ 'ResultID','Player','1', '2', '3', '4', '5', '6', '7',
					'8', '9', 'OUT', '10', '11', '12', '13',
					'14', '15', '16', '17', '18', 'OUT',
					'Total' ],
			colModel : [
			            {
			            	name : 'playerResult_id',
							index : 'playerResult_id',
							width: 10,
							align:"center"	,
							hidden:true
			            },
			 {
				name : 'col_0',
				index : 'col_0',
				width : 30,
				align : "center",
				sortable: false
				//editable : false								
			},{
				name : 'col_1',
				index : 'col_1',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_2',
				index : 'col_2',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_3',
				index : 'col_3',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_4',
				index : 'col_4',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_5',
				index : 'col_5',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_6',
				index : 'col_6',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_7',
				index : 'col_7',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_8',
				index : 'col_8',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_9',
				index : 'col_9',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'out1',
				index : 'out1',
				width : 20,
				align : "center"
				//editable : false
			}, {
				name : 'col_10',
				index : 'col_10',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_11',
				index : 'col_11',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_12',
				index : 'col_12',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_13',
				index : 'col_13',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_14',
				index : 'col_14',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_15',
				index : 'col_15',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_16',
				index : 'col_16',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_17',
				index : 'col_17',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'col_18',
				index : 'col_18',
				width : 20,
				align : "center",
				editable : true,
				editrules:{required:true, number:true, minValue:1}
			}, {
				name : 'out2',
				index : 'out2',
				width : 20,
				align : "center"
				//editable : false
			}, {
				name : 'total',
				index : 'total',
				width : 20,
				align : "center"
				//editable : false
			}, ],
			onSelectRow : function(id) {								
				if (id == 4) {
					//alert(id);
					$('#scorecardgrid').jqGrid('editRow',id,true);																	
				}
			},			
			  postData: $("#profilepage").serialize(),			
	          rowNum:10,		   	
	          rowList:[10,20,40,60],		   	
	          height: "100%",		   	
	          autowidth: true,
	          rownumbers: false,		   	
	          pager: '#pager3',		   			    
	          viewrecords: false,		    
	          caption:"ScoreCard",		    		    
	          loadonce: true,
	          sortable: false,
	          pgbuttons:false,
	          pgtext:false,
	          rowList:[],
	          loadComplete: function (){
					var rowData1 = $("#scorecardgrid").jqGrid("getRowData",3);
					var rowData4 = $("#scorecardgrid").jqGrid("getRowData",4);
					var color;
					var diff, holePar, colName, playerScore;
					// need to do this for ll columns excpect out and total
					for (var i=1;i<=20;i++){
						if (i != 10 && i != 20)
							{
								colName="col_" + i;
								holePar = rowData1[colName];
								playerScore = rowData4[colName];
								diff = playerScore - holePar;									
								switch(diff)
								{
								case -2:
									color= "orange";
									break;
								case -1:
									color= "green";
									break;	
								case 0:
									color= "transparent";
									break;	
								case 1:
									color= "orange";
									break;	
								case 2:
									color= "red";
									break;	
								default:
									color= "red";
									break;
								}
								//alert(color);
								$("#scorecardgrid").jqGrid("setCell",4,colName,"",{background:color});
							}
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
	 
	 if (firstClick==1)
		 {
		 	jQuery("#scorecardgrid").jqGrid('navGrid','#pager3',{edit:false,add:false,del:false,search:false})
			 						.navButtonAdd('#pager3',{caption:"Save", 
							         onClickButton: function(){ 
							        	 		$( "#datetimedialog" ).dialog( "open" );							       	  				                                                                          
							                                  }, 
							          position:"last", title:"", cursor: "pointer"
			 						});	 
		 }
}
				$('#playdate').datepicker( {					
					dateFormat : "mm/dd/yy",
					minDate : "-1M -10D",
					maxDate : 0,
					changeMonth : true,
					changeYear : true,
					showButtonPanel : true,
					showAnim : "blind"
				});
				

				function scorecheck(value, colname)
				{
					alert('inside score check' + value + colname);
					return [false,""];
				}
								
				/*function populateCourse(data)
				 {
					 $('#golfcourse').empty();
					 $('#golfcourse').append("<option value=\"" + -9 + "\">--Please Select--</option>");
					 for ( var i = 0, len = data.length; i < len; ++i) {
				            var user = data[i];
				            $('#golfcourse').append("<option value=\"" + user.golfcourse_id + "\">" + user.course_name+ "</option>");
				    }	
				 }*/
				
				$('#golfcourse').change(function() {
					if ($('#golfcourse').val()) {
						//alert("golfcourse selected");
						getTeeTypes();
					}
				});
				
				/* Blur does not work once chosen was applied
				 * $('#golfcourse').blur(function() {
					if ($('#golfcourse').val()) {
						alert("golfcourse selected");
						getTeeTypes();
					}
				});*/
				
				function getTeeTypes() {
					var urlVal = 'teetype/' + $('#golfcourse').val();
					//alert("Tee Type URL " + urlVal);
					$.ajax( {
						url : urlVal,
						datatype : 'json',
						mtype : 'GET'
					}).done(function(data) {
						//alert("Ready to populate teetypes on JSP");
						populateTeeTypes(data);
					});
				}
				
				function populateTeeTypes(data)
				 {
					 $('#teetype').empty();
					 $('#teetype').append("<option value=\"" + -9 + "\">--Please Select--</option>");
					 for ( var i = 0, len = data.length; i < len; ++i) {
				            var user = data[i];
				            $('#teetype').append("<option value=\"" + user.golfcoursedetail_id + "\">" + user.tee_type+ "</option>");
				    }	
					 $('#teetype').trigger('chosen:updated');
				 }
				
	
				$("#search").click(function(){
					
					var validFlag;					
					validFlag = validateAjaxCall('srchtest',$("#profilepage").serialize());
					//alert('Valid Flag is: '+ validFlag);
					if (validFlag == 0){
						// call reload grid to get scorecard
					   reloadScorecard($("#profilepage").serialize());
					}
					else
						{
						reloadScorecard(null);
						}
				});
				
			});