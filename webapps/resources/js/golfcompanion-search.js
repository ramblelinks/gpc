var golfCourses;

/**
 * 
 */
 $(document).ready(function() { 
	 	 
	$(".chosen-select").chosen();
   	 
   	var subcity = document.getElementById('subcity');
   	subcity.style.visibility = 'hidden';

	 
	 function sleep(milliseconds) {
		 var start = new Date().getTime();
		 while ((new Date().getTime() - start) < milliseconds){
		 // Do nothing
		 }
		 }
	 
	$('#zipcode').blur(function(){
		$("#zipcode_err").html("");
		$("#city_err").html("");
		var zip = $('#zipcode').val();
		if (zip) {
			//alert(zip);
			//var isValidZip = /(^\d{5}$)/.test(zip);			
			//if (!isValidZip)
			//	{
				//$("#zipcodeerr").html('<span style="color:red;font-size:8pt">Please enter a valid zip code (#####)</span>');
					//$('#zipcode').focus();
			//		return false;
			//	}
			var geoStatus ;
			geoStatus =getGeocodes(zip);			
			setTimeout(function() {getGolfCoursesByZipCode();},500);			
		}
	});
		
	 function getGeocodes(zipCode) {
		    // geocoder = new google.maps.Geocoder();
		 $('#latitude').val('');
		 $('#longitude').val('');
			 var geocoder = new google.maps.Geocoder();

		      if (geocoder) {		    	  
		         geocoder.geocode({ 'address': zipCode }, function (results, status) {
		        	// alert(status);
		            if (status == google.maps.GeocoderStatus.OK) {
		            	$('#latitude').val(results[0].geometry.location.lat());
		            	$('#longitude').val(results[0].geometry.location.lng());
		            	return true;
		            } 
		            else {		            	
		            	return false;
		            }
		         });
		      }
		 }
	 
	 // attempt to get data via JSON object so that Id can be used
	 
	 $('#city').autocomplete({		 
		source: function(request, response){
			$.ajax({
				url:'cities',
				mtype:'GET',
				datatype:'json',
				minlength:0,
				data: {
				      name: "city",
				      term: request.term
				     },			
			success: function(data){
				$("#city_err").html("");
				$("#zipcode_err").html("");
				subcity.style.visibility = 'hidden';
				response($.map(data, function(item){				
					return{
					label: item.city_name + ", " + item.state.stateName,
					value: item.city_name,
					id: item.city_id
					};
				}));
			}
		});
		},
		select: function( event, ui ) {   
			$('#city_id').val(ui.item.id);
			getGolfCoursesByCity();
			//setTimeout(function() {getGolfCoursesByCity();},20);
			},
		change: function(event,ui){	
			try{
			$('#city_id').val(ui.item.id);			
			setTimeout(function() {getGolfCoursesByCity();},100);
			}
			catch(err)
			{
				$('#city_id').val("");	
				if ($('#city').val()) {
				$.ajax({
					url:'cities',
					mtype:'GET',
					datatype:'json',
					minlength:0,
					data: {
					      name: "city",
					      term: $("#city").val()
					     },		
				success: function(data) {
					//alert('Data returned for the city name typed');
					subcity.style.visibility = 'visible';
					$('#subcity').empty();
					$("#subcity").append($('<option selected="selected"></option>').val(-9).html("--Select City--"));
					// set city id to -9 to begin with
					$('#city_id').val(-9);
					 for ( var i = 0, len = data.length; i < len; ++i) {
				            var user = data[i];	         
				           $("#subcity").append($('<option></option>').val(user.city_id).html(user.city_name + '-' + user.state.stateName));
					 }
					 $("#city_err").html('<span style="color:blue;font-size:8pt">Please select an appropriate city</span>');
					
					}				
				});	
				}
			}			
			//getGolfCoursesByCity(ui.item.id);			
		}
	 });
	 
	 
	 $('#subcity').change(function(){
		 //alert($('#subcity').val());
		 // set the value 
		 $('#city_id').val($('#subcity').val());
		 setTimeout(function() {getGolfCoursesByCity();},150);
	 });
 
	 
	 $('#radius').change(function(){
		 if ($('#zipcode').val()) {
		 getGolfCoursesByZipCode();
		 }
	 });
	 
	 function getGolfCoursesByCity(){
		 //alert($('#city_id').val());
		 if ($('#city_id').val()!=-9){
			 getGeocodes($('#city_id').val());
			 
			 var urlVal ="golfcourse/city"; //" + data ;
			 $.ajax({cache: false,
				 	url: urlVal,
					datatype: 'json',
					mtype: 'GET',
					async: false,
					data: $("#profilepage").serialize()
					}
					)
					.done(function(data) {	
						//alert("get gc by city");
						populateCourse(data);    					
					});    				
		 }
	 	}  
	 
	 function getGolfCoursesByZipCode(){		 
		 //var urlVal ="golfcourse/zipcode/" + data + "/" + radius ;
		 //alert('Inside Js for search get golf course by Zip code');
		 var urlVal ="golfcourse/zipcode" ;
		 $.ajax({
			 	cache: false,
			 	url: urlVal,
				datatype: 'json',
				mtype: 'GET', 
				async: false,
				data: $("#profilepage").serialize()
				 })
				.done(function(data) {						
					populateCourse(data);    					
				});    				
	 	}
	 
	  
	 function populateCourse(data)
	 {
		 golfCourses = "";
		 //$('#golfcourse').empty();
		 for ( var i = 0, len = data.length; i < len; ++i) {
	            var user = data[i];	         
	       //     $("#golfcourse").append($('<option selected="selected"></option>').val(user.golfcourse_id).html(user.course_name));
	            if (golfCourses != "") {	            
	            	golfCourses = golfCourses + user.golfcourse_id + ":" + user.course_name + ";";
	            }
	            else
            	{	            	
	            	golfCourses = user.golfcourse_id + ":" + user.course_name + ";";
            	}	            
	    }	
		 var sPath=window.location.pathname; 
    	 var sPage = sPath.substring(sPath.lastIndexOf('scorecard'));     	 
		 if (sPage != -1)
			 {
			 $('#golfcourse').empty();
			 $('#golfcourse').append("<option value=\"" + -9 + "\">--Please Select--</option>");
			 for ( var i = 0, len = data.length; i < len; ++i) {
		            var user = data[i];
		            $('#golfcourse').append("<option value=\"" + user.golfcourse_id + "\">" + user.course_name+ "</option>");
		    }	
			 }
		 //$("#golfcourse").multiselect("refresh");
		 $('#golfcourse').trigger('chosen:updated');
	 }
	 
	 var lastsel;

	 function getGolfCourses() { 
			var urlVal = 'golfcourse/' + $('#golfclubs').val();
			$.ajax({url: urlVal,
					datatype: 'json',
					mtype: 'GET' }
					)
					.done(function(data) {						
						populateCourse(data);  						
					});    				
		 	} 
	  
 });