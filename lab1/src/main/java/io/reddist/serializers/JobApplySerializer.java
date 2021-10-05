package io.reddist.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.reddist.entities.JobApply;

import java.io.IOException;

public class JobApplySerializer extends StdSerializer<JobApply> {

    //НЕ УДАЛЯТЬ! (нужен при сериализации)
    public JobApplySerializer() { this(null); }

    public JobApplySerializer(Class<JobApply> t) { super(t); }

    @Override public void serialize(JobApply jobApply, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("user_id", jobApply.getUser().getUserId());
        jgen.writeStringField("user_mail", jobApply.getUser().getEmail());
        jgen.writeNumberField("cv_id", jobApply.getCv().getCvId());
        jgen.writeNumberField("vacancy_id", jobApply.getVacancy().getVacancyId());
        jgen.writeStringField("vacancy_desc", jobApply.getVacancy().getDescription());
        jgen.writeEndObject();
    }
}
