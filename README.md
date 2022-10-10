# cloud
Netdisk Project
# * 功能介绍

1. cloud是一个网盘项目，致力于打造安全、方便、快捷的云存储服务。可以将项目部署到远程服务器或局域网，支持多文件上传下载、文件查找、回收等功能。
2. 具体功能：
   - 后台管理
     - 用户管理
     - 角色管理
     - 权限管理
   - 前台功能
     - 文件/文件夹：创建、删除、移动
     - 多文件上传、下载
     - 回收站：文件恢复、永久删除

# 一、数据表

### 1、t_user

1. 说明：t_user是用户信息表

2. 字段

   | 字段        | 数据类型 | 长度 | 说明         |
   | ----------- | -------- | ---- | ------------ |
   | id          | int      | 11   | 自增id，主键 |
   | account     | varchar  | 255  | 账号         |
   | password    | char     | 100  | 密码         |
   | user_name   | varchar  | 255  | 用户名       |
   | email       | varchar  | 50   | 邮箱         |
   | create_time | char     | 19   | 创建时间     |

3. 建表语句

   ```sql
   CREATE TABLE `t_user` (
       `id` int(11) NOT NULL AUTO_INCREMENT,
       `account` varchar(255) NOT NULL,
       `password` char(100) NOT NULL,
       `user_name` varchar(255) NOT NULL,
       `email` varchar(50) NOT NULL,
       `create_time` char(19) DEFAULT NULL,
       PRIMARY KEY (`id`),
       UNIQUE KEY `unique_account` (`account`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
   ```

4. 超级管理员数据

   ```sql
   insert  into `t_user`(`id`,`account`,`password`,`user_name`,`email`,`create_time`) values (1,'superAdmin','$2a$10$taYQ32s0MVIB3irbpuBakejcW05CbtnQXM36zw.mf3lXC5DPSUzxi','superAdmin','superAdministrator@qq.com','2022-10-10 14:58:59'),(2,'admin','$2a$10$ZJMVntR6PLgaSPVw7j.UN.Bg76XU8Me089a6Nat07eIDmLItMdbFO','administrators','administrators@qq.com','2022-10-10 14:47:04'),(3,'ordinaryUsers','$2a$10$cbf3QYB6HI1uu9Y2IOaH6exqV9siVHGsKJ.fpvC38//xIrLggunRu','ordinaryUsers','ordinaryUsers@qq.com','2022-10-10 15:00:24');
   // 超级管理员、管理员、普通用户初始密码是都是789789，根据需求自行更改及添加用户，删除用户
   ```

### 2、t_role

1. 说明：t_role是角色信息表

2. 字段

   | 字段        | 数据类型 | 长度 | 说明         |
   | ----------- | -------- | ---- | ------------ |
   | id          | int      | 11   | 自增id，主键 |
   | name        | char     | 100  | 角色名称     |
   | create_time | char     | 19   | 创建时间     |

3. 建表语句

   ```sql
   CREATE TABLE `t_role` (
       `id` int(11) NOT NULL AUTO_INCREMENT,
       `name` char(100) NOT NULL,
       `create_time` char(19) NOT NULL,
       PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
   ```

4. 角色数据

   ```sql
   insert  into `t_role`(`id`,`name`,`create_time`) values (1,'超级管理员','2022-09-16 21:11:18'),(2,'管理员','2022-09-16 21:11:24'),(3,'超级用户','2022-09-16 21:11:40'),(4,'普通用户','2022-09-16 21:11:46');
   ```

### 3、t_authority

1. 说明：t_authority是权限信息表

2. 字段

   | 字段        | 数据类型 | 长度 | 说明                 |
   | ----------- | -------- | ---- | -------------------- |
   | id          | int      | 11   | 自增id，主键         |
   | name        | varchar  | 200  | 权限名称             |
   | title       | varchar  | 200  | 权限标题（权限说明） |
   | create_time | char     | 19   | 创建时间             |

3. 建表语句

   ```sql
   CREATE TABLE `t_authority` (
       `id` int(11) NOT NULL AUTO_INCREMENT,
       `name` varchar(200) NOT NULL,
       `title` varchar(200) NOT NULL,
       `create_time` char(19) NOT NULL,
       PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
   ```

