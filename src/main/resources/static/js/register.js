
var email = false;
var username = false;
var phone = false;
var password = false;
var confirmPassword = false;
var validated = false;

function registerjsfunction() {
	
	email = false;
	username = false;
	phone = false;
	password = false;
	confirmPassword = false;
	validated = false;
	
	$("#reg-email-error").hide();
	$("#reg-userName-error").hide();
	$("#reg-phone-error").hide();
	$("#reg-newPassword-error").hide();
	$("#reg-confirmPassword-error").hide();	
	$("#reg-btnRegister").prop('disabled', true);
					
	$("#reg-userName").blur(function () {
		
        if ($("#reg-userName").val() == "") {
            $("#reg-userName-error").show();
            $("#reg-userName-error").text("Name Cannot Be Empty!");
            $("#reg-userName").focus();
        }
    });

	
	$("#reg-userName").on('input', function () {
		
	   if ($("#reg-userName").val() == "") {
           $("#reg-userName-error").show();
           $("#reg-userName-error").text("Name Cannot Be Empty!");
           $("#reg-userName").focus();
       }
	   else {
		   var filter = /^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$/;
		   var userName = $('#reg-userName').val();

	       if (filter.test(userName)) {
	            $("#reg-userName-error").hide();
	            username = true;
	       }
	       else {
	            $("#reg-userName-error").text("Enter a Valid Name");
	            $("#reg-userName-error").show();
		   }
	   }
    });
 	
	$("#reg-btnRegister").click(function () {
    	createUser();
    });
	
    $("#reg-email").blur(function () {

        $("#reg-email-error").hide();
        checkAllValidated();
        
        if ($("#reg-email").val().length == 0) {
            $("#reg-email-error").val("Email ID Cannot Be Empty!");
            $("#reg-email-error").show();
        }
        
        var sEmail = $('#reg-email').val();
        var filter = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;  

        if (filter.test(sEmail)) {
            $("#reg-email-error").hide();
	        email = true;
        }
        else {
            $("#reg-email-error").text("Enter Valid Email ID!");
            $("#reg-email-error").show();
	    }
    });


    $("#reg-phone").blur(function () {
       	checkAllValidated();
    });

    $("#reg-phone").on('input', function () {

        if ($("#reg-phone").val().length == 0) {
            $("#reg-phone-error").text("Phone Number Cannot Be Empty!");
            $("#reg-phone-error").show();
        }
        else{
        	var phonefilter = /^[1-9]{1}[0-9]{9}$/;
            var phoneNo = $("#reg-phone").val();
            if (!phonefilter.test(phoneNo)) {
                $("#reg-phone-error").text("Enter a valid 10 Digit  number");
                $("#reg-phone-error").show();
            }
            else{
                $("#reg-phone-error").hide();
            	phone = true;
            }
        }
    });

    $("#reg-newPassword").blur(function () {
        if ($("#reg-newPassword").val().length == 0) {
            $("#reg-newPassword-error").text("Password Cannot Be Empty");
            $("#reg-newPassword-error").show();
        }
    });

    $("#reg-newPassword").on('input', function () {
    	
    	checkAllValidated();
    	
    	if ($("#reg-newPassword").val() == "") {
            $("#reg-newPassword-error").show();
            $("#reg-newPassword-error").text("Password Cannot Be Empty!");
            $("#reg-confirmPassword").val("");
        }
        else {
            if ($("#reg-newPassword").val().length < 8) {
                $("#reg-newPassword-error").show();
                $("#reg-newPassword-error").text("Password Should Have Atleast 8 Characters!");
                $("#reg-confirmPassword").val("");
            }
            else {
                $("#reg-newPassword-error").hide();
                $("#reg-confirmPassword").val("");
                password = true;
            }
        }
    });

     $("#reg-confirmPassword").focus(function(){
        if($("#reg-newPassword").val().length==0){
            $("#reg-newPassword").focus();
        }
     });

    $("#reg-confirmPassword").on('input', function () {
    	
    	checkAllValidated();
    	
        if ($("#reg-confirmPassword").val() == "") {
            $("#reg-confirmPassword-error").text("Password Cannot Be Empty!");
            $("#reg-confirmPassword-error").show();
        }
        else {
            if ($("#reg-confirmPassword").val() != $("#reg-newPassword").val()) {
                $("#reg-confirmPassword-error").text("Passwords entered do not Match. Please enter again");
                $("#reg-confirmPassword-error").show();
                confirmPassword = false;
                checkAllValidated();
            }
            else {
                $("#reg-confirmPassword-error").hide();
                confirmPassword = true;
                checkAllValidated();
            }
        }
    });
 };
 
 
 function checkAllValidated() {
	
	if(validated == false && email == true && phone == true ) {
		validateAjaxCall();
	}
	
	 if(validated == true &&
	     email == true &&
		 username == true &&
		 phone == true &&
		 password == true &&
		 confirmPassword == true ) {
		 $("#reg-btnRegister").prop('disabled', false);
	 }
	 else
		 $("#reg-btnRegister").prop('disabled', true);
};

function validateAjaxCall() {
	
	if(validated == true)
		 return;
	
	var data = {
		"email" : $("#reg-email").val(),
		"phone" : $("#reg-phone").val()
	};
	
//    console.log(data);
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        Accept:"text/plain",
        url: "user/validcredentials",
        data: JSON.stringify(data),
        timeout: 100000,
        success: function (data) {
//            console.log("SUCCESS: ", data);
            
            if(data === "Valid Input") {
            	$("#reg-btnRegister").removeClass("btn-primary").addClass("btn-success");
	            $("#reg-email").prop('disabled', true);
	            $("#reg-phone").prop('disabled', true);
	            $("#reg-email-error").text("User is available");
	            $("#reg-email-error").removeClass("btn-outline-danger").addClass("btn-outline-success");
	            $("#reg-email-error").show();
	            validated = true;
	        }
            else {
            	if(validated == true)
            		return;
                $("#reg-email-error").text(data);
            	$("#reg-email-error").removeClass("btn-outline-success").addClass("btn-outline-danger");
            	$("#reg-email-error").show();
            }
        },
        error: function (e) {
//            console.log("ERROR: ", e);
        },
        done: function (e) {
//            console.log("DONE");
        }
    });
}

function createUser() {
	
	$("#reg-btnRegister").prop('disabled', true);
	
	var data = {
		"userName" : $("#reg-userName").val(),
		"emailId" : $("#reg-email").val(),
		"mobileNo" : $("#reg-phone").val(),
		"dateOfBirth" : $("#reg-dob").val(),
		"gender" : $("#reg-gender").val(),
		"password" : $("#reg-newPassword").val(),
	};
	
//    console.log(data);
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        Accept:"text/plain",
        url: "user/register",
        data: JSON.stringify(data),
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: ", data);
            console.log("User has been created");
            
            if(data.includes("added")) {
            	loadLogin();
            	userCreated = true;
            }
            else {
            	reloadRegister(data);
            }
        },
        error: function (e) {
//            console.log("ERROR: ", e);
            reloadRegister(e);
        },
        done: function (e) {
//            console.log("DONE");
        }
    });
}

function loadLogin() {
	$("#modalContent").load("html/success.html", function() {
		$('#register-fail').hide();
	});
}	

function reloadRegister(data) {
	$("#modalContent").load("html/success.html", function() {
    	$('#register-success').hide();
    	$("#register-error").text("Error : " + data.responseText.error);
    	$("#btn-register-fail").click(function() {
		   $('#modalContent').load('html/register.html', function() {
			   registerjsfunction();
		   });
    	});
    });
}

 