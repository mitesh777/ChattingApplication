<!DOCTYPE html>
<html lang="en">

<head>
    <title>ChatApp</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src='https://production-assets.codepen.io/assets/editor/live/console_runner-079c09a0e3b9ff743e39ee2d5637b9216b3545af0de366d4b9aad9dc87e26bfd.js'></script>
    <script src='https://production-assets.codepen.io/assets/editor/live/css_live_reload_init-2c0dc5167d60a5af3ee189d570b1835129687ea2a61bee3513dee3a50c115a77.js'></script>
    <meta charset='UTF-8'>
    <meta name="robots" content="noindex">
    <!-- <link rel="shortcut icon" type="image/x-icon" href="https://production-assets.codepen.io/assets/favicon/favicon-8ea04875e70c4b0bb41da869e81236e54394d63638a1ef12fa558a4a835f1164.ico"
    /> -->
    <!-- <script src='//production-assets.codepen.io/assets/editor/live/events_runner-73716630c22bbc8cff4bd0f07b135f00a0bdc5d14629260c3ec49e5606f98fdd.js'></script> -->
    <link rel="mask-icon" type="" href="https://production-assets.codepen.io/assets/favicon/logo-pin-f2d2b6d2c61838f7e76325261b7195c27224080bc099486ddd6dccb469b8e8e6.svg"
        color="#111" />
    <link rel="canonical" href="https://codepen.io/emilcarlsson/pen/ZOQZaV?limit=all&page=74&q=contact+" />
    <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600,700,300' rel='stylesheet' type='text/css'>

    <script src="https://use.typekit.net/hoy3lrg.js"></script>
    <!-- <script>try { Typekit.load({ async: true }); } catch (e) { }</script> -->
    <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css'>
    <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.2/css/font-awesome.min.css'>

<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/chatappstyle.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/broadcastui.css" />

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" ></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>


    <script src="js/appscript.js"></script>


</head>

<body>
<div id="sessionVariable" style="display:none">
        <div id="userId">${chatUserObj.userId}</div>
        <div id="userName">${chatUserObj.userName}</div>
        <div id="emailId">${chatUserObj.emailId}</div>
        <div id="mobileNo">${chatUserObj.mobileNo}</div>
        <div id="pictureUrl">${chatUserObj.pictureUrl}</div>
        <div id="accessToken">${accessToken}</div>
    </div>

    <div id="frame">
        <div class="modal fade" id="modal" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content" id="modalContent">
                    <!-- ModalPopUP -->
                </div>
            </div>
        </div>

        <div id="sidepanel">
            <div id="profile">
                <div class="wrap">
                    <div id="divMyProfile" data-toggle="modal" data-target="#modal">
                    	<img id="profile-img" src="images/profile-pic/${chatUserObj.userId}" class="online" alt="" />
                        <p>${chatUserObj.userName}</p>
                    </div>
                    <div style="float:right;">
                        <button name="logout" class="btn btn-dark btn-sm" id="btnLogout">
                            <i class="fa fa-sign-out fa-fw" aria-hidden="true"></i>
                        </button>
                    </div>
                    
                </div>
            </div>
            <div id="search">
				<label for=""><i class="fa fa-search" aria-hidden="true"></i></label>
				<input type="text" placeholder="Search contacts..." />
			</div>
			<div id="contacts">
				<ul id="contactList">

					<!--Dynamically generated chatUser List-->

				</ul>
			</div>
			
			
			<div id="bottom-bar">
				<button id="addNew" data-toggle="modal" data-target="#modal">
					<i class="fa fa-plus fa-fw" aria-hidden="true"></i> <span>Add
						New</span>
				</button>
				<button id="settings">
					<i class="fa fa-whatsapp fa-fw" aria-hidden="true"></i> <span>Chats</span>
				</button>
				<button id="groupChat">
					<i class="fa fa-users fa-fw" aria-hidden="true"></i> <span>Groups</span>
				</button>
				<button id="broadCast">
					<i class="fa fa-rss fa-fw" aria-hidden="true"></i> <span>Broadcast</span>
				</button>

			</div>
		</div>
        <div class="content">
            <div class="contact-profile">
                <div id="divContactProfile" class="genericProfile" data-toggle="modal" data-target="#modal">
                    <img id="contact-profile-pic" src="" alt="" />
                    <p id="contact-name"></p>
                </div>
                <div>
                    <div class="social-media">
                        <button class="btn btn-sm default" id="btnSelect">Select</button>
                        <button class="btn btn-sm default" id="toggleDone" data-toggle="modal" data-target="#modal">Done</button>
                    </div>
                </div>
            </div>

            <div class="messages">

				<ul>
				
			</ul>
			</div>
			<div class="message-input">
				<div class="wrap">
					<input type="text" placeholder="Write your message..." /> <i
						class="fa fa-paperclip attachment" aria-hidden="true"></i>
					<button class="submit">
						<i class="fa fa-paper-plane" aria-hidden="true"></i>
					</button>
				</div>
			</div>
		</div>

	</div>
	<script
		src='https://production-assets.codepen.io/assets/common/stopExecutionOnTimeout-b2a7b3fe212eaa732349046d8416e00a9dec26eb7fd347590fbced3ab38af52e.js'></script>

	<script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>

	<script src="js/chatapp.js"></script>
	<script type="text/javascript" src="js/broadcastui.js"></script>

	
	<script src="js/script.js"></script>




<!-- Group managment Resource -->
	<script src="js/group_management.js"></script>
	<link rel="stylesheet" href="css/group_management.css">
</body>

</html>