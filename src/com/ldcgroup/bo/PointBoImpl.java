package com.ldcgroup.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.dao.PointDao;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Point;
import com.ldcgroup.model.Task;
import com.ldcgroup.model.Item;

public class PointBoImpl implements PointBo {
	
	private PointDao pointDao;

	@Override
	public Point findById(Long id) {
		return pointDao.findById(id);
	}

	//DI via Spring
	public void setPointDao(PointDao pointDao) {
		this.pointDao = pointDao;
	}
	
	@Override
	@Transactional
	public Point add(Point point) {
		return pointDao.add(point);
	}

	@Override
	@Transactional
	public Point delete(Long id) {
		return pointDao.delete(id);
	}

	@Override
	public Point detail(Long id) {
		return pointDao.detail(id);
	}

	@Override
	@Transactional
	public Point update(Point point) {
		return pointDao.update(point);
	}

	@Override
	public List<Point> list() {
		return pointDao.list();
	}
	
	@Override
	public List<Point> list(Member member) {
		return pointDao.list(member);
	}

	@Override
	public List<Point> list(Task task) {
		return pointDao.list(task);
	}
	
	@Override
	public List<Point> list(Task task, Item item) {
		return pointDao.list(task, item);
	}

	@Override
	public List<Point> list(Item item) {
		return pointDao.list(item);
	}

	@Override
	public double getPointSum() {
		return pointDao.getPointSum();
	}

	@Override
	public double getPointSum(Member member) {
		return pointDao.getPointSum(member);
	}

	@Override
	public double getPointSum(Task task) {
		return pointDao.getPointSum(task);
	}
	
	@Override
	public double getPointSum(Task task, Item item) {
		return pointDao.getPointSum(task, item);
	}

	@Override
	public double getPointSum(Item item) {
		return pointDao.getPointSum(item);
	}
}
