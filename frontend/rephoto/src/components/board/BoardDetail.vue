<template>
    <div class="board-detail-container">
        <!-- 제목 섹션 -->
        <div class="title-section">
            <h1 class="board-title">{{ store.board.boardTitle }}</h1>
            <div class="meta-info">
                <!-- 작성자, 작성일, 조회수 -->
                <div class="left-meta">
                    <RouterLink v-if="store.board.userId"
                    :to="{ name: 'profile', params: { userId: store.board.userId, userNick: store.board.userNick } }"
                    class="gallery-author">{{ store.board.userNick }}
                    <span class="writer"></span>
                </RouterLink>
                    <span class="separator"> </span>
                    <span class="date">{{ store.board.boardCreatedAt }}</span>
                    <span class="separator">·</span>
                    <span class="view-count">{{ store.board.viewCnt }} views</span>
                    <span class="separator">·</span>
                    <span class="avg-score">★ {{ formatScore(store.board.avgScore) }}</span>
                </div>
                <!-- 삭제 버튼 -->
                <div v-if="isOwner(store.board.userNick)" class="right-meta">
                    <button class="delete-button" @click="deleteBoard">삭제</button>
                </div>
            </div>
        </div>

        <!-- 사진 섹션 -->
        <div v-if="store.board.photoUuid" class="gallery">
            <div class="gallery-item h-100">
                <img :src="getPhotoUrl(store.board.photoUuid)" alt="Board Image" class="img-fluid" />
                <div class="gallery-links d-flex flex-column align-items-center justify-content-center">
                    <button class="gallery-link" @click="openOriginalImage">원본 보기</button>
                    <a :href="getDownloadUrl(store.board.photoUuid)" class="gallery-link" download>파일 다운로드</a>
                </div>
            </div>
        </div>

        <!-- 본문 내용 -->
        <div class="content-section">
            <p>{{ store.board.boardInfo }}</p>
        </div>

        <!-- 댓글 섹션 -->
        <div class="comments-section">
            <h2>댓글</h2>
            <ul class="comment-list">
                <li v-for="comment in store.board.comments" :key="comment.id" class="comment-item">
                    <div class="comment-container">
                        <!-- 댓글 작성자 프로필 이미지 -->
                        <img :src="`http://localhost:8080/user/userImg/${comment.userUuid}`" alt="Profile"
                            class="profile-image" />
                        <!-- 댓글 내용 -->
                        <div class="comment-content">
                            <div v-if="comment.isEditing">
                                <!-- 수정 모드 -->
                                <div class="d-flex align-items-center mt-2">
                                    <span style="font-weight: bold;">평가 </span>
                                    <div class="star-rating">
                                        <span v-for="n in 5" :key="n" class="star"
                                            :class="{ filled: n <= editCommentScore }" @click="editCommentScore = n">
                                            ★
                                        </span>
                                    </div>
                                </div>
                                <textarea v-model="editCommentContent" class="form-control"></textarea>
                                <!-- 경고 메시지 -->
                                <p v-if="editWarning" class="text-danger mt-2">{{ editWarning }}</p>
                                <div class="action-buttons">
                                    <button class="edit-button"
                                        @click.prevent.stop="saveEditedComment(comment)">저장</button>
                                    <button class="delete-button" @click.prevent.stop="cancelEdit(comment)">취소</button>
                                </div>
                            </div>
                            <div v-else>
                                <!-- 댓글 헤더 -->
                                <div class="comment-header">
                                    <span class="comment-writer">{{ comment.userNick }}</span>
                                    <div class="star-rating">
                                        <span v-for="n in 5" :key="n" class="star"
                                            :class="{ filled: comment.score >= n }">★</span>
                                    </div>
                                </div>

                                <!-- 댓글 본문 -->
                                <p class="comment-body">{{ comment.review }}</p>

                                <span class="comment-date">{{ comment.commentUpdatedAt }}</span>
                                <!-- 댓글 하단 -->
                                <div class="comment-footer">
                                    
                                    <div v-if="isOwner(comment.userNick)" class="action-buttons">
                                        <button class="edit-button" @click="editComment(comment)">수정</button>
                                        <button class="delete-button"
                                            @click.prevent.stop="deleteConfirm(comment)">삭제</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>

            <!-- 댓글 입력창 -->
