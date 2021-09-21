package io.reddist.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "cvs")
public class CV {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cvs_seq_gen")
    @SequenceGenerator(name = "cvs_seq_gen", sequenceName = "cvs_id_seq")
    @Id
    private long cvId;

    @OneToMany(mappedBy = "cv", fetch = FetchType.EAGER) private List<JobApply> jobApplies;

    @ManyToOne(optional = false, cascade = CascadeType.ALL) @JoinColumn(name = "user_id") private User user;

    private String description;
}
