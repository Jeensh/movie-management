<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>PoscoMovie login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../../css/login.css">
</head>
<body>
<section id="container" style="margin-top: 10%">
    <h1 style="font-size: xx-large">Posco Movie</h1>
    <fieldset style="border-radius: 40px 40px 40px 40px">
        <form>
            <input type="text" name="username" class="input-text" placeholder="아이디"><br>
            <input type="text" name="password" class="input-text" placeholder="비밀번호"><br>
        </form>
            <button onclick="login()"
                   style="font-size: large; font-weight: bold; margin-top: 10px; width: 100px; border-radius: 20px 20px 20px 20px">
            로그인</button>
            <button onclick="signUp()"
                   style="font-size: large; font-weight: bold; margin-top: 10px; width: 100px; border-radius: 20px 20px 20px 20px">
            회원가입</button>
    </fieldset>
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