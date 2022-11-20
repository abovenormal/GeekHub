package com.GeekHub.KafkaServer.service;

import com.GeekHub.KafkaServer.model.Message;
import com.GeekHub.KafkaServer.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository repository;

    public Message getMessage(String driver){
        Message message = new Message();
        List<Message> messages = repository.getMessage(driver);
        message = messages.get(0);
        return message;
    }
    public List<Message> getLogs(String driver,String date){
        String[] cont = date.split("-");
        for (int i=1;i<3;i++){
            System.out.println(cont[i]);
            if(Integer.parseInt(cont[i])<10){
                cont[i] = "0"+cont[i];
            }
        }
        date = cont[0]+"."+cont[1]+"."+cont[2];

        List<Message> messages = repository.getLogs(driver,date);
        return messages;
    }
    public List<Message> getLogs50(String driver,String date){
        String[] cont = date.split("-");
        for (int i=1;i<3;i++){
            if(Integer.parseInt(cont[i])<10){
                cont[i] = "0"+cont[i];
            }
        }
        date = cont[0]+"."+cont[1]+"."+cont[2];

        List<Message> messages = repository.getLogs(driver,date);
        List<Message> LimitMessages = new ArrayList<>();
        if (messages.size()>50){
            for (int i=0;i<50;i++){
                LimitMessages.add(messages.get(i));
            }

            messages = LimitMessages;

        }
        return messages;
    }
}
