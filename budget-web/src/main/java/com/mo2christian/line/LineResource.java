package com.mo2christian.line;

import com.mo2christian.common.converter.DateParamConverter;
import com.mo2christian.common.filter.ApiKey;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/line")
public class LineResource {

    @Inject
    Template line;

    @Inject
    @ResourcePath("line_info")
    Template lineInfo;

    @Inject
    Validator validator;

    private final LineService lineService;

    private final LineMapper mapper;

    @Inject
    public LineResource(LineService lineService) {
        this.lineService = lineService;
        mapper = new LineMapper();
    }

    @GET
    public TemplateInstance getAll(){
        return build(Collections.EMPTY_LIST);
    }

    @Path("/delete/{id}")
    @GET
    @Transactional
    public TemplateInstance delete(@PathParam("id") Long id){
        Optional<Line> line = lineService.get(id);
        if (!line.isPresent()){
            List<String> errors = Collections.singletonList(String.format("Line %d not found", id));
            return build(errors);
        }
        lineService.delete(line.get());
        return build(Collections.EMPTY_LIST);
    }

    @Path("/detail/{id}")
    @GET
    @Transactional
    public TemplateInstance detail(@PathParam("id") Long id){
        Optional<LineDto> line = lineService
                .get(id)
                .map(mapper::toDto);
        if (!line.isPresent()){
            List<String> errors = Collections.singletonList(String.format("Line %d not found", id));
            return build(errors);
        }
        return lineInfo.data("line", line.get());
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@Valid @BeanParam Field field){
        try{
            lineService.update(field);
        }
        catch(IllegalArgumentException ex){
            throw new WebApplicationException(ex, Response.Status.BAD_REQUEST);
        }
        catch (Exception ex){
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.ok().build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public TemplateInstance save(@BeanParam LineDto dto){
        List<String> errors = validate(dto);
        if (errors.isEmpty())
            lineService.add(mapper.toLine(dto));
        return build(errors);
    }

    private TemplateInstance build(List<String> errors){
        List<LineDto> dtos = lineService.getAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return line.data("lines", dtos)
                .data("errors", errors);
    }

    private List<String> validate(LineDto dto){
        return validator.validate(dto)
                .stream()
                .map(v -> v.getMessage())
                .collect(Collectors.toList());
    }

}