<div v-if="store.board.userId !== storeU.loginUser.userId" class="comment-item">
    <div class="comment-container">
        <!-- 프로필 이미지 -->
        <img :src="`http://localhost:8080/user/userImg/${userUuid}`" alt="Profile" class="profile-image" />

        <!-- 댓글 작성 영역 -->
        <div class="comment-content">
            <!-- 별점 입력 -->
            <div class="rating-section">
                <span style="font-weight: bold;">평가</span>
                <div class="star-rating">
                    <span v-for="n in 5" :key="n" class="star" :class="{ filled: n <= newComment.score }"
                        @click="setScore(n)">
                        ★
                    </span>
                </div>
            </div>

            <!-- 댓글 입력 -->
            <textarea v-model="newComment.review" placeholder="댓글을 입력하세요" class="form-control"></textarea>
            <!-- 경고 메시지 추가 -->
            <p v-if="reviewWarning" class="text-danger mt-2">{{ reviewWarning }}</p>

            <!-- 버튼들 -->
            <div class="action-buttons">
                <button class="edit-button" @click="addComment" :disabled="isSubmitDisabled">
                    작성
                </button>
            </div>
        </div>
    </div>
</div>

        </div>
    </div>
</template>




<script setup>
import { useRoute } from "vue-router";
import { ref, onMounted, computed, watch } from "vue";
import { useBoardStore } from "@/stores/board";
import { useCommentStore } from "@/stores/comment";
import { useUserStore } from "@/stores/user";

const route = useRoute();
const store = useBoardStore();
const storeC = useCommentStore();
const storeU = useUserStore();

const editCommentContent = ref('');
const editCommentScore = ref(0);


const userUuid = computed(() => storeU.user.userUuid)

const reviewWarning = ref('');
const editWarning = ref('');

onMounted(() => {
    store.getBoard(route.params.id);
    storeU.getUser(storeU.loginUser.userId);

});

const formatScore = (score) => {
    if (score === undefined || score === null) {
        return "0.00"; // 기본값 설정
    }
    return parseFloat(score.toFixed(2));
};

const newComment = ref({
    review: "",
    score: 0,
    userNick: storeU.loginUser.userNick,
    boardId: parseInt(route.params.id)
});

const addComment = () => {
    if (!newComment.value.review.trim()) {
        alert("댓글 내용을 입력하세요.");
        return;
    }

    if (newComment.value.score < 1 || newComment.value.score > 5) {
        alert("평점은 1~5 사이여야 합니다.");
        return;
    }

    storeC.addComment(newComment.value);
    newComment.value.review = "";
    newComment.value.score = 0;
};

const validateReview = () => {
    if (newComment.value.review.length > 500) {
        reviewWarning.value = "댓글은 500자 이내로 작성해주세요.";
    } else {
        reviewWarning.value = "";
    }
};

// 댓글 수정 유효성 검사 함수
const validateEditComment = () => {
    if (editCommentContent.value.length > 500) {
        editWarning.value = "댓글은 500자 이내로 작성해주세요.";
    } else {
        editWarning.value = "";
    }
};

// 댓글 감시자
watch(
    () => newComment.value.review,
    validateReview
);
// 댓글 수정 감시자 추가
watch(
    () => editCommentContent.value,
    validateEditComment
);

// 댓글 작성 버튼 비활성화 상태 계산
const isSubmitDisabled = computed(() => {
    return (
        !newComment.value.review.trim() || // 댓글 내용이 비어있을 경우
        !!reviewWarning.value              // 경고 메시지가 있을 경우
    );
});

const editComment = (comment) => {
    comment.isEditing = true;
    editCommentContent.value = comment.review;
    editCommentScore.value = comment.score;
};

// 댓글 저장 함수 수정
const saveEditedComment = (comment) => {
    if (editWarning.value) {
        alert(editWarning.value); // 경고 메시지가 있을 경우 저장 중단
        return;
    }
    if (!editCommentContent.value.trim()) {
        alert("댓글 내용을 입력하세요.");
        return;
    }
    if (editCommentScore.value < 1 || editCommentScore.value > 5) {
        alert("평점은 1~5 사이여야 합니다.");
        return;
    }
    comment.review = editCommentContent.value;
    comment.score = editCommentScore.value;
    storeC.saveEditedComment(comment.commentId, comment);
    comment.isEditing = false;
};

const cancelEdit = (comment) => {
    comment.isEditing = false;
    editCommentContent.value = "";
    editCommentScore.value = 0;
};

const deleteConfirm = (comment) => {
    if (confirm("정말로 삭제하시겠습니까?")) {
        storeC.deleteComment(comment.commentId, comment.boardId);
    }
};

