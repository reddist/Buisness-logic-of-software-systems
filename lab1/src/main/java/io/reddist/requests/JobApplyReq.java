package io.reddist.requests;

import lombok.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data @Builder @Value
public class JobApplyReq implements Serializable {

    public static final long serialVersionUID = 4L;
    @NotNull public int user_id;
    @NotNull public int vacancy_id;
    @NotNull public int cv_id;
}
