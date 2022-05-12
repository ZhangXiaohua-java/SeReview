package cloud.huel.stream;

import cloud.huel.domain.User;
import cloud.huel.utils.UUIDUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;


/**
 * @author 张晓华
 * @version 1.0
 */
@SuppressWarnings({"all"})
public class BaseStream {


	@Test
	public void baseUse(){
//		简单数据类型的数组和list基本使用
		Integer [] nums = new Integer[10];
		Random random = null;
		for (int i = 0; i < 10; i++) {
			random = new Random();
			nums[i] = random.nextInt(10000);
		}
//		统计数量
		long num = Arrays.stream(nums).distinct().count();
		System.out.println(num);
//		过滤元素
		Arrays.stream(nums).distinct().filter(i->i>9000).forEach(n-> System.out.println(n));

		System.out.println("=======================");

		for (Integer integer : nums) {
			System.out.println(integer);
		}
//		基本的聚合操作
		Arrays.stream(nums).distinct().filter(n->n>10).skip(3).limit(3).forEach(i-> System.out.println(i));
		System.out.println("---------------------------");
//		自然排序,正序
		Arrays.stream(nums).sorted().forEach(i-> System.out.println(i));
		System.out.println("sort--------------------------sort");
//		定制排序,倒序
		Arrays.stream(nums).sorted((a,b)->{
			return b-a;
		}).forEach(s-> System.out.println(s));
	}

	@Test
	public void userfulMethod(){
		String[] strs = new String[10];
		strs[0] = "hello world";
		strs[1] = "hello java";
		strs[2] = "hello go";
		strs[3] = "hello c";
		strs[4] = "hello c#";
		strs[5] = "hello c#";
		strs[6] = "hello c++";
		strs[7] = "hello python";
		strs[8] = "hello rust";
		strs[9] = "hello rust";
//		hello Java
		Arrays.stream(strs).filter(s->s.startsWith("hello")&&s.length()>5).filter(s->s.endsWith("a")).forEach(c-> System.out.println(c));
//		hello GO
		Arrays.stream(strs).filter(s->s.contains("go")).forEach(s -> System.out.println(s.toUpperCase(Locale.ROOT)));

	}

	@Test
	public void collectOpeartion(){
		String[] strs = new String[10];
		strs[0] = "hello world";
		strs[1] = "hello java";
		strs[2] = "hello go";
		strs[3] = "hello c";
		strs[4] = "hello c#";
		strs[5] = "hello c#";
		strs[6] = "hello c++";
		strs[7] = "hello python";
		strs[8] = "hello rust";
		strs[9] = "hello rust";
		Object [] strArray = new String[strs.length];
		strArray =  Arrays.stream(strs).filter(s -> s.contains("c")).toArray();
		System.out.println(Arrays.toString(strArray));
		System.out.println("-------------------------");
		List<String> list = Arrays.stream(strs).map(s -> s.toUpperCase(Locale.ROOT)).collect(Collectors.toList());
		list.stream().map(s->s.toLowerCase(Locale.ROOT)).forEach(s-> System.out.println(s));
		System.out.println("java----------java");



//		收集操作获取到的List集合是一个正常的集合,可以进行修改操作
		list.set(0,"hello JAVA");
		list.forEach(s-> System.out.println(s));

		System.out.println("boundary--------------------------boundary");
//		拼接操作,String类中也有一个类似的方法
		String str = Arrays.stream(strs).filter(s -> s.endsWith("a") || s.endsWith("o")).collect(Collectors.joining("$"));
		System.out.println(str);
//		join方法两个参数 分隔符 实现了iterable接口的字符序列
		String s = String.join("$", Arrays.stream(strs).filter(sr->sr.endsWith("a")||sr.endsWith("o")).collect(Collectors.toList()));
		Assert.assertEquals(true,s.equals(str));
		List<String> stringList = new ArrayList<>();
		Set<String> stringSet = Arrays.stream(strs).collect(Collectors.toSet());
		stringSet.forEach(s2-> System.out.println(s2));
	}

	/**
	 * collect 操作的四个参数
	 * Collectors工具类提供的简化操作
	 * Collectors.toList()
	 * Collectors.toSet()
	 * Collectors.joining(String s)
	 * Collectors.toMap()
	 */

	@Test
	public void toMap(){
		List<User> userList = new ArrayList<>();
		userList.add(new User(UUIDUtils.getId(),"jack"));
		userList.add(new User(UUIDUtils.getId(),"tom"));
		userList.add(new User(UUIDUtils.getId(),"harry"));
		userList.add(new User(UUIDUtils.getId(),"johnson"));
		Thread thread = new Thread(()->{
			ConcurrentMap<String, String> concurrentMap = userList.parallelStream().collect(Collectors.toConcurrentMap(User::getId,User::getName));
			concurrentMap.forEach((key,value)->{
				System.out.println("key-> "+key+" value->"+value);
			});
		});
		thread.start();
		System.out.println("hello java");
	}



}
