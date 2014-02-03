package com.torama;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.visit.IVisitor;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.visit.IVisit;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	private Label firstLabel;
	private Label secondLabel;
	
	
	public HomePage(final PageParameters parameters) {
		super(parameters);
		firstLabel = new Label("label", "FirstLabel");
		secondLabel = new Label("label", "Second Label");
		
		this.add(new Link<Object>("reload") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				if(this.getPage().contains(firstLabel, true)) {
					this.getPage().replace(secondLabel);
				} else {
					this.getPage().replace(firstLabel);
				}
			}
		});
		
		this.add(firstLabel);
		Label versionLabel = new Label("version", getApplication()
				.getFrameworkSettings().getVersion());
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
		
		int random = ((Math.random() * 3) > 1.5 ) ? 1 : 2;
		this.add(new Label("frag-random", random));
		
		Fragment fragment = new Fragment("fragment-container", "frag-var-" + random, this);
		this.add(fragment);
		
		if (random == 2) {
			fragment.add(new Label("frag2-specific", "gmx"));
			fragment.add(new AttributeModifier("data-brand", "gmx"));
		}

		TestBorder testBorder = new TestBorder("testborder");
		testBorder.addToBorder(new Label("childMarkup", "Child inside Markup"));
		testBorder.add(new Label("childTag", "Child inside Tag"));
		this.add(testBorder);
		WebMarkupContainer marktest = new WebMarkupContainer("information-box");
		marktest.add(new Label("information-message", "Hallo"));
		Label additionalInformation =  new Label("additional-information", "4");
		marktest.add(additionalInformation);
		additionalInformation.setVisible(false);
		
		marktest.add(new Label("additional-information2", "addinfo2") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onInitialize() {
				super.onInitialize();
			}
			
			@Override
			protected void onConfigure() {
				super.onConfigure();
				this.setVisible(true);
				this.setEnabled(false);
			}
			
			@Override
			protected void onComponentTag(ComponentTag tag) {
				// TODO Auto-generated method stub
				super.onComponentTag(tag);
				tag.setName("small");
				if(!this.isEnabled()) {
					tag.put("style", "color: grey");
				}
			}
			
			@Override
			public void onComponentTagBody(MarkupStream markupStream,
					ComponentTag openTag) {
				if (!this.isEnabled()) {
					this.replaceComponentTagBody(markupStream, openTag, "disabled");
				} else {
					super.onComponentTagBody(markupStream, openTag);
				}
			}
			
		});
		
		this.add(marktest);
		
		
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		this.visitChildren(new IVisitor<Component, Void>() {
			public void component(Component component, IVisit<Void> visit) {
				if(!component.isStateless()) {
					System.out.println("Component " + component.getId() + " is not stateless");
				}
			};
		});
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
