<template>
    <div class="layout">
        <Row>
            <Col span="24">
                用户名：<Input placeholder="请输入" style="width: 200px"/>
                <Button type="primary" shape="circle" icon="ios-search">搜索</Button>
            </Col>
        </Row>
        <br>
        <Row>
            <Col span="24">
                <Button type="info" icon="md-add" @click="addUserModel=true">新建</Button>
                <Button type="success" icon="ios-build" @click="openUpdateModal">修改</Button>
                <Button type="error" icon="ios-trash" @click="deleteUser">删除</Button>
                <Button type="success" icon="ios-build" @click="UpdateRole">分配角色</Button>

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
                v-model="addUserModel"
                title="添加用户"
                width="80%"
                @on-ok="addUser"
                @on-cancel="cancel"
                :loading="loading">
            <Form ref="addForm" :model="addForm" :rules="ruleValidate" :label-width="100">
                <Row>
                    <Col span="10">
                        <FormItem label="所在部门">
                            <Select v-model="addForm.dept.deptId">
                                <Option v-for="item in deptlist" :value="item.deptId" :key="item.deptId">
                                    {{item.deptName}}
                                </Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="直属领导">
                            <Select v-model="addForm.userInfo.managerId">
                                <Option v-for="item in userInfolist" :value="item.userInfoId" :key="item.userInfoId">
                                    {{item.name}}
                                </Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="登录名" prop="username">
                            <Input v-model="addForm.username" placeholder="请输入用户名"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="密码">
                            <Input v-model="addForm.password" placeholder="请输入密码"></Input>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="入职时间">
                            <DatePicker
                                    type="date"
                                    placeholder="Select date"
                                    v-model="addForm.userInfo.joinDate"
                                    style="width: 200px">
                            </DatePicker>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="薪水">
                            <Input v-model="addForm.userInfo.salary" placeholder="请输入工资"></Input>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="等级">
                            <Select v-model="addForm.userInfo.degree">
                                <Option v-for="item in degreeList" :value="item.value" :key="item.value">
                                    {{item.label}}
                                </Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="性别">
                            <RadioGroup v-model="addForm.userInfo.gender" type="button">
                                <Radio label="1">男</Radio>
                                <Radio label="0">女</Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="状态">
                            <RadioGroup v-model="addForm.state" type="button">
                                <Radio label="0">可用</Radio>
                                <Radio label="1">不可用</Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="岗位">
                            <Input v-model="addForm.userInfo.station" placeholder="请输入岗位"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="电话">
                            <Input v-model="addForm.userInfo.telephone" placeholder="请输入电话"></Input>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="邮箱">
                            <Input v-model="addForm.userInfo.email" placeholder="请输入邮箱"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="出身年月">
                            <DatePicker type="date" placeholder="Select date" v-model="addForm.userInfo.birthday"
                                        style="width: 200px"></DatePicker>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="排序号">
                            <Input v-model="addForm.userInfo.orderNo" placeholder="请输入排序号"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="说明">
                            <Input v-model="addForm.userInfo.remark" type="textarea" :autosize="{minRows: 2,maxRows: 5}"
                                   placeholder="Enter something..."></Input>
                        </FormItem>
                    </Col>
                </Row>
            </Form>
        </Modal>
        <Modal
                v-model="updateModal"
                width="80%"
                title="修改"
                @on-ok="update_ok"
                @on-cancel="update_cancel">
            <!--ref:用于程序中获取表单的标识名-->
            <!--:model：数据绑定-->
            <!--:rules：指定前端校验规则-->
            <Form ref="updateForm" :model="updateForm" :label-width="80">
                <Row>
                    <Col span="10">
                        <FormItem label="所在部门">
                            <Select v-model="updateForm.dept.deptId">
                                <Option v-for="item in deptlist" :value="item.deptId" :key="item.deptId">
                                    {{item.deptName}}
                                </Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="直属领导">
                            <Select v-model="updateForm.userInfo.managerId">
                                <Option v-for="item in userInfolist" :value="item.userInfoId" :key="item.userInfoId">
                                    {{item.name}}
                                </Option>
                            </Select>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="登录名" prop="username">
                            <Input v-model="updateForm.username" placeholder="请输入用户名"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="姓名">
                            <Input v-model="updateForm.userInfo.name" placeholder="请输入姓名"></Input>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="入职时间">
                            <DatePicker
                                    type="date"
                                    placeholder="Select date"
                                    v-model="updateForm.userInfo.joinDate"
                                    style="width: 200px">
                            </DatePicker>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="薪水">
                            <Input v-model="updateForm.userInfo.salary" placeholder="请输入工资"></Input>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="等级">
                            <Select v-model="updateForm.userInfo.degree">
                                <Option v-for="item in degreeList" :value="item.value" :key="item.value">
                                    {{item.label}}
                                </Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="性别">
                            <RadioGroup v-model="updateForm.userInfo.gender" type="button">
                                <Radio label="1">男</Radio>
                                <Radio label="0">女</Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="状态">
                            <RadioGroup v-model="updateForm.state" type="button">
                                <Radio :label="0">可用</Radio>
                                <Radio :label="1">不可用</Radio>
                            </RadioGroup>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="岗位">
                            <Input v-model="updateForm.userInfo.station" placeholder="请输入岗位"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="电话">
                            <Input v-model="updateForm.userInfo.telephone" placeholder="请输入电话"></Input>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="邮箱">
                            <Input v-model="updateForm.userInfo.email" placeholder="请输入邮箱"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="出身年月">
                            <DatePicker type="date" placeholder="Select date" v-model="updateForm.userInfo.birthday"
                                        style="width: 200px"></DatePicker>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="10">
                        <FormItem label="排序号">
                            <Input v-model="updateForm.userInfo.orderNo" placeholder="请输入排序号"></Input>
                        </FormItem>
                    </Col>
                    <Col span="4"></Col>
                    <Col span="10">
                        <FormItem label="说明">
                            <Input v-model="updateForm.userInfo.remark" type="textarea"
                                   :autosize="{minRows: 2,maxRows: 5}" placeholder="Enter something..."></Input>
                        </FormItem>
                    </Col>
                </Row>
            </Form>

        </Modal>
        <Modal
                v-model="roleModal"
                title="分配用户"
                width="60%"
                @on-ok="role_ok"
                @on-cancel="role_cancel">
            <Form ref="roleForm" :model="roleForm" :label-width="80">
                <Row>
                    <Col span="10">
                        <FormItem label="登录名">
                            <Input v-model="roleForm.username" disabled></Input>
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="20">
                        <FormItem label="角色">
                            <CheckboxGroup v-model="roleForm.roleIds">
                                <Checkbox v-for="item in roleList" :label="item.roleId" :key="item.roleId">
                                    {{item.name}}
                                </Checkbox>
                            </CheckboxGroup>
                        </FormItem>
                    </Col>
                </Row>
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
                addUserModel: false,
                updateModal: false,
                roleModal:false,
                addForm: {
                    "userame": "str",
                    "password": "str",
                    "state": 0,
                    "dept": {
                        "deptId": "str",
                        "deptName": "str",
                        "state": 0,
                        "parentId": "str"
                    },
                    "userInfo": {

                        "name": "str",
                        "joinDate": '',
                        "salary": 0.0,
                        "birthday": '',
                        "gender": "str",
                        "station": "str",
                        "telephone": "str",
                        "degree": 0,
                        "remark": "str",
                        "orderNo": 0,
                        "email": "str",
                        "managerId": "str"

                    },

                },
                updateForm: {
                    "userId": "",
                    "username": "str",
                    "password": "str",
                    "state": 0,
                    "dept": {
                        "deptId": "str",
                        "deptName": "str",
                        "state": 0,
                        "parentId": "str"
                    },
                    "userInfo": {
                        "name": "str",
                        "joinDate": '',
                        "salary": 0.0,
                        "birthday": '',
                        "gender": 0,
                        "station": "str",
                        "telephone": "str",
                        "degree": 0,
                        "remark": "str",
                        "orderNo": 0,
                        "email": "str",
                        "managerId": "str"
                    }
                },
                roleForm:{
                    userId:'',
                    username:'',
                    roleIds:[]//存储当前用户所有的角色id
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
                        title: '用户名',
                        // 代码操作的是key的值
                        key: 'username'
                    },
                    {
                        title: '状态',
                        key: 'state'
                    },
                ],
                degreeList: [
                    {
                        value: 0,
                        label: '管理员'
                    },
                    {
                        value: 1,
                        label: '部门经理'
                    },
                    {
                        value: 2,
                        label: '组长'
                    },
                    {
                        value: 3,
                        label: '普通员工'
                    }

                ],
                data1: [],
                selectedIds: [],//存储选中项的id
                deptlist: [],
                userInfolist: [],


            }

        },
        methods: {
            getPageByNo(pageNo) {
                //把事件返回页码赋值给上面的属性
                this.pageNo = pageNo;
                util.ajax({
                    // url: 'http://localhost:8081/'
                    url: "user/getPage",// 因为在utiljs中配置了前缀所有指定地址时可以省略前缀
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
            addUser() {
                //进行验证操作
                this.$refs['addForm'].validate((valid) => {
                    if (valid) {//判断验证结果
                        //请求后端新增数据
                        util.ajax({
                            url: "user/addUser",
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
                    this.selectedIds.push(objs[i].userId);
                }
                //如果当前选中只有一项，需要将当前选中项赋值给对话框加载对象
                this.updateForm = objs[0];
            },
            update_ok() {
                util.ajax({
                    url: "user/update",
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
            deleteUser: function () {
                //判断是否有选中
                if (this.selectedIds != null && this.selectedIds.length != 0) {
                    util.ajax({
                        url: "user/delete",
                        method: "delete",
                        data: this.selectedIds
                    }).then((resp) => {//请求成功后端操作
                        if (resp.data == "success") {//删除成功
                            //调用获取分页数据方法刷新页面
                            this.getPageByNo(this.current);
                            this.$Message.success('删除成功');

                        }
                    })
                }
            },
            UpdateRole:function(){
                if (this.selectedIds.length == 1) {//只选中一项
                    //构建当前用户的角色对象
                    var obj = this.updateForm;
                    this.roleForm.userId = obj.userId;
                    this.roleForm.username = obj.username;
                    this.roleForm.roleIds=[];//还原
                    for(var i = 0;i < obj.roles.length;i++){
                        //获取当前用户所有角色
                        this.roleForm.roleIds.push(obj.roles[i].roleId);
                    }
                    this.roleModal = true;//打开对话框
                } else {
                    this.$Message.error('修改必须选中一项');
                }
            },
            //分配角色
            role_ok:function(){
                util.ajax({
                    url: "user/updateRole/"+this.roleForm.userId,
                    method: "put",
                    // params:{
                    //     userId:this.roleForm.userId
                    // },
                    data: this.roleForm.roleIds
                }).then((resp) => {//请求成功后端操作
                    if (resp.data == "success") {//新增成功
                        //调用获取分页数据方法刷新页面
                        this.getPageByNo(this.current);
                        this.$Message.success('分配成功');
                    }
                })
            },
            role_cancel() {
                this.$Message.info('取消分配');
            },

        },
        mounted() {
            //页面加载是调用方法
            this.getPageByNo(1);
            //加载所有部门
            util.ajax({
                url: "dept/getAllDept",
                method: "get"
            }).then((resp) => {
                this.deptlist = resp.data;
            })
            //加载所有用户（直属领导）
            util.ajax({
                url: "userInfo/getAllUserInfo",
                method: "get"
            }).then((resp) => {
                this.userInfolist = resp.data;
            })
            //加载所有角色
            util.ajax({
                url: "role/getAllRole",
                method: "get"
            }).then((resp) => {
                this.roleList = resp.data;
            })
        }
    }
</script>

<style scoped>

</style>