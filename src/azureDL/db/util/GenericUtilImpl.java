package azureDL.db.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import azureDL.db.GenericInstance;
import azureDL.utilities.EntityManagerUtil;
import azureDL.utilities.Filter;




public class GenericUtilImpl<DataBean extends GenericInstance>{
	
	protected EntityManager em = EntityManagerUtil.getEntityManager();
	protected Class<DataBean> entityClass;
	
	protected List<Filter> andFilters = new ArrayList<Filter>();
	protected List<Filter> orFilters = new ArrayList<Filter>();
	
	protected List<Predicate> andPredicates = new ArrayList<Predicate>();
	protected List<Predicate> orPredicates = new ArrayList<Predicate>();
	
	public static enum Operator {
		EQ (1),
		NE (2),
		LT (3),
		LTE (4),
		GT (5),
		GTE (6),
		LK(7),
		LLK (8),
		RLK (9),
		NLK (10),
		EMP (11),
		NEMP (12),
		NULL (13),
		NOTNULL (14),
		IN (15),
		NOTIN (16),
		SQL (17),
		AND (18),
		OR (19);
		
		private int number;
		
		public int getNumber(){
				return number;
		}
		
		private Operator(int number){
			this.number = number;
		}
	}
	
	public void saveObject(DataBean obj){
		try {
		      em.getTransaction().begin();
		      em.persist(obj);
		      em.getTransaction().commit();
		    } catch (Exception e) {
		      e.printStackTrace();
		      em.getTransaction().rollback();
		      System.out.println("insert fallito");
		    }
	}
	
	public void saveObjectList(List<DataBean> objList){
		Iterator<DataBean> it = objList.iterator();
		while(it.hasNext()){
			saveObject(it.next());
		}
	}
	
	public void saveObject(DataBean obj, boolean forceInsert){
			try {
				  em.getTransaction().begin();
				  if(forceInsert)
					  em.persist(obj);
				  else
					  em.merge(obj);
			      em.getTransaction().commit();
			    } catch (Exception e) {
			      em.getTransaction().rollback();
			      System.out.println("insert fallito");
			    }
	}

	public void updateObject(DataBean obj){
		try {
		      em.getTransaction().begin();
		      em.merge(obj);
		      em.getTransaction().commit();
	    	} catch (Exception e) {
	    	  em.getTransaction().rollback();
	    	}
	}
	
	public void deleteObject(DataBean obj){
		try {
		      em.getTransaction().begin();
		      em.remove(obj);
		      em.getTransaction().commit();
	    	} catch (Exception e) {
	    	  em.getTransaction().rollback();
	    	}
	}
	
	public void flush(){
		em.flush();
	}
	
	public DataBean findByID(int id){
		try{
			em.getTransaction().begin();
			DataBean db =  em.find(entityClass,id);
			return db;
		} catch (Exception e) {
	    	  em.getTransaction().rollback();
	    }
		return null;
	}
	
	public List<DataBean> findAll(){
		try{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DataBean> query = builder.createQuery(entityClass);
			Root<DataBean> root = query.from(entityClass);			
			addPredicates(root, builder);
			
			if (andPredicates.size() > 0 && orPredicates.size() == 0) {
				Predicate p = builder.conjunction();
//				for(int i = 0; i < andPredicates.size(); i++)
//					p1 = builder.and(andPredicates.get(i));
				p = builder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
			    // no need to make new predicate, it is already a conjunction
			    query.select(root).where(p);
			} else if (andPredicates.size() == 0 && orPredicates.size() > 0) {
			    // make a disjunction, this part is missing above
			    Predicate p = builder.disjunction();
			    p = builder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
			    query.where(p);
			} else {
				if(andPredicates.size() > 0 && orPredicates.size() > 0){
				    // both types of statements combined
//					System.out.println("IF 3"+andPredicates.size() + "  "+ orPredicates.size());
				    Predicate o = builder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
				    Predicate p = builder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
				    Predicate q = builder.and(o,p);
				    query.where(q);
				}
			}
			ArrayList<DataBean> list = new ArrayList<DataBean>(); 
			list.addAll(em.createQuery(query).getResultList());
			return  list;
		} catch (Exception e) {
	    	  em.getTransaction().rollback();
	    }
		return null;
	}
	
	public void addFilter(Filter filter){
		this.addFilter(filter, null);
	}
	
	protected void addPredicates(Root<DataBean> root, CriteriaBuilder builder){
		
//		System.out.println("andFilters" + andFilters.size());
//		System.out.println("orFilters" + orFilters.size());
		
		for(Filter andFilter : andFilters){
//			System.out.println("andFilter");
			andPredicates.add(createPredicate(andFilter, root, builder));
		}
		
		for(Filter orFilter : orFilters){
			orPredicates.add(createPredicate(orFilter, root, builder));
		}
	}
	
	public void addFilter(Filter filter, Operator ope){
//		System.out.println("addFilter + ope");
		
		if(ope == null)
			andFilters.add(filter);
		else 
			if(ope.equals(Operator.AND))
				andFilters.add(filter);
			else
				if(ope.equals(Operator.OR))
					andFilters.add(filter);
	}
	
	private Predicate createPredicate(Filter f, Root<DataBean> root, CriteriaBuilder builder){
		Predicate predicate = null;
		String name = f.getField();
		String value = f.getValue();
	    Path<String> path = root.get(name);
	    
		switch(f.getOperator()) {
		 case EQ: predicate = builder.equal(path, value); break;
		 case NE: predicate = builder.notEqual(path, value); break;
		 case GT: predicate = builder.greaterThan(path, value); break;
		 case GTE: predicate = builder.greaterThanOrEqualTo(path, value); break;
		 case LT: predicate = builder.lessThan(path, value); break;
		 case LTE: predicate = builder.lessThanOrEqualTo(path, value); break;
		 case LK: predicate = builder.like(path, "%"+value+"%"); break;
		 case LLK: predicate = builder.like(path, "%"+value); break;
		 case RLK: predicate = builder.like(path, value+"%"); break;
		 case NLK: predicate = builder.notLike(path, value); break;
		 case NULL: predicate = builder.isNull(path); break;
		 case NOTNULL: predicate = builder.isNotNull(path); break;
		 case IN: predicate = builder.in(path); break;
		 case NOTIN: predicate = builder.not(builder.in(path)); break;
		 default : break;
		}
		return predicate;
	}
	
	public void closeConnection(){
		em.close();
		EntityManagerUtil.closeEMFactory();
	}

	public Class<DataBean> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<DataBean> entityClass) {
		this.entityClass = entityClass;
	}
	
}
