<!doctype html>
<html lang="en" ng-app="daily-bill">
    <head>
        <meta charset="utf-8">
        <title>Daily Bill</title>
        <script src="js/libs/vue.js"></script>
        <script src="js/libs/vue-router.js"></script>
        <script src="js/vue/daily-bill.js"></script>
    </head>
    <body>
        <div id="daily-bill-app">
            <h1>Daily bill app</h1>
            <p>
                <router-link to="/foo">Go to Foo</router-link>
                <router-link to="/bar">Go to Bar</router-link>
                <router-link to="/bill">Go to BillList</router-link>
            </p>
            <router-view></router-view>
        </div>
    </body>
</html>