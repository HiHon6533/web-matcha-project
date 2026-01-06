const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');
const signUpForm = document.getElementById('signUpForm');
const signInForm = document.getElementById('signInForm');

// --- HÀM KHÔI PHỤC TRẠNG THÁI FORM (FIX LỖI) ---
function resetFormUI(form) {
    const formContent = form.querySelector('.form-content');
    const successMsg = form.querySelector('.success-message');

    // 1. Xóa dữ liệu cũ
    form.reset();

    // 2. Hiện lại form nhập liệu (QUAN TRỌNG: Thêm flex-direction: column)
    if (formContent) {
        formContent.style.display = 'flex'; 
        formContent.style.flexDirection = 'column'; // <--- Dòng này sửa lỗi vỡ giao diện
    }

    // 3. Ẩn thông báo thành công
    if (successMsg) {
        successMsg.style.display = 'none';
    }
}

// --- XỬ LÝ SỰ KIỆN CHUYỂN ĐỔI TAB ---

// Khi bấm nút "Đăng ký" (ở panel Overlay)
signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
    // FIX: Reset lại form đăng ký ngay lập tức để tránh bị vỡ giao diện
});

// Khi bấm nút "Đăng nhập" (ở panel Overlay)


// --- XỬ LÝ SUBMIT FORM (LOGIC GIẢ LẬP) ---

// 1. Xử lý ĐĂNG KÝ
const btnSwitchToSignIn = document.getElementById('btnSwitchToSignIn');

signUpForm.addEventListener('submit', (e) => {
});

// Nút "Đăng nhập ngay" trong thông báo thành công
if (btnSwitchToSignIn) {
    btnSwitchToSignIn.addEventListener('click', () => {
        // Trượt sang màn hình đăng nhập
        container.classList.remove("right-panel-active");
        
        // Reset lại form đăng ký sau khi hiệu ứng trượt xong (600ms)
        setTimeout(() => {
            resetFormUI(signUpForm);
        }, 600);
    });
}


