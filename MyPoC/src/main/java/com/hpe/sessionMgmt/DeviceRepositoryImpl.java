package com.hpe.sessionMgmt;

import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.hpe.sessionMgmt.vo.Device;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository
{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY = "Device";
    private HashOperations hashOperations;

    @PostConstruct
    private void init()
    {
    	System.out.println("DeviceRepositoryImpl.init()");
        hashOperations = redisTemplate.opsForHash();

    }

    @Override
    public void saveOrUpdate(Device device)
    {

        hashOperations.put(KEY, device.getUniqueIdentifier(), device);
    }

    @Override
    public Device find(String uniqueIdentifier)
    {
        return (Device) hashOperations.get(KEY, uniqueIdentifier);
    }

    @Override
    public Map<Object, Object> findAll()
    {
        return hashOperations.entries(KEY);
    }

    @Override
    public void delete(String uniqueIdentifier)
    {
        hashOperations.delete(KEY, uniqueIdentifier);
    }
    


}
