# 需求文档

## 1. 背景说明

为了方便学校内部社团与社联的交流，学生更加方便地了解社团，固开发此系统。



## 2. 功能需求

概念介绍

**用户**：学生，老师，以及系统管理员（开发者

**管理员**：除了系统管理员，还有社团的管理员，可以是会长，副会长，或者社团干部

初始化会长为管理员，其他管理员以及具体权限由会长授予

**权限**：可能是添加社员权限、修改社团基本信息权限等

### 2.1.招新/人员管理

1. 社员报名（增加）

   用户：学生通过登入系统，录入信息，扫码支付报名（可选）

   管理员：管理员后台添加/确认，支持文件形式(txt, excel)批量导入

2. 社员退社

   用户：学生因为某些原因提交退社申请

   管理员：

   ①经过社团管理员确认进行退社

   ②管理员也可以踢出社员

3. 社员信息修改

   用户：学生因为某些原因需要修改个人信息，如学号等

   管理员：管理员可以修改用户信息

4. 社员查询

   用户：

   ①未加入前，学生可以查看干部成员

   ②加入后，学生可以查看本社成员非隐私信息

   管理员：管理员查询社员名单，应该可以根据年份、性别等等区分

### 2.2 日常活动

1. 公告（置顶）

   用户：

   学生加入社团之前可以看到公告

   管理员：

   管理员可以发布公告

2. 活动

   用户：所有学生可以查看以往发布的活动

   管理员：管理员可以发布活动

### 2.3 通信部分

1. 会长通知：

   社联可以发通知给其他社团会长，并且可以看到是否已读，如果可以的话绑定公众号

### 2.4 社团基本信息

1. 社团简介

   用户：所有用户都可以看到社团简介

   管理员：管理员可以修改社团简介

2. 社徽

   用户：所有用户可以看到社徽

   管理员：只有会长可以修改社徽

3. 社团章程（规章制度）

   用户：可以查看

   管理员：只有会长可以修改章程

4. 社团职位

   用户：用户可以查看社团职位

   管理员：只有会长可以修改职位及其职责
   
5. 社团添加

   管理员：系统管理员可以添加社团，支持文件形式(txt, excel)批量导入

## 3. 扩展需求

 - 系统高可用，支持横向扩容
 - 日志分析平台
 - 服务安全
 - 高并发
 - 将收集起来的日志进行数据分析/挖掘

## 4. 基本表设计

ID采取雪花算法

### 4.1 用户表 user

|     列名     |   类型   | 长度 | 主键 | 唯一 | 索引 | 外键 |     说明     |
| :----------: | :------: | :--: | :--: | :--: | :--: | :--: | :----------: |
|      id      | varchar  |  64  |  √   |  √   |  √   |      |      ID      |
|  student_id  | varchar  |  32  |      |      |      |      |     学号     |
|   username   | varchar  |  32  |      |  √   |      |      |    用户名    |
|   password   | varchar  | 255  |      |      |      |      |  Bcrypt加密  |
|   nickname   | varchar  |  32  |      |      |      |      |     昵称     |
| user_profile | varchar  | 255  |      |      |      |      | 用户头像地址 |
|    phone     | varchar  |  11  |      |      |      |      |    手机号    |
|    class     | varcahr  | 255  |      |      |      |      |     班级     |
|      wx      | varchar  |  64  |      |      |      |      |    微信号    |
|      qq      | varchar  |  32  |      |      |      |      |     QQ号     |
|    email     | varchar  |  64  |      |      |      |      |     邮件     |
|   open_id    | varchar  | 255  |      |      |      |      |  微信OPENID  |
|    state     | tinyint  |  1   |      |      |      |      | 0正常/1禁用  |
| create_time  | datetime |  0   |      |      |      |      |   创建时间   |
| update_time  | datetime |  0   |      |      |      |      |   修改时间   |
|   operator   | varchar  |  64  |      |      |      |      |    操作者    |

### 4.2 社团表 club

