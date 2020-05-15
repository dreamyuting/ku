import Main from '@/views/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: 'Login - 登录'
    },
    component: () => import('@/views/login.vue')
};

export const page404 = {
    path: '/*',
    name: 'error-404',
    meta: {
        title: '404-页面不存在'
    },
    component: () => import('@/views/error-page/404.vue')
};

export const page403 = {
    path: '/403',
    meta: {
        title: '403-权限不足'
    },
    name: 'error-403',
    component: () => import('@//views/error-page/403.vue')
};

export const page500 = {
    path: '/500',
    meta: {
        title: '500-服务端错误'
    },
    name: 'error-500',
    component: () => import('@/views/error-page/500.vue')
};

export const preview = {
    path: '/preview',
    name: 'preview',
    component: () => import('@/views/form/article-publish/preview.vue')
};

export const locking = {
    path: '/locking',
    name: 'locking',
    component: () => import('@/views/main-components/lockscreen/components/locking-page.vue')
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    redirect: '/home',
    component: Main,
    children: [
        { path: 'home', title: {i18n: 'home'}, name: 'home_index', component: () => import('@/views/home/home.vue') },
        { path: 'ownspace', title: '个人中心', name: 'ownspace_index', component: () => import('@/views/own-space/own-space.vue') },
        { path: 'order/:order_id', title: '订单详情', name: 'order-info', component: () => import('@/views/advanced-router/component/order-info.vue') }, // 用于展示动态路由
        { path: 'shopping', title: '购物详情', name: 'shopping', component: () => import('@/views/advanced-router/component/shopping-info.vue') }, // 用于展示带参路由
        { path: 'message', title: '消息中心', name: 'message_index', component: () => import('@/views/message/message.vue') }
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [
    {
        path: '/system',
        icon: 'ios-grid-view',
        name: 'system',
        title: '系统管理',
        component: Main,
        children: [
            { path: 'dept', title: '区域管理', name: 'dept', icon: 'arrow-move', component: () => import('@/views/system/Dept.vue') },
            { path: 'user', title: '用户管理', name: 'user', icon: 'edit', component: () => import('@/views/system/User.vue') },
            { path: 'role', title: '角色管理', name: 'role', icon: 'search', component: () => import('@/views/system/Role.vue') },
            { path: 'module', title: '模块管理', name: 'module', icon: 'code-download', component: () => import('@/views/system/Module.vue') }
        ]
    },
    {
        path: '/cargo',
        icon: 'ios-grid-view',
        name: 'cargo',
        title: '销售管理',
        component: Main,
        children: [
            { path: 'contract', title: '订单管理', name: 'contract', icon: 'arrow-move', component: () => import('@/views/cargo/contract.vue') },
            { path: 'contractProduct/:id', title: '油品信息管理', hidden: true,name: 'contractProduct', icon: 'edit', component: () => import('@/views/cargo/contractProduct.vue') },
            { path: 'export_contract', title: `库存管理`, name: 'export_contract', icon: 'code-download', component: () => import('@/views/cargo/ExportContract.vue') },
            { path: 'outProduct', title: '账号信息打印', name: 'outProduct', icon: 'code-download', component: () => import('@/views/cargo/outProduct.vue') },
            { path: 'extCproduct/:id', title: '帖子管理', hidden: true,name: 'extCproduct', icon: 'search', component: () => import('@/views/cargo/extCproduct.vue') },
          /*  { path: 'export', title: '出口报运', name: 'export', icon: 'code-download', component: () => import('@/views/cargo/Export.vue') },
            { path: 'exportEdit/:id', title: 'exportEdit', name: 'exportEdit', icon: 'code-download', component: () => import('@/views/cargo/ExportEdit.vue') },*/

        ]
    },
    {
        path: '/stat',
        icon: 'ios-grid-view',
        name: 'stat',
        title: '业绩分析',
        component: Main,
        children: [
            { path: 'factorysale', title: '个人销售情况', name: 'factorysale', icon: 'arrow-move', component: () => import('@/views/sale/factorysale.vue') },
            { path: 'productsale', title: '产品销售排行', name: 'productsale', icon: 'edit', component: () => import('@/views/sale/productsale.vue') },
          /*  { path: 'onlineinfo', title: '系统访问压力', name: 'onlineinfo', icon: 'search', component: () => import('@/views/system/Dept.vue') }*/
        ]
    }
];


// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    loginRouter,
    otherRouter,
    preview,
    locking,
    ...appRouter,
    page500,
    page403,
    page404

];