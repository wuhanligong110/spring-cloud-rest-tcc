package com.miget.hxb.repository;

import com.miget.hxb.model.ErrorInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description 异常处理
 * @Author ErnestCheng
 * @Date 2017/6/23.
 */
@Repository
public interface ErrorRepository   extends MongoRepository<ErrorInfo,Long> {

}
