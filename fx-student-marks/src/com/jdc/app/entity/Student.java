package com.jdc.app.entity;

import lombok.Getter;

@Getter
public class Student {

	private String name;
	private int myan;
	private int eng;
	private int maths;
	private int che;
	private int phy;
	private int bio;
	
	private int[] marks;

	public Student(String line) {
		String[] arr = line.split("\t");
		name = arr[0];
		myan = Integer.parseInt(arr[1]);
		eng = Integer.parseInt(arr[2]);
		maths = Integer.parseInt(arr[3]);
		che = Integer.parseInt(arr[4]);
		phy = Integer.parseInt(arr[5]);
		bio = Integer.parseInt(arr[6]);
		
		marks = new int[]{myan, eng, maths, che, phy, bio};
	}

	public String getAverage() {
		return String.format("%.1f", getTotal() / 6.0);
	}
	
	public int getTotal() {
		return myan + eng + maths + che + phy + bio;
	}

	public String getResult() {
		
		int dist = 0;
		
		for(int mark : marks) {
			if(mark < 40) {
				return "Fail";
			}
			else if(mark >= 80) {
				dist++;
			}
		}
		
		return dist > 0 ? String.format("%d%s", dist, "D") : "Pass";
	}

}
