package com.san.app.main;

import com.san.holder.Holder;

public class App {
	public static void main(String[] args) {
		Holder holder = Holder.getHolderInstance();
		holder.scan();
		
		System.out.println(holder.getMapping());
		
	}
}
