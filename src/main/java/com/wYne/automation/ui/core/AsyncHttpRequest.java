package com.wYne.automation.ui.core;

import com.ning.http.client.AsyncCompletionHandlerBase;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.ning.http.client.multipart.FilePart;
import com.ning.http.client.multipart.StringPart;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class AsyncHttpRequest {

	static File file;

	public static void postFileWithParams(String uri, Map<String, String> pairs) throws ExecutionException, InterruptedException {

		final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

		AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.preparePost(uri);
		//builder.setHeader("Content-Type", "multipart/form-data");

		for( String key: pairs.keySet())
		{
			if ( key.equals("userfile"))
			{
				file = new File(pairs.get(key));
				builder.addBodyPart(new FilePart(key, file));
			}
			else
				builder.addBodyPart(new StringPart(key, pairs.get(key)));
		}


		Future responseFuture = asyncHttpClient.executeRequest(builder.build(), new AsyncCompletionHandlerBase() {
			@Override
			public void onThrowable(Throwable t) {
			}

			@Override
			public Response onCompleted(Response response) throws Exception {
				System.out.println(response.getStatusCode());
				System.out.println(response.getResponseBody());
				file.delete();
				return response;
			}
		});

		try {
			Response r = (Response) responseFuture.get();
			asyncHttpClient.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void postParams(String uri, Map<String, String> pairs , Map<String,String> headersMap) {

		final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

		AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.preparePost(uri);

		for( String key: pairs.keySet())
		{
			builder.addFormParam(key, pairs.get(key));
		}

		for(String key: headersMap.keySet()){
			builder.setHeader(key, headersMap.get(key));
		}


		Future responseFuture = asyncHttpClient.executeRequest(builder.build(), new AsyncCompletionHandlerBase() {
			@Override
			public void onThrowable(Throwable t) {
			}

			@Override
			public Response onCompleted(Response response) throws Exception {
				System.out.println(response.getStatusCode());
				System.out.println(response.getResponseBody());
				return response;
			}
		});

		try {
			Response r = (Response) responseFuture.get();
			asyncHttpClient.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
