$(function(){

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
		var btnHtml = '<button class="mt-1 btn btn-danger removeUserFromBroadcastBtn" id="memberAdded-'+  
		id +'">Remove</button>';
		var html = createChatUserDiv(id, chatUser.userName, chatUser.emailId, btnHtml);
		$("#chatUser-" + id).remove();
		$("#broadcastMembers").append(html);
	}

	$("#toggleDone").click(
			function() {
				$("#modalContent").load(
						"html/forward.html",
						function(response, status, xhr) {
							if (status == "error") {
								var msg = "There was an error: ";
								alert(msg + xhr.status + " "
										+ xhr.statusText);
							}
							$("#suhasmodalbody").hide();
							$("#Forward").click(function() {
//								$("#modalContent").load(
//								"html/forwardModal.html",
//								function(response, status, xhr) {
//								if (status == "error") {
//								var msg = "There was an error: ";
//								alert(msg + xhr.status + " "
//								+ xhr.statusText);
//								}
//								console.log(getValueUsingClassF().selected);

//								$('<p>'+getValueUsingClassF().selected+'</p>').appendTo($('#forwardtest'));
//								$("#toggleDone").toggle();
//								startstop(true,fetchdata,5000);
//								selectflag = true;
//								});
								$("#btnGroup").hide();
								$("#suhasmodalbody").show();
								console.log("inside search");
								
								//send
								$("#searchBtn").click(function () {
									console.log("after search btn click");
									var searchString = $("#searchUserInput").val();
									searchResults = getSearchResults(searchString);
									showSearchResults(searchResults);
								});

								console.log(getValueUsingClassF().selected);

								$('<p>'+getValueUsingClassF().selected+'</p>').appendTo($('#forwardtest'));
								$("#toggleDone").toggle();
								startstop(true,fetchdata,5000);
								selectflag = true;   
							})
							$("#buttonClass").click(function() {
								selectflag = true;
								$("#toggleDone").toggle();

								startstop(true,fetchdata,5000);
								getValueUsingClass();


							});
							$("#delete").click(function(){
								selectflag = true;
								$("#toggleDone").toggle();
								console.log("indelete");
								startstop(true,fetchdata,5000);
								deleteMessage();
							})

						} )
			})



		$("#modalContent").on('click', '.addUserToBroadcastBtn', function (e) {
			var id = parseInt(this.id.split("-")[1]);
			console.log(id);
			addToBroadcastMembers(id);
		});

	$("#modalContent").on('click', '.removeUserFromBroadcastBtn', function (event) {
		var id = parseInt(this.id.split("-")[1]);
		console.log(id);
		membersAdded.delete(id);
		$("#chatUser-" + id).remove();
	});

	$("button.close").click(function () {
		$("#searchResults").html('');
		$("#broadcastMembers").html('');
		$("#modal .close").click();
	});




});


//$("#Forward").click(function () {
//$("#modalContent").load("html/forward.html", function (response, status, xhr)
//{
//console.log("loaded createBroadcast");

//if (status == "error") {
//var msg = "There was an error: ";
//alert(msg + xhr.status + " " + xhr.statusText);

//}
//// Changing the id of contact profile, this code has to be done,
//// when the broadcast button is pressed

//$(".contact-profile").children("#divContactProfile").prop("id",
//"modifyBroadcastBtn");

//initCreateBroadcast();
//});
//});

/*
 * $(".contact-profile").on("click", "#modifyBroadcastBtn", function(){
 * $("#modalContent").load("html/modifyBroadcast.html", function (response,
 * status, xhr){ console.log("loaded modifyBroadcast");
 * 
 * if (status == "error") { var msg = "There was an error: "; alert(msg +
 * xhr.status + " " + xhr.statusText);
 *  }
 * 
 * var id = 2; initModifyBroadcast(id); }); });
 * 
 * });
 */






/*
 * $("#modalContent").on('click','#createBroadcastBtn',function(){ var
 * broadcastName = $("#broadcastNameInput").val(); var chatUsers =
 * membersAdded; console.log(membersAdded); console.log(chatUsers);
 * console.log(Array.from(chatUsers).join(",")); var myurl = baseUrl +
 * "broadcast/create"; var broadcastData = { "broadcastName": broadcastName,
 * "addedMembers" : Array.from(chatUsers) } console.log(broadcastData)
 * $.ajax({ type: "POST", dataType: "json",
 * 
 * url: myurl, data: broadcastData, success: function(data){
 * console.log(data); $("#searchResults").html('');
 * $("#broadcastMembers").html(''); $("#modal").removeClass("show");
 * $('body').removeClass('modal-open'); $('.modal-backdrop').remove(); },
 * error: function(data){ console.log("not working");
 * $("#modal").removeClass("show"); $('body').removeClass('modal-open');
 * $('.modal-backdrop').remove(); alert("Something went wrong! Try again") }
 * 
 * });
 * 
 * 
 * 
 * });
 */

//$("#modalContent").on('click', "#searchBtn", function () {
//var searchString = $("#searchUserInput").val();
//searchResults = getSearchResults(searchString);
//showSearchResults(searchResults);
//});

/*
 * $("#searchBtn").click(function () { var searchString =
 * $("#searchUserInput").val(); searchResults =
 * getSearchResults(searchString); showSearchResults(searchResults); });
 */







