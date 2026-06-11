package com.spendwise.service.UserConfigService;

import com.spendwise.model.UserConfig;
import com.spendwise.repo.UserConfigRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConfigServiceImpl implements UserConfigService {

    private final UserConfigRepo userConfigRepo;

    @Override
    public UserConfig getByUserId(String appUserId) {
        return userConfigRepo.findByAppUserId(appUserId)
                .orElseThrow(() -> new RuntimeException("UserConfig not found for appUserId: " + appUserId));
    }
}
