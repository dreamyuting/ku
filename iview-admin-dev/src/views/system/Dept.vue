<template>
    <div class="layout">
        <Row>
            <Col span="24">
                部门名称：<Input placeholder="请输入部门名称" style="width: 200px"/>
                <Button type="primary" shape="circle" icon="ios-search">搜索</Button>
            </Col>
        </Row>
        <br>
        <Row>
            <Col span="24">
                <Button type="info" icon="md-add" @click="addDeptModel=true">新建</Button>
                <Button type="success" icon="ios-build" @click="openUpdateModal">修改</Button>
                <Button type="error" icon="ios-trash" @click="deleteDept">删除</Button>

            </Col>
        </Row>

        <br>
        <Row style="margin-top: 10px">
            <Col span="24">
                <!--在组件中引用下面data中的columns4作为标题，data1作为数据行-->
                <Table border ref="selection" :columns="columns4" :data="data1" @on-selection-change="change"></Table>
            </Col>
        </Row>
        <br>
        <row style="margin-top: 10px">
            <Col span="24">
                <Page :total="total" :page-size="pageSize" :current="current" show-total align="center"
                      @on-change="getPageByNo"/>
            </Col>
        </row>
        <!--新建部门对话框-->
        <Modal
                v-model="addDeptModel"
                title="新增部门"
                @on-ok="addDept"
                @on-cancel="cancel"
                :loading="loading">
            <Form ref="addForm" :model="addForm" :rules="ruleValidate" :label-width="100">
                <FormItem prop="deptName" label="部门名称：">
                    <Input type="text" v-model="addForm.deptName" placeholder="请输入部门名称" style="width: 200px"/>
                </FormItem>
            </Form>
        </Modal>
        <Modal
                v-model="updateModal"
                title="更新部门"
                @on-ok="update_ok"
                @on-cancel="update_cancel">
            <!--ref:用于程序中获取表单的标识名-->
            <!--:model：数据绑定-->
            <!--:rules：指定前端校验规则-->
            <Form ref="updateModal" :model="updateModal" :label-width="80">
                <!--label:表单项文本，也就是控件前面的提示文本-->
                <!--porp：指定校验标识名，是必须和:rules指点的校验数组中的标识名一致-->
                <FormItem label="部门名称">
                    <!--v-model:指定当前控件和表单哪个对象属性进行绑定-->
                    <Input v-model="updateForm.deptName" placeholder="请输入部门名称"></Input>
                </FormItem>
                <FormItem label="状态">
                    <RadioGroup v-model="updateForm.state">
                        <!--:label，加载时默认选中-->
                        <Radio :label="0">可用</Radio>
                        <Radio :label="1">不可用</Radio>
                    </RadioGroup>
                </FormItem>
            </Form>
        </Modal>


    </div>
</template>

<script>
    import util from "../../libs/util";

    export default {
        data() {

            return {
                total: 50,
                pageSize: 5,
                current: 1,
                //指定表格标题
                addDeptModel: false,
                updateModal: false,
                addForm: {
                    deptName: '',
                    state: 0
                },

                updateForm: {
                    deptName: '',
                    state: 0
                },

                //定义表单验证规则
                ruleValidate: {
                    deptName: [
                        {
                            required: true,
                            message: '部门名称不能为空',
                            trigger: 'blur'
                        }
                    ]
                },
                columns4: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        // 显示给用户看的
                        title: '部门名称',
                        // 代码操作的是key的值
                        key: 'deptName'
                    },
                    {
                        title: '状态',
                        key: 'state'
                    },
                ],
                data1: [],
                selectedIds: []//存储选中项的id


            }

        },
        methods: {
            getPageByNo(pageNo) {
                //把事件返回页码赋值给上面的属性
                this.pageNo = pageNo;
                util.ajax({
                    // url: 'http://localhost:8081/'
                    url: "dept/getPage",// 因为在utiljs中配置了前缀所有指定地址时可以省略前缀
                    method: "get",
                    params: {
                        pageSize: this.pageSize,
                        pageNo: pageNo
                    }
                }).then((resp) => {
                    console.log(resp);
                    //请求成功后端返回的数据：resp.data
                    //将分页条显示的总条数动态
                    this.total = resp.data.totalCount;
                    //将后端返回的分页对象中的数据集合赋值给表格的数据属性
                    this.data1 = resp.data.list;
                })
            },
            addDept() {
                //进行验证操作
                this.$refs['addForm'].validate((valid) => {
                    if (valid) {//判断验证结果
                        //请求后端新增数据
                        util.ajax({
                            url: "dept/addDept",
                            method: "post",
                            data: this.addForm//处理get请求使用params设置参数，其他请求都是data设置请求参数
                        }).then(resp => {
                            //成功
                            if (resp.data == '1') {
                                this.$Message.success("新增成功");
                                //新增后必须重新获取最新数据
                                this.getPageByNo(this.pageNo);
                                //优雅的关闭
                                setTimeout(() => {
                                    this.addDeptModel = false;
                                }, 2000);
                            }
                        })
                    } else {
                        this.$Message.error("验证失败");
                    }
                })
            },
            openUpdateModal: function () {
                if (this.selectedIds.length == 1) {//只选中一项
                    this.updateModal = true;//打开对话框
                } else {
                    this.$Message.error('修改必须选中一项');
                }
            },
            change: function (objs) {//参数接收选中项，数值对象值
                this.selectedIds = [];//还原数组值
                for (var i = 0; i < objs.length; i++) {
                    this.selectedIds.push(objs[i].deptId);
                }
                //如果当前选中只有一项，需要将当前选中项赋值给对话框加载对象
                this.updateForm = objs[0];
            },
            update_ok() {
                util.ajax({
                    url: "dept/update",
                    method: "put",
                    data: this.updateForm
                }).then((resp) => {//请求成功后端操作
                    if (resp.data == "success") {//更新成功
                        //调用获取分页数据方法刷新页面
                        this.getPageByNo(this.current);
                        this.$Message.success('更新成功');
                    }
                })
            },
            update_cancel() {
                this.$Message.info('取消更新');
            },
            //删除部门
            deleteDept:function(){
                //判断是否有选中
                if(this.selectedIds != null&&this.selectedIds.length !=0){
                    util.ajax({
                        url:"dept/delete",
                        method:"delete",
                        data:this.selectedIds
                    }).then((resp)=>{//请求成功后端操作
                        if(resp.data=="success"){//删除成功
                            //调用获取分页数据方法刷新页面
                            this.getPageByNo(this.current);
                            this.$Message.success('删除成功');

                        }
                    })
                }
            },

        },
        mounted() {
            //页面加载是调用方法
            this.getPageByNo(1);
        }
    }
</script>

<style scoped>

</style>