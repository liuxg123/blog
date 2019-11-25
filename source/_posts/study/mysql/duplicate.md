---
title: mysql 学习总结
date: 2019-11-25 10:38:26
tags: mysql
---
进行数据批量插入时，根据表中唯一性约束判断某些记录在数据表中已经存在时，这时继续进行insert操作则会报错，执行中断。
mysql提供了此种情况的解决办法，但是在使用中，需要注意一些细节，以下举例
{% codeblock %}
INSERT INTO tableName VALUES(),(),...() ON DUPLICATE KEY UPDATE 
column1=VALUES(column1),...column(n)=VALUES(column(1))
{% endcodeblock %}

具体需求：一张测试表test，字段status记录了该条记录的状态，字段last_status记录该条记录的上一次状态，当记录更新时，last_status更新为原记录的status字段值

## 1.新建测试表并初始化测试数据
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
INSERT INTO test(`name`,`status`,`last_status`,`create_time`,`create_user`,`update_time`,`update_user`) 
VALUES ("name1", 1, null, NOW(), "admin", NOW(), "admin")
INSERT INTO test(`name`,`status`,`last_status`,`create_time`,`create_user`,`update_time`,`update_user`) 
VALUES ("name2", 2, null, NOW(), "admin", NOW(), "admin")
执行结果
1	name1	1		20191125105413	admin	20191125105413	admin
2	name2	2		20191125105423	admin	20191125105423	admin
{% endcodeblock %}
## 2.插入一条记录，如果唯一性约束字段匹配记录存在，则更新该记录
{% codeblock %}
INSERT INTO test(`name`,`status`,`last_status`,`create_time`,`create_user`,`update_time`,`update_user`) 
VALUES ("name2", 5, null, NOW(), "admin", NOW(), "admin") ON DUPLICATE KEY UPDATE
`name`=VALUES(name),last_status=VALUES(status),update_time=now(),update_user="test"
执行结果
1	name1	1		20191125105413	admin	20191125105413	admin
2	name2	2	5	20191125105423	admin	20191125111104	test
{% endcodeblock %}
name字段为“name2”的记录已经存在，此时会执行update语句，由update_user字段可以看出

## 3.使用last_status字段记录修改前记录的状态，执行下列语句
{% codeblock %}
INSERT INTO test(`name`,`status`,`last_status`,`create_time`,`create_user`,`update_time`,`update_user`) 
VALUES ("name2", 6, null, NOW(), "admin", NOW(), "admin") ON DUPLICATE KEY UPDATE
`name`=VALUES(name),status=6, last_status=VALUES(status),update_time=now(),update_user="test"
执行结果
1	name1	1		20191125105413	admin	20191125105413	admin
2	name2	6	6	20191125105423	admin	20191125111151	test
{% endcodeblock %}
当原表中status字段值被引用时，如上述例子，结果不符合要求，因为执行update语句时status字段先被更新，然后status字段值被引用，所以得到上述结果

## 4.修改顺序，继续执行下列语句
{% codeblock %}
INSERT INTO test(`name`,`status`,`last_status`,`create_time`,`create_user`,`update_time`,`update_user`) 
VALUES ("name2", 8, null, NOW(), "admin", NOW(), "admin") ON DUPLICATE KEY UPDATE
`name`=VALUES(name), last_status=VALUES(status), status=8, update_time=now(), update_user="test"
执行结果
1	name1	1		20191125105413	admin	20191125105413	admin
2	name2	8	8	20191125105423	admin	20191125112021	test
{% endcodeblock %}
修改赋值顺序后还是不能获得想要的结果，原因是通过VALUES()取值取的是插入记录数据中的值，去除VALUES()包裹，直接通过数表字段名称取值即可
## 5.修改字段取值，继续执行下列语句
{% codeblock %}
INSERT INTO test(`name`,`status`,`last_status`,`create_time`,`create_user`,`update_time`,`update_user`) 
VALUES ("name2", 10, null, NOW(), "admin", NOW(), "admin") ON DUPLICATE KEY UPDATE
`name`=VALUES(name), last_status=status, status=10, update_time=now(), update_user="test"
执行结果
1	name1	1		20191125105413	admin	20191125105413	admin
2	name2	10	8	20191125105423	admin	20191125113348	test
{% endcodeblock %}
ok，执行成功，符合需求，新的记录中last_status记录了上一条记录的数据状态