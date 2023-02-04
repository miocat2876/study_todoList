package com.example.famback.client;

import com.example.famback.util.AES256Cipher;
import com.example.famback.util.SHA256Cipher;
import org.springframework.security.core.parameters.P;

import java.util.Arrays;

public class TestMain {
	public static void main(String[] args) throws Exception {
//		int[] numbers = {1, 2, 3, 4, 5};
//		int num1 = 1;
//		int num2 = 3;
//		int j = 0;
//        int[] results = new int[num2 - num1 + 1];
//		for (int i = 0; i < numbers.length; i++) {
//			if(i>=num1 && i<=num2){
//				results[j] = numbers[i];
//				j = j + 1;
//			}
//		}
//		System.out.println(Arrays.toString(results));
		int n = 25;
		int result = 0;
		for(int i = n; i > 0; i--){
			if(n%i ==0 ){
				result = result + 1;
			}
		}
		System.out.println("결과 => " + result);
	}
}
