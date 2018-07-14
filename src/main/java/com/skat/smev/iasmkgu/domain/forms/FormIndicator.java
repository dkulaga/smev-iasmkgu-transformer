package com.skat.smev.iasmkgu.domain.forms;

import java.util.List;

public class FormIndicator {
    private String id;
    private String queue;
    private String viewAs;
    private String blockId;
    private String title;
    private String description;
    private List<FormValue> values;

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

    public String getViewAs() {
        return viewAs;
    }

    public void setViewAs(String viewAs) {
        this.viewAs = viewAs;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FormValue> getValues() {
        return values;
    }

    public void setValues(List<FormValue> values) {
        this.values = values;
    }
}
