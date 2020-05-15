<template>
    <div class="animated fadeIn">
        <Card>
            <Row>
                <Col span="7">角色：
                    <Input placeholder="请输入..." style="width:200px"/>
                </Col>
                <Col span="8">
                    <Button type="primary" shape="circle" icon="ios-search">搜索</Button>
                </Col>
            </Row>
            <br>
            <Row>
                <Button type="primary" icon="plus-round" @click="addModal=true">新建</Button>
                <Button type="success" icon="wrench" @click="openUpdateModal" >修改</Button>
                <Button type="error" icon="trash-a"@click="deleteRole">删除</Button>
            </Row>
            <br>
            <Row>
                <Table border :columns="columns1" :data="data1"@on-selection-change="change"></Table>
            </Row>
            <br>
            <Row>
                <Page :total="totalCount" :page-size="pageSize" :current="pageNo" align="center" show-total @on-change="getpage"></Page>
            </Row>

            <Modal
                    v-model="addModal"
                    title="添加角色"
                    @on-ok="addRole"
                    @on-cancel="cancel"
                    width="60%">
                <Form ref="addForm" :model="addForm"  :rules="formCustom" :label-width="80">
                    <Row>
                        <Col span="11">
                            <FormItem label="角色名字" prop="name">
                                <Input type="text" v-model="addForm.name"/>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                        <Col span="11">
                            <FormItem label="备注" prop="remark">
                                <Input type="text" v-model="addForm.remark"/>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="11">
                            <FormItem label="排序号" prop="orderNo">
                                <Input type="text" v-model="addForm.orderNo"/>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                    </Row>
                    <Row>
                        <Col span="11">
                            <FormItem label="模块" style="width:200px">
                                <ul id="addtree" class="ztree"></ul>
                            </FormItem>
                        </Col>
                    </Row>

                </Form>
            </Modal>

            <Modal
                    v-model="updateModal"
                    title="编辑角色"
                    @on-ok="update_ok"
                    @on-cancel="cancel"
                    width="75%">
                <Form ref="updateForm"  :label-width="80">

                    <Row>
                        <Col span="11">
                            <FormItem label="角色名字" prop="name">
                                <Input type="text"v-model="updateForm.name"/>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                        <Col span="11">
                            <FormItem label="备注" prop="remark">
                                <Input type="text"v-model="updateForm.remark"/>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="11">
                            <FormItem label="排序号" prop="orderNo">
                                <Input type="text" v-model="updateForm.orderNo"/>
                            </FormItem>
                        </Col>
                        <Col span="2" style="text-align: center"/>
                    </Row>
                    <Row>
                        <Col span="11">
                            <FormItem label="模块" style="width:200px">
                                <ul id="updatetree" class="ztree" style="background-color: #3ab477;width: 300px"></ul>
                            </FormItem>
                        </Col>
                    </Row>

                </Form>
            </Modal>
        </Card>
    </div>
</template>

