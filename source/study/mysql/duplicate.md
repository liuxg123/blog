---
title: mysql 学习总结
date: 2019-11-25 10:38:26
---
mysql 批量插入数据学习总结
进行数据批量插入时，根据表中唯一性约束判断某些记录在数据表中已经存在时，这时继续进行insert操作则会报错，执行中断。
mysql提供了此种情况的解决办法，就是 
{% codeblock %}
INSERT INTO tableName VALUES(),(),...() ON DUPLICATE KEY UPDATE column1=VALUES(column1),...column(n)=VALUES(column(1))
{% endcodeblock %}
但是在使用中，需要注意一些细节，以下举例
1.新建测试表
{% codeblock %}
CREATE TABLE `test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `last_status` tinyint(1) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `udpate_time` bigint(20) DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
{% endcodeblock %}
