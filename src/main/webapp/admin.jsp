<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hinatfu Admin - Quản lý cửa hàng</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="icon" type="image/png" href="logo.png">
    <link rel="stylesheet" href="admin.css">

</head>
<body>

    <div class="sidebar">
        <div class="sidebar-header">
            <h2>HINATFU</h2>
            <p style="font-size: 12px; opacity: 0.7;">Admin Dashboard</p>
        </div>
        <div class="sidebar-menu">
            <a class="menu-item active" onclick="switchTab('dashboard', this)">
                <i class="fas fa-chart-line"></i> Tổng quan Doanh thu
            </a>
            <a class="menu-item" onclick="switchTab('products', this)">
                <i class="fas fa-box-open"></i> Quản lý Sản phẩm
            </a>
            <a class="menu-item" onclick="switchTab('orders', this)">
                <i class="fas fa-shopping-cart"></i> Đơn hàng & Vận đơn
            </a>
        </div>
        <div style="padding: 20px;">
            <a href="logout" class="btn btn-delete" style="width: 100%; justify-content: center;">
                <i class="fas fa-sign-out-alt"></i> Đăng xuất
            </a>
        </div>
    </div>

    <div class="main-content">
        
        <div class="page-header">
            <h2 id="page-title-text" class="page-title">Tổng quan kinh doanh</h2>
            <div class="user-info">
                <span>Xin chào, <b>Admin</b></span>
                <div class="user-avatar"></div>
            </div>
        </div>

        <div id="dashboard" class="section-tab active">
            <div class="filter-group">
                <button class="filter-btn active">Hôm nay</button>
                <button class="filter-btn">Tuần này</button>
                <button class="filter-btn">Tháng này</button>
                <button class="filter-btn">Tùy chọn...</button>
            </div>

            <div class="stats-container">
                <div class="stat-card">
                    <h3>Tổng Doanh Thu</h3>
                    <div class="value">5.200.000đ</div>
                    <small style="color: green;">+12% so với hôm qua</small>
                </div>
                <div class="stat-card">
                    <h3>Đơn hàng mới</h3>
                    <div class="value">24</div>
                </div>
                <div class="stat-card">
                    <h3>Sản phẩm bán chạy nhất</h3>
                    <div class="value" style="font-size: 18px;">Matcha Uji Latte</div>
                </div>
                <div class="stat-card">
                    <h3>Khách hàng mới</h3>
                    <div class="value">8</div>
                </div>
            </div>

            <div class="table-container">
                <h3 style="margin-bottom: 20px; color: var(--primary-color);">Giao dịch gần nhất</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Mã ĐH</th>
                            <th>Thời gian</th>
                            <th>Khách hàng</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>#ORD001</td>
                            <td>10:30 08/01</td>
                            <td>Nguyễn Văn A</td>
                            <td>150.000đ</td>
                            <td><span class="status-badge status-completed">Hoàn thành</span></td>
                        </tr>
                        </tbody>
                </table>
            </div>
        </div>

        <div id="products" class="section-tab">
            

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Hình ảnh</th>
                            <th>Tên sản phẩm</th>
                            
                            <th>Giá </th>
                            <th>Kho</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listMatcha}" var="m">
                        <tr>
                            <td>${m.productID}</td>
                            <td>
                                <img src="${pageContext.request.contextPath}/${m.image != null ? m.image : 'matchaImage/default.png'}" 
                                     alt="${m.productName}" style="width: 50px; height: 50px;">
                            </td>
                            <td><b>${m.productName}</b></td>

                            <td><fmt:formatNumber value="${m.pricePerUnit}" type="number" maxFractionDigits="0"/>đ / ${m.unit}</td>
                            <td style="color: green;"><fmt:formatNumber value="${m.quantity}" type="number" maxFractionDigits="0"/></td>
                            <td>
                                <button class="btn btn-edit" 
                                    onclick="openEditModal('${m.productID}', '${m.productName}', ${m.pricePerUnit}, ${m.quantity}, '${m.unit}', '${m.image}', 'matcha')">
                                    <i class="fas fa-edit"></i>
                                </button>
                            </td>
                        </tr>
                        </c:forEach>
                        <c:forEach items="${listMilk}" var="milk">
                        <tr>
                            <td>${milk.productID}</td>
                            <td>
                                <img src="${pageContext.request.contextPath}/${milk.image != null ? milk.image : 'milkImage/default.png'}" 
                                     alt="${milk.productName}" style="width: 50px; height: 50px;">
                            </td>
                            <td><b>${milk.productName}</b></td>

                            <td><fmt:formatNumber value="${milk.pricePerUnit}" type="number" maxFractionDigits="0"/>đ / ${milk.unit}</td>
                            <td style="color: green;"><fmt:formatNumber value="${milk.quantity}" type="number" maxFractionDigits="0"/></td>
                            <td>
                                <button class="btn btn-edit" 
                                    onclick="openEditModal('${milk.productID}', '${milk.productName}', ${milk.pricePerUnit}, ${milk.quantity}, '${milk.unit}', '${milk.image}', 'milk')">
                                    <i class="fas fa-edit"></i>
                                </button>
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div id="orders" class="section-tab">
            <div class="table-container">
                <div style="display: flex; justify-content: space-between; margin-bottom: 15px;">
                    <h3>Danh sách đơn hàng chờ xử lý</h3>
                </div>
                
                <table>
                    <thead>
                        <tr>
                            <th>Mã đơn hàng</th>
                            <th>Khách hàng</th>
                            <th>Chi tiết món</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái hiện tại</th>
                            <th>Trạng thái mới</th>
                            <th>Cập nhật</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orders}" var="order">
                            <c:if test="${order.orderStatus == 'Đã thanh toán' || order.orderStatus == 'Đang pha chế' 
                                          || order.orderStatus == 'Đang giao'}">
                                <tr>
                                    <td>${order.orderID}</td>
                                    <td>${order.customer.fullName}<br><small>${order.customer.phoneNumber}</small></td>
                                    <td>
                                        <c:forEach items="${order.products}" var="line">
                                            ${line.quantity}x ${line.product.productName}<br>
                                        </c:forEach>
                                    </td>
                                    <td><b><fmt:formatNumber value="${order.total}" type="currency" currencySymbol="đ" maxFractionDigits="0"/></b></td>
                                    <td>${order.orderStatus}</td>
                                    <td>
                                        <select name="status" form="form-${order.orderID}" style="padding: 5px; border-radius: 4px; border: 1px solid #ddd;">
                                            <option value="Đang pha chế" selected>Đang pha chế</option>
                                            <option value="Đang giao">Đang giao</option>
                                            <option value="Hoàn thành">Hoàn thành</option>
                                            <option value="Hủy">Hủy</option>
                                        </select>
                                    </td>
                                    <td>
                                        <form action="updateStatus" method="POST" id="form-${order.orderID}">
                                            <input type="hidden" name="orderId" value="${order.orderID}">
                                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </div> <div id="addProductModal" class="modal">
        <div class="modal-content">
            <span class="close-modal" onclick="closeModal('addProductModal')">&times;</span>
            <h3 style="margin-bottom: 20px; color: var(--primary-color);">Thêm Sản Phẩm / Nguyên Liệu</h3>
            <form>
                <div class="form-group">
                    <label>Tên sản phẩm</label>
                    <input type="text" placeholder="Ví dụ: Matcha Ceremonial...">
                </div>
                <div class="form-group">
                    <label>Loại danh mục</label>
                    <select>
                        <option value="matcha">Nguyên liệu Matcha</option>
                        <option value="milk">Sữa & Base</option>
                        <option value="topping">Topping</option>
                    </select>
                </div>
                <div class="form-group" style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
                    <div>
                        <label>Giá nhập</label>
                        <input type="number">
                    </div>
                    <div>
                        <label>Giá bán</label>
                        <input type="number">
                    </div>
                </div>
                <div class="form-group">
                    <label>Hình ảnh (URL hoặc File)</label>
                    <input type="file">
                </div>
                <button type="button" class="btn btn-primary" style="width: 100%;">Lưu sản phẩm</button>
            </form>
        </div>
    </div>
