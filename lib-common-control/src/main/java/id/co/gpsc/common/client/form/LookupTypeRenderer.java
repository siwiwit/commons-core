package id.co.gpsc.common.client.form;

import java.io.IOException;

import com.google.gwt.text.shared.Renderer;

public class LookupTypeRenderer<T> implements Renderer<T>{
	
	private Renderer<T> actualRender ; 

	@Override
	public String render(T data) {
		if (data == null) {
			return "";
		}
		return actualRender.render(data);
	}

	@Override
	public void render(T data, Appendable target) throws IOException {
		if (data == null) {
			target.append("");
			return ;
		}
		actualRender.render(data, target);
		
	}

	public Renderer<T> getActualRender() {
		return actualRender;
	}

	public void setActualRender(Renderer<T> actualRender) {
		this.actualRender = actualRender;
	}

}
