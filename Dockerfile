#使用 cnetos 作为基础镜像
FROM openjdk:17-jdk-alpine


# 设置工作目录
WORKDIR /app

# 将本地编译好的 JAR 文件复制到容器中的工作目录
COPY /target/Blog-Server-0.0.1-SNAPSHOT.jar /app/

# 安装字体
RUN apk add --no-cache ttf-dejavu

# 定义环境变量，指定文件上传的路径
ENV ARTICLE_UPLOAD_PATH /home/server/files/article/
ENV IMG_UPLOAD_PATH /home/server/files/img/

# 暴露应用程序的端口（如果有需要）
 EXPOSE 8181

# 定义容器启动时执行的命令
CMD ["java", "-jar", "Blog-Server-0.0.1-SNAPSHOT.jar"]
