package com.tongtu.tongtu.init;

import com.tongtu.tongtu.data.DeviceRepository;
import com.tongtu.tongtu.domain.Device;
import com.tongtu.tongtu.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn(value = "userInit")
@AllArgsConstructor
public class DeviceInit {
    private DeviceRepository deviceRepository;
    @Bean
    public CommandLineRunner initDevices(){
        return args -> {
            Device device = new Device();
            device.setUser(new User(1L));
            device.setName("HUAWEI P30");
            device.setAlias("动回的手机");
            deviceRepository.save(device);
        };
    }
}
