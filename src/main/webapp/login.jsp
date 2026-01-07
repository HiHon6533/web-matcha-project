<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập & Đăng ký - HINATFU</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&family=Playfair+Display:wght@600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="login.css">
</head>
<body>

<div class="container" id="container">

    <div class="form-container sign-up-container">
        <form action="${pageContext.request.contextPath}/register" method="post" id="signUpForm">

            <div class="form-content">
                <div class="brand-header">
                    <h1>HINATFU</h1>
                </div>

                <h2 class="form-title">Tạo tài khoản</h2>
                <span class="instruction">Điền thông tin cá nhân của bạn</span>

                <c:if test="${not empty error}">
                    <p class="error-msg">${error}</p>
                </c:if>

                <input type="text" name="fullname" placeholder="Họ và tên"
                       value="${fullname}" required />

                <input type="tel" name="phonenumber" placeholder="Số điện thoại"
                       value="${phonenumber}" required />

                <input type="email" name="email" placeholder="Email"
                       value="${email}" required />

                <input type="password" name="password" placeholder="Mật khẩu" required />

                <button type="submit" class="btn-primary">Đăng ký</button>
            </div>

            <div class="success-message" style="display: none;">
                <div class="icon-circle">
                    <i class="fas fa-check"></i>
                </div>
                <h2 class="form-title">Thành công!</h2>
                <p>Kiểm tra EMAIL để xác thực tài khoản ngay bây giờ.</p>
                <button type="button" class="btn-primary" id="btnSwitchToSignIn">
                    Kiểm tra ngay
                </button>
            </div>

        </form>
    </div>

    <div class="form-container sign-in-container">
        <form action="${pageContext.request.contextPath}/login" method="post" id="signInForm">

            <div class="form-content">
                <div class="brand-header">
                    <h1>HINATFU</h1>
                </div>

                <h2 class="form-title">Đăng nhập</h2>
                <span class="instruction">Chào mừng bạn quay trở lại</span>
                <input type="email" name="email" placeholder="Email" required />
                <input type="password" name="password" placeholder="Mật khẩu" required />

                <button type="submit" class="btn-primary">Đăng nhập</button>
            </div>

        </form>
    </div>

    <div class="overlay-container">
        <div class="overlay">
            <div class="overlay-panel overlay-left">
                <h1>Đã có tài khoản?</h1>
                <p>Hãy đăng nhập để tiếp tục mua sắm.</p>
                <button class="ghost" id="signIn">Đăng nhập</button>
            </div>
            <div class="overlay-panel overlay-right">
                <h1>Khách hàng mới?</h1>
                <p>Đăng ký ngay để nhận ưu đãi đặc biệt.</p>
                <button class="ghost" id="signUp">Đăng ký</button>
            </div>
        </div>
    </div>

</div>

<script src="login.js"></script>

<c:if test="${not empty registerSuccess || not empty error}">
<script>
    document.addEventListener("DOMContentLoaded", () => {
        document.getElementById("container")
                .classList.add("right-panel-active");
    });
</script>
</c:if>

<c:if test="${registerSuccess}">
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const form = document.getElementById("signUpForm");
        form.querySelector(".form-content").style.display = "none";
        form.querySelector(".success-message").style.display = "flex";
    });
</script>
</c:if>

</body>
</html>