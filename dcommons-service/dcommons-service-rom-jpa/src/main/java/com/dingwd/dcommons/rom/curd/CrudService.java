package com.dingwd.dcommons.rom.curd;


import java.io.Serializable;

public interface CrudService<T, ID extends Serializable> extends
        InsertService<T, ID>,
        DeleteService<ID>,
        UpdateService<T, ID>,
        SelectService<T, ID> {
}