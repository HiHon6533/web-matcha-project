<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="model.Matcha" %>
<%@ page import="java.math.BigDecimal" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>HINATFU Matcha - Tinh hoa trà Nhật</title>
    <script src="https://kit.fontawesome.com/41b883a0ca.js" crossorigin="anonymous"></script>
    <link rel="icon" type="image/png" href="logo.png">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Serif+JP:wght@400;700&amp;family=Montserrat:wght@300;500&amp;display=swap" rel="stylesheet">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/index.css">
</head>

<body>
<header class="navbar"> 
        <nav class="nav-left">
            <a href="#trangchu" class="logo">
                <h1>HINATFU<span>まっちゃ</span></h1>
            </a>
        </nav>

        <nav class="nav-center">
            <a href="#gioi-thieu">Giới thiệu</a>
            <a href="#noi-bat">Nổi bật</a>
            <a href="#san-pham">Sản phẩm</a>
            <a href="#nguyen-lieu">Nguyên liệu</a>
            <a href="#lien-he">Liên hệ</a>
        </nav>

        <nav class="nav-right">
            
            
            <a href="cart" class="icon-btn"><i class="fas fa-shopping-basket"></i></a>
            
            <c:if test="${sessionScope.LOGGED_IN_USER == null}">
                <a href="login.jsp" class="icon-btn">
                    <i class="fa-solid fa-user-ninja"></i>
                </a>
            </c:if>

            <c:if test="${sessionScope.LOGGED_IN_USER != null}">
                <div class="header-user-actions" style="position: relative; display: inline-flex; align-items: center;">
                    
                    <div class="user-profile-icon icon-btn" onclick="toggleUserMenu()" style="cursor: pointer;">
                        <i class="fa-solid fa-user-ninja"></i>
                    </div>

                    <div class="sub-menu-wrap" id="subMenu">
                        <div class="sub-menu">
                            <div class="user-info">
                                <h3>Xin chào, ${sessionScope.CURRENT_USER.fullName}</h3>
                            </div>
                            <hr>

                            <a href="profile" class="sub-menu-link">
                                <i class="fas fa-user-shield"></i>
                                <p>Thông tin & Bảo mật</p>
                                
                            </a>

                            <a href="history" class="sub-menu-link">
                                <i class="fas fa-history"></i>
                                <p>Lịch sử mua hàng</p>
                                
                            </a>

                            <a href="logout" class="sub-menu-link logout-btn">
                                <i class="fas fa-sign-out-alt"></i>
                                <p>Đăng xuất</p>
                                
                            </a>
                        </div>
                    </div>
                </div>
            </c:if>
        </nav>
    </header>

    <section id="trangchu" class="hero-section">
        <div class="background-container">
            <img src="${pageContext.request.contextPath}/br.png" alt="Matcha Background" />
            <div class="hero-text">
                <h2>Tĩnh lặng trong từng ngụm trà</h2>
                <p>Trải nghiệm hương vị Uji truyền thống giữa lòng thành phố.</p>
            </div>
        </div>
    </section>

    <section id="gioi-thieu" class="section-container about">
        <div class="section-title">
            <span>Giới thiệu</span>
            <h2>Về chúng tôi</h2>
        </div>
        <div class="about-content">
            <p>HINATFU ra đời không chỉ để mang đến một tách trà, mà là một khoảng lặng bình yên giữa nhịp sống hối hả.
                Chúng tôi tin rằng, thưởng thức Matcha là một nghi thức nhỏ giúp tâm hồn bạn tìm lại sự cân bằng quý giá.
                Hành trình của chúng tôi bắt đầu từ vùng đồi Uji, Kyoto - cái nôi thiêng liêng của trà đạo Nhật Bản.           
                Những búp trà non được che nắng cẩn thận suốt 20 ngày để gìn giữ trọn vẹn vị ngọt Umami thanh khiết nhất.
                Qua cối đá Granite nghiền chậm thủ công, từng hạt bột trà giữ nguyên màu xanh ngọc bích và hương thơm cỏ non tươi mới.
                Với triết lý "Nhất Kỳ Nhất Hội", chúng tôi trân trọng từng cơ hội được gửi trao sản phẩm đến tay bạn.
                Hãy để vị trà dẫn lối bạn về với sự an yên, hạnh phúc trong từng phút giây hiện tại.</p>
        </div>
    </section>

    <section id="noi-bat" class="section-container menu">
        <div class="section-title">
            <span>Gợi ý</span>
            <h2>Sản phẩm nổi bật</h2>
        </div>

        <div class="product-grid">
            <c:forEach items="${featuredProducts}" var="p">
                <div class="product-card">
                    <div class="product-img">
                        <img src="${p.image}" alt="${p.productName}" />
                    </div>

                    <div class="product-info">
                        <h3>${p.productName}</h3>
