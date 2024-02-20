let itemCount = 0
let totalPages = 0
let currentPage = 1
let itemsPerPage = 10

// 시작 시 세팅
$().ready(() => {
    // 1페이지 영화 세팅
    initMovieItems()

    // 페이징 세팅
    initPaging()
})

function initPaging() {
    let param = {
        pageNumber: 0
    }

    $.ajax({
        url: '/pm/rest/movies',
        method: 'post',
        data: param,
        success: (response) => {
            setPaging(response.total)
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function initMovieItems() {
    movePage(1)
}

function addNewMovieItem(movie) {
    let movieItem = $("#movie-item-basic").clone()
    movieItem.removeAttr('hidden')
    movieItem.attr("id", "movie-item" + ++itemCount);
    movieItem.find(".movie-id").val(movie.movieId)
    movieItem.find(".movie-title").text(movie.title)
    movieItem.find(".movie-summary").append(movie.description)

    // 평균 평점 삽입
    movieItem.find(".movie-score").append(movie.avgScore)

    // 등급 삽입
    putGradeToItem(movie, movieItem)

    // 포스터 삽입
    putThumbnailToItem(movie, movieItem)

    // 요소 추가
    $(".movie-list").append(movieItem)
}

function setPaging(total) {
    totalPages = Math.ceil(total / itemsPerPage);

    // 기존의 페이지 버튼 제거
    let paginationContainer = $('.pagination');
    paginationContainer.empty();

    // 이전 페이지 버튼 생성
    previousButton = '<li class="page-item disabled"><a class="page-link">Previous</a></li>'
    paginationContainer.append(previousButton)


    // 페이지 버튼 생성
    let startidx = 1
    let endidx = (totalPages <= 5) ? totalPages % 6 : 5
    generatePageButtons(startidx, endidx)
    $('#page1').addClass("active")

    // 다음 페이지 버튼 생성
    let nextButton
    if (totalPages > 5)
        nextButton = '<li class="page-item"><button class="page-link" onclick="nextPage()">Next</button></li>';
    else if (totalPages - currentPage <= 5)
        nextButton = '<li class="page-item disabled"><button class="page-link">Next</button></li>';
    paginationContainer.append(nextButton);
}

function putGradeToItem(movie, movieItem) {
    if (movie.grade === "ALL") {
        movieItem.find(".grade-image").attr("src", "../../resources/moviegrade/moviegrade1.png");
    } else if (movie.grade === "TWELVE") {
        movieItem.find(".grade-image").attr("src", "../../resources/moviegrade/moviegrade2.png");
    } else if (movie.grade === "FIFTEEN") {
        movieItem.find(".grade-image").attr("src", "../../resources/moviegrade/moviegrade3.png");
    } else if (movie.grade === "NINETEEN") {
        movieItem.find(".grade-image").attr("src", "../../resources/moviegrade/moviegrade4.png");
    }
}

function putThumbnailToItem(movie, movieItem) {
    if (movie.thumbnail != null) {
        let byteArray = movie.thumbnail
        let blob = new Blob([new Uint8Array(byteArray)], {type: 'image/jpeg'});
        movieItem.find(".movie-image").src = URL.createObjectURL(blob);
    }
    else if(movie.imageAddress != null){
        movieItem.find(".movie-image").attr("src", movie.imageAddress)
    }
    else {
        movieItem.find(".movie-image").attr("src", "../../resources/movie/default_movie.png")
    }

    movieItem.find('a').attr('href', '/pm/movie?movieId=' + movie.movieId);
}

function clearItems() {
    for (let i = 1; i <= itemCount; i++) {
        $('#movie-item' + i).remove()
    }
    itemCount = 0
}

function movePage(pageNum) {
    clearItems()
    $('#page' + currentPage).removeClass("active")
    currentPage = parseInt(pageNum)
    $('#page' + currentPage).addClass("active")

    let param = {
        pageNumber: pageNum
    }

    $.ajax({
        url: '/pm/rest/movies',
        method: 'post',
        data: param,
        success: (response) => {
            response.movies.forEach((movie) => {
                addNewMovieItem(movie)
            })
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function nextPage() {
    // 기존의 페이지 버튼 제거
    let paginationContainer = $('.pagination');
    paginationContainer.empty();

    let pageSection = Math.ceil(currentPage / 5) + 1

    // 이전 페이지 버튼 생성
    let previousButton
    if (pageSection == 1) {
        previousButton = '<li class="page-item disabled"><button class="page-link">Previous</button></li>'
    } else {
        previousButton = '<li class="page-item"><button class="page-link" onclick="prevPage()">Previous</button></li>'
    }
    paginationContainer.append(previousButton)

    // 페이지 버튼 생성
    let startidx = ((pageSection - 1) * 5) + 1
    let endidx = (totalPages >= (pageSection * 5)) ? pageSection * 5 : ((pageSection - 1) * 5) + (totalPages % 5)
    generatePageButtons(startidx, endidx)

    // 다음 페이지 버튼 생성
    let nextButton
    if (pageSection * 5 >= totalPages)
        nextButton = '<li class="page-item disabled"><button class="page-link">Next</button></li>'
    else
        nextButton = '<li class="page-item"><button class="page-link" onclick="nextPage()">Next</button></li>'
    paginationContainer.append(nextButton);

    movePage(startidx)
}

function prevPage() {
    // 기존의 페이지 버튼 제거
    let paginationContainer = $('.pagination');
    paginationContainer.empty();

    let pageSection = Math.ceil(currentPage / 5) - 1

    // 이전 페이지 버튼 생성
    let previousButton
    if (pageSection == 1) {
        previousButton = '<li class="page-item disabled"><button class="page-link">Previous</button></li>'
    } else {
        previousButton = '<li class="page-item"><button class="page-link" onclick="prevPage()">Previous</button></li>'
    }
    paginationContainer.append(previousButton)

    // 페이지 버튼 생성
    let startidx = ((pageSection - 1) * 5) + 1
    let endidx = (totalPages > (pageSection * 5)) ? pageSection * 5 : ((pageSection - 1) * 5) + (totalPages % 6)
    generatePageButtons(startidx, endidx)

    // 다음 페이지 버튼 생성
    let nextButton
    if (pageSection * 5 >= totalPages)
        nextButton = '<li class="page-item disabled"><button class="page-link">Next</button></li>'
    else
        nextButton = '<li class="page-item"><button class="page-link" onclick="nextPage()">Next</button></li>'
    paginationContainer.append(nextButton);

    movePage(startidx)
}

function generatePageButtons(startidx, endidx){
    for (let i = startidx; i <= endidx; i++) {
        let pageButton = '<li class="page-item" id="page' + i + '"><button class="page-link" onclick="movePage(this.textContent)">' + i + '</button></li>';
        $('.pagination').append(pageButton);
    }
}
