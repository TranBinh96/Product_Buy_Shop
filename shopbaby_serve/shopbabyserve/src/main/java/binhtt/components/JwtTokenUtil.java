package binhtt.components;

import binhtt.models.User;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.security.InvalidParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.*;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.secretKey}")
    private String secretKey;


    public String generateToken(User user ) throws Exception{
        Map<String,Object> claims = new HashMap<>();
        claims.put("phoneNumber",user.getPhoneNumber());
       // this.generaSecretKey();
        try{
            String token = builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis()+expiration *1000L))
                    .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                    .compact();
            return  token;
        }catch (Exception e){
            //you can use logger
            throw  new InvalidParameterException( "Cannot create jwts token,error :"+e.getMessage());
        }
    }

    private String generaSecretKey(){
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return  secretKey;
    }

    private Key getSignInKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        //Keys.hmacShaKeyFor(Decoders.BASE64.decode("Tv4/FuvDmXWS+Hl5Hr4HYKSGRIVTZWGjXOMpX/tETCg="))
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractALlClaims(String token){

          return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
    public  <T> T extractClaim(String token, Function<Claims,T> claimsTFunction){
        final Claims claims =  this.extractALlClaims(token);
        return claimsTFunction.apply(claims);
    }

    public  boolean isTokenExpired(String token){
        Date exprirationDate =this.extractClaim(token,Claims::getExpiration);
        return  exprirationDate.before(new Date());
    }

    public  String extractPhoneNumber(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public  boolean validateToken(String token, UserDetails userDetails){
        String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }





}
