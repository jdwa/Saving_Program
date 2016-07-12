package com.ldcgroup.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.dao.StatementDao;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.ldcgroup.model.Type;

public class StatementBoImpl implements StatementBo {
	
	private StatementDao statementDao;

	@Override
	public Statement findById(Long id) {
		return statementDao.findById(id);
	}

	//DI via Spring
	public void setStatementDao(StatementDao statementDao) {
		this.statementDao = statementDao;
	}
	
	@Override
	@Transactional
	public Statement add(Statement statement) {
		return statementDao.add(statement);
	}

	@Override
	@Transactional
	public Statement delete(Long id) {
		return statementDao.delete(id);
	}

	@Override
	public Statement detail(Long id) {
		return statementDao.detail(id);
	}

	@Override
	@Transactional
	public Statement update(Statement statement) {
		return statementDao.update(statement);
	}

	@Override
	public List<Statement> list() {
		return statementDao.list();
	}
	
	@Override
	public List<Statement> list(Member member) {
		return statementDao.list(member);
	}

	@Override
	public List<Statement> list(Trade trade) {
		return statementDao.list(trade);
	}
	
	@Override
	public List<Statement> list(Trade trade, Type type) {
		return statementDao.list(trade, type);
	}

	@Override
	public List<Statement> list(List<Trade> tradeList) {
		List<Statement> statementList = new ArrayList<Statement>();
		
		if (tradeList != null) {
			for (int i = 0; i < tradeList.size(); i++) {
				statementList.addAll(this.list(tradeList.get(i)));
			}			
		}
		return statementList;
	}

	@Override
	public List<Statement> list(List<Trade> tradeList, Type type) {
		List<Statement> statementList = new ArrayList<Statement>();
		
		if (tradeList != null) {
			for (int i = 0; i < tradeList.size(); i++) {
				statementList.addAll(this.list(tradeList.get(i), type));
			}			
		}
		return statementList;
	}
	
	@Override
	public List<Statement> list(Type type) {
		return statementDao.list(type);
	}

	@Override
	public double getStatementSum() {
		return statementDao.getStatementSum();
	}

	@Override
	public double getStatementSum(Member member) {
		return statementDao.getStatementSum(member);
	}

	@Override
	public double getStatementSum(Trade trade) {
		return statementDao.getStatementSum(trade);
	}
	
	@Override
	public double getStatementSum(Trade trade, Type type) {
		return statementDao.getStatementSum(trade, type);
	}

	@Override
	public double getStatementSum(Type type) {
		return statementDao.getStatementSum(type);
	}
}
