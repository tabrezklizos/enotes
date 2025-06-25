package com.tab.EnoteApp.config;

import com.tab.EnoteApp.util.CommonUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareConfig implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor()
    {
        return Optional.of(CommonUtil.getLogUser().getId());
    }
}
