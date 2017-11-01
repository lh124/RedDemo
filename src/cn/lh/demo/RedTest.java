package cn.lh.demo;

import java.util.ArrayList;
import java.util.List;

public class RedTest {
	private static final float MINMONEY = 0.01f;//设置一个最小值
	private static final float MAXMONEY = 200f;//设置一个最大值
	private static final float TIMES = 2.1f;
	/**
	 * 判断金额是否超过最大金额
	 * @param money 当前设置的金额
	 * @param count 红包个数
	 * @return 如果是合法红包返回false
	 */
	private static boolean isRight(float money,int count)
	{
		double avg = money/count;//设置一个平均值，利用当前金额除以红包个数 
		if(avg<MINMONEY){ //如果平均值小于最小金额，有可能为负数，直接返回false
			return false;
		}
		else if(avg>MAXMONEY){//如果平均值大于最大金额，超过我所设置的最大金额，直接返回false
			return false;
		}
		return true;
	}

/**
 * 用随机方法产生一个在最大值和最小值之间的一个红包，并判断该红包是否合法，
 * 是否在产生这个红包之后红包金额变成负数。
 * 在这次产生红包值较小时，下一次就产生一个大一点的红包。
 * @param money 当前金额
 * @param mins 最小金额
 * @param maxs 最大金额
 * @param count 红包个数
 * @return 
 */
	private  static float randomRedPacket(float money,float mins,float maxs,int count){
		if(count==1){//只有一个红包的时候 就设置它当前的金额
			return (float)(Math.round(money*100))/100;//以分为单位 ，所以要乘以100
		}
		if(mins == maxs){
			return mins;//如果最大值和最小值一样，就返回mins
		}
		float max = maxs>money?money:maxs;
		float one = ((float)Math.random()*(max-mins)+mins);
		one = (float)(Math.round(one*100))/100;
		float moneyOther = money - one;
		if(isRight(moneyOther,count-1)){
			return one;
		}else{
			//重新分配
			float avg = moneyOther / (count-1);
			if(avg<MINMONEY){
				return randomRedPacket(money,mins,one,count);
			}else if(avg>MAXMONEY){
				return randomRedPacket(money,one,maxs,count);
			}
		}
		return one;
	}

/**
 * 为了避免一个红包占用大量的资金，设定非最后一个红包的最大金额，可以设置为平均值的N倍
 * @param money
 * @param count
 * @return
 */
	public static List<Float> splitRedPackets(float money,int count)	{
		if(!isRight(money,count)){
			return null;
		}
		List<Float> list = new ArrayList<Float>();
		float max = (float)(money*TIMES/count);
		max = max>MAXMONEY?MAXMONEY:max;
		for(int i=0;i<count;i++){
			float one = randomRedPacket(money,MINMONEY,max,count-i);
			list.add(one);
			money-=one;
		}
		return list;
	}
	public static void main(String[] args) {  

		System.out.println(RedTest.splitRedPackets(10, 5));   
	}
}
