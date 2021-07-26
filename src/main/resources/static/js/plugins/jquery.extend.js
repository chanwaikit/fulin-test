(function ($) {
    //配置参数
    var defaults = {
        totalData: 0,			//数据总条数
        showData: 0,			//每页显示的条数
        pageCount: 9,			//总页数,默认为9
        current: 1,				//当前第几页
        prevCls: 'prev',		//上一页class
        nextCls: 'next',		//下一页class
        prevContent: '<',		//上一页内容
        nextContent: '>',		//下一页内容
        activeCls: 'active',	//当前页选中状态
        coping: false,			//首页和尾页
        isHide: false,			//当前页数为0页或者1页时不显示分页
        homePage: '',			//首页节点内容
        endPage: '',			//尾页节点内容
        keepShowPN: false,		//是否一直显示上一页下一页
        count: 2,				//当前页前后分页个数
        jump: false,			//跳转到指定页数
        jumpIptCls: 'jump-ipt',	//文本框内容
        jumpBtnCls: 'jump-btn',	//跳转按钮
        jumpBtn: '跳转',		//跳转按钮文本
        callback: function(){}	//回调
    };

    var Pagination = function(element,options){
        //全局变量
        var opts = options,//配置
            current,//当前页
            $document = $(document),
            $obj = $(element);//容器

        /**
         * 设置总页数
         * @param int page 页码
         * @return opts.pageCount 总页数配置
         */
        this.setPageCount = function(page){
            return opts.pageCount = page;
        };

        /**
         * 获取总页数
         * 如果配置了总条数和每页显示条数，将会自动计算总页数并略过总页数配置，反之
         * @return int p 总页数
         */
        this.getPageCount = function(){
            return opts.totalData || opts.showData ? Math.ceil(parseInt(opts.totalData) / opts.showData) : opts.pageCount;
        };

        /**
         * 获取当前页
         * @return int current 当前第几页
         */
        this.getCurrent = function(){
            return current;
        };

        /**
         * 填充数据
         * @param int index 页码
         */
        this.filling = function(index){
            var html = '';
            current = index || opts.current;//当前页码
            var pageCount = this.getPageCount();//获取的总页数
            if(opts.keepShowPN || current > 1){//上一页
                html += '<a href="javascript:;" class="'+opts.prevCls+'">'+opts.prevContent+'</a>';
            }else{
                if(opts.keepShowPN == false){
                    $obj.find('.'+opts.prevCls) && $obj.find('.'+opts.prevCls).remove();
                }
            }
            if(current >= opts.count + 2 && current != 1 && pageCount != opts.count){
                var home = opts.coping && opts.homePage ? opts.homePage : '1';
                html += opts.coping ? '<a href="javascript:;" data-page="1">'+home+'</a><span>...</span>' : '';
            }
            var end = current + opts.count;
            var start = '';
            //修复到最后一页时比第一页少显示两页
            start = current === pageCount ? current - opts.count - 2 : current - opts.count;
            ((start > 1 && current < opts.count) || current == 1) && end++;
            (current > pageCount - opts.count && current >= pageCount) && start++;
            for (;start <= end; start++) {
                if(start <= pageCount && start >= 1){
                    if(start != current){
                        html += '<a href="javascript:;" data-page="'+start+'">'+ start +'</a>';
                    }else{
                        html += '<span class="'+opts.activeCls+'">'+start+'</span>';
                    }
                }
            }
            if(current + opts.count < pageCount && current >= 1 && pageCount > opts.count){
                var end = opts.coping && opts.endPage ? opts.endPage : pageCount;
                html += opts.coping ? '<span>...</span><a href="javascript:;" data-page="'+pageCount+'">'+end+'</a>' : '';
            }
            if(opts.keepShowPN || current < pageCount){//下一页
                html += '<a href="javascript:;" class="'+opts.nextCls+'">'+opts.nextContent+'</a>'
            }else{
                if(opts.keepShowPN == false){
                    $obj.find('.'+opts.nextCls) && $obj.find('.'+opts.nextCls).remove();
                }
            }
            html += opts.jump ? '<input type="text" class="'+opts.jumpIptCls+'"><a href="javascript:;" class="'+opts.jumpBtnCls+'">'+opts.jumpBtn+'</a>' : '';
            $obj.empty().html(html);
        };

        //绑定事件
        this.eventBind = function(){
            var that = this;
            var pageCount = that.getPageCount();//总页数
            var index = 1;
            $obj.off().on('click','a',function(){
                if($(this).hasClass(opts.nextCls)){
                    if($obj.find('.'+opts.activeCls).text() >= pageCount){
                        $(this).addClass('disabled');
                        return false;
                    }else{
                        index = parseInt($obj.find('.'+opts.activeCls).text()) + 1;
                    }
                }else if($(this).hasClass(opts.prevCls)){
                    if($obj.find('.'+opts.activeCls).text() <= 1){
                        $(this).addClass('disabled');
                        return false;
                    }else{
                        index = parseInt($obj.find('.'+opts.activeCls).text()) - 1;
                    }
                }else if($(this).hasClass(opts.jumpBtnCls)){
                    if($obj.find('.'+opts.jumpIptCls).val() !== ''){
                        index = parseInt($obj.find('.'+opts.jumpIptCls).val());
                    }else{
                        return;
                    }
                }else{
                    index = parseInt($(this).data('page'));
                }
                that.filling(index);
                typeof opts.callback === 'function' && opts.callback(that);
            });
            //输入跳转的页码
            $obj.on('input propertychange','.'+opts.jumpIptCls,function(){
                var $this = $(this);
                var val = $this.val();
                var reg = /[^\d]/g;
                if (reg.test(val)) {
                    $this.val(val.replace(reg, ''));
                }
                (parseInt(val) > pageCount) && $this.val(pageCount);
                if(parseInt(val) === 0){//最小值为1
                    $this.val(1);
                }
            });
            //回车跳转指定页码
            $document.keydown(function(e){
                if(e.keyCode == 13 && $obj.find('.'+opts.jumpIptCls).val()){
                    var index = parseInt($obj.find('.'+opts.jumpIptCls).val());
                    that.filling(index);
                    typeof opts.callback === 'function' && opts.callback(that);
                }
            });
        };

        //初始化
        this.init = function(){
            this.filling(opts.current);
            this.eventBind();
            if(opts.isHide && this.getPageCount() == '1' || this.getPageCount() == '0') $obj.hide();
        };
        this.init();
    };

    $.fn.pagination = function(parameter,callback){
        if(typeof parameter == 'function'){//重载
            callback = parameter;
            parameter = {};
        }else{
            parameter = parameter || {};
            callback = callback || function(){};
        }
        var options = $.extend({},defaults,parameter);
        return this.each(function(){
            var pagination = new Pagination(this, options);
            callback(pagination);
        });
    };

})(jQuery);

