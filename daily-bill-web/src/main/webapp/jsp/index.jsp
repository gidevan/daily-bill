<!doctype html>
<html lang="en" ng-app="daily-bill">
  <head>
    <meta charset="utf-8">
    <title>Daily Bill</title>
    <script src="js/libs/angular.js"></script>
    <script src="js/libs/angular-route.js"></script>
    <script src="js/angular/app.js"></script>
    <script src="js/angular/app.config.js"></script>
    <script src="js/angular/bill-list/bill-list.component.js"></script>
  </head>
  <body>

    <div class="view-container">
          <div ng-view class="view-frame"></div>
    </div>
  </body>
</html>