<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>HINATFU Matcha - Tinh hoa trà Nh?t</title>
    <script src="https://kit.fontawesome.com/41b883a0ca.js" crossorigin="anonymous"></script>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Serif+JP:wght@400;700&family=Montserrat:wght@300;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="index.css">
</head>
<body>
    <!-- ----------------------------- Header ------------------------------------ -->
    <header class="navbar">
        <nav class="nav-left">
            <a href="#trangchu" class="logo">
                <h1>HINATFU<span>????</span></h1>
            </a>
        </nav>

        <nav class="nav-center">
            <a href="#gioi-thieu">Gi?i thi?u</a>
            <a href="#noi-bat">N?i b?t</a>
            <a href="#san-pham">S?n ph?m</a>
            <a href="#nguyen-lieu">Nguyên li?u</a>
            <a href="#lien-he">Liên h?</a>
        </nav>

        <nav class="nav-right">
            <a href="./cart.jsp" class="icon-btn"><i class="fas fa-shopping-basket"></i></a>
            <a href="login.jsp" class="icon-btn"><i class="fa-solid fa-user-ninja"></i></a>
        </nav>
    </header>
<!-- ----------------------------------------Background-------------------------------------------- -->
    <section id="trangchu" class="hero-section">
        <div class="background-container">
            <img src="./br.png" alt="Matcha Background" />
            <div class="hero-text">
                <h2>T?nh l?ng trong t?ng ng?m trà</h2>
                <p>Tr?i nghi?m h??ng v? Uji truy?n th?ng gi?a lòng thành ph?.</p>
            </div>
        </div>
    </section>
<!-- ----------------------------------------About-------------------------------------------- -->
    <section id="gioi-thieu" class="section-container about">
        <div class="section-title">
            <span>Gi?i thi?u</span>
            <h2>V? chúng tôi</h2>
        </div>
        <div class="about-content">
            <p>Chúng tôi mang ??n dòng Matcha th??ng h?ng t? vùng Uji, Kyoto - n?i có l?ch s? trà ??o lâu ??i nh?t Nh?t B?n.</p>
            <p>M?i chén trà là m?t s? k?t h?p gi?a ngh? thu?t pha ch? và s? t?nh l?ng c?a tâm h?n.</p>
        </div>
    </section>
