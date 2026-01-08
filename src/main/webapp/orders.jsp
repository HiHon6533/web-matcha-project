<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <title>Danh sách đơn hàng - Admin</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="orders.css">
</head>
<body>
    <div class="container">
        <h2>Danh sách đơn hàng</h2>
        <a href="${pageContext.request.contextPath}/admin" class="btn btn-primary">Quay về Dashboard</a>
        <table style="width:100%; margin-top:15px; border-collapse: collapse;">
            <thead>
                <tr>
                    <th>Mã đơn</th>
                    <th>Khách hàng</th>
                    <th>Ngày</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td>${order.orderID}</td>
                    <td>
                        ${order.customer.fullName}<br/>
                        <small>${order.customer.phoneNumber}</small>
                    </td>
                    <td>${order.createdAtStr}</td>
                    <td>
                        <fmt:formatNumber value="${order.total}"
                                          type="currency"
                                          currencySymbol="đ"
                                          maxFractionDigits="0"/>
                    </td>
                    <td>${order.orderStatus}</td>
                </tr>
            </c:forEach>
                <c:if test="${empty orders}">
                    <tr><td colspan="5">Chưa có đơn hàng</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
