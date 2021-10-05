package io.reddist.repositories;

import io.reddist.entities.JobApply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository public interface JobApplyRepo extends CrudRepository<JobApply, Long> {
    JobApply getAllByVacancy_VacancyId (long vacancyId);
    JobApply getAllByCv_CvId (long cvId);
    JobApply getAllByUser_UserId (long userId);
    JobApply getByVacancy_VacancyIdAndCv_CvIdAndUser_UserId (long vacancyId, long cvId, long userId);
}
