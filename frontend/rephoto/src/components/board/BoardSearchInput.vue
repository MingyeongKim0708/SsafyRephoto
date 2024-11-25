<template>
    <div class="text-center">
        <div class="row align-items-center">
            <div class="col-2">
                <select class="form-select" v-model="searchInfo.key">
                    <option value="board_title">제목</option>
                    <option value="user_nick">작성자</option>
                    <option value="board_info">내용</option>
                </select>
            </div>
            <div class="col-6">
                <input type="text" class="form-control" v-model="searchInfo.word" @keyup.enter="searchBoardList"/>
            </div>
            <div class="col-2">
                <div class="form-check form-check-inline">
                    <input v-model="searchInfo.orderBy" class="form-check-input" type="radio" id="boardId" value="board_id" />
                    <label class="form-check-label" for="boardId"><i class="bi bi-fonts"></i>등록순</label>
                </div>
                <div class="form-check form-check-inline">
                    <input v-model="searchInfo.orderBy" class="form-check-input" type="radio" id="avgScore" value="avg_score" />
                    <label class="form-check-label" for="avgScore"><i class="bi bi-person-fill"></i>평점순</label>
                </div>
                <div class="form-check form-check-inline">
                    <input v-model="searchInfo.orderBy" class="form-check-input" type="radio" id="viewCnt"
                        value="view_cnt" />
                    <label class="form-check-label" for="viewCnt"><i class="bi bi-eye-fill"></i>조회수</label>
                </div>
            </div>
            <div class="col-1">
                <div @click="sortChange">
                    <span v-if="searchInfo.orderByDir === 'asc'">
                        <i class="bi bi-arrow-up-right-square-fill"></i>오름차순
                    </span>
                    <span v-else><i class="bi bi-arrow-down-right-square-fill"></i>내림차순</span>
                </div>
            </div>
            <div class="col-1">
                <button class="btn btn-outline-warning" @click="searchBoardList">검색</button>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import { ref } from "vue";
import { useBoardStore } from "@/stores/board";

const store = useBoardStore();
const searchInfo = ref({
    key: "board_title",
    word: "",
    orderBy: "board_id",
    orderByDir: "desc",
});
const searchBoardList = function () {
    store.searchBoardList(searchInfo.value);
};

const sortChange = function () {
    if (searchInfo.value.orderByDir === "asc")
        searchInfo.value.orderByDir = "desc";
    else searchInfo.value.orderByDir = "asc";
};
</script>
  
<style scoped></style>
  