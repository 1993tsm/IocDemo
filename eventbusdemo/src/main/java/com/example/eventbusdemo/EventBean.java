package com.example.eventbusdemo;


/**
 * 定义事件发送的 封装类型
 */
public class EventBean {

    private String name;
    private String address;

    public EventBean(String name, String address) {
        this.name = name;
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "EventBean{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
