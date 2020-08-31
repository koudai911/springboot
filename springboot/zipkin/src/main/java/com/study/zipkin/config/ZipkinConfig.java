package com.study.zipkin.config;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.EmptySpanCollectorMetricsHandler;
import com.github.kristofa.brave.Sampler;
import com.github.kristofa.brave.SpanCollector;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.okhttp.BraveOkHttpRequestResponseInterceptor;
import com.github.kristofa.brave.servlet.BraveServletFilter;
import com.study.zipkin.properties.ZipkinProperties;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.kristofa.brave.Brave.Builder;
import static com.github.kristofa.brave.http.HttpSpanCollector.Config;
import static com.github.kristofa.brave.http.HttpSpanCollector.create;


@Configuration
@EnableConfigurationProperties(ZipkinProperties.class)
public class ZipkinConfig {

    @Autowired
    private ZipkinProperties zipkinProperties;
    /**
     * @Description: span（一次请求信息或者一次链路调用）信息收集器
     * @Param:
     * @return: SpanCollector 控制器
     * @Author: Mr.Yang
     */
    @Bean
    public SpanCollector spanCollector() {
        Config config = Config.builder()
                // 默认false，span在transport之前是否会被gzipped
                .compressionEnabled(zipkinProperties.isCompressionEnabled())
                .connectTimeout(zipkinProperties.getConnectTimeout())
                .flushInterval(zipkinProperties.getFlushInterval())
                .readTimeout(zipkinProperties.getReadTimeout())
                .build();
        return create(zipkinProperties.getUrl(), config, new EmptySpanCollectorMetricsHandler());
    }

    /**
     * @Description: 作为各调用链路，只需要负责将指定格式的数据发送给zipkin
     * @Param:
     * @return:
     * @Author: Mr.Yang
     */
    @Bean
    public Brave brave(SpanCollector spanCollector) {
        //调用服务的名称
        Builder builder = new Builder(zipkinProperties.getServiceName());
        builder.spanCollector(spanCollector);
        //采集率
        builder.traceSampler(Sampler.ALWAYS_SAMPLE);
        return builder.build();
    }


    /**
     * @Description: 设置server的（服务端收到请求和服务端完成处理，并将结果发送给客户端）过滤器
     * @Param:
     * @return: 过滤器
     * @Author: Mr.Yang
     */
    @Bean
    public BraveServletFilter braveServletFilter(Brave brave) {
        BraveServletFilter filter = new BraveServletFilter(brave.serverRequestInterceptor(),
                brave.serverResponseInterceptor(), new DefaultSpanNameProvider());
        return filter;
    }

    /**
     * @Description: 设置client的（发起请求和获取到服务端返回信息）拦截器
     * @Param:
     * @return: OkHttpClient 返回请求实例
     * @Author: Mr.Yang
     */
    @Bean
    public OkHttpClient okHttpClient(Brave brave) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new BraveOkHttpRequestResponseInterceptor(
                        brave.clientRequestInterceptor(),
                        brave.clientResponseInterceptor(),
                        new DefaultSpanNameProvider())).build();
        return httpClient;
    }

}
