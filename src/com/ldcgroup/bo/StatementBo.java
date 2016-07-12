package com.ldcgroup.bo;

import java.util.List;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;
import com.ldcgroup.model.Type;

public interface StatementBo {
	public Statement findById(Long id);
	public Statement add(Statement statement);
	public Statement delete(Long id);
	public Statement detail(Long id);
	public Statement update(Statement statement);
	public List<Statement> list();
	public List<Statement> list(Member member);
	public List<Statement> list(Trade trade);
	public List<Statement> list(Trade trade, Type type);
	public List<Statement> list(List<Trade> tradeList);
	public List<Statement> list(List<Trade> tradeList, Type type);	
	public List<Statement> list(Type type);
	public double getStatementSum();
	public double getStatementSum(Member member);
	public double getStatementSum(Trade trade);
	public double getStatementSum(Trade trade, Type type);
	public double getStatementSum(Type type);
}
