package com.lyf.springboot.wx.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class HttpClientUtil {

    public static final int DEFAULE_RETRY = 3;

    public static final int KMAXTOTAL = 2000;

    public static final int MAXPERROUTE = 2000;

    public static CloseableHttpClient buildHttpClient() {
        return buildHttpClient(DEFAULE_RETRY, KMAXTOTAL, MAXPERROUTE);
    }

    /**
     * create http client
     *
     * @param retryNum    重试次数
     * @param maxTotal    最大连接数
     * @param maxPerRoute 每个路由基础的连接数
     * @return
     */
    public static CloseableHttpClient buildHttpClient(Integer retryNum, Integer maxTotal,
                                                      Integer maxPerRoute) {
        final int kRetryNum = retryNum == null ? DEFAULE_RETRY : retryNum;

        HttpRequestRetryHandler retryHandler = (exception, executionCount, context) -> {
            if (executionCount > kRetryNum) {
                // Do not retry if over max retry count
                return false;
            }
            return true;
        };

        final int kMaxTotal = maxTotal == null ? KMAXTOTAL : maxTotal;
        final int kMaxPerRoute = maxPerRoute == null ? MAXPERROUTE : maxPerRoute;

        Registry<ConnectionSocketFactory> r =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.INSTANCE).build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);

        // ConnectionConfig
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(StandardCharsets.UTF_8).build();
        SocketConfig socketConfig =
                SocketConfig.custom().setTcpNoDelay(true).setSoKeepAlive(true).setSoReuseAddress(true)
                        .build();
        cm.setDefaultConnectionConfig(connectionConfig);
        cm.setDefaultSocketConfig(socketConfig);
        // 将最大连接数增加到2000
        cm.setMaxTotal(kMaxTotal);
        // 将每个路由基础的连接增加到2000
        cm.setDefaultMaxPerRoute(kMaxPerRoute);

        return HttpClients.custom().setConnectionManager(cm).setRetryHandler(retryHandler).build();
    }

    public static String getContent(String requestUrl, Map<String, String> header) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String returnValue = null;
        log.debug("request url: {}", requestUrl);
        try {

            HttpGet httpGet = new HttpGet(requestUrl);

            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }

            HttpContext context = HttpClientContext.create();

            response = httpClient.execute(httpGet, context);
            int statusCode = response.getStatusLine().getStatusCode();

            returnValue = new String(EntityUtils.toByteArray(response.getEntity()), StandardCharsets.UTF_8);
            log.debug(returnValue);

            log.debug("status code {}", statusCode);
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("目前提交不了，联系码农一下吧 :)");
            }

        } catch (Exception e) {
            log.error("Http Get Content", e);
        } finally {
            try {
                closeHttpResource(response, httpClient);
            } catch (IOException e) {
                log.warn("Close Http Resource error ", e);
            }
        }
        return returnValue;


    }


    public static String getContent(String requestUrl) {
        return getContent(requestUrl, null);
    }

    public static String getHttpsContent(String url) {
        CloseableHttpResponse response = null;
        String returnValue = null;
        CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
        try {
            HttpGet get = new HttpGet();
            get.setURI(new URI(url));
            response = httpClient.execute(get);
            returnValue = new String(EntityUtils.toByteArray(response.getEntity()), StandardCharsets.UTF_8);
            log.debug(returnValue);
        } catch (Exception e) {
            log.error("Http Get Content", e);
        } finally {
            try {
                closeHttpResource(response, httpClient);
            } catch (IOException e) {
                log.warn("Close Http Resource error ", e);
            }
        }
        return returnValue;
    }


    public static String postHttpContent(String url, Map<String, String> param, Map<String, String> header) {
        CloseableHttpResponse response = null;
        String returnValue = null;
        CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
        try {
            HttpPost post = new HttpPost();
            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }

            List<BasicNameValuePair> nvps = new ArrayList<>();

            Set<String> keySet = param.keySet();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, param.get(key)));
            }

            post.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));

            post.setURI(new URI(url));
            response = httpClient.execute(post);
            returnValue = new String(EntityUtils.toByteArray(response.getEntity()), StandardCharsets.UTF_8);
            log.debug(returnValue);
        } catch (Exception e) {
            log.error("Http Post Content", e);
        } finally {
            try {
                closeHttpResource(response, httpClient);
            } catch (IOException e) {
                log.warn("Close Http Resource error ", e);
            }
        }
        return returnValue;
    }


    public static void closeHttpResource(CloseableHttpResponse response, CloseableHttpClient httpClient)
            throws IOException {
        if (response != null) {
            response.close();
        }

        if (httpClient != null) {
            httpClient.close();
        }
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
                        throws java.security.cert.CertificateException {
                    return false;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("create ssl error");
    }

}
