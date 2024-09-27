app = angular.module("app", ["ngRoute"]);

// app.run(function($http, $rootScope){
//     $http.get(`/rest/security/authentication`).then(resp => {
//         if(resp.data){
//             $auth = $rootScope.$auth = resp.data;
//             $http.defaults.headers.common["Authorization"] = $auth.token;
//         }
//     });
// })

app.config(function ($routeProvider) {
    $routeProvider
        .when("/product", {
            templateUrl: "/adminResource/Product.html",
            controller: "product-ctrl"
        })
        .when("/account", {
            templateUrl: "/adminResource/Account.html",
            controller: "authority-ctrl"
        })
        .when("/orders", {
            templateUrl: "/adminResource/Orders.html",
            controller: "authority-ctrl"
        })
        .otherwise({
            redirectTo: '/product'
        });
});
app.controller("mainCtrl", function ($scope, $http, $document,$rootScope,$timeout) {
    $rootScope.notifications=[];
    $rootScope.pushNotification = function (title,content,date) {
        $rootScope.notifications.push(
            {
                title: title,
                content: content,
                time: moment(date).fromNow()
            }
        )
    }
    $rootScope.removeNotification = function (index) {
        $rootScope.notifications.splice(index,1);
    }
    $rootScope.clearNotification = function () {
        $rootScope.notifications = [];
    }

});
