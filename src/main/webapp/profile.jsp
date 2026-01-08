<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thông tin cá nhân & Địa chỉ</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="profile.css">
 
</head>
<body>

    <div class="main-container">
        
        <div class="top-nav">
            <a href="home" class="btn-home">
                <i class="fas fa-arrow-left"></i> Trở về trang chủ
            </a>
        </div>

        <div class="user-info-card">
            <div class="avatar-circle">
                <i class="fas fa-user"></i>
            </div>
            <div class="info-details">
                <h2>${sessionScope.CURRENT_USER.fullName}</h2>
                
                <div class="info-grid">
                    <div class="info-item">
                        <i class="fas fa-envelope"></i>
                        ${sessionScope.LOGGED_IN_USER.email}
                    </div>
                    <div class="info-item">
                        <i class="fas fa-phone"></i>
                        ${sessionScope.CURRENT_USER.phoneNumber != null ? sessionScope.CURRENT_USER.phoneNumber : "Chưa cập nhật SĐT"}
                    </div>
                    <div class="info-item">
                        <i class="fas fa-star"></i>
                        Khách hàng thân thiết
                    </div>
                </div>
            </div>
        </div>

        <div class="address-container">
            
            <div class="card">
                <div class="card-header">
                    <i class="fas fa-map-marked-alt"></i> Sổ địa chỉ của bạn
                </div>

                <c:if test="${empty listAddress}">
                    <div style="text-align: center; color: #777; padding: 20px;">
                        <i class="fas fa-box-open" style="font-size: 40px; color: #ddd; margin-bottom: 10px;"></i>
                        <p>Bạn chưa lưu địa chỉ nào.</p>
                    </div>
                </c:if>

                <c:forEach var="addr" items="${listAddress}">
                    <div class="address-item ${addr.is_default ? 'default' : ''}">
                        
                        <c:if test="${addr.is_default}">
                            <span class="badge-default"><i class="fas fa-check"></i> Mặc định</span>
                        </c:if>

                        <div style="font-weight: bold; font-size: 16px; margin-bottom: 5px;">
                            ${sessionScope.CURRENT_USER.fullName} 
                            <span style="font-weight: normal; color: #777; font-size: 14px;">
                                | ${sessionScope.CURRENT_USER.phoneNumber}
                            </span>
                        </div>
                        
                        <div style="color: #444; margin-bottom: 5px;">
                            ${addr.house_number}, ${addr.hamlet}
                        </div>
                        <div style="color: #444; margin-bottom: 5px;">
                            ${addr.ward}, ${addr.province}
                        </div>
                        
                        <c:if test="${not empty addr.note}">
                            <div style="font-style: italic; color: #888; font-size: 13px;">
                                <i class="fas fa-sticky-note"></i> Note: ${addr.note}
                            </div>
                        </c:if>

                        <c:if test="${!addr.is_default}">
                            <form action="set-default-address" method="post" style="margin-top: 10px;">
                                <input type="hidden" name="addressId" value="${addr.addressID}">
                                <button type="submit" class="btn-set-default">
                                    <i class="fas fa-check-circle"></i> Đặt làm địa chỉ mặc định
                                </button>
                            </form>
                        </c:if>
                    </div>
                </c:forEach>    
            </div>

            <div class="card">
                <div class="card-header">
                    <i class="fas fa-plus-circle"></i> Thêm địa chỉ mới
                </div>

                <c:if test="${not empty errorMessage}">
                    <div class="error-msg">
                        <i class="fas fa-exclamation-triangle"></i> ${errorMessage}
                    </div>
                </c:if>

                <form action="add-address" method="post">
                    <div class="form-group">
                        <label>Thành phố / Tỉnh <span style="color:red">*</span></label>
                        <input type="text" list="provinceList" name="province" class="form-control" placeholder="Nhập Thành phố/Tỉnh" required autocomplete="off">
                        <datalist id="provinceList"></datalist>
                    </div>

                    <div class="form-group">
                        <label>Phường / Xã <span style="color:red">*</span></label>
                        <input type="text" name="ward" class="form-control" placeholder="Nhập Phường/Xã" required>
                    </div>

                    <div class="form-group">
                        <label>Khu phố / Ấp <span style="color:red">*</span></label>
                        <input type="text" name="hamlet" class="form-control" placeholder="Nhập Khu phố/Ấp" required>
                    </div>

                    <div class="form-group">
                        <label>Số nhà / Tên đường</label>
                        <input type="text" name="houseNumber" class="form-control" placeholder="Ví dụ: 123 Đường ABC">
                    </div>

                    <div class="form-group">
                        <label>Ghi chú (Tùy chọn)</label>
                        <input type="text" name="note" class="form-control" placeholder="Ví dụ: Giao giờ hành chính">
                    </div>

                    <div class="form-group" style="display:flex; align-items:center; gap: 10px;">
                        <input type="checkbox" name="isDefault" id="chkDefault" style="width:18px; height:18px;">
                        <label for="chkDefault" style="margin:0; cursor:pointer;">Đặt làm địa chỉ mặc định</label>
                    </div>

                    <button type="submit" class="btn-submit">LƯU ĐỊA CHỈ</button>
                </form>
            </div>

        </div> </div> 

</body>
</html>