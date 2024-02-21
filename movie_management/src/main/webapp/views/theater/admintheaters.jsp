<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>PoscoMovie theaters</title>
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

<section class="list-container">
    <header class="container-header">
        <p style="font-weight: bolder; color: white; font-size: xx-large">극 장</p>
    </header>
    <section>
        <button class="add-button" onclick="moveAddPage()">극장 등록하기</button>
    </section>
    <section class="theater-list">
        <div class="theater-item" id="theater-item-basic" hidden>
            <input type="hidden" class="theater-id" value="-1">
            <a href="#"><img class="theater-image" src="../../resources/theater/default_theater.jpg"></a>
            <div class="theater-desc">
                <div class="theater-title">
                    마약왕
                </div>
                <div class="theater-tel">
                    <img class="tel-image" src="../../resources/theater/tel.png">
                </div>
                <div class="theater-location">
                    <span class="location-title">주소</span><br>
                </div>
                <div class="button-section">
                    <button class="edit-button" onclick="moveEditPage(this)">수 정</button>
                    <button class="delete-button" onclick="deleteTheater(this)">삭 제</button>
                </div>
            </div>
        </div>
    </section>
    <%--페이징--%>
    <%@include file="../component/paging.jsp"%>
</section>

<script src="../../js/theaters/admintheaters.js"></script>
</body>
</html>