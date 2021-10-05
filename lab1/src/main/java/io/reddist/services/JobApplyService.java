package io.reddist.services;

import io.reddist.entities.CV;
import io.reddist.entities.JobApply;
import io.reddist.entities.User;
import io.reddist.entities.Vacancy;
import io.reddist.repositories.CVRepo;
import io.reddist.repositories.JobApplyRepo;
import io.reddist.repositories.UserRepo;
import io.reddist.repositories.VacancyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JobApplyService {

    private final JobApplyRepo repo;
    private final VacancyRepo vacancyRepo;
    private final UserRepo userRepo;
    private final CVRepo cvRepo;
    private final EmailService postman;

    @Autowired public JobApplyService (JobApplyRepo jobApplyRepo, EmailService postman, VacancyRepo vacancyRepo, UserRepo userRepo, CVRepo cvRepo) {
        this.repo = jobApplyRepo;
        this.postman = postman;
        this.vacancyRepo = vacancyRepo;
        this.userRepo = userRepo;
        this.cvRepo = cvRepo;
    }

    @Transactional public Optional<JobApply> add (long userId, long vacancyId, long cvId) {
        Vacancy vacancy = vacancyRepo.getByVacancyId(vacancyId);
        CV cv = cvRepo.getByCvId(cvId);
        User user = userRepo.getByUserId(userId);
        JobApply jobApply = new JobApply(user, vacancy, cv);
        this.save(jobApply);
        try {
            // send notification or email for vacancy owner
        } catch (MailSendException e) {
            System.out.println("[ERROR] " + e.getLocalizedMessage());
        }
        return this.get(userId, vacancyId, cvId);
    }

    public List<JobApply> getAll() { return StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList()); }


    public Optional<JobApply> get (long userId, long vacancyId, long cvId) {
        return Optional.ofNullable(
                this.repo.getByVacancy_VacancyIdAndCv_CvIdAndUser_UserId(vacancyId, cvId, userId)
        );
    }

    @Transactional (propagation = Propagation.REQUIRES_NEW)
    public void save (JobApply j) { this.repo.save(j); }

    public boolean validate(long user_id, long vacancy_id, long cv_id) {
        return vacancyRepo.getByVacancyId(vacancy_id) != null &&
                cvRepo.getByCvId(cv_id) != null &&
                userRepo.getByUserId(user_id) != null;
    }
}
