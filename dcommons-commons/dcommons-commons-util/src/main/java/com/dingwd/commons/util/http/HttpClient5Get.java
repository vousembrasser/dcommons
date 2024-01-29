package com.dingwd.commons.util.http;

import com.dingwd.commons.constant.exceptions.DHttpException;
import com.dingwd.commons.constant.messages.DErrorMessage;
import com.dingwd.commons.util.jackson.JacksonUtil;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpClient5Get {

    private static final Logger log = LoggerFactory.getLogger(HttpClient5Get.class);

    public static CloseableHttpClient httpClient;

    static {
        // 设置超时时间
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(30000L))
                .setResponseTimeout(Timeout.ofMilliseconds(30000L)).build();
        // 未设置支持ssl
//		httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        // httpclient5经测试不设置支持ssl，也能调用https接口
        try {
            httpClient = HttpClients.custom().setDefaultRequestConfig(config)
                    .setConnectionManager(getHttpClientConnectionManager()).build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            httpClient = HttpClients.createDefault();
            log.error("Exception httpclient5 init httpClient exception=", e);
        }

    }

    private static HttpClientConnectionManager getHttpClientConnectionManager()
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(30000L))
                .setSocketTimeout(Timeout.ofMilliseconds(30000L))
                .build();
        // 设置连接池最大连接数1000,最大并发数200，及支持ssl,tls
        return PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(connectionConfig)
                .setMaxConnTotal(1000).setMaxConnPerRoute(200)
                .setSSLSocketFactory(getSslConnectionSocketFactory()).build();
    }

    /**
     * 支持SSL
     *
     * @return SSLConnectionSocketFactory
     */
    private static SSLConnectionSocketFactory getSslConnectionSocketFactory()
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        return new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        //链接tls版本协议，不指定也可以
//		return new SSLConnectionSocketFactory(sslContext,  new String[] {"TLSv1.1","TLSv1.2","TLSv1.3"},
//				  null, new NoopHostnameVerifier());

    }

    public static DHttpResponse<String> get(String url, Map<String, Object> headers, Map<String, Object> params) {
        return get(url, headers, params, String.class);
    }

    public static <T> DHttpResponse<T> get(String url, Map<String, Object> headers, Map<String, Object> params, Class<T> stringToSomeClassType) {
        log.info("httpclient5 get start url=[{}] headers=[{}] params=[{}]", url, JacksonUtil.toJSONString(headers), JacksonUtil.toJSONString(params));
        String result;
        HttpGet httpGet = new HttpGet(url);

        // 设置header
        if (headers != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (params != null && !params.isEmpty()) {
            // 表单参数
            List<NameValuePair> nvps = new ArrayList<>();
            // GET 请求参数，如果中文出现乱码需要加上URLEncoder.encode
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
            // 增加到请求 URL 中
            try {
                URI uri = new URIBuilder(new URI(url)).addParameters(nvps).build();
                httpGet.setUri(uri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            result = httpClient.execute(httpGet, new BasicHttpClientResponseHandler());
            log.info("httpclient5 get start url=[{}] headers=[{}] params=[{}] result[{}]", url, JacksonUtil.toJSONString(headers), JacksonUtil.toJSONString(params), result);
            if (stringToSomeClassType == null || stringToSomeClassType == String.class) {
                return (DHttpResponse<T>) new DHttpResponse<>(result);
            } else {
                T entity = JacksonUtil.from(result, stringToSomeClassType);
                return new DHttpResponse<>(entity);
            }
        } catch (Exception e) {
            log.error("httpclient5 get start url=[{}] headers=[{}] params=[{}] error:", url, JacksonUtil.toJSONString(headers), JacksonUtil.toJSONString(params), e);
            return new DHttpResponse<>(new DHttpException(new DErrorMessage.MESSAGE(e.getMessage())));
        }
    }

    /**
     * post form请求
     *
     * @param url    url
     * @param params 参数
     * @return String
     */
    public static <T> DHttpResponse<T> postForm(String url, Map<String, String> headers, Map<String, Object> params, Class<T> stringToSomeClassType) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置header
        if (headers != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                httpPost.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        // 表单参数
        List<NameValuePair> nvps = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }
        //注意编码为UTF-8,否则中文会出现乱码
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));

        try {
            result = httpClient.execute(httpPost, new BasicHttpClientResponseHandler());
            log.info("httpclient5 postForm end url=[{}] headers=[{}] params=[{}] result=[{}]", url, headers, params, result);
            if (stringToSomeClassType == null || stringToSomeClassType == String.class) {
                return (DHttpResponse<T>) new DHttpResponse<>(result);
            } else {
                T entity = JacksonUtil.from(result, stringToSomeClassType);
                return new DHttpResponse<>(entity);
            }
        } catch (Exception e) {
            log.error("Exception httpclient5 postForm url=[{}] headers=[{}] params=[{}] exception:", url, headers, params, e);
            return new DHttpResponse<>(new DHttpException(new DErrorMessage.MESSAGE(e.getMessage())));
        }
    }

    /**
     * post form请求,返回Response，有些请求需要拿到header
     *
     * @param url
     * @param params
     * @return
     */
    public static <T> DHttpResponse<T> postFormReturnResponse(String url, Map<String, Object> headers,
                                                              Map<String, Object> params, Class<T> stringToSomeClassType) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        // 设置header
        if (headers != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }
        // 表单参数
        List<NameValuePair> nvps = new ArrayList<>();
        // POST 请求参数
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));

        try {
            result = httpClient.execute(httpPost, new BasicHttpClientResponseHandler());
            log.info("httpclient5 postFormReturnResponse end url=[{}] headers=[{}] params=[{}] result=[{}]", url, headers, params, result);
            if (stringToSomeClassType == null || stringToSomeClassType == String.class) {
                return (DHttpResponse<T>) new DHttpResponse<>(result);
            } else {
                T entity = JacksonUtil.from(result, stringToSomeClassType);
                return new DHttpResponse<>(entity);
            }
        } catch (Exception e) {
            log.error("Exception httpclient5 postForm url=[{}] headers=[{}] params=[{}] exception:", url, headers, params, e);
            return new DHttpResponse<>(new DHttpException(new DErrorMessage.MESSAGE(e.getMessage())));
        }
    }

    /**
     * post json请求
     *
     * @param url
     * @param jsonBody
     * @return
     */
    public static DHttpResponse<String> postJson(String url, String jsonBody) {
        return postJson(url, jsonBody, String.class);
    }

    public static <T> DHttpResponse<T> postJson(String url, String jsonBody, Class<T> stringToSomeClassType) {
        log.info("httpclient5 postJson start url=[{}] jsonBody=[{}]", url, jsonBody);
        String result;
        DHttpException error;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
            result = httpClient.execute(httpPost, new BasicHttpClientResponseHandler());
            log.info("httpclient5 postJson end url=[{}] jsonBody=[{}] result=[{}]", url, jsonBody, result);
            if (stringToSomeClassType == null || stringToSomeClassType == String.class) {
                return (DHttpResponse<T>) new DHttpResponse<>(result);
            } else {
                //把 result 根据 stringToSomeClassType 参数 转换 成对于的实体类
                T entity = JacksonUtil.from(result, stringToSomeClassType);
                return new DHttpResponse<>(entity);
            }
        } catch (Exception e) {
            log.error("Exception postJson postForm url=[{}], jsonBody=[{}] ,exception=", url, jsonBody, e);
            return new DHttpResponse<>(new DHttpException(new DErrorMessage.MESSAGE(e.getMessage())));
        }
    }

    /**
     * post stream请求(file,json,xml转stream)
     *
     * @param url
     * @param
     * @return
     */
    public static <T> DHttpResponse<T> postStream(String url, String params, ContentType contentType, Class<T> stringToSomeClassType) {
        log.info("httpclient5 postJson end url=[{}] params=[{}]", url, params);
        String result = null;
        final HttpPost httppost = new HttpPost(url);
        InputStream inputStream = new ByteArrayInputStream(new String(params).getBytes());
        final InputStreamEntity reqEntity = new InputStreamEntity(inputStream, -1, contentType);
        // 也可以使用 FileEntity 的形式
        // FileEntity reqEntity = new FileEntity(new File(params),
        // ContentType.APPLICATION_JSON);

        httppost.setEntity(reqEntity);
        try {
            result = httpClient.execute(httppost, new BasicHttpClientResponseHandler());
            log.info("httpclient5 postJson end url=[{}] params=[{}] result=[{}]", url, params, result);
            if (stringToSomeClassType == null || stringToSomeClassType == String.class) {
                return (DHttpResponse<T>) new DHttpResponse<>(result);
            } else {
                //把 result 根据 stringToSomeClassType 参数 转换 成对于的实体类
                T entity = JacksonUtil.from(result, stringToSomeClassType);
                return new DHttpResponse<>(entity);
            }
        } catch (Exception e) {
            log.info("httpclient5 postJson end url=[{}] params=[{}] result=[{}] error:", url, params, e);
            return new DHttpResponse<>(new DHttpException(new DErrorMessage.MESSAGE(e.getMessage())));
        }
    }

    /**
     * getAsync异步请求，回调获取结果
     *
     * @param url
     * @return
     */
    public static void getAsync(String url, FutureCallback<SimpleHttpResponse> futureCallback) {
        try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
            // 开始 http clinet
            httpclient.start();
            // 根据请求响应情况进行回调操作
            SimpleHttpRequest request = SimpleRequestBuilder.get(url).build();

            if (futureCallback == null) {
                futureCallback = new FutureCallback<>() {
                    @Override
                    public void completed(SimpleHttpResponse response) {
                        log.info("getAsync: uri[{}] -> response[{}]", request.getRequestUri(), response.getBodyText());
                    }

                    @Override
                    public void failed(Exception ex) {
                        log.error("getAsync: uri[{}] error:", request.getRequestUri(), ex);
                    }

                    @Override
                    public void cancelled() {
                        log.warn("getAsync: uri[{}] cancelled", request.getRequestUri());
                    }
                };
            }
            httpclient.execute(request, futureCallback);
        } catch (Exception e) {
            throw new DHttpException(new DErrorMessage.MESSAGE(e.getMessage()));
        }
    }


    public static void getAsync(String url) {
        getAsync(url, null);
    }
}
