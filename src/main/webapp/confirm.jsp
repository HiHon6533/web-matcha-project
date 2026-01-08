<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận đơn hàng - HINATFU</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/confirm.css">
    <link rel="icon" type="image/png" href="logo.png">
</head>
<body>
    
    <div class="confirm-wrapper">
        <h2>Xác nhận đơn hàng</h2>

        <div class="customer-info">
            <p><strong>Khách hàng:</strong> ${customer.fullName}</p>
            <p><strong>Email / SĐT:</strong>
                ${customer.account.email} / ${customer.phoneNumber}
            </p>
        </div>

        <form action="${pageContext.request.contextPath}/confirm-action" method="post">
            <input type="hidden" name="amount" value="${amount}"/>
            <input type="hidden" name="orderInfo" value="${orderInfo}"/>

            <div class="address-section">
                <label for="addressId">Chọn địa chỉ nhận hàng:</label>
                <c:choose>
                    <c:when test="${not empty addresses}">
                        <select name="addressId" id="addressId">
                            <c:forEach items="${addresses}" var="addr">
                                <option value="${addr.addressID}">
                                    ${addr.addressDetail} - ${addr.receiverName} (${addr.phoneNumber})
                                </option>
                            </c:forEach>
                        </select>
                        <a href="${pageContext.request.contextPath}/profile">Thêm địa chỉ khác</a>
                    </c:when>
                    <c:otherwise>
                        <p>
                            Bạn chưa có địa chỉ giao hàng.
                            <a href="${pageContext.request.contextPath}/profile">Thêm địa chỉ ngay</a>
                        </p>
                    </c:otherwise>
                </c:choose>

            </div>

            <div class="cart-items">
                <h3>Danh sách sản phẩm</h3>
                <table>
                    <thead>
                        <tr><th>Sản phẩm</th><th>Giá</th><th>Số lượng</th><th>Thành tiền</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${cart.products}" var="line">
                            <tr>
                                <td>${line.product.productName}</td>
                                <td><fmt:formatNumber value="${line.product.price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></td>
                                <td>${line.quantity}</td>
                                <td>
                                    <fmt:formatNumber value="${line.product.price * line.quantity}" type="currency" currencySymbol="đ" maxFractionDigits="0"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="note-section">
                <label for="note">Ghi chú (tùy chọn):</label>
                <textarea name="note" id="note" rows="3" placeholder="Ghi chú cho người giao..."></textarea>
            </div>

            <div class="summary">
                <p><strong>Tổng tiền:</strong> <fmt:formatNumber value="${amount}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></p>
            </div>

            <div class="actions">
                <button type="submit" class="btn-confirm">Xác nhận và Thanh toán</button>
                <a href="${pageContext.request.contextPath}/cart" class="btn-cancel">Quay lại giỏ hàng</a>
            </div>
        </form>
    </div>
</body>
</html>
