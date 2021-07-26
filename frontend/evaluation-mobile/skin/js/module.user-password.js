define(function (require, exports, module) {
    module.exports = (function () {
        var obj = {
            el: '.mzt-main-vue'
        };
        obj.init = function () {
            return new Vue({
                el: obj.el,
                data() {
                    return {
                        accountForm: {},
                        userData: {
                            id: '',
                            email: '',
                            password:'',
                            confirmPassword: '',
                        },
                        loading: false,
                        countryList: []
                    }
                },
                mounted() {
                    this.getQueryClient()
                },
                methods: {
                    getQueryClient() {
                        $.get(window.GLOBAL_CONFIG.mainUrl + '/client/queryClient', res => {
                            this.userData.email = res.data.email
                            this.userData.id = res.data.id
                        })
                    },
                    saveClientSetting() {
                        this.$refs.userData.validate((valid) => {
                            if (valid) {
                                if(this.userData.password !== this.userData.confirmPassword){
                                    this.$message.error("The password is inconsistent");
                                    return false
                                }
                                let object = JSON.parse(JSON.stringify(this.userData))
                                object.password = $.md5(object.email + "#" + object.password)
                                object.confirmPassword = $.md5(object.email + "#" + object.confirmPassword)
                                $.post( window.GLOBAL_CONFIG.mainUrl + '/user/settingPassword',object, function (res) {
                                    if(res.state === 200){
                                        window.location.href = window.GLOBAL_CONFIG.mainUrl + '/evaClient/order';
                                    }else{
                                        this.$message.error(res.message);
                                    }
                                }, 'json')
                            }
                        })
                    }
                }
            })
        };
        return obj;
    })()
});

