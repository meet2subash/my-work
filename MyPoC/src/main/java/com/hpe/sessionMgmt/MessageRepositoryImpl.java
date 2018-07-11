package com.hpe.sessionMgmt;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.hpe.sessionMgmt.vo.Message;
import com.hpe.sessionMgmt.vo.Messages;


@Repository
public class MessageRepositoryImpl implements MessageRepository
{

    @Value("${queue.mode.messages.limit:10}")
    private int queueModeMessagesLimit;

    @Autowired
    private RedisTemplate<String, Message> redisMessageTemplate;

    private ListOperations listOperations;

    @PostConstruct
    private void init()
    {
        listOperations = redisMessageTemplate.opsForList();

    }

    @Override
    public void push(String deviceUniqueIdentifier, Message message)
    {
        listOperations.leftPush(deviceUniqueIdentifier, message);
        afterLeftPush(deviceUniqueIdentifier);
        // redisMessageTemplate.boundListOps(deviceUniqueIdentifier).leftPush(message);

    }

    @Override
    public void pushBack(String deviceUniqueIdentifier, Message message)
    {
        listOperations.rightPush(deviceUniqueIdentifier, message);
    }

    @Override
    public Message pop(String deviceUniqueIdentifier)
    {
        // System.out.println("Total messages = "+listOperations.size(deviceUniqueIdentifier));
        return (Message) listOperations.rightPop(deviceUniqueIdentifier);
        // return redisMessageTemplate.boundListOps(deviceUniqueIdentifier).rightPop();
    }

    @Override
    public List<Message> getAll(String deviceUniqueIdentifier)
    {
        return listOperations.range(deviceUniqueIdentifier, 0, -1);
        // return redisMessageTemplate.boundListOps(deviceUniqueIdentifier).range(0,-1);
    }

    @Override
    public Messages fetch(String deviceUniqueIdentifier, long start, long end)
    {

        Messages messages = new Messages();
        messages.setMessages(listOperations.range(deviceUniqueIdentifier, start, (start + end) - 1));
        Object totalRecords = redisMessageTemplate.execute(new RedisCallback<Object>()
        {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException
            {
                return connection.lLen(deviceUniqueIdentifier.getBytes());
            }
        });

        messages.setTotalRecords((Long) totalRecords);
        messages.setLimit(messages.getMessages().size());

        return messages;
    }

    private void afterLeftPush(String deviceUniqueIdentifier)
    {
        Object size = redisMessageTemplate
            .execute((RedisCallback) connection -> connection.lLen(deviceUniqueIdentifier.getBytes()));
        if ((Long) size > queueModeMessagesLimit)
        {
            listOperations.rightPop(deviceUniqueIdentifier);
        }
    }

    @Override
    public void delete(String deviceUniqueIdentifier)
    {
        redisMessageTemplate.delete(deviceUniqueIdentifier);
    }
}
