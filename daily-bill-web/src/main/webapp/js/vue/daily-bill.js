document.addEventListener("DOMContentLoaded", function(event) {
    const DailyBillService = {
        getBills: function() {
            console.log("Service.getBills")
            return [
                {billId: 1, billName: "BillName1"},
                {billId: 2, billName: "BillName2"},
                {billId: 3, billName: "BillName3"}
            ]
        },
        getBill : function(id) {
            return {billId: id, billName: "BillName" + 1, description: "Description: Bill" + id}
        }
    }
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
    Vue.component('bill-list', {
        billService: DailyBillService,
        template: ' \
            <div> \
                <div>Bill List</div> \
                <div>content</div> \
                <div  v-for="bill in bills">BillId: {{bill.billId}} BillName: {{bill.billName}}</div> \
            </div> \
        ',
        data : function() {
            return {
                //bills: []
                //bills: this.billService.getBills()
                //bills: billService.getBills()
                bills: DailyBillService.getBills()
            }
        },
        methods: {
            getBills : function() {
                return billService.getBills();
            }
        },
        watch: function() {
            console.log("watch")
            //$route() {
            //    this.bills = billService.getBills();

            //},
        },
        beforeRouteEnter: function (to, from, next) {
            console.log("before rendering");
            next(vm => {
                vm.bills = billService.getBills();
            })
        }

    })

    const Foo = {template: '<foo/>'};
    const Bar = {template: '<bar/>'};
    const BillList = {template: '<bill-list/>'};


    const routes = [
        {path: '/foo', component: Foo},
        {path: '/bar', component: Bar},
        {path: '/bill', component: BillList}
    ]

    const router = new VueRouter({routes});
    const dailyBillApp = new Vue({router}).$mount('#daily-bill-app')
})