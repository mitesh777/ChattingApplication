$(function () {
	console.log("Loaded");
	var baseUrl = "http://localhost:9079/gc/chat/"

	$("#broadCast").click(function getAllBroadcasts() {

		// Get the broadcast lists
		$.get(baseUrl + "getAllBroadcastLists/1")
		.then(function (response) {

			//Replace generic button with add broadcast button
			// $("#addNew").prop("id", "addBroadcastBtn");
			var addButtonHtml = $("#addNew")[0].outerHTML;
			var addButtonObj = $(addButtonHtml);
			addButtonObj.prop("id", "addBroadcastBtn");
			$("#addNew").replaceWith(addButtonObj[0].outerHTML);


			//Replace with profile modal popup with broadcast modal popup
			var profileHtml = $(".contact-profile").children("#divContactProfile")[0].outerHTML;
			var profileObj = $(profileHtml);
			profileObj.prop("id", "modifyBroadcastBtn");
			$(".contact-profile").children("#divContactProfile").replaceWith(profileObj[0].outerHTML);
			// .prop("id", "modifyBroadcastBtn");

			var broadcastList = response;
			var broadcastListData = "";
			$.each(broadcastList, function (i, x) {

				var html = '<li class="contact" id="' + broadcastList[i].chatBroadcastId +
					'" data-name="' + broadcastList[i].chatBroadcastName +
					'" data-broadcastid="' + broadcastList[i].chatBroadcastId + '">' +
					'<div class="wrap">' +
					'<span class="contact-status online"></span> <img src="images/profile_pic.png" alt="" />' +
					'<div class="meta">' +
					'<p class="name">' + broadcastList[i].chatBroadcastName + '</p>' +
					'</div>' +
					'</div>' +
					'</li>';
				broadcastListData += html;
			});
			
			$("#contactList").html(broadcastListData);
			
			$("#bottom-bar").on('click', '#settings', function(event){
				$("button.submit").attr("data-messagetype", "chat");
				$("div.genericProfile").prop("id", "divContactProfile");
			});

			$("#contacts").on('click',".contact" ,function (event) {
				var broadcastId = $(this).data("broadcastid")

				var html = '<img src="images/profile_pic.png" alt="" /><p id="broadcastDisplay" data-broadcastid="' + broadcastId +
					'">' +
					$(this).data("name") + '</p>';
				$(".contact-profile").children("#modifyBroadcastBtn").html(html);
				$.ajax({
					url: baseUrl + "broadcast/getAllMessages",
					data: {
						"broadcastId": broadcastId
					},
					success: function (response) {
						console.log(response);
						var html = "";
						var dataForMessageManagement = {"data" : []};
						for(var i = 0; i < response.length; i++){
							dataForMessageManagement.data.push({"messageid" : response[i].chatMessageId, "text": response[i].chatMessageText});
							html += '<li class="sent"><img src="images/profile_pic.png" alt="" /><p> ' + response[i].chatMessageText + ' <span id="timestamp" class="pull-right">'+response[i].createdDate.substring(11, 20)+'</span></p></li> <input type="checkbox" class="chk" value = "'+response[i].chatMessageId+'" style="display:none";>';
						}
						$('.messages ul').html(html);
						
						// names is the global variable used by message management team.
						names = dataForMessageManagement;
					},
					failure: function (response) {
						console.log(response);
					}
				});

				//$("#modifyBroadcastBtn").data("broadcastid", $(this).data("broadcastid"));
			});

			$("li.contact:first-child").click();

		})
		.fail(function(response){
			console.log(response);
			alert("Broadcasts couldnt be retrieved");
		});

		$("button.submit").attr("data-messagetype", "broadcast");

		$("button.submit").click(function(){
			console.log(this);
			if($(this).attr("data-messagetype")== "broadcast"){
				var message = $(".message-input input").val();
				var broadcastId = $("#broadcastDisplay").data("broadcastid");
				$(".message-input input").val(null);
				$.ajax({
					url: baseUrl + "broadcast/sendMessage/",
					type: "POST",
					data: {
						"broadcastListId": broadcastId,
						"message": message
					},
					success: function(){

					},
					failure: function(){
						console.log("Message not sent");
					}
				})
			}
		});

	});

	$("#bottom-bar").on('click', "#addBroadcastBtn", function () {
		$("#modalContent").load("html/createBroadcast.html", function (response, status, xhr) {
			console.log("loaded createBroadcast");

			if (status == "error") {
				var msg = "There was an error: ";
				alert(msg + xhr.status + " " + xhr.statusText);

			}
			// Changing the id of contact profile, this code has to be done,
			// when the broadcast button is pressed

			$(".contact-profile").children("#divContactProfile").prop("id", "modifyBroadcastBtn");

			initCreateBroadcast();
		});
	});

	$(".contact-profile").on("click", "#modifyBroadcastBtn", function () {
		$("#modalContent").load("html/modifyBroadcast.html", function (response, status, xhr) {
			console.log("loaded modifyBroadcast");

			if (status == "error") {
				var msg = "There was an error: ";
				alert(msg + xhr.status + " " + xhr.statusText);

			}
			var id = $("#broadcastDisplay").data("broadcastid");
			var broadcastName = $("#broadcastDisplay").text();
			console.log(id);
			$.ajax({
				url: baseUrl + "broadcast/getBroadcastListById",
				dataType: "json",
				data: { "broadcastId": id },
				success: function (response) {
					var members = response.chatBroadcastMembers;
					console.log("profile", response);
					initModifyBroadcast(id, broadcastName, members);
				},
				failure: function () {
					console.log(response);
					alert("Could not retrieve broadcast details for " + broadcastName);
				}
			});

		});
	});


});

