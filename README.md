初始化版本说明：
opf初衷是写一个基于spring security的权限管理后台demo模块。方便自己以后的项目中使用。如：opf-admin子项目。后来发现网络上现成的东西
太多了。 所以调整为了一个基于springcloud微服务的JWT权限DEMO系统。主要是通过opf-gateway网关做了URL的权限控制过滤。
很多代码都是参考的现有网络上的通用方案。如果您，不小心看到部分代码，是您以前编写的。那么请放心。就是我不小心copy过来的。
如果不允许使用，请给我留言，我马上去删除。
依赖服务说明：  
1、opf依赖redis。用于存放用户token信息
安装redis:   
docker pull redis:latest   
docker run -itd --name redis-test -p 6379:6379 --restart=always redis   
2、opf依赖nacos作为注册发现服务与配置管理中心。演示此demon前，请先安装nacos。   
例如：单机演示nacos可以使用如下命令（单机模式 Derby）：  
git clone https://github.com/nacos-group/nacos-docker.git  
cd nacos-docker  
docker-compose -f example/standalone-derby.yaml up  
启动后通过http://IP:8848/nacos/index.html  账号密码：nacos/nacos  
创建如下配置：  
DataID：opf-admin  
GROUP：DEFAULT_GROUP  
配置格式: yml  
内容为：  
spring:  
    datasource:   
        type: com.zaxxer.hikari.HikariDataSource  
        driver-class-name: com.mysql.cj.jdbc.Driver  
        url: jdbc:mysql://IP:3306/opf-dev?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true  
        username: 账号  
        password: 密码  
以上，完成nacos相关配置。  

opf:主要是由以下几部分组成：  
1.opf-common: 基础和通用类    
2.opf-admin:  用户、角色、资源维护接口。维护用户、角色、资源之间的关系。资源主要是指URL。  表结构信息见：[数据库结构.sql](src/main/resources/sql/数据库结构.sql)  
3.opf-oauth:  认证中心。生成token。并存储token和用户权限资源信息到redis。  
4.opf-gateway: 网关，对外暴露接口服务，并提供URL访问控制能力。  
5.opf-monitor: 实质就是启动了一个spring-boot-admin-starter-server服务。  
以上是初始版本。慢慢会新增其它功能。  

获取token接口:  
psot协议：http://IP:9030(gateway端口)/opf-oauth/login?username=admin&password=admin
根据ID查询用户信息:   
get协议：http://IP:9030(gateway端口)/opf-admin/admin/sysUser/{id} 
headers参数：Authorization=Bearer {token}  
根据用户名查询用户信息，并验证熔断功能：熔断和降级服务，使用的openfeign和Sentinel技术。 
get协议： http://IP:9030(gateway端口)/opf-oauth/getUser/{name}
headers参数：Authorization=Bearer {token}  
举例：http://127.0.0.1:9030/opf-oauth/getUser/admin  多次刷新，会触发QPS限制。正常时返回admin信息。降级时返回liubei信息。  
举例：http://127.0.0.1:9030/opf-oauth/getUser/caocao  后台会抛出一个系统异常，触发熔断。可以通过查看系统日志查看。服务接口并不会出现异常。 
   



