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

<section class="list-container theater-detail" style="width: 60%">
    <section class="theater-add-section">
        <header class="theater-detail-section-header">
            <span class="section-header-text">극장 등록</span>
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
                <textarea class="edit-location" name="location-textarea" placeholder="주소"></textarea>


                <%--                썸네일 경로            --%>
                <label class="add-thumbnail">썸네일 경로: <input type="text" name="image-address"></label>

                <button onclick="addTheater()">등록하기</button>
            </section>
        </section>

    </section>
</section>

<script src="../../js/theaters/addtheater.js"></script>
</body>
</html>