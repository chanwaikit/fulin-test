//footer 展开显示
(function (win, doc) {
    function changeSize() {
        doc.documentElement.style.fontSize = doc.documentElement.clientWidth / 375*20 + 'px';
    }
    changeSize();
    win.addEventListener('resize', changeSize, false);
})(window, document);

$(document).ready(function(){
    $(window).scroll(function () {
        if($(this).scrollTop() > 70 && $('body').height() > $(window).height() + $('.mzt-header_box').height() + 100) {
            $('.mzt-header_box').addClass('fixed');
        }else {
            $('.mzt-header_box').removeClass('fixed');
        }
        var proInfoDetail_offsetTop = ($('.mzt-goods_detail').offset() || { "top": NaN }).top;
        if ( $(this).scrollTop() > proInfoDetail_offsetTop - 100 ) {
            $('.mzt-goods_detail .nav.nav-tabs').addClass('tabbar_wrap_fixed');
            $('.mzt-header_box').hide();
        }else {
            $('.mzt-header_box').show();
            $('.mzt-goods_detail .nav.nav-tabs').removeClass('tabbar_wrap_fixed');
        }
    });
    if ($(window).width() < 769) {
        var $rotate = $('.mzt-footer .footer-title');
        $rotate.click(function () {
            $(this).toggleClass('active');
            $(this).siblings('.mzt-item_list').toggle(500);
        })
        $('.mzt-submenu .dropdown').removeClass('mzt-dropdown-open');
        $('.mzt-menu-search .mzt-search-icon').on('click',function () {
            $(this).siblings().toggle(500)
        })
        $('.mzt-menu_top .navbar-toggle').on('click', function () {
            var $dom = $(this).parents().find('.navbar-collapse')
            $dom.toggleClass('nav-open')
            if ($dom.hasClass('nav-open')){
                $('html').css({overflow: 'hidden'})
            } else {
                $('html').css({overflow: 'scroll'})
            }
        })
        $('.mzt-menu_top .navbar-nav .mzt-jiantou1').on('click', function () {
            $(this).parent().siblings().show()
        })
        $('.mzt-menu_top .mzt-menu-back').on('click', function () {
            $(this).parent().hide()
        })
        $('.mzt-menu-close').on('click', function () {
            $(this).parent().removeClass('nav-open')
            $('html').css({overflow: 'scroll'})
        })
    }
});
var InfiniteScrolling = {
    Scrolling: function (options) {
        this.defaultOptions = {
            url: '',
            builder: '',
            timeout: 1,
            loading: '',
            pageSize: 10,
            ul: '',
            goOn: '',
            btnMore: '',
            params: {}

        };
        $.extend(this.defaultOptions, options);

        this.init()
    },
    init: function (options) {
        var scrolling = new InfiniteScrolling.Scrolling(options)
        $(options.goOn).on('click', function () {
            scrolling.goOn();
        })
    }
};
InfiniteScrolling.Scrolling.prototype.init = function() {
    this.page = 1;
    this.valve = true;
    this.exeCount = 0;
    this.pageSize = this.defaultOptions.pageSize;
    this.dom = '';
    this.loadComplete = true;
    this.loadMore();
    this.listen();
};

InfiniteScrolling.Scrolling.prototype.listen = function() {
    var $this = this,
        winH = $(window).height(),
        scrollHandler = function () {
            var scrollT = $(window).scrollTop(); //滚动条top
            var footerT = $('.mzt-recommend-top').position().top
            if (scrollT > footerT - winH && $this.loadComplete) {//0.02是个参数
                $this.loadMore();
            }
        };
    //定义鼠标滚动事件
    $(window).scroll(scrollHandler);
};

InfiniteScrolling.Scrolling.prototype.goOn = function() {
    this.valve = true;
    this.exeCount = 0;
    this.loadMore();
};


