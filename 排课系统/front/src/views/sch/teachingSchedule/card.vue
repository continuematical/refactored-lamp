<template>
<div>
    <Card>
        <Row :gutter="10" v-for="(item1,index1) in parkList" :key="index1">
            <Col span="24" v-show="index1==2 || index1==4">
                <Divider  />
            </Col>
            <Col span="4" v-for="(item2,index2) in item1.scheduleList" :key="index2">
                <div>
                    <Card class="card-main" :class="{'superCard': item2.curId != ''}">
                        <span class="card-main-title">
                            {{ item2.title }}
                        </span>
                        <span class="card-main-content">
                            {{ item2.curName }}
                        </span>
                        <span class="card-main-content">
                            {{ item2.teacherName }}
                        </span>
                        <span class="card-main-content">
                            {{ item2.place }}
                        </span>
                    </Card>
                </div>
            </Col>
        </Row>
    </Card>
</div>
</template>

    
<script>
import {
    getCardList,
} from "./api.js";
export default {
    name: "single-window",
    components: {},
    data() {
        return {
            parkList: [],
        };
    },
    methods: {
        init() {
            this.getCardListFx();
            this.connect();
        },
        getCardListFx() {
            var that = this;
            that.parkList = [];
            getCardList().then(res => {
                if (res.success) {
                    that.parkList = res.result;
                }
            })
        },
    },
    mounted() {
        this.init();
    }
};
</script>

    
<style lang="less" scoped>
.card-main {
    font-family: SimHei;
    display: flex;
    flex-direction: column;
    margin: 5px;
    width: 160px;
    height: 250px;
    margin-top: 10px;
}

.card-main-title {
    font-size: 18px;
    font-weight: 550;
    display: block;
    text-align: center;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
}

.card-main-content {
    font-size: 18px;
    display: block;
    text-align: center;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
}

.superCard {
    background: #ec7a7a !important;
    color: aliceblue !important;
}
</style>