4. 权限数据

   ```sql
   insert  into `t_authority`(`id`,`name`,`title`,`create_time`) values (1,'user:delete','用户删除','2022-09-14 13:39:50'),(2,'user:get','用户查询','2022-09-14 13:40:06'),(3,'user:add','用户新增','2022-09-14 13:40:30'),(4,'user:save','用户保存','2022-09-14 13:40:44'),(5,'user:update','用户修改','2022-09-14 13:41:01'),(6,'user:assign','用户分配角色','2022-09-14 13:41:36'),(7,'role:get','角色查询','2022-09-14 13:41:51'),(8,'role:add','角色新增','2022-09-14 13:42:06'),(9,'role:save','角色保存','2022-09-14 13:42:21'),(10,'role:delete','角色删除','2022-09-14 13:42:34'),(11,'role:update','角色修改','2022-09-14 13:42:57'),(12,'role:assign','角色分配权限','2022-09-14 13:43:17'),(13,'auth:get','权限查询','2022-09-14 13:43:17'),(14,'auth:add','权限新增','2022-09-14 13:43:17'),(15,'auth:save','权限保存','2022-09-14 13:43:17'),(16,'auth:delete','权限删除','2022-09-14 13:43:17'),(17,'auth:update','权限修改','2022-09-14 13:43:17');
   ```

### 4、inner_user_role

1. 说明：用户跟角色关联表

2. 字段

   | 字段    | 数据类型 | 长度 | 说明       |
   | ------- | -------- | ---- | ---------- |
   | id      | int      | 11   | 自增主键id |
   | user_id | int      | 11   | 用户id     |
   | role_id | int      | 11   | 角色id     |

3. 建表语句

   ```sql
   CREATE TABLE `inner_user_role` (
       `id` int(11) NOT NULL AUTO_INCREMENT,
       `user_id` int(11) NOT NULL,
       `role_id` int(11) NOT NULL,
       PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
   ```

4. 初始数据

   ```sql
   insert  into `inner_user_role`(`id`,`user_id`,`role_id`) values (1,1,1),(2,2,2);
   ```

### 5、inner_role_authority

1. 说明：角色跟权限关联表

2. 字段

   | 字段         | 数据类型 | 长度 | 说明       |
   | ------------ | -------- | ---- | ---------- |
   | id           | int      | 11   | 自增主键id |
   | role_id      | int      | 11   | 角色id     |
   | authority_id | int      | 11   | 权限id     |

3. 建表语句

   ```sql
   CREATE TABLE `inner_role_authority` (
       `id` int(11) NOT NULL AUTO_INCREMENT,
       `role_id` int(11) NOT NULL,
       `authority_id` int(11) NOT NULL,
       PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
   ```

4. 初始数据

   ```sql
   insert  into `inner_role_authority`(`id`,`role_id`,`authority_id`) values (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,1,10),(11,1,11),(12,1,12),(13,1,13),(14,1,14),(15,1,15),(16,1,16),(17,1,17),(18,2,2),(19,2,3),(20,2,4),(21,2,5);
   ```

### 6、t_file

1. 说明：文件信息表

2. 字段

   | 字段         | 数据类型 | 长度 | 说明                                                         |
   | ------------ | -------- | ---- | ------------------------------------------------------------ |
   | file_id      | char     | 32   | 自定义主键，使用UUID作为file_id                              |
   | name         | varchar  | 256  | 文件名                                                       |
   | size         | bigint   | 20   | 文件大小，单位是字节，目录为空                               |
   | owner        | varchar  | 255  | 文件所有者                                                   |
   | path         | varchar  | 1024 | 文件所在路径，不包含自身文件名。相对于项目的files目录来说的。”\“就是files目录下，一般这个字段为"\"都是用户的家目录，用户家目录就是挂靠在项目的files目录下。 |
   | parent_id    | char     | 32   | 父目录的file_id，家目录这个字段为null，因为files目录不在表上登记。 |
   | create_time  | char     | 19   | 创建时间                                                     |
   | modi_time    | char     | 19   | 修改时间                                                     |
   | is_directory | tinyint  | 1    | 是否是目录                                                   |
   | share        | tinyint  | 1    | 是否分享                                                     |
   | trash        | int      | 11   | 是否删除至回收站。0表示没有删除到回收站。1表示主动删除到回收站。2表示连带删除至回收站（就是删除目录后，目录下的文件就是连带删除的） |
   | trash_by     | char     | 32   | 记录是由哪个目录的删除导致的连带删除。字段保存的是主动删除的目录的file_id。trash字段为2的这个字段才会有值。trash字段为0或为1的这个字段都为null |
   | trash_time   | char     | 19   | 删除到回收站的时间                                           |
   | is_delete    | tinyint  | 1    | 是从回收站永久删除                                           |