InfiniteScrolling.Scrolling.prototype.loadMore = function() {
    var $this = this;
    if(!this.valve && this.loadComplete) {
        return;
    }
    var _count = function() {
        $this.loadComplete = true
        $this.exeCount ++;
        $this.page ++;
        $($this.defaultOptions.btnMore).hide();
        $($this.defaultOptions.loading).hide();
        if($this.exeCount > 2) {
            $this.valve = false
            $($this.defaultOptions.btnMore).show();
        }
    };

    this.loadComplete = false;
    $($this.defaultOptions.loading).show();
    $.ajax({
        url: $this.defaultOptions.url,
        data: $.extend({ 'page': $this.page, 'pageSize': $this.pageSize }, $this.defaultOptions.params),
        type: 'GET',
        traditional: true,//这里设置为true
        dataType: 'json',
        success: function (data) {
            _count();
            if (data.state === 200) {
                $this.stop(data.data);
                $this.dom =  $this.builder[$this.defaultOptions.builder](data.data);
                $this.append();
                $this.appendAfter()
            }
        },
        error: function () {
            $($this.defaultOptions.loading).hide();
        }
    });
};

InfiniteScrolling.Scrolling.prototype.stop = function(list) {
    if(list.length === 0) {
        $(this.defaultOptions.loading).hide();
        $(this.defaultOptions.goOn).hide();
        this.valve = false;
    }
};


InfiniteScrolling.Scrolling.prototype.append = function() {
    $(this.defaultOptions.ul).append(this.dom);
};

InfiniteScrolling.Scrolling.prototype.appendAfter = function() {
    $.Theme.draw.raty();
    $.Theme.lazyload(this.defaultOptions.ul);
};

InfiniteScrolling.Scrolling.prototype.builder = {
    'productRecommend': function (recommendList) {
        if(!recommendList) {
            return '';
        }
        var result = '';
        for (var i = 0; i < recommendList.length; i++) {
            let product = recommendList[i]
            result +='<li class="fl mzt-shopProduct_item mzt-mouseInOut">';
            result +='<div class="mzt-shopProduct_img"><a href="'+product.url+'"><img data-lazy data-original="'+product.imgUrl+'!640x640" src="'+window.GLOBAL_CONFIG.siteMapUrl+'"/></a>'
            if (product.price !== product.oldPrice && product.oldPrice !== 0) {
                let discount = parseInt((((product.oldPrice - product.price)/product.oldPrice))*100)
                result += '<div class="hot-sale">\n' +
                    '<span>' + discount + '%</span>\n' +
                    '<small>OFF</small>\n' +
                    '</div>'
            }
            result+='</div>'
            result +='<div class="mzt-shopProduct_text">'
            result +='<a href="'+product.name+'" class="mzt-shopProduct_desc">'+product.name+'</a>'
            result +='<div class="mzt-shopProduct_price">'
            if (product.price === '') {
                result +='<span>$0</span>'
            } else {
                result +='<span>$' + product.price + '</span>'
            }
            if (product.price !== product.oldPrice) {
                result += '<span class="oldPrice">$' + product.oldPrice + '</span>'
            }
            result +='<p>\n' +
                '<div class="mzt-comment_icon p-b15">\n' +
                '<div class="ib" data-score="'+ product.comment.commentScore +'" data-mzt-raty></div>\n' +
                '<span class="mzt-comment_score">\n' +
                '<span class="hint"></span>\n' +
                '(<span>' + product.comment.commentNum +' <span>Reviews</span></span>)\n' +
                '</span>\n' +
                '</div>\n' +
                '</p>'
            result +='<div class="mzt-product_favorites">'
            // 已收藏
            if(product.isCollection){
                result += '<a data-mzt-collection="'+product.id+'" class="mzt-product-icon shoucang-icon"><i class="mzt-icon mzt-icon4"></i></a>'
            }else{
                result += '<a data-mzt-collection="'+product.id+'" class="mzt-product-icon"><i class="mzt-icon mzt-shoucang"></i></a>'
            }
            result += '<a data-toggle="modal" data-target="#simple-product" class="mzt-product-icon mzt-modal-card" style="color: #D50035;margin-left: 5px" data-id="'+product.id+'"> <i class="mzt-icon mzt-icon-"></i></a>'
            result +='</div>'
            result +='</div>'
            result +='</div>'
            result +='</li>';
        }
        return result;
    }
};


