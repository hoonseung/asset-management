package com.sewon.notification.application;

public interface NotificationProducer {


    void sendingNotification(Object message, String routingKey);
}
