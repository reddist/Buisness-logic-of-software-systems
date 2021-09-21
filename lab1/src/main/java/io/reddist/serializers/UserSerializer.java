package io.reddist.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.reddist.entities.CV;
import io.reddist.entities.JobApply;
import io.reddist.entities.User;

import java.io.IOException;

//https://www.baeldung.com/jackson-custom-serialization
public class UserSerializer extends StdSerializer<User> {

    //НЕ УДАЛЯТЬ! (нужен при сериализации)
    public UserSerializer() { this(null); }

    public UserSerializer(Class<User> t) { super(t); }

    @Override public void serialize(User user, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("userId", user.getUserId());

        jgen.writeFieldName("cvs");
        jgen.writeStartArray();
        for (CV cv: user.getCVs()) jgen.writeNumber(cv.getCvId());
        jgen.writeEndArray();

        jgen.writeFieldName("job_applies");
        jgen.writeStartArray();
        for (JobApply ja: user.getJobApplies()) {
            jgen.writeStartObject();
            jgen.writeFieldName("cv");
            jgen.writeNumber(ja.getCv().getCvId());
            jgen.writeFieldName("vacancy");
            jgen.writeNumber(ja.getVacancy().getVacancyId());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}