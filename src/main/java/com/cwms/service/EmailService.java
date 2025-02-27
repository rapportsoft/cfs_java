package com.cwms.service;



import jakarta.mail.util.ByteArrayDataSource;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
//import jakarta.mail.Authenticator;
import jakarta.mail.Message;
//import jakarta.mail.PasswordAuthentication;
//import jakarta.mail.Session;
import jakarta.mail.Transport;
//import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
//import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;


import jakarta.mail.Session;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Authenticator;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
//	
//	    public boolean sendEmailWithHtmlContent(String subject, String htmlContent, String to) {
//	        boolean success = false;
//
//	        String from = "rushikeshnirmal88@gmail.com";
//	        String host = "smtp.gmail.com";
//	        String username = "rushikeshnirmal88@gmail.com";
//	        String password = "bcoqpbgkllkvbfmf"; // Update with your actual password
//
//	        Properties properties = new Properties();
//	        properties.put("mail.smtp.auth", "true");
//	        properties.put("mail.smtp.starttls.enable", "true");
//	        properties.put("mail.smtp.host", host);
//	        properties.put("mail.smtp.port", "587");
//
//	        Session session = Session.getInstance(properties, new Authenticator() {
//	            @Override
//	            protected PasswordAuthentication getPasswordAuthentication() {
//	                return new PasswordAuthentication(username, password);
//	            }
//	        });
//
//	        session.setDebug(true);
//
//	        MimeMessage mimeMessage = new MimeMessage(session);
//	        try {
//	            mimeMessage.setFrom(new InternetAddress(from));
//	            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//	            mimeMessage.setSubject(subject);
//
//	            
////	            MimeBodyPart fileMine = new MimeBodyPart();
////	          String path = "/Users/macbook/Downloads/detention_data.xlsx";
////	          File file = new File(path);
////           fileMine.attachFile(file); 
//           
//           
//           
//	            MimeMultipart mimeMultipart = new MimeMultipart("related");
//
//	            MimeBodyPart htmlPart = new MimeBodyPart();
//	            htmlPart.setContent(htmlContent, "text/html");
//
//	            mimeMultipart.addBodyPart(htmlPart);
//	           // mimeMultipart.addBodyPart(fileMine);
//
//	            mimeMessage.setContent(mimeMultipart);
//
//	            Transport.send(mimeMessage);
//	            System.out.println("Sent HTML message successfully...");
//
//	            success = true;
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//
//	        return success;
//	    }
//	

	
	
	    
	@Value("${spring.from.mail}")
    private String from;  
	 
	 @Value("${spring.mail.host}")
	 private String host;
	 
	 @Value("${spring.mail.username}")
	 private String username;
	 
	 @Value("${spring.mail.password}")
	 private String password;
	 
	public boolean sendEmailWithHtmlContentAndAttachment(String subject, String htmlContent, String to,
			String attachmentFilePath, String fromEmail, String ccEmail) {
		boolean success = false;
		//String from = "rushikeshnirmal88@gmail.com";
//		String host = "smtp.gmail.com";
//		String username = "rushikeshnirmal88@gmail.com";
//		String password = "bcoqpbgkllkvbfmf"; // Update with your actual password

		Properties properties = new Properties();

		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
//		properties.put("mail.smtp.starttls.enable", "false");
//		properties.put("mail.smtp.ssl.enable", "false");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		session.setDebug(true);

		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(new InternetAddress(fromEmail));

			// mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccEmail));

			mimeMessage.setSubject(subject);

			MimeMultipart mimeMultipart = new MimeMultipart("related");

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlContent, "text/html");

			mimeMultipart.addBodyPart(htmlPart);

			// Add the attachment
			MimeBodyPart attachmentPart = new MimeBodyPart();
			FileDataSource fileDataSource = new FileDataSource(attachmentFilePath);
			attachmentPart.setDataHandler(new DataHandler(fileDataSource));
			attachmentPart.setFileName(fileDataSource.getName());
			mimeMultipart.addBodyPart(attachmentPart);

			mimeMessage.setContent(mimeMultipart);

			Transport.send(mimeMessage);
			System.out.println("Sent HTML message with attachment successfully...");

			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return success;
	}
	
	
	public boolean sendEmailWithHtmlContent(String subject, String htmlContent, String to,
		    String fromEmail, String ccMail) {
		    boolean success = false;

		    Properties properties = new Properties();
		    System.out.println("***** In Mail Service  *****");
		    System.out.println("***** From Mail  *****"+from);
		    System.out.println("***** Host  *****"+ host);
		    System.out.println("***** User Name   *****"+username);
		    System.out.println("***** Password   *****"+password);
		    
		    properties.put("mail.smtp.auth", "true");
		    
//		    System.out.println("***** After smtp.auth *****" );
		    properties.put("mail.smtp.starttls.enable", "true");
		    
//		    properties.put("mail.smtp.starttls.enable", "false");
//			properties.put("mail.smtp.ssl.enable", "false");
		    
//		    System.out.println("***** After starttls.enable *****" );
		    properties.put("mail.smtp.host", host);
		    
//		    System.out.println("***** After smtp.host *****" );
		    properties.put("mail.smtp.port", "587");

//		    System.out.println("***** After smtp.port *****" +"587");
		    
		    
//		    System.out.println("***** Going To Create Session *****");
		    Session session = Session.getInstance(properties, new Authenticator() {  	
		    	
		    	
		        @Override
		        protected PasswordAuthentication getPasswordAuthentication() {
		        	
		        	
//		        	System.out.println("***** Going To Create Session 222  *****");
//		        	
//		        	System.out.println("***** PasswordAuthentication *****");
//		        	
//		        	System.out.println("***** Password Authentication  for UserName :" + username + "Password +"+ password);
//		        	
//		        	System.out.println(new PasswordAuthentication(username, password));
		        	
		            return new PasswordAuthentication(username, password);
		        }
		        
		    });

//		    System.out.println("*****PasswordAuthentication Successfull....  *****");
//		    
//		    System.out.println("***** Mail Session Started  *****");
//		    
//		    System.out.println("***** Session Started *****"+session);
		    session.setDebug(true);

		    MimeMessage mimeMessage = new MimeMessage(session);
		    try {
		        mimeMessage.setFrom(new InternetAddress(fromEmail));

		        
		        System.out.println("***** In  mimeMessage *****");
		        
		        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		        mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccMail));

