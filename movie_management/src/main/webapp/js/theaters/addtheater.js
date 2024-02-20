function addTheater() {
    let name = $('.edit-input-section input[name="theater-name"]').val();
    let loc = $('.edit-input-section textarea[name="location-textarea"]').val();
    let tel = $('.edit-input-section input[name="theater-tel"]').val()
    let src = $('.edit-input-section input[name="image-address"]').val();

    let param = {
        name: name,
        location: loc,
        tel: tel,
        imageAddress: src
    }

    $.ajax({
        url: '/pm/rest/theater/add',
        method: 'post',
        data: param,
        success: (response) => {
            alert('등록되었습니다')
            location.reload()
        },
        error: (request, status, error) => {
            alert("에러 발생 : " + error.messages)
        }
    })
}