<div id="editProductModal" class="modal" style="display: none; position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; overflow: auto; background-color: rgba(0,0,0,0.5);">
    <div style="background-color: #fefefe; margin: 10% auto; padding: 20px; border: 1px solid #888; width: 40%; border-radius: 10px;">
        <span onclick="closeModal()" style="color: #aaa; float: right; font-size: 28px; font-weight: bold; cursor: pointer;">&times;</span>
        <h2 style="text-align: center; color: #3A5A40;">Cập nhật sản phẩm</h2>

        <form action="updateProduct" method="post">
            <input type="hidden" id="editType" name="type">
            
            <div class="form-group">
                <label>Mã sản phẩm (ID):</label>
                <input type="text" id="editID" name="id" readonly style="background: #eee;">
            </div>

            <div class="form-group">
                <label>Tên sản phẩm:</label>
                <input type="text" id="editName" name="name" required>
            </div>

            <div class="form-group">
                <label>Giá (VNĐ):</label>
                <input type="number" id="editPrice" name="price" required>
            </div>

            <div class="form-group">
                <label>Số lượng tồn kho:</label>
                <input type="number" id="editQuantity" name="quantity" required step="0.1">
            </div>

            <div class="form-group">
                <label>Đơn vị (Unit):</label>
                <input type="text" id="editUnit" name="unit" required>
            </div>
            
            <div class="form-group">
                <label>Đường dẫn ảnh:</label>
                <input type="text" id="editImage" name="image">
            </div>

            <br>
            <button type="submit" class="btn btn-primary" style="width: 100%; background-color: #3A5A40;">Lưu thay đổi</button>
        </form>
    </div>
