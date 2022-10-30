package com.cracowdragons.userhack.repository;

import com.cracowdragons.userhack.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device,Long> {
    Optional<Device>findDeviceByOwnerId(Long ownerId);
}
