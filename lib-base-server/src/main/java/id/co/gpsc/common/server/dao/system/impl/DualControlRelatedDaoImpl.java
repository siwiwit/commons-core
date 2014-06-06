package id.co.gpsc.common.server.dao.system.impl;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.app.SimplifiedDualControlContainerTable;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SigmaSimpleSortArgument;
import id.co.gpsc.common.server.dao.base.BaseSigmaDao;
import id.co.gpsc.common.server.dao.system.DualControlRelatedDao;

/**
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class DualControlRelatedDaoImpl extends BaseSigmaDao implements DualControlRelatedDao{

	@Override
	public PagedResultHolder<SimplifiedDualControlContainerTable> getApprovalList(
			SigmaSimpleQueryFilter[] filters, SigmaSimpleSortArgument[] sorts,
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
