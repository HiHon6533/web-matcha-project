<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh toán thành công</title>
    <link rel="stylesheet" href="payment-result.css">
    <link rel="icon" type="image/png" href="logo.png">
</head>
<body>

<div class="result-container">
    <div class="icon success">
        ✓
    </div>

    <h2>Thanh toán thành công!</h2>
    <p class="desc">
        Cảm ơn bạn đã hoàn tất thanh toán. Giao dịch của bạn đã được xử lý thành công.
    </p>

    <div class="info-box">
        <div class="row">
            <span>Mã giao dịch:</span>
            <span>${param.vnp_TxnRef}</span>
        </div>
        <div class="row">
            <span>Số tiền:</span>
            <span>
                <%= Long.parseLong(request.getParameter("vnp_Amount")) / 100 %> VND
            </span>
        </div>
        <div class="row">
            <span>Nội dung:</span>
            <span>${param.vnp_OrderInfo}</span>
        </div>
        <div class="row">
            <span>Mã ngân hàng:</span>
            <span>${param.vnp_BankCode}</span>
        </div>
        <div class="row">
            <span>Thời gian:</span>
            <span>${param.vnp_PayDate}</span>
        </div>
    </div>

    <a href="home" class="btn-home">Quay lại trang chủ</a>
</div>

</body>
</html>
