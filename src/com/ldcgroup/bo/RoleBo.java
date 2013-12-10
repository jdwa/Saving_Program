package com.ldcgroup.bo;

import java.util.List;
import com.ldcgroup.model.Role;

public interface RoleBo {
	public Role findById(Long id);
	public Role findByCode(String code);
	public Role add(Role role);
	public Role delete(Long id);
	public List<Role> list();
	public Role detail(Long id);
	public Role update(Role role);
}
