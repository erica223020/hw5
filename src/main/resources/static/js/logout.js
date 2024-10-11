document.getElementById("logoutButton").addEventListener("click", function(event) {
    // 顯示 SweetAlert 彈出視窗
    Swal.fire({
        title: '確定要登出嗎？',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '是的，登出',
        cancelButtonText: '取消'
    }).then((result) => {
        if (result.isConfirmed) {	                
            Swal.fire({
                icon: 'success',
                title: '登出成功',
                text: '成功登出！轉跳中...',
                timer: 800,
                timerProgressBar: true,
                showConfirmButton: false
            }).then(() => {
            	document.getElementById("logoutForm").submit();
            });
        }
    });
});