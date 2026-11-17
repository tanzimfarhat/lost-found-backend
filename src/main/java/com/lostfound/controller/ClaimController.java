
package com.lostfound.controller;

import com.lostfound.entity.Claim;
import com.lostfound.service.ClaimService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "http://localhost:5173")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    // Submit a claim
    @PostMapping
    public Claim createClaim(@RequestBody Claim claim) {
        return claimService.createClaim(claim);
    }

    // Get all claims
    @GetMapping
    public List<Claim> getAllClaims() {
        return claimService.getAllClaims();
    }

    // Track a claim using its tracking 
    @GetMapping("/track/{code}")
    public Claim trackClaim(@PathVariable String code) {
        return claimService.trackClaim(code);
    }
}
