package com.ldcgroup.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.model.Company;
import com.ldcgroup.model.Member;
import com.ldcgroup.util.Definition;

public class MemberDaoImpl extends HibernateDaoSupport implements MemberDao {

	@Override
	@Transactional(readOnly = true)
	public Member findById(Long id) {
		return (Member) getHibernateTemplate().get(Member.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Member findByAccount(String account) {
		List<Member> list = getHibernateTemplate().find("from Member where account = ?", account);
		return ((list != null) && (list.size() > 0)) ? (Member) list.get(0) : null;
	}

	@Override
	@Transactional
	public Member add(Member member) {
		getHibernateTemplate().saveOrUpdate(member);
		return member;
	}

	@Override
	@Transactional
	public Member delete(Long id) {
		Member member = findById(id);
		getHibernateTemplate().delete(member);
		return member;
	}

	@Override
	@Transactional(readOnly = true)
	public Member detail(Long id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Member update(Member member) {
		getHibernateTemplate().saveOrUpdate(member);
		return member;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> list() {
		return (List<Member>) getHibernateTemplate().find("from Member");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> listNormal() {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_NORMAL);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> listResign() {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_RESIGN);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> listRetire() {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_RETIRE);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> listRemain() {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_REMAIN);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> listReturn() {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_RETURN);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> list(Company company) {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.company = ?", company);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> listNormal(Company company) {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_NORMAL, company);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> listNormalHistory(Company company) {
		
		final String queryString = "select distinct Member from Statement where company = '" + company.getId() + "'";
		List<Object> list = (List<Object>) getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query query = session.createSQLQuery(queryString);
				return query.list();
			}
		});
		
		List<Member> memberList = new ArrayList<Member>();
		for (int i = 0; i < list.size(); i++) {
			memberList.add(findById(((BigDecimal)list.get(i)).longValue()));
		}

		return memberList;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> listResign(Company company) {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_RESIGN, company);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> listRetire(Company company) {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_RETIRE, company);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> listRemain(Company company) {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_REMAIN, company);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Member> listReturn(Company company) {
		return (List<Member>) getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_RETURN, company);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Member findRemainAccount(Company company) {
		List<Member> list = getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_REMAIN, company);
		if ((list != null) && (list.size() == 0)) {
			list = getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_REMAIN);
		}
		return ((list != null) && (list.size() > 0)) ? (Member) list.get(0) : null;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Member findReturnAccount(Company company) {
		List<Member> list = getHibernateTemplate().find("from Member e where e.role.role_code = ? and e.company = ?", Definition.ROLE_RETURN, company);
		if ((list != null) && (list.size() == 0)) {
			list = getHibernateTemplate().find("from Member e where e.role.role_code = ?", Definition.ROLE_RETURN);
		}
		return ((list != null) && (list.size() > 0)) ? (Member) list.get(0) : null;
	}
}
