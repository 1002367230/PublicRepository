package com.aspire.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.model.KeyAndValueModel;
import com.aspire.model.User;

/**
 * HttpClient����
 *
 * @author JustryDeng
 * @date 2018��7��13�� ����4:05:57
 */
@RestController
public class HttpClientController {

	/**
	 * GET�޲�
	 *
	 * @return ��������
	 * @date 2018��7��13�� ����5:29:44
	 */
	@RequestMapping("/doGetControllerOne")
	public String doGetControllerOne() {
		return "123";
	}

	/**
	 * GET�в�
	 *
	 * @param name
	 *            ����
	 * @param age
	 *            ����
	 * @return ��������
	 * @date 2018��7��13�� ����5:29:47
	 */
	@RequestMapping("/doGetControllerTwo")
	public String doGetControllerTwo(String name, Integer age) {
		return "û�뵽[" + name + "]��" + age + "����!";
	}

	/**
	 * POST�޲�
	 *
	 * @return ��������
	 * @date 2018��7��13�� ����5:29:49
	 */
	@RequestMapping(value = "/doPostControllerOne", method = RequestMethod.POST)
	public String doPostControllerOne() {
		return "���post����û���κβ���!";
	}

	/**
	 * POST�в�(�������)
	 *
	 * @return ��������
	 * @date 2018��7��13�� ����5:29:52
	 */
	@RequestMapping(value = "/doPostControllerTwo", method = RequestMethod.POST)
	public String doPostControllerTwo(@RequestBody User user) {
		return user.toString();
	}

	/**
	 * POST�в�(��ͨ���� + �������)
	 *
	 * @return ��������
	 * @date 2018��7��13�� ����5:29:52
	 */
	@RequestMapping(value = "/doPostControllerThree", method = RequestMethod.POST)
	public String doPostControllerThree(@RequestBody User user,Integer flag, String meaning) {
		return user.toString() + "\n" + flag + ">>>" + meaning;
	}
	
	/**
	 * POST�в�(��ͨ����)
	 *
	 * @return ��������
	 * @date 2018��7��14�� ����10:54:29
	 */
	@RequestMapping(value = "/doPostControllerFour", method = RequestMethod.POST)
	public String doPostControllerThree1(String name, Integer age) {
		return "[" + name + "]��Ȼ��[" + age + "]��!!!";
	}

}
