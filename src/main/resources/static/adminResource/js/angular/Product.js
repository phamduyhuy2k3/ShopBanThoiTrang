app.controller("product-ctrl", function ($scope, $http, $document,$rootScope) {

    $scope.initialize = function () {
        $scope.items = [];
        $http.get("/api/product/findAll").then(resp => {
            $scope.items = resp.data.map(item => {
                item.createdDate = new Date(item.createdDate);
                return item;
            });

        })
        $http.get("/api/category/findAll").then(resp => {
            $scope.categories = resp.data;

        })
    }
    $scope.form = {
        createdDate: new Date(),
        available: true,
        imageCover: "cloud-upload.jpg",
        images: []
    }

    $scope.reset = function () {
        $scope.initialize();
        $scope.form = {
            createdDate: new Date(),
            available: true,
            imageCover: "cloud-upload.jpg",
            images: []
        }
        $scope.action = true;
        $scope.uppy1.cancelAll();
        $scope.uppy.cancelAll();
    }

    $scope.edit = function (item) {
        $scope.uppy1.cancelAll();
        $scope.uppy.cancelAll();
        $scope.form = angular.copy(item);
        $scope.action = false;
        fetch("/api/files/fetch?url="+$scope.form.imageCover)
            .then(response => response.blob())
            .then(blob => {
                $scope.uppy1.addFile({
                    name: $scope.form.imageCover,
                    type: blob.type,
                    data: blob // changed blob -> data
                })
            })
            .then(() => {
                $scope.uppy1.getFiles().forEach(file => {
                    $scope.uppy1.setFileState(file.id, {
                        progress: {uploadComplete: true, uploadStarted: true}
                    })
                })
            })
        $scope.form.images.forEach(image => {
            fetch("/api/files/fetch?url="+image)
                .then(response => response.blob())
                .then(blob => {
                    $scope.uppy.addFile({
                        name: image,
                        type: blob.type,
                        data: blob
                    })
                }).then(() => {
                $scope.uppy.getFiles().forEach(file => {
                    $scope.uppy.setFileState(file.id, {
                        progress: {uploadComplete: true, uploadStarted: true}
                    })
                })
            })
        });
    }
    $scope.create = function () {
        if ($scope.uppy1.getFiles().length > 0 && $scope.uppy.getFiles().length > 0) {
            areSomeFileNotUploaded().then(someFileNotUploaded => {
                if (someFileNotUploaded.result) {
                    areUploadsComplete(someFileNotUploaded).then(uploadComplete => {
                        if (!uploadComplete) {
                            alert('Some files failed to upload. Please try again.');
                            return;
                        }
                        setTimeout(function () {
                            $http.post('/api/product/save', $scope.form).then(resp => {
                                resp.data.createDate = new Date(resp.data.createDate);
                                $scope.items.push(resp.data);
                                $scope.reset();
                                $scope.resetUppyState();
                                alert('Thêm mới sản phẩm thành công!')
                                $rootScope.pushNotification("Success",
                                    'Thêm mới sản phẩm thành công!.',new Date());
                            }).catch(error => {
                                if(error.status === 400) {
                                    alert(error.data);
                                    return;
                                }
                                alert('Lỗi thêm mới sản phẩm!');
                                console.log('Error', error);
                            });
                        }, 500);
                    });
                } else {
                    $http.post('/api/product/save', $scope.form).then(resp => {
                        resp.data.createDate = new Date(resp.data.createDate);
                        $scope.items.push(resp.data);
                        $scope.reset();
                        $scope.resetUppyState();
                        alert('Thêm mới sản phẩm thành công!')
                        $rootScope.pushNotification("Success",
                            'Thêm mới sản phẩm thành công!',new Date());
                    }).catch(error => {
                        if(error.status === 400) {
                            alert(error.data);
                            return;
                        }
                        alert('Lỗi thêm mới sản phẩm!');
                        console.log('Error', error);
                    });
                }
            });

        } else {
            $http.post('/api/product/save', $scope.form).then(resp => {
                resp.data.createDate = new Date(resp.data.createDate);
                $scope.items.push(resp.data);
                $scope.reset();
                $scope.resetUppyState();
                alert('Thêm mới sản phẩm thành công!')
                $rootScope.pushNotification("Success",
                    'Thêm mới sản phẩm thành công!',new Date());
            }).catch(error => {
                if(error.status === 400) {
                    alert(error.data);
                    return;
                }
                alert('Lỗi thêm mới sản phẩm!');
                console.log('Error', error);
            });
        }
    };

    function areUploadsComplete(args) {


        if(args.uppy && Array.isArray(args.uppy)){
            return Promise.all(args.uppy.map(uppy => uppy.upload())).then(results => {
                return results.every(result => result.failed.length === 0);
            })
        }else {
            return args.uppy.upload().then(result => {
                return result.failed.length === 0;
            })
        }
    }
    function areSomeFileNotUploaded() {
        return Promise.all(
            [
                $scope.uppy1.getFiles().some(file => !file.progress.uploadComplete || !file.progress.uploadStarted),
                $scope.uppy.getFiles().some(file => !file.progress.uploadComplete || !file.progress.uploadStarted)
            ])
            .then(([result1, result2]) => {
               if(result1 && result2){
                     return {
                          uppy: [$scope.uppy1, $scope.uppy],
                          result: result1
                     };
               }
                if(result1 ){
                    return {
                        uppy: $scope.uppy1,
                        result: result1
                    };
                }else if(result2) {
                    return {
                        uppy: $scope.uppy,
                        result: result2
                    }
                }else {
                    return {
                        result: false
                    };
                }
            });
    }
    $scope.update = function () {
        if ($scope.uppy1.getFiles().length > 0 && $scope.uppy.getFiles().length > 0) {
            areSomeFileNotUploaded().then(someFileNotUploaded => {
                if (someFileNotUploaded.result) {
                    areUploadsComplete(someFileNotUploaded).then(uploadComplete => {
                        if (!uploadComplete) {
                            alert('Some files failed to upload. Please try again.');
                            return;
                        }
                        setTimeout(function () {
                            $http.put(`/api/product/update`, $scope.form).then(resp => {
                                resp.data.createdDate = new Date(resp.data.createdDate);
                                let index = $scope.items.findIndex(p => p.id === resp.data.id);
                                $scope.items[index] = resp.data;
                                alert("Cập nhật sản phẩm thành công!")
                                $rootScope.pushNotification("Success",
                                    "Cập nhật sản phẩm thành công!",new Date());
                            }).catch(error => {
                                if(error.status === 400) {
                                    alert(error.data);
                                    console.log("Error", error);
                                    return;
                                }
                                alert("Lỗi cập nhật sản phẩm!");
                                console.log("Error", error);
                            });
                        }, 500);
                    });
                } else {
                    $http.put(`/api/product/update`, $scope.form).then(resp => {
                        resp.data.createdDate = new Date(resp.data.createdDate);
                        let index = $scope.items.findIndex(p => p.id == resp.data.id);
                        $scope.items[index] = resp.data;
                        alert("Cập nhật sản phẩm thành công!")
                        $rootScope.pushNotification("Success",
                            "Cập nhật sản phẩm thành công!",new Date());
                    }).catch(error => {
                        if(error.status === 400) {
                            alert(error.data);
                            console.log("Error", error);
                            return;
                        }
                        alert("Lỗi cập nhật sản phẩm!");
                        console.log("Error", error);
                    });
                }
            });

        } else {
            $http.put(`/api/product/update`, $scope.form).then(resp => {
                resp.data.createdDate = new Date(resp.data.createdDate);
                let index = $scope.items.findIndex(p => p.id == resp.data.id);
                $scope.items[index] = resp.data;
                alert("Cập nhật sản phẩm thành công!")
                $rootScope.pushNotification("Success",
                    "Cập nhật sản phẩm thành công!",new Date());
            }).catch(error => {
                if(error.status === 400) {
                    alert(error.data);
                    console.log("Error", error);
                    return;
                }
                alert("Lỗi cập nhật sản phẩm!");
                console.log("Error", error);
            });
        }
    };

    $scope.delete = function (item) {
        if (confirm("Bạn muốn xóa sản phẩm này?")) {
            $http.delete(`/api/product/deleteById/${item.id}`).then(resp => {
                var index = $scope.items.findIndex(p => p.id == item.id);
                $scope.items.splice(index, 1);
                $scope.reset();
                alert("Xóa sản phẩm thành công!");
            }).catch(error => {
                alert("Lỗi xóa sản phẩm!");
                console.log("Error", error);
            })
        }
    }

    $scope.resetUppyState = () => {
        $scope.uppy1.cancelAll()
        $scope.uppy.cancelAll();
    }
    $scope.initialize();

    $scope.pager = {
        page: 0,
        size: 10,
        get items() {
            if (this.page < 0) {
                this.last();
            }
            if (this.page >= this.count) {
                this.first();
            }
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size)
        },
        get count() {
            return Math.ceil(1.0 * $scope.items.length / this.size);
        },
        first() {
            this.page = 0;
        },
        last() {
            this.page = this.count - 1;
        },
        next() {
            this.page++;
        },
        prev() {
            this.page--;
        }
    }
    $document.ready(function () {
        $scope.form = {
            createdDate: new Date(),
            available: true,
            imageCover: "cloud-upload.jpg",
            images: []
        }
        const targetElement1 = angular.element(document.querySelector('#drag-drop-area1'))[0];
        const targetElement2 = angular.element(document.querySelector('#drag-drop-area2'))[0];
        $scope.uppy1 = Uppy.Core({
            autoProceed: false,
            restrictions: {
                minNumberOfFiles: 1,
                maxNumberOfFiles: 1,
                maxFileSize: 100000000,
                allowedFileTypes: ['image/*']
            }
        })
            .use(Uppy.Dashboard,
                {
                    showLinkToFileUploadResult: false,
                    inline: true,
                    target: targetElement1,

                    proudlyDisplayPoweredByUppy: false,
                    showProgressDetails: true,
                    showRemoveButtonAfterComplete: true,
                    height: 200,
                    plugins: ['Webcam'],
                    maxNumberOfFiles: 1
                })
            .use(Uppy.XHRUpload, {
                endpoint: 'http://localhost:8080/api/files/upload', // Replace with your Tus server endpoint
                formData: true,
                fieldName: 'file' // Automatically retry failed uploads
            })
        $scope.uppy = Uppy.Core({
            autoProceed: false,
            restrictions: {
                maxFileSize: 100000000,
                allowedFileTypes: ['image/*']
            }
        }).use(Uppy.Dashboard, {
            showLinkToFileUploadResult: false,
            target: targetElement2,
            inline: true,
            height: 400,
            showProgressDetails: true,
            showRemoveButtonAfterComplete: true,
            plugins: ['Webcam'],
            proudlyDisplayPoweredByUppy: false
        }).use(Uppy.XHRUpload, {
            endpoint: 'http://localhost:8080/api/files/upload', // Replace with your Tus server endpoint
            formData: true,
            fieldName: 'file' // Automatically retry failed uploads
        });

        $scope.uppy1.on('file-removed', (file) => {

            $scope.form.imageCover = null;


        });

        $scope.uppy1.on('complete', (result) => {
            console.log(result)
            console.log(result.successful[0].response.body.uploadUrl)

            $scope.form.imageCover = result.successful[0].response.body.uploadUrl;
            console.log($scope.form.imageCover);
        });
        $scope.uppy.on('complete', (result) => {
            console.log(result)
            const fileURLs = result.successful.map((file) => file.response.body.uploadUrl);
            if (!$scope.form.images) {
                $scope.form.images = [];
            }
            fileURLs.forEach((url) => {
                $scope.form.images.push(url);
            });
            console.log($scope.form.images);


        });

        $scope.uppy.on('file-removed', (file) => {
            const removedIndex = $scope.form.images.findIndex(image => image === file.name);

            if (removedIndex !== -1) {
                $scope.form.images.splice(removedIndex, 1);

            }

        });
    });
});