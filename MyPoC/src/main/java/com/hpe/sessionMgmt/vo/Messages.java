package com.hpe.sessionMgmt.vo;

import java.util.ArrayList;
import java.util.List;

public class Messages
{
    private long totalRecords;
    private long limit;
    private List<Message> messages = new ArrayList<>();

    public void setTotalRecords(long totalRecords)
    {
        this.totalRecords = totalRecords;
    }

    public void setLimit(long limit)
    {
        this.limit = limit;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public long getTotalRecords()
    {
        return totalRecords;
    }

    public long getLimit()
    {
        return limit;
    }

    @Override
    public String toString()
    {
        return "Messages{" + "totalRecords=" + totalRecords + ", size=" + limit + ", messages=" + messages + '}';
    }
}