<!-- ----------------------------------------San pham noi bat -------------------------------------------- -->
    <section id="noi-bat" class="section-container menu">
        <div class="section-title">
            <span>G?i ý</span>
            <h2>S?n ph?m n?i b?t</h2>
        </div>

        <div class="product-grid">
            <div class="product-card" 
            data-product-id="drink_matcha_dai_meiji"
            data-price="65000">
                <div class="product-img">
                    <img src="./SanPhamImage/MatchaDaiSuaTuoi.png" alt="Matcha ?ài" />
                </div>
                <div class="product-info">
                    <h3>Matcha ?ài s?a Meiji</h3>
                    <p>Matcha thanh khi?t, s?a Meiji m??t mà, h?u v? d?u êm.</p>
                    <div class="price-add">
                        <span>65.000?</span>
                        <button class="add-btn">Thêm +</button>
                    </div>
                </div>
            </div>
            <div class="product-card"
            data-product-id="drink_matcha_uji_gau"
            data-price="75000">
                <div class="product-img">
                    <img src="./SanPhamImage/MatchaHaruSuaOatside.png" alt="Matcha Haru" />
                </div>
                <div class="product-info">
                    <h3>Matcha Haru s?a h?t Oatside</h3>
                    <p>Trà xuân Haru thanh tao, y?n m?ch bùi béo, thu?n khi?t.</p>
                    <div class="price-add">
                        <span>85.000?</span>
                        <button class="add-btn">Thêm +</button>
                    </div>
                </div>
            </div>
            <div class="product-card"
            data-product-id="drink_matcha_fuji_mk4_oatside"
            data-price="60000">
                <div class="product-img">
                    <img src="./SanPhamImage/MatchaFujiMk4SuaOatSide.png" alt="Matcha Fuji Mk4" />
                </div>
                <div class="product-info">
                    <h3>Matcha Fuji Mk4 s?a h?t Oatside</h3>
                    <p>V? trà rang m?c m?c, ??m ?à, quy?n cùng s?a h?t sánh m?n.</p>
                    <div class="price-add">
                        <span>60.000?</span>
                        <button class="add-btn">Thêm +</button>
                    </div>
                </div>
            </div>
            
        </div>  
    </section>
 <!-- ----------------------------------------Tuy chinh san pham-------------------------------------------- -->  
    <section id="san-pham" class="section-container custom-selection">
        <div class="section-title">
            <span>Sáng t?o</span>
            <h2>Tùy ch?nh Matcha c?a b?n</h2>
        </div>
        
        <div class="selection-container">
            <div class="selection-frame" data-category="matcha">
                <h3><i class="fas fa-leaf"></i> 1. Ch?n lo?i Matcha</h3>
                <div class="option-list">
                    <div class="option-item" data-value="matcha_taiwan">
                        <img src="./matchaImage/Dai.png" alt="Matcha ?ài">
                        <div class="option-text">
                            <h4>Matcha ?ài Loan</h4>
                            <p>V? trà thanh mát, nh? nhàng.</p>
                        </div>
                    </div>
                    <div class="option-item" data-value="matcha_uji">
                        <img src="./matchaImage/Uji.png" alt="Matcha Uji">
                        <div class="option-text">
                            <h4>Matcha Uji Cao C?p</h4>
                            <p>Tinh hoa Kyoto ??m ?à Umami.</p>
                        </div>
                    </div>
                    <div class="option-item" data-value="matcha_haru">
                        <img src="./matchaImage/Haru.png" alt="Matcha Haru">
                        <div class="option-text">
                            <h4>Matcha Haru</h4>
                            <p>H??ng c? non t??i m?i.</p>
                        </div>
                    </div>
                    <div class="option-item" data-value="matcha_fuji_03">
                        <img src="./matchaImage/Fuji03.png" alt="Matcha Fuji 03">
                        <div class="option-text">
                            <h4>Matcha Fuji No.03</h4>
                            <p>Màu xanh th?m, v? m?nh m?.</p>
                        </div>
                    </div>
                    <div class="option-item" data-value="matcha_fuji_Mk4">
                        <img src="./matchaImage/FujiMk4.png" alt="Matcha Fuji Mk4">
                        <div class="option-text">
                            <h4>Matcha Fuji Mk4</h4>
                            <p>S? cân b?ng hoàn h?o gi?a v? chát nh? và h??ng trà rang ??c tr?ng.</p>
                        </div>
                    </div>
                    <div class="option-item" data-value="matcha_Houjicha">
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
                    <div class="option-item" data-value="milk_fresh">
                        <img src="./milkImage/SuaTuoi.png" alt="S?a T??i">
                        <div class="option-text">
                            <h4>S?a T??i Thanh Trùng</h4>
                            <p>V? kem béo t? nhiên.</p>
                        </div>
                    </div>
                    <div class="option-item" data-value="milk_oatside">
                        <img src="./milkImage/SuaHatOatside.png" alt="S?a Oatside">
                        <div class="option-text">
                            <h4>S?a Y?n M?ch Oatside</h4>
                            <p>L?a ch?n thu?n chay hoàn h?o.</p>
                        </div>
                    </div>
                    <div class="option-item" data-value="milk_gau">
                        <img src="./milkImage/SuaGau.png" alt="S?a G?u">
                        <div class="option-text">
                            <h4>S?a T??i G?u</h4>
                            <p>??m ??c, ít béo, mang l?i c?m giác hoài ni?m và thanh tao.</p>
                        </div>
                    </div>
                    <div class="option-item" data-value="milk_meiji">
                        <img src="./milkImage/SuaMeiji.png" alt="S?a Meiji">
                        <div class="option-text">
                            <h4>S?a T??i Meiji</h4>
                            <p>Tiêu chu?n Nh?t B?n m?n màng.</p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="selection-frame" data-category="size">
                <h3><i class="fas fa-expand-alt"></i> 3. Ch?n Size</h3>
                <div class="size-list">
                    <div class="size-item" data-value="size_s">
                        <i class="fa-solid fa-mug-hot"></i>
                        <span>Size S (180ml)</span>
                    </div>
                    <div class="size-item active" data-value="size_l">
                        <i class="fa-solid fa-mug-hot" style="font-size: 1.4em;"></i>
                        <span>Size L (350ml)</span>
                    </div>
                </div>
            </div>
        </div>

        <div style="text-align: center; margin-top: 40px;">
            <button id="confirm-custom" class="add-btn" style="padding: 15px 40px; font-size: 18px;">
                Thêm vào gi? hàng ngay <i class="fas fa-arrow-right"></i>
            </button>
        </div>
    </section>

