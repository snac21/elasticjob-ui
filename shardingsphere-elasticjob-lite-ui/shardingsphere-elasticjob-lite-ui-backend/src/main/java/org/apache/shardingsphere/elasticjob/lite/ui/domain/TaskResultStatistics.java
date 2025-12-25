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

package org.apache.shardingsphere.elasticjob.lite.ui.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Task result statistics.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@TableName("TASK_RESULT_STATISTICS")
public class TaskResultStatistics {
    
    @TableId
    private Long id;
    
    private Long successCount;
    
    private Long failedCount;
    
    private String statisticInterval;
    
    private Date statisticsTime;
    
    private Date creationTime = new Date();
    
    public TaskResultStatistics(final Long successCount, final Long failedCount) {
        this.successCount = successCount;
        this.failedCount = failedCount;
    }
    
    public TaskResultStatistics(final Long successCount, final Long failedCount, final String statisticInterval, final Date statisticsTime) {
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.statisticInterval = statisticInterval;
        this.statisticsTime = statisticsTime;
    }
}
