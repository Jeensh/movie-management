<%@ page import="com.poscodx.movie_management.model.UserDTO" %>
<%@ page import="com.poscodx.movie_management.config.UserGrade" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>PoscoTheater theater</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../../css/theater.css">
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

<section class="list-container theater-detail">
    <%--    왼쪽 영화 정보 섹션--%>
    <section class="theater-detail-section">
        <header class="theater-detail-section-header">
            <span class="section-header-text">극 장</span>
        </header>
        <section id="theater-section">
            <header>
                <img class="theater-detail-image">
                <div class="theater-title-text" id="theater-title">엄준식</div>
            </header>
            <section style="padding: 5px">
                <div class="theater-tel">
                    <img class="tel-image" src="../../resources/theater/tel.png">
                </div>
                <div class="theater-location">
                    <span class="location-title">주소</span><br>
                </div>
            </section>
        </section>
    </section>

    <%--    오른쪽 리뷰 정보 섹션--%>
    <section class="theater-detail-section">
        <header class="theater-detail-section-header">
            <span class="section-header-text">현재 상영 정보</span>
        </header>
        <section class="add-button-section" id="add-button-section">
            <button class="add-button" onclick="moveToAddPage()">상영정보 등록하기</button>
        </section>
        <section id="schedule-section">
            <section class="schedule-list">
                <div class="schedule-item-basic" id="schedule-item" hidden>
                    <input type="hidden" class="schedule-id" value="-1">
                    <div class="schedule-header">
                        <img class="schedule-image">
                    </div>
                    <div class="schedule-content">
                        <div class="movie-content">
                            <div class="movie-title">
                                <span class="title">제목 : </span>
                            </div>
                            <div class="movie-grade">
                                <span class="grade-title">등급 : </span>
                                <img class="grade-image" src="../../resources/moviegrade/moviegrade1.png">
                            </div>
                            <div class="movie-score">
                                <span class="score-title">평점 : </span>
                            </div>
                        </div>
                        <div class="movie-date">
                            <div class="movie-start-date">
                                <span class="title">시작 : </span>
                            </div>
                            <div class="movie-end-date">
                                <span class="title">종료 : </span>
                            </div>
                        </div>
                    </div>
                    <div class="schedule-control">
                        <button class="edit-button" onclick="editSchedule(this)">수정</button>
                        <button class="delete-button" onclick="deleteSchedule(this)">삭제</button>
                    </div>
                </div>
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

<!-- 수정 모달 -->
<div class="modal fade" id="edit-schedule-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">상영정보 수정</h5>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="edit-start-date">시작일:</label>
                    <input type="date" class="form-control" id="edit-start-date">
                </div>
                <div class="form-group">
                    <label for="edit-end-date">종료일:</label>
                    <input type="date" class="form-control" id="edit-end-date">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                <button id="edit-schedule-button" type="button" class="btn btn-primary">저장</button>
            </div>
        </div>
    </div>
</div>

<script src="../../js/theaters/theater.js"></script>
</body>
</html>