function textFieldIsNotEmpty(jqueryObject, message) {
	if (jqueryObject.val() == "" || jqueryObject.val() == null) {
		jqueryObject.css("background-color", "red");
		var html = "<div class='text-danger' id='emptyBroadcastNameLabel'>" + message + "</div>";
		$(html).appendTo(jqueryObject);
		return false;
	}
	return true;
}


function initCreateBroadcast() {
	var users = [
		{
			"id": 1,
			"userName": "Suhas",
			"emailId": "suhas@mail.com"
		},
		{
			"id": 2,
			"userName": "Gariman",
			"emailId": "gariman@mail.com"
		},
		{
			"id": 3,
			"userName": "Rajat",
			"emailId": "rajat@mail.com"
		},
		{
			"id": 4,
			"userName": "Saurav",
			"emailId": "saurav@mail.com"
		},
	];

	var searchResults = [];
	var membersAdded = new Set();
	var baseUrl = "http://localhost:9079/gc/chat/"

	function createChatUserDiv(id, userName, emailId, btnHtml) {
		var html = '<div class="chatUser mt-2" id="chatUser-' + id + '">' +
			'<div class="chatUserDp">' +
			'<img src="images/placeholder.png">' +
			'</div>' +
			'<div class="chatUserDetails ml-2">' +
			'<div class="chatTitle">' +
			userName +
			'</div>' +
			'<div class="chatSubText">' +
			emailId +
			'</div>' +
			'</div>' +
			'<div>' +
			btnHtml +
			'</div>' +
			'</div>';
		return html;
	}


	function getSearchResults(searchString) {
		var resultOfUsers = [];
		for (var i = 0; i < users.length; i++) {
			if (searchString === users[i].userName) {
				resultOfUsers.push(users[i]);
			}

		}
		return resultOfUsers;
	}
	
//	function getSearchResults(searchString){
//			
//			$.ajax({
//				url: baseUrl + "/search/searchentity/searchtype/CHATUSER/chatuser/1/" + searchString,
//				type: "POST",
//				data: {
//					"searchType": "CHATUSER",
//					"userId": 1,
//					"searchString": searchString
//				},
//				success: function(response){
//					var responseData = []
//					for(var i = 0; i < response.length; i++){
//						obj = {
//								"userName" : response[i].name,
//								"emailId": response[i].email,
//								"id" : response[i].id
//						};
//						responseData.push(obj);
//					}
//					showSearchResults(responseData);
//				},
//				failure: function(response){
//					showSearchResults([]);
//				}
//				
//			});
//		}

	function showSearchResults(data) {
		$("#searchResults").html('');
		for (var i = 0; i < data.length; i++) {
			// Making sure that user with same id isnt shown again
			var btnHtml = '<button class="addUserToBroadcastBtn mt-1 btn btn-success" id="searchResult-' + data[i].id + '">Add</button>';

			if (membersAdded.has(data[i].id)) {
				btnHtml = '<button class="addUserToBroadcastBtn mt-1 btn btn-success" id="searchResult-' + data[i].id + '" disabled>Exists</button>'
			}
			var html = createChatUserDiv(data[i].id, data[i].userName, data[i].emailId, btnHtml);
			$("#searchResults").append(html);
		}
	}


	function addToBroadcastMembers(id) {
		membersAdded.add(id);
		for (var i = 0; i < searchResults.length; i++) {
			if (id == searchResults[i].id) {
				var chatUser = searchResults[i]
				break;
			}
		}
		var btnHtml = '<button class="mt-1 btn btn-danger removeUserFromBroadcastBtn" id="memberAdded-' +
			id + '">Remove</button>';
		var html = createChatUserDiv(id, chatUser.userName, chatUser.emailId, btnHtml);
		$("#chatUser-" + id).remove();
		$("#broadcastMembers").append(html);
	}
	
	$("#modalContent").off("input.name").on("input.name", "#broadcastNameInput", function(){
		$("#emptyNameAlert").hide();
	});


	$("#modalContent").off('click.create').on('click.create', '#createBroadcastBtn', function () {
		var broadcastName = $("#broadcastNameInput").val();
		if(broadcastName == null || broadcastName == ""){
			$("#emptyNameAlert").show();
		
		}else{
//		if(membersAdded.size == 0){
//			$("#emptyMembersAlert").show();
//			return;
//		}
		var chatUsers = membersAdded;
		console.log(membersAdded);
		console.log(chatUsers);
		console.log(Array.from(chatUsers).join(","));
		var myurl = baseUrl + "broadcast/create";
		var broadcastData = {
			"broadcastName": broadcastName,
			"addedMembers": Array.from(chatUsers)
		}
		console.log(broadcastData)
		$.ajax({
			type: "POST",
			dataType: "json",

			url: myurl,
			data: broadcastData,
			success: function (data) {
				console.log(data);

				var html = '<li class="contact" id="' + data.chatBroadcastId +
					'" data-name="' + broadcastName +
					'" data-broadcastid="' + data.chatBroadcastId + '">' +
					'<div class="wrap">' +
					'<span class="contact-status online"></span> <img src="images/profile_pic.png" alt="" />' +
					'<div class="meta">' +
					'<p class="name">' + broadcastName + '</p>' +
					'</div>' +
					'</div>' +
					'</li>';
				$("#contactList").append(html);


				$("#searchResults").html('');
				$("#broadcastMembers").html('');
				$("#modal").removeClass("show");
				$('body').removeClass('modal-open');
				$('.modal-backdrop').remove();
			},
			error: function (data) {
				console.log("not working");
				$("#modal").removeClass("show");
				$('body').removeClass('modal-open');
				$('.modal-backdrop').remove();
				alert("Something went wrong! Try again")
			}

		});
		}

	});

	$("#modalContent").on('click.newSearch', "#searchBtn", function () {
		console.log("inside search");
		var searchString = $("#searchUserInput").val();
		searchResults = getSearchResults(searchString);
		showSearchResults(searchResults);
	});

	$("#modalContent").off('click.addUser').on('click.addUser', '.addUserToBroadcastBtn', function (e) {
//		$("#emptyMembersAlert").hide();
//		console.log("No members");
		var id = parseInt(this.id.split("-")[1]);
		console.log(id);
		addToBroadcastMembers(id);
	});

	$("#modalContent").off('click.removeUser').on('click.removeUser', '.removeUserFromBroadcastBtn', function (event) {
		var id = parseInt(this.id.split("-")[1]);
		console.log(id);
		membersAdded.delete(id);
		$("#chatUser-" + id).remove();
	});


	//	$("#modalContent").on("input", "#broadcastNameInput", function(event){
	//		$("#emptyBroadcastNameLabel").remove();
	//		$("#broadcastNameInput").css("backgound-color", "white");
	//	});

	$("button.close").click(function () {
		$("#searchResults").html('');
		$("#broadcastMembers").html('');
		$("#modal .close").click();
	});


}

