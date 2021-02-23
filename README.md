# 基于web的校园快递管理系统
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

__TODO__
```markdown
待完成：
1. 添加快递单号生成的 API
2. 快递状态的筛选过滤
3. 搜索（已取根据签收人搜索，未取与全部根据收件人搜索，揽收根据寄件人搜索）
4. 添加标签页/面包屑
5. 页面布局优化
6. Vue 项目刷新数据丢失问题

打包：
1. Vue 项目去除大部分 console.log()
2. Vue 项目修改 Constant 的 baseUrl 为空
3. SpringBoot 项目去除所有 `@CrossOrigin` 注解
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
WHERE per_tel = '13305193691'
AND status = 1
OR status = -1;
```
实际执行的是: 
```mysql
SELECT count(*)
FROM t_pack
WHERE per_tel = '13305193691'
AND status = 1;
```
+
```mysql
SELECT count(*)
FROM t_pack
WHERE status = -1;
```
导致结果不正确。