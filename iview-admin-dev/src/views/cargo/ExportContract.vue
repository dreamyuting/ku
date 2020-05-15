<template>


    <div class="animated fadeIn">
        <Row >
            合同号：
            <Input v-model="contractNo" placeholder="请输入..." style="width:200px"/>
           <Button type="primary" shape="circle" icon="ios-search" @click="search()">搜索</Button>
            <Button type="primary" shape="circle" icon="ios-search" @click="showPage()">返回</Button>
            <Button type="error" icon="trash-a" @click="addExport()">报运</Button>
        </Row>
        <br>
        <Row>
            <Table :columns="columns1" :data="data1" @on-selection-change="s=>{change(s)}"></Table>
        </Row>
        <br>
        <Row>
            <Page :total="totalCount" :page-size="pageSize" :current="pageNo" @on-change="getpage"
                  align="center" show-total></Page>
        </Row>
        <Modal
                v-model="modal2"
                title="新增出口报运"
                @on-ok="add"
                @on-cancel="cancel" width="60%">
            <Form ref="addForm" :model="addForm" :rules="ruleCustom" :label-width="80">
                <Row>
                    <Col span="11">
                    <FormItem label="信用证号" prop="lcno">
                        <Input type="text" v-model="addForm.lcno"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="收货人及地址" prop="consignee">
                        <Input type="text" v-model="addForm.consignee"/>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="唛头" prop="marks">
                        <Input type="text" v-model="addForm.marks"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="装运港" prop="shipmentPort">
                        <Input type="text" v-model="addForm.shipmentPort"/>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="目的港" prop="destinationPort">
                        <Input type="text" v-model="addForm.destinationPort"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="运输方式" prop="transportMode">
                        <Input type="text" v-model="addForm.transportMode"/>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="价格条件" prop="priceCondition">
                        <Input type="text" v-model="addForm.priceCondition"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="备注" prop="remark">
                        <Input type="textarea" v-model="addForm.remark"/>
                    </FormItem>
                    </Col>
                </Row>
            </Form>
        </Modal>
    </div>
</template>

