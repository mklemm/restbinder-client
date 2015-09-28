package net.codesup.restbinder.client.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mirko Klemm 2015-09-24
 */
public class MediaTypeTest {
	@Test
	public void testSimpleValueOf() {
		final String testTypeStringSimple = "text/plain";
		final MediaType textHtml = MediaType.valueOf(testTypeStringSimple);
		assertEquals("text", textHtml.getType());
		assertEquals("plain", textHtml.getSubType());
	}
	@Test
	public void testOneParamValueOf() {
		final String testTypeStringSimple = "text/html; charset=ISO-8859-1";
		final MediaType textHtml = MediaType.valueOf(testTypeStringSimple);
		assertEquals("text", textHtml.getType());
		assertEquals("html", textHtml.getSubType());
		assertEquals("ISO-8859-1", textHtml.getParameters().get("charset"));
	}
	@Test
	public void testMultiParamValueOf() {
		final String testTypeStringSimple = "application/x-vnd-rb-object+xml;class=net.codesup.JavaClass  ; charset=ISO-8859-1";
		final MediaType textHtml = MediaType.valueOf(testTypeStringSimple);
		assertEquals("application", textHtml.getType());
		assertEquals("x-vnd-rb-object+xml", textHtml.getSubType());
		assertEquals("ISO-8859-1", textHtml.getParameters().get("charset"));
		assertEquals("net.codesup.JavaClass", textHtml.getParameters().get("class"));
	}
}
