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

package org.apache.shardingsphere.elasticjob.lite.ui.dao.search;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.shardingsphere.elasticjob.lite.ui.domain.JobExecutionLog;

import java.util.List;

@Mapper
public interface JobExecutionLogMapper extends BaseMapper<JobExecutionLog> {
    
    /**
     * Find all job names with specific prefix.
     *
     * @param prefix job name prefix
     * @return matched job names
     */
    @Select("SELECT DISTINCT job_name FROM JOB_EXECUTION_LOG WHERE job_name LIKE CONCAT(#{prefix}, '%')")
    List<String> findJobNameByJobNameLike(String prefix);
    
    /**
     * Find all IP addresses with specific prefix.
     *
     * @param prefix ip prefix
     * @return matched ip
     */
    @Select("SELECT DISTINCT ip FROM JOB_EXECUTION_LOG WHERE ip LIKE CONCAT(#{prefix}, '%')")
    List<String> findIpByIpLike(String prefix);
}