//		        System.out.println("***** Add Recipient *****");
//		        System.out.println(Message.RecipientType.TO);
		        
		        
		        mimeMessage.setSubject(subject);

		        MimeBodyPart htmlPart = new MimeBodyPart();
		        htmlPart.setContent(htmlContent, "text/html");

//		        System.out.println("***** In  htmlContent  *****");
//		        System.out.println(htmlContent);
		        
		        MimeMultipart mimeMultipart = new MimeMultipart("related");
		        mimeMultipart.addBodyPart(htmlPart);

//		        System.out.println("mimeMultipart ");
//		        
//		        System.out.println(mimeMultipart);
		        
		        mimeMessage.setContent(mimeMultipart);

//		        System.out.println(mimeMessage);
		        
		        Transport.send(mimeMessage);
//		        System.out.println(mimeMessage);
//		        
//		        System.out.println("Sent HTML message successfully...");

		        success = true;
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    System.out.println("Email Send Successfully.......");
		    
		    return success;
		}
	
	
	
	public boolean SendOtpOnEmail(String to,String otp ,String nop)
	{
		
		String subject = "Otp for Verification";     
        
        String ccEmail = "sanketghodake316@gmail.com";   
                
        String htmlContent = "<html><body>" +
                "<p>Dear Sir/Madam, Please validate your identity in DGDC E-Custodian with OTP <strong>" + otp + "</strong> for number of packages <strong>" + nop + "</strong>.</p>" +
                "</body></html>"; 
        
        
        
      return sendEmailWithHtmlContent(subject,
				htmlContent, to, from,ccEmail);
		
	}
