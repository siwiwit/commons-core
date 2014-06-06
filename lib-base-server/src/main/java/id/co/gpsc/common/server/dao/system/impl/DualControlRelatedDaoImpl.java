package id.co.gpsc.common.server.dao.system.impl;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.app.SimplifiedDualControlContainerTable;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.server.dao.base.BaseJPADao;
import id.co.gpsc.common.server.dao.system.DualControlRelatedDao;

/**
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class DualControlRelatedDaoImpl extends BaseJPADao implements DualControlRelatedDao{

	@Override
	public PagedResultHolder<SimplifiedDualControlContainerTable> getApprovalList(
			SimpleQueryFilter[] filters, SimpleSortArgument[] sorts,
			String username, int page, int pageSize) {
		String hqlSmt = 
				"  SELECT a "
				+ " FROM "
				+ "		SimplifiedDualControlContainerTable a inner join fetch a.dualControlDefinition b  "
				+ " WHERE 1=1 "  + buildWhereStatement("a", filters) 
				+ " AND ( " 
				+ " ( b.strickDualControlFlag='Y' and a.creatorUserId != :APPROVER_NAME  ) " // strick dan bukan milik saya
				+ " or "
				+ " ) "; 
		return null;
	}

}
