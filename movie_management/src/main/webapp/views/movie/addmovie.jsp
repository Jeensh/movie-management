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

<section class="list-container movie-detail" style="width: 60%">
    <section class="movie-add-section">
        <header class="movie-detail-section-header">
            <span class="section-header-text">영화 등록</span>
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
                <textarea class="edit-description" name="description-textarea" placeholder="줄거리"></textarea>

                <%--                썸네일 경로            --%>
                <label class="edit-thumbnail" style="margin-top: 1%; margin-bottom: 2%">썸네일 경로: <input type="text"
                                                                                                       name="image-address"></label>

                <button onclick="addMovie()">등록하기</button>
            </section>
        </section>

    </section>
</section>

<script src="../../js/movies/addmovie.js"></script>
</body>
</html>