package com.example.marketmanagement.scheduler;

import com.example.marketmanagement.dao.entity.UserProfileEntity;
import com.example.marketmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: nijataghayev
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

    private final UserService userService;

    @Scheduled(cron = "0 01 00 * * ?")
    public void sendDiscountEmail(){
        List<UserProfileEntity> usersWithBirthdayToday = userService.getUsersWithBirthdayToday();

        for (UserProfileEntity user : usersWithBirthdayToday) {
            String email = user.getCredential().getEmail();
            String name = user.getName();
            userService.sendDiscountEmail(email, name);
        }
    }
}
