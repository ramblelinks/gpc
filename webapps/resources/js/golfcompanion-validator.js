/**
 * this is the JS where we issue the ajax validation in controllers and then parse the errors if any 
 */

 function validateAjaxCall(p_url,p_data)
 {
	 $('span[id*="_err"]').html('');
	 $('input[id*=]').removeClass('ui-state-error');
	 $('select[id*=]').removeClass('ui-state-error');
	var validFlag = 0;
	 $.ajax({
			url:p_url,
			  datatype:'json',
			  mtype:'GET',						  
			  data: p_data,
			  async:false,
			  success: function(data)
			  {		
				  //alert(jQuery.isEmptyObject(data));
				  if (jQuery.isEmptyObject(data)){
					  validFlag= 0;
				  }
				  else
				  {
					for ( var i = 0, len = data.br.length; i < len; ++i) {
						//alert(data.br[i].defaultMessage);
						//alert(data.br[i].field);
						$("#"+ data.br[i].field +"_err").html('<span style="color:red;font-size:8pt">' + data.br[i].defaultMessage +'</span>');
						$("#"+ data.br[i].field ).addClass('ui-state-error');
						validFlag = 1;
					}
				  }				  
			  }	 
		});
	 //alert(validFlag);
	 return validFlag;	 
 }