function initModifyBroadcast(broadcastId, broadcastName, members) {


	var users = [
		{
			"userId": 1,
			"userName": "Suhas",
			"emailId": "suhas@mail.com"
		},
		{
			"userId": 2,
			"userName": "Gariman",
			"emailId": "gariman@mail.com"
		},
		{
			"userId": 3,
			"userName": "Rajat",
			"emailId": "rajat@mail.com"
		},
		{
			"userId": 4,
			"userName": "Saurav",
			"emailId": "saurav@mail.com"
		},
	];

	var searchResults = [];
	var membersAdded = new Set(members);
	var baseUrl = "http://localhost:9079/gc/chat/";
	addExistingUsersToBroadcastMembers();
	$("#broadcastNameChangeInput").prop("value", broadcastName);

	function createChatUserDiv(id, userName, emailId, btnHtml) {
		var html = '<div class="chatUser mt-2" id="chatUser-' + id + '">' +
			'<div class="chatUserDp">' +
			'<img src="images/placeholder.png">' +
			'</div>' +
			'<div class="chatUserDetails ml-2">' +
			'<div class="chatTitle">' +
			userName +
			'</div>' +
			'<div class="chatSubText">' +
			emailId +
			'</div>' +
			'</div>' +
			'<div>' +
			btnHtml +
			'</div>' +
			'</div>';
		return html;
	}


	function addExistingUsersToBroadcastMembers() {
		var members = Array.from(membersAdded);
		for (var i = 0; i < members.length; i++) {
			var btnHtml = '<button class="mt-1 btn btn-danger removeUserFromBroadcastBtn persist" id="memberAdded-' +
				members[i].userId + '">Remove</button>';
			var html = createChatUserDiv(members[i].userId,
				members[i].userName,
				members[i].emailId,
				btnHtml);

			$("#broadcastMembers").append(html);

		}
	}


	function getSearchResults(searchString) {
		var resultOfUsers = [];
		for (var i = 0; i < users.length; i++) {
			if (searchString === users[i].userName) {
				resultOfUsers.push(users[i]);
			}

		}
		return resultOfUsers;
	}
	
//	function getSearchResults(searchString){
//		
//		$.ajax({
//			url: baseUrl + "/search/searchentity/searchtype/CHATUSER/chatuser/1/" + searchString,
//			type: "POST",
//			data: {
//				"searchType": "CHATUSER",
//				"userId": 1,
//				"searchString": searchString
//			},
//			success: function(response){
//				var responseData = []
//				for(var i = 0; i < response.length; i++){
//					obj = {
//							"userName" : response[i].name,
//							"emailId": response[i].email,
//							"userId" : response[i].id
//					};
//					responseData.push(obj);
//				}
//				showSearchResults(responseData);
//			},
//			failure: function(response){
//				showSearchResults([]);
//			}
//			
//		});
//	}

	function showSearchResults(data) {
		$("#searchResults").html('');
		for (var i = 0; i < data.length; i++) {
			// Making sure that user with same id isnt shown again
			var btnHtml = '<button class="addUserToBroadcastBtn persist mt-1 btn btn-success" id="searchResult-' + data[i].userId + '">Add</button>';

			if (membersAdded.has(data[i].userId)) {
				btnHtml = '<button class="addUserToBroadcastBtn persist mt-1 btn btn-success" id="searchResult-' + data[i].userId + '" disabled>Exists</button>'
			}
			var html = createChatUserDiv(data[i].userId, data[i].userName, data[i].emailId, btnHtml);
			$("#searchResults").append(html);
		}
	}


	function addToBroadcastMembers(id) {

		var callUrl = baseUrl + "broadcast/addUser";
		var dataToSend = {
			"broadcastId": broadcastId,
			"chatUserId": id
		};
		$.ajax({
			url: callUrl,
			type: "POST",
			dataType: "json",
			data: dataToSend,
			success: function () {
				membersAdded.add(id);
				for (var i = 0; i < searchResults.length; i++) {
					if (id == searchResults[i].userId) {
						var chatUser = searchResults[i]
						break;
					}
				}
				var btnHtml = '<button class="mt-1 btn btn-danger removeUserFromBroadcastBtn persist" id="memberAdded-' +
					id + '">Remove</button>';
				var html = createChatUserDiv(id, chatUser.userName, chatUser.emailId, btnHtml);
				$("#chatUser-" + id).remove();
				$("#broadcastMembers").append(html);
			},
			failure: function (data) {
				console.log(data);
				alert("Could not add the user to broadcast");
			}

		});
	}



	$("#modalContent").off('click.modifySearch').on('click.modifySearch', "#searchBtn", function () {
		var searchString = $("#searchUserInput").val();
		searchResults = getSearchResults(searchString);
		showSearchResults(searchResults);
	});

	$("#modalContent").off('click.modifyAddUser').on('click.modifyAddUser', '.addUserToBroadcastBtn.persist', function (e) {
		var id = parseInt(this.id.split("-")[1]);
		console.log(id);
		addToBroadcastMembers(id);
	});

	$("#modalContent").off('click.modifyRemoveUser').on('click.modifyRemoveUser', '.removeUserFromBroadcastBtn.persist', function (event) {
		var id = parseInt(this.id.split("-")[1]);
		console.log(id);

		var callUrl = baseUrl + "broadcast/deleteUser";
		var dataToSend = {
			"broadcastId": broadcastId,
			"chatUserId": id
		};
		$.ajax({
			url: callUrl,
			type: "POST",
			data: dataToSend,
			success: function () {
				//					membersAdded.delete(id);
				$("#chatUser-" + id).remove();
			},
			failure: function () {
				alert("Could not remove user from broadcast");
			}
		})



	});


	$("#modalContent").off('click.modifyDelete').on('click.modifyDelete', "#deleteBroadcastBtn", function (event) {
		if (confirm("Are you sure you want to delete?")) {
			var callUrl = baseUrl + "broadcast/deleteBroadcast";
			var dataToSend = { "broadcastListId": broadcastId }
			$.ajax({
				url: callUrl,
				type: "POST",
				data: dataToSend,
				success: function () {
					console.log($("li[data-broadcastId=" + broadcastId+ "]"));
					$("li[data-broadcastId=" + broadcastId+ "]").remove();
					if($("li.contact").length > 0){
						$("li.contact:first-child").click();
					}

				},
				failure: function () {
					alert("Could not remove user from broadcast");
				}
			});

			$("#modal").removeClass("show");
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();

		}
	});

	// $("#modalContent").on('click', '#editBroadcastNameBtn', function () {
	// 	$("#broadcastNameChangeInput").prop("disabled", false);
	// 	var html = '<btn class="btn btn-success ml-2" id="confirmEditBroadcastName">'+
	// 				'<i class="fa fa-check" aria-hidden="true"></i>'+
	// 				'</button>';
	// 	$("#editBroadcastNameBtn").replaceWith(html);
	// 	// $("#editBroadcastNameBtn").prop("id", "confirmEditBroadcastName");
	// 	$("#broadcastNameChangeInput").focus();
	// });

	// $("#modalContent").on('blur', '#broadcastNameChangeInput', function () {
	// 	setTimeout(1000);
	// 	console.log($(':focus'));
	// 	$("#broadcastNameChangeInput").prop("value", broadcastName);
	// 	var html = '<btn class="btn btn-info ml-2" id="editBroadcastNameBtn">'+
	// 				'<i class="fa fa-pencil" aria-hidden="true"></i>'+
	// 				'</button>';
	// 	$("#confirmEditBroadcastName").replaceWith(html);
	// 	// $("#confirmEditBroadcastName").prop("id", "editBroadcastNameBtn")
	// 	$("#broadcastNameChangeInput").prop("disabled", true);

	// });

	$("#modalContent").off('click.editName').on('click.editName', "#editBroadcastNameBtn", function () {
		console.log("confirm edit name")
		var newName = $("#broadcastNameChangeInput").val();

		$.ajax({
			type: "POST",
			url: baseUrl + "broadcast/modifyNameOfBroadcastList",
			data: {
				"broadcastId": broadcastId,
				"broadcastName": newName,
			},
			success: function (response) {
				$("li[data-broadcastId=" + broadcastId+ "] > .wrap > .meta > .name").text(newName);
				$("li[data-broadcastId=" + broadcastId + "]").data("name", newName);
				$("#broadcastDisplay").text(newName);
				broadcastName = newName;
				
			},
			failure: function (response) {
				alert("Could not change the name");
				$("#broadcastNameChangeInput").val(broadcastName);
			}
		});
	});

	$("button.close").click(function () {
		$("#searchResults").html('');
		$("#broadcastMembers").html('');
		$("#modal .close").click();
	});



}
