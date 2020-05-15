<template>
    <div class="animated fadeIn">
        <Card>
            <div>
                <ul>
                    <li>
                        <Button type="primary" icon="plus-round" @click="addContractProduct()">新建</Button>
                        <Button type="success" icon="wrench" @click="edit()">修改</Button>
                        <Button type="error" icon="trash-a" @click="remove()">删除</Button>
                        <Button type="info" icon="arrow-left-c" @click="()=>{this.$router.go(-1)}">返回</Button>
                    </li>
                    <li>
                        <div style="padding: 10px 0;">
                            <Table :columns="columns1" :data="data1" @on-selection-change="s=>{change(s)}"></Table>
                            <!--<Table border :columns="columns1" :data="data1" :height="400" @on-selection-change="s=>{change(s)}" @on-row-dblclick="s=>{dblclick(s)}"></Table>-->
                        </div>
                    </li>
                    <li>
                        <div style="text-align: right;">
                            <Page :total="totalCount" :page-size="pageSize" :current="pageNo" show-total @on-change="getPage"
                                  align="center"></Page>
                        </div>
                    </li>
                </ul>
            </div>


            <Modal
                    v-model="modal1"
                    title="编辑购销合同货物-附件"
                    width="60%"
                    @on-ok="update"
                    @on-cancel="cancel"

            >
                <Form ref="updateForm" :model="updateForm" :rules="ruleCustom" :label-width="80">
                    <Row>
                        <Col span="11">
                            <FormItem label="生产厂家" prop="factory.id">
                                <Select v-model="updateForm.factory.factoryId" filterable @on-change="o=>changeFactory(this.updateForm,o)" label-in-value="true">
                                    <Option v-for="item in factoryList" :value="item.factoryId" :key="item.factoryId" >{{ item.factoryName }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                        <Col span="11">
                            <FormItem label="货号" prop="productNo">
                                <Input type="text" v-model="updateForm.productNo"/>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="11">
                            <FormItem label="数量" prop="cnumber">
                                <Input type="text" v-model="updateForm.cnumber"/>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                        <Col span="11">
                            <FormItem label="包装单位" prop="packingUnit">
                                <Radio-group v-model="updateForm.packingUnit" type="button">
                                    <Radio label="PCS" >只</Radio>
                                    <Radio label="SETS">套</Radio>
                                </Radio-group>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="11">
                            <FormItem label="单价" prop="price">
                                <Input type="text" v-model="updateForm.price"/>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                        <Col span="11">
                            <FormItem label="排序号" prop="orderNo">
                                <Input type="text" v-model="updateForm.orderNo"/>
                            </FormItem>
                        </Col>
                    </Row>

                    <Row>
                        <Col span="11">
                            <FormItem label="货物描述" prop="productDesc">
                                <Input type="textarea" v-model="updateForm.productDesc"/>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                        <Col span="11">
                            <FormItem label="要求" prop="productRequest">
                                <Input type="textarea" v-model="updateForm.productRequest"/>
                            </FormItem>
                        </Col>
                    </Row>

                </Form>
            </Modal>
            <Modal
                    v-model="modal2"
                    title="添加购销合同货物-附件"
                    width="60%"
                    @on-ok="add"
                    @on-cancel="cancel"

            >
                <Form ref="addForm" :model="addForm" :rules="ruleCustom" :label-width="80">
                    <Row>
                        <Col span="11">
                            <FormItem label="生产厂家" prop="factory.id">
                                <Select v-model="addForm.factory.factoryId" filterable @on-change="o=>changeFactory(this.addForm,o)" label-in-value="true">
                                    <Option v-for="item in factoryList" :value="item.factoryId" :key="item.factoryId" >{{ item.factoryName }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                        <Col span="11">
                            <FormItem label="货号" prop="productNo">
                                <Input type="text" v-model="addForm.productNo"/>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="11">
                            <FormItem label="数量" prop="cnumber">
                                <Input type="text" v-model="addForm.cnumber"/>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                        <Col span="11">
                            <FormItem label="包装单位" prop="packingUnit">
                                <Radio-group v-model="addForm.packingUnit" type="button">
                                    <Radio label="PCS" >只</Radio>
                                    <Radio label="SETS">套</Radio>
                                </Radio-group>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="11">
                            <FormItem label="装率" prop="loadingRate">
                                <Input type="text" v-model="addForm.loadingRate"/>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                        <Col span="11">
                            <FormItem label="箱数" prop="boxNum">
                                <Input type="text" v-model="addForm.boxNum"/>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="11">
                            <FormItem label="单价" prop="price">
                                <Input type="text" v-model="addForm.price"/>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                        <Col span="11">
                            <FormItem label="排序号" prop="orderNo">
                                <Input type="text" v-model="addForm.orderNo"/>
                            </FormItem>
                        </Col>
                    </Row>

                    <Row>
                        <Col span="11">
                            <FormItem label="货物描述" prop="productDesc">
                                <Input type="textarea" v-model="addForm.productDesc"/>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                        <Col span="11">
                            <FormItem label="要求" prop="productRequest">
                                <Input type="textarea" v-model="addForm.productRequest"/>
                            </FormItem>
                        </Col>
                    </Row>
                </Form>
            </Modal>
        </Card>
    </div>
</template>

<script type="text/ecmascript-6">
    import util from '../../libs/util';

    export default {
        data() {
            const validateUser = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入购销合同货物-附件名'));
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

            };
            return {

                contractProductId:null,
                count: 0,
                groupId: null,
                tempIndex: 0,
                pageSize: 20,
                pageNo: 1,
                totalPage: 0,
                totalCount: 0,
                columns1: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: '厂家',
                        key: 'factoryName'
                    },
                    {
                        title: '货号',
                        key: 'productNo'
                    },
                    {
                        title: '包装单位',
                        key: 'packingUnit'
                    },
                    {
                        title: '数量',
                        key: 'cnumber'
                    },
                    {
                        title: '单价',
                        key: 'price'
                    },
                    {
                        title: '总金额',
                        key: 'amount'
                    }
                ],
                self: this,
                data1: [],
                factoryList:[],
                modal1: false,
                modal2: false,
                updateForm: {
                    factory: {
                        factoryId:""
                    },
                    contractProduct:{
                        contractProductId:""
                    },
                    factoryName:"",
                    productNo:"",
                    productImage:"",
                    productDesc:"",
                    packingUnit:"",
                    cnumber:0,
                    outNumber:"",
                    finished:"",
                    productRequest:"",
                    price:0,
                    amount:0,
                    orderNo:""
                },
                addForm: {
                    factory: {
                        factoryId:""
                    },
                    contractProduct:{
                        contractProductId:""
                    },
                    factoryName:"",
                    productNo:"",
                    productImage:"",
                    productDesc:"",
                    packingUnit:"",
                    cnumber:0,
                    outNumber:"",
                    finished:"",
                    productRequest:"",
                    price:0,
                    amount:0,
                    orderNo:""
                },
                ruleCustom: {
                    extCproductName: [
                        {validator: validateUser, trigger: 'blur'}
                    ],
                    id: [
                        {validator: validateid, trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            changeFactory(form,option){
                form.factoryName = option.label;
            },
            reset(form){
                this.$refs[form].resetFields();
            },
            addContractProduct(){
                this.modal2 = true;
            },
            add(){
                this.$refs['addForm'].validate((valid) => {
                    if (valid) {
                        this.addForm.contractProduct.contractProductId = this.contractProductId;
                        util.ajax({
                            url: 'extCproduct/add',
                            method: 'post',
                            data: this.addForm
                        }).then((result) => {
                            if(result.data=="1"){
                                //   this.data1.push(result.data);
                                this.getPage(this.pageNo);
                                this.$refs['addForm'].resetFields();
                                this.$Message.success('增加成功!');
                                this.modal2 = false;
                            }
                        });
                    }  else {
                        this.$Message.error("表单验证失败");

                    }
                })
            },
            change(e){
                if (e.length == 1) {
                    this.updateForm = e[0];
                    //空值处理一下
                    if(this.updateForm.factory==null)
                    {
                        this.updateForm.factory = {id:null};
                    }
                    //将importNum 转成字符串
//                    this.updateForm.importNum = this.updateForm.importNum + "";
                }
                this.setGroupId(e);
            },
            setGroupId(e)
            {
                this.groupId = [];
                this.count = e.length;
                for (var i = 0; i < e.length; i++) {
                    this.groupId.push(e[i].extCproductId);
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
            remove () {
                if (this.groupId != null && this.groupId != "") {
                    util.ajax({
                        url: 'extCproduct/delete/'+this.contractProductId,
                        method: 'delete',
                        data: this.groupId
                    }).then((result) => {
                        if (result.data == '1') {
                            this.$Message.success('删除成功!');
                            this.getPage(this.pageNo);
                        }
                    });
                } else {
                    this.$Message.warning('请至少选择一项');
                }

            },
            update () {

                util.ajax({
                    url: 'extCproduct/update',
                    method: 'put',
                    data: this.updateForm
                }).then((result) => {
                    if(result.data=="1"){
                        //刷新页面数据
                        alert(1);
                        this.getPage(this.pageNo);
                        this.$Message.success('更新成功!');
                    }
                });
            },
            handleReset (form) {
                this.$refs[form].resetFields();
            },
            getPage(pageNo){
                const pageSize = this.pageSize;
                const contractProductId = this.contractProductId;
                util.ajax({
                    url: 'extCproduct/getPage',
                    method: 'get',
                    params: {pageSize,pageNo,contractProductId}
                }).then((result) => {
                    this.data1 = result.data.list;
                    this.totalCount = result.data.totalCount;
                });
            }
        },
        mounted: function () {
            //接受url参数
            this.contractProductId = this.$route.params.id;

            this.getPage(1);

            //初始化部门列表
            util.ajax({
                url:'factory/all',
                method:'get',
                params:{ctype:"附件"}
            }).then((result)=>{
                this.factoryList = result.data;
            })
        }
    }


</script>
<style scoped="scoped">
    li{list-style-type: none}
</style>
