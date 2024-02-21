<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- 수정 모달 -->
<div class="modal fade" id="edit-schedule-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">상영정보 수정</h5>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="edit-start-date">시작일:</label>
                    <input type="date" class="form-control" id="edit-start-date">
                </div>
                <div class="form-group">
                    <label for="edit-end-date">종료일:</label>
                    <input type="date" class="form-control" id="edit-end-date">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                <button id="edit-schedule-button" type="button" class="btn btn-primary">저장</button>
            </div>
        </div>
    </div>
</div>