package com.sandy.securitydemo.demosecurity.jwt;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sandy.securitydemo.demosecurity.security.SecurityUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTTokenUtil {
	
	@Value("${app.jwtSecret}")
	private String secret;
	
	@Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
	
	public String generateToken(Authentication authentication)
	{
		
		
		
		Date date = new Date();
		
		Date termDate = new Date(date.getTime()+jwtExpirationInMs);
		
		String jwtToken = Jwts.builder().setSubject(authentication.getName())
							.setIssuedAt(date)
							.setExpiration(termDate)
							.signWith(SignatureAlgorithm.HS512, secret)
							.compact();
		
		System.out.println("jwtToken:"+jwtToken);
		
		return jwtToken;
		
	}//generateToken()
	
    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }//getUserNameFromJWT()

	
	public boolean validateToken(String authenticateToken)
	{
		
		try
		{
			Jwt jwt =Jwts.parser().setSigningKey(secret).parse(authenticateToken);
			
			System.out.println("validateToken:"+jwt);
			return true;
			
		}//try
		catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
        	  System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
        	  System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
        	  System.out.println("Unsupported JWT token"+ex.getLocalizedMessage());
        } catch (IllegalArgumentException ex) {
        	  System.out.println("JWT claims string is empty.");
        }
		
		return false;		
	}//validateToken()
	


}//JWTTokenUtil


