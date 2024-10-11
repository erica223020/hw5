document.getElementById("loginButton").addEventListener("click", function () {
      const username = document.getElementById("username").value;
      const password = document.getElementById("password").value;

      // 使用 Fetch API 發送 JSON 格式的請求
      fetch("/hw5/ajaxlogin", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: username,
          password: password,
        }),
      })
        .then((response) => {
          if (response.ok) {
            // 登入成功
            Swal.fire({
              icon: "success",
              title: "登入成功",
              text: "登入成功！跳轉中...",
              showConfirmButton: false,
              timer: 800,
              timerProgressBar: true,
            }).then(() => {
              window.location.href = "/hw5/lottery"; // 登入成功後跳轉
            });
          } else {
            // 登入失敗，顯示錯誤訊息
            return response.text().then((message) => {
              document.getElementById("error-message").textContent = message;
              document.getElementById("error-message").style.display = "block";
            });
          }
        })
        .catch((error) => {
          console.error("Error:", error);
          document.getElementById("error-message").textContent =
            "發生錯誤，請稍後再試";
          document.getElementById("error-message").style.display = "block";
        });
    });