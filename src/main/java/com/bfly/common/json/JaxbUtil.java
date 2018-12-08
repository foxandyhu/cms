package com.bfly.common.json;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * xml操作工具类
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/6 16:05
 */
public class JaxbUtil {

    /**
     * 对象转为字符串
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/6 16:05
     */
    public static String toXml(Object obj, String encoding) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            if (encoding != null) {
                marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            }
            marshaller.marshal(obj, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转为对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/6 16:05
     */
    public static <T> T toObject(String xml, Class<T> cls) {
        try {
            StringReader reader = new StringReader(xml);
            JAXBContext jaxbContext = JAXBContext.newInstance(cls);
            return (T) jaxbContext.createUnmarshaller().unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
