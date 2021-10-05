package io.reddist.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.reddist.serializers.CVSerializer;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@Entity
@Table(name = "cvs")
@JsonSerialize(using = CVSerializer.class)
public class CV {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cvs_seq_gen")
    @SequenceGenerator(name = "cvs_seq_gen", sequenceName = "cvs_id_seq")
    @Id
    private long cvId;

    @OneToMany(mappedBy = "cv", fetch = FetchType.EAGER) private List<JobApply> jobApplies;

    @ManyToOne(optional = false, cascade = CascadeType.ALL) @JoinColumn(name = "user_id") private User user;

    private String description;

    private String first_name;

    private String last_name;

    @Min(0)
    private byte age;

    @Nullable
    private String working_experience;

    public CV() {}

    public CV(String description,String first_name, String last_name, byte age, @Nullable String working_experience) {
        this.description = description;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.working_experience = working_experience;
    }


}
