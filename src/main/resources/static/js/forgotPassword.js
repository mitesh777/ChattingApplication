
$(function ($) {
    "use strict";


    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('#btnSubmit').click(function(e){
       
        e.preventDefault();
        $("#lblEmailError").show();
        $("#serverError").show();
        var email = $('#txtEmail').val();

        if (email.length < 1) {
            $('#lblEmailError').html('This field is required');
        } else {
            var regEx = /^[A-Za-z0-9][A-Za-z0-9._%+-]{0,63}@(?:[A-Za-z0-9-]{1,63}\.){1,125}[A-Za-z]{2,63}$/;
            var validEmail = regEx.test(email);
            if (validEmail) {
                $('#lblEmailError').text('');
                $('#validateform').submit();
            } else {
                $('#lblEmailError').text('Enter a valid Email Id');
            }
        }
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

    $('#txtEmail').on('input',function(){
        $("#lblEmailError").hide();
        $("#serverError").hide();
        $('#serverError').text('');
    });

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