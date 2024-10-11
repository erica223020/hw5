document.getElementById("registerButton").addEventListener("click", function() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
	const confirmPassword = document.getElementById("confirmPassword").value;

	// 檢查密碼是否匹配
	if (password !== confirmPassword) {
	    document.getElementById("error-message").textContent = "密碼與確認密碼不相同";
	    document.getElementById("error-message").style.display = "block";
	    return; // 阻止進一步處理
	}
	
    // 使用 Fetch API 發送 JSON 格式的請求
    fetch("/hw5/ajaxregister", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            username: username,
            password: password,
        }),
    })
    .then(response => {
        if (response.ok) {
			console.log("Registration successful"); // 確認是否進入這裡
            // 註冊成功，重定向到登入頁面
			Swal.fire({
			  icon: "success",
			  title: "註冊成功",
			  text: "註冊成功！跳轉登入頁面...",
			  showConfirmButton: false,
			  timer: 800,
			  timerProgressBar: true,
			}).then(() => {
			  window.location.href = "/hw5/ajaxlogin";
			});

        } else {
            // 註冊失敗，顯示錯誤訊息
            return response.text().then(message => {
                document.getElementById("error-message").textContent = message;
                document.getElementById("error-message").style.display = "block";
            });
        }
    })
    .catch(error => {
        console.error("Error:", error);
        document.getElementById("error-message").textContent = "發生錯誤，請稍後再試";
        document.getElementById("error-message").style.display = "block";
    });
});
