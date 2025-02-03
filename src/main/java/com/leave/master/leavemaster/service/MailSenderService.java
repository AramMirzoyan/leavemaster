package com.leave.master.leavemaster.service;

/**
 * Service interface for sending emails.
 *
 * <p>Defines methods for sending simple emails and emails with attachments.
 */
public interface MailSenderService {
  /**
   * Sends a simple email message without attachments.
   *
   * @param mailTo The recipient's email address.
   * @param subject The subject of the email.
   * @param text The body content of the email.
   */
  void sendSimpleMessage(String mailTo, String subject, String text);

  /**
   * Sends an email message with an attachment.
   *
   * @param mailTo The recipient's email address.
   * @param subject The subject of the email.
   * @param text The body content of the email.
   * @param pathToAttachment The file path of the attachment.
   */
  void sendMessageWithAttachment(
      String mailTo, String subject, String text, String pathToAttachment);
}
