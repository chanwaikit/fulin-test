$ = window.$ || {};

$.fn.serializeObject = function() {
    var o = {};
    $.each(this.serializeArray(), function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
$.fn.objectSerialize = function (obj) {
    var $from = this;
    $.each(obj, function (name, val) {
       var $outInput = $from.find('*[name="'+name+'"]');
        $outInput.each(function () {
           var $input = $(this);
           if ($input.attr('type') === 'checkbox') {
                var checkboxValArray = [];
                if(!(val instanceof Array)) {
                    checkboxValArray.push(val);
                } else {
                    checkboxValArray = val;
                }
               checkboxValArray.forEach(function (item) {
                    if($input.val() == item) {
                        $input.prop('checked', 'checked');
                    }
               });
           } else if($input.attr('type') === 'radio') {
                if($input.val() == val) {
                    $input.prop('checked', 'true');
                }
           } else {
                $input.val(val)
           }
           $input.trigger('change');
        });
    })
};

$.Theme = {
    $body                   :  $('body'),
    Util                    : {},
    readyMethods            : {},
    currentHistoryStateId   : false,
    plugins   : {}
};
$.Theme['addReadyMethod'] = function (name, method) {
    $.Theme.readyMethods[name] = method;
};

$.Theme['loadReady'] = function (element) {
    var $element = $(element);
    var readyMethod;
    for ( readyMethod in $.Theme.readyMethods ) {
        $.Theme.readyMethods[readyMethod].call(this, $element);
    }
};
$.Theme['uuid'] = function () {
    var prefix = arguments[0] || '';
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
    s[8] = s[13] = s[18] = s[23];
    return prefix + s.join("");
};
/**
 * $.Theme.isLoginState().then(function (state) {
 *   if(state) {
 *
 *   }
 * })
 * @returns {*}
 */
$.Theme.isLoginState = function() {
    var def =  $.Deferred();
    $.ajax({
        type: 'GET',
        dataType: 'JSON',
        contentType: "application/json",
        url: window.GLOBAL_CONFIG.mainUrl + '/isLogin',
        success: function (res) {
            if (res.data.code === 200) {
                def.resolve(true);
            } else {
                def.resolve(false);
            }
        }
    })
    return def.promise();
}

$.Theme.Util = {
    // 个位数补零
    singleDigitZero : function (num) {
        if(num < 10){
            return '0'+ num;
        }else{
            return '' + num;
        }
    },
    getParams: function (url) {
        var paramObj = {};
        if(url.indexOf('?') === -1) {
            return paramObj
        }
        var keyValueArr = url.split('?')[1].split('&');
        keyValueArr.forEach(function(item)  {
            var keyValue = item.split('=');
            paramObj[keyValue[0]] = decodeURI(keyValue[1]);
        });
        return paramObj
    },
    pushState: function (url, paramObj, title) {
        var queryString = '';
        Object.keys(paramObj).forEach((key) => {
            queryString += `${key}=${paramObj[key]}&`;
        });
        queryString = queryString.substring(0, queryString.lastIndexOf('&'));
        url += '?'+queryString;
        history.pushState({ key: $.Theme.uuid() }, title, url);
    },
    formatTimestamp: function(timestamp, format) {
       return  this.formatDate(new Date(timestamp * 1000), format)
    },
    formatDate:function (date,format) {
        if (date === '') {
            return '';
        } else {
            var paddNum = function(num){
                num += "";
                return num.replace(/^(\d)$/,"0$1");
            };
            var cfg = {
                yyyy : date.getFullYear() //年 : 4位
                ,yy : date.getFullYear().toString().substring(2)//年 : 2位
                ,M  : date.getMonth() + 1  //月 : 如果1位的时候不补0
                ,MM : paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0
                ,d  : date.getDate()   //日 : 如果1位的时候不补0
                ,dd : paddNum(date.getDate())//日 : 如果1位的时候补0
                ,hh : date.getHours()  //时
                ,mm : date.getMinutes() //分
                ,ss : date.getSeconds() //秒
            };
            format || (format = "yyyy-MM-dd hh:mm:ss");
            return format.replace(/([a-z])(\1)*/ig,function(m){return cfg[m];});
        }
    },
    splitTime: function (time, end) {
        let timeStr = time + ''
        if(timeStr.length > 1) {
            if(end) {
                return timeStr.substr(-1)
            }
            return timeStr.charAt(0)
        } else {
            if(end) {
                return time
            }
            return 0
        }
    }
};

$.Theme.res = function (fileName) {
   return window.GLOBAL_CONFIG.mainUrl + '/static' + window.GLOBAL_CONFIG.templateName + '/skin/img/' + fileName;
};

$.Theme.dom = function (element) {
    this.$element = $(element);
};

$.Theme.dom.prototype.isReady = function() {
    if(this.$element.data('plugins')) {
        return true;
    }
    this.$element.data('plugins', true)
    return false;
};

$.Theme.dom.prototype.data = function (dataName, vlaue) {

    if(vlaue) {
        this.$element.attr('data-'+dataName, JSON.stringify(vlaue));
    } else {
        var val = this.$element.attr('data-'+dataName);
        return !val ? undefined : (function () {
            if(typeof val === 'string') {
                if(val.indexOf('}') > 0) {
                    return eval('(' + val + ')')
                } else {
                    return val
                }
            }
            return val
        })();
    }
};

$.Theme.dom.prototype.attr = function (attrName) {
    return this.$element.attr(attrName);
};

$.Theme.dom.prototype.attr = function () {
    return this.$element.val();
};


$(document).ready(function(){
    // 初始化加载完成绑定
    $.Theme.loadReady(document);
});


//提示框

// 1. 出现位置， 屏幕上方中央
// 2. 提示样式： 成功、失败、警告、信息
// 3. 提示框存活时间

$.Theme.Tips = {
    success: function(msg) {
        new $.Theme.Tips.Message({msg: msg, type: 'success', placement: 'top-center'})
    },
    warning: function(msg) {
        new $.Theme.Tips.Message({msg: msg, type: 'warning'})
    },
    info: function(msg) {
        new $.Theme.Tips.Message({msg: msg, type: 'info'})
    },
    error: function(msg) {
        new $.Theme.Tips.Message({msg: msg, type: 'error'})
    }
};

$.Theme.Tips.Message = function (options) {
    this.defaultOptions = {
        type:'warning',
        msg: '',
        placement: 'top-center',
        timeout: 2000
    };
    Object.assign(this.defaultOptions, options);
    this.init();

};
$.Theme.Tips.Message.prototype = {
    init: function () {
        var that = this;
        this.builderMessage();
        $.Theme.$body.append(this.$message);
        setTimeout(function () {
            that.$message.remove()
        }, this.defaultOptions.timeout);
    },
    builderMessage : function() {
        this.$message = this.builderBox();
        this.$message.addClass(this.defaultOptions.placement)
        this.$message.addClass(this.defaultOptions.type)
        this.$message.append(this.builderType[this.defaultOptions.type]);
        this.$message.append(this.builderMsg());
    },
    builderBox: function() {
        return $('<div class="mzt-message"></div>');
    },
    builderMsg: function() {
        return '<span>'+this.defaultOptions.msg+'</span>';
    },
    builderType: {
        success: '<i class="mzt-icon mzt-ok" ></i>',
        warning: '<i class="mzt-icon mzt-warninfo"></i>',
        info: '<i class="mzt-icon mzt-gantanhao"></i>',
        error: '<i class="mzt-icon mzt-cuowu-"></i>',
    }
};

$.Theme.Base = function (element) {
    this.$element       = $(element);
    this.webDomain      = window.webDomain;
};

$.Theme.Base.prototype = {
    dom: function () {
        var obj = {}, _self = this;
        obj.elem = _self.$element;

        obj.attr = function (attrName) {
            return obj.elem.attr(attrName);
        };
        obj.val = function () {
            return obj.elem.val();
        };
        obj.data = function (attrName, vlaue) {
            if(vlaue) {
                obj.elem.data('mzt-' + attrName, JSON.stringify(vlaue));
            } else {
                var val = obj.elem.data('mzt-' + attrName);
                return !val ? undefined : (function () {
                    if(typeof val === 'string') {
                        if(val.indexOf('}') > 0) {
                            return eval('(' + val + ')')
                        } else {
                            return val
                        }
                    }
                    return val
                })();
            }
        };
        return obj;
    },
    getFromDOM : function () {
        var options = {};
        options['target'] = this.dom().attr('target') || undefined;
        options['title'] = this.dom().attr('title') || undefined;
        options['text'] = this.dom().attr('data-text') || undefined;
        options['id'] = this.dom().attr('data-id') || undefined;
        options['template'] = this.dom().attr('template') || undefined;
        options['closeText'] = this.dom().attr('closeText') || undefined;
        options['confirmText'] = this.dom().attr('confirmText') || undefined;
        options['radio'] = this.dom().attr('data-radio') || undefined;
        return options;
    }
};

$.Theme.Ladda = {
    create: function (element) {
        return new $.Theme.Loading(element);
    }
}

$.Theme.Loading = function (element) {
    this.$element = $(element);
    this.beforeContent = this.$element.html()
    return this;
};

$.Theme.Loading.prototype = {
    stop: function () {
        this.$element.data('loading', false);
        this.$element.removeAttr('disabled');
        this.$element.html(this.beforeContent);
    },
    isLoading: function () {
        return this.$element.data('loading');
    },
    start: function () {
        this.$element.data('loading', true);
        this.$element.attr('disabled', 'disabled');
        this.$element.html('<i class="iconfont mzt-icon icon-loading mzt-loading"></i>');
    },
    callFunction: function (callback) {
        if (!this.isLoading() && typeof callback === 'function') {
            this.start();
            callback(this)
        }
    }
};

$.Theme.lazyload = function (container) {
    $(container).find('[data-lazy]').each(function () {
        $(this).show().lazyload({effect : 'fadeIn', effect_params: [400], placeholder : window.GLOBAL_CONFIG.siteMapUrl, container: container });
    })
};

/*$(document).ready(function(){
    jQuery('#product-search #mzt-search').liveSearch({
        url: window.webDomain + '/suggest'
    });
    // 初始化加载完成绑定
    $.Theme.loadReady(document);
});*/

function isLoginState() {
    let flag = false;
    $.ajax({
        type: 'GET',
        dataType: 'JSON',
        async: false,
        contentType: "application/json",
        url: window.GLOBAL_CONFIG.mainUrl + '/isLogin',
        success: function (res) {
            if (res.data.code == 200) {
                flag = true;
            }
        }
    })
    return flag;
}
// 重写全局 ajax请求 控制login
(function($){
    var laddaObj = '';
    $(document).on('click', '*[mzt-loading]', function (e) {
        e.preventDefault();
        laddaObj = $.Theme.Ladda.create(this);
        if(laddaObj.isLoading()) {
            return false;
        }
        if($(this).attr('mzt-click')) {
            eval($(this).attr('mzt-click'));
        } else {
            $(this).trigger('mzt.submit.click', [this]);
        }
        return false;
    });
    //备份jquery的ajax方法
    var _ajax = $.ajax;
    //重写jquery的ajax方法
    $.ajax = function(opt){
        //备份opt中error和success方法
        var fn = {
            error:function(XMLHttpRequest, textStatus, errorThrown){},
            success:function(data, textStatus){}
        }
        if(opt.error){
            fn.error = opt.error;
        }
        if(opt.success){
            fn.success = opt.success;
        }
        //扩展增强处理
        var _opt = $.extend(opt,{
            beforeSend: function() {
                if(laddaObj) {
                    laddaObj.start();
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                if(laddaObj) {
                    laddaObj.stop();
                }
                if(!'abort' === textStatus) {
                    //错误方法增强处理
                    $.Theme.Tips.error('服务器内部错误，请联系运维人员。', '操作失败');
                }
                if(XMLHttpRequest.status === 403) {
                    // 服务器拒绝请求。跳转登录页面
                    location.href = window.webDomain + "/manage/home";
                } else if(XMLHttpRequest.status === 500) {
                    $.Theme.Tips.error('服务器内部错误，请联系运维人员。', '操作失败');
                } else if(XMLHttpRequest.status === 401) {
                    $.Theme.Tips.error('未经授权拒绝访问', '操作失败');
                }

                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            success:function(data, textStatus){
                if(laddaObj) {
                    // laddaObj.stop();
                }
                //成功回调方法增强处理
                fn.success(data, textStatus);
            }
        });
        return _ajax(_opt);
    };
})(jQuery);
// //
+(function ($) {
    var MZTTips = function (element) {
        var that         =        this,
            mzt          =        new $.Theme.dom(element);
        this.$element    =        $(element);
        this.options = mzt.data('mzt-tips');

        this.defaultOptions = {
            type:'tip',
            msg: '2323'
        };
        Object.assign(this.defaultOptions, this.options);
        this.init();

    };
    MZTTips.prototype = {
        init: function () {
            var that = this;
            this.$element.on('click', function () {
                that[that.defaultOptions.type].call(that)
            })
        },
        tip: function () {
            var $body = $.Theme.$body;
            var $tooltip =
                $body.append(tooltip);
            setTimeout(function () {
                $tooltip.remove()
            }, 2000);
        }
    };
    $.Theme.addReadyMethod('mzt-tips', function (element) {
        $(element).find('div[data-mzt-tips]').each(function () {
            new MZTTips(this);
        });
    });
})
(jQuery);

// 幻灯片
+(function ($) {
    /**
     * @return {boolean}
     */
    var MZTSwiper = function (element, options) {
        var that         =        this,
            mzt          =        new $.Theme.dom(element);
        this.$element    =        $(element);

        if(mzt.isReady()) {
            return false
        }

        this.defaultOptions = {
            el : '.swiper-container'
            , slidesPerView : 'auto'
            , spaceBetween : 0
            , autoplay : false
            , loop : false
            , allowSlideNext : true
        };

        options     =    mzt.data('mzt-swiper') || options;

        if(typeof options === 'string') {
            this.defaultOptions.el = options;
        } else {
            $.extend(this.defaultOptions, options);
        }
        var mySwiper = new Swiper(this.defaultOptions.el, {
            loop:that.defaultOptions.loop,
            autoplay:that.defaultOptions.autoplay,
            slidesPerView: that.defaultOptions.slidesPerView,
            spaceBetween: that.defaultOptions.spaceBetween,
            keyboard: {
                enabled: true
            },
            pagination: '.swiper-pagination',
            paginationClickable: true,
            nextButton: '.swiper-button-next',
            prevButton: '.swiper-button-prev',
            observer:true,//修改swiper自己或子元素时，自动初始化swiper
            observeParents:true//修改swiper的父元素时，自动初始化swiper
        });
        this.$element.find('.swiper-slide').mouseenter(function () {
            if(mySwiper.autoplay) {
                mySwiper.autoplay.stop();
            }
        });
        this.$element.find('.swiper-slide').mouseleave(function () {
            if(mySwiper.autoplay) {
                mySwiper.autoplay.start();
            }
        });
    };

    $.Theme.addReadyMethod('mzt-swiper', function (element) {
        $(element).find('div[data-mzt-swiper]').each(function () {
            new MZTSwiper(this);
        });
    });
})(jQuery);



// close
$(document).ready(function(){
    var $close = $('.mzt-close');
    $close.click(function(){
        $close.parent().hide();
        $close.show();
    });
});

// 倒计时
+(function ($) {
    /**
     * @return {boolean}
     */
    var  MZTCountdown = function (element, options) {
        var mzt          =        new $.Theme.dom(element);
        this.$element    =        $(element);
        if(mzt.isReady()) {
            return false
        }
        this.defaultOptions = {
            // 语言
            lang: 'en',
            // 结束时间
            end: '',
            // 几秒跳一次
            timeout: 1,
            // 回调函数
            callback: function () {}
        };

        options     =    mzt.data('mzt-countdown') || options;
        if(typeof options === 'string') {
            this.defaultOptions.end = options;
        } else {
            $.extend(this.defaultOptions, options);
        }

        this.init()
    };
    MZTCountdown.prototype = {
        init : function () {
            var that     =     this;
            this.currentTimer = this.formatData();
            this.timer = setInterval(function(){
                that.countdown();
            },  this.defaultOptions.timeout * 1000);
        },
        formatData : function() {
            return  new Date(this.defaultOptions.end);
        },
        countdown : function () {
            //未来时间距离现在的秒数
            this.second       =        Math.floor((this.currentTimer - new Date()) / 1000),
                //整数部分代表的是天；一天有24*60*60=86400秒 ；
                this.day          =        Math.floor(this.second / 86400),
                //余数代表剩下的秒数；
                this.second       =        this.second % 86400;
            //整数部分代表小时；
            this.hour         =        Math.floor(this.second / 3600),
                //余数代表 剩下的秒数；
                this.second      %=        3600;
            //整数部分代表分钟；
            this.minute       =        Math.floor(this.second / 60);
            this.second      %=        60;

            this.$element.html(this.builderDom());

            if(this.isEnd()){
                clearInterval(this.timer);
                // 时间结束后隐藏
                this.$element.hide();
                // 回调函数
                this.defaultOptions.callback(this.$element);
            }
        },
        // 判断时间是否结束
        isEnd: function() {
            return this.day === 0 && this.hour === 0 && this.minute === 0 && this.second === 0 || ( this.second < 0 )
        },
        builderDom : function() {
            var htmlDomStr = [];
            htmlDomStr.push('<span>'+ $.Theme.Util.singleDigitZero(this.day) +'</span>'+ this.language[this.defaultOptions.lang].d);
            htmlDomStr.push('<span>'+ $.Theme.Util.singleDigitZero(this.hour)+'</span>'+ this.language[this.defaultOptions.lang].h);
            htmlDomStr.push('<span>'+ $.Theme.Util.singleDigitZero(this.minute)+'</span>'+ this.language[this.defaultOptions.lang].m);
            htmlDomStr.push('<span>'+ $.Theme.Util.singleDigitZero(this.second)+'</span>'+ this.language[this.defaultOptions.lang].s);
            return htmlDomStr.join('')
        },
        language: {
            en: {
                d: 'd',
                h: 'h',
                m: 'm',
                s: 's',
            },
            zh: {
                d: '天',
                h: '时',
                m: '分',
                s: '秒',
            }
        }
    };

    $.Theme.plugins['MZTCountdown'] = MZTCountdown;
    $.Theme.addReadyMethod('MZTCountdown', function (element) {
        $(element).find('div[data-mzt-countdown]').each(function () {
            new MZTCountdown(this);
        });
    });
})(jQuery);

// 添加购买数量
+(function($) {
    /**
     * @return {boolean}
     */
    var MZTWanSpinner = function (element, options) {
        var mzt          =        new $.Theme.dom(element);
        this.$element    =        $(element);
        if(mzt.isReady()) {
            return false
        }

        this.defaultOptions = {
            maxValue: 99,
            minValue: 1,
            inputWidth: 58,
            step: 1,
            start: 1,
            plusClick: function(element, val) {
                return true;
            },
            minusClick: function(element, val) {
                return true;
            },
            exceptionFun: function(exp) {
                return true;
            },
            valueChanged: function(element, val) {
                return true;
            }
        };

        options     =     options || mzt.data('data-mzt-wanspinner');
        if(typeof options === 'string') {
            this.defaultOptions.maxValue = options
        } else {
            $.extend(this.defaultOptions, options);
        }
        this.$element.WanSpinner(this.defaultOptions);
    };

    $.Theme.plugins['MZTWanSpinner'] = MZTWanSpinner;

    $.Theme.addReadyMethod('MZTWanSpinner', function (element) {
        $(element).find('div[data-mzt-wanspinner]').each(function () {
            new MZTWanSpinner(this);
        });
    });
})(jQuery);

// 邮件订阅-带验证码
+(function ($) {
    /**
     * @return {boolean}
     */
    var  MZTSubscription = function (element, options) {
        var mzt          =        new $.Theme.dom(element);
        this.$element    =        $(element);
        if(mzt.isReady()) {
            return false
        }
        this.defaultOptions = {
            // 语言
            btn: 'button'
        };
        options     =    mzt.data('mzt-subscription') || options;
        if(typeof options === 'string') {
            this.defaultOptions.btn = options;
        } else {
            $.extend(this.defaultOptions, options);
        }
        this.init();
        this.$element.removeAttr('disabled');
    };
    MZTSubscription.prototype = {
        init : function () {
            this.valid();
            this.$element.on('mzt.submit.click', this.defaultOptions.btn, function () {
                $(this).submit()
            });
        },
        valid : function () {
            var that = this;
            this.$element.validate({
                rules:{
                    email:{
                        required:true,
                        email:true
                    },
                    code: {
                        required:true
                    }
                },
                submitHandler : function() {
                    $.post(window.GLOBAL_CONFIG.mainUrl + '/subscription', that.$element.serialize(), function (res) {
                        if(res.success) {
                            that.$element[0].reset();
                            $.Theme.Tips.success('Subscription successful')
                        } else {
                            $.Theme.Tips.error(res.message)
                        }
                        if(res.data.viewCode) {
                            that.$element.find('.capcha-container').show();
                            $.Theme.draw.webform.reloadKaptcha(that.$element.find('.capcha-container'))
                        }
                    })
                }
            });
        }
    };
    $.Theme.addReadyMethod('MZTSubscription', function (element) {
        $(element).find('form[data-mzt-subscription]').each(function () {
            new MZTSubscription(this);
        });
    });
})(jQuery);


// 邮件订阅-不带验证码
+(function ($) {
    /**
     * @return {boolean}
     */
    var  MZTSubscriptionNoCode = function (element, options) {
        var mzt = new $.Theme.dom(element);
        this.$element = $(element);
        if(mzt.isReady()) {
            return false
        }
        this.defaultOptions = {
            // 语言
            btn: 'button'
        };
        options = mzt.data('mzt-subscription') || options;
        if(typeof options === 'string') {
            this.defaultOptions.btn = options;
        } else {
            $.extend(this.defaultOptions, options);
        }
        this.init();
        this.$element.removeAttr('disabled');
    };
    MZTSubscriptionNoCode.prototype = {
        init : function () {
            this.valid();
            this.$element.on('mzt.submit.click', this.defaultOptions.btn, function () {
                $(this).submit()
            });
        },
        valid : function () {
            var that = this;
            this.$element.validate({
                rules:{
                    email:{
                        required:true,
                        email:true
                    }
                },
                submitHandler : function() {
                    $.post(window.GLOBAL_CONFIG.mainUrl + '/subscription', that.$element.serialize(), function (res) {
                        if(res.success) {
                            that.$element[0].reset();
                            $.Theme.Tips.success('Subscription successful')
                        } else {
                            $.Theme.Tips.error(res.message)
                        }
                    })
                }
            });
        }
    };
    $.Theme.addReadyMethod('MZTSubscriptionNoCode', function (element) {
        $(element).find('form[data-mzt-subscription-no-code]').each(function () {
            new MZTSubscriptionNoCode(this);
        });
    });
})(jQuery);


// 图片上传器
+(function ($) {
    var MZTUploader = function (element) {
        var mzt          =        new $.Theme.dom(element);
        this.$element    =        $(element);
        this.$curNum     =        this.$element.find('[data-cur-num]');
        this.maxNum     =         this.$element.find('[data-max-num]').data('max-num');

        if(mzt.isReady()) {
            return false
        }
        this.defaultOptions = {
            fileUl: '.mzt-upload_queue',
            uploader: '.upload-img-box',
            inputName: 'imgUrl',
            delBtn: '.mzt-icon.mzt-shanchu'
        };
        var options     =    mzt.data('mzt-uploader');
        if(typeof options === 'string') {
            this.defaultOptions.inputName = options;
        } else {
            $.extend(this.defaultOptions, options);
        }

        this.$uploader = this.$element.find(this.defaultOptions.uploader);

        this.init();
    };
    MZTUploader.prototype = {
        init : function () {
            var that = this;
            this.$element.on('change', 'input[type="file"]', function () {
                that.uploader(this);
            });
        },
        uploader : function (file) {
            if(file.files.length === 0) {
                return
            }
            var that = this;
            var formData = new FormData();
            formData.append("file", file.files[0]);
            $.ajax({
                url: window.GLOBAL_CONFIG.mainUrl+'/webFinder/upload',
                data: formData,
                type: 'post',
                cache: false,
                processData: false,
                contentType: false,
                success: function (res) {
                    if(res.state == 200) {
                        that.insertUploaderFinishFile(res.data)
                    }
                }
            })
        },
        insertUploaderFinishFile: function (file) {
            var that = this;
            var $fileLi = this.builderFileLi().clone();
            var $fileStatus = this.builderFileStatus().clone();
            $fileLi.find('input')
                .attr('name', this.defaultOptions.inputName)
                .attr('value', file.url);
            $fileLi.find('img')
                .attr('src', file.url);


            $fileLi.append($fileStatus);

            $fileStatus.on('click', this.defaultOptions.delBtn, function () {
                $fileLi.remove();
                that.calculateQuantity();
            });

            this.$element.find(this.defaultOptions.fileUl).append($fileLi);
            this.calculateQuantity();
        },

        calculateQuantity : function () {
            var fileLength =  this.$element.find(this.defaultOptions.fileUl).find('li').length;
            if(fileLength === this.maxNum) {
                this.$uploader.hide();
            } else {
                this.$uploader.show();
            }
            this.$curNum.text(fileLength);
            this.$curNum.data('cur-num', fileLength);
        },

        builderFileLi: function () {
            return $(' <li class="g-u up-success upload-img-box" >\n' +
                '                                            <div class="picli">\n' +
                '                                                <a href="javascript:void(0);">\n' +
                '                                                    <img class="preview-imgli" src="" width="60">\n' +
                '                                                    <input name="" value=""  type="hidden"/>\n' +
                '                                                </a>\n' +
                '                                            </div>\n' +
                '                                        </li>');
        },
        builderFileStatus: function () {
            return $('' +
                ' <div class="status-wrap">\n' +
                '                                                <div class="picli-mask st-success"></div>\n' +
                '                                                <div class="mzt-icon mzt-shanchu"></div>\n' +
                '                                                <div class="st-error">\n' +
                '                                                    <span class="picli-error">Fail</span></div>\n' +
                '                                                <div class="st-progress">\n' +
                '                                                    <span class="picli-porgress">...</span>\n' +
                '                                                </div>\n' +
                '                                            </div>')
        }
    };
    $.Theme.addReadyMethod('MZTUploader', function (element) {
        $(element).find('div[data-mzt-uploader]').each(function () {
            new MZTUploader(this);
        });
    });
})(jQuery);


// 扩展 Bootstrap 标签页支持切换动态加载数据
// 标签组 包含 data-pajax 属性值为跳转地址 则激活动态标签
+(function($) {
    $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
        var $element = $(e.target);


        if($element[0].hasAttribute('data-pajax')) {

            var pajax = $element.attr('data-pajax');

            if(pajax && !$element.data('refresh')) {

                $.get(window.GLOBAL_CONFIG.mainUrl +  pajax, function (html) {

                    $($element.attr('href')).html(html);
                    $.Theme.draw.raty();
                    $.Theme.draw.pagination({
                        container: $element.attr('href')
                    });
                    $element.data('refresh', true);
                })
            }
        }
    });
})(jQuery);

$.Theme.draw = {};

$.Theme.draw.raty = function (element) {
    element = element || '[data-mzt-raty]';

    $(element).each(function () {
        var dom          =        new $.Theme.dom(this);
        var $element     =        dom.$element;
        this.defaultOptions = {
            cancelOff:    $.Theme.res('cancel-off.png'),
            cancelOn:     $.Theme.res('cancel-on.png'),
            starHalf:     $.Theme.res('star-half.png'),
            starOff:      $.Theme.res('star-off.png'),
            starOn:       $.Theme.res('star-on.png'),

            readOnly: true,
            score: dom.data('score'),
            cancel: false,
            targetKeep: true,
            targetType: 'number'
        };

       var options = dom.data('mzt-raty');

        if(typeof options === 'string') {
            this.defaultOptions.readOnly = options;
        } else {
            $.extend(this.defaultOptions, options);
        }
        if(this.defaultOptions.readOnly) {
            this.defaultOptions.precision = true;
            if(this.defaultOptions.score || this.defaultOptions.score == 0) {
                $element.raty(this.defaultOptions);
            }
        }
        else {
            this.defaultOptions.score = 0;
            $element.raty(this.defaultOptions);
        }
    })

};

$.Theme.draw.pagination = function (options) {
    var JqueryPagination = function (element) {
        var mzt          =        new $.Theme.dom(element);
        this.$element    =        $(element);

        this.defaultOptions = {
            // 容器
            container: 'body',
            form: '',
            url: '',
            pageCount: 0,
            current: 1,
            loading: 'ajax'
        };
        $.extend(this.defaultOptions, mzt.data('mzt-pagination'), options);

        this.init = function () {
            var that = this;
            this.$element.pagination({
                pageCount: this.defaultOptions.pageCount,
                current: this.defaultOptions.current,
                jump: false,
                coping: true,
                isHide: true,
                homePage: '',
                endPage: '',
                prevContent: '<',
                nextContent: '>',
                callback: function (page) {
                    that[that.defaultOptions.loading](page);
                }
            });
        };
        this.valid = function () {
            this.$container     =        $(this.defaultOptions.container);
            this.$form          =        this.$container.find(this.defaultOptions.form);
            if (this.$container.length  === 0) {
                console.error('异步加载， 数据容器dom不存在 【'+ this.defaultOptions.container + '】');
                console.dir(this.$container);
                return false;
            }
            if (this.$form.length  === 0) {
                console.error('异步加载，分页表单不存在 【'+ this.defaultOptions.form + '】');
                console.dir(this.$form);
                return false;
            }
            if (!this.$form.attr('action') && !this.defaultOptions.url) {
                console.error('异步加载，分页表单属性[action] 或者 options.url 必须填写 ');
                return false;
            }
            return true;
        };
        this.url = function () {
            return this.$form.attr('action') || this.defaultOptions.url
        };
        this.scrollTop = function () {
            if (this.defaultOptions.scrollTop) {
                var scrollTopEl = this.$container;
                if(this.defaultOptions.scrollTop !== true) {
                    scrollTopEl = $(this.defaultOptions.scrollTop)
                }

                $("html, body").animate({
                    scrollTop: scrollTopEl.offset().top + 'px'
                }, {
                    duration: 500,
                    easing: "swing"
                });
            }
        };
        this.self = function (page) {
            if(!this.valid()) {
                return
            }
            this._input(page);
            this.$form.submit();
        };
        this._input = function (page) {
            this.$form.find('input[name="p"]').val(page.getCurrent());
        };

        this.reload = function (current) {
            if(!this.valid()) {
                return
            }
            this.$form.find('input[name="p"]').val(current);
            this._get();
        };

        this.ajax = function (page) {

            if(!this.valid()) {
                return
            }
            this._input(page);
            this._get();
        };

        this._get = function () {
            var that = this;
            $.get(this.url(), this.$form.serialize(), function (html) {
                that.$container.html(html);
                $.Theme.draw.raty();
                that.scrollTop();
                $.Theme.draw.pagination({
                    container: that.$container
                })
            })
        };

        this.init();
    };
    if(options && options.container) {
        var $pagination = $(options.container).find('div[data-mzt-pagination]');
        $pagination.each(function () {
            var $that = $(this);
            $that.data('plugin', new JqueryPagination($that, options));
        })
    }
};

$.Theme.draw.dropdown = function () {
    var caret = '<span class="caret"></span>';

    $('div[data-mzt-dropdown]').each(function () {
       var $dropdown =  $(this);
        $dropdown.on('click', '.dropdown-menu li', function () {
            var that = $(this);
            var $input = $('<input name="'+$dropdown.attr('data-mzt-dropdown')+'" type="hidden">');
            $input.val(that.data('value'));
            $dropdown.find('[data-toggle="dropdown"]').html($input).append(that.text()).append(caret);
        })
    });
};

// 收藏
$.Theme.draw.collection = function() {
    $('body').on('click', 'a[data-mzt-collection]', function () {
        // 判断是否登录
        if(!isLoginState()){
            window.location.href = window.GLOBAL_CONFIG.mainUrl+'/login';
            return false;
        }
        var that = $(this), $p = $(this).parent();
        var productId = that.attr('data-mzt-collection')
         $.get(window.GLOBAL_CONFIG.mainUrl + '/product/'+productId+'/isCollection', function (html) {
             $p.html(html)
             if($.Theme.config.navUserContainer) {
                 $.Theme.parts.reload($.Theme.config.navUserContainer, 'nav-user')
             }
         })
    })
}

// 加入购物车
$.Theme.draw.addCart = function() {
    $('body').on('click', 'a[data-mzt-cart]', function () {
        // 判断是否登录
        if(!isLoginState()){
            window.location.href = window.GLOBAL_CONFIG.mainUrl+'/login';
            return false;
        }
        var that = $(this), $p = $(this).parent();
        var productId = that.attr('data-mzt-cart')
        $.ajax({
            type: 'POST',
            dataType: 'JSON',
            async: false,
            contentType: "application/json",
            url: window.GLOBAL_CONFIG.mainUrl+'/product/' + productId + '/addCart',
            success: function (res) {
                console.log(res)
                if (res.state == 200) {
                    if($.Theme.config.navUserContainer) {
                        $.Theme.parts.reload($.Theme.config.navUserContainer, 'nav-user')
                    }
                    $.Theme.Tips.error('Added successfully')
                } else {
                    $.Theme.Tips.warning(res.message)
                }
            }
        })
    })
}

$.Theme.config = {
    navUserContainer: '.mzt-header_box .head-acount'
}


$.Theme.draw.webform = function () {

    this.reKaptcha = function ($obj) {
        $obj.attr('src', window.GLOBAL_CONFIG.mainUrl+'/kaptcha?' + Math.floor(Math.random() * 100)).fadeIn();
    };
    this.kaptcha = function () {
        var that = this;
        $('body').on('click', 'img[data-kaptcha]', function () {
            that.reKaptcha($(this))
        })
    };
    this.init = function () {
        this.kaptcha();
    };
    this.init();
};
$.Theme.draw.webform.reloadKaptcha = function ($capchaContainer) {
    new $.Theme.draw.webform().reKaptcha($capchaContainer.find('img[data-kaptcha]'))
};


$.Theme.draw.ready = function () {
    this.dropdown();
    this.collection();
    this.addCart();
    this.pagination();
    this.webform();
    this.raty();
};

+(function ($) {
    $.Theme.addReadyMethod('data-mzt-raty', function () {
        $.Theme.draw.ready();
    });
})(jQuery);

$.Theme.parts = {
    reload: function(container, parts) {
        $.get(window.GLOBAL_CONFIG.mainUrl + '/parts/' + parts, function (html) {
            $(container).html(html);
        });
    }
};


window.onload = function(){
    var vu = document.getElementsByClassName('skeleton')
    for(var i = 0;i<vu.length;i++) {
        var son = vu[i];
        setson(son);
    }
    function setson(son) {
        setTimeout(function(){
            addClass(son,'skeleton');
            setTimeout(function(){
                removeClass(son,'skeleton');
            },1000)
        },500)
    }
    function addClass(element,name) {
        if(!element.className.match(new RegExp('(^|\\s)'+name+'(\\s|$)'))) {
            element.className += '' + name;
        }
    }
    function removeClass(element,name) {
        if(element.className.match(new RegExp('(^|\\s)'+name+'(\\s|$)'))) {
            element.className = element.className.replace(new RegExp('(^|\\s)'+name+'(\\s|$)'),'');
        }
    }
};


var Login = function() {
    this.$loginForm = $('#login');
    this.$form = this.$loginForm.find('.login-form');
    this.$submitBtn = this.$loginForm.find('.submit');
    this.$username =  $('.loginName');
    this.$password =  $('.password');
    this.$loginAutoLogin =  $('#loginAutoLogin');

    this.valid = function () {
        var that = this;
        this.$form.validate({
            errorElement: 'strong',
            rules:{
                username:{
                    required:true,
                },
                password: {
                    required:true
                }
            },
            submitHandler : function() {
                that.submit();
            }
        });
    };
    this.submit = function () {
        this.username = this.$username.val();
        this.password = this.$password .val();
        this.loginAutoLogin = this.$loginAutoLogin.val();

        var that = this;
        var str = this.username + '#' + this.password;
        var data = {
            loginName: this.username ,
            password: $.md5(str)
        }
        $.Theme.Ladda.create($('#loginActionBtn')).callFunction(function (loading) {
            $.ajax({
                type: 'POST',
                dataType: 'JSON',
                contentType: "application/json",
                url: window.GLOBAL_CONFIG.mainUrl + '/user/login',
                data: JSON.stringify(data),
                success: function (res) {
                    if (res.state === 200) {
                        if (that.loginAutoLogin) {
                            $.cookie("loginInfo", JSON.stringify({
                                loginName: that.username,
                                password: that.password
                            }), {path: '/', expires: 10})
                        } else {
                            $.removeCookie("loginInfo", {path: '/'})
                        }
                        $.cookie("isLogin", "success");
                        $.cookie("loginName", that.username);
                        // 登录成功之后跳转到个人中心页面
                        location.href = window.GLOBAL_CONFIG.mainUrl + "/client/findSetting"
                    } else {
                        loading.stop()
                        $.Theme.Tips.error(res.message)
                    }
                }
            })
        });
    };
    this.autoLogin = function () {
        var clientJson = eval("(" + $.cookie("loginInfo") + ")");
        if (clientJson !== undefined) {
            this.$username.val(clientJson.loginName);
            this.$password.val(clientJson.password);
            this.$loginAutoLogin.attr("checked", true);
        } else if ($.cookie("loginName") !== undefined) {
            this.$username.val($.cookie("loginName"));
        }
    };
    this.keydown = function () {
        var that = this;
        this.$loginForm.bind("keydown", function (e) {
            // 兼容FF和IE和Opera
            var theEvent = e || window.event;
            var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
            if (code === 13) {
                //回车执行查询
                that.$form.submit();
                return false;
            }
        });
    };
    this.init = function () {
        var that = this;
        var search = {cId: ''};
        this.autoLogin();
        this.keydown();
        this.valid();

        this.$loginForm.find('a.jumpResetPassword').on('click', function () {
            if (that.$username.val() !== '') {
                $.cookie("loginName", that.$username.val());
            }
            const paramObj = $.Theme.Util.getParams(window.location.href)
            Object.assign(search, paramObj)
            if(search.cId){
                window.location.href = window.GLOBAL_CONFIG.mainUrl + '/resetPassword?cId='+search.cId;
            }else{
                window.location.href = window.GLOBAL_CONFIG.mainUrl + '/resetPassword';
            }
        });

        this.$submitBtn.on('click', function () {
            that.$form.submit();
        });
    };
    this.init();
};

var registered = function() {
    this.$loginForm = $('#register');
    this.$form = $('.registered-form');
    this.$submitBtn = this.$form.find('.submit');
    this.$email =  $('.email');
    this.$password =  $('.password');
    this.$phoneName = $('.phoneName');
    this.$confirmPassword =  $('.confirmPassword');
    this.$sourceUserId =  $('#sourceUserId');
    this.$verification =  $('.verification');
    this.$codeBtn = this.$form.find('.close_tel');
    this.$country =  $('#country');

    // 获取验证码
    var InterValObj; //timer变量，控制时间
    var count = 60; //间隔函数，1秒执行
    var curCount; //当前剩余秒数

    this.valid = function () {
        var that = this;
        this.$form.validate({
            errorElement: 'strong',
            rules:{
                email:{
                    required:true,
                    email: true
                },
                phone:{
                    required:true
                },
                country:{
                    required:true
                },
                password: {
                    required:true
                },
                confirmPassword: {
                    equalTo: "#password"
                },
                verification: {
                    required:true
                }
            },
            submitHandler : function() {
                that.submit();
                // that.sendMessage();
                // that.SetRemainTime();
            }
        });
    };
    this.submit = function () {
        this.email = this.$email.val();
        this.password = this.$password.val();
        this.phone = this.$phoneName.val();
        this.country = this.$country.val();
        this.confirmPassword = this.$confirmPassword.val();
        this.sourceUserId = this.$sourceUserId.val();
        this.verification = this.$verification.val();

        var that = this;
        var str = this.email + '#' + this.password;
        var data = {
            email: this.email ,
            password: $.md5(str),
            sourceUserId: this.sourceUserId ,
            code: this.verification ,
            phone: this.phone,
            clientSurname: '',
            clientName: '',
            country: this.country
        };
        $.Theme.Ladda.create($('#submit')).callFunction(function (loading) {
            $.ajax({
                type: 'POST',
                dataType: 'JSON',
                contentType: "application/json",
                url: window.GLOBAL_CONFIG.mainUrl + '/user/doUserRegistration',
                data: JSON.stringify(data),
                success: function (res) {
                    loading.stop()
                    if (res.state === 200) {
                        $.Theme.event.completeRegistration()
                        $.Theme.Tips.success('Registered successfully');
                        setTimeout(function () {
                            // 注册成功之后跳转到个人中心页面
                            location.href = window.GLOBAL_CONFIG.mainUrl + "/client/findSetting"
                        }, 1000)
                    } else if (res.state === 203) {
                        $.Theme.Tips.error(res.message);
                        $.cookie("loginName", that.email);
                    } else {
                        $.Theme.Tips.error('Registration Failed');
                    }
                }
            });
        });
    };
    this.sendMessage = function () {
        var that = this;
        that.email = that.$email.val();

        //向后台发送处理数据
        if(that.email === ''){
            $.Theme.Tips.error('E-mail cannot be blank')
            return false;
        }
        $("#btnSendCode").attr('disabled',true);
        $.ajax({
            type: "GET", //用POST方式传输
            dataType: "JSON", //数据格式:JSON
            url: window.GLOBAL_CONFIG.mainUrl+'/user/getSysHelpCenterType/'+ that.$email.val(), //目标地址
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $("#btnSendCode").removeAttr("disabled");//启用按钮
            },
            success: function (msg){
                curCount = count;
                //设置button效果，开始计时
                $("#btnSendCode").val(curCount + " s");
                InterValObj = window.setInterval(that.SetRemainTime, 1000); //启动计时器，1秒执行一次
            }
        });
    };
    this.SetRemainTime = function () {
        if (curCount === 0) {
            window.clearInterval(InterValObj);//停止计时器
            $("#btnSendCode").removeAttr("disabled");//启用按钮
            $("#btnSendCode").val('Get code again');
        } else {
            curCount--;
            $("#btnSendCode").val(curCount + " s");
        }
    };
    this.getCountryList = function () {
        var countryList = []
        var that = this;
        $.ajax({
            type: "GET",
            url: window.GLOBAL_CONFIG.mainUrl + '/getCountryList',
            success: function (res){
                countryList = res;
                var html = '<option value="">Choose a country</option>'
                for (var i =0; i<countryList.length;i++){
                    console.log(countryList)
                    html += '<option value="'+countryList[i].countryCode+'">'+countryList[i].countryName+'</option>'
                }
                that.$country.append(html)
            }
        });
    }
    this.init = function () {
        var that = this;

        this.valid();

        this.$codeBtn.on('click', function () {
            that.sendMessage();
        });

        this.$submitBtn.on('click', function () {
            that.$form.submit();
        });
        this.getCountryList();
    };
    this.init();

};

function auth(value) {
    var self = window.open('','','width=450,height=500,top=100, left=100,menubar=no,toolbar=no,status=no,scrollbars=yes');
    $.ajax({
        type: 'POST',
        dataType: 'JSON',
        async: false,
        contentType: "application/json",
        url: window.GLOBAL_CONFIG.mainUrl+'/webAuthorization/render/'+value,
        success: function (res) {
            if(res.state == 200) {
                self.location = res.data
            }else{
                self.parent.close()
                $.Theme.Tips.error(res.message)
            }
        }
    })
}
$.Theme.event = {
    /**
     * 添加支付信息 对应 支付流程 Shipping -> Continue Shipping 按钮
     *  $.Theme.event.addPaymentInfo()
     * @constructor
     */
    'addPaymentInfo' : function () {
        if(window.fbq){
            fbq('track', 'AddPaymentInfo');
        }
        if(window.gtag) {
            gtag('event', 'add_payment_info', {'event_category': 'ecommerce'});
        }
    },
    // 添加收藏
    'addToWishlist': function () {
        if(window.fbq){
            fbq('track', 'AddToWishlist');
        }
        if(window.gtag) {
            gtag('event', 'add_to_wishlist', {'event_category': 'ecommerce'});
        }
    },
    //  加入购物车:
    'addToCart' : function () {
        if(window.fbq){
            fbq('track', 'AddToCart');
        }
        if(window.gtag) {
            gtag('event', 'add_to_cart', {'event_category': 'ecommerce'});
        }
    },
    /**
     * 发起结账
     *  对应 购物车结算按钮或商品 直接购买按钮
     * @constructor
     */
    'initiateCheckout' : function () {
        if(window.fbq){
            fbq('track', 'InitiateCheckout');
        }
        if(window.gtag) {
            gtag('event', 'begin_checkout', { 'event_category': 'ecommerce'});
        }
    },
    /**
     * 付款成功 对应支付流程 支付按钮
     *
     * @param price 单位元
     * @param currency USD
     * @constructor
     */
    'purchase' : function (price, currency) {
        if(window.fbq){
            fbq('track', 'Purchase', {value: price, currency: currency});
        }
        if(window.gtag) {
            gtag('event', 'purchase', {'event_category': 'ecommerce'});
        }
    },
    /**
     * 完成注册
     */
    'completeRegistration' : function () {
        if(window.fbq){
            fbq('track', 'CompleteRegistration');
        }
        if(window.gtag) {
            gtag('event', 'sign_up', {'event_category': 'engagement', 'event_label': 'method'});
        }
    },

}




