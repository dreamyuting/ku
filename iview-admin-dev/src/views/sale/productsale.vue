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
                    title: {
                        text: '产品销售排行',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: []//动态后端返回
                    },
                    series: [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: [],// {value: 335, name: '直接访问'},返回返回数据格式
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                }

            }
        },
        methods: {},
        mounted() {
            //页面加载时请求后端数据
            util.ajax({
                url:'ProductSale/map',
                method:'get'
            }).then((resp)=>{
                this.option.legend.data = resp.data.titles;
                this.option.series[0].data=resp.data.datas;
            })
        }
    }
</script>

<style scoped>

</style>