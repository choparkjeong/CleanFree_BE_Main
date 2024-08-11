package site.cleanfree.be_main.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientIpAccessor {

    public static String getIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        log.info("X-Forwarded-For: {}", clientIp);
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
            log.info("Proxy-Client-IP: {}", clientIp);
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
            log.info("WL-Proxy-Client-IP: {}", clientIp);
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
            log.info("remoteAddr: {}", clientIp);
        }
        if (clientIp == null) {
            clientIp = "no ip";
        }
        log.info("final clientIp: {}", clientIp);
        return clientIp;
    }

}
