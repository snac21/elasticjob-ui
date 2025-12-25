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

package org.apache.shardingsphere.elasticjob.lite.ui.dao.statistics;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.shardingsphere.elasticjob.lite.ui.domain.TaskResultStatistics;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Task result statistics mapper.
 */
@Mapper
public interface TaskResultStatisticsMapper extends BaseMapper<TaskResultStatistics> {
    
    /**
     * Find task result statistics.
     *
     * @param fromTime from date to statistics
     * @param statisticInterval statistic interval
     * @return task result statistics
     */
    @Select("SELECT * FROM TASK_RESULT_STATISTICS WHERE statistic_interval = #{statisticInterval} AND statistics_time >= #{fromTime} ORDER BY id ASC")
    List<TaskResultStatistics> findTaskResultStatistics(Date fromTime, String statisticInterval);
    
    /**
     * Get summed task result statistics.
     *
     * @param fromTime from date to statistics
     * @param statisticInterval statistic interval
     * @return summed task result statistics (map with success_count and failed_count keys)
     */
    @Select("SELECT SUM(success_count) as successCount, SUM(failed_count) as failedCount FROM TASK_RESULT_STATISTICS WHERE statistic_interval = #{statisticInterval} AND statistics_time >= #{fromTime}")
    Map<String, Long> getSummedTaskResultStatistics(Date fromTime, String statisticInterval);
}
