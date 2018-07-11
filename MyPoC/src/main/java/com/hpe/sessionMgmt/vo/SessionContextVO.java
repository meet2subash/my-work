/*
* Copyright 2016 Hewlett Packard Enterprise Co. All Rights Reserved.
* An unpublished and CONFIDENTIAL work. Reproduction,
* adaptation, or translation without prior written permission
* is prohibited except as allowed under the copyright laws.
*-----------------------------------------------------------------------------
* Project: dav-1.0
* Package: com.hpe.iot.sessionMgmt.vo
* Source:  SessionContextVO.java
* Author:  G C Madhushree
* Organization: Hewlett Packard Enterprise
* Revision:
* Date: Oct 5, 2016
* Contents:
*-----------------------------------------------------------------------------
*/

package com.hpe.sessionMgmt.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 * @author gcmad
 */
public class SessionContextVO implements Serializable
{

    private static final long serialVersionUID = -284787455151398683L;
    private String id; // sessionID
    private String deviceId;
    private String status;
    private String description;
    private HashMap<String, String> context;
    private HashMap<String, String> content;
    private String channel; // dc instance
    private Timestamp establishedTimestamp;
    private Timestamp closedTimestamp;
    private String uniqueIdentifier;

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId()
    {
        return deviceId;
    }

    /**
     * @param deviceId
     *            the deviceId to set
     */
    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    /**
     * @return the status
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the channel
     */
    public String getChannel()
    {
        return channel;
    }

    /**
     * @param channel
     *            the channel to set
     */
    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    /**
     * @return the content
     */
    public HashMap<String, String> getContent()
    {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(HashMap<String, String> content)
    {
        this.content = content;
    }

    public Timestamp getEstablishedTimestamp()
    {
        return establishedTimestamp;
    }

    public void setEstablishedTimestamp(Timestamp establishedTimestamp)
    {
        this.establishedTimestamp = establishedTimestamp;
    }

    public Timestamp getClosedTimestamp()
    {
        return closedTimestamp;
    }

    public void setClosedTimestamp(Timestamp closedTimestamp)
    {
        this.closedTimestamp = closedTimestamp;
    }

    /**
     * @return the context
     */
    public HashMap<String, String> getContext()
    {
        return context;
    }

    /**
     * @param context
     *            the context to set
     */
    public void setContext(HashMap<String, String> context)
    {
        this.context = context;
    }

    /**
     * @return the uniqueIdentifer
     */
    public String getUniqueIdentifier()
    {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier)
    {
        this.uniqueIdentifier = uniqueIdentifier;
    }

}