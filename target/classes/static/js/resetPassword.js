
$(function () {
    "use strict";


    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('#btnSubmit').click(function(e){
        e.preventDefault();
        $("#lblPasswordError").show();
        $("#lblConfirmPasswordError").show();
        $("#lblMatchPasswordError").show();
        var password = $('#txtPassword').val();
        var confirmPassword = $('#txtConfirmPassword').val();
        var validatePassword = false;
        var validateConfirmPassword = false;

        // $(".error").remove();
        if (password.length < 1) {
            $('#lblPasswordError').text('Please Enter Password');
            
        } 
        else {
        	if(confirmPassword.length < 5){
            	$('#lblPasswordError').text('Password length can not be less than 5 characters');
            }
        	else{
	            $('#lblPasswordError').text('');
	            validatePassword = true;
	        }
        }

        if (confirmPassword.length < 1) {
            $('#lblConfirmPasswordError').text('Please Retype Password');
            
        } else {
            $('#lblConfirmPasswordError').text('');
            validateConfirmPassword = true;
        }
        
        if(password == confirmPassword){
            if(validateConfirmPassword && validatePassword){
                $('#validateform').submit();
            }
        }else{
            $('#lblMatchPasswordError').text('Passwords do not match'); 
        }
    });

    $('#txtPassword').on('input',function(){
        $("#lblPasswordError").hide();
        $("#lblMatchPasswordError").hide();
        $("#serverError").hide();
        $('#serverError').text('');
    });

    $('#txtConfirmPassword').on('input',function(){
        $("#lblConfirmPasswordError").hide();
        $("#lblMatchPasswordError").hide();
        $("#serverError").hide();
        $('#serverError').text('');
    });

    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });
    
   


  


    function validate (input) {
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }1
        }
        else {
            if($(input).val().trim() == ''){
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        //$(thisAlert).addClass('alert-validate');
    }

   

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        //$(thisAlert).removeClass('alert-validate');
    }
    
    /*==================================================================
    [ Show pass ]*/
    var showPass = 0;
    $('.btn-show-pass').on('click', function(){
        if(showPass == 0) {
            $(this).next('input').attr('type','text');
            $(this).find('i').removeClass('fa-eye');
            $(this).find('i').addClass('fa-eye-slash');
            showPass = 1;
        }
        else {
            $(this).next('input').attr('type','password');
            $(this).find('i').removeClass('fa-eye-slash');
            $(this).find('i').addClass('fa-eye');
            showPass = 0;
        }
        
    });
    

})
