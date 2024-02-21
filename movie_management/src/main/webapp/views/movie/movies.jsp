<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>PoscoMovie movies</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../../css/movie.css">
</head>
<body>
<header>
    <%@ include file="../component/nav.jsp" %>
</header>

<section class="list-container">
    <header class="container-header">
        <p style="font-weight: bolder; color: white; font-size: xx-large">영 화</p>
    </header>

    <section class="movie-list">
        <div class="movie-item" id="movie-item-basic" hidden>
            <input type="hidden" class="movie-id" value="-1">
            <a href="#"><img class="movie-image" src="../../resources/movie/default_movie.png"></a>
            <div class="movie-desc">
                <div class="movie-title">
                    마약왕
                </div>
                <div class="movie-grade">
                    <span class="grade-title">등급 : </span>
                    <img class="grade-image" src="../../resources/moviegrade/moviegrade1.png">
                </div>
                <div class="movie-score">
                    <span class="score-title">평점 : </span>
                </div>
                <div class="movie-summary">
                    <span class="summary-title">줄거리 : </span><br>
                </div>
            </div>
        </div>
    </section>
    <%--페이징--%>
    <%@include file="../component/paging.jsp"%>
</section>

<script src="../../js/movies/movies.js"></script>
</body>
</html>