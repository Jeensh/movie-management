let total = 0

let itemCount = 0
let totalPages = 0
let currentPage = 1
let itemsPerPage = 10

$().ready(() => {
    // 데이터 가져오기
    initUsers()

    // 페이징 세팅
    initPaging()
})

function initUsers() {
    movePage(1)
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
        url: '/pm/rest/users',
        method: 'post',
        data: param,
        success: (response) => {
            response.users.forEach((user) => {
                addNewUserItem(user)
            })
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function addNewUserItem(user) {
    let userItem = $("#user-item").clone()
    userItem.removeAttr('hidden')
    userItem.attr("id", "user-item" + ++itemCount);
    userItem.find('input[name="user-id"]').val(user.userId)
    userItem.find('input[name="username"]').val(user.userName)
    userItem.find('input[name="nickname"]').val(user.nickname)

    let gradeVal = 0
    if(user.grade == "NORMAL")
        gradeVal = 1
    else if(user.grade == "EXPERT")
        gradeVal = 2
    else if(user.grade == "ADMIN")
        gradeVal = 3

    userItem.find('select[name="grade"]').val(gradeVal)

    $(".user-table").append(userItem)
}

function initPaging() {
    let param = {
        pageNumber: 0
    }

    $.ajax({
        url: '/pm/rest/users',
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

function clearItems() {
    for (let i = 1; i <= itemCount; i++) {
        $('#user-item' + i).remove()
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
        url: '/pm/rest/users',
        method: 'post',
        data: param,
        success: (response) => {
            response.users.forEach((user) => {
                addNewUserItem(user)
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

function editUser(button) {
    let userItem = $(button).closest('.user-item-basic');
    let userId = userItem.find('input[name="user-id"]').val();
    let username = userItem.find('input[name="username"]').val();
    let password = userItem.find('input[name="password"]').val();
    let nickname = userItem.find('input[name="nickname"]').val();
    let grade = userItem.find('select[name="grade"]').val();

    let param = {
        userId: userId,
        username: username,
        password: password,
        nickname: nickname,
        grade: grade
    }

    $.ajax({
        url: '/pm/rest/user/edit',
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