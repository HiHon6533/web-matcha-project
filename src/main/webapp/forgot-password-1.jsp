<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quên mật khẩu - HINATFU</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <link rel="stylesheet" href="forgot-password.css"> 
</head>
<body>

    <div class="brand">
        <h1>HINATFU</h1>
        <p>Matcha Store & More</p>
    </div>

    <div class="card">
        <i class="fa-solid fa-lock" style="font-size: 40px; color: var(--green);"></i>

        <h2>Quên Mật Khẩu?</h2>
        <p class="desc">Nhập email của bạn để nhận mã xác thực.</p>

        <form action="forgot-password" method="post">
            <c:if test="${not empty error}">
                <div class="error">
                    <i class="fa-solid fa-circle-exclamation"></i> ${error}
                </div>
            </c:if>

            <input type="email" name="email" placeholder="Nhập địa chỉ Email" required autofocus>

            <button type="submit">Gửi mã xác thực</button>
        </form>

        <a href="login.jsp" class="bottom-link">
            <i class="fa-solid fa-arrow-left"></i> Quay lại Đăng nhập
        </a>
    </div>

</body>
</html>