document.addEventListener("DOMContentLoaded", function(event) {
    Vue.component('foo', {
        data: function() {
            return {message: 'Foo message'}
        },
        template: ' \
            <div class="foo"> \
                foo \
                <div>message: {{message}}</div> \
            </div> \
        '

    });
    Vue.component('bar', {
        template: ' \
            <div class="bar"> \
                bar \
                <div>message: {{message}}<div> \
            </div> \
        ',
        data: function() {
            return {message: 'Bar message'}
        }
    })

    const Foo = {template: '<foo>'};
    const Bar = {template: '<bar>'};

    const routes = [
        {path: '/foo', component: Foo},
        {path: '/bar', component: Bar}
    ]

    const router = new VueRouter({routes});
    const dailyBillApp = new Vue({router}).$mount('#daily-bill-app')
})