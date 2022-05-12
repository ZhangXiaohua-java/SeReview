package cloud.huel.stream;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
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



}
