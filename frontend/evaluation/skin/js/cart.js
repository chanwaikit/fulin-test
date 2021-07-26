/**
 *  product : {
 *    stocks: [],
 *    skus: [],
 *  }
 * @param $element
 * @param product
 * @constructor
 */
var ProductSkuDrawing = function ($element, product, options) {
    this.$element = $element;
    this.product = product;
    this.skuStocks = this.product.stocks;
    this.attrs = this.product.skus;
    this.selecteds = [];
    this.productStock = {
        minPrice: '0.00',
        minMarketPrice: '0.00',
        stock: 0,
        sku: '',
        imageUrl: ''
    };

    this.defaultOptions = {
        productPrice: '[data-mzt-ProductPrice]',
        productOldPrice: '[data-mzt-ProductOldPrice]',
        stockNumber: '[data-mzt-StockNumber]',
        spreadPrice: '[data-mzt-spreadPrice]',
        productPriceSave: '.pro_pri_save',
        previewImg: '.middleImg',
        item: 'li',
        specSeparator: '|',
        state: {
            enable: 'enable',
            selected: 'selected',
            disabled: 'disabled'
        }
    };
    $.extend(this.defaultOptions, options);
    this._init()
}
ProductSkuDrawing.SkuSelect = function(group_attrs, sku_list) {
    this.groupAttrs = group_attrs;
    this.skuList = sku_list;

    this.init = function() {
        this.skuList.map((subArr) => subArr.sort());
        this.available = this.groupAttrs.reduce((total, current) => [...total, ...current], []);
        this.excludes = this.getCompleteExcludes();
    }

    this.getCompleteExcludes = function() {
        const skuList = this.skuList.reduce((total, current) => [...total, ...current], []);

        return Array.from(new Set(
            this.available.map(item => !skuList.includes(item) && item).filter(Boolean))
        );
    }
    this. queryAvailable = function(selected) {
        let unavailable = this.getUnavailable();

        if (unavailable.length === 0) {
            return this.available;
        }

        let excludes = Array.from(this.excludes);
        let available = Array.from(this.available);



        if (selected.some(Boolean) && this.groupAttrs.length > 1) {

            if (this.groupAttrs.length - selected.length === 1) {
                unavailable.forEach(item => {
                    let exclude = item.map(v => !selected.includes(v) && v).filter(Boolean);

                    if (exclude.length === 1) {
                        excludes = excludes.concat(exclude);
                    }
                });
            } else if (this.groupAttrs.length === selected.length) {
                let selectedCombine = this.getSelectedCombine(selected);

                unavailable.forEach(v => {
                    selectedCombine.forEach(c => {
                        let exclude = v.map(item => !c.includes(item) && item).filter(Boolean);

                        if (exclude.length === 1) {
                            excludes = excludes.concat(exclude);
                        }
                    });
                });
            }

            excludes = Array.from(new Set(excludes));
        }

        return this.getAvailable(available, excludes);
    }
    this.getSelectedCombine = function(selected) {
        let results = [];

        let len = selected.length;

        for (let i = 0; i < len; i++) {
            for (let j = i + 1; j < len; j++) {
                let result = [];

                result.push(selected[i], selected[j]);
                results.push(result);
            }
        }

        return results;
    }
    this.getAvailable = function(data, excludes) {
        excludes.forEach(item => {
            let index = data.indexOf(item);

            if (index !== -1) {
                data.splice(index, 1);
            }
        });

        return data;
    }

    this.getUnavailable = function() {
        return this.getDiffArray(this.calcDescartes(this.groupAttrs), this.skuList);
    }
    this.calcDescartes = function(array) {
        if (array.length < 2) return array[0] || [];
        return [].reduce.call(array, function (col, set) {
            let res = [];

            col.forEach(function (c) {
                set.forEach(function (s) {
                    let t = [].concat(Array.isArray(c) ? c : [c]);
                    t.push(s);
                    res.push(t);
                })
            });

            return res;
        });
    }

    this.getDiffArray = function(full_sku_list, this_sku_list) {
        full_sku_list.map((subArr) => subArr.sort());

        let unavailable = [];

        full_sku_list.forEach((sArr) => {
            if (typeof this_sku_list.find(item => item.toString() === sArr.toString()) === 'undefined') {
                unavailable.push(sArr);
            }
        });
        return unavailable;
    };

    this.init();
};
ProductSkuDrawing.prototype = {
    _init: function() {
        var groupAttrs = this.attrs.map((value) => value.specList.map((option) => option.id));
        var specs = this.skuStocks.map(item => item.stockNumber > 0 && item.spec).filter(Boolean);
        var skuList = [];
        specs.forEach(item => {
            skuList.push(item.split(this.defaultOptions.specSeparator))
        });
        this.skuSelect = new ProductSkuDrawing.SkuSelect(groupAttrs, skuList);
        this.available = this.skuSelect.queryAvailable(this.selecteds);

        this.attrs.forEach(function (attr) {
            attr.specList.forEach(function (item) {
                item.state = ''
            })
        });
        // 初始化商品价格
        this.productStock.minPrice = this.product.minPrice
        this.productStock.minMarketPrice = this.product.minMarketPrice
        this.productStock.stock = this.product.stockNumber
        if (this.product.isSingle !== 1) {
            this.setDefault();
            this._bindingEvent();
        } else {
            this.skuStocks.forEach(item => {
                this.productStock.sku = item.productSubSku
                this.productStock.imageUrl = item.imageUrl
            })
        }
        this._drawingPrice();
    },
    _specToArray : function(item) {
        return item.spec.split(this.defaultOptions.specSeparator);
    },
    setDefault : function() {
        // 清空　已选　ｓｋｕ
        this.productStock.sku = ''
        // 查找默认库存大于0 的一组数据
        var spec = this.skuStocks.map(item => item.stockNumber > 0 && item.spec).filter(Boolean)[0];
        this.selecteds = [...this.selecteds, ...spec.split(this.defaultOptions.specSeparator)];

        this._builderAttrState();
        this._drawingSkuItem();
        this._selectFinishToPrice()
    },
    _drawingSkuItem : function () {
        var skuDomStr = [],attrs = this.attrs;
        attrs.forEach(function (attr, index) {
            skuDomStr.push('<ul class="mzt-goods-sku">');
            skuDomStr.push('<span class="mzt-goods-sku__title">'+attr.specName+'</span>');
            attr.specList.forEach(function (item) {
                if(attr.colorFlag === 0) {
                    skuDomStr.push('<li class="mzt-goods-sku__text '+item.state+'" data-attr="'+index+'" data-val="'+item.id+'">');
                    skuDomStr.push(' <span>'+item.specName+'</span>');
                } else {
                    skuDomStr.push('<li class="mzt-goods-sku__color '+item.state+'" data-attr="'+index+'" data-val="'+item.id+'">');
                    skuDomStr.push(' <span title="'+item.specName+'" style="width: 100%;height: 100%;background:'+item.colorCode+'"></span>');
                }
                skuDomStr.push('</li>');
            });
            skuDomStr.push('</ul>')
        });
        this.$element.html(skuDomStr.join(''));
    },
    _bindingEvent : function() {
        var _self = this;
        this.$element.on('click', this.defaultOptions.item, function () {
            var $this = $(this);
            _self.handleSelect($this.attr('data-attr'), $this.attr('data-val'));
        })
    },
    _drawingPrice : function() {
        $(this.defaultOptions.productPrice).html('$'+this.productStock.minPrice);
        $(this.defaultOptions.stockNumber).html(this.productStock.stock);

        if(this.productStock.imageUrl) {
            $(this.defaultOptions.previewImg).attr('src', this.productStock.imageUrl)
        }
        var priceSpread = this.productStock.minMarketPrice - this.productStock.minPrice
        if (priceSpread !== 0) {
            $(this.defaultOptions.productOldPrice).html('$'+this.productStock.minMarketPrice);
            priceSpread = priceSpread.toFixed(2)
            $(this.defaultOptions.spreadPrice).html(priceSpread);
            $(this.defaultOptions.productPriceSave).show()
        }
    },
    _selectFinishToPrice : function() {
        if(this.isSelectFinish()) {
            this.skuStocks.forEach(item => {
                if(this._arrayEqual(this._specToArray(item), this.selecteds)) {
                    this.productStock.minPrice = item.mallPrice
                    this.productStock.minMarketPrice = item.marketPrice
                    this.productStock.stock = item.stockNumber
                    this.productStock.sku = item.productSubSku
                    this.productStock.imageUrl = item.imageUrl
                    this._drawingPrice()
                }
            })
        } else {
            this.productStock.sku = ''
        }
    },
    _arrayEqual : function (arr1, arr2) {
        return arr1.length === arr2.length
            &&
            arr1.every((item)=>{
                return arr2.indexOf(item) >-1
            })
            &&
            arr2.every((item)=>{
                return arr1.indexOf(item) >-1
            })
    },
    isSelectFinish : function() {
        return this.selecteds.length === this.attrs.length
    },
    getSelectAttr : function() {
        return {
            sku : this.productStock.sku,
            stock : this.productStock.stock,
            productId: this.product.productId
        }
    },
    _builderAttrState : function() {
        this.available = this.skuSelect.queryAvailable(this.selecteds);
        this.attrs.forEach(attr => {
            attr.specList.forEach(item => {
                item.state = ''
                item.selected = this.selecteds.includes(item.id)
                if(item.selected) {
                    item.state = this.defaultOptions.state.selected
                } else {
                    item.state = ''
                }
                if(!this.available.includes(item.id)) {
                    item.state = this.defaultOptions.state.disabled
                }
            })
        });
    },
    handleSelect(attrIndex, option_id) {
        if (this.available.indexOf(option_id) === -1) return;
        let attr = this.attrs[attrIndex];

        attr.specList.forEach((option, index, item) => {
            if (option.id !== option_id) {
                option.selected = false;
                return;
            }
            option.selected = !option.selected;

            if (option.selected) {
                this.selecteds = this.selecteds.filter((v) => !item.map((id) => id.id).includes(v));
                this.selecteds.push(option.id);
            } else {
                this.selecteds.splice(this.selecteds.findIndex((v) => v === option.id), 1);
            }
        });

        this._builderAttrState();
        this._drawingSkuItem();
        this._selectFinishToPrice()
    }
};
// 购物车
var Cart = function () {
    this.events = {
        settlementBtn: function (that) {
            var SETEC_URL = window.webDomain + '/jumpSettlement/saveSettlement';
            return fetch(SETEC_URL, {
                method: 'post'
            }).then(function(res) {
                return res.json();
            }).then(function(data) {
                if(data.state == 200){
                    window.location.href = window.webDomain + '/jumpSettlement/'+data.data+'?step=information';
                }
            });

        }
    };
    this.el = {
        selectAll: $('#selectAll'),
        checks: $('input[name="chooseInfo"]'),
        totalNumber: $("#totalNumber"),
        totalAmount: $("#totalAmountView"),
        discountAmount: $("#discountAmountVIew"),
        wanSpinner : $(".wan-spinner-2")
    }

    this.initAmountCount = function () {
        this.el.totalNumber.empty();
        this.el.totalNumber.append(0);

        this.el.totalAmount.empty();
        this.el.totalAmount.append('$' + 0.00);

        this.el.discountAmount.empty();
        this.el.discountAmount.append('$' + 0.00);
    }

    this.totalAmountCount = function () {
        var $check = $("input[name='chooseInfo']"),
            totalNumber = $check.length,
            totalAmount = 0,
            discountAmount = 0;

        $check.each(function () {
            var $item = $('[data-item='+$(this).val()+']');
            totalAmount += ($item.find('input[name="itemTotalAmount"]').val() * 1);
            discountAmount += ($item.find('input[name="discountAmounts"]').val() * 1);
        });
        this.el.totalNumber.html(totalNumber);
        // 保留2位小数
        var totalAmount2 = totalAmount.toFixed(2)
        this.el.discountAmount.html('$ ' + (totalAmount + discountAmount).toFixed(2));
        this.el.totalAmount.html('$ ' + totalAmount2);
    }

    this.initSelectAll = function () {
        var that = this;
        that.el.selectAll.on('click',function () {
            if ($(this).is(':checked')) {
                that.el.checks.each(function () {
                    $(this).prop("checked", true);
                    $('[data-item='+$(this).val()+']').addClass('info');
                });
                that.updateShoppingCartSelected('all', 1)
            } else {
                that.el.checks.each(function () {
                    $(this).prop("checked", false);
                    $('[data-item='+$(this).val()+']').removeClass('info');
                });
                that.updateShoppingCartSelected('all', 0);
            }
            that.totalAmountCount();
        });
        that.el.checks.click(function () {
            var $item = $('[data-item='+$(this).val()+']');
            if ($(this).prop('checked')) {
                $item.addClass('info');
                that.updateShoppingCartSelected($(this).val(), 1);
            } else {
                $item.removeClass('info');
                that.updateShoppingCartSelected($(this).val(), 0)
            }
            that.itemSelectAll();
            that.totalAmountCount();
        });

        that.el.checks.each(function () {
            var selected = $(this).data("selected");
            var $item = $('[data-item='+$(this).val()+']');
            if(selected == 1) {
                $item.addClass("info");
                $(this).prop("checked", true);
                that.itemSelectAll();
                that.totalAmountCount();
            }
        })
    }

    this.selectArray = function () {
        var goodsArray = [];
        $('input[name="chooseInfo"]').each(function () {
            goodsArray.push($(this).val());
        });
        return goodsArray;
    };

    this.itemSelectAll = function () {
        if($('input[name="chooseInfo"]').length === this.el.checks.length){
            this.el.selectAll.prop("checked", true);
        } else {
            this.el.selectAll.prop("checked", false);
        }
    }
    this.calculationArticle = function(id, flag) {
        var that = this;
        var url = window.webDomain + '/shoppingCart/updateOrdShopping/' + id + "/" + flag;
        $.ajax({
            url: url,
            data: null,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    if (data.data === "") {
                        window.location.href = window.webDomain + '/shoppingCart'
                    } else {
                        var $item =  $('div[data-item='+id+']');
                        // 价格
                        $item.find('span[data-view="itemTotalAmount"]').html('$ ' + data.data.exten.totalAmount);
                        $item.find('input[name="itemTotalAmount"]').val(data.data.exten.totalAmount);
                        that.totalAmountCount();
                    }
                }
            }
        })
    }

    this.delProduct = function(id) {
        var ids = []
        ids.push(id)
        $.ajax({
            url: window.webDomain + '/shoppingCart/delOrdShopping',
            type: 'get',
            dataType: 'json',
            data: {
                'goods': ids
            },
            traditional: true,//这里设置为true
            success: function (data) {
                if(data.state == 200) {
                    window.location.reload()
                }else{
                    $.Theme.Tips.warning(data.message)
                }
            }
        })
    }

    this.showTable = function () {
        $('.details-table table').show();
    }

    this.initWanSpinner = function () {
        var that = this;
        var options = {
            maxValue: 99,
            minValue: 1,
            step: 1,
            inputWidth: 58,
            start: 0
        };
        this.el.wanSpinner.WanSpinner(options);
        this.el.wanSpinner.on('click', 'a[data-spinner-sub]', function () {
            that.calculationArticle($(this).attr('data-spinner-sub'), '0')
        });
        this.el.wanSpinner.on('click', 'a[data-spinner-plus]', function () {
            that.calculationArticle($(this).attr('data-spinner-plus'), '1')
        });
    }
    this.updateShoppingCartSelected = function (itemId, selected) {
        $.ajax({
            url: window.webDomain + '/shoppingCart/updateShoppingCartSelected/'+itemId+'/'+selected,
            type: 'post',
            dataType: 'json',
            success: function (data) {}
        })
    }
    this.initPaypal = function () {
        paypal.Buttons({
            style: {
                layout: 'horizontal'
            },
            // Set up the transaction
            createOrder: function() {
                if($("input[name='chooseInfo']").length== 0){
                    $.Theme.Tips.warning('Select Goods')
                }else{
                    var settIds = new Array()
                    $("input[name='chooseInfo']").each(function (i,e) {
                        settIds.push($(e).attr("value"));
                    })
                    var SETEC_URL = window.webDomain + '/orderBooks/createCartOrder'
                    return fetch(SETEC_URL, {
                        method: 'post',
                        headers: {
                            'content-type': 'application/json',
                        },
                        body: JSON.stringify({
                            'ordShoppingCartList':settIds
                        })
                    }).then(function (res) {
                        return res.json();
                    }).then(function (data) {
                        if(data.state == 200){
                            return data.data.orderId;
                        }else{
                            $.Theme.Tips.warning(data.message)
                        }
                    });
                }
            },
            onApprove: function(data,actions) {
                $('#loading-box-ProductBuyPay').show()
                var SETEC_URL = window.webDomain + '/orderBooks/getOrderData?token='+data.orderID+'&payerID='+data.payerID;
                return fetch(SETEC_URL, {
                    method: 'get'
                }).then(function(res) {
                    return res.json();
                }).then(function(data) {
                    if(data.state == 200){
                        if(data.data.settlementId){
                           window.location.href = window.webDomain + '/jumpSettlement/'+data.data.settlementId+'?step=information';
                        }else {
                           window.location.href = window.webDomain + '/paymentInfo/registeredFailure?settlementId='+data.data.settlementId;
                        }
                    }else{
                        window.location.href = window.webDomain + '/paymentInfo/registeredFailure?settlementId='+data.data.settlementId;
                    }

                });
            }
        }).render('#paypal-button-container');
    }

    this.bind = function () {
        var that = this;
        $('.settlement').on('click', '#settlementBtn', function () {
            that.events[$(this).attr('id')].call(this, that);
        })
    }

    this.openProductSkuModal = function () {
        $('#cart-productSku').on('show.bs.modal', function (e) {
            var title = $(e.relatedTarget).attr('data-title'), that = $(this);
            that.find('.modal-title').html(title)
            $.ajax({
                type: 'GET',
                url: window.GLOBAL_CONFIG.mainUrl + '/parts/product-sku?productId=' + $(e.relatedTarget).attr('data-id'),
                success: function (res) {
                    that.find('.modal-body').html(res);
                }
            })
        })
    }
    this.init = function () {
        this.bind();
        this.initWanSpinner();
        this.initSelectAll();
        this.showTable();
        this.initPaypal();
        this.totalAmountCount();
        this.updateShoppingCartSelected('all', 1)
        this.openProductSkuModal()
    };
    this.init();
};

