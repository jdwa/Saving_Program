package com.ldcgroup.bo;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ldcgroup.dao.TaskDao;
import com.ldcgroup.model.Company;
import com.ldcgroup.model.Task;

public class TaskBoImpl implements TaskBo {
	
	private TaskDao taskDao;
	
	@Override
	public Task findById(Long id) {
		return taskDao.findById(id);
	}

	@Override
	public Task findByNo(String no) {
		return taskDao.findByNo(no);
	}

	//DI via Spring
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	@Override
	@Transactional
	public Task add(Task task) {
		return taskDao.add(task);
	}

	@Override
	@Transactional
	public Task delete(Long id) {
		return taskDao.delete(id);
	}

	@Override
	public List<Task> list() {
		return taskDao.list();
	}
	
	@Override
	public List<Task> list(Company company) {
		return taskDao.list(company);
	}
	
	@Override
	public List<Task> listValidate() {
		return taskDao.listValidate();
	}

	@Override
	public Task detail(Long id) {
		return taskDao.detail(id);
	}

	@Override
	@Transactional
	public Task update(Task task) {
		return taskDao.update(task);
	}
}
