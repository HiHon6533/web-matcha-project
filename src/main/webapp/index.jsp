<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>HINATFU Matcha - Tinh hoa trà Nhật</title>
    <script src="https://kit.fontawesome.com/41b883a0ca.js" crossorigin="anonymous"></script>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Serif+JP:wght@400;700&family=Montserrat:wght@300;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="index.css">
    
</head>
<body>
    <!-- ----------------------------- Header ------------------------------------ -->
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
            <a href="history" class="icon-btn">History</a>
            <a href="cart" class="icon-btn"><i class="fas fa-shopping-basket"></i></a>
            <a href="login.jsp" class="icon-btn"><i class="fa-solid fa-user-ninja"></i></a>
        </nav>
    </header>
<!-- ----------------------------------------Background-------------------------------------------- -->
    <section id="trangchu" class="hero-section">
        <div class="background-container">
            <img src="./br.png" alt="Matcha Background" />
            <div class="hero-text">
                <h2>Tĩnh lặng trong từng ngụm trà</h2>
                <p>Trải nghiệm hương vị Uji truyền thống giữa lòng thành phố.</p>
            </div>
        </div>
    </section>
<!-- ----------------------------------------About-------------------------------------------- -->
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
<!-- ----------------------------------------SẢN PHẨM NỘI BẬT -------------------------------------------- -->
    <section id="noi-bat" class="section-container menu">
        <div class="section-title">
            <span>Gợi ý</span>
            <h2>Sản phẩm nổi bật</h2>
        </div>

        <div class="product-grid">
            <!-- PRODUCT 1 -->
            <div class="product-card">
                <div class="product-img">
                    <img src="./SanPhamImage/MatchaDaiSuaTuoi.png"
                         alt="Matcha Đài sữa Meiji" />
                </div>

                <div class="product-info">
                    <h3>Matcha Đài sữa Meiji</h3>
                    <p>Matcha thanh khiết, sữa Meiji mượt mà, hậu vị dịu êm.</p>

                    <div class="price-add">
                        <span>50.000đ</span>

                        <form action="${pageContext.request.contextPath}/addToCart"
                              method="post">
                            <input type="hidden" name="type" value="standard">
                            <input type="hidden" name="productId" value="62" />
                            <input type="hidden" name="quantity" value="1" />
                            <button type="submit" class="add-btn">
                                Thêm +
                            </button>
                        </form>
                    </div>
                </div>
            </div>
                              
            <!-- PRODUCT 2 -->
            <div class="product-card">
                <div class="product-img">
                    <img src="./SanPhamImage/MatchaHaruSuaOatside.png"
                         alt="Matcha Haru sữa hạt Oatside" />
                </div>

                <div class="product-info">
                    <h3>Matcha Haru sữa hạt Oatside</h3>
                    <p>Trà xuân Haru thanh tao, yến mạch bùi béo, thuần khiết.</p>

                    <div class="price-add">
                        <span>55.000đ</span>

                        <form action="${pageContext.request.contextPath}/addToCart"
                              method="post">
                            <input type="hidden" name="type" value="standard">
                            <input type="hidden" name="productId" value="68" />
                            <input type="hidden" name="quantity" value="1" />
                            <button type="submit" class="add-btn">
                                Thêm +
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- PRODUCT 3 -->
            <div class="product-card">
                <div class="product-img">
                    <img src="./SanPhamImage/MatchaFujiMk4SuaOatSide.png"
                         alt="Matcha Fuji Mk4 sữa hạt Oatside" />
                </div>

                <div class="product-info">
                    <h3>Matcha Fuji Mk4 sữa hạt Oatside</h3>
                    <p>Vị trà rang mộc mạc, đậm đà, quyện cùng sữa hạt sánh mịn.</p>

                    <div class="price-add">
                        <span>55.000đ</span>

                        <form action="${pageContext.request.contextPath}/addToCart"
                              method="post">
                            <input type="hidden" name="type" value="standard">
                            <input type="hidden" name="productId" value="76" />
                            <input type="hidden" name="quantity" value="1" />
                            <button type="submit" class="add-btn">
                                Thêm +
                            </button>
                        </form>
                    </div>
                </div>
            </div>
            
        </div>  
    </section>

                              
 <!-- ----------------------------------------Tuy chinh san pham-------------------------------------------- -->  

    <section id="san-pham" class="section-container custom-selection">
        <div class="section-title">
            <span>Sáng tạo</span>
            <h2>Tùy chỉnh Matcha của bạn</h2>
        </div>
        <form action="${pageContext.request.contextPath}/addToCart"
            method="post"
            id="customDrinkForm">
            <div class="selection-container">
                <div class="selection-frame" data-category="matcha">
                    <h3><i class="fas fa-leaf"></i> 1. Chọn loại Matcha</h3>
                    <div class="option-list">
                        <span id="error-matcha" style="color: red; display: none; margin-top: 10px;">
                            <i class="fas fa-exclamation-circle"></i> Vui lòng chọn loại Matcha
                        </span>
                        <div class="option-item" data-value="1">
                            <img src="./matchaImage/Dai.png" alt="Matcha ?ài">
                            <div class="option-text">
                                <h4>Matcha Đài Loan</h4>
                                <p>Vị trà thanh mát, nhẹ nhàng.</p>
                            </div>
                        </div>
                        <div class="option-item" data-value="2">
                            <img src="./matchaImage/Uji.png" alt="Matcha Uji">
                            <div class="option-text">
                                <h4>Matcha Uji Cao Cấp</h4>
                                <p>Tinh hoa Kyoto ??m ?à Umami.</p>
                            </div>
                        </div>
                        <div class="option-item" data-value="3">
                            <img src="./matchaImage/Haru.png" alt="Matcha Haru">
                            <div class="option-text">
                                <h4>Matcha Haru</h4>
                                <p>H??ng c? non t??i m?i.</p>
                            </div>
                        </div>
                        <div class="option-item" data-value="4">
                            <img src="./matchaImage/Fuji03.png" alt="Matcha Fuji 03">
                            <div class="option-text">
                                <h4>Matcha Fuji No.03</h4>
                                <p>Màu xanh th?m, v? m?nh m?.</p>
                            </div>
                        </div>
                        <div class="option-item" data-value="5">
                            <img src="./matchaImage/FujiMk4.png" alt="Matcha Fuji Mk4">
                            <div class="option-text">
                                <h4>Matcha Fuji Mk4</h4>
                                <p>S? cân b?ng hoàn h?o gi?a v? chát nh? và h??ng trà rang ??c tr?ng.</p>
                            </div>
                        </div>
                        <div class="option-item" data-value="6">
                            <img src="./matchaImage/Houjicha.png" alt="Matcha Houjicha">
                            <div class="option-text">
                                <h4>Matcha Houjicha</h4>
                                <p>Trà rang th?m l?ng mùi khói, ít cafein, thích h?p cho bu?i t?i.</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="selection-frame" data-category="milk">
                    <h3><i class="fas fa-water"></i> 2. Ch?n lo?i S?a</h3>
                    <div class="option-list">
                        <span id="error-milk" style="color: red; display: none; margin-top: 10px;">
                            <i class="fas fa-exclamation-circle"></i> Vui lòng chọn loại Sữa
                        </span>
                        <div class="option-item" data-value="7">
                            <img src="./milkImage/SuaTuoi.png" alt="Sữa Tươi">
                            <div class="option-text">
                                <h4>Sữa Tươi Thanh Trùng</h4>
                                <p>Vị kem béo tự nhiên.</p>
                            </div>
                        </div>
                        <div class="option-item" data-value="8">
                            <img src="./milkImage/SuaHatOatside.png" alt="Sữa Oatside">
                            <div class="option-text">
                                <h4>S?a Y?n M?ch Oatside</h4>
                                <p>L?a ch?n thu?n chay hoàn h?o.</p>
                            </div>
                        </div>
                        <div class="option-item" data-value="9">
                            <img src="./milkImage/SuaGau.png" alt="Sữa Gấu">
                            <div class="option-text">
                                <h4>S?a T??i G?u</h4>
                                <p>??m ??c, ít béo, mang l?i c?m giác hoài ni?m và thanh tao.</p>
                            </div>
                        </div>
                        <div class="option-item" data-value="10">
                            <img src="./milkImage/SuaMeiji.png" alt="Sữa Meiji">
                            <div class="option-text">
                                <h4>S?a T??i Meiji</h4>
                                <p>Tiêu chu?n Nh?t B?n m?n màng.</p>
                            </div>
                        </div>
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
                <button type="submit"
                        id="confirm-custom"
                        class="add-btn"
                        style="padding: 15px 40px; font-size: 18px;">
                    Thêm vào giỏ hàng ngay <i class="fas fa-arrow-right"></i>
                </button>
            </div>
        </form>

    </section>
    

