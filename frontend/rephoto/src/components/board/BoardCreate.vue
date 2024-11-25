<template>
    <div class="container">
        <h4>게시글 작성</h4>

        <form @submit.prevent.stop="createBoard">
            <fieldset>

                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="title" placeholder="제목" v-model="board.title">
                    <label for="title">Title</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="writer" placeholder="작성자" v-model="board.writer"
                        readonly>
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
                    <img v-if="previewUrl" :src="previewUrl" alt="프로필 이미지 미리보기" width="200px" height="200px" />
                    <div v-if="error" class="text-danger mt-2">{{ error }}</div>
                </div>
                <div class="d-flex justify-content-end">
                    <button class="btn btn-outline-primary" :disabled="isSubmitDisabled" type="submit">등록</button>
                </div>
            </fieldset>

        </form>


    </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useBoardStore } from "@/stores/board";
import { useUserStore } from "@/stores/user";

const board = ref({
    title: "",
    writer: "",
    content: "",
})


const store = useBoardStore();
const store2 = useUserStore();

// 파일 상태 관리
const file = ref(null); // 업로드된 파일
const error = ref(""); // 오류 메시지
const previewUrl = ref("") // 미리보기

// 로컬 스토리지에서 로그인 사용자 정보 가져오기
const loadUserNick = () => {
    if (store2.loginUser) {
        board.value.writer = store2.loginUser.userNick;
    }
};

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

                if (uploadedFile) {
                    previewUrl.value = URL.createObjectURL(uploadedFile);
                } else {
                    previewUrl.value = "";
                }
                error.value = ""; // 오류 초기화
            } else {
                file.value = null;
                error.value = "허용되지 않은 확장자입니다. jpg, jpeg, png, webp 파일만 업로드 가능합니다.";
            }
        } else {
            console.error("파일 이름이 유효하지 않습니다.");
        }
    } else {
        file.value = null;
        error.value = "파일을 선택해주세요.";
    }
};
// 등록 버튼 비활성화 상태 계산
const isSubmitDisabled = computed(() => {
    return (
        !board.value.title.trim() || // 제목이 비어 있는지 확인
        !board.value.writer.trim() || // 작성자가 비어 있는지 확인
        !board.value.content.trim() || // 내용이 비어 있는지 확인
        !file.value || // 파일이 업로드되지 않았는지 확인
        !!error.value // 오류 메시지가 있는지 확인
    );
});
// 게시글 등록 함수
const createBoard = function () {
    if (file.value) {
        console.log("업로드된 파일 : ", file.value.name)
    }
    store.createBoard(file.value, board.value.writer, board.value.title, board.value.content)
};

// 컴포넌트가 마운트될 때 로그인 사용자 닉네임 로드
onMounted(() => {
    loadUserNick();
    console.log(store2.loginUser)
});
</script>

<style scoped></style>