(function($) {
    'use strict';

    var methods = {
        init: function(options) {
            return this.each(function() {
                this.self = $(this);

                methods.destroy.call(this.self);

                this.opt = $.extend(true, {}, $.fn.raty.defaults, options, this.self.data());

                methods._adjustCallback.call(this);
                methods._adjustNumber.call(this);
                methods._adjustHints.call(this);

                this.opt.score = methods._adjustedScore.call(this, this.opt.score);

                if (this.opt.starType !== 'img') {
                    methods._adjustStarType.call(this);
                }

                methods._adjustPath.call(this);
                methods._createStars.call(this);

                if (this.opt.cancel) {
                    methods._createCancel.call(this);
                }

                if (this.opt.precision) {
                    methods._adjustPrecision.call(this);
                }

                methods._createScore.call(this);
                methods._apply.call(this, this.opt.score);
                methods._setTitle.call(this, this.opt.score);
                methods._target.call(this, this.opt.score);

                if (this.opt.readOnly) {
                    methods._lock.call(this);
                } else {
                    this.style.cursor = 'pointer';

                    methods._binds.call(this);
                }
            });
        },

        _adjustCallback: function() {
            var options = ['number', 'readOnly', 'score', 'scoreName', 'target', 'path'];

            for (var i = 0; i < options.length; i++) {
                if (typeof this.opt[options[i]] === 'function') {
                    this.opt[options[i]] = this.opt[options[i]].call(this);
                }
            }
        },

        _adjustedScore: function(score) {
            if (!score) {
                return score;
            }

            return methods._between(score, 0, this.opt.number);
        },

        _adjustHints: function() {
            if (!this.opt.hints) {
                this.opt.hints = [];
            }

            if (!this.opt.halfShow && !this.opt.half) {
                return;
            }

            var steps = this.opt.precision ? 10 : 2;

            for (var i = 0; i < this.opt.number; i++) {
                var group = this.opt.hints[i];

                if (Object.prototype.toString.call(group) !== '[object Array]') {
                    group = [group];
                }

                this.opt.hints[i] = [];

                for (var j = 0; j < steps; j++) {
                    var
                        hint = group[j],
                        last = group[group.length - 1];

                    if (last === undefined) {
                        last = null;
                    }

                    this.opt.hints[i][j] = hint === undefined ? last : hint;
                }
            }
        },

        _adjustNumber: function() {
            this.opt.number = methods._between(this.opt.number, 1, this.opt.numberMax);
        },

        _adjustPath: function() {
            this.opt.path = this.opt.path || '';

            if (this.opt.path && this.opt.path.charAt(this.opt.path.length - 1) !== '/') {
                this.opt.path += '/';
            }
        },

        _adjustPrecision: function() {
            this.opt.half = true;
        },

        _adjustStarType: function() {
            var replaces = ['cancelOff', 'cancelOn', 'starHalf', 'starOff', 'starOn'];

            this.opt.path = '';

            for (var i = 0; i < replaces.length; i++) {
                this.opt[replaces[i]] = this.opt[replaces[i]].replace('.', '-');
            }
        },

        _apply: function(score) {
            methods._fill.call(this, score);

            if (score) {
                if (score > 0) {
                    this.score.val(score);
                }

                methods._roundStars.call(this, score);
            }
        },

        _between: function(value, min, max) {
            return Math.min(Math.max(parseFloat(value), min), max);
        },

        _binds: function() {
            if (this.cancel) {
                methods._bindOverCancel.call(this);
                methods._bindClickCancel.call(this);
                methods._bindOutCancel.call(this);
            }

            methods._bindOver.call(this);
            methods._bindClick.call(this);
            methods._bindOut.call(this);
        },

        _bindClick: function() {
            var that = this;

            that.stars.on('click.raty', function(evt) {
                var
                    execute = true,
                    score   = (that.opt.half || that.opt.precision) ? that.self.data('score') : (this.alt || $(this).data('alt'));

                if (that.opt.click) {
                    execute = that.opt.click.call(that, +score, evt);
                }

                if (execute || execute === undefined) {
                    if (that.opt.half && !that.opt.precision) {
                        score = methods._roundHalfScore.call(that, score);
                    }

                    methods._apply.call(that, score);
                }
            });
        },

        _bindClickCancel: function() {
            var that = this;

            that.cancel.on('click.raty', function(evt) {
                that.score.removeAttr('value');

                if (that.opt.click) {
                    that.opt.click.call(that, null, evt);
                }
            });
        },

        _bindOut: function() {
            var that = this;

            that.self.on('mouseleave.raty', function(evt) {
                var score = +that.score.val() || undefined;

                methods._apply.call(that, score);
                methods._target.call(that, score, evt);
                methods._resetTitle.call(that);

                if (that.opt.mouseout) {
                    that.opt.mouseout.call(that, score, evt);
                }
            });
        },

        _bindOutCancel: function() {
            var that = this;

            that.cancel.on('mouseleave.raty', function(evt) {
                var icon = that.opt.cancelOff;

                if (that.opt.starType !== 'img') {
                    icon = that.opt.cancelClass + ' ' + icon;
                }

                methods._setIcon.call(that, this, icon);

                if (that.opt.mouseout) {
                    var score = +that.score.val() || undefined;

                    that.opt.mouseout.call(that, score, evt);
                }
            });
        },

        _bindOver: function() {
            var
                that   = this,
                action = that.opt.half ? 'mousemove.raty' : 'mouseover.raty';

            that.stars.on(action, function(evt) {
                var score = methods._getScoreByPosition.call(that, evt, this);

                methods._fill.call(that, score);

                if (that.opt.half) {
                    methods._roundStars.call(that, score, evt);
                    methods._setTitle.call(that, score, evt);

                    that.self.data('score', score);
                }

                methods._target.call(that, score, evt);

                if (that.opt.mouseover) {
                    that.opt.mouseover.call(that, score, evt);
                }
            });
        },

        _bindOverCancel: function() {
            var that = this;

            that.cancel.on('mouseover.raty', function(evt) {
                var
                    starOff = that.opt.path + that.opt.starOff,
                    icon    = that.opt.cancelOn;

                if (that.opt.starType === 'img') {
                    that.stars.attr('src', starOff);
                } else {
                    icon = that.opt.cancelClass + ' ' + icon;

                    that.stars.attr('class', starOff);
                }

                methods._setIcon.call(that, this, icon);
                methods._target.call(that, null, evt);

                if (that.opt.mouseover) {
                    that.opt.mouseover.call(that, null);
                }
            });
        },

        _buildScoreField: function() {
            return $('<input />', { name: this.opt.scoreName, type: 'hidden' }).appendTo(this);
        },

        _createCancel: function() {
            var
                icon   = this.opt.path + this.opt.cancelOff,
                cancel = $('<' + this.opt.starType + ' />', { title: this.opt.cancelHint, 'class': this.opt.cancelClass });

            if (this.opt.starType === 'img') {
                cancel.attr({ src: icon, alt: 'x' });
            } else {
                // TODO: use $.data
                cancel.attr('data-alt', 'x').addClass(icon);
            }

            if (this.opt.cancelPlace === 'left') {
                this.self.prepend('&#160;').prepend(cancel);
            } else {
                this.self.append('&#160;').append(cancel);
            }

            this.cancel = cancel;
        },

        _createScore: function() {
            var score = $(this.opt.targetScore);

            this.score = score.length ? score : methods._buildScoreField.call(this);
        },

        _createStars: function() {
            for (var i = 1; i <= this.opt.number; i++) {
                var
                    name  = methods._nameForIndex.call(this, i),
                    attrs = { alt: i, src: this.opt.path + this.opt[name] };

                if (this.opt.starType !== 'img') {
                    attrs = { 'data-alt': i, 'class': attrs.src }; // TODO: use $.data.
                }

                attrs.title = methods._getHint.call(this, i);

                $('<' + this.opt.starType + ' />', attrs).appendTo(this);

                if (this.opt.space) {
                    this.self.append(i < this.opt.number ? '&#160;' : '');
                }
            }

            this.stars = this.self.children(this.opt.starType);
        },

        _error: function(message) {
            $(this).text(message);

            $.error(message);
        },

        _fill: function(score) {
            var hash = 0;

            for (var i = 1; i <= this.stars.length; i++) {
                var
                    icon,
                    star   = this.stars[i - 1],
                    turnOn = methods._turnOn.call(this, i, score);

                if (this.opt.iconRange && this.opt.iconRange.length > hash) {
                    var irange = this.opt.iconRange[hash];

                    icon = methods._getRangeIcon.call(this, irange, turnOn);

                    if (i <= irange.range) {
                        methods._setIcon.call(this, star, icon);
                    }

                    if (i === irange.range) {
                        hash++;
                    }
                } else {
                    icon = this.opt[turnOn ? 'starOn' : 'starOff'];

                    methods._setIcon.call(this, star, icon);
                }
            }
        },

        _getFirstDecimal: function(number) {
            var
                decimal = number.toString().split('.')[1],
                result  = 0;

            if (decimal) {
                result = parseInt(decimal.charAt(0), 10);

                if (decimal.slice(1, 5) === '9999') {
                    result++;
                }
            }

            return result;
        },

        _getRangeIcon: function(irange, turnOn) {
            return turnOn ? irange.on || this.opt.starOn : irange.off || this.opt.starOff;
        },

        _getScoreByPosition: function(evt, icon) {
            var score = parseInt(icon.alt || icon.getAttribute('data-alt'), 10);

            if (this.opt.half) {
                var
                    size    = methods._getWidth.call(this),
                    percent = parseFloat((evt.pageX - $(icon).offset().left) / size);

                score = score - 1 + percent;
            }

            return score;
        },

        _getHint: function(score, evt) {
            if (score !== 0 && !score) {
                return this.opt.noRatedMsg;
            }

            var
                decimal = methods._getFirstDecimal.call(this, score),
                integer = Math.ceil(score),
                group   = this.opt.hints[(integer || 1) - 1],
                hint    = group,
                set     = !evt || this.move;

            if (this.opt.precision) {
                if (set) {
                    decimal = decimal === 0 ? 9 : decimal - 1;
                }

                hint = group[decimal];
            } else if (this.opt.halfShow || this.opt.half) {
                decimal = set && decimal === 0 ? 1 : decimal > 5 ? 1 : 0;

                hint = group[decimal];
            }

            return hint === '' ? '' : hint || score;
        },

        _getWidth: function() {
            var width = this.stars[0].width || parseFloat(this.stars.eq(0).css('font-size'));

            if (!width) {
                methods._error.call(this, 'Could not get the icon width!');
            }

            return width;
        },

        _lock: function() {
            var hint = methods._getHint.call(this, this.score.val());

            this.style.cursor = '';
            this.title        = hint;

            this.score.prop('readonly', true);
            this.score.prop('disabled', true);
            this.stars.prop('title', hint);

            if (this.cancel) {
                this.cancel.hide();
            }

            this.self.data('readonly', true);
        },

        _nameForIndex: function(i) {
            return this.opt.score && this.opt.score >= i ? 'starOn' : 'starOff';
        },

        _resetTitle: function() {
            for (var i = 0; i < this.opt.number; i++) {
                this.stars[i].title = methods._getHint.call(this, i + 1);
            }
        },

        _roundHalfScore: function(score) {
            var
                integer = parseInt(score, 10),
                decimal = methods._getFirstDecimal.call(this, score);

            if (decimal !== 0) {
                decimal = decimal > 5 ? 1 : 0.5;
            }

            return integer + decimal;
        },

        _roundStars: function(score, evt) {
            var
                decimal = (score % 1).toFixed(2),
                name ;

            if (evt || this.move) {
                name = decimal > 0.5 ? 'starOn' : 'starHalf';
            } else if (decimal > this.opt.round.down) { // Up: [x.76 .. x.99]
                name = 'starOn';

                if (this.opt.halfShow && decimal < this.opt.round.up) { // Half: [x.26 .. x.75]
                    name = 'starHalf';
                } else if (decimal < this.opt.round.full) { // Down: [x.00 .. x.5]
                    name = 'starOff';
                }
            }

            if (name) {
                var
                    icon = this.opt[name],
                    star = this.stars[Math.ceil(score) - 1];

                methods._setIcon.call(this, star, icon);
            } // Full down: [x.00 .. x.25]
        },

        _setIcon: function(star, icon) {
            star[this.opt.starType === 'img' ? 'src' : 'className'] = this.opt.path + icon;
        },

        _setTarget: function(target, score) {
            if (score) {
                score = this.opt.targetFormat.toString().replace('{score}', score);
            }

            if (target.is(':input')) {
                target.val(score);
            } else {
                target.html(score);
            }
        },

        _setTitle: function(score, evt) {
            if (score) {
                var
                    integer = parseInt(Math.ceil(score), 10),
                    star    = this.stars[integer - 1];

                star.title = methods._getHint.call(this, score, evt);
            }
        },

        _target: function(score, evt) {
            if (this.opt.target) {
                var target = $(this.opt.target);

                if (!target.length) {
                    methods._error.call(this, 'Target selector invalid or missing!');
                }

                var mouseover = evt && evt.type === 'mouseover';

                if (score === undefined) {
                    score = this.opt.targetText;
                } else if (score === null) {
                    score = mouseover ? this.opt.cancelHint : this.opt.targetText;
                } else {
                    if (this.opt.targetType === 'hint') {
                        score = methods._getHint.call(this, score, evt);
                    } else if (this.opt.precision) {
                        score = parseFloat(score).toFixed(1);
                    }

                    var mousemove = evt && evt.type === 'mousemove';

                    if (!mouseover && !mousemove && !this.opt.targetKeep) {
                        score = this.opt.targetText;
                    }
                }

                methods._setTarget.call(this, target, score);
            }
        },

        _turnOn: function(i, score) {
            return this.opt.single ? (i === score) : (i <= score);
        },

        _unlock: function() {
            this.style.cursor = 'pointer';
            this.removeAttribute('title');

            this.score.removeAttr('readonly');

            this.self.data('readonly', false);

            for (var i = 0; i < this.opt.number; i++) {
                this.stars[i].title = methods._getHint.call(this, i + 1);
            }

            if (this.cancel) {
                this.cancel.css('display', '');
            }
        },

        cancel: function(click) {
            return this.each(function() {
                var self = $(this);

                if (self.data('readonly') !== true) {
                    methods[click ? 'click' : 'score'].call(self, null);

                    this.score.removeAttr('value');
                }
            });
        },

        click: function(score) {
            return this.each(function() {
                if ($(this).data('readonly') !== true) {
                    score = methods._adjustedScore.call(this, score);

                    methods._apply.call(this, score);

                    if (this.opt.click) {
                        this.opt.click.call(this, score, $.Event('click'));
                    }

                    methods._target.call(this, score);
                }
            });
        },

        destroy: function() {
            return this.each(function() {
                var
                    self = $(this),
                    raw  = self.data('raw');

                if (raw) {
                    self.off('.raty').empty().css({ cursor: raw.style.cursor }).removeData('readonly');
                } else {
                    self.data('raw', self.clone()[0]);
                }
            });
        },

        getScore: function() {
            var
                score = [],
                value ;

            this.each(function() {
                value = this.score.val();

                score.push(value ? +value : undefined);
            });

            return (score.length > 1) ? score : score[0];
        },

        move: function(score) {
            return this.each(function() {
                var
                    integer  = parseInt(score, 10),
                    decimal  = methods._getFirstDecimal.call(this, score);

                if (integer >= this.opt.number) {
                    integer = this.opt.number - 1;
                    decimal = 10;
                }

                var
                    width   = methods._getWidth.call(this),
                    steps   = width / 10,
                    star    = $(this.stars[integer]),
                    percent = star.offset().left + steps * decimal,
                    evt     = $.Event('mousemove', { pageX: percent });

                this.move = true;

                star.trigger(evt);

                this.move = false;
            });
        },

        readOnly: function(readonly) {
            return this.each(function() {
                var self = $(this);

                if (self.data('readonly') !== readonly) {
                    if (readonly) {
                        self.off('.raty').children(this.opt.starType).off('.raty');

                        methods._lock.call(this);
                    } else {
                        methods._binds.call(this);
                        methods._unlock.call(this);
                    }

                    self.data('readonly', readonly);
                }
            });
        },

        reload: function() {
            return methods.set.call(this, {});
        },

        score: function() {
            var self = $(this);

            return arguments.length ? methods.setScore.apply(self, arguments) : methods.getScore.call(self);
        },

        set: function(options) {
            return this.each(function() {
                $(this).raty($.extend({}, this.opt, options));
            });
        },

        setScore: function(score) {
            return this.each(function() {
                if ($(this).data('readonly') !== true) {
                    score = methods._adjustedScore.call(this, score);

                    methods._apply.call(this, score);
                    methods._target.call(this, score);
                }
            });
        }
    };

    $.fn.raty = function(method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist!');
        }
    };

    $.fn.raty.defaults = {
        cancel:       false,
        cancelClass:  'raty-cancel',
        cancelHint:   'Cancel this rating!',
        cancelOff:    '../img/cancel-off.png',
        cancelOn:     '../img/cancel-on.png',
        cancelPlace:  'left',
        click:        undefined,
        half:         false,
        halfShow:     true,
        hints:        ['bad', 'poor', 'regular', 'good', 'gorgeous'],
        iconRange:    undefined,
        mouseout:     undefined,
        mouseover:    undefined,
        noRatedMsg:   'Not rated yet!',
        number:       5,
        numberMax:    20,
        path:         undefined,
        precision:    false,
        readOnly:     false,
        round:        { down: 0.25, full: 0.6, up: 0.76 },
        score:        undefined,
        scoreName:    'score',
        single:       false,
        space:        true,
        starHalf:     '../img/star-half.png',
        starOff:      '../img/star-off.png',
        starOn:       '../img/star-on.png',
        starType:     'img',
        target:       undefined,
        targetFormat: '{score}',
        targetKeep:   false,
        targetScore:  undefined,
        targetText:   '',
        targetType:   'hint'
    };
})(jQuery);
/*!
 * An jQuery | zepto plugin for lazy loading images.
 * author -> jieyou
 * see https://github.com/jieyou/lazyload
 * use some tuupola's code https://github.com/tuupola/jquery_lazyload (BSD)
 * use component's throttle https://github.com/component/throttle (MIT)
 */
