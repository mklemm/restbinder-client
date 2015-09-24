package net.codesup.restbinder.client.jaxb;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.codesup.restbinder.client.ClientSession;
import net.codesup.restbinder.client.DocumentDescriptor;
import net.codesup.restbinder.client.MessageBodyHandler;
import net.codesup.restbinder.client.RestBinderException;
import net.codesup.restbinder.client.WebDocument;
import net.codesup.restbinder.client.http.HttpResponse;
import net.codesup.restbinder.client.http.JaxbMediaType;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class JaxbMessageBodyHandler implements MessageBodyHandler {
	private static final Map<Class<?>, JAXBContext> JAXB_CONTEXT_POOL = new HashMap<>();

	private synchronized static JAXBContext getJaxbContext(final Class<?> contentClass) throws JAXBException {
		JAXBContext jaxbContext = JaxbMessageBodyHandler.JAXB_CONTEXT_POOL.get(contentClass);
		if(jaxbContext == null) {
			jaxbContext = JAXBContext.newInstance(contentClass);
			JaxbMessageBodyHandler.JAXB_CONTEXT_POOL.put(contentClass, jaxbContext);
		}
		return jaxbContext;
	}

	private final JaxbMediaType<Void> objectMediaType;

	public JaxbMessageBodyHandler(final JaxbMediaType<Void> objectMediaType) {
		this.objectMediaType = objectMediaType;
	}

	@Override
	public <T> WebDocument<T> handle(final ClientSession clientSession, final HttpResponse response) {
		try (final InputStream inputStream = response.getInputStream()){
			final JaxbMediaType<T> contentType = objectMediaType.match(response.getContentType());
			if (contentType != null) {
				final DocumentDescriptor<T> documentDescriptor = clientSession.getDocumentDescriptor(contentType);
				final Unmarshaller unmarshaller = getJaxbContext(contentType.getJaxbClass()).createUnmarshaller();
				@SuppressWarnings("unchecked") final T content = (T)unmarshaller.unmarshal(inputStream);
				return documentDescriptor.createDocument(response.getUri(), content);
			} else {
				return null;
			}
		} catch(final Exception x) {
			throw new RestBinderException(x);
		}
	}


}
