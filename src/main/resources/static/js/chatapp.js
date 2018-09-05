var messageCallbackHandler;

$(".messages").animate({
	scrollTop : $(document).height()
}, "fast");

$("#profile-img").click(function() {
	$("#status-options").toggleClass("active");
});

$(".expand-button").click(function() {
	$("#profile").toggleClass("expanded");
	$("#contacts").toggleClass("expanded");
});

$("#status-options ul li").click(function() {
	$("#profile-img").removeClass();
	$("#status-online").removeClass("active");
	$("#status-away").removeClass("active");
	$("#status-busy").removeClass("active");
	$("#status-offline").removeClass("active");
	$(this).addClass("active");

	if ($("#status-online").hasClass("active")) {
		$("#profile-img").addClass("online");
	} else if ($("#status-away").hasClass("active")) {
		$("#profile-img").addClass("away");
	} else if ($("#status-busy").hasClass("active")) {
		$("#profile-img").addClass("busy");
	} else if ($("#status-offline").hasClass("active")) {
		$("#profile-img").addClass("offline");
	} else {
		$("#profile-img").removeClass();
	}
	;

	$("#status-options").removeClass("active");
});
$('#select').click(function() {
	$("#sentBox").show();
});
var selected;
var names ;
var chkArray = [];
var contactid;
var sessionuser = getSessionAttribute();
console.log(sessionuser);
var getChatUsersURL = "http://172.31.11.239:9079/gc/chat/getChatUsers/"+sessionuser.userId; //chatUsersList(Archana's Controller)
//changed from

function fetchdata() {
	var html = "";

	   $.ajax({
	    type: 'GET', 
	                  url: 'http://172.31.11.239:9079/gc/chat/getdata/getchatdata/getchathistory/'+contactid+'/'+sessionuser.userId,
	    				//url: 'http://172.31.11.239:9079/gc/chat/getdata/getchatdata/getchathistory/'+contactid, 
					data: { get_param: 'value' }, 
				
	                
	                  success: function (data) { 
										  names = JSON.parse(data);
										  console.log(names);
										  console.log(names.data.length);
										  
	                                      if(names.data == undefined) {
											  html = '';
											  console.log('inif');
										  } else {
											  console.log('inside else');
	                                      for(var i=0;i<names.data.length;i++)  { 
	                                          // var arr = arr + ('<li class="replies"><img src="http://emilcarlsson.se/assets/harveyspecter.png" alt="" /><p>' + names[i].title + '</p></li> <input type="checkbox" class="chk" style="display:none";>'); 
	                                          //var arr = arr + names[i].title;
	                                          // console.log(names.data[0].text);
	                                          // console.log(names.data[1].text);
	                                          // console.log(names.data[2].text);
	                                          // console.log(names.data[3].text);
	                                          // console.log(names.data[4].text);
	                                          if(names.data[i].sender == sessionuser.userId){
	                                           // console.log("Inside Received : "+names.data[i].text);
	                                          html += '<li class="replies"><img src="http://emilcarlsson.se/assets/harveyspecter.png" alt="" /><p>' + names.data[i].text + '<span id="timestamp" class="pull-right"></span></p></li> <input type="checkbox" class="chk" value = "'+names.data[i].messageid+'"style="display:none";>';
	                                          }
	                                          else {
	                                              html += '<li class="sent"><img src="http://emilcarlsson.se/assets/harveyspecter.png" alt="" /><p>' + names.data[i].text + '<span id="timestamp" class="pull-right"></span></p></li> <input type="checkbox" class="chk" value = "'+names.data[i].messageid+'" style="display:none";>';
	                                          }
	                                        // console.log(names[i].title);
	                                        // console.log(names[i].userId);
											 }
											}
	          //  $('<li class="replies"><img src="http://emilcarlsson.se/assets/harveyspecter.png" alt="" /><p>' + names[i].title + '</p></li> <input type="checkbox" class="chk" style="display:none";>').replaceWith($('.messages ul'));
	          $('.messages ul').html(html);
	              //$(arr).replaceAll($('.messages ul'));
	    // $('.contact.active .preview').html('<span>You: </span>' + names[i].title);
	    // $(".messages").animate({ scrollTop: $(document).height() }, "fast");
	             
	   
	     //alert(response);
	    }
	   });
			
}
function postdata(json){
	$.ajax({
	    type: "POST",
	    url: "http://172.31.11.239:9079/gc/chat/sendMessage",
	    data: JSON.stringify(json),
        contentType: 'application/json',
	//    dataType: 'json'
	});
}
function deleteMessage(){
	console.log("Inside Delete");
	//console.log(getValueUsingClassF().chkArray);
	var newarray = getValueUsingClassF().chkArray;
	for(var i=0;i<newarray.length;i++){
		console.log(newarray[i]);
		$.ajax({
			  url: "http://172.31.11.239:9079/gc/chat/getdata/getchatdata/deletemessage/"+newarray[i],
			  type: "get", //send it through get method
			  //console.log(selected);

			  success: function(response) {
			    //Do Something
			    console.log("Message deleted"+selected);
			  },
			  error: function(xhr) {
			    //Do Something to handle error
			  }
			});
	}
	  
}
 
