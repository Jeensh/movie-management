let theaterData
let user

let total = 0
let itemCount = 0
let totalPages = 0
let currentPage = 1
let itemsPerPage = 5

$().ready(() => {
    // 데이터 가져오기
    getData()

    // 관리자 아닐 시 상영정보 등록 버튼 삭제
    if(user.grade != "ADMIN")
        $('#add-button-section').remove()
})

function initSchedules() {
    movePage(1)
}

function getData() {
    let theaterId = new URL(location.href).searchParams.get('theaterId');

    let param = {
        theaterId: theaterId,
        schedulePageNumber: 1
    }

    $.ajax({
        url: '/pm/rest/theaters',
        method: 'post',
        data: param,
        success: (response) => {
            theaterData = response.theater
            user = response.user
            initTheater()
            initSchedules()
            total = response.total
            setPaging()
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function phoneNumber(value) {
    if (!value) {
        return "";
    }

    value = value.replace(/[^0-9]/g, "");

    let result = [];
    let restNumber = "";

    // 지역번호와 나머지 번호로 나누기
    if (value.startsWith("02")) {
        // 서울 02 지역번호
        result.push(value.substr(0, 2));
        restNumber = value.substring(2);
    } else if (value.startsWith("1")) {
        // 지역 번호가 없는 경우
        // 1xxx-yyyy
        restNumber = value;
    } else {
        // 나머지 3자리 지역번호
        // 0xx-yyyy-zzzz
        result.push(value.substr(0, 3));
        restNumber = value.substring(3);
    }

    if (restNumber.length === 7) {
        // 7자리만 남았을 때는 xxx-yyyy
        result.push(restNumber.substring(0, 3));
        result.push(restNumber.substring(3));
    } else {
        result.push(restNumber.substring(0, 4));
        result.push(restNumber.substring(4));
    }

    return result.filter((val) => val).join("-");
}

function initTheater() {
    // 제목 초기화
    $('#theater-title').text(theaterData.name)

    // 썸네일 초기화
    putTheaterImage()

    // 전화번호 초기화
    $('.theater-tel').append(phoneNumber(theaterData.tel))

    // 주소 초기화
    $('.theater-location').append(theaterData.location)
}

function putTheaterImage(theater) {
    let Theaterimage = $('.theater-detail-image')
    if (theaterData.thumbnail != null) {
        let byteArray = theater.thumbnail
        let blob = new Blob([new Uint8Array(byteArray)], {type: 'image/jpeg'});
        Theaterimage.src = URL.createObjectURL(blob);
    } else if (theaterData.imageAddress != null) {
        Theaterimage.attr("src", theaterData.imageAddress)
    } else {
        Theaterimage.attr("src", "../../resources/theater/default_theater.jpg")
    }
}

function putGradeToItem(scheduleItem, movie) {
    if (movie.grade === "ALL") {
        scheduleItem.find(".grade-image").attr("src", "../../resources/moviegrade/moviegrade1.png");
    } else if (movie.grade === "TWELVE") {
        scheduleItem.find(".grade-image").attr("src", "../../resources/moviegrade/moviegrade2.png");
    } else if (movie.grade === "FIFTEEN") {
        scheduleItem.find(".grade-image").attr("src", "../../resources/moviegrade/moviegrade3.png");
    } else if (movie.grade === "NINETEEN") {
        scheduleItem.find(".grade-image").attr("src", "../../resources/moviegrade/moviegrade4.png");
    }
}

function movePage(pageNum) {
    clearItems()
    $('#page' + currentPage).removeClass("active")
    currentPage = parseInt(pageNum)
    $('#page' + currentPage).addClass("active")

    let param = {
        theaterId: theaterData.theaterId,
        schedulePageNumber: pageNum
    }

    $.ajax({
        url: '/pm/rest/theaters',
        method: 'post',
        data: param,
        success: (response) => {
            total = response.total
            theaterData = response.theater
            totalPages = Math.ceil(total / itemsPerPage)
            theaterData.scheduleList.forEach((schedule) => {
                addNewScheduleItem(schedule)
            })
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function clearItems() {
    for (let i = 1; i <= itemCount; i++) {
        $('#schedule-item' + i).remove()
    }
    itemCount = 0
}

function addNewScheduleItem(schedule) {
    let scheduleItem = $("#schedule-item").clone()
    scheduleItem.removeAttr('hidden')
    scheduleItem.attr("id", "schedule-item" + ++itemCount);
    scheduleItem.find(".schedule-image").attr("src", schedule.movie.image_address)
    scheduleItem.find(".schedule-id").val(schedule.scheduleId)
    scheduleItem.find('.movie-title').append(schedule.movie.title)
    console.log(schedule.movie)
    scheduleItem.find(".movie-score").append(schedule.movie.avgScore)
    putScheduleImage(scheduleItem, schedule.movie)
    putGradeToItem(scheduleItem, schedule.movie)

    // 날짜 넣기
    putDataToItem(scheduleItem, schedule)

    // 관리자 아닐 시 버튼 삭제
    if (user.grade != "ADMIN") {
        scheduleItem.find('.edit-button').remove()
        scheduleItem.find('.delete-button').remove()
    }

    // 요소 추가
    $('.schedule-list').append(scheduleItem)
}

function putDataToItem(scheduleItem, schedule) {
    let startDate = DateToString(schedule.startDate)
    let endDate = DateToString(schedule.endDate)

    scheduleItem.find('.movie-start-date').append(startDate)
    scheduleItem.find('.movie-end-date').append(endDate)
}

function DateToString(Date) {
    let data = Date.split(' ')
    let str = data[2] + '년 ' + data[0] + ' ' + data[1].replace(',', '') + '일'
    return str
}

function putWriterGrade(schedule, scheduleItem) {
    if (schedule.writer.grade === "NORMAL") {
        scheduleItem.find('.writer-grade').text('(사용자)')
    } else if (schedule.writer.grade === "EXPERT") {
        scheduleItem.find('.writer-grade').text('(평론가)')
    } else if (schedule.writer.grade === "ADMIN") {
        scheduleItem.find('.writer-grade').text('(관리자)')
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

function deleteSchedule(button) {
    // 삭제할 리뷰 아이템을 찾음
    let scheduleItem = $(button).closest('.schedule-item-basic');
    // 해당 아이템의 schedule-id 값을 가져옴
    let scheduleId = scheduleItem.find('.schedule-id').val();

    let param = {
        scheduleId: scheduleId,
        userId: user.userId
    }

    $.ajax({
        url: '/pm/rest/schedule/delete',
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

function putScheduleImage(scheduleItem, movieData) {
    let scheduleImage = scheduleItem.find('.schedule-image')
    if (movieData.thumbnail != null) {
        let byteArray = movie.thumbnail
        let blob = new Blob([new Uint8Array(byteArray)], {type: 'image/jpeg'});
        scheduleImage.src = URL.createObjectURL(blob);
    } else if (movieData.imageAddress != null) {
        scheduleImage.attr("src", movieData.imageAddress)
    } else {
        scheduleImage.attr("src", "../../resources/movie/default_movie.png")
    }
}

function editSchedule(button) {
    let scheduleItem = $(button).closest('.schedule-item-basic');
    let scheduleId = scheduleItem.find('.schedule-id').val();
    let startDate = scheduleItem.find('.movie-start-date').text().trim();
    let endDate = scheduleItem.find('.movie-end-date').text().trim();

    // 시작일과 종료일 입력창에 값을 설정
    $('#edit-start-date').val(startDate);
    $('#edit-end-date').val(endDate);

    // 수정 모달을 보이게 함
    $('#edit-schedule-modal').modal('show');

    // 수정 버튼 클릭 시 스케줄 정보를 업데이트하는 함수 호출
    $('#edit-schedule-button').unbind('click').click(function() {
        updateSchedule(scheduleId);
    });
}

function updateSchedule(schduleId){
    let startDate = $('#edit-start-date').val()
    let endDate = $('#edit-end-date').val()

    let param = {
        scheduleId: schduleId,
        startDate: startDate,
        endDate: endDate
    }

    $.ajax({
        url: '/pm/rest/schedule/edit',
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

// 상영정보 추가 페이지로 이동
function moveToAddPage() {
    location.href = "/pm/movies?theaterId=" + theaterData.theaterId
}


