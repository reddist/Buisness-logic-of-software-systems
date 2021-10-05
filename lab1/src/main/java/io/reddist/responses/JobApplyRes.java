package io.reddist.responses;

import io.reddist.entities.JobApply;
import lombok.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@Value
@Builder
public class JobApplyRes implements Serializable {

    public static final long serialVersionUID = 4L;
    @Builder.Default public String msg = "";
    @Builder.Default public List<JobApply> job_applies = Collections.emptyList();
}