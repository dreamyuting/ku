<template>

    <div class="animated fadeIn">
        <Card>
        <Row >
        <Col span="8">合同号：
            <Input v-model="contractNo" placeholder="请输入..." style="width:200px"/>
        </Col>
            <Col span="8"><Button type="primary" shape="circle" icon="ios-search" @click="search()">搜索</Button></Col>
        </Row>
        <br>
        <Row >
            <Button type="primary" icon="plus-round" @click="addContract()">新建</Button>
            <Button type="success" icon="wrench" @click="edit()">修改</Button>
            <Button type="error" icon="trash-a" @click="removeContract">删除</Button>
            <Button type="warning"  @click="submit()">提交</Button>
            <Button type="info"  @click="cancel()">取消</Button>

        </Row>
        <br>
        <Row>
            <Table :columns="columns1" :data="data1" @on-selection-change="change"></Table>
        </Row>
        <br>
        <Row>
            <Page :total="totalCount" :page-size="pageSize" :current="pageNo" @on-change="getpage" align="center" show-total></Page>
        </Row>

        <Modal
                v-model="modal1"
                title="编辑购销合同"
                width="60%"
                @on-ok="update"
                @on-cancel="cancel"
                :loading="loading">
            <Form ref="updateForm" :model="updateForm" :rules="ruleCustom" :label-width="80">
                <Row>
                    <Col span="11">
                    <FormItem label="客户名称" prop="customName">
                        <Input type="text" v-model="updateForm.customName"/>
                    </FormItem>
                    </Col>

                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="打印板式" prop="printStyle">
                        <RadioGroup v-model="updateForm.printStyle" type="button">
                            <Radio label="2">两款</Radio>
                            <Radio label="1">一款</Radio>
                        </RadioGroup>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="合同号" prop="contractNo">
                        <Input type="text" v-model="updateForm.contractNo"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="收购方" prop="offeror">
                        <Input type="text" v-model="updateForm.offeror"/>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="制单人" prop="inputBy">
                        <Input type="text" v-model="updateForm.inputBy"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="审单人" prop="checkBy">
                        <Input type="text" v-model="updateForm.checkBy"/>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="验货员" prop="inspector">
                        <Input type="text" v-model="updateForm.inspector"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="签单日期" prop="signingDate">
                        <DatePicker type="date" v-model="updateForm.signingDate" format="yyyy-MM-dd"
                                    placeholder="Select date" style="width: 200px"></DatePicker>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="重要程度" prop="importNum">
                        <RadioGroup v-model="updateForm.importNum" type="button">
                            <Radio :label=3>★★★</Radio>
                            <Radio :label=2>★★</Radio>
                            <Radio :label=1>★</Radio>
                        </RadioGroup>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="船期" prop="shipTime">
                        <DatePicker type="date" v-model="updateForm.shipTime" format="yyyy-MM-dd"
                                    placeholder="Select date" style="width: 200px"></DatePicker>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="贸易条款" prop="tradeTerms">
                        <Input type="text" v-model="updateForm.tradeTerms"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="交货日期" prop="deliveryPeriod">
                        <DatePicker type="date" v-model="updateForm.deliveryPeriod" format="yyyy-MM-dd"
                                    placeholder="Select date" style="width: 200px"></DatePicker>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="说明" prop="remark">
                        <Input type="textarea" v-model="updateForm.remark"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="要求" prop="crequest">
                        <Input type="textarea" v-model="updateForm.crequest"/>
                    </FormItem>
                    </Col>
                </Row>

            </Form>
        </Modal>
        <Modal
                v-model="modal2"
                title="添加购销合同"
                @on-ok="add"
                @on-cancel="cancel" width="60%">
            <Form ref="addForm" :model="addForm" :rules="ruleCustom" :label-width="80">
                <Row>
                    <Col span="11">
                    <FormItem label="客户名称" prop="customName">
                        <Input type="text" v-model="addForm.customName"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="打印板式" prop="printStyle">
                        <RadioGroup v-model="addForm.printStyle" type="button">
                            <Radio label="2">两款</Radio>
                            <Radio label="1">一款</Radio>
                        </RadioGroup>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="合同号" prop="contractNo">
                        <Input type="text" v-model="addForm.contractNo"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="收购方" prop="offeror">
                        <Input type="text" v-model="addForm.offeror"/>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="制单人" prop="inputBy">
                        <Input type="text" v-model="addForm.inputBy"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="审单人" prop="checkBy">
                        <Input type="text" v-model="addForm.checkBy"/>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="验货员" prop="inspector">
                        <Input type="text" v-model="addForm.inspector"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="签单日期" prop="signingDate">
                        <DatePicker type="date" v-model="addForm.signingDate" format="yyyy-MM-dd"
                                    placeholder="Select date" style="width: 200px"></DatePicker>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="重要程度" prop="importNum">
                        <RadioGroup v-model="addForm.importNum" type="button">
                            <Radio label="3">★★★</Radio>
                            <Radio label="2">★★</Radio>
                            <Radio label="1">★</Radio>
                        </RadioGroup>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="船期" prop="shipTime">
                        <DatePicker type="date" v-model="addForm.shipTime" format="yyyy-MM-dd" placeholder="Select date"
                                    style="width: 200px"></DatePicker>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="贸易条款" prop="tradeTerms">
                        <Input type="text" v-model="addForm.tradeTerms"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="交货日期" prop="deliveryPeriod">
                        <DatePicker type="date" v-model="addForm.deliveryPeriod" format="yyyy-MM-dd"
                                    placeholder="Select date" style="width: 200px"></DatePicker>
                    </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                    <FormItem label="说明" prop="remark">
                        <Input type="textarea" v-model="addForm.remark"/>
                    </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                    <FormItem label="要求" prop="crequest">
                        <Input type="textarea" v-model="addForm.crequest"/>
                    </FormItem>
                    </Col>
                </Row>
            </Form>
        </Modal>
        </Card>
    </div>
