package com.mo2christian.budget;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/line")
@Produces(MediaType.APPLICATION_JSON)
public class LineResource {

    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello world";
    }

    @POST
    @Transactional
    public Response add(@Valid LineDto lineDto){
        Line line = toLine(lineDto);
        em.persist(line);
        return Response.ok().build();
    }

    @GET
    public Response getAll(){
        List<LineDto> dtos = em.createNamedQuery(Line.FIND_ALL, Line.class)
                .getResultList()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return Response.ok(dtos)
                .build();
    }

    @PUT
    @Transactional
    public Response update(@Valid LineDto dto){
        Line line = em.find(Line.class, dto.getId());
        if (line == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        line.setType(dto.getType());
        line.setLabel(dto.getLabel());
        line.setAmount(dto.getAmount());
        em.persist(line);
        return Response.ok().build();
    }

    @Path("{id}")
    @DELETE()
    @Transactional
    public Response delete(@PathParam("id") long id){
        Line line = em.find(Line.class, id);
        if (line == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        em.remove(line);
        return Response.ok().build();
    }

    @Path("{id}")
    @GET
    @Transactional
    public Response get(@PathParam("id") long id){
        Line line = em.find(Line.class, id);
        if (line == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        return Response.ok()
                .entity(line)
                .build();
    }

    private Line toLine(LineDto dto){
        Line line = new Line();
        line.setAmount(dto.getAmount());
        line.setLabel(dto.getLabel());
        line.setType(dto.getType());
        return line;
    }

    private LineDto toDto(Line line){
        LineDto dto = new LineDto();
        dto.setAmount(line.getAmount());
        dto.setId(line.getId());
        dto.setLabel(line.getLabel());
        dto.setType(line.getType());
        return dto;
    }
}