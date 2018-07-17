package com.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.AbcHttpClientDemoApplication;
import com.alibaba.fastjson.JSON;
import com.aspire.model.User;

@SpringBootTest(classes = { AbcHttpClientDemoApplication.class })
@RunWith(SpringRunner.class)
public class HttpClientDemoTest {
	/**
	 * GET---�޲β���
	 *
	 * @date 2018��7��13�� ����4:18:50
	 */
	@Test
	public void doGetTestOne() {
		// ���Http�ͻ���(�������Ϊ:�������һ�������;ע��:ʵ����HttpClient��������ǲ�һ����)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// ����Get����
		HttpGet httpGet = new HttpGet("http://localhost:12345/doGetControllerOne");

		// ��Ӧģ��
		CloseableHttpResponse response = null;
		try {
			// �ɿͻ���ִ��(����)Get����
			response = httpClient.execute(httpGet);
			// ����Ӧģ���л�ȡ��Ӧʵ��
			HttpEntity responseEntity = response.getEntity();
			System.out.println("��Ӧ״̬Ϊ:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("��Ӧ���ݳ���Ϊ:" + responseEntity.getContentLength());
				System.out.println("��Ӧ����Ϊ:" + EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// �ͷ���Դ
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * GET---�вβ��� (��ʽһ:�ֶ���url������ϲ���)
	 *
	 * @date 2018��7��13�� ����4:19:23
	 */
	@Test
	public void doGetTestWayOne() {
		// ���Http�ͻ���(�������Ϊ:�������һ�������;ע��:ʵ����HttpClient��������ǲ�һ����)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		// ����
		StringBuffer params = new StringBuffer();
		try {
			// �ַ��������encoding����;����һ����ĳЩ�����ַ����ܴ���ȥ(��:ĳ�˵����־��ǡ�&��,��encoding�Ļ�,������ȥ)
			params.append("name=" + URLEncoder.encode("&", "utf-8"));
			params.append("&");
			params.append("age=24");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		// ����Get����
		HttpGet httpGet = new HttpGet("http://localhost:12345/doGetControllerTwo" + "?" + params);
		// ��Ӧģ��
		CloseableHttpResponse response = null;
		try {
			// �ɿͻ���ִ��(����)Get����
			response = httpClient.execute(httpGet);

			// ������Ϣ
			RequestConfig requestConfig = RequestConfig.custom()
					// �������ӳ�ʱʱ��(��λ����)
					.setConnectTimeout(5000)
					// ��������ʱʱ��(��λ����)
					.setConnectionRequestTimeout(5000)
					// socket��д��ʱʱ��(��λ����)
					.setSocketTimeout(5000)
					// �����Ƿ������ض���(Ĭ��Ϊtrue)
					.setRedirectsEnabled(true).build();

			// �������������Ϣ ���õ����Get������
			httpGet.setConfig(requestConfig);

			// ����Ӧģ���л�ȡ��Ӧʵ��
			HttpEntity responseEntity = response.getEntity();
			System.out.println("��Ӧ״̬Ϊ:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("��Ӧ���ݳ���Ϊ:" + responseEntity.getContentLength());
				System.out.println("��Ӧ����Ϊ:" + EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// �ͷ���Դ
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * GET---�вβ��� (��ʽ��:�����������ֵ������,�ٷ���URI��,�Ӷ�ͨ��URI�õ�HttpGetʵ��)
	 *
	 * @date 2018��7��13�� ����4:19:23
	 */
	@Test
	public void doGetTestWayTwo() {
		// ���Http�ͻ���(�������Ϊ:�������һ�������;ע��:ʵ����HttpClient��������ǲ�һ����)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		// ����
		URI uri = null;
		try {
			// �����������ֵ����NameValuePair��,�ٷ��뼯����
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("name", "&"));
			params.add(new BasicNameValuePair("age", "18"));
			// ����uri��Ϣ,�����������Ϸ���uri;
			// ע:����Ҳ֧��һ����ֵ��һ����ֵ�Ե��������setParameter(String key, String value)
			uri = new URIBuilder().setScheme("http").setHost("localhost")
					              .setPort(12345).setPath("/doGetControllerTwo")
					              .setParameters(params).build();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		// ����Get����
		HttpGet httpGet = new HttpGet(uri);

		// ��Ӧģ��
		CloseableHttpResponse response = null;
		try {
			// �ɿͻ���ִ��(����)Get����
			response = httpClient.execute(httpGet);

			// ������Ϣ
			RequestConfig requestConfig = RequestConfig.custom()
					// �������ӳ�ʱʱ��(��λ����)
					.setConnectTimeout(5000)
					// ��������ʱʱ��(��λ����)
					.setConnectionRequestTimeout(5000)
					// socket��д��ʱʱ��(��λ����)
					.setSocketTimeout(5000)
					// �����Ƿ������ض���(Ĭ��Ϊtrue)
					.setRedirectsEnabled(true).build();

			// �������������Ϣ ���õ����Get������
			httpGet.setConfig(requestConfig);

			// ����Ӧģ���л�ȡ��Ӧʵ��
			HttpEntity responseEntity = response.getEntity();
			System.out.println("��Ӧ״̬Ϊ:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("��Ӧ���ݳ���Ϊ:" + responseEntity.getContentLength());
				System.out.println("��Ӧ����Ϊ:" + EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// �ͷ���Դ
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * POST---�޲β���
	 *
	 * @date 2018��7��13�� ����4:18:50
	 */
	@Test
	public void doPostTestOne() {

		// ���Http�ͻ���(�������Ϊ:�������һ�������;ע��:ʵ����HttpClient��������ǲ�һ����)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		// ����Post����
		HttpPost httpPost = new HttpPost("http://localhost:12345/doPostControllerOne");
		// ��Ӧģ��
		CloseableHttpResponse response = null;
		try {
			// �ɿͻ���ִ��(����)Post����
			response = httpClient.execute(httpPost);
			// ����Ӧģ���л�ȡ��Ӧʵ��
			HttpEntity responseEntity = response.getEntity();

			System.out.println("��Ӧ״̬Ϊ:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("��Ӧ���ݳ���Ϊ:" + responseEntity.getContentLength());
				System.out.println("��Ӧ����Ϊ:" + EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// �ͷ���Դ
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * POST---�вβ���(�������)
	 *
	 * @date 2018��7��13�� ����4:18:50
	 */
	@Test
	public void doPostTestTwo() {

		// ���Http�ͻ���(�������Ϊ:�������һ�������;ע��:ʵ����HttpClient��������ǲ�һ����)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		// ����Post����
		HttpPost httpPost = new HttpPost("http://localhost:12345/doPostControllerTwo");
		User user = new User();
		user.setName("������");
		user.setAge(18);
		user.setGender("Ů");
		user.setMotto("����Ҫ����~");
		// ���������ð����fastjson����Objectת��Ϊjson�ַ���;
		// (��Ҫ����com.alibaba.fastjson.JSON��)
		String jsonString = JSON.toJSONString(user);

		StringEntity entity = new StringEntity(jsonString, "UTF-8");

		// post�����ǽ������������������洫��ȥ��;���ｫentity����post��������
		httpPost.setEntity(entity);

		httpPost.setHeader("Content-Type", "application/json;charset=utf8");

		// ��Ӧģ��
		CloseableHttpResponse response = null;
		try {
			// �ɿͻ���ִ��(����)Post����
			response = httpClient.execute(httpPost);
			// ����Ӧģ���л�ȡ��Ӧʵ��
			HttpEntity responseEntity = response.getEntity();

			System.out.println("��Ӧ״̬Ϊ:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("��Ӧ���ݳ���Ϊ:" + responseEntity.getContentLength());
				System.out.println("��Ӧ����Ϊ:" + EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// �ͷ���Դ
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * POST---�вβ���(�������� + �������)
	 *
	 * @date 2018��7��13�� ����4:18:50
	 */
	@Test
	public void doPostTestThree() {

		// ���Http�ͻ���(�������Ϊ:�������һ�������;ע��:ʵ����HttpClient��������ǲ�һ����)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		// ����Post����
		// ����
		URI uri = null;
		try {
			// �����������ֵ����NameValuePair��,�ٷ��뼯����
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("flag", "4"));
			params.add(new BasicNameValuePair("meaning", "����ʲô��"));
			// ����uri��Ϣ,�����������Ϸ���uri;
			// ע:����Ҳ֧��һ����ֵ��һ����ֵ�Ե��������setParameter(String key, String value)
			uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(12345)
					.setPath("/doPostControllerThree").setParameters(params).build();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		HttpPost httpPost = new HttpPost(uri);
		// HttpPost httpPost = new
		// HttpPost("http://localhost:12345/doPostControllerThree1");

		// ����user����
		User user = new User();
		user.setName("������");
		user.setAge(18);
		user.setGender("Ů");
		user.setMotto("����Ҫ����~");

		// ��user����ת��Ϊjson�ַ�����������entity��
		StringEntity entity = new StringEntity(JSON.toJSONString(user), "UTF-8");

		// post�����ǽ������������������洫��ȥ��;���ｫentity����post��������
		httpPost.setEntity(entity);

		httpPost.setHeader("Content-Type", "application/json;charset=utf8");

		// ��Ӧģ��
		CloseableHttpResponse response = null;
		try {
			// �ɿͻ���ִ��(����)Post����
			response = httpClient.execute(httpPost);
			// ����Ӧģ���л�ȡ��Ӧʵ��
			HttpEntity responseEntity = response.getEntity();

			System.out.println("��Ӧ״̬Ϊ:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("��Ӧ���ݳ���Ϊ:" + responseEntity.getContentLength());
				System.out.println("��Ӧ����Ϊ:" + EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// �ͷ���Դ
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * POST---�вβ���(��������)
	 *
	 * @date 2018��7��13�� ����4:18:50
	 */
	@Test
	public void doPostTestFour() {

		// ���Http�ͻ���(�������Ϊ:�������һ�������;ע��:ʵ����HttpClient��������ǲ�һ����)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		// ����
		StringBuffer params = new StringBuffer();
		try {
			// �ַ��������encoding����;����һ����ĳЩ�����ַ����ܴ���ȥ(��:ĳ�˵����־��ǡ�&��,��encoding�Ļ�,������ȥ)
			params.append("name=" + URLEncoder.encode("&", "utf-8"));
			params.append("&");
			params.append("age=24");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		// ����Post����
		HttpPost httpPost = new HttpPost("http://localhost:12345/doPostControllerFour" + "?" + params);

		// ����ContentType(ע:���ֻ�Ǵ���ͨ�����Ļ�,ContentType��һ����Ҫ��application/json)
		httpPost.setHeader("Content-Type", "application/json;charset=utf8");

		// ��Ӧģ��
		CloseableHttpResponse response = null;
		try {
			// �ɿͻ���ִ��(����)Post����
			response = httpClient.execute(httpPost);
			// ����Ӧģ���л�ȡ��Ӧʵ��
			HttpEntity responseEntity = response.getEntity();

			System.out.println("��Ӧ״̬Ϊ:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("��Ӧ���ݳ���Ϊ:" + responseEntity.getContentLength());
				System.out.println("��Ӧ����Ϊ:" + EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// �ͷ���Դ
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
