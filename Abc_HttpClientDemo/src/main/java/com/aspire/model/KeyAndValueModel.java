package com.aspire.model;

/**
 * ��ֵ��ģ�ͣ����ڽ��ռ�ֵ������
 *
 * @author JustryDeng
 * @date 2018��7��14�� ����2:16:53
 */
public class KeyAndValueModel {

    /** �� */
    private String key;
    
    /** ֵ */
    private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/* 
	 * ��дtoString()
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "key = " + key + ",value = " + value;
	}
    
    
}
