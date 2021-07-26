window.user_center = {
    _pagination : function(container) {
        $.Theme.draw.pagination({
            container: container
        })
    }
};
/** 个人中心收货地址**/
window.user_center.address = {
    $dom: {
        addressDelModal : $('#address-del'),
        addressEditModal : $('#address-window'),
        form : $('#address-window form')
    },
    init: function() {
        var that = this;
        window.user_center._pagination('.data-address-list');
        this.$dom.addressDelModal.on('show.bs.modal', function (e) {
            $(this).find('button.btn-primary')
                .addClass('deleteAddress').attr('data-id', $(e.relatedTarget).attr('data-id'));
        });
        this.$dom.addressDelModal.on('click', '.deleteAddress', function () {
            that.deleteAddress(this)
        });
        this.$dom.addressEditModal.on('show.bs.modal', function (e) {
           var addressId = $(e.relatedTarget).attr('data-id'), mZTCountryProvince = that.getMZTCountryProvince();
           if(addressId) {
               $.get(window.GLOBAL_CONFIG.mainUrl + '/client/address/' + addressId, function (res) {
                   that.$dom.form.objectSerialize(res.data.address);
                   mZTCountryProvince.setDefaultSelected({
                       country: res.data.address.country,
                       province: res.data.address.province,
                   })
               }, 'json')
           } else {
               mZTCountryProvince.setDefaultSelected({
                   country: '',
                   province: ''
               })
           }
            that.validateForm()
        }).on('hide.bs.modal', function () {
            that.resetForm()
        }).on('click', 'button.btn-primary', function () {
            that.$dom.form.submit()
        })
    },
    getMZTCountryProvince() {
        if(!this.countryProvince) {
            this.countryProvince =  new $.Theme.plugins.MZTCountryProvince('select[data-select-mzt-country-province]');
        }
        return this.countryProvince;
    },
    reloadListData: function() {
        var jqueryPagination = $('.mzt-address_pagination div[data-mzt-pagination]').data('plugin');
        jqueryPagination.reload(1);
    },
    closeEditModal: function() {
        this.$dom.addressEditModal.modal('hide');
    },
    closeDelModal: function() {
        this.$dom.addressDelModal.modal('hide');
    },
    deleteAddress : function (btn) {
        var that                    = this;
        $.ajax({
            url: window.GLOBAL_CONFIG.mainUrl + '/client/delete/address/' + $(btn).attr('data-id'),
            type: 'delete',
            dataType: 'json',
            success: function () {
                that.closeDelModal();
                that.reloadListData();
            }
        })
    },
    validateForm : function() {
        var that                    = this,
            $form                   = this.$dom.form;
        $form.validate({
            rules:{
                email:{
                    required:true,
                    email:true
                },
                clientName: {
                    required:true,
                },
                clientSurname: {
                    required:true,
                },
                city: {
                    required:true,
                },
                post: {
                    required:true,
                },
                country: {
                    required:true,
                },
                province: {
                    required:true,
                },
                address: {
                    required:true,
                }
            },
            submitHandler: function () {
                $.ajax({
                    url: window.GLOBAL_CONFIG.mainUrl + '/client/address/edit',
                    type: 'post',
                    contentType: 'application/json;charset=UTF-8',
                    data: JSON.stringify($form.serializeObject()),
                    dataType: 'json',
                    success: function () {
                        that.closeEditModal()
                        that.reloadListData()
                    }
                });
            }
        })
    },
    resetForm : function() {
        this.$dom.form.validate().resetForm();
        this.$dom.form[0].reset();
        this.$dom.form.find('input[type="checkbox"]').prop('checked', false)
    }
};

/** 个人中心-推荐好友 **/
window.user_center.share = {
    $dom: {
        shareTabList : $("li[name='share_tab_list']"),
        shareList : $('.share-data-list')
    },
    init: function () {
        var that = this;
        window.user_center._pagination('.order-data-list');
        that.$dom.shareTabList.each(function () {
            $(this).on('click',function () {
                let tab;
                that.$dom.shareTabList.each(function (i, e) {
                    if ($(e).attr("class") === 'active') {
                        tab = $(e).attr('tab');
                        if(tab == 'share'){
                            $('.address-content').hide()
                            $('.share-data-list').show()
                        }else{
                            $('.address-content').show()
                            $('.share-data-list').hide()
                        }
                    }
                })
            })
        })
    }
};

/** 个人中心订单**/
window.user_center.order = {
   init: function () {

   }
};

// 我的余额
window.user_center.balance = {
    init: function () {

    }
};

/** 收藏欢迎页面**/
window.user_center.favorite = {
    $dom: {
        favoriteId : $('a[favorite-id]'),
        favoriteIdHide : $('#favorite_id'),
        favoriteDelModal: $('#favorite-del')
    },
    init: function () {
        var that = this;
        window.user_center._pagination('.data-favorite-table');
        this.$dom.favoriteDelModal.on('show.bs.modal', function (e) {
            $(this).find('button.btn-primary')
                .addClass('deleteFavorite').attr('data-id', $(e.relatedTarget).attr('data-id'));
        });
        this.$dom.favoriteDelModal.on('click', '.deleteFavorite', function () {
            that.deleteFavorite(this)
        });
    },
    reloadListData: function() {
        var jqueryPagination = $('.mzt-favorite_pagination div[data-mzt-pagination]').data('plugin');
        jqueryPagination.reload(1);
    },
    closeDelModal: function() {
        this.$dom.favoriteDelModal.modal('hide');
    },
    deleteFavorite: function(btn) {
        var that = this;
        $.ajax({
            url: window.GLOBAL_CONFIG.mainUrl +'/client/favorite/del/' + $(btn).attr('data-id'),
            data: null,
            type: 'get',
            dataType: 'json',
            success: function () {
                that.closeDelModal()
                that.reloadListData()
            }
        })
    }
};

/** 支付流水欢迎页面**/
window.user_center.paymentRecord = {
    init: function () {
        window.user_center._pagination('.data-payment-table');
    }
};

/** 浏览记录欢迎页面**/
window.user_center.goodsTrails = {
    init: function () {
        window.user_center._pagination('.data-trails-table');
    }
};

/** 个人中心欢迎页面**/
window.user_center.account = {
    init: function () {

    }
};

/** 用户设置**/
window.user_center.setting = {
    init: function () {}
};
