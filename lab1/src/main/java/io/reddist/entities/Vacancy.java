package io.reddist.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "vacancies")
public class Vacancy {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacancies_seq_gen")
    @SequenceGenerator(name = "vacancies_seq_gen", sequenceName = "vacancies_id_seq")
    @Id
    private long vacancyId;

    @OneToMany(mappedBy = "vacancy", fetch = FetchType.EAGER) private List<JobApply> jobApplies;

    private String description;

}