//	 Email for send Bill to Party 	
	public boolean sendEmailWithTwoPdfAttachments(String to, byte[] pdfBytes1, byte[] pdfBytes2, String body, String subject, String ccEmail, String fileName1, String fileName2) {
	    boolean success = false;

	    Properties properties = new Properties();
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.smtp.host", host);
	    properties.put("mail.smtp.port", "587");

	    Session session = Session.getInstance(properties, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	        }
	    });

	    session.setDebug(true);

	    MimeMessage mimeMessage = new MimeMessage(session);
	    try {
	        mimeMessage.setFrom(new InternetAddress(from));
	        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	        mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccEmail));
	        mimeMessage.setSubject(subject);

	        // Create multipart message
	        MimeMultipart mimeMultipart = new MimeMultipart();

	        // Email body
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setContent(body, "text/html");
	        mimeMultipart.addBodyPart(messageBodyPart);

	        // Attachment 1
	        MimeBodyPart attachmentPart1 = new MimeBodyPart();
	        attachmentPart1.setDataHandler(new DataHandler(new ByteArrayDataSource(pdfBytes1, "application/pdf")));
	        attachmentPart1.setFileName(fileName1);
	        mimeMultipart.addBodyPart(attachmentPart1);

	        // Attachment 2
	        MimeBodyPart attachmentPart2 = new MimeBodyPart();
	        attachmentPart2.setDataHandler(new DataHandler(new ByteArrayDataSource(pdfBytes2, "application/pdf")));
	        attachmentPart2.setFileName(fileName2);
	        mimeMultipart.addBodyPart(attachmentPart2);

	        // Set content
	        mimeMessage.setContent(mimeMultipart);

	        Transport.send(mimeMessage);

	        success = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return success;
	}

	
	@Async
	public CompletableFuture<Boolean> sendRegistrationMail(String user_Email, String compayName, String toName, String userId,String user_Password,String link,String otp) {

		String subject = "Welcome to "+compayName+" - Your Registration is Successful";

		String ccEmail = "sanketghodake316@gmail.com";

		 String htmlContent = "<html><body>" +
	                "<div>Dear Sir/Madam,</div>"+
	                "<br/>"+
	                "<div>          We are delighted to inform you that <strong>"+toName+"</strong> has been successfully registered with <strong>"+compayName+"</strong>. Please find your account details below to access our platform.</div>" +
	                "<br/>"+       
	                "<div style=\"font-size:12px; float:left; width:30%; text-align:left;\">Website Link</div>"+
	                "<div style=\"font-weight:bold; font-size:12px; float:left; width:70%; \">: <strong>"+link+"</strong></div>"+
	                "<br style=\"clear:both;\"/>"+
	                "<div style=\"font-size:12px; float:left; width:30%; text-align:left;\">Username</div>"+
	                "<div style=\"font-weight:bold; font-size:12px; float:left; width:70%; \">: "+userId+"</div>"+
	                "<br style=\"clear:both;\"/>"+
	                "<div style=\"font-size:12px; float:left; width:30%; text-align:left;\">Password</div>"+
	                "<div style=\"font-weight:bold; font-size:12px; float:left; width:70%; \">: "+user_Password+"</div>"+
	                "<br style=\"clear:both;\"/>"+
	                "<div style=\"font-size:12px; float:left; width:30%; text-align:left;\">Temporary OTP</div>"+
	                "<div style=\"font-weight:bold; font-size:12px; float:left; width:70%; \">: "+otp+"</div>"+
	                "<br style=\"clear:both;\"/>"+
	                "<br/>"+    
	                "<div style=\"font-size:12px; float:left; width:100%; text-align:left;\">Thanks & Regards</div>"+
	                "<br/>"+    
	                "<div style=\"font-size:12px; float:left; width:100%; text-align:left;\">"+compayName+"</div>"+
	                "<div>&nbsp;</div>"+
	                "</body></html>"; 

		boolean result = sendEmailWithHtmlContent(subject, htmlContent, user_Email, from, ccEmail);

		return CompletableFuture.completedFuture(result);

	}
	
	}