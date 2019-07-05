##SpringBoot学习-1
##资料
[Spring 文档](https://spring.io/guides)
[Spring Web](https://spring.io/guides/gs/serving-web-content/)
[es](https://elasticsearch.cn/explore)
[github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)
[Bootstrap](https://v3.bootcss.com/getting-started/)
[github OAuth文档](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
[Spring Boot MyBatis](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#using-boot-devtools)
[Spring boot mybatis](www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html)

##工具
[git下载](https://git-scm.com/download)
'''
CREATE CACHED TABLE PUBLIC.USER(
    ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_D8DF2A00_9E08_40B8_B762_D385339E99F0) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_D8DF2A00_9E08_40B8_B762_D385339E99F0,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(50),
    TOKEN CHAR(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
)
'''
