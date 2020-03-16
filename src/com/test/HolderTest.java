package com.test;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.san.holder.Holder;
import com.san.mapping.Mapping;

public class HolderTest {
	
	@Test
	public void testHolderSingleton() {
		Holder holder1 = Holder.getHolderInstance();
		Holder holder2 = Holder.getHolderInstance();
		
		assertEquals(holder1, holder2);
	}

	@Test
	public void testHolderMethodInvocation() {
		Holder holder = Holder.getHolderInstance();
		holder.scan();

		String expectedResult = "hello";
		Mapping m = holder.getMapping().get("/controllerPath/methodPath");
		try {
			String actualResult = (String)m.getMethod().invoke(m.getObject(), null);
			
			assertEquals(expectedResult, actualResult);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
