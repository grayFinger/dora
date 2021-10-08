package com.dora.commonservice.config;



import com.dora.commonservice.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class ServerStartup implements ApplicationRunner {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${bezkoder.app.jwtSecret}")
	private  String jwtSecret;

	@Value("${bezkoder.app.jwtExpirationMs}")
	private  int jwtAccessExpirationMs;

	@Value("${bezkoder.app.jwtRefreshExpirationMs}")
	private  int jwtRefreshTokenExpirationMs;

	@Transactional
	@Override
	public void run(ApplicationArguments args) {
		logger.info("============= 初始化JWT参数 =============");
		JwtTokenUtil.initConfig(jwtSecret, jwtAccessExpirationMs,jwtRefreshTokenExpirationMs);
		logger.info("============= 初始化JWT参数完成 =============");
	}
}
