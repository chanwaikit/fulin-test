$.validator.setDefaults({
    rules: {
        'checkbox': {
            required: true
        },
        'gender': {
            required: true
        }
    },
    errorElement : 'span',
    errorClass : 'help-error',
    //自定义错误消息放到哪里
    errorPlacement : function(error, element) {
        element.closest('.form-group').append(error);
    },
    //给未通过验证的元素进行处理
    highlight : function(element) {
        $(element).closest('.form-group').addClass('has-feedback');
    },

    unhighlight: function (element) {
        $(element).closest('.form-group').removeClass('has-feedback');
    },
    //验证通过的处理
    success : function(element) {
        $(element).parents('.form-group').addClass('success');
    }
});

jQuery.validator.addMethod("englishString", function(value, element) {
    var regex = /^[a-zA-Z-_]+$/;
    return this.optional(element) || (regex.test(value));
}, "请输入英文字母");

// 手机号码验证
jQuery.validator.addMethod("isPhoneOrEmail", function(value, element) {
    var length = value.length;
    var checkPhone = /^\s*\+?\s*(\(\s*\d+\s*\)|\d+)(\s*-?\s*(\(\s*\d+\s*\)|\s*\d+\s*))*\s*$/;
    var checkEmail = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/i;
    return this.optional(element) || (length <14 && checkPhone.test(value)) || checkEmail.test(value);
}, "Enter a valid email address");

// 手机号码验证
jQuery.validator.addMethod("isPhone", function(value, element) {
    var length = value.length;
    var checkPhone = /^\s*\+?\s*(\(\s*\d+\s*\)|\d+)(\s*-?\s*(\(\s*\d+\s*\)|\s*\d+\s*))*\s*$/;
    return this.optional(element) || (length < 14 && checkPhone.test(value));
}, "Enter a valid Phone");

// 邮箱验证
jQuery.validator.addMethod("isEmail", function(value, element) {
    var length = value.length;
    var checkMail = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/i;
    return this.optional(element) || (length == 11 && email.test(value));
}, "Enter a valid Email");
// 电话号码验证
jQuery.validator.addMethod("isTel", function(value, element) {
    var tel = /^(\d{3,4}-)?\d{7,8}$/g; // 区号－3、4位 号码－7、8位
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的电话号码。");
// 匹配密码，以字母开头，长度在6-12之间，必须包含数字和特殊字符。
jQuery.validator.addMethod("isPwd", function(value, element) {
    var str = value;
    if (str.length < 6 || str.length > 18)
        return false;
    if (!/^[a-zA-Z]/.test(str))
        return false;
    if (!/[0-9]/.test(str))
        return fasle;
    return this.optional(element) || /[^A-Za-z0-9]/.test(str);
}, "以字母开头，长度在6-12之间，必须包含数字和特殊字符。");