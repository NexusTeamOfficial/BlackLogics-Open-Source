package com.shapun.layouteditor.utils;

import com.shapun.layouteditor.utils.PrimitiveUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import android.content.Context;
import android.view.View;
import java.lang.reflect.*;


public class ReflectionUtils{
	
	public static void setField(Object target,String memberName,Object argument)throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
		Class<?> cls = target.getClass();
		Field field= cls.getField(memberName);
		field.set(target, argument);
	}
	
	
	public static void invokeMethod(Object target,String methodName,Object... arguments)throws ClassNotFoundException,NoSuchMethodException,IllegalAccessException,java.lang.reflect.InvocationTargetException{
		
		Class<?> argumentClasses[] = new Class<?>[arguments.length];
		
		for(int i= 0;i <arguments.length;i++){
			argumentClasses[i] = arguments[i].getClass();
		}
		
		
		Method method = getMethod(target.getClass(),methodName,argumentClasses);
		
		
		method.invoke(target, arguments);
		
	}
	
	
	public static View createView(Context context,String classPath)throws ClassNotFoundException,InstantiationException,InvocationTargetException,NoSuchMethodException,IllegalAccessException{
		Class<?> cls = Class.forName(classPath);
		java.lang.reflect.Constructor<?> ct = cls.getConstructor(Context.class);
		Object view = ct.newInstance(context); 
		return (View)view;	
	}
	
	
	
	public static java.lang.reflect.Method getMethod(Class<?> cls,String methodName,Class... argumentClasses)throws ClassNotFoundException,NoSuchMethodException,IllegalAccessException,java.lang.reflect.InvocationTargetException{
		
		Method[] methods = cls.getMethods(); 
		
		for(Method method :methods){
			
			if(! method.getName().equals(methodName)) continue;
			Class<?>[] parameters = method.getParameterTypes();
			if(parameters.length != argumentClasses.length) continue;
			
			for(int i= 0; i<parameters.length;i++){
				Class<?> parameter = parameters[i];
				
				if(!parameter.isAssignableFrom(argumentClasses[i])){
					if(parameter.isPrimitive()){
						if(! parameter.isAssignableFrom(PrimitiveUtils.getPrimitiveType(argumentClasses[i]))) break;
					}
				}
				
				if(i== parameters.length-1) return method;
				
			}
			
			
		}
		return null;
	}
	
	
	
	
	
	
	public static Object getStaticFieldValue(String fieldFullPath) throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
		return getStaticField(fieldFullPath).get(null);
	}
	
	
	public static Field getStaticField(String fieldFullPath) throws ClassNotFoundException,NoSuchFieldException,IllegalAccessException{
		
		String path = fieldFullPath.substring(0,fieldFullPath.lastIndexOf("$"));
		
		String fieldName = fieldFullPath.substring(fieldFullPath.lastIndexOf("$")+1,fieldFullPath.length());
		
		Class<?> cls = Class.forName(path);
		
		return cls.getField(fieldName);
	}
	
	public static boolean isValidClass(String classPath){
		try{
			Class.forName(classPath);
			return true;
		}catch(Exception e){}
		return false;
	} 
	
	
}
