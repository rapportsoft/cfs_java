package com.cwms.service;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Service
public class TicketEmailService {

	@Value("${spring.from.mail}")
	private String from;

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	public boolean sendEmailWithHtmlContent(String subject, String htmlContent, String to, String fromEmail,
			String ccMails, List<String> attachmentPaths) {
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

		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(fromEmail));
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

// Add multiple CC recipients
			if (ccMails != null && !ccMails.isEmpty()) {
				String[] ccList = ccMails.split(",");
				for (String cc : ccList) {
					mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(cc.trim()));
				}
			}

			mimeMessage.setSubject(subject);

// Create a multipart email
			MimeMultipart mimeMultipart = new MimeMultipart();

// HTML Part
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlContent, "text/html");
			mimeMultipart.addBodyPart(htmlPart);

// Attachments
			if (attachmentPaths != null && !attachmentPaths.isEmpty()) {
				for (String filePath : attachmentPaths) {
					File file = new File(filePath);
					if (file.exists()) {
						MimeBodyPart attachmentPart = new MimeBodyPart();
						DataSource source = new FileDataSource(file);
						attachmentPart.setDataHandler(new DataHandler(source));
						attachmentPart.setFileName(file.getName());
						mimeMultipart.addBodyPart(attachmentPart);
					} else {
						System.out.println("File not found: " + filePath);
					}
				}
			}

// Set the content
			mimeMessage.setContent(mimeMultipart);