</template>

<script>
    import util from '../../libs/util';

    export default {
        data() {
            return {
                loading: true,
                count: 0,
                groupId: null,
                states:[],
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
                        width: 100,
                        key: 'customName'
                    },
                    {
                        title: '合同号',
                        key: 'contractNo'
                    },
                    {
                        title: '货物/附件数',
                        width: 110,
                        key: 'nums'
                    },
                    {
                        title: '制单人',
                        key: 'inputBy'
                    },
                    {
                        title: '验货员',
                        key: 'inspector'
                    },
                    {
                        title: '签单日期',
                        width: 120,
                        key: 'signingDate',
                    },
                    {
                        title: '交货期限',
                        width: 120,
                        key: 'deliveryPeriod',
                    },
                    {
                        title: '船期',
                        width: 120,
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
                                    h('strong', '未知')
                                ]);

                            }
                        }
                    },
                    {
                        title: '操作',
                        key: 'action',
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.$router.push({path:'/cargo/contractProduct/'+(this.data1[params.index].contractId) })
                                        }
                                    }
                                }, '货物')
                            ]);
                        }
                    }
                ],
                self: this,
                data1: [],
                modal1: false,
                modal2: false,
                updateForm: {
                    contractId: '',
                    offeror: '',
                    contractNo: '',
                    signingDate: '',
                    inputBy: '',
                    checkBy: '',
                    inspector: '',
                    totalAmount:0,
                    crequest: '',
                    customName: '',
                    shipTime: '',
                    importNum: '',
                    deliveryPeriod: '',//交货日期
                    oldState: '',
                    outState: '',
                    tradeTerms: '',
                    printStyle: '',
                    remark: '',
                    state: ''
                },
                addForm: {
                    offeror: '1',
                    contractNo: '1',
                    signingDate: '2018-12-12',
                    inputBy: 'cgx',
                    checkBy: 'cgx',
                    inspector: 'cgx',
                    totalAmount: '0',
                    crequest: 'request',
                    customName: '客户名字',
                    shipTime: '2018-12-12',
                    importNum: '1',
                    deliveryPeriod: '2018-12-12',//交货日期
                    oldState: '',
                    outState: '',
                    tradeTerms: 'fob',
                    printStyle: '2',
                    remark: 'remark',
                    state: '0'
                },
                ruleCustom: {
                    customName: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    contractNo: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    offeror: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    inputBy: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    checkBy: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],
                    inspector: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],

                    tradeTerms: [
                        {required: true, message: '不能为空', trigger: 'blur'}
                    ],

                }
            }
        },
        methods: {
            reset(form){
                this.$refs[form].resetFields();
            },
            addContract(){
                this.modal2 = true;
            },
            add(){
                this.$refs['addForm'].validate((valid) => {
                        if (valid) {
                            const contract = this.addForm;
                            util.ajax({
                                url: 'contract/add',
                                method: 'post',
                                data: contract
                            }).then((resp) => {
                                if(resp.data == "1"){
                                    this.getpage(this.pageNo);
                                    this.$refs['addForm'].resetFields();//清空表单控件中的值
                                    this.$Message.success('合同添加成功');
                                    this.modal2 = false;
                                }
                            });
                        }  else {
                            this.$Message.error("表单验证失败");
                            setTimeout(() => {
                                this.loading = false;
                                this.$nextTick(() => {
                                    this.loading = true;
                                });
                            }, 1000);
                        }
                })
            },
            show (index) {
                this.$Modal.info({
                    title: '购销合同信息',
                    content: `姓名：${this.data1[index].contractName}`
                })
            },
            change(e){
                this.setGroupId(e);
                if (e.length == 1) {
                    this.updateForm = e[0];
                }
            },
            setGroupId(e) {
                this.groupId = [];//存储删除时发生后端的id
                this.states = [];//存储点击提交或取消按钮时发生后端的参数
                this.count = e.length;
                for (var i = 0; i < e.length; i++) {
                    this.groupId.push(e[i].contractId);
                    this.states.push(e[i].state);//将当前选中项的状态存入状态数组中
                }
            },
            edit () {
                if (this.count != 1) {
                    this.modal1 = false;
                    this.$Message.warning('请至少并只能选择一项');
                }
                else {
                    this.modal1 = true;
                }
            },
            removeContract () {
                if (this.groupId != null && this.groupId != "") {
                    util.ajax({
                        url: 'contract/deleteContract',
                        method: 'delete',
                        data: this.groupId
                    }).then((resp) => {
                        if(resp.data == "success"){
                            this.getpage(this.pageNo);
                            this.$Message.success('合同删除成功');
                            this.modal1 = false;
                        }
                    }).catch(resp=>{
                        this.$Message.error(resp.response.data);
                    });
                } else {
                    this.$Message.warning('请至少选择一项');
                }

            },
            update () {
                this.$refs['updateForm'].validate((valid) => {
                        if (valid) {
                            util.ajax({
                                url: 'contract/update',
                                method: 'put',
                                data: this.updateForm
                            }).then((resp) => {
                                if(resp.data == "success"){
                                    this.getpage(this.pageNo);
                                    this.$Message.success('合同修改成功');
                                    this.modal1 = false;
                                }
                            });
                        }
                        else {
                            this.$Message.error("表单验证失败");
                            setTimeout(() => {
                                this.loading = false;
                                this.$nextTick(() => {
                                    this.loading = true;
                                });
                            }, 1000);
                        }
                })
            },
            handleReset (form) {
                this.$refs[form].resetFields();
            },
            getpage(pageNo){
                util.ajax({
                    url: 'contract/getPage',
                    method: 'get',
                    params: {
                        pageSize:this.pageSize,
                        pageNo:pageNo
                    }
                }).then((result) => {
                    this.data1 = result.data.list;
                    this.totalCount = result.data.totalCount;
                });
            },
            submit(){

                if (this.groupId != null && this.groupId != "") {
                    //只能提交状态为0（草稿）状态的合同
                    for(let i = 0 ;i < this.states.length;i++)
                    {
                        if(this.states[i] != 0)
                        {
                            this.$Message.error('只能提交草稿状态的购销合同！');
                            return;//结束方法
                        }
                    }

                    util.ajax({
                        url: 'contract/submit',
                        method: 'put',
                        data: this.groupId
                    }).then((result) => {

                        if (result.data == 'success') {
                            this.$Message.success('提交成功');
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
                        url: 'contract/cancel',
                        method: 'put',
                        data: this.groupId
                    }).then((result) => {
                        if (result.data == 'success') {
                            this.$Message.success('取消成功');
                            this.getpage(this.pageNo);
                        }
                    });
                } else {
                    this.$Message.warning('请至少选择一项');
                }
            },
            search(){
                this.getpage();
            }
        },
        mounted: function () {
            this.getpage(1)
        }
    }


</script>
