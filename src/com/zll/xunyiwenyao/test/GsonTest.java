package com.zll.xunyiwenyao.test;

import com.google.gson.Gson;

/**
 * Gooogle gson
 * @author rxz
 *
 */
public class GsonTest {

	public static void main(String[] args) {
		Gson gson = new Gson();
		int[] ints = {1, 2, 3, 4, 5};
		String[] strings = {"abc", "def", "ghi"};

		String result1 = gson.toJson(ints);     // ==> prints [1,2,3,4,5]
		String result2 = gson.toJson(strings);  //==> prints ["abc", "def", "ghi"]
		
		System.out.println(result1);
		System.out.println(result2);
		
		int[] ints2 = gson.fromJson("[1,2,3,4,5]", int[].class); 
		for(int i : ints2){
			System.out.println(i);
		}

	}
	
}
