package gov.nasa.api.servico.cliente;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.apache.http.Header;
 
public class ServicoClienteRest {


	
	
	/**
	 * 
	 * @param url endpoint do servidor (REST) com JSON
	 * @param isHTTPS true caso seja um endpoint https, false caso seje http
	 * @param jsonString JSON como parametros
	 * @param headers pra ser adicionado na requisição
	 * @param timeout da requisição
	 * @return json
	 * @throws Exception
	 */
	public static String GET(String url, boolean isHTTPS , Map <String,String> params,Map <String,String> headers, int timeout) throws Exception {
		try {	
			CloseableHttpClient client = criarCloseableHttpClient(isHTTPS,timeout);
			HttpGet httpGet = null;
			if(params != null && !params.isEmpty()) {

				URIBuilder builder = new URIBuilder();
		
				builder.setPath(url);
				
				for(Map.Entry<String, String> param : params.entrySet()) {
					builder.setParameter(param.getKey(), param.getValue());
				}
			
				URI uri = builder.build();
				httpGet = new HttpGet(uri);
			
			} else {
				httpGet = new HttpGet(url);
			}
			
	
			httpGet.setHeader("Accept", "application/json");
			httpGet.setHeader("Content-type", "application/json;charset=utf-8");
			if(headers != null) {
			    for(Map.Entry<String, String> header : headers.entrySet()) {
			    	httpGet.setHeader(header.getKey(),header.getValue());
			    }		    
		    }
			
			HttpResponse response = client.execute(httpGet);
			response.setHeader("Content-type", "application/json;charset=utf-8");
	
			if(response.getStatusLine().getStatusCode() == 200) {
				
				BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent()), StandardCharsets.UTF_8));
				String output;
				StringBuilder jsonBuilder = new StringBuilder();
				while ((output = br.readLine()) != null) {
					jsonBuilder.append(output);
				}
				return jsonBuilder.toString();
			} else if (response.getStatusLine().getStatusCode() == 404) {
				throw new HttpResponseException(response.getStatusLine().getStatusCode(),"página ou (enpoint) : " + url +" , não encontrado");
			} else {
				return new JSONObject("{\"erro Causa:\": " + response.getStatusLine().getStatusCode() + "}").toString() ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	private static CloseableHttpClient criarCloseableHttpClient(boolean isHTTPS,int timeout) throws Exception {
		try {
			CloseableHttpClient client = null;

			if(isHTTPS) {	
				SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

					public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						return true;
					}
				}).build();



				client = HttpClients.custom()
						.setSslcontext(sslContext)
						.setConnectionTimeToLive(timeout, TimeUnit.SECONDS)
						.setSSLHostnameVerifier(new NoopHostnameVerifier())
						.build();

			} else {
				client = HttpClients.custom()
						.setConnectionTimeToLive(timeout, TimeUnit.SECONDS)
						.build();
			}

			return client;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	
	
}