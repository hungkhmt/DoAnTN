package org.example.transactionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNoFoundException extends RuntimeException{
    public ResourceNoFoundException(String resourceName, String field, String value){
        super(String.format("%s không tìm thấy giá trị %s phù hợp với trường %s",resourceName,value,field));
    }
}
