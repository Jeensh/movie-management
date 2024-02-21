$("#password").keypress((event) => {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == '13') {
        login();
    }
});

function login() {
    let param = {
        username: $('input[name="username"]').val(),
        password: $('input[name="password"]').val()
    }

    $.ajax({
        url: '/pm/rest/auth',
        method: 'post',
        data: param,
        success: (response) => {
            // 로그인 성공
            console.log(response)
            if (JSON.parse(response.auth)) {
                location.href = "/pm/main"
            }
            // 실패
            else {
                alert("로그인 실패")
            }
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function signUp() {
    console.log("회원가입으로 이동")
    location.href = "/pm/signup"
}