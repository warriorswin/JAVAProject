package cn.hdu.HDU_Minitor.util;
import java.text.DecimalFormat;
import java.util.concurrent.Executors;  
import java.util.concurrent.ScheduledExecutorService;  
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


  
public class MqttClientUtil{  
  
    public static final String HOST = "tcp://39.105.100.144:1883";  
    public static final String TOPIC = "$neucrack/gprs";  
    private static final String clientid = "client124";  
    private MqttClient client;  
    private MqttConnectOptions options;  
    private String userName = "mqtt";
    private String passWord = "mqtt";
  
    private ScheduledExecutorService scheduler;  
  
    private void start() {  
        try {  
            // hostΪ��������clientid������MQTT�Ŀͻ���ID��һ����Ψһ��ʶ����ʾ��MemoryPersistence����clientid�ı�����ʽ��Ĭ��Ϊ���ڴ汣��  
            client = new MqttClient(HOST, clientid, new MemoryPersistence());  
            // MQTT����������  
            options = new MqttConnectOptions();  
            // �����Ƿ����session,�����������Ϊfalse��ʾ�������ᱣ���ͻ��˵����Ӽ�¼����������Ϊtrue��ʾÿ�����ӵ������������µ��������  
            options.setCleanSession(true);  
            // �������ӵ��û���  
            options.setUserName(userName);  
            // �������ӵ�����  
            options.setPassword(passWord.toCharArray());  
            // ���ó�ʱʱ�� ��λΪ��  
            options.setConnectionTimeout(10);  
            // ���ûỰ����ʱ�� ��λΪ�� ��������ÿ��1.5*20���ʱ����ͻ��˷��͸���Ϣ�жϿͻ����Ƿ����ߣ������������û�������Ļ���  
            options.setKeepAliveInterval(20);  
            // ���ûص�  
            client.setCallback(new PushCallback());  
            MqttTopic topic = client.getTopic(TOPIC);  
            //setWill�����������Ŀ����Ҫ֪���ͻ����Ƿ���߿��Ե��ø÷������������ն˿ڵ�֪ͨ��Ϣ    
            options.setWill(topic, "close".getBytes(), 2, true);  
              
            client.connect(options);  
            //������Ϣ  
            int[] Qos  = {1};  
            String[] topic1 = {TOPIC};  
            client.subscribe(topic1, Qos);  
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
   //�������Զ�����һ���̴߳���
    public static void main(String[] args) throws MqttException {     
    	//MqttClientUtil client = new MqttClientUtil();  
        //client.start();  
    	DecimalFormat df = new DecimalFormat( "0.00 ");
    	double d1 = 1.0;
    	double d2 = 4.56789;
    	System.out.println(df.format(d1));
    	System.out.println(df.format(d2));
    	
    }  
}
 class PushCallback implements MqttCallback {  
	  
    public void connectionLost(Throwable cause) {  
        // ���Ӷ�ʧ��һ�����������������  
        System.out.println("���ӶϿ�������������");  
    }  
    
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());  
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe��õ�����Ϣ��ִ�е�������  
        System.out.println("������Ϣ���� : " + topic);  
        System.out.println("������ϢQos : " + message.getQos());  
        System.out.println("������Ϣ���� : " + new String(message.getPayload()));  
    }  
}
