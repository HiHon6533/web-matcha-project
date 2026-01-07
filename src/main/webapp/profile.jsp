<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thông tin cá nhân & Địa chỉ</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <style>
        /* --- 1. CẤU TRÚC CHUNG --- */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 20px;
            color: #333;
        }

        .main-container {
            max-width: 1100px;
            margin: 0 auto;
        }

        /* Nút Back về trang chủ */
        .top-nav {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 20px;
        }

        .btn-home {
            text-decoration: none;
            background: white;
            color: #2e7d32;
            padding: 10px 20px;
            border-radius: 30px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            font-weight: bold;
            display: flex;
            align-items: center;
            gap: 8px;
            transition: 0.3s;
        }
        .btn-home:hover {
            background: #2e7d32;
            color: white;
        }

        /* --- 2. PHẦN THÔNG TIN CÁ NHÂN (USER INFO) --- */
        .user-info-card {
            background: white;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
            display: flex;
            align-items: center;
            gap: 30px;
            margin-bottom: 30px; /* Cách phần địa chỉ ra */
        }

        .avatar-circle {
            width: 80px;
            height: 80px;
            background: #e8f5e9;
            color: #2e7d32;
            font-size: 35px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            border: 3px solid white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .info-details h2 {
            margin: 0 0 5px 0;
            color: #2e7d32;
            font-size: 24px;
        }

        .info-grid {
            display: flex;
            gap: 30px;
            margin-top: 10px;
            color: #555;
            font-size: 14px;
        }

        .info-item i {
            color: #2e7d32;
            margin-right: 5px;
            width: 20px;
            text-align: center;
        }

        /* --- 3. LAYOUT ĐỊA CHỈ (2 CỘT) --- */
        .address-container {
            display: grid;
            grid-template-columns: 1.4fr 1fr; /* Cột trái rộng hơn chút */
            gap: 25px;
        }

        .card {
            background: white;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
            height: fit-content;
        }

        .card-header {
            font-size: 18px;
            font-weight: bold;
            color: #2e7d32;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #f0f0f0;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        /* --- LIST ĐỊA CHỈ --- */
        .address-item {
            border: 1px solid #eee;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 15px;
            position: relative;
            background: #fff;
            transition: 0.2s;
        }
        
        /* Hiệu ứng khi hover */
        .address-item:hover {
            border-color: #2e7d32;
            box-shadow: 0 2px 8px rgba(46, 125, 50, 0.1);
        }

        /* Style cho địa chỉ Mặc định */
        .address-item.default {
            border: 2px solid #2e7d32;
            background-color: #f9fdf9;
        }

        .badge-default {
            position: absolute;
            top: 15px;
            right: 15px;
            background: #2e7d32;
            color: white;
            padding: 4px 10px;
            font-size: 11px;
            border-radius: 20px;
            font-weight: bold;
            text-transform: uppercase;
        }

        .btn-set-default {
            margin-top: 10px;
            font-size: 13px;
            color: #1976d2;
            background: none;
            border: none;
            padding: 0;
            cursor: pointer;
            text-decoration: underline;
        }
        .btn-set-default:hover { color: #0d47a1; }

        /* --- FORM NHẬP LIỆU --- */
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: 500; font-size: 14px; }
        .form-control {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-sizing: border-box;
            font-size: 14px;
        }
        .form-control:focus {
            outline: none;
            border-color: #2e7d32;
            box-shadow: 0 0 0 3px rgba(46,125,50,0.1);
        }

        .btn-submit {
            background: #2e7d32;
            color: white;
            border: none;
            width: 100%;
            padding: 12px;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin-top: 10px;
        }
        .btn-submit:hover { background: #1b5e20; }
        
        .error-msg {
            background: #ffebee;
            color: #c62828;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 15px;
            font-size: 14px;
        }
    </style>
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
                                    <i class="fas fa-check-circle"></i> Đặt làm địa chỉ giao hàng
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
                        <label>Tỉnh / Thành phố <span style="color:red">*</span></label>
                        <input type="text" list="provinceList" name="province" class="form-control" placeholder="Chọn hoặc nhập Tỉnh/Thành" required autocomplete="off">
                        <datalist id="provinceList"></datalist>
                    </div>

                    <div class="form-group">
                        <label>Quận / Huyện <span style="color:red">*</span></label>
                        <input type="text" name="ward" class="form-control" placeholder="Nhập Quận/Huyện" required>
                    </div>

                    <div class="form-group">
                        <label>Phường / Xã <span style="color:red">*</span></label>
                        <input type="text" name="hamlet" class="form-control" placeholder="Nhập Phường/Xã" required>
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