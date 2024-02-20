function addMovie() {
    let title = $('.edit-input-section input[name="movie-title"]').val();
    let selectedGrade = $('.edit-input-section input[name="grade"]:checked').val();
    let description = $('.edit-input-section textarea[name="description-textarea"]').val();
    let src = $('.edit-input-section input[name="image-address"]').val();

    let param = {
        title: title,
        grade: selectedGrade,
        description: description,
        imageAddress: src
    }

    $.ajax({
        url: '/pm/rest/movie/add',
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