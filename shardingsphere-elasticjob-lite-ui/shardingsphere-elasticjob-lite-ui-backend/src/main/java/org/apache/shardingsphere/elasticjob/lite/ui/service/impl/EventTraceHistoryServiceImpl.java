/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.elasticjob.lite.ui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.lite.ui.dao.search.JobExecutionLogMapper;
import org.apache.shardingsphere.elasticjob.lite.ui.dao.search.JobStatusTraceLogMapper;
import org.apache.shardingsphere.elasticjob.lite.ui.domain.JobExecutionLog;
import org.apache.shardingsphere.elasticjob.lite.ui.domain.JobStatusTraceLog;
import org.apache.shardingsphere.elasticjob.lite.ui.dto.request.BasePageRequest;
import org.apache.shardingsphere.elasticjob.lite.ui.dto.request.FindJobExecutionEventsRequest;
import org.apache.shardingsphere.elasticjob.lite.ui.dto.request.FindJobStatusTraceEventsRequest;
import org.apache.shardingsphere.elasticjob.lite.ui.dto.response.BasePageResponse;
import org.apache.shardingsphere.elasticjob.lite.ui.service.EventTraceHistoryService;
import org.apache.shardingsphere.elasticjob.spi.tracing.event.JobExecutionEvent;
import org.apache.shardingsphere.elasticjob.spi.tracing.event.JobStatusTraceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Event trace history service implementation.
 */
@Slf4j
@Component
public final class EventTraceHistoryServiceImpl implements EventTraceHistoryService {
    
    @Autowired
    private JobExecutionLogMapper jobExecutionLogMapper;
    
    @Autowired
    private JobStatusTraceLogMapper jobStatusTraceLogMapper;
    
    @Override
    public BasePageResponse<JobExecutionEvent> findJobExecutionEvents(final FindJobExecutionEventsRequest request) {
        QueryWrapper<JobExecutionLog> queryWrapper = new QueryWrapper<>();
        
        // 添加查询条件
        if (!Strings.isNullOrEmpty(request.getJobName())) {
            queryWrapper.eq("job_name", request.getJobName());
        }
        if (!Strings.isNullOrEmpty(request.getIp())) {
            queryWrapper.eq("ip", request.getIp());
        }
        if (request.getIsSuccess() != null) {
            queryWrapper.eq("is_success", request.getIsSuccess());
        }
        if (request.getStartTimeFrom() != null) {
            queryWrapper.gt("start_time", request.getStartTimeFrom());
        }
        if (request.getStartTimeTo() != null) {
            queryWrapper.lt("start_time", request.getStartTimeTo());
        }
        
        // 排序
        queryWrapper.orderByDesc("start_time");
        
        // 分页查询
        Page<JobExecutionLog> page = new Page<>(
                request.getPageNumber() > 0 ? request.getPageNumber() : 1,
                request.getPageSize() > 0 ? request.getPageSize() : BasePageRequest.DEFAULT_PAGE_SIZE
        );
        
        IPage<JobExecutionLog> result = jobExecutionLogMapper.selectPage(page, queryWrapper);
        
        // 转换为响应对象
        List<JobExecutionEvent> events = result.getRecords().stream()
                .map(JobExecutionLog::toJobExecutionEvent)
                .collect(Collectors.toList());
        
        return BasePageResponse.of(events, (int) result.getTotal(), request.getPageSize(), request.getPageNumber());
    }
    
    @Override
    public List<String> findJobNamesInExecutionLog(final String jobNamePrefix) {
        return jobExecutionLogMapper.findJobNameByJobNameLike(jobNamePrefix);
    }
    
    @Override
    public List<String> findIpInExecutionLog(final String ipPrefix) {
        return jobExecutionLogMapper.findIpByIpLike(ipPrefix);
    }
    
    @Override
    public BasePageResponse<JobStatusTraceEvent> findJobStatusTraceEvents(final FindJobStatusTraceEventsRequest request) {
        QueryWrapper<JobStatusTraceLog> queryWrapper = new QueryWrapper<>();
        
        // 添加查询条件
        if (!Strings.isNullOrEmpty(request.getJobName())) {
            queryWrapper.eq("job_name", request.getJobName());
        }
        if (!Strings.isNullOrEmpty(request.getExecutionType())) {
            queryWrapper.eq("execution_type", request.getExecutionType());
        }
        if (!Strings.isNullOrEmpty(request.getState())) {
            queryWrapper.eq("state", request.getState());
        }
        if (request.getCreationTimeFrom() != null) {
            queryWrapper.gt("creation_time", request.getCreationTimeFrom());
        }
        if (request.getCreationTimeTo() != null) {
            queryWrapper.lt("creation_time", request.getCreationTimeTo());
        }
        
        // 排序
        queryWrapper.orderByDesc("creation_time");
        
        // 分页查询
        Page<JobStatusTraceLog> page = new Page<>(
                request.getPageNumber() > 0 ? request.getPageNumber() : 1,
                request.getPageSize() > 0 ? request.getPageSize() : BasePageRequest.DEFAULT_PAGE_SIZE
        );
        
        IPage<JobStatusTraceLog> result = jobStatusTraceLogMapper.selectPage(page, queryWrapper);
        
        // 转换为响应对象
        List<JobStatusTraceEvent> events = result.getRecords().stream()
                .map(JobStatusTraceLog::toJobStatusTraceEvent)
                .collect(Collectors.toList());
        
        return BasePageResponse.of(events, (int) result.getTotal(), request.getPageSize(), request.getPageNumber());
    }
    
    @Override
    public List<String> findJobNamesInStatusTraceLog(final String jobNamePrefix) {
        return jobStatusTraceLogMapper.findJobNameByJobNameLike(jobNamePrefix);
    }
}
