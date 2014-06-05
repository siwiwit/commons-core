/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.sigma.common.server.service.datagrid;


import id.co.sigma.common.data.query.SigmaSimpleQueryFilter;
import id.co.sigma.common.data.query.SigmaSimpleSortArgument;

/**
 * interface untuk simple list data provider
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface IJSONDataGridProvider {
    
    
    /**
     * membaca data untuk data grid
     * @param  fetchedFieldNames  nama fields yang perlu di baca
     * @param  pagePosition  posisi page yang perlu di baca
     * @param  pageSize  ukuran page per pembacaan data
     * @param  sortArguments  data di sort berdasarkan apa
     * @param queryFilters  filters untuk proses select
    */
    public SimplifiedGridDataWrapper getData( String[] fetchedFieldNames , int pageSize , int pagePosition , SigmaSimpleQueryFilter[] queryFilters , SigmaSimpleSortArgument[] sortArguments ); 
    
    
}
