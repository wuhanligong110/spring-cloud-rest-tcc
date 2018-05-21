package com.miget.hxb.controller;

import com.google.common.base.Preconditions;
import com.miget.hxb.domain.SmAdvertisement;
import com.miget.hxb.model.request.AdvRequest;
import com.miget.hxb.model.response.ObjectDataResponse;
import com.miget.hxb.service.SmAdvertisementService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author hxb
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class SmAdvertisementController {

    @Autowired
    private SmAdvertisementService advertisementService;

    @ApiOperation(value = "广告列表", notes = "按商家Id查询对应的广告")
    @RequestMapping(value = "/advs/{businessId}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ObjectDataResponse<List<SmAdvertisement>> addressPageList(@PathVariable Long businessId, @Valid @ModelAttribute AdvRequest request, BindingResult error) {
        Preconditions.checkNotNull(request);
        request.setBusinessId(businessId);
        final List<SmAdvertisement> advsList = advertisementService.queryAdvsPageList(request);
        return new ObjectDataResponse<>(advsList);
    }

}
