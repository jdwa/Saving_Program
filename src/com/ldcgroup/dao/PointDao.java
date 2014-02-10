package com.ldcgroup.dao;

import java.util.List;
import com.ldcgroup.model.Member;
import com.ldcgroup.model.Point;
import com.ldcgroup.model.Task;
import com.ldcgroup.model.Item;

public interface PointDao {
	public Point findById(Long id);
	public Point add(Point point);
	public Point delete(Long id);
	public Point detail(Long id);
	public Point update(Point point);
	public List<Point> list();
	public List<Point> list(Member member);
	public List<Point> list(Task task);
	public List<Point> list(Task task, Item item);
	public List<Point> list(Item item);
	public double getPointSum();
	public double getPointSum(Member member);
	public double getPointSum(Task task);
	public double getPointSum(Task task, Item item);
	public double getPointSum(Item item);
}