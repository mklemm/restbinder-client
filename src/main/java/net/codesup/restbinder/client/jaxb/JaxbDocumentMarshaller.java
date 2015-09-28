package net.codesup.restbinder.client.jaxb;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import net.codesup.restbinder.client.DocumentDescriptor;
import net.codesup.restbinder.client.DocumentMarshaller;
import net.codesup.restbinder.client.util.RestBinderException;
import net.codesup.restbinder.client.WebDocument;
import net.codesup.restbinder.client.http.HttpResponse;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class JaxbDocumentMarshaller implements DocumentMarshaller {
	private static final Map<Class<?>, JAXBContext> JAXB_CONTEXT_POOL = new HashMap<>();

	private synchronized static JAXBContext getJaxbContext(final Class<?> contentClass) throws JAXBException {
		JAXBContext jaxbContext = JaxbDocumentMarshaller.JAXB_CONTEXT_POOL.get(contentClass);
		if(jaxbContext == null) {
			jaxbContext = JAXBContext.newInstance(contentClass);
			JaxbDocumentMarshaller.JAXB_CONTEXT_POOL.put(contentClass, jaxbContext);
		}
		return jaxbContext;
	}

	private final JaxbMediaType<Void> objectMediaType;

	public JaxbDocumentMarshaller(final JaxbMediaType<Void> objectMediaType) {
		this.objectMediaType = objectMediaType;
	}

	@Override
	public <T> WebDocument<T> unmarshal(final DocumentDescriptor<T> documentDescriptor, final HttpResponse response) {
		try (final InputStream inputStream = response.getInputStream()){
			final JaxbMediaType<T> contentType = this.objectMediaType.match(response.getContentType());
			if (contentType != null) {
				final Unmarshaller unmarshaller = getJaxbContext(contentType.getJaxbClass()).createUnmarshaller();
				@SuppressWarnings("unchecked") final T content = unmarshaller.unmarshal(new StreamSource(inputStream), contentType.getJaxbClass()).getValue();
				return documentDescriptor.createDocument(response.getRequest().getUri(), content);
			} else {
				return null;
			}
		} catch(final Exception x) {
			throw new RestBinderException(x);
		}
	}


}
