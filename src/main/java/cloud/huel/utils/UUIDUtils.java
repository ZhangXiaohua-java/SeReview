package cloud.huel.utils;

import java.util.UUID;

/**
 * @author 张晓华
 * @version 1.0
 */
public class UUIDUtils {

	public static String getId(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}

}
