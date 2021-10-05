package io.reddist.services;

import io.reddist.entities.CV;
import io.reddist.repositories.CVRepo;
import io.reddist.repositories.UserRepo;
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
public class CVService {

    private final CVRepo cvRepo;
    private final UserRepo userRepo;
    private final EmailService postman;

    @Autowired
    public CVService (EmailService postman, CVRepo cvRepo, UserRepo userRepo) {
        this.cvRepo = cvRepo;
        this.postman = postman;
        this.userRepo = userRepo;
    }

    @Transactional
    public Optional<CV> add (long user_id, String description, String first_name, String last_name, byte age, String working_experience) {
        CV cv = new CV(description, first_name, last_name, age, working_experience);
        if (validate(cv)) {
            this.save(cv);
            try {
                this.postman.send(userRepo.getByUserId(user_id).getEmail(), "Adding CV", "CV was successfully added to your account");
            } catch (MailSendException e) {
                System.out.println("[ERROR] " + e.getLocalizedMessage());
            }
            return this.get(cv.getCvId());
        }
        return Optional.empty();
    }

    public List<CV> getAll() { return StreamSupport.stream(cvRepo.findAll().spliterator(), false).collect(Collectors.toList()); }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save (CV cv) { this.cvRepo.save(cv); }

    public Optional<CV> get (long cvId) {
        return Optional.ofNullable(
            this.cvRepo.getByCvId(cvId)
        );
    }

    public boolean validate (CV cv) {
        return validate(cv.getDescription(), cv.getFirst_name(), cv.getLast_name(), cv.getAge(), cv.getWorking_experience());
    }

    public boolean validate (String description, String first_name, String last_name, byte age, String working_experience) {
        // validate cv
        return true;
    }
}