// Send email
			Transport.send(mimeMessage);
			success = true;

			System.out.println("Email Sent Successfully with Attachments.......");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return success;
	}

	@Async
	public CompletableFuture<Boolean> sendRegistrationMail(String user_Email, String compayName, String sub, String cc,
	                                                       String message, List<String> attachmentPaths, String ticketId,
	                                                       String userId, String branchName, String priority, String category,
	                                                       String status) {
	    String subject = sub;
	    String ccEmail = cc;

	    String htmlContent = "<html><body>" + 
	        "<div>Dear Sir/Madam,</div><br/>" +
	        "<div>I am writing to raise a support ticket regarding the following issue:</div><br/>" +
	        
	        // Ticket Details Table
	        "<table border='1' cellpadding='8' cellspacing='0' style='border-collapse: collapse; width: 100%;'>" +
	        "<tr><th style='background-color:#f2f2f2;'>Field</th><th>Details</th></tr>" +
	        "<tr><td><b>Ticket ID</b></td><td>" + ticketId + "</td></tr>" +
	        "<tr><td><b>Raised By</b></td><td>" + userId + "</td></tr>" +
	        "<tr><td><b>Company/Branch</b></td><td>" + compayName + " / " + branchName + "</td></tr>" +
	        "<tr><td><b>Priority</b></td><td>" + priority + "</td></tr>" +
	        "<tr><td><b>Category</b></td><td>" + category + "</td></tr>" +
	        "<tr><td><b>Status</b></td><td>" + status + "</td></tr>" +
	        "</table><br/>" +

	        // Issue Description in Bullet Points
	        "<div style='font-weight: bold;font-size:15px;'>Issue Description:</div><br/>" +
	        "<ul style='word-wrap: break-word; overflow-wrap: break-word; white-space: pre-wrap;'>" +
	        "<li>" + message.replace("\n", "</li><li>") + "</li>" +
	        "</ul><br/>" +

	        "<div style='text-align:left;'>Thanks & Regards,</div>" +
	        "<div style='text-align:left;'>" + compayName + "</div>" +
	        "<div>&nbsp;</div></body></html>";

	    boolean result = sendEmailWithHtmlContent(subject, htmlContent, user_Email, from, ccEmail, attachmentPaths);

	    return CompletableFuture.completedFuture(result);
	}
	
	@Async
	public CompletableFuture<Boolean> sendApproverRegistrationMail(String user_Email, String compayName, String sub, String cc,
	                                                       String message, List<String> attachmentPaths, String ticketId,
	                                                       String userId, String branchName, String priority, String category,
	                                                       String issue,String status) {
	    String subject = sub;
	    String ccEmail = cc;

	    String htmlContent = "<html><body>" + 
	        "<div>Dear Sir/Madam,</div><br/>" +
	        "<div>The support ticket has been successfully approved. Please find the details below:</div><br/>" +
	        
	        // Ticket Details Table
	        "<table border='1' cellpadding='8' cellspacing='0' style='border-collapse: collapse; width: 100%;'>" +
	        "<tr><th style='background-color:#f2f2f2;'>Field</th><th>Details</th></tr>" +
	        "<tr><td><b>Ticket ID</b></td><td>" + ticketId + "</td></tr>" +
	        "<tr><td><b>Raised By</b></td><td>" + userId + "</td></tr>" +
	        "<tr><td><b>Company/Branch</b></td><td>" + compayName + " / " + branchName + "</td></tr>" +
	        "<tr><td><b>Priority</b></td><td>" + priority + "</td></tr>" +
	        "<tr><td><b>Category</b></td><td>" + category + "</td></tr>" +
	        "<tr><td><b>Status</b></td><td>" + status + "</td></tr>" +
	        "</table><br/>" +

	        // Issue Description in Bullet Points
	        "<div style='font-weight: bold;font-size:15px;'>Issue Description:</div><br/>" +
	        "<ul style='word-wrap: break-word; overflow-wrap: break-word; white-space: pre-wrap;'>" +
	        "<li>" + issue.replace("\n", "</li><li>") + "</li>" +
	        "</ul><br/>" +
	         
             // Approver Message in Bullet Points
            "<div style='font-weight: bold;font-size:15px;'>Approver Remarks:</div><br/>" +
            "<ul style='word-wrap: break-word; overflow-wrap: break-word; white-space: pre-wrap;'>" +
            "<li>" + message.replace("\n", "</li><li>") + "</li>" +
             "</ul><br/>" +

	        "<div style='text-align:left;'>Thanks & Regards,</div>" +
	        "<div style='text-align:left;'>" + compayName + "</div>" +
	        "<div>&nbsp;</div></body></html>";

	    boolean result = sendEmailWithHtmlContent(subject, htmlContent, user_Email, from, ccEmail, attachmentPaths);

	    return CompletableFuture.completedFuture(result);
	}
	
	@Async
	public CompletableFuture<Boolean> sendSolvedRegistrationMail(String user_Email, String compayName, String sub, String cc,
	                                                       String message, List<String> attachmentPaths, String ticketId,
	                                                       String userId, String branchName, String priority, String category,
	                                                       String issue,String status, String serviceProvicer, String desc) {
	    String subject = sub;
	    String ccEmail = cc;

	    String htmlContent = "<html><body>" + 
	        "<div>Dear Sir/Madam,</div><br/>" +
	        "<div>"+desc+"</div><br/>" +
	        
	        // Ticket Details Table
	        "<table border='1' cellpadding='8' cellspacing='0' style='border-collapse: collapse; width: 100%;'>" +
	        "<tr><th style='background-color:#f2f2f2;'>Field</th><th>Details</th></tr>" +
	        "<tr><td><b>Ticket ID</b></td><td>" + ticketId + "</td></tr>" +
	        "<tr><td><b>Raised By</b></td><td>" + userId + "</td></tr>" +
	        "<tr><td><b>Company/Branch</b></td><td>" + compayName + " / " + branchName + "</td></tr>" +
	        "<tr><td><b>Priority</b></td><td>" + priority + "</td></tr>" +
	        "<tr><td><b>Category</b></td><td>" + category + "</td></tr>" +
	        "<tr><td><b>Status</b></td><td>" + status + "</td></tr>" +
	        "</table><br/>" +

	        // Issue Description in Bullet Points
	        "<div style='font-weight: bold;font-size:15px;'>Issue Description:</div><br/>" +
	        "<ul style='word-wrap: break-word; overflow-wrap: break-word; white-space: pre-wrap;'>" +
	        "<li>" + issue.replace("\n", "</li><li>") + "</li>" +
	        "</ul><br/>" +
	         
             // Approver Message in Bullet Points
            "<div style='font-weight: bold;font-size:15px;'>Current Status Remarks:</div><br/>" +
            "<ul style='word-wrap: break-word; overflow-wrap: break-word; white-space: pre-wrap;'>" +
            "<li>" + message.replace("\n", "</li><li>") + "</li>" +
             "</ul><br/>" +

	        "<div style='text-align:left;'>Thanks & Regards,</div>" +
	        "<div style='text-align:left;'>" + serviceProvicer + "</div>" +
	        "<div>&nbsp;</div></body></html>";

	    boolean result = sendEmailWithHtmlContent(subject, htmlContent, user_Email, from, ccEmail, attachmentPaths);

	    return CompletableFuture.completedFuture(result);
	}


}
