package com.dingwd.rom.service.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


public interface PageDefault {

    int page = 0;
    int size = 10;
    Sort sort = null;

    default PageRequest toPageRequest() {
        return new PageRecord(page, size, sort).toPageRequest();
    }
}