(function($,undefined){
    var w = window,
        $window = $(w),
        defaultOptions = {
            threshold                   : 0,
            failure_limit               : 0,
            event                       : 'scroll',
            effect                      : 'show',
            effect_params               : null,
            container                   : w,
            data_attribute              : 'original',
            data_srcset_attribute       : 'original-srcset',
            skip_invisible              : true,
            appear                      : emptyFn,
            load                        : emptyFn,
            vertical_only               : false,
            check_appear_throttle_time  : 300,
            url_rewriter_fn             : emptyFn,
            no_fake_img_loader          : false,
            placeholder_data_img        : '',
            placeholder_real_img        : ''
            // todo : 将某些属性用global来配置，而不是每次在$(selector).lazyload({})内配置
        },
        type // function

    function emptyFn(){}

    type = (function(){
        var object_prototype_toString = Object.prototype.toString
        return function(obj){
            // todo: compare the speeds of replace string twice or replace a regExp
            return object_prototype_toString.call(obj).replace('[object ','').replace(']','')
        }
    })()

    function belowthefold($element, options){
        var fold
        if(options._$container == $window){
            fold = ('innerHeight' in w ? w.innerHeight : $window.height()) + $window.scrollTop()
        }else{
            fold = options._$container.offset().top + options._$container.height()
        }
        return fold <= $element.offset().top - options.threshold
    }

    function rightoffold($element, options){
        var fold
        if(options._$container == $window){
            // Zepto do not support `$window.scrollLeft()` yet.
            fold = $window.width() + ($.fn.scrollLeft?$window.scrollLeft():w.pageXOffset)
        }else{
            fold = options._$container.offset().left + options._$container.width()
        }
        return fold <= $element.offset().left - options.threshold
    }

    function abovethetop($element, options){
        var fold
        if(options._$container == $window){
            fold = $window.scrollTop()
        }else{
            fold = options._$container.offset().top
        }
        // console.log('abovethetop fold '+ fold)
        // console.log('abovethetop $element.height() '+ $element.height())
        return fold >= $element.offset().top + options.threshold  + $element.height()
    }

    function leftofbegin($element, options){
        var fold
        if(options._$container == $window){
            // Zepto do not support `$window.scrollLeft()` yet.
            fold = $.fn.scrollLeft?$window.scrollLeft():w.pageXOffset
        }else{
            fold = options._$container.offset().left
        }
        return fold >= $element.offset().left + options.threshold + $element.width()
    }

    function checkAppear($elements, options){
        var counter = 0
        $elements.each(function(i,e){
            var $element = $elements.eq(i)
            if(($element.width() <= 0 && $element.height() <= 0) || $element.css('display') === 'none'){
                return
            }
            function appear(){
                $element.trigger('_lazyload_appear')
                // if we found an image we'll load, reset the counter
                counter = 0
            }
            // If vertical_only is set to true, only check the vertical to decide appear or not
            // In most situations, page can only scroll vertically, set vertical_only to true will improve performance
            if(options.vertical_only){
                if(abovethetop($element, options)){
                    // Nothing.
                }else if(!belowthefold($element, options)){
                    appear()
                }else{
                    if(++counter > options.failure_limit){
                        return false
                    }
                }
            }else{
                if(abovethetop($element, options) || leftofbegin($element, options)){
                    // Nothing.
                }else if(!belowthefold($element, options) && !rightoffold($element, options)){
                    appear()
                }else{
                    if(++counter > options.failure_limit){
                        return false
                    }
                }
            }
        })
    }
    
    function loadCache($img, originalSrc) {
        var loadImage = function (src) {
            var def =  $.Deferred(), image = new Image();
            image.src = src;
            image.onload = function () {
                def.resolve(image.src)
            };
            image.onerror = function () {
                def.resolve(window.GLOBAL_CONFIG.siteMapUrl)
            };
            return def.promise()
        };
        loadImage(originalSrc).then(function (src) {
            $img.attr('src', src)
        })
    }

    // Remove image from array so it is not looped next time.
    function getUnloadElements($elements){
        return $elements.filter(function(i){
            return !$elements.eq(i).data('_lazyload_loadStarted')
        })
    }

    // throttle : https://github.com/component/throttle , MIT License
    function throttle (func, wait) {
        var ctx, args, rtn, timeoutID // caching
        var last = 0

        return function throttled () {
            ctx = this
            args = arguments
            var delta = new Date() - last
            if (!timeoutID)
                if (delta >= wait) call()
                else timeoutID = setTimeout(call, wait - delta)
            return rtn
        }

        function call () {
            timeoutID = 0
            last = +new Date()
            rtn = func.apply(ctx, args)
            ctx = null
            args = null
        }
    }

    if(!$.fn.hasOwnProperty('lazyload')){

        $.fn.lazyload = function(options){
            var $elements = this,
                isScrollEvent,
                isScrollTypeEvent,
                throttleCheckAppear

            if(!$.isPlainObject(options)){
                options = {}
            }

            $.each(defaultOptions,function(k,v){
                var typeK = type(options[k])
                if($.inArray(k,['threshold','failure_limit','check_appear_throttle_time']) != -1){ // these params can be a string
                    if(typeK == 'String'){
                        options[k] = parseInt(options[k],10)
                    }else if(typeK != 'Number'){
                        options[k] = v
                    }
                }else if(k == 'container'){ // options.container can be a seletor string \ dom \ jQuery object
                    if(options.hasOwnProperty(k)){
                        if(options[k] == w || options[k] == document){
                            options._$container = $window
                        }else{
                            options._$container = $(options[k])
                        }
                    }else{
                        options._$container = $window
                    }
                    delete options.container
                }else if(defaultOptions.hasOwnProperty(k) && (!options.hasOwnProperty(k) || (typeK != type(defaultOptions[k])))){
                    options[k] = v
                }
            });
            options.placeholder_data_img = options.placeholder
            options.placeholder_real_img = options.placeholder

            isScrollEvent = options.event == 'scroll'
            throttleCheckAppear = options.check_appear_throttle_time == 0?
                checkAppear
                :throttle(checkAppear,options.check_appear_throttle_time)

            // isScrollTypeEvent cantains custom scrollEvent . Such as 'scrollstart' & 'scrollstop'
            // https://github.com/search?utf8=%E2%9C%93&q=scrollstart
            isScrollTypeEvent = isScrollEvent || options.event == 'scrollstart' || options.event == 'scrollstop'

            $elements.each(function(i,e){
                var element = this,
                    $element = $elements.eq(i),
                    placeholderSrc = $element.attr('src'),
                    originalSrcInAttr = $element.attr('data-'+options.data_attribute), // `data-original` attribute value
                    originalSrc = options.url_rewriter_fn == emptyFn?
                        originalSrcInAttr:
                        options.url_rewriter_fn.call(element,$element,originalSrcInAttr),
                    originalSrcset = $element.attr('data-'+options.data_srcset_attribute),
                    isImg = $element.is('img')

                if($element.data('_lazyload_loadStarted') || placeholderSrc == originalSrc){
                    $element.data('_lazyload_loadStarted',true)
                    $elements = getUnloadElements($elements)
                    return
                }

                $element.data('_lazyload_loadStarted',false)

                // If element is an img and no src attribute given, use placeholder.
                if(isImg && !placeholderSrc){
                    // For browsers that do not support data image.
                    $element.one('error',function(){ // `on` -> `one` : IE6 triggered twice error event sometimes
                        $element.attr('src',options.placeholder_real_img)
                    }).attr('src',options.placeholder_data_img)
                }

                // When appear is triggered load original image.
                $element.one('_lazyload_appear',function(){
                    var effectParamsIsArray = $.isArray(options.effect_params),
                        effectIsNotImmediacyShow
                    function loadFunc(){
                        // In most situations, the effect is immediacy show, at this time there is no need to hide element first
                        // Hide this element may cause css reflow, call it as less as possible
                        if(effectIsNotImmediacyShow){
                            // todo: opacity:0 for fadeIn effect
                            $element.hide()
                        }
                        if(isImg){
                            // attr srcset first
                            if(originalSrcset){
                                $element.attr('srcset', originalSrcset)
                            }
                            if(originalSrc){
                                loadCache($element, originalSrc)
                            }
                        }else{
                            $element.css('background-image','url("' + originalSrc + '")')
                        }
                        if(effectIsNotImmediacyShow){
                            $element[options.effect].apply($element,effectParamsIsArray?options.effect_params:[])
                        }
                        $elements = getUnloadElements($elements)
                    }
                    if(!$element.data('_lazyload_loadStarted')){
                        effectIsNotImmediacyShow = (options.effect != 'show' && $.fn[options.effect] && (!options.effect_params || (effectParamsIsArray && options.effect_params.length == 0)))
                        if(options.appear != emptyFn){
                            options.appear.call(element, $element, $elements.length, options)
                        }
                        $element.data('_lazyload_loadStarted',true)
                        if(options.no_fake_img_loader || originalSrcset){
                            if(options.load != emptyFn){
                                $element.one('load',function(){
                                    options.load.call(element, $element, $elements.length, options)
                                })
                            }
                            loadFunc()
                        }else{
                            var $img = $('<img />').one('load', function(){ // `on` -> `one` : IE6 triggered twice load event sometimes
                                loadFunc()
                                if(options.load != emptyFn){
                                    options.load.call(element, $element, $elements.length, options)
                                }
                            });
                            loadCache($img, originalSrc)
                        }
                    }
                })

                // When wanted event is triggered load original image
                // by triggering appear.
                if (!isScrollTypeEvent){
                    $element.on(options.event, function(){
                        if (!$element.data('_lazyload_loadStarted')){
                            $element.trigger('_lazyload_appear')
                        }
                    })
                }
            })

            // Fire one scroll event per scroll. Not one scroll event per image.
            if(isScrollTypeEvent){
                options._$container.on(options.event, function(){
                    throttleCheckAppear($elements, options)
                })
            }

            // Check if something appears when window is resized.
            // Force initial check if images should appear when window is onload.
            $window.on('resize load', function(){
                throttleCheckAppear($elements, options)
            })

            // Force initial check if images should appear.
            $(function(){
                throttleCheckAppear($elements, options)
            })

            return this
        }
    }
})(jQuery);
/*!
 * jQuery Validation Plugin v1.15.1
 *
 * http://jqueryvalidation.org/
 *
 * Copyright (c) 2016 Jörn Zaefferer
 * Released under the MIT license
 */
