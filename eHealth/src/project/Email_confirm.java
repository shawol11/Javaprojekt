/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Htet 
 */
public class Email_confirm {
    
    private String appointment;
    private String[] receiver;
    private String confirm_subject = "Confirmation";
    private String confirm_body = "Your doctor Appointment has been confirmed!";
    
    /**
     *
     * @param appointment doctor appointment
     * @param to List of patients
     */
    public Email_confirm(String appointment, String[] to)
    {
        this.appointment = "\n\nYour Appointment is: "+appointment+" o'clock";
        this.receiver = to;
    }

    public class confirm_time extends TimerTask
    {

        @Override
        public void run() {
        confirmation_mail();
        }
        
    }

    /**
     *
     * @param time time when the appointment is added 
     */
    
    public void confirmation(Date time)
    {
        new Timer().schedule(new confirm_time(), time);
    }
    
    public void confirmation_mail()
    {
        
        String [] to = receiver;
        String subject = this.confirm_subject;
        String body = this.confirm_body;
        body = body+ "\n" + appointment;
        
        //Setup email and password of admin
        String myAcc = "javaproject.ehealth@gmail.com";
        String myPwd = "#JavaProject4";
        
        //Get systen properties
        Properties p = System.getProperties();
        
        //Setup the mail server
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host","smtp.gmail.com");
        p.put("mail.smtp.user", myAcc);
        p.put("mail.smtp.password", myPwd);
        p.put("mail.smtp.port", "587");
        p.put("mail.smtp.auth", "true");
        
        //Creating a session with username and password
        Session session = Session.getDefaultInstance(p,new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAcc, myPwd);  
            }
        });
        
        //Compose the message
            MimeMessage message = new MimeMessage(session);
            try
            {
                message.setFrom(new InternetAddress(myAcc));  
            
            InternetAddress[] toAddress = new InternetAddress[to.length];
            
            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }
            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
            //Set subject and Body of the mail
            message.setSubject(subject);
            message.setText(body);
            //Sent the message
            Transport.send(message);
            System.out.println("Bestätigungsmail erfolgreich versendet");

            } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        } 
            
        }
    

}
