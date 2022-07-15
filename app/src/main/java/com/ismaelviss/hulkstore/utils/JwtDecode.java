package com.ismaelviss.hulkstore.utils;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtDecode {


    public static Claims decode(String secret, String jwt) {
        Key key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt).getBody();

        Log.d("JWT", "Subject: " + claims.getSubject());
        Log.d("JWT", "Issuer: " + claims.getIssuer());
        Log.d("JWT", "Level: " + claims.get("lvl"));
        Log.d("JWT", "Exp: " + claims.getExpiration());
        Log.d("JWT", "name: " + claims.get("name"));
        Log.d("JWT", "email: " + claims.get("email"));

        return claims;
    }
}
