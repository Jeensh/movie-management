let total = 0
let itemCount = 0
let totalPages = 0
let currentPage = 1
let itemsPerPage = 10

// 시작 시 세팅
$().ready(() => {
    // 1페이지 영화 세팅
    initTheaterItems()

    // 페이징 세팅
    initPaging()
})

function initPaging() {
    let param = {
        pageNumber: 0
    }

    $.ajax({
        url: '/pm/rest/theaters',
        method: 'post',
        data: param,
        success: (response) => {
            total = response.total
            setPaging()
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function initTheaterItems() {
    movePage(1)
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

function addNewTheaterItem(theater) {
    let theaterItem = $("#theater-item-basic").clone()
    theaterItem.removeAttr('hidden')
    theaterItem.attr("id", "theater-item" + ++itemCount);
    theaterItem.find(".theater-id").val(theater.theaterId)
    theaterItem.find('.theater-tel').append(phoneNumber(theater.tel))
    theaterItem.find(".theater-title").text(theater.name)
    theaterItem.find(".theater-location").append(theater.location)

    // 평균 평점 삽입 - 미완
    theaterItem.find(".theater-score").append(7.5)

    // 등급 삽입
    putGradeToItem(theater, theaterItem)

    // 포스터 삽입
    putThumbnailToItem(theater, theaterItem)

    // 요소 추가
    $(".theater-list").append(theaterItem)
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

function putGradeToItem(theater, theaterItem) {
    if (theater.grade === "ALL") {
        theaterItem.find(".tel-image").attr("src", "../../resources/theatergrade/theatergrade1.png");
    } else if (theater.grade === "TWELVE") {
        theaterItem.find(".tel-image").attr("src", "../../resources/theatergrade/theatergrade2.png");
    } else if (theater.grade === "FIFTEEN") {
        theaterItem.find(".tel-image").attr("src", "../../resources/theatergrade/theatergrade3.png");
    } else if (theater.grade === "NINETEEN") {
        theaterItem.find(".tel-image").attr("src", "../../resources/theatergrade/theatergrade4.png");
    }
}

function putThumbnailToItem(theater, theaterItem) {
    if (theater.thumbnail != null) {
        let byteArray = theater.thumbnail
        let blob = new Blob([new Uint8Array(byteArray)], {type: 'image/jpeg'});
        theaterItem.find(".theater-image").src = URL.createObjectURL(blob);
    } else if (theater.imageAddress != null) {
        theaterItem.find(".theater-image").attr("src", theater.imageAddress)
    } else {
        theaterItem.find(".theater-image").attr("src", "../../resources/theater/default_theater.jpg")
    }

    theaterItem.find('a').attr('href', '/pm/theater?theaterId=' + theater.theaterId);
}

function clearItems() {
    for (let i = 1; i <= itemCount; i++) {
        $('#theater-item' + i).remove()
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
        url: '/pm/rest/theaters',
        method: 'post',
        data: param,
        success: (response) => {
            response.theaters.forEach((theater) => {
                addNewTheaterItem(theater)
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

function generatePageButtons(startidx, endidx) {
    for (let i = startidx; i <= endidx; i++) {
        let pageButton = '<li class="page-item" id="page' + i + '"><button class="page-link" onclick="movePage(this.textContent)">' + i + '</button></li>';
        $('.pagination').append(pageButton);
    }
}

function deleteTheater(button) {
    // 삭제할 리뷰 아이템을 찾음
    let theaterItem = $(button).closest('.theater-item');
    // 해당 아이템의 review-id 값을 가져옴
    let theaterId = theaterItem.find('.theater-id').val();

    let param = {
        theaterId: theaterId,
    }

    $.ajax({
        url: '/pm/rest/theater/delete',
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

function moveEditPage(button) {
    let theaterItem = $(button).closest('.theater-item');
    let theaterId = theaterItem.find('.theater-id').val();
    location.href = "/pm/theater/edit?theaterId=" + theaterId
}

function moveAddPage() {
    location.href = "/pm/theater/add"
}