<!-- ----------------------------------------Nguyen lieu matcha-------------------------------------------- -->
    <section id="nguyen-lieu" class="section-container ingredients">

    <div class="section-title">
        <span>Nguyên li?u</span>
        <h2>B?t Matcha Nh?t B?n</h2>
    </div>

    <div class="matcha-two-columns">

        <!-- MATCHA ?ÀI -->
        <div class="ingredient-card"
        data-product-id="matcha_taiwan"
        data-price-per-gram="600">
        <img src="./matchaImage/Dai.png" alt="Matcha ?ài">
        <div class="ingredient-content">
            <h4>Matcha ?ài Loan</h4>
            <p>V? trà nh?, thanh mát, d? u?ng cho ng??i m?i.</p>

            <!-- GIÁ -->
            <div class="price-info">
            <span class="price-per-gram">600? / g</span>
            <span class="total-price">T?ng: 30.000?</span>
            </div>

            <!-- CH?N GRAM -->
            <div class="quantity-box">
            <label>S? l??ng (g)</label>
            <input type="number" min="10" step="10" value="50">
            </div>

            <div class="stats">
            <div class="stat"><span>?? m?nh</span><div class="bar" style="--value:2"></div></div>
            <div class="stat"><span>?? ??ng</span><div class="bar" style="--value:1"></div></div>
            <div class="stat"><span>Màu s?c</span><div class="bar" style="--value:3"></div></div>
            <div class="stat"><span>H??ng th?m</span><div class="bar" style="--value:2"></div></div>
            <div class="stat"><span>Cafeine</span><div class="bar" style="--value:2"></div></div>
            </div>

            <button class="add-btn">Thêm vào gi?</button>
        </div>
        </div>

        <!-- MATCHA UJI -->
        <div class="ingredient-card"
        data-product-id="matcha_uji"
        data-price-per-gram="1200">
        <img src="./matchaImage/Uji.png" alt="Matcha Uji">
        <div class="ingredient-content">
            <h4>Matcha Uji Cao C?p</h4>
            <p>Tinh hoa Kyoto, v? umami ??m ?à, h?u ng?t sâu.</p>
            
            <!-- GIÁ -->
            <div class="price-info">
            <span class="price-per-gram">1.200? / g</span>
            <span class="total-price">T?ng: 60.000?</span>
            </div>

            <!-- CH?N GRAM -->
            <div class="quantity-box">
            <label>S? l??ng (g)</label>
            <input type="number" min="10" step="10" value="50">
            </div>

            <div class="stats">
            <div class="stat"><span>?? m?nh</span><div class="bar" style="--value:4"></div></div>
            <div class="stat"><span>?? ??ng</span><div class="bar" style="--value:3"></div></div>
            <div class="stat"><span>Màu s?c</span><div class="bar" style="--value:5"></div></div>
            <div class="stat"><span>H??ng th?m</span><div class="bar" style="--value:5"></div></div>
            <div class="stat"><span>Cafeine</span><div class="bar" style="--value:4"></div></div>
            </div>

            <button class="add-btn">Thêm vào gi?</button>
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

            <!-- CH?N GRAM -->
            <div class="quantity-box">
            <label>S? l??ng (g)</label>
            <input type="number" min="10" step="10" value="50">
            </div>

            <div class="stats">
            <div class="stat"><span>?? m?nh</span><div class="bar" style="--value:3"></div></div>
            <div class="stat"><span>?? ??ng</span><div class="bar" style="--value:2"></div></div>
            <div class="stat"><span>Màu s?c</span><div class="bar" style="--value:4"></div></div>
            <div class="stat"><span>H??ng th?m</span><div class="bar" style="--value:4"></div></div>
            <div class="stat"><span>Cafeine</span><div class="bar" style="--value:3"></div></div>
            </div>

            <button class="add-btn">Thêm vào gi?</button>
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

            <!-- CH?N GRAM -->
            <div class="quantity-box">
            <label>S? l??ng (g)</label>
            <input type="number" min="10" step="10" value="50">
            </div>

            <div class="stats">
            <div class="stat"><span>?? m?nh</span><div class="bar" style="--value:4"></div></div>
            <div class="stat"><span>?? ??ng</span><div class="bar" style="--value:4"></div></div>
            <div class="stat"><span>Màu s?c</span><div class="bar" style="--value:4"></div></div>
            <div class="stat"><span>H??ng th?m</span><div class="bar" style="--value:3"></div></div>
            <div class="stat"><span>Cafeine</span><div class="bar" style="--value:4"></div></div>
            </div>

            <button class="add-btn">Thêm vào gi?</button>
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

            <!-- CH?N GRAM -->
            <div class="quantity-box">
            <label>S? l??ng (g)</label>
            <input type="number" min="10" step="10" value="50">
            </div>

            <div class="stats">
            <div class="stat"><span>?? m?nh</span><div class="bar" style="--value:5"></div></div>
            <div class="stat"><span>?? ??ng</span><div class="bar" style="--value:4"></div></div>
            <div class="stat"><span>Màu s?c</span><div class="bar" style="--value:5"></div></div>
            <div class="stat"><span>H??ng th?m</span><div class="bar" style="--value:4"></div></div>
            <div class="stat"><span>Cafeine</span><div class="bar" style="--value:5"></div></div>
            </div>

            <button class="add-btn">Thêm vào gi?</button>
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

            <!-- CH?N GRAM -->
            <div class="quantity-box">
            <label>S? l??ng (g)</label>
            <input type="number" min="10" step="10" value="50">
            </div>

            <div class="stats">
            <div class="stat"><span>?? m?nh</span><div class="bar" style="--value:2"></div></div>
            <div class="stat"><span>?? ??ng</span><div class="bar" style="--value:1"></div></div>
            <div class="stat"><span>Màu s?c</span><div class="bar" style="--value:2"></div></div>
            <div class="stat"><span>H??ng th?m</span><div class="bar" style="--value:5"></div></div>
            <div class="stat"><span>Cafeine</span><div class="bar" style="--value:1"></div></div>
            </div>

            <button class="add-btn">Thêm vào gi?</button>
        </div>
        </div>

    </div>
    </section>


