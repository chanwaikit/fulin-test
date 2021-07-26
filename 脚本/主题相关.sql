

drop table if exists skin_page;

drop table if exists skin_page_block;

drop table if exists skin_page_block_nav;

drop table if exists skin_page_data;

drop table if exists skin_page_data_snapshot;

drop table if exists skin_page_layout;


/*==============================================================*/
/* Table: skin_page                                             */
/*==============================================================*/
create table skin_page
(
  id                   varchar(20) not null comment '主键',
  template_id          varchar(20) comment '归属模版主键',
  title                varchar(200) comment '页面标题',
  url                  varchar(200) comment '页面访问地址',
  add_time             bigint(11) comment '创建时间',
  primary key (id)
);

alter table skin_page comment '主题-页面';

/*==============================================================*/
/* Table: skin_page_block                                       */
/*==============================================================*/
create table skin_page_block
(
  id                   varchar(20) not null comment '主键',
  pid                  varchar(20) comment 'pid',
  template_id          varchar(20) comment '归属模版主键 子级块可为null',
  title                varchar(100) comment '标题',
  layout_id            varchar(100) comment '布局',
  view                 varchar(50) comment '显示页面 正常 page 主键,全局显示可特殊定义名称 子级块可为null',
  cover_url            varchar(200) comment '缩略图',
  sort                 int(4) comment '排序',
  is_enable            tinyint(1) comment '是否启用 0 否 1是',
  remarks              varchar(500) comment '备注',
  add_time             bigint(11) comment '创建时间',
  primary key (id)
);

alter table skin_page_block comment '主题-页面块';

/*==============================================================*/
/* Table: skin_page_block_nav                                   */
/*==============================================================*/
create table skin_page_block_nav
(
   id                   varchar(20) not null comment '主键',
   block_id             varchar(20) comment '块主键',
   pid                  varchar(20) comment 'pid',
   title                varchar(100) comment '标题',
   url                  varchar(300) comment '跳转地址',
   target               varchar(15) comment '跳转目标 _blank _self',
   icon                 varchar(200) comment '图标',
   type                 varchar(20) comment '类型 超连接 link 分类 category  其他自定义',
   inline_data          text comment '内联数据 type 非 link 类型 该数据必填, json格式',
   sort                 int(4) comment '排序',
   add_time             bigint(11) comment '创建时间',
   primary key (id)
);

alter table skin_page_block_nav comment '主题-页面导航';

/*==============================================================*/
/* Table: skin_page_data                                        */
/*==============================================================*/
create table skin_page_data
(
   id                   varchar(20) not null comment '主键 ',
   block_id             varchar(20) comment '块主键',
   data_type            varchar(50) comment '数据类型 collections 产品专题',
   data_source          varchar(20) comment '数据来源 手动 manual  自动 automatic',
   sorting_rules        varchar(40) comment '排序规则',
   data_options         text comment '检索条件 数据来源为 automatic 自动 该值必填',
   max_limit            int(4) comment '最大显示条目 0 表示不限制',
   primary key (id)
);
alter table skin_page_data comment '主题-商品页面';

/*==============================================================*/
/* Table: skin_page_data_snapshot                               */
/*==============================================================*/
create table skin_page_data_snapshot
(
  id                   varchar(20) not null comment '主键',
  page_id              varchar(20) comment '页面主键',
  data_id              varchar(20) comment '数据主键',
  primary key (id)
);

alter table skin_page_data_snapshot comment '主题-页面数据快照表   页面数据手动选择,直接存储关联即可,如果是自动选择需要在持久化的时候判断是否需要加入快照涉及到';

/*==============================================================*/
/* Table: skin_page_layout                                      */
/*==============================================================*/
create table skin_page_layout
(
  id                   varchar(20) not null comment '主键',
  template_id          varchar(20) comment '归属模版主键 子级块可为null',
  name                 varchar(100) comment '布局名称',
  template_path        varchar(200) comment '模版路径',
  max_limit            int(4) comment '最大显示条目 0 表示不限制',
  primary key (id)
);

alter table skin_page_layout comment '主题-页面布局';

