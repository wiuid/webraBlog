/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : webra_blog

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 19/12/2020 15:13:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '标题名',
  `cover_map` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '封面图url',
  `synopsis` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文章简介',
  `state` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态  发表  草稿  回收站',
  `rotograms` int(2) NOT NULL DEFAULT 0 COMMENT '是否轮播图显示2 否 1是',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `text` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文章内容',
  `classification_id` int(255) NOT NULL DEFAULT 1 COMMENT '分类id,有个默认的会放置的分类',
  `comments` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '评论数',
  `views` int(11) NOT NULL DEFAULT 0 COMMENT '浏览数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_article_user_id`(`user_id`) USING BTREE,
  INDEX `fk_article_classification_id`(`classification_id`) USING BTREE,
  CONSTRAINT `fk_article_classification_id` FOREIGN KEY (`classification_id`) REFERENCES `classification` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_article_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '本博客的使用', '/static/images/f+9d4e793652778051390823ea5857f068.jpg', 'sdada', '发表', 0, 1, '<h3>作者的话</h3>\n<blockquote>\n<p>首先非常感谢您使用本博客系统，本博客目前是第一版本，还不成熟，请多多包含，关于该博客的使用和bug都可以向制作者询问修改等</p>\n<p>然后本博客主要使用了<span style=\"text-indent: 2em;\">ootstrap3.0、SpringBoot2.0、MyBatis、SpringSecurity</span></p>\n<p>制作者：Webra</p>\n<p>微信：yu5921jing</p>\n<p>qq：1654899391</p>\n<p>博客后台--&gt;系统--&gt;关于有我个人的微信二维码及qq群二维码</p>\n<p>如果向加微信群的话先加下我吧</p>\n</blockquote>\n<h3>初始化</h3>\n<blockquote>\n<p>初次使用，请<strong>务必</strong>首先访问<a href=\"../../../../webra/init\">初始化页面</a>，提交用户名、昵称、密码、邮箱信息，用户名是用来登录的，不可修改，昵称是用来显示的</p>\n<p>提交之后会自动跳转至登录页面，输入刚刚提交的账号密码便可以登录，初始化页面点击提交之后便无法再访问，所以请牢记自己的账号，账号在后台也不会显示！！</p>\n</blockquote>\n<h3>使用</h3>\n<blockquote>\n<p>后台分为五块内容</p>\n<ul>\n<li>仪表板</li>\n<li>文章管理</li>\n<li>页面管理</li>\n<li>其他管理</li>\n<li>系统设置</li>\n</ul>\n<p>首先是<strong>仪表板</strong>，在仪表板页面可以查看发布文章的数量（可跳转至文章管理页面）、评论的总数量（可跳转至评论管理页面）、阅读的总数量、建立天数（建立天数是按照初始化完成开始计算的）</p>\n<p>可查看最近发布的五篇文章、最近评论的五条评论、最近的五条操作日志（点击操作记录标题旁边的三个点可以查看所有的记录（不可搜索））</p>\n<p><strong>文章管理</strong>可以按照关键词、状态、分类进行搜索，基础的对文章增删改查，对分类、标签的创建、更新、删除都在这里，分类和标签可以将自身的链接发送到菜单，就可以在首页前台顶部看到设置的菜单，点进去就可以看到该分类或标签所关联的文章</p>\n<p>发布的文章封面图默认是轮播图第二章，如果您用第三方图库的图片，请确保图片宽高比 5 ：3</p>\n<p><strong>页面管理</strong>就是简化版的文章管理，只有对页面的增删改查，其中归档页面是固定的，不可删除，不可修改，默认归档页是在菜单中存在的，你也可以将归档页从菜单中删除。</p>\n<p><strong>其他管理</strong>有对图片、评论、菜单、首页的管理</p>\n<p><span style=\"text-decoration: underline;\">图片管理</span>就是查看和删除，目前仅支持本地存储，之后更新会把其他云存储加进来</p>\n<p><span style=\"text-decoration: underline;\">评论管理</span>可根据文章、用户查评论，评论不可修改，只能删除</p>\n<p><span style=\"text-decoration: underline;\">菜单管理</span>中默认有归档和About页面，该页面的增删改查和分类、标签、页面没有直接关系，菜单最多可支持二级菜单，有二级菜单的一级菜单不不可点击（有链接页无法跳转）</p>\n<p><span style=\"text-decoration: underline;\">首页管理</span>是对轮播图的增删改查，如果一个轮播图都没有，默认是三张图片，文章添加到轮播图的图片是文章的封面图；再者就是标签云的控制，目前还没写，之后更新</p>\n<p><strong>系统设置</strong>可以对个人信息、博客信息、文章设置进行更新，其他的图片和SMTP暂不可用，之后更新；个人信息页点击头像就可以对头像进行更新，资料和密码就是正常的表单，填写之后提交就可更新，博客信息页的页脚信息，我把代码抠出来了，如果你懂些知识的话可以自定义，如果不懂得话备案号改掉然后添加备案跳转地址，虽然希望你对 Design by&nbsp; WebRA 进行保留，但是如果您特别不喜欢该标志的话，去掉就去掉吧，也无妨。</p>\n</blockquote>\n<pre class=\"language-markup\"><code>&lt;a href=\"#\" class=\"un font-red\"&gt;\n改成下面的\n&lt;a href=\"地址\" class=\"un font-red\"&gt;</code></pre>\n<h3>结束</h3>\n<p>其他的也没啥了，希望你喜欢我的博客，如果可以点个start，愿你的博客红红火火</p>', 1, 0, 0, '2020-12-12 12:12:12', '2020-12-19 14:55:47');

