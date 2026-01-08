<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận đơn hàng - HINATFU</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/confirm.css">
</head>
<body>

<%-- Fallback: request attribute -> session PENDING_* nếu không có --%>
<c:set var="effectiveCart" value="${cart}" />
<c:if test="${empty effectiveCart}">
    <c:set var="effectiveCart" value="${sessionScope.PENDING_CART}" />
</c:if>

<c:set var="effectiveCustomer" value="${customer}" />
<c:if test="${empty effectiveCustomer}">
    <c:set var="effectiveCustomer" value="${sessionScope.PENDING_CUSTOMER}" />
</c:if>

<c:set var="effectiveAddresses" value="${addresses}" />
<c:if test="${empty effectiveAddresses}">
    <c:set var="effectiveAddresses" value="${sessionScope.PENDING_ADDRESSES}" />
</c:if>

<c:set var="effectiveAmount" value="${amount}" />
<c:if test="${empty effectiveAmount}">
    <c:set var="effectiveAmount" value="${sessionScope.PENDING_AMOUNT}" />
</c:if>

<c:set var="effectiveOrderInfo" value="${orderInfo}" />
<c:if test="${empty effectiveOrderInfo}">
    <c:set var="effectiveOrderInfo" value="${sessionScope.PENDING_ORDERINFO}" />
</c:if>

    <div class="confirm-wrapper">
        <h2>Xác nhận đơn hàng</h2>

        <div class="customer-info">
            <p><strong>Khách hàng:</strong>
                <c:out value="${effectiveCustomer.fullName}" default="(Không có thông tin khách)"/>
            </p>
            <p><strong>Email / SĐT:</strong>
                <c:out value="${effectiveCustomer.account.email}" default="(Không có)"/> /
                <c:out value="${effectiveCustomer.phoneNumber}" default="(Không có)"/>
            </p>
        </div>

        <form action="${pageContext.request.contextPath}/confirm-action" method="post">
            <input type="hidden" name="amount" value="${effectiveAmount}"/>
            <input type="hidden" name="orderInfo" value="${effectiveOrderInfo}"/>

            <div class="address-section">
                <label for="addressId">Chọn địa chỉ nhận hàng:</label>
                <c:choose>
                    <c:when test="${not empty effectiveAddresses}">
                        <select name="addressId" id="addressId">
                            <c:forEach items="${effectiveAddresses}" var="addr">
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
                        <c:forEach items="${effectiveCart.products}" var="line">
                            <tr>
                                <td><c:out value="${line.product.productName}"/></td>
                                <td>
                                    <fmt:formatNumber value="${line.product.price}" type="currency" currencySymbol="đ" maxFractionDigits="0"/>
                                </td>
                                <td><c:out value="${line.quantity}"/></td>
                                <td>
                                    <fmt:formatNumber value="${line.product.price * line.quantity}" type="currency" currencySymbol="đ" maxFractionDigits="0"/>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty effectiveCart.products}">
                            <tr><td colspan="4">Giỏ hàng trống.</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>

            <div class="note-section">
                <label for="note">Ghi chú (tùy chọn):</label>
                <textarea name="note" id="note" rows="3" placeholder="Ghi chú cho người giao..."></textarea>
            </div>

            <div class="summary">
                <p><strong>Tổng tiền:</strong>
                    <fmt:formatNumber value="${effectiveAmount}" type="currency" currencySymbol="đ" maxFractionDigits="0"/>
                </p>
            </div>

            <div class="actions">
                <button type="submit" class="btn-confirm">Xác nhận và Thanh toán</button>
                <a href="${pageContext.request.contextPath}/cart" class="btn-cancel">Quay lại giỏ hàng</a>
            </div>
        </form>



    </div>
</body>
</html>
