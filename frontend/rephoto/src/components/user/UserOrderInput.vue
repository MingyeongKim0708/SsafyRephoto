<template>
    <div class="text-center">
        <div class="row align-items-center">
            <div class="col-10">
                <div class="form-check form-check-inline">
                    <input v-model="searchInfo.orderBy" class="form-check-input" type="radio" id="boardId" value="board_id" />
                    <label class="form-check-label" for="boardId"><i class="bi bi-fonts"></i>등록순</label>
                </div>
                <div class="form-check form-check-inline">
                    <input v-model="searchInfo.orderBy" class="form-check-input" type="radio" id="avgScore" value="avg_score" />
                    <label class="form-check-label" for="avgScore"><i class="bi bi-person-fill">평점순</i></label>
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

const props = defineProps({
    userNick: String,
})
const store = useBoardStore();
const searchInfo = ref({
    key: "user_nick",
    word: props.userNick,
    orderBy: "board_id",
    orderByDir: "desc",
});
const searchBoardList = function () {
    store.searchUserBoardList(searchInfo.value,props.userNick);
};

const sortChange = function () {
    if (searchInfo.value.orderByDir === "asc")
        searchInfo.value.orderByDir = "desc";
    else searchInfo.value.orderByDir = "asc";
};
</script>
  
<style scoped></style>
  