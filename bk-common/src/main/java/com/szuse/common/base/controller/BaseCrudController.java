package com.szuse.common.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.szuse.common.base.enums.ResultCode;
import com.szuse.common.base.result.UnwrappedResultVo;
import com.szuse.common.base.service.IBaseCrudService;
import com.szuse.common.base.util.WrapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @author LiuYe
 * @version 1.0
 * @date 4/4/2023 下午9:29
 * Entity 实体类型
 */
@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public class BaseCrudController<Entity> {

    protected final IBaseCrudService baseCrudService;
    protected final String entityName;

    protected final Class<Entity> entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public BaseCrudController(IBaseCrudService IBaseCrudService) {
        this.baseCrudService = IBaseCrudService;
        this.entityName = entityClass.getSimpleName();
    }

    //------------------------------------------------------------------------------------------------------------------
    // basic crud create and update part must use concrete entity type as argument
    // can realize it in child class
    @GetMapping("/byId/{id}")
    public UnwrappedResultVo getById(HttpServletRequest request, @PathVariable Serializable id) {
        log.info("basic_api_info: entity_type={} argument={}", entityName, id);

        Entity data = (Entity) baseCrudService.getById(id);

        if (ObjectUtils.isEmpty(data)) {
            return UnwrappedResultVo
                    .error()
                    .msg("未查询到相应数据")
                    .code(ResultCode.NOT_FOUND);
        }

        return UnwrappedResultVo
                .success()
                .msg("查询详情成功")
                .data(data);
    }

    @DeleteMapping("/byId/{id}")
    public UnwrappedResultVo removeById(HttpServletRequest request, @PathVariable Serializable id) {
        log.info("basic_api_remove: entity_type={} argument={}", entityName, id);

        boolean status = baseCrudService.removeById(id);
        return UnwrappedResultVo
                .success()
                .msg("删除成功")
                .data(status);
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    public UnwrappedResultVo page(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int pageSize, @RequestParam Map<String, Object> params) {
        log.info("basic_api_page: entity_type={} arguments= pageNum={} pageSize={}", entityName, pageNum, pageSize);

        Page<Entity> page = new Page<>(pageNum, pageSize);
        if (params.isEmpty()) {
            log.info("basic_api_page: no wrapper argument");
            page = (Page<Entity>) baseCrudService.page(page);
        } else {
            log.info("basic_api_page: wrapper arguments: {}", params);
            QueryWrapper<Entity> wrapper = new QueryWrapper<>();
            WrapperUtil.allEquals(params, wrapper);
            page = (Page<Entity>) baseCrudService.page(page, wrapper);
        }
        if (ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(page.getRecords())) {
            return UnwrappedResultVo
                    .error()
                    .code(ResultCode.NOT_FOUND)
                    .msg("未查询到数据");
        }

        return UnwrappedResultVo
                .success()
                .msg("分页查询成功")
                .data(page);
    }

    //------------------------------------------------------------------------------------------------------------------
}
