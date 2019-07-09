package com.tulip.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tulip.model.AppUser;
import com.tulip.model.Role;
import com.tulip.model.UserRole;
import com.tulip.repo.AppUserRepo;
import com.tulip.repo.RoleRepo;
import com.tulip.service.AccountService;
import com.tulip.utility.Constants;
import com.tulip.utility.EmailConstructor;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	private AppUserRepo appUserRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired 
	private EmailConstructor emailConstructor;
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Transactional
	public AppUser saveUser(String name, String username, String email) {
		String password = RandomStringUtils.randomAlphanumeric(10);
		String encryptedPassword = bCryptPasswordEncoder.encode(password);
		AppUser appUser = new AppUser();
		appUser.setPassword(encryptedPassword);
		appUser.setName(name);
		appUser.setUsername(username);
		appUser.setEmail(email);
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(appUser, accountService.findUserRoleByName("USER")));
		appUser.setUserRoles(userRoles);
		appUserRepo.save(appUser);
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(Constants.TEMP_USER_PIC.toPath());
			String fileName = appUser.getId() + ".png";
			Path path = Paths.get(Constants.USER_FOLDER + fileName);
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
		  mailSender.send(emailConstructor.constructNewUserEmail(appUser, password));
		}catch(Exception e) {
			
		}
		return appUser;
	}

	@Override
	public void updateUserPassword(AppUser appUser, String newpassword) {
		String encryptedPassword = bCryptPasswordEncoder.encode(newpassword);
		appUser.setPassword(encryptedPassword);
		appUserRepo.save(appUser);
		try {
		  mailSender.send(emailConstructor.constructResetPasswordEmail(appUser, newpassword));
		}catch(Exception e) {
			
		}
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepo.save(role);
	}

	@Override
	public AppUser findByUsername(String username) {
		return appUserRepo.findByUsername(username);
	}

	@Override
	public AppUser findByEmail(String userEmail) {
		return appUserRepo.findByEmail(userEmail);
	}

	@Override
	public List<AppUser> userList() {
		return appUserRepo.findAll();
	}

	@Override
	public Role findUserRoleByName(String name) {
		return roleRepo.findRoleByName(name);
	}

	@Override
	public AppUser simpleSaveUser(AppUser user) {
		appUserRepo.save(user);
		mailSender.send(emailConstructor.constructUpdateUserProfileEmail(user));
		return user;

	}

	@Override
	public AppUser updateUser(AppUser user, HashMap<String, String> request) {
		String name = request.get("name");
		// String username = request.get("username");
		String email = request.get("email");
		String bio = request.get("bio");
		user.setName(name);
		// appUser.setUsername(username);
		user.setEmail(email);
		user.setBio(bio);
		appUserRepo.save(user);
		mailSender.send(emailConstructor.constructUpdateUserProfileEmail(user));
		return user;

	}

	@Override
	public AppUser findUserById(Long id) {
		return appUserRepo.findUserById(id);
	}

	@Override
	public void deleteUser(AppUser appUser) {
		appUserRepo.delete(appUser);

	}

	@Override
	public void resetPassword(AppUser user) {
		String password = RandomStringUtils.randomAlphanumeric(10);
		String encryptedPassword = bCryptPasswordEncoder.encode(password);
		user.setPassword(encryptedPassword);
		appUserRepo.save(user);
		mailSender.send(emailConstructor.constructResetPasswordEmail(user, password));

	}

	@Override
	public List<AppUser> getUsersListByUsername(String username) {
		return appUserRepo.findByUsernameContaining(username);
	}

	@Override
	public String saveUserImage(MultipartFile multipartFile, Long userImageId) {
		/*
		 * MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)
		 * request; Iterator<String> it = multipartRequest.getFileNames(); MultipartFile
		 * multipartFile = multipartRequest.getFile(it.next());
		 */
		byte[] bytes;
		try {
			Files.deleteIfExists(Paths.get(Constants.USER_FOLDER + "/" + userImageId + ".png"));
			bytes = multipartFile.getBytes();
			Path path = Paths.get(Constants.USER_FOLDER + userImageId + ".png");
			Files.write(path, bytes);
			return "User picture saved to server";
		} catch (IOException e) {
			return "User picture Saved";
		}
	}
}
