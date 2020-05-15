<template>
<div class="animated fadeIn">
    <Row>
        <Button type="success" icon="wrench" @click="edit()">修改</Button>
        <Button type="error" icon="trash-a" @click="remove()">删除</Button>
        <Button type="info" @click="submit">提交</Button>
        <Button type="info" @click="cancel()">取消</Button>
        <Button type="success" icon="wrench" @click="outPrint">打印</Button>
    </Row>
    <br>
    <Row>
        <div style="padding: 10px 0;">
            <Table border :columns="columns1" :data="data1" @on-selection-change="s=>{change(s)}"></Table>
        </div>
    </Row>
    <br>
    <Row>
        <div style="text-align: right;">
            <Page :total="totalCount" :page-size="pageSize" :current="pageNo" @on-change="getpage"
                  align="center" show-total></Page>
        </div>
    </Row>
</div>
</template>

<script type="text/ecmascript-6">
    import util from '../../libs/util';

    export default {
        data() {

            return {
                count: 0,
                gourpId: null,
                pageSize: 20,
                pageNo: 1,
                totalPage: 0,
                totalCount: 0,
                columns1: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    }/*,
                    {
                        title: '保运号',
                        key: 'id'
                    }*/,
                    {
                        title: '货物数/附件数',
                        key: 'nums'
                    },
                    {
                        title: '信用证号',
                        key: 'lcno'
                    },
                    {
                        title: '装运港',
                        width: 90,
                        key: 'shipmentPort'
                    },
                    {
                        title: '目的港',
                        key: 'destinationPort'
                    },
                    {
                        title: '运输方式',
                        key: 'transportMode'
                    },
                    {
                        title: '价格条件',
                        key: 'priceCondition'
                    },
                    {
                        title: '制单日期',
                        key: 'inputDate'
                    },
                    {
                        title: '状态',
                        key: 'state',
                        width: 80,
                        render: (h, params) => {
                            // 0-草稿 1-已上报 2-装箱 3-委托 4-发票 5-财务
                            const task_status = parseInt(params.row.state);
                            if (task_status === 0) {
                                return "草稿";
                            }
                            else if (task_status === 1) {
                                return "已上报";
                            }
                            else if (task_status === 2) {
                                return "装箱";
                            }
                            else if (task_status === 3) {
                                return "委托";
                            }
                            else if (task_status === 4) {
                                return "发票";
                            }
                            else if (task_status === 5) {
                                return "财务";
                            }
                            else {
                                return "unkown"
                            }
                        }
                    }
                ],
                self: this,
                data1: [],
                modal1: false,
                modal2: false
            }
        },
        methods: {
            cancel() {
                this.$Message.info('点击了取消');
            },
            reset(form){
                this.$refs[form].resetFields();
            },
            change(e){
                if (e.length == 1) {
                    this.updateForm = e[0];
                }
                this.setGroupId(e);
            },
            setGroupId(e)
            {
                this.groupId = [];
                this.count = e.length;
                for (var i = 0; i < e.length; i++) {
                    this.groupId.push(e[i].id);
                }
            },
            edit () {
                if (this.count != 1) {
                    this.modal1 = false;
                    this.$Message.warning('请至少并只能选择一项');
                }
                else {
                    //跳转到编辑页面
                    this.$router.push({path:'/cargo/ExportEdit/'+(this.groupId[0])});
                }
            },
            remove () {
                if (this.groupId != null && this.groupId != "") {
                    util.ajax({
                        url: 'export/delete',
                        method: 'delete',
                        data: this.groupId
                    }).then((result) => {
                        if (result.data == 'success') {
                            this.$Message.success('Success!');
                            this.getpage(this.pageNo);
                        }
                    });
                } else {
                    this.$Message.warning('请至少选择一项');
                }
            },
            handleReset (form) {
                this.$refs[form].resetFields();
            },
            getpage(pageNo){
                const pageSize = this.pageSize;
                util.ajax({
                    url: 'export/getPage',
                    method: 'get',
                    params: {pageSize, pageNo}
                }).then((result) => {
                    this.data1 = result.data.list;
                    this.pageSize = pageSize;
                    this.pageNo = pageNo;
                    this.totalCount = result.data.totalCount;
                });
            },
            submit(){
                if (this.groupId != null && this.groupId != "") {
                    util.ajax({
                        url: 'export/submit',
                        method: 'put',
                        data: this.groupId
                    }).then((result) => {
                        if (result.data == 'success') {
                            this.$Message.success('Success!');
                            this.getpage(this.pageNo);
                        }
                    });
                } else {
                    this.$Message.warning('请至少选择一项');
                }
            },
            cancel(){
                if (this.groupId != null && this.groupId != "") {
                    util.ajax({
                        url: 'export/cancel',
                        method: 'put',
                        data: this.groupId
                    }).then((result) => {
                        if (result.data == 'success') {
                            this.$Message.success('Success!');
                            this.getpage(this.pageNo);
                        }
                    });
                } else {
                    this.$Message.warning('请至少选择一项');
                }
            },
            outPrint:function () {
                if (this.count != 1) {
                    this.$Message.warning('只能选择一项');
                }
                else {
                    util.ajax({
                        url: 'export/outPrint/'+this.groupId[0],
                        method: 'get',
                    }).then((result) => {

                    });
                }

            }
        },
        mounted: function () {
            this.getpage(1)
        }
    }
</script>
