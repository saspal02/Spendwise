package com.spendwise.service.UserConfigService;

import com.spendwise.model.UserConfig;

public interface UserConfigService {
    UserConfig getByUserId(String appUserId);
}