<!-- ----------------------------------------Nguyen lieu sua-------------------------------------------- -->
    <section class="section-container ingredients">

    <div class="section-title">
        <span>Nguyên li?u</span>
        <h2>S?a & Milk Base</h2>
    </div>

    <div class="matcha-two-columns">

        <!-- S?A T??I -->
        <div class="ingredient-card"
        data-product-id="milk_fresh"
        data-price-per-gram="150">
        <img src="./milkImage/SuaTuoi.png" alt="S?a t??i">
        <div class="ingredient-content">
            <h4>S?a t??i</h4>
            <p>V? s?a t? nhiên, nh?, làm n?n cho matcha.</p>

            <div class="price-info">
            <span class="price-per-gram">150? / ml</span>
            <span class="total-price">T?ng: 1.500?</span>
            </div>

            <div class="quantity-box">
            <label>S? l??ng (ml)</label>
            <input type="number" min="50" step="50" value="100">
            </div>

            <div class="stats">
            <div class="stat"><span>?? béo</span><div class="bar" style="--value:2"></div></div>
            <div class="stat"><span>V? ng?t</span><div class="bar" style="--value:2"></div></div>
            <div class="stat"><span>H??ng v?</span><div class="bar" style="--value:2"></div></div>
            <div class="stat"><span>?? sánh</span><div class="bar" style="--value:2"></div></div>
            </div>

            <button class="add-btn">Thêm vào gi?</button>
        </div>
        </div>

        <!-- S?A MEIJI -->
        <div class="ingredient-card"
        data-product-id="milk_meiji"
        data-price-per-gram="180">
        <img src="./milkImage/SuaMeiji.png" alt="S?a Meiji">
        <div class="ingredient-content">
            <h4>S?a Meiji</h4>
            <p>S?a Nh?t béo nh?, h?u ng?t d?u.</p>

            <div class="price-info">
            <span class="price-per-gram">180? / ml</span>
            <span class="total-price">T?ng: 1.800?</span>
            </div>

            <div class="quantity-box">
            <label>S? l??ng (ml)</label>
            <input type="number" min="50" step="50" value="100">
            </div>

            <div class="stats">
            <div class="stat"><span>?? béo</span><div class="bar" style="--value:3"></div></div>
            <div class="stat"><span>V? ng?t</span><div class="bar" style="--value:3"></div></div>
            <div class="stat"><span>H??ng v?</span><div class="bar" style="--value:4"></div></div>
            <div class="stat"><span>?? sánh</span><div class="bar" style="--value:3"></div></div>
            </div>

            <button class="add-btn">Thêm vào gi?</button>
        </div>
        </div>

        <!-- S?A G?U -->
        <div class="ingredient-card"
        data-product-id="milk_gau"
        data-price-per-gram="200">
        <img src="./milkImage/SuaGau.png" alt="S?a G?u">
        <div class="ingredient-content">
            <h4>S?a G?u</h4>
            <p>Béo ??m, ng?t rõ, h?p matcha m?nh.</p>

            <div class="price-info">
            <span class="price-per-gram">200? / ml</span>
            <span class="total-price">T?ng: 2.000?</span>
            </div>

            <div class="quantity-box">
            <label>S? l??ng (ml)</label>
            <input type="number" min="50" step="50" value="100">
            </div>

            <div class="stats">
            <div class="stat"><span>?? béo</span><div class="bar" style="--value:5"></div></div>
            <div class="stat"><span>V? ng?t</span><div class="bar" style="--value:4"></div></div>
            <div class="stat"><span>H??ng v?</span><div class="bar" style="--value:4"></div></div>
            <div class="stat"><span>?? sánh</span><div class="bar" style="--value:4"></div></div>
            </div>

            <button class="add-btn">Thêm vào gi?</button>
        </div>
        </div>

        <!-- S?A H?T OATSIDE -->
        <div class="ingredient-card"
        data-product-id="milk_oatside"
        data-price-per-gram="220">
        <img src="./milkImage/SuaHatOatside.png" alt="S?a Oatside">
        <div class="ingredient-content">
            <h4>S?a h?t Oatside</h4>
            <p>Ít ng?t, béo m?n, h??ng y?n m?ch.</p>

            <div class="price-info">
            <span class="price-per-gram">220? / ml</span>
            <span class="total-price">T?ng: 2.200?</span>
            </div>

            <div class="quantity-box">
            <label>S? l??ng (ml)</label>
            <input type="number" min="50" step="50" value="100">
            </div>

            <div class="stats">
            <div class="stat"><span>?? béo</span><div class="bar" style="--value:3"></div></div>
            <div class="stat"><span>V? ng?t</span><div class="bar" style="--value:2"></div></div>
            <div class="stat"><span>H??ng v?</span><div class="bar" style="--value:5"></div></div>
            <div class="stat"><span>?? sánh</span><div class="bar" style="--value:4"></div></div>
            </div>

            <button class="add-btn">Thêm vào gi?</button>
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

    <!-- <script>
    document.addEventListener('DOMContentLoaded', function () {

        /* ===============================
        1. CUSTOM MATCHA (PHA S?N)
        =============================== */

        const userOrder = {
            matcha_id: null,
            milk_id: null,
            size_id: "size_l" // m?c ??nh
        };

        function setupSelection(selector) {
            document.querySelectorAll(selector).forEach(item => {
                item.addEventListener('click', function () {
                    const frame = this.closest('.selection-frame');
                    const category = frame.dataset.category;
                    const value = this.dataset.value;

                    // UI
                    frame.querySelectorAll(selector).forEach(el => el.classList.remove('active'));
                    this.classList.add('active');

                    // DATA
                    if (category === 'matcha') userOrder.matcha_id = value;
                    if (category === 'milk') userOrder.milk_id = value;
                    if (category === 'size') userOrder.size_id = value;

                    console.log("STATE CUSTOM MATCHA:", userOrder);
                });
            });
        }

        setupSelection('.option-item');
        setupSelection('.size-item');

        document.getElementById('confirm-custom').addEventListener('click', function () {
            if (!userOrder.matcha_id || !userOrder.milk_id || !userOrder.size_id) {
                alert("Vui lòng ch?n ??y ?? Matcha ? S?a ? Size");
                return;
            }

            const payload = {
                product_type: "custom_matcha",
                ...userOrder,
                quantity: 1
            };

            console.log("G?I BACKEND (CUSTOM MATCHA):", payload);
            alert("Custom matcha ?ã ???c ghi nh?n (xem Console)");
        });


        /* ===============================
        2. NGUYÊN LI?U L? (MATCHA / S?A)
        ?? YÊU C?U HTML CÓ:
        data-product-id
        data-price-per-gram (ho?c ml)
        =============================== */

        document.querySelectorAll('.ingredient-card').forEach(card => {

            const productId = card.dataset.productId;
            const pricePerUnit = parseInt(card.dataset.pricePerGram);

            if (!productId || isNaN(pricePerUnit)) return;

            const input = card.querySelector('input[type="number"]');
            const totalPriceEl = card.querySelector('.total-price');
            const addBtn = card.querySelector('.add-btn');
            const name = card.querySelector('h4').innerText;

            function updatePrice() {
                const qty = parseInt(input.value);
                const total = qty * pricePerUnit;
                totalPriceEl.textContent = "T?ng: " + total.toLocaleString() + "?";
            }

            input.addEventListener('input', updatePrice);
            updatePrice();

            addBtn.addEventListener('click', () => {
                const qty = parseInt(input.value);

                const orderItem = {
                    product_type: "ingredient",
                    product_id: productId,
                    product_name: name,
                    quantity: qty,
                    unit_price: pricePerUnit,
                    total_price: qty * pricePerUnit
                };

                console.log("G?I BACKEND (NGUYÊN LI?U):", orderItem);
                alert("?ã thêm vào gi?: " + name);
            });
        });

    });
    /* ===============================
   3. S?N PH?M N?I B?T (PHA S?N)
    =============================== */

    document.querySelectorAll('.product-card .add-btn').forEach(btn => {
        btn.addEventListener('click', function () {

            const card = this.closest('.product-card');

            const orderItem = {
                product_type: "drink",
                product_id: card.dataset.productId,
                product_name: card.querySelector('h3').innerText,
                unit_price: parseInt(card.dataset.price),
                quantity: 1,
                total_price: parseInt(card.dataset.price)
            };

            console.log("G?I BACKEND (S?N PH?M N?I B?T):", orderItem);
            alert("?ã thêm vào gi?: " + orderItem.product_name);
        });
    });

    </script> -->

    
</body>
</html>