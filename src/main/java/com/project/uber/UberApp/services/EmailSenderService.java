package com.project.uber.UberApp.services;

import java.util.List;

public interface EmailSenderService {

    public void sendEmail(String toEmail, String subject, String body);
    public void sendEmail(String[] toEmail, String subject, String body);
}
