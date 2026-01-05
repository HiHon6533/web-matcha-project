document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.option-item').forEach(item => {
        item.addEventListener('click', function () {
            const frame = this.closest('.selection-frame');
            const category = frame.dataset.category;
            const value = this.dataset.value;

            // Xử lý UX: Active class
            frame
                .querySelectorAll(".option-item")
                .forEach((el) => el.classList.remove("active"));
            this.classList.add("active");

            if (category === 'matcha') {
                document.getElementById('matchaId').value = value;
                //Ẩn lỗi khi đã chọn
                const err = document.getElementById('error-matcha');
                if(err) err.style.display = 'none';
            }

            if (category === 'milk') {
                document.getElementById('milkId').value = value;
                //Ẩn lỗi khi đã chọn
                const err = document.getElementById('error-milk');
                if(err) err.style.display = 'none';
            }
        });
    });

    document.querySelectorAll('.size-item').forEach(item => {
        item.addEventListener('click', function () {
        const frame = this.closest(".selection-frame");
        frame
            .querySelectorAll(".size-item")
            .forEach((el) => el.classList.remove("active"));
        this.classList.add("active");
            document.getElementById('size').value = this.dataset.value;
        });
    });

    // Validate Submit
    const form = document.getElementById('customDrinkForm');
    if (form) {
        form.addEventListener('submit', function(e) {
            let valid = true;
            const matcha = document.getElementById('matchaId').value;
            const milk = document.getElementById('milkId').value;

            if (!matcha) {
                const err = document.getElementById('error-matcha');
                if (err) err.style.display = 'block'; // Kiểm tra tồn tại để tránh crash
                else alert("Chưa chọn Matcha!"); // Fallback nếu quên thêm thẻ HTML
                valid = false;
            }
            if (!milk) {
                const err = document.getElementById('error-milk');
                if (err) err.style.display = 'block';
                else alert("Chưa chọn Sữa!");
                valid = false;
            }

            if (!valid) {
                e.preventDefault(); // Chặn gửi form
            }
        });
    }
});