package cloud.huel.stream;

import cloud.huel.domain.Student;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.function.BinaryOperator;

/**
 * @author 张晓华
 * @version 1.0
 */
public class CompareStream {

	/**
	 * 返回较大值
	 * public static <T> BinaryOperator<T> minBy(Comparator<? super T> comparator) {
	 *         Objects.requireNonNull(comparator);
	 *         return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
	 *     }
	 */
	@Test
	public void max(){
		Student u1 = new Student(100,"张三");
		Student u2 = new Student(200,"李四");

		BinaryOperator<Student> binaryOperator = BinaryOperator.maxBy(( item,item2)->item.getId()-item2.getId());
		Student student = binaryOperator.apply(u1, u2);
		System.out.println(student);
		Assert.assertEquals(u2,student);
	}

	/**
	 * 传入两个对象,返回一个较小的对象,minBy和maxBy方法的形参是一个Comparable对象,可以直接使用lambda表达式重写compare方法
	 *BinaryOperator比较的对象必须是相同类型的,并且返回值也是和传入的参数的类型保持一致
	 *   public static <T> BinaryOperator<T> maxBy(Comparator<? super T> comparator) {
	 *         Objects.requireNonNull(comparator);
	 *         return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;
	 *     }
	 */
	@Test
	public void min(){
		Student student = new Student(300,"jack");
		Student student2 = new Student(100,"tom");
		BinaryOperator<Student> binaryOperator = BinaryOperator.minBy((s1,s2)->s1.getId()-s2.getId());
		Student student1 = binaryOperator.apply(student, student2);
		Assert.assertEquals(student2.getName(),student1.getName());

	}

}

