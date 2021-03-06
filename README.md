# springboot集成第三方框架

-- 配置基础服务
eureka  9000
config  9998
admin  9001
base-gateway 9999

-- 公共 应该不配置web 
verify-demo  6010
cache   6020

canal   6000
elasticjob  6030
zipkin  6040
minio   6050
seata   6060
food   6070


- [x]  服务启动配置SkyWalking 本地启动的话，需要配置启动参数

例如：
-javaagent:E://install//apache-skywalking-apm-8.4.0//apache-skywalking-apm-bin//agent//skywalking-agent.jar
-Dskywalking.agent.service_name=config -Dskywalking.collector.backend_service=localhost:11800


