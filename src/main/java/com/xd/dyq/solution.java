package com.xd.dyq;

public class solution{
    public static  void  main(String [] args){
        String str = "use o2o;\n" +
                "create table `tb_person_info`(\n" +
                "\t`user_id` int(10) NOT NULL AUTO_INCREMENT,\n" +
                "\t`name` varchar(32) DEFAULT NULL,\n" +
                "\t`profile_img` varchar(1024) DEFAULT NULL,\n" +
                "    `email`varchar(1024) default null,\n" +
                "    `gender` varchar(2) default null,\n" +
                "    'enable_status' int(2) NOT NULL DEFAULT '0' COMMENT '0:禁止使用,1:允许使用',\n" +
                "    'user_type' int(2) not null default '1' comment '1:顾客,2:店家,3:超级管理员'\n" +
                "    'creat_time' datetime default null,\n" +
                "    'last_edit_time' datetime default null,\n" +
                "    primary key('user_id')\n" +
                ")ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;";
        System.out.println(str.replace('\'','`'));
    }
}
