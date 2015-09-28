package net.codesup.restbinder.client.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import net.codesup.restbinder.client.util.RestBinderException;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class HttpClient {

	public HttpClient() {
	}

	public HttpResponse execute(final HttpRequest request) {
		final HttpURLConnection urlConnection = createConnection(request);
		final URI uri = request.getUri();
		try {
			return new HttpResponse(request, urlConnection.getInputStream(), urlConnection.getResponseCode(), urlConnection.getResponseMessage(), urlConnection.getHeaderFields());
		} catch(final FileNotFoundException e) {
			try {
				return new HttpResponse(request, urlConnection.getErrorStream(), urlConnection.getResponseCode(), urlConnection.getResponseMessage(), urlConnection.getHeaderFields());
			} catch(final IOException ex) {
				throw new RestBinderException(ex);
			}
		} catch (final IOException e) {
			try {
				if (urlConnection.getResponseCode() >= 400) {
					try {
						return new HttpResponse(request, urlConnection.getErrorStream(), urlConnection.getResponseCode(), urlConnection.getResponseMessage(), urlConnection.getHeaderFields());
					} catch (final IOException ex) {
						throw new RestBinderException(ex);
					}
				} else {
					throw new RestBinderException(e);
				}
			} catch(final IOException iox) {
				throw new RestBinderException(iox);
			}
		}
	}

	private HttpURLConnection createConnection(final HttpRequest request) {
		try {
			final URL url = request.getUri().toURL();
			final HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setRequestMethod(request.getMethod());
			urlConnection.setUseCaches(request.isUseCaches());
			if(request.getChunkSize() > -1) {
				urlConnection.setChunkedStreamingMode(request.getChunkSize());
			} else if(request.getContentLength() > -1) {
				urlConnection.setFixedLengthStreamingMode(request.getContentLength());
			}
			urlConnection.setInstanceFollowRedirects(true);
			if(request.getConnectTimeout() > -1) {
				urlConnection.setConnectTimeout(request.getConnectTimeout());
			}
			if(request.getReadTimeout() > -1) {
				urlConnection.setReadTimeout(request.getReadTimeout());
			}
			urlConnection.setAllowUserInteraction(request.isAllowUserInteraction());
			urlConnection.setDoInput(request.isDoInput());
			urlConnection.setDoOutput(request.isDoOutput());
			for(final HeaderField headerField : request.getHeaders()) {
				urlConnection.addRequestProperty(headerField.getKey(), headerField.getValue());
			}
			return urlConnection;
		} catch (final IOException e) {
			throw new RestBinderException(e);
		}
	}
}
