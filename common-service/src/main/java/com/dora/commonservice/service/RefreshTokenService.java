//package com.dora.commonservice.service;
//
//import com.dora.commonservice.entity.RefreshToken;
//import com.dora.commonservice.service.userService.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.time.Instant;
//import java.util.Optional;
//import java.util.UUID;
//
//public class RefreshTokenService {
//    @Value("${bezkoder.app.jwtRefreshExpirationMs}")
//    private Integer refreshTokenDurationMs;
//
//    @Autowired
//    private RedisService redisService;
//
//    @Autowired
//    private UserService userService;
//
//    public Optional<RefreshToken> findByToken(String token) {
//
//        return Optional.of((RefreshToken) redisService.get("refresh_token"+token));
//    }
//
//    /**
//     * 创建一个refresh_token 并存入redis
//     * @param userId
//     * @return
//     */
//    public RefreshToken createRefreshToken(Long userId) {
//        RefreshToken refreshToken = new RefreshToken();
//
//        refreshToken.setUser(userService.getUserById(userId));
//        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
//        refreshToken.setToken(UUID.randomUUID().toString());
//
//        redisService.put("refresh_token_" + userId,refreshToken,refreshTokenDurationMs);
//        return refreshToken;
//    }
//
//    public RefreshToken verifyExpiration(RefreshToken token) {
//        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
//            redisService.(token);
//            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
//        }
//
//        return token;
//    }
//
//    @Transactional
//    public int deleteByUserId(Long userId) {
//        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
//    }
//}
