package id.co.gpsc.jquery.client.container;


import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.jquery.client.BaseJqueryWidget;

/**
 * Accordion panel
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public class JQAccordionPanel extends BaseJqueryWidget {

	private static FlowPanel temporaryContentPanelHolder;

	public class AccordionContent {

		private String titleId;
		private String contentId;

		public AccordionContent(String titleId, String contentId) {
			this.titleId = titleId;
			this.contentId = contentId;
		}

		public void remove() {
			DOM.getElementById(titleId).removeFromParent();
			DOM.getElementById(contentId).removeFromParent();
			if ( jqueryRendered){
				destroy();
				renderJQueryWidget();
			}

		}

		public void activate() {

		}
	}

	private void initateHolder() {
		if (temporaryContentPanelHolder == null) {
			temporaryContentPanelHolder = new FlowPanel();
			temporaryContentPanelHolder.setVisible(false);
			temporaryContentPanelHolder.setWidth("1px");
			temporaryContentPanelHolder.setHeight("1px");
			RootPanel.get().add(temporaryContentPanelHolder);
		}
	}

	@Override
	protected String getJQueryClassName() {
		return "accordion";
	}

	private final String elementId = "accordion-" + DOM.createUniqueId();

	public JQAccordionPanel() {
		super();
		initateHolder();
	}

	
	
	@Override
	protected Element generateUnderlyingElement() {
		Element swap = DOM.createDiv() ;
		swap.setId(elementId);
		return null;
	}
	
	
	/**
	 * worker untuk append widget ke dalam 
	 **/
	public AccordionContent append(String title, Widget widget) {

		String titleId = DOM.createUniqueId();
		String contentId = DOM.createUniqueId();
		temporaryContentPanelHolder.add(widget);

		Element h3 = DOM.createElement("H3");
		AnchorElement a = AnchorElement.as(DOM.createAnchor());
		Element div = DOM.createDiv();
		div.setId(contentId);
		h3.setId(titleId);
		a.setHref("#");
		a.setInnerHTML(title);

		h3.appendChild(a);
		
		// masukan h3 + div
		DOM.appendChild(getElement(), h3);
		DOM.appendChild(getElement(), div);
		// widget di append ke 
		DOM.appendChild(div, widget.getElement());
		
		if ( jqueryRendered){
			destroy();
			renderJQueryWidget();
		}
		

		return new AccordionContent(titleId, contentId);

	}

	
	@Override
	protected void onDetach() {
		destroy();
		this.jqueryRendered=false ;
		super.onDetach();
	}
}
