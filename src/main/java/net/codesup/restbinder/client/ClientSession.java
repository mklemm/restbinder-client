package net.codesup.restbinder.client;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import net.codesup.restbinder.client.http.HttpClient;
import net.codesup.restbinder.client.http.HttpRequest;
import net.codesup.restbinder.client.http.HttpResponse;
import net.codesup.restbinder.client.http.HttpStatusCategory;
import net.codesup.restbinder.client.jaxb.JaxbMediaType;
import net.codesup.restbinder.client.http.MediaType;

/**
 * Created by klemm0 on 2015-09-22.
 */
public class ClientSession {
	public static final HttpRequest.Builder DEFAULT_GET_BUILDER = HttpRequest.newBuilder()
			.withMethod("GET")
			.withAccept(MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON, MediaType.XHTML);
	public static final HttpRequest.Builder DEFAULT_PUT_BUILDER = HttpRequest.newBuilder()
			.withMethod("PUT")
			.withDoInput(false)
			.withDoOutput(true)
			.withContentType(MediaType.APPLICATION_XML);
	public static final HttpRequest.Builder DEFAULT_POST_BUILDER = HttpRequest.newBuilder()
			.withMethod("POST")
			.withDoInput(true)
			.withDoOutput(true)
			.withContentType(MediaType.APPLICATION_X_WWW_FORM_URLENCODED);
	public static final HttpRequest.Builder DEFAULT_DELETE_BUILDER = HttpRequest.newBuilder()
			.withMethod("DELETE")
			.withDoInput(false)
			.withDoOutput(true)
			.withContentType(MediaType.APPLICATION_XML);

	private final Map<URI,WebDocument<?>> webDocumentCache = new WeakHashMap<>();
	private final Map<MediaType, DocumentDescriptor<?>> documentDescriptors = new HashMap<>();
	private final HttpClient httpClient = new HttpClient();
	private final DocumentDescriptorFactory documentDescriptorFactory;
	private final long maxAgeInCache;

	public ClientSession(final DocumentDescriptorFactory documentDescriptorFactory, final long maxAgeInCache) {
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.maxAgeInCache = maxAgeInCache;
	}

	/**
	 * Internal use only
	 * @param uri
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> WebDocument<T> get(final Class<T> requestedType, final URI uri) {
		WebDocument<T> webDocument = (WebDocument<T>)this.webDocumentCache.get(uri);
		if(webDocument == null || webDocument.isExpired()) {
			final HttpRequest request = ClientSession.DEFAULT_GET_BUILDER.withAccept(new JaxbMediaType<>(requestedType)).withUri(uri).build();
			final HttpResponse httpResponse = this.httpClient.execute(request);
			webDocument = this.getDocumentDescriptor(requestedType, httpResponse.getContentType()).unmarshal(httpResponse);
			if(httpResponse.getStatus() == HttpStatusCategory.SUCCESS) {
				this.webDocumentCache.put(uri, webDocument);
			} else {
				throw new DocumentResponseException(httpResponse);
			}
		}
		return webDocument;
	}

	public <T> void put(final WebDocument<T> webDocument) {
		final HttpRequest request = ClientSession.DEFAULT_PUT_BUILDER.withUri(webDocument.getUri()).build();

	}

	public <T> WebDocument post(final URI uri, final T content) {
		final HttpRequest request = ClientSession.DEFAULT_POST_BUILDER.withUri(uri).build();
		return null; // TODO
	}

	public <T> void delete(final WebDocument<T> webDocument) {
		final HttpRequest request = ClientSession.DEFAULT_POST_BUILDER.withUri(webDocument.getUri()).build();

	}

	public <T> DocumentDescriptor<T> getDocumentDescriptor(final Class<T> requestedType, final MediaType contentType) {
		@SuppressWarnings("unchecked") DocumentDescriptor<T> documentDescriptor = (DocumentDescriptor<T>)this.documentDescriptors.get(contentType);
		if(documentDescriptor == null) {
			documentDescriptor = this.documentDescriptorFactory.createDocumentDescriptor(this, requestedType, contentType);
			this.documentDescriptors.put(contentType, documentDescriptor);
		}
		return documentDescriptor;
	}

	public long getMaxAgeInCache() {
		return this.maxAgeInCache;
	}

	public void invalidateCache() {
		this.webDocumentCache.clear();
	}

	public void invalidateCache(final URI uri) {
		this.webDocumentCache.remove(uri);
	}
}
