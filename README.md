# 基于web的校园快递管理系统

## 调试运行步骤
1. 所需环境：JDK1.8、MySQL 5.x.x、Redis、Maven、IDEA
1. MySQL 5.x.x 版本 导入 `./database/pack-prod-2400.sql` SQL文件；
2. 修改 `application.yml` 配置文件中的数据库 `url`、`username`、`password`；
3. 修改 `application.yml` 配置文件中的 Redis 的 `host`、`port`、`password`（Redis 密码可选项）；
4. 如果 `8080` 端口被占用，修改 `application.yml` 配置文件中的 `server.port`；
5. SpringBoot 项目选择 `PackApplication` ，带参数[--jasypt.encryptor.password=${JASYPT_PASSWORD}]进行调试运行；
6. 浏览器输入 `127.0.0.1:8080/pack` 访问 `基于 Web 的校园快递管理系统`；  
学生账号（用户名：2120172171  密码：123456）  
驿站管理员账号1：（用户名：2101   密码：admin）  
驿站管理员账号2：（用户名：2102   密码：admin）  
驿站管理员账号3：（用户名：2103   密码：admin）

## 进度记录
- 2020.12.29<br>
```markdown
1. 实现学生/管理员的登录与登录，学生的注册与忘记密码
2. 实现查询当前驿站/学生所有的快递，并可以分页展示
```
- 2021.01.02<br>
```markdown
1. 实现学生取件（本人取件和他人代取），并同步在相应快递信息上添加签收人
```
- 2021.01.08<br>
```markdown
1. 完成修改密码时的验证码与图片生成
2. 完成修改密码与更新信息
```
- 2021.01.11<br>
```markdown
1. 完成寄件逻辑，包括学生提交寄件表单、确认支付，管理员确认寄件、发出寄件
2. 完成 Aside 部分获取4中状态寄件数量并显示
```
- 2021.01.12<br>
```markdown
1. 实现管理员邮件通知未取快递的收件人
```
- 2021.01.22<br>
```markdown
1. 使用自定义配置文件完成邮件发送邮箱配置
2. 使用 jasypt 对 application.yml 中的配置进行加密
3. 开启 Druid 的 SQL 监控功能
# 加密
$ .\encrypt input=demo-input password=demo-pwd algorithm=PBEWithMD5AndDES
输出结果为：`av8dLw8htTU7k5AQaoMgGzI5KLaj9Glp`
# 解密
$ .\decrypt input="av8dLw8htTU7k5AQaoMgGzI5KLaj9Glp" password="demo-pwd" algorithm=PBEWithMD5AndDES
输出结果为: `demo-input`
```
- 2021.02.19<br>
```markdown
1. 优化代码逻辑，在 NoPick 部分添加学生在根据快递单号取件
2. npm 打包文件放入 `static` 文件夹下
```
- 2021.02.22<br>
```markdown
1. 完成后端数据筛选过滤；优化代码逻辑
```
- 2021.02.23<br>
```markdown
1. 优化代码逻辑；添加单元测试；造取数据测试
2. 优化多参数方法为传入 JSON 字符串再转 Map 解析参数
3. 不同环境不同配置文件，并方便切换
```
- 2021.02.24<br>
```markdown
1. 添加快递单号生成页面；完成快递状态的筛选过滤
2. 添加 swagger 使用，可查询 API；优化代码逻辑
```
- 2021.02.25<br>
```markdown
1. 优化 swagger API；优化请求方法类型；优化代码逻辑
2. 实现 selection 多选处理
```
- 2021.02.26<br>
```markdown
1. 优化 selection 多选处理；添加快递数据导出为 Excel 表格
```
- 2021.03.02<br>
```markdown
1. 优化 Java Bean 构造模式
2. 采用 全字段 LIKE 查询实现搜索
TODO：换用 elasticsearch
```
- 2021.03.03<br>
```markdown
1. 添加日志记录；添加定时任务；优化代码逻辑
```
- 2021.03.03<br>
```markdown
1. 优化日志
```
- 2021.03.07<br>
```markdown
1. 优化代码逻辑；container 高度铺满
```
- 2021.03.08<br>
```markdown
1. 添加 Jasypt 测试类；优化页面布局；优化日志记录
```
- 2021.03.09<br>
```markdown
1. 优化依赖；优化代码逻辑
```
- 2021.03.10<br>
```markdown
1. 优化代码逻辑；添加请求 IP 获取；页面走马灯添加图片
2. 优化日志记录；添加快递单号生成单元测试；优化配置文件
```
- 2021.03.11<br>
```markdown
1. 优化前台表单验证
```
- 2021.03.19<br>
```markdown
1. 添加加载 loading；优化代码结构
```
- 2021.03.28<br>
```markdown
1. 添加取件 Echarts 图使用
2. 添加 live2d 看板娘
```
- 2021.03.29<br>
```markdown
1. 优化路由结构
2. 修复 Echarts 数据更新 BUG
```
- 2021.03.31<br>
```markdown
1. 更换 ORM 为 MyBatis-plus
2. 优化 RequestMapping 路径
3. 优化日志记录
4. 添加自定义错误页面
```
- 2021.04.01<br>
```markdown
1. 优化代码结构
2. 添加 API 文档
3. 优化错误页面
```
- 2021.04.12<br>
```markdown
1. 优化定时任务
2. 优化日志配置
3. 添加自定义 banner
```



## 相关算法
### 学生登录退出的状态保存
### Vue 项目全局变量的存储与管理
### 取件码给予算法
```markdown
1. 如果驿站快递未满且还有取件码未被使用过，则根据未入站前驿站快递总数，使用取件码工具类生成的取件码，给予新入站的快递
2. 如果驿站快递已满，则当有快递被取出时，未有取件码的快递根据最旧入站时间给予其被取出的快递释放的取件码
3. 取件码工具类生成取件码，仅在当前驿站快递未满且最大取件码未被使用时才使用
```



## 注意
SQL 语句: 
```mysql
SELECT count(*) 未取快递数
FROM t_pack
WHERE per_tel = 'xxx'
AND status = 1
OR status = -1;
```
实际执行的是: 
```mysql
SELECT count(*)
FROM t_pack
WHERE per_tel = 'xxx'
AND status = 1;
```
+
```mysql
SELECT count(*)
FROM t_pack
WHERE status = -1;
```
导致结果不正确。

## 打包上线
1. 配置环境变量
JASYPT_PASSWORD=[密钥]
export JASYPT_PASSWORD
2. java -jar [jar包] --server.port=[端口] --jasypt.encryptor.password=${JASYPT_PASSWORD}

## 请求响应类型
produces：它的作用是指定返回值类型，不但可以设置返回值类型还可以设定返回值的字符编码；
consumes： 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;

## elasticsearch
1. 前端 搜索 根据 输入内容 请求 服务器
2. 服务器根据索引请求 elasticsearch 获取数据 返回 前端