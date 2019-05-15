package com.spring.cloud.service.service.feign;

import com.spring.cloud.service.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "spring-cloud-utils")
public interface ResultService {

    @GetMapping("result/ok")
    Result ok();

    @GetMapping("result/okWithMessage")
    Result okWithMessage(@RequestParam("message") String message);

    @GetMapping("result/okWithData")
    Result okWithData(@RequestParam("data") Object data);

    @GetMapping("result/okWithDataAndMessage")
    Result okWithDataAndMessage(@RequestParam("data") Object data, @RequestParam("message") String message);

    @GetMapping("result/queryErrorWithDataAndMessage")
    Result queryErrorWithDataAndMessage(@RequestParam("data") Object data, @RequestParam("message") String message);

    @GetMapping("result/createErrorWithDataAndMessage")
    Result createErrorWithDataAndMessage(@RequestParam("data") Object data, @RequestParam("message") String message);

    @GetMapping("result/updateErrorWithDataAndMessage")
    Result updateErrorWithDataAndMessage(@RequestParam("data") Object data, @RequestParam("message") String message);

    @GetMapping("result/deleteErrorWithDataAndMessage")
    Result deleteErrorWithDataAndMessage(@RequestParam("data") Object data, @RequestParam("message") String message);
}
