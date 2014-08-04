package org.alloy.user.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.alloy.domain.entity.DescribedEntity;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.Sets;

@Entity
@Table(name = "role")
public class RoleImpl extends DescribedEntity implements Role {
	private static final long serialVersionUID = 8928679201537590638L;
	private static final String ENTITY_NAME = "role";

	@Id
	@GeneratedValue(generator = ENTITY_NAME + "Id", strategy = GenerationType.TABLE)
	@TableGenerator(name = ENTITY_NAME + "Id", table = "sequenceGenerator", pkColumnName = "idName", valueColumnName = "idVal", pkColumnValue = ENTITY_NAME, allocationSize = 50)
	@Column(name = ENTITY_NAME + "Id")
	protected Long id;

	@ManyToMany(targetEntity = PermissionImpl.class)
	@JoinTable(name = "rolePermissionXref", joinColumns = @JoinColumn(name = "roleId"), inverseJoinColumns = @JoinColumn(name = "permissionId"))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "vStandardElements")
	@BatchSize(size = 50)
	protected Set<Permission> permissions = Sets.newHashSet();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Set<Permission> getPermissions() {
		return permissions;
	}
}