package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Description 资源
 * @Author ErnestCheng
 * @Date 2017/6/26.
 */
@MyBatisRepository
public interface ResourcesMapper {
    /**
     * 添加资源
     * @param resource
     * @return
     */
    int addResource(Resource resource);

    /**
     * 获得资源
     * @param id
     * @return
     */
    Resource getResource(@Param("id") Integer id);

    /**
     * 获得状态为资源列表
     * @param status
     * @return
     */
    List<Resource> listResources(@Param("status") Integer status);


}
