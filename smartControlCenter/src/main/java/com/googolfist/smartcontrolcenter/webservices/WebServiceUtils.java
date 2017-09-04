package com.googolfist.smartcontrolcenter.webservices;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

/*import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;*/
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/6/2.
 */

public class WebServiceUtils {
    private static final String TAG = "WebServiceUtils";
    private static String ENDPOIT = "http://192.168.1.241:8088/GWServices.asmx";
    private static final String NAME_SPACE = "http://tempuri.org/";
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * @param url 域名或地址加端口号 http://192.168.1.2:8088
     */
    public static void setSerUrl(String url) {
        ENDPOIT = url.concat("/GWServices.asmx");
    }

    private static final HandlerThread mHandlerThread = new HandlerThread("webserviceUtils");

    /**
     * @param action     方法
     * @param properties 参数表
     * @param callback   回调对象
     */
    public static void callWebService(final int action, final HashMap<String, String> properties,
                                      final WebServiceCallback callback) {/*
        final String methodName = WebServiceAction.getActionName(action);
        SoapObject rpc = new SoapObject(NAME_SPACE, methodName);
        if (properties != null) {
            for (Iterator<Map.Entry<String, String>> it = properties.entrySet()
                    .iterator(); it.hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                rpc.addProperty(entry.getKey(), entry.getValue());
            }
        }

        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        final HttpTransportSE transportSE = new HttpTransportSE(ENDPOIT);
        transportSE.debug = true;

        //final HandlerThread mHandlerThread = new HandlerThread("webserviceUtils");

        //mhandler.getLooper()
        if (mHandlerThread.isAlive() == false) {
            mHandlerThread.start();
        }
        final Handler mhandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.obj != null) {
                    callback.callback(msg);
                } else {
                    Log.e(TAG, "handleMessage msg.obj is null");
                }
            }
        };

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                SoapObject resultObject = null;
                try {
                    //Log.d(TAG, "http transport se call");
                    transportSE.call(NAME_SPACE.concat(methodName), envelope);
                    if (envelope.getResponse() != null) {
                        // 获取服务器响应返回的SoapObject
                        resultObject = (SoapObject) envelope.bodyIn;
                    }
                } catch (HttpResponseException e) {
                    Log.e(TAG, "HttpResponseException: ", e);
                } catch (IOException e) {
                    Log.e(TAG, "IOException: 服务器IO错误", e);
                } catch (XmlPullParserException e) {
                    Log.e(TAG, "XmlPullParserException: ", e);
                } finally {
                    if(action == WebServiceAction.REAL_TIME_DATA) {
                        String pty = properties.get("tableName");
                        String equipNo = properties.get("selectedEquipNo");
                        resultObject.addProperty("tableName", pty);
                        resultObject.addProperty("equipNo", equipNo);
                    }else if(action == WebServiceAction.EXPRESSION_EVAL) {
                        String exp = properties.get("expression");
                        String equipNo = exp.replace("$E(", "");
                        equipNo = equipNo.replace(")", "");
                        resultObject.addProperty("equipNo", equipNo);
                    }
                    mhandler.sendMessage(mhandler.obtainMessage(action, resultObject));
                }

            }
        });*/
    }


}
