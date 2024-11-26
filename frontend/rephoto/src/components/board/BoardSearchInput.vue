<template>
    <div class="search-bar">
        <div class="search-bar-row">
            <!-- 검색 키 선택 -->
            <select class="search-select" v-model="searchInfo.key">
                <option value="board_title">제목</option>
                <option value="b.user_nick">작성자</option>
                <option value="board_info">내용</option>
            </select>

            <!-- 검색어 입력 -->
            <input type="text" class="search-input" v-model="searchInfo.word" @keyup.enter="searchBoardList"
                placeholder="검색어를 입력하세요" />

                <!-- 검색 버튼 -->
                <button class="search-button" @click="searchBoardList">
                    검색
                </button>

            <!-- 정렬 옵션 -->
            <div class="sort-options">
                <!-- 정렬 방식 선택 -->
                <div class="search-filters">
                    <div class="form-check-inline">
                        <input v-model="searchInfo.orderBy" class="form-check-input" type="radio" id="boardId"
                            value="board_id" />
                        <label class="form-check-label" for="boardId">등록일</label>
                    </div>
                    <div class="form-check-inline">
                        <input v-model="searchInfo.orderBy" class="form-check-input" type="radio" id="avgScore"
                            value="avg_score" />
                        <label class="form-check-label" for="avgScore">평점</label>
                    </div>
                    <div class="form-check-inline">
                        <input v-model="searchInfo.orderBy" class="form-check-input" type="radio" id="viewCnt"
                            value="view_cnt" />
                        <label class="form-check-label" for="viewCnt">조회수</label>
                    </div>
                </div>

                <!-- 정렬 방향 -->
                <button class="sort-button" @click="sortChange">
                    <span v-if="searchInfo.orderByDir === 'asc'">
                        <i class="bi bi-arrow-up-right-square-fill"></i> 오름차순
                    </span>
                    <span v-else>
                        <i class="bi bi-arrow-down-right-square-fill"></i> 내림차순
                    </span>
                </button>
                
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

<style scoped>
/* 검색창 스타일 */
.search-bar {
    margin: 20px auto;
    padding: 20px;
    background-color: var(--surface-color);
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.search-bar-row {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 20px;
    flex-wrap: wrap;
}

.search-select {
    font-family: var(--default-font);
    font-size: 14px;
    padding: 10px 15px;
    background-color: var(--surface-color);
    border: 1px solid color-mix(in srgb, var(--default-color), transparent 80%);
    border-radius: 4px;
    color: var(--default-color);
    transition: border-color 0.3s ease;
}

.search-select:focus {
    border-color: var(--accent-color);
}

.search-input {
    flex: 1;
    font-family: var(--default-font);
    font-size: 14px;
    padding: 10px 15px;
    border-radius: 4px;
    border: 1px solid color-mix(in srgb, var(--default-color), transparent 80%);
    background-color: var(--surface-color);
    color: var(--default-color);
}

.search-input:focus {
    border-color: var(--accent-color);
}

.search-filters {
    display: flex;
    gap: 1px;
    align-items: center;
    /* 라디오 버튼과 텍스트 정렬 */
}

.sort-options {
    display: flex;
    align-items: center;
    /* 라디오 버튼과 내림차순 버튼을 동일한 선상에 배치 */
}

.form-check-inline {
    display: flex;
    align-items: center;
}

.form-check-label {
    margin-left: 5px;
    font-family: var(--default-font);
    font-size: 14px;
    color: var(--default-color);
}

.form-check-input {
    accent-color: var(--accent-color);
}

.sort-button,
.search-button {
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

.sort-button:hover,
.search-button:hover {
    background-color: color-mix(in srgb, var(--accent-color) 60%, white 40%);
}

/* 내림차순 버튼 스타일 */
.sort-button {
    padding: 8px 15px; /* 버튼 크기 조정 */
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 5px; /* 아이콘과 텍스트 사이 간격 */
    border: 1px solid var(--accent-color); /* 테두리를 강조 */
    border-radius: 20px; /* 토글 버튼 느낌을 주기 위한 둥근 테두리 */
    cursor: pointer;
    transition: all 0.3s ease;
    background-color: transparent; /* 기본 배경색 */
    color: var(--accent-color); /* 기본 글자색 */
    font-weight: bold;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 약간의 그림자 */
}

.sort-button.active {
    background-color: var(--accent-color); /* 활성화 상태 배경색 */
    color: white; /* 활성화 상태 글자색 */
}

.sort-button:hover {
    background-color: color-mix(in srgb, var(--accent-color) 80%, white 20%);
    color: white;
}

/* 아이콘 크기 조정 */
.sort-button i {
    font-size: 16px;
}




/* 반응형 스타일 */
@media (max-width: 768px) {
    .search-bar-row {
        flex-direction: column;
        /* 모바일 화면에서 세로 배치 */
        align-items: stretch;
        /* 너비를 100%로 */
        gap: 15px;
        /* 요소 간격을 늘림 */
    }

    .search-select,
    .search-input{
        width: 100%;
        /* 너비를 100%로 */
    }

    .search-filters {
        justify-content: space-between;
    }

    .sort-options {
        flex-direction: column;
        /* 모바일에서는 라디오 버튼과 내림차순 버튼을 세로로 배치 */
        gap: 10px;
        align-items: center; /* 정렬 옵션과 검색 버튼 가운데 정렬 */
        text-align: center; /* 텍스트 가운데 정렬 */
    }

    .sort-button,
    .search-button {
        width: auto; /* 버튼 크기를 콘텐츠에 맞춤 */
    }

    .sort-button {
        width: auto;
        font-size: 14px;
    }
}
</style>
