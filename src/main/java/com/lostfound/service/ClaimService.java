
package com.lostfound.service;

import com.lostfound.entity.Claim;
import com.lostfound.repository.ClaimRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    // Submit a new claim
    public Claim createClaim(Claim claim) {

        String code = "LF-" +
                UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        claim.setClaimCode(code);
        claim.setStatus("PENDING");

        return claimRepository.save(claim);
    }

    // Get all claims
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    // Track claim using code
    public Claim trackClaim(String code) {
        return claimRepository.findByClaimCode(code)
                .orElse(null);
    }
}