(function( $ ) {

    $.extend( $.fn, {

        // http://jqueryvalidation.org/validate/
        validate: function( options ) {

            // If nothing is selected, return nothing; can't chain anyway
            if ( !this.length ) {
                if ( options && options.debug && window.console ) {
                    console.warn( "Nothing selected, can't validate, returning nothing." );
                }
                return;
            }

            // Check if a validator for this form was already created
            var validator = $.data( this[ 0 ], "validator" );
            if ( validator ) {
                return validator;
            }

            // Add novalidate tag if HTML5.
            this.attr( "novalidate", "novalidate" );

            validator = new $.validator( options, this[ 0 ] );
            $.data( this[ 0 ], "validator", validator );

            if ( validator.settings.onsubmit ) {

                this.on( "click.validate", ":submit", function( event ) {
                    if ( validator.settings.submitHandler ) {
                        validator.submitButton = event.target;
                    }

                    // Allow suppressing validation by adding a cancel class to the submit button
                    if ( $( this ).hasClass( "cancel" ) ) {
                        validator.cancelSubmit = true;
                    }

                    // Allow suppressing validation by adding the html5 formnovalidate attribute to the submit button
                    if ( $( this ).attr( "formnovalidate" ) !== undefined ) {
                        validator.cancelSubmit = true;
                    }
                } );

                // Validate the form on submit
                this.on( "submit.validate", function( event ) {
                    if ( validator.settings.debug ) {

                        // Prevent form submit to be able to see console output
                        event.preventDefault();
                    }
                    function handle() {
                        var hidden, result;
                        if ( validator.settings.submitHandler ) {
                            if ( validator.submitButton ) {

                                // Insert a hidden input as a replacement for the missing submit button
                                hidden = $( "<input type='hidden'/>" )
                                    .attr( "name", validator.submitButton.name )
                                    .val( $( validator.submitButton ).val() )
                                    .appendTo( validator.currentForm );
                            }
                            result = validator.settings.submitHandler.call( validator, validator.currentForm, event );
                            if ( validator.submitButton ) {

                                // And clean up afterwards; thanks to no-block-scope, hidden can be referenced
                                hidden.remove();
                            }
                            if ( result !== undefined ) {
                                return result;
                            }
                            return false;
                        }
                        return true;
                    }

                    // Prevent submit for invalid forms or custom submit handlers
                    if ( validator.cancelSubmit ) {
                        validator.cancelSubmit = false;
                        return handle();
                    }
                    if ( validator.form() ) {
                        if ( validator.pendingRequest ) {
                            validator.formSubmitted = true;
                            return false;
                        }
                        return handle();
                    } else {
                        validator.focusInvalid();
                        return false;
                    }
                } );
            }

            return validator;
        },

        // http://jqueryvalidation.org/valid/
        valid: function() {
            var valid, validator, errorList;

            if ( $( this[ 0 ] ).is( "form" ) ) {
                valid = this.validate().form();
            } else {
                errorList = [];
                valid = true;
                validator = $( this[ 0 ].form ).validate();
                this.each( function() {
                    valid = validator.element( this ) && valid;
                    if ( !valid ) {
                        errorList = errorList.concat( validator.errorList );
                    }
                } );
                validator.errorList = errorList;
            }
            return valid;
        },

        // http://jqueryvalidation.org/rules/
        rules: function( command, argument ) {
            var element = this[ 0 ],
                settings, staticRules, existingRules, data, param, filtered;

            // If nothing is selected, return empty object; can't chain anyway
            if ( element == null || element.form == null ) {
                return;
            }

            if ( command ) {
                settings = $.data( element.form, "validator" ).settings;
                staticRules = settings.rules;
                existingRules = $.validator.staticRules( element );
                switch ( command ) {
                    case "add":
                        $.extend( existingRules, $.validator.normalizeRule( argument ) );

                        // Remove messages from rules, but allow them to be set separately
                        delete existingRules.messages;
                        staticRules[ element.name ] = existingRules;
                        if ( argument.messages ) {
                            settings.messages[ element.name ] = $.extend( settings.messages[ element.name ], argument.messages );
                        }
                        break;
                    case "remove":
                        if ( !argument ) {
                            delete staticRules[ element.name ];
                            return existingRules;
                        }
                        filtered = {};
                        $.each( argument.split( /\s/ ), function( index, method ) {
                            filtered[ method ] = existingRules[ method ];
                            delete existingRules[ method ];
                            if ( method === "required" ) {
                                $( element ).removeAttr( "aria-required" );
                            }
                        } );
                        return filtered;
                }
            }

            data = $.validator.normalizeRules(
                $.extend(
                    {},
                    $.validator.classRules( element ),
                    $.validator.attributeRules( element ),
                    $.validator.dataRules( element ),
                    $.validator.staticRules( element )
                ), element );

            // Make sure required is at front
            if ( data.required ) {
                param = data.required;
                delete data.required;
                data = $.extend( { required: param }, data );
                $( element ).attr( "aria-required", "true" );
            }

            // Make sure remote is at back
            if ( data.remote ) {
                param = data.remote;
                delete data.remote;
                data = $.extend( data, { remote: param } );
            }

            return data;
        }
    } );

// Custom selectors
    $.extend( $.expr[ ":" ], {

        // http://jqueryvalidation.org/blank-selector/
        blank: function( a ) {
            return !$.trim( "" + $( a ).val() );
        },

        // http://jqueryvalidation.org/filled-selector/
        filled: function( a ) {
            var val = $( a ).val();
            return val !== null && !!$.trim( "" + val );
        },

        // http://jqueryvalidation.org/unchecked-selector/
        unchecked: function( a ) {
            return !$( a ).prop( "checked" );
        }
    } );

// Constructor for validator
    $.validator = function( options, form ) {
        this.settings = $.extend( true, {}, $.validator.defaults, options );
        this.currentForm = form;
        this.init();
    };

// http://jqueryvalidation.org/jQuery.validator.format/
    $.validator.format = function( source, params ) {
        if ( arguments.length === 1 ) {
            return function() {
                var args = $.makeArray( arguments );
                args.unshift( source );
                return $.validator.format.apply( this, args );
            };
        }
        if ( params === undefined ) {
            return source;
        }
        if ( arguments.length > 2 && params.constructor !== Array  ) {
            params = $.makeArray( arguments ).slice( 1 );
        }
        if ( params.constructor !== Array ) {
            params = [ params ];
        }
        $.each( params, function( i, n ) {
            source = source.replace( new RegExp( "\\{" + i + "\\}", "g" ), function() {
                return n;
            } );
        } );
        return source;
    };

    $.extend( $.validator, {

        defaults: {
            messages: {},
            groups: {},
            rules: {},
            errorClass: "error",
            pendingClass: "pending",
            validClass: "valid",
            errorElement: "label",
            focusCleanup: false,
            focusInvalid: true,
            errorContainer: $( [] ),
            errorLabelContainer: $( [] ),
            onsubmit: true,
            ignore: ":hidden",
            ignoreTitle: false,
            onfocusin: function( element ) {
                this.lastActive = element;

                // Hide error label and remove error class on focus if enabled
                if ( this.settings.focusCleanup ) {
                    if ( this.settings.unhighlight ) {
                        this.settings.unhighlight.call( this, element, this.settings.errorClass, this.settings.validClass );
                    }
                    this.hideThese( this.errorsFor( element ) );
                }
            },
            onfocusout: function( element ) {
                if ( !this.checkable( element ) && ( element.name in this.submitted || !this.optional( element ) ) ) {
                    this.element( element );
                }
            },
            onkeyup: function( element, event ) {

                // Avoid revalidate the field when pressing one of the following keys
                // Shift       => 16
                // Ctrl        => 17
                // Alt         => 18
                // Caps lock   => 20
                // End         => 35
                // Home        => 36
                // Left arrow  => 37
                // Up arrow    => 38
                // Right arrow => 39
                // Down arrow  => 40
                // Insert      => 45
                // Num lock    => 144
                // AltGr key   => 225
                var excludedKeys = [
                    16, 17, 18, 20, 35, 36, 37,
                    38, 39, 40, 45, 144, 225
                ];

                if ( event.which === 9 && this.elementValue( element ) === "" || $.inArray( event.keyCode, excludedKeys ) !== -1 ) {
                    return;
                } else if ( element.name in this.submitted || element.name in this.invalid ) {
                    this.element( element );
                }
            },
            onclick: function( element ) {

                // Click on selects, radiobuttons and checkboxes
                if ( element.name in this.submitted ) {
                    this.element( element );

                    // Or option elements, check parent select in that case
                } else if ( element.parentNode.name in this.submitted ) {
                    this.element( element.parentNode );
                }
            },
            highlight: function( element, errorClass, validClass ) {
                if ( element.type === "radio" ) {
                    this.findByName( element.name ).addClass( errorClass ).removeClass( validClass );
                } else {
                    $( element ).addClass( errorClass ).removeClass( validClass );
                }
            },
            unhighlight: function( element, errorClass, validClass ) {
                if ( element.type === "radio" ) {
                    this.findByName( element.name ).removeClass( errorClass ).addClass( validClass );
                } else {
                    $( element ).removeClass( errorClass ).addClass( validClass );
                }
            }
        },

        // http://jqueryvalidation.org/jQuery.validator.setDefaults/
        setDefaults: function( settings ) {
            $.extend( $.validator.defaults, settings );
        },

        messages: {
            required: "This field is required.",
            remote: "Please fix this field.",
            email: "Please enter a valid email address.",
            url: "Please enter a valid URL.",
            date: "Please enter a valid date.",
            dateISO: "Please enter a valid date (ISO).",
            number: "Please enter a valid number.",
            digits: "Please enter only digits.",
            equalTo: "Please enter the same value again.",
            maxlength: $.validator.format( "Please enter no more than {0} characters." ),
            minlength: $.validator.format( "Please enter at least {0} characters." ),
            rangelength: $.validator.format( "Please enter a value between {0} and {1} characters long." ),
            range: $.validator.format( "Please enter a value between {0} and {1}." ),
            max: $.validator.format( "Please enter a value less than or equal to {0}." ),
            min: $.validator.format( "Please enter a value greater than or equal to {0}." ),
            step: $.validator.format( "Please enter a multiple of {0}." )
        },

        autoCreateRanges: false,

        prototype: {

            init: function() {
                this.labelContainer = $( this.settings.errorLabelContainer );
                this.errorContext = this.labelContainer.length && this.labelContainer || $( this.currentForm );
                this.containers = $( this.settings.errorContainer ).add( this.settings.errorLabelContainer );
                this.submitted = {};
                this.valueCache = {};
                this.pendingRequest = 0;
                this.pending = {};
                this.invalid = {};
                this.reset();

                var groups = ( this.groups = {} ),
                    rules;
                $.each( this.settings.groups, function( key, value ) {
                    if ( typeof value === "string" ) {
                        value = value.split( /\s/ );
                    }
                    $.each( value, function( index, name ) {
                        groups[ name ] = key;
                    } );
                } );
                rules = this.settings.rules;
                $.each( rules, function( key, value ) {
                    rules[ key ] = $.validator.normalizeRule( value );
                } );

                function delegate( event ) {

                    // Set form expando on contenteditable
                    if ( !this.form && this.hasAttribute( "contenteditable" ) ) {
                        this.form = $( this ).closest( "form" )[ 0 ];
                    }

                    var validator = $.data( this.form, "validator" ),
                        eventType = "on" + event.type.replace( /^validate/, "" ),
                        settings = validator.settings;
                    if ( settings[ eventType ] && !$( this ).is( settings.ignore ) ) {
                        settings[ eventType ].call( validator, this, event );
                    }
                }

                $( this.currentForm )
                    .on( "focusin.validate focusout.validate keyup.validate",
                        ":text, [type='password'], [type='file'], select, textarea, [type='number'], [type='search'], " +
                        "[type='tel'], [type='url'], [type='email'], [type='datetime'], [type='date'], [type='month'], " +
                        "[type='week'], [type='time'], [type='datetime-local'], [type='range'], [type='color'], " +
                        "[type='radio'], [type='checkbox'], [contenteditable]", delegate )

                    // Support: Chrome, oldIE
                    // "select" is provided as event.target when clicking a option
                    .on( "click.validate", "select, option, [type='radio'], [type='checkbox']", delegate );

                if ( this.settings.invalidHandler ) {
                    $( this.currentForm ).on( "invalid-form.validate", this.settings.invalidHandler );
                }

                // Add aria-required to any Static/Data/Class required fields before first validation
                // Screen readers require this attribute to be present before the initial submission http://www.w3.org/TR/WCAG-TECHS/ARIA2.html
                $( this.currentForm ).find( "[required], [data-rule-required], .required" ).attr( "aria-required", "true" );
            },

            // http://jqueryvalidation.org/Validator.form/
            form: function() {
                this.checkForm();
                $.extend( this.submitted, this.errorMap );
                this.invalid = $.extend( {}, this.errorMap );
                if ( !this.valid() ) {
                    $( this.currentForm ).triggerHandler( "invalid-form", [ this ] );
                }
                this.showErrors();
                return this.valid();
            },

            checkForm: function() {
                this.prepareForm();
                for ( var i = 0, elements = ( this.currentElements = this.elements() ); elements[ i ]; i++ ) {
                    this.check( elements[ i ] );
                }
                return this.valid();
            },

            // http://jqueryvalidation.org/Validator.element/
            element: function( element ) {
                var cleanElement = this.clean( element ),
                    checkElement = this.validationTargetFor( cleanElement ),
                    v = this,
                    result = true,
                    rs, group;

                if ( checkElement === undefined ) {
                    delete this.invalid[ cleanElement.name ];
                } else {
                    this.prepareElement( checkElement );
                    this.currentElements = $( checkElement );

                    // If this element is grouped, then validate all group elements already
                    // containing a value
                    group = this.groups[ checkElement.name ];
                    if ( group ) {
                        $.each( this.groups, function( name, testgroup ) {
                            if ( testgroup === group && name !== checkElement.name ) {
                                cleanElement = v.validationTargetFor( v.clean( v.findByName( name ) ) );
                                if ( cleanElement && cleanElement.name in v.invalid ) {
                                    v.currentElements.push( cleanElement );
                                    result = v.check( cleanElement ) && result;
                                }
                            }
                        } );
                    }

                    rs = this.check( checkElement ) !== false;
                    result = result && rs;
                    if ( rs ) {
                        this.invalid[ checkElement.name ] = false;
                    } else {
                        this.invalid[ checkElement.name ] = true;
                    }

                    if ( !this.numberOfInvalids() ) {

                        // Hide error containers on last error
                        this.toHide = this.toHide.add( this.containers );
                    }
                    this.showErrors();

                    // Add aria-invalid status for screen readers
                    $( element ).attr( "aria-invalid", !rs );
                }

                return result;
            },

            // http://jqueryvalidation.org/Validator.showErrors/
            showErrors: function( errors ) {
                if ( errors ) {
                    var validator = this;

                    // Add items to error list and map
                    $.extend( this.errorMap, errors );
                    this.errorList = $.map( this.errorMap, function( message, name ) {
                        return {
                            message: message,
                            element: validator.findByName( name )[ 0 ]
                        };
                    } );

                    // Remove items from success list
                    this.successList = $.grep( this.successList, function( element ) {
                        return !( element.name in errors );
                    } );
                }
                if ( this.settings.showErrors ) {
                    this.settings.showErrors.call( this, this.errorMap, this.errorList );
                } else {
                    this.defaultShowErrors();
                }
            },

            // http://jqueryvalidation.org/Validator.resetForm/
            resetForm: function() {
                if ( $.fn.resetForm ) {
                    $( this.currentForm ).resetForm();
                }
                this.invalid = {};
                this.submitted = {};
                this.prepareForm();
                this.hideErrors();
                var elements = this.elements()
                    .removeData( "previousValue" )
                    .removeAttr( "aria-invalid" );

                this.resetElements( elements );
            },

            resetElements: function( elements ) {
                var i;

                if ( this.settings.unhighlight ) {
                    for ( i = 0; elements[ i ]; i++ ) {
                        this.settings.unhighlight.call( this, elements[ i ],
                            this.settings.errorClass, "" );
                        this.findByName( elements[ i ].name ).removeClass( this.settings.validClass );
                    }
                } else {
                    elements
                        .removeClass( this.settings.errorClass )
                        .removeClass( this.settings.validClass );
                }
            },

            numberOfInvalids: function() {
                return this.objectLength( this.invalid );
            },

            objectLength: function( obj ) {
                /* jshint unused: false */
                var count = 0,
                    i;
                for ( i in obj ) {
                    if ( obj[ i ] ) {
                        count++;
                    }
                }
                return count;
            },

            hideErrors: function() {
                this.hideThese( this.toHide );
            },

            hideThese: function( errors ) {
                errors.not( this.containers ).text( "" );
                this.addWrapper( errors ).hide();
            },

            valid: function() {
                return this.size() === 0;
            },

            size: function() {
                return this.errorList.length;
            },

            focusInvalid: function() {
                if ( this.settings.focusInvalid ) {
                    try {
                        $( this.findLastActive() || this.errorList.length && this.errorList[ 0 ].element || [] )
                            .filter( ":visible" )
                            .focus()

                            // Manually trigger focusin event; without it, focusin handler isn't called, findLastActive won't have anything to find
                            .trigger( "focusin" );
                    } catch ( e ) {

                        // Ignore IE throwing errors when focusing hidden elements
                    }
                }
            },

            findLastActive: function() {
                var lastActive = this.lastActive;
                return lastActive && $.grep( this.errorList, function( n ) {
                    return n.element.name === lastActive.name;
                } ).length === 1 && lastActive;
            },

            elements: function() {
                var validator = this,
                    rulesCache = {};

                // Select all valid inputs inside the form (no submit or reset buttons)
                return $( this.currentForm )
                    .find( "input, select, textarea, [contenteditable]" )
                    .not( ":submit, :reset, :image, :disabled" )
                    .not( this.settings.ignore )
                    .filter( function() {
                        var name = this.name || $( this ).attr( "name" ); // For contenteditable
                        if ( !name && validator.settings.debug && window.console ) {
                            console.error( "%o has no name assigned", this );
                        }

                        // Set form expando on contenteditable
                        if ( this.hasAttribute( "contenteditable" ) ) {
                            this.form = $( this ).closest( "form" )[ 0 ];
                        }

                        // Select only the first element for each name, and only those with rules specified
                        if ( name in rulesCache || !validator.objectLength( $( this ).rules() ) ) {
                            return false;
                        }

                        rulesCache[ name ] = true;
                        return true;
                    } );
            },

            clean: function( selector ) {
                return $( selector )[ 0 ];
            },

            errors: function() {
                var errorClass = this.settings.errorClass.split( " " ).join( "." );
                return $( this.settings.errorElement + "." + errorClass, this.errorContext );
            },

            resetInternals: function() {
                this.successList = [];
                this.errorList = [];
                this.errorMap = {};
                this.toShow = $( [] );
                this.toHide = $( [] );
            },

            reset: function() {
                this.resetInternals();
                this.currentElements = $( [] );
            },

            prepareForm: function() {
                this.reset();
                this.toHide = this.errors().add( this.containers );
            },

            prepareElement: function( element ) {
                this.reset();
                this.toHide = this.errorsFor( element );
            },

            elementValue: function( element ) {
                var $element = $( element ),
                    type = element.type,
                    val, idx;

                if ( type === "radio" || type === "checkbox" ) {
                    return this.findByName( element.name ).filter( ":checked" ).val();
                } else if ( type === "number" && typeof element.validity !== "undefined" ) {
                    return element.validity.badInput ? "NaN" : $element.val();
                }

                if ( element.hasAttribute( "contenteditable" ) ) {
                    val = $element.text();
                } else {
                    val = $element.val();
                }

                if ( type === "file" ) {

                    // Modern browser (chrome & safari)
                    if ( val.substr( 0, 12 ) === "C:\\fakepath\\" ) {
                        return val.substr( 12 );
                    }

                    // Legacy browsers
                    // Unix-based path
                    idx = val.lastIndexOf( "/" );
                    if ( idx >= 0 ) {
                        return val.substr( idx + 1 );
                    }

                    // Windows-based path
                    idx = val.lastIndexOf( "\\" );
                    if ( idx >= 0 ) {
                        return val.substr( idx + 1 );
                    }

                    // Just the file name
                    return val;
                }

                if ( typeof val === "string" ) {
                    return val.replace( /\r/g, "" );
                }
                return val;
            },

            check: function( element ) {
                element = this.validationTargetFor( this.clean( element ) );

                var rules = $( element ).rules(),
                    rulesCount = $.map( rules, function( n, i ) {
                        return i;
                    } ).length,
                    dependencyMismatch = false,
                    val = this.elementValue( element ),
                    result, method, rule;

                // If a normalizer is defined for this element, then
                // call it to retreive the changed value instead
                // of using the real one.
                // Note that `this` in the normalizer is `element`.
                if ( typeof rules.normalizer === "function" ) {
                    val = rules.normalizer.call( element, val );

                    if ( typeof val !== "string" ) {
                        throw new TypeError( "The normalizer should return a string value." );
                    }

                    // Delete the normalizer from rules to avoid treating
                    // it as a pre-defined method.
                    delete rules.normalizer;
                }

                for ( method in rules ) {
                    rule = { method: method, parameters: rules[ method ] };
                    try {
                        result = $.validator.methods[ method ].call( this, val, element, rule.parameters );

                        // If a method indicates that the field is optional and therefore valid,
                        // don't mark it as valid when there are no other rules
                        if ( result === "dependency-mismatch" && rulesCount === 1 ) {
                            dependencyMismatch = true;
                            continue;
                        }
                        dependencyMismatch = false;

                        if ( result === "pending" ) {
                            this.toHide = this.toHide.not( this.errorsFor( element ) );
                            return;
                        }

                        if ( !result ) {
                            this.formatAndAdd( element, rule );
                            return false;
                        }
                    } catch ( e ) {
                        if ( this.settings.debug && window.console ) {
                            console.log( "Exception occurred when checking element " + element.id + ", check the '" + rule.method + "' method.", e );
                        }
                        if ( e instanceof TypeError ) {
                            e.message += ".  Exception occurred when checking element " + element.id + ", check the '" + rule.method + "' method.";
                        }

                        throw e;
                    }
                }
                if ( dependencyMismatch ) {
                    return;
                }
                if ( this.objectLength( rules ) ) {
                    this.successList.push( element );
                }
                return true;
            },

            // Return the custom message for the given element and validation method
            // specified in the element's HTML5 data attribute
            // return the generic message if present and no method specific message is present
            customDataMessage: function( element, method ) {
                return $( element ).data( "msg" + method.charAt( 0 ).toUpperCase() +
                    method.substring( 1 ).toLowerCase() ) || $( element ).data( "msg" );
            },

            // Return the custom message for the given element name and validation method
            customMessage: function( name, method ) {
                var m = this.settings.messages[ name ];
                return m && ( m.constructor === String ? m : m[ method ] );
            },

            // Return the first defined argument, allowing empty strings
            findDefined: function() {
                for ( var i = 0; i < arguments.length; i++ ) {
                    if ( arguments[ i ] !== undefined ) {
                        return arguments[ i ];
                    }
                }
                return undefined;
            },

            // The second parameter 'rule' used to be a string, and extended to an object literal
            // of the following form:
            // rule = {
            //     method: "method name",
            //     parameters: "the given method parameters"
            // }
            //
            // The old behavior still supported, kept to maintain backward compatibility with
            // old code, and will be removed in the next major release.
            defaultMessage: function( element, rule ) {
                if ( typeof rule === "string" ) {
                    rule = { method: rule };
                }

                var message = this.findDefined(
                    this.customMessage( element.name, rule.method ),
                    this.customDataMessage( element, rule.method ),

                    // 'title' is never undefined, so handle empty string as undefined
                    !this.settings.ignoreTitle && element.title || undefined,
                    $.validator.messages[ rule.method ],
                    "<strong>Warning: No message defined for " + element.name + "</strong>"
                    ),
                    theregex = /\$?\{(\d+)\}/g;
                if ( typeof message === "function" ) {
                    message = message.call( this, rule.parameters, element );
                } else if ( theregex.test( message ) ) {
                    message = $.validator.format( message.replace( theregex, "{$1}" ), rule.parameters );
                }

                return message;
            },

            formatAndAdd: function( element, rule ) {
                var message = this.defaultMessage( element, rule );

                this.errorList.push( {
                    message: message,
                    element: element,
                    method: rule.method
                } );

                this.errorMap[ element.name ] = message;
                this.submitted[ element.name ] = message;
            },

            addWrapper: function( toToggle ) {
                if ( this.settings.wrapper ) {
                    toToggle = toToggle.add( toToggle.parent( this.settings.wrapper ) );
                }
                return toToggle;
            },

            defaultShowErrors: function() {
                var i, elements, error;
                for ( i = 0; this.errorList[ i ]; i++ ) {
                    error = this.errorList[ i ];
                    if ( this.settings.highlight ) {
                        this.settings.highlight.call( this, error.element, this.settings.errorClass, this.settings.validClass );
                    }
                    this.showLabel( error.element, error.message );
                }
                if ( this.errorList.length ) {
                    this.toShow = this.toShow.add( this.containers );
                }
                if ( this.settings.success ) {
                    for ( i = 0; this.successList[ i ]; i++ ) {
                        this.showLabel( this.successList[ i ] );
                    }
                }
                if ( this.settings.unhighlight ) {
                    for ( i = 0, elements = this.validElements(); elements[ i ]; i++ ) {
                        this.settings.unhighlight.call( this, elements[ i ], this.settings.errorClass, this.settings.validClass );
                    }
                }
                this.toHide = this.toHide.not( this.toShow );
                this.hideErrors();
                this.addWrapper( this.toShow ).show();
            },

            validElements: function() {
                return this.currentElements.not( this.invalidElements() );
            },

            invalidElements: function() {
                return $( this.errorList ).map( function() {
                    return this.element;
                } );
            },

            showLabel: function( element, message ) {
                var place, group, errorID, v,
                    error = this.errorsFor( element ),
                    elementID = this.idOrName( element ),
                    describedBy = $( element ).attr( "aria-describedby" );

                if ( error.length ) {

                    // Refresh error/success class
                    error.removeClass( this.settings.validClass ).addClass( this.settings.errorClass );

                    // Replace message on existing label
                    error.html( message );
                } else {

                    // Create error element
                    error = $( "<" + this.settings.errorElement + ">" )
                        .attr( "id", elementID + "-error" )
                        .addClass( this.settings.errorClass )
                        .html( message || "" );

                    // Maintain reference to the element to be placed into the DOM
                    place = error;
                    if ( this.settings.wrapper ) {

                        // Make sure the element is visible, even in IE
                        // actually showing the wrapped element is handled elsewhere
                        place = error.hide().show().wrap( "<" + this.settings.wrapper + "/>" ).parent();
                    }
                    if ( this.labelContainer.length ) {
                        this.labelContainer.append( place );
                    } else if ( this.settings.errorPlacement ) {
                        this.settings.errorPlacement.call( this, place, $( element ) );
                    } else {
                        place.insertAfter( element );
                    }

                    // Link error back to the element
                    if ( error.is( "label" ) ) {

                        // If the error is a label, then associate using 'for'
                        error.attr( "for", elementID );

                        // If the element is not a child of an associated label, then it's necessary
                        // to explicitly apply aria-describedby
                    } else if ( error.parents( "label[for='" + this.escapeCssMeta( elementID ) + "']" ).length === 0 ) {
                        errorID = error.attr( "id" );

                        // Respect existing non-error aria-describedby
                        if ( !describedBy ) {
                            describedBy = errorID;
                        } else if ( !describedBy.match( new RegExp( "\\b" + this.escapeCssMeta( errorID ) + "\\b" ) ) ) {

                            // Add to end of list if not already present
                            describedBy += " " + errorID;
                        }
                        $( element ).attr( "aria-describedby", describedBy );

                        // If this element is grouped, then assign to all elements in the same group
                        group = this.groups[ element.name ];
                        if ( group ) {
                            v = this;
                            $.each( v.groups, function( name, testgroup ) {
                                if ( testgroup === group ) {
                                    $( "[name='" + v.escapeCssMeta( name ) + "']", v.currentForm )
                                        .attr( "aria-describedby", error.attr( "id" ) );
                                }
                            } );
                        }
                    }
                }
                if ( !message && this.settings.success ) {
                    error.text( "" );
                    if ( typeof this.settings.success === "string" ) {
                        error.addClass( this.settings.success );
                    } else {
                        this.settings.success( error, element );
                    }
                }
                this.toShow = this.toShow.add( error );
            },

            errorsFor: function( element ) {
                var name = this.escapeCssMeta( this.idOrName( element ) ),
                    describer = $( element ).attr( "aria-describedby" ),
                    selector = "label[for='" + name + "'], label[for='" + name + "'] *";

                // 'aria-describedby' should directly reference the error element
                if ( describer ) {
                    selector = selector + ", #" + this.escapeCssMeta( describer )
                        .replace( /\s+/g, ", #" );
                }

                return this
                    .errors()
                    .filter( selector );
            },

            // See https://api.jquery.com/category/selectors/, for CSS
            // meta-characters that should be escaped in order to be used with JQuery
            // as a literal part of a name/id or any selector.
            escapeCssMeta: function( string ) {
                return string.replace( /([\\!"#$%&'()*+,./:;<=>?@\[\]^`{|}~])/g, "\\$1" );
            },

            idOrName: function( element ) {
                return this.groups[ element.name ] || ( this.checkable( element ) ? element.name : element.id || element.name );
            },

            validationTargetFor: function( element ) {

                // If radio/checkbox, validate first element in group instead
                if ( this.checkable( element ) ) {
                    element = this.findByName( element.name );
                }

                // Always apply ignore filter
                return $( element ).not( this.settings.ignore )[ 0 ];
            },

            checkable: function( element ) {
                return ( /radio|checkbox/i ).test( element.type );
            },

            findByName: function( name ) {
                return $( this.currentForm ).find( "[name='" + this.escapeCssMeta( name ) + "']" );
            },

            getLength: function( value, element ) {
                switch ( element.nodeName.toLowerCase() ) {
                    case "select":
                        return $( "option:selected", element ).length;
                    case "input":
                        if ( this.checkable( element ) ) {
                            return this.findByName( element.name ).filter( ":checked" ).length;
                        }
                }
                return value.length;
            },

            depend: function( param, element ) {
                return this.dependTypes[ typeof param ] ? this.dependTypes[ typeof param ]( param, element ) : true;
            },

            dependTypes: {
                "boolean": function( param ) {
                    return param;
                },
                "string": function( param, element ) {
                    return !!$( param, element.form ).length;
                },
                "function": function( param, element ) {
                    return param( element );
                }
            },

            optional: function( element ) {
                var val = this.elementValue( element );
                return !$.validator.methods.required.call( this, val, element ) && "dependency-mismatch";
            },

            startRequest: function( element ) {
                if ( !this.pending[ element.name ] ) {
                    this.pendingRequest++;
                    $( element ).addClass( this.settings.pendingClass );
                    this.pending[ element.name ] = true;
                }
            },

            stopRequest: function( element, valid ) {
                this.pendingRequest--;

                // Sometimes synchronization fails, make sure pendingRequest is never < 0
                if ( this.pendingRequest < 0 ) {
                    this.pendingRequest = 0;
                }
                delete this.pending[ element.name ];
                $( element ).removeClass( this.settings.pendingClass );
                if ( valid && this.pendingRequest === 0 && this.formSubmitted && this.form() ) {
                    $( this.currentForm ).submit();
                    this.formSubmitted = false;
                } else if ( !valid && this.pendingRequest === 0 && this.formSubmitted ) {
                    $( this.currentForm ).triggerHandler( "invalid-form", [ this ] );
                    this.formSubmitted = false;
                }
            },

            previousValue: function( element, method ) {
                method = typeof method === "string" && method || "remote";

                return $.data( element, "previousValue" ) || $.data( element, "previousValue", {
                    old: null,
                    valid: true,
                    message: this.defaultMessage( element, { method: method } )
                } );
            },

            // Cleans up all forms and elements, removes validator-specific events
            destroy: function() {
                this.resetForm();

                $( this.currentForm )
                    .off( ".validate" )
                    .removeData( "validator" )
                    .find( ".validate-equalTo-blur" )
                    .off( ".validate-equalTo" )
                    .removeClass( "validate-equalTo-blur" );
            }

        },

        classRuleSettings: {
            required: { required: true },
            email: { email: true },
            url: { url: true },
            date: { date: true },
            dateISO: { dateISO: true },
            number: { number: true },
            digits: { digits: true },
            creditcard: { creditcard: true }
        },

        addClassRules: function( className, rules ) {
            if ( className.constructor === String ) {
                this.classRuleSettings[ className ] = rules;
            } else {
                $.extend( this.classRuleSettings, className );
            }
        },

        classRules: function( element ) {
            var rules = {},
                classes = $( element ).attr( "class" );

            if ( classes ) {
                $.each( classes.split( " " ), function() {
                    if ( this in $.validator.classRuleSettings ) {
                        $.extend( rules, $.validator.classRuleSettings[ this ] );
                    }
                } );
            }
            return rules;
        },

        normalizeAttributeRule: function( rules, type, method, value ) {

            // Convert the value to a number for number inputs, and for text for backwards compability
            // allows type="date" and others to be compared as strings
            if ( /min|max|step/.test( method ) && ( type === null || /number|range|text/.test( type ) ) ) {
                value = Number( value );

                // Support Opera Mini, which returns NaN for undefined minlength
                if ( isNaN( value ) ) {
                    value = undefined;
                }
            }

            if ( value || value === 0 ) {
                rules[ method ] = value;
            } else if ( type === method && type !== "range" ) {

                // Exception: the jquery validate 'range' method
                // does not test for the html5 'range' type
                rules[ method ] = true;
            }
        },

        attributeRules: function( element ) {
            var rules = {},
                $element = $( element ),
                type = element.getAttribute( "type" ),
                method, value;

            for ( method in $.validator.methods ) {

                // Support for <input required> in both html5 and older browsers
                if ( method === "required" ) {
                    value = element.getAttribute( method );

                    // Some browsers return an empty string for the required attribute
                    // and non-HTML5 browsers might have required="" markup
                    if ( value === "" ) {
                        value = true;
                    }

                    // Force non-HTML5 browsers to return bool
                    value = !!value;
                } else {
                    value = $element.attr( method );
                }

                this.normalizeAttributeRule( rules, type, method, value );
            }

            // 'maxlength' may be returned as -1, 2147483647 ( IE ) and 524288 ( safari ) for text inputs
            if ( rules.maxlength && /-1|2147483647|524288/.test( rules.maxlength ) ) {
                delete rules.maxlength;
            }

            return rules;
        },

        dataRules: function( element ) {
            var rules = {},
                $element = $( element ),
                type = element.getAttribute( "type" ),
                method, value;

            for ( method in $.validator.methods ) {
                value = $element.data( "rule" + method.charAt( 0 ).toUpperCase() + method.substring( 1 ).toLowerCase() );
                this.normalizeAttributeRule( rules, type, method, value );
            }
            return rules;
        },

        staticRules: function( element ) {
            var rules = {},
                validator = $.data( element.form, "validator" );

            if ( validator.settings.rules ) {
                rules = $.validator.normalizeRule( validator.settings.rules[ element.name ] ) || {};
            }
            return rules;
        },

        normalizeRules: function( rules, element ) {

            // Handle dependency check
            $.each( rules, function( prop, val ) {

                // Ignore rule when param is explicitly false, eg. required:false
                if ( val === false ) {
                    delete rules[ prop ];
                    return;
                }
                if ( val.param || val.depends ) {
                    var keepRule = true;
                    switch ( typeof val.depends ) {
                        case "string":
                            keepRule = !!$( val.depends, element.form ).length;
                            break;
                        case "function":
                            keepRule = val.depends.call( element, element );
                            break;
                    }
                    if ( keepRule ) {
                        rules[ prop ] = val.param !== undefined ? val.param : true;
                    } else {
                        $.data( element.form, "validator" ).resetElements( $( element ) );
                        delete rules[ prop ];
                    }
                }
            } );

            // Evaluate parameters
            $.each( rules, function( rule, parameter ) {
                rules[ rule ] = $.isFunction( parameter ) && rule !== "normalizer" ? parameter( element ) : parameter;
            } );

            // Clean number parameters
            $.each( [ "minlength", "maxlength" ], function() {
                if ( rules[ this ] ) {
                    rules[ this ] = Number( rules[ this ] );
                }
            } );
            $.each( [ "rangelength", "range" ], function() {
                var parts;
                if ( rules[ this ] ) {
                    if ( $.isArray( rules[ this ] ) ) {
                        rules[ this ] = [ Number( rules[ this ][ 0 ] ), Number( rules[ this ][ 1 ] ) ];
                    } else if ( typeof rules[ this ] === "string" ) {
                        parts = rules[ this ].replace( /[\[\]]/g, "" ).split( /[\s,]+/ );
                        rules[ this ] = [ Number( parts[ 0 ] ), Number( parts[ 1 ] ) ];
                    }
                }
            } );

            if ( $.validator.autoCreateRanges ) {

                // Auto-create ranges
                if ( rules.min != null && rules.max != null ) {
                    rules.range = [ rules.min, rules.max ];
                    delete rules.min;
                    delete rules.max;
                }
                if ( rules.minlength != null && rules.maxlength != null ) {
                    rules.rangelength = [ rules.minlength, rules.maxlength ];
                    delete rules.minlength;
                    delete rules.maxlength;
                }
            }

            return rules;
        },

        // Converts a simple string to a {string: true} rule, e.g., "required" to {required:true}
        normalizeRule: function( data ) {
            if ( typeof data === "string" ) {
                var transformed = {};
                $.each( data.split( /\s/ ), function() {
                    transformed[ this ] = true;
                } );
                data = transformed;
            }
            return data;
        },

        // http://jqueryvalidation.org/jQuery.validator.addMethod/
        addMethod: function( name, method, message ) {
            $.validator.methods[ name ] = method;
            $.validator.messages[ name ] = message !== undefined ? message : $.validator.messages[ name ];
            if ( method.length < 3 ) {
                $.validator.addClassRules( name, $.validator.normalizeRule( name ) );
            }
        },

        // http://jqueryvalidation.org/jQuery.validator.methods/
        methods: {

            // http://jqueryvalidation.org/required-method/
            required: function( value, element, param ) {

                // Check if dependency is met
                if ( !this.depend( param, element ) ) {
                    return "dependency-mismatch";
                }
                if ( element.nodeName.toLowerCase() === "select" ) {

                    // Could be an array for select-multiple or a string, both are fine this way
                    var val = $( element ).val();
                    return val && val.length > 0;
                }
                if ( this.checkable( element ) ) {
                    return this.getLength( value, element ) > 0;
                }
                return value.length > 0;
            },

            // http://jqueryvalidation.org/email-method/
            email: function( value, element ) {

                // From https://html.spec.whatwg.org/multipage/forms.html#valid-e-mail-address
                // Retrieved 2014-01-14
                // If you have a problem with this implementation, report a bug against the above spec
                // Or use custom methods to implement your own email validation
                return this.optional( element ) || /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test( value );
            },

            // http://jqueryvalidation.org/url-method/
            url: function( value, element ) {

                // Copyright (c) 2010-2013 Diego Perini, MIT licensed
                // https://gist.github.com/dperini/729294
                // see also https://mathiasbynens.be/demo/url-regex
                // modified to allow protocol-relative URLs
                return this.optional( element ) || /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$/i.test( value );
            },

            // http://jqueryvalidation.org/date-method/
            date: function( value, element ) {
                return this.optional( element ) || !/Invalid|NaN/.test( new Date( value ).toString() );
            },

            // http://jqueryvalidation.org/dateISO-method/
            dateISO: function( value, element ) {
                return this.optional( element ) || /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/.test( value );
            },

            // http://jqueryvalidation.org/number-method/
            number: function( value, element ) {
                return this.optional( element ) || /^(?:-?\d+|-?\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test( value );
            },

            // http://jqueryvalidation.org/digits-method/
            digits: function( value, element ) {
                return this.optional( element ) || /^\d+$/.test( value );
            },

            // http://jqueryvalidation.org/minlength-method/
            minlength: function( value, element, param ) {
                var length = $.isArray( value ) ? value.length : this.getLength( value, element );
                return this.optional( element ) || length >= param;
            },

            // http://jqueryvalidation.org/maxlength-method/
            maxlength: function( value, element, param ) {
                var length = $.isArray( value ) ? value.length : this.getLength( value, element );
                return this.optional( element ) || length <= param;
            },

            // http://jqueryvalidation.org/rangelength-method/
            rangelength: function( value, element, param ) {
                var length = $.isArray( value ) ? value.length : this.getLength( value, element );
                return this.optional( element ) || ( length >= param[ 0 ] && length <= param[ 1 ] );
            },

            // http://jqueryvalidation.org/min-method/
            min: function( value, element, param ) {
                return this.optional( element ) || value >= param;
            },

            // http://jqueryvalidation.org/max-method/
            max: function( value, element, param ) {
                return this.optional( element ) || value <= param;
            },

            // http://jqueryvalidation.org/range-method/
            range: function( value, element, param ) {
                return this.optional( element ) || ( value >= param[ 0 ] && value <= param[ 1 ] );
            },

            // http://jqueryvalidation.org/step-method/
            step: function( value, element, param ) {
                var type = $( element ).attr( "type" ),
                    errorMessage = "Step attribute on input type " + type + " is not supported.",
                    supportedTypes = [ "text", "number", "range" ],
                    re = new RegExp( "\\b" + type + "\\b" ),
                    notSupported = type && !re.test( supportedTypes.join() ),
                    decimalPlaces = function( num ) {
                        var match = ( "" + num ).match( /(?:\.(\d+))?$/ );
                        if ( !match ) {
                            return 0;
                        }

                        // Number of digits right of decimal point.
                        return match[ 1 ] ? match[ 1 ].length : 0;
                    },
                    toInt = function( num ) {
                        return Math.round( num * Math.pow( 10, decimals ) );
                    },
                    valid = true,
                    decimals;

                // Works only for text, number and range input types
                // TODO find a way to support input types date, datetime, datetime-local, month, time and week
                if ( notSupported ) {
                    throw new Error( errorMessage );
                }

                decimals = decimalPlaces( param );

                // Value can't have too many decimals
                if ( decimalPlaces( value ) > decimals || toInt( value ) % toInt( param ) !== 0 ) {
                    valid = false;
                }

                return this.optional( element ) || valid;
            },

            // http://jqueryvalidation.org/equalTo-method/
            equalTo: function( value, element, param ) {

                // Bind to the blur event of the target in order to revalidate whenever the target field is updated
                var target = $( param );
                if ( this.settings.onfocusout && target.not( ".validate-equalTo-blur" ).length ) {
                    target.addClass( "validate-equalTo-blur" ).on( "blur.validate-equalTo", function() {
                        $( element ).valid();
                    } );
                }
                return value === target.val();
            },

            // http://jqueryvalidation.org/remote-method/
            remote: function( value, element, param, method ) {
                if ( this.optional( element ) ) {
                    return "dependency-mismatch";
                }

                method = typeof method === "string" && method || "remote";

                var previous = this.previousValue( element, method ),
                    validator, data, optionDataString;

                if ( !this.settings.messages[ element.name ] ) {
                    this.settings.messages[ element.name ] = {};
                }
                previous.originalMessage = previous.originalMessage || this.settings.messages[ element.name ][ method ];
                this.settings.messages[ element.name ][ method ] = previous.message;

                param = typeof param === "string" && { url: param } || param;
                optionDataString = $.param( $.extend( { data: value }, param.data ) );
                if ( previous.old === optionDataString ) {
                    return previous.valid;
                }

                previous.old = optionDataString;
                validator = this;
                this.startRequest( element );
                data = {};
                data[ element.name ] = value;
                $.ajax( $.extend( true, {
                    mode: "abort",
                    port: "validate" + element.name,
                    dataType: "json",
                    data: data,
                    context: validator.currentForm,
                    success: function( response ) {
                        var valid = response === true || response === "true",
                            errors, message, submitted;

                        validator.settings.messages[ element.name ][ method ] = previous.originalMessage;
                        if ( valid ) {
                            submitted = validator.formSubmitted;
                            validator.resetInternals();
                            validator.toHide = validator.errorsFor( element );
                            validator.formSubmitted = submitted;
                            validator.successList.push( element );
                            validator.invalid[ element.name ] = false;
                            validator.showErrors();
                        } else {
                            errors = {};
                            message = response || validator.defaultMessage( element, { method: method, parameters: value } );
                            errors[ element.name ] = previous.message = message;
                            validator.invalid[ element.name ] = true;
                            validator.showErrors( errors );
                        }
                        previous.valid = valid;
                        validator.stopRequest( element, valid );
                    }
                }, param ) );
                return "pending";
            }
        }

    } );

// Ajax mode: abort
// usage: $.ajax({ mode: "abort"[, port: "uniqueport"]});
// if mode:"abort" is used, the previous request on that port (port can be undefined) is aborted via XMLHttpRequest.abort()

    var pendingRequests = {},
        ajax;

// Use a prefilter if available (1.5+)
    if ( $.ajaxPrefilter ) {
        $.ajaxPrefilter( function( settings, _, xhr ) {
            var port = settings.port;
            if ( settings.mode === "abort" ) {
                if ( pendingRequests[ port ] ) {
                    pendingRequests[ port ].abort();
                }
                pendingRequests[ port ] = xhr;
            }
        } );
    } else {

        // Proxy ajax
        ajax = $.ajax;
        $.ajax = function( settings ) {
            var mode = ( "mode" in settings ? settings : $.ajaxSettings ).mode,
                port = ( "port" in settings ? settings : $.ajaxSettings ).port;
            if ( mode === "abort" ) {
                if ( pendingRequests[ port ] ) {
                    pendingRequests[ port ].abort();
                }
                pendingRequests[ port ] = ajax.apply( this, arguments );
                return pendingRequests[ port ];
            }
            return ajax.apply( this, arguments );
        };
    }

})(jQuery);;

;(function($, window, document, undefined) {
    'use strict';
    var output = function(msg) {
        window.console && console.log(msg);
    };
    var WanSpinner = function(element, options) {
        var that = this;
        this.defaults = {
            maxValue: 999,
            minValue: -999,
            step: 1,
            start: 1,
            inputWidth: 40,
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
        this.options = $.extend({}, this.defaults, options);
        this.options.stepLength = ((+this.options.step).toString().split('.')[1] || '').length;
        this.options.stepFloat = parseInt(1 * Math.pow(10, this.options.stepLength) / this.options.step) || 1;
        this.element = $(element);

        this.element.each(function(index, dt) {
            var input = $(dt).find('input');
            var initValue = input.val() || that.options.start;
            input.val(initValue);
        });
        this.EXCEPTION = {
            TOO_LARGE: 1,
            NORMAL: 0,
            TOO_SMALL: -1
        };
    };
    WanSpinner.prototype.bind = function() {
        var self = this;
        $(self.element).delegate('.minus', 'click', function() {
            var val;
            var input = self.element.find('input');
            if (self.options.stepFloat > 1) {
                val = (+input.val() || 0) * self.options.stepFloat - self.options.step  * self.options.stepFloat;
                val = Math.round(val) / self.options.stepFloat;
            } else {
                val = (+input.val() || 0) - self.options.step;
            }
            val = val.toFixed(self.options.stepLength);
            if (val < self.options.minValue) {
                typeof(self.options.exceptionFun) === 'function' && self.options.exceptionFun(self.EXCEPTION.TOO_SMALL);
            } else {
                input.val(val);
                typeof(self.options.minusClick) === 'function' && self.options.minusClick($(this).parent(), val);
                typeof(self.options.valueChanged) === 'function' && self.options.valueChanged($(this).parent(), val);
            }
            return false;
        }).delegate('.plus', 'click', function() {
            var val;
            var input = self.element.find('input');
            if (self.options.stepFloat > 1) {
                val = (+input.val() || 0) * self.options.stepFloat + self.options.step * self.options.stepFloat;
                val = Math.round(val) / self.options.stepFloat;
            } else {
                val = (+input.val() || 0) + self.options.step;
            }
            val = val.toFixed(self.options.stepLength);
            if (val > self.options.maxValue) {
                typeof(self.options.exceptionFun) === 'function' && self.options.exceptionFun(self.EXCEPTION.TOO_LARGE);
            } else {
                input.val(val);
                typeof(self.options.plusClick) === 'function' && self.options.plusClick($(this).parent(), val);
                typeof(self.options.valueChanged) === 'function' && self.options.valueChanged($(this).parent(), val);
            }
            return false;
        }).delegate('input', 'change', function() {
            var val = +$(this).val() || 0;
            if (val > self.options.maxValue) {
                val = self.options.maxValue;
                typeof(self.options.exceptionFun) === 'function' && self.options.exceptionFun(self.EXCEPTION.TOO_LARGE);
            } else if (val < self.options.minValue) {
                val = self.options.minValue;
                typeof(self.options.exceptionFun) === 'function' && self.options.exceptionFun(self.EXCEPTION.TOO_SMALL);
            }
            $(this).val(val);
            typeof(self.options.valueChanged) === 'function' && self.options.valueChanged($(this).parent(), val);
        });
    };
    $.fn.WanSpinner = function(options) {
        var wanSpinner = new WanSpinner(this, options);
        wanSpinner.bind();
        return this;
    };
})(jQuery, window, document);
