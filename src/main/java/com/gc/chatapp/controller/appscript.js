
$(function () {

    //console.log("api testing");
    //viewProfileById();
    //resetPassword();
    //updateUserProfile();

    $("#divMyProfile").click(function () {

        showMyProfile();

    });

    $("#divContactProfile").click(function () {
        imageUpload();
        showContactProfile();
    });

    $("#btnLogout").click(function () { location.href = "logout"; });

    $("#btnSignUp").click(function () {
        console.log("btn click");
        $("#modalContent").load("html/register.html", function (response, status, xhr) {
            console.log("loaded");
            if (status == "error") {
                var msg = "There was an error: ";
                alert(msg + xhr.status + " " + xhr.statusText);
            }

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
       /* $("#btnSubmitImage").click(function (e) {
            //e.preventDefault();
            //uploadImageAjax();
            return false;
        });*/
        //$("#btnSubmitImage").click();
    });


    $("#iconUpload").click(function () {
        $("#btnFileUpload").click();
    });
};



//function to access session attributes
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

function uploadImageAjax() {
    $.ajax({
        url: "uploadImage", // Url to which the request is send
        type: "POST",             // Type of request to be send, called as method
        data: new FormData(this), // Data sent to server, a set of key/value pairs (i.e. form fields and values)
        contentType: false,       // The content type used when sending data to the server.
        cache: false,             // To unable request pages to be cached
        processData: false,        // To send DOMDocument or non processed data file it is set to false
        success: function (data){
            console.log("SUCCESS: view profile", data);
        },
        error: function (e) {
            console.log("ERROR: ", e);
            successful = 0;
        },
        done: function (e) {
            console.log("DONE");
        }
    });
};

//function to run ajax call to view profile for current session user and populate the fields.
function viewProfileById() {

    /*var data = { "userId": getSessionAttribute().userId };*/

    //for testing
    var data = { "userId": "4" };
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
            //if(data.err==0)
            console.log("SUCCESS: view profile", data);
            $("#user_name").text(data.userName);
            $("#email").text(data.emailId);
            $("#phone").val(data.mobileNo);
            $("#gender").val(data.gender);
            $("#dob").val("1996-09-01");
            successful = 1;

        },
        error: function (e) {
            console.log("ERROR: ", e);
            successful = 0;
        },
        done: function (e) {
            console.log("DONE");
        }
    });

    return successful;
}


//function to run ajax call to update profile for the current session user and populate the fields.
function updateUserProfile() {
    var successful = 0;

    var userdto = {};
    //userdto.userId= getSessionAttribute().userId;
    userdto.userId = "4";
    userdto.userName = $("#user_name").text();
    userdto.gender = $("#gender").val();
    userdto.dob = $("#dob").val();
    userdto.pictureUrl = "1.jpg";

    //for testing
    /* userdto.userId="1";
     userdto.userName = "Nishith";
     userdto.gender="FEMALE";
     userdto.dob="1996-09-01";*/

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "updateUserProfile",
        data: JSON.stringify(userdto),
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: update user profile ", data);
            if (data == true) {
                successful = 1;
                $("#user_name").text(data.userName);
                $("#email").text(data.emailId);
                $("#phone").val(data.mobileNo);
                $("#gender").val(data.gender);
                $("#dob").val("1996-09-01");
            }
        },
        error: function (e) {
            console.log("ERROR: ", e);
            successful = 0;
        },
        done: function (e) {
            console.log("DONE");
        }
    });
    return successful;
}

//function to run ajax call to reset the password for the current session user.
function resetPassword(userId) {

    var resetPasswordSuccess = 0;
//    'userId': getSessionAttribute().userId
    var data={
            'userId': "1",
            'oldPassword': $("#oldPassword").val(),
            'newPassword': $("#newPassword").val()
       };

    //for testing
//    var data = {
//        'userId': "1",
//        'oldPassword': "admin",
//        'newPassword': "password"
//    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "resetPassword",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: reset password ", data);
            if (data=='true') { resetPasswordSuccess = 1; }
        },
        error: function (e) {
            console.log("ERROR: ", e);
            resetPasswordSuccess = 0;
        },
        done: function (e) {
            console.log("DONE");
        }
    });

    return resetPasswordSuccess;

}


////function to run ajax call to change user status for the current session user.
function changeUserStatus() {
    var confirmPassword = 0;

    /*var data={
            'userId': getSessionAttribute().userId,
            'confirmPassword': $("#confirmPassword").text()
       };*/

    //for testing
    var data = {
        'userId': getSessionAttribute().userId,
        'confirmPassword': $("#confirmPassword").text()
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "changeUserStatus",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
        	console.log(data);
            console.log("SUCCESS: ", data);
            console.log(data);
            var val=JSON.stringify(data);
            console.log(val);
            if (val=="true") { 
            	console.log("password confirmed enter the block");
            	confirmPassword = 1; }
        },
        error: function (e) {
            console.log("ERROR: ", e);
            confirmPassword = 0;
        },
        done: function (e) {
            console.log("DONE");
        }
    });

    return confirmPassword;
}

function showMyProfile() {
    $("#modalContent").load("html/profile.html", function () {
        var userActive = 1;
        imageUpload();
        $("#divWrongPassword").hide();
        $("#divPasswordReset").hide();

        $("#divEditProfile").show();
        $("#divResetPassword").hide();
        $("#divConfirmPassword").hide();
        $("#user_name").attr('contenteditable', 'true');
        $("#dob").removeAttr('disabled');
        $("#gender").removeAttr('disabled');
        viewProfileById();

        $("#btnUpdateProfile").click(function () {
            var confirmation = confirm("Do you want to update the password?");
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
            var confirmation = confirm("Do you want to update the password?");
            if (confirmation == true) {
                console.log("password updated");

                var resetPasswordSucess = resetPassword();
                if (resetPasswordSucess) {
                    $("#divPasswordReset").show();
                    $("#divWrongPassword").hide();
                    $("#divResetPassword").hide();
                    $("#divEditProfileButtons").show();
                }
                else {
                    $("#divWrongPassword").show();
                }
            }
            else {
                console.log("password not updated");
            }
        });

        $("#submitConfirmPassword").click(function () {
            var confirmation = confirm("Do you confirm?");
            if (confirmation == true) {
                console.log("password updated");
                $("#divConfirmPassword").hide();
                var statusChanged = changeUserStatus(chatUser.userId);
                if (resetPasswordSucess) { $("#divPasswordReset").show(); }
                else { $("#divWrongPassword").show(); }
            }
            else {
                console.log("password not updated");
            }
        });


        //      $("#user_name").text("Nishith");
        //      $("#email").text("abc@xyz.com");
        //      $("#phone").val("7042335885");
        //      $("#dob").val("1996-04-09");
        //      $("#gender").val("MALE");
        //      $('#profile_pic').attr('src', 'images/profile_pic.png');
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

        viewProfileById();
        //         $("#user_name").text("Kalash");
        //         $("#email").text("iii@xyz.com");
        //         $("#phone").val("9999999999");
        //         $("#dob").val("1998-04-03");
        //         $("#gender").val("male");
        //         $('#profile_pic').attr('src', 'images/profile_pic.png');
    });
}
