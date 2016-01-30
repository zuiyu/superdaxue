SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for super_course_timetable
-- ----------------------------
DROP TABLE IF EXISTS `super_course_timetable`;
CREATE TABLE `super_course_timetable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseName` varchar(50) NOT NULL DEFAULT '',
  `courseCode` varchar(50) DEFAULT '',
  `credit` float DEFAULT NULL COMMENT '',
  `type` varchar(50) DEFAULT '' COMMENT '',
  `teacher` varchar(50) DEFAULT '',
  `classId` varchar(50) DEFAULT '',
  `classNum` varchar(50) DEFAULT NULL COMMENT '',
  `user_id` int(11) NOT NULL COMMENT '',
  `address` varchar(50) DEFAULT '',
  `cycle` varchar(50) DEFAULT '',
  `week` varchar(50) DEFAULT '',
  `time` varchar(50) DEFAULT '',
  `singleDouble` varchar(50) DEFAULT '',
  `schoolyear` varchar(50) DEFAULT '',
  `semester` varchar(50) DEFAULT '',
  `school` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for super_course_user
-- ----------------------------
DROP TABLE IF EXISTS `super_course_user`;
CREATE TABLE `super_course_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(20) DEFAULT '',
  `year` varchar(20) DEFAULT '',
  `major` varchar(20) DEFAULT '',
  `college` varchar(20) DEFAULT '',
  `length` varchar(20) DEFAULT '',
  `school` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IndexName` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for super_course_score
-- ----------------------------
DROP TABLE IF EXISTS `super_course_score`;
CREATE TABLE `super_course_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coursesname` varchar(50) NOT NULL DEFAULT '',
  `courseCode` varchar(50) DEFAULT '',
  `credit` float DEFAULT NULL COMMENT '?',
  `score` varchar(50) DEFAULT '',
  `schoolYear` varchar(50) DEFAULT '' COMMENT '?',
  `semester` varchar(50) DEFAULT '' COMMENT '?',
  `type` varchar(50) DEFAULT '',
  `leanType` varchar(50) DEFAULT '',
  `getType` varchar(50) DEFAULT '' COMMENT '?',
  `checkType` varchar(50) DEFAULT '',
  `remark` varchar(50) DEFAULT '',
  `user_id` int(11) NOT NULL,
  `school` varchar(50) DEFAULT '' COMMENT '学校',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for super_school
