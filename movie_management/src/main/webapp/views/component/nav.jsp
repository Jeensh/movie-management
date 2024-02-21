<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
    <a class="navbar-brand" style="margin-left: 5px" href="/pm/main">Posco Movie</a>
    <button class="navbar-toggler active" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-between" id="navbarSupportedContent">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/pm/movies">영화</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/pm/theaters">극장</a>
            </li>
        </ul>
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/">로그아웃</a>
            </li>
        </ul>
    </div>
    <div id="search-section" hidden>
        <form class="d-flex" role="search" id="search-form" hidden style="margin-right: 5px; margin-left: 5px">
            <input class="form-control me-2" type="search" id="search-input" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
    </div>
</nav>