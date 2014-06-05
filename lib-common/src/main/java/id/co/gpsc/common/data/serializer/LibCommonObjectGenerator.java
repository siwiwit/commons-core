package id.co.gpsc.common.data.serializer;




/**
 * generator object lib commmon. ini untuk meng-cover object 
 * @author Gede Sutarsa
 **/
public class LibCommonObjectGenerator extends AbstractObjectGenerator{
	
	
	private static Class<?> [] ACCEPTED_CLASSES ={
		id.co.gpsc.common.data.ModificationDataContainer.class , 
		id.co.gpsc.common.data.PagedResultHolder.class,
		id.co.gpsc.common.data.serializer.json.ListDataWrapperJSONFriendlyType.class, 
		id.co.gpsc.common.data.serializer.json.ListDataWrapperPrimitiveType.class,
		id.co.gpsc.common.data.serializer.json.NonPrimitiveArrayDataWrapper.class,
		id.co.gpsc.common.data.serializer.json.RPCResponseWrapper.class,
		id.co.gpsc.common.data.serializer.json.SimpleObjectWrapper.class, 
		id.co.gpsc.common.exception.SimpleJSONSerializableException.class,
		//add by dode
		id.co.gpsc.common.data.serializer.json.PrimitiveArrayDataWrapper.class , 
		id.co.gpsc.common.data.SimpleKeyValue.class
	};

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T instantiateSampleObject(String objectFQCN) {
		if (id.co.gpsc.common.data.ModificationDataContainer.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.data.ModificationDataContainer();
		if (id.co.gpsc.common.data.PagedResultHolder.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.data.PagedResultHolder();
		
		if (id.co.gpsc.common.data.serializer.json.ListDataWrapperJSONFriendlyType.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.data.serializer.json.ListDataWrapperJSONFriendlyType();
		if (id.co.gpsc.common.data.serializer.json.ListDataWrapperPrimitiveType.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.data.serializer.json.ListDataWrapperPrimitiveType();
		if (id.co.gpsc.common.data.serializer.json.NonPrimitiveArrayDataWrapper.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.data.serializer.json.NonPrimitiveArrayDataWrapper();
		if (id.co.gpsc.common.data.serializer.json.PrimitiveArrayDataWrapper.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.data.serializer.json.PrimitiveArrayDataWrapper();
		if (id.co.gpsc.common.data.serializer.json.RPCResponseWrapper.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.data.serializer.json.RPCResponseWrapper();
		if (id.co.gpsc.common.data.serializer.json.SimpleObjectWrapper.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.data.serializer.json.SimpleObjectWrapper();
		if (id.co.gpsc.common.exception.SimpleJSONSerializableException.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.exception.SimpleJSONSerializableException();
		
		//add by dode
			
		if ( id.co.gpsc.common.data.SimpleKeyValue.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.data.SimpleKeyValue();
		
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] instantiateArray(String objectFQCN, int size) {
		if (id.co.gpsc.common.data.ModificationDataContainer.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.data.ModificationDataContainer[size];
		if (id.co.gpsc.common.data.PagedResultHolder.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.data.PagedResultHolder[size];
		
		if (id.co.gpsc.common.data.serializer.json.ListDataWrapperJSONFriendlyType.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.data.serializer.json.ListDataWrapperJSONFriendlyType[size];
		if (id.co.gpsc.common.data.serializer.json.ListDataWrapperPrimitiveType.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.data.serializer.json.ListDataWrapperPrimitiveType[size];
		if (id.co.gpsc.common.data.serializer.json.NonPrimitiveArrayDataWrapper.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.data.serializer.json.NonPrimitiveArrayDataWrapper[size];
		if (id.co.gpsc.common.data.serializer.json.PrimitiveArrayDataWrapper.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.data.serializer.json.PrimitiveArrayDataWrapper[size];
		if (id.co.gpsc.common.data.serializer.json.RPCResponseWrapper.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.data.serializer.json.RPCResponseWrapper[size];
		if (id.co.gpsc.common.data.serializer.json.SimpleObjectWrapper.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.data.serializer.json.SimpleObjectWrapper[size];
		if (id.co.gpsc.common.exception.SimpleJSONSerializableException.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.exception.SimpleJSONSerializableException[size];
		
		//add by dode
		
		if ( id.co.gpsc.common.data.SimpleKeyValue.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.data.SimpleKeyValue[size];
		return null;
	}

	@Override
	public Class<?>[] generatedClass() {
		return ACCEPTED_CLASSES;
	}

}
