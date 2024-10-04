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

    //MailManager : version with google app token : https://youtu.be/Ug_8d12LNc8?si=8SaYSdbxvSzBoe63

    //email account for sending an email.
    private final static String MAIL_ACCOUNT_ADDRESS_SENDER = "gamepath.project@gmail.com";
    private final static String MAIL_ACCOUNT_PASSWORD = "jygw kmeh helc oyce"; //the token from google app.
    private final static String MAIL_ACCOUNT_SMTP_HOST = "smtp.gmail.com";

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
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", MAIL_ACCOUNT_SMTP_HOST);
        properties.put("mail.smtp.port", "587");

        //authenticator to the email (using token of google app).
        Authenticator authenticator = new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(MAIL_ACCOUNT_ADDRESS_SENDER, MAIL_ACCOUNT_PASSWORD);
            }
        };
        Session session = Session.getInstance(properties, authenticator);

        try{

            //mime message.
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(MAIL_ACCOUNT_ADDRESS_SENDER)); //sender.
            if(mailAccountAddressesToSend.size() == 1){ //mail to (one).
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailAccountAddressesToSend.get(0)));
            }else{
                for(String emailAddressToSend : mailAccountAddressesToSend) { //mail to (many).
                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddressToSend));
                }
            }

            mimeMessage.setSubject(titleEmail); //title.
            mimeMessage.setContent(bodyEmail, "text/html"); //email content (txt).

            mimeMessage.setHeader("Content-Type", "text/html;charset=\"UTF-8\""); //format utf8.
            mimeMessage.setHeader("Content-Transfert-Encoding", "8bit");

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

            //FIXME: the throw exception is temporary disable for prevent bug in mail sender (so the PDF is still generate and order validate).
            //throw e; //throw the error before catch, only catch for print in console if the error is from sendMail function.
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
