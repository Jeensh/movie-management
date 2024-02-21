<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>PoscoMovie main</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../../css/main.css">
</head>
<body>
<header>
    <%@ include file="../component/nav.jsp" %>
</header>

<section style="margin-top: 10%; text-align: center; background-color: lightgray">
    <button style="margin: 40px 40px 40px 40px; background-color: transparent; border-color: lightgray"
            onclick="toMovies()">
        <img src="../../resources/menuimages/movie.png" width="200" height="200">
        <p style="font-size: medium; font-weight: bold; margin-top: 10px">영화</p>
    </button>
    <button style="margin: 40px 40px 40px 40px; background-color: transparent; border-color: lightgray"
            onclick="toTheaters()">
        <img src="../../resources/menuimages/theater.png" width="200" height="200">
        <p style="font-size: medium; font-weight: bold; margin-top: 10px">극장</p>
    </button>
</section>

<script>
    function toMovies() {
        location.href = "/pm/movies"
    }

    function toTheaters() {
        location.href = "/pm/theaters"
    }

</script>
</body>
</html>