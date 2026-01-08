<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Nhập mã xác thực - HINATFU</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="icon" type="image/png" href="logo.png">
    <link rel="stylesheet" href="forgot-password.css">
</head>
<body>

    <div class="brand">
        <h1>HINATFU</h1>
        <p>Matcha Store & More</p>
    </div>

    <div class="card">
        <i class="fa-solid fa-shield-halved" style="font-size: 40px; color: var(--green);"></i>

        <h2>Nhập Mã OTP</h2>
        <p class="desc">
            Mã xác thực 6 số đã được gửi đến:<br>
            <strong>${sessionScope.resetEmail}</strong>
        </p>

        <form action="verify-otp" method="post">
            
            <c:if test="${not empty error}">
                <div class="error">
                    <i class="fa-solid fa-circle-exclamation"></i> ${error}
                </div>
            </c:if>

            <input type="text" name="otp" class="input-otp" maxlength="6" placeholder="" required autofocus>

            <button type="submit">Xác nhận</button>
        </form>

        <div class="resend-link">
            Bạn không nhận được mã? 
            <a href="forgot-password-1.jsp">Gửi lại</a>
        </div>
    </div>

</body>
</html>