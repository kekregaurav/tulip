package com.tulip.utility;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.tulip.model.AppUser;

@Component
public class EmailConstructor {
   
	@Autowired
	Environment env;
	
	@Autowired
	TemplateEngine templateEngine;
	
	
	public MimeMessagePreparator constructNewUserEmail(AppUser user, String password){
		
		Context context = new Context();
		context.setVariable("user", user);
		context.setVariable("password", password);
		
		String text = templateEngine.process("newUserEmailTemplate", context);
		
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {  
            
			@Override
            public void prepare(MimeMessage mimeMessage) throws Exception {  
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
				helper.setPriority(1);
				helper.setTo(user.getEmail());
				helper.setSubject("Welcome to Tulip");
				helper.setText(text, true);
				helper.setFrom(new InternetAddress(env.getProperty("support.email"))); 
            }  
		};  
		
		return messagePreparator;
	}
	
	
	public MimeMessagePreparator constructResetPasswordEmail(AppUser user, String password) {
		Context context = new Context();
		context.setVariable("user", user);
		context.setVariable("password", password);
		String text = templateEngine.process("resetPasswordEmailTemplate", context);
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setPriority(1);
				email.setTo(user.getEmail());
				email.setSubject("New Password - Tulip");
				email.setText(text, true);
				email.setFrom(new InternetAddress(env.getProperty("support.email")));
			}
		};
		return messagePreparator;
	}

	public MimeMessagePreparator constructUpdateUserProfileEmail(AppUser user) {
		Context context = new Context();
		context.setVariable("user", user);
		String text = templateEngine.process("updateUserProfileEmailTemplate", context);
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setPriority(1);
				email.setTo(user.getEmail());
				email.setSubject("Profile Update - Tulip");
				email.setText(text, true);
				email.setFrom(new InternetAddress(env.getProperty("support.email")));
			}
		};
		return messagePreparator;
	}
}
