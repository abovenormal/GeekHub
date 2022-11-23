package com.Geekhub.ChatServer.constant;

public class KafkaConstants {
    // 생성한 토픽의 이름, 나중에 생성할 토픽과 이름 일치해야함
    public static final String KAFKA_TOPIC = "chat";
    // consumer를 식별할 수 있는 그룹
    public static final String GROUP_ID = "test";
    // kafka에 연결하기 위한 포트값
    public static final String KAFKA_BROKER = "k7c205.p.ssafy.io:9092";
}