function addToCart(type) {
    /*    if(!this.isLoginState()){
            window.location.href = window.GLOBAL_CONFIG.mainUrl+'/login';
            return;
        }*/
    var buyVal = $('.qty-input .input-text').val()
    if(checkCart(buyVal)){
        var selectAttr = window.PRODUCT_SKU_DRAWING.getSelectAttr();
        $.ajax({
            type: 'GET',
            dataType: 'JSON',
            async: false,
            contentType: "application/json",
            data: {
                id: selectAttr.productId,
                proNum: buyVal,
                productSubSku:selectAttr.sku
            },
            url: window.GLOBAL_CONFIG.mainUrl+'/shoppingCart/addOrdShopping',
            success: function (res) {
                if (res.state == 200) {
                    if($.Theme.config.navUserContainer) {
                        $.Theme.parts.reload($.Theme.config.navUserContainer, 'nav-user')
                    }
                    if (type) {
                        $('#cart-productSku').modal('hide');
                        window.location.reload()
                    } else {
                        openProductCardModal(selectAttr)
                    }
                } else {
                    $.Theme.Tips.warning(res.message)
                }
            }
        })
    }
}
$('#productCard-dialog').on('show.bs.modal', function () {
    var $dialog = $(this);
    var selectAttr = window.PRODUCT_SKU_DRAWING.getSelectAttr();
    $.get( window.GLOBAL_CONFIG.mainUrl + '/product/cart_confirm_data',  {
        productId: selectAttr.productId,
        productSubSku: selectAttr.sku
    }, function (res) {
        $dialog.find('.modal-content').html(res)
    })
})

