<template>
    <div class="container">
        <h4>게시글 작성</h4>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="title" placeholder="제목" v-model="board.title">
            <label for="title">Title</label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="writer" placeholder="작성자" v-model="board.writer">
            <label for="writer">Writer</label>
        </div>
        <div class="form-floating mb-3">
            <textarea class="form-control" id="content" placeholder="내용" style="height: 200px"
                v-model="board.content"></textarea>
            <label for="content">Content</label>
        </div>
        <div class="mb-3">
            <label for="fileUpload" class="form-label">Attach Image</label>
            <input type="file" class="form-control" id="fileUpload" accept="image/*" @change="validateFile">
            <div v-if="error" class="text-danger mt-2">{{ error }}</div>
        </div>
        <div class="d-flex justify-content-end">
            <button class="btn btn-outline-primary" :disabled="isSubmitDisabled" @click="createBoard">등록</button>

        </div>

    </div>
</template>

<script setup>
import { ref } from "vue";
import { useBoardStore } from "@/stores/board";

const board = ref({
    title: "",
    writer: "",
    content: "",
})


const store = useBoardStore();

// 파일 상태 관리
const file = ref(null); // 업로드된 파일
const error = ref(""); // 오류 메시지
const isSubmitDisabled = ref(false); // 등록 버튼 비활성화 상태

// 파일 유효성 검사 함수
const validateFile = (event) => {
    const allowedExtensions = ["jpg", "jpeg", "png", "webp"];
    const uploadedFile = event.target.files[0];

    if (uploadedFile) {
        // 마지막 부분(확장자) 추출
        const fileExtension = uploadedFile.name.split(".").pop().toLowerCase();

        if (uploadedFile.name.includes(".") && fileExtension) {
            // 정상적인 확장자가 있는 경우 처리
            console.log("확장자:", fileExtension);

            if (allowedExtensions.includes(fileExtension)) {
                file.value = uploadedFile;
                error.value = ""; // 오류 초기화
                isSubmitDisabled.value = false; // 등록버튼 활성화
            } else {
                file.value = null;
                error.value = "허용되지 않은 확장자입니다. jpg, jpeg, png, webp 파일만 업로드 가능합니다.";
                isSubmitDisabled.value = true; // 등록버튼 비활성화
            }
        } else {
            console.error("파일 이름이 유효하지 않습니다.");
        }
    } else {
        file.value = null;
        error.value = "파일을 선택해주세요.";
        isSubmitDisabled.value = true;
    }
};

// 게시글 등록 함수
const createBoard = function () {
    if (file.value) {
        console.log("업로드된 파일 : ", file.value.name)
    }
    store.createBoard(board.value)
};
</script>

<style scoped></style>