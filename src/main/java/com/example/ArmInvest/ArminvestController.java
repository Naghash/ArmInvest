package com.example.ArmInvest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController()
@RequestMapping("/api")
public class ArminvestController {
    @Autowired
    private InvestorRepository repoInvestor;

    @RequestMapping("/investors")
    private List<Object> makeDTO2 (){

        return repoInvestor.findAll()

                .stream()
                .map(investor -> investor.toDTO1())
                .collect(toList());


    }


    @RequestMapping(path = "/investors", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String userEmail, @RequestParam String password) {

        if (userEmail.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (repoInvestor.findByUserEmail(userEmail) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        repoInvestor.save(new Investor(userEmail, password));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
