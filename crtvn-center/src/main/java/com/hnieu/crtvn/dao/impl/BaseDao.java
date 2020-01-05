package com.hnieu.crtvn.dao.impl;

import com.hnieu.crtvn.dao.IBaseDAO;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class BaseDao<T> implements IBaseDAO<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }
	
	private Session getCurrentSession() {
        return getSession();
    }
      
    @SuppressWarnings("unchecked")
	@Override  
    public T get(Class<T> entityClazz, Serializable id) {  
        return (T) getCurrentSession().get(entityClazz, id);  
    }  
  
    @Override  
    public Serializable save(T entity) {  
        return getCurrentSession().save(entity);  
    }  
  
    @Override  
    public void update(T entity) {  
        getCurrentSession().saveOrUpdate(entity);  
    }  
  
    @Override  
    public void delete(T entity) {  
        getCurrentSession().delete(entity);  
    }  
  
    @Override  
    public boolean delete(Class<T> entityClazz, Serializable id) {  
        String hql = "delete " + entityClazz.getSimpleName() + " en where en.id = ?0";  
        Query query = getCurrentSession().createQuery(hql).setParameter("0", id);
        return (query.executeUpdate() > 0);  
    }  
  
    @Override  
    public List<T> findAll(Class<T> entityClazz) {  
        String hql = "select en from " + entityClazz.getSimpleName() + " en";  
        return find(hql);  
    }  
  
    @Override  
    public long findCount(Class<T> entityClazz) {  
        String  hql  = "select count(*) from " + entityClazz.getSimpleName();  
        List<T> list = find(hql);  
        if (list != null && list.size() == 1) {  
            return (Long) list.get(0);  
        }  
        return 0;  
    }  
  
    /** 
     * 根据HQL语句查询实体 
     * @param hql           待查询的HQL语句 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    protected List<T> find(String hql) {  
        return getCurrentSession().createQuery(hql).list();  
    }  
      
    /** 
     *根据带占位符参数HQL语句查询实体 
     * @param hql           待查询的HQL语句 
     * @param params        参数 
     * @return
     */    
    protected Object getMax(String hql, Map<String,Object> params) {  
    	Query query = getCurrentSession().createQuery(hql);
      	for (Map.Entry<String,Object> entry : params.entrySet()) { 
      		query.setParameter( entry.getKey() , entry.getValue());
      	}   
          return query.uniqueResult();
    }  
    
    /** 
     *根据带占位符参数HQL语句查询实体 
     * @param hql           待查询的HQL语句 
     * @param params        参数 
     * @return
     */  
    @SuppressWarnings("unchecked")  
    protected List<T> find(String hql, Map<String,Object> params) {  
        Query query = getCurrentSession().createQuery(hql);
          
    	for (Map.Entry<String,Object> entry : params.entrySet()) { 
    		query.setParameter( entry.getKey() , entry.getValue());
    	}   
        return query.list();  
    }  
      
    /** 
     *  使用hql 语句进行分页查询操作 
     * @param hql       需要查询的hql语句 
     * @param pageNo    查询第pageNo页的记录 
     * @param pageSize  每页需要显示的记录数 
     * @return          当前页的所有记录 
     */  
    @SuppressWarnings("unchecked")  
    protected List<T> findByPage(String hql, int pageNo, int pageSize) {  
        Query query = getCurrentSession().createQuery(hql);
        return query.setFirstResult((pageNo-1) * pageSize).setMaxResults(pageSize).list();  
    }  
      
    /** 
     * 使用hql 语句进行分页查询操作 
     * @param hql       需要查询的hql语句 
     * @param pageNo    查询第pageNo页的记录 
     * @param pageSize  每页需要显示的记录数 
     * @param params    如果hql带占位符参数，params用于传入占位符参数 
     * @return          当前页的所有记录 
     */  
    @SuppressWarnings("unchecked")  
    protected List<T> findByPage(String hql , int pageNo, int pageSize, Map<String,Object> params) {  
        Query query = getSession().createQuery(hql);
       
        for (Map.Entry<String,Object> entry : params.entrySet()) { 
          query.setParameter( entry.getKey() , entry.getValue());
        }       
        return query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();  
    }

	
	@SuppressWarnings("unchecked")
	public List<T> findByPageAndSQL(String sql , int pageNo, int pageSize, Map<String,Object> params,Class<?> cla){		
		
		SQLQuery query =getCurrentSession().createSQLQuery(sql).addEntity(cla);
		
		for (Map.Entry<String,Object> entry : params.entrySet()) { 			
	          query.setParameter( entry.getKey() , entry.getValue());
	    }   
		
		return query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findBySQL(String sql , Map<String,Object> params,Class<?> cla){		
		
		SQLQuery query =getCurrentSession().createSQLQuery(sql).addEntity(cla);
		
		for (Map.Entry<String,Object> entry : params.entrySet()) { 			
	          query.setParameter( entry.getKey() , entry.getValue());
	    }   
		
		return query.list(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findCountByPageAndSQL(String sql , Map<String,Object> params,Class<?> cla){		
		
		SQLQuery query = getCurrentSession().createSQLQuery(sql).addEntity(cla);

		for (Map.Entry<String,Object> entry : params.entrySet()) { 			
	          query.setParameter( entry.getKey() , entry.getValue());
	    }   
		
		return query.list(); 
	}
	
	public void clear(){
		getCurrentSession().clear();
	}
	
	@Override
	public void flush() {
		 getCurrentSession().flush();
	}  
	
	public void commit(){
		getCurrentSession().getTransaction().commit();
	}

	@Override
	public void startT() {
		getCurrentSession().getTransaction().begin();
	}


	public void truncate(String tableName) {
		
		SQLQuery query = getCurrentSession().createSQLQuery(" TRUNCATE TABLE " + tableName );
		query.executeUpdate();
	}
}
