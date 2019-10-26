package com.example.ArmInvest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface  InvestorRepository extends JpaRepository<Investor, String> {

    Investor findByUserEmail(@Param("email") String email);
}
