package com.ldcgroup.bo;

import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.model.Member;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Trade;

public class TransactionServiceBoImpl implements TransactionServiceBo {

	private Trade trade;
	private MemberBo memberBo;
	private StatementBo statementBo;
	private TradeBo tradeBo;
	
	@Override
	@Transactional
	public void addTradeStatement(Trade trade, Statement statement) {
		// Begin Transaction
		// Add Trade
		getTradeBo().add(trade);
		// Add statement
		getStatementBo().add(statement);
		// End Transaction	
	}

	@Override
	@Transactional
	public void addTradeStatement(Trade trade, Statement statement, Member member) {
		// Begin Transaction
		// Update member status (Role)
		getMemberBo().update(member);
		// Add Trade
		getTradeBo().add(trade);
		// Add statement
		getStatementBo().add(statement);
		// End Transaction	
	}

	@Override
	@Transactional
	public void addTradeStatementList(Trade trade, List<Statement> statements) {
		
		// Begin Transaction
		// Add Trade
		getTradeBo().add(trade);
		// Add all statements
		Iterator<Statement> i = statements.iterator();
		while (i.hasNext()) {
			Statement statement = (Statement) i.next();
			getStatementBo().add(statement);
		}
		// End Transaction
	}
	
	@Override
	@Transactional
	public void addTradeStatementList(Trade trade, List<Statement> statements, Member member) {
		
		// Begin Transaction
		// Update member (Role) status
		getMemberBo().update(member);
		// Add Trade
		getTradeBo().add(trade);
		// Add all statements
		Iterator<Statement> i = statements.iterator();
		while (i.hasNext()) {
			Statement statement = (Statement) i.next();
			getStatementBo().add(statement);
		}
		// End Transaction
	}
	
	@Override
	@Transactional
	public void addTradeStatementList(Trade trade, List<Statement> statements, List<Member> members) {
		
		// Begin Transaction
		// Add or Update member record
		Iterator<Member> m = members.iterator();
		while (m.hasNext()) {
			Member member = (Member) m.next();
			getMemberBo().add(member);
		}
		// Add Trade
		getTradeBo().add(trade);
		// Add all statements
		Iterator<Statement> i = statements.iterator();
		while (i.hasNext()) {
			Statement statement = (Statement) i.next();
			getStatementBo().add(statement);
		}
		// End Transaction
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	
	public MemberBo getMemberBo() {
		return memberBo;
	}

	public void setMemberBo(MemberBo memberBo) {
		this.memberBo = memberBo;
	}

	public StatementBo getStatementBo() {
		return statementBo;
	}

	public void setStatementBo(StatementBo statementBo) {
		this.statementBo = statementBo;
	}

	public TradeBo getTradeBo() {
		return tradeBo;
	}

	public void setTradeBo(TradeBo tradeBo) {
		this.tradeBo = tradeBo;
	}
}
