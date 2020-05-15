<template>
    <!--页面相关的组件代码都放在一个div中-->
    <div class="layout">
        <Row>
            <Col span="24">
                模块名称：<Input placeholder="请输入模块名称" style="width: 200px" />
                <Button type="primary" shape="circle" icon="ios-search">搜索</Button>
            </Col>
        </Row>
        <br>
        <Row>
            <Col span="24">
                <Button type="info" icon="md-add" @click="addModel=true">新建</Button>
                <Button type="success" icon="ios-build" @click="openUpdateModel">修改</Button>
                <Button type="error" icon="ios-trash" @click="deleteModule">删除</Button>
            </Col>
        </Row>
        <br>
        <Row>
            <Col span="24">
                <Table border ref="selection" :columns="columns4" :data="data1" @on-selection-change="change"></Table>
            </Col>
        </Row>
        <br>
        <Row>
            <Page :total="total" :page-size="pageSize" :current="pageNo" align="center" @on-change="getpage" show-total/>
        </Row>

        <Modal
                v-model="addModel"
                title="新增模块"
                @on-ok="addModule"
                @on-cancel="cancel">
            <Form ref="addForm" :model="addForm" :rules="formRules" inline>
                <Row>
                    <Col span="14">
                        <FormItem label="等级" prop="ctype" >
                            <RadioGroup v-model="addForm.ctype">
                                <Radio label="0">一级菜单</Radio>
                                <Radio label="1">二级菜单</Radio>
                                <Radio label="2">按钮</Radio>
                                <Radio label="3">链接</Radio>
                                <Radio label="4">状态</Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>

                </Row>
                <Row>
                    <Col span="11">
                        <FormItem label="父模块" prop="parentId" >
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem label="模块名" prop="name" >
                            <Input type="text" v-model="addForm.name"/>
                        </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                        <FormItem label="层数" prop="layerNum" >
                            <Input type="text" v-model="addForm.layerNum"/>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem label="权限标识" prop="cpermission" >
                            <Input type="text" v-model="addForm.cpermission"/>
                        </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                        <FormItem label="链接" prop="curl" >
                            <Input type="text" v-model="addForm.curl"/>
                        </FormItem>
                    </Col>
                </Row>

                <Row>
                    <Col span="11">
                        <FormItem label="状态" prop="state" >
                            <Radio-group type="button" v-model="addForm.state">
                                <Radio label="1" >启用</Radio>
                                <Radio label="0">停用</Radio>
                            </Radio-group>
                        </FormItem>
                    </Col>
                </Row>


                <Row>
                    <Col span="11">

                        <FormItem label="排序号" prop="orderNo" >
                            <Input type="text"/>
                        </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                        <FormItem label="说明" prop="remark" >
                            <Input type="textarea"/>
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        </Modal>
        <Modal
                v-model="updateModel"
                title="修改模块"
                @on-ok="updateModule"
                @on-cancel="cancel">
            <Form ref="updateForm" :model="updateForm" :rules="formRules" inline>
                <Row>

                    <Col span="14">
                        <FormItem label="等级" prop="ctype" >
                            <RadioGroup v-model="updateForm.ctype">
                                <Radio label="0">主菜单</Radio>
                                <Radio label="1">左侧菜单</Radio>
                                <Radio label="2">按钮</Radio>
                                <Radio label="3">链接</Radio>
                                <Radio label="4">状态</Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>

                </Row>
                <Row>
                    <Col span="11">
                        <FormItem label="父模块" prop="parentId" >
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem label="模块名" prop="name" >
                            <Input type="text" v-model="updateForm.name"/>
                        </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                        <FormItem label="层数" prop="layerNum" >
                            <Input type="text" v-model="updateForm.layerNum"/>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                        <FormItem label="权限标识" prop="cpermission" >
                            <Input type="text" v-model="updateForm.cpermission"/>
                        </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                        <FormItem label="链接" prop="curl" >
                            <Input type="text" v-model="updateForm.curl"/>
                        </FormItem>
                    </Col>
                </Row>

                <Row>
                    <Col span="11">
                        <FormItem label="状态" prop="state" >
                            <Radio-group type="button" v-model="updateForm.state">
                                <Radio :label="1" >启用</Radio>
                                <Radio :label="0">停用</Radio>
                            </Radio-group>
                        </FormItem>
                    </Col>
                </Row>


                <Row>
                    <Col span="11">

                        <FormItem label="排序号" prop="orderNo" >
                            <Input type="text" v-model="updateForm.orderNo"/>
                        </FormItem>
                    </Col>
                    <Col span="2" style="text-align: center"/>
                    <Col span="11">
                        <FormItem label="说明" prop="remark" >
                            <Input type="textarea" v-model="updateForm.remark"/>
                        </FormItem>
                    </Col>
                </Row>

            </Form>
        </Modal>
    </div>