3. 建表语句

   ```sql
   CREATE TABLE `t_file` (
       `file_id` char(32) NOT NULL,
       `name` varchar(256) NOT NULL,
       `size` bigint(20) DEFAULT NULL,
       `owner` varchar(255) NOT NULL,
       `path` varchar(1024) NOT NULL,
       `parent_id` char(32) DEFAULT NULL,
       `create_time` char(19) NOT NULL,
       `modi_time` char(19) DEFAULT NULL,
       `is_directory` tinyint(1) NOT NULL,
       `share` tinyint(1) NOT NULL,
       `trash` int(11) NOT NULL,
       `trash_by` char(32) DEFAULT NULL,
       `trash_time` char(19) DEFAULT NULL,
       `is_delete` tinyint(1) NOT NULL,
       PRIMARY KEY (`file_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
   ```

4. 超级管理员、管理员、普通用户初始家目录数据

   ```sql
   insert  into `t_file`(`file_id`,`name`,`size`,`owner`,`path`,`parent_id`,`create_time`,`modi_time`,`is_directory`,`share`,`trash`,`trash_by`,`trash_time`,`is_delete`) values ('236f1d60a44b4778b073ff7620a286e1','superAdmin',NULL,'superAdmin','\\',NULL,'2022-10-10 14:58:59',NULL,1,0,0,NULL,NULL,0),('ab286620449b478b953cde9a4e84add5','ordinaryUsers',NULL,'ordinaryUsers','\\',NULL,'2022-10-10 15:00:24',NULL,1,0,0,NULL,NULL,0),('f0bff2dfbaa34d08ad1638d2535acba2','admin',NULL,'admin','\\',NULL,'2022-10-10 14:47:04',NULL,1,0,0,NULL,NULL,0);
   ```

### 7、t_system_log

1. 说明：系统日志表

2. 字段

   | 字段         | 数据类型 | 长度 | 说明                 |
   | ------------ | -------- | ---- | -------------------- |
   | id           | int      | 11   | 自增主键id           |
   | create_time  | varchar  | 255  | 系统日志记录生成时间 |
   | level        | varchar  | 20   | 消息等级             |
   | thread       | varchar  | 255  | 产生日志记录的线程   |
   | class        | varchar  | 255  | 产生日志记录的类     |
   | method       | varchar  | 255  | 产生日志记录的方法   |
   | message      | varchar  | 2048 | 信息                 |
   | all_category | varchar  | 1024 | 所有分类汇总（详细） |

3. 建表语句

   ```sql
   CREATE TABLE `t_system_log` (
       `id` int(11) NOT NULL AUTO_INCREMENT,
       `create_time` varchar(255) DEFAULT NULL,
       `level` varchar(20) DEFAULT NULL,
       `thread` varchar(255) DEFAULT NULL,
       `class` varchar(255) DEFAULT NULL,
       `method` varchar(255) DEFAULT NULL,
       `message` varchar(2048) DEFAULT NULL,
       `all_category` varchar(1024) DEFAULT NULL,
       PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
   ```

### 8、t_request_log

1. 说明：请求日志表

2. 字段

   | 字段         | 数据类型 | 长度 | 说明                                                         |
   | ------------ | -------- | ---- | ------------------------------------------------------------ |
   | id           | int      | 11   | 自增主键id                                                   |
   | create_time  | char     | 19   | 请求发生时间                                                 |
   | method       | char     | 20   | 请求方式（GET或POST）                                        |
   | ip_address   | varchar  | 15   | ip地址                                                       |
   | request_path | varchar  | 255  | 请求路径                                                     |
   | request_url  | varchar  | 255  | 请求url                                                      |
   | account      | varchar  | 255  | 请求账号。对于没有登录的请求，如请求登录页等，这个字段为null。只有已登录的才是用户账号 |
   | parameter    | varchar  | 2048 | 请求参数                                                     |

3. 建表语句

   ```sql
   CREATE TABLE `t_request_log` (
       `id` int(11) NOT NULL AUTO_INCREMENT,
       `create_time` char(19) DEFAULT NULL,
       `method` char(20) DEFAULT NULL,
       `ip_address` varchar(15) DEFAULT NULL,
       `request_path` varchar(255) DEFAULT NULL,
       `request_url` varchar(255) DEFAULT NULL,
       `account` varchar(255) DEFAULT NULL,
       `parameter` varchar(2048) DEFAULT NULL,
       PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
   ```


# 二、登录相关接口

### 1、/login/page

1. 请求方式：get
2. 说明：访问到登录页

### 2、/login

1. 请求方式：post

2. 说明：登录提交地址

3. 请求数据：

   | 属性     | 说明 |
   | -------- | ---- |
   | account  | 账号 |
   | password | 密码 |

4. 返回数据：页面。登录成功跳转至首页/main。登录失败回到登录页。

### 3、/main

1. 请求方式：get
2. 说明：访问到首页
3. 请求数据：无
4. 返回数据：页面

### 4、/logout

1. 请求方式：get
2. 说明：退出登录
3. 请求数据：无
4. 返回数据：页面。重定向到登录页/login/page

# 三、用户维护相关接口

### 1、/user

1. 请求方式：get
2. 说明：访问用户维护页

### 2、/user/info

1. 请求方式：post

2. 说明：分页方式获取用户数据

3. 请求数据：

   | 属性     | 说明                 |
   | -------- | -------------------- |
   | pageNum  | 页码(可省略)         |
   | pageSize | 页面大小(可省略)     |
   | keyword  | 搜索用关键词(可省略) |

4. 返回数据格式：

   | 属性 | 说明                                 |
   | ---- | ------------------------------------ |
   | code | 结果状态码，"0"代表成功，"1"代表失败 |
   | msg  | 错误信息。成功时为null               |
   | data | 返回数据                             |


### 3、/user/save

1. 请求方式：post

2. 说明：新增用户

3. 请求数据：

   | 属性     | 说明   |
   | -------- | ------ |
   | account  | 账号   |
   | password | 密码   |
   | userName | 用户名 |
   | email    | 邮箱   |

4. 返回数据格式：

   | 属性 | 说明                                 |
   | ---- | ------------------------------------ |
   | code | 结果状态码，"0"代表成功，"1"代表失败 |
   | msg  | 错误信息。成功时为null               |
   | data | 返回数据                             |

### 4、/user/delete

1. 请求方式：post

2. 说明：删除用户，统一了单个用户删除跟批量删除，都是传递数组

3. 请求数据：

   | 属性    | 说明         |
   | ------- | ------------ |
   | userIds | 用户的id数组 |

4. 返回数据格式：同上，也是{code:"", msg:"", data:""}

### 5、/user/update

1. 请求方式：post

2. 说明：修改用户邮箱、用户名

3. 请求数据：

   | 属性     | 说明                                                         |
   | -------- | ------------------------------------------------------------ |
   | account  | 用户账号，不能修改，只是用来作为where条件查询。不使用id作为查询条件，是后端Handler使用User对象直接接收参数，让SpringMVC去封装参数，为了避免客户绕过前端，使用接口调试工具（postMan等），间接修改账号。其实使用id作为查询条件也行，只有确保sql语句update的时候不设置account即可。看个人选择。 |
   | userName | 用户名                                                       |
   | email    | 用户邮箱                                                     |

4. 返回数据：同上，也是{code:"", msg:"", data:""}

# 四、角色维护相关接口

### 1、/role

1. 请求方式：get

2. 说明：访问用户维护页

3. 请求数据：无

4. 返回数据：html文档


### 2、/role/info

1. 请求方式：post

2. 说明：分页方式获取用户数据

3. 请求数据：

   | 属性     | 说明                                 |
   | -------- | ------------------------------------ |
   | pageNum  | 页码(可省略)，默认值1                |
   | pageSize | 页面大小(可省略)，默认值5            |
   | keyword  | 搜索用关键词(可省略)，默认值空字符串 |

4. 返回数据：{code:"", msg:"", data:""}


### 3、/role/save

1. 请求方式：post

2. 说明：新增角色

3. 请求数据：

   | 属性 | 说明     |
   | ---- | -------- |
   | name | 角色名称 |

4. 返回数据：{code:"", msg:"", data:""}

### 4、/role/delete

1. 请求方式：post

2. 说明：批量删除角色

3. 请求数据：

   | 属性    | 说明       |
   | ------- | ---------- |
   | roleIds | 角色id数组 |

4. 返回数据：{code:"", msg:"", data:""}

### 5、/role/update

1. 请求方式：post

2. 说明：修改角色信息

3. 请求数据

   | 属性 | 说明     |
   | ---- | -------- |
   | id   | 角色id   |
   | name | 角色名称 |

4. 返回数据：{code:"", msg:"", data:""}

# 五、权限维护相关接口

### 1、/authority

1. 请求方式：get
2. 说明：访问权限维护页
3. 请求数据：无

4. 返回数据：html文档


### 2、/authority/info

1. 请求方式：post

2. 说明：获取权限分页信息

3. 请求数据：

   | 属性     | 说明                 |
   | -------- | -------------------- |
   | pageNum  | 页码(可省略)         |
   | pageSize | 页面大小(可省略)     |
   | keyword  | 搜索用关键词(可省略) |

4. 返回数据：{code:"", msg:"", data:""}


### 3、/authority/save

1. 请求方式：post

2. 说明：保存新增权限

3. 请求数据：

   | 属性  | 说明     |
   | ----- | -------- |
   | name  | 权限名称 |
   | title | 权限标题 |

4. 返回数据：{code:"", msg:"", data:""}


### 4、/authority/delete

1. 请求方式：post

2. 说明：删除权限

3. 请求数据：

   | 属性         | 说明       |
   | ------------ | ---------- |
   | authorityIds | 权限id数组 |

4. 返回数据：{code:"", msg:"", data:""}

### 5、/authority/update

1. 请求方式：post

2. 说明：更新权限信息

3. 请求数据：

   | 属性  | 说明     |
   | ----- | -------- |
   | id    | 权限id   |
   | name  | 权限名称 |
   | title | 权限标题 |

4. 返回数据：{code:"", msg:"", data:""}

# 六、用户分配角色相关接口

### 1、/assign/role/info

1. 请求方式：post

2. 说明：查看用户分配角色情况

3. 请求数据：

   | 属性   | 说明     |
   | ------ | -------- |
   | userId | 用户的Id |

4. 返回数据：{code:"", msg:"", data:""}

   - 成功时返回的data中包含已分配的角色信息和未分配的角色信息

     ```json
     {
         "unAssignRoleList":[未分配的角色列表],
         "assignedRoleList":[已分配的角色列表]
     }
     ```

### 2、/assign/role/save

1. 请求方式：post

2. 说明：保存用户分配的角色

3. 请求数据：

   | 属性    | 说明                                                   |
   | ------- | ------------------------------------------------------ |
   | userId  | 用户的Id                                               |
   | roleIds | 角色Id数组，可以不传这个参数。因为可以不分配角色给用户 |

4. 返回数据：{code:"", msg:"", data:""}

# 七、角色分配权限相关接口

### 1、/assign/authority/info

1. 请求方式：post

2. 说明：查看角色分配权限情况

3. 请求数据：

   | 属性   | 说明   |
   | ------ | ------ |
   | roleId | 角色Id |

4. 返回数据：{code:"", msg:"", data:""}

   - 成功时返回的data中包含已分配的角色信息和未分配的角色信息

     ```json
     {
         "unAssignAuthorityList":[未分配的权限列表],
         "assignedAuthorityList":[已分配的权限列表]
     }
     ```


### 2、/assign/authority/save

1. 请求方式：post

2. 说明：保存角色分配的权限

3. 请求数据：

   | 属性         | 说明                                                     |
   | ------------ | -------------------------------------------------------- |
   | roleId       | 角色的Id                                                 |
   | authorityIds | 权限的Id数组，可以不传这个参数。因为可以不分配权限给角色 |

4. 返回数据：{code:"", msg:"", data:""}

# 八、文件相关接口

### 1、/file

1. 请求方式：get
2. 说明：访问文件页
3. 请求数据：无

4. 返回数据：html页面

### 2、/file/home/info

1. 请求方式：post

2. 说明：获取用户家目录信息

3. 请求数据：无。根据session来获取用户信息，查询数据库

4. 返回数据：{code:"", msg:"", data:""}
   - data中是一个File对象的json格式数据。就是用户家目录的fileId、name等信息。保存到页面全局变量中。方便后续请求获取家目录的信息。


### 3、/file/home

1. 请求方式：post

2. 说明：获取用户家目录中的文件信息

3. 请求数据：

   | 属性 | 说明                                                         |
   | ---- | ------------------------------------------------------------ |
   | path | 家目录路径，就是\"\\"，相对于项目来说就是\"\files\用户账号\\" |

4. 返回数据：{code:"", msg:"", data:""}

   - data中是一个File对象的json格式数据。

### 4、/file/create/folder

1. 请求方式：post

2. 说明：创建文件夹

3. 请求数据：

   | 属性     | 说明               |
   | -------- | ------------------ |
   | name     | 需要创建的目录名   |
   | path     | 目录的父路径       |
   | parentId | 目录父路径的fileId |

4. 返回数据：{code:"", msg:"", data:""}

### 5、/file/upload

1. 请求方式：post

2. 说明：上传文件（支持一次性多文件上传）

3. 请求数据：

   | 属性           | 说明                                            |
   | -------------- | ----------------------------------------------- |
   | multipartFiles | MultipartFile数组，文件二进制数据，支持多个文件 |
   | path           | 需要将上传文件到的位置，也就是目标目录路径      |
   | parentId       | 目标目录路径的fileId                            |

4. 返回数据：{code:"", msg:"", data:""}

### 6、/file/checked

1. 请求方式：post

2. 说明：检查是否是目录，检查文件是否在服务器存储目录files中存在。用于下载前进行检查

3. 请求数据：

   | 属性  | 说明                                                         |
   | ----- | ------------------------------------------------------------ |
   | paths | 需要下载的文件的路径。是相对于用户家目录来说的。用户家目录是“\” |

4. 返回数据：{code:"", msg:"", data:""}

### 7、/file/download

1. 请求方式：post

2. 说明：下载文件（支持一次性多文件下载（打包成zip））。前端实现是先通过/file/checked检查后端文件是否存在，然后再通过、/file/download来下载文件。

3. 请求数据：

   | 属性  | 说明                                                         |
   | ----- | ------------------------------------------------------------ |
   | paths | 需要下载的文件的路径。是相对于用户家目录来说的。用户家目录是“\” |

4. 返回数据：{code:"", msg:"", data:""}

### 8、/file/folders

1. 请求方式：post
2. 说明：移动文件前，查询用户所有的目录信息，按创建时间倒序排序。
3. 请求数据：无，根据session获取用户账号去查询
4. 返回数据：{code:"", msg:"", data:""}

### 9、/file/move

1. 请求方式：post

2. 说明：对文件夹/文件进行移动

3. 请求数据：

   | 属性       | 说明                                                   |
   | ---------- | ------------------------------------------------------ |
   | fileId     | 需要移动的文件或目录的fileId                           |
   | path       | 需要移动的文件或目录的路径（自身路径）                 |
   | fileType   | 需要移动的文件或目录的类型（文件：file，目录：folder） |
   | parentPath | 需要移动的文件或目录的父路径                           |
   | toFileId   | 目标位置目录的fileId                                   |
   | toPath     | 目标位置目录的路径                                     |

   - 请求参数示例：

     - 移动文件：将\jinuxes\测试3\测试1\到此为止-连诗雅.mp3文件移动到\jinuxes\测试1\目录下

     - 移动前数据库中的文件信息

       ![](S:\笔记\Typora\个人项目\相关截图\移动文件前.PNG)

     - 移动后数据库中的文件信息

       ![](S:\笔记\Typora\个人项目\相关截图\移动文件后.PNG)

     - 各个请求的参数值如下表

       | 属性       | 请求参数值                       |
       | ---------- | -------------------------------- |
       | fileId     | d08cae5654594021a318644eacc25fec |
       | path       | \测试3\测试1\到此为止-连诗雅.mp3 |
       | fileType   | file                             |
       | parentPath | \测试3\测试1\                    |
       | toFileId   | 3aaf55743c3049c1867aa96aa0a921a0 |
       | toPath     | \测试1\                          |

       - 注意：\jinuxes是用户的家目录，每个用户创建的时候自动进行创建的，\则是项目的files目录，作为所有存储文件的根目录，各个用户的家目录都挂靠在\下。请求的时候是不需要带上用户家目录的，后台默认从session中获取登录的用户信息，来拼成完整的目录。因此哪个用户的请求，默认的就是对应到登录的用户。

     - 移动目录：将目录\jinuxes\测试2\测试5\移动到\jinuxes\测试3\目录下

       - 移动目录前数据库信息

         ![](S:\笔记\Typora\个人项目\相关截图\移动目录前.PNG)

       - 移动目录后数据库信息

         ![](S:\笔记\Typora\个人项目\相关截图\移动目录后.PNG)

       - 各个请求的参数如下表

         | 属性       | 请求参数值                       |
         | ---------- | -------------------------------- |
         | fileId     | 4cc53c726e9b46989b1f57a557224a2f |
         | path       | \测试2\测试5\                    |
         | fileType   | folder                           |
         | parentPath | \测试2\                          |
         | toFileId   | 8c55dcc7389940128e039f2a45b3c66f |
         | toPath     | \测试3\                          |

4. 返回数据：{code:"", msg:"", data:""}

   - 成功时：code：0；msg：空；data：空
   - 失败时：code：1；msg：相关错误信息；data：空

### 10、/file/delete

1. 请求方式：post

2. 说明：删除文件、目录

3. 请求数据：

   | 属性    | 说明                             |
   | ------- | -------------------------------- |
   | fileIds | 需要删除的文件或目录的fileId数组 |

4. 返回数据：{code:"", msg:"", data:""}

### 11、/file/search

1. 请求方式：post

2. 说明：根据文件名关键字查找文件

3. 请求数据：

   | 属性    | 说明             |
   | ------- | ---------------- |
   | keyword | 需要查找的关键字 |

4. 返回数据：{code:"", msg:"", data:""}

### 12、/file/capacity

1. 请求方式：post

2. 说明：获取用户的存储空间总容量及存储空间使用量。根据session获取用户账号去查询。

3. 请求数据：无

4. 返回数据：{code:"", msg:"", data:""}

   - 返回成功时，data中的数据

     ```json
     {
         "usedCapacity":xxx,
         "totalCapacity":xxx
     }
     ```

### 13、/file/recycle

1. 请求方式：get
2. 说明：获取回收站页面

### 14、/file/recycle/info

1. 请求方式：post
2. 说明：获取回收站中的文件。根据session来获取用户账号去查找。
3. 请求数据：无
4. 返回数据：{code:"", msg:"", data:""}

### 15、/file/recovery

1. 请求方式：post

2. 说明：回收站恢复文件到原来用户文件的位置

3. 请求数据：

   | 属性    | 说明                       |
   | ------- | -------------------------- |
   | fileIds | 需要恢复的文件的fileId数组 |

4. 返回数据：{code:"", msg:"", data:""}

### 16、/file/delete/permanently

1. 请求方式：post

2. 说明：回收站中删除文件。

3. 请求数据：

   | 属性    | 说明                               |
   | ------- | ---------------------------------- |
   | fileIds | 回收站中需要删除的文件的fileId数组 |

4. 返回数据：{code:"", msg:"", data:""}

# 九、用户个人相关接口

### 1、/personal/info

1. 请求方式：post

2. 说明：根据session获取用户账号，根据用户账号获取用户个人信息

3. 请求数据：无

4. 返回数据：{code:"", msg:"", data:""}


### 2、/personal/info/update

1. 请求方式：post

2. 说明：根据session获取用户账号，根据用户账号修改用户个人信息

3. 请求数据：

   | 属性     | 说明   |
   | -------- | ------ |
   | userName | 用户名 |
   | email    | 邮箱   |

4. 返回数据：{code:"", msg:"", data:""}

### 3、/personal/password/update

1. 请求方式：post

2. 说明：根据session获取用户账号，根据用户账号修改用户密码

3. 请求数据：

   | 属性     | 说明     |
   | -------- | -------- |
   | password | 用户密码 |

4. 返回数据：{code:"", msg:"", data:""}

### 4、/register

1. 请求方式：get
2. 说明：获取用户注册页面

### 5、/register/save

1. 请求方式：post

2. 说明：注册用户

3. 请求数据：

   | 属性     | 说明   |
   | -------- | ------ |
   | account  | 账号   |
   | password | 密码   |
   | userName | 用户名 |
   | email    | 邮箱   |

4. 返回数据：{code:"", msg:"", data:""}
