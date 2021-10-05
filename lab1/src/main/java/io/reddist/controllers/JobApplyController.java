package io.reddist.controllers;

import io.reddist.entities.JobApply;
import io.reddist.requests.JobApplyReq;
import io.reddist.responses.JobApplyRes;
import io.reddist.security.JWTUtil;
import io.reddist.services.JobApplyService;
import io.reddist.services.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "/job_applies")
public class JobApplyController {

    @Autowired private JobApplyService jobApplyService;
    @Autowired private UserService userService;
    @Autowired private JWTUtil jwtUtil;

    @PutMapping(path = "add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JobApplyRes> login(@Valid @RequestBody JobApplyReq req, HttpServletRequest rawReq) {
        return (userService.get(jwtUtil.decode(rawReq)))
            .map(u -> {
                if (u.getUserId() == req.user_id) {
                    if (jobApplyService.validate(req.user_id, req.vacancy_id, req.cv_id)) {
                        return jobApplyService.get(req.user_id, req.vacancy_id, req.cv_id)
                                .map(j -> new ResponseEntity<>(
                                        JobApplyRes.builder().msg("You already send an apply with this cv").build(),
                                        HttpStatus.CONFLICT)
                                ).orElseGet(() -> {
                                    val j = jobApplyService.add(req.user_id, req.vacancy_id, req.cv_id).get();
                                    val jobAppliesList = new ArrayList<JobApply>();
                                    jobAppliesList.add(j);
                                    return new ResponseEntity<>(
                                            JobApplyRes.builder()
                                                    .job_applies(jobAppliesList)
                                                    .msg("Successfully added job apply")
                                                    .build(),
                                            HttpStatus.CREATED);
                                });
                    } else {
                        return new ResponseEntity<>(
                                JobApplyRes.builder()
                                        .msg("One of specified user_id, cv_id or vacancy_id is incorrect")
                                        .build(),
                                HttpStatus.CONFLICT);
                    }
                } else {
                    return new ResponseEntity<>(
                            JobApplyRes.builder().msg("Wrong session token").build(),
                            HttpStatus.UNAUTHORIZED
                    );
                }
            })
            .orElse(new ResponseEntity<>(
                JobApplyRes.builder().msg("Wrong session token").build(),
                HttpStatus.UNAUTHORIZED
            ));
    }

    @GetMapping(path = "all", produces = "application/json")
    public ResponseEntity<JobApplyRes> getAll() {
        return new ResponseEntity<>(
                JobApplyRes.builder().job_applies(jobApplyService.getAll()).build(),
                HttpStatus.OK);
    }
}
