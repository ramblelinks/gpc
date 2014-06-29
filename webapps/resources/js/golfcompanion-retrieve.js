/**
 * 
 */

 $(document).ready(function() {
	 

	 finddiv = document.getElementById("findaccount");
	 resetdiv = document.getElementById("resetpassword");
	 
	 resetdiv.style.display = "none";

	$('#cancel').click(function(){
		window.location = '/golfcompanion/login';
	});
	
	$('#find').click(function(){
		$.ajax({
				url:"find",
				type:"GET",
				data:$("#retrievepage").serialize(),
				success:function(data){
					if (data=="valid")
						{
							finddiv.style.display = "none";	
							resetdiv.style.display = "block";							
						}
					else
						{			
							$.notify("Could not find user. Contact admin at info@golfprocompanion.com", "Warning");
							$("#finderr").html('<span style="color:red;font-size:8pt">Could not find user. Contact admin at info@golfprocompanion.com</span>');
						}
				}	
				});
	});
	
	$('#reset').click(function(){
		
		
		var validFlag;
   	 	validFlag = validateAjaxCall('resetvalidation',$("#retrievepage").serialize());
	   	 if (validFlag == 0) 
	   	 {
			$.ajax({
				url:"reset",
				type:"POST",
				data:$("#retrievepage").serialize(),
				success:function(data){
					if (data == "Success")
						{									
						$.notify("Password reset successfull!!", "success");
						$("#message").html('<a href="#" onclick=redirect_login()>Click Here for login</a>');					
						}
				}
				});
	   	 }
	});
	 
 });
 
 function redirect_login(){
		window.location = "/golfcompanion/login";
	}	
