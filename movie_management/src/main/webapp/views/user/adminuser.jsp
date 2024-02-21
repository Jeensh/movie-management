<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>PoscoMovie User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../../css/user.css">
</head>
<body>
<header>
    <%@ include file="../component/adminnav.jsp" %>
</header>

<section class="list-container">
    <section class="user-list-section">
        <header class="user-list-section-header">
            <span class="section-header-text">사용자 관리</span>
        </header>
        <section class="user-list" id="user-list">
            <div class="container">
                <div class="row justify-content-center">
                    <form id="userInfoForm" class="col-md-8">
                        <table class="user-table">
                            <tr class="user-table-header">
                                <th>번호</th>
                                <th>등급</th>
                                <th>아이디</th>
                                <th>별명</th>
                                <th>패스워드</th>
                            </tr>
                            <tr class="user-item-basic" id="user-item" hidden>
                                <td><input type="text" name="user-id" class="form-control" required readonly></td>
                                <td>
                                    <select name="grade" required>
                                        <option value="1">NORMAL</option>
                                        <option value="2">EXPERT</option>
                                        <option value="3" hidden>ADMIN</option>
                                    </select>
                                </td>
                                <td><input type="text" name="username" class="form-control" required readonly></td>
                                <td><input type="text" name="nickname" class="form-control" required></td>
                                <td><input type="password" name="password" class="form-control" required></td>
                                <td>
                                    <button type="button" onclick="editUser(this)" class="btn btn-primary"
                                            style="width: 5em">저장
                                    </button>
                                </td>
                            </tr>
                        </table>
                        <br>
                    </form>
                </div>
            </div>
        </section>
        <%--페이징--%>
        <%@include file="../component/paging.jsp"%>
    </section>
</section>

<script src="../../js/user/adminuser.js"></script>
</body>
</html>