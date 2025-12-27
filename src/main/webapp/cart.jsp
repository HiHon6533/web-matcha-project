<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gi? hàng - HINATFU</title>
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
                <span class="subtitle">????</span>
            </a>
        </div>
        <a href="index.html" class="back-link"><i class="fas fa-arrow-left"></i> Ti?p t?c mua s?m</a>
    </header>

    <div class="cart-wrapper">
        <h2 class="page-title">Gi? hàng c?a b?n</h2>

        <div class="cart-container">
            <div class="cart-items">
                </div>

            <div class="cart-summary">
                <div class="summary-card">
                    <h3>C?ng gi? hàng</h3>
                    <div class="summary-row">
                        <span>T?m tính</span>
                        <span id="subtotal">0?</span>
                    </div>
                    <div class="summary-row">
                        <span>Phí v?n chuy?n</span>
                        <span>Mi?n phí</span>
                    </div>
                    <div class="divider"></div>
                    <div class="summary-row total">
                        <span>T?ng c?ng</span>
                        <span id="total-price">0?</span>
                    </div>
                    <button class="btn-checkout">Thanh toán ngay</button>
                    <p class="secure-note"><i class="fas fa-shield-alt"></i> B?o m?t thanh toán 100%</p>
                </div>
            </div>
        </div>
    </div>

    <script src="cart.js"></script>
</body>
</html>