$.Theme.plugins['MZTCountdown'].prototype.builderDom = function() {
    var htmlDomStr = [];
    htmlDomStr.push('<span><span class="countdown-num">'+ $.Theme.Util.singleDigitZero(this.day) +'</span><span class="countdown-text">'+this.language[this.defaultOptions.lang].d+'</span></span>');
    htmlDomStr.push('<span><span class="countdown-num">'+ $.Theme.Util.singleDigitZero(this.hour)+'</span><span class="countdown-text">'+this.language[this.defaultOptions.lang].h+'</span></span>');
    htmlDomStr.push('<span><span class="countdown-num">'+ $.Theme.Util.singleDigitZero(this.minute)+'</span><span class="countdown-text">'+this.language[this.defaultOptions.lang].m+'</span></span>');
    htmlDomStr.push('<span><span class="countdown-num">'+ $.Theme.Util.singleDigitZero(this.second)+'</span><span class="countdown-text">'+this.language[this.defaultOptions.lang].s+'</span></span>');
    return htmlDomStr.join('')
};
$.Theme.plugins['MZTCountdown'].prototype.language = {
    en: {
        d: 'DAYS',
        h: 'HRS',
        m: 'MINS',
        s: 'SECS',
    }
}

// vue实例
window.GLOBAL_VUE = {}

