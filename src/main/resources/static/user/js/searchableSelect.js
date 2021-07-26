// Author: David Qin
// E-mail: david@hereapp.cn
// Date: 2014-11-05

(function($){

    // a case insensitive jQuery :contains selector
    $.expr[":"].searchableSelectContains = $.expr.createPseudo(function(arg) {
        return function( elem ) {
            return $(elem).text().toUpperCase().indexOf(arg.toUpperCase()) >= 0;
        };
    });

    $.searchableSelect = function(element, options) {
        this.element = element;
        this.options = options || {};
        this.init();

        var _this = this;

        this.searchableElement.click(function(event){
            // event.stopPropagation();
            _this.show();
        }).on('keydown', function(event){
            if (event.which === 13 || event.which === 40 || event.which == 38){
                event.preventDefault();
                _this.show();
            }
        });

        $(document).on('click', null, function(event){
            if(_this.searchableElement.has($(event.target)).length === 0)
                _this.hide();
        });

        this.input.on('keydown', function(event){
            event.stopPropagation();
            if(event.which === 13){         //enter
                event.preventDefault();
                _this.selectCurrentHoverItem();
                _this.hide();
            } else if (event.which == 27) { //ese
                _this.hide();
            } else if (event.which == 40) { //down
                _this.hoverNextItem();
            } else if (event.which == 38) { //up
                _this.hoverPreviousItem();
            }
        }).on('keyup', function(event){
            if(event.which != 13 && event.which != 27 && event.which != 38 && event.which != 40)
                _this.filter();
        })
    }

    var $sS = $.searchableSelect;

    $sS.fn = $sS.prototype = {
        version: '0.0.1'
    };

    $sS.fn.extend = $sS.extend = $.extend;

    $sS.fn.extend({
        init: function(){
            var _this = this;
            this.element.hide();

            this.searchableElement = $('<div tabindex="0" class="searchable-select"></div>');
            this.holder = $('<div class="searchable-select-holder"></div>');
            this.dropdown = $('<div class="searchable-select-dropdown searchable-select-hide"></div>');
            this.input = $('<input type="text" class="searchable-select-input form-control" placeholder="Search" />');
            this.items = $('<div class="searchable-select-items"></div>');
            this.caret = $('<span class="searchable-select-caret"></span>');

            this.scrollPart = $('<div class="searchable-scroll"></div>');
            this.hasPrivious = $('<div class="searchable-has-privious">...</div>');
            this.hasNext = $('<div class="searchable-has-next">...</div>');

            this.hasNext.on('mouseenter', function(){
                _this.hasNextTimer = null;

                var f = function(){
                    var scrollTop = _this.items.scrollTop();
                    _this.items.scrollTop(scrollTop + 20);
                    _this.hasNextTimer = setTimeout(f, 50);
                }

                f();
            }).on('mouseleave', function(event) {
                clearTimeout(_this.hasNextTimer);
            });

            this.hasPrivious.on('mouseenter', function(){
                _this.hasPriviousTimer = null;

                var f = function(){
                    var scrollTop = _this.items.scrollTop();
                    _this.items.scrollTop(scrollTop - 20);
                    _this.hasPriviousTimer = setTimeout(f, 50);
                }

                f();
            }).on('mouseleave', function(event) {
                clearTimeout(_this.hasPriviousTimer);
            });

            this.dropdown.append(this.input);
            this.dropdown.append(this.scrollPart);

            this.scrollPart.append(this.hasPrivious);
            this.scrollPart.append(this.items);
            this.scrollPart.append(this.hasNext);

            this.searchableElement.append(this.caret);
            this.searchableElement.append(this.holder);
            this.searchableElement.append(this.dropdown);
            this.element.after(this.searchableElement);

            this.buildItems();
            this.setPriviousAndNextVisibility();
        },

        filter: function(){
            var text = this.input.val();
            this.items.find('.searchable-select-item').addClass('searchable-select-hide');
            this.items.find('.searchable-select-item:searchableSelectContains('+text+')').removeClass('searchable-select-hide');
            if(this.currentSelectedItem.hasClass('searchable-select-hide') && this.items.find('.searchable-select-item:not(.searchable-select-hide)').length > 0){
                this.hoverFirstNotHideItem();
            }

            this.setPriviousAndNextVisibility();
        },

        hoverFirstNotHideItem: function(){
            this.hoverItem(this.items.find('.searchable-select-item:not(.searchable-select-hide)').first());
        },

        selectCurrentHoverItem: function(){
            if(!this.currentHoverItem.hasClass('searchable-select-hide'))
                this.selectItem(this.currentHoverItem);
        },

        hoverPreviousItem: function(){
            if(!this.hasCurrentHoverItem())
                this.hoverFirstNotHideItem();
            else{
                var prevItem = this.currentHoverItem.prevAll('.searchable-select-item:not(.searchable-select-hide):first')
                if(prevItem.length > 0)
                    this.hoverItem(prevItem);
            }
        },

        hoverNextItem: function(){
            if(!this.hasCurrentHoverItem())
                this.hoverFirstNotHideItem();
            else{
                var nextItem = this.currentHoverItem.nextAll('.searchable-select-item:not(.searchable-select-hide):first')
                if(nextItem.length > 0)
                    this.hoverItem(nextItem);
            }
        },

        buildItems: function(){
            var _this = this;
            this.element.find('option').each(function(){
                var item = $('<div class="searchable-select-item" data-value="'+$(this).attr('value')+'">'+$(this).text()+'</div>');

                if(this.selected){
                    _this.selectItem(item);
                    _this.hoverItem(item);
                }

                item.on('mouseenter', function(){
                    $(this).addClass('hover');
                }).on('mouseleave', function(){
                    $(this).removeClass('hover');
                }).click(function(event){
                    event.stopPropagation();
                    _this.selectItem($(this));
                    _this.hide();
                });

                _this.items.append(item);
            });

            this.items.on('scroll', function(){
                _this.setPriviousAndNextVisibility();
            })
        },
        show: function(){
            this.dropdown.removeClass('searchable-select-hide');
            this.input.focus();
            this.status = 'show';
            this.setPriviousAndNextVisibility();
        },

        hide: function(){
            if(!(this.status === 'show'))
                return;

            if(this.items.find(':not(.searchable-select-hide)').length === 0)
                this.input.val('');
            this.dropdown.addClass('searchable-select-hide');
            this.searchableElement.trigger('focus');
            this.status = 'hide';
        },

        hasCurrentSelectedItem: function(){
            return this.currentSelectedItem && this.currentSelectedItem.length > 0;
        },

        selectOptionItem: function(option) {
            this.selectItem(this.items.find('div:eq('+$(option).index()+')'))
        },

        selectItem: function(item){
            if(this.hasCurrentSelectedItem())
                this.currentSelectedItem.removeClass('selected');

            this.currentSelectedItem = item;
            item.addClass('selected');

            this.hoverItem(item);

            this.holder.text(item.text());
            var value = item.data('value');
            this.holder.data('value', value);
            this.element.val(value);

            this.element.trigger('change');
            if(this.options.afterSelectItem){
                this.options.afterSelectItem.apply(this);
            }
        },

        hasCurrentHoverItem: function(){
            return this.currentHoverItem && this.currentHoverItem.length > 0;
        },

        hoverItem: function(item){
            if(this.hasCurrentHoverItem())
                this.currentHoverItem.removeClass('hover');

            if(item.outerHeight() + item.position().top > this.items.height())
                this.items.scrollTop(this.items.scrollTop() + item.outerHeight() + item.position().top - this.items.height());
            else if(item.position().top < 0)
                this.items.scrollTop(this.items.scrollTop() + item.position().top);

            this.currentHoverItem = item;
            item.addClass('hover');
        },

        setPriviousAndNextVisibility: function(){
            if(this.items.scrollTop() === 0){
                this.hasPrivious.addClass('searchable-select-hide');
                this.scrollPart.removeClass('has-privious');
            } else {
                this.hasPrivious.removeClass('searchable-select-hide');
                this.scrollPart.addClass('has-privious');
            }

            if(this.items.scrollTop() + this.items.innerHeight() >= this.items[0].scrollHeight){
                this.hasNext.addClass('searchable-select-hide');
                this.scrollPart.removeClass('has-next');
            } else {
                this.hasNext.removeClass('searchable-select-hide');
                this.scrollPart.addClass('has-next');
            }
        }
    });

    $.fn.searchableSelect = function(options){
        var ss = $.data( this[ 0 ] , "searchableSelect" );
        if ( ss ) {
            return ss;
        }
        ss = new $sS($(this), options);
        $.data( this[ 0 ], "searchableSelect", ss );
        return ss;
    };

})(jQuery);

