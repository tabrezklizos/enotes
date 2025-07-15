package com.tab.enote_app.event;

import com.tab.enote_app.entity.User;
import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {
    private final User user;
    private final String url;

    public UserRegisteredEvent(Object source, User user, String url) {
        super(source);
        this.user = user;
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public String getUrl() {
        return url;
    }
}
