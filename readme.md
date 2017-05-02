
## 权限控制子系统

[设计文档](doc/dev/privilege_权限控制设计思路.md)

### 技术
Spring-mvc、Spring-security、Spring-jdbc、Jetty、MySql、Jquery、Boostrap等 <br>
Spring的版本： 4.2.2.RELEASE

### 运行代码

- 执行 doc/change.sql 文件内脚本。
- 在 src/main/resource/config.properties 文件内的修改数据库连接。
- 表t_base_user 插入用户 super， 密码：this, 密码MD5：9e925e9341b490bfd3b4c4ca3b0c1ef2 
- 项目目录运行gradlew eclipse 构建jar包依赖。
- 运行java应用(run as java application)：OlobaMain

### 步骤

1. web.xml配置filterchain。
2. 建立security的xml配置文件security.xml。
3. security.xml加上http元素的配置、authentication-manager元素的配置。可临时指定用户、密码进行登录验证测试。
4. 因为不是用默认的form-login,所以我们在security.xml的http元素上配置认证切入点LoginUrlAuthenticationEntryPoint,指定登陆页面的url。
5. security.xml上定义登录提交的Bean,一个UsernamePasswordAuthenticationFilter，指定监听处理登录提交的url.并指定authenticationManager。相对应的也要定义一个登出的filter.
6. 分别实现我们的successHandler、failureHandler的处理对象。在security.xml修改Bean:UsernamePasswordAuthenticationFilter的successHandler、failureHandler替换为我们的实现。
7. 如果前面用的内存用户进行认证。在此我们需要继续实现数据库的用户、密码认证，这涉及到 UserDetailsService 接口的实现。
8. security.xml的authenticationManager属性ref指定到DaoAuthenticationProvider,DaoAuthenticationProvider 接入UserDetailsService的实现。
9. 如果需要对密码验证进行干预，需要重写DaoAuthenticationProvider的authenticate方法。
10. 增加一个filter, 实现请求的权限校验。并在web.xml注册。通过SecurityContextHolder.getContext().getAuthentication()获取用户的权限信息，进行检查校验。
11. 权限系统设计采用RBAC基于角色的访问控制基。角色可以不与用户关联，在大批量用户有相当权限时才选择采用角色来批量控制。重点是你需要对实际应用权限关系进行强梳理，必要的时候需要强制改变之前的管理权限。权限细粒度控制采用索引位的校验方式("100000000"表示只有一个。

## License

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).