+(function ($) {
    /**
     * @return {boolean}
     */
    var MZTCountryProvince = function (element, options) {
        var that         =        this,
            mzt          =        new $.Theme.dom(element);
        this.$element    =        $(element);

        if(mzt.isReady()) {
            return false
        }
        this.defaultOptions = {
            provinceEl: 'province',
            country: '',
            province: ''
        };
        options     =    options || mzt.data('mzt-country-province');
        if(typeof options === 'string') {
            this.defaultOptions.provinceEl = options;
        } else {
            $.extend(this.defaultOptions, options);
        }

        this.$provinceEL = $('select[name="'+this.defaultOptions.provinceEl+'"]');

        this.init();
    };
    MZTCountryProvince.prototype = {
        init : function () {
            var that = this;
            this.$element.on('change', function () {
                that.ajaxProvince($(this).val())
            });
            this.ajaxCountry()
        },
        setDefaultSelected : function({ country = '', province = ''}) {
            this.defaultOptions.country = country;
            this.defaultOptions.province = province;
            this.setSelected()
        },
        setSelected : function() {
            if(this.loadCountryComplete ) {
                if(this.defaultOptions.country) {
                    this.$element.searchableSelect().selectOptionItem(
                        this.$element.find('option[value="'+this.defaultOptions.country+'"]'));
                } else {
                    this.$element.searchableSelect().selectOptionItem(this.$element.find('option:first'));
                }
            }
            if(this.loadProvinceComplete &&  this.defaultOptions.province) {
                this.$provinceEL.find('option[value="'+this.defaultOptions.province+'"]').attr('selected', true);
            }
        },
        ajaxCountry : function() {
            var that = this;
            $.get(window.GLOBAL_CONFIG.mainUrl + '/client/countryList', function (res) {
                res.data.forEach(function (item) {
                    that.$element.append(that._builderOption(
                        {   value: item.alpha3,
                            text: item.name_cn,
                            selected: item.alpha3 == that.defaultOptions.country
                        }));
                });
                that.$element.searchableSelect();
                that.loadCountryComplete = true;
            }, 'json')
        },

        ajaxProvince : function(alpha3) {
            var that = this;
            if(alpha3) {
                $.get(window.GLOBAL_CONFIG.mainUrl + '/client/countryList/' + alpha3, function (res) {
                    that.$provinceEL.empty();
                    res.data.province.forEach(function (item) {
                        that.$provinceEL.append(that._builderOption(
                            {
                                value: item.provinceEn,
                                text: item.provinceEn,
                                selected: item.provinceEn == that.defaultOptions.province
                            }));
                        that.loadProvinceComplete = true;
                    })
                }, 'json')
            } else {
                this.resetProvince();
            }
        },
        resetProvince : function() {
            this.$provinceEL.empty();
            this.$provinceEL.append(this._builderOption({ value: '', text : 'Please select a country'}))
        },
        _builderOption : function ({value, text, selected}) {
            if(selected) {
                return '<option value="'+value+'" selected>'+text+'</option>';
            }
            return  '<option value="'+value+'" >'+text+'</option>';
        }
    };

    $.Theme.plugins['MZTCountryProvince'] = MZTCountryProvince;

    $.Theme.addReadyMethod('MZTCountryProvince', function (element) {
        $(element).find('div[data-mzt-country-province]').each(function () {
            new MZTCountryProvince(this);
        });
    });
})(jQuery);
