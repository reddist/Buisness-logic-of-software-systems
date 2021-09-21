package io.reddist.responses;

import lombok.*;
import io.reddist.entities.User;
import java.io.Serializable;
import java.util.*;

@Data @Value @Builder
public class UserRes implements Serializable {

    public static final long serialVersionUID = 4L;
    @Builder.Default public String token = "";
    @Builder.Default public String msg = "";
    @Builder.Default public List<User> users = Collections.emptyList();
}