</template>

<script>
    //导入需要使用的模块
    import util from "../../libs/util"

    export default {
        data(){
            return{
                addModel: false,//控制对话框显示和隐藏
                updateModel: false,//控制对话框显示和隐藏
                addForm:{
                    name:'',
                    parentId:0,
                    layerNum:'',
                    cpermission:'',
                    curl:'',
                    ctype:0,
                    state:0,
                    orderNo:0,
                    remark:''

                },
                updateForm:{
                    moduleId:'',//更新需要当前对象的id传入后端
                    name:'',
                    parentId:'',
                    layerNum:0,
                    cpermission:'',
                    curl:'',
                    ctype:0,
                    state:0,
                    orderNo:0,
                    remark:''
                },
                formRules: {
                    name: [
                        {required: true, message: '部门名称不能为空', trigger: 'blur'}
                    ]
                },
                //表格标题
                columns4: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        // 显示给用户看的
                        title: '模块名称',
                        // 代码操作的是key的值
                        key: 'name'
                    },
                    {
                        title: '层数',
                        key: 'layerNum'
                    },
                    {
                        title: '权限标识',
                        key: 'cpermission'
                    },
                    {
                        title: '链接',
                        key: 'curl'
                    },
                    {
                        title: '类型',
                        key: 'ctype',
                    },
                    {
                        title: '状态',
                        ellipsis:'true',
                        key:'state',
                    }
                ],
                data1: [],
                total:0,
                pageSize:5,
                pageNo:1,
                checkedIds:[],
            }
        },
        methods:{
            change(objs){//每行选中数据对象数组
                this.checkedIds = [];//还原
                for (var i = 0; i < objs.length; i++) {
                    this.checkedIds.push(objs[i].moduleId)
                }
                if (objs.length == 1) {//选中一项
                    this.updateForm = objs[0];//把选中项数据赋值给更新表单关联属性用于显示数据
                }

            },
            openUpdateModel(){
                if(this.checkedIds.length == 1){
                    this.updateModel=true;
                }else{
                    this.$Message.error("更新必须选中一项");
                }
            },
            addModule(){
                this.$refs['addForm'].validate((valid)=>{
                    if(valid){

                        util.ajax({
                            url:"module/addmodule",
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
            deleteModule(){
                if(this.checkedIds.length > 0){
                    util.ajax({
                        url: "module/deletemodule",
                        method: "delete",
                        data: this.checkedIds,
                    }).then((resp) => {//请求成功返回操作
                        if(resp.data == "1"){

                            this.$Message.success("删除成功");
                            this.getpage(this.pageNo);

                        }
                    });
                }else{
                    this.$Message.error("至少选中一项");
                }
            },
            updateModule(){
                this.$refs['updateForm'].validate((valid)=>{
                    if (valid) {
                        util.ajax({
                            url: "module/updatemodule",
                            method: "put",
                            data: this.updateForm,
                        }).then((resp) => {//请求成功返回操作
                            if(resp.data == "1"){
                                this.$Message.success("更新成功");
                                this.getpage(this.pageNo);
                            }
                        });
                    }
                })
            },
            getpage(pageNo){
                util.ajax({
                    url:"module/getpage",
                    method:"get",
                    params:{
                        pageSize:this.pageSize,
                        pageNo:pageNo
                    },
                }).then((resp)=>{//请求成功返回操作
                    console.log(resp);
                    this.data1=resp.data.list;
                    this.total=resp.data.totalCount;
                });
            },
            cancel () {
                this.$Message.info('Clicked cancel');
            },
            handleSelectAll (status) {
                this.$refs.selection.selectAll(status);
            }
        },
        mounted(){
            this.getpage(1);
        }

    }
</script>

<style scoped>

</style>
