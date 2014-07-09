package org.vault.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.vault.base.domain.DomainObject;

@Entity
@Table(name = "VAULT_USER")
public class User implements DomainObject {
	private static final long serialVersionUID = -2572842708982205448L;

	public User() {
		// Do nothing
	}

	public User(String username) {
		this.username = username;
	}

	@Id
	@GeneratedValue(generator = "UserId", strategy = GenerationType.TABLE)
	@TableGenerator(name = "UserId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "User", allocationSize = 50)
	@Column(name = "USER_ID")
	protected Long id;

	@Column(name = "USERNAME")
	protected String username;

	public String getUsername() {
		return username;
	}

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
}