FROM openjdk:17 as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

################################

FROM openjdk:17
MAINTAINER gaoyd <gaoyd@gaoap.com>
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

# JVM_XMS and JVM_XMX configs deprecated for removal in halov1.4.4
ENV JVM_XMS="256m" \
    JVM_XMX="256m" \
    JVM_OPTS="-Xmx256m -Xms256m" \
    TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone

ENTRYPOINT java -Xms${JVM_XMS} -Xmx${JVM_XMX} ${JVM_OPTS} -Djava.security.egd=file:/dev/./urandom org.springframework.boot.loader.JarLauncher



#说明：
#
#TZ： 时区设置，而 Asia/Shanghai 表示使用中国上海时区。
#
#-Djarmode=layertools： 指定构建 Jar 的模式。
#
#extract： 从 Jar 包中提取构建镜像所需的内容。
#
#-from=builder 多级镜像构建中，从上一级镜像复制文件到当前镜像中。