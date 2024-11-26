<template>
    <div class="board-create-container">
        <form @submit.prevent.stop="createBoard">
            <fieldset>
                <!-- 제목 입력 -->
                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" class="form-control" id="title" placeholder="제목" v-model="board.title" />
                    <div v-if="titleWarning" class="text-danger mt-2">{{ titleWarning }}</div>
                </div>

                <!-- 작성자 -->
                <div class="form-group">
                    <label for="writer">Writer</label>
                    <input type="text" class="form-control" id="writer" placeholder="작성자" v-model="board.writer"
                        readonly />
                </div>

                <!-- 이미지 업로드 -->
                <div class="form-group">
                    <label for="fileUpload">Attach Image</label>
                    <div class="image-preview-container">
                        <img v-if="previewUrl" :src="previewUrl" alt="프로필 이미지 미리보기" class="image-preview" />
                        <div v-else class="image-placeholder">
                            이미지 없음
                        </div>
                    </div>
                    <input type="file" class="form-control" id="fileUpload" accept="image/*" @change="validateFile"
                        ref="fileInput" />
                    <div v-if="error" class="text-danger mt-2">{{ error }}</div>
                </div>

                <!-- 내용 입력 -->
                <div class="form-group">
                    <label for="content">Content</label>
                    <textarea class="form-control" id="content" placeholder="내용" style="height: 200px"
                        v-model="board.content"></textarea>
                </div>

                <!-- 등록 버튼 -->
                <div class="form-actions">
                    <button class="submit-button" :disabled="isSubmitDisabled" type="submit">
                        등록
                    </button>
                </div>
            </fieldset>
        </form>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { useBoardStore } from "@/stores/board";
import { useUserStore } from "@/stores/user";

const board = ref({
    title: "",
    writer: "",
    content: "",
});

const store = useBoardStore();
const store2 = useUserStore();

const titleWarning = ref(""); // 제목 경고 메시지
const file = ref(null); // 업로드된 파일
const error = ref(""); // 파일 관련 오류 메시지
const previewUrl = ref(""); // 이미지 미리보기 URL

// 로컬 스토리지에서 로그인 사용자 정보 가져오기
const loadUserNick = () => {
    if (store2.loginUser) {
        board.value.writer = store2.loginUser.userNick;
    }
};

// 제목 글자수 유효성 검사
const validateTitle = () => {
    if (board.value.title.length > 50) {
        titleWarning.value = "제목은 50자 이내로 작성해주세요.";
    } else {
        titleWarning.value = "";
    }
};

// 파일 유효성 검사 함수
const validateFile = (event) => {
    const allowedExtensions = ["jpg", "jpeg", "png", "webp"];
    const uploadedFile = event.target.files[0];

    if (uploadedFile) {
        const fileExtension = uploadedFile.name.split(".").pop().toLowerCase();

        if (allowedExtensions.includes(fileExtension)) {
            file.value = uploadedFile;
            previewUrl.value = URL.createObjectURL(uploadedFile);
            error.value = "";
        } else {
            clearFile(); // 파일 상태 초기화
            error.value = "허용되지 않은 확장자입니다. jpg, jpeg, png, webp 파일만 업로드 가능합니다.";
        }
    } else {
        clearFile(); // 파일 상태 초기화
        error.value = "파일을 선택해주세요.";
    }
};

// 등록 버튼 비활성화 상태 계산
const isSubmitDisabled = computed(() => {
    return (
        !board.value.title.trim() ||
        titleWarning.value ||
        !board.value.writer.trim() ||
        !board.value.content.trim() ||
        !file.value ||
        !!error.value
    );
});

// 게시글 등록 함수
const createBoard = function () {
    if (file.value) {
        console.log("업로드된 파일 : ", file.value.name);
    }
    store.createBoard(file.value, board.value.writer, board.value.title, board.value.content);
};

// 컴포넌트가 마운트될 때 로그인 사용자 닉네임 로드
onMounted(() => {
    loadUserNick();
});

// 제목 감시자
watch(
    () => board.value.title,
    validateTitle
);

const fileInput = ref(null); // 파일 입력 요소에 대한 참조

const clearFile = () => {
    file.value = null; // 파일 상태 초기화
    previewUrl.value = ""; // 미리보기 URL 초기화
    error.value = ""; // 오류 메시지 초기화
    if (fileInput.value) {
        fileInput.value.value = ""; // 파일 입력 요소 초기화
    }
};

</script>

<style scoped>
.board-create-container {
    margin: 20px auto;
    padding: 20px;
    background-color: var(--surface-color);
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    font-weight: bold;
    color: var(--default-color);
}

.form-control {
    font-family: var(--default-font);
    font-size: 14px;
    padding: 10px 15px;
    background-color: var(--surface-color);
    border: 1px solid color-mix(in srgb, var(--default-color), transparent 80%);
    border-radius: 4px;
    color: var(--default-color);
    transition: border-color 0.3s ease;
}

.form-control:focus {
    border-color: var(--accent-color);
}

.image-preview-container {
    width: 100%;
    height: 250px;
    background-color: color-mix(in srgb, var(--default-color), transparent 85%);
    border: 1px dashed var(--default-color);
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 10px;
}

.image-preview {
    max-width: 100%;
    max-height: 100%;
    object-fit: cover;
    border-radius: 8px;
}

.image-placeholder {
    font-family: var(--default-font);
    font-size: 14px;
    color: var(--default-color);
}

.submit-button {
    font-family: var(--default-font);
    font-size: 14px;
    padding: 10px 20px;
    color: var(--default-color);
    background-color: var(--accent-color);
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.submit-button:disabled {
    background-color: color-mix(in srgb, var(--accent-color), transparent 50%);
    cursor: not-allowed;
}

.submit-button:hover:not(:disabled) {
    background-color: color-mix(in srgb, var(--accent-color) 90%, white 10%);
}

.text-danger {
    color: #dc3545;
}

.form-actions {
    display: flex;
    justify-content: flex-end;
    /* 버튼을 오른쪽으로 배치 */
    margin-top: 20px;
}
</style>