package com.example.famback.util;

import com.google.gson.Gson;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DataUtil {

	private static final byte[] INIT_BYTE = new byte[]{};

//	public static <T1,T2> T2 dtoCopy(T1 original, T2 target, Map<String,String> mapping){
//		if(original == null || target == null) throw new RuntimeException("객체가 null 입니다");
//		Class<?> cls  = (original).getClass();
//		Class<?> cls2 = (target).getClass();
//		Map<String, String> map = Arrays.stream(cls.getDeclaredMethods()).collect(
//				Collectors.toMap(
//						(method)-> "get" + method.getName().substring(3),
//						(method)-> "get" + method.getName().substring(3)
//				)
//		);
//
//		mapping.forEach((key,value)->{map.merge(key,value,(mapData,mappingData)->mappingData);});
//
//		map.forEach((methodName)->{
//				try {
//					Class<?> type = Class.forName(String.valueOf(method.getReturnType()).replace("class ",""));
//					Method targetMethod = cls2.getMethod("set" + filterItemName, type);
//					targetMethod.invoke(target,method.invoke(original));
//				} catch (ReflectiveOperationException ignored) {
//				}
//		});
//		return target;
//	}

	public static <T1,T2> T2 dtoCopy(T1 original, T2 target){
		if(original == null || target == null) throw new RuntimeException("객체가 null 입니다");
		Class<?> cls  = (original).getClass();
		Class<?> cls2 = (target).getClass();
//		Map<String, String> map = Arrays.stream(cls.getDeclaredMethods()).collect(
//				Collectors.toMap(
//						(method)-> "get" + method.getName().substring(3),
//						(method)-> "get" + method.getName().substring(3)
//				)
//		);

		Arrays.stream(cls.getDeclaredMethods()).forEach((method)->{
			String filterItemName = method.getName().substring(3);
			if(method.getName().equals("get" + filterItemName)){
				try {
					Class<?> type = Class.forName(String.valueOf(method.getReturnType()).replace("class ",""));
					Method targetMethod = cls2.getMethod("set" + filterItemName, type);
					targetMethod.invoke(target,method.invoke(original));
				} catch (ReflectiveOperationException ignored) {
				}
			}
		});
		return target;
	}

	public static byte[] toBase64(String data){
		if("".equals(data)) return INIT_BYTE;
		return Base64.getEncoder().encode(data.getBytes());
	}

	public static String getUuid(){
		return UUID.randomUUID().toString();
	}

	public static String customReplace(String original, String...newTexts){
		if("".equals(original) || newTexts.length <= 0) return original;
		AtomicInteger index = new AtomicInteger();
		return Arrays.stream(newTexts).reduce(original,(subtotal, element)-> subtotal.replace("{"+index+"}",element));
	}

	public static String nullIf(String original, String basic){
		if(isNotNull(original)){
			if(isNotNull(basic)){
				return basic;
			}
		}
		return original;
	}

	public static boolean isNotNull(String original){
		return original != null && !"".equals(original);
	}

	public static <T extends Enum<T>>boolean hasEnumValue(Class<? extends Enum<T>> tClass, String target){
		for (Enum<T> t: tClass.getEnumConstants()) {
			if(t.name().equals(target)){
				return true;
			}
		}
		return false;
	}

	@Getter
	public enum getGson{
		GSON(new Gson());
		private final Gson gson;
		getGson(Gson gson) {
			this.gson = gson;
		}
	}
}
