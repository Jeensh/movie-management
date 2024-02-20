<%@ page import="com.poscodx.movie_management.model.UserDTO" %>
<%@ page import="com.poscodx.movie_management.config.UserGrade" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>PoscoMovie movie</title>
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
    <%
        UserDTO user = (UserDTO) request.getSession().getAttribute("auth");
        if (user.getGrade() == UserGrade.ADMIN) {
    %>
    <%@ include file="../component/adminnav.jsp" %>
    <%
    } else {
        // 관리자의 경우
    %>
    <%@ include file="../component/nav.jsp" %>
    <%
        }
    %>
</header>

<section class="list-container movie-detail">
    <%--    왼쪽 영화 정보 섹션--%>
    <section class="movie-detail-section">
        <header class="movie-detail-section-header">
            <span class="section-header-text">영 화</span>
        </header>
        <section id="movie-section">
            <header>
                <img class="movie-detail-image">
                <div class="movie-title-text" id="movie-title">무 제</div>
            </header>
            <section style="padding: 5px">
                <div class="movie-grade">
                    <span class="grade-title">등급 : </span>
                    <img class="grade-image" src="../../resources/moviegrade/moviegrade1.png">
                </div>
                <div class="movie-score">
                    <span class="score-title">평점 : </span>
                </div>
                <div class="movie-summary">
                    <span class="summary-title">줄거리 : </span>
                </div>
            </section>
        </section>
    </section>

    <%--    오른쪽 리뷰 정보 섹션--%>
    <section class="movie-detail-section">
        <header class="movie-detail-section-header">
            <span class="section-header-text">리 뷰</span>
        </header>
<%--         리뷰 조회 타입 설정--%>
        <section class="review-type-section">
            <input type="radio" class="btn-check" name="review-type" id="all" value=1 autocomplete="off" checked>
            <label class="btn type-label" for="all">ALL</label>
            <input type="radio" class="btn-check" name="review-type" id="normal" value=2 autocomplete="off">
            <label class="btn type-label" for="normal">NORMAL</label>
            <input type="radio" class="btn-check" name="review-type" id="expert" value=3 autocomplete="off">
            <label class="btn type-label" for="expert">EXPERT</label>
        </section>

        <section id="review-section">
            <section class="review-list">
                <div class="review-item-basic" id="review-item" hidden>
                    <input type="hidden" class="review-id" value="-1">
                    <div class="review-header">
                        <div class="review-writer">
                            홍길동
                        </div>
                        <figure class="review-score">
                            <img class="review-grade-image" src="../../resources/movie/star.png">
                            <figcaption class="review-grade-text"></figcaption>
                        </figure>
                        <div class="writer-grade">
                        </div>
                    </div>
                    <div class="review-content">
                    </div>
                    <div class="review-control">
                        <button class="delete-button" onclick="deleteReview(this)">삭제</button>
                    </div>
                </div>
            </section>
            <section class="review-input-section">
                <textarea class="review-textarea" name="review-textarea"></textarea>
                <span style="font-weight: bold">평점</span>
                <input type="range" class="rangeInput" name="review-score" min="0" max="10" step="1" value="5">
                <button onclick="addReview(this)">등록하기</button>
            </section>
            <%--페이징--%>
            <section class="page-navigation" style="margin-top: 5px">
                <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                    </ul>
                </nav>
            </section>
        </section>

    </section>
</section>

<script src="../../js/movies/movie.js"></script>
</body>
</html>