<!--                        <p>Hương vị đậm đà, nguyên liệu tự nhiên (${p.size}).</p>-->
                        <div class="price-add">
                            <span>
                                <fmt:formatNumber value="${p.price}" type="number" maxFractionDigits="0"/>đ
                            </span>
                            <form action="${pageContext.request.contextPath}/addToCart" method="post">
                                <input type="hidden" name="type" value="standard">
                                <input type="hidden" name="productId" value="${p.productID}" />
                                <input type="hidden" name="quantity" value="1" />
                                <button type="submit" class="add-btn">Thêm +</button>
                            </form>
                        </div>
                    </div>
                </div>

            </c:forEach>

        </div>  
    </section>
            
    <section id="san-pham" class="section-container custom-selection">
        <div class="section-title">
            <span>Sáng tạo</span>
            <h2>Tùy chỉnh Matcha của bạn</h2>
        </div>       
        <form action="${pageContext.request.contextPath}/addToCart" method="post" id="customDrinkForm">
        <div class="selection-container">

            <div class="selection-frame" data-category="matcha">
                <h3><i class="fas fa-leaf"></i> 1. Chọn loại Matcha</h3>
                <div class="option-list">
                    <span id="error-matcha" style="color: red; display: none; margin-top: 10px;">
                        <i class="fas fa-exclamation-circle"></i> Vui lòng chọn loại Matcha
                    </span>

                    <c:forEach items="${listMatcha}" var="m">
                        <div class="option-item" data-value="${m.productID}">

                            <img src="${pageContext.request.contextPath}/${m.image != null ? m.image : 'matchaImage/default.png'}" 
                                 alt="${m.productName}">

                            <div class="option-text">
                                <h4>${m.productName}</h4>
                                <p>Xuất xứ: ${m.origin} | Đậm: ${m.strength}/10</p>
                            </div>
                        </div>
                    </c:forEach>
                    <c:if test="${empty listMatcha}">
                        <p>Đang cập nhật danh sách Matcha...</p>
                    </c:if>
                </div>
            </div>

            <div class="selection-frame" data-category="milk">
                <h3><i class="fas fa-water"></i> 2. Chọn loại Sữa</h3>
                <div class="option-list">
                    <span id="error-milk" style="color: red; display: none; margin-top: 10px;">
                        <i class="fas fa-exclamation-circle"></i> Vui lòng chọn loại Sữa
                    </span>


                    <c:forEach items="${listMilk}" var="milk">
                        <div class="option-item" data-value="${milk.productID}">

                            <img src="${pageContext.request.contextPath}/${milk.image != null ? milk.image : 'milkImage/default.png'}" 
                                 alt="${milk.productName}">

                            <div class="option-text">
                                <h4>${milk.productName}</h4>
                                <p>Độ béo: ${milk.fatLevel}/10 | Vị: ${milk.flavor}</p>

                            </div>
                        </div>
                    </c:forEach>

                     <c:if test="${empty listMilk}">
                        <p>Đang cập nhật danh sách Sữa...</p>
                    </c:if>
                </div>
            </div>

            <div class="selection-frame" data-category="size">
                <h3><i class="fas fa-expand-alt"></i> 3. Chọn Size</h3>
                <div class="size-list">
                    <div class="size-item" data-value="S">
                        <i class="fa-solid fa-mug-hot"></i>
                        <span>Size S (180ml)</span>
                    </div>
                    <div class="size-item active" data-value="M">
                        <i class="fa-solid fa-mug-hot" style="font-size: 1.4em;"></i>
                        <span>Size M (350ml)</span>
                    </div>
                    <div class="size-item" data-value="XL">
                        <i class="fa-solid fa-mug-hot"></i>
                        <span>Size XL (500ml)</span>
                    </div>
                </div>
            </div>
        </div>

        <input type="hidden" name="type" value="custom">
        <input type="hidden" name="matchaId" id="matchaId">
        <input type="hidden" name="milkId" id="milkId">
        <input type="hidden" name="size" id="size" value="S">
        <input type="hidden" name="quantity" value="1">

        <div style="text-align: center; margin-top: 40px;">
            <button type="submit" id="confirm-custom" class="add-btn" style="padding: 15px 40px; font-size: 18px;">
                Thêm vào giỏ hàng ngay <i class="fas fa-arrow-right"></i>
            </button>
        </div>
    </form>
    </section>    

    <section id="nguyen-lieu" class="section-container ingredients">
        <div class="section-title">
            <span>Nguyên liệu</span>
            <h2>Bột Matcha Nhật Bản</h2>
        </div>

        <div class="matcha-two-columns">
            
            <c:forEach items="${listMatcha}" var="m">
                
                <div class="ingredient-card" 
                     data-product-id="${m.productID}" 
                     data-price-per-gram="${m.pricePerUnit}">
                    
                    <img src="${pageContext.request.contextPath}/${m.image != null ? m.image : 'matchaImage/default.png'}" 
                         alt="${m.productName}">

                    <div class="ingredient-content">
                        <h4>${m.productName}</h4>
                        <p>${m.origin} | Đơn vị: ${m.unit}</p>

                        <div class="price-info">
                            <span class="price-per-gram">
                                <fmt:formatNumber value="${m.pricePerUnit}" type="number" maxFractionDigits="0"/>đ / ${m.unit}
                            </span>
                            <span class="total-price" id="total-price-${m.productID}">
                                Tổng: <fmt:formatNumber value="${m.pricePerUnit * 50}" type="number" maxFractionDigits="0"/>đ
                            </span>
                        </div>

                        <form action="${pageContext.request.contextPath}/addToCart" method="post">
                            <input type="hidden" name="type" value="standard">
                            <input type="hidden" name="productId" value="${m.productID}" />

                            <div class="quantity-box">
                                <label>Số lượng (${m.unit})</label>
                                <input type="number" 
                                       name="quantity" 
                                       min="10" 
                                       step="10" 
                                       value="50"
                                       oninput="updateIngredientPrice(this, ${m.pricePerUnit}, '${m.productID}')">
                            </div>

                            <div class="stats">
                                <div class="stat">
                                    <span>Độ mạnh</span>
                                    <div class="bar" style="--value:${m.strength}"></div>
                                </div>

                                <div class="stat">
                                    <span>Độ đắng</span>
                                    <div class="bar" style="--value:${m.bitterness}"></div>
                                </div>

                                <div class="stat">
                                    <span>Màu sắc</span>
                                    <div class="bar" style="--value:${m.colorLevel}"></div>
                                </div>

                                <div class="stat">
                                    <span>Hương thơm</span>
                                    <div class="bar" style="--value:${m.aromaLevel}"></div>
                                </div>

                                <div class="stat">
                                    <span>Caffeine</span>
                                    <div class="bar" style="--value:${m.caffeineLevel}"></div>
                                </div>
                            </div>

                            <button type="submit" class="add-btn">Thêm vào giỏ</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
            </div>

    </section>

    <section class="section-container ingredients">

    <div class="section-title">
        <span>Nguyên liệu</span>
        <h2>Sữa & Milk Base</h2>
    </div>

    <div class="matcha-two-columns">

        <c:forEach items="${listMilk}" var="milk">
            
            <div class="ingredient-card"
                 data-product-id="${milk.productID}"
                 data-price-per-gram="${milk.pricePerUnit}">
                
                <img src="${pageContext.request.contextPath}/${milk.image != null ? milk.image : 'milkImage/default.png'}" 
                     alt="${milk.productName}">
                
                <div class="ingredient-content">
                    <h4>${milk.productName}</h4>
                    <p>${milk.origin} | Đơn vị: ${milk.unit}</p>

                    <div class="price-info">
                        <span class="price-per-gram">
                            <fmt:formatNumber value="${milk.pricePerUnit}" type="number" maxFractionDigits="0"/>đ / ${milk.unit}
                        </span>
                        <span class="total-price" id="total-price-${milk.productID}">
                            Tổng: <fmt:formatNumber value="${milk.pricePerUnit * 100}" type="number" maxFractionDigits="0"/>đ
                        </span>
                    </div>

                    <form action="${pageContext.request.contextPath}/addToCart" method="post">
                        <input type="hidden" name="type" value="standard">
                        <input type="hidden" name="productId" value="${milk.productID}" />

                        <div class="quantity-box">
                            <label>Số lượng (${milk.unit})</label>
                            <input type="number" 
                                   name="quantity"
                                   min="50" 
                                   step="50" 
                                   value="100"
                                   oninput="updateIngredientPrice(this, ${milk.pricePerUnit}, '${milk.productID}')">
                        </div>

                        <div class="stats">
                            
                            <div class="stat">
                                <span>Độ béo</span>
                                <div class="bar" style="--value:${milk.fatLevel}"></div>
                            </div>
                            
                            <div class="stat">
                                <span>Vị ngọt</span>
                                <div class="bar" style="--value:${milk.sweetnessLevel}"></div>
                            </div>
                            
                            <div class="stat">
                                <span>Độ sánh</span>
                                <div class="bar" style="--value:${milk.texture}"></div>
                            </div>

                        </div>

                        <button class="add-btn">Thêm vào giỏ</button>
                    </form>
                </div>
            </div>

        </c:forEach>
        </div>
