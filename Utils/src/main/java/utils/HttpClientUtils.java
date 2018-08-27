package utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;


/**
 * http工具类
 * Created by 寇含尧 on 2018/6/28.
 */
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 最大线程池
     */
    private int msMaxSize = 6;
    private int msMaxPerRoute = 2;

    private PoolingHttpClientConnectionManager cm;

    static volatile HttpClientUtils httpClientUtils = null;

    private HttpClientUtils() {
        //连接池管理器
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(msMaxSize);
        cm.setDefaultMaxPerRoute(msMaxPerRoute);
    }

    public CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }

    public static HttpClientUtils getInstance() {
        if (httpClientUtils == null) {
            synchronized (HttpClientUtils.class) {
                if (httpClientUtils == null) {
                    httpClientUtils = new HttpClientUtils();
                }
            }
            return httpClientUtils;
        }
        return httpClientUtils;
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public String httpGet(String url) throws Exception {
        return httpGet(url, null);
    }

    /**
     * http get请求
     *
     * @param url
     * @return
     */
    public String httpGet(String url, Map<String, String> headMap) throws Exception {
        String responseContent = null;
        HttpGet httpGet = new HttpGet(url);
        setGetHead(httpGet, headMap);
        CloseableHttpResponse response1 = getHttpClient().execute(httpGet);
        HttpEntity entity = response1.getEntity();
        if (HttpStatus.SC_OK == response1.getStatusLine().getStatusCode()) {
            responseContent = EntityUtils.toString(entity);
        } else {
            logger.error("请求路径=".concat(url).concat(",返回参数状态码=").concat(String.valueOf(response1.getStatusLine().getStatusCode())));
            EntityUtils.consume(entity);
        }
        if (response1 != null) {
            response1.close();
        }
        return responseContent;
    }

    public String httpPost(String url, Map<String, String> paramsMap) throws Exception {
        return httpPost(url, paramsMap, null);
    }

    /**
     * http的post请求
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public String httpPost(String url, Map<String, String> paramsMap,
                           Map<String, String> headMap) throws Exception {
        String responseContent = null;
        HttpPost httpPost = new HttpPost(url);
        setPostHead(httpPost, headMap);
        setPostParams(httpPost, paramsMap);
        CloseableHttpResponse response = getHttpClient().execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            responseContent = EntityUtils.toString(entity, Charset.forName("utf-8"));
        } else {
            logger.error("请求路径=".concat(url).concat(",返回参数状态码=").concat(String.valueOf(response.getStatusLine().getStatusCode())));
            EntityUtils.consume(entity);
        }
        if (response != null) {
            response.close();
        }
        return responseContent;
    }

    /**
     * 设置POST的参数
     *
     * @param httpPost
     * @param paramsMap
     * @throws Exception
     */
    private void setPostParams(HttpPost httpPost, Map<String, String> paramsMap)
            throws Exception {
        if (paramsMap != null && paramsMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<String> keySet = paramsMap.keySet();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, paramsMap.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        }
    }

    /**
     * 设置http的HEAD
     *
     * @param httpPost
     * @param headMap
     */
    private void setPostHead(HttpPost httpPost, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            for (String key : keySet) {
                httpPost.addHeader(key, headMap.get(key));
            }
        }
    }

    /**
     * 设置http的HEAD
     *
     * @param httpGet
     * @param headMap
     */
    private void setGetHead(HttpGet httpGet, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            for (String key : keySet) {
                httpGet.addHeader(key, headMap.get(key));
            }
        }
    }

    /**
     * 将返回结果转化为String
     *
     * @param entity
     * @return
     * @throws Exception
     */
    private String getRespString(HttpEntity entity) throws Exception {
        if (entity == null) {
            return null;
        }
        InputStream is = entity.getContent();
        StringBuffer strBuf = new StringBuffer();
        byte[] buffer = new byte[4096];
        int r = 0;
        while ((r = is.read(buffer)) > 0) {
            strBuf.append(new String(buffer, 0, r, "UTF-8"));
        }
        return strBuf.toString();
    }

    /**
     * 将返回结果转化为String
     *
     * @param response
     * @return
     * @throws Exception
     */
    private String getContentFromResponse(CloseableHttpResponse response) throws IOException {
        String respMsg = null;
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            respMsg = EntityUtils.toString(response.getEntity());
        } else {
            EntityUtils.consume(response.getEntity());
        }
        if (response != null) {
            response.close();
        }
        return respMsg;
    }

    public void close() throws IOException {
        if (httpClientUtils != null) {
            getHttpClient().close();
        }
    }

    /**
     * 通过json方式发送post请求
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public String postJson(String url, String json) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json, "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = getHttpClient().execute(httpPost);
        try {
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                return EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
            } else {
                logger.error("请求路径=".concat(url).concat(",返回参数状态码=").concat(String.valueOf(response.getStatusLine().getStatusCode())));
                return null;
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     *  用于进行Https请求的HttpClient
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault(){
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
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
        return  HttpClients.createDefault();
    }

    /**
     * 通过json方式发送post请求(https)
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String postJsonByHttps(String url, String json) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json, "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = createSSLClientDefault().execute(httpPost);
        try {
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                return EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
            } else {
                logger.error("请求路径=".concat(url).concat(",返回参数状态码=").concat(String.valueOf(response.getStatusLine().getStatusCode())));
                return null;
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * HttpClient Post 二进制/字节流/byte[]
     * https://blog.csdn.net/sidyhe/article/details/51577578
     * https://blog.csdn.net/u013766436/article/details/51095514
     * https://blog.csdn.net/langzi7758521/article/details/51557179
     * @param url
     * @param bytes
     * @param contentType
     * @return
     * @throws IOException
     */
    public byte[] post(String url, byte[] bytes, String contentType) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new ByteArrayEntity(bytes));
        if (contentType != null)
            httpPost.setHeader("Content-type", contentType);
        CloseableHttpResponse httpResponse = getHttpClient().execute(httpPost);
        try {
            HttpEntity entityResponse = httpResponse.getEntity();
            int contentLength = (int) entityResponse.getContentLength();
            if (contentLength <= 0)
                throw new IOException("No response");
            byte[] respBuffer = new byte[contentLength];
            if (entityResponse.getContent().read(respBuffer) != respBuffer.length)
                throw new IOException("Read response buffer error");
            return respBuffer;
        } finally {
            httpResponse.close();
        }
        /*HttpEntity entity = response.getEntity();
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            responseContent = EntityUtils.toString(entity, Charset.forName("utf-8"));
        } else {
            logger.error("请求路径=".concat(url).concat(",返回参数状态码=").concat(String.valueOf(response.getStatusLine().getStatusCode())));
            EntityUtils.consume(entity);
        }
        if (response != null) {
            response.close();
        }
        return responseContent;*/
    }



    public static void main(String[] args) throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("a", "a");
        param.put("b", "b");

        /*Map<String, String> head = new HashMap<String, String>();
        head.put("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        String s = httpClientUtils.getInstance().httpPost("http://127.0.0.1:8080/index", param, head);*/
        byte[] s = httpClientUtils.getInstance().post("http://127.0.0.1:8080/index", JSON.toJSONBytes(param), "application/json; charset=UTF-8");
        System.out.println(new String(s));
    }
}
