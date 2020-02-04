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

	
	public static final String SUCESSO = "sucesso";
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}



	
	/**
	 * 
	 * @param stringURL do endnpoint
	 * @return  Json do endpoint
	 * @throws IOException
	 * @throws JSONException
	 */
	public static String carregarDadosStringWebServiceREST(String stringURL) throws IOException, JSONException {
		
		URL url = new URL(stringURL);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection ();
      
        uc.connect();
	  
        int code = uc.getResponseCode();
        
        if(code == 200){
        
        	InputStream is = uc.getInputStream();
			try {
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				String jsonText = readAll(rd);
				return jsonText;
			} finally {
				is.close();
			}
        } else {
        	return "";
        }
	}
	
	

	
	public static Node carregarNodeDocumento(String response,String elementByTagName)  throws Exception {
		
		try {
		
			
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
			Reader reader = new InputStreamReader(is, "UTF-8");
			InputSource io = new InputSource(reader);
			io.setEncoding("UTF-8");
			Document document = dbf.newDocumentBuilder().parse(io);
		    

			Element docEle = document.getDocumentElement();

			NodeList nodeTarefas = docEle.getElementsByTagName(elementByTagName);

			Node node = nodeTarefas.item(0);
		
		    return node;
			
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * @param url endpoint do servidor (REST) com JSON
	 * @param isHTTPS true caso seja um endpoint https, false caso seje http
	 * @param  arquivos arquivos para atachar no body
	 * @param  nomeParametroArquivos
	 * @return sucesso caso executou, senãoi retorna mensagem de erro coma  causa
	 * @throws Exception
	 */
	public static String POST(String url, boolean isHTTPS , List <File> arquivos,String nomeParametroArquivos, String nomeParametroJson,String json) throws Exception {
		try {	
			CloseableHttpClient client = criarCloseableHttpClient(isHTTPS);
	
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
					
			for(File arquivo : arquivos) {
				builder.addBinaryBody(nomeParametroArquivos , arquivo );
			}
			
			builder.addTextBody(nomeParametroJson, json ,ContentType.APPLICATION_JSON);
			
			HttpPost httpPost = new HttpPost(url);
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
	
	
			HttpResponse response = client.execute(httpPost);
	
	
			if(response.getStatusLine().getStatusCode() == 200) {
				return SUCESSO;
			} else {
				return "erro Causa: " + response.getStatusLine().getReasonPhrase();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @param url endpoint do servidor (REST) com JSON
	 * @param isHTTPS true caso seja um endpoint https, false caso seje http
	 * @param sadcParametrosBeanJsonUtil parametros adicionados para enviar para endpoint
	 * @return sucesso caso executou, senãoi retorna mensagem de erro coma  causa
	 * @throws Exception
	 */
	public static String POST(String url, boolean isHTTPS , String jsonString,  List<File> arquivos) throws Exception {
		try {	
			CloseableHttpClient client = criarCloseableHttpClient(isHTTPS);
	
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
					
			for(File arquivo : arquivos) {
				builder.addBinaryBody("arquivos" , arquivo );
			}
			
			builder.addTextBody("jsonString", jsonString,ContentType.APPLICATION_JSON);
			
			HttpPost httpPost = new HttpPost(url);
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
	
	
			HttpResponse response = client.execute(httpPost);
	
	
			if(response.getStatusLine().getStatusCode() == 200) {
				return SUCESSO;
			} else {
				return "erro Causa: " + response.getStatusLine().getReasonPhrase();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/**
	 * 
	 * @param url endpoint do servidor (REST) com JSON
	 * @param isHTTPS true caso seja um endpoint https, false caso seje http
	 * @param sadcParametrosBeanJsonUtil parametros adicionados para enviar para endpoint
	 * @return sucesso caso executou, senãoi retorna mensagem de erro coma  causa
	 * @throws Exception
	 */
	public static String POST(String url, boolean isHTTPS , String jsonString,Map <String,String> headers) throws Exception {
		try {	
			CloseableHttpClient client = criarCloseableHttpClient(isHTTPS);
	
			HttpPost httpPost = new HttpPost(url);
			
			StringEntity entity = new StringEntity(jsonString,Charset.forName("UTF-8"));
		    httpPost.setEntity(entity);
		    httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json;charset=utf-8");
		    
		    if(headers != null) {
			    for(Map.Entry<String, String> header : headers.entrySet()) {
			    	httpPost.setHeader(header.getKey(),header.getValue());
			    }		    
		    }
			httpPost.setEntity(entity);
	
	
			CloseableHttpResponse response = client.execute(httpPost);
	
			String jsonRetorno = EntityUtils.toString(response.getEntity());
	
			if(response.getStatusLine().getStatusCode() == 200) {
				return jsonRetorno;
			} else {
				return "erro Causa: " + jsonRetorno;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
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
	public static String POST(String url, boolean isHTTPS , String jsonString,Map <String,String> headers, int timeout) throws Exception {
		try {	
			CloseableHttpClient client = criarCloseableHttpClient(isHTTPS,timeout);
	
			HttpPost httpPost = new HttpPost(url);
			
			StringEntity entity = new StringEntity(jsonString,Charset.forName("UTF-8"));
		    httpPost.setEntity(entity);
		    httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json;charset=utf-8");
		    
		    if(headers != null) {
			    for(Map.Entry<String, String> header : headers.entrySet()) {
			    	httpPost.setHeader(header.getKey(),header.getValue());
			    }		    
		    }
			httpPost.setEntity(entity);
	
	
			CloseableHttpResponse response = client.execute(httpPost);
	
			String jsonRetorno = EntityUtils.toString(response.getEntity());
	
			if(response.getStatusLine().getStatusCode() == 200) {
				return jsonRetorno;
			} else {
				return "erro Causa: " + jsonRetorno;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @param url endpoint do servidor (REST) com JSON
	 * @param isHTTPS true caso seja um endpoint https, false caso seje http
	 * @param jsonString JSON como parametros
	 * @param headers pra ser adicionado na requisição
	 * @param cookies para enviar na requisicao
	 * @param timeout da requisição
	 * @return json
	 * @throws Exception
	 */
	public static String POST(String url, boolean isHTTPS , String jsonString,Map <String,String> headers,Map <String,String> cookies, int timeout) throws Exception {
		try {	
			CloseableHttpClient client = criarCloseableHttpClient(isHTTPS,timeout);
	
			HttpPost httpPost = new HttpPost(url);
			
			if(jsonString != null && !jsonString.equals("")) {
				StringEntity entity = new StringEntity(jsonString,Charset.forName("UTF-8"));
				httpPost.setEntity(entity);
			}
		    httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json;charset=utf-8");
		    
		    if(headers != null) {
			    for(Map.Entry<String, String> header : headers.entrySet()) {
			    	httpPost.setHeader(header.getKey(),header.getValue());
			    }		    
		    }
				
			HttpClientContext context = HttpClientContext.create();

			if(cookies != null) {
			    for(Map.Entry<String, String> cookie : cookies.entrySet()) {
			    	CookieStore cookieStore = new BasicCookieStore();
			    	BasicClientCookie basicCookie = new BasicClientCookie(cookie.getKey(), cookie.getValue());
			    	basicCookie.setDomain(".bb.com.br"); 
			    	basicCookie.setAttribute(ClientCookie.DOMAIN_ATTR, "true");
			    	cookieStore.addCookie(basicCookie);
			    	context.setCookieStore(cookieStore);
			    }
			}
	
			CloseableHttpResponse response = client.execute(httpPost,context);
	
			String jsonRetorno = EntityUtils.toString(response.getEntity());
	
			if(response.getStatusLine().getStatusCode() == 200) {
				return jsonRetorno;
			} else {
				return new JSONObject("{\"erro Causa:\": " + jsonRetorno + "}").toString() ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
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
	public static String POST(String url, boolean isHTTPS , Map <String,String> headers, int timeout) throws Exception {
		try {	
			CloseableHttpClient client = criarCloseableHttpClient(isHTTPS,timeout);
	
			HttpPost httpPost = new HttpPost(url);
			
		    //httpPost.setHeader("Accept", "application/json");
		    //httpPost.setHeader("Content-type", "application/json;charset=utf-8");
		    
		    if(headers != null) {
			    for(Map.Entry<String, String> header : headers.entrySet()) {
			    	httpPost.setHeader(header.getKey(),header.getValue());
			    }		    
		    }
	
	
			CloseableHttpResponse response = client.execute(httpPost);
	
			String jsonRetorno = EntityUtils.toString(response.getEntity());
	
			if(response.getStatusLine().getStatusCode() == 200) {
				return jsonRetorno;
			} else {
				return "erro Causa: " + jsonRetorno;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
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
	public static String PUT(String url, boolean isHTTPS , String jsonString,Map <String,String> headers, int timeout) throws Exception {
		try {	
			CloseableHttpClient client = criarCloseableHttpClient(isHTTPS,timeout);
	
			HttpPut httpPut = new HttpPut(url);
			
			StringEntity entity = new StringEntity(jsonString,Charset.forName("UTF-8"));
			httpPut.setEntity(entity);
			httpPut.setHeader("Accept", "application/json");
			httpPut.setHeader("Content-type", "application/json;charset=utf-8");
		    
		    if(headers != null) {
			    for(Map.Entry<String, String> header : headers.entrySet()) {
			    	httpPut.setHeader(header.getKey(),header.getValue());
			    }		    
		    }
		    httpPut.setEntity(entity);
	
	
			CloseableHttpResponse response = client.execute(httpPut);
	
			String jsonRetorno = EntityUtils.toString(response.getEntity());
	
			if(response.getStatusLine().getStatusCode() == 200) {
				return jsonRetorno;
			} else {
				return "erro Causa: " + jsonRetorno;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
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
				return new JSONObject("{\"erro Causa:\": " + response.getEntity().toString() + "}").toString() ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/**
	 * 
	 * @param url endpoint do servidor (REST) com JSON
	 * @param isHTTPS true caso seja um endpoint https, false caso seje http
	 * @param sadcParametrosBeanJsonUtil parametros adicionados para enviar para endpoint
	 * @return sucesso caso executou, senãoi retorna mensagem de erro coma  causa
	 * @throws Exception
	 */
	public static String POST(String url, boolean isHTTPS , String jsonString,Map <String,String> headers,String headerString, int timeout) throws Exception {
		try {	
			CloseableHttpClient client = criarCloseableHttpClient(isHTTPS,timeout);
	
			HttpPost httpPost = new HttpPost(url);
			
			StringEntity entity = new StringEntity(jsonString,Charset.forName("UTF-8"));
		    httpPost.setEntity(entity);
		    httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json;charset=utf-8");
		    
		    if(headers != null) {
			    for(Map.Entry<String, String> header : headers.entrySet()) {
			    	httpPost.setHeader(header.getKey(),header.getValue());
			    }		    
		    }
			httpPost.setEntity(entity);
	
	
			CloseableHttpResponse response = client.execute(httpPost);
			
			Header header = response.getFirstHeader(headerString);
	
	
			if(response.getStatusLine().getStatusCode() == 200) {
				return header.getValue().trim().replace("Bearer ", "");
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	/**
	 * 
	 * @param url endpoint do servidor (REST)
	 * @param isHTTPS true caso seja um endpoint https, false caso seje http
	 * @param sadcParametrosBeanJsonUtil parametros adicionados para enviar para endpoint
	 * @return sucesso caso executou, senãoi retorna mensagem de erro coma  causa
	 * @throws Exception caso tenha erro na execução
	 */
	public static String POST(String url, boolean isHTTPS , List <Map<String,Object>> mapParametros)
			throws Exception {
		try {	
			CloseableHttpClient client = criarCloseableHttpClient(isHTTPS);


			List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();

			for(Map<String,Object> parametros : mapParametros) {
				for(Map.Entry<String, Object> param : parametros.entrySet()) {

					StringBuilder builderValues = new StringBuilder();	
					if(param.getValue() instanceof Object[]) {
						String virgula = ",";
						int i = 0;
						Object[] values = (Object[]) param.getValue();
						
						for(Object value : values) {
							if(i == values.length - 1) {
								virgula = "";
							}
							builderValues.append(value.toString() + virgula);
							i++;
						}
						nameValuePairs.add(new BasicNameValuePair(param.getKey(), builderValues.toString()));
					} else {
						nameValuePairs.add(new BasicNameValuePair(param.getKey(), (String) param.getValue()));
					}
					
				}
			}



			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded;charset=UTF-8");
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

			HttpResponse response = client.execute(httpPost);

			if(response.getStatusLine().getStatusCode() == 200) {
				return SUCESSO;
			} else {
				return "erro Causa: " + response.getStatusLine().getReasonPhrase();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private static CloseableHttpClient criarCloseableHttpClient(boolean isHTTPS) throws Exception {
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
						.setConnectionTimeToLive(15, TimeUnit.SECONDS)
						.setSSLHostnameVerifier(new NoopHostnameVerifier())
						.build();

			} else {
				client = HttpClients.custom()
						.setConnectionTimeToLive(15, TimeUnit.SECONDS)
						.build();
			}

			return client;
			
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

	
	
	public static List <FileBody> getFilebodies(List <File> arquivos) throws IOException {
		   
		List <FileBody> lista = new ArrayList <FileBody> ();	
		for(File arq : arquivos) {					
			lista.add(new FileBody(arq, ContentType.DEFAULT_BINARY));
		}		 
	    return lista;
	 }

	
}