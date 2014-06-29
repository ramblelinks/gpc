/**
 * 
 */

 $(document).ready(function() {
	 
	 $(".chosen-select").chosen();
	 
	 var theform = document.getElementById('ppform');
	 if ($('#errorchk').val() == "N")
		 {		 
			if (document.all || document.getElementById) {
				for (i = 0; i < theform.length; i++) {
				var formElement = theform.elements[i];
					if (true) {
						if (formElement.id != "editprofile") {
						formElement.disabled = true;
						}
					}
				}
			}
		 }		
		
	 // this is to enable disable the user logout menu
	 m = document.getElementById("userMenu");
	 if(m.style.display == "none") {
			 m.style.display ='block';
	 }
	
	 $("#editprofile").click(function(){
		 // enable the elements of the page
		 if (document.all || document.getElementById) {
				for (i = 0; i < theform.length; i++) {
				var formElement = theform.elements[i];
					if (true) {
						if (formElement.id != "editprofile") {
						formElement.disabled = false;
						}
					}
				}
			}
		 //document.getElementById('editsection').disabled = false;			 
		 //document.getElementById('saveprofile').disabled = false;
	 });
	 
	 	 
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
	 
	 $('#zip_code').blur(function(){		 
			if ($('#zip_code').val()) {
				getGeocodes($('#zip_code').val());							
			}
		});

	function getGeocodes(zipCode) {			  
				 var geocoder = new google.maps.Geocoder();
				 var finalAddr;
			      if (geocoder) {
			    	  var addr = $('#street_address1').val();
			    	  var city = $('#city').val();
			    	  var state = $('#state_id').val();
			    	  if (addr && city) {
			    		  finalAddr = addr + " " + city + ", " + state + " " + zipCode;			    		  
			    	  }
			    	  else {
			    		  finalAddr = zipCode;  
			    	  }			    	  
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