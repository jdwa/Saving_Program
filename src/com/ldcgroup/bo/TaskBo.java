package com.ldcgroup.bo;

import java.util.List;

import com.ldcgroup.model.Company;
import com.ldcgroup.model.Task;

public interface TaskBo {
	public Task findById(Long id);
	public Task findByNo(String no);
	public Task add(Task task);
	public Task delete(Long id);
	public List<Task> list();
	public List<Task> list(Company company);
	public List<Task> listValidate();
	public Task detail(Long id);
	public Task update(Task task);
}
