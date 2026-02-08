package com.example.JWT_UserNamePasswordFromDB.filter;

import com.example.JWT_UserNamePasswordFromDB.service.CustomUserDetailsService;
import com.example.JWT_UserNamePasswordFromDB.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthFilter
 * ------------------------------------------------------------
 * PURPOSE (Professional):
 * This filter intercepts every incoming HTTP request and tries
 * to convert the JWT token into an authenticated Spring Security user.
 *
 * Until SecurityContext is populated, Spring treats the request
 * as coming from an anonymous user.
 *
 * PURPOSE (Hinglish):
 * Ye class token ko pakad ke user ko "logged-in" banati hai.
 * Agar yaha authentication set nahi hua,
 * to Spring bolega user anonymous hai.
 *
 * FLOW:
 * Request → Token read → Username extract → DB verify →
 * Authentication object create → SecurityContext set →
 * Controller allowed.
 */

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    /**
     * JwtService
     * Token ko decode karega.
     * Username / claims nikalega.
     */
    private final JwtService jwtService;

    /**
     * CustomUserDetailsService
     * Database se real user data laayega.
     * (Token jo bol raha hai, usko DB confirm karega.)
     */
    private final CustomUserDetailsService userDetailsService;


    /**
     * Ye method har request ke liye ek baar run hota hai.
     * Controller tak pahuchne se pehle.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        /**
         * STEP 1 – Authorization header read karo.
         *
         * Expect:
         * Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
         */
        String authHeader = request.getHeader("Authorization");


        /**
         * STEP 2 – Agar header nahi hai ya Bearer format nahi hai,
         * to authentication attempt mat karo.
         *
         * Reason:
         * Kuch APIs public ho sakti hain (login/signup).
         *
         * Hinglish:
         * Token nahi → aage badho → baad me Spring decide karega allow ya deny.
         */
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }


        /**
         * STEP 3 – "Bearer " hata ke sirf JWT nikaal lo.
         */
        String token = authHeader.substring(7);


        /**
         * STEP 4 – Token ke andar se username nikaalo.
         *
         * Important:
         * Abhi ye claim hai, truth nahi.
         * DB se verify karna abhi baaki hai.
         */
        String username = jwtService.extractUsername(token);


        /**
         * STEP 5 – Agar username mila aur user pehle se authenticated nahi hai.
         */
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            /**
             * STEP 6 – DB se user laao.
             *
             * Security principle:
             * Token bol raha hai paras.
             * DB confirm karega paras exist karta hai.
             */
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);


            /**
             * STEP 7 – Spring ke liye authentication object banao.
             *
             * Isme:
             * principal = user
             * credentials = null (already verified via token)
             * authorities = roles
             */
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );


            /**
             * STEP 8 – Request details attach karo.
             * (IP, session etc – useful for audit/logging)
             */
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );


            /**
             * STEP 9 – MOST IMPORTANT 🔥
             *
             * Ab Spring ko officially bol diya:
             * Ye banda authenticated hai.
             *
             * Iske baad:
             * ✔ role check chalega
             * ✔ secured API chalega
             * ✔ principal available hoga
             */
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }


        /**
         * STEP 10 – Ab request ko aage bhej do.
         * Next → authorization → controller.
         */
        filterChain.doFilter(request, response);
    }
}