//changed till

/*function newMessage() {
	message = $(".message-input input").val();
	if ($.trim(message) == '') {
		return false;
	}
	$(
			'<li class="sent"><img src="http://emilcarlsson.se/assets/mikeross.png" alt="" /><p>'
					+ message
					+ '</p></li> <input type="checkbox" class="chk" style="display:none";>')
			.appendTo($('.messages ul'));
	$('.message-input input').val(null);
	$('.contact.active .preview').html('<span>You: </span>' + message);
	// $(".messages").animate({ scrollTop: $(document).height() }, "fast");
	$(".messages").animate({
		scrollTop : $(document).height()
	}, "fast");
};

$('.submit').click(function() {
	newMessage();
});*/
function newMessage() {
	message = $(".message-input input").val();
	var today = new Date();
	var timestamp = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate()+' '+today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

	var json = {
			"text":message,
			"sender": sessionuser.userId,
			"receiver":contactid,
			"status": "SENT",
			"type": "INDIVIDUAL",
			"timestamp":"2018-08-25 06:23:34",
			"access_token":sessionuser.accessToken
	}
	var testjson = {
		    "name": message,
		    "job": "leader"
		}
	postdata(json);
	console.log(json);
	if ($.trim(message) == '') {
		return false;
	}
	messageCallbackHandler(message);
	
	
	$(
			'<li class="sent"><img src="http://emilcarlsson.se/assets/mikeross.png" alt="" /><p>'
					+ message
					+ '</p></li> <input type="checkbox" class="chk" style="display:none";>')
			.appendTo($('.messages ul'));
	$('.message-input input').val(null);
	$('.contact.active .preview').html('<span>You: </span>' + message);
	// $(".messages").animate({ scrollTop: $(document).height() }, "fast");
	$(".messages").animate({
		scrollTop : $(document).height()
	}, "fast");
};

$('.submit').click(function() {
	newMessage();
});

$(window).on('keydown', function(e) {
	if (e.which == 13) {
		newMessage();
		return false;
	}
});

/*function getValueUsingClass() {
	 declare an checkbox array 
	var chkArray = [];

	
	 * look for all checkboes that have a class 'chk' attached to it and check
	 * if it was checked
	 
	$(".chk:checked").each(function() {
		chkArray.push($(this).val());
	});

	 we join the array separated by the comma 
	var selected;
	selected = chkArray.join(',');
	copyToClip(selected);
	
	 * check if there is selected checkboxes, by default the length is 1 as it
	 * contains one single comma
	 
	if (selected.length > 0) {
		alert("You have selected " + selected);
	} else {
		alert("Please at least check one of the checkbox");
	}
}*/


