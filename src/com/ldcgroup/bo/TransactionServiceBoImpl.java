package com.ldcgroup.bo;

import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.model.Member;
import com.ldcgroup.model.Pay;
import com.ldcgroup.model.Point;
import com.ldcgroup.model.Roll;
import com.ldcgroup.model.Statement;
import com.ldcgroup.model.Task;
import com.ldcgroup.model.Trade;

public class TransactionServiceBoImpl implements TransactionServiceBo {

	private Roll roll;
	private Task task;
	private Trade trade;
	private MemberBo memberBo;
	private PayBo payBo;
	private PointBo pointBo;
	private StatementBo statementBo;
	private RollBo rollBo;
	private TaskBo taskBo;
	private TradeBo tradeBo;
	
	@Override
	@Transactional
	public void addRollPayList(Roll roll, List<Pay> pays) {
		
		// Begin Transaction
		// Add Roll
		getRollBo().add(roll);
		// Add all pays
		Iterator<Pay> i = pays.iterator();
		while (i.hasNext()) {
			Pay pay = (Pay) i.next();
			getPayBo().add(pay);
		}
		// End Transaction
	}
	
	@Override
	@Transactional
	public void addRollPayList(Roll roll, List<Pay> pays, List<Member> members) {
		
		// Begin Transaction
		// Add or Update member record
		Iterator<Member> m = members.iterator();
		while (m.hasNext()) {
			Member member = (Member) m.next();
			getMemberBo().add(member);
		}
		// Add Trade
		getRollBo().add(roll);
		// Add all statements
		Iterator<Pay> i = pays.iterator();
		while (i.hasNext()) {
			Pay pay = (Pay) i.next();
			getPayBo().add(pay);
		}
		// End Transaction
	}
	
	@Override
	@Transactional
	public void addTaskPointList(Task task, List<Point> points) {
		
		// Begin Transaction
		// Add Task
		getTaskBo().add(task);
		// Add all points
		Iterator<Point> i = points.iterator();
		while (i.hasNext()) {
			Point point = (Point) i.next();
			getPointBo().add(point);
		}
		// End Transaction
	}
	
	@Override
	@Transactional
	public void addTaskPointList(Task task, List<Point> points, List<Member> members) {
		
		// Begin Transaction
		// Add or Update member record
		Iterator<Member> m = members.iterator();
		while (m.hasNext()) {
			Member member = (Member) m.next();
			getMemberBo().add(member);
		}
		// Add Trade
		getTaskBo().add(task);
		// Add all statements
		Iterator<Point> i = points.iterator();
		while (i.hasNext()) {
			Point point = (Point) i.next();
			getPointBo().add(point);
		}
		// End Transaction
	}
	
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

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public PointBo getPointBo() {
		return pointBo;
	}

	public void setPointBo(PointBo pointBo) {
		this.pointBo = pointBo;
	}

	public TaskBo getTaskBo() {
		return taskBo;
	}

	public void setTaskBo(TaskBo taskBo) {
		this.taskBo = taskBo;
	}

	public Roll getRoll() {
		return roll;
	}

	public void setRoll(Roll roll) {
		this.roll = roll;
	}

	public PayBo getPayBo() {
		return payBo;
	}

	public void setPayBo(PayBo payBo) {
		this.payBo = payBo;
	}

	public RollBo getRollBo() {
		return rollBo;
	}

	public void setRollBo(RollBo rollBo) {
		this.rollBo = rollBo;
	}
}
