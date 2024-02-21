function signUp() {
    let param = {
        username: $('input[name="username"]').val(),
        password: $('input[name="password"]').val(),
        nickname: $('input[name="nickname"]').val()
    }

    $.ajax({
        url: '/pm/rest/signup',
        method: 'post',
        data: param,
        success: (response) => {
            alert("회원가입 완료")
            location.href = "/"
        },
        error: (request, status, error) => {
            alert("회원가입 실패")
        }
    })
}