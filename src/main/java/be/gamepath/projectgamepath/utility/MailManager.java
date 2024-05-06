package be.gamepath.projectgamepath.utility;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailManager {

    //email account for sending an email.
    private final static String MAIL_ACCOUNT_ADDRESS_SENDER = "projectgamepath@outlook.com";
    private final static String MAIL_ACCOUNT_PASSWORD_SENDER = "mail1234";
    private final static String MAIL_ACCOUNT_NICK_NAME = "GamePath";


    /**
     * function to send an email.
     * @param mailAccountAddressesToSend list of mail addresses to send the email.
     * @param titleEmail title of email (subject).
     * @param bodyEmail body of email (main contend).
     * @param filePath path of file attachment (C://---/---.---).
     */
    public static void sendMail(
            List<String> mailAccountAddressesToSend,
            String titleEmail,
            String bodyEmail,
            String filePath
    ) throws Exception {

        //properties.
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust","*");
        properties.put("mail.transport.protocol", "smtp");

        //session (for connect to the mail account), (need pom dependency com.sun.mail, javax.mail).
        Session session = Session.getInstance(properties, new Authenticator() { //second param, send obj Authenticator.
            protected PasswordAuthentication getPasswordAuthentication() { //override function getter.
                 return new PasswordAuthentication( //return password authentication with mailAddress and password.
                         MAIL_ACCOUNT_ADDRESS_SENDER,
                         MAIL_ACCOUNT_PASSWORD_SENDER
                 );
            }
        });

        try{

            //mime message.
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setSubject(titleEmail); //title.
            mimeMessage.setHeader("Content-Type", "text/html;charset=\"UTF-8\""); //format utf8.
            mimeMessage.setHeader("Content-Transfert-Encoding", "8bit");
            mimeMessage.setFrom(new InternetAddress(MAIL_ACCOUNT_ADDRESS_SENDER, MAIL_ACCOUNT_NICK_NAME)); //sender.
            for(String emailAddressToSend : mailAccountAddressesToSend) { //mail to.
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddressToSend));
            }
            mimeMessage.setReplyTo(new Address[]{new InternetAddress(MAIL_ACCOUNT_ADDRESS_SENDER)});
            mimeMessage.setText(bodyEmail);

            //mime body part.
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(bodyEmail, "UTF-8");

            //multipart (for regroup bodies part).
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            //mime body part (for file attachment) if email has one.
            if(filePath != null){
                MimeBodyPart mimeBodyPartAttachment = new MimeBodyPart();
                DataSource source = new FileDataSource(filePath);
                mimeBodyPartAttachment.setDataHandler(new DataHandler(source));
                Pattern patternGetFileName = Pattern.compile("[a-zA-Z0-9_-]{1,}[.][a-zA-Z0-9_-]{1,}$");
                Matcher matcher = patternGetFileName.matcher(filePath);
                if(!matcher.find()){
                    throw new Exception("error can't extract file name attachment, verify special chars.");
                }
                mimeBodyPartAttachment.setFileName(matcher.group(0));

                //regroup in multipart.
                multipart.addBodyPart(mimeBodyPartAttachment);
            }

            //set multipart in mime message.
            mimeMessage.setContent(multipart);

            //send the email.
            Transport.send(mimeMessage);

        }catch(Exception e){
            Utility.debug("error catch in sendMail : " + e.getMessage());
            throw e; //throw the error before catch, only catch for print in console if the error is from sendMail function.
        }

    }


    /*
     * function to send an email (without file attachment).
     */
    public static void sendMail(
            List<String> mailAccountAddressesToSend,
            String titleEmail,
            String bodyEmail
    ) throws Exception {
        MailManager.sendMail(mailAccountAddressesToSend, titleEmail, bodyEmail, null);
    }

    /*
     * function to send an email (to only one person).
     */
    public static void sendMail(
            String mailAccountAddressToSend,
            String titleEmail,
            String bodyEmail,
            String filePath
    ) throws Exception {
        List<String> mailAccountAddressesToSend = new ArrayList<>();
        mailAccountAddressesToSend.add(mailAccountAddressToSend);
        MailManager.sendMail(mailAccountAddressesToSend, titleEmail, bodyEmail, filePath);
    }


    /*
     * function to send an email (without file attachment and to only one person).
     */
    public static void sendMail(
            String mailAccountAddressToSend,
            String titleEmail,
            String bodyEmail
    ) throws Exception {
        List<String> mailAccountAddressesToSend = new ArrayList<>();
        mailAccountAddressesToSend.add(mailAccountAddressToSend);
        MailManager.sendMail(mailAccountAddressesToSend, titleEmail, bodyEmail, null);
    }

}
