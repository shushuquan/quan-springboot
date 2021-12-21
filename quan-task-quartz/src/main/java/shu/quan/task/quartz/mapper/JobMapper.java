package shu.quan.task.quartz.mapper;


import org.springframework.stereotype.Component;
import shu.quan.task.quartz.entity.domain.JobAndTrigger;

import java.util.List;

/**
 * <p>
 * Job Mapper
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-26 15:12
 */
@Component
public interface JobMapper {
    /**
     * 查询定时作业和触发器列表
     *
     * @return 定时作业和触发器列表
     */
    List<JobAndTrigger> list();
}
