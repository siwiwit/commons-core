package id.co.sigma.common.server.dao.util.impl;

import java.util.Set;

import id.co.sigma.common.server.dao.base.BaseSigmaDao;
import id.co.sigma.common.server.dao.util.SigmaUtilDao;

public class SigmaUtilDaoImpl extends BaseSigmaDao implements SigmaUtilDao {

	@Override
	public Set<?> getClassAttribute(Class<?> clazz) {
		return getEntityManager().getMetamodel().entity(clazz).getAttributes();
	}

}