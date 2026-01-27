# 若依需要用jdk8来运行
FROM eclipse-temurin:17-jre
# 解决时区问题
ENV TZ=Asia/Shanghai
# 将jar包添加到容器中
ADD ruoyi-admin/target/ruoyi-admin.jar app.jar
# 运行容器时应该执行的命令
ENTRYPOINT ["java", "-jar", "app.jar"]
