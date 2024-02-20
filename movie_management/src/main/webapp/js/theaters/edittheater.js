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
})

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
            initInput()
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}

function initInput() {
    $('.edit-input-section input[name="theater-name"]').val(theaterData.name);
    $('.edit-input-section textarea[name="location-textarea"]').val(theaterData.location);
    $('.edit-input-section input[name="theater-tel"]').val(theaterData.tel)
    $('.edit-input-section input[name="image-address"]').val(theaterData.imageAddress);
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

function editTheater() {
    let theaterId = theaterData.theaterId
    let name = $('.edit-input-section input[name="theater-name"]').val();
    let loc = $('.edit-input-section textarea[name="location-textarea"]').val();
    let tel = $('.edit-input-section input[name="theater-tel"]').val()
    let src = $('.edit-input-section input[name="image-address"]').val();

    let param = {
        theaterId: theaterId,
        name: name,
        location: loc,
        tel: tel,
        imageAddress: src
    }

    $.ajax({
        url: '/pm/rest/theater/edit',
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