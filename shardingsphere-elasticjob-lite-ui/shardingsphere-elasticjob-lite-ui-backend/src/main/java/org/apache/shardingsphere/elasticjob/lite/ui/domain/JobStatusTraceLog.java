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
import lombok.Data;
import org.apache.shardingsphere.elasticjob.spi.tracing.event.JobStatusTraceEvent;

import java.util.Date;

@Data
@TableName("JOB_STATUS_TRACE_LOG")
public class JobStatusTraceLog {

    @TableId
    private String id;

    private String jobName;

    private String originalTaskId;

    private String taskId;

    private String slaveId;

    private String executionType;

    private String shardingItem;

    private String state;

    private String message;

    private Date creationTime;

    /**
     * JobStatusTraceLog convert to JobStatusTraceEvent.
     *
     * @return JobStatusTraceEvent entity
     */
    public JobStatusTraceEvent toJobStatusTraceEvent() {
        JobStatusTraceEvent event = new JobStatusTraceEvent(
                id,
                jobName,
                originalTaskId,
                taskId,
                slaveId,
                org.apache.shardingsphere.elasticjob.spi.executor.ExecutionType.valueOf(executionType),
                shardingItem,
                JobStatusTraceEvent.State.valueOf(state),
                message,
                creationTime
        );
        return event;
    }

}
