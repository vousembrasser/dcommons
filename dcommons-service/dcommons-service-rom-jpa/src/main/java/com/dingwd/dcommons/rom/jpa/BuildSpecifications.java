package com.dingwd.dcommons.rom.jpa;

import com.dingwd.rom.service.SearchFilter;

import java.util.ArrayList;
import java.util.List;

public class BuildSpecifications {
    private BuildSpecifications() {
        list = new ArrayList<>();
    }


    private List<SearchFilter> list;

    public static BuildSpecifications build() {
        return new BuildSpecifications();
    }

    public BuildSpecifications add(SearchFilter filter) {
        list.add(filter);
        return this;
    }

    public BuildSpecifications add(String fieldName, SearchFilter.Operator operator, Object value) {
        list.add(new SearchFilter(fieldName, operator, value));
        return this;
    }


    public List<SearchFilter> done() {
        return list;
    }
}
