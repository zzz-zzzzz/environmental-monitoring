package com.tsu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ZZZ
 * @create 2020/9/19/15:41
 */


//进一步包装HttpClient
@Service
public class HttpClientService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;

    public String doGet(String url) throws Exception {
        return this.doGet(url, null);
    }

    public String doGet(String url, Map<String, Object> paramMap) throws Exception {
        return this.doGet(url, null, paramMap);
    }

    public String doGet(String url, Map<String, Object> headerMap, Map<String, Object> paramMap) throws IOException, URISyntaxException {
        HttpGet httpGet = null;
        if (paramMap != null) {
            URIBuilder uriBuilder = new URIBuilder(url);
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
            httpGet = new HttpGet(uriBuilder.build().toString());
        } else {
            httpGet = new HttpGet(url);
        }
        if (headerMap != null) {
            for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        httpGet.setConfig(config);
        return EntityUtils.toString(this.httpClient.execute(httpGet).getEntity());
    }


    public String doPost(String url, Map<String, Object> contentMap) throws Exception {
        return this.doPost(url, null, contentMap);
    }

    public String doPost(String url) throws Exception {
        return this.doPost(url, null);
    }

    public String doPost(String url, Map<String, Object> headerMap, Map<String, Object> contentMap) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);

        if (contentMap != null) {
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            String param = new ObjectMapper().writeValueAsString(contentMap);
            httpPost.setEntity(new StringEntity(param));
        }

        if (headerMap != null) {
            for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        return EntityUtils.toString(this.httpClient.execute(httpPost).getEntity());
    }

}