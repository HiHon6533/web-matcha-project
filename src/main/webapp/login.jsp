<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>??ng nh?p & ??ng ký - HINATFU</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&family=Playfair+Display:wght@600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="login.css">
</head>
<body>

    <div class="container" id="container">
        
        <div class="form-container sign-up-container">
            <form action="${pageContext.request.contextPath}/login" method="post" id="signUpForm">
                
                <div class="form-content">
                    <div class="brand-header">
                        <h1>HINATFU</h1>
                        <span class="subtitle">????</span>
                    </div>
                    <h2 class="form-title">T?o tài kho?n</h2>
                    <span class="instruction">?i?n thông tin các nhân c?a b?n</span>
                    
                    <input type="text" name="fullname" placeholder="H? và tên" required />
                    <input type="email" name="email" placeholder="Email" required />
                    <input type="password" name="password" placeholder="M?t kh?u" required />
                    <button type="submit" class="btn-primary">??ng ký</button>
                </div>

                <div class="success-message" style="display: none;">
                    <div class="icon-circle">
                        <i class="fas fa-check"></i>
                    </div>
                    <h2 class="form-title">Thành công!</h2>
                    <p>Tài kho?n c?a b?n ?ã ???c t?o.</p>
                    <button type="button" class="btn-primary" id="btnSwitchToSignIn">??ng nh?p ngay</button>
                </div>

            </form>
        </div>

        <div class="form-container sign-in-container">
            <form action="${pageContext.request.contextPath}/login" method="post" id="signInForm">
                
                <div class="form-content">
                    <div class="brand-header">
                        <h1>HINATFU</h1>
                        <span class="subtitle">????</span>
                    </div>
                    <h2 class="form-title">??ng nh?p</h2>
                    <span class="instruction">Chào m?ng b?n quay tr? l?i</span>

                    <input type="email" name="email" placeholder="Email" required />
                    <input type="password" name="password" placeholder="M?t kh?u" required />
                    <!-- <a href="#" class="forgot-pass">Quên m?t kh?u?</a> -->
                    <button type="submit" class="btn-primary">??ng nh?p</button>
                </div>

                <div class="success-message" style="display: none;">
                    <div class="icon-circle">
                        <i class="fas fa-check"></i>
                    </div>
                    <h2 class="form-title">Xin chào!</h2>
                    <p>B?n ?ã ??ng nh?p thành công.</p>
                    <a href="index.jsp" class="btn-home">
                        <button type="button" class="btn-primary">V? trang ch?</button>
                    </a>
                </div>

            </form>
        </div>

        <div class="overlay-container">
            <div class="overlay">
                <div class="overlay-panel overlay-left">
                    <h1>?ã có tài kho?n?</h1>
                    <p>Hãy ??ng nh?p ?? ti?p t?c mua s?m các s?n ph?m Matcha yêu thích.</p>
                    <button class="ghost" id="signIn">??ng nh?p</button>
                </div>
                <div class="overlay-panel overlay-right">
                    <h1>Khách hàng m?i?</h1>
                    <p>??ng ký ngay ?? nh?n nh?ng ?u ?ãi ??c bi?t t? HINATFU.</p>
                    <button class="ghost" id="signUp">??ng ký</button>
                </div>
            </div>
        </div>
    </div>

    <script src="login.js"></script>
</body>
</html>