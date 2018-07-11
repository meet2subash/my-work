package com.hpe.sessionMgmt.vo;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable
{

    private static final long serialVersionUID = 4395907436655019244L;
    private String requestId;
    private String requestPrimitive;
    private Date timestamp;

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    public String getRequestPrimitive()
    {
        return requestPrimitive;
    }

    public void setRequestPrimitive(String requestPrimitive)
    {
        this.requestPrimitive = requestPrimitive;
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }
}