function jumpbuyNow() {
    var buyVal = $('.qty-input .input-text').val()
    if(checkCart(buyVal)) {
        var selectAttr = window.PRODUCT_SKU_DRAWING.getSelectAttr();
        $.ajax({
            type: 'GET',
            dataType: 'JSON',
            async: false,
            data: {
                id: selectAttr.productId,
                proNum: buyVal,
                productSubSku: selectAttr.sku
            },
            contentType: "application/json",
            url:window.GLOBAL_CONFIG.mainUrl+'/shoppingCart/jumpSettlementData',
            success: function (res) {
                window.location.href = window.GLOBAL_CONFIG.mainUrl + '/jumpSettlement/'+res.data.settlementId+'?step=information';
            }
        })
    }
}
function checkCart(buyVal) {
    var selectAttr = window.PRODUCT_SKU_DRAWING.getSelectAttr();
    if (pageConfig.product.isSingle === 0) {
        if(!selectAttr.stock){
            $.Theme.Tips.warning("请选择商品属性！")
            return false
        }
    }
    if(buyVal === 0 || !(/(^[1-9]\d*$)/.test(buyVal))){
        $.Theme.Tips.warning("请输入正确数量！")
        return false
    }
    if(buyVal > selectAttr.stock){
        $.Theme.Tips.warning("购买数量不能大于库存数量！")
        return false
    }
    return true
}
function settlementBtn() {
    var SETEC_URL = window.GLOBAL_CONFIG.mainUrl + '/jumpSettlement/saveSettlement?cartId='+$('#cartId').val();
    return fetch(SETEC_URL, {
        method: 'post',
    }).then(function(res) {
        return res.json();
    }).then(function(data) {
        if(data.state == 200){
            window.location.href = window.GLOBAL_CONFIG.mainUrl + '/jumpSettlement/'+data.data+'?step=information';
        }
    });

}
// 判断 是否登录
/*function isLoginState() {
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
}*/