<script type="text/ecmascript-6">
    import util from '../../libs/util';

    export default {
        data() {
            //自定义校验规则
            const validateUser = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入购销合同名'));
                }
            };
            const validateAddr = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入地址'));
                }
            };
            const validateid = (rule, value, callback) => {
                if (value === '') {
                    return callback(new Error('年龄不能为空'));
                }
                // 模拟异步验证效果
                setTimeout(() => {
                    if (!Number.isInteger(value)) {
                        callback(new Error('请输入整数'));
                    } else {
                        if (value < 18) {
                            callback(new Error('必须年满18！'));
                        } else {
                            callback();
                        }
                    }
                }, 1000);
            };
            return {
                count: 0,
                gourpId: null,
                customerContract:null,
                tempIndex: 0,
                pageSize: 20,
                pageNo: 1,
                totalPage: 0,
                totalCount: 0,
                contractNo:"",
                columns1: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: '客户名字',
                        key: 'customName'
                    },
                    {
                        title: '合同号',
                        key: 'contractNo'
                    },
                    {
                        title: '货物数/附件数',
                        key: 'nums'
                    },
                    {
                        title: '制单人',
                        width: 90,
                        key: 'inputBy'
                    }/*,
                     {
                     title: '审单人',
                     key: 'checkBy'
                     }*/,
                    {
                        title: '验货员',
                        key: 'inspector'
                    },
                    {
                        title: '签单日期',
                        key: 'signingDate',
                    },
                    {
                        title: '交货期限',
                        key: 'deliveryPeriod',
                    },
                    {
                        title: '船期',
                        key: 'shipTime',
                    },
                    {
                        title: '总金额',
                        width: 80,
                        key: 'totalAmount'
                    },
                    {
                        title: '状态',
                        key: 'state',
                        width: 80,
                        render: (h, params) => {
                            const task_status = parseInt(params.row.state);
                            if (task_status === 0) {
                            	return h('div', [
                                    h('strong', '草稿')
                                ]);

                            }
                            else if (task_status === 1) {
                            	return h('div', [
                                    h('strong', '已上报待报运')
                                ]);

                            }
                            else if (task_status === 2) {
                            	return h('div', [
                                    h('strong', '已报运')
                                ]);

                            }
                            else {
                            	return h('div', [
                                    h('strong', 'unkown')
                                ]);

                            }
                        }
                    }
                ],
                self: this,
                data1: [],
                modal1: false,
                modal2: false,
                addForm: {
                    lcno: '1',
                    contractIds: '',//用于后端打断设计的字段
                    customerContract: '',
                    consignee: 'consignee',
                    marks: 'marks',
                    shipmentPort: 'shipmentPort',
                    destinationPort: 'destinationPort',
                    transportMode: 'sea',
                    priceCondition: 'price',
                    remark: '1',
                    boxNums: '12',
                    state: '0'
                },
                ruleCustom: {
                    //使用自定义校验器进行校验
                    contractName: [
                        {validator: validateUser, trigger: 'blur'}
                    ],
                    id: [
                        {validator: validateid, trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            cancel() {
                this.$Message.info('点击了取消');
            },
            reset(form){
                this.$refs[form].resetFields();
            },
            addExport(){

                if (this.groupId != null && this.groupId != "") {
                    this.modal2 = true;
                } else {
                    this.$Message.warning('请至少选择一项');
                }
            },
            add(){
                this.addForm.contractIds = "";
                this.addForm.customerContract = "";
                var ids = this.groupId;
                var idsStr = '';
                for(var i = 0; i < ids.length;i ++){
                    idsStr += ids[i];
                    if(i < ids.length-1){
                        idsStr += ',';
                    }
                }
                this.addForm.contractIds = idsStr;
                this.customerContract.forEach((id,index)=>{
                    this.addForm.customerContract+=id;
                    if(index+1<this.customerContract.length){
                        this.addForm.customerContract+=" "
                    }
                });
                console.log(this.addForm)
                util.ajax({
                    url: 'export/add',
                    method: 'post',
                    data: this.addForm
                }).then((result) => {
                    if(result.data == "success"){
                        this.getpage(this.pageNo);
                        this.$refs['addForm'].resetFields();
                        this.$Message.success('新增成功');
                        this.modal2 = false;
                    }

                }).catch((e)=>{
                    this.$Message.error(e.response.data);
                });
            },
            //勾选复选框时执行的事件处理方法
            change(e){
                //存储合同id
                this.setGroupId(e);
                // 存储合同编号
                this.setCustomerContract(e);
            },
            setGroupId(e)
            {   //勾选复选框时，将合同对象的id存入数组
                this.groupId = [];
                this.count = e.length;
                for (var i = 0; i < e.length; i++) {
                    this.groupId.push(e[i].contractId);
                }
            },
            setCustomerContract(e)
            {   //勾选复选框时，将合同对象的编号存入数组
                this.customerContract = [];
                this.count = e.length;
                for (var i = 0; i < e.length; i++) {
                    this.customerContract.push(e[i].contractNo);
                }
            },
            handleReset (form) {
                this.$refs[form].resetFields();
            },
            getpage(pageNo){
                util.ajax({
                    url: 'contract/1',//基于RestFul风格传参
                    method: 'get',
                    params: {
                        pageSize:this.pageSize,
                        pageNo:pageNo,
                        contractNo: this.contractNo

                    }
                }).then((result) => {
                    this.data1 = result.data.list;
                    this.totalCount = result.data.totalCount;
                });
            },
            search(){
                this.getpage();
            },
            showPage(){
                this.contractNo="";
                this.getpage()
            }
        },
        mounted: function () {
            this.getpage(1)
        }
    }


</script>
