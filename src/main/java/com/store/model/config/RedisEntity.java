package com.store.model.config;

import java.util.Map;

public class RedisEntity {

    private String name;
    private String port;
    private String timeout;
    private String password;
    private Map<String,Object> pool;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> getPool() {
        return pool;
    }

    public void setPool(Map<String, Object> pool) {
        this.pool = pool;
    }
}