/**
 * 
 */

 $(document).ready(function() {
	 
	 $('form:first *:input[type!=hidden]:first').focus();
	 
	$('#reset').click(function(){
		window.location = '/golfcompanion/login/retrieve';
	});
		 
 });
