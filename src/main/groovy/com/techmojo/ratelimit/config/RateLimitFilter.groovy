package com.techmojo.ratelimit.config
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.concurrent.ConcurrentHashMap

@Component
@EnableScheduling
class RateLimitFilter extends OncePerRequestFilter{

    static Map<String,Integer> tMap=['tenant1':5,'tenant2':5]
    Map<String,Integer> limitMap= new ConcurrentHashMap<>()

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, java.io.IOException {

        String tenantId=httpServletRequest.getHeader('tenantId')
        Integer limit=tMap[tenantId]
        !limitMap[tenantId] &&  limitMap.put(tenantId,0)
        if(limitMap[tenantId]<limit) {
            limitMap.put(tenantId, limitMap[tenantId]?limitMap[tenantId]+1:1)
            filterChain.doFilter(httpServletRequest,httpServletResponse)}
        else{
            httpServletResponse.setStatus(500)
            httpServletResponse.writer.write('Reached max allowed limit per hour')
        }
    }
    @Scheduled( cron = '* * 1 * * * ')
    public void rateLimitReset(){
        print('Reset done')
        limitMap.clear()
    }
}