-- ----------------------------
DROP TABLE IF EXISTS `super_school`;
CREATE TABLE `super_school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `website` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `established` int(11) NOT NULL DEFAULT '0',
  `ignored` tinyint(4) NOT NULL DEFAULT '0',
  `province` varchar(10) DEFAULT NULL,
  `gflag` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=269 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of super_school
-- ----------------------------
INSERT INTO `super_school` VALUES ('1', 'henu', '河南大学', '1', '0', '河南', '1');
INSERT INTO `super_school` VALUES ('2', 'zzu', '郑州大学', '1', '0', '河南', '0');
INSERT INTO `super_school` VALUES ('6', 'njit', '南京工程学院', '1', '0', '江苏', '1');
INSERT INTO `super_school` VALUES ('7', 'hualixy', '广东工业大学华立学院', '1', '0', '广东', '1');
INSERT INTO `super_school` VALUES ('8', 'ypc', '绍兴文理学院元培学院', '1', '0', '江苏', '1');
INSERT INTO `super_school` VALUES ('9', 'xmu', '厦门大学', '1', '0', '福建', '0');
INSERT INTO `super_school` VALUES ('10', 'hhuwtian', '河海大学文天学院', '1', '0', '江苏', '1');
INSERT INTO `super_school` VALUES ('11', 'hainu', '海南大学', '1', '0', '海南', '1');
INSERT INTO `super_school` VALUES ('12', 'guat', '桂林航天工业学院', '1', '0', '广西', '1');
INSERT INTO `super_school` VALUES ('13', 'zyufl', '浙江越秀外国语学院', '1', '0', '浙江', '0');
INSERT INTO `super_school` VALUES ('14', 'hkc', '海口经济学院', '1', '0', '海南', '0');
INSERT INTO `super_school` VALUES ('15', 'gdqy', '广东轻工职业技术学院', '1', '0', '广东', '1');
INSERT INTO `super_school` VALUES ('16', 'ahsztc', '宿州学院', '1', '0', '安徽', '0');
INSERT INTO `super_school` VALUES ('17', 'hneu', '湖南涉外经济学院', '1', '0', '湖南', '1');
INSERT INTO `super_school` VALUES ('18', 'lnit', '辽宁工业大学', '1', '0', '辽宁', '1');
INSERT INTO `super_school` VALUES ('19', 'wxc', '皖西学院', '1', '0', '安徽', '0');
INSERT INTO `super_school` VALUES ('20', 'sdmu', '山东管理学院', '1', '0', '山东', '1');
INSERT INTO `super_school` VALUES ('21', 'sqnc', '商丘师范学院', '1', '0', '河南', '1');
INSERT INTO `super_school` VALUES ('22', 'zjxu', '嘉兴学院', '1', '0', '浙江', '0');
INSERT INTO `super_school` VALUES ('23', 'xiyi', '西安医学院', '1', '0', '陕西', '1');
INSERT INTO `super_school` VALUES ('24', 'hsc', '黄山学院', '1', '0', '安徽', '0');
INSERT INTO `super_school` VALUES ('25', 'xxu', '新乡学院', '1', '0', '河南', '1');
INSERT INTO `super_school` VALUES ('26', 'cqjtu', '重庆交通大学', '1', '0', '重庆', '0');
INSERT INTO `super_school` VALUES ('28', 'ntst', '南通科技职业学院', '1', '0', '江西', '1');
INSERT INTO `super_school` VALUES ('29', 'ruc', '中国人民大学', '1', '0', '北京', '0');
INSERT INTO `super_school` VALUES ('30', 'yzu', '扬州大学', '1', '0', '江苏', '0');
INSERT INTO `super_school` VALUES ('31', 'xznu', '江苏师范大学', '1', '0', '江苏', '0');
INSERT INTO `super_school` VALUES ('32', 'yngsxy', '云南工商学院', '1', '0', '云南', '0');
INSERT INTO `super_school` VALUES ('33', 'ujn', '济南大学', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('34', 'hebeu', '河北工程大学', '1', '0', '河北', '1');
INSERT INTO `super_school` VALUES ('35', 'dfxy', '黑龙江东方学院', '1', '0', '黑龙江', '1');
INSERT INTO `super_school` VALUES ('36', 'hebust', '河北科技大学', '1', '0', '河北', '0');
INSERT INTO `super_school` VALUES ('37', 'zufe', '浙江财经大学', '1', '0', '浙江', '0');
INSERT INTO `super_school` VALUES ('38', 'ldxy', '陇东学院', '1', '0', '甘肃', '1');
INSERT INTO `super_school` VALUES ('39', 'snut', '陕西理工学院', '1', '0', '陕西', '0');
INSERT INTO `super_school` VALUES ('40', 'nyjj', '黑龙江农业经济职业学院', '1', '0', '黑龙江', '1');
INSERT INTO `super_school` VALUES ('41', 'hljp', '黑龙江职业学院', '1', '0', '黑龙江', '1');
INSERT INTO `super_school` VALUES ('43', 'cucn', '中国传媒大学南广学院', '1', '0', '北京', '0');
INSERT INTO `super_school` VALUES ('44', 'nenu', '东北师范大学', '1', '0', '吉林', '0');
INSERT INTO `super_school` VALUES ('46', 'sxetcedu', '陕西电子科技职业学院', '1', '0', '陕西', '0');
INSERT INTO `super_school` VALUES ('47', 'hut', '湖南工业大学', '1', '0', '湖南', '1');
INSERT INTO `super_school` VALUES ('48', 'jlu', '吉林大学', '1', '0', '吉林', '0');
INSERT INTO `super_school` VALUES ('49', 'nckjxy', '南昌航空大学科技学院', '1', '0', '江西', '0');
INSERT INTO `super_school` VALUES ('50', 'xaau', '西安航空学院', '1', '0', '陕西', '0');
INSERT INTO `super_school` VALUES ('51', 'ycxy', '山东英才学院', '1', '0', '山东', '1');
INSERT INTO `super_school` VALUES ('52', 'seu', '东南大学', '1', '0', '福建', '0');
INSERT INTO `super_school` VALUES ('53', 'sdjzu', '山东建筑大学', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('54', 'tjsyxy', '天津石油职业技术学院', '1', '0', '天津', '0');
INSERT INTO `super_school` VALUES ('55', 'npumd', '西北工业大学明德学院', '1', '0', '陕西', '1');
INSERT INTO `super_school` VALUES ('56', 'csu', '中南大学', '1', '0', '湖南', '0');
INSERT INTO `super_school` VALUES ('57', 'hhstu', '黄河科技学院', '1', '0', '河南', '0');
INSERT INTO `super_school` VALUES ('58', 'ysu', '燕山大学', '1', '0', '北京', '0');
INSERT INTO `super_school` VALUES ('59', 'xcu', '许昌学院', '1', '0', '河南', '1');
INSERT INTO `super_school` VALUES ('60', 'jzu', '焦作大学', '1', '0', '河南', '0');
INSERT INTO `super_school` VALUES ('61', 'ahstu', '安徽科技学院', '1', '0', '安徽', '0');
INSERT INTO `super_school` VALUES ('62', 'fspt', '佛山职业技术学院', '1', '0', '广东', '1');
INSERT INTO `super_school` VALUES ('63', 'jmu', '集美大学', '1', '0', '福建', '0');
INSERT INTO `super_school` VALUES ('64', 'aynu', '安阳师范学院', '1', '0', '河南', '0');
INSERT INTO `super_school` VALUES ('65', 'nsmc', '川北医学院', '1', '0', '四川', '0');
INSERT INTO `super_school` VALUES ('66', 'dudan', 'fudan', '0', '1', null, '0');
INSERT INTO `super_school` VALUES ('67', 'lyu', '临沂大学', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('68', 'dzu', '德州学院', '1', '0', '山东', '1');
INSERT INTO `super_school` VALUES ('69', 'dgpt', '东莞职业技术学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('70', 'gdcj', '广东财经大学', '0', '1', '广东', '0');
INSERT INTO `super_school` VALUES ('71', 'hznu', '杭州师范大学钱江学院', '1', '0', '浙江', '1');
INSERT INTO `super_school` VALUES ('72', 'zqkjxy', '广东理工学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('73', 'changqinglzufe', '兰州财经大学长青学院', '1', '0', '甘肃', '1');
INSERT INTO `super_school` VALUES ('74', 'jxgymy', '江西陶瓷工艺美术职业技术学院', '1', '0', '江西', '1');
INSERT INTO `super_school` VALUES ('75', 'cidp', '防灾科技学院', '1', '0', '河北', '0');
INSERT INTO `super_school` VALUES ('76', 'lzcu', '兰州城市学院', '1', '0', '甘肃', '0');
INSERT INTO `super_school` VALUES ('77', 'qqhru', '齐齐哈尔大学', '1', '0', '黑龙江', '0');
INSERT INTO `super_school` VALUES ('78', 'jlai', '吉林动画学院', '1', '0', '吉林', '0');
INSERT INTO `super_school` VALUES ('79', 'sxdtdx', '山西大同大学', '1', '0', '山西', '1');
INSERT INTO `super_school` VALUES ('80', 'gfxy', '陕西国防工业职业技术学院', '1', '0', '陕西', '0');
INSERT INTO `super_school` VALUES ('81', 'hnzj', '河南职业技术学院', '1', '0', '河南', '0');
INSERT INTO `super_school` VALUES ('83', 'sx', '绍兴市', '1', '0', '浙江', '0');
INSERT INTO `super_school` VALUES ('84', 'dxatc', '定西师范高等专科学校', '1', '0', '甘肃', '0');
INSERT INTO `super_school` VALUES ('85', 'gdou', '广东海洋大学', '1', '0', '广东', '1');
INSERT INTO `super_school` VALUES ('86', 'gdpa', '广东石油化工学院', '1', '0', '广东', '1');
INSERT INTO `super_school` VALUES ('87', 'siit', '苏州工业职业技术学院', '1', '0', '江苏', '1');
INSERT INTO `super_school` VALUES ('89', 'sqc', '宿迁学院', '1', '0', '安徽', '0');
INSERT INTO `super_school` VALUES ('90', 'njutcm', '南京中医药大学', '1', '0', '南京', '1');
INSERT INTO `super_school` VALUES ('91', 'fzfu', '福州外语外贸学院', '1', '0', '福建', '1');
INSERT INTO `super_school` VALUES ('92', 'fjjxu', '福建江夏学院', '1', '0', '福建', '0');
INSERT INTO `super_school` VALUES ('93', 'njci', '南京交通职业技术学院', '1', '0', '江苏', '0');
INSERT INTO `super_school` VALUES ('94', 'qdu', '青岛大学', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('95', 'lnist', '辽宁科技学院', '1', '0', '辽宁', '0');
INSERT INTO `super_school` VALUES ('96', 'wust', '武汉科技大学', '1', '0', '湖北', '1');
INSERT INTO `super_school` VALUES ('97', 'zut', '中原工学院', '1', '0', '河南', '0');
INSERT INTO `super_school` VALUES ('98', 'hbcf', '河北金融学院', '1', '0', '河北', '0');
INSERT INTO `super_school` VALUES ('99', 'upc', '中国石油大学（华东）', '1', '0', '北京', '0');
INSERT INTO `super_school` VALUES ('100', 'aust', '安徽理工大学', '1', '0', '安徽', '0');
INSERT INTO `super_school` VALUES ('101', 'jcut', '荆楚理工学院', '1', '0', '湖北', '0');
INSERT INTO `super_school` VALUES ('102', 'sptc', '陕西邮电职业技术学院', '1', '0', '陕西', '1');
INSERT INTO `super_school` VALUES ('103', 'lnpu', '辽宁石油化工大学', '1', '0', '辽宁', '0');
INSERT INTO `super_school` VALUES ('104', 'sanquanyixuey', '新乡医学院三全学院', '1', '0', '河南', '1');
INSERT INTO `super_school` VALUES ('105', 'tsnc', '天水师范学院', '1', '0', '甘肃', '1');
INSERT INTO `super_school` VALUES ('106', 'jxgzy', '江西工业职业技术学院', '1', '0', '江西', '1');
INSERT INTO `super_school` VALUES ('107', 'jining', '济宁大学', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('109', 'whit', '芜湖职业技术学院', '1', '0', '安徽', '1');
INSERT INTO `super_school` VALUES ('110', 'szitu', '苏州信息职业技术学院', '1', '0', '江苏', '1');
INSERT INTO `super_school` VALUES ('111', 'jssvc', '苏州职业大学', '1', '0', '江苏', '0');
INSERT INTO `super_school` VALUES ('112', 'wzu', '温州大学', '1', '0', '浙江', '0');
INSERT INTO `super_school` VALUES ('113', 'kmmc', '昆明医科大学', '1', '0', '云南', '1');
INSERT INTO `super_school` VALUES ('114', 'yykj', '商丘学院应用科技学院', '1', '0', '河南', '1');
INSERT INTO `super_school` VALUES ('115', 'sdust', '山东科技大学', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('116', 'ouc', '中国海洋大学', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('117', 'ncwu', '华北水利水电大学', '1', '0', '河南', '0');
INSERT INTO `super_school` VALUES ('118', 'hlu', '私立华联学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('119', 'hubu', '湖北大学', '1', '0', '湖北', '0');
INSERT INTO `super_school` VALUES ('120', 'scu', '四川大学', '1', '0', '四川', '0');
INSERT INTO `super_school` VALUES ('121', 'xtu', '湘潭大学', '1', '0', '湖南', '0');
INSERT INTO `super_school` VALUES ('122', 'hbu', '河北大学', '1', '0', '河北', '0');
INSERT INTO `super_school` VALUES ('123', 'hrbcu', '哈尔滨商业大学', '1', '0', '黑龙江', '0');
INSERT INTO `super_school` VALUES ('124', 'hfuu', '合肥学院', '1', '0', '安徽', '0');
INSERT INTO `super_school` VALUES ('125', 'hlju', '黑龙江大学', '1', '0', '黑龙江', '0');
INSERT INTO `super_school` VALUES ('128', 'ujs', '江苏大学', '1', '0', '江苏', '0');
INSERT INTO `super_school` VALUES ('129', 'ccughc', '长春光华学院', '1', '0', '吉林', '0');
INSERT INTO `super_school` VALUES ('130', 'feedu', '哈尔滨远东理工学院', '1', '0', '黑龙江', '1');
INSERT INTO `super_school` VALUES ('131', 'shupl', '上海政法学院', '1', '0', '上海', '0');
INSERT INTO `super_school` VALUES ('132', 'gy38zx', '贵阳市第三十八中学', '1', '0', '贵州', '0');
INSERT INTO `super_school` VALUES ('133', 'ecnu', '华东师范大学', '1', '0', '上海', '0');
INSERT INTO `super_school` VALUES ('134', 'zju', '浙江大学', '1', '0', '浙江', '0');
INSERT INTO `super_school` VALUES ('135', 'xagdyz', '西安医学高等专科学校', '1', '0', '陕西', '1');
INSERT INTO `super_school` VALUES ('136', 'bigc', '北京印刷学院', '1', '0', '北京', '0');
INSERT INTO `super_school` VALUES ('137', 'imut', '内蒙古工业大学', '1', '0', '内蒙古', '0');
INSERT INTO `super_school` VALUES ('138', 'weihaicollege', '威海职业学院', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('139', 'tdxy', '南京邮电大学通达学院', '1', '0', '江苏', '0');
INSERT INTO `super_school` VALUES ('140', 'fjut', '福建工程学院', '1', '0', '南京', '0');
INSERT INTO `super_school` VALUES ('141', 'snnu', '陕西师范大学', '1', '0', '陕西', '0');
INSERT INTO `super_school` VALUES ('142', 'yrcti', '黄河水院', '1', '0', '河南', '1');
INSERT INTO `super_school` VALUES ('144', 'qgxy', '包头轻工职业技术学院', '1', '0', '内蒙古', '0');
INSERT INTO `super_school` VALUES ('145', 'jlnu', '吉林师范大学', '1', '0', '吉林', '1');
INSERT INTO `super_school` VALUES ('146', 'xmut', '厦门理工', '1', '0', '福建', '0');
INSERT INTO `super_school` VALUES ('147', 'ycu', '银川能源学院', '1', '0', '宁夏', '0');
INSERT INTO `super_school` VALUES ('148', 'hist', '河南科技学院', '1', '0', '河南', '0');
INSERT INTO `super_school` VALUES ('149', 'gengdan', '北京工业大学耿丹学院', '1', '0', '北京', '0');
INSERT INTO `super_school` VALUES ('150', 'cjc', '东华理工大学长江学院', '1', '0', '江西', '0');
INSERT INTO `super_school` VALUES ('151', 'xdkj', '太原理工大学现代科技学院', '1', '0', '山西', '1');
INSERT INTO `super_school` VALUES ('152', 'sut', '沈阳工业大学', '1', '0', '辽宁', '0');
INSERT INTO `super_school` VALUES ('153', 'jyu', '嘉应学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('154', 'sict', '山东商业职业技术学院', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('155', 'lynu', '洛阳师范学院', '1', '0', '河南', '0');
INSERT INTO `super_school` VALUES ('156', 'hbqgy', '河北青年干部管理学院', '1', '0', '河北', '0');
INSERT INTO `super_school` VALUES ('157', 'zstu', '浙江理工大学', '1', '0', '浙江', '0');
INSERT INTO `super_school` VALUES ('158', 'usc', '南华大学', '1', '0', '湖南', '1');
INSERT INTO `super_school` VALUES ('159', 'njust', '南京理工大学', '1', '0', '江苏', '1');
INSERT INTO `super_school` VALUES ('160', 'jxcfs', '江西外语外贸职业学院', '1', '0', '江西', '1');
INSERT INTO `super_school` VALUES ('161', 'jxstnupi', '江西科技师范大学理工学院', '1', '0', '江西', '1');
INSERT INTO `super_school` VALUES ('162', 'xdxy', '天津现代职业技术学院', '1', '0', '天津', '0');
INSERT INTO `super_school` VALUES ('163', 'fjnu', '福建师范大学', '1', '0', '福建', '1');
INSERT INTO `super_school` VALUES ('164', 'hnust', '湖南科技大学', '1', '0', '湖南', '0');
INSERT INTO `super_school` VALUES ('165', 'llhc', '吕梁学院', '1', '0', '山西', '1');
INSERT INTO `super_school` VALUES ('166', 'zjtongji', '浙江同济科技职业学院', '1', '0', '浙江', '0');
INSERT INTO `super_school` VALUES ('167', 'gdmec', '广东机电', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('170', 'sdaeu', '山东农业工程学院', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('171', 'xzit', '徐州工程学院', '1', '0', '江苏', '1');
INSERT INTO `super_school` VALUES ('172', 'sxit', '山西工程技术学院', '1', '0', '山西', '1');
INSERT INTO `super_school` VALUES ('173', 'mdjnu', '牡丹江师范学院', '1', '0', '黑龙江', '1');
INSERT INTO `super_school` VALUES ('174', 'gdhsc', '广东财经大学华商学院', '1', '0', '广东', '1');
INSERT INTO `super_school` VALUES ('175', 'njith', '南京旅游职业学院', '1', '0', '江苏', '1');
INSERT INTO `super_school` VALUES ('176', 'hncu', '湖南城市学院', '1', '0', '湖南', '1');
INSERT INTO `super_school` VALUES ('177', 'nclg', '南昌理工学院', '1', '0', '江西', '0');
INSERT INTO `super_school` VALUES ('178', 'ddkjy', '重庆市大渡口小学', '1', '0', '重庆', '0');
INSERT INTO `super_school` VALUES ('179', 'usx', '绍兴文理学院', '1', '0', '浙江', '0');
INSERT INTO `super_school` VALUES ('182', 'gdcxxy', '广东创新科技职业学院', '1', '0', '广东', '1');
INSERT INTO `super_school` VALUES ('183', 'wenjingytu', '烟台大学文经学院', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('184', 'sdpei', '山东体育学院', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('185', 'zzti', '中原工学院', '1', '0', '河南', '0');
INSERT INTO `super_school` VALUES ('187', 'hnist', '湖南理工学院', '1', '0', '湖南', '0');
INSERT INTO `super_school` VALUES ('188', 'tyut', '太原理工大学', '1', '0', '山西', '0');
INSERT INTO `super_school` VALUES ('189', 'ynu', '云南大学', '1', '0', '云南', '1');
INSERT INTO `super_school` VALUES ('190', 'wxwsxy', '皖西卫生职业学院', '1', '0', '安徽', '0');
INSERT INTO `super_school` VALUES ('191', 'gyvtc', '贵阳职业技术学院', '1', '0', '贵州', '0');
INSERT INTO `super_school` VALUES ('192', 'gcp', '广州城市职业学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('193', 'sanxiau', '重庆三峡学院', '1', '0', '重庆', '1');
INSERT INTO `super_school` VALUES ('194', 'qdc', '青岛理工大学琴岛学院', '1', '0', '山东', '1');
INSERT INTO `super_school` VALUES ('195', 'gzpyp', '广州番禺职业技术学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('196', 'tjtdxy', '天津铁道职业技术学院', '1', '0', '天津', '0');
INSERT INTO `super_school` VALUES ('197', 'huhst', '湖南人文科技学院', '1', '0', '湖南', '1');
INSERT INTO `super_school` VALUES ('198', 'sxnu', '山西师范大学', '1', '0', '山西', '0');
INSERT INTO `super_school` VALUES ('199', 'lngsyj', '广东省岭南工商第一技师学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('200', 'gzmodern', '广州现代信息工程职业技术学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('201', 'cauc', '中国民航大学', '1', '0', '天津', '0');
INSERT INTO `super_school` VALUES ('202', 'cdut', '成都理工大学', '1', '0', '四川', '1');
INSERT INTO `super_school` VALUES ('203', 'hnuu', '淮南联合大学', '1', '0', '安徽', '0');
INSERT INTO `super_school` VALUES ('204', 'gzdhxy', '广州东华职业学院', '1', '0', '广东', '1');
INSERT INTO `super_school` VALUES ('205', 'jit', '金陵科技学院', '1', '0', '江苏', '1');
INSERT INTO `super_school` VALUES ('206', 'gdkm', '广东科贸职业学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('207', 'gdgm', '广东工贸职业技术学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('208', 'gsut', '兰州理工大学', '1', '0', '甘肃', '0');
INSERT INTO `super_school` VALUES ('209', 'ycit', '盐城工学院', '1', '0', '江苏', '1');
INSERT INTO `super_school` VALUES ('210', 'gzhmu', '广州医科大学', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('211', 'njtech', '南京工业大学', '1', '0', '江苏', '0');
INSERT INTO `super_school` VALUES ('212', 'gdufe', '广东财经大学', '1', '0', '广东', '1');
INSERT INTO `super_school` VALUES ('213', 'cust', '长春理工大学', '1', '0', '吉林', '0');
INSERT INTO `super_school` VALUES ('215', 'hebiace', '河北建筑工程学院', '1', '0', '河北', '0');
INSERT INTO `super_school` VALUES ('216', 'yctc', '盐城师范学院', '1', '0', '江苏', '0');
INSERT INTO `super_school` VALUES ('217', 'jlbtc', '吉林工商学院', '1', '0', '吉林', '0');
INSERT INTO `super_school` VALUES ('218', 'gwng', '广东外语外贸大学南国商学院', '1', '0', '广东', '1');
INSERT INTO `super_school` VALUES ('219', 'gduf', '广东金融学院', '1', '0', '广东', '1');
INSERT INTO `super_school` VALUES ('220', 'wechatmc', '微信医学院', '1', '0', '云南', '0');
INSERT INTO `super_school` VALUES ('221', 'tyust', '太原科技大学', '1', '0', '山西', '0');
INSERT INTO `super_school` VALUES ('222', 'ycxy2', '山东英才学院', '1', '0', '山东', '1');
INSERT INTO `super_school` VALUES ('223', 'ahpu', '安徽工程大学', '1', '0', '安徽', '0');
INSERT INTO `super_school` VALUES ('224', 'lygtc', '连云港职业技术学院', '1', '0', '江苏', '0');
INSERT INTO `super_school` VALUES ('225', 'tju', '天津大学', '1', '0', '天津', '0');
INSERT INTO `super_school` VALUES ('227', 'ccdxs', '长春大学生帮', '1', '0', '吉林', '0');
INSERT INTO `super_school` VALUES ('229', 'hifa', '湖北美术学院', '1', '0', '湖北', '0');
INSERT INTO `super_school` VALUES ('230', 'henannu', '河南师范大学', '1', '0', '河南', '0');
INSERT INTO `super_school` VALUES ('231', 'tit', '太原工业学院', '1', '0', '山西', '0');
INSERT INTO `super_school` VALUES ('232', 'pusc', '中国石油大学胜利学院', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('233', 'hbei', '湖北工程职业学院', '1', '0', '湖北', '0');
INSERT INTO `super_school` VALUES ('234', 'ahyz', '安徽医学高等专科学校', '1', '0', '安徽', '0');
INSERT INTO `super_school` VALUES ('235', 'sziitxy', '深圳信息职业技术学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('236', 'luas', '兰州文理学院', '1', '0', '甘肃', '0');
INSERT INTO `super_school` VALUES ('237', 'jxcsedu', '江西应用科技学院', '1', '0', '江西', '0');
INSERT INTO `super_school` VALUES ('238', 'nanya', '长沙市南雅中学', '1', '0', '湖南', '0');
INSERT INTO `super_school` VALUES ('239', 'honder', '内蒙古师范大学鸿德学院', '1', '0', '内蒙古', '0');
INSERT INTO `super_school` VALUES ('240', 'nwsuaf', '西北农林科技大学', '1', '0', '陕西', '0');
INSERT INTO `super_school` VALUES ('241', 'imnu', '内蒙古师范大学', '1', '0', '内蒙古', '0');
INSERT INTO `super_school` VALUES ('242', 'tellhowedu', '江西泰豪动漫职业学院', '1', '0', '江西', '0');
INSERT INTO `super_school` VALUES ('243', 'hzu', '惠州学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('246', 'lyun', '龙岩学院', '1', '0', '福建', '0');
INSERT INTO `super_school` VALUES ('247', 'hnie', '湖南工程学院', '1', '0', '湖南', '0');
INSERT INTO `super_school` VALUES ('248', 'hnsyu', '邵阳学院', '1', '0', '湖南', '0');
INSERT INTO `super_school` VALUES ('249', 'shxy', '绥化学院', '1', '0', '黑龙江', '0');
INSERT INTO `super_school` VALUES ('250', 'bupt', '北京邮电大学', '1', '0', '北京', '0');
INSERT INTO `super_school` VALUES ('251', 'gcu', '华南理工大学广州学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('252', 'cdpc', '承德石油高等专科学校', '1', '0', '河北', '0');
INSERT INTO `super_school` VALUES ('253', 'njrts', '南京铁道职业技术学院', '1', '0', '江苏', '0');
INSERT INTO `super_school` VALUES ('254', 'luibe', '辽宁对外经贸学院', '1', '0', '辽宁', '0');
INSERT INTO `super_school` VALUES ('255', 'sdufe', '山东财经大学', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('256', 'jstu', '江苏理工学院', '1', '0', '江苏', '0');
INSERT INTO `super_school` VALUES ('257', 'ncbuct', '燕京理工学院', '1', '0', '河北', '0');
INSERT INTO `super_school` VALUES ('259', 'fjcc', '福建商业高等专科学校', '1', '0', '福建', '0');
INSERT INTO `super_school` VALUES ('260', 'qfnu', '曲阜师范大学', '1', '0', '山东', '0');
INSERT INTO `super_school` VALUES ('261', 'lzlqc', '兰州商学院陇桥学院', '1', '0', '甘肃', '0');
INSERT INTO `super_school` VALUES ('262', 'ccnu', '华中师范大学', '1', '0', '湖北', '0');
INSERT INTO `super_school` VALUES ('263', 'gdin', '广东技术师范学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('264', 'lise', '辽宁理工学院', '1', '0', '辽宁', '0');
INSERT INTO `super_school` VALUES ('265', 'jnu', '暨南大学', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('266', 'huel', '河南财经政法大学', '1', '0', '河南', '0');
INSERT INTO `super_school` VALUES ('267', 'lnc', '广东岭南职业技术学院', '1', '0', '广东', '0');
INSERT INTO `super_school` VALUES ('268', 'lingnan', '岭南师范学院', '1', '0', '广东', '0');
