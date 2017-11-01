/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Classe Utilitaria responsavel por facilitar o envio de e-mail de Boas Vindas.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class MailClient implements Serializable {

    /**
     * @param name
     * @param toAddress
     * @param token
     * @param image
     */
    public static void enviarBoasVindas(String name, String toAddress, String token, String image) {

        String html = "<p> Olá ";
        html += "<b>" + name + ", </b> ";
        html += "seja bem vindo ao <b>" + Constants.PRODUCT_NAME + "</b>, ajudaremos você a aumentar suas contratações.";
        html += "</p>";
        html += "<p> <a href='" + Constants.URL_SITE + "'> <img src='" + image + "' alt='Smiley face' height='200' width='350'> </a> </p>";
        html += "<p> <a href='" + Constants.URL_CONFIMACAO + token + "'> Confime seu CADASTRO AQUI !!!</a></p>";

        try {
            Properties props = new Properties();
            props.put("mail.user", Constants.MAIL_USER);
            props.put("mail.password", Constants.MAIL_PASSWORD);
            props.put("mail.smtp.host", Constants.MAIL_HOST);
            props.put("mail.smtp.socketFactory.port", Constants.MAIL_PORT);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", Constants.MAIL_PORT);

            Authenticator auth = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Constants.MAIL_USER, Constants.MAIL_PASSWORD);
                }
            };

            Session session = Session.getInstance(props, auth);
            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            Multipart multipart = new MimeMultipart("alternative");

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(name, "utf-8");

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(html, "text/html; charset=utf-8");

            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);

            msg.setFrom(new InternetAddress(Constants.MAIL_USER));
            InternetAddress[] toAddresses = {new InternetAddress(toAddress)};

            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(Constants.MAIL_SUBJECT_1);
            msg.setSentDate(new Date());

            msg.setContent(multipart);
            Transport.send(msg);

        } catch (Exception ex) {
            System.err.println("ERRO: " + ex);
        }
    }

    /**
     * Enviar e-mail com nova senha.
     * 
     * @param name
     * @param email
     * @param image
     * @param senha
     */
    public static void enviarNovaSenha(String name, String email, String image, String senha) {
        
        String html = "<p> Olá ";
        html += "<b>" + name + ", </b> ";        
        html += "<p><img src='" + image + "' alt='Smiley face' height='200' width='350'></p>";        
        html += "<p>Uma nova senha foi solicitada para o seu Usuário.</p>";
        html += "<p>Nova Senha: <b style='color: blue' >"+senha+"</b></p>";
        html += "<p>Para sua segurança, após o recebimento desta chave crie uma com seus proprios critérios.</p>";

        try {
            Properties props = new Properties();
            props.put("mail.user", Constants.MAIL_USER);
            props.put("mail.password", Constants.MAIL_PASSWORD);
            props.put("mail.smtp.host", Constants.MAIL_HOST);
            props.put("mail.smtp.socketFactory.port", Constants.MAIL_PORT);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", Constants.MAIL_PORT);

            Authenticator auth = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Constants.MAIL_USER, Constants.MAIL_PASSWORD);
                }
            };

            Session session = Session.getInstance(props, auth);
            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            Multipart multipart = new MimeMultipart("alternative");

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(name, "utf-8");

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(html, "text/html; charset=utf-8");

            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);

            msg.setFrom(new InternetAddress(Constants.MAIL_USER));
            InternetAddress[] toAddresses = {new InternetAddress(email)};

            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(Constants.MAIL_SUBJECT_2);
            msg.setSentDate(new Date());

            msg.setContent(multipart);
            Transport.send(msg);

        } catch (Exception ex) {
            System.err.println("ERRO: " + ex);
        }
    }
}
