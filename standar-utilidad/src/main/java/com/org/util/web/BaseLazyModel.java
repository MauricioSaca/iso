package com.org.util.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.org.util.domain.BaseModelEntity;
import com.org.util.safe.GroupedFilter;
import com.org.util.safe.PaginatorHelper;
import com.org.util.safe.ValueHolder;
import com.org.util.service.BaseService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseLazyModel <T extends BaseModelEntity<ID>, ID extends Serializable> extends LazyDataModel<T>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BaseService<T, ID> service;
	protected boolean any = true;
	protected transient List<SortMeta> sortingRecord;
	private Set<ValueHolder> customFilters;
	protected ValueHolder newCustomFilter = new ValueHolder(new String(), new String());
    protected ValueHolder selectedFilter;
    protected GroupedFilter groupedFilters;
    protected Class<ID> entityClass;
    
	public BaseLazyModel(BaseService<T, ID> service) {
		this.service = service;
	}
	
	@Override
	public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {

		if (multiSortMeta != null) {
			sortingRecord = multiSortMeta;
		}
		Pageable pageable = PaginatorHelper.pageable(first, pageSize, sortingRecord);
		Set<ValueHolder> conditions = PaginatorHelper.transformer(generalSearchFields(), filters, entityClass);
        if (conditions == null) {
            conditions = new HashSet<ValueHolder>();
        }
        conditions.addAll(getCustomFilters());
		Page<T> page = service.findAll(pageable, any, true, conditions, new HashSet<GroupedFilter>());
		
		this.setRowCount((int) page.getTotalElements());
		
		return page.getContent();
	}
	
	public Set<ValueHolder> getCustomFilters() {
        if (customFilters == null) {
            customFilters = new HashSet<ValueHolder>();
        }
        return customFilters;
    }

    public void addCustomFilter() {
        addCustomFilter(newCustomFilter);
    }

    public void addCustomFilter(ValueHolder filter) {
        getCustomFilters().add(filter);
    }

    public void addCustomFilters(Collection<ValueHolder> filters) {
        getCustomFilters().addAll(filters);
    }
    
    public List<String> generalSearchFields() {
        return new ArrayList<String>();
    }

    public void addGroupedFilter(ValueHolder valueHolder) {
        getGroupedFilters().addValueHolder(valueHolder);
    }

    public GroupedFilter getGroupedFilters() {
        if (groupedFilters == null) {
            groupedFilters = new GroupedFilter();
        }
        return groupedFilters;
    }

    public void clearGroupedFilters() {
        getGroupedFilters().clear();
    }
    
	@Override
    public T getRowData(String rowKey) {
        T ret = null;
        if (service != null) {
            ID recid = null;
            Class idclass = service.getClass();
            if (idclass.equals(String.class)) {
                recid = (ID) rowKey;
            } else if (idclass.equals(Long.class)) {
                recid = (ID) new Long(rowKey);
            } else if (idclass.equals(Integer.class)) {
                recid = (ID) new Integer(rowKey);
            }

            ret = service.findOne(recid);
        }
        return ret;
    }

    @Override
    public ID getRowKey(T element) {
        return element.getId();
    }

}