// 사진 UUID를 기반으로 URL 생성
const getPhotoUrl = (photoUuid) => `http://localhost:8080/img/${photoUuid}`;
const getDownloadUrl = (photoUuid) => `http://localhost:8080/download?fileUuid=${photoUuid}`;

// 새 창에서 원본 이미지를 열기
const openOriginalImage = () => {
    const photoUuid = store.board.photoUuid;
    const originalUrl = getPhotoUrl(photoUuid);
    window.open(originalUrl, "_blank");
};
const deleteBoard = () => {
    store.deleteBoard(route.params.id);
};

const isOwner = (userNick) => userNick === storeU.loginUser.userNick;

const setScore = (score) => {
    newComment.value.score = score;
};
</script>




<style scoped>
/* 공통 스타일 */
.board-detail-container {
    margin: 20px auto;
    padding: 20px;
    background-color: var(--surface-color);
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    color: var(--default-color);
}

/* 제목 섹션 */
.title-section {
    margin-bottom: 20px;
}

.board-title {
    font-size: 24px;
    font-weight: bold;
    color: var(--default-color);
    margin-bottom: 10px;
}

.meta-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 14px;
}

.left-meta {
    display: flex;
    align-items: center;
    gap: 5px;
    color: gray;
}

.writer {
    font-weight: bold;
    color: var(--default-color);
}

.separator {
    color: gray;
}

.date,
.view-count {
    font-size: 12px;
    color: gray;
}

.avg-score {
    font-size: 12px;
    color: gray;
    vertical-align: middle;
    /* 텍스트와 별을 동일 높이로 정렬 */
}

.right-meta {
    margin-left: auto;
}

/* 사진 섹션 */
.image-preview-container {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 20px;
    background-color: color-mix(in srgb, var(--default-color), transparent 85%);
    border-radius: 8px;
    overflow: hidden;
}

.image-preview {
    max-width: 100%;
    max-height: 400px;
    object-fit: contain;
    border-radius: 8px;
    cursor: pointer;
    transition: transform 0.3s;
}

.image-preview:hover {
    transform: scale(1.1);
}


/* 본문 내용 */
.content-section {
    margin-bottom: 30px;
    font-size: 16px;
}

/* 댓글 섹션 */
.star {
    font-size: 1.5rem;
    /* 기본 크기 */
    color: #ddd;
    /* 비활성화 상태 색상 */
    cursor: pointer;
    transition: transform 0.2s ease, color 0.2s ease;
}

.star.filled {
    color: gold !important;
    /* 활성화 상태 색상 */
}

.star:hover {
    transform: scale(1.2);
    color: gold !important;
    /* 호버 시 강조 */
}

.comments-section {
    margin-top: 30px;
}

.comments-section h2 {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 20px;
}

.comment-list {
    list-style: none;
    padding: 0;
    margin: 0;
}

.comment-item {
    display: flex;
    flex-direction: column;
    /* 세로 방향 정렬 */
    align-items: flex-start;
    /* 왼쪽 정렬 */
    margin-bottom: 20px;
    padding: 15px;
    background-color: color-mix(in srgb, var(--default-color), transparent 90%);
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    width: 100%;
    /* 부모 컨테이너 너비를 가득 채움 */
}

.comment-container {
    display: flex;
    width: 100%;
    /* 부모의 너비를 가득 채움 */
    align-items: flex-start;
    /* 이미지와 내용을 상단 정렬 */
    justify-content: space-between;
    /* 이름과 평가를 양쪽 끝으로 정렬 */
    gap: 1px;
    /* 이미지와 내용 간격 */
}

.profile-image {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 15px;
    flex-shrink: 0;
}

.comment-content {
    display: flex;
    flex-direction: column;
    flex: 1;
    /* 나머지 공간을 차지 */
}

.comment-content textarea {
    width: 100%;
    font-family: var(--default-font);
    font-size: 14px;
    padding: 10px 15px;
    border: 1px solid color-mix(in srgb, var(--default-color), transparent 80%);
    border-radius: 4px;
    background-color: var(--surface-color);
    /* 어두운 색상으로 변경 */
    color: var(--default-color);
    /* 텍스트 색상은 전역 기본값 */
    resize: none;
    transition: border-color 0.3s ease, background-color 0.3s ease;
    /* 부드러운 전환 효과 */
    height: 120px;
    /* 높이 약간 증가 */
}

.comment-content textarea:focus {
    border-color: var(--accent-color);
    outline: none;
}

