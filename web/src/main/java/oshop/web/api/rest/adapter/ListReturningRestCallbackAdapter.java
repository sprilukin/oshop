package oshop.web.api.rest.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import oshop.web.dto.GenericListDto;

import java.util.List;

public abstract class ListReturningRestCallbackAdapter<T extends List> extends ReturningRestCallbackAdapter<GenericListDto<T>> {

    @Override
    protected ResponseEntity<GenericListDto<T>> getResponse() throws Exception {
        GenericListDto<T> result = getResult();
        if (result == null || result.getList().isEmpty()) {
            return new ResponseBuilder<GenericListDto<T>>().headers(getHeaders()).status(HttpStatus.NO_CONTENT).build();
        } else {
            return new ResponseBuilder<GenericListDto<T>>().headers(getHeaders()).body(result).status(HttpStatus.OK).build();
        }
    }

    @Override
    protected GenericListDto<T> convertResult(GenericListDto<T> result) throws Exception {
        result.setList(convertList(result.getList()));
        return result;
    }

    @Override
    protected GenericListDto<T> getResult() throws Exception {
        return new GenericListDto<T>(convertList(getList()), getSize());
    }

    protected T convertList(T list) throws Exception {
        return list;
    }

    protected abstract Long getSize() throws Exception;
    protected abstract T getList() throws Exception;
}
