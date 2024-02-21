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
    <%@ include file="../component/adminnav.jsp" %>
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

    <%--    오른쪽 수정 정보 섹션--%>
    <section class="theater-detail-section">
        <header class="theater-detail-section-header">
            <span class="section-header-text">수 정</span>
        </header>
        <section id="schedule-section">
            <section class="edit-input-section">
                <%--                극장 이름--%>
                <label class="edit-name">이름: <input type="text"
                                                    name="theater-name"></label><br>

                <%--                전화 번호--%>
                <label class="edit-tel">번호: <input type="text"
                                                   name="theater-tel" maxlength="11" minlength="10"></label><br>

                <%--                주소--%>
                <textarea class="edit-location" name="location-textarea"></textarea>


                <%--                썸네일 경로            --%>
                <label class="edit-thumbnail">썸네일 경로: <input type="text" name="image-address"></label>

                <button onclick="editTheater()">수정하기</button>
            </section>
        </section>

    </section>
</section>

<script src="../../js/theaters/edittheater.js"></script>
</body>
</html>