<!-- ----------------------------------------NGUYÊN LIỆU MATCHA-------------------------------------------- -->
    <section id="nguyen-lieu" class="section-container ingredients">

    <div class="section-title">
        <span>Nguyên liệu</span>
        <h2>Bột Matcha Nhật Bản</h2>
    </div>

    <div class="matcha-two-columns">

        <!-- MATCHA ĐÀI -->
        <div class="ingredient-card"
        data-product-id="matcha_taiwan"
        data-price-per-gram="600">
        <img src="./matchaImage/Dai.png" alt="Matcha Đài">
        <div class="ingredient-content">
            <h4>Matcha Đài Loan</h4>
            <p>V? trà nh?, thanh mát, d? u?ng cho ng??i m?i.</p>

            <!-- GIÁ -->
            <div class="price-info">
            <span class="price-per-gram">600? / g</span>
            <span class="total-price">T?ng: 30.000?</span>
            </div>
            
            <form action="${pageContext.request.contextPath}/addToCart"
                              method="post">
                <input type="hidden" name="type" value="standard">
                <input type="hidden" name="productId" value="1" />
                <!-- CHỌN GRAM -->
                <div class="quantity-box">
                <label>Số lượng (g)</label>
                <input type="number" name="quantity" min="10" step="10" value="50">
                </div>

                <div class="stats">
                <div class="stat"><span>?? m?nh</span><div class="bar" style="--value:2"></div></div>
                <div class="stat"><span>?? ??ng</span><div class="bar" style="--value:1"></div></div>
                <div class="stat"><span>Màu s?c</span><div class="bar" style="--value:3"></div></div>
                <div class="stat"><span>H??ng th?m</span><div class="bar" style="--value:2"></div></div>
                <div class="stat"><span>Cafeine</span><div class="bar" style="--value:2"></div></div>
                </div>

                <button type="submit" class="add-btn">
                    Thêm vào giỏ
                </button>
            </form>

        </div>
        </div>

        <!-- MATCHA UJI -->
        <div class="ingredient-card"
        data-product-id="matcha_uji"
        data-price-per-gram="1200">
        <img src="./matchaImage/Uji.png" alt="Matcha Uji">
        <div class="ingredient-content">
            <h4>Matcha Uji Cao Cấp</h4>
            <p>Tinh hoa Kyoto, v? umami ??m ?à, h?u ng?t sâu.</p>
            
            <!-- GIÁ -->
            <div class="price-info">
            <span class="price-per-gram">1.200? / g</span>
            <span class="total-price">T?ng: 60.000?</span>
            </div>
            
            <form action="${pageContext.request.contextPath}/addToCart"
                              method="post">
                <input type="hidden" name="type" value="standard">
                <input type="hidden" name="productId" value="2" />
                <!-- CHỌN GRAM -->
                <div class="quantity-box">
                <label>Số lượng (g)</label>
                <input type="number" name="quantity" min="10" step="10" value="50">
                </div>

                <div class="stats">
                <div class="stat"><span>?? m?nh</span><div class="bar" style="--value:4"></div></div>
                <div class="stat"><span>?? ??ng</span><div class="bar" style="--value:3"></div></div>
                <div class="stat"><span>Màu s?c</span><div class="bar" style="--value:5"></div></div>
                <div class="stat"><span>H??ng th?m</span><div class="bar" style="--value:5"></div></div>
                <div class="stat"><span>Cafeine</span><div class="bar" style="--value:4"></div></div>
                </div>
                <button type="submit" class="add-btn">
                    Thêm vào giỏ
                </button>
            </form>
        </div>
        </div>

        <!-- MATCHA HARU -->
        <div class="ingredient-card"
        data-product-id="matcha_haru"
        data-price-per-gram="900">
        <img src="./matchaImage/Haru.png" alt="Matcha Haru">
        <div class="ingredient-content">
            <h4>Matcha Haru</h4>
            <p>H??ng c? non t??i m?i, v? d?u nh?.</p>

            <!-- GIÁ -->
            <div class="price-info">
            <span class="price-per-gram">900? / g</span>
            <span class="total-price">T?ng: 45.000?</span>
            </div>

            <form action="${pageContext.request.contextPath}/addToCart"
                              method="post">
                <input type="hidden" name="type" value="standard">
                <input type="hidden" name="productId" value="3" />
                <!-- CHỌN GRAM -->
                <div class="quantity-box">
                <label>Số lượng (g)</label>
                <input type="number" name="quantity" min="10" step="10" value="50">
                </div>

                <div class="stats">
                <div class="stat"><span>?? m?nh</span><div class="bar" style="--value:3"></div></div>
                <div class="stat"><span>?? ??ng</span><div class="bar" style="--value:2"></div></div>
                <div class="stat"><span>Màu s?c</span><div class="bar" style="--value:4"></div></div>
                <div class="stat"><span>H??ng th?m</span><div class="bar" style="--value:4"></div></div>
                <div class="stat"><span>Cafeine</span><div class="bar" style="--value:3"></div></div>
                </div>
                <button type="submit" class="add-btn">
                    Thêm vào giỏ
                </button>
            </form>
        </div>
        </div>

        <!-- MATCHA FUJI 03 -->
        <div class="ingredient-card"
        data-product-id="matcha_fuji_03"
        data-price-per-gram="1100">
        <img src="./matchaImage/Fuji03.png" alt="Matcha Fuji 03">
        <div class="ingredient-content">
            <h4>Matcha Fuji No.03</h4>
            <p>Màu xanh ??m, v? m?nh m?, h?u trà rõ.</p>

            <!-- GIÁ -->
            <div class="price-info">
            <span class="price-per-gram">1.100? / g</span>
            <span class="total-price">T?ng: 55.000?</span>
            </div>

            <form action="${pageContext.request.contextPath}/addToCart"
                              method="post">
                <input type="hidden" name="type" value="standard">
                <input type="hidden" name="productId" value="4" />
                <!-- CHỌN GRAM -->
                <div class="quantity-box">
                <label>Số lượng (g)</label>
                <input type="number" name="quantity" min="10" step="10" value="50">
                </div>

                <div class="stats">
                <div class="stat"><span>?? m?nh</span><div class="bar" style="--value:4"></div></div>
                <div class="stat"><span>?? ??ng</span><div class="bar" style="--value:4"></div></div>
                <div class="stat"><span>Màu s?c</span><div class="bar" style="--value:4"></div></div>
                <div class="stat"><span>H??ng th?m</span><div class="bar" style="--value:3"></div></div>
                <div class="stat"><span>Cafeine</span><div class="bar" style="--value:4"></div></div>
                </div>

                <button type="submit" class="add-btn">
                    Thêm vào giỏ
                </button>
            </form>
        </div>
        </div>

        <!-- MATCHA FUJI MK4 -->
        <div class="ingredient-card"
        data-product-id="matcha_fuji_Mk4"
        data-price-per-gram="1500">
        <img src="./matchaImage/FujiMk4.png" alt="Matcha Fuji Mk4">
        <div class="ingredient-content">
            <h4>Matcha Fuji Mk4</h4>
            <p>Dòng cao c?p, v? trà ??m sâu, giàu n?ng l??ng.</p>

            <!-- GIÁ -->
            <div class="price-info">
            <span class="price-per-gram">1.500? / g</span>
            <span class="total-price">T?ng: 75.000?</span>
            </div>

            <form action="${pageContext.request.contextPath}/addToCart"
                              method="post">
                <input type="hidden" name="type" value="standard">
                <input type="hidden" name="productId" value="5" />
                <!-- CHỌN GRAM -->
                <div class="quantity-box">
                <label>Số lượng (g)</label>
                <input type="number" name="quantity" min="10" step="10" value="50">
                </div>

                <div class="stats">
                <div class="stat"><span>?? m?nh</span><div class="bar" style="--value:5"></div></div>
                <div class="stat"><span>?? ??ng</span><div class="bar" style="--value:4"></div></div>
                <div class="stat"><span>Màu s?c</span><div class="bar" style="--value:5"></div></div>
                <div class="stat"><span>H??ng th?m</span><div class="bar" style="--value:4"></div></div>
                <div class="stat"><span>Cafeine</span><div class="bar" style="--value:5"></div></div>
                </div>

                <button type="submit" class="add-btn">
                    Thêm vào giỏ
                </button>
            </form>
        </div>
        </div>

        <!-- HOUJICHA -->
        <div class="ingredient-card"
        data-product-id="matcha_Houjicha"
        data-price-per-gram="700">
        <img src="./matchaImage/Houjicha.png" alt="Houjicha">
        <div class="ingredient-content">
            <h4>Houjicha</h4>
            <p>Trà rang th?m mùi khói, r?t ít cafeine.</p>

            <!-- GIÁ -->
            <div class="price-info">
            <span class="price-per-gram">700? / g</span>
            <span class="total-price">T?ng: 35.000?</span>
            </div>

            <form action="${pageContext.request.contextPath}/addToCart"
                              method="post">
                <input type="hidden" name="type" value="standard">
                <input type="hidden" name="productId" value="6" />
                <!-- CHỌN GRAM -->
                <div class="quantity-box">
                <label>Số lượng (g)</label>
                <input type="number" name="quantity" min="10" step="10" value="50">
                </div>

                <div class="stats">
                <div class="stat"><span>?? m?nh</span><div class="bar" style="--value:2"></div></div>
                <div class="stat"><span>?? ??ng</span><div class="bar" style="--value:1"></div></div>
                <div class="stat"><span>Màu s?c</span><div class="bar" style="--value:2"></div></div>
                <div class="stat"><span>H??ng th?m</span><div class="bar" style="--value:5"></div></div>
                <div class="stat"><span>Cafeine</span><div class="bar" style="--value:1"></div></div>
                </div>

                <button type="submit" class="add-btn">
                    Thêm vào giỏ
                </button>
            </form>
        </div>
        </div>

    </div>
    </section>


