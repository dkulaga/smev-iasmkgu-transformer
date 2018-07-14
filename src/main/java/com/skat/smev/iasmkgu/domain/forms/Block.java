package com.skat.smev.iasmkgu.domain.forms;

/**
 * @author daria.kulaga
 * @since 14.07.2018.
 */
public class Block {
    private String id;
    private String queue;
    private String optional;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
