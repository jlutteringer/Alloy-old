package org.alloy.user.domain;

import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.alloy.domain.entity.BaseEntity;
import org.alloy.metal.enumeration._ExtendableEnumeration;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Entity
@Table(name = "user")
public class UserImpl extends BaseEntity implements User {
	private static final long serialVersionUID = -2572842708982205448L;
	private static final String ENTITY_NAME = "user";

	@Id
	@GeneratedValue(generator = ENTITY_NAME + "Id", strategy = GenerationType.TABLE)
	@TableGenerator(name = ENTITY_NAME + "Id", table = "sequenceGenerator", pkColumnName = "idName", valueColumnName = "idVal", pkColumnValue = ENTITY_NAME, allocationSize = 50)
	@Column(name = ENTITY_NAME + "Id")
	protected Long id;

	@Column(name = "username")
	protected String username;

	@Column(name = "authentication")
	protected String authentication;

	@Column(name = "type")
	protected String type = UserType.NORMAL.getType();

	@ElementCollection
	@MapKeyColumn(name = "fieldName")
	@Column(name = "fieldValue")
	@BatchSize(size = 50)
	@CollectionTable(name = ENTITY_NAME + "AdditionalFields", joinColumns = @JoinColumn(name = ENTITY_NAME + "Id"))
	protected Map<String, String> additionalFields = Maps.newHashMap();

	@ManyToMany(targetEntity = RoleImpl.class)
	@JoinTable(name = "userRoleXref", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "vStandardElements")
	@BatchSize(size = 50)
	protected Set<Role> roles = Sets.newHashSet();

	@ManyToMany(targetEntity = PermissionImpl.class)
	@JoinTable(name = "userPermissionXref", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "permissionId"))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "vStandardElements")
	@BatchSize(size = 50)
	protected Set<Permission> permissions = Sets.newHashSet();

	public UserImpl() {
		// Do nothing
	}

	public UserImpl(String username) {
		this.username = username;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getAuthentication() {
		return authentication;
	}

	@Override
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	@Override
	public UserType getType() {
		return _ExtendableEnumeration.getInstance(type, UserType.class);
	}

	@Override
	public void setType(UserType type) {
		this.type = type.getType();
	}

	@Override
	public Set<Role> getRoles() {
		return roles;
	}

	@Override
	public Set<Permission> getPermissions() {
		return permissions;
	}

	@Override
	public Map<String, String> getAdditionalFields() {
		return additionalFields;
	}

	@Override
	public String toString() {
		return super.toString() + " " + username;
	}
}