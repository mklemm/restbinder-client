package net.codesup.restbinder.client;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import net.codesup.restbinder.client.http.HttpClient;
import net.codesup.restbinder.client.http.HttpRequest;
import net.codesup.restbinder.client.http.HttpResponse;
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
	private final Map<MediaType, MessageBodyHandler> messageBodyHandlers = new HashMap<>();
	private final HttpClient httpClient = new HttpClient();
	private final MessageBodyHandlerFactory messageBodyHandlerFactory;
	private final DocumentDescriptorFactory documentDescriptorFactory;
	private final long maxAgeInCache;

	public ClientSession(final MessageBodyHandlerFactory messageBodyHandlerFactory, final DocumentDescriptorFactory documentDescriptorFactory, final long maxAgeInCache) {
		this.messageBodyHandlerFactory = messageBodyHandlerFactory;
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
	public <T> WebDocument<T> get(final URI uri) {
		WebDocument<T> webDocument = (WebDocument<T>)this.webDocumentCache.get(uri);
		if(webDocument == null || webDocument.isExpired()) {
			final HttpResponse httpResponse = this.httpClient.execute(ClientSession.DEFAULT_GET_BUILDER.withUri(uri).build());
			webDocument = getMessageBodyHandler(httpResponse).handle(this, httpResponse);
		}
		return webDocument;
	}

	public <T> DocumentDescriptor<T> getDocumentDescriptor(final MediaType contentType) {
		@SuppressWarnings("unchecked") DocumentDescriptor<T> documentDescriptor = (DocumentDescriptor<T>)this.documentDescriptors.get(contentType);
		if(documentDescriptor == null) {
			documentDescriptor = this.documentDescriptorFactory.createDocumentDescriptor(this, contentType);
			this.documentDescriptors.put(contentType, documentDescriptor);
		}
		return documentDescriptor;
	}

	public MessageBodyHandler getMessageBodyHandler(final HttpResponse response) {
		MessageBodyHandler messageBodyHandler = this.messageBodyHandlers.get(response.getContentType());
		if(messageBodyHandler == null) {
			messageBodyHandler = this.messageBodyHandlerFactory.createHandler(response);
			this.messageBodyHandlers.put(response.getContentType(), messageBodyHandler);
		}
		return messageBodyHandler;
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
