/**
 * 
 */

 $(document).ready(function() {
	 
	
	 $( "#dialog" ).dialog({      
		 autoOpen: false,      
		 show: {        
			 effect: "blind",        
			 duration: 1000      
			 },      
			 hide: {        
				 effect: "explode",        
				 duration: 1000      
				 }    
			 });
	 
	 $('#zipcode').blur(function(){		
			if ($('#zipcode').val()) {
				getGeocodes($('#zipcode').val());							
			}
		});

	function getGeocodes(zipCode) {			  
				 var geocoder = new google.maps.Geocoder();
				 var finalAddr;
			      if (geocoder) {
			    	  var addr = $('#addressOne').val();
			    	  var city = $('#city').val();
			    	  var state = $('#state').val();
			    	  if (addr && city) {
			    		  finalAddr = addr + " " + city + ", " + state + " " + zipCode;			    		  
			    	  }
			    	  else {
			    		  finalAddr = zipCode;  
			    	  }
			    	  //alert (finalAddr);
			         geocoder.geocode({ 'address': finalAddr }, function (results, status) {
			            if (status == google.maps.GeocoderStatus.OK) {
			            	$('#latitude').val(results[0].geometry.location.lat());
			            	$('#longitude').val(results[0].geometry.location.lng());		            	
			            } 
			            else {
			            	alert('error');
			              alert('No results found: ' + status);
			            }
			         });
			      }
			 }
	 
	 
 });

 
 function QuestionDialog() {	 
	 $( "#dialog" ).dialog( "open" );
 }