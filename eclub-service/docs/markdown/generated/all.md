# EClub api文档


<a name="overview"></a>
## 概览
EClub 描述


### 版本信息
*版本* : 1.0


### URI scheme
*域名* : localhost:9000  
*基础路径* : /


### 标签

* 用户接口 : User Controller Impl
* 社团接口 : Club Controller Impl
* 认证接口 : Auth Controller Impl




<a name="paths"></a>
## 资源

<a name="5b0ad68a45d0727406020ff99714ad3d"></a>
### 用户接口
User Controller Impl


<a name="insertuserusingget"></a>
#### 添加用户
```
GET /user
```


##### 参数

|类型|名称|类型|
|---|---|---|
|**Query**|**classGrade**  <br>*可选*|string|
|**Query**|**createTime**  <br>*可选*|string (date-time)|
|**Query**|**email**  <br>*可选*|string|
|**Query**|**id**  <br>*可选*|string|
|**Query**|**nickname**  <br>*可选*|string|
|**Query**|**openId**  <br>*可选*|string|
|**Query**|**operator**  <br>*可选*|string|
|**Query**|**password**  <br>*可选*|string|
|**Query**|**phone**  <br>*可选*|string|
|**Query**|**qq**  <br>*可选*|string|
|**Query**|**sessionKey**  <br>*可选*|string|
|**Query**|**state**  <br>*可选*|integer (int32)|
|**Query**|**studentId**  <br>*可选*|string|
|**Query**|**updateTime**  <br>*可选*|string (date-time)|
|**Query**|**userProfile**  <br>*可选*|string|
|**Query**|**username**  <br>*可选*|string|
|**Query**|**wx**  <br>*可选*|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResponseResult](#responseresult)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/user
```


###### 请求 query
```
json :
{
  "classGrade" : "string",
  "createTime" : "string",
  "email" : "string",
  "id" : "string",
  "nickname" : "string",
  "openId" : "string",
  "operator" : "string",
  "password" : "string",
  "phone" : "string",
  "qq" : "string",
  "sessionKey" : "string",
  "state" : 0,
  "studentId" : "string",
  "updateTime" : "string",
  "userProfile" : "string",
  "username" : "string",
  "wx" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "message" : "string",
  "success" : true
}
```


<a name="deleteuserbyidsusingdelete"></a>
#### 通过ID列表删除用户列表
```
DELETE /user
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**ids**  <br>*必填*|ids|< string > array|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResponseResult](#responseresult)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/user
```


###### 请求 body
```
json :
[ "string" ]
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "message" : "string",
  "success" : true
}
```


<a name="listusersbypageusingget"></a>
#### 分页查询用户
```
GET /user/list/{page}/{size}
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Path**|**page**  <br>*必填*|page|integer (int32)|
|**Path**|**size**  <br>*必填*|size|integer (int32)|
|**Query**|**userQueryParam**  <br>*必填*|userQueryParam|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[QueryResponseResult](#queryresponseresult)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/user/list/0/0
```


###### 请求 query
```
json :
{
  "userQueryParam" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "message" : "string",
  "queryResult" : {
    "list" : [ "object" ],
    "total" : 0,
    "totalPage" : 0
  },
  "success" : true
}
```


<a name="deleteuserusingdelete"></a>
#### 通过ID删除用户
```
DELETE /user/{id}
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Path**|**id**  <br>*必填*|id|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResponseResult](#responseresult)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/user/string
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "message" : "string",
  "success" : true
}
```


<a name="3f094d4af2d9dd81414434b97c9494a1"></a>
### 社团接口
Club Controller Impl


<a name="insertclubusingget"></a>
#### 添加社团
```
GET /club
```


##### 参数

|类型|名称|类型|
|---|---|---|
|**Query**|**createTime**  <br>*可选*|string (date-time)|
|**Query**|**description**  <br>*可选*|string|
|**Query**|**id**  <br>*可选*|string|
|**Query**|**logoUri**  <br>*可选*|string|
|**Query**|**name**  <br>*可选*|string|
|**Query**|**operator**  <br>*可选*|string|
|**Query**|**regulationUri**  <br>*可选*|string|
|**Query**|**state**  <br>*可选*|integer (int32)|
|**Query**|**updateTime**  <br>*可选*|string (date-time)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResponseResult](#responseresult)|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/club
```


###### 请求 query
```
json :
{
  "createTime" : "string",
  "description" : "string",
  "id" : "string",
  "logoUri" : "string",
  "name" : "string",
  "operator" : "string",
  "regulationUri" : "string",
  "state" : 0,
  "updateTime" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "message" : "string",
  "success" : true
}
```


<a name="7d44630033dbde4b227a10b41817c27f"></a>
### 认证接口
Auth Controller Impl


<a name="getopenidbycodeusingpost"></a>
#### 通过code得到openId
```
POST /auth/openid
```


##### 说明
小程序授权之后应该携带code请求此接口，在此接口中携带AppId、AppSecret和code访问微信服务器，微信服务器返回session_key和openId，接口返回openId和cookie给小程序


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**code**  <br>*必填*|code|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[OpenIdResponse](#openidresponse)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/auth/openid
```


###### 请求 body
```
json :
{ }
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "message" : "string",
  "openId" : "string",
  "success" : true
}
```


<a name="setuserinfousingput"></a>
#### 设置用户信息
```
PUT /auth/userinfo
```


##### 说明
用户通过getOpenIdByCode后得到了openid，然后在前端通过getUserInfo接口拿到用户数据，再带着数据和签名请求此接口进行用户信息的录入


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**signature**  <br>*可选*|signature|string|
|**Query**|**userInfoStr**  <br>*可选*|userInfoStr|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[ResponseResult](#responseresult)|
|**201**|Created|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**404**|Not Found|无内容|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### HTTP请求示例

###### 请求 path
```
/auth/userinfo
```


###### 请求 query
```
json :
{
  "signature" : "string",
  "userInfoStr" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "message" : "string",
  "success" : true
}
```




<a name="definitions"></a>
## 定义

<a name="openidresponse"></a>
### OpenIdResponse

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|**样例** : `0`|integer (int32)|
|**message**  <br>*可选*|**样例** : `"string"`|string|
|**openId**  <br>*可选*|**样例** : `"string"`|string|
|**success**  <br>*可选*|**样例** : `true`|boolean|


<a name="queryresponseresult"></a>
### QueryResponseResult

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|**样例** : `0`|integer (int32)|
|**message**  <br>*可选*|**样例** : `"string"`|string|
|**queryResult**  <br>*可选*|**样例** : `"[queryresult](#queryresult)"`|[QueryResult](#queryresult)|
|**success**  <br>*可选*|**样例** : `true`|boolean|


<a name="queryresult"></a>
### QueryResult

|名称|说明|类型|
|---|---|---|
|**list**  <br>*可选*|**样例** : `[ "object" ]`|< object > array|
|**total**  <br>*可选*|**样例** : `0`|integer (int64)|
|**totalPage**  <br>*可选*|**样例** : `0`|integer (int64)|


<a name="responseresult"></a>
### ResponseResult

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|**样例** : `0`|integer (int32)|
|**message**  <br>*可选*|**样例** : `"string"`|string|
|**success**  <br>*可选*|**样例** : `true`|boolean|





