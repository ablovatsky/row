package by.avectis.contracts.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractDao<PK extends Serializable, T> {
	
	private final Class<T> persistentClass;

    @Autowired
    private	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public AbstractDao(){
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	protected T getById(PK key) {
		return (T) getSession().get(persistentClass, key);
	}

	protected void persist(T entity) {
		getSession().persist(entity);
	}

	protected void update(T entity) {
		getSession().update(entity);
	}

	protected void delete(T entity) {
		getSession().delete(entity);
	}
	
	protected Criteria createEntityCriteria(){
		return getSession().createCriteria(persistentClass);
	}

}