|      列名      |   类型   | 长度 | 主键 | 唯一 | 索引 |  外键   |     说明      |
| :------------: | :------: | :--: | :--: | :--: | :--: | :-----: | :-----------: |
|       id       | varchar  |  64  |  √   |  √   |  √   |         |      ID       |
|      name      | varchar  |  32  |      |  √   |  √   |         |   社团名称    |
|  description   | varchar  | 255  |      |      |      |         |   社团简介    |
|    logo_uri    | varchar  | 255  |      |      |      |         | logo/社徽地址 |
| regulation_uri | varchar  | 255  |      |      |      |         | 章程文件地址  |
|     state      | tinyint  |  1   |      |      |      |         |  0正常/1禁用  |
|  create_time   | datetime |  0   |      |      |      |         |   创建时间    |
|  update_time   | datetime |  0   |      |      |      |         |   修改时间    |
|    operator    | varchar  |  64  |      |      |      | user.id |    操作者     |

### 4.3 角色表 role（职位）

|    列名     |   类型   | 长度 | 主键 | 唯一 | 索引 |  外键   |    说明     |
| :---------: | :------: | :--: | :--: | :--: | :--: | :-----: | :---------: |
|     id      | varchar  |  64  |  √   |  √   |  √   |         |     ID      |
|    name     | varchar  |  32  |      |  √   |  √   |         |  角色名称   |
| description | varchar  | 255  |      |      |      |         |    描述     |
|    state    | tinyint  |  1   |      |      |      |         | 0正常/1禁用 |
| create_time | datetime |  0   |      |      |      |         |  创建时间   |
| update_time | datetime |  0   |      |      |      |         |  修改时间   |
|  operator   | varchar  |  64  |      |      |      | user.id |   操作者    |

### 4.4 用户_角色表 user_role

|    列名     |   类型   | 长度 | 主键 | 唯一 | 索引 |  外键   |    说明     |
| :---------: | :------: | :--: | :--: | :--: | :--: | :-----: | :---------: |
|     id      | varchar  |  64  |  √   |  √   |  √   |         |     ID      |
|   user_id   | varchar  |  64  |      |      |      | user.id |   用户ID    |
|   role_id   | varchar  |  64  |      |      |      | role.id |   角色ID    |
|    state    | tinyint  |  1   |      |      |      |         | 0正常/1禁用 |
| create_time | datetime |  0   |      |      |      |         |  创建时间   |
| update_time | datetime |  0   |      |      |      |         |  修改时间   |
|  operator   | varchar  |  64  |      |      |      | user.id |   操作者    |

### 4.5 权限表 permission

|    列名     |   类型   | 长度 | 主键 | 唯一 | 索引 |  外键   |    说明     |
| :---------: | :------: | :--: | :--: | :--: | :--: | :-----: | :---------: |
|     id      | varchar  |  64  |  √   |  √   |  √   |         |     ID      |
|    code     | varchar  |  64  |      |      |      |         |  授权代码   |
|  parent_id  | varchar  |  64  |      |      |      |         |    父级     |
|   is_menu   | tinyint  |  1   |      |      |      |         |  0是/1不是  |
|    level    |   int    |  11  |      |      |      |         |    层级     |
|    sort     |   int    |  11  |      |      |      |         |    顺序     |
|  icon_uri   | varchar  |  32  |      |      |      |         |   iconUri   |
|    state    | tinyint  |  1   |      |      |      |         | 0正常/1禁用 |
| create_time | datetime |  0   |      |      |      |         |  创建时间   |
| update_time | datetime |  0   |      |      |      |         |  修改时间   |
|  operator   | varchar  |  64  |      |      |      | user.id |   操作者    |

### 4.6 权限_角色表 permission_role

|     列名      |   类型   | 长度 | 主键 | 唯一 | 索引 |     外键      |    说明     |
| :-----------: | :------: | :--: | :--: | :--: | :--: | :-----------: | :---------: |
|      id       | varchar  |  64  |  √   |  √   |  √   |               |     ID      |
| permission_id | varchar  |  64  |      |      |      | permission.id |   权限ID    |
|    role_id    | varchar  |  64  |      |      |      |    role.id    |   角色ID    |
|     state     | tinyint  |  1   |      |      |      |               | 0正常/1禁用 |
|  create_time  | datetime |  0   |      |      |      |               |  创建时间   |
|  update_time  | datetime |  0   |      |      |      |               |  修改时间   |
|   operator    | varchar  |  32  |      |      |      |    user.id    |   操作者    |

