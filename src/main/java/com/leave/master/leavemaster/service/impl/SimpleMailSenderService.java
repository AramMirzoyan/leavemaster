package com.leave.master.leavemaster.service.impl;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.leave.master.leavemaster.config.MailConfigurationProperties;
import com.leave.master.leavemaster.exceptiondendling.ServiceErrorCode;
import com.leave.master.leavemaster.exceptiondendling.ServiceException;
import com.leave.master.leavemaster.service.MailSenderService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

/**
 * Service for sending emails, including simple messages and messages with attachments.
 *
 * <p>This class can be extended to customize email-sending behavior.
 */
@Service
@RequiredArgsConstructor
public class SimpleMailSenderService implements MailSenderService {

  private final JavaMailSender javaMailSender;
  private final MailConfigurationProperties properties;

  /**
   * Sends a simple email message.
   *
   * <p>Subclasses may override this method to modify how simple emails are sent.
   *
   * @param mailTo The recipient's email address.
   * @param subject The subject of the email.
   * @param text The body content of the email.
   */
  @Override
  public void sendSimpleMessage(String mailTo, String subject, String text) {
    final SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(properties.getUsername());
    message.setTo(mailTo);
    message.setSubject(subject);
    message.setText(text);
    javaMailSender.send(message);
  }

  /**
   * Sends an email message with an attachment.
   *
   * <p>Subclasses may override this method to modify how emails with attachments are sent.
   *
   * @param mailTo The recipient's email address.
   * @param subject The subject of the email.
   * @param text The body content of the email.
   * @param pathToAttachment The file path of the attachment to include.
   * @throws ServiceException if an error occurs while sending the email.
   */
  @Override
  @SuppressWarnings("java:S4449")
  public void sendMessageWithAttachment(
      String mailTo, String subject, String text, String pathToAttachment) {
    var message = javaMailSender.createMimeMessage();
    try {
      var helper = new MimeMessageHelper(message, true);
      helper.setFrom("noreply@yourdomain.com");
      helper.setTo(mailTo);
      helper.setSubject(subject);
      helper.setText(text);

      FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
      String filename = file.getFilename();
      helper.addAttachment(filename, file);
      javaMailSender.send(message);

    } catch (MessagingException e) {
      throw new ServiceException(ServiceErrorCode.CAN_NOT_SEND_EMAIL);
    }
  }
}