var initVue = function() {
    window.GLOBAL_VUE.header = new Vue({
        el: '.header_qyjyx',
        data() {
            return {
                shareUrl: '',
                search: {
                    k: '',
                    c: ''
                },
                activeName: $.Theme.$body.attr('data-page'),
                countryList: [],
                categoryList: [],
                showMenu: false,
                category: ['category'],
                showSearch: false,
                countryName: '',
                countryFlag: ''
            }
        },
        mounted() {
            this.getCategoryList()
            this.getCountryList()
        },
        methods: {
            getRouterUrl() {
                const paramObj = $.Theme.Util.getParams(window.location.href);
                Object.assign(this.search, paramObj);
                if(this.search.country){
                    this.shareUrl = '?country=' + this.search.country
                }
                if(this.search.cId){
                    this.shareUrl += '&cId=' + this.search.cId
                }
            },
            // 获取商品分类
            getCategoryList: function () {
                $.get(window.GLOBAL_CONFIG.mainUrl +'/product/getProductTypes', (res) => {
                    this.categoryList = res
                }, 'json')
            },
            handleSearch(id){
                if (id) {
                    if (id === 'all'){
                        window.open(window.GLOBAL_CONFIG.mainUrl+'/productList?tab=1','_self');
                        return
                    }
                    let url = window.GLOBAL_CONFIG.mainUrl+'/productList?tab=1&c='+id
                    if(this.search.cId){
                        url += '&cId=' +  this.search.cId
                    }
                    window.open(url,'_self');
                } else {
                    let url2 = window.GLOBAL_CONFIG.mainUrl+'/productList?tab=1&c=' + this.search.c + '&k=' + this.search.k
                    if(this.search.cId){
                        url2 += '&cId=' +  this.search.cId
                    }
                    window.open(url2,'_self');
                }
            },
            showMenuPopup() {
                this.showMenu = true
            },
            showSearchPopup() {
                this.showSearch = true
            },
            // 获取支持国家
            getCountryList () {
                $.get(window.GLOBAL_CONFIG.mainUrl + '/getCountryList', res => {
                    this.countryList = res
                    if(!this.countryName){
                        this.getCountryById()
                    }
                    this.setCountrySession()
                    this.countryList.forEach(item => {
                        if(this.search.country === item.countryCode){
                            this.countryName = item.countryName
                            this.countryFlag = item.flag
                        }
                    })
                }, 'json')
            },
            // 处理国家
            handleCountrySearch(country){
                this.countryName = country.countryName
                this.countryFlag = country.flag
                this.search.country = country.countryCode
                let href = window.location.href
                // 商品详情页
                if(href.search('/product/') !== -1){
                    href += '?country=' + this.search.country
                    // 商品搜索
                }else if(href.search('/productList') != -1){
                    href += '&country=' + this.search.country
                }else if(href.search('/') != -1){
                    href = '?country=' + this.search.country
                }
                if(this.search.cId){
                    href += '&cId=' +  this.search.cId
                }
                window.open(href,'_self');
                this.setCountrySession()
            },
            getCountryById() {
                this.countryName =  window.GLOBAL_CONFIG.sCountry.countryName
                this.countryFlag =  window.GLOBAL_CONFIG.sCountry.flag
                this.search.country =  window.GLOBAL_CONFIG.sCountry.countryCode
                this.getRouterUrl()
            },
            // 将切换的国家存到session
            setCountrySession(){
                if(this.search.country){
                    $.get(window.GLOBAL_CONFIG.mainUrl +'/setCountrySession/'+this.search.country, (res) => {

                    }, 'json')
                }
            },
        }
    })
    window.GLOBAL_VUE.orderDialog = new Vue({
        el: '#product-order-dialog',
        data() {
            return {
                cartDialogVisible: false,
                originPage: '',
                type: '',
                id: '',
                isAddOrder: true,
                isAddAmazonProfileUrl: false,
                orderForm: {
                    status: '',
                    productPicImageUrl: '',
                    productName: '',
                    amazonUrl: '',
                    amazonSeller: '',
                    orderTiming: '',
                    reviewerRemarks: '',
                    commentsRemarks: '',
                    exten: {
                        rebates: 0,
                        price: 0,
                        originalPrice: 0,
                        discount: 0,
                        tips: []
                    }
                },
                // 亚马逊信息审核
                amazonForm: {
                    id: '',
                    amazonStoreUrl: '',
                    amazonOrderNo: '',
                    amazonOrderScreenshot: '',
                    imageList: []
                },
                time: 0,
                // 返现申请
                applyForm: {
                    commentImg: '',
                    evaOrderId: '',
                    // amazonOrderNo: '',
                    amazonCommentLink: '',
                    paypalAccount: '',
                    imageList: []
                },
                // 申请状态流程
                step: 0,
                imageList: [],
                dialogVisible: false,
                dialogImageUrl: '',
                uploadDisabled: false,
                uploadFileUrl: window.GLOBAL_CONFIG.mainUrl + '/webFinder/upload',
                countryCurrency: '',
                verifyProductLinks: '',
                showExample: false
            }
        },
        computed: {
            uploadDisabled(){
                return this.imageList.length > 0
            }
        },
        mounted() {
        },
        methods: {
            closedDialogVisible() {
                this.cartDialogVisible = false
                this.step=0
                this.type= ''
                this.applyForm ={
                    commentImg: '',
                    evaOrderId: '',
                    amazonCommentLink: '',
                    paypalAccount: '',
                    imageList: []
                }
                this.amazonForm = {
                    id: '',
                    amazonStoreUrl: '',
                    amazonOrderNo: '',
                    amazonOrderScreenshot: '',
                    imageList: []
                }
            },
            showExamplePopup() {
                this.showExample = true
            },
            validationProducts() {
                if (this.verifyProductLinks !== '') {
                    this.btnLoading = true
                    $.get(window.GLOBAL_CONFIG.mainUrl +'/product/checkProduct',{ url: this.verifyProductLinks, asin: this.orderForm.productSku }, res => {
                        this.btnLoading = false
                        if (res.state === 200) {
                            if (this.isAddOrder){
                                this.step = 1
                                this.type = 'order'
                                this.addOrder(this.id)
                            }
                        } else {
                            this.$message.error(res.message)
                        }
                    }, 'json')
                } else {
                    this.$message.error('Link cannot be empty')
                }
            },
            handleToAmazon(url,isOpen){
                if (this.orderForm.amazonUrl){
                    window.open(this.orderForm.amazonUrl, '_blank')
                }else if(url){
                    window.open(url, '_blank')
                    if(isOpen){
                        this.openOrder(this.amazonForm.id,1)
                    }
                }
            },
            getCountryById() {
                this.countryCurrency = window.GLOBAL_CONFIG.sCountry.currency
            },
            // 获取产品详情
            getProductDetails (productId){
                var that = this;
                $.get(window.GLOBAL_CONFIG.mainUrl +'/evaOrder/getProductDetails/' + productId, function (res) {
                    that.orderForm = res.data
                }, 'json')
            },
            // 获取订单详情
            getOrderDetails (orderId){
                var that = this;
                $.get(window.GLOBAL_CONFIG.mainUrl +'/evaOrder/getOrderDetails/' + orderId, function (res) {
                    that.amazonForm.amazonOrderNo = res.data.amazonOrderNo
                    that.amazonForm.amazonOrderScreenshot = res.data.amazonOrderScreenshot
                    if(that.amazonForm.amazonOrderScreenshot){
                        that.amazonForm.imageList = [{name:'',url: that.amazonForm.amazonOrderScreenshot}]
                    }
                    that.orderForm.productPicImageUrl = res.data.productImageUrl
                    that.orderForm.productName = res.data.productName
                    that.orderForm.amazonUrl = res.data.amazonUrl
                    that.orderForm.exten.rebates = res.data.exten.rebates
                    that.orderForm.reviewerRemarks = res.data.reviewerRemarks
                    that.orderForm.commentsRemarks = res.data.commentsRemarks
                    that.applyForm.evaOrderId = res.data.id
                    that.amazonForm.id = res.data.id
                    that.time = res.data.exten.time
                    that.orderForm.status = res.data.status
                }, 'json')
            },
            copyKeywordsText() {
                var clipboard = new ClipboardJS('.keywordsBtn')
                clipboard.on('success', e => {
                    this.$message.success('Copy Amazon keyword successfully')
                    // 释放内存
                    clipboard.destroy()
                })
                clipboard.on('error', e => {
                    // 不支持复制
                    this.$message.error('The browser does not support automatic copying')
                    // 释放内存
                    clipboard.destroy()
                })
            },
            handleOpen(item,type,isAddOrder){
                if (type === 'keyword'){
                    this.open(item.fkProductId,type,isAddOrder)
                }else {
                    this.handleToAmazon(item.exten.amazonProductUrl,false)

                }
            },
            open(id, type,isAddOrder) {
                this.id = id
                this.type = type
                this.isAddOrder = isAddOrder
                this.cartDialogVisible = true
                this.getCountryById()
                this.getProductDetails(this.id)
            },

            getQueryClient(orderId) {
                $.get(window.GLOBAL_CONFIG.mainUrl + '/client/queryClient', res => {
                    this.applyForm.paypalAccount = res.data.paypalAccount
                    this.amazonForm.amazonStoreUrl = res.data.amazonProfileUrl
                    if (!res.data.amazonProfileUrl){
                        this.isAddAmazonProfileUrl = true
                    }
                    this.getOrderDetails(orderId)
                }, 'json')
            },
            openOrder(orderId,step, originPage) {
                this.type= ''
                this.getCountryById()
                this.getQueryClient(orderId)
                this.step = step
                this.cartDialogVisible = true
                this.originPage  = originPage
            },
            addOrder (productId) {
                // 先验证是否加入订单
                $.post(window.GLOBAL_CONFIG.mainUrl +'/evaOrder/addOrder',{'fkProductId': productId}, res => {
                    if (res.success){
                        this.amazonForm.id = res.data.orderId
                        this.isAddAmazonProfileUrl = res.data.isvAmazonProfileUrl
                        this.time = res.data.time
                    }else {
                        this.$message.error(res.message);
                    }

                }, 'json')
            },
            applyAmazonOrder() {
                var that = this;
                if(that.amazonForm.imageList.length === 0){
                    this.$message.error('Please upload order screenshot');
                    return
                }
                if (that.amazonForm.imageList.length > 0) {
                    that.amazonForm.amazonOrderScreenshot = that.amazonForm.imageList[0].url
                }
                // 验证
                this.$refs['amazonForm'].validate((valid) => {
                    if (valid) {
                        if(!that.amazonForm.amazonOrderScreenshot){
                            this.$message.error('Please upload order screenshot');
                            return
                        }
                        $.post(window.GLOBAL_CONFIG.mainUrl +'/evaOrder/applyAmazonOrder',that.amazonForm, function (res) {
                            if(res.data.state === 200){
                                that.$message.success('Successful application');
                                that.cartDialogVisible = false
                                // 刷新列表
                                if(this.originPage) {
                                    this.originPage.orderDialogCloseCallback();
                                    this.originPage = ''
                                } else {
                                    window.location.reload()
                                }
                            }else{
                                that.$message.error(res.data.message);
                            }
                        }, 'json')
                    }
                })
            },
            // 提交返现申请
            applyCashBack () {
                var that = this;
                if(that.applyForm.imageList.length === 0){
                    this.$message.error('Please upload comment image');
                    return
                }
                if (that.applyForm.imageList.length > 0) {
                    that.applyForm.imageList.forEach(item => {
                        if(that.applyForm.commentImg){
                            that.applyForm.commentImg += ','
                        }
                        that.applyForm.commentImg += item.url
                    })
                }
                // 验证
                this.$refs['applyForm'].validate((valid) => {
                    if (valid) {
                        if(!that.applyForm.commentImg){
                            this.$message.error('Please upload comment image');
                            return
                        }
                        $.post(window.GLOBAL_CONFIG.mainUrl +'/evaOrder/applyCashBack',that.applyForm, function (res) {
                            if(res.data.state === 200){
                                that.$message.success('Successful application');
                                that.cartDialogVisible = false
                                // 刷新列表
                                if(this.originPage) {
                                    this.originPage.orderDialogCloseCallback();
                                    this.originPage = ''
                                } else {
                                    window.location.reload()
                                }
                            }
                        }, 'json')
                    }
                })
            },
            handleUploadApplyFormSuccess(res) {
                if (res.state === 200) {
                    this.applyForm.imageList.push(res.data)
                } else {
                    this.$message.error(res.message);
                }
            },
            handleUploadAmazonFormSuccess(res) {
                if (res.state === 200) {
                    this.amazonForm.imageList = [res.data]
                } else {
                    this.$message.error(res.message);
                }
            },
            handleUploadApplyFormRemove(res) {
                this.handleRemove(res, this.applyForm)
            },
            handleUploadAmazonFormRemove(res) {
                this.handleRemove(res, this.amazonForm)
            },
            handleUploadExceed() {
                this.$message.error('Exceeding the maximum uploads');
            },
            beforeAvatarUpload(file) {
                const isJPG = true;
                const isLt2M = file.size / 1024 / 1024 < 10;

                if (!isJPG) {
                    this.$message.error('Please upload the image format');
                }
                if (!isLt2M) {
                    this.$message.error('The size of the uploaded image cannot exceed 10MB');
                }
                // this.uploadDisabled = true
                return isJPG && isLt2M;
            },
            handleRemove(file, obj) {
                obj['imageList'].splice(obj['imageList'].indexOf(file),1);
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url
                this.dialogVisible = true
            }
        }
    })
    window.GLOBAL_VUE.footerTab = new Vue({
        el: '.navFooter',
        data() {
            return {
                tabActive: 0,
                routerIndex: {}
            }
        },
        mounted() {
            this.getRouterUrl()
        },
        computed: {
        },
        methods: {
            getRouterUrl() {
                const paramObj = $.Theme.Util.getParams(window.location.href)
                Object.assign(this.routerIndex, paramObj)
                if(this.routerIndex){
                    if(this.routerIndex.tab === undefined || this.routerIndex.tab === null ||this.routerIndex.tab ==='') {
                        this.tabActive = 0
                    } else {
                        this.tabActive = parseInt(this.routerIndex.tab)
                    }

                }
            },
            handleTabClick(index) {
                switch (index) {
                    case 0:
                        var url = window.GLOBAL_CONFIG.mainUrl+'/?tab=' + index
                        if(this.routerIndex.cId){
                            url += '&cId='+this.routerIndex.cId
                        }
                        window.open(url,'_self');
                        break;
                    case 1:
                        var url = window.GLOBAL_CONFIG.mainUrl+'/productList?tab=' + index
                        if(this.routerIndex.cId){
                            url += '&cId='+this.routerIndex.cId
                        }
                        window.open(url,'_self');
                        break;
                    case 2:
                        var url = window.GLOBAL_CONFIG.mainUrl+'/evaClient/order?tab=' + index
                        if(this.routerIndex.cId){
                            url += '&cId='+this.routerIndex.cId
                        }
                        window.open(url,'_self');
                        // window.open(window.GLOBAL_CONFIG.mainUrl+'/evaClient/order?tab=' + index+'&cId='+this.routerIndex.cId,'_self');
                        break;
                    case 3:
                        var url = window.GLOBAL_CONFIG.mainUrl+'/client/home?tab=' + index
                        if(this.routerIndex.cId){
                            url += '&cId='+this.routerIndex.cId
                        }
                        window.open(url,'_self');
                        // window.open(window.GLOBAL_CONFIG.mainUrl+'/client/home?tab=' + index +'&cId='+this.routerIndex.cId,'_self');
                        break;
                }
            },
        }
    });

    // 查看订单详情
    window.GLOBAL_VUE.viewOrderDialog = new Vue({
        el: '#view-order-dialog',
        data() {
            return {
                detailsDialogVisible: false,
                orderForm: {
                    productImageUrl: '',
                    productName: '',
                    amazonUrl: '',
                    amazonSeller: '',
                    status: 0,
                    amazonStoreUrl: '',
                    amazonOrderNo: '',
                    amazonOrderScreenshot: '',
                    applyTime: '',
                    reviewerRemarks: '',
                    exten: {
                        rebates: 0,
                        price: 0,
                        originalPrice: 0,
                        cashBackApply:{
                            amazonCommentLink: '',
                            paypalAccount: '',
                            reviewerRemarks: '',
                            transferVoucher: ''
                        }
                    }
                }
            }
        },
        mounted() {
        },
        methods: {
            // 获取订单详情
            getOrderDetails (orderId){
                var that = this;
                $.get(window.GLOBAL_CONFIG.mainUrl +'/evaOrder/getOrderDetails/' + orderId, function (res) {
                    that.orderForm = res.data
                }, 'json')
            },
            openView(orderId) {
                this.getOrderDetails(orderId)
                this.detailsDialogVisible = true
            }
        }
    })

    // 佣金提现
    window.GLOBAL_VUE.withdrawalDialog = new Vue({
        el: '#commission-withdrawal-dialog',
        data() {
            return {
                withdrawalDialogVisible: false,
                withdrawalForm: {
                    withdrawal: '',
                    paypalAccount: '',
                    withdrawalRemarks: '',
                    type: '2'
                }
            }
        },
        mounted() {
        },
        methods: {
            // 佣金提现
            doWithdrawal(){
                var that = this;
                this.$refs['withdrawalForm'].validate((valid) => {
                    if (valid) {
                        // 判断佣金金额填写是否是数字
                        if(!/((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/.test(that.withdrawalForm.withdrawal)){
                            that.$message.error('Wrong input of withdrawal amount format');
                            return
                        }
                        $.post(window.GLOBAL_CONFIG.mainUrl +'/evaBalance/applyWithdrawal', this.withdrawalForm , function (res) {
                            if(res.data.state === 200){
                                that.$message.success('Withdrawal application submitted successfully');
                                that.withdrawalDialogVisible = false
                                // 刷新页面
                                window.location.reload()
                            }else{
                                that.$message.error(res.data.message);
                            }
                        }, 'json')
                    }
                })
            },
            openWithdrawal() {
                this.withdrawalDialogVisible = true
            }
        }
    })
};
initVue();

seajs.config({
    // 别名配置
    alias: {
        'home': 'module.home',
        'list': 'module.list',
        'userGuide': 'module.userGuide',
        'faq': 'module.faq',
        'details': 'module.details',
        'user-order': 'module.user-order',
        'user-balance': 'module.user-balance',
        'user-share': 'module.user-share',
        'user-setting': 'module.user-setting',
        'user-settingPassword' : 'module.user-password'
    },
    base: window.GLOBAL_CONFIG.mainUrl +'/static/' +　window.GLOBAL_CONFIG.templateName + '/skin/js',
    charset: 'utf-8',
    map: [
        [/.js$/, '.js?v=1.0'],
    ]
});

var dataPage = $.Theme.$body.attr('data-page');
if(dataPage) {
    seajs.use([dataPage], function (obj) {
        obj && obj.init();
        // 当网页向下滑动 20px 出现"返回顶部" 按钮
        $(window).scroll(function(){
            scrollFunction($(this))
        })
    });
}

function scrollFunction(self) {
    if (self.scrollTop() > 109) {
        $('[data-mzt-top]').show();
    } else {
        $('[data-mzt-top]').hide();
    }
}
$('[data-mzt-top]').on('click', function () {
    $('html').animate({scrollTop:0},500);
})