package net.codesup.restbinder.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import net.codesup.restbinder.client.http.HttpResponse;
import net.codesup.restbinder.client.util.RestBinderException;

/**
 * @author Mirko Klemm 2015-09-24
 */
public class PlainTextDocumentMarshaller implements DocumentMarshaller {
	@Override
	public <T> WebDocument<T> unmarshal(final DocumentDescriptor<T> documentDescriptor, final HttpResponse response) {
		final StringWriter sw = new StringWriter();
		try(final PrintWriter bw = new PrintWriter(sw); final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getInputStream()))) {
			String line;
			while((line = rd.readLine()) != null) {
				bw.println(line);
			}
			bw.flush();
			return new WebDocument<T>(documentDescriptor, response.getRequest().getUri(), (T)sw.toString());
		} catch (final IOException e) {
			throw new RestBinderException(e);
		}
	}
}
