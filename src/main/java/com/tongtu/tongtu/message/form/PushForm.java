package com.tongtu.tongtu.message.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushForm {

    private String platform;
    private Audience audience;
    private Notification notification;

    public PushForm(String platform,String registration,String alert){
        this.platform = platform;
        List<String> registration_id =  new ArrayList<>(1);
        registration_id.add(registration);
        audience = new Audience(registration_id);
        notification = new Notification(alert);
    }


}
