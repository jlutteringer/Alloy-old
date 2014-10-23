package org.alloy.content.category.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.alloy.domain.entity.FriendlyNamedEntity;

@Entity
@Table(name = "navigationCategory")
public class NavigationCategoryEntity extends FriendlyNamedEntity implements NavigationCategoryFragment {
	private static final long serialVersionUID = 3461300059552047066L;
	private static final String ENTITY_NAME = "navigationCategory";

	@Id
	@GeneratedValue(generator = ENTITY_NAME + "Id", strategy = GenerationType.TABLE)
	@TableGenerator(name = ENTITY_NAME + "Id", table = "sequenceGenerator", pkColumnName = "idName", valueColumnName = "idVal", pkColumnValue = ENTITY_NAME, allocationSize = 50)
	@Column(name = ENTITY_NAME + "Id")
	protected Long id;

	@Column(name = "parentCategoryName")
	private String parentCategoryName;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getParentCategoryName() {
		return parentCategoryName;
	}

	@Override
	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}
}