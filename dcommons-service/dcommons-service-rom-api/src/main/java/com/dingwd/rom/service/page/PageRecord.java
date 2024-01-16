package com.dingwd.rom.service.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

record PageRecord(int page, int size, Sort sort) {

    public PageRequest toPageRequest() {
        if (sort != null) {
            return PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
        }
        return PageRequest.of(page > 0 ? page - 1 : 0, size);
    }
}
