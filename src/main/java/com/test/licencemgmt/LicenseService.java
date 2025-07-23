package com.test.licencemgmt;

import java.io.IOException;

import org.json.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.test.licencemgmt.models.Product;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class LicenseService {
	
	@Value("${api.url}")
	private String apiUrl;

	public HttpResponse<JsonNode> GenerateToken(String accountId) {
		HttpResponse<JsonNode> res = Unirest.post("https://api.keygen.localhost/v1/accounts/"+accountId+"/tokens")
				  .header("Accept", "application/vnd.api+json")
				  .basicAuth("pola.someshwar@tcs.com", "Pola@1234")
				  .asJson();
				System.out.print(res.getHeaders()+"\n"+res.getBody());
		return res;
	}
	public Response getAllLicenses(String account_id,String token) throws IOException {
		okhttp3.OkHttpClient client = new OkHttpClient().newBuilder().build();
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		Request request = new Request.Builder()
		  .url("https://"+apiUrl+"/v1/accounts/"+account_id+"/licenses")
		  .method("GET",null)
		  .addHeader("Authorization", "Bearer "+token)
		  .addHeader("Content-Type", "application/vnd.api+json")
		  .addHeader("Accept", "application/vnd.api+json")
		  .build();
		Response response = client.newCall(request).execute();
		return response;
	}
	public Response getLicenceById(String id,String accountId,String token) throws IOException {
		okhttp3.OkHttpClient client = new OkHttpClient().newBuilder().build();
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		Request request = new Request.Builder()
		  .url("https://"+apiUrl+"/v1/accounts/"+accountId+"/licenses/"+id)
		  .method("GET",null)
		  .addHeader("Authorization", "Bearer "+token)
		  .addHeader("Content-Type", "application/vnd.api+json")
		  .addHeader("Accept", "application/vnd.api+json")
		  .build();
		Response response = client.newCall(request).execute();
		return response;
	}
	public Response createPolicy(String account_id,String product_id,String token,int duration) throws IOException {		
		okhttp3.OkHttpClient client = new OkHttpClient().newBuilder().build();
		//MediaType mediaType = MediaType.parse("application/json");
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		String s = "    {    \"data\": {\n"
				+ "          \"type\": \"policies\",\n"
				+ "          \"attributes\": {\n"
				+ "            \"name\": \"Basic\",\n"
				+ "			   \"duration\":"+duration+"\n"
				+ "          },\n"
				+ "          \"relationships\": {\n"
				+ "            \"product\": {\n"
				+ "              \"data\": { \"type\": \"product\", \"id\":"+"\""+product_id+"\""+"}\n"
				+ "            }\n"
				+ "          }\n"
				+ "        }}";
		System.out.println(s);
		RequestBody body = RequestBody.create(s,mediaType);
		System.out.print(body.toString());
		Request request = new Request.Builder()
		  .url("https://"+apiUrl+"/v1/accounts/"+account_id+"/policies")
		  .method("POST", body)
		  .addHeader("Authorization", "Bearer "+token)
		  .addHeader("Content-Type", "application/vnd.api+json")
		  .addHeader("Accept", "application/vnd.api+json")
		  .build();
		Response response = client.newCall(request).execute();
		//System.out.print(response.body().string());
		return response;
	}
	public Response createProduct(String account_id,String token,Product product) throws IOException{
		okhttp3.OkHttpClient client = new OkHttpClient().newBuilder().build();
		//MediaType mediaType = MediaType.parse("application/json");
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		String s = "{\n"
				+ "        \"data\": {\n"
				+ "          \"type\": \"products\",\n"
				+ "          \"attributes\": {\n"
				+ "            \"name\": "+"\""+product.getName()+"\""+",\n"
				+ "            \"url\": "+"\""+product.getUrl()+"\""+",\n"
				+ "            \"platforms\": [\"Linux\"]\n"
				+ "          }\n"
				+ "        }\n"
				+ "      }";
		System.out.println(s);
		RequestBody body = RequestBody.create(s,mediaType);
		System.out.print(body.toString());
		Request request = new Request.Builder()
		  .url("https://"+apiUrl+"/v1/accounts/"+account_id+"/products")
		  .method("POST", body)
		  .addHeader("Authorization", "Bearer "+token)
		  .addHeader("Content-Type", "application/vnd.api+json")
		  .addHeader("Accept", "application/vnd.api+json")
		  .build();
		Response response = client.newCall(request).execute();
		//System.out.print(response.body().string());
		return response;
	}
	public Response createLicence(String accountId,String token,String policyId) throws IOException {
		okhttp3.OkHttpClient client = new OkHttpClient().newBuilder().build();
		//MediaType mediaType = MediaType.parse("application/json");
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		String s = "{\n"
				+ "        \"data\": {\n"
				+ "          \"type\": \"licenses\",\n"
				+ "          \"relationships\": {\n"
				+ "            \"policy\": {\n"
				+ "              \"data\": { \"type\": \"policies\", \"id\":"+"\""+policyId+"\""
				+ "            }\n"
				+ "          }\n"
				+ "        }\n"
				+ "      }}";
		System.out.println(s);
		RequestBody body = RequestBody.create(s,mediaType);
		System.out.print(body.toString());
		Request request = new Request.Builder()
		  .url("https://"+apiUrl+"/v1/accounts/"+accountId+"/licenses")
		  .method("POST", body)
		  .addHeader("Authorization", "Bearer "+token)
		  .addHeader("Content-Type", "application/vnd.api+json")
		  .addHeader("Accept", "application/vnd.api+json")
		  .build();
		Response response = client.newCall(request).execute();
		//System.out.print(response.body().string());
		return response;
	}
	public Response validateLicense(String id,String accountId,String token) throws IOException
	{
		okhttp3.OkHttpClient client = new OkHttpClient().newBuilder().build();
		//MediaType mediaType = MediaType.parse("application/json");
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		Request request = new Request.Builder()
		  .url("https://"+apiUrl+"/v1/accounts/"+accountId+"/licenses/"+id+"/actions/validate")
		  .method("POST", RequestBody.create("{}",mediaType))
		  .addHeader("Authorization", "Bearer "+token)
		  .addHeader("Content-Type", "application/vnd.api+json")
		  .addHeader("Accept", "application/vnd.api+json")
		  .build();
		Response response = client.newCall(request).execute();
		//System.out.print(response.body().string());
		return response;
	}
	public Response validateLicenseByKey(String key,String accountId,String token) throws IOException
	{
		okhttp3.OkHttpClient client = new OkHttpClient().newBuilder().build();
		//MediaType mediaType = MediaType.parse("application/json");
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		String s = "{\n"
				+ "        \"meta\": {\n"
				+ "          \"key\": "+"\""+key+"\""+"\n"
				+ "        }\n"
				+ "      }";
		System.out.println(s);
		RequestBody body = RequestBody.create(s,mediaType);
		System.out.print(body.toString());
		Request request = new Request.Builder()
		  .url("https://"+apiUrl+"/v1/accounts/"+accountId+"/licenses/actions/validate-key")
		  .method("POST", body)
		  .addHeader("Authorization", "Bearer "+token)
		  .addHeader("Content-Type", "application/vnd.api+json")
		  .addHeader("Accept", "application/vnd.api+json")
		  .build();
		Response response = client.newCall(request).execute();
		//System.out.print(response.body().string());
		return response;
	}
}
