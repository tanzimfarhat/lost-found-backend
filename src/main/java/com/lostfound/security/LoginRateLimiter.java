package com.lostfound.security;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IP-based login rate limiter with lockout mechanism.
 *
 * Rules:
 *  - Up to 5 attempts allowed.
 *  - On 5 consecutive failures, the IP is locked out for 60 seconds (1 minute).
 *  - Successful login immediately resets the failure counter.
 */
@Component
public class LoginRateLimiter {

    public static final int MAX_ATTEMPTS = 5;            // Max attempts before lock
    public static final long LOCK_DURATION_MS = 60_000L; // 60 seconds lockout duration

    private static class AttemptInfo {
        int attempts;
        long lockedUntil;

        AttemptInfo(int attempts, long lockedUntil) {
            this.attempts = attempts;
            this.lockedUntil = lockedUntil;
        }
    }

    private final ConcurrentHashMap<String, AttemptInfo> cache = new ConcurrentHashMap<>();

    /**
     * Checks if IP is currently locked out.
     * Returns remaining lockout seconds (> 0 if locked, 0 if not locked).
     */
    public long getRemainingLockSeconds(String ip) {
        AttemptInfo info = cache.get(ip);
        if (info == null) return 0;
        long now = System.currentTimeMillis();
        if (info.lockedUntil > now) {
            return (info.lockedUntil - now + 999) / 1000;
        }
        // Lock expired
        if (info.lockedUntil > 0 && info.lockedUntil <= now) {
            cache.remove(ip);
            return 0;
        }
        return 0;
    }

    /**
     * Record a failed attempt.
     */
    public AttemptResult recordFailure(String ip) {
        long now = System.currentTimeMillis();
        AttemptInfo info = cache.compute(ip, (k, v) -> {
            if (v == null || (v.lockedUntil > 0 && v.lockedUntil <= now)) {
                return new AttemptInfo(1, 0);
            }
            int newAttempts = v.attempts + 1;
            long lockedUntil = 0;
            if (newAttempts >= MAX_ATTEMPTS) {
                lockedUntil = now + LOCK_DURATION_MS;
            }
            return new AttemptInfo(newAttempts, lockedUntil);
        });

        if (info.lockedUntil > now) {
            long remainingSec = (info.lockedUntil - now + 999) / 1000;
            return new AttemptResult(true, 0, remainingSec);
        } else {
            int remainingAttempts = Math.max(0, MAX_ATTEMPTS - info.attempts);
            return new AttemptResult(false, remainingAttempts, 0);
        }
    }

    /** Reset counter on successful login */
    public void recordSuccess(String ip) {
        cache.remove(ip);
    }

    public static class AttemptResult {
        public final boolean isLocked;
        public final int remainingAttempts;
        public final long lockSeconds;

        public AttemptResult(boolean isLocked, int remainingAttempts, long lockSeconds) {
            this.isLocked = isLocked;
            this.remainingAttempts = remainingAttempts;
            this.lockSeconds = lockSeconds;
        }
    }
}
