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

	/**
	 * 当Map的key重复时,就会抛出异常java.lang.IllegalStateException: Duplicate key jack
	 * 所以需要对异常进行处理,而不是和向map中添加数据,key相同就用新值替换旧值并返回旧值
	 * 所以在Collectors.toMap(key,value,(oldValue,newValue)->oldValue || newValue)
	 * 中需要加一个参数,用来指定当key重复时来使用哪个值
	 */
	@Test
	public void keyException(){

		List<User> userList = new ArrayList<>();
		userList.add(new User("a","jack"));
		userList.add(new User(UUIDUtils.getId(),"tom"));
		userList.add(new User(UUIDUtils.getId(),"harry"));
		userList.add(new User("a","johnson"));
		Map<String, String> umap = userList.stream().collect(Collectors.toMap(item -> item.getId(), item -> item.getName(),(oldValue,newValue)->oldValue));
		umap.forEach((key,value)->{
			System.out.println(key+"==="+value);
		});

		new Thread(()->{
			Map<String, String> map = userList.parallelStream().collect(Collectors.toMap(item -> item.getId(), item -> item.getName(), (oldVal, newVal) -> newVal));
			map.forEach((key,value)-> System.out.println("key-> "+key+" value->"+value));
		}).start();
		/**
		 * 运行结果
		 * a===jack 使用旧值
		 * dccc784bd1a24d91b22bb55cc1b01d43===tom
		 * c58451a84b2048a9ae1a528d70d2a212===harry
		 *
		 * key-> a value->johnson 使用新值
		 * key-> dccc784bd1a24d91b22bb55cc1b01d43 value->tom
		 * key-> c58451a84b2048a9ae1a528d70d2a212 value->harry
		 */
//		或则可以来一些更好玩的,贪婪写法,将value同时保存
		/***
		 * 只有第一条结果的value是 , 分隔的,而且明显就是旧值和新值的和
		 * a ===jack,johnson
		 * 6703ddb157d44f9c91b61221d4d52c1b ===harry
		 * 596b1465867f428c87dbcd509764fa89 ===tom
		 */
		System.out.println("differ");
		new Thread(()->{
			ConcurrentMap<String, String> concurrentMap = userList.stream().collect(Collectors.toConcurrentMap(item -> item.getId(), item -> item.getName(), (oldValue, newValue) -> {
				 return oldValue+","+newValue;
			}));
			for (String key:concurrentMap.keySet()){
				System.out.println(key+" ==="+concurrentMap.get(key));
			}
		}).start();


	}


}
