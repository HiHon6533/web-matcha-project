<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng - HINATFU</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&family=Playfair+Display:wght@600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="cart.css">
</head>
<body>

    <header class="cart-header">
        <div class="brand-header">
            <a href="index.html" class="logo-link">
                <h1>HINATFU</h1>
                <span class="subtitle">まっちゃ</span>
            </a>
        </div>
        <a href="index.html" class="back-link"><i class="fas fa-arrow-left"></i> Tiếp tục mua sắm</a>
    </header>

    <div class="cart-wrapper">
        <h2 class="page-title">Giỏ hàng của bạn</h2>

        <div class="cart-container">
            <div class="cart-items">
                </div>

            <div class="cart-summary">
                <div class="summary-card">
                    <h3>Cộng giỏ hàng</h3>
                    <div class="summary-row">
                        <span>Tạm tính</span>
                        <span id="subtotal">0đ</span>
                    </div>
                    <div class="summary-row">
                        <span>Phí vận chuyển</span>
                        <span>Miễn phí</span>
                    </div>
                    <div class="divider"></div>
                    <div class="summary-row total">
                        <span>Tổng cộng</span>
                        <span id="total-price">10000đ</span>
                    </div>
                    <form action="payment" method="POST" id="checkoutForm">
                        <input type="hidden" name="amount" id="vnpay-amount" value="10000">
                        <input type="hidden" name="orderInfo" value="Thanh toan don hang Matcha HINATFU">
                        <button type="submit" class="btn-checkout">Thanh toán ngay</button>
                    </form>
                    <p class="secure-note"><i class="fas fa-shield-alt"></i> Bảo mật thanh toán 100%</p>
                </div>
            </div>
        </div>
    </div>

    <script src="cart.js"></script>
</body>
</html>