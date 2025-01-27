package utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

public class ReadingEmails {

	Properties properties = null;
	private Session session = null;
	private Store store = null;
	private Folder inbox = null;
	private String result;
	private Date resultDate;
	private String resultContent;
	Path currentRelativePath = Paths.get("");
	String s = currentRelativePath.toAbsolutePath().toString();
	String basePath = s + File.separator;
	String testDownloadedItems=System.getProperty("user.dir")+File.separator +"testDownloadedItems"+File.separator;


	public Store authenticate(String userName, String password) throws MessagingException {
		properties = new Properties();
		properties.setProperty("mail.host", "imap.gmail.com");
		properties.setProperty("mail.port", "993");
		properties.setProperty("mail.transport.protocol", "imaps");
		session = Session.getInstance(properties, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		store = session.getStore("imaps");
		store.connect();
		return store;
	}

	public boolean hasAliasName(String username, String password, String fromName) throws Exception {
		boolean flag = false;
		int count = 0;
		store = authenticate(username, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
		if (messages.length == 0) {
			Thread.sleep(10000);
		} else {
			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				count++;
				//System.out.println("Subject: " + message.getSubject());
				//System.out.println("From: " + message.getFrom()[0]);
				String fromNames = message.getFrom()[0].toString();
				//System.out.println("To: " + message.getAllRecipients()[0]);
				//System.out.println("Date: " + message.getReceivedDate());
				//System.out.println("Size: " + message.getSize());
				//System.out.println("Flags: " + message.getFlags());
				//System.out.println("ContentType: " + message.getContentType());
				// System.out.println("Body: \n//System.out.printlnail));
				// System.out.println("Has Attachments: " +
				// hasAttachments(message));
				if (fromNames.contains(fromName)) {
					flag = true;
					//message.setFlag(Flag.DELETED, true);

					if (message.getContentType().contains("multipart")) {
						MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
						result = getTextFromMimeMultipart(mimeMultipart);
					}

					if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
						result = message.getContent().toString();
					}
				}
				if (flag == true) {
					inbox.close(true);
					break;
				}
				if (count == 10)
					break;
			}
		}
		return flag;
	}

