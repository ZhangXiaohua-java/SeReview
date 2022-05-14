package cloud.huel.stream;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author 张晓华
 * @version 1.0
 */
@SuppressWarnings({"all"})
public class StreamArrayOpearation {

	/**
	 * toArray中必须传入要转换的目标数组类型,否则拿到的就是Object类型的数组
	 */
	@Test
	public void base(){
		Stream<Integer> stream = Stream.of(1,2,3,5,7,9);
		Integer[] array = stream.filter(i -> i > 2).toArray(Integer[]::new);
		System.out.println(Arrays.toString(array));
//		反例,得到的Object类型的数组不能进行类型转换,否则就会报类型转换异常
//		java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.Integer;
		Object[] nums = Stream.of(1,2,3,5,7,9).filter(i -> i > 2).toArray();
		System.out.println(Arrays.toString(nums));
//		Integer [] destArray = (Integer[]) nums;
//		System.out.println(Arrays.toString(destArray));
	}

	/**
	 * 将List集合转换为String数组
	 */
	@Test
	public void stringListConvertStringArray(){
		ArrayList<String> list = new ArrayList<>();
		list.add("java");
		list.add("go");
		list.add("spark");
		list.add("rust");
		String[] strings = list.stream().map(s -> s.toUpperCase(Locale.ROOT)).toArray(String[]::new);
		for (String s : strings) {
			System.out.println(s);
		}

	}

	/**
	 * 串行流
	 */
	@Test
	public void toArray(){
		Integer[] array = Stream.of(1, 2, 3, 57, 9).toArray(Integer[]::new);
		System.out.println(Arrays.toString(array));
		List<String> list = Arrays.asList("a", "b", "c", "d", "e");
		list.parallelStream().forEach(System.out::println);
		list.stream().parallel().forEach(s-> System.out.println(s));

	}


	/**
	 * 没看出来啥区别
	 */
	@Test
	public void find(){
		List<String> list = Arrays.asList("a", "b", "c", "c1", "c2", "d");
		Optional<String> ele = list.parallelStream().filter(s -> s.startsWith("c")).findFirst();
		System.out.println(ele.get());
		Optional<String> ele2 = list.parallelStream().filter(s -> s.startsWith("c")).findAny();
		System.out.println(ele2.get());

//		串行流
		Optional<String> s1 = list.stream().filter(s -> s.startsWith("c")).findFirst();
		System.out.println(s1.get());
		new Thread(()->{
			Optional<String> s2 = list.stream().filter(s -> s.startsWith("c")).findAny();
			System.out.println(s2.get());
		}).start();

	}



}