</div>

<style>
    .form-group { margin-bottom: 15px; }
    .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
    .form-group input { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
</style>
<script>
    // Hàm mở modal và điền dữ liệu
    function openEditModal(id, name, price, quantity, unit, image, type) {
        document.getElementById('editID').value = id;
        document.getElementById('editName').value = name;
        document.getElementById('editPrice').value = price;
        document.getElementById('editQuantity').value = quantity;
        document.getElementById('editUnit').value = unit;
        document.getElementById('editImage').value = image;
        document.getElementById('editType').value = type; // matcha hoặc milk
        
        document.getElementById('editProductModal').style.display = "block";
    }

    // Hàm đóng modal
    function closeModal() {
        document.getElementById('editProductModal').style.display = "none";
    }
    
    // Đóng khi click ra ngoài modal
    window.onclick = function(event) {
        var modal = document.getElementById('editProductModal');
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

        // Hàm chuyển Tab
        function switchTab(tabId, element) {
            // Ẩn tất cả tab
            document.querySelectorAll('.section-tab').forEach(tab => tab.classList.remove('active'));
            // Bỏ active ở menu cũ
            document.querySelectorAll('.menu-item').forEach(item => item.classList.remove('active'));
            
            // Hiện tab được chọn
            document.getElementById(tabId).classList.add('active');
            // Active menu mới
            element.classList.add('active');

            // Đổi tên tiêu đề
            let title = "";
            if(tabId === 'dashboard') title = "Tổng quan kinh doanh";
            else if(tabId === 'products') title = "Quản lý sản phẩm";
            else if(tabId === 'orders') title = "Quản lý đơn hàng";
            document.getElementById('page-title-text').innerText = title;
        }

        // Modal Logic
        function openModal(modalId) {
            document.getElementById(modalId).style.display = 'flex';
        }
        function closeModal(modalId) {
            document.getElementById(modalId).style.display = 'none';
        }
        // Đóng khi click ra ngoài
        window.onclick = function(event) {
            if (event.target.classList.contains('modal')) {
                event.target.style.display = "none";
            }
        };
    </script>
</body>
</html>