package com.hpe.sessionMgmt;

import java.util.Map;

import com.hpe.sessionMgmt.vo.Device;

public interface DeviceRepository
{

    void saveOrUpdate(Device device);

    Device find(String uniqueIdentifier);

    Map<Object, Object> findAll();

    void delete(String uniqueIdentifier);
}