.comment-content .star-rating {
    display: flex;
    gap: 1px;
    /* 별과 텍스트 사이의 간격 추가 */
    margin-left: 10px;
    /* 별과 '평가' 텍스트 간의 여유 공간 */
}

.comment-content .star-rating .star {
    font-size: 1.2rem;
    /* 별 크기 키우기 */
    color: #ddd;
    cursor: pointer;
    /* 클릭 가능하다는 느낌 제공 */
    transition: transform 0.2s ease;
    /* 클릭 시 부드러운 애니메이션 */
}

.comment-content .star-rating .star:hover {
    transform: scale(1.2);
    /* 마우스를 올리면 확대 */
}

.comment-content .star-rating .star.filled {
    color: gold;
    /* 선택된 별 색상 */
}

.comment-content .action-buttons {
    display: flex;
    justify-content: flex-end;
    /* 버튼을 오른쪽 정렬 */
    gap: 10px;
    /* 버튼 사이 간격 */
    margin-top: 10px;
    /* 수정창과 버튼 사이에 여백 추가 */
}

.edit-button,
.delete-button {
    font-family: var(--default-font);
    font-size: 12px;
    padding: 5px 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.edit-button {
    background-color: #2776a7;
    color: var(--default-color);
}

.edit-button:hover {
    background-color: color-mix(in srgb, #2776a7 60%, white 40%);
}

.delete-button {
    background-color: #a72727;
    color: var(--default-color);
}

.delete-button:hover {
    background-color: color-mix(in srgb, #a72727 60%, white 40%);
}


.edit-button:disabled {
    background-color: color-mix(in srgb, #2776a7, transparent 50%);
    cursor: not-allowed;
}

.edit-button:hover:not(:disabled) {
    background-color: color-mix(in srgb, #2776a7 90%, white 10%);
}


/* 댓글 헤더 */
.comment-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 5px;
    font-size: 14px;
    width: 100%;
}

.comment-writer {
    font-weight: bold;
    color: var(--default-color);
}

.star-rating {
    display: flex;
    gap: 2px;
    font-size: 14px;
}

.star {
    color: #ddd;
}

.star.filled {
    color: gold;
}

/* 댓글 본문 */
.comment-body {
    font-size: 14px;
    color: var(--default-color);
    margin: 5px 0 10px;
}

.comment-date{
    display: flex;
    justify-content:end;
    font-size: 12px;
    color: gray;
}

/* 댓글 하단 */
.comment-footer {
    display: flex;
    justify-content:end;
    font-size: 12px;
    color: gray;
}

.comment-date {
    font-size: 10px;
}

/* 수정/삭제 버튼 */
.edit-button,
.delete-button {
    font-family: var(--default-font);
    font-size: 12px;
    padding: 5px 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.edit-button {
    background-color: #2776a7;
    color: var(--default-color);
    margin-right: 5px;
}

.edit-button:hover {
    background-color: color-mix(in srgb, #2776a7 60%, white 40%);
}

.delete-button {
    background-color: #a72727;
    color: var(--default-color);
}

.delete-button:hover {
    background-color: color-mix(in srgb, #a72727 60%, white 40%);
}


/* 댓글 입력창 */
.comment-form {
    margin-top: 20px;
    padding: 15px;
    background-color: color-mix(in srgb, var(--default-color), transparent 90%);
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.comment-form textarea {
    width: 100%;
    font-family: var(--default-font);
    font-size: 14px;
    padding: 10px 15px;
    border: 1px solid color-mix(in srgb, var(--default-color), transparent 80%);
    border-radius: 4px;
    background-color: var(--surface-color);
    color: var(--default-color);
    resize: none;
    transition: border-color 0.3s ease;
}

.comment-form textarea:focus {
    border-color: var(--accent-color);
    outline: none;
}

.rating-section {
    display: flex;
    align-items: center;
    margin-top: 10px;
    font-size: 14px;
    margin: 10px 0 20px;
    /* 아래쪽 여백 추가 */
}

.rating-section .star-rating {
    display: flex;
    gap: 5px;
    margin-left: 10px;
}

.rating-section .star {
    font-size: 1.5rem;
    color: #ddd;
    cursor: pointer;
}

.rating-section .star.filled {
    color: gold;
}

/* 댓글 작성 버튼 */
.submit-button {
    font-family: var(--default-font);
    font-size: 14px;
    padding: 10px 20px;
    background-color: var(--accent-color);
    color: var(--default-color);
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    margin-top: 10px;
}

.submit-button:disabled {
    background-color: color-mix(in srgb, var(--accent-color), transparent 50%);
    cursor: not-allowed;
}

.submit-button:hover:not(:disabled) {
    background-color: color-mix(in srgb, var(--accent-color) 90%, white 10%);
}

.gallery {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px 0;
}

.gallery .gallery-item {
    position: relative;
    overflow: hidden;
    border-radius: 10px;
    max-width: 100%;
}

.gallery .gallery-item img {
    width: 100%;
    max-height: 400px;
    /* 이전 크기 유지 */
    object-fit: contain;
    /* 이미지 비율 유지 */
    object-position: center;
    /* 중앙 정렬 */
    transition: transform 0.3s;
}

.gallery .gallery-links {
    position: absolute;
    inset: 0;
    opacity: 0;
    transition: opacity 0.3s ease-in-out;
    background: rgba(0, 0, 0, 0.6);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.gallery .gallery-link {
    color: var(--contrast-color);
    /* 기본 텍스트 색상 */
    background-color: var(--surface-color);
    /* 기본 배경색 */
    border: none;
    padding: 10px 15px;
    margin: 5px 0;
    font-size: 14px;
    font-weight: bold;
    border-radius: 5px;
    cursor: pointer;
    text-align: center;
    text-decoration: none;
    transition: background-color 0.3s ease, color 0.3s ease;
    /* 색상과 배경색 변화에 애니메이션 추가 */
}

.gallery .gallery-link:hover {
    background-color: var(--accent-color);
    /* 호버 시 배경을 강조색으로 변경 */
    color: var(--heading-color);
    /* 텍스트 색상을 강조색 대비 색으로 변경 */
}

.gallery .gallery-item:hover .gallery-links {
    opacity: 1;
}

.gallery .gallery-item:hover img {
    transform: scale(1.1);
}

.gallery-author {
    color: #ffffff;
  font-size: 14px;
  font-weight: bold;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-decoration: none;

}

.gallery-author:hover {
  
  color: #bdbdbd;
}

@media screen and (max-width: 768px) {
    .meta-info {
        flex-direction: column;
        /* 세로 배치 */
        align-items: flex-start;
        /* 왼쪽 정렬 */
    }

    .left-meta {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        /* 줄바꿈 허용 */
        gap: 5px;
    }

    .separator {
        display: none;
        /* 구분자 제거 */
    }

    .writer,
    .date,
    .view-count {
        display: block;
        /* 한 줄에 하나씩 */
        font-size: 14px;
        /* 글씨 크기 조정 */
        margin-bottom: 5px;
    }

    .right-meta {
        align-self: flex-end;
        /* 삭제 버튼은 아래쪽에 정렬 */
        margin-top: 10px;
    }

    .view-count:last-child {
        margin-bottom: 10px;
        /* 마지막 항목 하단 여백 */
    }

    .avg-score {
        display: flex;
        align-items: center;
        /* 텍스트와 별 정렬 */
        gap: 5px;
        /* 별과 숫자 간격 */
        font-size: 14px;
        /* 모바일에서도 적절한 크기 */
        margin-bottom: 5px;
        /* 하단 여백 추가 */
    }

    .comment-container {
        flex-direction: row;
        /* 프로필 이미지는 왼쪽에 유지 */
        align-items: flex-start;
        /* 프로필 이미지와 내용 정렬 */
        gap: 10px;
    }

    .comment-content {
        display: flex;
        flex-direction: column;
        /* 닉네임, 별점, 내용, 수정일자, 버튼 순으로 */
        align-items: flex-start;
        gap: 5px;
    }

    .comment-header {
        flex-direction: column;
        /* 닉네임과 별점을 세로로 */
        align-items: flex-start;
        margin-bottom: 10px;
    }

    .comment-footer {
        flex-direction: column;
        /* 수정일자와 버튼을 세로로 */
        align-items: flex-start;
        gap: 10px;
    }

    .comment-footer .action-buttons {
        justify-content: flex-start;
        /* 버튼을 왼쪽 정렬 */
    }


    .comment-body {
        margin: 10px 0;
        /* 댓글 내용 간격 */
    }
}

@media screen and (max-width: 360px) {
    .rating-section {
        flex-direction: column;
        /* 세로 정렬 */
        align-items: center;
        /* 가운데 정렬 */
        gap: 5px;
        /* 텍스트와 별 간격 좁힘 */
        margin: 10px 0 20px;
        /* 아래쪽 여백 추가 */
    }
}
</style>
