package org.vault.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.vault.base.domain.DomainObject;

@Entity
@Table(name = "VAULT_USER")
public class User implements DomainObject {
	private static final long serialVersionUID = -2572842708982205448L;

	@Id
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
}