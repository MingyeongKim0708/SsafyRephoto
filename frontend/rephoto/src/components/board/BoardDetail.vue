<template>
    <div class="container">
        <h4>게시글 상세</h4>
        <hr />
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
                    <strong>{{ comment.userNick }}</strong> <small class="text-muted">{{ comment.createdAt }}</small>
                    <p>{{ comment.review }}</p>
                    <div v-if="isOwner(comment.userNick)" class="d-flex justify-content-end">
                        <button class="btn btn-outline-primary btn-sm mx-1" @click="editComment(comment)">수정</button>
                        <button class="btn btn-outline-danger btn-sm" @click="deleteComment(comment.id)">삭제</button>
                    </div>
                </li>
            </ul>
            <!-- 댓글 입력 -->
            <div class="mt-3">
                <textarea v-model="newComment.review" class="form-control" rows="3" placeholder="댓글을 입력하세요"></textarea>
                <input v-model.number="newComment.score" type="number" class="form-control mt-2"
                    placeholder="평점을 입력하세요 (0~5)" min="0" max="5" />
                <button class="btn btn-primary mt-2" @click.prevent.stop="addComment">댓글 작성</button>
            </div>

        </div>
    </div>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import { useBoardStore } from "@/stores/board";
import { useCommentStore } from "@/stores/comment";
import { useUserStore } from "@/stores/user";
import axios from "axios";

const route = useRoute();
const router = useRouter();
const store = useBoardStore();
const store2 = useCommentStore();
const store3 = useUserStore();

// 새 댓글 입력 데이터
const newComment = ref({
    review: "",
    score: 0,
    userNick: store3.loginUser.userNick,
    boardId: parseInt(route.params.id)
})

onMounted(() => {
    store.getBoard(route.params.id); // 게시글 정보 및 댓글 목록 로드
});

// 사진 URL 생성 함수
const getPhotoUrl = (photoUuid) => `http://localhost:8080/img/${photoUuid}`;

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
    store2.addComment(newComment.value)

    newComment.value.review = "";   // 입력 필드 초기화
    newComment.value.score = 0;      // 평점 초기화
}

// 게시글 삭제
const deleteBoard = function(){
    store.deleteBoard(route.params.id)
}

// 본인 여부 확인
const isOwner = (userNick) => {
    const loginUser = JSON.parse(localStorage.getItem("loginUser"));
    return loginUser && loginUser.userNick === userNick;
};


</script>


<style scoped></style>
