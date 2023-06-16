FROM openjdk:21-oracle

COPY ./gateway/target/*.jar gateway.jar
COPY ./named_entity/target/*.jar named_entity.jar
COPY ./named_entity_type/target/*.jar named_entity_type.jar

COPY ./raport/target/*.jar raport.jar
COPY ./selected_word/target/*.jar selected_word.jar
COPY ./task/target/*.jar task.jar
COPY ./task_set/target/*.jar task_set.jar

COPY ./text/target/*.jar text.jar
COPY ./text_tag/target/*.jar text_tag.jar
COPY ./user_response/target/*.jar user_response.jar

CMD java -jar gateway.jar & sleep 2; \ 
    java -jar named_entity.jar & sleep 2; \
    java -jar named_entity_type.jar & sleep 2; \
    java -jar raport.jar & sleep 2; \ 
    java -jar selected_word.jar & sleep 2; \
    java -jar task.jar & sleep 2; \
    java -jar task_set.jar & sleep 2; \
    java -jar text.jar & sleep 2; \
    java -jar text_tag.jar & sleep 2; \
    java -jar user_response.jar