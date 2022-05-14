package cloud.huel.domain;

import java.io.Serializable;

/**
 * @author 张晓华
 * @version 1.0
 */
public class Student implements Serializable {

	private Integer id;
	private String name;

	public Student() {
	}

	public Student(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Student student = (Student) o;

		return new org.apache.commons.lang3.builder.EqualsBuilder().append(id, student.id).append(name, student.name).isEquals();
	}

	@Override
	public int hashCode() {
		return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37).append(id).append(name).toHashCode();
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