	public String getMailBodyContent(String userName, String password, String subject) throws Exception {
		boolean flag = false;
		int count = 0;
		store = authenticate(userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
		if (messages.length == 0) {
			Thread.sleep(10000);
		} else {
			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				count++;
				//System.out.println("Subject: " + message.getSubject());
				//System.out.println("From: " + message.getFrom()[0]);
				//System.out.println("To: " + message.getAllRecipients()[0]);
				//System.out.println("Date: " + message.getReceivedDate());
				//System.out.println("Size: " + message.getSize());
				//System.out.println("Flags: " + message.getFlags());
				//System.out.println("ContentType: " + message.getContentType());
				// System.out.println("Body: \n" + getEmailBody(mail));
				// System.out.println("Has Attachments: " + hasAttachments(message));
				if (message.getSubject().contains(subject)) {
					flag = true;
					message.setFlag(Flag.SEEN, true);

					if (message.getContentType().contains("multipart")) {
						MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
						result = getTextFromMimeMultipart(mimeMultipart);
					}

					if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
						result = message.getContent().toString();
					}
				}
				if (flag == true) {
					inbox.close(true);
					break;
				}
				if (count == 10)
					break;
			}
		}
		return result;
	}

	public String getMailBodyContent(String userName, String password, String subject, int messageCount) throws Exception {
		boolean flag = false;
		int count = 0;
		store = authenticate(userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
		if (messages.length == 0) {
			Thread.sleep(10000);
		} else {
			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				count++;
				//System.out.println("Subject: " + message.getSubject());
				//System.out.println("From: " + message.getFrom()[0]);
				//System.out.println("To: " + message.getAllRecipients()[0]);
				//System.out.println("Date: " + message.getReceivedDate());
				//System.out.println("Size: " + message.getSize());
				//System.out.println("Flags: " + message.getFlags());
				//System.out.println("ContentType: " + message.getContentType());
				// System.out.println("Body: \n" + getEmailBody(mail));
				// System.out.println("Has Attachments: " + hasAttachments(message));
				if (message.getSubject().contains(subject)) {
					flag = true;
					message.setFlag(Flag.DELETED, true);

					if (message.getContentType().contains("multipart")) {
						MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
						result = getTextFromMimeMultipart(mimeMultipart);
					}

					if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
						result = message.getContent().toString();
					}
				}
				if (flag == true) {
					inbox.close(true);
					break;
				}
				if (count == messageCount)
					break;
			}
		}
		return result;
	}

	public String getMailBodyContentByAliasName(String userName, String password, String fromName) throws Exception {
		boolean flag = false;
		int count = 0;
		store = authenticate(userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
		if (messages.length == 0) {
			Thread.sleep(10000);
		} else {
			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				count++;
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				String fromNames = message.getFrom()[0].toString();
				System.out.println("To: " + message.getAllRecipients()[0]);
				System.out.println("Date: " + message.getReceivedDate());
				System.out.println("Size: " + message.getSize());
				System.out.println("Flags: " + message.getFlags());
				System.out.println("ContentType: " + message.getContentType());
				if (fromNames.contains(fromName)) {
					flag = true;
					message.setFlag(Flag.DELETED, true);

					if (message.getContentType().contains("multipart")) {
						MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
						result = getTextFromMimeMultipart(mimeMultipart);
					}

					if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
						result = message.getContent().toString();
					}
				}
				if (flag == true) {
					inbox.close(true);
					break;
				}
				if (count == 10)
					break;
			}
		}
		return result;
	}

	public String getAliasName(String userName, String password, String fromName) throws Exception {
		boolean flag = false;
		int count = 0;
		store = authenticate(userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
		if (messages.length == 0) {
			Thread.sleep(10000);
		} else {
			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				count++;
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				String fromNames = message.getFrom()[0].toString();
				System.out.println("To: " + message.getAllRecipients()[0]);
				System.out.println("Date: " + message.getReceivedDate());
				System.out.println("Size: " + message.getSize());
				System.out.println("Flags: " + message.getFlags());
				System.out.println("ContentType: " + message.getContentType());
				if (fromNames.contains(fromName)) {
					flag = true;
					message.setFlag(Flag.DELETED, true);
					return fromNames;
				}
				if (flag == true) {
					inbox.close(true);
					break;
				}
				if (count == 10)
					break;
			}
		}
		return null;
	}

	public String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break;
			} else if (bodyPart.isMimeType("text/html")) {
				result = result + "\n" + bodyPart.getContent();
				break;
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

	// App Specific Method
	public void sendEmail(String senderEmail, String senderPassword, String toEmails, String ccEmails, String bccEmails,
			String subject, String body, String testRunStatus) throws MessagingException {
		final String user = senderEmail;
		final String password = senderPassword;

		InternetAddress[] to = InternetAddress.parse(toEmails);
		InternetAddress[] cc = InternetAddress.parse(ccEmails);
		InternetAddress[] bcc = InternetAddress.parse(bccEmails);

		// 1) get the session object
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// 2) compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipients(Message.RecipientType.TO, to);
			message.addRecipients(Message.RecipientType.CC, cc);
			message.addRecipients(Message.RecipientType.BCC, bcc);

			if (testRunStatus.equalsIgnoreCase("fail")) {
				message.setSubject("TEST EXECUTION FAILED - " + subject);
			} else {
				message.setSubject("TEST EXECUTION PASSED - " + subject);
			}

			// 3) create MimeBodyPart object and set your message text
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(body);

			// 4) create new MimeBodyPart object and set DataHandler object to this object
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			String filename = basePath + File.separator + "Zipped Report/Zipped Report.zip";
			DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			if (testRunStatus.equalsIgnoreCase("fail")) {
				messageBodyPart2.setFileName("TEST EXECUTION FAILED - Test Automation Report.zip");
			} else {
				messageBodyPart2.setFileName("TEST EXECUTION PASSED - Test Automation Report.zip");
			}
			// messageBodyPart2.setFileName("Test Automation Report.zip");

			// 5) create Multipart object and add MimeBodyPart objects to this object
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);

			// 6) set the multiplart object to the message object
			message.setContent(multipart);

			// 7) send message
			Transport.send(message);

			//System.out.println("message sent....");
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}

	public void sendEmail(String senderEmail, String senderPassword, String toEmails, String subject, String body)throws MessagingException {
		final String user = senderEmail;
		final String password = senderPassword;

		InternetAddress[] to = InternetAddress.parse(toEmails);

		// 1) get the session object
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// 2) compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipients(Message.RecipientType.TO, to);

			// if (testStatusForEmail.equalsIgnoreCase("fail")) {
			// message.setSubject("FAILED : " + subject);
			// } else{
			// message.setSubject("PASSED : " + subject);
			// }

			message.setSubject(subject);

			// 3) create MimeBodyPart object and set your message text
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(body);

			// 4) create new MimeBodyPart object and set DataHandler object to
			// this object

			// messageBodyPart2.setFileName("Test Automation Report.zip");

			// 5) create Multipart object and add MimeBodyPart objects to this
			// object
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);

			// 6) set the multiplart object to the message object
			message.setContent(multipart);

			// 7) send message
			Transport.send(message);

			//System.out.println("message sent....");
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}

	public void sendEmail(String senderEmail, String senderPassword, String toEmails,String ccEmail, String subject, String body)throws MessagingException {
		final String user = senderEmail;
		final String password = senderPassword;
		InternetAddress[] to = InternetAddress.parse(toEmails);
		InternetAddress[] cc = InternetAddress.parse(ccEmail);
		// 1) get the session object
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		// 2) compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipients(Message.RecipientType.TO, to);
			message.addRecipients(Message.RecipientType.CC, cc);
			// if (testStatusForEmail.equalsIgnoreCase("fail")) {
			// message.setSubject("FAILED : " + subject);
			// } else{
			// message.setSubject("PASSED : " + subject);
			// }
			message.setSubject(subject);
			// 3) create MimeBodyPart object and set your message text
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(body);
			// 4) create new MimeBodyPart object and set DataHandler object to this object
			// messageBodyPart2.setFileName("Test Automation Report.zip");

			// 5) create Multipart object and add MimeBodyPart objects to this object
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			// 6) set the multiplart object to the message object
			message.setContent(multipart);
			// 7) send message
			Transport.send(message);
			//System.out.println("message sent....");
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}

	public String getToMailPerson(String userName, String password, String subject) throws Exception {
		boolean flag = false;
		int count = 0;
		store = authenticate(userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
		if (messages.length == 0) {
			Thread.sleep(10000);
		} else {
			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				count++;
				System.out.println("----------SUBJECT-----------");
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("To: " + message.getAllRecipients()[0]);
				System.out.println("Date: " + message.getReceivedDate());
				System.out.println("Size: " + message.getSize());
				System.out.println("Flags: " + message.getFlags());
				System.out.println("ContentType: " + message.getContentType());
				if (message.getSubject().contains(subject)) {
					System.out.println("-------------------------------------------------------------------");
					flag = true;
					message.setFlag(Flag.SEEN, false);
					if (message.getContentType().contains("multipart")) {
						result = message.getAllRecipients()[0].toString();
					}
					if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
						result = message.getAllRecipients()[0].toString();
					}
				}
				if (flag == true) {
					inbox.close(true);
					break;
				}
				if (count == 10)
					break;
			}
		}
		return result;
	}
	
	public String getCCMailPerson(String userName, String password, String subject) throws Exception {
		boolean flag = false;
		int count = 0;
		store = authenticate(userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), true));
		if (messages.length == 0) {
			Thread.sleep(10000);
		} else {
			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				count++;
				/*
				 * System.out.println("Subject: " + message.getSubject());
				 * System.out.println("From: " + message.getFrom()[0]);
				 * System.out.println("Date: " + message.getReceivedDate());
				 * System.out.println("Size: " + message.getSize());
				 * System.out.println("Flags: " + message.getFlags());
				 * System.out.println("ContentType: " + message.getContentType());
				 */
				//System.out.println("CC : " + message.getAllRecipients()[1]);
				if (message.getSubject().contains(subject)) {
					flag = true;
					message.setFlag(Flag.SEEN, true);
					if (message.getContentType().contains("multipart")) {
						result = message.getAllRecipients()[1].toString();
					}
					if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
						result = message.getAllRecipients()[1].toString();
					}
				}
				if (flag == true) {
					inbox.close(true);
					break;
				}
				if (count == 5)
					break;
			}
		}
		return result;
	}

	public String getMailAttachment(String userName, String password, String subject) throws Exception {
		boolean flag = false;
		int count = 0;
		store = authenticate(userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		String attachFiles = "";
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), true));
		if (messages.length == 0) {
			Thread.sleep(10000);
		} else {
			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				count++;
				if (message.getSubject().contains(subject)) {
					flag = true;
					message.setFlag(Flag.SEEN, true);
					if (message.getContentType().contains("multipart")) {
						Multipart multiPart = (Multipart) message.getContent();
						int numberOfParts = multiPart.getCount();
						for (int partCount = 0; partCount < numberOfParts; partCount++) {
							MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
							if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
								// this part is attachment
								String fileName = part.getFileName();
								attachFiles += fileName + ", ";
								part.saveFile(testDownloadedItems + File.separator + fileName);
							}
							if (attachFiles.length() > 1) {
								attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
							}}if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
								resultContent= message.getContentType();
								if (resultContent != null) {
									String messageContent = resultContent.toString();
								}}}}
				if (flag == true) {
					inbox.close(true);
					break;
				}
				if (count == 5) {
					break;
				}}
		}
		System.out.println("Attach File:------------------"+attachFiles);
		return attachFiles;
	}

	public Date getDateFormatFromMail(String userName, String password, String subject) throws Exception {
		boolean flag = false;
		int count = 0;
		store = authenticate(userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), true));
		if (messages.length == 0) {
			Thread.sleep(10000);
		} else {
			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				count++;
				/*
				 * System.out.println("Subject: " + message.getSubject());
				 * System.out.println("From: " + message.getFrom()[0]);
				 * System.out.println("Date: " + message.getReceivedDate());
				 * System.out.println("Size: " + message.getSize());
				 * System.out.println("Flags: " + message.getFlags());
				 * System.out.println("ContentType: " + message.getContentType());
				 */
				System.out.println("Date : " + message.getReceivedDate());
				if (message.getSubject().contains(subject)) {
					flag = true;
					message.setFlag(Flag.SEEN, true);
					if (message.getContentType().contains("multipart")) {
						resultDate = message.getReceivedDate();
					}
					if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
						resultDate = message.getReceivedDate();
					}
				}
				if (flag == true) {
					inbox.close(true);
					break;
				}
				if (count == 5)
					break;
			}
		}
		System.out.println("Result Date : " + resultDate);
		return resultDate;
	}

	public String getReplyToMailPerson(String userName, String password, String subject) throws Exception {
		boolean flag = false;
		int count = 0;
		store = authenticate(userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), true));
		if (messages.length == 0) {
			Thread.sleep(10000);
		} else {
			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				count++;
				/*
				 * System.out.println("From: " + message.getFrom()[0]);
				 * System.out.println("To: " + message.getAllRecipients()[0]);
				 * System.out.println("Date: " + message.getReceivedDate());
				 * System.out.println("Size: " + message.getSize());
				 * System.out.println("Flags: " + message.getFlags());
				 * System.out.println("ContentType: " + message.getContentType());
				 */
				System.out.println("reply To: " +message.getReplyTo()[0].toString());
				if (message.getSubject().contains(subject)) {
					System.out.println("-------------------------FLAG-------------------");
					flag = true;
					message.setFlag(Flag.SEEN, false);
					if (message.getContentType().contains("multipart")) {
						System.out.println("MULTIPART------------------------------");
						result = message.getReplyTo()[0].toString();
					}
					if (message.isMimeType("text/plain") || message.isMimeType("text/html")) {
						result = message.getReplyTo()[0].toString();
					}
				}
				if (flag == true) {
					inbox.close(true);
					break;
				}
				if (count == 2)
					break;
			}
		}
		return result;
	}


	public String getSubjectOfReceivedMail(String userName, String password, String subject) throws Exception {
		String requiredSubject=null;
		int count = 0;
		store = authenticate(userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
		if (messages.length == 0) {
			Thread.sleep(10000);
		} else {
			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				count++;
				System.out.println("Subject: " + message.getSubject());
				//System.out.println("From: " + message.getFrom()[0]);
				//System.out.println("To: " + message.getAllRecipients()[0]);
				//System.out.println("Date: " + message.getReceivedDate());
				//System.out.println("Size: " + message.getSize());
				//System.out.println("Flags: " + message.getFlags());
				//System.out.println("ContentType: " + message.getContentType());
				if (message.getSubject().contains(subject)) {
					requiredSubject = message.getSubject();
					inbox.close(true);
					break;
				}
				if (count == 10)
					break;
			}
		}
		return requiredSubject;
	}

	public String getSubjectOfReceivedMailFromFirstMail(String userName, String password, String subject) throws Exception { //by selenium+6
		String requiredSubject=null;
		int count = 0;
		store = authenticate(userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
		if (messages.length == 0) {
			Thread.sleep(10000);
		} else {
			for (int i = messages.length - 1; i >= 0; i--) {
				Message message = messages[i];
				count++;
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("To: " + message.getAllRecipients()[0]);
				System.out.println("Date: " + message.getReceivedDate());
				System.out.println("Size: " + message.getSize());
				System.out.println("Flags: " + message.getFlags());
				System.out.println("ContentType: " + message.getContentType());
				if (message.getSubject().contains(subject)) {
					requiredSubject = message.getSubject();
					message.setFlag(Flag.DELETED, true);
					inbox.close(true);
					break;
				}
				if (count == 1)
					break;
			}
		}
		return requiredSubject;
	}

	public Boolean getCountOfTwoMailWithSameSubject(String userName, String password, String subject, int noOfMails)throws Exception {
		boolean subjectCountFlag=false;
		store = authenticate(userName, password);
		inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
		if (messages.length != 0) {
			int noOfSubjects = 0;
			int count = 0;
			for (int i = messages.length - 1; i >= 0; i--) {
				count++;
				Message message = messages[i];
				if (message.getSubject().contains(subject)) {
					noOfSubjects++;
				}
				if (count == noOfMails)
					break;
			}
			if (noOfSubjects == 2) {
				subjectCountFlag =true;
			}
		}
		inbox.close(true);
		return subjectCountFlag;
	}
}
