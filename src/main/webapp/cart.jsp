<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

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
        <a href="index.jsp" class="back-link"><i class="fas fa-arrow-left"></i> Tiếp tục mua sắm</a>
    </header>

    <div class="cart-wrapper">
        <h2 class="page-title">Giỏ hàng của bạn</h2>

        <div class="cart-container">  
            <c:choose>
                <%-- Giỏ hàng trống --%>
                <c:when test="${empty cart.products}">
                    <div class="cart-items" style="justify-content: center; align-items: center; text-align: center;">
                        <p>Giỏ hàng trống!</p>
                    </div>
                </c:when>
                
                <%-- Có sản phẩm --%>
                <c:otherwise>
                    <div class="cart-items">
                        <c:set var="grandTotal" value="0" />

                        <c:forEach items="${cart.products}" var="line">

                            <%-- Tính tổng tiền --%>
                            <c:set var="price" value="${line.product.price}" />
                            <c:set var="lineTotal" value="${price * line.quantity}" />
                            <c:set var="grandTotal" value="${grandTotal + lineTotal}" />

                            <div class="item-card"> 
                                <div class="item-image">
                                    
                                    <img src="${pageContext.request.contextPath}/${line.product.image}" 
                                         alt="${line.product.productName}" 
                                         onerror="this.src='./matchaImage/Dai.png'">
                                </div>

                                <div class="item-info">
                                    <h3 class="item-name">${line.product.productName}</h3>

                                    <p class="item-desc">
                                        <c:if test="${line.product.productID > 10}">
                                            Size: ${line.product.size} <br>
                                        </c:if>
                                    </p>

                                    <div class="item-price">
                                        <fmt:formatNumber value="${price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/>
                                    </div>
                                </div>

                                <div class="item-actions">
                                    <div class="quantity-control">
                                        <form action="${pageContext.request.contextPath}/updateCart" method="post" class="qty-form">
                                            <input type="hidden" name="action" value="decrease">
                                            <input type="hidden" name="lineId" value="${line.id}">
                                            <button type="submit" class="btn-qty minus"><i class="fas fa-minus"></i></button>
                                        </form>

                                        <input type="text" value="${line.quantity}" readonly style="width: 30px; text-align: center; border: none; background: transparent;">

                                        <form action="${pageContext.request.contextPath}/updateCart" method="post" class="qty-form">
                                            <input type="hidden" name="action" value="increase">
                                            <input type="hidden" name="lineId" value="${line.id}">
                                            <button type="submit" class="btn-qty plus"><i class="fas fa-plus"></i></button>
                                        </form>
                                    </div>

                                    <form action="${pageContext.request.contextPath}/updateCart" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="lineId" value="${line.id}">
                                        <button type="submit" class="btn-remove" onclick="return confirm('Bạn có chắc muốn xóa món này không?');">
                                            <i class="far fa-trash-alt"></i>
                                        </button>
                                    </form>
                                </div> 
                            </div> 
                        </c:forEach>
                    </div>

                    <div class="cart-summary">
                        <div class="summary-card">
                            <h3>Tổng giỏ hàng</h3>
                            <div class="summary-row">
                                <span>Tạm tính</span>
                                <span id="subtotal">
                                    <fmt:formatNumber value="${grandTotal}" type="currency" currencySymbol="đ" maxFractionDigits="0"/>
                                </span>
                            </div>
                            <div class="summary-row">
                                <span>Phí vận chuyển</span>
                                <span>Miễn phí</span>
                            </div>
                            <div class="divider"></div>
                            <div class="summary-row total">
                                <span>Tổng cộng</span>
                                <span id="total-price">
                                    <fmt:formatNumber value="${grandTotal}" type="currency" currencySymbol="đ" maxFractionDigits="0"/>
                                </span>
                            </div>

                            <form action="order" method="POST" id="checkoutForm">
                                <input type="hidden" name="amount" id="vnpay-amount" value="${grandTotal}">
                                <input type="hidden" name="orderInfo" value="Thanh toan don hang Matcha HINATFU">
                                <button type="submit" class="btn-checkout">Thanh toán ngay</button>
                            </form>

                            <p class="secure-note"><i class="fas fa-shield-alt"></i> Bảo mật thanh toán 100%</p>
                        </div>
                    </div>

                </c:otherwise>
            </c:choose>
            
        </div>
    </div>

    </body>
</html>