function getValueUsingClass() {
	/* declare an checkbox array */
	 chkArray = [];
	var copyMsg = [];
	var arrayElem = [];
	/*
	 * look for all checkboes that have a class 'chk' attached to it and check
	 * if it was checked
	 */
	$(".chk:checked").each(function() {
		chkArray.push($(this).val());
	});

	/* we join the array separated by the comma */
	for(var j=0;j<chkArray.length;j++){
		
		 arrayElem[j] = chkArray[j];
	 for(var i=0;i<names.data.length;i++)  { 
         // var arr = arr + ('<li class="replies"><img src="http://emilcarlsson.se/assets/harveyspecter.png" alt="" /><p>' + names[i].title + '</p></li> <input type="checkbox" class="chk" style="display:none";>'); 
         //var arr = arr + names[i].title;
         // console.log(names.data[0].text);
         // console.log(names.data[1].text);
         // console.log(names.data[2].text);
         // console.log(names.data[3].text);
         // console.log(names.data[4].text);
         if(names.data[i].messageid == arrayElem[j]){
        	 copyMsg.push(names.data[i].text);
          // console.log("Inside Received : "+names.data[i].text);
//         html += '<li class="replies"><img src="http://emilcarlsson.se/assets/harveyspecter.png" alt="" /><p>' + names.data[i].text + '<span id="timestamp" class="pull-right">2:48&nbsp;p.m</span></p></li> <input type="checkbox" class="chk" value = "'+names.data[i].messageid+'"style="display:none";>';
         }
//         else {
//             html += '<li class="sent"><img src="http://emilcarlsson.se/assets/harveyspecter.png" alt="" /><p>' + names.data[i].text + '<span id="timestamp" class="pull-right">2:48&nbsp;p.m</span></p></li> <input type="checkbox" class="chk" value = "'+names.data[i].messageid+'" style="display:none";>';
//         }
       // console.log(names[i].title);
       // console.log(names[i].userId);
        }
	}
	
	//selected = chkArray.join(',');
	selected = copyMsg.join(',');
	copyToClip(selected);
	/*
	 * check if there is selected checkboxes, by default the length is 1 as it
	 * contains one single comma
	 */
	if (selected.length > 0) {
		alert("You have selected " + selected);
	} else {
		alert("Please at least check one of the checkbox");
	return{
		selected:selected
	}
	
	}
}


/*function getValueUsingClassF() {
	 declare an checkbox array 
	var chkArray = [];

	
	 * look for all checkboes that have a class 'chk' attached to it and check
	 * if it was checked
	 
	$(".chk:checked").each(function() {
		chkArray.push($(this).val());
	});

	 we join the array separated by the comma 
	var selected;
	selected = chkArray.join(',');
	return{
		selected:selected
	}
}*/


function getValueUsingClassF() {
	/* declare an checkbox array */
	
	var copyMsg = [];
	var arrayElem = [];
	chkArray = [];
	/*
	 * look for all checkboes that have a class 'chk' attached to it and check
	 * if it was checked
	 */
	$(".chk:checked").each(function() {
		chkArray.push($(this).val());
	});
	for(var j=0;j<chkArray.length;j++){
		
		 arrayElem[j] = chkArray[j];
	 for(var i=0;i<names.data.length;i++)  { 
        // var arr = arr + ('<li class="replies"><img src="http://emilcarlsson.se/assets/harveyspecter.png" alt="" /><p>' + names[i].title + '</p></li> <input type="checkbox" class="chk" style="display:none";>'); 
        //var arr = arr + names[i].title;
        // console.log(names.data[0].text);
        // console.log(names.data[1].text);
        // console.log(names.data[2].text);
        // console.log(names.data[3].text);
        // console.log(names.data[4].text);
        if(names.data[i].messageid == arrayElem[j]){
       	 copyMsg.push(names.data[i].text);
         // console.log("Inside Received : "+names.data[i].text);
//        html += '<li class="replies"><img src="http://emilcarlsson.se/assets/harveyspecter.png" alt="" /><p>' + names.data[i].text + '<span id="timestamp" class="pull-right">2:48&nbsp;p.m</span></p></li> <input type="checkbox" class="chk" value = "'+names.data[i].messageid+'"style="display:none";>';
        }
//        else {
//            html += '<li class="sent"><img src="http://emilcarlsson.se/assets/harveyspecter.png" alt="" /><p>' + names.data[i].text + '<span id="timestamp" class="pull-right">2:48&nbsp;p.m</span></p></li> <input type="checkbox" class="chk" value = "'+names.data[i].messageid+'" style="display:none";>';
//        }
      // console.log(names[i].title);
      // console.log(names[i].userId);
       }
	}
	
	//selected = chkArray.join(',');
	selected = copyMsg.join(',');
	return{
		selected:selected,
		chkArray:chkArray
	}
}



