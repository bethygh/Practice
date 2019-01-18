package bgh.practice.spring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.Objects;

public class ObjectSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectSerializer.class);

    public static Object fromBytes(byte[] bytes) throws Exception {

        if (Objects.isNull(bytes)) {
            LOGGER.error("byte array to deserialize is null");
            throw new Exception("deserialization.byte.array.is.null");
        }
        LOGGER.debug("deserialize object - byte array {}", bytes);
        if (bytes.length == 0) {
            LOGGER.error("byte array to deserialize is empty");
            throw new Exception("deserialization.byte.array.is.empty");
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            return (Object) in.readObject();
        } catch (Exception e) {
            LOGGER.error("IO runtime error in deserializiation, objectInputStream.readObject - {}", e);
            throw new Exception("from.bytes.object.deserializing.objectInputStream.readObject", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                LOGGER.error("IO runtime error in deserializiation, objectInputStream.close - {}", e);
                throw new Exception("from.bytes.object.deserializing.objectInputStream.close", e);
            }
        }
    }

    public static byte[] toBytes(Object object) throws Exception {

        if (Objects.isNull(object)) {
            LOGGER.error("object to deserialize is null");
            throw new Exception("deserialization.byte.array.is.null");
        }
        LOGGER.debug("deserialize object - byte array {}", object);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            return bos.toByteArray();
        } catch (Exception e) {
            LOGGER.error("IO runtime error in deserializiation, objectOutputStream.writeObject - {}", e);
            throw new Exception("from.bytes.object.deserializing.objectOutputStream.writeObject", e);
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                LOGGER.error("IO runtime error in deserializiation, objectOutputStream.close - {}", e);
                throw new Exception("from.bytes.object.deserializing.objectOutputStream.close", e);
            }
        }
    }
}
