package service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;

import model.Order;
import model.ProductLine;

public class EmailService {

    private final String FROM_EMAIL = "phamleanhtu010705@gmail.com";
    private final String PASSWORD = "cssq snix ljxl wgyc";

    public boolean sendVerificationEmail(String toEmail, String name, String token, String baseUrl) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, "HINATFU Support"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Xác thực tài khoản HINATFU của bạn");

            String htmlContent = loadEmailTemplate("verify.html");
            String verifyLink = baseUrl + "/verify?token=" + token;
            htmlContent = htmlContent.replace("[[NAME]]", name);
            htmlContent = htmlContent.replace("[[LINK]]", verifyLink);

            message.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(message);
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean sendChangePasswordEmail(String toEmail, String name, String token) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, "HINATFU Support"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            
            message.setSubject("Mã xác thực đổi mật khẩu - HINATFU");

            String htmlContent = loadEmailTemplate("forgetpass.html");

            htmlContent = htmlContent.replace("[[NAME]]", name);
            htmlContent = htmlContent.replace("[[TOKEN]]", token);
            

            message.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(message);
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean sendOrderConfirmation(String toEmail, Order order) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, "HINATFU Orders"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Xác nhận đơn hàng #" + order.getOrderID() + " - HINATFU");

            String htmlContent = loadEmailTemplate("orderconfirm.html");
            if (htmlContent.isEmpty()) return false;

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedTotal = currencyFormatter.format(order.getTotal());

            StringBuilder productRows = new StringBuilder();
            
            for (ProductLine line : order.getProducts()) {
                String productName = line.getProduct().getProductName(); 
                int quantity = line.getQuantity();

                productRows.append("<tr>");
                
                productRows.append("<td style='border-bottom: 1px solid #eee; padding: 12px; color: #333; font-size: 14px;'>");
                productRows.append(productName);
                productRows.append("</td>");
                
                productRows.append("<td align='center' style='border-bottom: 1px solid #eee; padding: 12px; color: #333; font-weight: bold;'>");
                productRows.append(quantity);
                productRows.append("</td>");
                
                productRows.append("</tr>");
            }

            htmlContent = htmlContent.replace("[[ORDER_ID]]", String.valueOf(order.getOrderID()));
            htmlContent = htmlContent.replace("[[ORDER_DATE]]", order.getCreatedAt().format(dateFormatter));
            
            htmlContent = htmlContent.replace("[[PRODUCT_LIST]]", productRows.toString());
            htmlContent = htmlContent.replace("[[TOTAL]]", formattedTotal);

            message.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(message);
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm đọc file HTML
    private String loadEmailTemplate(String fileName) {
        try {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream("email-templates/" + fileName);
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}