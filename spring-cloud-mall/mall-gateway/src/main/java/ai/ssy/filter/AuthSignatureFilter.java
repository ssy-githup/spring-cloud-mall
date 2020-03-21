package ai.ssy.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * getaway过滤率器
 */
@Component
public class AuthSignatureFilter implements GlobalFilter, Ordered {

    public final static String ATTRIBUTE_IGNORE_GLOBAL_FILTER = "@ignoreGlobalFilter";

    /**
     * 拦截请求，获取authToken，校验
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //从消息头中获取token，根据业务需要进行设置吧
        String token = exchange.getRequest().getHeaders().getFirst("token");
        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            //获取ip放入请求头
            //这里这样做是为了下游可以从消息头获取到对应参数
            httpHeader.set("USER-AUTHENTICITY-IP", getIpAddress(exchange.getRequest()));
            System.out.println("=========httpHeader="+httpHeader+"=================");
            httpHeader.set("token", token);
        };
        //打印参数的封装map
        Map data = new HashMap();
        // 部分路由不进行token验证
        if (exchange.getAttribute(ATTRIBUTE_IGNORE_GLOBAL_FILTER) != null) {
            System.out.println("IgnoreGlobalFilter:"+exchange.getRequest().getPath().value());
            ServerHttpRequest serverHttpRequest = exchange.getRequest();
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        }

        //token验证
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String method = serverHttpRequest.getMethodValue();

        serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
        String serviceMode = serverHttpRequest.getQueryParams().getFirst("mode");
        data.put("token",token);
        data.put("url",serverHttpRequest.getPath().value());
        data.put("mode","parseToken");
        if("GET".equals(method) || StringUtils.isNotBlank(serviceMode)){
            data.put("serviceMode",serviceMode);
            //模拟某些系连接需要被禁止：比如验证token
            if("forbidn".equals(token)){
                System.out.println("get:"+serverHttpRequest.getPath().value()+"--"+serviceMode);
                System.out.println("==============请====================");
                System.out.println("================求==================");
                System.out.println("=================被=================");
                System.out.println("===================禁===============");
                System.out.println("=====================止=============");
                System.out.println("==================================");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        }else if ("POST".equals(method)) {
           return DataBufferUtils.join(exchange.getRequest().getBody())
                    .flatMap(dataBuffer -> {
                         byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        //==========================================
                        //和get请求一样进行token验证
                        //这里只对get和post请求进行了处理，只是演示使用；不具备生产环境使用，
                        // 请慎重！！！
                        // 请慎重！！！
                        //==========================================
                        DataBufferUtils.release(dataBuffer);
                        Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                            DataBuffer buffer = exchange.getResponse().bufferFactory()
                                    .wrap(bytes);
                            return Mono.just(buffer);
                        });

                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(
                                exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return cachedFlux;
                            }
                        };
                        mutatedRequest = mutatedRequest.mutate().headers(httpHeaders).build();
                        System.out.println("post:"+mutatedRequest.getPath().value()+"--"+data.get("serviceMode"));
                        return chain.filter(exchange.mutate().request(mutatedRequest)
                                .build());
                    });
        }
        return chain.filter(exchange);
    }







    public static Map<String, String> paramToMap(String paramStr) {
        String[] params = paramStr.split("&");
        Map<String, String> resMap = new HashMap<String, String>();
        for (int i = 0; i < params.length; i++) {
            String[] param = params[i].split("=");
            if (param.length >= 2) {
                String key = param[0];
                String value = param[1];
                for (int j = 2; j < param.length; j++) {
                    value += "=" + param[j];
                }
                resMap.put(key, value);
            }
        }
        return resMap;
    }


    public static String getIpAddress(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        return ip;
    }



    @Override
    public int getOrder() {
        return 10;
    }
}