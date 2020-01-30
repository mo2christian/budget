package com.mo2christian.budget;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
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

    private LineService lineService;

    @Inject
    public LineResource(LineService lineService) {
        this.lineService = lineService;
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
    public TemplateInstance save(@FormParam("label") String label, @FormParam("amount") BigDecimal amount, @FormParam("type") LineType type){
        LineDto dto = new LineDto();
        dto.setAmount(amount);
        dto.setLabel(label);
        dto.setType(type);
        List<String> errors = validate(dto);
        if (errors.isEmpty())
            lineService.add(LineMapper.INSTANCE.toLine(dto));
        return build(errors);
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@Valid LineDto lineDto){
        Line line = LineMapper.INSTANCE.toLine(lineDto);
        lineService.add(line);
        return Response.ok().build();
    }

    @PUT
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
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
                .map(LineMapper.INSTANCE::toDto)
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