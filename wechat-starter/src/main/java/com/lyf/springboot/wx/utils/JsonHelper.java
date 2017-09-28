package com.lyf.springboot.wx.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonHelper {

  private static Logger logger = LoggerFactory.getLogger(JsonHelper.class);

  private static ObjectMapper mapper = new ObjectMapper();

  public static String toJsonString(Object o) {
    if (o == null) {
      return null;
    }

    try {
      String jsonString = mapper.writeValueAsString(o);
      return jsonString;
    } catch (IOException e) {
      logger.error("unknow error: {}", e);
      return null;
    }
  }

  public static <T> T fromJsonString(String json, Class<T> valueType) {
    if (StringUtils.isBlank(json)) {
      return null;
    }

    try {
      return mapper.readValue(json.getBytes("utf-8"), valueType);
    } catch (IOException e) {
      logger.error("can't parse json string: {}", json, e);
      e.printStackTrace();
      return null;
    }
  }
  public static <T> T fromJsonString(String json, TypeReference<T> type){
    if (StringUtils.isBlank(json)) {
      return null;
    }
    try {
      return mapper.readValue(json, type);
    } catch (IOException e) {
      logger.error("can't parse json string: {}", json, e);
      return null;
    }
  }

}