<!-- ----------------------------------------Nguyên liệu sữa-------------------------------------------- -->
    <section class="section-container ingredients">

    <div class="section-title">
        <span>Nguyên liệu</span>
        <h2>Sữa & Milk Base</h2>
    </div>

    <div class="matcha-two-columns">

        <!-- Sữa tươi -->
        <div class="ingredient-card"
        data-product-id="milk_fresh"
        data-price-per-gram="150">
        <img src="./milkImage/SuaTuoi.png" alt="S?a t??i">
        <div class="ingredient-content">
            <h4>Sữa tươi</h4>
            <p>V? s?a t? nhiên, nh?, làm n?n cho matcha.</p>

            <div class="price-info">
            <span class="price-per-gram">150? / ml</span>
            <span class="total-price">T?ng: 1.500?</span>
            </div>

            <form action="${pageContext.request.contextPath}/addToCart"
                              method="post">
                <input type="hidden" name="type" value="standard">
                <input type="hidden" name="productId" value="7" />
                <div class="quantity-box">
                <label>Số lượng (ml)</label>
                <input type="number" name="quantity" min="50" step="50" value="100">
                </div>

                <div class="stats">
                <div class="stat"><span>?? béo</span><div class="bar" style="--value:2"></div></div>
                <div class="stat"><span>V? ng?t</span><div class="bar" style="--value:2"></div></div>
                <div class="stat"><span>H??ng v?</span><div class="bar" style="--value:2"></div></div>
                <div class="stat"><span>?? sánh</span><div class="bar" style="--value:2"></div></div>
                </div>

                <button type="submit" class="add-btn">
                    Thêm vào giỏ
                </button>
            </form>
        </div>
        </div>

        <!-- SỮA MEIJI -->
        <div class="ingredient-card"
        data-product-id="milk_meiji"
        data-price-per-gram="180">
        <img src="./milkImage/SuaMeiji.png" alt="S?a Meiji">
        <div class="ingredient-content">
            <h4>Sữa Meiji</h4>
            <p>S?a Nh?t béo nh?, h?u ng?t d?u.</p>

            <div class="price-info">
            <span class="price-per-gram">180? / ml</span>
            <span class="total-price">T?ng: 1.800?</span>
            </div>

            <form action="${pageContext.request.contextPath}/addToCart"
                              method="post">
                <input type="hidden" name="type" value="standard">
                <input type="hidden" name="productId" value="10" />
                <div class="quantity-box">
                <label>Số lượng (ml)</label>
                <input type="number" name="quantity" min="50" step="50" value="100">
                </div>

                <div class="stats">
                <div class="stat"><span>Độ béo</span><div class="bar" style="--value:3"></div></div>
                <div class="stat"><span>V? ng?t</span><div class="bar" style="--value:3"></div></div>
                <div class="stat"><span>H??ng v?</span><div class="bar" style="--value:4"></div></div>
                <div class="stat"><span>?? sánh</span><div class="bar" style="--value:3"></div></div>
                </div>
                <button type="submit" class="add-btn">
                    Thêm vào giỏ
                </button>
            </form>
        </div>
        </div>

        <!-- SỮA GẤU -->
        <div class="ingredient-card"
        data-product-id="milk_gau"
        data-price-per-gram="200">
        <img src="./milkImage/SuaGau.png" alt="S?a G?u">
        <div class="ingredient-content">
            <h4>Sữa Gấu</h4>
            <p>Béo ??m, ng?t rõ, h?p matcha m?nh.</p>

            <div class="price-info">
            <span class="price-per-gram">200? / ml</span>
            <span class="total-price">T?ng: 2.000?</span>
            </div>

            <form action="${pageContext.request.contextPath}/addToCart"
                  method="post">
                <input type="hidden" name="type" value="standard">
                <input type="hidden" name="productId" value="9" />
                <div class="quantity-box">
                <label>Số lượng (ml)</label>
                <input type="number" name="quantity" min="50" step="50" value="100">
                </div>

                <div class="stats">
                <div class="stat"><span>?? béo</span><div class="bar" style="--value:5"></div></div>
                <div class="stat"><span>V? ng?t</span><div class="bar" style="--value:4"></div></div>
                <div class="stat"><span>H??ng v?</span><div class="bar" style="--value:4"></div></div>
                <div class="stat"><span>?? sánh</span><div class="bar" style="--value:4"></div></div>
                </div>

                <button type="submit" class="add-btn">
                    Thêm vào giỏ
                </button>
            </form>
        </div>
        </div>

        <!-- SỮA HẠT OATSIDE -->
        <div class="ingredient-card"
        data-product-id="milk_oatside"
        data-price-per-gram="220">
        <img src="./milkImage/SuaHatOatside.png" alt="S?a Oatside">
        <div class="ingredient-content">
            <h4>Sữa hạt Oatside</h4>
            <p>Ít ng?t, béo m?n, h??ng y?n m?ch.</p>

            <div class="price-info">
            <span class="price-per-gram">220? / ml</span>
            <span class="total-price">T?ng: 2.200?</span>
            </div>

            <form action="${pageContext.request.contextPath}/addToCart"
                              method="post">
                <input type="hidden" name="type" value="standard">
                <input type="hidden" name="productId" value="8" />
                <div class="quantity-box">
                <label>Số lượng (ml)</label>
                <input type="number" name="quantity" min="50" step="50" value="100">
                </div>

                <div class="stats">
                <div class="stat"><span>?? béo</span><div class="bar" style="--value:3"></div></div>
                <div class="stat"><span>V? ng?t</span><div class="bar" style="--value:2"></div></div>
                <div class="stat"><span>H??ng v?</span><div class="bar" style="--value:5"></div></div>
                <div class="stat"><span>?? sánh</span><div class="bar" style="--value:4"></div></div>
                </div>

                <button type="submit" class="add-btn">
                    Thêm vào giỏ
                </button>
            </form>
        </div>
        </div>

    </div>
    </section>


    <footer id="lien-he" class="contact section-container">
        <div class="section-title">
            <span>K?t n?i</span>
            <h2>Liên h? v?i chúng tôi</h2>
        </div>
        <div class="contact-info">
            <p><i class="fas fa-map-marker-alt"></i> 123 ???ng Uji, Qu?n 1, TP. H? Chí Minh</p>
        </div>
    </footer>
    <script src="index.js"></script>

    
</body>
</html>