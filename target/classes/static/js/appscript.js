
$(function () {
    // console.log("api testing");
    // viewProfileById();
    // resetPassword();
    // updateUserProfile();

    $("#divMyProfile").click(function () {
        showMyProfile();
    });

    $("#divContactProfile").click(function () {
        imageUpload();
        showContactProfile();
    });

    $("#btnLogout").click(function () { location.href = "login.html"; });

    $("#btnSignUp").click(function () {
        console.log("btn click");
        $("#modalContent").load("html/register.html", function (response, status, xhr) {
            console.log("loaded");
            if (status == "error") {
                var msg = "There was an error: ";
                alert(msg + xhr.status + " " + xhr.statusText);
            }
            registerjsfunction();

        });
    });
});

function imageUpload() {

    var readURL = function (input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('.profile-pic').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#btnFileUpload").on('change', function () {
        readURL(this);
        $("#btnSubmitImage").removeAttr('disabled');
       });


    $("#iconUpload").click(function () {
        $("#btnFileUpload").click();
    });
};



// function to access session attributes
function getSessionAttribute() {

    return {
        userId: $("#userId").text(),
        userName: $("#userName").text(),
        emailId: $("#emailId").text(),
        mobileNo: $("#mobileNo").text(),
        pictureUrl: $("#pictureUrl").text(),
        accessToken: $("#accessToken").text(),
    }

};

// function to run ajax call to view profile for current session user and
// populate the fields.
function viewProfileById(userId) {
    /* var data = { "userId": userId; */
    // for testing
	
	var uId = ""+userId+"";
	
    var data = { "userId": uId};
    var successful = 0;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "viewUserProfile",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 10000,
        success: function (data) {
            console.log("SUCCESS: view user profile ", data);
            // if(data.err==0)
           if(true)
        	 {$("#user_name").text(data.userName);
            $("#email").text(data.emailId);
            $("#phone").val(data.mobileNo);
            $("#gender").val(data.gender);
            $("#dob").val("1996-09-01");
            return 1;}
           else{
        	   return 0;}
           },
        error: function (e) {
            console.log("ERROR: ", e);
            return 0;
        },
        done: function (e) {
            console.log("DONE");
        }
    });
  
}


// function to run ajax call to update profile for the current session user and
// populate the fields.
function updateUserProfile() {
    var successful = 0;

    var userdto = {};
    // for testing
    //userdto.userId = "1";
    
    userdto.userId= ""+getSessionAttribute().userId+"";
    userdto.userName = $("#user_name").text();
    userdto.gender = $("#gender").val();
    userdto.dob = $("#dob").val();
    userdto.pictureUrl = "images/profile-pic/"+getSessionAttribute().userId;  
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "updateUserProfile",
        data: JSON.stringify(userdto),
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: update user profile ", data);
            if (data.success == true) {
                return 1;
            }
            else{
            	console.log(data.success);
            	return 0;
            }
        },
        error: function (e) {
            console.log("ERROR: ", e);
            return 0;
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}

// function to run ajax call to reset the password for the current session user.
function resetPassword(userId) {
			
    var data={
    		'userId': ""+getSessionAttribute().userId+"",
    		// 'userId': "1",
    		'oldPassword': $("#oldPassword").val(),
            'newPassword': $("#newPassword").val()
       };

    	console.log("old password: "+$("#oldPassword").val());
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "resetPassword",
        async: false,
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: reset password ", data);
            if (data.success) { 
            	console.log("inside true stub");
            	$("#divPasswordReset").show();
            	setTimeout(function(){$("#divPasswordReset").hide();}, 3000);
                $("#divWrongPassword").hide();
                $("#divResetPassword").hide();
                $("#divEditProfileButtons").show();
            	return 1; }
            else{
            	 $("#divWrongPassword").show();
            	 setTimeout(function(){$("#divWrongPassword").hide();}, 3000);
            	return 0;
            }
        },
        error: function (e) {
            console.log("ERROR: ", e);
            return 0;
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}


