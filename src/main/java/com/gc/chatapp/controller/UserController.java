package com.gc.chatapp.controller;

import java.io.Console;
import java.io.File;
import java.text.ParseException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.Gender;
import com.gc.chatapp.entities.UserStatus;
import com.gc.chatapp.services.UserService;
import com.google.gson.Gson;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/AddUser.htm",method=RequestMethod.GET)
    public String showForm(){
        return "AddUser";
    }
	
	
	/**
	 * Upload single file using Spring Controller
	 */
	
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public String uploadFileHandler(@RequestParam("userId") String userId, @RequestParam("btnFileUpload") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				//String rootPath = System.getProperty("catalina.home");
				String rootPath = "src/main/resources/static/images";
				File dir = new File(rootPath + File.separator + "profile-pic");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()+ File.separator + userId);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				return "redirect:chatapp.html";
				//return "You successfully uploaded file=" + userId;
			} catch (Exception e) {
				return "You failed to upload " + userId + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + userId + " because the file was empty.";
		}
	}
	
}