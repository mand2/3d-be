package com.wtd.ddd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.wtd.ddd.controller.ApiResult.OK;

/**
 * Created By mand2 on 2020-10-26.
 */
@RestController
@RequestMapping("api/_hcheck")
public class HealthCheckRestController {
    @GetMapping
    public ApiResult<Long> healthCheck() {
        return OK(System.currentTimeMillis());
    }
}
