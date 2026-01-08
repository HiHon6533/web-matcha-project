<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch sử mua hàng - HINATFU</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&family=Playfair+Display:wght@600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="history.css">
    <link rel="icon" type="image/png" href="logo.png">
</head>
<body>

    <header class="history-header">
        <div class="brand-header">
            <a href="home" class="logo-link">
                <h1>HINATFU</h1>
                <span class="subtitle">まっちゃ</span>
            </a>
        </div>
        <a href="home" class="back-link"><i class="fas fa-arrow-left"></i> Tiếp tục mua sắm</a>
    </header>

    <div class="history-wrapper">
        <h2 class="page-title">Lịch sử mua hàng của bạn</h2>

        <div class="history-container">  
            <c:choose>
                <%-- Chưa mua hàng --%>
                <c:when test="${empty historyOrders}">
                    <div class="history-items" style="justify-content: center; align-items: center; text-align: center;">
                        <p>Bạn chưa mua sản phẩm nào!</p>
                    </div>
                </c:when>
                
                <%-- Danh sách Order --%>
                <c:otherwise>
                    <div class="history-items">
                        <c:forEach items="${historyOrders}" var="order">
                            <div class="item-card">
                                <div class="item-info">
                                   <h3 class="item-name">Đơn hàng số ${order.orderID}</h3>
                                   <p class="item-desc">
                                       Ngày mua: ${line.createdAt} <br>
                                       Danh sách sản phẩm đã mua: <br>
                                        <c:forEach var="line" items="${order.products}">
                                            <c:set var="price" value="${line.product.price}" />
                                            <c:set var="lineTotal" value="${price * line.quantity}" />
                                            <c:set var="grandTotal" value="${grandTotal + lineTotal}" />
                                            <c:choose>
                                                <c:when test="${line.product.productID > 10}">
                                                    - ${line.product.productName} ${line.product.size}, đơn giá: <fmt:formatNumber value="${line.product.price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/>, số lượng: ${line.quantity}.<br>
                                                </c:when>
                                                <c:otherwise>
                                                    - ${line.product.productName}, đơn giá: <fmt:formatNumber value="${line.product.price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/>, số lượng: ${line.quantity}.<br>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        Tổng tiền thanh toán: <fmt:formatNumber value="${order.total}" type="currency" currencySymbol="đ" maxFractionDigits="0"/> <br>
                                    <p> 
                                </div>    
                                <div class="item-status">
                                    <p>${order.orderStatus}</p>
                                </div>
                            </div> 
                        </c:forEach>
                    </div>

                </c:otherwise>
            </c:choose>
            
        </div>
    </div>

    </body>
</html>