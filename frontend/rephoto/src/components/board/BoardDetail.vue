<template>
    <div class="container">
        <h4>게시글 상세</h4>
        <hr />
        <div class="d-flex justify-content-center">
            <div class="card" style="width: 30rem">
                <div class="card-body">
                    <div class="mb-3 d-flex justify-content-between align-items-center">
                        <h4 class="card-title">{{ store.board.boardTitle }} <span class="badge bg-danger">{{ store.board.viewCnt
                        }}</span></h4>
                        <div class="d-flex justify-content-end">
                            <h6 class="card-subtitle mx-3 text-body-secondary">{{ store.board.userNick }}</h6>
                            <h6 class="card-subtitle text-body-secondary">{{ store.board.boardCreatedAt }}</h6>
                        </div>
                    </div>
                    <img :src="getPhotoUrl(store.board.photoUuid)" 
                    :alt="store.board.boardTitle" class="card-img-top">
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
    </div>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import { useBoardStore } from "@/stores/board";
import axios from "axios";
const route = useRoute();
const router = useRouter();
const store = useBoardStore();

onMounted(() => {
    store.getBoard(route.params.id);
});


const deleteBoard = function () {
    axios
        .delete(`http://localhost:8080/board/${route.params.id}`)
        .then(() => {
            router.push({ name: "boardList" });
        })
        .catch(() => { });
};

// 사진 URL 생성 함수
const getPhotoUrl = (photoUuid) => `http://localhost:8080/img/${photoUuid}`;
</script>

<style scoped></style>
