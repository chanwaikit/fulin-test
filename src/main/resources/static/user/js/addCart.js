function getQueryVariable(variable) {//获取参数id
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

var id = getQueryVariable('id')
var minicartList = []
var totalPrice = 0
var priceCurrency;
function loadCart(animation) {
        $.ajax({
        type: 'GET',
        dataType: 'JSON',
        async: false,
        contentType: "application/json",
        url:'shoppingCartshoppingCartDataList',
        beforeSend: function () {

        },
        success: function (res) {
            minicartList =  res.data
            $('#minicart-box').empty()
            $('#minicart').tmpl(minicartList).appendTo('#minicart-box')
            var cartTotal = minicartList.cartList
            if(cartTotal!=''&&cartTotal!=null&&cartTotal!=undefined){
                priceCurrency = cartTotal[0].exten.currency
            }
            for(var i = 0; i < cartTotal.length; i++) {
                if(cartTotal[i].discountTypeName==''||cartTotal[i].discountTypeName==null||cartTotal[i].discountTypeName==undefined){
                    totalPrice += cartTotal[i].exten.productMallPrice*cartTotal[i].totalProductNumber
                    $('#cartPrice').html(parseFloat(totalPrice).toFixed(2))
                } else {
                    totalPrice += cartTotal[i].exten.productPromotionPrice*cartTotal[i].totalProductNumber
                    $('#cartPrice').html(parseFloat(totalPrice).toFixed(2))
                }
            }
            totalPrice = 0
            if(animation) {
                if (cartTotal.length>0){
                    $('.minicart-box').addClass('scaleMini')
                    setTimeout(function () {
                        $('.minicart-box').removeClass('scaleMini')
                    },300)
                }
            }
        }
    })
}
// $.cookie('ratio').split("-")[1]+' '+
// $('#cartPrice').on('mzt.submit.click')

//加入购物车
function addToCart(e) {
    e.stopPropagation()
    var buyVal = $('.qty-input .input-text').val()
    if (details.isSingleProduct == 1) {
        if(defaultColorSizeId==''||defaultColorSizeId==null||defaultColorSizeId== undefined){
            $.Theme.Tips.warning("请选择商品属性！")
            return false
        }
    }
    if(buyVal > productStockNumber){
        $.Theme.Tips.warning("购买数量不能大于库存数量！")
        return false
    }
    $.ajax({
        type: 'GET',
        dataType: 'JSON',
        async: false,
        contentType: "application/json",
        data: {
            id: $('#prodetailsId').val(),
            proNum: buyVal,
            fkProductColorSizeId: defaultColorSizeId
        },
        url: window.webDomain + '/shoppingCart/addOrdShopping',
        success: function (res) {
            if (res.state == 200) {
                loadCart(true)
                $.Theme.Tips.success("添加成功!")
            } else {
                $.Theme.Tips.error(res.message)
            }
        }
    })
}
function delProduct(id) {
    var ids = []
    ids.push(id)
    $.ajax({
        url: '/shoppingCart/delOrdShopping',
        type: 'get',
        dataType: 'json',
        data: {
            'goods': ids
        },
        traditional: true,//这里设置为true
        success: function (data) {
            if(data.state == 200) {
                window.location.reload()
            }
        }
    })
}


//结算
function goToCheckOut() {
    var settIds = new Array()
    $(".cart-group .cartId").each(function (i,e) {
        settIds.push($(e).attr("value"));
    })
    if(settIds.length>0){
      window.location.href = window.webDomain + '/shoppingCart/jumpSettlement?ordShoppingCartList=' + settIds
    }
}