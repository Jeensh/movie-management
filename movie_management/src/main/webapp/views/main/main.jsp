<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>PoscoMovie main</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../../css/main.css">
</head>
<body>
<header>
    <%@ include file="../component/nav.jsp" %>
</header>

<section id="container" style="margin-top: 10%">

</section>

<script>
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
            if(JSON.parse(response.auth)){
                console.log(response.userData)
            }
            // 실패
            else{
                alert("로그인 실패")
            }
        },
        error:  (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function signUp() {
    console.log("회원가입으로 이동")
    location.href = "/pm/signup"
}
</script>
</body>
</html>