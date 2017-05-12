package com.courier.db.dao.crud;

import com.courier.commons.entity.IdEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 解析POJO的类
 * @author Ryan
 */
public class EntityUtil {
	private static final long serialVersionUID = 1L;

	/**
	 * 获取POJO对应的表名
	 * 需要POJO中的属性定义@Table(name)
	 * @return
	 * @throws Exception 
	 */
	public static String tablename(Object obj) throws Exception {
		Table table = obj.getClass().getAnnotation(Table.class);
		if(table != null)
			return table.name();
		else
			throw new Exception("undefine POJO @Table, need Tablename(@Table(name))");
	}
	
	/**
	 * 用于计算类定义
	 * 需要POJO中的属性定义@Column(name)
	 */
	public static void caculationColumnList(Object obj) {
		if(columnMap.containsKey(obj.getClass()))
			return;
		
		BeanInfo intro = null;
		try {
			intro = Introspector.getBeanInfo(obj.getClass());
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertyDescriptor[] propertyDescriptors = intro.getPropertyDescriptors();
		List<PropertyDescriptor> columnList = new ArrayList<PropertyDescriptor>(propertyDescriptors.length);
		for (PropertyDescriptor p : propertyDescriptors) {
			Method method = p.getReadMethod();
			if (method.isAnnotationPresent(Column.class)) {
				columnList.add(p);
			}
		}
		columnMap.put(obj.getClass(), columnList);
	}
	
	/**
	 * 获取POJO中的主键字段名
	 * 需要定义@Id
	 * @return
	 */
	public static String id(Object obj) {
		String idName = "";
		try {
			BeanInfo intro = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = intro.getPropertyDescriptors();
			for (PropertyDescriptor p : propertyDescriptors) {
				Method method = p.getReadMethod();
				if (method.isAnnotationPresent(Id.class)) {
					idName = getPropertyColumnName(p);
					if (null == idName) {
						idName = p.getName();
					}
					break;
				}
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException("undefine POJO @Id");
		}
		return idName;
	}

	/**
	 * 获取POJO的主键字段名所对应的值
	 * @param obj
	 * @return
	 */
	public static String idValue(Object obj) {
		String idValue = "";
		try{
			BeanInfo info = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
			for(PropertyDescriptor p : propertyDescriptors){
				Method method = p.getReadMethod();
				if(method.isAnnotationPresent(Id.class)){
					idValue = BeanUtils.getProperty(obj, p.getName());
				}
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException("undefine POJO @Id");
		} catch (IllegalAccessException e) {
			throw new RuntimeException("undefine POJO @Id");
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("undefine POJO @Id");
		} catch (InvocationTargetException e) {
			throw new RuntimeException("undefine POJO @Id");
		}
		return idValue;
	}
	
	/**
	 * 获得列Column的数字库列名
	 * @param p
	 * @return
	 */
	public static String getPropertyColumnName(PropertyDescriptor p) {
		String methodName = null;
		Method method = p.getReadMethod();
		if (method.isAnnotationPresent(Column.class)) {
			Annotation columnAnnotation = method.getAnnotation(Column.class);
			if (null != columnAnnotation) {
				Column column = (Column)columnAnnotation;
				String columnName = column.name();
				if (StringUtils.isNotBlank(columnName)) {
					methodName = columnName;
				}
			}
			if (StringUtils.isBlank(methodName)) {
				methodName = p.getName();
			}
		}
		return methodName;
	}
	
	/**
	 * 获取用于WHERE的 有值字段表
	 * @return
	 */
	public static List<WhereColumn> returnWhereColumnsName(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		List<WhereColumn> columnList = new ArrayList<WhereColumn>(fields.length);
		
		for(Field field : fields) {
			if(field.isAnnotationPresent(Column.class) && !isNull(obj, field)) 
				columnList.add(new WhereColumn(field.getName(), field.getGenericType().equals(String.class)));
		}
		
		return columnList;
	}

	/**
	 * Where条件信息
	 */
	public static class WhereColumn {
		public String name;
		public boolean isString;
		
		public WhereColumn(String name,boolean isString) {
			this.name = name;
			this.isString = isString;
		}
	}

	/**
	 * 用于获取Select的字段映射
	 * @param obj
	 * @return
	 */
	public static String returnSelectColumnsName(Object obj) {
		StringBuilder sb = new StringBuilder();

		List<PropertyDescriptor> propertyDescriptorList = columnMap.get(obj.getClass());
		
		for (int i = 0; i < propertyDescriptorList.size(); i ++) {
			PropertyDescriptor p = propertyDescriptorList.get(i);
			sb.append(getPropertyColumnName(p));
			sb.append(" as ");
			sb.append(p.getName());
			if (i != propertyDescriptorList.size() - 1) {
				sb.append(" , ");
			}
		}
		
		return sb.toString();
	}
	/**
	 * 用于获取Insert的字段累加
	 * @return
	 * @throws Exception 
	 */
	public static String returnInsertColumnsName(Object obj) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		List<PropertyDescriptor> propertyDescriptorList = columnMap.get(obj.getClass());
		int i = 0;
		
		String idName = id(obj);
		
		for(PropertyDescriptor p : propertyDescriptorList) {
			if (!idName.equals(p.getName()) && isNull(obj, p)) {
				continue;
			}
			if(i++ != 0) {
				sb.append(',');
			}
			
			sb.append(getPropertyColumnName(p));
		}
		return sb.toString();
	}

	/**
	 * 用于去重字段累加
	 * @param obj
	 * @param fieldSet
	 * @return
	 * @throws Exception
	 */
	public static String returnDistinctColumnsName(Object obj, LinkedHashSet<String> fieldSet) throws Exception{
		StringBuilder sb = new StringBuilder();

		if(fieldSet != null && !fieldSet.isEmpty()) {
			List<PropertyDescriptor> propertyDescriptorList = columnMap.get(obj.getClass());
			int i = 0;

			for (PropertyDescriptor p : propertyDescriptorList) {
				if(fieldSet.contains(p.getName())){
					if(i++ != 0){
						sb.append(',');
					}
					sb.append(getPropertyColumnName(p));
					sb.append(" as ");
					sb.append(p.getName());
				}
			}
		}

		return sb.toString();
	}


	/**
	 * 用于获取Insert的字段映射累加
	 * @return
	 */
	public static String returnInsertColumnsDefine(Object obj) {
		StringBuilder sb = new StringBuilder();

		String idName = id(obj);
		
		List<PropertyDescriptor> propertyDescriptorList = columnMap.get(obj.getClass());
		int i = 0;
		for(PropertyDescriptor p : propertyDescriptorList) {
			String column = p.getName();
			if (!idName.equals(column) && isNull(obj, p)) {
				continue;
			}

			Class propertyTypeClass = p.getPropertyType();
			// 不为基本数据类型。为其它的映射bean
			if (!(propertyTypeClass == Byte.class
					|| propertyTypeClass == Character.class
					|| propertyTypeClass == Boolean.class
					|| propertyTypeClass == Integer.class
					|| propertyTypeClass == Long.class
					|| propertyTypeClass == Float.class
					|| propertyTypeClass == Double.class
					|| propertyTypeClass == String.class
					|| propertyTypeClass == Date.class)) {
				try {
					IdEntity o = (IdEntity)p.getReadMethod().invoke(obj);
					column +="."+idBeanName(o);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
			if(i++ != 0)
				sb.append(',');
			sb.append("#{").append(column).append('}');
		}
		return sb.toString();
	}
	
	public static String returnWhereDefine(Object obj, List<SearchFilter> searchFilterList) {
		StringBuilder sb = new StringBuilder();
		List<PropertyDescriptor> propertyDescriptorList = columnMap.get(obj.getClass());
		
		for (int i = 0; i < searchFilterList.size(); i ++) {
			SearchFilter searchFilter = searchFilterList.get(i);

			if(searchFilter.bLBracket)
				sb.append(" ( ");

			for (int j = 0; j < propertyDescriptorList.size(); j ++) {
				PropertyDescriptor p = propertyDescriptorList.get(j);
				if (p.getName().equalsIgnoreCase(searchFilter.fieldName)) {
					sb.append(getPropertyColumnName(p));
					break;
				}
			}

			switch (searchFilter.operator) {
			case EQ:
				sb.append(" = ");
				sb.append("#{param2["+i+"].value}");
				break;
			case GT:
				sb.append(" > ");
				sb.append("#{param2["+i+"].value}");
				break;
			case LT:
				sb.append(" < ");
				sb.append("#{param2["+i+"].value}");
				break;
			case GTE:
				sb.append(" >= ");
				sb.append("#{param2["+i+"].value}");
				break;
			case LTE:
				sb.append(" <= ");
				sb.append("#{param2["+i+"].value}");
				break;
			case LEFT_LIKE:
				sb.append(" like ");
				searchFilter.value= "%"+searchFilter.value;
				sb.append("#{param2["+i+"].value}");
				break;
			case RIGHT_LIKE:
				sb.append(" like ");
				searchFilter.value = searchFilter.value + "%";
				sb.append("#{param2["+i+"].value}");
				break;
			case LIKE:
				sb.append(" like ");
				searchFilter.value = "%" + searchFilter.value + "%";
				sb.append("#{param2["+i+"].value}");
				break;
//			case IN:
//				sb.append(" in ");
////				List<Object> lst = (List<Object>)searchFilter.value;
//				sb.append("( #{param2["+i+"].value} )");
//				searchFilter.value = "(" + searchFilter.value + ")";
//				sb.append("#{param2["+i+"].value}");
			}

			if(searchFilter.bRBracket)
				sb.append(" ) ");

			if(i != searchFilterList.size() - 1)
				linkSql(sb, searchFilterList.get(i+1).link);
		}
		return sb.toString();
	}

	public static String returnOrderDefine(){
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}

	/**
	 * 用于获取Update Set的字段累加
	 * @return
	 */
	public static String returnWhereDefine(Object obj) {
		StringBuilder sb = new StringBuilder();
		
		List<PropertyDescriptor> propertyDescriptorList = columnMap.get(obj.getClass());
		int i = 0;
		for(PropertyDescriptor p : propertyDescriptorList) {
			if(i++ != 0)
				sb.append(',');
			sb.append(getPropertyColumnName(p));

			if (isNull(obj, p)) {
				appendSqlNull(sb, p);
				continue;
			}
			
			Class propertyTypeClass = p.getPropertyType();
			
			String column = p.getName();
			
			// 不为基本数据类型。为其它的映射bean
			if (!(propertyTypeClass == Byte.class
					|| propertyTypeClass == Character.class
					|| propertyTypeClass == Boolean.class
					|| propertyTypeClass == Integer.class
					|| propertyTypeClass == Long.class
					|| propertyTypeClass == Float.class
					|| propertyTypeClass == Double.class
					|| propertyTypeClass == String.class
					|| propertyTypeClass == Date.class)) {
				try {
					IdEntity o = (IdEntity)p.getReadMethod().invoke(obj);
					column +="."+ idBeanName(o);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}

			sb.append("=#{").append(column).append('}');
		}
		return sb.toString();
	}
	
	public static String idBeanName(IdEntity obj) {
		String idName = "";
		try {
			BeanInfo intro = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = intro.getPropertyDescriptors();
			for (PropertyDescriptor p : propertyDescriptors) {
				Method method = p.getReadMethod();
				if (method.isAnnotationPresent(Id.class)) {
					idName = p.getName();
					break;
				}
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException("undefine POJO @Id");
		}
		return idName;
	}
	
	/**
	 * 用于存放POJO的列信息
	 */
	private static Map<Class, List<PropertyDescriptor>> columnMap = new HashMap<Class, List<PropertyDescriptor>>();
	
	private static boolean isNull(Object obj, PropertyDescriptor propertyDescriptor) {
		try {
			return propertyDescriptor.getReadMethod().invoke(obj) == null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return true;
	}

	private static boolean isNotNull(Object obj, PropertyDescriptor propertyDescriptor) {
		return !isNull(obj, propertyDescriptor);
	}
	
	private static boolean isNull(Object obj, Field field) {
		try {
			field.setAccessible(true);
			return field.get(obj) == null;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private static boolean isNull(Object obj, String fieldname) {
		try {
			Field field = obj.getClass().getDeclaredField(fieldname);
			return isNull(obj, field);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	private static void linkSql(StringBuilder sb, SearchFilter.Link link){
		switch (link){
			case AND:
				sb.append(" and ");
				break;
			case OR:
				sb.append(" or ");
				break;
		}
	}

	private static void appendSqlNull(StringBuilder sb, PropertyDescriptor p){
		Method method = p.getReadMethod();
		sb.append(" = null");
//		if(method.isAnnotationPresent(Column.class) && method.isAnnotationPresent(Temporal.class))
//			sb.append(" = null");
//		else
//			sb.append(" = null");
	}

}
