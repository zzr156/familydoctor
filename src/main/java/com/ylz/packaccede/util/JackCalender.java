package com.ylz.packaccede.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.ylz.packcommon.common.util.ExtendDate;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by hzk on 2017/8/15.
 */
public class JackCalender extends JsonDeserializer<Calendar> {

    @Override
    public Calendar deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        return  ExtendDate.getCalendar(date);
    }
}
