package com.miget.hxb.service;

import com.google.common.base.Preconditions;
import com.miget.hxb.domain.Resource;
import com.miget.hxb.model.vo.RoleResourcesVO;
import com.miget.hxb.persistence.ResourcesMapper;
import com.miget.hxb.persistence.RoleResourcesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description 资源
 * @author ErnestCheng
 * @Date 2017/6/26.
 */
@Service
public class ResourcesService{

    @Autowired
    private ResourcesMapper resourcesMapper;

    @Autowired
    private RoleResourcesMapper roleResourcesMapper;

    public int addResource(Resource resource) {
        Preconditions.checkNotNull(resource);
        resource.setCreateTime(OffsetDateTime.now());
        return resourcesMapper.addResource(resource);
    }

    public List<Resource> listResources(Integer status) {
        Preconditions.checkNotNull(status);
        List<Resource> resourceList = resourcesMapper.listResources(status);
        if (Objects.nonNull(resourceList) && !resourceList.isEmpty()) {
            return resourceList;
        } else {
            return new ArrayList<>();
        }
    }

    public List<String> listRoleByResourceId(Integer id) {
        List<RoleResourcesVO> roleResourcesVOS = roleResourcesMapper.listRoleByResourcesId(id);
        if (Objects.nonNull(roleResourcesVOS) && !roleResourcesVOS.isEmpty()) {
            return roleResourcesVOS.stream().map(roleResourcesVO -> roleResourcesVO.getRoleName()).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
