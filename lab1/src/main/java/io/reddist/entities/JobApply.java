package io.reddist.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "job_applies")
public class JobApply {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_applies_seq_gen")
    @SequenceGenerator(name = "job_applies_seq_gen", sequenceName = "job_applies_id_seq")
    @Id
    private long jobApplyId;

    @ManyToOne(optional = false, cascade = CascadeType.ALL) @JoinColumn(name = "user_id") private User user;

    @ManyToOne(optional = false, cascade = CascadeType.ALL) @JoinColumn(name = "vacancy_id") private Vacancy vacancy;

    @ManyToOne(optional = false, cascade = CascadeType.ALL) @JoinColumn(name = "cv_id") private CV cv;
}
