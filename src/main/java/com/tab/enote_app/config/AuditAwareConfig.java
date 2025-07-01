package com.tab.enote_app.config;

import com.tab.enote_app.util.CommonUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareConfig implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor()
    {
        return Optional.of(CommonUtil.getLogUser().getId());
    }
}
