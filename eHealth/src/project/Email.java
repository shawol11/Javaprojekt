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

/**
 *
 * @author htet
 */
public class Email {
    
    private String appointment;
    private String[] receiver;
    private String confirm_subject = "Confirmation";
    private String confirm_body = "Your doctor Appointment has been confirmed!";
    private String cancel_subject = "Canceling your Appointment";
    private String cancel_body = "Your doctor Appointment has been cancelled";
    private String reminder_subject = "Reminder";
    private String reminder_body =  "You have Doctor Appointment in: ";
    private String edit_subject = "Editing your Appointment";
    private String edit_body = "Your Doctor Appointment that has been edited is: ";
   
    /**
     *
     * @param appointment doctor appointment
     * @param to List of patients
     */
    public Email(String appointment, String[] to)
    {
        this.appointment = "\n\n"+appointment+" Uhr";
        this.receiver = to;
    }

    
    public class reminder_time extends TimerTask
    {
        @Override
        public void run() {
        senden(2);          //put 2 as Type for methode "senden()"
        
        }
        
    }
    /*public void confirmation()
    {    
        String subject = this.confirm_subject;
        String body = this.confirm_body;
    }
    */
    
    /**
     *
     * @param remindertype 1 for 1week, 2 for 3days, 3 for 1hour and 4 for 10mins
     * @param remindertime  time when reminder-mail needs to be sent
     */
    public void reminder_funktion(int remindertype, Date remindertime) {
        switch(remindertype)
        {
            case 1->
                this.reminder_body = this.reminder_body + "1 week.";
            case 2->
                this.reminder_body = this.reminder_body + "3 days.";
            case 3->
                this.reminder_body = this.reminder_body + "1 hour.";
            case 4->
                this.reminder_body = this.reminder_body + "10 minutes.";
        }
        new Timer().schedule(new reminder_time(), remindertime);
    }
    

    /**
     * 
     *
     * @param type
     */
    
    public void senden(int type) 
    {
        String [] to = receiver;
        String subject = null;
        String body = null;
        
        switch(type)
        {
            case 1->
            {
                subject = this.edit_subject;
                body = this.edit_body;
                
            }            
            case 2->
            {
                subject = this.reminder_subject;
                body = this.reminder_body;
            }
            
            case 3->
            {
                subject = this.cancel_subject;
                body = this.cancel_body;
            }
 
        }
        body = body+ "\n" + appointment;

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
            System.out.println("Email erfolgereich versendet");

            } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        } 
            
        }
    }
    

