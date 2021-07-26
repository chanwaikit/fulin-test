define(function (require, exports, module) {
    module.exports = (function () {
        var obj = {
            el: '.mzt-main-vue',
            countryURl: window.GLOBAL_CONFIG.mainUrl + '/static/js/country.json'
        };
        obj.init = function () {
            return new Vue({
                el: obj.el,
                data() {
                    var validatePass = (rule, value, callback) => {
                        if (value === '') {
                            callback(new Error('This is required'));
                        } else {
                            if (this.passwordForm.confirmPassword !== '') {
                                this.$refs.passwordForm.validateField('confirmPassword');
                            }
                            callback();
                        }
                    };
                    var validatePass2 = (rule, value, callback) => {
                        if (value === '') {
                            callback(new Error('This is required'));
                        } else if (value !== this.passwordForm.password) {
                            callback(new Error('Two input password must be consistent'));
                        } else {
                            callback();
                        }
                    };
                    return {
                        accountForm: {},
                        passwordForm: {
                            oldPassword: '',
                            password: '',
                            confirmPassword: '',
                        },
                        passwordFormRules: {
                            oldPassword: [{required: true, message: 'This is required', trigger: 'blur'}],
                            password: [
                                { validator: validatePass, trigger: 'blur' }
                            ],
                            confirmPassword: [
                                { validator: validatePass2, trigger: 'blur' }
                            ]
                        },
                        loading: false,
                        countryList: [],
                        editDialogVisible: false,
                        editPasswordDialogVisible: false,
                        btnLoading: false,
                        amazonLoading: false
                    }
                },
                computed: {
                    countryName() {
                        if(this.countryList.length > 0 && this.accountForm.country) {
                            return this.countryList.filter(item => item.id === this.accountForm.country)[0].nameCn
                        }
                        return ''
                    }
                },
                mounted() {
                    this.getCountryList()

                },
                methods: {
                    getQueryClient() {
                        $.get(window.GLOBAL_CONFIG.mainUrl + '/client/queryClient', res => {
                            this.accountForm = res.data
                        })
                    },
                    getCountryList() {
                        $.get(obj.countryURl, res => {
                            this.countryList = res.data
                            this.getQueryClient()
                        })
                    },
                    handleAvatarExceed(file){
                        this.$message.warning(`The maximum limit is to upload one file at a time. This time, you have selected${ file.length }Files`)
                    },
                    beforeAvatarUpload(file) {
                        this.loading = true
                        var isLtMax = file.size / 1024 / 1024 < 100
                        if (!isLtMax) {
                            this.$message.error('Upload avatar image size cannot exceed 100MB!')
                        }
                        this.loading = true
                        return isLtMax
                    },
                    handleAvatarSuccess(res) {
                        this.loading = false
                        this.accountForm.iconImageUrl  = res.data.url
                    },
                    handleAmazonSuccess(res) {
                        this.amazonLoading = false
                        this.accountForm.amazonProfileScreenshot  = res.data.url
                    },
                    handleEditProfile() {
                        this.editDialogVisible = true
                    },
                    handleEditPassword() {
                        this.editPasswordDialogVisible = true
                    },
                    updateClientInformationCookie : function () {
                        $.get(window.GLOBAL_CONFIG.mainUrl + '/client/queryClient', function (res) {
                            if (res.state === 200) {
                                var clientStr = JSON.stringify(res.data)
                                $.removeCookie("clientInformation");
                                $.cookie("clientInformation", clientStr, {expires: 1})
                            }
                        }, 'json')
                    },
                    saveClientPassword() {
                        this.$refs.passwordForm.validate((valid) => {
                            if (valid) {
                                this.btnLoading = true
                                $.ajax({
                                    url: window.GLOBAL_CONFIG.mainUrl + '/user/updatePassword',
                                    type: 'post',
                                    contentType: 'application/json;charset=UTF-8',
                                    data: JSON.stringify({
                                        'oldPassword': $.md5(this.accountForm.email + "#" + this.passwordForm.oldPassword),
                                        'loginName': this.accountForm.email,
                                        'email': this.accountForm.email,
                                        'password': $.md5(this.accountForm.email + "#" + this.passwordForm.password)
                                    }),
                                    dataType: 'json',
                                    success: res => {
                                        if (res.state === 200) {
                                            this.btnLoading = false
                                            this.editPasswordDialogVisible = false
                                            this.updateClientInformationCookie();
                                            this.$message.success('Successful application');
                                        } else {
                                            this.$message.error(res.message);
                                        }
                                    }
                                });
                            }
                        })
                    },
                    resetForm(formName) {
                        this.$refs[formName].resetFields();
                    },
                    saveClientSetting() {
                        this.$refs.accountForm.validate((valid) => {
                            if (valid) {
                                this.btnLoading = true
                                $.post( window.GLOBAL_CONFIG.mainUrl + '/evaClient/editSetting',this.accountForm,  res => {
                                    if(res.state === 200){
                                        this.btnLoading = false
                                        this.editDialogVisible = false
                                        this.getQueryClient()
                                        this.$message.success('Successful application');
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

