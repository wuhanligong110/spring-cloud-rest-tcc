package com.miget.hxb.persistence;

import com.miget.hxb.MyBatisRepository;
import com.miget.hxb.domain.RoleResource;
import com.miget.hxb.model.vo.RoleResourcesVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Description:${Description}
 * @Author : Mr.Cheng
 * @Date:2017/6/24
 */
@MyBatisRepository
public interface RoleResourcesMapper {
    /**
     * 添加角色资源
     * @param roleResource
     * @return
     */
    int addRoleResource(@Param("RoleResource") RoleResource roleResource);

    /**
     * 添加角色资源列表
     * @param roleResourceList
     * @return
     */
    int addRoleResources(@Param("RoleResources") List<RoleResource> roleResourceList);


    /**
     * 获得角色资源
     * @param roleId
     * @return
     */
    RoleResourcesVO getRoleResourcesVO(@Param("roleId") Integer roleId);

    /**
     *获得角色通过资源id
     * @return
     */
    List<RoleResourcesVO> listRoleByResourcesId(@Param("id") Integer id);
}
