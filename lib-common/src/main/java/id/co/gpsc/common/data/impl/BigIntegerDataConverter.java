package id.co.gpsc.common.data.impl;

import java.math.BigInteger;

import id.co.gpsc.common.data.DataConverter;



/**
 * konverter dari big integer ke string
 **/
public class BigIntegerDataConverter extends DataConverter<BigInteger>{

	public BigInteger translateData(String stringRepresentation) {
		return new BigInteger(stringRepresentation); 
	};
}