-- ----------------------------
-- Table structure for article_label
-- ----------------------------
DROP TABLE IF EXISTS `article_label`;
CREATE TABLE `article_label`  (
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `label_id` int(11) NOT NULL COMMENT '标签id',
  INDEX `fk_article_label_article_id`(`article_id`) USING BTREE,
  INDEX `fk_article_label_label_id`(`label_id`) USING BTREE,
  CONSTRAINT `fk_article_label_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_article_label_label_id` FOREIGN KEY (`label_id`) REFERENCES `label` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_label
-- ----------------------------

-- ----------------------------
-- Table structure for classification
-- ----------------------------
DROP TABLE IF EXISTS `classification`;
CREATE TABLE `classification`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '分类名',
  `index` int(2) NULL DEFAULT 0 COMMENT '首页菜单0否  1是',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '别称,用于url链接',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classification
-- ----------------------------
INSERT INTO `classification` VALUES (1, 'index', 0, 'index', '前端处理', '2020-11-12 16:54:13', '2020-11-29 16:43:40');
INSERT INTO `classification` VALUES (2, '展示分类一号', 0, 'classify1', NULL, '2020-12-19 13:40:28', '2020-12-19 13:40:28');
INSERT INTO `classification` VALUES (3, '展示分类二号', 0, 'classify2', NULL, '2020-12-19 13:40:44', '2020-12-19 13:40:44');

-- ----------------------------
-- Table structure for cloud_storage
-- ----------------------------
DROP TABLE IF EXISTS `cloud_storage`;
CREATE TABLE `cloud_storage`  (
  `id` int(2) NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '云厂商名称',
  `domain_name_agreement` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '绑定域名协议',
  `domain_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '绑定域名',
  `ak` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'accessKey 私钥',
  `sk` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'secretKey 公钥',
  `bucket` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '空间名称',
  `regional` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '服务器区域',
  `directory` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '虚拟目录',
  `off_on` int(2) NOT NULL DEFAULT 0 COMMENT '0 关闭 1 开启  只有一个是开启的 代表图片将上传到该厂商',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cloud_storage
-- ----------------------------
INSERT INTO `cloud_storage` VALUES (1, '本地', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `cloud_storage` VALUES (2, 'sm.ms', NULL, NULL, NULL, '482184', NULL, NULL, NULL, 0);
INSERT INTO `cloud_storage` VALUES (3, '又拍云', 'Https', '又拍云', NULL, NULL, 'undefined', NULL, NULL, 0);
INSERT INTO `cloud_storage` VALUES (4, '七牛云', 'Https', NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `cloud_storage` VALUES (5, '阿里云', 'Https', NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `cloud_storage` VALUES (6, '腾讯云', 'Https', NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `cloud_storage` VALUES (7, '百度云', 'Https', NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `cloud_storage` VALUES (8, '华为云', 'Https', NULL, NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `reply` int(11) NULL DEFAULT 0 COMMENT '回复谁的评论(评论id,如果是一级评论则是0)',
  `reply_user_nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '回复的评论的人的昵称(如果有的话)',
  `text` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '评论内容',
  `super_id` int(11) NOT NULL DEFAULT 0 COMMENT '本表上级评论id,如果是一级评论,默认0',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_comments_article_id`(`article_id`) USING BTREE,
  INDEX `fk_comments_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_comments__user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_comments_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comments
-- ----------------------------

-- ----------------------------
-- Table structure for file_hash
-- ----------------------------
DROP TABLE IF EXISTS `file_hash`;
CREATE TABLE `file_hash`  (
  `manufacturer_id` int(10) NOT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `file_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  INDEX `fk_file_hash_manufacturer_id_cloud_storage_id`(`manufacturer_id`) USING BTREE,
  CONSTRAINT `fk_file_hash_manufacturer_id_cloud_storage_id` FOREIGN KEY (`manufacturer_id`) REFERENCES `cloud_storage` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_hash
-- ----------------------------
INSERT INTO `file_hash` VALUES (1, 'db2ced531ed7c6ace9325d73977452cc.png', 'db2ced531ed7c6ace9325d73977452cc', '2020-12-18 20:03:29');
INSERT INTO `file_hash` VALUES (1, 'f+9d4e793652778051390823ea5857f068.jpg', '9d4e793652778051390823ea5857f068', '2020-12-19 14:50:15');

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id标识',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '标签名',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of label
-- ----------------------------
INSERT INTO `label` VALUES (2, '展示标签1', '2020-11-12 21:33:21', '2020-12-19 15:08:33');
INSERT INTO `label` VALUES (14, '展示标签2', '2020-11-13 14:07:28', '2020-12-19 15:08:42');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '显示名称,首页显示',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用作url链接的一部分',
  `jump` int(2) NOT NULL DEFAULT 0 COMMENT '是否跳转新页面默认0否    1是',
  `super_id` int(11) NOT NULL DEFAULT 0 COMMENT '上级id,默认0-本身是一级,最多二级',
  `number` int(4) NOT NULL DEFAULT 0 COMMENT '用作排序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '归档', '/page/archive', 1, 0, 50, '2020-12-05 11:51:01', '2020-12-05 11:51:09');
INSERT INTO `menu` VALUES (2, 'About', '/page/about', 1, 0, 99, '2020-12-05 10:54:55', '2020-12-05 15:30:21');

-- ----------------------------
-- Table structure for page
-- ----------------------------
DROP TABLE IF EXISTS `page`;
CREATE TABLE `page`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '页面名称',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '别称,用于链接',
  `text` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '正文内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of page
-- ----------------------------
INSERT INTO `page` VALUES (1, '归档', 'archive', NULL, '2020-12-04 20:19:08', '2020-12-04 20:19:08');
INSERT INTO `page` VALUES (2, 'About', 'about', '<h2 style=\"text-align: center;\">关于本博客</h2>\n<p style=\"text-indent: 2em;\">网站搭建主要使用了Bootstrap3.0、SpringBoot2.0、MyBatis、SpringSecurity</p>\n<p style=\"text-indent: 2em;\">制作者：<a href=\"https://www.webra.top\" target=\"_blank\" rel=\"noopener\">webra</a></p>\n<p style=\"text-indent: 2em;\">如果有什么想询问的，可以随时加制作者进行询问。</p>\n<p style=\"text-indent: 2em;\">这个界面可以在后台删除，也可以稍微改改，用作你的博客的About</p>\n<p style=\"text-indent: 2em;\">关于本博客的一些使用可以看第一篇文章<a href=\"../../../article/1\" target=\"_blank\" rel=\"noopener\">http://localhost:8080/article/1</a></p>\n<p style=\"text-indent: 2em;\">&nbsp;</p>', '2020-12-04 20:19:08', '2020-12-19 13:20:25');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '事件名',
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (56, '标签管理', '更新标签：展示标签1', '2020-12-19 15:08:33');
INSERT INTO `record` VALUES (57, '标签管理', '更新标签：展示标签2', '2020-12-19 15:08:42');
INSERT INTO `record` VALUES (58, '标签管理', '删除标签：redis', '2020-12-19 15:08:47');
INSERT INTO `record` VALUES (59, '标签管理', '删除标签：tinymce', '2020-12-19 15:08:50');

-- ----------------------------
-- Table structure for set
-- ----------------------------
DROP TABLE IF EXISTS `set`;
CREATE TABLE `set`  (
  `id` int(11) NOT NULL,
  `index_article_number` int(11) NULL DEFAULT NULL COMMENT '首页文章数',
  `search_article_number` int(11) NULL DEFAULT NULL COMMENT '搜索文章数',
  `label_article_number` int(11) NULL DEFAULT NULL COMMENT '标签文章数',
  `classify_article_number` int(11) NULL DEFAULT NULL COMMENT '分类文章数',
  `comments_off_on` int(11) NULL DEFAULT NULL COMMENT '全局关闭评论',
  `email_off_on` int(2) NULL DEFAULT NULL COMMENT '邮件开关',
  `smtp_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'smtp地址',
  `smtp_agreement` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '发送协议',
  `smtp_ssl` int(5) NULL DEFAULT NULL COMMENT 'ssl端口',
  `smtp_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱账号',
  `smtp_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `smtp_nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '发件人昵称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of set
-- ----------------------------
INSERT INTO `set` VALUES (1, 10, 10, 10, 10, 0, 0, '0', '0', 52, '0', '0', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户名',
  `tourist` int(2) NOT NULL DEFAULT 0 COMMENT '0:游客,1注册用户',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '昵称',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'url主站地址',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '邮箱',
  `portrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '头像url',
  `explain` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '个人说明',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'webra', 1, '0', 'webra', '', 'li_zhm@qq.com', '/static/images/db2ced531ed7c6ace9325d73977452cc.png', '', '2020-01-01 00:00:00', '2020-12-18 20:06:42');

-- ----------------------------
-- Table structure for website
-- ----------------------------
DROP TABLE IF EXISTS `website`;
CREATE TABLE `website`  (
  `id` int(11) NOT NULL COMMENT 'id标识',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '网站名称',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '网站地址',
  `info` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '页脚信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of website
-- ----------------------------
INSERT INTO `website` VALUES (20210101, 'WebraBlog', 'webra.top', ' Copyright © 2020 WebRA\n                        <a href=\"#\" class=\"un font-red\">\n                            <b>\n                                京ICP备19057153号-1\n                            </b>\n                        </a>\n                        Design by\n                        <a href=\"https://www.webra.top\" class=\"un font-red\">\n                            <b>\n                                WebRA\n                            </b>\n                        </a>');

SET FOREIGN_KEY_CHECKS = 1;
