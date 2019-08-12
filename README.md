##SpringBoot学习
##资料
[Spring 文档](https://spring.io/guides)  
[Spring 创建初始项目](https://spring.io/guides/gs/serving-web-content/)  
[es 社区](https://elasticsearch.cn/explore)  
[github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)  
[Bootstrap 样式](https://v3.bootcss.com/getting-started/)  
[github OAuth文档](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)  
[Spring Boot Reference Guide Developer Tools 部分](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#using-boot-devtools)  
[MyBatis Spring Boot Autoconfigure](www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html)  
[Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)  
[菜鸟教程 数据库](https://www.runoob.com/mysql/mysql-insert-query.html)  
[Using Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)  
[Spring Web MVC](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)  
[在线 Markdown 编辑器](http://editor.md.ipandao.com/)  
[log](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-logging.html)
##工具
[git下载](https://git-scm.com/download)  
[uml建模](https://www.visual-paradigm.com/cn/)  
[flyway maven](https://flywaydb.org/getstarted/firststeps/maven)  
[lombok](https://www.projectlombok.org/)  
[Markdown](https://pandao.github.io/editor.md/)

```sql
CREATE CACHED TABLE PUBLIC.USER(
    ID INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(50),
    TOKEN CHAR(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
);
```

```
mvn flyway:migrate  
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
