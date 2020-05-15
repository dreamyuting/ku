<template>

    <div class="animated fadeIn">
        <Card>
            <Row >
                <Col span="8">出货日期：
                    <DatePicker type="date" format="yyyy-MM" v-model="outDate"
                                placeholder="Select date" style="width: 200px"></DatePicker>
                    <Button type="primary" shape="circle" icon="ios-search" @click="print">打印</Button></Col>
            </Row>
        </Card>
    </div>
</template>

<script>
    import dateFormat from '../../libs/date'
    export default {
        data() {
            return {
                outDate:'2019-10'
            }

        },
        methods: {
            //下载出货表
            print(){
                //使用工具构造日期字符串
                var dateString = dateFormat(this.outDate);
                //dateString.substr(0,7):截取年月
                //一般通过打开新窗口方式，请求地址是后端响应数据地址
                var url = "http://localhost:8081/outProduct/print?outDate="+dateString.substr(0,7);//
                //不覆盖当前页面，而是在新窗口下载
                window.open(url)
            }
        },
        mounted: function () {

        }
    }


</script>