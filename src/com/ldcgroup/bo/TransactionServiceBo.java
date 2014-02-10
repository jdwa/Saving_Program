package com.ldcgroup.bo;

import java.util.List;

import com.ldcgroup.model.Member;
import com.ldcgroup.model.Point;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Task;
import com.ldcgroup.model.Trade;

public interface TransactionServiceBo {
	public void addTaskPointList(Task task, List<Point> points);
	public void addTaskPointList(Task task, List<Point> points, List<Member> members);
	public void addTradeStatement(Trade trade, Statement statement);
	public void addTradeStatement(Trade trade, Statement statement, Member member);
	public void addTradeStatementList(Trade trade, List<Statement> statements);
	public void addTradeStatementList(Trade trade, List<Statement> statements, Member member);
	public void addTradeStatementList(Trade trade, List<Statement> statements, List<Member> members);
}
