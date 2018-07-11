package com.hpe.sessionMgmt.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;

public class Device implements Serializable
{

    private static final long serialVersionUID = 3163907993431627004L;
    private boolean requestReachability;
    private boolean alwaysAvailable;
    private String uniqueIdentifier;
    private Map<String, SessionContextVO> sessionContexts;

    public Device()
    {
        requestReachability = true;
        alwaysAvailable = false;
        sessionContexts = new LinkedHashMap(10)
        {
            /**
             * .
             */
            private static final long serialVersionUID = 1L;

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest)
            {
                return size() > 10;
            }
        };

    }

    public Device(boolean requestReachability, boolean alwaysAvailable, String uniqueIdentifier,
        HashMap<String, SessionContextVO> sessionContexts, Queue<Message> messages)
    {
        this.requestReachability = requestReachability;
        this.alwaysAvailable = alwaysAvailable;
        this.uniqueIdentifier = uniqueIdentifier;
        this.sessionContexts = sessionContexts;
    }

    public boolean isRequestReachability()
    {
        return requestReachability;
    }

    public void setRequestReachability(boolean requestReachability)
    {
        this.requestReachability = requestReachability;
    }

    public boolean isAlwaysAvailable()
    {
        return alwaysAvailable;
    }

    public void setAlwaysAvailable(boolean alwaysAvailable)
    {
        this.alwaysAvailable = alwaysAvailable;
    }

    public String getUniqueIdentifier()
    {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier)
    {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public Map<String, SessionContextVO> getSessionContexts()
    {

        return sessionContexts;
    }

    public void setSessionContexts(Map<String, SessionContextVO> sessionContexts)
    {

        this.sessionContexts = sessionContexts;
    }

}
