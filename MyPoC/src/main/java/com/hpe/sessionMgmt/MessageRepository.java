package com.hpe.sessionMgmt;

import java.util.List;

import com.hpe.sessionMgmt.vo.Message;
import com.hpe.sessionMgmt.vo.Messages;

/**
 * Created by Nomeswaran on 19-01-2018.
 */
public interface MessageRepository
{

    void push(String deviceUniqueIdentifier, Message message);

    void pushBack(String deviceUniqueIdentifier, Message message);

    Message pop(String deviceUniqueIdentifier);

    List<Message> getAll(String deviceUniqueIdentifier);

    void delete(String deviceUniqueIdentifier);

    Messages fetch(String deviceUniqueIdentifier, long start, long end);

}
