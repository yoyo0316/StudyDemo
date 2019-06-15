package com.github.yoyozhangh.studydemo.eventbus;

class EventBean {

    private String onel;
    private String two;

    public EventBean(String onel, String two) {
        this.onel = onel;
        this.two = two;
    }

    public String getOnel() {
        return onel;
    }

    public void setOnel(String onel) {
        this.onel = onel;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    @Override
    public String toString() {
        return "EventBean{" +
                "onel='" + onel + '\'' +
                ", two='" + two + '\'' +
                '}';
    }
}
