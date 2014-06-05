package id.co.gpsc.common.client.control;

/**
 * 
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public interface IFormElementConfiguration {

	public abstract String getFormId();

	public abstract String getElementId();

	public abstract String getHintI18NKey();

	public abstract String getPlaceHolderI18NKey();

	public abstract Boolean getMandatory();

	public abstract Integer getMaxLength();

	public abstract String getMinValue();

	public abstract String getMaxValue();

	public abstract String getGroupId();

}