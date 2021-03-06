/*
*File: agis.ps.util.MathTool.java
*User: mqin
*Email: mqin@ymail.com
*Date: 2016年1月13日
*/
package agis.ps.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathTool {
	private static Logger logger = LoggerFactory.getLogger(MathTool.class);

	public static Integer sum(List<Integer> nums) {
		int sum = 0;
		for (int i = 0; i < nums.size(); i++) {
			sum += nums.get(i);
		}
		return sum;
	}
	
	public static int sum(int [] nums)
	{
		int sum = 0;
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
		}
		return sum;
	}
	
	public static Map<String, Double> summary(List<Integer> nums)
	{
		return MathTool.summary(nums.toArray(new Integer[nums.size()]));
	}
	
	public static Map<String, Double> summary(Integer [] nums)
	{
		Map<String, Double> values = new HashMap<String, Double>();
		int len = nums.length;
		double min = 0;
		double firstQ = 0;
		double median = 0;
		double thirdQ = 0;
		double max = 0;
		Arrays.sort(nums);
		min = nums[0];
		max = nums[len - 1];
		if(((len + 1) % 4) != 0)
		{
//			double q1Index = Math.ceil(len + 1) * 0.25;
			double q2Index = Math.ceil(len + 1) * 0.5;
//			double q3Index = Math.ceil(len + 1) * 0.75;
//			firstQ = (double)(nums[(int)q1Index - 1] + 
//					(nums[(int)q1Index] - nums[(int)q1Index - 1]) * (q1Index - (int)q1Index));
//			median = (double)(nums[(int)q2Index - 1] + 
//					(nums[(int)q2Index] - nums[(int)q2Index - 1]) * (q2Index - (int)q2Index));
//			thirdQ = (double)(nums[(int)q3Index - 1] + 
//					(nums[(int)q3Index] - nums[(int)q3Index - 1]) * (q3Index - (int)q3Index));
//			firstQ = (nums[(int)q1Index] + nums[(int)q1Index - 1]) * 0.5;
			firstQ = median(Arrays.copyOfRange(nums, 0, (int)q2Index));
			median = (nums[(int)q2Index] + nums[(int)q2Index - 1]) * 0.5;
			thirdQ = median(Arrays.copyOfRange(nums, (int)q2Index, len));
			
		} else
		{
//			int q1Index = (int) Math.ceil((len + 1) * 0.25);
			int q2Index = (int) ((int)(len + 1) * 0.5);
//			int q3Index =  (int) Math.ceil((len * 1) * 0.75);
			firstQ = median(Arrays.copyOfRange(nums, 0, (int)q2Index - 1));
			median = nums[q2Index - 1];
			thirdQ = median(Arrays.copyOfRange(nums, (int)q2Index, len));
		}
		values.put("MIN", min);
		values.put("FIRSTQ", firstQ);
		values.put("MEDIAN", median);
		values.put("THIRDQ", thirdQ);
		values.put("MAX", max);
		return values;
	}
	
	public static Map<String, Double> summary(int [] nums)
	{
		Map<String, Double> values = new HashMap<String, Double>();
		int len = nums.length;
		double min = 0;
		double firstQ = 0;
		double median = 0;
		double thirdQ = 0;
		double max = 0;
		Arrays.sort(nums);
		min = nums[0];
		max = nums[len - 1];
		if(((len + 1) % 4) != 0)
		{
//			double q1Index = Math.ceil(len + 1) * 0.25;
			double q2Index = Math.ceil(len + 1) * 0.5;
//			double q3Index = Math.ceil(len + 1) * 0.75;
//			firstQ = (double)(nums[(int)q1Index - 1] + 
//					(nums[(int)q1Index] - nums[(int)q1Index - 1]) * (q1Index - (int)q1Index));
//			median = (double)(nums[(int)q2Index - 1] + 
//					(nums[(int)q2Index] - nums[(int)q2Index - 1]) * (q2Index - (int)q2Index));
//			thirdQ = (double)(nums[(int)q3Index - 1] + 
//					(nums[(int)q3Index] - nums[(int)q3Index - 1]) * (q3Index - (int)q3Index));
//			firstQ = (nums[(int)q1Index] + nums[(int)q1Index - 1]) * 0.5;
			firstQ = median(Arrays.copyOfRange(nums, 0, (int)q2Index));
			median = (nums[(int)q2Index] + nums[(int)q2Index - 1]) * 0.5;
			thirdQ = median(Arrays.copyOfRange(nums, (int)q2Index, len));
			
		} else
		{
//			int q1Index = (int) Math.ceil((len + 1) * 0.25);
			int q2Index = (int) ((int)(len + 1) * 0.5);
//			int q3Index =  (int) Math.ceil((len * 1) * 0.75);
			firstQ = median(Arrays.copyOfRange(nums, 0, (int)q2Index - 1));
			median = nums[q2Index - 1];
			thirdQ = median(Arrays.copyOfRange(nums, (int)q2Index, len));
		}
		values.put("MIN", min);
		values.put("FIRSTQ", firstQ);
		values.put("MEDIAN", median);
		values.put("THIRDQ", thirdQ);
		values.put("MAX", max);
		return values;
	}
	
	public static double median(List<Integer> nums)
	{
		return MathTool.median(nums.toArray(new Integer[nums.size()]));
	}
	
	public static double median(Integer [] nums)
	{
		double median = 0;
		int len = nums.length;
		if(len == 0)
			return median;
		if(len == 1)
			return median = nums[0];
//		int [] data = new int[len];
//		Arrays.sort(data);
		if((len % 2) == 1)
		{
			int index = len / 2;
			median = nums[index];
		} else
		{
			int n = len/2;
			int f = n - 1;
			int sum = nums[n] + nums[f];
			median = (double)sum / 2;
		}
		return median;
	}
	
	public static double median(int [] nums)
	{
		double median = 0;
		int len = nums.length;
		if(len == 0)
			return median;
		if(len == 1)
			return median = nums[0];
//		int [] data = new int[len];
//		Arrays.sort(data);
		if((len % 2) == 1)
		{
			int index = len / 2;
			median = nums[index];
		} else
		{
			int n = len/2;
			int f = n - 1;
			int sum = nums[n] + nums[f];
			median = (double)sum / 2;
		}
		return median;
	}

	public static Integer mean(List<Integer> nums) {
		return MathTool.mean(nums.toArray(new Integer[nums.size()]));
	}

	public static Integer mean(Integer [] nums) {
		int mean = 0;
		try {
			int sum = 0;
			int size = nums.length;
			if (size == 0)
				return 0;
//				throw new Exception("The arrays could not be empty when computed mean!");
			for (int i = 0; i < size; i++) {
				sum += nums[i];
			}
			mean = Math.round(sum / size);
		} catch (Exception e) {
			logger.error(MathTool.class.getName() + "\t" + e.getMessage());
		}
		return mean;
	}
	
	public static int mean(int [] nums)
	{
		int mean = 0;
		try {
			int sum = 0;
			int size = nums.length;
			if (size == 0)
				return 0;
//				throw new Exception("The arrays could not be empty when computed mean!");
			for (int i = 0; i < size; i++) {
				sum += nums[i];
			}
			mean = Math.round(sum / size);
		} catch (Exception e) {
			logger.error(MathTool.class.getName() + "\t" + e.getMessage());
		}
		return mean;
	}

	public static Integer sd(List<Integer> nums) {
		return MathTool.sd(nums.toArray(new Integer[nums.size()]));
	}

	public static Integer sd(Integer[] nums) {
		int sd = 0;
		try {
			int mean = MathTool.mean(nums);
			int size = nums.length;
			double diff = 0.0f;
			for (int i = 0; i < size; i++) {
				diff += Math.pow(nums[i] - mean, 2);
			}
			sd = (int) Math.round(Math.sqrt(diff / (size - 1)));
		} catch (Exception e) {
			logger.error(MathTool.class.getName() + "\t" + e.getMessage());
		}
		return sd;
	}
	
	public static Integer sd(int[] nums) {
		int sd = 0;
		try {
			int mean = MathTool.mean(nums);
			int size = nums.length;
			double diff = 0.0f;
			for (int i = 0; i < size; i++) {
				diff += Math.pow(nums[i] - mean, 2);
			}
			sd = (int) Math.round(Math.sqrt(diff / (size - 1)));
		} catch (Exception e) {
			logger.error(MathTool.class.getName() + "\t" + e.getMessage());
		}
		return sd;
	} 

	public static Integer avgSd(List<Integer> sds) {
		int sum = 0;
		for (int i = 0; i < sds.size(); i++) {
			sum += Math.pow(sds.get(i), 2);
		}
		int sd = (int) Math.sqrt(sum);
		return sd;
	}
	
	public static int max(List<Integer> values)
	{
		Integer [] arrs = values.toArray(new Integer[values.size()]);
		return max(arrs);
	}
	
	public static int max(Integer[] values)
	{
		Arrays.sort(values);
		return values[values.length - 1];
	}

	public static int max(int typeA, int typeB, int typeC, int typeD) {
		// TODO Auto-generated method stub
		int[] arr = { typeA, typeB, typeC, typeD };
		Arrays.sort(arr);
		return arr[3];
	}

	public static int min(Integer[] values) {
		// TODO Auto-generated method stub
		Arrays.sort(values);
		return values[0];
	}

	public static int min(List<Integer> values) {
		Integer [] arrs = values.toArray(new Integer[values.size()]);
		return min(arrs);
	}

//	public static Map<String, Integer> summary(List<Integer> suptLinks) {
//		int mean = mean(suptLinks);
//		int median = median(suptLinks);
//		int min = min(suptLinks);
//		int max = max(suptLinks);
//		int sd = sd(suptLinks);
//		Map<String, Integer> values = new HashMap<String, Integer>();
//		values.put("MEAN", mean);
//		values.put("MEDIAN", median);
//		values.put("MIN", min);
//		values.put("MAX", max);
//		values.put("SD", sd);
//		return values;
//	}

}