var Sku = {};

Sku.ImageDisplayBox = {
    $box : $('.product-content'),
    thumbnailLoad : '.thumbnail-load',
    loaderClass : '.sp-loading',
    loadAfter:'.mzt-detail-loadAfter',
    outer: function () {
        var tempLength = 0; //临时变量,当前移动的长度
        var viewNum = 4; //设置每次显示图片的个数量
        var moveNum = 3; //每次移动的数量
        var moveTime = 300; //移动速度,毫秒
        var scrollDiv = $(".productSmallPic ul"); //进行移动动画的容器
        var scrollItems = $(".productSmallPic ul li"); //移动容器里的集合
        var moveLength = scrollItems.eq(0).width() * moveNum; //计算每次移动的长度
        var countLength = (scrollItems.length - viewNum) * scrollItems.eq(0).width()+26; //计算总长度,总个数*单个长度
        //下一张
        $(".moveRightClick").bind("click",function(){
            if(tempLength < countLength){
                if((countLength - tempLength) > moveLength){
                    scrollDiv.animate({left:"-=" + moveLength + "px"}, moveTime);
                    tempLength += moveLength;
                }else{
                    scrollDiv.animate({left:"-=" + (countLength - tempLength) + "px"}, moveTime);
                    tempLength += (countLength - tempLength);
                }
            }
        });
        //上一张
        $(".moveLeftClick").bind("click",function(){
            if(tempLength > 0){
                if(tempLength > moveLength){
                    scrollDiv.animate({left: "+=" + moveLength + "px"}, moveTime);
                    tempLength -= moveLength;
                }else{
                    scrollDiv.animate({left: "+=" + tempLength + "px"}, moveTime);
                    tempLength = 0;
                }
            }
        });
    },
    inner: function () {
        $('.product-content').find('.sp-wrap').smoothproducts();
    },
    loader: function () {
        var arry = [], promises = [], that = this;
        var loadImage = function ($img) {
            var def =  $.Deferred(), image = new Image();
            image.src = $img.attr('data-src');
            image.onload = function () {
                $img.attr('src', image.src);
                def.resolve();
            };
            image.onerror = function () {
                $img.attr('src', window.GLOBAL_CONFIG.siteMapUrl);
                def.resolve();
            };
            return def.promise()
        };
        that.$box.find(this.thumbnailLoad).each(function () {
            arry.push($(this));
        });
        arry.forEach(function (item) {
            promises.push(loadImage(item));
        });
        $.when(...promises).then(function () {
             that.$box.find(that.loaderClass).remove();
             that.$box.find(that.loadAfter).show();
        })
    },
    init: function () {
        var imageBox = $('[data-image-box]');
        if(imageBox.length > 0) {
            this[imageBox.data('image-box')].call();
            this.loader();
        }
    }
};

