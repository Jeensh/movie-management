// 주의. 자바스크립트 코드파일 상단에 위치해야함, 변수가 상단에서 선언되어야 함

// url 변수
let urlForPaging = ""

// 페이징 수행 변수
let total = 0           // 검색한 결과물의 총 결과물(아이템) 수, 아이템 추가시 +1필요, 아이템 삭제시 -1 필요
let totalPages = 0      // 검색한 결과물 수에 기반한 총 페이지 수
let currentPage = 1     // 현재 페이지
let itemsPerPage = 10   // 한 페이지당 보여줄 아이템 개수
let type = 0            // 서버에 리스트 요청시 보낼 컨트롤 변수

function setUrlForPaging(url){
    urlForPaging = url
}

// 초기 페이징 버튼 설정
function initPaging() {
    let param = {
        pageNumber: 0
        type: type
    }

    $.ajax({
        url: urlForPaging,
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

// 초기 페이징 버튼 생성
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

// 페이지 이동(숫자 페이징 버튼 클릭 이벤트)
function movePage(pageNum, keyword) {
    clearItems()
    $('#page' + currentPage).removeClass("active")
    currentPage = parseInt(pageNum)
    $('#page' + currentPage).addClass("active")

    let param = {
        pageNumber: pageNum
        keyword: keyword
    }

    console.log(param)

    $.ajax({
        url: urlForPaging,
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

// 페이지 이동(다음 페이지 버튼 클릭 이벤트)
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

// 페이지 이동(이전 페이징 버튼 클릭 이벤트)
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