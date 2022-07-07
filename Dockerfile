FROM openjdk:8

COPY target/venkat-thatukuri-pq-assignment.jar stocks-pq.jar

ENTRYPOINT [ "java","-jar","stocks-pq.jar" ]