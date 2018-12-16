package com.revature.repositories;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Staff;

@Repository
public class StaffRepository {
	SessionFactory sf;
	
	@Autowired
	public StaffRepository(SessionFactory sf) {
		super();
		this.sf = sf;
	}
	
	@Transactional
	public Staff Login(String username) {
		CriteriaBuilder cb = sf.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Staff> initQuery = cb.createQuery(Staff.class);
		Root<Staff> root = initQuery.from(Staff.class);
		initQuery
			.select(root)
			.where(cb.equal(root.get("username"), username));
		Query<Staff> query = sf.getCurrentSession().createQuery(initQuery);
		List<Staff> results = query.getResultList();
		if(results.size() == 0) {
			System.out.println("No user was found");
			return null;
		}
		System.out.println(results.get(0));
		return results.get(0);
	}

	@Transactional
	public void insert(Staff staff) {
		System.out.println("New staff member is being created in the StaffRepo");
		System.out.println(staff.toString());
		sf.openSession().persist(staff);
	}

}