Sku.share = function() {
    (function (d, j) {
        j = d.createElement('script');
        j.async = true;
        j.src ='//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-5fe5af3da268ae1e';
        d.body.appendChild(j);
    })(document);
};

Sku.init = function () {
    var CardSku = function (element) {
        var mzt          =        new $.Theme.dom(element);
        this.$element    =        $(element);
        this.$spec       =        this.$element.find('div[data-spec]')
        this.specData    =        new $.Theme.dom(this.$spec).data('spec');

        this.view        =        false;
        if(mzt.isReady()) {
            return false
        }
        this.defaultOptions = {
            openBtn: '.addTocart',
            cancelBtn: '.sku-cancel',
            confirmBtn: '.sku-confirm',
            skuCard: '.product-sku',
            select: '.mzt-sku_color li',
            selectClass: 'selectAttr',
            selectColor: '.selector-wrapper ul li',
            selectColorClass: 'active',
            productCard: '.mzt-float ui li',
        };

        var options     =    mzt.data('mzt-cartsku');
        if(typeof options === 'string') {
            this.defaultOptions.end = options;
        } else {
            $.extend(this.defaultOptions, options);
        }
        //添加购物车
        this.addCart = function () {
            that = this
            if(this.specData.isSingle == 0) {
                if(!this.valid()) {
                    return false;
                }
            }
            var sepcList =  this.getSelect();
            if(this.getSelectColor().length>0){
                var color = this.getSelectColor()
                sepcList.push(color[0])
            }
            var data =  {
                id: this.specData.productId,
                proNum: 1,
                specList: sepcList,
                isSingle: this.specData.isSingle
            }
            $.ajax({
                type: 'POST',
                dataType: 'JSON',
                contentType: "application/json",
                // async: false,
                url: window.GLOBAL_CONFIG.mainUrl+'/shoppingCart/addToOrdShopping',
                data: JSON.stringify(data),
                success: function (res) {
                    if (res.state == 200) {
                        that.successOption()
                        $.Theme.Tips.success("添加成功!")
                    } else {
                        $.Theme.Tips.error(res.message)
                    }
                }
            })
        };
        //校验
        this.valid = function () {
            if(this.specData.specSize == this.getSelect().length + this.getSelectColor().length) {
                return true;
            }
            $.Theme.Tips.warning('请选中商品属性')
            return false;
        };
        //获取选中的规格
        this.getSelect = function () {
            var selectVal = [];
            var that = this;
            this.$selectLi.each(function () {
                var _this = $(this);
                if(_this.hasClass(that.defaultOptions.selectClass)) {
                    selectVal.push(_this.data('value'))
                }
            });
            return selectVal;
        };
        this.getSelectColor = function () {
            var selectVal = [];
            var that = this;
            this.$selectColorLi.each(function () {
                var _this = $(this);
                if(_this.hasClass(that.defaultOptions.selectColorClass)) {
                    selectVal.push(_this.data('value'))
                }
            });
            return selectVal;
        }
        //更改选中
        this.onSelect = function () {
            var that = this;
            this.$element.on('click', this.defaultOptions.select, function () {
                $(this).siblings().removeClass(that.defaultOptions.selectClass);
                $(this).addClass(that.defaultOptions.selectClass);
            });
        };
        this.removeSelect = function () {
            var that = this;
            this.$selectLi.removeClass(that.defaultOptions.selectClass);
        }
        //选中色卡
        this.onSelectColor = function () {
            var that = this;
            this.$element.on('mouseover', this.defaultOptions.selectColor, function () {
                $(this).siblings().removeClass(that.defaultOptions.selectColorClass);
                $(this).addClass(that.defaultOptions.selectColorClass);
            });
        };
        this.trigger = function () {
            if(!this.view) {
                this.$element.find(this.defaultOptions.skuCard).show();
                this.view = true;
            } else {
                this.removeSelect()
                this.$element.find(this.defaultOptions.skuCard).hide();
                this.view = false
            }
        };
        this.successOption = function () {
            this.removeSelect()
            this.$element.find(this.defaultOptions.skuCard).hide();
            this.view = false
        }
        this.init = function () {
            var that = this;
            this.$selectLi = this.$element.find(this.defaultOptions.select);
            this.$selectColorLi = this.$element.find(this.defaultOptions.selectColor);
            this.$element.on('click', this.defaultOptions.openBtn, function () {
                if(that.specData.isSingle) {
                    that.addCart()
                } else {
                    if(that.getSelectColor().length ==1 && that.specData.specSize == that.getSelectColor().length){
                        that.addCart();
                    }else{
                        that.view = false;
                        that.trigger();
                    }
                }
            });
            this.$element.on('click', this.defaultOptions.cancelBtn, function () {
                that.view = true;
                that.trigger();
            });
            this.$element.on('click', this.defaultOptions.confirmBtn, function () {
                that.addCart();
            });
            this.onSelect();
            this.onSelectColor();
        };

        this.init();
    }
    $('body').find('[data-mzt-cardsku]').each(function () {
        new CardSku(this);
    })
    Sku.ImageDisplayBox.init();
};
Sku.init();

