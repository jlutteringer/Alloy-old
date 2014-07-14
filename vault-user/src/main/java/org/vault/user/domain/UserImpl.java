package org.vault.user.domain;

import java.util.Map;
import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.vault.domain.entity.AdditionalFieldsEntity;

@Entity
@Table(name = "VAULT_USER")
@AssociationOverride(name = "additionalFields")
public class UserImpl extends AdditionalFieldsEntity implements User {
	private static final long serialVersionUID = -2572842708982205448L;

	public UserImpl() {
		// Do nothing
	}

	public UserImpl(String username) {
		this.username = username;
	}

	@Id
	@GeneratedValue(generator = "UserId", strategy = GenerationType.TABLE)
	@TableGenerator(name = "UserId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "User", allocationSize = 50)
	@Column(name = "USER_ID")
	protected Long id;

	@Column(name = "USERNAME")
	protected String username;

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "[" + id + "] " + username;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLogin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogin(String login) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEmail(String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Role> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRoles(Set<Role> roles) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPhoneNumber(String phone) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPhoneNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Permission> getPermissions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPermissions(Set<Permission> permissions) {
		// TODO Auto-generated method stub

	}

	@Override
	@CollectionTable(name = "VAULT_USER_ADDTL_FIELDS", joinColumns = @JoinColumn(name = "USER_ID"))
	public Map<String, String> getAdditionalFields() {
		return additionalFields;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setActive(boolean isActive) {
		// TODO Auto-generated method stub

	}
}
