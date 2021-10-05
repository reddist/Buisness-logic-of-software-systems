package io.reddist.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.reddist.entities.CV;
import io.reddist.entities.JobApply;

import java.io.IOException;

public class CVSerializer extends StdSerializer<CV> {

    //НЕ УДАЛЯТЬ! (нужен при сериализации)
    public CVSerializer() { this(null); }

    public CVSerializer(Class<CV> t) { super(t); }

    @Override public void serialize(CV cv, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("cv_id", cv.getCvId());
        jgen.writeFieldName("user");
        jgen.writeStartObject();
        jgen.writeNumberField("user_id", cv.getUser().getUserId());
        jgen.writeStringField("user_mail", cv.getUser().getEmail());
        jgen.writeEndObject();
        jgen.writeFieldName("job_applies");
        jgen.writeStartArray();
        for (JobApply ja: cv.getJobApplies()) {
            jgen.writeStartObject();
            jgen.writeNumberField("vacancy_id", ja.getVacancy().getVacancyId());
            jgen.writeStringField("vacancy_desc", ja.getVacancy().getDescription());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}
