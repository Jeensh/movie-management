let movieData
let user
let reviewType = 1

let total = 0
let itemCount = 0
let totalPages = 0
let currentPage = 1
let itemsPerPage = 5

$().ready(() => {
    // 데이터 가져오기
    getData()

    // 리뷰 타입 이벤트 설정
    setReviewRadioTypeEvent()
})

function initReviews() {
    movePage(1)
}

function getData() {
    let movieId = new URL(location.href).searchParams.get('movieId');

    let param = {
        movieId: movieId,
        reviewPageNumber: 1
    }

    $.ajax({
        url: '/pm/rest/movies',
        method: 'post',
        data: param,
        success: (response) => {
            movieData = response.movie
            user = response.user
            initMovie()
            initReviews()
            total = response.total
            setPaging()
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function initMovie() {
    // 제목 초기화
    $('#movie-title').text(movieData.title)

    // 썸네일 초기화
    putMovieImage()

    // 평균 평점 삽입 - 미완
    $(".movie-score").append(7.5)

    // 등급 삽입
    putGradeToItem()

    // 줄거리 삽입
    $(".movie-summary").append(movieData.description)

    // 유저 등급에 맞게 세팅
    if (user.grade == "NORMAL") {
        $(".review-textarea").remove()
    }
}

function putMovieImage(movie) {
    let Movieimage = $('.movie-detail-image')
    if (movieData.thumbnail != null) {
        let byteArray = movie.thumbnail
        let blob = new Blob([new Uint8Array(byteArray)], {type: 'image/jpeg'});
        Movieimage.src = URL.createObjectURL(blob);
    } else if (movieData.imageAddress != null) {
        Movieimage.attr("src", movieData.imageAddress)
    } else {
        Movieimage.attr("src", "../../resources/movie/default_movie.png")
    }
}

function putGradeToItem() {
    if (movieData.grade === "ALL") {
        $(".grade-image").attr("src", "../../resources/moviegrade/moviegrade1.png");
    } else if (movieData.grade === "TWELVE") {
        $(".grade-image").attr("src", "../../resources/moviegrade/moviegrade2.png");
    } else if (movieData.grade === "FIFTEEN") {
        $(".grade-image").attr("src", "../../resources/moviegrade/moviegrade3.png");
    } else if (movieData.grade === "NINETEEN") {
        $(".grade-image").attr("src", "../../resources/moviegrade/moviegrade4.png");
    }
}

function addReview(button) {
    let form = $(button).closest('section');
    let reviewText = form.find('.review-textarea').val();
    let reviewScore = form.find('.rangeInput').val();

    let param = {
        movieId: movieData.movieId,
        userId: user.userId,
        score: reviewScore,
        content: reviewText
    }

    $.ajax({
        url: '/pm/rest/review',
        method: 'post',
        data: param,
        success: (response) => {
            alert("리뷰가 등록되었습니다")
            form.find('.review-textarea').val("")
            form.find('.rangeInput').val(5)
            total += 1
            setPaging();
            movePage(1)
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function movePage(pageNum) {
    clearItems()
    $('#page' + currentPage).removeClass("active")
    currentPage = parseInt(pageNum)
    $('#page' + currentPage).addClass("active")

    let param = {
        movieId: movieData.movieId,
        reviewPageNumber: pageNum,
        reviewType: reviewType
    }

    $.ajax({
        url: '/pm/rest/movies',
        method: 'post',
        data: param,
        success: (response) => {
            total = response.total
            movieData = response.movie
            totalPages = Math.ceil(total / itemsPerPage);
            movieData.reviewList.forEach((review) => {
                addNewReviewItem(review)
            })
            setPaging()
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function clearItems() {
    for (let i = 1; i <= itemCount; i++) {
        $('#review-item' + i).remove()
    }
    itemCount = 0
}

function addNewReviewItem(review) {
    let reviewItem = $("#review-item").clone()
    reviewItem.removeAttr('hidden')
    reviewItem.attr("id", "review-item" + ++itemCount);
    console.log(review)
    reviewItem.find(".review-id").val(review.reviewId)
    reviewItem.find(".review-writer").text(review.writer.nickname)
    reviewItem.find(".review-content").append(review.content)

    // 작성자 불일치 시 버튼 삭제
    if ((review.writer.userId != user.userId) && user.grade != "ADMIN") {
        reviewItem.find('.edit-button').remove()
        reviewItem.find('.delete-button').remove()
    }

    // 평균 평점 삽입 - 미완
    reviewItem.find(".review-grade-text").append(review.score)

    // 작성자 등급 삽입
    putWriterGrade(review, reviewItem)

    // 요소 추가
    $('.review-list').append(reviewItem)
}

function putWriterGrade(review, reviewItem) {
    if (review.writer.grade === "NORMAL") {
        reviewItem.find('.writer-grade').text('(사용자)')
    } else if (review.writer.grade === "EXPERT") {
        reviewItem.find('.writer-grade').text('(평론가)')
    } else if (review.writer.grade === "ADMIN") {
        reviewItem.find('.writer-grade').text('(관리자)')
    }
}

function setPaging() {
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

function generatePageButtons(startidx, endidx) {
    for (let i = startidx; i <= endidx; i++) {
        let pageButton = '<li class="page-item" id="page' + i + '"><button class="page-link" onclick="movePage(this.textContent)">' + i + '</button></li>';
        $('.pagination').append(pageButton);
    }
}

function deleteReview(button) {
    // 삭제할 리뷰 아이템을 찾음
    let reviewItem = $(button).closest('.review-item-basic');
    // 해당 아이템의 review-id 값을 가져옴
    let reviewId = reviewItem.find('.review-id').val();

    console.log(reviewItem)
    console.log(reviewId)

    let param = {
        reviewId: reviewId,
        userId: user.userId
    }

    $.ajax({
        url: '/pm/rest/review/delete',
        method: 'post',
        data: param,
        success: (response) => {
            alert('삭제되었습니다')
            total -= 1
            setPaging()
            movePage(1)
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function setReviewRadioTypeEvent(){
    $('input[name="review-type"]').change((event) => {
        // 선택한 라디오 버튼의 값을 가져옴
        let selectedValue = $(event.target).val()

        if (selectedValue == 1) {
            // 전체 리뷰 표시
            reviewType = 1
        } else if (selectedValue == 2) {
            // 사용자 리뷰만 표시
            reviewType = 2
        } else if (selectedValue == 3) {
            // 평론가 리뷰만 표시
            reviewType = 3
        }
        movePage(1)
    });
}