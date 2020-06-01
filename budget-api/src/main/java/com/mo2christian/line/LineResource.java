package com.mo2christian.line;

import com.mo2christian.common.converter.DateParamConverter;
import com.mo2christian.common.filter.ApiKey;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.annotation.security.RolesAllowed;
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
    Validator validator;

    private final LineService lineService;

    private final LineMapper mapper;

    @Inject
    public LineResource(LineService lineService, DateParamConverter converter) {
        this.lineService = lineService;
        mapper = new LineMapper(converter);
    }

    @GET
    @RolesAllowed("user")
    public TemplateInstance getAll(){
        return build(Collections.EMPTY_LIST);
    }

    @Path("/delete/{id}")
    @GET
    @Transactional
    @RolesAllowed("user")
    public TemplateInstance delete(@PathParam("id") Long id){
        Optional<Line> line = lineService.get(id);
        if (!line.isPresent()){
            List<String> errors = Arrays.asList(String.format("Line %d not found", id));
            return build(errors);
        }
        lineService.delete(line.get());
        return build(Collections.EMPTY_LIST);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    @RolesAllowed("user")
    public TemplateInstance save(@BeanParam LineDto dto){
        List<String> errors = validate(dto);
        if (errors.isEmpty())
            lineService.add(mapper.toLine(dto));
        return build(errors);
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @ApiKey
    public Response add(@Valid LineDto lineDto){
        Line line = mapper.toLine(lineDto);
        lineService.add(line);
        return Response.ok().build();
    }

    @PUT
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @ApiKey
    public Response update(@Valid LineDto dto){
        Line line = lineService.get(dto.getId())
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
        line.setType(dto.getType());
        line.setLabel(dto.getLabel());
        line.setAmount(dto.getAmount());
        lineService.add(line);
        return Response.ok().build();
    }

    @Path("{id}")
    @DELETE()
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @ApiKey
    public Response delete(@PathParam("id") long id){
        Line line = lineService.get(id)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
        lineService.delete(line);
        return Response.ok().build();
    }

    @Path("{id}")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @ApiKey
    public Response get(@PathParam("id") long id){
        Line line = lineService.get(id)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
        return Response.ok()
                .entity(line)
                .build();
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