</section>
        
    <footer id="lien-he" class="contact section-container">
        <div class="section-title">
            <span>Kết nối</span>
            <h2>Liên hệ với chúng tôi</h2>
        </div>
        <div class="contact-info">
            <p><i class="fas fa-map-marker-alt"></i> 123 Đường Uji, Quận 1, TP. Hồ Chí Minh</p>
        </div>
    </footer>
    
    <script src="${pageContext.request.contextPath}/index.js"></script>
</body>
<script>
    function updateIngredientPrice(inputElement, pricePerUnit, productId) {
        // Lấy số lượng người dùng nhập
        const quantity = inputElement.value;
        
        // Kiểm tra an toàn: Nếu số lượng < 0 hoặc trống thì không tính
        if (!quantity || quantity < 0) return;

        // Tính tổng: Số lượng * Giá đơn vị
        // Lưu ý: pricePerUnit được truyền vào từ JSP (đã là số)
        const total = quantity * pricePerUnit;

        // Format tiền tệ kiểu Việt Nam (ví dụ: 100.000)
        const formattedTotal = new Intl.NumberFormat('vi-VN').format(total);

        // Tìm thẻ hiển thị tổng tiền và cập nhật nội dung
        const totalElement = document.getElementById('total-price-' + productId);
        if (totalElement) {
            totalElement.innerHTML = 'Tổng: ' + formattedTotal + 'đ';
        } else {
            console.error('Không tìm thấy thẻ có id: total-price-' + productId);
        }
    }
</script>
</html>