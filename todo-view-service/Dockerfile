ARG baseImageVersion
FROM eventuateio/eventuate-examples-docker-images-spring-example-base-image:$baseImageVersion
CMD java ${JAVA_OPTS} -jar todo-view-service.jar
COPY build/libs/todo-view-service.jar .
