<template>
    <div class="container">
        <div class="d-flex justify-content-center">
            <div class="card" style="width: 30rem">
                <div class="card-body">
                    <div class="mb-3 d-flex justify-content-between align-items-center">
                        <h4 class="card-title">{{ store.board.boardTitle }} <span class="badge bg-danger">{{
                            store.board.viewCnt
                                }}</span></h4>
                        <div class="d-flex justify-content-end">
                            <h6 class="card-subtitle mx-3 text-body-secondary">{{ store.board.userNick }}</h6>
                            <h6 class="card-subtitle text-body-secondary">{{ store.board.boardCreatedAt }}</h6>
                        </div>
                    </div>
                    <img :src="getPhotoUrl(store.board.photoUuid)" :alt="store.board.boardTitle" class="card-img-top">
                    <p class="card-text ">
                        {{ store.board.boardInfo }}
                    </p>
                    <p class="card-text">
                        {{ store.board.photoName }}
                    </p>
                    <p class="card-text">
                        {{ store.board.avgScore }}
                    </p>
                    <div class="d-flex justify-content-center">

                        <button class="mx-3 btn btn-outline-danger" @click="deleteBoard">삭제</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- 댓글 섹션 -->
        <div class="mt-5">
            <h5>댓글</h5>
            <ul class="list-group">
                <li class="list-group-item" v-for="comment in store.board.comments" :key="comment.id">
                    <div v-if="comment.isEditing">
                        <textarea v-model="editContent" class="form-control"></textarea>
                        <button class="btn btn-success btn-sm mt-2"
                            @click.prevent.stop="saveEditedComment(comment)">저장</button>
                        <button class="btn btn-secondary btn-sm mt-2" @click.prevent.stop="cancelEdit">취소</button>
                    </div>
                    <div v-else>
                        <!-- 일반 모드 -->
                        <strong>{{ comment.userNick }}</strong>
                        <small class="text-muted">{{ comment.commentUpdatedAt }}</small>
                        <p>{{ comment.review }}</p>
                        <div class="d-flex align-items-center">
                            <!-- 별 표시 -->
                            <span v-for="n in 5" :key="n" :class="{'text-warning': comment.score >= n, 'text-muted': comment.score < n}">
                                ★
                            </span>
                            <!-- 평점 숫자 표시 -->
                            <small class="ms-2">({{ comment.score }})</small>
                        </div>
                        <div v-if="isOwner(comment.userNick)" class="d-flex justify-content-end">    
                            <button class="btn btn-outline-primary btn-sm mx-1"
                                @click.prevent.stop="editComment(comment)">수정</button>
                            <button class="btn btn-outline-danger btn-sm"
                                @click.prevent.stop="deleteConfirm(comment)">삭제</button>
                        </div>
                    </div>
                </li>
            </ul>
            <!-- 댓글 입력 -->
            <div class="mt-3">
                <form @submit.prevent.stop="addComment">
                    <textarea v-model="newComment.review" class="form-control" rows="3" placeholder="댓글을 입력하세요"></textarea>
                    <div class="d-flex align-items-center mt-2">
                        <span>평점: </span>
                        <div class="star-rating">
                            <span 
                                v-for="n in 5" 
                                :key="n" 
                                class="star" 
                                :class="{ filled: n <= newComment.score }"
                                @click="setScore(n)">
                                ★
                            </span>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary mt-2" >댓글 작성</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useRoute} from "vue-router";
import { ref, onMounted } from "vue";
import { useBoardStore } from "@/stores/board";
import { useCommentStore } from "@/stores/comment";
import { useUserStore } from "@/stores/user";

const route = useRoute();
const store = useBoardStore();
const storeC = useCommentStore();
const storeU = useUserStore();
const editCommentContent = ref('');

onMounted(() => {
    console.log(route.params.id)
    store.getBoard(route.params.id); // 게시글 정보 및 댓글 목록 로드
});

// 새 댓글 입력 데이터
const newComment = ref({
    review: "",
    score: 0,
    userNick: storeU.loginUser.userNick,
    boardId: parseInt(route.params.id)
})

// 댓글 등록
const addComment = function () {
    if (!newComment.value.review.trim()) {
        alert("댓글 내용을 입력하세요.");
        return;
    }

    if (newComment.value.score < 0 || newComment.value.score > 5) {
        alert("평점은 0~5 사이여야 합니다.");
        return;
    }

    console.log(typeof newComment.value.review)
    console.log(typeof newComment.value.boardId)
    console.log(typeof newComment.value.score)
    console.log(typeof newComment.value.userNick)
    console.log(newComment.value)
    storeC.addComment(newComment.value)


    newComment.value.review = "";   // 입력 필드 초기화
    newComment.value.score = 0;      // 평점 초기화
}



// 댓글 수정
const editComment = (comment) => {
    console.log(comment)
    comment.isEditing = true; // 해당 댓글 수정 모드로 변경
    editCommentContent.value = comment.review;
};

// 댓글 수정 저장
const saveEditedComment = (comment) => {
    if (!editContent.value.trim()) {
        alert("댓글 내용을 입력하세요.");
        return;
    }
    comment.review = editCommentContent.value
    // store를 통해 댓글 수정 요청
    storeC.saveEditedComment(comment.commentId, comment);
    comment.isEditing = false; // 수정 모드 종료
};

// 수정 취소
const cancelEdit = (comment) => {
    comment.isEditing = false; // 수정 모드 종료
    editCommentContent.value = ""; // 내용 초기화
};

// 댓글 삭제 확인
const deleteConfirm = (comment) => {
    if (confirm("정말로 삭제하시겠습니까?")) {
        storeC.deleteComment(comment.commentId,comment.boardId); // 댓글 삭제
    }
};


// 사진 URL 생성 함수
const getPhotoUrl = (photoUuid) => `http://localhost:8080/img/${photoUuid}`;


// 게시글 삭제
const deleteBoard = function () {
    store.deleteBoard(route.params.id)
}

// 본인 여부 확인
const isOwner = (userNick) => {
    // 로그인한 사용자의 닉네임과 댓글 작성자의 닉네임을 비교
    return userNick === storeU.loginUser.userNick;
};

// 평점 설정
const setScore = (score) => {
    newComment.value.score = score; // 평점 설정
};


</script>



<style scoped>
.star {
    font-size: 1.5rem;
    cursor: pointer;
    color: #ddd;
}

.star.filled {
    color: gold;
}
</style>
