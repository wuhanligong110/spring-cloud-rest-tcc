package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.CimProductCategoty;

import java.util.List;

/**
 * @author hxb
 */
@SuppressWarnings("InterfaceNeverImplemented")
@MyBatisRepository
public interface CimProductCategotyMapper extends CrudMapper<CimProductCategoty> {

    /**
     * 所有分类
     * @return 产品分类列表
     */
    List<CimProductCategoty> findAll();

    /**
     * 第一级分类
     * @return 产品分类列表
     */
    List<CimProductCategoty> queryFirstLevel();

    /**
     * 第二级分类
     * @return 产品分类列表
     */
    List<CimProductCategoty> querySecondLevel();

    /**
     * 第三级分类
     * @return 产品分类列表
     */
    List<CimProductCategoty> queryThirdLevel();
}