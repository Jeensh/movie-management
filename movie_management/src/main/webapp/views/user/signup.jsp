<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>PoscoMovie signUp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../../css/login.css">
</head>
<body>
<section id="container" style="margin-top: 10%">
    <h1 style="font-size: xx-large">회원가입</h1>
    <fieldset style="border-radius: 40px 40px 40px 40px">
        <form>
            <input type="text" name="username" class="input-text" required placeholder="아이디"><br>
            <input type="text" name="password" class="input-text" required placeholder="비밀번호"><br>
            <input type="text" name="nickname" class="input-text" required placeholder="닉네임"><br>
            <input type="button" value="회원가입" onclick="signUp()"
                   style="font-size: large; font-weight: bold; margin-top: 10px; width: 100px; border-radius: 20px 20px 20px 20px">
        </form>
    </fieldset>
</section>

<script>
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
        error:  (request, status, error) => {
            alert("회원가입 실패")
        }
    })
}
</script>
</body>
</html>