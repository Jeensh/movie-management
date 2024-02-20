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
    <%@ include file="../component/adminnav.jsp" %>
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

    <%--    오른쪽 수정 정보 섹션--%>
    <section class="movie-detail-section">
        <header class="movie-detail-section-header">
            <span class="section-header-text">수 정</span>
        </header>
        <section id="review-section">
            <section class="edit-input-section">
                <%--                제목--%>
                <label class="edit-title">제목: <input type="text"
                                                     name="movie-title"></label><br>

                <%--                등급--%>
                <div class="grade-radio">
                    <input type="radio" class="btn-check" name="grade" id="option1" value="1" autocomplete="off"
                           checked>
                    <label class="btn" for="option1">ALL</label>

                    <input type="radio" class="btn-check" name="grade" id="option2" value="2" autocomplete="off">
                    <label class="btn" for="option2">12세</label>

                    <input type="radio" class="btn-check" name="grade" id="option3" value="3" autocomplete="off">
                    <label class="btn" for="option3">15세</label>

                    <input type="radio" class="btn-check" name="grade" id="option4" value="4" autocomplete="off">
                    <label class="btn" for="option4">18세</label>
                </div>

                <%--                줄거리--%>
                <textarea class="edit-description" name="description-textarea"></textarea>

                <%--                썸네일 경로            --%>
                <label class="edit-thumbnail">썸네일 경로: <input type="text" name="image-address"></label>

                <button onclick="editMovie()">수정하기</button>
            </section>
        </section>

    </section>
</section>

<script src="../../js/movies/editmovie.js"></script>
</body>
</html>