### 4.7 活动表 activity

|    列名     |   类型   | 长度 | 主键 | 唯一 | 索引 |  外键   |     说明      |
| :---------: | :------: | :--: | :--: | :--: | :--: | :-----: | :-----------: |
|     id      | varchar  |  64  |  √   |  √   |  √   |         |      ID       |
|    title    | varchar  |  64  |      |      |      |         |   活动标题    |
|    time     | datetime |  0   |      |      |      |         |   活动时间    |
|    area     | varchar  | 255  |      |      |      |         |   活动地点    |
| description | varchar  | 512  |      |      |      |         |   活动描述    |
|   club_id   | varchar  |  64  |      |      |      | club.id |    社团ID     |
|   user_id   | varchar  |  64  |      |      |      | user.id |   发布人ID    |
|   is_top    | tinyint  |  1   |      |      |      |         | 0置顶/1不置顶 |
|    state    | tinyint  |  1   |      |      |      |         |  0正常/1禁用  |
| create_time | datetime |  0   |      |      |      |         |   创建时间    |
| update_time | datetime |  0   |      |      |      |         |   修改时间    |
|  operator   | varchar  |  64  |      |      |      | user.id |    操作者     |

### 4.8 社团人员表 user_club

|    列名     |   类型   | 长度 | 主键 | 唯一 | 索引 |  外键   |         说明          |
| :---------: | :------: | :--: | :--: | :--: | :--: | :-----: | :-------------------: |
|     id      | varchar  |  64  |  √   |  √   |  √   |         |          ID           |
|   user_id   | varchar  |  64  |      |      |      | user.id |        用户ID         |
|   club_id   | varchar  |  64  |      |      |      | club.id |        社团ID         |
|    year     |   int    |  4   |      |      |      |         |         年份          |
|    state    | tinyint  |  1   |      |      |      |         | 0正常/1禁用/2未支付/3 |
| create_time | datetime |  0   |      |      |      |         |       创建时间        |
| update_time | datetime |  0   |      |      |      |         |       修改时间        |
|  operator   | varchar  |  64  |      |      |      | user.id |        操作者         |

### 4.9 社团职位表 department

|    列名     |   类型   | 长度 | 主键 | 唯一 | 索引 |  外键   |    说明     |
| :---------: | :------: | :--: | :--: | :--: | :--: | :-----: | :---------: |
|     id      | varchar  |  64  |  √   |  √   |  √   |         |     ID      |
|    name     | varchar  |  64  |      |      |      |         |  职位名称   |
| description | varchar  | 255  |      |      |      |         |  职位描述   |
|   club_id   | varchar  |  64  |      |      |      | club.id |   社团ID    |
|    state    | tinyint  |  1   |      |      |      |         | 0正常/1禁用 |
| create_time | datetime |  0   |      |      |      |         |  创建时间   |
| update_time | datetime |  0   |      |      |      |         |  修改时间   |
|  operator   | varchar  |  64  |      |      |      | user.id |   操作者    |

### 4.10 社团干部表

|     列名      |   类型   | 长度 | 主键 | 唯一 | 索引 |     外键      |    说明     |
| :-----------: | :------: | :--: | :--: | :--: | :--: | :-----------: | :---------: |
|      id       | varchar  |  64  |  √   |  √   |  √   |               |     ID      |
| department_id | varchar  |  64  |      |      |      | department.id |   职位ID    |
|    club_id    | varchar  |  64  |      |      |      |    user.id    |   用户ID    |
|     year      |   int    |  4   |      |      |      |               |    年份     |
|     state     | tinyint  |  1   |      |      |      |               | 0正常/1禁用 |
|  create_time  | datetime |  0   |      |      |      |               |  创建时间   |
|  update_time  | datetime |  0   |      |      |      |               |  修改时间   |
|   operator    | varchar  |  64  |      |      |      |    user.id    |   操作者    |