<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đặt lại mật khẩu - HINATFU</title>
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
        <i class="fa-solid fa-key" style="font-size: 40px; color: var(--green);"></i>

        <h2>Mật Khẩu Mới</h2>
        <p class="desc">Thiết lập mật khẩu mới cho tài khoản của bạn.</p>

        <form action="reset-password" method="post">
            
            <c:if test="${not empty error}">
                <div class="error">
                    <i class="fa-solid fa-circle-exclamation"></i> ${error}
                </div>
            </c:if>

            <input type="password" name="newPassword" placeholder="Mật khẩu mới" required autofocus>
            
            <input type="password" name="confirmPassword" placeholder="Xác nhận mật khẩu" required>

            <button type="submit">Đổi mật khẩu</button>
        </form>

        <a href="login.jsp" class="bottom-link">
            Hủy thao tác
        </a>
    </div>

</body>
</html>