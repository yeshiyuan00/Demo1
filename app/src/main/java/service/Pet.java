/**
 * 
 */
package service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description:
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class Pet implements Parcelable
{
	private String name;
	private double weight;
	public Pet()
	{
	}
	public Pet(String name, double weight)
	{
		super();
		this.name = name;
		this.weight = weight;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public double getWeight()
	{
		return weight;
	}
	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}
	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		//�Ѹö���������������д��Parcel
		dest.writeString(name);
		dest.writeDouble(weight);
	}

	// ���һ����̬��Ա,��ΪCREATOR,�ö���ʵ����Parcelable.Creator�ӿ�
	public static final Creator<Pet> CREATOR
		= new Creator<Pet>()
	{
		@Override
		public Pet createFromParcel(Parcel source)
		{
			// ��Parcel�ж�ȡ���ݣ�����Person����
			return new Pet(source.readString()
				, source.readDouble());
		}

		@Override
		public Pet[] newArray(int size)
		{
			return new Pet[size];
		}
	};
	@Override
	public String toString()
	{
		return "Pet [name=" + name + ", weight=" + weight + "]";
	}
}
