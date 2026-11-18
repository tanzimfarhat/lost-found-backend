
package com.lostfound.controller;

import com.lostfound.entity.Claim;
import com.lostfound.service.ClaimService;

import org.springframework.security.access.prepost.PreAuthorize;
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

    // Track a claim using its tracking code
    @GetMapping("/track/{code}")
    public Claim trackClaim(@PathVariable String code) {
        return claimService.trackClaim(code);
    }

    // Approve a claim (admin only)
    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public Claim approveClaim(@PathVariable Long id) {
        return claimService.approveClaim(id);
    }

    // Reject a claim (admin only)
    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public Claim rejectClaim(@PathVariable Long id) {
        return claimService.rejectClaim(id);
    }
    
}
