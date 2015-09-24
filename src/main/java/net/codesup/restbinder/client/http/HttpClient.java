package net.codesup.restbinder.client.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import net.codesup.restbinder.client.RestBinderException;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class HttpClient {

	public HttpClient() {
	}

	public HttpResponse execute(final HttpRequest request) {
		final HttpURLConnection urlConnection = createConnection(request);
		try {
			return new HttpResponse(urlConnection.getURL().toURI(), urlConnection.getInputStream(), urlConnection.getResponseCode(), urlConnection.getResponseMessage(), urlConnection.getHeaderFields());
		} catch (IOException | URISyntaxException e) {
			throw new RestBinderException(e);
		}
	}

	private HttpURLConnection createConnection(final HttpRequest request) {
		try {
			final URL url = request.getUri().toURL();
			final HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setRequestMethod(request.getMethod());
			urlConnection.setUseCaches(request.isUseCaches());
			urlConnection.setChunkedStreamingMode(request.getChunkSize());
			urlConnection.setFixedLengthStreamingMode(request.getContentLength());
			urlConnection.setInstanceFollowRedirects(true);
			urlConnection.setConnectTimeout(request.getConnectTimeout());
			urlConnection.setReadTimeout(request.getReadTimeout());
			urlConnection.setAllowUserInteraction(request.isAllowUserInteraction());
			urlConnection.setDoInput(request.isDoInput());
			urlConnection.setDoOutput(request.isDoOutput());
			for(final Header header : request.getHeaders().values()) {
				urlConnection.addRequestProperty(header.getKey(), header.getValue());
			}
			return urlConnection;
		} catch (final IOException e) {
			throw new RestBinderException(e);
		}
	}
}