//changed till
function CopyToClipboard(containerid) {
	if (document.selection) {
		var range = document.body.createTextRange();
		range.moveToElementText(document.getElementById(containerid));
		range.select().createTextRange();
		document.execCommand("copy");

	} else if (window.getSelection) {
		var range = document.createRange();
		range.selectNode(document.getElementById(containerid));
		window.getSelection().addRange(range);
		document.execCommand("copy");
		alert("text copied")
	}
}
function copyToClip(str) {
	function listener(e) {
		e.clipboardData.setData("text/html", str);
		e.clipboardData.setData("text/plain", str);
		e.preventDefault();
	}
	document.addEventListener("copy", listener);
	document.execCommand("copy");
	document.removeEventListener("copy", listener);
};
var intervalID = null;
var selectflag = true;
var currentOpenedChat;
var contactList;
var currentOpenedChatGroup;
var groupList;

var chatInterval;
var groupInterval;
$(document).ready(
		function() {
			//danish
			
			function getAllChatUsers()
			{$.ajax({
				url:getChatUsersURL,
				type:"GET",
				dataType: "json",
				success:function (response) {	
					
					console.log(response);
					contactList = response;	
					console.log(contactList);
					var chatListData="";
					$.each(contactList, function (i, x) {
						chatListData+='<li class="contact" id="' + i + '"><div class="wrap"><span class="contact-status on\line"></span><img src="" alt="" /><div class="meta"><p class="name" >' + contactList[i].userName + '</p><p class="preview">Preview Message</p></div></div></li>';
					});
					$("#contactList").html(chatListData);
					
				},
			
				error:function(data){
					console.log(data);
					//alert("Unable to load the Chats");
					}
				});
			}
			//Clicking on contact Event
					//To get the clicked contact ID , use this.id
					$("#contactList").on("click", ".contact", function (event) {
						
						currentOpenedChat=contactList[this.id];
						//$(".contact-profile").html('<img src="" alt="" /><p>'+contactList[this.id].userName+'</p>');
						$("#contact-name").text(contactList[this.id].userName);
						$("#contact-profile-pic").attr('src',"images/profile-pic/"+getSessionAttribute().userId);

						contactid = contactList[this.id].userId;
				});	
				
			
				
			
			

			//Add New Chat
			
//				$("#addNew").click(function addNewChat(){
//				var name='';
//				var dataToBeSent = 'userId='+ id  & 'searchString='+ name & 'searchType=CHATUSER';
//				$("#searchInput").focus();  
//				$("#searchInput").keypress(function(event){
//					var keycode = (event.keyCode ? event.keyCode : event.which);
//					if(keycode == '32'){
//						name+=$("#searchInput").val();
		//	
//						$.ajax({
		//	
//							type:"POST",
//							url:"searchController",
//							data:dataToBeSent,
//							success: function(){}
//						});
		//	
//					}
//				});
		//	
//			});	
			getAllChatUsers();
			chatInterval = setInterval(getAllChatUsers,5000);

			$("#groupChat").click(function getAllGroups() {
				console.log("group clicked");
				startstop(false);
				clearInterval(chatInterval);
				
			function getAllGroups() {
			$.ajax({
				url: "http://localhost:9079/gc/chat/group/getAllGroupsOfUser",
				type: "GET",
				dataType: "json",
				success: function (response) {

					console.log(response);
					groupList = response;
					console.log(groupList);
					var groupListData = "";
					$.each(groupList, function (i, x) {
						groupListData += '<li class="contact" id="' + i + '"><div class="wrap"><span class="contact-status on\line"></span><img src="" alt="" /><div class="meta"><p class="name" >' + groupList[i].chatGroupName + '</p><p class="preview">Preview Message</p></div></div></li>';
					});
					$("#contactList").html(groupListData);

				},

				error: function (data) {
					console.log(data);
					//to be removed
					//alert("Unable to load the Groups");
				}
			});
		}
		getAllGroups();
		groupInterval = setInterval(getAllGroups,5000);
	});
	//Clicking on GroupContact Event
	//To get the clicked contact ID , use this.id
	$("#contactList").on("click", ".contact", function (event) {

		groupManagement.loadGroupChatHistory();
		currentOpenedChatGroup=groupList[this.id]; //For Srivatsa team
				
		$("#contact-name").text(groupList[this.id].userName); //contact-name -->p
		$("#contact-profile-pic").attr('src',"images/profile-pic/"+getSessionAttribute().userId);
		
	});


$("#settings").click(function(){
					
					console.log("chat clicked");
					//clearInterval(broadCastInterval);
					clearInterval(groupInterval);
					clearInterval(chatInterval);
					
					function getAllChatUsers()
					{$.ajax({
						url:"http://localhost:9079/gc/chat/getChatUsers/"+getSessionAttribute().userId,  //to be confirmed
						type:"GET",
						dataType: "json",
						success:function (response) {	
							
							contactList = response;	
							var chatListData="";
							$.each(contactList, function (i, x) {
								chatListData+='<li class="contact" id="' + i + '"><div class="wrap"><span class="contact-status on\line"></span><img src="" alt="" /><div class="meta"><p class="name" >' + contactList[i].userName + '</p><p class="preview">Preview Message</p></div></div></li>';
							});
							$("#contactList").html(chatListData);
							
						},
					
						error:function(data){
							console.log(data);
							//alert("Unable to load the Chats");
							}
						});
					}
					//Clicking on contact Event
							//To get the clicked contact ID , use this.id
					$("#contactList").on("click", ".contact", function (event) {
								
								currentOpenedChat=contactList[this.id];
								$("#contact-name").text(contactList[this.id].userName); //contact-name -->p					//change
								$("#contact-profile-pic").attr('src',"images/profile-pic/"+getSessionAttribute().userId);	//change
								      //contact-profile-pic
								contactid = contactList[this.id].userId;
						});	
							
					getAllChatUsers();
					chatInterval = setInterval(getAllChatUsers,5000);
					startstop(true,fetchdata,5000);
              });

			
			//danish
			
			
			
			
			
			
			
		    startstop(true,fetchdata,5000);
		 //   var interval = setInterval(fetchdata, 5000);
			$("#toggleDone").css('display', 'none');
			//$("#toggleDone").attr('disabled');

			$("#btnSelect").click(function() {
				if(selectflag == true) {
					console.log('inin');
					//clearInterval(interval);
			        startstop(false);    
			        //console.log('here');
			        $(".chk").toggle();
			        $("#toggleDone").toggle();
			    selectflag = false;
			    }
				else {
			        //console.log('in double');
			        fetchdata();
			        startstop(true,fetchdata,5000);
			        selectflag = true;

			            }
			});

			var span = document.getElementsByClassName("close")[0];

			
		});
function startstop(flag, loop, time) {
	//console.log("inside startstop");
	
	if(flag == true){
		intervalID =  setInterval(loop, time);
		console.log(intervalID);
	}
     
   else{
		console.log(intervalID);

	   clearInterval(intervalID);
   }
     
}


// # sourceURL=pen.js