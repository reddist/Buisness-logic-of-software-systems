package io.reddist.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.reddist.serializers.JobApplySerializer;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;

@Data
@Entity
@Table(name = "job_applies")
@JsonSerialize(using = JobApplySerializer.class)
public class JobApply {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_applies_seq_gen")
    @SequenceGenerator(name = "job_applies_seq_gen", sequenceName = "job_applies_id_seq")
    @Id
    private long jobApplyId;

    @ManyToOne(optional = false, cascade = CascadeType.ALL) @JoinColumn(name = "user_id") private User user;

    @ManyToOne(optional = false, cascade = CascadeType.ALL) @JoinColumn(name = "vacancy_id") private Vacancy vacancy;

    @ManyToOne(optional = false, cascade = CascadeType.ALL) @JoinColumn(name = "cv_id") private CV cv;

    public JobApply() {}

    public JobApply(@Nullable User user, @Nullable Vacancy vacancy, @Nullable CV cv) {
        this.user = user;
        this.vacancy = vacancy;
        this.cv = cv;
    }
}
