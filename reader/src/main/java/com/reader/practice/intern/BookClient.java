package com.reader.practice.intern;


import com.intern.practice.hw10.dto.BookDetailInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "bookClient", url = "http://localhost:8080/")
public interface BookClient {

    @GetMapping(value = "/book/myBook")
    List<BookDetailInfo> read(@RequestHeader("Authorization") String header);

    @GetMapping(value = "/book/myBook/{id}/text")
    String readText(@PathVariable String id, @RequestHeader("Authorization") String header);
}
