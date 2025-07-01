package com.tab.enote_app.enums;

public enum TodoStatus {

    NOT_STARTED(1,"Not Started"),
    IN_PROGRESS(2,"In PROGRESS"),
    COMPLETED(3,"Completed");

    private Integer id;
    private String name;

    TodoStatus(Integer id, String name) {
        this.id=id;
        this.name=name;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }



}
