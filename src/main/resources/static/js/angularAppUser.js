
var app = angular.module("userApp", []);
app.run(function($http, $rootScope){
    $http.get(`/api/auth/authentication`).then(resp => {
        if(resp.data){
            $auth = $rootScope.$auth = resp.data;
            $http.defaults.headers.common["Authorization"] = $auth.token;
            console.log(resp)
            console.log($auth);
        }
    }).catch(error => {
        $auth = $rootScope.$auth =null
        console.log(error);
    });
})
