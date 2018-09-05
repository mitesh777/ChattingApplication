<!DOCTYPE html>
<html lang="en">

<head>
    <title>Global Connect | Forgot Password</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

    <link rel="icon" type="image/png" href="images/icons/favicon.ico" />
    <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
    <link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
    <link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
    <link rel="stylesheet" type="text/css" href="vendor/animsition/css/animsition.min.css">
    <link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
    <link rel="stylesheet" type="text/css" href="vendor/daterangepicker/daterangepicker.css">

    <script src="vendor/animsition/js/animsition.min.js"></script>
    <script src="vendor/select2/select2.min.js"></script>
    <script src="vendor/daterangepicker/moment.min.js"></script>
    <script src="vendor/daterangepicker/daterangepicker.js"></script>
    <script src="vendor/countdowntime/countdowntime.js"></script>
    
    <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
    <link rel="stylesheet" type="text/css" href="css/loginutil.css">
    <link rel="stylesheet" type="text/css" href="css/login.css">
	<script src="js/appscript.js"></script>

    <script src="js/forgotPassword.js"></script>

    <script src="js/login.js"></script>
	<script src="js/register.js"></script>

	
</head>
<body>
	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-l-85 p-r-85 p-t-55 p-b-55" id="container-login">
				<form class="login100-form validate-form flex-sb flex-w" action="forgotPassword" method="post" id="validateform">
					<span class="login100-form-title p-b-32">
						Forgot Password
					</span>
					<div class="login100-form">
						<span class="txt1 p-b-11">
							Email
						</span>
						<div class="wrap-input100 m-b-12">
							<input class="input100" type="text" id="txtEmail" placeholder="Enter Your Email" name="username" >
							<span class="focus-input100"></span>
						</div>
						<label id="lblEmailError" for="txtEmail" class="error"></label><br/>
					</div>
					<div class="container-login100-form-btn">
						<button class="login100-form-btn m-r-90" id="btnSubmit">
							Submit
						</button>
					</div>
					
					<div class="contact100-form-checkbox">
                            <label id="serverError" class="error">
                            
                           ${resp}  </label>
                        </div>
					
				</form>
			</div>
		</div>
	</div>
	

	<div id="dropDownSelect1"></div>
	

</body>
</html>