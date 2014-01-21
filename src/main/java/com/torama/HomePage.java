package com.torama;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		Label versionLabel = new Label("version", getApplication().getFrameworkSettings()
				.getVersion());
		versionLabel.add(new AttributeAppender("style", "color:red;"));
		this.add(versionLabel);
		this.add(new Link<AbstractLink>("impressum-link") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(Impressum.class);
			}
		});
		this.add(new HeaderPanel("testpanel"));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(JavaScriptReferenceHeaderItem
				.forReference(new JavaScriptResourceReference(HomePage.class,
						"resources/js/HomePage.js")));
		response.render(CssReferenceHeaderItem
				.forReference(new CssResourceReference(HomePage.class,
						"resources/css/HomePage.css")));
	}
}
