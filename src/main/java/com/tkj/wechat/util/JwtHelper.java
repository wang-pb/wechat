package com.tkj.wechat.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public class JwtHelper {
	// 秘钥
	static final String SECRET = "RI}Jt9*L730i!fwU6A3?5]1.aBS#0)EQkXne8944Zj1Pu2=yW7hqgHzdN[Kvr58G";
	// 签名是有谁生成
	static final String ISSUSER = "tkj_wechat_dev_group";
	// 签名的主题
	static final String SUBJECT = "user identify token";
	// 签名的观众
	static final String AUDIENCE = "miniapp user";

	public final Integer EVA_TIME = 24;
	
	public String createToken(Integer userId,Integer isTeacher){
		try {
		    Algorithm algorithm = Algorithm.HMAC256(SECRET);
		    Map<String, Object> map = new HashMap<String, Object>();
		    Date nowDate = new Date();
		    // 过期时间：5小时
		    Date expireDate = getAfterDate(nowDate,0,0,0,EVA_TIME,0,0);
	        map.put("alg", "HS256");
	        map.put("typ", "JWT");
		    String token = JWT.create()
		    	// 设置头部信息 Header
		    	.withHeader(map)
		    	// 设置 载荷 Payload
		    	.withClaim("userId", userId)
				.withClaim("isTeacher", isTeacher)
		        .withIssuer(ISSUSER)
		        .withSubject(SUBJECT)
		        .withAudience(AUDIENCE)
		        // 生成签名的时间 
		        .withIssuedAt(nowDate)
		        // 签名过期的时间 
		        .withExpiresAt(expireDate)
		        // 签名 Signature
		        .sign(algorithm);
		    return token;
		} catch (JWTCreationException exception){
			exception.printStackTrace();
		}
		return null;
	}

	public String createAdminToken(Integer userId){
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			Map<String, Object> map = new HashMap<String, Object>();
			Date nowDate = new Date();
			// 过期时间：5小时
			Date expireDate = getAfterDate(nowDate,0,0,0,EVA_TIME,0,0);
			map.put("alg", "HS256");
			map.put("typ", "JWT");
			String token = JWT.create()
					// 设置头部信息 Header
					.withHeader(map)
					// 设置 载荷 Payload
					.withClaim("userId", userId)
					.withClaim("admin","admin")
					.withIssuer(ISSUSER)
					.withSubject(SUBJECT)
					.withAudience(AUDIENCE)
					// 生成签名的时间
					.withIssuedAt(nowDate)
					// 签名过期的时间
					.withExpiresAt(expireDate)
					// 签名 Signature
					.sign(algorithm);
			return token;
		} catch (JWTCreationException exception){
			exception.printStackTrace();
		}
		return null;
	}

	public Integer verifyTokenAndGetUserId(String token,Integer isTeacher){
		try {
		    Algorithm algorithm = Algorithm.HMAC256(SECRET);
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer(ISSUSER)
		        .build();
		    DecodedJWT jwt = verifier.verify(token);
		    Map<String, Claim> claims = jwt.getClaims();
		    Claim claimId = claims.get("userId");
		    Claim claimTeacher = claims.get("isTeacher");
		    if(isTeacher == StatusCode.IS_TEACHER_STUDENT){
				return claimId.asInt();
			}
		} catch (JWTVerificationException exception){
//			exception.printStackTrace();
		}
		return null;
	}

	public Integer verifyAdminTokenAndGetUserId(String token){
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(ISSUSER)
					.build();
			DecodedJWT jwt = verifier.verify(token);
			Map<String, Claim> claims = jwt.getClaims();
			Claim claimId = claims.get("userId");
			Claim clainAdmin = claims.get("admin");
			if("admin".equals(clainAdmin.asString()))
				return claimId.asInt();
		} catch (JWTVerificationException exception){
//			exception.printStackTrace();
		}
		return null;
	}

	public  Integer verifyTokenAndGetUserId(String token){
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(ISSUSER)
					.build();
			DecodedJWT jwt = verifier.verify(token);
			Map<String, Claim> claims = jwt.getClaims();
			Claim claimId = claims.get("userId");
			return claimId.asInt();
		} catch (JWTVerificationException exception){
//			exception.printStackTrace();
		}
		return null;

	}

	public String renewToken(String token){

		Integer teacher_id = verifyTokenAndGetUserId(token,StatusCode.IS_TEACHER_TEACHER);
		if(null != teacher_id && teacher_id != 0){
			return createToken(teacher_id,StatusCode.IS_TEACHER_TEACHER);
		}
		Integer student_id = verifyTokenAndGetUserId(token,StatusCode.IS_TEACHER_STUDENT);
		if(null != student_id && student_id != 0){
			return createToken(student_id,StatusCode.IS_TEACHER_STUDENT);
		}
		return null;
	}

	
	public  Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second){
		if(date == null){
			date = new Date();
		}
		
		Calendar cal = new GregorianCalendar();
		
		cal.setTime(date);
		if(year != 0){
			cal.add(Calendar.YEAR, year);
		}
		if(month != 0){
			cal.add(Calendar.MONTH, month);
		}
		if(day != 0){
			cal.add(Calendar.DATE, day);
		}
		if(hour != 0){
			cal.add(Calendar.HOUR_OF_DAY, hour);
		}
		if(minute != 0){
			cal.add(Calendar.MINUTE, minute);
		}
		if(second != 0){
			cal.add(Calendar.SECOND, second);
		}
		return cal.getTime();
	}
	
}