// //function to run ajax call to change user status for the current session
// user.
function changeUserStatus() {
    var confirmPassword = 0;

    
	  var data={ 
			  'userId': ""+getSessionAttribute().userId+"", 
			  'confirmPassword': $("#confirmPassword").text() };
	 

    // for testing
//    var data = {
//        'userId': getSessionAttribute().userId,
//        'confirmPassword': $("#confirmPassword").text()
//    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "changeUserStatus",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
        	console.log("SUCCESS: ", data);
            if (data.success) { 
            	$("#divPasswordReset").show();
            	setTimeout(function(){$("#divPasswordReset").hide();}, 3000);
            	return 1; }
            else{
            	$("#divWrongPassword").show();
            	setTimeout(function(){$("#divWrongPassword").hide();}, 3000);
            	
            	return 0;
            }
        },
        error: function (e) {
            console.log("ERROR: ", e);
            confirmPassword = 0;
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}

function showMyProfile() {
    $("#modalContent").load("html/profile.html", function () {
        var userActive = 1;
        imageUpload();
        
        $("#divWrongPassword").hide();
        $("#divPasswordReset").hide();
        $("#divPasswordMinimum").hide();

        $("#divEditProfile").show();
        $("#divResetPassword").hide();
        $("#divConfirmPassword").hide();
        $("#user_name").attr('contenteditable', 'true');
        $("#dob").removeAttr('disabled');
        $("#gender").removeAttr('disabled');
        
        viewProfileById(getSessionAttribute().userId);
        // for test
        //viewProfileById(1);
        
        $("#btnUpdateProfile").click(function () {
            var confirmation = confirm("Do you want to update the profile?");
            if (confirmation == true) {
                updateUserProfile();
                $("#btnSubmitImage").click();
            }
            else {
                console.log("password not updated");
            }
        });

        $("#btnResetPassword").click(function () {
            $("#divEditProfileButtons").hide();
            $("#divResetPassword").show();
            $("#divConfirmPassword").hide();
        });

        $("#oldPassword").keypress(function () {
            $("#divWrongPassword").hide();
        });

        $("#btnDeactivateProfile").click(function () {
            $("#divEditProfileButtons").hide();
            $("#divConfirmPassword").show();
            $("#divResetPassword").hide();
            userActive = 0;
        });

        $("#btnDeleteProfile").click(function () {
            $("#divConfirmPassword").show();
            $("#divResetPassword").hide();
            userActive = -1;

        });

        $("#cancelConfirmPassword").click(function () {
            $("#divEditProfileButtons").show();
            $("#divConfirmPassword").hide();
            $("#divResetPassword").hide();
        });

        $("#cancelNewPassword").click(function () {
            $("#divEditProfileButtons").show();
            $("#divConfirmPassword").hide();
            $("#divResetPassword").hide();
        });

        $("#submitNewPassword").click(function () {
            /*if($("#newPassword").val().length>=8)
        	{*/
        	var confirmation = confirm("Do you want to update the password?");
        	
            if (confirmation == true) {
                console.log("password updated");
                resetPassword();                
            }
            else {
                console.log("password not updated");
            }
            /*}else{
            	$("#divPasswordMinimum").show();
            	setTimeout($("#divPasswordMinimum").hide(), 3000);
            }*/ 
        });

        $("#submitConfirmPassword").click(function () {
            var confirmation = confirm("Do you confirm?");
            if (confirmation == true) {
                console.log("password updated");
                $("#divConfirmPassword").hide();
                changeUserStatus();
                
            }
            else {
                console.log("password not updated");
            }
        });

    });
}

function showContactProfile() {
    $("#modalContent").load("html/profile.html", function (response, status, xhr) {
        if (status == "error") {
            var msg = "There was an error: ";
            alert(msg + xhr.status + " " + xhr.statusText);
        }

        $("#divWrongPassword").hide();
        $("#divPasswordReset").hide();

        $("#divEditProfileButtons").hide();
        $("#divResetPassword").hide();
        $("#divConfirmPassword").hide();
        $("#user_name").attr('contenteditable', 'false');
        $("#dob").attr('disabled', 'disabled');
        $("#gender").attr('disabled', 'disabled');

        viewProfileById(currentOpenedChat.userId);
        // for test
        //viewProfileById(2);
       });
}