<script>
    import '../../static/ztree/js/jquery-1.4.4.min'
    import '../../static/ztree/js/jquery.ztree.core.min'
    import '../../static/ztree/js/jquery.ztree.excheck'
    import util from '../../libs/util';
    export default {
        data() {
            return {
                setting : {
                    check: {
                        enable: true
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    }
                },
                zNodes:[],//访问数据
                zTreeObj:{},//ztree的值
                updateModal: false,
                addModal: false,
                loading: true,
                checkedIds:[],
                pageSize: 5,
                pageNo: 1,
                totalCount: 0,
                columns1: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: '角色名字',
                        key: 'name'
                    },
                    {
                        title: '备注',
                        key: 'remark'
                    },
                    {
                        title: '排序号',
                        key: 'orderNo'
                    }
                ],
                data1: [],
                updateForm: {
                    name: "",
                    remark: "",
                    orderNo: ""
                },
                //新建窗口数据绑定对象
                addForm: {
                    name: "",
                    remark: "",
                    orderNo: "1"
                },
                formCustom: {
                    name: [
                        {required: true, message: '角色名字不能为空', trigger: 'blur'}
                    ]
                    ,
                    remark: [
                        {required: true, message: '备注不能为空', trigger: 'blur'}
                    ]
                },

            }
        },
        methods: {
            ok () {
                this.$Message.info('点击了确定');
            },
            cancel () {
                this.$Message.info('点击了取消');
            },
            getpage(pageNo){
                this.pageNo = pageNo;
                util.ajax({
                    url:"role/getPage",
                    method:"get",
                    params:{

                        pageSize:this.pageSize,
                        pageNo:pageNo
                    }
                }).then((resp)=>{
                        this.data1 = resp.data.list;
                        this.totalCount=resp.data.totalCount;
                    }
                )
            },
            addRole(){

                this.$refs['addForm'].validate((valid)=>{
                    if(valid){
                        //角色名称，备注，排序号/addForm/角色属性，模板id/
                        //第1种：基于后端改造，传入后端属性有addForm，一个字符串（包含所有模板id）
                        //字符串的样式为node1，node2,node3
                       var treeNodes = this.zTreeObj.getCheckedNodes(true);//获取所有选中的
                        var ids="";
                        for (var i=0;i<treeNodes.length;i++){
                            if (ids!=""){
                                ids+=",";
                            }
                            ids+=treeNodes[i].id;
                        }
                        util.ajax({
                            url:"role/addRole/"+ids,//restFul风格
                            method:"post",
                            data:this.addForm,
                        }).then((resp) => {
                            if (resp.data=='1'){
                                this.getpage(this.pageNo);
                                this.$Message.success("添加成功");

                            } else {
                                this.$Message.error("添加失败");
                            }
                        })
                    }else{
                        this.$Message.warning("验证失败")
                    }
                });
            },
            //点击新增对话框确定按钮事件
            update_ok: function () {
                var treeNodes = this.zTreeObj.getCheckedNodes(true);
                var ids="";
                for (var i=0;i<treeNodes.length;i++){

                    if (ids!=""){
                        ids+=",";
                    }
                    ids+=treeNodes[i].id;
                }
                util.ajax({
                    url: "role/update/"+ids,
                    method: "put",
                    data: this.updateForm

                }).then((resp) => {

                    if (resp.data == "success") {
                        this.$Message.success("更新成功")
                        this.getpage(this.pageNo);
                    }

                })
            },
            openUpdateModal(){
                if (this.checkedIds.length == 1) {//只选中一项
                    this.updateModal = true;
                    //初始化显示当前角色的e4 ztree
                     //通过当前角色id获取模块ztree
                    var roleId =this.checkedIds[0];
                    util.ajax({
                        url: "module/getZtreeNodes/"+roleId,
                        method: "get"
                    }).then((resp) => {
                        console.log(resp.data);
                        this.zNodes = resp.data;
                        //初始化加载显示
                        this.zTreeObj = $.fn.zTree.init($("#updatetree"), this.setting, this.zNodes);
                        //在其他任意地方都可以通过
                        // this.zTreeObject.getCheckedNodes(true)//获取所有选中的节点
                    })
                } else {
                    this.$Message.error("更新必须选中一项");
                }
            },
            change(objs){//每行选中数据对象数组
                this.checkedIds = [];//还原
                for (var i = 0; i < objs.length; i++) {
                    this.checkedIds.push(objs[i].roleId)
                }
                if (objs.length == 1) {//选中一项
                    this.updateForm = objs[0];//把选中项数据赋值给更新表单关联属性用于显示数据
                }

            },
            //点击删除按钮事件方法
            deleteRole: function () {
                if (this.checkedIds.length >= 1) {//至少选中一项
                    util.ajax({
                        url: "role/delete",
                        method: "delete",
                        data: this.checkedIds
                    }).then((resp) => {
                        if (resp.data == "success") {
                            this.getpage(this.pageNo);
                            this.$Message.success("删除成功")
                        }
                    })
                }
            },


        },

        mounted: function () {
            this.getpage(1);
            //加载所有模块内容
            util.ajax({
                url: 'module/getZtreeNodes',// 因为在utiljs中配置了前缀所有指定地址时可以省略前缀
                method: 'get',
            }).then((resp) => {
                this.zNodes=resp.data;
                //初始化显示ztree
                this.zTreeObj = $.fn.zTree.init($("#addtree"), this.setting, this.zNodes);
            });
        }
    }

</script>
<style scoped>
    @import "../../static/ztree/css/zTreeStyle/zTreeStyle.css";
</style>
