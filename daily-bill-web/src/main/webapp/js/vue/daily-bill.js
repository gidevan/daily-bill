document.addEventListener("DOMContentLoaded", function(event) {
    const Foo = {template: '<div>foo</div>'}
    const Bar = {template: '<div>bar</div>'}

    const routes = [
        {path: '/foo', component: Foo},
        {path: '/bar', component: Bar}
    ]

    const router = new VueRouter({routes});
    const dailyBillApp = new Vue({router}).$mount('#daily-bill-app')
})