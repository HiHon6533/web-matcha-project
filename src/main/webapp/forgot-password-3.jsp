<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đặt lại mật khẩu - HINATFU</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        :root {
            --green: #3A5A40;
            --cream: #FAF9F4;
            --white: #FFFFFF;
            --text: #1A1A1A;
            --grey: #666666;
            --border: #E0E0E0;
            --red-bg: #ffebee;
            --red-text: #c62828;
        }

        body {
            background-color: var(--cream);
            font-family: 'Inter', sans-serif;
            height: 100vh;
            margin: 0;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .brand h1 {
            font-family: 'Playfair Display', serif;
            color: var(--green);
            font-size: 2.2rem;
            margin: 0 0 5px 0;
            letter-spacing: 2px;
        }
        .brand p {
            color: var(--grey);
            font-size: 0.9rem;
            margin: 0 0 25px 0;
            letter-spacing: 1px;
        }

        .card {
            background: var(--white);
            width: 400px;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.05);
            text-align: center;
        }

        .card h2 {
            font-family: 'Playfair Display', serif;
            color: var(--text);
            margin: 15px 0 10px;
        }

        .card .desc {
            color: var(--grey);
            font-size: 14px;
            margin-bottom: 25px;
            line-height: 1.5;
        }

        input {
            width: 100%;
            background-color: #FAFAFA;
            border: 1px solid var(--border);
            padding: 12px 15px;
            margin-bottom: 15px;
            border-radius: 10px;
            outline: none;
            box-sizing: border-box;
        }
        input:focus {
            border-color: var(--green);
            background-color: #fff;
        }

        button {
            width: 100%;
            background-color: var(--green);
            color: var(--white);
            border: none;
            padding: 12px;
            border-radius: 30px;
            font-weight: bold;
            text-transform: uppercase;
            cursor: pointer;
            transition: 0.2s;
        }
        button:hover {
            opacity: 0.9;
        }

        .error {
            background-color: var(--red-bg);
            color: var(--red-text);
            padding: 10px;
            border-radius: 8px;
            font-size: 13px;
            margin-bottom: 15px;
        }
    </style>
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

        <a href="login.jsp" style="display: block; margin-top: 20px; color: var(--grey); text-decoration: none; font-size: 13px;">
            Hủy thao tác
        </a>
    </div>

</body>
</html>