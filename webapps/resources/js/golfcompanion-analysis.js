 $(document).ready(function() {
	 
	 $('#golfcourse').chosen();

	 // this is to enable disable the user logout menu
	 m = document.getElementById("userMenu");
	 if(m.style.display == "none") {
			 m.style.display ='block';
	 }

	 var playerResult_id = document.getElementById("plyResultId").value;
	 
	 if (playerResult_id != -9) 
	 {
		 reloadScorecard($("#profilepage").serialize(),playerResult_id);		 
	 }
//	 s=document.getElementById("scorecard");
//	 s.style.display="none";
	 
	 var plot = [];
	 
	 // calculate total Pars, eagles, birdies, bogey's in the scorecard Loaded
	 var roundPars = 0, 
	 roundEagles = 0, 
	 roundBirdies = 0, 
	 roundBogeys = 0, 
	 roundBogeyPlus = 0;
	 
	 $.ajax({
		 url:'stats',
		 mtype:'GET',
		 datatype:'json',
		 async:false,
		 success: function(data)
		 {
			 if (!jQuery.isEmptyObject(data)){
				 for ( var i = 0, len = data.rows.length; i < len; ++i) {					 
					 plot.push([[data.rows[i].eagles,'Eagles'],[data.rows[i].birdies,'Birdies'],[data.rows[i].pars,'Pars'],
					            [data.rows[i].bogeys,'Bogeys'], [data.rows[i].doublebogeys,'Double Bogeys'], [data.rows[i].doublebogeyplus,'DoublePlus']]);
				 }
				 $.jqplot('statschart',  plot, {
					 title: 'Player Statistics',					 
					 series:[{renderer:$.jqplot.BarRenderer}],
					 seriesDefaults: {
						 rendererOptions: {                
							 barDirection: 'horizontal'            
							 },					
							 pointLabels: { show: true, location: 'e', edgeTolerance: -15 }
					 },
					 axesDefaults: {        
						 tickRenderer: $.jqplot.CanvasAxisTickRenderer ,        
						 tickOptions: {          
							 angle: -30,          
							 fontSize: '10pt',
							 showGridline: false
								 }    
				 }, 				 
				 axes: {      
					 yaxis: {        
						 renderer: $.jqplot.CategoryAxisRenderer      
						 }
					 }
				 });
			 }
		 }
	 });
	 
	 var eagles = [];
	 var birdies = [];
	 var pars = [];
	 var bogeys = [];
	 var doublebogeys = [];
	 var doubleplus = [];
	 
	 $.ajax({
		 url:'allscores',
		 mtype:'GET',
		 datatype:'json',
		 async:false,
		 success: function(data)
		 {
			 if (!jQuery.isEmptyObject(data)){
				 for ( var i = 0, len = data.rows.length; i < len; ++i) {					 
					 eagles.push([data.rows[i].date_played,data.rows[i].eagles]);
					 birdies.push([data.rows[i].date_played,data.rows[i].birdies]);
					 pars.push([data.rows[i].date_played,data.rows[i].pars]);
					 bogeys.push([data.rows[i].date_played,data.rows[i].bogeys]);
					 doublebogeys.push([data.rows[i].date_played,data.rows[i].doublebogeys]);					 
					 doubleplus.push([data.rows[i].date_played,data.rows[i].doublebogeyplus]);
				 }
				 $.jqplot('scoreschart',  [eagles,birdies,pars,bogeys,doublebogeys,doubleplus], {
					 title: 'All Scores',
					 series:[
						 {markerOptions: {style:'diamond'}},
						 {markerOptions: {style:'circles'}},
						 {markerOptions: {style:'square'}},
						 {markerOptions: {style:'filledsquare'}},
						 {markerOptions: {style:'filledcicle'}},
						 {markerOptions: {style:'none'}}
					 ],
					 legend: {
					        show: true,
					        renderer: $.jqplot.EnhancedLegendRenderer,
					        labels: ['Eagles','Birdies','Pars','Bogeys','Double Bogeys','Double Plus'],
					        placement: 'outsideGrid',
					        location: 'ne'     // compass direction, nw, n, ne, e, se, s, sw, w.					        
					    },
					 seriesDefaults: {						 					
							 pointLabels: { show: true, location: 'e', edgeTolerance: -15 }
					 },
					 axesDefaults: {        
						 tickRenderer: $.jqplot.CanvasAxisTickRenderer ,        
						 tickOptions: {          
							 angle: -30,          
							 fontSize: '10pt',
							 showGridline: false
								 }    
				 }, 
				 highlighter: {        
					 show: true,        
					 sizeAdjust: 7.5      
					 },
				 axes: {      
					 xaxis: {        
						 renderer: $.jqplot.CategoryAxisRenderer      
						 }
					 }
				 });
			 }
		 }
	 });	 

// chart ploting 
	 //$.jqplot('chartdiv',  [[[1, 2],[3,5.12],[5,13.1],[7,33.6],[9,85.9],[11,219.9]]]);
	 
	 $("#view").click(function(){ 		
		 reloadScorecard($("#profilepage").serialize(),$('#scores').val());
															
		});

	 var firstClick;
	 firstClick =0;
	 function reloadScorecard(p_postdata,result_id)
	 {	 
		 firstClick =firstClick+1;
		 var urlVal = 'view/' + result_id;
		 
			//s.style.display="block";
				$("#scorecardgrid").setGridParam(
								{
								  url:urlVal,
								  datatype:'json',
								  mtype:'GET',
								  page:1, 
								  postData: p_postdata
								 }).trigger('reloadGrid'); 
		 
	 $('#scorecardgrid').jqGrid({
		 	url:urlVal,
			datatype:'json',
			mtype:'GET',
			async: false,
			colNames : [ 'ResultID','Player','1', '2', '3', '4', '5', '6', '7',
					'8', '9', 'OUT', '10', '11', '12', '13',
					'14', '15', '16', '17', '18', 'OUT',
					'Total' ],
			colModel : [
			            {
			            	name : 'playerResult_id',
							index : 'playerResult_id',
							width: 10,
							align:"center",	
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
									color= "blue";
									roundEagles = roundEagles + 1;
									break;
								case -1:
									color= "green";
									roundBirdies = roundBirdies + 1;
									break;	
								case 0:
									color= "transparent";
									roundPars = roundPars + 1;
									break;	
								case 1:
									color= "orange";
									roundBogeys = roundBogeys + 1;
									break;	
								case 2:
									color= "red";
									roundBogeyPlus = roundBogeyPlus + 1;
									break;	
								default:
									color= "red";
									roundBogeyPlus = roundBogeyPlus + 1;
									break;
								}
								//alert(color);
								$("#scorecardgrid").jqGrid("setCell",4,colName,"",{background:color});							
						}
				}
					//reload done call display chart
					 displayScoreGraph();
			},
			  postData: $("#scorecardgrid").serialize(),			
	          rowNum:10,		   	
	          rowList:[10,20,40,60],		   	
	          height: "100%",		   	
	          autowidth: true,
	          rownumbers: false,
	          //rownumbers: true,		   	
	          pager: '#pager3',		   	
	         // sortname: 'searchresult_id',		    
	          viewrecords: false,		    
	          //sortorder: "desc",
	          //multiselect: true,
	          caption:"ScoreCard",		    
	          emptyrecords: "Empty records",		    
	          loadonce: true,
	          sortable: false,
	          pgbuttons:false,
	          pgtext:false,
	          rowList:[],	          		   
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
                              buttonicon:"ui-icon-edit", 
                              onClickButton: function(){ 
                                                         //alert("Adding Row");
                            	  				var score = jQuery("#scorecardgrid").jqGrid('getRowData',4);
                            	  				//alert(score["playerResult_id"]);
                                                         jQuery("#scorecardgrid").jqGrid('saveRow',4, { url : 'save', "extraparam" : {"obj":$("#profilepage").serialize(),"playerResult_id":score["playerResult_id"],"gc_id":$('#golfcourse').val(),"gcd_id":$('#teetype').val(),"date_played":$('#playdate').val()} });                                                                           
                                                       }, 
                               position:"last", title:"", cursor: "pointer"
                              })
                              .navButtonAdd('#pager3',{caption:"Cancel",buttonicon:"ui-icon-cancel",
                            	  onClickButton: function(){
                            		  jQuery("#scorecardgrid").jqGrid('restoreRow',4);
                            	  }
                              });
	 }	 
	 }	 
	 
	 function displayScoreGraph()
	 {
		 var roundPlot = [];
		 //alert("will populate the jqplot for the current corecard " + roundPars);		 					 
		 roundPlot.push([[roundEagles,'Eagles'],[roundBirdies,'Birdies'],[roundPars,'Pars'],
			           [roundBogeys,'Bogeys'], [roundBogeyPlus,'Bogeys Plus']]);		 
		 //roundPlot.push([[0,'Eagles'],[4,'Birdies'],[7,'Pars'],
			//	            [4,'Bogeys'], [3,'Bogeys Plus']]);
		 $.jqplot('roundstats',  roundPlot, {
			 title: 'Round Statistics',					
			seriesColors:['#00FF00', '#0000FF', '#C0C0C0', '#C7754C', '#FF0000'],
			 series:[{renderer:$.jqplot.BarRenderer}],			
			 seriesDefaults: {
				 rendererOptions: {                
					 barDirection: 'horizontal'
					,varyBarColor: true	 
					 },					
					 pointLabels: { show: true, location: 'e', edgeTolerance: -15 }
			 },
			 axesDefaults: {        
				 tickRenderer: $.jqplot.CanvasAxisTickRenderer ,        
				 tickOptions: {          
					 angle: -30,          
					 fontSize: '10pt',
					 showGridline: false
						 }    
		 }, 				 
		 axes: {      
			 yaxis: {        
				 renderer: $.jqplot.CategoryAxisRenderer      
				 }
			 }
		 });
	 }

				function scorecheck(value, colname)
				{
					alert('inside score check' + value + colname);
					return [false,""];
				}
				
				/*function populateCourse(data)
				 {
					 $('#golfcourse').empty();
					 for ( var i = 0, len = data.length; i < len; ++i) {
				            var user = data[i];
				            $('#golfcourse').append("<option value=\"" + user.golfcourse_id + "\">" + user.course_name+ "</option>");
				    }					 					 
				 }*/
				
				// Reset all the select drop downs.
				$("#reset").click(function(){
					$('#golfcourse').empty();
					$('#golfcourse').append("<option value=-9>--Please Select</option>");
					$('#scores').empty();
					$('#scores').append("<option value=-9>--Please Select</option>");
				});
			
				//var lastsel2;
				$("#search").click(function(){ 		
					//alert($('#golfcourse').val());
					var validFlag;					
					validFlag = validateAjaxCall('homeredirectvalidate',$("#profilepage").serialize());					
					if (validFlag == 0){
						// call reload grid to get scorecard
						//alert('Page has no errors');								
					var urlVal = 'result/' + $('#golfcourse').val();
							$.ajax({
								url:urlVal,
								mtype:'GET'								
								//postData: $("#profilepage").serialize()								
							})
							.done(function (data) {
									 // alert('Search complete');								   
									    populateScores(data);								   
									   
								  });																					
				        }
					});
				
				
				function populateScores(data)
				 {
					 $('#scores').empty();
					 for ( var i = 0, len = data.length; i < len; ++i) {
				            var user = data[i];
				            $('#scores').append("<option value=\"" + user.playerResult_id + "\">" + user.date_played +"-"+ user.tee_type + "</option>");
				    }	
					 $('#scores').trigger("chosen:updated");
					 document.getElementById("view").disabled = false;
				 }							
													
			});