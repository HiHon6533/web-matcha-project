<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hinatfu Admin - Quản lý cửa hàng</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="icon" type="image/png" href="logo.png">
    <style>
        /* --- 1. GLOBAL STYLES --- */
        :root {
            --primary-color: #3e6540; /* Xanh Matcha */
            --light-bg: #f4f7f4;
            --white: #ffffff;
            --text-color: #333;
            --danger: #d32f2f;
            --warning: #fbc02d;
            --success: #388e3c;
        }

        * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        body { background-color: var(--light-bg); color: var(--text-color); display: flex; min-height: 100vh; }

        /* --- 2. SIDEBAR (THANH BÊN) --- */
        .sidebar {
            width: 260px;
            background-color: var(--primary-color);
            color: var(--white);
            display: flex;
            flex-direction: column;
            position: fixed;
            height: 100%;
            transition: all 0.3s;
        }
        .sidebar-header { padding: 30px 20px; text-align: center; border-bottom: 1px solid rgba(255,255,255,0.1); }
        .sidebar-header h2 { font-size: 24px; letter-spacing: 2px; }
        .sidebar-menu { padding: 20px 0; flex: 1; }
        .menu-item {
            padding: 15px 25px;
            display: flex;
            align-items: center;
            cursor: pointer;
            transition: 0.2s;
            color: rgba(255,255,255,0.8);
            text-decoration: none;
        }
        .menu-item:hover, .menu-item.active { background-color: rgba(255,255,255,0.15); color: #fff; border-left: 4px solid #fff; }
        .menu-item i { margin-right: 15px; width: 20px; text-align: center; }

        /* --- 3. MAIN CONTENT (NỘI DUNG CHÍNH) --- */
        .main-content { margin-left: 260px; flex: 1; padding: 30px; }
        
        /* Header của trang */
        .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        .page-title { color: var(--primary-color); font-size: 28px; font-weight: bold; }
        .user-info { display: flex; align-items: center; gap: 10px; }
        .user-avatar { width: 40px; height: 40px; border-radius: 50%; background: #ccc; }

        /* Các Section (Tab nội dung) */
        .section-tab { display: none; animation: fadeIn 0.4s ease; }
        .section-tab.active { display: block; }

        /* Card thống kê */
        .stats-container { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; margin-bottom: 30px; }
        .stat-card { background: var(--white); padding: 20px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); }
        .stat-card h3 { font-size: 14px; color: #888; margin-bottom: 10px; }
        .stat-card .value { font-size: 24px; font-weight: bold; color: var(--primary-color); }
        
        /* Bộ lọc thời gian */
        .filter-group { display: flex; gap: 10px; margin-bottom: 20px; background: white; padding: 10px; border-radius: 8px; width: fit-content; }
        .filter-btn { padding: 8px 16px; border: none; background: transparent; cursor: pointer; border-radius: 5px; font-weight: 600; color: #666; }
        .filter-btn.active { background-color: var(--primary-color); color: white; }

        /* Bảng dữ liệu (Table) */
        .table-container { background: var(--white); padding: 20px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); overflow-x: auto; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 15px; text-align: left; border-bottom: 1px solid #eee; }
        th { font-weight: 600; color: #555; background-color: #f9f9f9; }
        td img { width: 50px; height: 50px; object-fit: cover; border-radius: 4px; }
        
        /* Nút hành động (Action Buttons) */
        .btn { padding: 8px 15px; border-radius: 5px; border: none; cursor: pointer; font-size: 14px; font-weight: 500; display: inline-flex; align-items: center; gap: 5px; text-decoration: none; }
        .btn-primary { background: var(--primary-color); color: white; }
        .btn-edit { background: var(--warning); color: #333; }
        .btn-delete { background: var(--danger); color: white; }
        .status-badge { padding: 5px 10px; border-radius: 20px; font-size: 12px; font-weight: bold; }
        
        .status-pending { background: #fff3cd; color: #856404; }
        .status-shipping { background: #d1ecf1; color: #0c5460; }
        .status-completed { background: #d4edda; color: #155724; }
        
        /* Modal (Popup thêm sửa xóa) */
        .modal { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 1000; justify-content: center; align-items: center; }
        .modal-content { background: white; width: 500px; padding: 30px; border-radius: 10px; position: relative; }
        .close-modal { position: absolute; top: 15px; right: 20px; font-size: 24px; cursor: pointer; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: 600; }
        .form-group input, .form-group select { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; }

        @keyframes fadeIn { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: translateY(0); } }
    </style>
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
            <a href="#" class="btn btn-delete" style="width: 100%; justify-content: center;">
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
            <div style="display: flex; justify-content: space-between; margin-bottom: 20px;">
                <div class="filter-group" style="margin-bottom: 0;">
                    <button class="filter-btn active">Tất cả</button>
                    <button class="filter-btn">Nguyên liệu Matcha</button>
                    <button class="filter-btn">Sữa & Base</button>
                </div>
                <button class="btn btn-primary" onclick="openModal('addProductModal')">
                    <i class="fas fa-plus"></i> Thêm sản phẩm mới
                </button>
            </div>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Hình ảnh</th>
                            <th>Tên sản phẩm</th>
                            <th>Loại (Category)</th>
                            <th>Giá vốn/bán</th>
                            <th>Kho</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>#M01</td>
                            <td><img src="placeholder-matcha.png" alt="Matcha"></td>
                            <td><b>Bột Matcha Uji Haru</b></td>
                            <td>Matcha</td>
                            <td>250.000đ</td>
                            <td style="color: green;">Còn hàng (5kg)</td>
                            <td>
                                <button class="btn btn-edit"><i class="fas fa-edit"></i></button>
                                <button class="btn btn-delete"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>
                        <tr>
                            <td>#S02</td>
                            <td><img src="placeholder-milk.png" alt="Milk"></td>
                            <td><b>Sữa Yến Mạch Oatside</b></td>
                            <td>Sữa</td>
                            <td>55.000đ</td>
                            <td style="color: red;">Sắp hết (2 hộp)</td>
                            <td>
                                <button class="btn btn-edit"><i class="fas fa-edit"></i></button>
                                <button class="btn btn-delete"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div id="orders" class="section-tab">
            <div class="table-container">
                <div style="display: flex; justify-content: space-between; margin-bottom: 15px;">
                    <h3>Danh sách đơn hàng chờ xử lý</h3>
                    <input type="text" placeholder="Tìm kiếm mã đơn..." style="padding: 8px; border: 1px solid #ddd; border-radius: 5px;">
                </div>
                
                <table>
                    <thead>
                        <tr>
                            <th>Mã ĐH</th>
                            <th>Khách hàng</th>
                            <th>Chi tiết món</th>
                            <th>Tổng tiền</th>
                            <th>Cập nhật trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>#ORD999</td>
                            <td>Trần Thị B<br><small>0905xxx</small></td>
                            <td>
                                1x Matcha Latte (Sữa hạt)<br>
                                2x Bánh quy trà xanh
                            </td>
                            <td><b>185.000đ</b></td>
                            <td>
                                <select style="padding: 5px; border-radius: 4px; border: 1px solid #ddd;">
                                    <option value="pending" selected>Chờ xác nhận</option>
                                    <option value="preparing">Đang pha chế</option>
                                    <option value="shipping">Đang giao</option>
                                    <option value="completed">Hoàn thành</option>
                                    <option value="cancelled">Hủy</option>
                                </select>
                            </td>
                            <td>
                                <button class="btn btn-primary" title="Xem chi tiết & In"><i class="fas fa-eye"></i></button>
                            </td>
                        </tr>
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

    <script>
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
        }
    </script>
</body>
</html>