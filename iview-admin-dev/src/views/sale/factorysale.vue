<template>
    <div class="layout">
        <card>
            <v-chart :options="option" style="width: 600px;height:400px;"/>
        </card>

    </div>
</template>

<script>
    //使用图表需要的依赖
    //下面是单个图表依赖(推荐)
    // import 'echarts/lib/chart/bar';
    // import 'echarts/lib/component/tooltip';
    //下面是所有图表依赖
    import 'echarts';
    //注册组件需要的依赖
    import ECharts from 'vue-echarts'
    // 参考全局组件注册代码
    // Vue.component('v-chart', ECharts)
    import util from '../../libs/util'
    export default {
        //局部组件注册
        components: {
            'v-chart': ECharts
        },
        data() {
            return {
                option: {
                    title:{
                        text:'厂家销售情况',
                        left: 'center'
                    },
                    color: ['#3398DB'],
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: [],//后端返回的
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '直接访问',
                            type: 'bar',
                            barWidth: '60%',
                            data: []//后端返回的
                        }
                    ]
                }

            }
        },
        methods: {},
        mounted() {
            //页面加载时请求后端数据
            util.ajax({
                url:'factorySale/map',
                method:'get'
            }).then((resp)=>{
                this.option.xAxis[0].data = resp.data.titles;
                this.option.series[0].data=resp.data.datas;
            })
        }
    }
</script>

<style scoped>

</style>