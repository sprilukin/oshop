package oshop.services;

import org.springframework.transaction.annotation.Transactional;
import oshop.dto.GenericListDto;
import oshop.model.BaseEntity;
import oshop.dto.GenericListDto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public interface GenericService<T extends BaseEntity<ID>, ID extends Serializable> {

    @Transactional(readOnly = false)
    public T add(T entity) throws Exception;

    @Transactional(readOnly = false)
    public T update(T entity, ID id) throws Exception;

    @Transactional(readOnly = false)
    public void remove(ID id) throws Exception;

    public T get(ID id) throws Exception;

    public GenericListDto<T> list(
            Map<String, List<String>> filters, Map<String, List<String>> sorters,
            Integer limit, Integer offset) throws Exception;
}