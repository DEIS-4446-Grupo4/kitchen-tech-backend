package com.kitchenapp.kitchentech.ia.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @PostConstruct
    public void init() {
        String accountSid = "ACbdb5393c89ec66ed282a3915908e8455";
        String authToken = "33ca149ca2204b773c3b92b898b863ef";

        Twilio.init(accountSid, authToken);
    }

    public void sendWhatsAppMessage(String to, String message) {
        Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber("whatsapp:+14155238886"),
                message
        ).create();
    }
}
