let movieData
let user

$().ready(() => {
    // 데이터 가져오기
    getData()
})

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
            initInput()
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
}

function initInput() {
    $('.edit-input-section input[name="movie-title"]').val(movieData.title);
    $('.edit-input-section textarea[name="description-textarea"]').val(movieData.description);
    $('.edit-input-section input[name="image-address"]').val(movieData.imageAddress);

    let grade = 1
    if (movieData.grade === "ALL") {
        grade = 1
    } else if (movieData.grade === "TWELVE") {
        grade = 2
    } else if (movieData.grade === "FIFTEEN") {
        grade = 3
    } else if (movieData.grade === "NINETEEN")
        grade = 4

    $('.edit-input-section input[name="grade"]').eq(grade - 1).prop('checked', true)
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

function editMovie() {
    let movieId = movieData.movieId
    let title = $('.edit-input-section input[name="movie-title"]').val();
    let selectedGrade = $('.edit-input-section input[name="grade"]:checked').val();
    let description = $('.edit-input-section textarea[name="description-textarea"]').val();
    let src = $('.edit-input-section input[name="image-address"]').val();

    let param = {
        movieId: movieId,
        title: title,
        grade: selectedGrade,
        description: description,
        imageAddress: src
    }

    $.ajax({
        url: '/pm/rest/movie/edit',
        method: 'post',
        data: param,
        success: (response) => {
            alert('수정되었습니다')
            location.reload()
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}