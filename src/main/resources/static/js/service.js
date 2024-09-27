app.controller("cart-ctrl", function ($scope, $http, $rootScope) {
    // quản lý giỏ hàng
    $scope.searchProductModel = '';
    $scope.suggestions = [];
    $scope.getAutocompleteSuggestions = function () {
        $http.get('/api/product/search?s=' + $scope.searchProductModel).then(
            (response) => {
                $scope.suggestions = response.data;
            }
        ).catch((error) => {
            console.log(error);
        })

    };
    $scope.searchProduct = function () {
        location.href = "/product/search?s=" + $scope.searchProductModel;
    }
    var $cart
        = $rootScope.cart = {
        items: [],
        add(id) { // thêm sản phẩm vào giỏ hàng
            var item = this.items.find(item => item.id == id);
            if (item) {

                item.qty++;
                if (item.qty > item.stock) {
                    item.qty = item.stock;
                    alert("Số lượng sản phẩm trong kho không đủ!")
                } else {
                    this.saveToLocalStorage();
                    alert("Đã thêm sản phẩm vào giỏ hàng!")
                }
            } else {
                $http.get(`/api/product/findById/${id}`).then(resp => {
                    if(resp.data.stock <= 0){
                        alert("Sản phẩm đã hết hàng!")
                        return;
                    }
                    resp.data.qty = 1;
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                    alert("Đã thêm sản phẩm vào giỏ hàng!")
                })
            }
        },
        remove(id) { // xóa sản phẩm khỏi giỏ hàng
            let isConfirm = confirm("Bạn có muốn xóa sản phẩm này không?")
            if (isConfirm) {
                var index = this.items.findIndex(item => item.id == id);
                this.items.splice(index, 1);
                this.saveToLocalStorage();
            }

        },
        clear() { // Xóa sạch các mặt hàng trong giỏ
            this.items = []
            this.saveToLocalStorage();
        },
        amt_of(item) { // tính thành tiền của 1 sản phẩm
            return item.qty * item.price;
        },
        increase(id) {
            var item = this.items.find(item => item.id == id);

            if (item) {

                $http.get(`/api/product/findById/${item.id}`).then(resp => {

                    if (item.qty >= resp.data.stock) {
                        item.qty = resp.data.stock;
                        alert("Số lượng sản phẩm trong kho không đủ!")
                    } else {
                        item.qty++;
                    }
                    this.saveToLocalStorage();
                })

            }
        },
        decrease(id) {
            var item = this.items.find(item => item.id == id);
            if (item) {
                item.qty--;
                if (item.qty <= 0) {
                    let isConfirm = confirm("Bạn có muốn xóa sản phẩm này không?")
                    if (isConfirm) {
                        this.remove(item.id);
                    } else {
                        item.qty = 1;
                    }
                }
                this.saveToLocalStorage();
            }
        },
        checkMinValue(id) {
            var item = this.items.find(item => item.id == id);
            if (item.qty < 1) {
                item.qty = 1;
            }
            $http.get(`/api/product/findById/${id}`).then(resp => {
                if (item.qty >= resp.data.stock) {
                    item.qty = resp.data.stock;
                    alert("Số lượng sản phẩm trong kho không đủ!")
                }
                this.saveToLocalStorage();
            })

        },
        get count() { // tính tổng số lượng các mặt hàng trong giỏ
            return this.items
                .map(item => item.qty)
                .reduce((total, qty) => total += qty, 0);
        },
        get amount() { // tổng thành tiền các mặt hàng trong giỏ
            return this.items
                .map(item => this.amt_of(item))
                .reduce((total, amt) => total += amt, 0);
        },
        saveToLocalStorage() { // lưu giỏ hàng vào local storage
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem("cart", json);
        },
        loadFromLocalStorage() { // đọc giỏ hàng từ local storage
            var json = localStorage.getItem("cart");
            this.items = json ? JSON.parse(json) : [];
        }
    }

    $cart.loadFromLocalStorage();

    // Đặt hàng

})
app.controller("checkout-ctrl", function ($scope, $http, $rootScope) {
    if($rootScope.cart.items.length === 0){
        alert("Giỏ hàng trống!")
        location.href = "/";


    }

    $scope.initialize = async function () {
        $scope.countries = [];
        await $http.get("https://countriesnow.space/api/v0.1/countries/flag/images")
            .then(resp => {
                $scope.countries = resp.data.data;
                console.log($scope.countries)
                // Add flag images to the 'data' object for each option
                $scope.countries.forEach(function (country) {
                    country.id = country.name;
                    country.text = country.name;
                });
            });
    };

    $scope.initialize().then(r => {
        $scope.select = $('#country').select2({
            // Use the modified 'data' object
            data: $scope.countries,
            templateResult: function (data) {
                if (!data.id) return data.text; // Option is not an object (e.g., the "Select a country" option)
                let $result = $('<span><img src="' + data.flag + '" class="img-flag" style="width: 25px; height: 25px; border: #0a0c0d solid thin " /> ' + data.text + '</span>');
                return $result;
            },
            templateSelection: function (data) {
                if (!data.id) return data.text; // Option is not an object (e.g., the "Select a country" option)
                let $selection = $('<span><img src="' + data.flag + '" class="img-flag" style="width: 25px; height: 25px; border: #0a0c0d solid thin" /> ' + data.text + '</span>');
                return $selection;
            }
        });
        $scope.select.on('select2:select', function (e) {
            let data = e.params.data;
            console.log(data);
            $scope.$apply(function () {
                $scope.order.address.country = data.name;
                console.log($scope.order.address.country);
            });
        });
    });
    console.log($rootScope.$auth)
    $scope.order = {

        get account() {
            return $rootScope.$auth.user
        },
        address: {
            addressLine: "",
            city: "",
            country: "",
            province: "",
            postalCode: ""

        },
        phoneNumber: "",
        orderedAt: new Date(),
        paymentmethod: "",
        get ordersDetails() {
            if (this.account == null) {
                alert("Bạn cần đăng nhập để đặt hàng!")
            }
            return $rootScope.cart.items.map(item => {
                return {
                    product:
                        {
                            id: item.id,
                            name: item.name,
                            price: item.price,
                        },
                    price: item.price,
                    quantity: item.qty
                }
            });
        },
        totalPrice: $rootScope.cart.amount,

        purchase() {
            let order = angular.copy(this);
            order.address = order.address.addressLine + ", " + order.address.city + ", " + order.address.province + ", " + order.address.country + ", " + order.address.postalCode;
            // Thực hiện đặt hàng
            $http.post("/user/api/orders/purchase", order).then(resp => {
                alert("Đặt hàng thành công!");
                $rootScope.cart.clear();
                location.href = "/user/orders/" + resp.data.id;
            }).catch(error => {
                alert("Đặt hàng lỗi!")
                console.log(error)
            })
        }
    }
})